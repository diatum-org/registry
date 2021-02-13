package org.coredb.registry.service;

import java.io.*;
import java.util.*;
import java.time.Instant;
import java.security.*;
import java.security.spec.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.*;

import javax.ws.rs.NotAcceptableException;
import org.coredb.registry.api.NotFoundException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import java.nio.charset.StandardCharsets;

import static org.coredb.registry.NameRegistry.*;

import org.coredb.registry.model.Emigo;
import org.coredb.registry.model.EmigoMessage;
import org.coredb.registry.model.Result;

import org.coredb.registry.jpa.entity.Account;
import org.coredb.registry.service.util.PageUtil;

import org.coredb.registry.jpa.repository.AccountRepository;
import org.coredb.registry.jpa.repository.ServerStatRepository;

@Service
public class EmigoService {

  @Autowired
  private ServerStatRepository serverStatRepository;

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private ConfigService configService;

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public Result getStatus(String handle, String emigoId) {

    // check if any records are using specified handle
    List<Account> emigos = accountRepository.findByHandle(handle);
    Result res = new Result();
    if(emigos.isEmpty()) {
      res.setBoolValue(true);
    }
    else if(emigoId != null) {
      if(emigos.get(0).getEmigoId().equals(emigoId)) {
        res.setBoolValue(true);
      }
      else {
        res.setBoolValue(false);
      }
    }
    else {
      res.setBoolValue(false);
    }
    return res;
  }

  public String getId(String handle) throws NotFoundException {

    // retrieve emigo id for handle
    List<Account> emigos = accountRepository.findByHandle(handle);
    if(emigos.isEmpty()) {
      throw new NotFoundException(404, "emigo handle not found");
    }
    return emigos.get(0).getEmigoId();
  }

  public EmigoMessage getMessage(String emigoId, String handle) throws NotFoundException, IllegalArgumentException {
    
    // retrieve any records for id
    if(emigoId != null) {
      List<Account> emigos = accountRepository.findByEmigoId(emigoId);
      if(emigos.isEmpty()) {
        throw new NotFoundException(404, "emigo id not found");
      }
      return emigos.get(0);
    }
    else if(handle != null) {
      List<Account> emigos = accountRepository.findByHandle(handle);
      if(emigos.isEmpty()) {
        throw new NotFoundException(404, "emigo handle not found");
      }
      return emigos.get(0);
    }
    throw new IllegalArgumentException("id or handle not specified");
  }

  public InputStream getLogo(String emigoId, String handle) throws NotFoundException, IllegalArgumentException {
     
    // retrieve any records for id
    if(emigoId != null) {
      List<Account> emigos = accountRepository.findByEmigoId(emigoId);
      if(emigos.isEmpty() || emigos.get(0).getLogo() == null) {
        throw new NotFoundException(404, "emigo id logo not found");
      }
      byte[] bytes = emigos.get(0).getLogo();
      return new ByteArrayInputStream(bytes);
    }
    else if(handle != null) {
      List<Account> emigos = accountRepository.findByHandle(handle);
      if(emigos.isEmpty() || emigos.get(0).getLogo() == null) {
        throw new NotFoundException(404, "emigo handle logo not found");
      }
      byte[] bytes = emigos.get(0).getLogo();
      return new ByteArrayInputStream(bytes);
    }
    throw new IllegalArgumentException("id or handle not specified");
  }

  public String getName(String emigoId, String handle) throws NotFoundException, IllegalArgumentException {
     
    // retrieve any records for id
    if(emigoId != null) {
      List<Account> emigos = accountRepository.findByEmigoId(emigoId);
      if(emigos.isEmpty()) {
        throw new NotFoundException(404, "emigo id not found");
      }
      return emigos.get(0).getName();
    }
    else if(handle != null) {
      List<Account> emigos = accountRepository.findByHandle(handle);
      if(emigos.isEmpty()) {
        throw new NotFoundException(404, "emigo handle not found");
      }
      return emigos.get(0).getName();
    }
    throw new IllegalArgumentException("id or handle not specified");
  }

  public Integer getRevision(String emigoId, String handle) throws NotFoundException, IllegalArgumentException {
     
    // retrieve any records for id
    if(emigoId != null) {
      List<Account> emigos = accountRepository.findByEmigoId(emigoId);
      if(emigos.isEmpty()) {
        throw new NotFoundException(404, "emigo id not found");
      }
      return emigos.get(0).getRevision();
    }
    else if(handle != null) {
      List<Account> emigos = accountRepository.findByHandle(handle);
      if(emigos.isEmpty()) {
        throw new NotFoundException(404, "emigo handle not found");
      }
      return emigos.get(0).getRevision();
    }
    throw new IllegalArgumentException("id or handle not specified");
  }

  public static PublicKey readPubkey(String key, String type) throws Exception {
    
    // load public key data
    try {
      byte[] bytes = Hex.decodeHex(key);
      KeyFactory publicKeyFactory = KeyFactory.getInstance(type);
      X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(bytes);
      return publicKeyFactory.generatePublic(publicKeySpec);
    }
    catch(Exception e) {
      throw new Exception("failed to load key data");
    }
  }

