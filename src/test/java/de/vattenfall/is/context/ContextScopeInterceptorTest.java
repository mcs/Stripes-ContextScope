package de.vattenfall.is.context;

import de.vattenfall.is.BaseTestFixture;
import de.vattenfall.is.context.action.NoScopeActionBean;
import de.vattenfall.is.context.action.Scope1ActionBean;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockRoundtrip;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContextScopeInterceptorTest extends BaseTestFixture {

    protected MockHttpSession session;

    @Before
    public void setUp() {
        session = new MockHttpSession(ctx);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldInjectValuesToContextInSameBean() throws Exception {
        // GIVEN
        MockRoundtrip trip = new MockRoundtrip(ctx, Scope1ActionBean.class, session);
        trip.addParameter("publicString", "X");
        trip.addParameter("protectedInteger1", "5");
        trip.addParameter("packagePrivateLong1", "10");
        trip.addParameter("publicNotAnnotated1", "Y");
        trip.addParameter("privateTestClass1.field1", "Z");
        trip.execute();
        Scope1ActionBean bean = trip.getActionBean(Scope1ActionBean.class);
        assertEquals("X1", bean.getPublicString());
        
        // WHEN
        trip = new MockRoundtrip(ctx, Scope1ActionBean.class, session);
        trip.execute();

        // THEN
        bean = trip.getActionBean(Scope1ActionBean.class);
        assertEquals("X11", bean.getPublicString());
    }

    @Test
    public void shouldntInjectValuesToContextInBeanWithoutContextScope() throws Exception {
        // GIVEN
        MockRoundtrip trip = new MockRoundtrip(ctx, NoScopeActionBean.class, session);
        trip.addParameter("publicString", "X");
        trip.addParameter("protectedInteger1", "5");
        trip.addParameter("packagePrivateLong1", "10");
        trip.addParameter("publicNotAnnotated1", "Y");
        trip.addParameter("privateTestClass1.field1", "Z");
        trip.execute();
        NoScopeActionBean bean = trip.getActionBean(NoScopeActionBean.class);
        assertEquals("X", bean.publicString);

        // WHEN
        trip = new MockRoundtrip(ctx, NoScopeActionBean.class, session);
        // no request parameters added => leave bean uninitialized
        trip.execute();

        // THEN
        bean = trip.getActionBean(NoScopeActionBean.class);
        assertNull(bean.publicString);
    }
}
