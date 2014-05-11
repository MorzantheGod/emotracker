package com.innerman.emotracker.core.service;

import com.innerman.emotracker.core.model.DataEventEntity;
import com.innerman.emotracker.core.model.UserEntity;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    @Autowired
    private DataEventEntityService dataEventEntityService;

    private static final String REPORT_PATH = "/reports/data_event.jrxml";

    public byte[] getDataEventReport(String id, UserEntity user) throws JRException {

        DataEventEntity entity = dataEventEntityService.getDataEventForUser(id, user);
        if( entity == null ) {
            return new byte[0];
        }

        URL location = this.getClass().getResource(REPORT_PATH);
        String fullPath = location.getPath();

        JasperReport jasperReport = JasperCompileManager.compileReport(fullPath);

        Map<String, Object> params = new HashMap<String, Object>();
        return JasperRunManager.runReportToPdf(jasperReport, params, new JREmptyDataSource());
    }
}
