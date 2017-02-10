/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.email;

import com.google.common.base.Strings;
import com.stately.common.utils.CommonUtil;
import com.stately.common.utils.FileUtilities;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Admin
 */
public class EmailProcessor {

    private static final Logger LOG = Logger.getLogger(EmailProcessor.class.getName());

    private HtmlEmail htmlEmail;

    private EmailSetupConfig emailCredential;
    private String subject;

    public EmailProcessor() {
    }

    public EmailProcessor(EmailSetupConfig emailCredential, String subject) {
        this.emailCredential = emailCredential;
        this.subject = subject;

        configureHtmlEmail();
    }

    public EmailProcessor(HtmlEmail htmlEmail, String subject) {
        this.htmlEmail = htmlEmail;
        this.subject = subject;

    }

    private void configureHtmlEmail() {
        htmlEmail = new HtmlEmail();

        try {

            htmlEmail.setHostName(emailCredential.getEmailHost());
            htmlEmail.setFrom(emailCredential.getSenderEmailAddress(), emailCredential.getAccountName());
            htmlEmail.setSubject(subject);
            htmlEmail.setAuthentication(emailCredential.getAuthEmail(), emailCredential.getEmailPassword());

            if (emailCredential.getSmptPort() != 0) {
                htmlEmail.setSmtpPort(emailCredential.getSmptPort());
            }
            
            if (emailCredential.isUseSsl())
            {
//                htmlEmail.sets
                htmlEmail.setSslSmtpPort(""+emailCredential.getSmptPort());
                htmlEmail.setSSLOnConnect(true);
            }
            
        }
        catch (Exception e)
        {
            LOG.log(Level.SEVERE, "Email configuration error", e);
        }
    }
    
    public HtmlEmail configureHtmlEmail(EmailSetupConfig configuration)
    {
        if(configuration == null)
        {
            return new HtmlEmail();
        }
        this.emailCredential = configuration;

        configureHtmlEmail();

        return htmlEmail;
    }

    public boolean attachToEmail(File fileToAttach) {
        return attachToEmail(fileToAttach.getName(), fileToAttach);
    }

    public boolean attachToEmail(String fileName, File fileToAttach) {
        EmailAttachment emailAttachment = new EmailAttachment();

        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        emailAttachment.setDescription(fileName);
        
        try 
        {
            if(fileToAttach != null)
            {
                String nameToAttact = fileName + "." + FileUtilities.getFileExtension(fileToAttach.getName());

                emailAttachment.setName(nameToAttact);

                emailAttachment.setPath(fileToAttach.getAbsolutePath());
                htmlEmail.attach(emailAttachment);
                htmlEmail.setBoolHasAttachments(true);
                return true;
            }
        } catch (EmailException ex) {
            LOG.log(Level.SEVERE, "Unable to attach file", ex);
        }

        return false;
    }

    public String embedFile(File fileToEmbed, String name) {
        try {
            if (fileToEmbed != null) {
                return htmlEmail.embed(fileToEmbed, name);
            }
        } catch (Exception e) {
        }

        return null;
    }

    public String embedFile(URL urlToFile) {
        try {
            if (urlToFile != null) {
                return htmlEmail.embed(urlToFile, "px");
            }
        } catch (Exception e) {
        }
        return null;
    }

    public boolean sentHtmlEmail(String receipient, String emailContent) {
        try {
            htmlEmail.addTo(receipient);

            if (emailContent == null || emailContent.isEmpty()) {
                emailContent = "<html></html>";
            }

            
//            System.out.println("configuration :");
//            System.out.println("htmlEmail.getSslSmtpPort() " + htmlEmail.getSslSmtpPort());
//            System.out.println("htmlEmail.getSmtpPort()" + htmlEmail.getSmtpPort());
//            System.out.println("htmlEmail.getSslSmtpPort()" + htmlEmail.getSslSmtpPort());
//            System.out.println("htmlEmail.getHostName() " + htmlEmail.getHostName());
//            System.out.println("isSSLOnConnect " + htmlEmail.isSSLOnConnect());
            
            htmlEmail.setHtmlMsg(emailContent);
            String emailId = htmlEmail.send();

            System.out.println("Email dispatched : " + emailId);

            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to send file", e);
        }

        return false;
    }

    public boolean sentHtmlEmail(EmailMessage emailMessage, EmailSetupConfig configuration) {
        try {
            htmlEmail = configureHtmlEmail(configuration);

            return sentHtmlEmail(emailMessage);

//            for (String emailAddress : emailMessage.getToAddressList())
//            {
//                htmlEmail.addTo(emailAddress);
//            }
//            
//            for (String emailAddress : emailMessage.getBcAddressList())
//            {
//                htmlEmail.addBcc(emailAddress);
//            }
//            
//            htmlEmail.setHtmlMsg("<html>"+emailMessage.getEmailText()+"</html>");
//            htmlEmail.setSubject(emailMessage.getSubject());
//            
//            String id = htmlEmail.send();
//            System.out.println("EMAIL SENT ID : " + id);
//            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to send email messaeg", e);
        }

        return false;
    }

