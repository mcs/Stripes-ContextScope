package de.vattenfall.is.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Utility class for handling annotations at runtime.
 * @author Marcus Krassmann
 */
public class AnnotationUtil {

    /**
     * Fetches all fields within a class that are annotated with the given annotation.
     * @param annotation the annotation
     * @param target the class where to look for annotated fields
     * @return all annotated fields, or an empty set if no field 
     */
    public static Collection<Field> getFields(final Class<? extends Annotation> annotation, final Class<?> target) {
        
        // first collect all accessible fields
        Collection<Field> fields = AccessController.doPrivileged(new PrivilegedAction<Collection<Field>>() {
            @Override
            public Collection<Field> run() {
                return new HashSet<Field>(Arrays.asList(target.getDeclaredFields()));
            }
        });
        Iterator<Field> it = fields.iterator();
        while (it.hasNext()) {
            final Field field = it.next();
            if (!field.isAnnotationPresent(annotation)) {
                it.remove();
            } else if (!field.isAccessible()) {
                try {
                    AccessController.doPrivileged(new PrivilegedAction<Object>() {
                        @Override
                        public Object run() {
                            field.setAccessible(true);
                            return null;
                        }
                    });
                } catch (SecurityException se) {
                    throw new RuntimeException(
                            "Field " + target.getName() + "." + field.getName() + "is marked " +
                            "with @Context and is not public. An attempt to call " +
                            "setAccessible(true) resulted in a SecurityException. Please " +
                            "either make the field public " +
                            "or modify your JVM security policy to allow ContextScope to " +
                            "setAccessible(true).", se);

                }
            }
        }
        return fields;
    }
}
