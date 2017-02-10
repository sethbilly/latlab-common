/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.latlab.common.constants;

public enum ActiveInactiveStatus implements EnumCommon
{
    ACTIVE, INACTIVE;

    public String getFullString()
    {
        return getClass().getCanonicalName()+"."+name();
    }
}
