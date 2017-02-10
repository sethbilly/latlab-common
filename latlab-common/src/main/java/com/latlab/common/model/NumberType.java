/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.model;

import com.latlab.common.constants.EnumResolvable;

public enum NumberType implements EnumResolvable {

    DOUBLE("active", "Active"),
    PERCENT("active", "Active"),
    INTEGER("inactive", "Inactive");

    private String code;
    private String label;

    private NumberType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

}
