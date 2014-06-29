package com.innerman.emotracker.core.service;


import com.innerman.emotracker.core.dto.DartaSensorDTO;
import com.innerman.emotracker.core.model.DataEventEntity;
import com.innerman.emotracker.core.model.UserEntity;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.URL;
import java.sql.SQLException;
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

    public void generateExcelReport(OutputStream stream, String id, UserEntity user) throws SQLException, JRException {

        DataEventEntity entity = dataEventEntityService.getDataEventForUser(id, user);
        if( entity == null ) {
            return;
        }

        URL location = this.getClass().getResource(REPORT_PATH);
        String fullPath = location.getPath();

        ReportDataSource ds = new ReportDataSource(entity);

        try {
            final JExcelApiExporter exporter = makeExcelReport(entity, ds, fullPath);

            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, stream);
            exporter.exportReport();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public JExcelApiExporter makeExcelReport(DataEventEntity entity, JRDataSource customDataSource, String fullPath) throws SQLException {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", entity.getName());
        params.put("startDate", entity.getStartDate());
        params.put("description", entity.getDescription());
        params.put("REPORT_DATASET", customDataSource);

        JasperPrint print;
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(fullPath);
//            jasperReport.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);

            print = JasperFillManager.fillReport(jasperReport, params, customDataSource);
//            JasperExportManager.exportReportToPdfFile(print, "/Users/petrpopov/Downloads/export.pdf");

            return makeExcelReport(print);
        } catch (JRException e) {
            e.printStackTrace();
        }


        return null;
    }



    @Deprecated
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

    private JExcelApiExporter makeExcelReport(JasperPrint print) throws SQLException {

        final JExcelApiExporter exporter = new JExcelApiExporter();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
        exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
        exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_IGNORE_CELL_BORDER, Boolean.FALSE);
        exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.FALSE);

        return exporter;
    }

    private static final class ReportDataSource implements JRDataSource {

        private DataEventEntity data;
        int rowIndex = -1;

        private ReportDataSource(DataEventEntity data) {
            this.data = data;
        }

        @Override
        public boolean next() throws JRException {
            if( rowIndex < data.getSensors().size() ) {
                rowIndex++;
                return true;
            }
            return false;
        }

        @Override
        public Object getFieldValue(JRField jrField) throws JRException {
            Object value = null;

            DartaSensorDTO dto = data.getSensors().get(rowIndex - 1);
            String name = jrField.getName();

            if( name.equals("header") ) {
                value = dto.getHeader();
            }
            else if( name.equals("counter")) {
                value = dto.getCounter();
            }
            else if( name.equals("deviceDate") ) {
                value = dto.getDeviceDate();
            }
            else if( name.equals("systemDate") ) {
                value = dto.getSystemDate();
            }
            else if( name.equals("pulseMs") ) {
                value = dto.getPulseMs();
            }
            else if( name.equals("accX") ) {
                value = dto.getAccX();
            }
            else if( name.equals("accY") ) {
                value = dto.getAccY();
            }
            else if( name.equals("accZ") ) {
                value = dto.getAccZ();
            }

            return value;
        }
    }
}
