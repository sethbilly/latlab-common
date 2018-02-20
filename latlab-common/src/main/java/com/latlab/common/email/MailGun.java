/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.email;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 *
 * @author INF-BILLY
 */
public class MailGun {
    
    private static final String API_KEY = "key-0c6e19255e9b421568acde5e23edc921";
    private static final String DOMAIN = "sandbox7428db3cf4b64332bfdca02749a692a5.mailgun.org";
    
    private static JsonNode sendSimpleMessage(String from, String to, String subject, String message) throws UnirestException {

        HttpResponse<JsonNode> request = Unirest.post("https://api.mailgun.net/v3/" + DOMAIN + "/messages")
                        .basicAuth("api", API_KEY)
                    .queryString("from", from + " <USER@YOURDOMAIN.COM>")
                    .queryString("to", to)
                    .queryString("subject", subject)
                    .queryString("text", message)
                    .asJson();

           return request.getBody();
    }
    
    public static void sendMessage(String sender, String receipient, String emailSubject, String emailContent){
        try {
            sendSimpleMessage(sender, receipient, emailSubject, emailContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            sendSimpleMessage("Remittance", "sethbilly.nartey@gmail.com", "Test", "Hello world");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
