/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.constants;

/**
 *
 * @author Edwin
 */
public enum EducationalLevel implements EnumResolvable {
    NONE("None", "NONE"),
    FIRST_CYCLE("Basic", "FIRST_CYCLE"),
    SECOND_CYCLE("Secondary", "SECOND_CYCLE"),
    TERTIARY("Tetiary", "TERTIARY");

    private String label, code;

    private EducationalLevel(String label, String code) {
        this.label = label;
        this.code = code;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return label;
    }

}
