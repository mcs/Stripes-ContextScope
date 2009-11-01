package de.vattenfall.is.context.action;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;

public class BaseTestActionBean implements ActionBean {

    public ActionBeanContext ctx;

    @Override
    public void setContext(ActionBeanContext context) {
        ctx = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return ctx;
    }
}
