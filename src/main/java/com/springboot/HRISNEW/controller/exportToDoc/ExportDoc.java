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
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSpacing;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STLineSpacingRule;

/**
 *
 * @author Dharta
 */
public class ExportDoc {

    public static void exportDoc(final Date currentDate, List<Object[]> dataKaryawan, HttpServletResponse response) throws FileNotFoundException, IOException {
        // Judul
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        setSingleLineSpacing(p1);
        XWPFRun r1 = p1.createRun();
        String title = "SURAT KETERANGAN";
        r1.setText(title);
        r1.setBold(true);
        r1.setFontSize(20);
        r1.setUnderline(UnderlinePatterns.SINGLE);

        // Insert page break after this paragraph
        // p2.setPageBreak(true);
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph para1 = doc.createParagraph();
            para1.setAlignment(ParagraphAlignment.CENTER);
            para1.setWordWrapped(true);
//            para1.setFirstLineIndent(400);
            String string1 = "No ." + dataHistory[12];
            XWPFRun para1Run = para1.createRun();
            para1Run.setText(string1);
            para1Run.setFontSize(14);
        }

        //Spasi Enter Kebawah
        XWPFParagraph p2 = doc.createParagraph();
        p2.setFirstLineIndent(400);
        p2.setAlignment(ParagraphAlignment.CENTER);
        p2.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Paragraph 1
        XWPFParagraph p3 = doc.createParagraph();
        p3.setAlignment(ParagraphAlignment.LEFT);
        p3.setWordWrapped(true);
        XWPFRun r3 = p3.createRun();
        String t3 = "Saya yang bertanda tangan di bawah ini menerangkan bahwa :";
        r3.setText(t3);
        r3.setFontSize(12);

