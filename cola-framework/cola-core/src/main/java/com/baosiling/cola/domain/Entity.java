package com.baosiling.cola.domain;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Entity, Entity Object is prototype and is not thread-safe.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public @interface Entity {
}
