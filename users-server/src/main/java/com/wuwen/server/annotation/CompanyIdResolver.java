package com.wuwen.server.annotation;

import java.lang.annotation.*;

/**
 * @author wuwen
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CompanyIdResolver {
}
