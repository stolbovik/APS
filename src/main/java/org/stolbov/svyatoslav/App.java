package org.stolbov.svyatoslav;

import org.stolbov.svyatoslav.System.Dates.Buffer;
import org.stolbov.svyatoslav.System.Dates.HomeRequest;

public class App
{
    public static void main( String[] args )
    {
        Buffer buffer = new Buffer(5);
        double time = 0;
        for (int i = 0; i < 31; i++) {
            buffer.addRequest(new HomeRequest(1, i, time, 0, 0));
            for(int j = 0; j < buffer.getSizeBuffer(); j++) {
                System.out.println(j + " " + buffer.getBuffer().get(j));
            }
            System.out.println("free: " + buffer.getFirstFreeIndex() +
                    "\nlast: " + buffer.getLastRequestIndex() + "\noldest: " + buffer.getOldestRequestIndex());
            double temp = 0;
            while (temp == 0.0) {
                temp = Math.random();
            }
            time += -1.0/5.0 * Math.log(temp);
        }
        buffer.getRequest();
        for(int j = 0; j < buffer.getSizeBuffer(); j++) {
            System.out.println(j + " " + buffer.getBuffer().get(j));
        }
        System.out.println("free: " + buffer.getFirstFreeIndex() +
                "\nlast: " + buffer.getLastRequestIndex() + "\noldest: " + buffer.getOldestRequestIndex());
        buffer.getRequest();
        for(int j = 0; j < buffer.getSizeBuffer(); j++) {
            System.out.println(j + " " + buffer.getBuffer().get(j));
        }
        System.out.println("free: " + buffer.getFirstFreeIndex() +
                "\nlast: " + buffer.getLastRequestIndex() + "\noldest: " + buffer.getOldestRequestIndex());
        buffer.getRequest();
        for(int j = 0; j < buffer.getSizeBuffer(); j++) {
            System.out.println(j + " " + buffer.getBuffer().get(j));
        }
        System.out.println("free: " + buffer.getFirstFreeIndex() +
                "\nlast: " + buffer.getLastRequestIndex() + "\noldest: " + buffer.getOldestRequestIndex());
        buffer.getRequest();
        for(int j = 0; j < buffer.getSizeBuffer(); j++) {
            System.out.println(j + " " + buffer.getBuffer().get(j));
        }
        System.out.println("free: " + buffer.getFirstFreeIndex() +
                "\nlast: " + buffer.getLastRequestIndex() + "\noldest: " + buffer.getOldestRequestIndex());
        buffer.getRequest();
        for(int j = 0; j < buffer.getSizeBuffer(); j++) {
            System.out.println(j + " " + buffer.getBuffer().get(j));
        }
        System.out.println("free: " + buffer.getFirstFreeIndex() +
                "\nlast: " + buffer.getLastRequestIndex() + "\noldest: " + buffer.getOldestRequestIndex());
    }
}