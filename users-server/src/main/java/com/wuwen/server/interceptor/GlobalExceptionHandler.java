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

/**
 * @author wuwen
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数异常
     */
    @ExceptionHandler(value = {ParamException.class, IllegalArgumentException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public RpcResult handleParamException(RuntimeException se) {
        log.info("参数异常：" + se.getMessage());
        return RpcResult.buildFail(ErrorEnum.PARAM_ERROR.getCode(), se.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(value = {ServiceException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public RpcResult handleServiceException(RuntimeException se) {
        log.info("业务异常：" + se.getMessage());
        return RpcResult.buildFail(ErrorEnum.PARAM_ERROR.getCode(), se.getMessage());
    }

    /**
     * 服务请求处理异常
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(code = HttpStatus.OK)
    public RpcResult handleException(Exception se) {
        log.info("服务请求处理异常：" + se.getMessage());
        return RpcResult.buildFail(ErrorEnum.SYSTEM_ERROR.getCode(), se.getMessage());
    }

    /**
     * 参数校验处理异常
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(code = HttpStatus.OK)
    public RpcResult handleMethodArgumentNotValidException(Exception se) {
        log.info("参数校验处理异常：" + se.getMessage());
        String message = se.getMessage();
        if (se instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) se;
            message = validException.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        }
        return RpcResult.buildFail(ErrorEnum.PARAM_ERROR.getCode(), message);
    }
}
