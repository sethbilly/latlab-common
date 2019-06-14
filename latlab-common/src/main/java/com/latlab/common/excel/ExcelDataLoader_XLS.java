/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

import com.stately.common.utils.StringUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Edwin
 */
public class ExcelDataLoader_XLS implements ExcelLoader {

    @Override
    public ExcelWorkBook read(String fileName) 
    {
        System.out.println("reading data");


        File excellFile = new File(fileName);
        ExcelWorkBook excelWorkBook = new ExcelWorkBook();
        excelWorkBook.setFileName(excellFile.getName());

        System.out.println("file to process is " + excellFile);
        try {

            Workbook workbook = Workbook.getWorkbook(excellFile);

            System.out.println("file loaded. ");

            Sheet[] excelSheets = workbook.getSheets();

            System.out.println("number of excel sheets is " + excelSheets.length);

            for (Sheet sheet : excelSheets) {
                System.out.print("processing sheet " + sheet.getName());
                ExcelSheet excelSheet = read(sheet);
                excelWorkBook.addExcelSheet(excelSheet);
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (BiffException ex) {
            ex.printStackTrace();
        }


        return excelWorkBook;
    }

    @Override
    public  ExcelWorkBook read(String fileName, String sheetName) 
    {
        System.out.println("reading data");


        File excellFile = new File(fileName);
        ExcelWorkBook excelWorkBook = new ExcelWorkBook();
        excelWorkBook.setFileName(excellFile.getName());

        System.out.println("file to process is " + excellFile);
        try {

            Workbook workbook = Workbook.getWorkbook(excellFile);

            System.out.println("file loaded. ");

            

            Sheet sheet = workbook.getSheet(sheetName);
            
            
                System.out.print("processing sheet " + sheet.getName());
                ExcelSheet excelSheet = read(sheet);
                excelWorkBook.addExcelSheet(excelSheet);
                


        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (BiffException ex) {
            ex.printStackTrace();
        }


        return excelWorkBook;
    }

    public ExcelSheet read(Sheet sheet) 
    {
        System.out.println("Processing sheet ..... " + sheet.getName());
        ExcelSheet excelSheet = new ExcelSheet();

        System.out.print("processing sheet " + sheet.getName());
        excelSheet.setSheetName(sheet.getName());

        int numberOfRows = sheet.getRows();
        int numberOfColumns = sheet.getColumns();

        System.out.println(" \t rows=" + numberOfRows + " cols" + numberOfColumns);

        for (int row = 0; row < numberOfRows; row++) {
            excelSheet.createNewRows();

            for (int col = 0; col < numberOfColumns; col++) {
//                        System.out.print("reading cell [row=" + row + ", col=" + col+"]");
                Cell cell = null;
                try {
                    cell = sheet.getCell(col, row);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (cell == null) {
//                            System.out.println("");
                    continue;
                }

//                        System.out.println(" \t " + cell.getContents());



                if (cell.getType() == CellType.LABEL) {
//                            LabelCell lc = (LabelCell) cell;
//                            excelSheet.addRowData(lc.getString());

                    excelSheet.addRowData(cell.getContents());

                } else if (cell.getType() == CellType.NUMBER) {
                    NumberCell numberCell = (NumberCell) cell;
                    excelSheet.addRowData(numberCell.getValue());
//                    System.out.println("value is ....  " + numberCell.getValue());
                } else if (cell.getType() == CellType.DATE) {
                    DateCell dateCell = (DateCell) cell;
                    excelSheet.addRowData(dateCell.getDate());
                } else {
                    excelSheet.addRowData(cell.getContents());
                }

            }

            excelSheet.completeRows();
            
        }
        return excelSheet;
    }
    
    public static void main(String[] args)
    {
        try
        {
            List<Object[]> list = new ExcelDataLoader_XLS().read("C:/Users/Edwin/Downloads/Opinamangdraft.xlsx").getWorkBookData();
            
            StringUtil.printObjectListArray(list);
            
        } catch (Exception e)
        {
        }
    }
}
