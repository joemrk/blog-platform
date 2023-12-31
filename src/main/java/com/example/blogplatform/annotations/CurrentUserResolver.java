package com.example.blogplatform.annotations;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.example.blogplatform.user.User;


public class CurrentUserResolver implements HandlerMethodArgumentResolver {
  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(CurrentUser.class) != null && parameter.getParameterType().equals(User.class);
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
    if (supportsParameter(parameter)) {
      Object current = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if(current instanceof User) {
        return current;
      }
    }
    return null;
  }


  // also it might be used as
  // @AuthenticationPrincipal User user
  // without some custom annotations
}
