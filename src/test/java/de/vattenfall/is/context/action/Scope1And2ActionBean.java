package de.vattenfall.is.context.action;

import de.vattenfall.is.context.Context;
import de.vattenfall.is.context.ContextScope;
import de.vattenfall.is.util.model.MyTestClass;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

@ContextScope(scopes = {"scope1", "scope2"})
public class Scope1And2ActionBean extends BaseTestActionBean {

    @Context
    public String publicString;
    @Context
    protected Integer protectedInteger12;
    @Context
    long packagePrivateLong12;
    @Context(key = "testClassKey")
    private MyTestClass privateTestClass12;
    public String publicNotAnnotated12;

    @DefaultHandler
    public Resolution defaultHandler() {
        publicString += "12";
        protectedInteger12 += 12;
        packagePrivateLong12 += 12;
        publicNotAnnotated12 += "12";
        return new ForwardResolution("/success.jsp");
    }

    public long getPackagePrivateLong12() {
        return packagePrivateLong12;
    }

    public void setPackagePrivateLong12(long packagePrivateLong) {
        this.packagePrivateLong12 = packagePrivateLong;
    }

    public MyTestClass getPrivateTestClass12() {
        return privateTestClass12;
    }

    public void setPrivateTestClass12(MyTestClass privateTestClass) {
        this.privateTestClass12 = privateTestClass;
    }

    public Integer getProtectedInteger12() {
        return protectedInteger12;
    }

    public void setProtectedInteger12(Integer protectedInteger) {
        this.protectedInteger12 = protectedInteger;
    }

    public String getPublicString() {
        return publicString;
    }

    public void setPublicString(String publicString) {
        this.publicString = publicString;
    }
}
