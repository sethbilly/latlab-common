/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.constants;



/**
 *
 * @author edwin
 */
public enum DebitCredit implements EnumCommon
{

    DEBIT("Dr"),
    CREDIT("Cr");

    private String initials;

    private DebitCredit(String initials)
    {
        this.initials = initials;
    }

    public String getInitials()
    {
        return initials;
    }

    public void setInitials(String initials)
    {
        this.initials = initials;
    }


    public String getFullString()
    {
        return getClass().getCanonicalName()+"."+name();
    }
    
    public DebitCredit reverse()
    {
        if(this != null)
        {
            if(this ==  DebitCredit.CREDIT)
            {
                return DEBIT;
            }
            else if(this ==  DebitCredit.DEBIT)
            {
                return CREDIT;
            }
        }
        
        return null;
    }

    private static DebitCredit[] drCr = new DebitCredit[]{DebitCredit.CREDIT, DebitCredit.DEBIT};
    
    public static DebitCredit[] debitCredit()
    {
        return drCr;
    }
    
}
