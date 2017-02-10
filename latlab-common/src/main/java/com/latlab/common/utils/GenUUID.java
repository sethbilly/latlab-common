/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.common.utils;

import java.util.UUID;

/**
 *
 * @author Edwin
 */
public class GenUUID
{

    private static String generatedPK;
    private static String baseString = "";

    public static void setBaseString(String base)
    {
        baseString = base;
    }

    public static String getEnterUUIDPK(String entityName)
    {
        String name = baseString + entityName;
        byte[] nameByte = name.getBytes();

        generatedPK = UUID.nameUUIDFromBytes(nameByte).toString();

        return generatedPK.toUpperCase();
    }

    public static String getRandomUUID()
    {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    public static String getRandomUUID(int length)
    {
        return getRandomUUID().substring(0, length);
    }
    

}
