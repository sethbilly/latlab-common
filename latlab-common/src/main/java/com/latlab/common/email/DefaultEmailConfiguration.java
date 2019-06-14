/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.email;

import com.latlab.common.jpa.CommonEntityModel;
import java.io.Serializable;
import javax.persistence.*;


/**
 *
 * @author Edwin
 */

public class DefaultEmailConfiguration extends CommonEntityModel implements Serializable, EmailSetupConfig
{
    private static final long serialVersionUID = 1L;
    
    
    @Column(name = "auth_email_address")
    private String authEmail;
    
    @Column(name = "sender_email_address")
    private String senderEmailAddress;
        
    @Column(name = "smpt_port")
    private int smptPort;
    
    @Column(name = "ssl")
    private boolean ssl;
    
    
    @Column(name = "bcc_email")
    private String bccEmail;
    
    @Column(name = "bcc_email_account_name")
    private String bccEmailAccountName;
    
    
    @Column(name = "email_account_name")
    private String accountName;
    
    
    @Column(name = "email_password")
    private String emailPassword;
    
    @Column(name = "email_host")
    private String emailHost;
    
    
    @Column(name = "email_subject")
    private String emailSubject;
    
    
    @Column(name = "email_text")
    private String emailText;
        
    @Column(name = "email_footer")
    private String emailFooter;
    

    @Override
    public String getSenderEmailAddress()
    {
        return senderEmailAddress;
    }

    @Override
    public void setSenderEmailAddress(String senderEmailAddress)
    {
        this.senderEmailAddress = senderEmailAddress;
    }
    
    
    public DefaultEmailConfiguration()
    {
        
    }

    @Override
    public String getAuthEmail()
    {
        return authEmail;
    }

    @Override
    public void setAuthEmail(String authEmail)
    {
        this.authEmail = authEmail;
    }

    public String getEmailFooter()
    {
        return emailFooter;
    }

    
    public String getBccEmail() {
        return bccEmail;
    }

    
    public void setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
    }

    
    public String getBccEmailAccountName() {
        return bccEmailAccountName;
    }

    
    public void setBccEmailAccountName(String bccEmailAccountName) {
        this.bccEmailAccountName = bccEmailAccountName;
    }

    public void setEmailFooter(String emailFooter)
    {
        this.emailFooter = emailFooter;
    }

    @Override
    public String getAccountName()
    {
        return accountName;
    }

    @Override
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    @Override
    public String getEmailHost()
    {
        return emailHost;
    }

    @Override
    public void setEmailHost(String emailHost)
    {
        this.emailHost = emailHost;
    }

    @Override
    public String getEmailPassword()
    {
        return emailPassword;
    }

    @Override
    public void setEmailPassword(String emailPassword)
    {
        this.emailPassword = emailPassword;
    }

    public String getEmailText()
    {
        return emailText;
    }

    public void setEmailText(String emailStatement)
    {
        this.emailText = emailStatement;
    }

    public String getEmailSubject()
    {
        return emailSubject;
    }

    public void setEmailSubject(String emailSubject)
    {
        this.emailSubject = emailSubject;
    }
    
    

    @Override
    public String toString()
    {
        return authEmail;
    }

    @Override
    public boolean isUseSsl()
    {
        return ssl;
    }

    @Override
    public void setUseSsl(boolean ssl)
    {
        this.ssl = ssl;
    }

    @Override
    public int getSmptPort()
    {
        return smptPort;
    }

    @Override
    public void setSmptPort(int smptPort)
    {
        this.smptPort = smptPort;
    }

    
    
}
