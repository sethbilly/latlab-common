/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;

public enum Region {

    GREATER_ACCRA("Greater Accra", "GA"),
    CENTRAL("Central", "CR"),
    ASHANTI("Ashanti", "AR"),
    BRONG_AHAFO("Brong Ahafo", "BA"),
    NORTHERN("Northern", "NR"),
    UPPER_EAST("Upper East", "UE"),
    UPPER_WEST("Upper West", "UW"),
    EASTERN("Eastern", "ER"),
    WESTERN("Western", "WR"),
    VOLTA("Volta", "VR");

    private String regionName;
    private String regionCode;

    private Region(String regionName, String regionCode) {
        this.regionName = regionName;
        this.regionCode = regionCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return regionName;
    }

    public static Region getRegion(String regionCode) {
        if (regionCode == null) {
            return null;
        }

        Region[] allRegions = Region.values();

        for (int i = 0; i < allRegions.length; i++) {
            Region region = allRegions[i];

            if (region.getRegionCode().equalsIgnoreCase(regionCode)) {
                return region;
            }
        }

        return null;
    }

}