  private Boolean verify(String data, String signature, PublicKey publicKey) throws Exception {

    // validate signature
    try {
      Signature publicSignature = Signature.getInstance("SHA256withRSA");
      publicSignature.initVerify(publicKey);
      publicSignature.update(data.getBytes(StandardCharsets.UTF_8));
      byte[] signatureBytes = Hex.decodeHex(signature);
      return publicSignature.verify(signatureBytes);
    }
    catch(Exception e) {
      throw new Exception("failed to compute signature");
    }
  }

  private String getEmigoId(PublicKey publicKey) throws Exception {
  
    // hash key to evaludate emigo id
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-256");
      return Hex.encodeHexString(sha.digest(publicKey.getEncoded()));
    }
    catch(Exception e) {
      throw new Exception("failed to evaluate emigo id");
    }
  }

  private Emigo decode(String base)  throws IllegalArgumentException {
    
    // deserialize message data
    try {
      byte[] bytes = Base64.getDecoder().decode(base);
      String serial = new String(bytes);
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(serial, Emigo.class);
    }
    catch(Exception e) {
      throw new IllegalArgumentException("invalid emigo message");
    }
  }

  private Emigo getEmigo(EmigoMessage msg) throws IllegalArgumentException, Exception {

    // validate emigo message
    if(msg.getKey() == null || msg.getKeyType() == null || msg.getData() == null || msg.getSignature() == null) {
      throw new IllegalArgumentException("incomplete emigo message");
    }

    // load the public key object
    PublicKey key = readPubkey(msg.getKey(), msg.getKeyType());

    //validate signature
    if(!verify(msg.getData(), msg.getSignature(), key)) {
      throw new IllegalArgumentException("emigo signature error");
    }

    //populate data entry
    Emigo emigo = decode(msg.getData());

    //validate key and id
    if(!emigo.getEmigoId().equals(getEmigoId(key))) {
      throw new IllegalArgumentException("emigo key id mismatch");
    }

    return emigo;
  }

  @Transactional
  public List<String> setBatch(List<EmigoMessage> msgs) {
    List<String> ids = new ArrayList<String>();
    for(EmigoMessage msg: msgs) {
      try {
        ids.add(setMessage(msg));
      }
      catch(Exception e) {
        System.out.println(e.toString());
      }
    }
    return ids;
  }

  private byte[] getEmigoLogo(Emigo emigo) {
    if(emigo == null || emigo.getLogo() == null) {
      return null;
    }
    try {
      return Base64.getDecoder().decode(emigo.getLogo());
    }
    catch(Exception e) {
      log.error(e.toString());
      return null;
    }
  }

  @Transactional
  public String setMessage(EmigoMessage msg) throws IllegalArgumentException, NotAcceptableException, Exception {
    
    // extract emigo object
    Emigo emigo = getEmigo(msg);

    // get current time
    Long cur = Instant.now().getEpochSecond();

    // handle must be null if not part of registry
    String reg = configService.getServerStringValue(SC_SERVER_HOST_PORT, null);

    // handles must be null if not part of registry
    if(emigo.getRegistry() != null && emigo.getRegistry().equals(reg) == false) {
      if(emigo.getHandle() != null) {
        throw new NotAcceptableException("handle must be null of registry doesn't match");
      }
    }

    // retrieve any accounts with matching handle
    List<Account> accounts = accountRepository.findByHandle(emigo.getHandle());

    // retrieve any accounts with matching emigo id
    List<Account> emigos = accountRepository.findByEmigoId(emigo.getEmigoId());
    if(emigos.isEmpty()) {

      // check if handle is available
      if(emigo.getHandle() != null && !accounts.isEmpty()) {
        throw new NotAcceptableException("handle already taken");
      }
      Account account = new Account();
      account.setEmigoId(emigo.getEmigoId());
      account.setHandle(emigo.getHandle());
      account.setName(emigo.getName());
      account.setLogo(getEmigoLogo(emigo));
      account.setRegistry(emigo.getRegistry());
      account.setCreated(cur);
      account.setUpdated(cur);
      account.setRevision(emigo.getRevision());
      account.setMessage(msg.getData());
      account.setSignature(msg.getSignature());
      account.setPubkey(msg.getKey());
      account.setPubkeyType(msg.getKeyType());
      account.setBlocked(false);
      accountRepository.save(account);
    }
    else {

      // check if changing handle to something not null
      Account account = emigos.get(0);
      if(emigo.getHandle() != null) {
        // check if account was null or not the specified handle
        if(account.getHandle() == null || !account.getHandle().equals(emigo.getHandle())) {
          // check if new handle already taken
          if(!accounts.isEmpty()) {
            throw new NotAcceptableException("handle already taken");
          }
        }
      }
      
      // check if revision is more recent
      if(emigo.getRevision() > account.getRevision()) {
        account.setHandle(emigo.getHandle());
        account.setName(emigo.getName());
        account.setLogo(getEmigoLogo(emigo));
        account.setRegistry(emigo.getRegistry());
        account.setUpdated(cur);
        account.setRevision(emigo.getRevision());
        account.setMessage(msg.getData());
        account.setSignature(msg.getSignature());
        account.setPubkey(msg.getKey());
        account.setPubkeyType(msg.getKeyType());
        accountRepository.save(account);
      }
    }

    return emigo.getEmigoId();
  }
}

