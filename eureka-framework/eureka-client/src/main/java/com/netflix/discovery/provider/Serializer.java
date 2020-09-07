package com.netflix.discovery.provider;

import java.lang.annotation.*;

/**
 * An annotation that helps in specifying the custom serializer/de-serialization
 * implementation for jersey.
 *
 * <p>
 * Once the annotation is specified, a custom jersey provider invokes an
 * instance of the class specified as the value and dispatches all objects that
 * needs to be serialized/de-serialized to handle them as and only when it is responsible
 * for handling those.
 * </p>
 */
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Serializer {
    String value() default "";
}
