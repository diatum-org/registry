package org.coredb.registry.service.util;

import java.io.*;
import java.security.*;
import java.security.spec.*;
import java.util.*;
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

public class PageUtil {

  public static Pageable getPage(Integer offset, Integer limit, String sortField) {
    return getPage(offset, limit, sortField, true);
  }

  public static Pageable getPage(Integer offset, Integer limit, String sortField, Boolean asc) {

    // sort topics by name
    Sort.Order order;
    if(asc) {
      order = new Sort.Order(Sort.Direction.ASC, sortField);
    }
    else {
      order = new Sort.Order(Sort.Direction.DESC, sortField);
    }
    Sort sort = new Sort(order.ignoreCase());

    // create page
    if(offset == null && limit == null) {
      return new OffsetLimitPageable(0, Integer.MAX_VALUE, sort);
    }
    else if(offset != null && limit != null) {
      return new OffsetLimitPageable(offset, limit, sort);
    }
    else if(offset == null) {
      return new OffsetLimitPageable(0, limit, sort);
    }
    else {
      return new OffsetLimitPageable(offset, Integer.MAX_VALUE, sort);
    }
  }

}

