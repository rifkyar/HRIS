/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.exportExcel;

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
//import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
//import org.apache.poi.ss.usermodel.IndexedColors;
//import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author alexa
 */
public class ExportExcel {
//data.put("1", new Object[]{"No", "NIK", "Nama Staff", "Periode Klaim", "Police Number",
//            "Jenis Kendaraan", "Tipe Pembayaran", "Total",
//            "Nama Pemlik", "No STNK/BPKB", "Nama Pengelola Parkir",
//            "Alamat Parkir", "No telp Pengelola", "Gedung Customer"});

//    private static final String[] titles = {
//        "No", "NIK", "Nama Staff", "Periode\nKlaim", "Police\nNumber", "Jenis\nKendaraan", "Tipe\nPembayaran", "Total",
//        "No STNK/BPKB", "Nama Pengelola\nParkir", "Alamat Parkir", "No Telp\nPengelola", "Gedung\nCustomer"
//    };
    
    //Baru
    //MSFC Parking Recap
    private static final String[] titles = {
        "No", "NIK", "Nama Staff", "Periode\nKlaim", "Police\nNumber", "Jenis\nKendaraan", "Tipe\nPembayaran", "Total",
        "No STNK/BPKB", "Nama Pengelola\nParkir", "Alamat Parkir", "No Telp\nPengelola", "Gedung\nCustomer", "Create\nDate",
        "Update Date","Relation Manager Name","Sales Order","Customer Name","Request Status"
    };
    //EMPLY Parking Recap
    private static final String[] titles2 = {
        "No", "NIK", "Nama Staff", "Periode\nKlaim", "Police\nNumber", "Jenis\nKendaraan", "Tipe\nPembayaran", "Total",
        "No STNK/BPKB", "Nama Pengelola\nParkir", "Alamat Parkir", "No Telp\nPengelola", "Gedung\nCustomer"
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

    public static void exportExcel3(HttpServletResponse response, List<Object[]> DataHistory, String NIK_Requester, int lenData) throws FileNotFoundException, IOException {
        Workbook wb;

        wb = new XSSFWorkbook();

        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet("ParkingRec");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Parking Recap");
        titleCell.setCellStyle(styles.get("title"));
//        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$M$1"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$S$1"));

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
        for (int i = 0; i < (lenData+2); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titles.length; j++) {
                Cell cell = row.createCell(j);
                //autocoloumn
                sheet.autoSizeColumn(j);
//                if (j == 9) {
//                    //the 10th cell contains sum over week days, e.g. SUM(C3:I3)
//                    String ref = "C" + rownum + ":I" + rownum;
//                    cell.setCellFormula("SUM(" + ref + ")");
//                    cell.setCellStyle(styles.get("formula"));
//                } else if (j == 11) {
//                    cell.setCellFormula("J" + rownum + "-K" + rownum);
//                    cell.setCellStyle(styles.get("formula"));
//                } else {
                cell.setCellStyle(styles.get("cell"));
//                }
            }
        }

        //row with totals below
//        Row sumRow = sheet.createRow(rownum++);
//        sumRow.setHeightInPoints(35);
//        Cell cell;
//        cell = sumRow.createCell(0);
//        cell.setCellStyle(styles.get("formula"));
//        cell = sumRow.createCell(1);
//        cell.setCellValue("Total Hrs:");
//        cell.setCellStyle(styles.get("formula"));
//
//        for (int j = 2; j < 12; j++) {
//            cell = sumRow.createCell(j);
//            String ref = (char) ('A' + j) + "3:" + (char) ('A' + j) + "12";
//            cell.setCellFormula("SUM(" + ref + ")");
//            if (j >= 9) {
//                cell.setCellStyle(styles.get("formula_2"));
//            } else {
//                cell.setCellStyle(styles.get("formula"));
//            }
//        }
//        rownum++;
//        sumRow = sheet.createRow(rownum++);
//        sumRow.setHeightInPoints(25);
//        cell = sumRow.createCell(0);
//        cell.setCellValue("Total Regular Hours");
//        cell.setCellStyle(styles.get("formula"));
//        cell = sumRow.createCell(1);
//        cell.setCellFormula("L13");
//        cell.setCellStyle(styles.get("formula_2"));
//        sumRow = sheet.createRow(rownum++);
//        sumRow.setHeightInPoints(25);
//        cell = sumRow.createCell(0);
//        cell.setCellValue("Total Overtime Hours");
//        cell.setCellStyle(styles.get("formula"));
//        cell = sumRow.createCell(1);
//        cell.setCellFormula("K13");
//        cell.setCellStyle(styles.get("formula_2"));
        //set sample data
        int counterRow = 2;
        for (Object[] dataHistory : DataHistory) {
            counterRow++;
            Row row = sheet.getRow(counterRow);
            for (int j = 0; j < dataHistory.length; j++) {
                if (j == 0) {
                    row.getCell(j).setCellValue((Integer) counterRow - 2);
                } else if (j == 11) {
                    row.getCell(j).setCellValue((String) dataHistory[j].toString());
                } else {

                    if (dataHistory[j] == null) {
                        continue;
                    }
                    try {
                        if (dataHistory[j] instanceof Double) {
                            row.getCell(j).setCellValue((Double) dataHistory[j]);
                        } else if (dataHistory[j] instanceof Date) {
                            row.getCell(j).setCellValue((Date) dataHistory[j]);
                        } else if (dataHistory[j] instanceof Integer) {
                            row.getCell(j).setCellValue((Integer) dataHistory[j]);
                        } else if (dataHistory[j] instanceof BigInteger) {
                            row.getCell(j).setCellValue((RichTextString) (BigInteger) dataHistory[j]);
                        } else {
                            row.getCell(j).setCellValue((String) dataHistory[j]);
                        }
                    } catch (Exception e) {
                        System.out.println("data Error: " + dataHistory[j]);
                    }

                }
            }
        }

//        for (int i = 0; i < sample_data.length; i++) {
//            Row row = sheet.getRow(2 + i);
//            for (int j = 0; j < sample_data[i].length; j++) {
//                if (sample_data[i][j] == null) {
//                    continue;
//                }
//
//                if (sample_data[i][j] instanceof String) {
//                    row.getCell(j).setCellValue((String) sample_data[i][j]);
//                } else {
//                    row.getCell(j).setCellValue((Double) sample_data[i][j]);
//                }
//            }
//        }
        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6 * 256); //30 characters wide
        for (int i = 2; i < 12; i++) {
            sheet.setColumnWidth(i, 25 * 256);  //6 characters wide
        }
        sheet.setColumnWidth(13, 20 * 256); //10 characters wide

