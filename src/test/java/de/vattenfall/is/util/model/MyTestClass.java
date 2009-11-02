package de.vattenfall.is.util.model;


@TestAnno
public class MyTestClass {

    public MyTestClass() {
    }

    public MyTestClass(String field1) {
        this.field1 = field1;
    }
    
    @TestAnno
    public String field1;
    @TestAnno
    protected String field2;
    @TestAnno
    String field3;
    @TestAnno
    private String field4;
    public String field5;
    @TestAnno2
    public String field6;

    @TestAnno2
    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    @TestAnno2
    protected String getField2() {
        return field2;
    }

    @TestAnno2
    String getField3() {
        return field3;
    }

    @TestAnno2
    private String getField4() {
        return field4;
    }

    public String getField5() {
        return field5;
    }

    @TestAnno
    public String getField6() {
        return field6;
    }
}
