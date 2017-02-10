/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.jpa;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

/**
 *
 * @author Edwin
 */
public class JPaProvider implements Serializable
{
    private final String oracledefault_driver = "oracle.jdbc.driver.OracleDriver";
    
    private final String url_key = "javax.persistence.jdbc.url";
    private final String password_key = "javax.persistence.jdbc.password";
    private final String driver_key = "javax.persistence.jdbc.driver";
    private final String user_key = "javax.persistence.jdbc.user";
    
    private Map<String,String> properties = new HashMap<>();
    
    private String host = "";
    private int port;
    private String sid;
    
    
    private String username;
    private String userpassword;
    
    private String puName;
    
    
    private EntityManager em = null;
    
    public static EntityManager getEntityOracleManager(String sid, String host, String userame, String password, String puName)
    {
        JPaProvider provider = new JPaProvider();
        provider.sid = sid;
        provider.host = host;
        provider.port = 1521;
        provider.username = userame;
        provider.userpassword = password;
        provider.puName = puName;
        provider.intJpAOracle();
        
        
        return provider.em;
        
    }
    
    private void intJpAOracle()
    {
        properties.put(url_key, "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid);
        properties.put(password_key, userpassword);
        properties.put(driver_key, oracledefault_driver);
        properties.put(user_key, username);
               
        em = Persistence.createEntityManagerFactory(puName, properties).createEntityManager();
    }
}
