/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.common.utils;

import java.io.InputStream;

public class FileUtilities
{
  public static String getFileExtension(String fileName)
  {
    String ext = "";

    int lastIndexOfDot = fileName.lastIndexOf(".");

    ext = fileName.substring(lastIndexOfDot);

    return ext;
  }

  public static String getFilenameMinusExtenstion(String fileName)
  {
    String ext = "";

    int lastIndexOfDot = fileName.lastIndexOf(".");

    ext = fileName.substring(lastIndexOfDot);

    ext = fileName.substring(0, fileName.length() - ext.length());

    return ext;
  }
  
  void ddd(String resourceFilePath)
  {
      try
      {
          InputStream inputStream = FileUtilities.class.getResourceAsStream(resourceFilePath);
          
      } catch (Exception e)
      {
      }
  }
  
  
}