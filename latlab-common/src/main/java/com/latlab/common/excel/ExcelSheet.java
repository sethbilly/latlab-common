/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 */
public class ExcelSheet 
{
    private String sheetName;
    
    private int columnsCount;
    private int rowsCount;
    
    private List<Object[]> dataList = new LinkedList<>();
    
    private List<Object> dataRowsList = null;
    
    public void createNewRows()
    {
        dataRowsList = new LinkedList<>();
    }
    
    public void addRowData(Object rowObject)
    {
//        System.out.println(rowObject);
        dataRowsList.add(rowObject);
    }
    
    public void completeRows()
    {
        Object[] rows = dataRowsList.toArray();
        
        dataList.add(rows);
    }

    public List<Object[]> getDataList() 
    {
        return dataList;
    }

    public void setDataList(List<Object[]> dataList) 
    {
        this.dataList = dataList;
    }
    
    
    public int getMaximumCol()
    {
        int max = 0;
        
        for (Object[] objects : dataList)
        {
            max = Math.max(max, objects.length);
        }
        
        return max;
    }

    public String getSheetName()
    {
        return sheetName;
    }

    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }

//    public int getColumnsCount()
//    {
//        return columnsCount;
//    }

//    public void setColumnsCount(int columnsCount)
//    {
//        this.columnsCount = columnsCount;
//    }

    public int getRowsCount()
    {
        return dataList.size();
//        return rowsCount;
    }

//    public void setRowsCount(int rowsCount)
//    {
//        this.rowsCount = rowsCount;
//    }
    
    
    
}
