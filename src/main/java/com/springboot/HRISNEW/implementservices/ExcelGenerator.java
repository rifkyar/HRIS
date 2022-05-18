/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.Recap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author USER
 */
public class ExcelGenerator {

    public static ByteArrayInputStream recapToExcel(List<Recap> listRecap) throws IOException {

        String[] COLUMNs = {"No", "ID Employee", "Nama Employee", "Employee Position", "SO ID", "PIC Name", "PIC Posititon", "Ticket Number", "Salary", "Location","Start Date", "End Date", 
            "Start Time", "End Time", "Total Hours", "Type Day", "Create Date", "Update By", "Update Date", "Meal & Transport", "Chargeback Status", "Chargerback Type", 
            "Month", "Assignment", "Customer Name", "Request Status", "Status Contract", "1", "1.5", "2", "2", "3", "4", "Total Hour (Rounded)", "Total Fee"};

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Recap Overtime");
            for (int i = 0; i < COLUMNs.length; i++) {
                sheet.autoSizeColumn(i);
            }

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLACK.getIndex());
            headerFont.setFontHeightInPoints((short) 12);

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);
            headerCellStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerCellStyle.setAlignment(HorizontalAlignment.CENTER);

            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

      
            CellStyle ageCellStyle = workbook.createCellStyle();
            ageCellStyle.setBorderBottom(BorderStyle.MEDIUM);
            ageCellStyle.setBorderTop(BorderStyle.MEDIUM);
            ageCellStyle.setBorderLeft(BorderStyle.MEDIUM);
            ageCellStyle.setBorderRight(BorderStyle.MEDIUM);

            int rowNum = 1;
            for (Recap recaps : listRecap) {
                Row row = sheet.createRow(rowNum++);

                sheet.autoSizeColumn(rowNum);

                row.createCell(0).setCellValue(recaps.getNo());
                row.createCell(1).setCellValue(recaps.getIdEmployee());
                row.createCell(2).setCellValue(recaps.getNamaEmployee());
                row.createCell(3).setCellValue(recaps.getPositionEmployee());
                row.createCell(4).setCellValue(recaps.getSoId());
                row.createCell(5).setCellValue(recaps.getNamePIC());
                row.createCell(6).setCellValue(recaps.getPositionPIC());
                row.createCell(7).setCellValue(recaps.getTicketNumber());
                row.createCell(8).setCellValue(recaps.getSalaryEmployee());
                row.createCell(9).setCellValue(recaps.getLocation());
                row.createCell(10).setCellValue(recaps.getStartDate());
                row.createCell(11).setCellValue(recaps.getEndDate());
                row.createCell(12).setCellValue(recaps.getStartTime());
                row.createCell(13).setCellValue(recaps.getEndTime());
                row.createCell(14).setCellValue(recaps.getTotalHour());
                row.createCell(15).setCellValue(recaps.getDateType());
                row.createCell(16).setCellValue(recaps.getCreateDate());
                row.createCell(17).setCellValue(recaps.getUpdateBy());
                row.createCell(18).setCellValue(recaps.getUpdateDate());
                row.createCell(19).setCellValue(recaps.getMealsTransport());
                row.createCell(20).setCellValue(recaps.getChargerbackStatus());
                row.createCell(21).setCellValue(recaps.getChargerBackType());
                row.createCell(22).setCellValue(recaps.getPeriode());

                row.createCell(23).setCellValue(recaps.getAssignment());
                row.createCell(24).setCellValue(recaps.getNamaCustomer());
                row.createCell(25).setCellValue(recaps.getStatus());
                row.createCell(26).setCellValue(recaps.getEmployeeContractStatus());

                row.createCell(27).setCellValue(recaps.getCell1());
                row.createCell(28).setCellValue(recaps.getCell2());
                row.createCell(29).setCellValue(recaps.getCell3());
                row.createCell(30).setCellValue(recaps.getCell4());
                row.createCell(31).setCellValue(recaps.getCell5());
                row.createCell(32).setCellValue(recaps.getCell6());
                row.createCell(33).setCellValue(recaps.getTotalRounded());
                row.createCell(34).setCellValue(recaps.getTotalFee());
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
