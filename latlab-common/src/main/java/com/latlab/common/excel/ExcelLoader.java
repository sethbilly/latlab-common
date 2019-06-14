/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.excel;

/**
 *
 * @author Edwin
 */
public interface ExcelLoader
{

    ExcelWorkBook read(String fileName);

    ExcelWorkBook read(String fileName, String sheetName);
    
}
