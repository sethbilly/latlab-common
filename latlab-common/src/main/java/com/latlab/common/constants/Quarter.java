/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;

import java.util.Calendar;
import java.util.Date;

public enum Quarter implements EnumResolvable {
    FIRST_QUARTER(1, "first_quarter", "1st Quarter"),
    SECOND_QUARTER(2, "second_quarter", "2nd Quarter"),
    THIRD_QUARTER(3, "third_quarter", "3rd Quarter"),
    FOURTH_QUARTER(4, "fourth_quarter", "4th Quarter");

    Quarter(int nominal, String key, String label) {
        this.nominal = nominal;
        this.label = label;
        this.key = key;
    }

    private int nominal;
    private String label;
    private String key;

    public static Quarter getMonth(int index) {
        index = Math.abs(index);
        if (index >= 0 && index < 5) {
            return Quarter.values()[index + 1];
        }

        return null;
    }

    public static Quarter getQuarter(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int calenderMonth = calendar.get(Calendar.MONTH) + 1;

        if (calenderMonth <= 3) {
            return Quarter.FIRST_QUARTER;
        } else if (calenderMonth <= 6) {
            return Quarter.SECOND_QUARTER;
        } else if (calenderMonth <= 9) {
            return Quarter.THIRD_QUARTER;
        } else if (calenderMonth <= 12) {
            return Quarter.FOURTH_QUARTER;
        }

        return null;
    }

    @Override
    public String getLabel() {
        return label;
    }

   
    public String getKey() {
        return key;
    }

   
    public void setKey(String key) {
        this.key = key;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }

    @Override
    public String toString() {
        return getLabel();
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
