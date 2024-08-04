package org.scratch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SpringDep {
    private Map<Class<?>, Object> instances=new HashMap<>();

    public void registerInstance(Class<?> clazz,Object instance){
        instances.put(clazz,instance);
    }

    public void injectDependencies(Object target){
        Field[] fields=target.getClass().getDeclaredFields();
        for(Field field:fields){
            if(field.isAnnotationPresent(MyInject.class)){
                Class<?> fieldType=field.getType();
                Object instance=instances.get(fieldType);
                if(instance!=null){
                    field.setAccessible(true);
                    try{
                        field.set(target,instance);
                    }catch (IllegalArgumentException | IllegalAccessException e){
                        throw  new RuntimeException("Failed to Inject Dependencies",e);
                    }
                }
            }
        }
    }

}
