package de.vattenfall.is.context.action;

import de.vattenfall.is.context.Context;
import de.vattenfall.is.context.ContextScope;
import de.vattenfall.is.util.model.MyTestClass;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

@ContextScope(scopes = "scope3")
public class Scope3ActionBean extends BaseTestActionBean {

    @Context
    public String publicString;
    @Context
    protected Integer protectedInteger3;
    @Context
    long packagePrivateLong3;
    @Context(key = "testClassKey")
    private MyTestClass privateTestClass3;
    public String publicNotAnnotated3;

    @DefaultHandler
    public Resolution defaultHandler() {
        publicString += "3";
        protectedInteger3 += 3;
        packagePrivateLong3 += 3;
        publicNotAnnotated3 += "3";
        return new ForwardResolution("/success.jsp");
    }

    public long getPackagePrivateLong3() {
        return packagePrivateLong3;
    }

    public void setPackagePrivateLong3(long packagePrivateLong) {
        this.packagePrivateLong3 = packagePrivateLong;
    }

    public MyTestClass getPrivateTestClass3() {
        return privateTestClass3;
    }

    public void setPrivateTestClass3(MyTestClass privateTestClass) {
        this.privateTestClass3 = privateTestClass;
    }

    public Integer getProtectedInteger3() {
        return protectedInteger3;
    }

    public void setProtectedInteger3(Integer protectedInteger) {
        this.protectedInteger3 = protectedInteger;
    }

    public String getPublicString() {
        return publicString;
    }

    public void setPublicString(String publicString) {
        this.publicString = publicString;
    }
}
