/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;


import com.latlab.common.dateutils.DateTimeUtils;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.joda.time.DateTime;

public enum Month implements EnumResolvable {
    JANUARY("january", "January", "Jan", 1),
    FEBRUARY("february", "February", "Feb", 2),
    MARCH("march", "March", "Mar", 3),
    APRIL("april", "April", "Apr", 4),
    MAY("may", "May", "May", 5),
    JUNE("june", "June", "Jun", 6),
    JULY("july", "July", "Jul", 7),
    AUGUST("august", "August", "Aug", 8),
    SEPTEMBER("september", "September", "Sep", 9),
    OCTOBER("october", "October", "Oct", 10),
    NOVEMBER("november", "November", "Nov", 11),
    DECEMBER("december", "December", "Dec", 12);

    Month(String key, String longLabel, String shortLabel, int index) {
        this.longLabel = longLabel;
        this.key = key;
        this.index = index;
        this.shortLabel = shortLabel;
    }

    private String longLabel;
    private String shortLabel;
    private String key;
    private int index;

    public static Month getMonth(int index) {
        index = Math.abs(index);
        if (index > 0 && index < 13) {
            return Month.values()[index - 1];
        }

        return null;
    }

    public static Month getMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getMonth(calendar.get(Calendar.MONTH) + 1);
    }

    @Override
    public String getLabel() {
        return longLabel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return getLabel();
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public static Integer monthCode(Date valueDate) {
        Month month = Month.getMonth(valueDate);

        int monthIndex = (DateTimeUtils.getYearInDate(valueDate) * 100) + month.index;

        return monthIndex;
    }

    public static Integer monthCode(Month month, Integer valueYear) {
        int monthIndex = (valueYear * 100) + month.index;

        return monthIndex;
    }

    public static List<Integer> monthsCodes(Date fromDate, Date toDate) {
        Set<Integer> integersList = new LinkedHashSet<>();

        int startMonthCode = monthCode(fromDate);
        int endMonthCode = monthCode(toDate);

        DateTime dateTime = new DateTime(fromDate);

        integersList.add(startMonthCode);

        while (dateTime.toDate().before(toDate)) {
            integersList.add(monthCode(dateTime.toDate()));

            dateTime = dateTime.plusMonths(1);

        }
        integersList.add(endMonthCode);

        return new LinkedList<>(integersList);

    }

    @Override
    public String getCode() {
        return "";
    }

}
