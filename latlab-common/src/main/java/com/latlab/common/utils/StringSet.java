/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.common.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Edwin
 */
public class StringSet
{
    public static List<String> createStringSet(String fullName)
    {
        if(fullName == null)
            fullName =  UUID.randomUUID().toString();

        String[] nameSplit = fullName.split(" ");

        List<String> finalNames = new LinkedList<String>();

        for (int i = 0; i < nameSplit.length; i++)
        {
            String string = nameSplit[i];
            if(!string.isEmpty())
                finalNames.add(string);
        }




        return finalNames;

    }


    public static String variableName(String className)
    {
        String classNameFirstCharacter = className.substring(0, 1).toLowerCase();
        String classNameRestOfCharacters = className.substring(1);
        String objectName = classNameFirstCharacter+classNameRestOfCharacters;

        return objectName;
    }
    
    public static String initials(String className)
    {
        StringBuilder sb = new StringBuilder();
        
        for (int x = 0; x < className.length(); x++)
        {
            if(Character.isUpperCase(className.charAt(x)))
            {
                sb.append(className.charAt(x));
            }
        }

        return sb.toString();
    }


}
