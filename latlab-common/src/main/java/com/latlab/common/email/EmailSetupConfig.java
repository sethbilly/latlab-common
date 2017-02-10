/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.email;

/**
 *
 * @author Edwin
 */
public interface EmailSetupConfig
{

    String getAccountName();

    String getAuthEmail();

    String getEmailHost();

    String getEmailPassword();

    void setAccountName(String accountName);

    void setAuthEmail(String emailAddress);

    void setEmailHost(String emailHost);

    void setEmailPassword(String emailPassword);

    public String getSenderEmailAddress();

    public void setSenderEmailAddress(String senderEmaiAddress);
    
    boolean isUseSsl();
    
    public void setUseSsl(boolean ssl);
    
    int getSmptPort();
    public void setSmptPort(int port);

}
