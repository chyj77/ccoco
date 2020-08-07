package cn.ccoco.platform.common.handler;

import cn.ccoco.platform.common.entity.CcocoResponse;
import cn.ccoco.platform.common.exception.CcocoException;
import cn.ccoco.platform.common.exception.FileDownloadException;
import cn.ccoco.platform.common.exception.LimitAccessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * @author CCoco
 */
@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public CcocoResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return  CcocoResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),"系统内部异常");
    }

    @ExceptionHandler(value = CcocoException.class)
    public CcocoResponse handleCcocoException(CcocoException e) {
        log.error("系统错误", e);
        return CcocoResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
    }

    /**
     * 统一处理请求参数校验(实体对象传参-form)
     *
     * @param e BindException
     * @return ccocoResponse
     */
    @ExceptionHandler(BindException.class)
    public CcocoResponse validExceptionHandler(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return CcocoResponse.fail(HttpStatus.BAD_REQUEST.value(),message.toString());
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return ccocoResponse
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CcocoResponse handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return CcocoResponse.fail(HttpStatus.BAD_REQUEST.value(),message.toString());
    }

    /**
     * 统一处理请求参数校验(json)
     *
     * @param e ConstraintViolationException
     * @return ccocoResponse
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CcocoResponse handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        log.error(message.toString(), e);
        return CcocoResponse.fail(HttpStatus.BAD_REQUEST.value(),message.toString());
    }

    @ExceptionHandler(value = LimitAccessException.class)
    public CcocoResponse handleLimitAccessException(LimitAccessException e) {
        log.error("LimitAccessException", e);
        return CcocoResponse.fail(HttpStatus.TOO_MANY_REQUESTS.value(),e.getMessage());
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public CcocoResponse handleAuthenticationException(AuthenticationException e) {
        log.error("AuthenticationException, {}", e.getMessage());
        return CcocoResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(),e.getMessage());
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public CcocoResponse handleAuthorizationException(UsernameNotFoundException e){
        log.error("UsernameNotFoundException, {}", e.getMessage());
        return CcocoResponse.fail(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
    }


    @ExceptionHandler(value = AccountExpiredException.class)
    public CcocoResponse handleExpiredSessionException(AccountExpiredException e) {
        log.error("AccountExpiredException", e);
        return CcocoResponse.fail(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
    }

    @ExceptionHandler(value = FileDownloadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleFileDownloadException(FileDownloadException e) {
        log.error("FileDownloadException", e);
    }
}
