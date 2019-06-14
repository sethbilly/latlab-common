/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

import com.stately.common.utils.StringUtil;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ExcelWorkBook 
{
    private String fileName;
    
    private List<ExcelSheet> excelSheetsList = new LinkedList<>();
    
    public void addExcelSheet(ExcelSheet excelSheet)
    {
        excelSheetsList.add(excelSheet);
    }

    public List<ExcelSheet> getExcelSheetsList() 
    {
        return excelSheetsList;
    }
    
    
    public List<Object[]> getWorkBookData()
    {
        
        List<Object[]> list = new LinkedList<>();
        
        for (ExcelSheet excelSheet : excelSheetsList)
        {
            list.addAll(excelSheet.getDataList());
        }
        
        
        
        return list;
    }
    
    
    public void printDetails()
    {
        for (ExcelSheet excelSheet : excelSheetsList)
        {
            StringUtil.printObjectListArray(excelSheet.getDataList());
        }
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    
}
