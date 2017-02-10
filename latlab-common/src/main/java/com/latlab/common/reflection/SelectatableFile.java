/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.reflection;

import java.io.File;

/**
 *
 * @author Edwin
 */
public class SelectatableFile
{
    private File javaFile;
    private String fileName;
    private boolean selected = false;


    public SelectatableFile(File javaFile)
    {
        this.javaFile = javaFile;
    }
    

    public File getJavaFile()
    {
        return javaFile;
    }

    public void setJavaFile(File javaFile)
    {
        this.javaFile = javaFile;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public String getFileName()
    {
        fileName = javaFile.getName();
        return fileName;
    }

    @Override
    public String toString()
    {
        return getFileName() + ":" + isSelected();
    }




}
