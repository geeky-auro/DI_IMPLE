package org.scratch;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScanClasses {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        // Scan all the classes having annotation @Compo
        ScanClasses ob=new ScanClasses();
        ob.scanClasses();
    }


    public void scanClasses() throws InstantiationException, IllegalAccessException {
        String packageName = "org.scratch";
        Class<?> c=Main.class;
        var c1=c.newInstance();
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setScanners(new SubTypesScanner(false))
                        .setUrls(ClasspathHelper.forPackage(packageName))
                        .filterInputsBy(new FilterBuilder().includePackage(packageName))
        );

        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        Set<Class<?>> getCompoClasse=new HashSet<>();
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(Compo.class)){
                getCompoClasse.add(clazz);
            }
        }
//        getCompoClasse.forEach(System.out::println);
        ArrayList<Field>[] adjacencyList =new ArrayList[getCompoClasse.size()];
        for(int i=0;i<adjacencyList.length;i++){
            adjacencyList[i]=new ArrayList<>();
        }
        int cnt=0;
        for(var compoClasses:getCompoClasse){
            Field fields[]= compoClasses.getDeclaredFields();
            for(var f_:fields){
                if (f_.isAnnotationPresent(MyInject.class)){
                    adjacencyList[cnt].add(f_);
                }
            }
            cnt++;
        }
        for(int i=0;i< adjacencyList.length-1;i++){
            System.out.print(i+"->{");
            adjacencyList[i].stream().forEach(x->{System.out.println(((Field)x).getName());});
            System.out.println("}");
        }


    }



}
