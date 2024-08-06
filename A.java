package org.scratch;

@Compo
public class A {
    @Override
    public String toString() {
        return "A{" +
                "b=" + b +
                ", c=" + c +
                '}';
    }

    A(){

    }

    @MyInject
    private B b;



    @MyInject
    private C c;


}
