package org.coredb.registry.api;

import org.coredb.registry.model.SystemStat;
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

import org.coredb.registry.service.AuthService;
import org.coredb.registry.service.ServerStatService;
import java.nio.file.AccessDeniedException;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@Controller
public class AdminApiController implements AdminApi {

    private static final Logger log = LoggerFactory.getLogger(AdminApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    private AuthService authService;

    @org.springframework.beans.factory.annotation.Autowired
    private ServerStatService statService;

    @org.springframework.beans.factory.annotation.Autowired
    public AdminApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> adminServerStat(@NotNull @Min(0) @Max(100) @ApiParam(value = "time", required = true, allowableValues = "") @Valid @RequestParam(value = "processor", required = true) Integer processor,@NotNull @ApiParam(value = "current memory free", required = true) @Valid @RequestParam(value = "memory", required = true) Long memory,@NotNull @ApiParam(value = "current storage free", required = true) @Valid @RequestParam(value = "storage", required = true) Long storage,@ApiParam(value = "access token") @Valid @RequestParam(value = "token", required = false) String token) {
    try {
        authService.statToken(token);
        statService.addStat(processor, memory, storage);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
      }
      catch(AccessDeniedException e) {
        log.error(e.toString());
        return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
      }
      catch(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

    public ResponseEntity<List<SystemStat>> adminServerStats(@NotNull @ApiParam(value = "access token", required = true) @Valid @RequestParam(value = "token", required = true) String token,@ApiParam(value = "offset into record set") @Valid @RequestParam(value = "offset", required = false) Integer offset,@ApiParam(value = "max size of record set") @Valid @RequestParam(value = "limit", required = false) Integer limit) {
    try {
        authService.statToken(token);
        List<SystemStat> stats = statService.getStats(offset, limit);
        return new ResponseEntity<List<SystemStat>>(stats, HttpStatus.OK);
      }
      catch(AccessDeniedException e) {
        log.error(e.toString());
        return new ResponseEntity<List<SystemStat>>(HttpStatus.UNAUTHORIZED);
      }
      catch(Exception e) {
        log.error(e.toString());
        return new ResponseEntity<List<SystemStat>>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }

}
