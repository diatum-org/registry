package org.coredb.registry.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.coredb.registry.NameRegistry;

@Service
public class ConsoleService {

  @Autowired
  ConfigService configService;

  public boolean checkAccess(String t) {
    String token = configService.getServerStringValue(NameRegistry.SC_CONSOLE_ACCESS, null);
    if(t == null || token == null) {
      return false;
    }
    return token.equals(t);
  }
}

