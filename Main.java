package org.scratch;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ServiceA serviceA=new ServiceA();
        ServiceB serviceB=new ServiceB();

        SpringDep springDep=new SpringDep();
        // Create the Injector
        springDep.registerInstance(ServiceA.class,serviceA);
        // Inject Dependencies
        springDep.injectDependencies(serviceB);
        serviceB.perform();

    }

    public void scanClasses(){
        String packageName = "org.scratch";

        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setScanners(new SubTypesScanner(false))
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .filterInputsBy(new FilterBuilder().includePackage(packageName))
        );

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);

        for (Class<?> clazz : classes) {
            System.out.println(clazz.getName());
        }
    }
}
