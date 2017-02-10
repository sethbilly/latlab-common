/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.stately.common.utils;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Edwin
 * @param <T>
 */
public class ProcessingMessage<T>
{
    private boolean successfull;
    private Boolean state;
    
    private T result;
    
    public static enum Severity{
        INFO, FATAL, WARN
    }
    private String msg;
    private Severity type;
    List<ProcessingMessage> list = new LinkedList<>();

    public ProcessingMessage(String msg, Severity type)
    {
        this.msg = msg;
        this.type = type;
    }

    public ProcessingMessage()
    {
    }
    

    public void addProcessingMessage(String msg, Severity type)
    {
        ProcessingMessage message = new ProcessingMessage(msg, type);
        list.add(message);
    }

    public void info(String msg)
    {
        ProcessingMessage message = new ProcessingMessage(msg, Severity.INFO);
        list.add(message);
    }
    
    public void error(String msg)
    {
        ProcessingMessage message = new ProcessingMessage(msg, Severity.FATAL);
        list.add(message);
    }
    
    public void addWarn(String msg)
    {
        ProcessingMessage message = new ProcessingMessage(msg, Severity.WARN);
        list.add(message);
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public Severity getType()
    {
        return type;
    }

    public void setType(Severity type)
    {
        this.type = type;
    }

    public List<ProcessingMessage> getList()
    {
        return list;
    }

    public boolean isSuccessfull()
    {
        return successfull;
    }

    public void setSuccessfull(boolean successfull)
    {
        this.successfull = successfull;
        this.state = successfull;
    }

    public Boolean getState()
    {
        return state;
    }

    public T getResult()
    {
        return result;
    }

    public void setResult(T result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "ProcessingMessage{" + "successfull=" + successfull + ", state=" + state + ", result=" + result + ", msg=" + msg + ", type=" + type + ", list=" + list + '}';
    }
    
    
}
