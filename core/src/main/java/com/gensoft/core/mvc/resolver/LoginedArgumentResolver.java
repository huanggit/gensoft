package com.gensoft.core.mvc.resolver;

import com.gensoft.core.annotation.Login;
import com.gensoft.core.pojo.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginedArgumentResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.getParameterAnnotation(Login.class) != null &&
                parameter.getParameterType() == UserInfo.class) {
            return true;
        }
        return false;
    }

    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession httpSession = request.getSession(false);
        if(httpSession == null)
            return null;
        UserInfo user = (UserInfo) httpSession.getAttribute("userInfo");
        return user;
    }
}

