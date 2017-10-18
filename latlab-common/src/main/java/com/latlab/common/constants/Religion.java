/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.constants;

/**
 *
 * @author edwin
 */
public enum Religion implements EnumResolvable
{

    CHRISTIAN("Christian","CH"),
    MOSLEM("Islam","MS"),
    TRADITIONALIST("Traditionalist","TD"),
    OTHER("Other","OT");
    
    private String label, code;

    private Religion(String label, String code)
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