        // Nama
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph p4 = doc.createParagraph();
            p4.setFirstLineIndent(400);
            p4.setAlignment(ParagraphAlignment.LEFT);
            p4.setWordWrapped(true);
            String string1 = "Nama                                 : " + dataHistory[1];
            XWPFRun para1Run = p4.createRun();
            para1Run.setText(string1);
            para1Run.setFontSize(12);
            para1Run.setBold(true);
        }

        //Nik
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph p5 = doc.createParagraph();
            p5.setFirstLineIndent(400);
            p5.setAlignment(ParagraphAlignment.LEFT);
            p5.setWordWrapped(true);
            String string1 = "Nik                                      : " + dataHistory[0];
            XWPFRun para1Run = p5.createRun();
            para1Run.setText(string1);
            para1Run.setFontSize(12);
            para1Run.setBold(true);
        }

        //Tempat, Tgl Lahiir
        try {
            for (Object[] dataHistory : dataKaryawan) {

                String converted = dataHistory[5].toString();
                String pattern = "dd MMMM yyyy";
                String sDate = converted;
                SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateNow = formatter6.parse(sDate);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String tglHasil = simpleDateFormat.format(dateNow);
                XWPFParagraph p6 = doc.createParagraph();
                p6.setFirstLineIndent(400);
                p6.setAlignment(ParagraphAlignment.LEFT);
                p6.setWordWrapped(true);
                String string1 = "Tempat/Tanggal Lahir    : " + dataHistory[4] + ", " + tglHasil;
                XWPFRun para1Run = p6.createRun();
                para1Run.setText(string1);
                para1Run.setFontSize(12);
                para1Run.setBold(true);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        //Jabatan
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph p7 = doc.createParagraph();
            p7.setFirstLineIndent(400);
            p7.setAlignment(ParagraphAlignment.LEFT);
            p7.setWordWrapped(true);
            String string1 = "Jabatan                              : " + dataHistory[3];
            XWPFRun para1Run = p7.createRun();
            para1Run.setText(string1);
            para1Run.setFontSize(12);
            para1Run.setBold(true);
        }

        //Alamat
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph p8 = doc.createParagraph();
            p8.setFirstLineIndent(400);
            p8.setAlignment(ParagraphAlignment.LEFT);
            p8.setWordWrapped(true);
            String string1 = "Alamat                               : " + dataHistory[6];
            XWPFRun para1Run = p8.createRun();
            para1Run.setText(string1);
            para1Run.setFontSize(12);
            para1Run.setBold(true);
        }

        //No KTP
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph p9 = doc.createParagraph();
            p9.setFirstLineIndent(400);
            p9.setAlignment(ParagraphAlignment.LEFT);
            p9.setWordWrapped(true);
            String string1 = "No KTP                               : " + dataHistory[2];
            XWPFRun para1Run = p9.createRun();
            para1Run.setText(string1);
            para1Run.setFontSize(12);
            para1Run.setBold(true);
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

                String converted = dataHistory[9].toString();
                String pattern = "dd MMMMM yyyy";
                String sDate = converted;
                SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateNow = formatter6.parse(sDate);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String tglHasil = simpleDateFormat.format(dateNow);
                XWPFParagraph para5 = doc.createParagraph();
                para5.setAlignment(ParagraphAlignment.BOTH);
                para5.setSpacingAfter(0);
                para5.setSpacingAfterLines(0);
                para5.setWordWrapped(true);
                String string1 = "Adalah benar karyawan tersebut bekerja di PT. Mitra Integrasi Informatika mulai tanggal " + tglHasil + " sampai sekarang.";
                XWPFRun para1Run = para5.createRun();
                para1Run.setText(string1);
                para1Run.setFontSize(12);
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

        for (Object[] dataHistory : dataKaryawan) {

            XWPFParagraph para6 = doc.createParagraph();
            para6.setAlignment(ParagraphAlignment.BOTH);
            para6.setSpacingAfter(0);
            para6.setSpacingAfterLines(0);
            para6.setWordWrapped(true);
            XWPFRun r6 = para6.createRun();
            String t6 = "Surat Keterangan ini dibuat untuk keperluan " + dataHistory[11];
            r6.setText(t6);
            r6.setFontSize(12);
            String t7 = ". Segala biaya yang timbul baik sekarang maupun dikemudian hari adalah sepenuhnya menjadi tanggung jawab pribadi.";
            r6.setText(t7);
            r6.setFontSize(12);
        }
        //Paragraph 3

        //Spasi Enter Kebawah
        XWPFParagraph space8 = doc.createParagraph();
        space8.setFirstLineIndent(400);
        space8.setAlignment(ParagraphAlignment.CENTER);
        space8.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Paragraph 8
        XWPFParagraph plast = doc.createParagraph();
        plast.setAlignment(ParagraphAlignment.BOTH);
        plast.setSpacingAfter(0);
        plast.setSpacingAfterLines(0);
        plast.setWordWrapped(true);
        XWPFRun r8 = plast.createRun();
        String t8 = "PT. Mitra Integrasi Informatika beserta pejabat yang menandatangani surat ini dibebaskan dari segala tanggung jawab dan tuntutan di masa mendatang atas tindakan dan/atau kejadian yang berkaitan dengan nama di atas dan/atau atas dikeluarkannya surat keterangan ini.";
        r8.setText(t8);
        r8.setFontSize(12);

        //Spasi Enter Kebawah
        XWPFParagraph space7 = doc.createParagraph();
        space7.setFirstLineIndent(400);
        space7.setAlignment(ParagraphAlignment.CENTER);
        space7.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Paragraph Last
        XWPFParagraph parag7 = doc.createParagraph();
        parag7.setAlignment(ParagraphAlignment.BOTH);
        parag7.setSpacingAfter(0);
        parag7.setSpacingAfterLines(0);
        parag7.setWordWrapped(true);
        XWPFRun r7 = parag7.createRun();
        String t7 = "Surat Keterangan ini berlaku selama 30 (tiga puluh) hari setelah dikeluarkannya surat ini.";
        r7.setText(t7);
        r7.setFontSize(12);

        //Spasi Enter Kebawah
        XWPFParagraph space0 = doc.createParagraph();
        space0.setFirstLineIndent(400);
        space0.setAlignment(ParagraphAlignment.CENTER);
        space0.setWordWrapped(true);
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
        XWPFParagraph space11 = doc.createParagraph();
        space11.setFirstLineIndent(400);
        space11.setAlignment(ParagraphAlignment.CENTER);
        space11.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Spasi Enter Kebawah
        XWPFParagraph space12 = doc.createParagraph();
        space12.setFirstLineIndent(400);
        space12.setAlignment(ParagraphAlignment.CENTER);
        space12.setWordWrapped(true);
        //End Spasi Enter Kebawah

        XWPFParagraph namaTTD = doc.createParagraph();
        namaTTD.setAlignment(ParagraphAlignment.RIGHT);
        namaTTD.setWordWrapped(true);
        XWPFRun rNamaTTD = namaTTD.createRun();
        String tNamaTTD = "Ridwan Setiawan";
        rNamaTTD.setText(tNamaTTD);
        rNamaTTD.setBold(true);
        rNamaTTD.setUnderline(UnderlinePatterns.SINGLE);
        rNamaTTD.setFontSize(12);

        XWPFParagraph posisiTTD = doc.createParagraph();
        posisiTTD.setAlignment(ParagraphAlignment.RIGHT);
        posisiTTD.setWordWrapped(true);
        XWPFRun rPosisiTTD = posisiTTD.createRun();
        String tPosisiTTD = "HR Manager";
        rPosisiTTD.setText(tPosisiTTD);
        rPosisiTTD.setFontSize(12);

        //Write
        String NIK_Requester = "9999";
        String File_name = "DocumentRequest_" + NIK_Requester + ".docx";
        String file = File_name;
        //lokal & dev
        //FileOutputStream out = new FileOutputStream(file);
        //production
        FileOutputStream out = new FileOutputStream("/opt/tomcat/webapps/uploadedFiles/bin/"+file);
        doc.write(out);
        out.close();

        try {
            response.setContentType("application/vnd.ms-docx");
            response.setHeader("Content-Disposition", "attachment; filename=DocumentRequest.docx");
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

            System.out.println("DocumentRequest.docx written successfully on disk.");
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
