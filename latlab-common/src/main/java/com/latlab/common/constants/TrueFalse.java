/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;

/**
 *
 * @author Edwin
 */
public enum TrueFalse 
{
    TRUE(true, "Yes"),
    FALSE(false,"No");
    boolean value;
    private String label;

    private TrueFalse(boolean value, String label) {
        this.value = value;
        this.label = label;
    }

    public boolean isValue() {
        return value;
    }

    public String getLabel()
    {
        return label;
    }

    
    
    @Override
    public String toString()
    {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
    
    
    
}
