package org.scratch;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.Field;
import java.util.*;

public class ScanClasses {

    public static void main(String[] args) throws Exception {
        // Scan all the classes having annotation @Compo
        ScanClasses ob=new ScanClasses();
        ob.scanClasses();
    }

    HashMap<Class<?>,Object> instances=new HashMap<>();


    public void scanClasses() throws Exception {
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
        int nctr=0;
        HashMap<String,Integer> cls_no=new HashMap<>();
        HashMap<Integer,String> no_cls=new HashMap<>();
        HashMap<String,Class<?>> anssss=new HashMap<>();

        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(Compo.class)){
                cls_no.putIfAbsent(clazz.getName().split("\\.")[2],nctr);
                no_cls.putIfAbsent(nctr,clazz.getName().split("\\.")[2]);
                anssss.put(clazz.getName().split("\\.")[2],clazz);
                getCompoClasse.add(clazz);
                nctr++;
            }
        }

//        getCompoClasse.forEach(System.out::println);
        ArrayList<Integer>[] adjacencyList =new ArrayList[getCompoClasse.size()];
        for(int i=0;i<adjacencyList.length;i++){
            adjacencyList[i]=new ArrayList<>();
        }
        int cnt=0;
        HashMap<String,ArrayList<String>> par_child=new HashMap<>();
        for(var compoClasses:getCompoClasse){
//            System.out.println(STR."Class Name:\{compoClasses.getName()}");
            String parent=compoClasses.getName().split("\\.")[2].toLowerCase();
            System.out.println("Parent:"+parent);
            ArrayList<String> temp=new ArrayList<>();
//            int no=cls_no.get(compoClasses);
            Field fields[]= compoClasses.getDeclaredFields();
            for(var f_:fields){
                if (f_.isAnnotationPresent(MyInject.class)){
                    final Field desired_field=f_;
                    String child=desired_field.getName();
                    temp.add(child);
                    System.out.println("Child Class:"+desired_field.getName());
//                    adjacencyList[cls_no.get(parent)].add(cls_no.get(child)!=null?cls_no.get(child):null);
                }
            }
            par_child.put(parent,temp);
            cnt++;
        }
//        for(int i=0;i< adjacencyList.length;i++){
//            System.out.print(i+"->{");
////            adjacencyList[i].forEach(x->{System.out.println(((Field)x).getName());});
//            adjacencyList[i].forEach(x->{System.out.println(x);});
//            System.out.println("}");
//        }
        System.out.println(par_child.size());
        for (var x:par_child.entrySet()){
            System.out.print(x.getKey()+"->{");
            x.getValue().stream().forEach(System.out::print);
            System.out.print("}");
            System.out.println();
        }

        List<String> result = toposort(par_child);
//        for (Map.Entry<String, ArrayList<String>> entry : par_child.entrySet()) {
//            System.out.print(entry.getKey() + "->{");
//            entry.getValue().forEach(System.out::print);
//            System.out.print("}");
//            System.out.println();
//        }

        // Print the result as a comma-separated string
//        System.out.println((String.join(",", result)));
//        result.stream().forEach(System.out::print);
            for(int i= result.size()-1;i>=0;i--){
                String ch=result.get(i);
                Class<?> cls=anssss.get(ch.toUpperCase());
//                anssss.forEach((x,y)->System.out.println(x+" "+y));
                createIns(cls);
            }

//        System.out.println(A.class);
        instances.forEach((x,y)->{
            System.out.println(x+"   "+y);
        });
//    Collections.reverse((String.join(",", result));

        // convert the no's to maps ;)

    }


    private void createIns(Class<?> cls) throws Exception{
        if(!instances.containsKey(cls)){
            Object ins=cls.getDeclaredConstructor().newInstance();
            instances.put(cls,ins);

            for (Field f:cls.getDeclaredFields()){
                if(f.isAnnotationPresent(MyInject.class)){
                f.setAccessible(true);
                Class<?> dep=f.getType();
                f.set(ins,instances.get(dep));
                }
            }
        }
    }
    public List<String> toposort(HashMap<String, ArrayList<String>> adjacencyList) {
        Stack<String> stack = new Stack<>();
        Set<String> visited = new HashSet<>();

        for (String node : adjacencyList.keySet()) {
            if (!visited.contains(node)) {
                topologicalSortUtil(node, visited, stack, adjacencyList);
            }
        }


        List<String> topologicalOrder = new ArrayList<>();
        while (!stack.isEmpty()) {
            topologicalOrder.add(stack.pop());
        }

        return topologicalOrder;
    }

    private void topologicalSortUtil(String node, Set<String> vis, Stack<String> st, HashMap<String, ArrayList<String>> adjlist) {

        vis.add(node);
        ArrayList<String> neighbours = adjlist.get(node);
        if (neighbours != null) {
            for (String neighbour : neighbours) {
                if (!vis.contains(neighbour)) {
                    topologicalSortUtil(neighbour, vis, st, adjlist);
                }
            }
        }

        // Push current vertex to stack which stores the result
        st.push(node);
    }
}
//e f b c a d

