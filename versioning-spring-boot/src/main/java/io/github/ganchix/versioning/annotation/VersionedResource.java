package io.github.ganchix.versioning.annotation;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates the versions limit and accept headers.
 *
 * Created by Rafa on 24/01/17.
 */
@RequestMapping
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionedResource {

    String media() default "";

    String from() default "";

    String to() default "99.99";

}
