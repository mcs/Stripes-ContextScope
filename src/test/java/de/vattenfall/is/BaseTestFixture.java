package de.vattenfall.is;

import java.util.HashMap;
import java.util.Map;
import net.sourceforge.stripes.controller.DispatcherServlet;
import net.sourceforge.stripes.controller.StripesFilter;
import net.sourceforge.stripes.mock.MockServletContext;

public class BaseTestFixture {
    protected static final MockServletContext ctx = new MockServletContext("test");

    static {
        // Add the Stripes Filter
        Map<String, String> filterParams = new HashMap<String, String>();
        filterParams.put("ActionResolver.Packages", "de.vattenfall.is.context.action");
        filterParams.put("Extension.Packages", "de.vattenfall.is.context");
        filterParams.put("Stripes.EncryptionKey", "TestKey");
        ctx.addFilter(StripesFilter.class, "StripesFilter", filterParams);

        // Add the Stripes Dispatcher
        Map<String, String> dispatcherParams = new HashMap<String, String>();
        dispatcherParams.put("javax.servlet.jsp.jstl.fmt.localizationContext", "StripesResources");
        ctx.setServlet(DispatcherServlet.class, "StripesDispatcher", null);
    }
}
