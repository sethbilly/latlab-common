/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

import com.google.common.base.Strings;
import com.latlab.common.dateutils.DateRange;
import com.latlab.common.formating.ObjectValue;
import com.latlab.common.dateutils.LunarUtils;
import com.latlab.common.reflection.ClassInfo;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Workbook;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Edwin
 */
public class ExcelExporter implements Serializable
{
    private static final Logger logger = Logger.getLogger(ExcelExporter.class.getName());
    
    private File exportFile;
    private List<String> fieldsList = new LinkedList<>();
    private List<String> exclusingList = new LinkedList<>();
    
    private WritableWorkbook workbook = null;
    private  String filePath;
    public static String TEMP_DIR = System.getProperty("java.io.tmpdir");
    public static String USER_HOME = System.getProperty("user.home");
    
    private boolean formatHeader = true;
    private String[] sheetHeader;
    
    private final String DEFAULT_FILE_NAME = "DataExportFile";
    
    private boolean finalised;
    
    private Inclusiveness inclusiveness = Inclusiveness.EXCLUDE;
    
    
    public static enum  Inclusiveness{
        INCLUDE, EXCLUDE
    }
    
    public ExcelExporter(String filePath)
    {
        init(filePath, DEFAULT_FILE_NAME, formatHeader);
//        this.filePath = filePath;
//        exportFile = new File(filePath + "data_file.xls");
//        
//        try 
//        {
//            workbook = Workbook.createWorkbook(exportFile);
//        } catch (IOException e)
//        {
//            logger.log(Level.INFO, "error in initialising excel export .. {0}", e.getMessage());
//        }
    }
    
    
    
    public ExcelExporter(String filePath, boolean formatHeader)
    {
        init(filePath, DEFAULT_FILE_NAME, formatHeader);
    }
    
