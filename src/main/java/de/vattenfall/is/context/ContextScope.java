package de.vattenfall.is.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks an action bean to be part of one or more context scopes.
 * <p/>
 * By belonging to at least one context scope, fields annotated with @Context
 * are saved in the user's session when leaving the bean, and are repopulated
 * when re-entering the bean without having left the scope between these calls.
 *
 * @author Marcus Krassmann
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContextScope {

    /**
     * List of scopes this class may belong to.
     */
    String[] scopes();
}
