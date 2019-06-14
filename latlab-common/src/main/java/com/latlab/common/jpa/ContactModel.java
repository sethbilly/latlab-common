/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author ICSGH-BILLY
 */
@MappedSuperclass
public class ContactModel extends CommonEntityModel
{

     private static final long serialVersionUID = 1L;
     
    public static final String _telephoneNumber = "telephoneNumber";
    @Column(name = "telephone_number")
    private String telephoneNumber;

    public static final String _mobileNumber = "mobileNumber";
    @Column(name = "mobile_number")
    private String mobileNumber;

    public static final String _email = "email";
    @Column(name = "email")
    private String email;

    @Column(name = "address_line")
    private String addressLine;

    @Column(name = "address_line1")
    private String addressLine1;

    public ContactModel()
    {
    }
    
    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddressLine()
    {
        return addressLine;
    }

    public void setAddressLine(String addressLine)
    {
        this.addressLine = addressLine;
    }

    public String getAddressLine1()
    {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    
}
