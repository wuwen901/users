package com.wuwen.server.interceptor;


import com.alibaba.fastjson.JSONObject;
import com.wuwen.api.util.RpcResult;
import com.wuwen.server.constant.ExceptionConstant;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wuwen
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    private List<String> allows = new ArrayList<>();

    public AuthorizationInterceptor() {
        allows.add("bss/login");
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        for (String allow : allows) {
            if (allow.equals(servletPath)) {
                return true;
            }
        }

        return true;
    }

    private void resultAuth(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset = utf-8");
        RpcResult<Object> objectRpcResult = RpcResult.buildFail(HttpStatus.UNAUTHORIZED.value(), ExceptionConstant.NON_LOGIN);
        try (PrintWriter out = response.getWriter()) {
            out.append(JSONObject.toJSONString(objectRpcResult));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
