package com.latlab.common.constants;


import java.util.Arrays;
import java.util.List;

/**
 *
 * @author ic securities
 */
public enum IdType implements EnumResolvable{
    VOTER_ID("Voter's ID"),
    DRIVER_LICENCE("Driver's Licence"),
    PASSPORT("National Passport"),
    BIRTH_CERTIFICATE("Birth Ceritificate"),
    NHIS_CARD("NHIS Card"),
    CERTIFICATE_OF_INCORPORATION("Certificate of Incorporation"),
    NATIONAL_ID("National ID"),
    OTHER("Others");
    
    String label;

    public static final List<IdType> INDIVIDUAL_LIST = 
            Arrays.asList(VOTER_ID, DRIVER_LICENCE, PASSPORT, NHIS_CARD, NATIONAL_ID, OTHER);
    
    
    private IdType(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString()
    {
        return label;
    }

    @Override
    public String getCode() {
       return label;
    }
    
    
    
    
}
