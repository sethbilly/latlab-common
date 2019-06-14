/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.latlab.common.reporting;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.lang3.StringUtils;

public class JasperReportManager implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(JasperReportManager.class.getName());

    private final Map<String, Object> defaultParamenters = new HashMap<>();
    private final Map<String, Object> reportParamenters = new HashMap<>();
    private final Map<String, JasperReportManager> reportInstances = new HashMap<>();
    private ReportOutputFileType reportOutputFileType = ReportOutputFileType.PDF;
    private ReportDesignFileType reportFileType;
    private ReportOutputEnvironment reportOutputEnvironment;
    private JasperPrint jasperPrint;
    private JRBeanCollectionDataSource jrCollectionDataSource = null;
    private String jasperFile;
    private String reportOutputDirectory;
    private Collection reportDataList;

    private File generatedFile;

    String msg = "";
    private String reportTitle;
    public static final String TEMP_DIR = System.getProperty("java.io.tmpdir");
    public static final String REPORT_TITLE = "reportTitle";

    public void addMap(Map reportParameters) {
        for (Object key : reportParameters.keySet()) {
            addParam(key.toString(), reportParameters.get(key));
        }
    }

    public void showReport(ReportData reportCreationData) {
        reportParamenters.putAll(reportCreationData.getMap());
        showReport(reportCreationData.getListData(), reportCreationData.getReportFile());
    }

    public void showReport(Collection reportData, InputStream inputStream) {
        this.reportDataList = reportData;
        createJasperPrint(inputStream);
        outputReport();
    }

    public boolean isVariableSet() {

        return true;
    }

    public void showReport(Collection reportData, String jasperFile) {
        this.reportDataList = reportData;
        this.jasperFile = jasperFile;

        createJasperPrint(null);

        outputReport();
    }

    public void showReport(Object reportData, String jasperFile) {
        List dataList = new ArrayList(1);
        dataList.add(reportData);
        showReport(dataList, jasperFile);
    }

    public void showReport(Object reportData, InputStream reportInputStrem) {
        List dataList = new ArrayList(1);
        dataList.add(reportData);
        showReport(dataList, reportInputStrem);
    }

    public void showHtmlReport(Collection reportData, String jasperFile) {
        this.reportDataList = reportData;
        this.jasperFile = jasperFile;

        createJasperPrint(null);

        outputReport();
    }

    private void webenviro() {

        HttpServletResponse response = getServeltResponse();

        response.setContentType("application/pdf");
        HttpServletRequest request = getServeltRequest();
        if (reportOutputFileType == null) {
            LOGGER.info("Report outfile type is not set, PDF will be assumed");
            reportOutputFileType = ReportOutputFileType.PDF;
        }
        try {
            switch (reportOutputFileType) {
                case PDF:
                    JRPdfExporter pdfExporter = new JRPdfExporter();

                    pdfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    pdfExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());

                    pdfExporter.exportReport();

                    break;
                case XHTML:

                    JRHtmlExporter htmlExporter = new JRHtmlExporter();
                    request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
                    htmlExporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "image?image=");

                    htmlExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    htmlExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());

                    htmlExporter.exportReport();

                    break;
            }

        } catch (Exception e) {
            Logger.getLogger(JasperReportManager.class.getName()).log(Level.SEVERE, e.toString());
        }

        try {
            FacesContext.getCurrentInstance().responseComplete();
        } catch (Exception e) {
            Logger.getLogger(JasperReportManager.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }

    }

    private void desktopEnviroment() {

        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
        try {
            reportTitle = (String) reportParamenters.get(REPORT_TITLE);
        } catch (Exception e) {
        }

        jasperViewer.setTitle(reportTitle);
        jasperViewer.setVisible(true);

    }

    public String writeToFile(Collection reportData, InputStream inputStream, String reportDirectory, String fileName) {
        reportDirectory = reportDirectory.trim();

        if (fileName == null || reportDirectory == null) {
            msg = "Please report directory and file name can not be NULL \n"
                    + "REPORT_DIRECTORY = " + reportDirectory + "\n"
                    + "FILE_NAME = " + fileName;

            Logger.getLogger(JasperReportManager.class.getName()).log(Level.INFO, msg);

            return null;

        }

        fileName = checkFileName(fileName);

        try {
            new File(reportDirectory.trim()).mkdirs();
        } catch (Exception e) {
            msg = "Unable to create or find Report Output directory (" + reportDirectory + ")";
            Logger.getLogger(JasperReportManager.class.getName()).log(Level.SEVERE, msg + "\n" + e.getMessage(), e);

            return null;
        }

        File file = new File(reportDirectory, fileName);

        this.reportDataList = reportData;

        createJasperPrint(inputStream);

        msg = "Report will be written to " + file.getAbsolutePath();
        Logger.getLogger(JasperReportManager.class.getName()).log(Level.INFO, msg);

        switch (reportOutputFileType) {
            case PDF:
                try {
                    JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());

                    LOGGER.info("Report exported to pdf completed - " + file.getAbsolutePath());

                    setGeneratedFile(file);

                    return file.toString();

                } catch (Exception ex) {
                    ex.printStackTrace();
                    Logger.getLogger(JasperReportManager.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
        }

        return null;
    }

    public String writeToFile(ReportData reportData, String reportDirectory, String fileName) {
        InputStream inputStream = JasperReportManager.class.getResourceAsStream(reportData.getReportFile());

        addMap(reportData.getMap());

        return writeToFile(reportData.getListData(), inputStream, reportDirectory, fileName);
    }

    public void addParam(String paramKey, Object paramValue) {
        reportParamenters.put(paramKey, paramValue);
    }

    public static HttpServletResponse getServeltResponse() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().
                getExternalContext().getResponse();

        return response;
    }

    public static HttpServletRequest getServeltRequest() {
        HttpServletRequest response = (HttpServletRequest) FacesContext.getCurrentInstance().
                getExternalContext().getRequest();

        return response;
    }

    public String checkFileName(String fileName) {
        String oldFilename = fileName;

        char[] chrs = new char[]{'\'', '/', ':', '*', '?', '"', '<', '>', '|'};
        if (!StringUtils.containsAny(fileName, chrs)) {
            return fileName;
        }

        for (int i = 0; i < chrs.length; i++) {
            char c = chrs[i];
            fileName = StringUtils.remove(fileName, c);
        }

        msg = "Report Output File Name (" + oldFilename + ") contains escape or invalid charaters. "
                + "Will be replaced with (-) to " + fileName;
        Logger.getLogger(JasperReportManager.class.getName()).log(Level.INFO, msg);
        return fileName;
    }

    private static void forceDownload() {
        HttpServletResponse hsr = getServeltResponse();
        hsr.setHeader("Content-type", "application/force-download");
        hsr.setHeader("Content-Transfer-Encoding", "Binary");
        hsr.setHeader("Content-length", null);
//        hsr.setContentType("application/force-download");
//        hsr.setCharacterEncoding("Binary");

    }

    private void outputReport() {
        if (jasperPrint == null) {
            msg = "Could not create Jasper Print so Report Process will be abborted";
            Logger.getLogger(JasperReportManager.class.getName()).log(Level.SEVERE, msg);

            return;
        }

        switch (reportOutputEnvironment) {
            case WEB_APPLICATION:
                webenviro();
                break;
            case DESKTOP_APPLICATION:
                desktopEnviroment();
                break;
        }
    }

    private void createJasperPrint(InputStream rptIns) {
        try {
            jrCollectionDataSource = new JRBeanCollectionDataSource(reportDataList,false);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }

        if (reportFileType == null) {
            throw new IllegalArgumentException("Please specify report file type : " + reportFileType);
        }

        if (reportFileType == ReportDesignFileType.INPUTSTREAM) {
            InputStream inputStream = null;

            if (rptIns != null) {
                inputStream = rptIns;
            } else {
                try {
//                    System.out.println("about to load input stream with " + jasperFile);

                    if (!jasperFile.endsWith(".jasper")) {
                        jasperFile = jasperFile + ".jasper";
                    }

                    inputStream = JasperReportManager.class.getResourceAsStream(jasperFile);
//                    System.out.println(inputStream + " result of searchin.... " + jasperFile);
                } catch (Exception e) {
                    msg = "Unable to load Input Stream for " + jasperFile;
                    Logger.getLogger(JasperReportManager.class.getName()).log(Level.SEVERE, msg + " \n " + e.toString(), e);

                    jasperPrint = null;

                    return;
                }
            }

            try {
                msg = "Creating JasperPrint with inputstream: " + inputStream
                        + " and bean collection datasource " + jrCollectionDataSource;

                Logger.getLogger(JasperReportManager.class.getName()).log(Level.INFO, msg);

                if (inputStream == null) {
                    return;
                }

//                System.out.println("going to create jasper print from : " + inputStream);
                jasperPrint = JasperFillManager.fillReport(inputStream, reportParamenters, jrCollectionDataSource);

                System.out.println("jasperPrint created = " + jasperPrint);

            } catch (Exception e) {
                Logger.getLogger(JasperReportManager.class.getName()).log(Level.SEVERE,
                        "Error Creating JasperPrint for " + jasperFile + "\n" + e.toString(), e);

            }
        } else if (reportFileType == ReportDesignFileType.STRING_FILE) {
        }
    }

    public void addToDefaultParameters(String paramKey, Object paramValue) {
        defaultParamenters.put(paramKey, paramValue);
        reportParamenters.put(paramKey, paramValue);
    }

    public void resetReportParametersToDefault() {
        reportParamenters.clear();
        reportParamenters.putAll(defaultParamenters);
    }

    public Map<String, Object> getReportParamenters() {
        return reportParamenters;
    }

    public ReportOutputEnvironment getReportEnvironment() {
        return reportOutputEnvironment;
    }

    public void setReportEnvironment(ReportOutputEnvironment reportEnvironment) {
        this.reportOutputEnvironment = reportEnvironment;
    }

    public ReportDesignFileType getReportFileType() {
        return reportFileType;
    }

    public void setReportFileType(ReportDesignFileType reportFileType) {
        this.reportFileType = reportFileType;
    }

    public ReportOutputFileType getReportOutput() {
        return reportOutputFileType;
    }

    public void setReportOutput(ReportOutputFileType reportOutput) {
        this.reportOutputFileType = reportOutput;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    private void create(String files) {
        File pa = new File(files);

        try {
            pa.mkdirs();
        } catch (Exception e) {
        }
    }

    public File getGeneratedFile() {
        return generatedFile;
    }

    public void setGeneratedFile(File generatedFile) {
        this.generatedFile = generatedFile;
    }
}
