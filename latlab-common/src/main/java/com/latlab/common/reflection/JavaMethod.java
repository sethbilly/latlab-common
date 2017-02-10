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
public class JavaMethod
{

    private boolean isBeanMethod = false;
    private boolean isGetterMethod = false;
    private boolean isSetterMethod = false;
    private String methodName = "aMehodName";
    private String methodReturnType = "aReturnType";

    public boolean isIsBeanMethod()
    {
        return isBeanMethod;
    }

    public void setIsBeanMethod(boolean isBeanMethod)
    {
        this.isBeanMethod = isBeanMethod;
    }

    public boolean isIsGetterMethod()
    {
        return isGetterMethod;
    }

    public void setIsGetterMethod(boolean isGetterMethod)
    {
        this.isGetterMethod = isGetterMethod;
    }

    public boolean isIsSetterMethod()
    {
        return isSetterMethod;
    }

    public void setIsSetterMethod(boolean isSetterMethod)
    {
        this.isSetterMethod = isSetterMethod;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public String getMethodReturnType()
    {
        return methodReturnType;
    }

    public void setMethodReturnType(String methodReturnType)
    {
        this.methodReturnType = methodReturnType;
    }

    @Override
    public String toString()
    {
        return methodReturnType + " " +methodName;
    }
}
