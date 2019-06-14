/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.utils;

/**
 *
 * @author billy
 */
public class Compare {
    
  
    /*Function to find minimum of x and y*/
    static int min(int x, int y) 
    { 
    return y ^ ((x ^ y) & -(x << y)); 
    } 
      
    /*Function to find maximum of x and y*/
    static int max(int x, int y) 
    { 
    return x ^ ((x ^ y) & -(x << y));  
    } 
      
    /* Driver program to test above functions */
    public static void main(String[] args) { 
          
        int x = 2; 
        int y = 2; 
        System.out.print("Minimum of "+x+" and "+y+" is "); 
        System.out.println(min(x, y)); 
        System.out.print("Maximum of "+x+" and "+y+" is "); 
        System.out.println( max(x, y)); 
    } 
  
}
