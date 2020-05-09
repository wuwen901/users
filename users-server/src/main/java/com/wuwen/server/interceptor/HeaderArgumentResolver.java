package com.wuwen.server.interceptor;


import com.wuwen.api.enums.ErrorEnum;
import com.wuwen.server.annotation.CompanyIdResolver;
import com.wuwen.server.annotation.HeaderResolver;
import com.wuwen.server.annotation.UserIdResolver;
import com.wuwen.server.constant.ExceptionConstant;
import com.wuwen.server.exception.ParamException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义参数解析器
 *
 * @author wuwen
 */
@Slf4j
public class HeaderArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String COMPANY_ID = "companyId";

    private static final String USER_ID = "userId";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CompanyIdResolver.class)
                || methodParameter.hasParameterAnnotation(UserIdResolver.class)
                || methodParameter.hasParameterAnnotation(HeaderResolver.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) {
        HttpServletRequest nativeRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);

        if (null == nativeRequest) {
            return null;
        }

        if (methodParameter.hasParameterAnnotation(CompanyIdResolver.class)) {
            String companyId = nativeRequest.getHeader(COMPANY_ID);
            if (StringUtils.isEmpty(companyId)) {
                throw new ParamException(ErrorEnum.PARAM_ERROR.getCode(), ExceptionConstant.NON_COMPANY_ID);
            }
            log.info("companyId={}", companyId);
            return Long.valueOf(companyId);
        }

        if (methodParameter.hasParameterAnnotation(UserIdResolver.class)) {
            String userId = nativeRequest.getHeader(USER_ID);
            if (StringUtils.isEmpty(userId)) {
                throw new ParamException(ErrorEnum.PARAM_ERROR.getCode(), ExceptionConstant.NON_USER_ID);
            }
            log.info("userId={}", userId);
            return Long.valueOf(userId);
        }

        return null;
    }
}
