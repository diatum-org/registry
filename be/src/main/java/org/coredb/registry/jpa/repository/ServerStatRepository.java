package org.coredb.registry.jpa.repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;
import java.util.Set;

import org.coredb.registry.jpa.entity.ServerStat;

public interface ServerStatRepository
      extends JpaRepository<ServerStat, Integer>, JpaSpecificationExecutor<ServerStat> {
  List<ServerStat> findByTimestampLessThanEqualOrderByTimestampDesc(Integer start);
  List<ServerStat> findByTimestampGreaterThanEqualOrderByTimestampDesc(Integer end);
  List<ServerStat> findByTimestampLessThanEqualAndTimestampGreaterThanEqualOrderByTimestampDesc(Integer start, Integer end);

  List<ServerStat> findAllByOrderByTimestampDesc();
  Long deleteByTimestampLessThanEqual(Integer ts);
}


