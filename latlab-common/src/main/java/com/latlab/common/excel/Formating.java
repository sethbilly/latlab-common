/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;

/**
 *
 * @author Edwin
 */
public class Formating
{
    
    private static final NumberFormat decimal = new  NumberFormat("###,##0.00");
    private static final NumberFormat number = new  NumberFormat("###,##0");
    
//    private static final WritableFont font = new WritableFont(WritableFont.ARIAL, 23, BoldStyle.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLUE);
//    private static final WritableFont font2 = new WritableFont(WritableFont.FontName, 23, WritableFont.BoldStyle.BOLD, false, WritableFont.UnderlineStyle.NO_UNDERLINE, Colour.DARK_BLUE);
    
    public static WritableCellFormat DECIMAL_FORMAT = new WritableCellFormat(decimal);
     public static WritableCellFormat NUMBER_FORMAT = new WritableCellFormat(number);
     public static WritableCellFormat DECIMAL_FORMAT_4_PRE = new WritableCellFormat(new NumberFormat("###,##0.0000"));
    
    
    static {
        
//        new WritableFont
        
    }
    
    public static Integer getInt(Object object)
    {
        try {
            String val = object.toString().trim().replaceFirst("\\.0", "");
            return Integer.parseInt(val);
        } catch (Exception e) {
        }
        
        return null;
        
        
    }
    
    private static WritableCellFormat cellHeaderTemplate(Colour backgroundColor, Colour foreColor, UnderlineStyle un, int fontSize)
    {
        WritableFont cellFont = new WritableFont(WritableFont.ARIAL, fontSize, WritableFont.BOLD, false, un, foreColor);
        WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
        try 
        {
            if(backgroundColor != null)
            {
                cellFormat.setBackground(backgroundColor);
            }
        } catch (Exception e)
        {
            System.err.println("Error in setting background color");
        }
        
        return cellFormat;
    }
    
    public static WritableCellFormat defaultHeader()
    {
        return cellHeaderTemplate(Colour.ORANGE, Colour.WHITE, UnderlineStyle.NO_UNDERLINE, 13);
    }
    
    public static WritableCellFormat headerBold()
    {
        return cellHeaderTemplate(null, Colour.BLACK, UnderlineStyle.NO_UNDERLINE, 12);
    }
    
    public static WritableCellFormat customHeader(Colour backgroundColor, Colour foreColor, UnderlineStyle un, int fontSize)
    {
        return cellHeaderTemplate(backgroundColor, foreColor, un, fontSize);
    }
    
}
