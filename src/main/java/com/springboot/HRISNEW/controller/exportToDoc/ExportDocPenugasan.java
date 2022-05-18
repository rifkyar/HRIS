/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.exportToDoc;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

/**
 *
 * @author Dharta
 */
public class ExportDocPenugasan {

    public static void exportDocPenugasan(final Date currentDate, List<Object[]> dataKaryawan, List<Object[]> dataDetailsKaryawan, HttpServletResponse response) throws FileNotFoundException, IOException {
        // Judul
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        setSingleLineSpacing(p1);
        XWPFRun r1 = p1.createRun();
        String title = "SURAT PENUGASAN";
        r1.setText(title);
        r1.setBold(true);
        r1.setFontSize(20);
        r1.setUnderline(UnderlinePatterns.SINGLE);

        // Insert page break after this paragraph
//         p2.setPageBreak(true);
        try {
            for (Object[] dataHistory : dataKaryawan) {
                XWPFParagraph para1 = doc.createParagraph();
                para1.setAlignment(ParagraphAlignment.CENTER);
                para1.setWordWrapped(true);
                String string1 = "No ." + dataHistory[1].toString();
                XWPFRun para1Run = para1.createRun();
                para1Run.setText(string1);
                para1Run.setFontSize(14);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        //Spasi Enter Kebawah
        XWPFParagraph p2 = doc.createParagraph();
        p2.setFirstLineIndent(400);
        p2.setAlignment(ParagraphAlignment.CENTER);
        p2.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Paragraph 1
        XWPFParagraph p3 = doc.createParagraph();
        p3.setAlignment(ParagraphAlignment.BOTH);
        p3.setWordWrapped(true);
        XWPFRun r3 = p3.createRun();
        String t3 = "Yang bertanda tangan di bawah ini, dengan ini menerangkan bahwa :";
        r3.setText(t3);
        r3.setFontSize(12);

        // Nama
        for (int o = 1; o < 2; o++) {

            XWPFTable table = doc.createTable();
            XWPFTableRow tableRow0 = table.getRow(0);
            XWPFTableCell tableCell0 = tableRow0.getCell(0);
            tableCell0.setText("No.");
            XWPFTableCell tableCell1 = tableRow0.addNewTableCell();
            tableCell1.setText("NIK");
            XWPFTableCell tableCell2 = tableRow0.addNewTableCell();
            tableCell2.setText("Nama");
            XWPFTableCell tableCell3 = tableRow0.addNewTableCell();
            tableCell3.setText("Posisi");
            XWPFTableCell tableCell4 = tableRow0.addNewTableCell();
            tableCell4.setText("No KTP.");
            XWPFTableCell tableCell5 = tableRow0.addNewTableCell();
            tableCell5.setText("No HP.");

        }

        int counter = 1;
        for (Object[] dataHistory : dataDetailsKaryawan) {
            XWPFTable table = doc.createTable();
            XWPFTableRow tableRow0 = table.getRow(0);

            XWPFTableCell tableCell0 = tableRow0.getCell(0);

            tableCell0.setText(String.valueOf(counter));
            counter++;

            XWPFTableCell tableCell1 = tableRow0.addNewTableCell();
            tableCell1.setText(" " + dataHistory[0] + "   ");
            XWPFTableCell tableCell2 = tableRow0.addNewTableCell();
            tableCell2.setText(" " + dataHistory[1] + "   ");
            XWPFTableCell tableCell3 = tableRow0.addNewTableCell();
            tableCell3.setText(" " + dataHistory[3] + "   ");
            XWPFTableCell tableCell4 = tableRow0.addNewTableCell();
            tableCell4.setText(" " + dataHistory[2] + "   ");
            XWPFTableCell tableCell5 = tableRow0.addNewTableCell();
            tableCell5.setText(" " + dataHistory[4] + "   ");

        }

        //Spasi Enter Kebawah
        XWPFParagraph space2 = doc.createParagraph();
        space2.setFirstLineIndent(400);
        space2.setAlignment(ParagraphAlignment.CENTER);
        space2.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Paragraph 2
        try {

            for (Object[] dataHistory : dataKaryawan) {

                String converted = dataHistory[2].toString();
                String converted2 = dataHistory[3].toString();
                String pattern = "dd MMMMM yyyy";
                String sDate = converted;
                String eDate = converted2;
                SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateNow = formatter6.parse(sDate);
                Date enDate = formatter6.parse(eDate);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String startDate = simpleDateFormat.format(dateNow);
                String endDate = simpleDateFormat.format(enDate);

                XWPFParagraph para5 = doc.createParagraph();
                para5.setAlignment(ParagraphAlignment.BOTH);

                para5.setWordWrapped(true);
                String t5 = "Adalah karyawan PT. Mitra Integrasi Informatika yang ditugaskan di " + dataHistory[4] + " untuk melakukan " + "'" + dataHistory[0] + "' pada tanggal " + startDate + " sampai dengan " + endDate + ".";
                XWPFRun r5 = para5.createRun();
                r5.setText(t5);
                r5.setFontSize(12);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        //Spasi Enter Kebawah
        XWPFParagraph space3 = doc.createParagraph();
        space3.setFirstLineIndent(400);
        space3.setAlignment(ParagraphAlignment.CENTER);
        space3.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Paragraph Last
        XWPFParagraph plast = doc.createParagraph();
        plast.setAlignment(ParagraphAlignment.BOTH);
        plast.setWordWrapped(true);
        XWPFRun r8 = plast.createRun();
        String t8 = "Demikian Surat Penugasan ini dibuat agar dapat dipergunakan dengan sebaik-baiknya.";
        r8.setText(t8);
        r8.setFontSize(12);

        //Spasi Enter Kebawah
        XWPFParagraph space0 = doc.createParagraph();
        space0.setFirstLineIndent(400);
        space0.setAlignment(ParagraphAlignment.CENTER);
        space0.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Spasi Enter Kebawah
        XWPFParagraph space10 = doc.createParagraph();
        space10.setFirstLineIndent(400);
        space10.setAlignment(ParagraphAlignment.CENTER);
        space10.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //TTD Tanggal
        try {
            String pattern = "dd MMMMM yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String tglHasil = simpleDateFormat.format(currentDate);

            XWPFParagraph ttdLast = doc.createParagraph();
            ttdLast.setAlignment(ParagraphAlignment.RIGHT);
            ttdLast.setWordWrapped(true);
            XWPFRun r50 = ttdLast.createRun();
            String t50 = "Jakarta , " + tglHasil;
            r50.setText(t50);
            r50.setFontSize(12);

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        //Spasi Enter Kebawah
        XWPFParagraph space1213 = doc.createParagraph();
        space1213.setFirstLineIndent(400);
        space1213.setAlignment(ParagraphAlignment.CENTER);
        space1213.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Spasi Enter Kebawah
        XWPFParagraph space12132 = doc.createParagraph();
        space12132.setFirstLineIndent(400);
        space12132.setAlignment(ParagraphAlignment.CENTER);
        space12132.setWordWrapped(true);
        //End Spasi Enter Kebawah

        for (Object[] dataHistory : dataKaryawan) {

            XWPFParagraph ttdName = doc.createParagraph();
            ttdName.setAlignment(ParagraphAlignment.RIGHT);
            ttdName.setWordWrapped(true);
            setSingleLineSpacing(ttdName);
            XWPFRun r59 = ttdName.createRun();
            String t59 = "" + dataHistory[5];
            r59.setText(t59);
            r59.setBold(true);
            r59.setUnderline(UnderlinePatterns.SINGLE);
            r59.setFontSize(12);
        }

        XWPFParagraph ttdRelation = doc.createParagraph();
        ttdRelation.setAlignment(ParagraphAlignment.RIGHT);
        ttdRelation.setWordWrapped(true);
        setSingleLineSpacing(ttdRelation);
        XWPFRun r60 = ttdRelation.createRun();
        String t60 = "Relation Manager";
        r60.setText(t60);
        r60.setFontSize(12);

        XWPFParagraph namaPerusahaan = doc.createParagraph();
        namaPerusahaan.setAlignment(ParagraphAlignment.RIGHT);
        namaPerusahaan.setWordWrapped(true);
        setSingleLineSpacing(namaPerusahaan);
        XWPFRun r61 = namaPerusahaan.createRun();
        String t61 = "PT. Mitra Integrasi Informatika";
        r61.setText(t61);
        r61.setFontSize(12);

        //Write
        String NIK_Requester = "9999";
        String File_name = "DocumentRequest_PENUGASAN" + NIK_Requester + ".docx";
        String file = File_name;
        //lokal & dev
        //FileOutputStream out = new FileOutputStream(file);
        //production
        FileOutputStream out = new FileOutputStream("/opt/tomcat/webapps/uploadedFiles/bin/"+file);
        doc.write(out);
        out.close();

        try {
            response.setContentType("application/vnd.ms-docx");
            response.setHeader("Content-Disposition", "attachment; filename=DocumentRequest_PENUGASAN.docx");
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

            System.out.println("DocumentRequestPenugasan.docx written successfully on disk.");
        } catch (Exception e) {
            e.printStackTrace();
        }
//end write
        // write to a docx file
//        FileOutputStream fo = null;
//
//        try {
//            FileOutputStream out = new FileOutputStream(new File("HowtodoinJava.docx"));
//            response.setContentType("application/vnd.ms-word");
//            response.setHeader("Content-Disposition", "attachment; filename=HowtodoinJava.docx");
//            response.setHeader("Content-Transfer-Encoding", "binary");
//            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
//            doc.write(out);
//            out.close();
//            bos.close();
//            response.flushBuffer();
//
//            System.out.println("Document.docx written successfully on disk.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            // create .docx file
//            fo = new FileOutputStream(outputFileName);
//
//            // write to the .docx file
//            doc.write(fo);
//        } catch (IOException e) {
//        }
    }
    
    public static void setSingleLineSpacing(XWPFParagraph para) {
        CTPPr ppr = para.getCTP().getPPr();
        if (ppr == null) {
            ppr = para.getCTP().addNewPPr();
        }
        CTSpacing spacing = ppr.isSetSpacing() ? ppr.getSpacing() : ppr.addNewSpacing();
        spacing.setAfter(BigInteger.valueOf(0));
        spacing.setBefore(BigInteger.valueOf(0));
        spacing.setLineRule(STLineSpacingRule.AUTO);
        spacing.setLine(BigInteger.valueOf(240));
    }
}
