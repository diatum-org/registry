package org.coredb.registry.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.coredb.registry.api.NotFoundException;
import java.nio.file.AccessDeniedException;

import static org.coredb.registry.NameRegistry.*;
import org.coredb.registry.service.ConfigService.ConfigServiceValue;

@Service
public class AuthService {

  @Autowired
  private ConfigService configService;

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  public void statToken(String token) throws AccessDeniedException {
    compareToken(SC_STAT_TOKEN, token);
  }

  private void compareToken(String id, String token) throws AccessDeniedException {

    // compare with stored  token
    ConfigServiceValue config = configService.getServerConfig(id);
    if(config == null || config.strValue == null) {
      throw new AccessDeniedException("admin token not set");
    }
    if(!config.strValue.equals(token)) {
      throw new AccessDeniedException("incorrect admin token");
    }
  }
}

