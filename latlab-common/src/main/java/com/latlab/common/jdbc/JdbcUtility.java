/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jdbc;
import com.latlab.common.jpa.QryHelper;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class JdbcUtility implements Serializable
{
    public String insertStatement(LinkedList<SqlMapping> mappings, String tableName)
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("INSERT INTO ").append(tableName).append("(");
        int count = mappings.size();
        int counter = 0;
        for (SqlMapping sqlMapping : mappings)
        {
            buffer.append(sqlMapping.getFieldName());
            counter++;
            if(count != counter)
            {
                buffer.append(",");
            }
        }
        
        counter = 0;
        
        buffer.append(") values (");
        
        for (SqlMapping sqlMapping : mappings)
        {
            buffer.append("'").append(sqlMapping.getValue()).append("'");
            counter++;
            if(count != counter)
            {
                buffer.append(",");
            }
        }
        
        buffer.append(");");
        
        return buffer.toString();
    }
    
    public static void main(String[] args)
    {
        LinkedList<SqlMapping> list = new LinkedList<>();
        list.add(new SqlMapping("name", QryHelper.FieldType.Date, "google"));
        list.add(new SqlMapping("yaw", QryHelper.FieldType.Date, "ama"));
        list.add(new SqlMapping("micheal", QryHelper.FieldType.Date, "mic"));
        JdbcUtility jdbcUtility = new JdbcUtility();
        String statement = jdbcUtility.insertStatement(list, "student");
        System.out.println(statement);
    }
}
