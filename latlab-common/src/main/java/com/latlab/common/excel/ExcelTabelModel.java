/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

import java.util.LinkedList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * @author Edwin
 */
public class ExcelTabelModel extends AbstractTableModel
{

    private List<Object[]> data =  new LinkedList<>();
    
    private ExcelSheet excelSheet = null;
    
    @Override
    public int getRowCount()
    {
        if(data != null)
            return data.size();
        
        return 0;
    }

    @Override
    public int getColumnCount()
    {
        
        return excelSheet.getMaximumCol();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        try
        {
            return data.get(rowIndex)[columnIndex];
        } catch (Exception e)
        {
//            e.printStackTrace();
        }
        
        return null;
    }

    public ExcelSheet getExcelSheet()
    {
        return excelSheet;
    }

    public void setExcelSheet(ExcelSheet excelSheet)
    {
        this.excelSheet = excelSheet;
        
//        data = excelSheet.getDataList();
//        System.out.println(excelSheet.getDataList());
//        fireTableDataChanged();
    }
    
    public void add(List<Object[]> newData)
    {
        this.data.addAll(newData);
        System.out.println("number of data in sheet = "+newData.size());
        fireTableStructureChanged();
        
//        fireTableDataChanged();
    }
    
}
