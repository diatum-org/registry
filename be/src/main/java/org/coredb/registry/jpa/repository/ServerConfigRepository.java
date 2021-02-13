package org.coredb.registry.jpa.repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Set;

import org.coredb.registry.jpa.entity.ServerConfig;

public interface ServerConfigRepository
      extends JpaRepository<ServerConfig, Integer>, JpaSpecificationExecutor<ServerConfig> {
  List<ServerConfig> findAll();
  List<ServerConfig> findByName(String key);
  List<ServerConfig> findByConfigId(String id);
  Long deleteByName(String key);
  Long deleteByConfigId(String id);
}

