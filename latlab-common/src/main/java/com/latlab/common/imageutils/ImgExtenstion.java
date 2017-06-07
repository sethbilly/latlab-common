/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.imageutils;

/**
 *
 * @author Edwin
 */
public enum ImgExtenstion {

    JPG("jpg");

    private ImgExtenstion(String ext)
    {
        this.ext = ext;
    }
     
    private String ext;

    public String getExt() {
        return ext;
    }

    

}
