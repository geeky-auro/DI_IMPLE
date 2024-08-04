package org.scratch;

public class ServiceB{
    @MyInject
    private ServiceA serviceA;

    public void perform(){
        serviceA.serve();
        System.out.println("ServiceB is performing.");
    }
}
