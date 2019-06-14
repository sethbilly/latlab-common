/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.reflection;

import com.latlab.common.dateutils.LunarUtils;

public class JavaFieldUIControl {

//    private String
    private JavaClassField javaClassField;

    private boolean addToCodeGeneration;

    private String inputAs;

    private JavaClass lookupSource;

    private JavaClass owingJavaClass;

    public JavaFieldUIControl(JavaClassField javaClassField) {
        this.javaClassField = javaClassField;
    }

    public boolean isAddToCodeGeneration() {
        return addToCodeGeneration;
    }

    public void setAddToCodeGeneration(boolean addToCodeGeneration) {
        this.addToCodeGeneration = addToCodeGeneration;
    }

    public JavaClassField getJavaClassField() {
        return javaClassField;
    }

    public void setJavaClassField(JavaClassField javaClassField) {
        this.javaClassField = javaClassField;
    }

    public String getInputAs() {
        return inputAs;
    }

    public void setInputAs(String inputAs) {
        this.inputAs = inputAs;
    }

    public JavaClass getLookupSource() {
        return lookupSource;
    }

    public void setLookupSource(JavaClass lookupSource) {
        this.lookupSource = lookupSource;
    }

    public JavaClass getOwingJavaClass() {
        return owingJavaClass;
    }

    public void setOwingJavaClass(JavaClass owingJavaClass) {
        this.owingJavaClass = owingJavaClass;
    }

    public String getCodeForDataGetter() {
        String str = "null";

        String fieldName = LunarUtils.getCamelCaseClassName(javaClassField.getFieldName());

        if (getInputAs().equalsIgnoreCase("TextField")) {
            str = "tf" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                    + ".getText().trim()";
        } else if (getInputAs().equalsIgnoreCase("TextArea")) {
            str = "ta" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                    + ".getText().trim()";
        } else if (getInputAs().equalsIgnoreCase("ComboBox")) {
            if (lookupSource != null) {
                String lookUpClassName = lookupSource.getClassName();

                if (lookupSource.getClassName().equalsIgnoreCase("String")) {
                    str = "cb" + fieldName + ".getSelectedItem().toString()";
                } else {
                    str = "selected" + lookUpClassName + "Id";
                }
            }
        } else if (getInputAs().equalsIgnoreCase("Date")) {

//            str = "cb"+LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + "" +
//                    ".getSelectedObject()";
            str = "cal" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                    + ".getDate()";
        } else if (getInputAs() == null) {
            str = "tf" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                    + ".getText().trim()";
        }

        return str;
    }

    public String getCodeForDataSetter() {
        String str = "null";

        String owingJavaClassName = owingJavaClass.getClassName();
//        if(MainFrame.selectedJavaPlatform == 0)
//                    owingJavaClassName = owingJavaClassName + MainFrame.detailSufix;

        if (getInputAs().equalsIgnoreCase("TextField")) {
            str = "tf" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                    + ".setText(selected" + owingJavaClassName + "." + javaClassField.getGetterMethod() + "())";
        } else if (getInputAs().equalsIgnoreCase("TextArea")) {
            str = "ta" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                    + ".setText(selected" + owingJavaClassName + "." + javaClassField.getGetterMethod() + "())";
        } else if (getInputAs().equalsIgnoreCase("Combobox")) {
            String lookUpClassName = lookupSource.getClassName();

            if (lookupSource.getClassName().equalsIgnoreCase("String")) {
                str = "cb" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                        + ".setSelectedItem(selected" + owingJavaClassName + "." + javaClassField.getGetterMethod() + "())";
            } else {
                str = "cb" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                        + ".setSelectedItem(selected" + lookUpClassName + ")";
            }
        } else if (getInputAs().equalsIgnoreCase("Date")) {
            str = "cal" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                    + ".setDate(selected" + owingJavaClassName + "." + javaClassField.getGetterMethod() + "())";
        } else if (getInputAs() == null) {
            str = "tf" + LunarUtils.getCamelCaseClassName(javaClassField.getFieldName()) + ""
                    + ".getText().trim()";
        }

        return str;
    }
}
