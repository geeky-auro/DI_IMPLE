package org.scratch;


import java.lang.reflect.Method;

class Apple{
    private void show(){
        System.out.println("In Apple Class ;)");
    }
}


public class ExecutingApple {

    public static void main(String[] args) throws Exception {

//        Apple apple=new Apple();
//        apple.show() -> This cannot be done ;)
            Class<?> appleClass=Class.forName("org.scratch.Apple");
            if(appleClass.isAnnotation()){
                System.out.println("This is an Annotation");
            }else if(appleClass.isAnonymousClass()){
                System.out.println("This is an Anonymous class");
            }else if(appleClass.isInterface()){
                System.out.println("This is an Interface");
            } else if (appleClass.isRecord()) {
                System.out.println("This is a Record !");
            }
        var new_apple_obj=appleClass.newInstance();
            Method apple_m[]=appleClass.getDeclaredMethods();
            for (var x:apple_m){
                x.setAccessible(true);
            }
            apple_m[0].invoke(new_apple_obj);


//            apple_m[0].invoke();


    }

}
