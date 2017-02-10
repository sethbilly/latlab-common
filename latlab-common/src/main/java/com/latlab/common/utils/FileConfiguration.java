/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.common.utils;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author Edwin
 */
public class FileConfiguration
{
    private static final Logger LOG = Logger.getLogger(FileConfiguration.class.getName());
    
    private static final FileConfiguration instance = new FileConfiguration();
    
    private Configuration c = new PropertiesConfiguration();
    
    private String fileName;

    public FileConfiguration()
    {
//        init();
    }
    
    public static FileConfiguration instance()
    {
        return instance;
    }
    
//    public void loadConfig(String fileName)
//    {
//        this.fileName = fileName;
//        try
//        {
//            c = new PropertiesConfiguration(fileName);
//        } catch (Exception e)
//        {
//            LOG.log(Level.SEVERE, "Error in locating/loading Configuration File (" + fileName+")", e);
//            LOG.info("creating new configuration file " + fileName);
//            try
//            {
//                new File(fileName).createNewFile();
//                c = new PropertiesConfiguration(fileName);
//                
//                 LOG.info("New Configuration File Created" + fileName);
//            } catch (Exception ex)
//            {
//                LOG.log(Level.SEVERE, "Error in Creating & loading new  GSE Adaptor Configuration File", e);
//            }
//        }
//        
//        System.out.println("Configuration Items : " + c.toString());
//    }
//    
    public String value(String prop)
    {
        
        return c.getString(prop);
    }
    
    private void init()
    {
        try
            {
                
                LOG.info("about to load configuration");
//                loadConfig();
                
            } catch (Exception e)
            {
                LOG.log(Level.SEVERE, "Error in loading lagtime", e);
            }
        
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    
}
