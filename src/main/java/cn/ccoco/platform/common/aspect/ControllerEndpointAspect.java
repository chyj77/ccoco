package cn.ccoco.platform.common.aspect;

import cn.ccoco.platform.common.annotation.ControllerEndpoint;
import cn.ccoco.platform.common.exception.CcocoException;
import cn.ccoco.platform.common.utils.CcocoUtil;
import cn.ccoco.platform.monitor.service.ILogService;
import cn.ccoco.platform.system.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ServerWebExchange;

import java.lang.reflect.Method;

/**
 * @author CCoco
 */
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class ControllerEndpointAspect extends BaseAspectSupport {

    private final ILogService logService;

    @Pointcut("@annotation(cn.ccoco.platform.common.annotation.ControllerEndpoint)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws CcocoException {
        Object result;
        Object[] args = point.getArgs();
        ServerWebExchange exchange = (ServerWebExchange) args[1];
        ServerHttpRequest request = exchange.getRequest();
        Method targetMethod = resolveMethod(point);
        ControllerEndpoint annotation = targetMethod.getAnnotation(ControllerEndpoint.class);
        String operation = annotation.operation();
        long start = System.currentTimeMillis();
        try {
            result = point.proceed();
            if (StringUtils.isNotBlank(operation)) {
                RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
                ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) attributes;
                String ip = StringUtils.EMPTY;
                if (servletRequestAttributes != null) {
                    ip = request.getRemoteAddress().getAddress().getHostAddress();
                }
                // 设置操作用户
                User user = new User();
                //todo
                logService.saveLog(user, point, targetMethod, ip, operation, start);
            }
            return result;
        } catch (Throwable throwable) {
            log.error(throwable.getMessage(), throwable);
            String exceptionMessage = annotation.exceptionMessage();
            String message = throwable.getMessage();
            String error = CcocoUtil.containChinese(message) ? exceptionMessage + "，" + message : exceptionMessage;
            throw new CcocoException(error);
        }
    }
}



