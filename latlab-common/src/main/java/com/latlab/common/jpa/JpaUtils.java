/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.jpa;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Edwin
 */
public class JpaUtils implements Serializable
{
    
    public static String hsqldb = "org.hsqldb.jdbc.JDBCDriver";
    public static String mysql = "com.mysql.jdbc.Driver";
    
    public static String javax_persistence_provider = "javax.persistence.provider";
    
    private static final Logger LOGGER = Logger.getLogger(JpaUtils.class.getName());
    
    public static Properties hibernateLocalMysqlProperties( String username, String password, String connectionUrl)
    {
         Properties datasourceParams = new Properties();
        datasourceParams.put("javax.persistence.provider", "org.hibernate.ejb.HibernatePersistence");
        datasourceParams.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
        datasourceParams.put("hibernate.connection.username",username);
        datasourceParams.put("hibernate.connection.password", password);
        datasourceParams.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
        datasourceParams.put("hibernate.connection.url", connectionUrl);
        datasourceParams.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        datasourceParams.put("hibernate.hbm2ddl.auto", "update");
        
        return datasourceParams;
    }
    
    
    public static Properties localMysqlProperties( String username, String password, String connectionUrl)
    {
         Properties datasourceParams = new Properties();
        datasourceParams.put("javax.persistence.provider", "org.hibernate.ejb.HibernatePersistence");
        datasourceParams.put("javax.persistence.transactionType", "RESOURCE_LOCAL");
        datasourceParams.put("javax.persistence.jdbc.user",username);
        datasourceParams.put("javax.persistence.jdbc.password", password);
        datasourceParams.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
        datasourceParams.put("javax.persistence.jdbc.url", connectionUrl);
        datasourceParams.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        datasourceParams.put("hibernate.hbm2ddl.auto", "update");
        
        return datasourceParams;
    }
    
    
    public static String getInQryString(String[] objectIds)
    {
        String inQry = "";
        
        for (int i = 0; i < objectIds.length; i++)
        {
            inQry += "'"+objectIds[i]+"'";
            
            if(i != (objectIds.length -1))
            {
                inQry += ",";
            }
            
        }
        return "("+inQry+")";
    }
    
    public static String getInQryString(List<String> list)
    {
        String ids[] = new String[list.size()];
        int counter = 0;
        for (String string : list)
        {
            ids[counter] = string;
            counter++;
        }
        
        return getInQryString(ids);
    }
    
    public static Object getIdValue(Object object)
    {
        try 
        {
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field f : fields) 
            {
                if(f.isAnnotationPresent(Id.class))
                {
                    Object val = BeanUtils.getProperty(object, f.getName());
                    return val;
                }
                else if(f.isAnnotationPresent(EmbeddedId.class))
                {
                    Object val = BeanUtils.getProperty(object, f.getName());
                    return val;
                }
            }
        } catch (Exception e) 
        {
            LOGGER.log(Level.SEVERE, "Error Retrieving Persistence Identity Annotation", e);
        }
        
        return null;
    }
    
}
