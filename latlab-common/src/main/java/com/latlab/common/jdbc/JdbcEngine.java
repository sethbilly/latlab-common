/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jdbc;

import com.stately.modules.jdbc.ResultSetOp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class JdbcEngine
{
    private String driverName;
    private String databaseURL;
    private String qry;
    
    private String username;
    private String userPassword;
    
    public Connection conn;
    
    public ResultSetMetaData resultSetMetaData;
    public ResultSet resultSet;
    public void init()
    {
        System.out.println(" Driver Name : " + driverName);
        System.out.println("Data base URL : " + databaseURL);
        
        try
        {
            Class.forName(driverName);
            
            conn = DriverManager.getConnection(databaseURL);
            
            System.out.println("connection is " + conn);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    public void rowRead(Object[] dataRead)
    {
        
    }

    public ResultSet resultSet(String qry)
    {
        try
        {
            System.out.println("executing qry = " + qry);
            resultSet =  conn.createStatement().executeQuery(qry);
            resultSetMetaData = resultSet.getMetaData();
            
            return resultSet;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Object[]> result(String qry)
    {
        ResultSet rs = resultSet(qry);
        
        return ResultSetOp.result(resultSet);
    }
    
    public String getDatabaseURL()
    {
        return databaseURL;
    }

    public void setDatabaseURL(String databaseURL)
    {
        this.databaseURL = databaseURL;
    }

    public String getDriverName()
    {
        return driverName;
    }

    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
    
    
    
    
    
}
