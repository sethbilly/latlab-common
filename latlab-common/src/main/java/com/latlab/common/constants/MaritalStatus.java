/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.constants;

/**
 *
 * @author edwin
 */
public enum MaritalStatus implements EnumResolvable
{
    SINGLE("Single", "SINGLE"),
    MARRIED("Married", "MARRIED"),
    COHABITATION("Cohabitation", "COHABITATION"),
    SEPARATION("Separated", "SEPARATION"),
    DIVORCED("Divorced", "DIVORCED"),
    WIDOWED("Widowed", "WIDOWED");
    
    private String label, code;

    private MaritalStatus(String label, String code)
    {
        this.label = label;
        this.code = code;
    }
    
   
    @Override
    public String getLabel()
    {
        return label;
    }

    @Override
    public String getCode()
    {
        return code;
    }

    @Override
    public String toString()
    {
        return label;
    }
    
    

}
