/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.security;

/**
 *
 * @author Edwin
 */

import java.io.Serializable;

public class LoginInfo
  implements Serializable
{
  private Object extrainfo;
  private String result;

  public LoginInfo()
  {
  }

  public LoginInfo(String result, Object extrainfo)
  {
    this.result = result;
    this.extrainfo = extrainfo;
  }

  public Object getExtrainfo()
  {
    return this.extrainfo;
  }

  public void setExtrainfo(Object extrainfo)
  {
    this.extrainfo = extrainfo;
  }

  public String getResult()
  {
    return this.result;
  }

  public void setResult(String result)
  {
    this.result = result;
  }

  public String toString()
  {
    StringBuffer buf = new StringBuffer(1000);

    buf.append(getExtrainfo()); buf.append(" ");
    buf.append(getResult()); buf.append(" ");

    return buf.toString();
  }
}