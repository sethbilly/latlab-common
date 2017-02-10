/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.model;

public class DataItem {

    private Number value;
    private String label;
    private NumberType numberType = NumberType.INTEGER;

    public DataItem(String label, Number count, NumberType numberType) {
        this.value = count;
        this.label = label;
        this.numberType = numberType;
    }

    public DataItem(String label, NumberType numberType) {
        this.label = label;
        this.numberType = numberType;
    }

    public DataItem(String label, Number value) {
        this.value = value;
        this.label = label;
    }

    public DataItem() {

    }

    public double getDoubleValue() {
        if (value != null) {
            return value.doubleValue();
        }

        return 0;
    }

    public Number getValue() {
        return value;
    }

    public void setValue(Number value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ItemFreq{" + "count=" + value + ", label=" + label + '}';
    }

    public boolean isPercent() {
        return numberType == NumberType.PERCENT;
    }

    public boolean isInt() {
        return numberType == NumberType.INTEGER;
    }

    public boolean isDouble() {
        return numberType == NumberType.DOUBLE;
    }

}
