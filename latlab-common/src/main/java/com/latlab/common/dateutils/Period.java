/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.dateutils;


import com.latlab.common.api.MessageResolvable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Seth Billy Nartey
 */
public enum Period implements MessageResolvable {
//        TODAY("Today"), TOMORROW("Tomorrow"), 
//        YESTERDAY("Yesterday"), 
//        THIS_WEEK("This Week"), LAST_WEEK("Last Week"), 
////        A_WEEK_AGO("A Week Ago"),
//        THIS_MONTH("This Month"), LAST_MONTH("Last Month"), 
//        A_MONTH_AGO("A Month Ago"), 
//        FIRST_QUARTER("1st Quarter"), SECOND_QUARTER("Second Quarter"), 
//        THIRD_QUARTER("Third Quarter"), LAST_QUARTER("Last Quarter"),
//        THIS_YEAR("This Year"), LAST_YEAR("Last Year"),

    A_WEEK_AGO("A Week Ago", "", false), A_MONTH_AGO("A Month Ago", "A Month Ago", false),
    FIRST_QUARTER("1st Quarter", "1st Quarter", false), SECOND_QUARTER("Second Quarter", "Second Quarter", false),
    THIRD_QUARTER("Third Quarter", "Third Quarter", false),
    //        LAST_QUARTER("Last Quarter", "Last Quarter", false),
    TODAY("today", "Today", false),
    YESTERDAY("yesterday", "Yesterday", false), TOMORROW("tomorrow", "Tomorrow", true),
    THIS_WEEK("this_week", "This week", false), THIS_WEEK_FUTURE("this_week", "This week", true),
    LAST_WEEK("last_week", "Last week", false), NEXT_WEEK("last_week", "Last week", true),
    THIS_MONTH("this_month", "This month", false), THIS_MONTH_FUTURE("this_month", "This month Future", true), THIS_MONTH_PAST("this_month", "This month Past", true),
    LAST_MONTH("last_month", "Last month", false), NEXT_MONTH("last_month", "Last month", true),
    THIS_QUARTER("this_quarter", "This quarter", false), THIS_QUARTER_FUTURE("this_quarter", "This quarter", true),
    LAST_QUARTER("last_quarter", "Last quarter", false), NEXT_QUARTER("last_quarter", "Last quarter", true),
    THIS_YEAR("this_year", "This year", false), THIS_YEAR_FUTURE("this_year", "This year", true),
    LAST_YEAR("last_year", "Last Year", false), NEXT_YEAR("this_year", "This year", true);

    static Period getPreviousFromOrdinal(int ordinal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static Period getNextFromOrdinal(int ordinal) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String label;
    private String key;
    private boolean future;
    private int year = new DateTime().getYear();

    private Period(String key, String label, boolean future) {
        this.key = key;
        this.label = label;
        this.future = future;
    }

    @Override
    public String getCode() {
        return this.key;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    public boolean isFuture() {
        return future;
    }

    public static List<Period> asList() {
        return Arrays.asList(Period.values());
    }

    public static List<Period> asPastList() {
        List<Period> options = new ArrayList<>();
        for (Period rangeOption : Period.values()) {
            if (!rangeOption.isFuture()) {
                options.add(rangeOption);
            }
        }
        return options;
    }

    public static List<Period> asFutureList() {
        List<Period> options = new ArrayList<>();
        for (Period rangeOption : Period.values()) {
            if (rangeOption.isFuture()) {
                options.add(rangeOption);
            }
        }
        return options;
    }

    public static List<MessageResolvable> asFutureResolvableList() {
        List<MessageResolvable> resolvables = new ArrayList<>();
        resolvables.addAll(asFutureList());
        return resolvables;
    }

    public static List<MessageResolvable> asPastResolvableList() {
        List<MessageResolvable> resolvables = new ArrayList<>();
        resolvables.addAll(asPastList());
        return resolvables;
    }

    public static List<MessageResolvable> asResolvableList() {
        List<MessageResolvable> resolvables = new ArrayList<>();
        resolvables.addAll(asList());
        return resolvables;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public static List<Period> quarters() {
        return Arrays.asList(FIRST_QUARTER, SECOND_QUARTER, THIRD_QUARTER, LAST_QUARTER);
    }

   

//    public static List<Period> quartersPrevious(Period period)
//    {
//        return Arrays.asList(FIRST_QUARTER, SECOND_QUARTER, THIRD_QUARTER, LAST_QUARTER);
//    }
    public static Period previous(Period period) {
        if (quarters().contains(period)) {

        }

        return null;
    }

    @Override
    public String toString() {
        return label;
    }

}
