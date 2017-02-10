/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.reflection;

/**
 *
 * @author Edwin
 */
public class NewClass
{

    public static void main(String[] args)
    {
        JavaClassFileExplorer explorer = new JavaClassFileExplorer(JavaClassFileExplorer.class);
        explorer.readClassData();

        System.out.println("fdfgff   " + explorer.getJavaClass().getListOfJavaClassFields());
    }

}
