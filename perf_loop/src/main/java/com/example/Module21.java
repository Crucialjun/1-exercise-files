package com.example;

import java.util.ArrayList;
import java.util.Random;

public class Module21 {

    public static void main(String[] args) {
        long sum = 0;
        Module21 myClass = new Module21();
        for (int i = 0; i < 10000; i++) {
            sum += myClass.poorPerformanceLoop();
        }
        System.out.println("Avg execution time : " +(float)sum );
        sum=0;
        for (int i = 0; i < 10000; i++) {
            sum += myClass.goodPerformanceLoop();
        }
        System.out.println("Avg execution time : " +(float)sum );


    }


    public long poorPerformanceLoop() {
        ArrayList<Model> data = generateDataSet();
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < data.size(); i++) {
            Model model = data.get(i);
            int age = model.getAge();
            String family = model.getFamily();
            String name=model.getName();
            boolean flag= model.isFlag();
            String full=name+family;

        }
        return System.currentTimeMillis() - timeStart;
    }
    public long goodPerformanceLoop() {
        ArrayList<Model> data = generateDataSet();
        long timeStart = System.currentTimeMillis();
        final int size=data.size();
        Model model;
        String family,name;
        boolean flag;
        int age;
        String full;
        for (int i = 0; i < size; i++) {
            model = data.get(i);
             age = model.getAge();
             family = model.getFamily();
             name=model.getName();
             flag= model.isFlag();
            full=name+family;

        }
        return System.currentTimeMillis() - timeStart;
    }



    public ArrayList<Model> generateDataSet() {
        Random random = new Random();
        ArrayList<Model> data = new ArrayList();
        for (int i = 0; i < 1000; i++)
            data.add(new Model(true, "Android", random.nextInt(), "Performance"));

        return data;

    }

}