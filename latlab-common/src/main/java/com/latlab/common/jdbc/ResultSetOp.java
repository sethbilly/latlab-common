/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.modules.jdbc;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ResultSetOp
{
     public static List<Object[]> result(ResultSet resultSet)
    {
        
        if(resultSet == null) {
            return Collections.EMPTY_LIST;
        }
        
        try
        {
            List<Object[]> result = new LinkedList<>();
            
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            
            int numberOfColumns = resultSetMetaData.getColumnCount();
           
            
            while(resultSet.next())
            {
                Object[] data = new Object[numberOfColumns];
                
                for(int x = 1; x <= numberOfColumns; x++)
                {
                    data[x -1] = resultSet.getObject(x);
                    
//                    System.out.println("object .. " + data[x -1]);
                }
                
                result.add(data);
            }
            
//            System.out.println(" \n\n\n\n ... ");
            
            return result;
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
        return Collections.EMPTY_LIST;
    }
     
     
       public static Object[] resultSingle(ResultSet resultSet)
    {
        
        if(resultSet == null)
            return null;
        
        try
        {
            
            
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            
            int numberOfColumns = resultSetMetaData.getColumnCount();
            
            Object[] result = new Object[numberOfColumns];
           
            
            resultSet.next();
            
                Object[] data = new Object[numberOfColumns];
                
                for(int x = 1; x <= numberOfColumns; x++)
                {
                    data[x -1] = resultSet.getObject(x);
                    
//                    System.out.println("object .. " + data[x -1]);
                }
                
                
                
//            System.out.println(" \n\n\n\n ... ");
            
            return result;
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
        return null;
    }
     
}
