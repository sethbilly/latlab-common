/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.reporting;

public enum ReportOutputFileType {

    PDF("application/pdf"),
    XHTML("text/html"),
    EXCELL("text/excel");

    private ReportOutputFileType(String contentType) {
        this.contentType = contentType;
    }

    private String contentType;

    public String getContentType() {
        return contentType;
    }

}
