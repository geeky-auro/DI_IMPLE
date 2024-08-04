package org.scratch;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.CONSTRUCTOR,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface Cricket{
    int age() default 19;
    String message() default "Hello I am Virat!";
}


@Cricket
class Virat{
    int runs;
    int innings;

    public void setRuns(int runs){
        this.runs=runs;
    }

    public void setInnings(int innings){
        this.innings=innings;
    }

    public void show(){

    }
}


public class Annotations {

    public static void main(String[] args) {

    }


}
