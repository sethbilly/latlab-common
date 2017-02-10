/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;

public enum Region {

    GREATER_ACCRA("Greater Accra", "GA"),
    CENTRAL_REGION("Central Region", "CR"),
    ASHANTI_REGION("Ashanti Region", "AR"),
    BRONG_AHAFO_REGION("Brong Ahafo Region", "BA"),
    NORTHERN_REGION("Northern Region", "NR"),
    UPPER_EAST_REGION("Upper East Region", "UE"),
    UPPER_WEST_REGION("Upper West Region", "UW"),
    EASTERN_REGION("Eastern Region", "ER"),
    WESTERN_REGION("Western Region", "WR"),
    VOLTA_REGION("Volta Region", "VR");

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
