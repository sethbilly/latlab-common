/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stately.common.utils;

/**
 *
 * @author Billy
 */
public class HttpConResponse
{
    private int responseCode;
    private String response;

    public int getResponseCode()
    {
        return responseCode;
    }

    public void setResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }

    public String getResponse()
    {
        return response;
    }

    public void setResponse(String response)
    {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "HttpConResponse{" + "responseCode=" + responseCode + ", response=" + response + '}';
    }
    
    
}
