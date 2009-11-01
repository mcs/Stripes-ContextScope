package de.vattenfall.is.context.action;

import de.vattenfall.is.context.Context;
import de.vattenfall.is.util.model.MyTestClass;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

public class NoScopeActionBean extends BaseTestActionBean {

    @Context
    public String publicString;
    @Context
    protected Integer protectedInteger1;
    @Context
    long packagePrivateLong1;
    @Context(key = "testClassKey")
    private MyTestClass privateTestClass1;
    public String publicNotAnnotated1;

    @DefaultHandler
    public Resolution defaultHandler() {
        return new ForwardResolution("/success.jsp");
    }

    public long getPackagePrivateLong1() {
        return packagePrivateLong1;
    }

    public void setPackagePrivateLong1(long packagePrivateLong) {
        this.packagePrivateLong1 = packagePrivateLong;
    }

    public MyTestClass getPrivateTestClass1() {
        return privateTestClass1;
    }

    public void setPrivateTestClass1(MyTestClass privateTestClass) {
        this.privateTestClass1 = privateTestClass;
    }

    public Integer getProtectedInteger1() {
        return protectedInteger1;
    }

    public void setProtectedInteger1(Integer protectedInteger) {
        this.protectedInteger1 = protectedInteger;
    }

    public String getPublicString() {
        return publicString;
    }

    public void setPublicString(String publicString) {
        this.publicString = publicString;
    }
}
