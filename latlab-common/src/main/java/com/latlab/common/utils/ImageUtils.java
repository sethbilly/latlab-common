package com.stately.common.utils;

import java.io.File;

/**
 *
 * @author Ainoo Dauda
 * @company IC Securities Ghana Ltd
 * @email dauda.ainoo@icsecurities.com
 * @date 15 September 2015
 * 
*
 */
public class ImageUtils
{

    private static String DOCUMENT_FOLDER = "folderName";
    private static final String SERVER_ROOT_PATH = System.getProperty("com.sun.aas.instanceRoot") + File.separator + "docroot" + File.separator + DOCUMENT_FOLDER + File.separator;
    private static String RESOURCE_PATH = "http://localhost:8080/" + DOCUMENT_FOLDER + "/";

    public static String getServerRoot(String documentFolder)
    {
        return SERVER_ROOT_PATH.replace(DOCUMENT_FOLDER, documentFolder);
    }

    public static String getResourcePath(String documentFolder)
    {
        return RESOURCE_PATH.replace(DOCUMENT_FOLDER, documentFolder);
    }

//    public 
}
