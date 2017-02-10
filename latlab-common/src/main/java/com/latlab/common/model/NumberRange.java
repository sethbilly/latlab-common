/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.model;

import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;

public class NumberRange {

    private Number minimum;
    private Number maximum;

    public NumberRange(Number minimum, Number maximum) {
        this.minimum = minimum;
        this.maximum = maximum;
    }

    public Number getMinimum() {
        return minimum;
    }

    public void setMinimum(Number minimum) {
        this.minimum = minimum;
    }

    public Number getMaximum() {
        return maximum;
    }

    public void setMaximum(Number maximum) {
        this.maximum = maximum;
    }

    public static List<Integer> generateRange(int baseNumber, int ceilingNumber) {
        List<Integer> integers = new LinkedList<>();

        for (int counter = baseNumber; counter <= ceilingNumber; counter++) {
            integers.add(counter);
        }

        return integers;
    }

    public static List<Integer> generateRange(int baseNumber, int ceilingNumber, int stepValue) {
        List<Integer> integers = new LinkedList<>();

        for (int counter = baseNumber; counter <= ceilingNumber; counter = counter + stepValue) {
            integers.add(counter);
        }

        return integers;
    }

    public static List<Integer> generateRange(int beginNumber) {
        List<Integer> integers = new LinkedList<>();
        int ceilingNumber = new DateTime().getYear();

        for (int counter = beginNumber; counter <= ceilingNumber; counter++) {
            integers.add(counter);
        }

        return integers;
    }

}
