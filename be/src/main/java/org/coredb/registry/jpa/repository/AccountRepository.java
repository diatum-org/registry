package org.coredb.registry.jpa.repository;

import org.springframework.data.jpa.repository.*;

import org.coredb.registry.jpa.entity.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
  List<Account> findByEmigoId(String id);
  List<Account> findByHandle(String handle);
  long count();
}

