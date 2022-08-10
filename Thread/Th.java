package com.javacource.se.lesson;

public class Th {
    public static void main(String[] args) {
        for (int i =0; i < 3; i++) {
            MultithreadThing mything = new MultithreadThing(i);
            Thread myThread = new Thread(mything);
            myThread.start();
            System.out.println(myThread.isAlive());
            try {
                myThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
