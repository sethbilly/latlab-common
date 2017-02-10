/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.formating;

/**
 *
 * @author Edwin
 */
public class HtmlTable
{
    String formatedText = "";
    private String[][] tableData;

    String header = "";

    String data = "";
    private boolean hasHeaders;

    public HtmlTable(String[][] tableData)
    {
        this.tableData = tableData;
    }

    public void prepareTable()
    {

        if(hasHeaders)
        {
            createHeaderRole();
        }
        
        addData();
        formatedText = "<table style='border-width: 1px; border-style: solid'>\n";

        formatedText = formatedText + header;
        formatedText = formatedText + data;


        formatedText = formatedText + "</table>\n";
    }


    public void createHeaderRole()
    {
        header = "<tr>\n";
        
        String[] strings = tableData[0];

        for (int i = 0; i < strings.length; i++)
        {
            header = header + "<td><b>"+strings[i] +"</b></td>\n";

        }

        header = header + "</tr>\n";
    }
    
    public void addData()
    {
        
        data = "";
        
        int startPos = 0;
        
        if(hasHeaders)
        {
            startPos = 1;
        }
        
        
        for (int i = startPos; i < tableData.length; i++)
        {
            String[] strings = tableData[i];

            data = data + "<tr>";
            for (int x = 0; x < strings.length; x++)
            {
                data = data + "<td>" + strings[x] + "</td>";

            }
            
            data = data + "</tr>";

        }
    }

    public String getFormatedText()
    {
        return formatedText;
    }

    public boolean isHasHeaders()
    {
        return hasHeaders;
    }

    public void setHasHeaders(boolean hasHeaders)
    {
        this.hasHeaders = hasHeaders;
    }

}
