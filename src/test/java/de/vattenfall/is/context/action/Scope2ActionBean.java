package de.vattenfall.is.context.action;

import de.vattenfall.is.context.Context;
import de.vattenfall.is.context.ContextScope;
import de.vattenfall.is.util.model.MyTestClass;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

@ContextScope(scopes = "scope2")
public class Scope2ActionBean extends BaseTestActionBean {

    @Context
    public String publicString = "2";
    @Context
    protected Integer protectedInteger2 = 2;
    @Context
    long packagePrivateLong2;
    @Context(key = "testClassKey")
    private MyTestClass privateTestClass2;
    public String publicNotAnnotated2 = "22";

    @DefaultHandler
    public Resolution defaultHandler() {
        publicString += "2";
        protectedInteger2 += 2;
        packagePrivateLong2 += 2;
        publicNotAnnotated2 += "2";
        return new ForwardResolution("/success.jsp");
    }

    public long getPackagePrivateLong2() {
        return packagePrivateLong2;
    }

    public void setPackagePrivateLong2(long packagePrivateLong) {
        this.packagePrivateLong2 = packagePrivateLong;
    }

    public MyTestClass getPrivateTestClass2() {
        return privateTestClass2;
    }

    public void setPrivateTestClass2(MyTestClass privateTestClass) {
        this.privateTestClass2 = privateTestClass;
    }

    public Integer getProtectedInteger2() {
        return protectedInteger2;
    }

    public void setProtectedInteger2(Integer protectedInteger) {
        this.protectedInteger2 = protectedInteger;
    }

    public String getPublicString() {
        return publicString;
    }

    public void setPublicString(String publicString) {
        this.publicString = publicString;
    }
}
