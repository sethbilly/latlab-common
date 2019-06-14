/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jdbc;

import com.latlab.common.jpa.QryHelper;

/**
 *
 * @author Edwin
 */
public class SqlMapping<T>
{
    String fieldName;
    private QryHelper.FieldType fieldType;
    private T value;

    public SqlMapping(String fieldName, QryHelper.FieldType fieldType, T value)
    {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
        this.value = value;
    }
        
    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public QryHelper.FieldType getFieldType()
    {
        return fieldType;
    }

    public void setFieldType(QryHelper.FieldType fieldType)
    {
        this.fieldType = fieldType;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
    }
    
    
}
