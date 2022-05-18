/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.configurations;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.ui.Model;

/**
 *
 * @author User
 */
public class ExcelExport {
    //data.put("1", new Object[]{"No", "NIK", "Nama Staff", "Periode Klaim", "Police Number",
//            "Jenis Kendaraan", "Tipe Pembayaran", "Total",
//            "Nama Pemlik", "No STNK/BPKB", "Nama Pengelola Parkir",
//            "Alamat Parkir", "No telp Pengelola", "Gedung Customer"});

    private static final String[] titles = {
        "No", "Nama", "NIK", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "10", "11", "12", "13", "14",
        "15", "16", "17", "18"
    };

    private static final Object[][] sample_data = {
        {"Yegor Kozlov", "YK", 5.0, 8.0, 10.0, 5.0, 5.0, 7.0, 6.0},
        {"Gisella Bronzetti", "GB", 4.0, 3.0, 1.0, 3.5, null, null, 4.0},};

    private static Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();
        CellStyle style;
        Font titleFont = wb.createFont();
        titleFont.setFontHeightInPoints((short) 18);
        titleFont.setBold(true);
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFont(titleFont);
        styles.put("title", style);

        Font monthFont = wb.createFont();
        monthFont.setFontHeightInPoints((short) 11);
        monthFont.setColor(IndexedColors.WHITE.getIndex());
        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(monthFont);
        style.setWrapText(true);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setWrapText(true);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        styles.put("cell", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(wb.createDataFormat().getFormat("0.00"));
        styles.put("formula_2", style);

        return styles;
    }

    public static void exportExcel3(HttpServletResponse response, List<Object[]> dataFeedback, String training_title) throws FileNotFoundException, IOException {
        Workbook wb;

        wb = new XSSFWorkbook();

        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet("FeedbackSummary");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Feedback Summary");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$U$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < 10; i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = row.createCell(j);

                cell.setCellStyle(styles.get("cell"));
            }
        }

        //set sample data
        int counterRow = 2;
        for (Object[] feedbackSummary : dataFeedback) {
            counterRow++;
            Row row = sheet.getRow(counterRow);
            for (int j = 0; j < feedbackSummary.length; j++) {
                System.out.println("Data : " + feedbackSummary[j]);
                if (j == 0) {
                    row.getCell(j).setCellValue((Integer) counterRow - 2);
                } //                else if (j == 11) {
                //                    row.getCell(j).setCellValue((String) dataHistory[j].toString());
                //                } 
                else {

                    if (feedbackSummary[j] == null) {
                        row.getCell(j).setCellValue((String) "N/A");

                    }
                    try {
                        if (feedbackSummary[j] instanceof Double) {
                            row.getCell(j).setCellValue((Double) feedbackSummary[j]);
                        } else if (feedbackSummary[j] instanceof Date) {
                            row.getCell(j).setCellValue((Date) feedbackSummary[j]);
                        } else if (feedbackSummary[j] instanceof Integer) {
                            row.getCell(j).setCellValue((Integer) feedbackSummary[j]);
                        } //                        else if (dataHistory[j] instanceof BigInteger) {
                        //                            row.getCell(j).setCellValue((BigInteger) dataHistory[j]);
                        //                        } 
                        else {
                            row.getCell(j).setCellValue((String) feedbackSummary[j]);
                        }
                    } catch (Exception e) {
                        System.out.println("data Error: " + feedbackSummary[j]);
                    }

                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6 * 256); //30 characters wide
        sheet.setColumnWidth(1, 25 * 256); //30 characters wide
        for (int i = 2; i < 17; i++) {
            sheet.setColumnWidth(i, 6 * 256);  //6 characters wide
        }
        for (int i = 18; i < 21; i++) {
            sheet.setColumnWidth(i, 60 * 256);  //6 characters wide
        }
//        sheet.setColumnWidth(13, 20 * 256); //10 characters wide

        // Write the output to a file
        String File_name = "Feedback Summary - " + training_title + ".xlsx";
        String file = File_name;
//        if (wb instanceof XSSFWorkbook) {
//            file += "";
//        }
        //lokal & dev
        //FileOutputStream out = new FileOutputStream(file);
        //production
       FileOutputStream out = new FileOutputStream("/opt/tomcat/webapps/uploadedFiles/bin/"+file);
        wb.write(out);
        out.close();

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=FeedbackSummary_" + training_title + ".xlsx");
            response.setHeader("Content-Transfer-Encoding", "binary");
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            //lokal & dev
            //FileInputStream fis = new FileInputStream(File_name);
            //production
            FileInputStream fis = new FileInputStream("/opt/tomcat/webapps/uploadedFiles/bin/"+File_name);
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            bos.close();
            response.flushBuffer();

            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportExcel(HttpServletResponse response) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("History Data");

        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[]{"ID", "NAME", "LASTNAME"});
        data.put("2", new Object[]{1, "Amit", "Shukla"});
        data.put("3", new Object[]{2, "Lokesh", "Gupta"});
        data.put("4", new Object[]{3, "John", "Adwards"});
        data.put("5", new Object[]{4, "Brian", "Schultz"});

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 0;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }
        }
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=excel.xlsx");
            response.setHeader("Content-Transfer-Encoding", "binary");
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileOutputStream out = new FileOutputStream(new File("howtodoinjava_demo.xlsx"));
            workbook.write(out);
            out.close();
            FileInputStream fis = new FileInputStream("howtodoinjava_demo.xlsx");
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            bos.close();
            response.flushBuffer();

            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportExcel2(HttpServletResponse response, List<Object[]> Data) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        //Create a blank sheet
        XSSFSheet sheet = workbook.createSheet("History Data");

