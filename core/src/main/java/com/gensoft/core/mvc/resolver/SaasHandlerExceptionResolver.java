package com.gensoft.core.mvc.resolver;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import com.gensoft.core.web.ApiResult;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * 异常处理，把异常转成提示语
 * 处理业务异常、自动绑定校验异常和根异常
 */
public class SaasHandlerExceptionResolver extends AbstractHandlerExceptionResolver {
    public SaasHandlerExceptionResolver() {
        setOrder(HIGHEST_PRECEDENCE);
    }

    //private MessageSource messageSource;
    /**
     * Actually resolve the given exception that got thrown during on handler execution,
     * returning a ModelAndView that represents a specific error page if appropriate.
     * <p>May be overridden in subclasses, in order to apply specific exception checks.
     * Note that this template method will be invoked <i>after</i> checking whether this
     * resolved applies ("mappedHandlers" etc), so an implementation may simply proceed
     * with its actual exception handling.
     *
     * @param request  current HTTP request
     * @param response current HTTP response
     * @param handler  the executed handler, or {@code null} if none chosen at the time
     *                 of the exception (for example, if multipart resolution failed)
     * @param ex       the exception that got thrown during handler execution
     * @return a corresponding ModelAndView to forward to, or {@code null} for default processing
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        ApiResult apiResult = ApiResult.failedInstance("todo", ApiResult.CODE_UNHANDLED_BUG);
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write(apiResult.toString());
        } catch (Exception e) {

        }
        return new ModelAndView();
    }

}

