package com.reactive.reporting.ReactiveJasperReport.service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class JasperReportService {

    public byte[] generateReport() {
        try {
            // Load the JasperReport template from the classpath
            InputStream templateInputStream = this.getClass().getResourceAsStream("/report_template.jrxml");

            // Compile the JasperReport template
            JasperReport jasperReport = JasperCompileManager.compileReport(templateInputStream);

            // Create an empty data source to fill the report (since we don't need any data)
            JRDataSource emptyDataSource = new JREmptyDataSource();

            Map<String, Object> reportData = new HashMap<>();
            reportData.put("existingField", "Existing Field Value");
            reportData.put("newField", "New Field Value");
            JRDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(reportData));
            // Fill the JasperReport template (no data source required)
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            // Export the JasperReport to a byte array (PDF format in this case)
            byte[] reportBytes = exportReportToPdf(jasperPrint);
            String outputDirectory = "D:/D/Personal_Project/"; // Change this to your desired directory
            String outputFile = outputDirectory + "report.pdf";
            saveReportToFile(reportBytes, outputFile);
            return reportBytes;
        } catch (JRException e) {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] exportReportToPdf(JasperPrint jasperPrint) throws JRException {
        // Export the JasperReport to a byte array (PDF format)
        JRPdfExporter exporter = new JRPdfExporter();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
        exporter.exportReport();

        return byteArrayOutputStream.toByteArray();
    }

    private void saveReportToFile(byte[] reportBytes, String outputFile) {
        try (FileOutputStream fos = new FileOutputStream(new File(outputFile))) {
            fos.write(reportBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
