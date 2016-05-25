package com.gensoft.core.mvc.interceptor;

import com.gensoft.core.annotation.AnonymousAccess;
import com.gensoft.core.pojo.UserInfo;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * 登录认证拦截,没有登录且cookie里用登录信息,则尝试自动登录
 */
public class AuthenticateInterceptor extends HandlerInterceptorAdapter {

    private static final String START_TIME_KEY = "START_TIME_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setStartTime(request);
        if(!HandlerMethod.class.isAssignableFrom(handler.getClass()))return true;
        UserInfo user = getSessionUser(request);
        if(user != null)
            return true;
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(isAllowAnonymous(handlerMethod))return true;
        throw new Exception("请登录");
    }

    /**
     * 设置开始时间，记录接口总耗时
     * @param request
     */
    private void setStartTime(HttpServletRequest request){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if(requestAttributes.getAttribute(
                START_TIME_KEY,RequestAttributes.SCOPE_REQUEST) == null)
            requestAttributes.setAttribute(START_TIME_KEY, System.currentTimeMillis(),
                    RequestAttributes.SCOPE_REQUEST);
    }

    private UserInfo getSessionUser(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        UserInfo user = null;
        if(session != null)
            user = (UserInfo) session.getAttribute("userInfo");
        return user;
    }

    private boolean isAllowAnonymous(HandlerMethod handlerMethod){
        if(AnnotationUtils.isAnnotationDeclaredLocally(AnonymousAccess.class,handlerMethod.getBeanType()))return true;
        return handlerMethod.getMethodAnnotation(AnonymousAccess.class) != null;
    }
}

