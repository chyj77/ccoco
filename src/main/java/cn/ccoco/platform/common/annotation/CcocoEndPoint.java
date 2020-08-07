package cn.ccoco.platform.common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author CCoco
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface CcocoEndPoint {
    @AliasFor(annotation = Component.class)
    String value() default "";
}