//       sheet.addMergedRegion(CellRangeAddress.valueOf("A1:M3"));
        Row header = sheet.createRow(1);
        Cell headerCell = header.createCell(6);
        headerCell.setCellValue("Parking Recap Form");
        Row header2 = sheet.createRow(2);
        Cell headerCell2 = header2.createCell(6);
        headerCell2.setCellValue("Periode: May 2020");
        CellStyle styleHeaderCell = workbook.createCellStyle();
        styleHeaderCell.setFillBackgroundColor(IndexedColors.AQUA.getIndex());
//        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 13));
//        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 13));
//        sheet.addMergedRegion(CellRangeAddress.valueOf("A1:M1"));
//        sheet.addMergedRegion(CellRangeAddress.valueOf("A2:M2"));
//        sheet.addMergedRegion(CellRangeAddress.valueOf("A3:M3"));
        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
        data.put("1", new Object[]{"No", "NIK", "Nama Staff", "Periode Klaim", "Police Number",
            "Jenis Kendaraan", "Tipe Pembayaran", "Total",
            "Nama Pemlik", "No STNK/BPKB", "Nama Pengelola Parkir",
            "Alamat Parkir", "No telp Pengelola", "Gedung Customer"});
        int counter = 2;
        int counterNo = 1;
        for (Object[] dataa : Data) {
            data.put(Integer.toString(counter), new Object[]{counterNo, dataa[1], "Shukla"});
            counter++;
            counterNo++;
        }

        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 4;
        for (String key : keyset) {
            Row row = sheet.createRow(rownum++);
            Object[] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
//                 sheet.autoSizeColumn(cell);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);
                } else if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                }
            }
        }
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=excel.xlsx");
            response.setHeader("Content-Transfer-Encoding", "binary");
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            FileOutputStream out = new FileOutputStream(new File("howtodoinjava_demo.xlsx"));
            workbook.write(out);
            out.close();
            FileInputStream fis = new FileInputStream("howtodoinjava_demo.xlsx");
            int len;
            byte[] buf = new byte[1024];
            while ((len = fis.read(buf)) > 0) {
                bos.write(buf, 0, len);
            }
            bos.close();
            response.flushBuffer();

            System.out.println("howtodoinjava_demo.xlsx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
