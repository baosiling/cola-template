package com.baosiling.cola.command;


import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface PostInterceptor {

    Class<? extends Command>[] commands() default {};
}
