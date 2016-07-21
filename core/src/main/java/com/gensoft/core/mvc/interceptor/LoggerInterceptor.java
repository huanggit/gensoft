package com.gensoft.core.mvc.interceptor;

import com.gensoft.core.annotation.AnonymousAccess;
import com.gensoft.core.pojo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;


/**
 * logger interceptor
 */
public class LoggerInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String START_TIME_KEY = "START_TIME_KEY";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        setStartTime(request);
        logger.info("[request http] request：{}, from: {}.",
                new Object[]{request.getRequestURI(), request.getRemoteAddr()});
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            Long startTime = (Long) requestAttributes.getAttribute(START_TIME_KEY,RequestAttributes.SCOPE_REQUEST);
            long cost = System.currentTimeMillis() - startTime;
            logger.info("[response http] request：{}, from: {}, response：{}, time cost: {} ms.",
                    new Object[]{request.getRequestURI(), request.getRemoteAddr(), "N/A", cost});
    }

    /**
     * 设置开始时间，记录接口总耗时
     *
     * @param request
     */
    private void setStartTime(HttpServletRequest request) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        if (requestAttributes.getAttribute(
                START_TIME_KEY, RequestAttributes.SCOPE_REQUEST) == null)
            requestAttributes.setAttribute(START_TIME_KEY, System.currentTimeMillis(),
                    RequestAttributes.SCOPE_REQUEST);
    }

}
