/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.formating;

/**
 *
 * @author Edwin
 */
public class ObjectValue {

    public static String getStringValue(Object object) {
        if (object == null) {
            return "";
        } else {
            return object.toString().trim();
        }
    }

    public static String get_AAAAAAAstringValue(Object object) {
        if (object == null) {
            return "";
        } else {
            return object.toString();
        }
    }

    public static Double getDoubleValue(Object object) {
        if (object == null) {
            return null;
        }

        try {
            return Double.parseDouble(String.valueOf(object.toString().replaceAll(",", "")));
        } catch (Exception e) {
        }
        return null;
    }

    public static double get_doubleValue(Object object) {
        if (object == null) {
            return 0.0;
        } else {
            try {
                return Double.parseDouble(object.toString().replaceAll(",", ""));
            } catch (Exception e) {
            }
        }
        return 0.0;
    }

    public static Integer getIntegerValue(Object object) {

        if (object == null) {
            return null;
        } else {
            try {
                String data = object.toString().replaceAll(",", "");

//                    if (data.contains("."))
//                    {
//                        data = data.replaceAll("\\.0", "");
//                    }
                try {
                    return Double.valueOf(data).intValue();
                } catch (Exception e) {
                }
            } catch (Exception e) {
//                    e.printStackTrace();
            }
        }

        return null;

    }

    public static int get_intValue(Object object) {

        if (object == null) {
            return 0;
        } else {
            Integer integer = getIntegerValue(object);
            if (integer != null) {
                return integer;
            }
        }

        return 0;

    }

    public static void main(String[] args) {
        System.out.println("texting data parsing ... ");
        System.out.println(Double.parseDouble("2.8385499E7") + 1);
        System.out.println(get_intValue("2.8385499E7"));
    }
}
