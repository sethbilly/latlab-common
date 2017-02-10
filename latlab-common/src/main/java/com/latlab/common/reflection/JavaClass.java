/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.reflection;

import java.util.ArrayList;
import java.util.List;

public class JavaClass {

    private String className;
    private String classPackageName;
    private boolean isJavaEntityClass = false;
    private String entityClassPK;
    private String entityClassPKDataType;
    private String getterMethod4PK;

    private List<JavaMethod> listOfJavaMethods = new ArrayList<>();
    private List<JavaClassField> listOfJavaClassFields = new ArrayList<>();

    public JavaClass() {
    }

    public JavaClass(String className) {
        this.className = className;
    }

    public JavaClass(String className, String classPackageName) {
        this.className = className;
        this.classPackageName = classPackageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassFullName() {
        return classPackageName + "." + className;
    }

    public String getClassPackageName() {
        return classPackageName;
    }

    public void setClassPackageName(String classPackageName) {
        this.classPackageName = classPackageName;
    }

    public List<JavaMethod> getListOfJavaMethods() {
        return listOfJavaMethods;
    }

    public void setListOfJavaMethods(List<JavaMethod> listOfJavaMethods) {
        this.listOfJavaMethods = listOfJavaMethods;
    }

    public List<JavaClassField> getListOfJavaClassFields() {
        return listOfJavaClassFields;
    }

    public void setListOfJavaClassFields(List<JavaClassField> listOfJavaClassFields) {
        this.listOfJavaClassFields = listOfJavaClassFields;
    }

    public boolean isIsJavaEntityClass() {
        return isJavaEntityClass;
    }

    public void setIsJavaEntityClass(boolean isJavaEntityClass) {
        this.isJavaEntityClass = isJavaEntityClass;
    }

    public String getEntityClassPK() {
        return entityClassPK;
    }

    public void setEntityClassPK(String entityClassPK) {
        this.entityClassPK = entityClassPK;
    }

    public String getEntityClassPKDataType() {
        return entityClassPKDataType;
    }

    public void setEntityClassPKDataType(String entityClassPKDataType) {
        this.entityClassPKDataType = entityClassPKDataType;
    }

    public String getGetterMethod4PK() {

        for (JavaClassField javaClassField : listOfJavaClassFields) {
            if (javaClassField.isIsAnnotatedAs_PK()) {
                getterMethod4PK = javaClassField.getGetterMethod();
            }
        }

        return getterMethod4PK;
    }

//    public void setGetterMethod4PK(String getterMethod4PK)
//    {
//        this.getterMethod4PK = getterMethod4PK;
//    }
    private List<JavaFieldUIControl> javaFieldUIControl;

    public List<JavaFieldUIControl> getJavaFieldUIControl() {
        return javaFieldUIControl;
    }

    public void setJavaFieldUIControl(List<JavaFieldUIControl> javaFieldUIControl) {
        this.javaFieldUIControl = javaFieldUIControl;
    }

    @Override
    public String toString() {
        String str = "Class Name: " + className + "\n";

        str += "Package Name: " + classPackageName + "\n";

        for (JavaMethod javaMethod : listOfJavaMethods) {
            str += "\t" + javaMethod.getMethodReturnType() + " " + javaMethod.getMethodName() + "\n";
        }

        str = str + "\n";

        for (JavaClassField classField : listOfJavaClassFields) {
            str += "\t" + classField.getFieldDataType() + " " + classField.getFieldName() + "\n";
        }

        str = className;

        return str;
    }

}
