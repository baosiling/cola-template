package com.netflix.discovery.converters;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A field annotation which helps in avoiding changes to
 * {@link com.netflix.discovery.converters.Converters.InstanceInfoConverter} every time additional fields are added to
 * {@link com.netflix.appinfo.InstanceInfo}.
 *
 * <p>
 * This annotation informs the {@link com.netflix.discovery.converters.Converters.InstanceInfoConverter} to
 * automatically marshall most primitive fields declared in the {@link com.netflix.appinfo.InstanceInfo} class.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Auto {
}
