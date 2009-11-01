package de.vattenfall.is.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks a field whose value shall be stored and retrieved from session,
 * if its surrounding action bean shares at least one context scope with the
 * previously called bean.
 * <p/>
 * If no key is passed, the field will just be injectable in the specific
 * bean where it is declared. By providing a key, the field's value
 * can be re-injected in other beans with exactly the same type.
 *
 * @author Marcus Krassmann
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Context {

    /**
     * (optional) Specifies the context key where to place the field.
     * Defaults to the field's type + field's name.
     */
    String key() default "";

}
