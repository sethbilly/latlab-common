package com.latlab.common.email;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class EmailMessage
{
    List<EmailAttachment> emailAttachmentsList = new LinkedList<>();
    private List<String> toAddressList = new ArrayList<>();
    private List<String> bcAddressList = new ArrayList<>();
    private String emailText = "";
    private String subject = "";

    public void addAttachement(EmailAttachment attachment)
    {
        emailAttachmentsList.add(attachment);
    }

    public void addAttachement(File attachment)
    {
        emailAttachmentsList.add(new EmailAttachment(attachment.getName(), attachment));
    }
    
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

    public List<EmailAttachment> getEmailAttachmentsList()
    {
        return emailAttachmentsList;
    }

    public void setEmailAttachmentsList(List<EmailAttachment> emailAttachmentsList)
    {
        this.emailAttachmentsList = emailAttachmentsList;
    }
    
    
}