    public ExcelExporter(String filePath, String fileName, boolean formatHeader)
    {
        init(filePath, fileName, formatHeader);
    }
    
    
    private void init(String fileFolder, String fileName, boolean formatHeader)
    {
        this.filePath = fileFolder;
        this.formatHeader = formatHeader;
        
        if(!fileName.endsWith(".xls"))
        {
            fileName += ".xls";
        }
        
        try
        {
            File directory = new File(fileFolder);
            if(directory.exists() == false)
            {
                directory.mkdirs();
//                System.out.println("    directory created : " + directory);
            }
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }
                
        exportFile = new File(fileFolder + File.separator + fileName);
        
        try 
        {
//            System.out.println("Will be written to location: " + (filePath + fileName));
            System.out.println("Will be written to location: " + exportFile.getAbsolutePath());
            workbook = Workbook.createWorkbook(exportFile);
            System.out.println("written to : " + exportFile.getAbsolutePath());
        } 
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            logger.log(Level.SEVERE, "could not create excel work book at location: {0}", exportFile);
        }
    }
    
    private void writeSheetHeader(WritableSheet sheet, List<Method> methods)
    {
        int col = 0;
        String fieldName;
        for (Method m : methods) {
            
            if(m.getReturnType() == boolean.class)
            {
                fieldName = LunarUtils.getVariableNameForDiplay(m.getName().substring(2));
            }
            else
            {
                fieldName = LunarUtils.getVariableNameForDiplay(m.getName().substring(3));
            }
            
            try
            {
                if(formatHeader)
                {
                    sheet.addCell(new Label(col++, 0, fieldName, Formating.defaultHeader()));
                }
                else
                {
                    sheet.addCell(new Label(col++, 0, fieldName, Formating.headerBold()));
                }
            } 
            catch (WriteException writeException)
            {
                logger.log(Level.SEVERE, "Could not write header details");
            }
        }
    }
    
    public void writeSheetHeader(WritableSheet sheet)
    {
        int col = 0;
        for (String m : getSheetHeader())
        {
            try
            {
                if(formatHeader)
                {
                    sheet.addCell(new Label(col++, 0, m, Formating.defaultHeader()));
                }
                else
                {
                    sheet.addCell(new Label(col++, 0, m, Formating.headerBold()));
                }
            } 
            catch (WriteException writeException)
            {
                logger.log(Level.SEVERE, "Could not write header details");
            }
        }
    }

    public InputStream getFileStream()
    {
        try 
        {
            return FileUtils.openInputStream(exportFile);
        } catch (IOException e)
        {
            logger.log(Level.SEVERE, "error converting file to inputstream", e);
        }
        return null;
    }
    
    public void add(String... inclustion)
    {
        fieldsList.addAll(Arrays.asList(inclustion));
    }
    
    public void addExclusion(String... exclusion)
    {
        exclusingList.addAll(Arrays.asList(exclusion));
    }
    
    public void add(String inclustion)
    {
        fieldsList.add(inclustion);
    }
    
    private List<Method> filterMethods(List<Method> methods, Class t)
    {
        List<String> includeList = excludeOrIncludeList(t);
        
        if(includeList.isEmpty())
        {
            return methods;
        }
        
        List<Method> newMethods = new LinkedList<>();
        
        for(String field : includeList)
        {
            for (Method method : methods)
            {
                String methodName = method.getName().toLowerCase();
                if(methodName.startsWith("set"))
                {
                    continue;
                }
                
                if(method.getReturnType() == boolean.class)
                {
                    methodName = methodName.substring(2);
                }
                else
                {
                    methodName = methodName.substring(3);
                }
                
                if(field.toLowerCase().equals(methodName))
                {
                    newMethods.add(method);
                }
            }
        }
        return newMethods;
    }
    
    
    
    
    private List<String> excludeOrIncludeList(Class<?> type)
    {
        List<String> newFields = new LinkedList<>();
        List<Field> fields = ClassInfo.getInheritedFields(type);
        
        if(inclusiveness == Inclusiveness.INCLUDE && !fieldsList.isEmpty())
        {
            return fieldsList;
        }
        if(inclusiveness == null)
        {
            return Collections.EMPTY_LIST;
        }
        if(fieldsList.isEmpty())
        {
            for (Field field : fields) 
            {
                newFields.add(field.getName());
            }
            
            return newFields;
        }
        
        for (Field field : fields) 
        {
            for(String f : fieldsList)
            {
                if(f.equalsIgnoreCase(field.getName()))
                {
                    continue;
                }
                newFields.add(field.getName());
            }
        }
        System.out.println(newFields);
        return newFields;
    }
    
    public ExcelExporter addSheetData(List<?> data, Class<?> type, boolean include)
    {
        return addSheetData(data, type, type.getSimpleName(), include);
    }
    
    public ExcelExporter addSheetData(List<?> data, Class<?> type, String sheetName, boolean include)
    {
        if(Strings.isNullOrEmpty(sheetName))
        {
            sheetName = "Sheet-" +UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);
        }
        
        if(include)
        {
            inclusiveness = Inclusiveness.INCLUDE;
        }
        else
        {
            inclusiveness = Inclusiveness.EXCLUDE;
        }
        
        List<Method> methods = filterMethods(ClassInfo.getInheritedMethods(type), type);
        WritableSheet sheet = workbook.createSheet(sheetName, 1);
        
        if(getSheetHeader() == null || getSheetHeader().length == 0)
        {
            writeSheetHeader(sheet, methods);
        }
        else
        {
            writeSheetHeader(sheet);
        }
        
        if(data.isEmpty())
        {
            logger.info("There's no data to populate this excel data");
            return this;
        }
        
        int row = 1;
        System.out.println("data list ==== " + data.size());
        for (Object t : data) 
        {
            int col = -1;
            for(Method m : methods)
            {
                col++;
                try 
                {
                    Object objVal = m.invoke(t);
                    
                    if(objVal == null)
                    {
                        continue;
                    }
                    if(m.getReturnType() == String.class)
                    {
                        sheet.addCell(new Label(col, row, (String)objVal + ""));
                    }
                    else if(m.getReturnType() == Integer.class )
                    {
                        sheet.addCell(new jxl.write.Number(col, row, ObjectValue.getIntegerValue(objVal), Formating.NUMBER_FORMAT));                        
                    }
                    else if(m.getReturnType() == int.class)
                    {
                        sheet.addCell(new jxl.write.Number(col, row, (int)objVal, Formating.NUMBER_FORMAT));                        
                    }
                    else if(m.getReturnType() == Double.class )
                    {
                        sheet.addCell(new jxl.write.Number(col, row, new BigDecimal(String.valueOf(objVal)).doubleValue(), Formating.DECIMAL_FORMAT));
                    }
                    else if(m.getReturnType() == BigDecimal.class )
                    {
                        sheet.addCell(new jxl.write.Number(col, row, ObjectValue.getDoubleValue(objVal), Formating.DECIMAL_FORMAT));
                    }
                    else if(m.getReturnType() == double.class)
                    {
                        sheet.addCell(new jxl.write.Number(col, row, (double)objVal, Formating.DECIMAL_FORMAT));
                    }
                    else if(m.getReturnType() == Date.class)
                    {
                        sheet.addCell(new DateTime(col, row, (Date)objVal));
                    }
                    else
                    {
                        sheet.addCell(new Label(col, row, objVal.toString()));
                    }
                }
                catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | WriteException e)
                {
                    logger.log(Level.SEVERE, "Error occured in filling an excel column {0}", m.getName());
                }
            }
            row++;
        }
        fieldsList = new LinkedList<>();
        return this;
    }
        
    
    public ExcelExporter addSheetData(List<?> data, Class<?> type, String sheetName, Map<String, String> fieldLabelMap)
    {
        String header[] = new String[fieldLabelMap.size()];
        
        int counter = 0;
        for (Map.Entry<String, String> entry : fieldLabelMap.entrySet())
        {
            String key = entry.getKey();
            String value = entry.getValue();
            
            header[counter] = value;
            fieldsList.add(key);
            
            counter++;
        }
        
//          String fileName = "InvestmentReport";
//        String sheetHeader[]
//                = {
//                    "Name of Scheme", "Date of Investment", "Name of Issuer", "Asset Class", "Asset Tenor",
//                    "Amount Invested", "Expected Return", "Commulative Amount", "Interest Earned"
//                };
//        ExcelExporter excelExporter = new ExcelExporter(ExcelExporter.TEMP_DIR + fileName, true);
        setSheetHeader(header);
//        excelExporter.add("fundName", "transDate", "issuer", "incomeClass", "tenor", "cost", "coupon", "maturityValue", "interest");
        return addSheetData(data, type, true);
       
        
        
    }
        
    
    
    
    public void finalise()
    {
        
        try 
        {
            workbook.write();
            workbook.close();
            finalised = true;
        } 
        catch (IOException | WriteException e) 
        {
            logger.log(Level.SEVERE, "Error closing workbook");
        }
    }

    public File getExportFile() {
        return exportFile;
    }

    public String[] getSheetHeader() {
        return sheetHeader;
    }

    public void setSheetHeader(String[] sheetHeader) {
        this.sheetHeader = sheetHeader;
    }

    public boolean isFinalised()
    {
        return finalised;
    }
    
    
    
    public static void main(String[] args)
    {
        DateRange dateRange1 = new DateRange(new Date(), new Date());
        DateRange dateRange2 = new DateRange(new Date(), new Date());
        DateRange dateRange3 = new DateRange(new Date(), new Date());
        
        List<DateRange> dateRangesList = Arrays.asList(dateRange1,dateRange2, dateRange3);
        
        Map<String,String> mapsList = new LinkedHashMap<>();
        mapsList.put("fromDate", "Fund Name");
        mapsList.put("toDate", "Transaction Date");
        
         ExcelExporter excelExporter = new ExcelExporter(ExcelExporter.TEMP_DIR + "file.xls", true);
        excelExporter.addSheetData(dateRangesList,  DateRange.class, "file.xls", mapsList);
        excelExporter.finalise();
    }
    
    
}
