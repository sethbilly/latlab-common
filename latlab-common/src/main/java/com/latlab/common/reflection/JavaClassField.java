/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.reflection;

/**
 *
 * @author Edwin Amoakwa Kwame
 *         akedwin@yahoo.com
 *          0277115150
 */
public class JavaClassField
{
    public static final String STRING = "String";
    public static final String INTEGERR = "Integer";
    public static final String DOUBLE = "Double";
    public static final String DATE = "Date";
    public static final String int_ = "int";
    public static final String double_ = "double";


    private boolean isAnnotated;
    private boolean isAnnotatedAs_PK;
    private String fieldName;
    private String fieldDataType;


    public JavaClassField(String fieldName, String fieldDataType)
    {
        this.fieldName = fieldName;
        this.fieldDataType = fieldDataType;
    }


    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getFieldDataType()
    {
        return fieldDataType;
    }

    public void setFieldDataType(String fieldDataType)
    {
        this.fieldDataType = fieldDataType;
    }

    public boolean isIsAnnotated()
    {
        return isAnnotated;
    }

    public void setIsAnnotated(boolean isAnnotated)
    {
        this.isAnnotated = isAnnotated;
    }

    public boolean isIsAnnotatedAs_PK()
    {
        return isAnnotatedAs_PK;
    }

    public void setIsAnnotatedAs_PK(boolean isAnnotatedAs_PK)
    {
        this.isAnnotatedAs_PK = isAnnotatedAs_PK;
    }


    public String getGetterMethod()
    {
        String classNameFirstCharacter = fieldName.substring(0, 1).toUpperCase();
        String classNameRestOfCharacters = fieldName.substring(1);
        String objectName = classNameFirstCharacter+classNameRestOfCharacters;

        return "get"+objectName+"";
    }

    public String getSetterMethod()
    {
        String classNameFirstCharacter = fieldName.substring(0, 1).toUpperCase();
        String classNameRestOfCharacters = fieldName.substring(1);
        String objectName = classNameFirstCharacter+classNameRestOfCharacters;

        return "set"+objectName+"";
    }


    public String getFieldDeclaration()
    {
        return fieldDataType + " " + fieldName;
    }

    @Override
    public String toString()
    {
        String str = "Type: " + fieldDataType + "\n" +
                "Name: " + fieldName + "\n" +
                "isAnnotated = " + isIsAnnotated() + "\n" +
                "isAnnotatedAs_PK = " + isIsAnnotatedAs_PK();

        str = getFieldName();

        return str;
    }



    

}
