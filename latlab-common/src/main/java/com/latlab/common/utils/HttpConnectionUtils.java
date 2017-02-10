package com.stately.common.utils;

import com.google.common.base.Strings;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Ainoo Dauda
 * @company IC Securities Ghana Ltd
 * @email dauda.ainoo@icsecurities.com
 * @date 20 October 2015
 *
 *
 */
public class HttpConnectionUtils
{

    public static boolean log = false;
    public static HttpConResponse sendHttp(String url, String method, String contentType, String body)
    {
        HttpConResponse httpConResponse = new HttpConResponse();
        try
        {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            
            if(contentType != null)
            {
                con.setRequestProperty("Content-Type", contentType+";charset=utf-8");
            }
            
            
            con.setConnectTimeout(5000);
            con.setReadTimeout(10000);

            // Send post request
            if(null != body)
            {
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(body);
                wr.flush();
                wr.close();
            }
            
//            System.out.println("Printing request properties ...");
//            System.out.println(con.getRequestProperties());
//            System.out.println("Content-Type : "+con.getRequestProperty("Content-Type"));
            
            int responseCode = con.getResponseCode();
            httpConResponse.setResponseCode(responseCode);
            String respd = "";
            
            if(log)
            {
                System.out.println("\nSending '" + method + "' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
            }
            

            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
            {
                respd = response.append(inputLine).toString();
            }
            in.close();

            //print result
//            System.out.println("The Response >>>>>> " + respd);
            httpConResponse.setResponse(respd);
        } catch (Exception e)
        {
            if(log)
            {
                e.printStackTrace();
            }
        }

        return httpConResponse;
    }

//    public static HttpConResponse sendHttp(String url, String method)
//    {
//        return sendHttp(url, method, "application/text", null);        
//    }

    public static HttpConResponse sendJsonPost(String url, String body)
    {
        return sendHttp(url, "POST", "application/json", body);
    }
    
    public static HttpConResponse sendJsonGet(String url, String body)
    {
        return sendHttp(url, "GET", "application/json", body);
    }

    public static HttpConResponse sendXmlPost(String url, String body)
    {
        return sendHttp(url, "POST", "application/xml", body);
    }

    public static HttpConResponse sendPost(String url)
    {
        return sendHttp(url, "POST", null, null);
    }

    public static HttpConResponse sendGet(String url)
    {
        return sendHttp(url, "GET", null, null);
    }

    public static String encodeParamValues(Map<String, String> map)
    {
        String webParam = "";
        try
        {
            List<String> list = new LinkedList<>(map.keySet());

            for (Iterator<String> iterator = list.iterator(); iterator.hasNext();)
            {
                String param = iterator.next();
                
                if(Strings.isNullOrEmpty(param))
                {
                    param = "";
                }
                
                webParam += param + "=" + URLEncoder.encode(map.get(param), "UTF-8");

                if (iterator.hasNext())
                {
                    webParam += "&";
                }

            }

        } catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }

        return webParam;
    }
    
    public static void main(String[] args)
    {
//        System.out.println(sendGet("http://139.162.171.157:8080/mtnsdpswitch/rest/bib/subscriber-detail?phoneNo=233245184371"));
//        System.out.println(sendHttp("http://139.162.171.157:8080/mtnsdpswitch/rest/bib/subscriber-detail?phoneNo=233245184371","GET",null, null));
        System.out.println(sendHttp("http://localhost/iexchange-unity/rest/api/investor-update?","POST",null, null));
//        System.out.println(sendHttp("http://localhost/iexchange-unity/rest/api/investor-update?","POST",MediaType.APPLICATION_JSON, "{'phoneNo':'0245184371'}"));
    }

}
