/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class EmailMessage
{
    private List<String> toAddressList = new ArrayList<String>();
    private List<String> bcAddressList = new ArrayList<String>();
    private String emailText = "";
    private String subject = "";
    private String fromName;

    public List<String> getToAddressList()
    {
        return toAddressList;
    }

    public void setToAddressList(List<String> toAddressList)
    {
        this.toAddressList = toAddressList;
    }

    public List<String> getBcAddressList()
    {
        return bcAddressList;
    }

    public void setBcAddressList(List<String> bcAddressList)
    {
        this.bcAddressList = bcAddressList;
    }

    public String getEmailText()
    {
        return emailText;
    }

    public void setEmailText(String emailText)
    {
        this.emailText = emailText;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getFromName()
    {
        return fromName;
    }

    public void setFromName(String fromName)
    {
        this.fromName = fromName;
    }
    
    
}
