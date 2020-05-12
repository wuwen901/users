package com.wuwen.server.interceptor;

import com.wuwen.api.enums.ErrorEnum;
import com.wuwen.api.util.RpcResult;
import com.wuwen.server.exception.ParamException;
import com.wuwen.server.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/** @author wuwen */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  private final HttpServletRequest request;

  public GlobalExceptionHandler(HttpServletRequest request) {
    this.request = request;
  }

  /** 参数异常 */
  @ExceptionHandler(value = {ParamException.class, IllegalArgumentException.class})
  @ResponseStatus(code = HttpStatus.OK)
  public RpcResult handleParamException(RuntimeException se) {
    log.info(
        "请求地址{},请求方式{},请求参数{},参数异常：",
        request.getRequestURL(),
        request.getMethod(),
        getRequestBody(request),
        se);
    return RpcResult.buildFail(ErrorEnum.PARAM_ERROR.getCode(), se.getMessage());
  }

  /** 业务异常 */
  @ExceptionHandler(value = {ServiceException.class})
  @ResponseStatus(code = HttpStatus.OK)
  public RpcResult handleServiceException(RuntimeException se) {
    log.info(
        "请求地址{},请求方式{},请求参数{},业务异常：",
        request.getRequestURL(),
        request.getMethod(),
        getRequestBody(request),
        se);
    return RpcResult.buildFail(ErrorEnum.PARAM_ERROR.getCode(), se.getMessage());
  }

  /** 服务请求处理异常 */
  @ExceptionHandler(value = {Exception.class})
  @ResponseStatus(code = HttpStatus.OK)
  public RpcResult handleException(Exception se) {
    log.info(
        "请求地址{},请求方式{},请求参数{},服务请求处理异常：",
        request.getRequestURL(),
        request.getMethod(),
        getRequestBody(request),
        se);
    return RpcResult.buildFail(ErrorEnum.SYSTEM_ERROR.getCode(), se.getMessage());
  }

  /** 参数校验处理异常 */
  @ExceptionHandler(value = {MethodArgumentNotValidException.class})
  @ResponseStatus(code = HttpStatus.OK)
  public RpcResult handleMethodArgumentNotValidException(Exception se) {
    log.info(
        "请求地址{},请求方式{},请求参数{},参数校验处理异常：",
        request.getRequestURL(),
        request.getMethod(),
        getRequestBody(request),
        se);
    String message = se.getMessage();
    if (se instanceof MethodArgumentNotValidException) {
      MethodArgumentNotValidException validException = (MethodArgumentNotValidException) se;
      message = validException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }
    return RpcResult.buildFail(ErrorEnum.PARAM_ERROR.getCode(), message);
  }

  private static String getRequestBody(HttpServletRequest request) {
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();
    try {
      br = request.getReader();
      String str;
      while ((str = br.readLine()) != null) {
        sb.append(str);
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != br) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return sb.toString();
  }
}