    public boolean sentHtmlEmail(EmailMessage emailMessage) {
        try {

            for (String emailAddress : emailMessage.getToAddressList()) {
                if (CommonUtil.isEmailValid(emailAddress)) {
                    htmlEmail.addTo(emailAddress);
                }
            }

            for (String emailAddress : emailMessage.getBcAddressList()) {
                htmlEmail.addBcc(emailAddress);
            }

            for (com.latlab.common.email.EmailAttachment attachemnt : emailMessage.getEmailAttachmentsList()) {
                attachToEmail(attachemnt.getName(), attachemnt.getFile());
            }

            htmlEmail.setHtmlMsg("<html>" + emailMessage.getEmailText() + "</html>");
            htmlEmail.setSubject(emailMessage.getSubject());

            String id = htmlEmail.send();
            System.out.println("EMAIL SENT ID : " + id);
            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to send email messaeg", e);
        }

        return false;
    }
    
    public boolean addBccEmail(String emailAddress)
    {
        return addBccEmail(emailAddress, null);
//        try
//        {
//            if(CommonUtil.isEmailValid(emailAddress))
//        {
//            htmlEmail.addBcc(emailAddress);
//            return true;
//        }
//        } catch (Exception e)
//        {
//        }
//        
//        return false;
        
        
    }
    
    public boolean addBccEmail(String emailAddress, String accountName)
    {
        try
        {
            if(CommonUtil.isEmailValid(emailAddress))
        {
            
            if(Strings.isNullOrEmpty(accountName))
            {
                htmlEmail.addBcc(emailAddress);
            }
            else
            {
                htmlEmail.addBcc(emailAddress,accountName);
            }
            
            return true;
        }
        } catch (Exception e)
        {
        }
        
        return false;
    }

    public boolean sendHtmlEmail(List<String> receipients, List<String> bccRecepients, String emailContent) {
        try {
            for (String receipient : receipients) 
            {
                htmlEmail.addTo(receipient);
            }

            for (String bcc : bccRecepients) {
                htmlEmail.addBcc(bcc);
            }

            htmlEmail.setHtmlMsg(emailContent);
            htmlEmail.send();

            return true;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to send file", e);
        }

        return false;
    }

    public boolean addTo(String toEmail) {
        
        return addTo(toEmail, null);
    }
    
    
    public boolean addTo(String emailAddress, String accountName)
    {
        try
        {
            if(CommonUtil.isEmailValid(emailAddress))
        {
            
            if(Strings.isNullOrEmpty(accountName))
            {
                htmlEmail.addTo(emailAddress);
            }
            else
            {
                htmlEmail.addTo(emailAddress,accountName);
            }
            
            return true;
        }
        } catch (Exception e)
        {
        }
        
        return false;
    }


    public HtmlEmail getHtmlEmail() {
        return htmlEmail;
    }

    public static void main(String[] args) {
        EmailSetupConfig emailSetupConfig = new DefaultEmailConfiguration();

//        
//        emailSetupConfig.setAuthEmail("no-reply@icsecurities.com");
//        emailSetupConfig.setEmailHost("smtp.fasthosts.co.uk");
//        emailSetupConfig.setEmailPassword("iCsecurities*");
//        emailSetupConfig.setSenderEmailAddress("mtnghanashares@icsecurities.com");
//        emailSetupConfig.setSenderEmailAddress("no-reply@icsecurities.com");
//        emailSetupConfig.setSmptPort(25);
//        
//        emailSetupConfig.setAuthEmail("edwin.amoakwa@mycowrie.com");
//        emailSetupConfig.setEmailHost("smtp.icassetmanagers.com");
//        emailSetupConfig.setEmailPassword("akedwin");
//        emailSetupConfig.setSenderEmailAddress("info@mycowrie.com");
        
//        emailSetupConfig.setAuthEmail("alerts@linkexchange.com.gh");
//        emailSetupConfig.setEmailHost("mail.linkexchange.com.gh");
//        emailSetupConfig.setEmailPassword("link123+");
//        emailSetupConfig.setSenderEmailAddress("alerts@linkexchange.com.gh");


        emailSetupConfig.setAuthEmail("statements@unitedpensiontrustees.com.gh");
        emailSetupConfig.setEmailHost("secure.emailsrvr.com");
        emailSetupConfig.setEmailPassword("YY5Z&hjx");
        emailSetupConfig.setSenderEmailAddress("statements@unitedpensiontrustees.com.gh");
        emailSetupConfig.setUseSsl(true);


//        emailSetupConfig.setAuthEmail("no-reply@mtnghanashares.com");
//        emailSetupConfig.setEmailHost("smtp.gmail.com");
//        emailSetupConfig.setEmailPassword("mtn$GH101$");
//        emailSetupConfig.setSenderEmailAddress("no-reply@mtnghanashares.com");
//        emailSetupConfig.setAccountName("MTN Ghana Shares");
        emailSetupConfig.setSmptPort(465);

//        emailSetupConfig.setUseSsl(true);

        EmailProcessor emailProcessor = new EmailProcessor(emailSetupConfig, "My Email");

        boolean sent = emailProcessor.sentHtmlEmail("seth.nartey@inflexioncap.com", "Sample Email");
//        boolean sent = emailProcessor.sentHtmlEmail("edwin.amoakwa@gmail.com", "Sample Email");
//        emailProcessor.sentHtmlEmail("edwin.amoakwa@icsecurities.com", "Sample Email");
    }
}
