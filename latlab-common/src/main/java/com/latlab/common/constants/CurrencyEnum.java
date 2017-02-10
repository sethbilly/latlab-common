/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.constants;

/**
 *
 * @author edwin
 */
public enum CurrencyEnum
{
    GHS("Ghana Cedis","GHS","Cedis","Pesewas"),
    USD("US Dollars","USD","USD","Cents"),
    N("Niara","N","Nira","Kobo"),
    GBP("British Pound","GBP","Pound","Cent");
    
    private String currencyName;
    
    private String symbol;

    private String majorName;

    private String minorName;

    CurrencyEnum(String currencyName, String symbol, String majorName, String minorName)
    {
        this.currencyName = currencyName;
        this.symbol = symbol;
        this.majorName = majorName;
        this.minorName = minorName;
    }

    public String getCurrencyName()
    {
        return currencyName;
    }

    public void setCurrencyName(String currencyName)
    {
        this.currencyName = currencyName;
    }

    public String getSymbol()
    {
        return symbol;
    }

    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }

    public String getMajorName()
    {
        return majorName;
    }

    public void setMajorName(String majorName)
    {
        this.majorName = majorName;
    }

    public String getMinorName()
    {
        return minorName;
    }

    public void setMinorName(String minorName)
    {
        this.minorName = minorName;
    }

    @Override
    public String toString()
    {
        return  currencyName ;
    }

    

}
