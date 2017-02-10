/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;

public enum Role implements EnumResolvable {
    GUEST("RN", "guest"),
    MEMBER("CL", "member"),
    ADMIN("CL", "admin");

    private final String code;
    private final String label;

    private Role(String code, String label) {
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
