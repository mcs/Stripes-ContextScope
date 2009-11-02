
package de.vattenfall.is.context;

import de.vattenfall.is.BaseTestFixture;
import de.vattenfall.is.context.action.Scope1ActionBean;
import java.util.Map;
import net.sourceforge.stripes.mock.MockHttpSession;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContextScopeSessionStrategyTest extends BaseTestFixture {

    private ContextScopeSessionStrategy strategy;

    @Before
    public void setUp() {
        strategy = new ContextScopeSessionStrategy(new MockHttpSession(null));
    }

    @Test
    public void shouldStoreBeanValuesInSession() {
        // GIVEN
        Scope1ActionBean bean = new Scope1ActionBean();
        bean.setPublicString("X");
        bean.setPackagePrivateLong1(42);
        Map<String, Object> valueMap = strategy.getValueMap();
        assertTrue(valueMap.isEmpty());

        // WHEN
        strategy.storeValuesInContext(bean);
        
        // THEN
        valueMap = strategy.getValueMap();
        assertFalse(valueMap.isEmpty());
    }

    @Test
    public void shouldRetrieveStoredValues() {
        // GIVEN
        Scope1ActionBean bean = new Scope1ActionBean();
        bean.setPublicString("Z");
        bean.setPackagePrivateLong1(42);
        strategy.storeValuesInContext(bean);
        bean = new Scope1ActionBean();
        assertEquals("X", bean.getPublicString());
        
        // WHEN
        strategy.injectContextValues(bean);

        // THEN
        assertEquals("Z", bean.getPublicString());
    }

    @Test
    public void shouldReadNullAfterChangingScope() {
        // GIVEN
        String key = "testKey";
        Object value = "testValue";
        strategy.setValue(key, value);

        // WHEN
        strategy.setSessionScopes(new String[] {"new_scope"});
        Object result = strategy.getValue(key);

        // THEN
        assertNull(result);
    }

    @Test
    public void shouldReadNullIfNothingIsStored() {
        // GIVEN
        String key = "testKey";

        // WHEN
        Object result = strategy.getValue(key);

        // THEN
        assertNull(result);
    }
}