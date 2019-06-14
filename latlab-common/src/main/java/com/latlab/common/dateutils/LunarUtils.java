/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.dateutils;

import com.latlab.common.reflection.JavaClass;
import com.latlab.common.reflection.JavaClassField;
import java.util.List;

public class LunarUtils {

    public static String tap = "    ";
    private static String str = "";

    public static String getCamelCaseObjectName(String string) {
        String firstChar = string.substring(0, 1).toLowerCase();
        String restOfString = string.substring(1);

        str = firstChar + restOfString;

        return str;
    }

    public void jjj(String className) {
//        StringUtils.
    }

    public static String getCamelCaseClassName(String string) {
        String firstChar = string.substring(0, 1).toUpperCase();
        String restOfString = string.substring(1);

        str = firstChar + restOfString;

        return str;
    }

    public static String getSimpleClassName(String fullClassName) {

        int lastIndexOfDot = fullClassName.lastIndexOf(".");

        str = fullClassName.substring(lastIndexOfDot + 1);

        return str;
    }

    public static String get_getAllMethodName(String detailclassName) {
        return getCamelCaseObjectName(detailclassName) + "GetAll";
    }

    public static String get_FindMehodName(String detailclassName) {
        return getCamelCaseObjectName(detailclassName) + "Find";
    }

    public static String get_FindByAttrib(String detailclassName) {
        return getCamelCaseObjectName(detailclassName) + "FindByAttribute";
    }

    public static String get_Delete(String detailclassName) {
        return getCamelCaseObjectName(detailclassName) + "Delete";
    }

    public static String get_Create(String detailclassName) {
        return getCamelCaseObjectName(detailclassName) + "Create";
    }

    public static String get_Update(String detailclassName) {
        return getCamelCaseObjectName(detailclassName) + "Update";
    }
//
//    public static String get_getAllMethodName(String detailclassName)
//    {
//        return getCamelCaseObjectName(detailclassName) + "GetAll";
//    }

    public static String getVariableNameAsconstant(String variableName) {

        StringBuilder builder = new StringBuilder();

        char letters[] = variableName.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            char c = letters[i];

            if (i == 0) {
                builder.append(Character.toUpperCase(c));
            } else if (Character.isUpperCase(c)) {
                builder.append("_" + c);
            } else {
                builder.append(Character.toUpperCase(c));
            }

        }

        return builder.toString();
    }

    public static String getVariableNameForDiplay(String variableName) {

        StringBuilder builder = new StringBuilder();

        char letters[] = variableName.toCharArray();

        for (int i = 0; i < letters.length; i++) {
            char c = letters[i];

            if (i == 0) {
                builder.append(Character.toUpperCase(c));
            } else if (Character.isUpperCase(c)) {
                builder.append(" " + c);
            } else {
//                builder.append(Character.toUpperCase(c));

                builder.append(c);
            }

        }

        return builder.toString();
    }

    public static String getColumLablesForTModel(JavaClass javaClass) {
        List<JavaClassField> listOfJavaClassFields = javaClass.getListOfJavaClassFields();

        if (listOfJavaClassFields == null) {
            return "\"\"";
        }

        if (listOfJavaClassFields.isEmpty()) {
            return "\"\"";
        }

        str = "";

        for (JavaClassField javaClassField : listOfJavaClassFields) {

            String variabeleDisplayName = getVariableNameForDiplay(javaClassField.getFieldName());
            str += "\"" + variabeleDisplayName + "\",";
        }

        str = str.substring(0, str.length() - 1);

        return str;
    }

    public static String getColumCodesForTModel(JavaClass javaClass) {
        List<JavaClassField> listOfJavaClassFields = javaClass.getListOfJavaClassFields();

        if (listOfJavaClassFields == null) {
            return "\"\"";
        }

        if (listOfJavaClassFields.isEmpty()) {
            return "\"\"";
        }

        str = "";

        for (JavaClassField javaClassField : listOfJavaClassFields) {

//            String variabeleDisplayName = getVariableNameForDiplay(javaClassField.getFieldName());
            str += "\"" + javaClassField.getFieldName() + "\",";
        }

        str = str.substring(0, str.length() - 1);

        return str;
    }

    public static String getTab(int number) {
        String tabs = "   ";

        for (int i = 0; i < number; i++) {
            tabs += tabs;
        }

        return tabs;
    }

}
