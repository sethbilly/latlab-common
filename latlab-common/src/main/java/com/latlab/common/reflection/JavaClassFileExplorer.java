/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.reflection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import javax.persistence.EmbeddedId;
import javax.persistence.Id;

public class JavaClassFileExplorer {

    File javaEntityFile;

    private JavaClass javaClass = new JavaClass();

    String className;
    String fullClassName;

    String packageName;

    StringBuffer sbReadLines = new StringBuffer();

    ArrayList<String> javaFileLinesOfCode = new ArrayList<String>();
    ArrayList<String> fieldLineCollections = new ArrayList<String>();

    FileReader fileReader;
    BufferedReader bufferedReader;

    File classRootFolder = null;
    File baseClassFolder = null;

    Method reflectedJavaClassMethods[] = null;

    URL baseClassFolderURL;
    URL[] classPathUrls;

    public static URLClassLoader uRLClassLoader;
    private Class loadClass = null;

    String classToLoadString;

    public static String classedPackage = null;

    int numberOfFields;

    public JavaClassFileExplorer() {

    }

    public JavaClassFileExplorer(Class javaClass) {
        loadClass = javaClass;
    }

    public JavaClassFileExplorer(File javaEntityFile) {
        this.javaEntityFile = javaEntityFile;

        if (javaEntityFile.getName().toLowerCase().endsWith(".class")) {
            try {
                processClassFiles();
            } catch (Exception e) {
            }
        }
    }

    private void processClassFiles() {
        int indexOfDot = javaEntityFile.getName().indexOf(".");
        className = javaEntityFile.getName().substring(0, indexOfDot);

        if (classedPackage == null) {
            getp(javaEntityFile.getParentFile());
        }

        classToLoadString = classedPackage + "." + className;

        try {

            loadClass = uRLClassLoader.loadClass(classToLoadString);

            readClassData();

        } catch (Exception ex) {

            ex.printStackTrace();

//            System.exit(0);
        }
    }

    public void readClassData() {
        className = loadClass.getSimpleName();
        packageName = loadClass.getPackage().getName();

        javaClass.setClassName(className);
        javaClass.setClassPackageName(packageName);

        try {
            try {
                System.out.println("processing class ... " + loadClass);
                reflectedJavaClassMethods = loadClass.getDeclaredMethods();
            } catch (Exception exp) {
                exp.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (reflectedJavaClassMethods != null) {
            for (Method method : reflectedJavaClassMethods) {
                JavaMethod javaClassMethod = new JavaMethod();

                String methodReturnTypeName = method.getReturnType().getSimpleName();
                String methodName = method.getName();

                if (methodName.startsWith("set")) {
                    javaClassMethod.setIsSetterMethod(true);
                    javaClassMethod.setMethodName(methodName);
                    javaClassMethod.setMethodReturnType(methodReturnTypeName);

                    javaClass.getListOfJavaMethods().add(javaClassMethod);
                } else if (methodName.startsWith("get")) {
                    javaClassMethod.setIsGetterMethod(true);
                    javaClassMethod.setMethodName(methodName);
                    javaClassMethod.setMethodReturnType(methodReturnTypeName);

                    javaClass.getListOfJavaMethods().add(javaClassMethod);
                }
            }
        }

        Field javaFields[] = loadClass.getDeclaredFields();
        for (Field field : javaFields) {

            String fieldName = field.getName();

            if (fieldName.equalsIgnoreCase("serialVersionUID")) {
                continue;
            }

            String fieldReturnType = field.getType().getSimpleName();

            JavaClassField javaClassField = new JavaClassField(fieldName, fieldReturnType);

            if (field.isAnnotationPresent(EmbeddedId.class)) {
                javaClassField.setIsAnnotated(true);
                javaClassField.setIsAnnotatedAs_PK(true);

                javaClass.setEntityClassPK(fieldName);
                javaClass.setEntityClassPKDataType(fieldReturnType);
            }

            if (field.isAnnotationPresent(Id.class)) {
                javaClassField.setIsAnnotated(true);
                javaClassField.setIsAnnotatedAs_PK(true);

                javaClass.setEntityClassPK(fieldName);
                javaClass.setEntityClassPKDataType(fieldReturnType);
            }

            javaClass.getListOfJavaClassFields().add(javaClassField);
        }

    }

    public File getJavaEntityFile() {
        return javaEntityFile;
    }

    public JavaClass getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(JavaClass javaClass) {
        this.javaClass = javaClass;
    }

    private void getp(File folderSearch) {
        baseClassFolder = folderSearch.getParentFile();

        if (classedPackage == null) {
            classedPackage = folderSearch.getName();
        } else {
            classedPackage = folderSearch.getName() + "." + classedPackage;
        }

        try {
            baseClassFolderURL = baseClassFolder.toURI().toURL();

            classPathUrls = new URL[]{baseClassFolderURL};

            fullClassName = classedPackage + "." + className;

            uRLClassLoader = new URLClassLoader(classPathUrls);
            Thread.currentThread().setContextClassLoader(uRLClassLoader);

            try {
                loadClass = uRLClassLoader.loadClass(fullClassName);

            } catch (NoClassDefFoundError e) {
                getp(baseClassFolder);
            } catch (Exception e) {

                getp(baseClassFolder);
            }
        } catch (MalformedURLException ex) {
        }

    }
}
