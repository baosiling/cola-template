package com.baosiling.cola.command;


import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface PreInterceptor {

    Class<? extends Command>[] commands() default {};
}
