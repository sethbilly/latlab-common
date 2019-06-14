package com.latlab.common.jpa;

import com.latlab.common.constants.Month;
import com.latlab.common.constants.Quarter;
import com.latlab.common.constants.Week;
import com.latlab.common.dateutils.DateTimeUtils;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class TimeUnit extends CommonEntityModel implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String _valueMonth = "valueMonth";
    @Enumerated(EnumType.STRING)
    @Column(name = "value_month", length = 20)
    private Month valueMonth;

    @Column(name = "value_week", length = 20)
    @Enumerated(EnumType.STRING)
    private Week valueWeek;

    public static final String _valueQuarter = "valueQuarter";
    @Column(name = "value_quarter", length = 20)
    @Enumerated(EnumType.STRING)
    private Quarter valueQuarter;

    public static final String _weekCode = "weekCode";
    @Column(name = "week_code")
    private Integer weekCode;

    public static final String _valueYear = "valueYear";
    @Column(name = "value_year")
    private Integer valueYear;

    public static final String _monthCode = "monthCode";
    @Column(name = "month_code")
    private Integer monthCode;

    public Month getValueMonth() {
        return valueMonth;
    }

    public void setValueMonth(Month valueMonth) {
        this.valueMonth = valueMonth;
    }

    public Week getValueWeek() {
        return valueWeek;
    }

    public void setValueWeek(Week valueWeek) {
        this.valueWeek = valueWeek;
    }

    public Quarter getValueQuarter() {
        return valueQuarter;
    }

    public void setValueQuarter(Quarter valueQuarter) {
        this.valueQuarter = valueQuarter;
    }

    public Integer getWeekCode() {
        return weekCode;
    }

    public void setWeekCode(Integer weekCode) {
        this.weekCode = weekCode;
    }

    public Integer getValueYear() {
        return valueYear;
    }

    public void setValueYear(Integer valueYear) {
        this.valueYear = valueYear;
    }

    public Integer getMonthCode() {
        return monthCode;
    }

    public void setMonthCode(Integer monthCode) {
        this.monthCode = monthCode;
    }

    public void setDateDetails(Date valueDate) {
        if (valueDate == null) {
            return;
        }
        valueMonth = Month.getMonth(valueDate);
        valueWeek = Week.getWeek(valueDate);
        valueQuarter = Quarter.getQuarter(valueDate);
        valueYear = DateTimeUtils.getYearInDate(valueDate);
        monthCode = Month.monthCode(valueDate);
        weekCode = Week.getWeekCode(valueDate);
    }

}
