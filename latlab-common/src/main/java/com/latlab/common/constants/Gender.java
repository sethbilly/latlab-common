/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.constants;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author edwin
 */
public enum Gender
{
    MALE("M","Male","Mr. "),
    FEMALE("F","Female","Mrs. "),
    NA("NA","N/A",""),
    BOTH_GENDER("B","Both", ""),
    INSTITUTION("I","Institution","");


    private String initial;
    private String name;
    private String title;

    private Gender(String initial, String name, String title)
    {
        this.initial = initial;
        this.name = name;
        this.title = title;
    }

    public String getInitial()
    {
        return initial;
    }



    private static final List<Gender> gendersList = new LinkedList<>();
    
    public static final List<Gender> common = new LinkedList<>();

    static
    {
        gendersList.add(MALE);
        gendersList.add(FEMALE);

        common.add(MALE);
        common.add(FEMALE);
        common.add(NA);
    }

    public static List<Gender> humanGender()
    {
        return gendersList;
    }

    public String getTitle()
    {
        return title;
    }
    
    

    @Override
    public String toString()
    {
        return name;
    }



}
