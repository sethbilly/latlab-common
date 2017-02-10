/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.common.utils;

import com.google.common.base.Strings;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Edwin
 */
public class StringUtil
{

    public static String removeTraillingZero(String figure)
    {
        return StringUtils.removeEnd(figure, ".0");
    }

    public static String straightenUp(String str)
    {
        return str.replaceAll("\r", " ").replaceAll("\r", " ").replaceAll("\n", " ");
    }

    public static String fill(String orig, int number)
    {
        StringBuilder builder = new StringBuilder(orig);

        int start = number - orig.length();

        for (int x = 0; x < start; x++)
        {
            builder.append(" ");

        }

        return builder.toString();
    }

    public static String fillUpEqually(List<String[]> input)
    {
        StringBuilder sb = new StringBuilder();

        if (input == null)
        {
            return "";
        }

        if (input.isEmpty())
        {
            return "";
        }

        int numberOfColumns = input.get(0).length;

        int[] maximumColumnLength = new int[numberOfColumns];

        for (String[] row : input)
        {

            for (int column = 0; column < numberOfColumns; column++)
            {
                int colLength = row[column].length();

                if (colLength > maximumColumnLength[column])
                {
                    maximumColumnLength[column] = colLength;
                }
            }
        }

        for (String[] row : input)
        {
            for (int column = 0; column < numberOfColumns; column++)
            {
                String colValue = row[column];

                sb.append(fill(colValue, maximumColumnLength[column]));
                sb.append("          ");

            }

            sb.append("\n");
        }

        return sb.toString();
    }

    public static String ecapeBackSlash(String path)
    {
        char pathChars[] = path.toCharArray();

        path = "";

        for (int i = 0; i < pathChars.length; i++)
        {
            char c = pathChars[i];

            if (c == '\\')
            {
                path = path + "\\";
            }

            path = path + c;

        }

        return path;
    }

    public static void printArray(Object arrays[])
    {
        String output = "";

        if (arrays == null)
        {
            System.out.println("Arrray is empty");
            return;
        }
        for (int i = 0; i < arrays.length; i++)
        {
            output += (i + 1) + " = " + arrays[i] + "\n";

        }

        System.out.println(output);
    }

    public static void printArrayHorizontally(Object arrays[])
    {
        String output = "";

        if (arrays == null)
        {
            System.out.println("Arrray is empty");
            return;
        }
        for (int i = 0; i < arrays.length; i++)
        {
            output += arrays[i] + "\t";

        }

        System.out.println(output);
    }

    public static void printArray(String arrays[][])
    {
        String output = "";
        if (arrays == null)
        {
            System.out.println("Arrray is empty");
            return;
        }

        for (int i = 0; i < arrays.length; i++)
        {
            String[] inner = arrays[i];

            for (int j = 0; j < inner.length; j++)
            {
                String item = inner[j];

                output += item + " \t";
            }

            output += "\n";
        }

        System.out.println(output);
    }

    public static void printObjectListArray(List<Object[]> objs)
    {
        if (objs == null)
        {
            return;
        }

        for (Object[] objects : objs)
        {
            if (objects == null)
            {
                continue;
            }

            printArrayHorizontally(objects);

        }
    }

    public static void printStringListArray(List<String[]> objs)
    {
        if (objs == null)
        {
            return;
        }

        for (Object[] objects : objs)
        {
            if (objects == null)
            {
                continue;
            }

            printArrayHorizontally(objects);

        }
    }

    public static String removeCharAt(String s, int i)
    {
        StringBuilder buf = new StringBuilder(s.length() - 1);
        buf.append(s.substring(0, i)).append(s.substring(i + 1));
        return buf.toString();
    }

    public static String removeChar(String s, char c)
    {
        StringBuilder buf = new StringBuilder(s.length());
        buf.setLength(s.length());
        int current = 0;
        for (int i = 0; i < s.length(); i++)
        {
            char cur = s.charAt(i);
            if (cur != c)
            {
                buf.setCharAt(current++, cur);
            }
        }
        return buf.toString();
    }

    public static String replaceCharAt(String s, int i, char c)
    {
        StringBuilder buf = new StringBuilder(s);
        buf.setCharAt(i, c);
        return buf.toString();
    }

    public static String deleteAllNonDigit(String s)
    {
        String temp = s.replaceAll("\\D", "");
        return temp;
    }

    public static String replaceAllChar(String originalStr, String f, String r)
    {
        if(Strings.isNullOrEmpty(originalStr))
        {
            return "";
        }
        String temp = originalStr.replace(f, r);
        return temp;
    }

    public static String replaceFirstOccurrenceWith(String str, String regex, String replacemment)
    {
        if(Strings.isNullOrEmpty(str))
        {
            return "";
        }
        
        String temp = StringUtils.removeStart(str, regex);
        temp = replacemment.concat(temp);
        return temp;
    }

    public static boolean isNullOrEmpty(String str)
    {
        return Strings.isNullOrEmpty(str);
    }

    public static void main(String[] args)
    {
        System.out.println(replaceCharAt("233245184371", 0, '0'));
        String[] records = new String[6];

        List<String[]> stringList = new LinkedList<String[]>();

        records = new String[6];
        records[0] = "vrstrwe trwe";
        records[1] = "dsgds gsd gsdg sdgd g";
        records[2] = "sdgfsd gdsg g ds";
        records[3] = "dsgs dg";
        records[4] = "";
        records[5] = "23265";

        stringList.add(records);

        records = new String[6];
        records[0] = "vrstrwe trwe";
        records[1] = "dsgds gsd gsdg sdgd g";
        records[2] = "sdgfsd gdsg g ds";
        records[3] = "dsgs dg";
        records[4] = "dd";
        records[5] = "23265";

        stringList.add(records);

        System.out.println(fillUpEqually(stringList));

    }

}
