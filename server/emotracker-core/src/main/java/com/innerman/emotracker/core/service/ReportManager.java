package com.innerman.emotracker.core.service;

import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * User: petrpopov
 * Date: 11.05.14
 * Time: 21:55
 */

@Component
public class ReportManager {

    public byte[] getReport() throws JRException, FileNotFoundException, URISyntaxException {

        URL location = this.getClass().getResource("/reports/report1.jrxml");
        String fullPath = location.getPath();

        JasperReport jasperReport = JasperCompileManager.compileReport(fullPath);

        Map<String, Object> params = new HashMap<String, Object>();
        byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, params, new JREmptyDataSource());

        return bytes;
    }
}
