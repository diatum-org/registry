package org.coredb.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import org.coredb.registry.NameRegistry;
import org.coredb.registry.service.ConfigService;

@Component
public class ScheduledTasks {

  @Autowired
  private ConfigService configService;
  
  private long intervalCount = 0;
  private long configInterval = 60;

  @Scheduled(fixedRate = 60000)
  public void reportCurrentTime() {
    if(intervalCount % configInterval == 0) {
      configInterval = configService.refresh();
    }
    intervalCount++;
  }
}

