/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.common.utils;



import com.stately.common.utils.StringSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Edwin
 */
public class UserAccountUtil
{

    public static String createUserName(String fullName)
    {
        List<String> finalNames = StringSet.createStringSet(fullName);
        String username = "";

        int indexCounter = 0;

        for (String individualNames : finalNames)
        {
            indexCounter++;

            if (indexCounter == 1)
            {
                continue;
            }

            username = individualNames.substring(0, 1)+username;

        }

        username += finalNames.get(0);

        System.out.println("user name is " + username);


        return username;
    }

     public static String createDateID(String fullString)
     {
        String username = createStringToken(fullString);

        String dateFo =  new SimpleDateFormat("ddMMyyhhmmss").format(new Date());

        username += dateFo;

        return username.toLowerCase();
    }



    public static String createStringToken(String fullString)
    {
         List<String> finalNames = StringSet.createStringSet(fullString);
        String username = "";

        int indexCounter = 0;

        for (String individualNames : finalNames)
        {
            indexCounter++;

            

            if(!username.isEmpty() )
            {
                username = username + "-";
            }

            individualNames = individualNames.replaceAll("\\.", "");
            individualNames = individualNames.replaceAll("-", "");
            individualNames = individualNames.replaceAll("\\(", "");
            individualNames = individualNames.replaceAll("\\)", "");
            individualNames = individualNames.replaceAll("/", "");
            individualNames = individualNames.replaceAll("\\'", "");
            

            if(individualNames.length() <=2)
            {
                username = username +individualNames;
            }
            else
            {
                username = username + individualNames.substring(0, 3);
            }

            
            if(indexCounter == 4)
                break;
            
        }

        return username.toLowerCase();
        
     }

     public static String createDate_uuidPart(String fullString)
    {
         String id = createStringToken(fullString);

         String datePart = new SimpleDateFormat("ddMMyy").format(new Date());

         id = id + "-" +datePart+"-" + uuidPart();

         return id.toLowerCase();

     }

     public static String uuidPart()
    {
         String id = UUID.randomUUID().toString().toUpperCase();

         id = id.substring(0, 4).toLowerCase();

         return id;
     }

     
     private static String baseString = "";
     public static String getEnterUUIDPK(String entityName)
    {
        String name = baseString + entityName;
        byte[] nameByte = name.getBytes();

        String id = UUID.nameUUIDFromBytes(nameByte).toString();

        return id.toLowerCase();
    }

    public static String getRandomUUID()
    {
        return UUID.randomUUID().toString().toLowerCase();
    }
    
    public static void main(String[] args)
    {
        String myName = "Kwame Mohammed Micheal";
        myName = "HFC Bank (Ghana) Limited";


        createDateID(myName);
        System.out.println(createDate_uuidPart(myName));
        System.out.println("");
    }

}
