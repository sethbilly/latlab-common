/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

import com.stately.common.utils.StringUtil;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author Edwin
 */
public class ExcelDataLoader_XLSX implements ExcelLoader
{

    @Override
    public ExcelWorkBook read(String fileName)
    {
        System.out.println("reading data");

        File excellFile = new File(fileName);
        ExcelWorkBook excelWorkBook = new ExcelWorkBook();
        excelWorkBook.setFileName(excellFile.getName());

        System.out.println("file to process is " + excellFile);
        try
        {

            Workbook workbook = WorkbookFactory.create(new File(fileName));

//            Workbook workbook = Workbook.getWorkbook(excellFile);
            System.out.println("file loaded. ");

//            Sheet[] excelSheets = workbook.getSheets();
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

//            System.out.println("number of excel sheets is " + excelSheets.length);
//            for (Sheet sheet : excelSheets) {
//                System.out.print("processing sheet " + sheet.getName());
//                ExcelSheet excelSheet = read(sheet);
//                excelWorkBook.addExcelSheet(excelSheet);
//            }
//workbook.gets
            while (sheetIterator.hasNext())
            {
                Sheet sheet = sheetIterator.next();
//            System.out.println("=> " + sheet.getSheetName());

                ExcelSheet excelSheet = read(sheet);
                excelWorkBook.addExcelSheet(excelSheet);
            }

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return excelWorkBook;
    }

    @Override
    public ExcelWorkBook read(String fileName, String sheetName)
    {
        System.out.println("reading data");

        File excellFile = new File(fileName);
        ExcelWorkBook excelWorkBook = new ExcelWorkBook();
        excelWorkBook.setFileName(excellFile.getName());

        System.out.println("file to process is " + excellFile);
        try
        {

            Workbook workbook = WorkbookFactory.create(new File(fileName));

            System.out.println("file loaded. ");

            Sheet sheet = workbook.getSheet(sheetName);

            System.out.print("processing sheet " + sheet.getSheetName());
            ExcelSheet excelSheet = read(sheet);
            excelWorkBook.addExcelSheet(excelSheet);

        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return excelWorkBook;
    }

    public ExcelSheet read(Sheet sheet)
    {
        System.out.println("Processing sheet ..... " + sheet.getSheetName());
        ExcelSheet excelSheet = new ExcelSheet();

        System.out.print("processing sheet " + sheet.getSheetName());
        excelSheet.setSheetName(sheet.getSheetName());
        
        for (Row row : sheet)
        {
            excelSheet.createNewRows();

            for (Cell cell : row)
            {

                if (null == cell.getCellTypeEnum())
                {
                    excelSheet.addRowData(cell.getStringCellValue());
                } 
                else
                {
                    switch (cell.getCellTypeEnum())
                    {
                        case STRING:
                            excelSheet.addRowData(cell.getStringCellValue());
                            break;
                        case BOOLEAN:
                            excelSheet.addRowData(cell.getBooleanCellValue());
                            break;
                        case NUMERIC:
                            if (DateUtil.isCellDateFormatted(cell))
                            {
                                excelSheet.addRowData(cell.getDateCellValue());
                            } else
                            {
                                excelSheet.addRowData(cell.getNumericCellValue());
                            }
                            break;
                        default:
                            excelSheet.addRowData(cell.getStringCellValue());
                            break;
                    }
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
            List<Object[]> list = new ExcelDataLoader_XLSX().read("C:/Users/Edwin/Downloads/Opinamangdraft.xlsx").getWorkBookData();

            StringUtil.printObjectListArray(list);

        } catch (Exception e)
        {
        }
    }
}
