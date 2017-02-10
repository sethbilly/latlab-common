/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.constants;

/**
 *
 * @author edwin
 */
public enum Religion
{

    CHRISTIAN("Christian","CH"),
    MOSLEM("Moslem","MS"),
    TRADITIONALIST("TraditionalList","TD"),
    OTHER("Other","OT");

    

    private String regionName;
    private String regionCode;

    private Religion(String regionName, String regionCode)
    {
        this.regionName = regionName;
        this.regionCode = regionCode;
    }

    public String getRegionCode()
    {
        return regionCode;
    }

    public void setRegionCode(String regionCode)
    {
        this.regionCode = regionCode;
    }

    public String getRegionName()
    {
        return regionName;
    }

    public void setRegionName(String regionName)
    {
        this.regionName = regionName;
    }

    @Override
    public String toString()
    {
        return regionName;
    }


    public static Religion getRegion(String regionCode)
    {
        if(regionCode == null)
            return null;

        Religion[] allRegions = Religion.values();

        for (int i = 0; i < allRegions.length; i++)
        {
            Religion region = allRegions[i];

            if(region.getRegionCode().equalsIgnoreCase(regionCode))
                return region;
        }

        return null;
    }
    

}
