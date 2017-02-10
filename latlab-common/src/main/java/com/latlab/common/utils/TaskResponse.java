/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.common.utils;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class TaskResponse implements Serializable {
    
    private boolean successfull;
    private String response;
    private List<String> responseList = new LinkedList<>();
    
    public enum Severity{
        INFO, FATAL, WARN
    }

    public boolean isSuccessfull() {
        return successfull;
    }

    public void setSuccessfull(boolean successfull) {
        this.successfull = successfull;
    }

    public String getResponse() 
    {
        response = "";
        
        for (String resp : responseList)
        {
            response += resp + "\n";
        }
        
        return response;
    }

    public void addResponse(String response)
    {
        responseList.add(response);
    }
    
    public void addErrorResponse(String response)
    {
        
    }
    
    public void resetResponse()
    {
        responseList.clear();
    }
            
    public List<String> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<String> responseList) {
        this.responseList = responseList;
    }
    
}
