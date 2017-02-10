/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.formating;

/**
 *
 * @author Edwin
 */
public class RegEx
{
    private static StringBuilder stringBuilder = new StringBuilder();


    public static String convertToNonCaseExpression(String inputExpression)
    {
        stringBuilder.delete(0, stringBuilder.length());

        char[] inputChars = inputExpression.toCharArray();

        for (int i = 0; i < inputChars.length; i++)
        {
            stringBuilder.append("["+Character.toUpperCase(inputChars[i]));
            stringBuilder.append("|");
            stringBuilder.append(Character.toLowerCase(inputChars[i]) + "]");

        }

        return stringBuilder.toString();
    }

}
