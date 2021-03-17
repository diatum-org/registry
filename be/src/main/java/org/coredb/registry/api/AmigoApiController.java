package org.coredb.registry.api;

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;
import org.coredb.registry.model.AmigoMessage;
import org.coredb.registry.model.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import javax.ws.rs.NotAcceptableException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.coredb.registry.service.EmigoService;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@Controller
public class AmigoApiController implements AmigoApi {

    private static final Logger log = LoggerFactory.getLogger(AmigoApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    private EmigoService amigoService;

    @org.springframework.beans.factory.annotation.Autowired
    public AmigoApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> getId(@NotNull @ApiParam(value = "handle to validate", required = true) @Valid @RequestParam(value = "handle", required = true) String handle, @ApiParam(value = "wrap response in quotes") @Valid @RequestParam(value = "wrap", required = false) Boolean wrap) {
      try {
        String id = amigoService.getId(handle);
        if(wrap != null && wrap == true) {
          id = "\"" + id + "\"";
        }
        return new ResponseEntity<String>(id, HttpStatus.OK);
      }
      catch(NotFoundException e) {
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
      }
      catch(Exception e) {
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    public ResponseEntity<AmigoMessage> getMessage(@ApiParam(value = "referenced id") @Valid @RequestParam(value = "amigoId", required = false) String amigoId,@ApiParam(value = "referenced handle") @Valid @RequestParam(value = "handle", required = false) String handle) {
      try {
        AmigoMessage msg = amigoService.getMessage(amigoId, handle);
        return new ResponseEntity<AmigoMessage>(msg, HttpStatus.OK);
      }
      catch(NotFoundException e) {
        return new ResponseEntity<AmigoMessage>(HttpStatus.NOT_FOUND);
      }
      catch(IllegalArgumentException e) {
        return new ResponseEntity<AmigoMessage>(HttpStatus.BAD_REQUEST);
      }
      catch(Exception e) {
        return new ResponseEntity<AmigoMessage>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    public ResponseEntity<Integer> getRevision(@ApiParam(value = "referenced id") @Valid @RequestParam(value = "amigoId", required = false) String amigoId,@ApiParam(value = "referenced handle") @Valid @RequestParam(value = "handle", required = false) String handle) {
      try {
        Integer rev = amigoService.getRevision(amigoId, handle);
        return new ResponseEntity<Integer>(rev, HttpStatus.OK);
      }
      catch(NotFoundException e) {
        return new ResponseEntity<Integer>(HttpStatus.NOT_FOUND);
      }
      catch(IllegalArgumentException e) {
        return new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
      }
      catch(Exception e) {
        return new ResponseEntity<Integer>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    public ResponseEntity<String> getName(@ApiParam(value = "referenced id") @Valid @RequestParam(value = "amigoId", required = false) String amigoId,@ApiParam(value = "referenced handle") @Valid @RequestParam(value = "handle", required = false) String handle) {
      try {
        String name = "\"" + amigoService.getName(amigoId, handle) + "\"";
        return new ResponseEntity<String>(name, HttpStatus.OK);
      }
      catch(NotFoundException e) {
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
      }
      catch(IllegalArgumentException e) {
        return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
      }
      catch(Exception e) {
        return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    public ResponseEntity<InputStreamResource> getLogo(@ApiParam(value = "referenced id") @Valid @RequestParam(value = "amigoId", required = false) String amigoId,@ApiParam(value = "referenced handle") @Valid @RequestParam(value = "handle", required = false) String handle) {
      try {
        InputStream stream = amigoService.getLogo(amigoId, handle);
        InputStreamResource resource = new InputStreamResource(stream);
        String mimeType = URLConnection.guessContentTypeFromStream(stream);
        return ResponseEntity.status(HttpStatus.OK)
              .contentType(MediaType.parseMediaType(mimeType))
              .contentLength(stream.available()).body(resource);
      }
      catch(NotFoundException e) {
        log.error(e.toString());
        return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
      }
      catch(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<InputStreamResource>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    public ResponseEntity<Result> getStatus(@NotNull @ApiParam(value = "handle to validate", required = true) @Valid @RequestParam(value = "handle", required = true) String handle,@ApiParam(value = "id of requesting amigo") @Valid @RequestParam(value = "amigoId", required = false) String amigoId) {
      try {
        Result res = amigoService.getStatus(handle, amigoId);
        return new ResponseEntity<Result>(res, HttpStatus.OK);
      }
      catch(Exception e) {
        return new ResponseEntity<Result>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    public ResponseEntity<List<String>> setBatch(@ApiParam(value = "amigo to message to update"  )  @Valid @RequestBody List<AmigoMessage> body) {
      try {
        List<String> ids = amigoService.setBatch(body);
        return new ResponseEntity<List<String>>(ids, HttpStatus.OK);
      } 
      catch(NotAcceptableException e) {
        log.error(e.toString());
        return new ResponseEntity<List<String>>(HttpStatus.NOT_ACCEPTABLE);
      }
      catch(IllegalArgumentException e) {
        log.error(e.toString());
        return new ResponseEntity<List<String>>(HttpStatus.BAD_REQUEST);
      }
      catch(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<List<String>>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    public ResponseEntity<Void> setMessage(@ApiParam(value = "amigo to message to update" ,required=true )  @Valid @RequestBody AmigoMessage body) {
      try {
        amigoService.setMessage(body);
        return new ResponseEntity<Void>(HttpStatus.OK);
      }
      catch(NotAcceptableException e) {
        log.error(e.toString());
        return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
      }
      catch(IllegalArgumentException e) {
        log.error(e.toString());
        return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
      }
      catch(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

}
