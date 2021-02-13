package org.coredb.registry.service;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.coredb.registry.jpa.repository.ServerConfigRepository;
import static org.coredb.registry.NameRegistry.*;
import org.coredb.registry.jpa.entity.ServerConfig;

@Service
public class ConfigService {

  public class ConfigServiceValue {
    public String strValue;
    public Long numValue;
    public Boolean boolValue;
    ConfigServiceValue() { }
    ConfigServiceValue(String val) { strValue = val; }
    ConfigServiceValue(Long val) { numValue = val; }
    ConfigServiceValue(Boolean val) { boolValue = val; }
    ConfigServiceValue(String s, Long n, Boolean b) {
      this.strValue = s;
      this.numValue = n;
      this.boolValue = b;
    }
  }

  private Map<String, ConfigServiceValue> serverConfigs =
      new HashMap<String, ConfigServiceValue>();

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private ServerConfigRepository serverConfigRepository;

  // to be invoked on configured interval
  public long refresh() {

    // refresh all server configs
    List<ServerConfig> servers = serverConfigRepository.findAll();
    serverConfigs.clear();
    for(ServerConfig server: servers) {
      String configId = server.getConfigId();
      serverConfigs.put(configId,
          new ConfigServiceValue(server.getStringValue(), server.getNumValue(), server.getBoolValue()));
    }

    return getServerNumValue(SC_CONFIG_REFRESH_INTERVAL, (long)60).longValue();
  }

  public String getServerStringValue(String configId, String unset) {
    ConfigServiceValue val = getServerConfig(configId);
    if(val == null || val.strValue == null) {
      return unset;
    }
    else {
      return val.strValue;
    }
  }

  public Long getServerNumValue(String configId, Long unset) {
    ConfigServiceValue val = getServerConfig(configId);
    if(val == null || val.numValue == null) {
      return unset;
    }
    else {
      return val.numValue;
    }
  }

  public Boolean getServerBoolValue(String configId, Boolean unset) {
    ConfigServiceValue val = getServerConfig(configId);
    if(val == null || val.boolValue == null) {
      return unset;
    }
    else {
      return val.boolValue;
    }
  }

  public ConfigServiceValue getServerConfig(String configId) {

    // return value if present
    return serverConfigs.get(configId);
  }
}


