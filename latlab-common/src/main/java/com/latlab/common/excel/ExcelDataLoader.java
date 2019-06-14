/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

import java.io.File;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ExcelDataLoader {

    
    public static ExcelWorkBook read(String fileName) 
    {
        System.out.println("reading data");
        
        File excellFile = new File(fileName);
        ExcelWorkBook excelWorkBook = new ExcelWorkBook();
        excelWorkBook.setFileName(excellFile.getName());

        System.out.println("file to process is " + excellFile);
        try {

            ExcelLoader newInterface;
            if(isXls(fileName))
            {
                System.out.println(".xls detected ....");
                
                 newInterface = new ExcelDataLoader_XLS();
                
                excelWorkBook = newInterface.read(fileName);
                
            }
            else if(isXlsx(fileName))
            {
                System.out.println(".xlsx detected ....");
                 newInterface = new ExcelDataLoader_XLSX();
                
                excelWorkBook = newInterface.read(fileName);
            }
            
            


        } catch (Exception ex) 
        {
            ex.printStackTrace();
        } 
        

        return excelWorkBook;
    }

    
    
    public static boolean isXlsx(String fileName)
    {
        return fileName.toLowerCase().endsWith("xlsx");
    }
    
    public static boolean isXls(String fileName)
    {
        return fileName.toLowerCase().endsWith("xls");
    }

    
    
    public static ExcelWorkBook read(String fileName, String sheetName) 
    {
        System.out.println("reading data");


        File excellFile = new File(fileName);
        ExcelWorkBook excelWorkBook = new ExcelWorkBook();
        excelWorkBook.setFileName(excellFile.getName());

        System.out.println("file to process is " + excellFile);
        try {

            ExcelLoader excelLoader = null;
            
            if(isXls(fileName))
            {
                excelLoader = new ExcelDataLoader_XLS();
            }
            else if(isXls(fileName))
            {
                excelLoader = new ExcelDataLoader_XLSX();
            }
            else
            {
                return excelWorkBook;
            }

//            ExcelSheet excelSheet = excelLoader.read(fileName, sheetName);
            excelWorkBook = excelLoader.read(fileName, sheetName);
            
        } catch (Exception ex) 
        {
            ex.printStackTrace();
        }
        


        return excelWorkBook;
    }
//
    
    public static void main(String[] args)
    {
        try
        {
//            List<Object[]> list = ExcelDataLoader.read("C:/Users/Edwin/Downloads/Opinamangdraft.xls").getWorkBookData();
            ExcelWorkBook workBook = ExcelDataLoader.read("C:/Users/Edwin/Downloads/Opinamangdraft.xls");
//            List<Object[]> list = ExcelDataLoader.read("C:/Users/Edwin/Downloads/Opinamangdraft.xlsx").getWorkBookData();
            
              System.out.println(workBook.getFileName());
              System.out.println(workBook.getExcelSheetsList().size());
              
              List<ExcelSheet> excelSheetsList  =  workBook.getExcelSheetsList();
              
              for (ExcelSheet excelSheet : excelSheetsList)
            {
                System.out.println(excelSheet.getSheetName() 
                        + "  -- " 
                        + excelSheet.getDataList().size()
                + "  -- " + excelSheet.getMaximumCol());
                
                List<Object[]> objectsList = excelSheet.getDataList();
                for (Object[] objects : objectsList)
                {
                    System.out.println(" --- " + objects.length);
                }
                
//                System.exit(0);
            }
    

//            StringUtil.printObjectListArray(list);
            
        } catch (Exception e)
        {
        }
    }
}
