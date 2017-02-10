/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.utils;

import com.latlab.common.constants.IdType;
import com.latlab.common.constants.Title;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class RandomUtils {

    private int number = 10;

    public String data() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public IdType getIdType() {
        return IdType.values()[(int) (Math.random() * (IdType.values().length - 1))];
    }

    public Title getTitle() {
        return Title.values()[(int) (Math.random() * (Title.values().length - 1))];
    }

//    
    public <T> T getOne(T[] array) {
        return array[(int) (Math.random() * (array.length - 1))];
    }

    public String email() {
        return randomIdentifier()
                + "." + data().substring(0, 9)
                + "@"
                + randomIdentifier()
                + ".com";
    }

    public Date getdate() {

        GregorianCalendar gc = new GregorianCalendar();

        int year = (int) randBetween(1950, 1997);

        gc.set(gc.YEAR, year);

        int dayOfYear = (int) randBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));

        gc.set(gc.DAY_OF_YEAR, dayOfYear);

//        System.out.println(gc.get(gc.YEAR) + "-" + (gc.get(gc.MONTH) + 1) + "-" + gc.get(gc.DAY_OF_MONTH));
        return gc.getTime();
    }

    public static long randBetween(long start, long end) {
        return start + (long) Math.round(Math.random() * (end - start));
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
//    final String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";
    final java.util.Random rand = new java.util.Random();

    public String randomIdentifier() {
        StringBuilder builder = new StringBuilder();
        while (builder.toString().length() == 0) {
            int length = rand.nextInt(5) + 5;
            for (int i = 0; i < length; i++) {
                builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
            }
//        if(identifiers.contains(builder.toString())) {
//            builder = new StringBuilder();
//        }
        }
        return builder.toString();
    }
}
