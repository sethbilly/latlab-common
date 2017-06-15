/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.model;

/**
 *
 * @author Billy
 */
public class RandomNumbers {
    
    public static String generateRandomNumber()
    {
        try {
            return randomstring();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    private  static String randomstring(int lo, int hi)
    {
        int n = rand(lo, hi);
        byte b[] = new byte[n];
        for (int i = 0; i < n; i++)
        {
            b[i] = (byte) rand('0', '9');
        }
        return new String(b, 0);
    }

    private static int rand(int lo, int hi)
    {
        java.util.Random rn = new java.util.Random();
        int n = hi - lo + 1;
        int i = rn.nextInt(n);
        if (i < 0)
        {
            i = -i;
        }
        return lo + i;
    }

    private static String randomstring()
    {
        return randomstring(9, 9);
    }
}
