/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ClassInfo
{
    public static List<Field> getInheritedFields(Class<?> type)
    {
        List<Field> fieldsList = new LinkedList<>();
        
        for(Class<?> c = type; c != null; c = c.getSuperclass())
        {
            fieldsList.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fieldsList;
    }
    
    public static List<Method> getInheritedMethods(Class<?> type)
    {
        List<Method> methodsList = new LinkedList<>();
        
        for(Class<?> c = type; c != null; c = c.getSuperclass())
        {
            Method[] declaredMethods = c.getDeclaredMethods();
            
            for (Method method : declaredMethods)
            {
                if(method.getName().startsWith("set"))
                {
                    continue;
                }
                if(method.getReturnType() == List.class)
                {
                    continue;
                }
                
                methodsList.add(method);
            }
            
        }
        return methodsList;
    }
}
