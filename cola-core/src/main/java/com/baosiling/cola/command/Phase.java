package com.baosiling.cola.command;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Phase
 * 在处理复杂Command时，可以分成多个阶段（Phase）处理，每个阶段可以分成多个步骤（Step）
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Component
public @interface Phase {
}