        // Write the output to a file
        String File_name = "Recap_Parking_" + NIK_Requester + ".xlsx";
        String file = File_name;
        //lokal & dev
        //FileOutputStream out = new FileOutputStream(file);
        //production
        FileOutputStream out = new FileOutputStream("/opt/tomcat/webapps/uploadedFiles/bin/"+file);
        wb.write(out);
        out.close();

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Parking_Recap.xlsx");
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
    //EMPLY & RM parking recap
    public static void exportExcel4(HttpServletResponse response, List<Object[]> DataHistory, String NIK_Requester, int lenData) throws FileNotFoundException, IOException {
        Workbook wb;

        wb = new XSSFWorkbook();

        Map<String, CellStyle> styles = createStyles(wb);

        Sheet sheet = wb.createSheet("ParkingRec");
        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        //title row
        Row titleRow = sheet.createRow(0);
        titleRow.setHeightInPoints(45);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Parking Recap");
        titleCell.setCellStyle(styles.get("title"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("$A$1:$M$1"));

        //header row
        Row headerRow = sheet.createRow(1);
        headerRow.setHeightInPoints(40);
        Cell headerCell;
        for (int i = 0; i < titles2.length; i++) {
            headerCell = headerRow.createCell(i);
            headerCell.setCellValue(titles2[i]);
            headerCell.setCellStyle(styles.get("header"));
        }

        int rownum = 2;
        for (int i = 0; i < (lenData+2); i++) {
            Row row = sheet.createRow(rownum++);
            for (int j = 0; j < titles2.length; j++) {
                Cell cell = row.createCell(j);
                sheet.autoSizeColumn(j);
                cell.setCellStyle(styles.get("cell"));
            }
        }

        //set sample data
        int counterRow = 2;
        for (Object[] dataHistory : DataHistory) {
            counterRow++;
            Row row = sheet.getRow(counterRow);
            for (int j = 0; j < dataHistory.length; j++) {
                if (j == 0) {
                    row.getCell(j).setCellValue((Integer) counterRow - 2);
                } else if (j == 11) {
                    row.getCell(j).setCellValue((String) dataHistory[j].toString());
                } else {

                    if (dataHistory[j] == null) {
                        continue;
                    }
                    try {
                        if (dataHistory[j] instanceof Double) {
                            row.getCell(j).setCellValue((Double) dataHistory[j]);
                        } else if (dataHistory[j] instanceof Date) {
                            row.getCell(j).setCellValue((Date) dataHistory[j]);
                        } else if (dataHistory[j] instanceof Integer) {
                            row.getCell(j).setCellValue((Integer) dataHistory[j]);
                        } else if (dataHistory[j] instanceof BigInteger) {
                            row.getCell(j).setCellValue((RichTextString) (BigInteger) dataHistory[j]);
                        } else {
                            row.getCell(j).setCellValue((String) dataHistory[j]);
                        }
                    } catch (Exception e) {
                        System.out.println("data Error: " + dataHistory[j]);
                    }

                }
            }
        }

        //finally set column widths, the width is measured in units of 1/256th of a character width
        sheet.setColumnWidth(0, 6 * 256); //30 characters wide
        for (int i = 2; i < 12; i++) {
            sheet.setColumnWidth(i, 25 * 256);  //6 characters wide
        }
        sheet.setColumnWidth(13, 20 * 256); //10 characters wide

        // Write the output to a file
        String File_name = "Recap_Parking_" + NIK_Requester + ".xlsx";
        String file = File_name;
        //lokal & dev
        //FileOutputStream out = new FileOutputStream(file);
        //production
        FileOutputStream out = new FileOutputStream("/opt/tomcat/webapps/uploadedFiles/bin/"+file);
        wb.write(out);
        out.close();

        try {
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Parking_Recap.xlsx");
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
}
