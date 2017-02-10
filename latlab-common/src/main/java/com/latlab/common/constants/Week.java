/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;

import java.util.Calendar;
import java.util.Date;

public enum Week implements EnumResolvable {
    FIRST_WEEK("first_week", "1st Week", 1),
    SECOND_WEEK("second_week", "2nd Week", 2),
    THIRD_WEEK("third_week", "3rd Week", 3),
    FOURTH_WEEK("fourth_week", "4th Week", 4),
    FIFTH_WEEK("fifth_week", "5th Week", 5),
    SIXTH_WEEK("sixth_week", "6th Week", 6);

    Week(String code, String label, int index) {
        this.label = label;
        this.code = code;
        this.index = index;
    }

    private final String label;
    private final String code;
    private final int index;

    public static Week getWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int calenderMonth = calendar.get(Calendar.WEEK_OF_MONTH);

        if (calenderMonth == 1) {
            return Week.FIRST_WEEK;
        } else if (calenderMonth == 2) {
            return Week.SECOND_WEEK;
        } else if (calenderMonth == 3) {
            return Week.THIRD_WEEK;
        } else if (calenderMonth == 4) {
            return Week.FOURTH_WEEK;
        } else if (calenderMonth == 5) {
            return Week.FIFTH_WEEK;
        } else if (calenderMonth == 6) {
            return Week.SIXTH_WEEK;
        }

        return null;
    }

    public static Integer getWeekCode(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int calenderMonth = calendar.get(Calendar.WEEK_OF_MONTH);

        Integer monthCode = Month.monthCode(date);
        if (monthCode == null) {
            return null;
        }

        Week week = Week.getWeek(date);

        if (week == null) {
            return null;
        }

        Integer weekCode = (monthCode * 100) + week.index;

        return weekCode;
    }

    public static Week resolve(Integer weekCode) {
        Week week = null;
        if (weekCode == null) {
            return week;
        }

        int weekIndex = Integer.parseInt(weekCode.toString().substring(6, 8));
//        System.out.println("weekIndex : " + weekIndex);

        if (weekIndex == 1) {
            return Week.FIRST_WEEK;
        } else if (weekIndex == 2) {
            return Week.SECOND_WEEK;
        } else if (weekIndex == 3) {
            return Week.THIRD_WEEK;
        } else if (weekIndex == 4) {
            return Week.FOURTH_WEEK;
        } else if (weekIndex == 5) {
            return Week.FIFTH_WEEK;
        } else if (weekIndex == 6) {
            return Week.SIXTH_WEEK;
        }

        return week;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return getLabel();
    }

}
