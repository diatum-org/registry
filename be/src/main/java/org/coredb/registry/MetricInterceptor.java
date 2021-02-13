package org.coredb.registry;

import org.springframework.beans.factory.annotation.Autowired;

import org.coredb.registry.service.ServerStatService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class MetricInterceptor implements HandlerInterceptor {

  @Autowired
  private ServerStatService statService;

   @Override
   public boolean preHandle
      (HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

      statService.incrementRequest();
      return true;
   }
   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response,
      Object handler, ModelAndView modelAndView) throws Exception {
   }
   @Override
   public void afterCompletion
      (HttpServletRequest request, HttpServletResponse response, Object
      handler, Exception exception) throws Exception {
   }
}

