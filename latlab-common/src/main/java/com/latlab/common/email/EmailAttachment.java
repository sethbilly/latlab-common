/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.email;

import java.io.File;

public class EmailAttachment 
{
    private String name;
    private File file;

    public EmailAttachment(String name, File file)
    {
        this.name = name;
        this.file = file;
    }
    

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public File getFile()
    {
        return file;
    }

    public void setFile(File file)
    {
        this.file = file;
    }
    
    

}
