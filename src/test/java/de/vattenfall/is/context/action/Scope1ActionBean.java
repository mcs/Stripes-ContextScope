package de.vattenfall.is.context.action;

import de.vattenfall.is.context.Context;
import de.vattenfall.is.context.ContextScope;
import de.vattenfall.is.util.model.MyTestClass;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

@ContextScope(scopes = "scope1")
public class Scope1ActionBean extends BaseTestActionBean {

    @Context
    public String publicString = "X";
    @Context
    protected Integer protectedInteger1 = 1;
    @Context
    long packagePrivateLong1;
    @Context(key = "testClassKey")
    private MyTestClass privateTestClass1;
    public String publicNotAnnotated1 = "Y";

    @DefaultHandler
    public Resolution defaultHandler() {
        publicString += "1";
        protectedInteger1 += 1;
        packagePrivateLong1 += 1;
        publicNotAnnotated1 += "1";
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
