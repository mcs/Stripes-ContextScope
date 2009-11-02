package de.vattenfall.is.context;

import de.vattenfall.is.BaseTestFixture;
import de.vattenfall.is.context.action.NoScopeActionBean;
import de.vattenfall.is.context.action.Scope1ActionBean;
import de.vattenfall.is.context.action.Scope1And2ActionBean;
import de.vattenfall.is.context.action.Scope2ActionBean;
import de.vattenfall.is.context.action.Scope3ActionBean;
import net.sourceforge.stripes.mock.MockHttpSession;
import net.sourceforge.stripes.mock.MockRoundtrip;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ContextScopeInterceptorTest extends BaseTestFixture {

    protected MockHttpSession session;

    @Before
    public void setUp() {
        session = new MockHttpSession(ctx);
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
    public void shouldInjectKeyValueInOtherBeanWithWiderScope() throws Exception {
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
        assertEquals("Z", bean.getPrivateTestClass1().field1);
        
        // WHEN
        trip = new MockRoundtrip(ctx, Scope1And2ActionBean.class, session);
        trip.execute();

        // THEN
        Scope1And2ActionBean bean2 = trip.getActionBean(Scope1And2ActionBean.class);
        assertEquals(24, bean2.getPackagePrivateLong12());   // insertion of null would throw exception
        assertEquals("M12", bean2.getPublicString());   // from bean itself
        assertEquals("Z", bean2.getPrivateTestClass12().field1);  // from scope
    }

    @Test
    public void shouldInjectKeyValueInOtherBeanWithSmallerScope() throws Exception {
        // GIVEN
        MockRoundtrip trip = new MockRoundtrip(ctx, Scope1And2ActionBean.class, session);
        trip.addParameter("publicString", "X");
        trip.addParameter("protectedInteger1", "5");
        trip.addParameter("packagePrivateLong1", "10");
        trip.addParameter("publicNotAnnotated1", "Y");
        trip.addParameter("privateTestClass12.field1", "V");
        trip.execute();
        Scope1And2ActionBean bean = trip.getActionBean(Scope1And2ActionBean.class);
        assertEquals("X12", bean.getPublicString());
        assertEquals("V", bean.getPrivateTestClass12().field1);

        // WHEN
        trip = new MockRoundtrip(ctx, Scope1ActionBean.class, session);
        trip.execute();

        // THEN
        Scope1ActionBean bean2 = trip.getActionBean(Scope1ActionBean.class);
        assertEquals(1, bean2.getPackagePrivateLong1());   // insertion of null would throw exception
        assertEquals("X1", bean2.getPublicString());   // from bean itself
        assertEquals("V", bean2.getPrivateTestClass1().field1);  // from scope
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

    @Test
    public void shouldntInjectKeyValueInOtherBeanWithOtherScope() throws Exception {
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
        assertEquals("Z", bean.getPrivateTestClass1().field1);

        // WHEN
        trip = new MockRoundtrip(ctx, Scope3ActionBean.class, session);
        trip.execute();

        // THEN
        Scope3ActionBean bean2 = trip.getActionBean(Scope3ActionBean.class);
        assertEquals(6, bean2.getPackagePrivateLong3());   // insertion of null would throw exception
        assertEquals("33", bean2.getPublicString());   // from bean itself
        assertNull(bean2.getPrivateTestClass3());  // from bean itself
    }

    @Test
    public void shouldForgetScopedValuesAfterScopeChange() throws Exception {
        // GIVEN
        // start with scope1 bean
        MockRoundtrip trip = new MockRoundtrip(ctx, Scope1ActionBean.class, session);
        trip.addParameter("publicString", "X");
        trip.addParameter("protectedInteger1", "5");
        trip.addParameter("packagePrivateLong1", "10");
        trip.addParameter("publicNotAnnotated1", "Y");
        trip.addParameter("privateTestClass1.field1", "Z");
        trip.execute();

        // change scope to scope1 and scope2 bean
        trip = new MockRoundtrip(ctx, Scope1And2ActionBean.class, session);
        trip.execute();

        // WHEN
        // switch to scope2 scoped bean
        trip = new MockRoundtrip(ctx, Scope2ActionBean.class, session);
        trip.execute();

        // THEN
        Scope2ActionBean bean2 = trip.getActionBean(Scope2ActionBean.class);
        assertEquals(2, bean2.getPackagePrivateLong2());   // insertion of null would throw exception
        assertEquals("22", bean2.getPublicString());   // from bean itself
        assertNull(bean2.getPrivateTestClass2());  // from bean itself
    }
}
