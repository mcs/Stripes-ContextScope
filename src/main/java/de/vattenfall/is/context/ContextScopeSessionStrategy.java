package de.vattenfall.is.context;

import de.vattenfall.is.util.AnnotationUtil;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpSession;
import net.sourceforge.stripes.action.ActionBean;

/**
 * Some utility methods to access and store values in context scope.
 *
 * @author Marcus Krassmann
 */
public class ContextScopeSessionStrategy {

    private static final String VALUES = "context_scope.values";
    private static final String SCOPES = "context_scope.scopes";
    private final HttpSession session;

    public ContextScopeSessionStrategy(HttpSession session) {
        this.session = session;
    }

    @SuppressWarnings("unchecked")
    public Set<String> getSessionScopes() {
        Set<String> result = (Set<String>) session.getAttribute(SCOPES);
        return result == null ? new HashSet<String>() : result;
    }

    public void setSessionScopes(String[] beanScopes) {
        Set<String> scopes = new HashSet<String>(Arrays.asList(beanScopes));
        Set<String> currentScopes = getSessionScopes();
        currentScopes.retainAll(scopes);
        if (currentScopes.isEmpty()) {
            // brand new scopes submitted, remove previously stored values
            session.removeAttribute(VALUES);
            currentScopes = scopes;
        }
        session.setAttribute(SCOPES, currentScopes);
    }

    @SuppressWarnings("unchecked")
    public Object getValue(String key) {
        Map<String, Object> values = getValueMap();
        return values.get(key);
    }

    public void setValue(String key, Object value) {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = getValueMap();
        values.put(key, value);
        session.setAttribute(VALUES, values);
    }

    public Map<String, Object> getValueMap() {
        @SuppressWarnings("unchecked")
        Map<String, Object> values = (Map<String, Object>) session.getAttribute(VALUES);
        if (values == null) {
            values = new HashMap<String, Object>();
        }
        return values;
    }

    public boolean isInScope(ContextScope beanScope) {
        // copy session scopes, will be changed
        Set<String> scopes = getSessionScopes();
        for (String each : beanScope.scopes()) {
            if (scopes.contains(each)) {
                return true;
            }
        }
        return false;
    }

    public void injectContextValues(ActionBean bean) {
        Collection<Field> fields = AnnotationUtil.getFields(Context.class, bean.getClass());
        for (Field field : fields) {
            String key = getKey(bean, field);
            Object value = getValue(key);
            if (value != null) {
                try {
                    field.set(bean, value);
                } catch (IllegalAccessException ex) {
                    String msg = String.format("Unexpected error while injecting value '%s' into field '%s'", value, field);
                    throw new RuntimeException(msg, ex);
                }

            }
        }
    }

    public String getKey(ActionBean bean, Field field) {
        Context context = field.getAnnotation(Context.class);
        String key = context.key();
        return key.isEmpty() ? bean.getClass().getName() + "." + field.getName() : field.getClass().getName() + "-" + key;
    }

    public void storeValuesInContext(ActionBean bean) {
        Collection<Field> fields = AnnotationUtil.getFields(Context.class, bean.getClass());
        for (Field field : fields) {
            try {
                String key = getKey(bean, field);
                Object value = field.get(bean);
                setValue(key, value);
            } catch (IllegalAccessException ex) {
                String msg = String.format("Unexpected error while retrieving value from field '%s'", field);
                throw new RuntimeException(msg, ex);
            }
        }
    }
}
