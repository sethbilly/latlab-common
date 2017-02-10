/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.formating;

/**
 *
 * @author Bryte
 */
public class Test 
{
    /*
     * how to use the NumberToWords class
     * Create an instance using NumberToWords.getInstance() which assumes default ghana currency names
     * or NumberToWords.getInstance(majorCurrency, minorCurrency) for any other country apart from ghana
     * then use the instance created to call the convertToWords method and parse the value you want to convert
     * 
     * example is show in the main method below
     */
    
    public static void main(String[] args)
    {
        NumberToWords numberToWords = NumberToWords.getInstance();
        String words = numberToWords.convertToWords(32_000_034_344.443);
        System.out.println(words);
        
        
        numberToWords = NumberToWords.getInstance();
        System.out.println(numberToWords.convertToWords(78.43));
        
        
    }
}
