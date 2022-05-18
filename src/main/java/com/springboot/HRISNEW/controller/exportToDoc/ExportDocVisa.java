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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

/**
 *
 * @author Dharta
 */
public class ExportDocVisa {

    public static void exportDocVia(final Date currentDate, List<Object[]> dataKaryawan, HttpServletResponse response) throws FileNotFoundException, IOException {
        // Judul
        XWPFDocument doc = new XWPFDocument();
        try {
            String converted = currentDate.toString();
            String pattern = "dd MMMMM yyyy";
            String sDate = converted;
            SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dateNow = formatter6.parse(sDate);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String tglHasil = simpleDateFormat.format(dateNow);

            XWPFParagraph ttdLast = doc.createParagraph();
            ttdLast.setAlignment(ParagraphAlignment.RIGHT);
            ttdLast.setWordWrapped(true);
            XWPFRun r50 = ttdLast.createRun();
            String t50 = "" + tglHasil;
            r50.setText(t50);
            r50.setFontSize(12);

            for (Object[] dataHistory : dataKaryawan) {
                XWPFParagraph para1 = doc.createParagraph();
                para1.setAlignment(ParagraphAlignment.LEFT);
                String string1 = "No ." + dataHistory[5];
                XWPFRun para1Run = para1.createRun();
                para1Run.setText(string1);
                para1Run.setFontSize(12);
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
        p3.setAlignment(ParagraphAlignment.LEFT);
        p3.setWordWrapped(true);
        XWPFRun r3 = p3.createRun();
        String t3 = "(Reception / Name)";
        r3.setText(t3);
        r3.setFontSize(12);

        XWPFParagraph p4 = doc.createParagraph();
        p4.setAlignment(ParagraphAlignment.LEFT);
        p4.setWordWrapped(true);
        XWPFRun r4 = p4.createRun();
        String t4 = "";
        r4.setText(t4);
        r4.setFontSize(12);

        //Spasi Enter Kebawah
        XWPFParagraph spas = doc.createParagraph();
        spas.setFirstLineIndent(400);
        spas.setAlignment(ParagraphAlignment.CENTER);
        spas.setWordWrapped(true);
        //End Spasi Enter Kebawah

        XWPFParagraph dear = doc.createParagraph();
        dear.setAlignment(ParagraphAlignment.LEFT);
        dear.setWordWrapped(true);
        XWPFRun run = dear.createRun();
        String dears = "Dear Sirs,";
        run.setText(dears);
        run.setFontSize(12);

        //Spasi Enter Kebawah
        XWPFParagraph spas2 = doc.createParagraph();
        spas2.setFirstLineIndent(400);
        spas2.setAlignment(ParagraphAlignment.CENTER);
        spas2.setWordWrapped(true);
        //End Spasi Enter Kebawah

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r1 = p1.createRun();
        String title = "Subject : Visa Application";
        r1.setText(title);
        r1.setFontSize(12);
        r1.setUnderline(UnderlinePatterns.SINGLE);

        XWPFParagraph dis = doc.createParagraph();
        dis.setAlignment(ParagraphAlignment.LEFT);
        dis.setWordWrapped(true);
        XWPFRun runwe = dis.createRun();
        String disss = "This is to certify that :";
        runwe.setText(disss);
        runwe.setFontSize(12);

        // Nama
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph p00 = doc.createParagraph();
            p00.setFirstLineIndent(500);
            p00.setAlignment(ParagraphAlignment.LEFT);
            p00.setWordWrapped(true);
            String string1 = "Name                               : " + dataHistory[0];
            XWPFRun para1Run = p00.createRun();
            para1Run.setText(string1);
            para1Run.setFontSize(12);
            para1Run.setBold(true);
        }

        //No Passport
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph p01 = doc.createParagraph();
            p01.setFirstLineIndent(500);
            p01.setAlignment(ParagraphAlignment.LEFT);
            p01.setWordWrapped(true);
            String string2 = "Passport Number          : " + dataHistory[3];
            XWPFRun para1Run = p01.createRun();
            para1Run.setText(string2);
            para1Run.setFontSize(12);
            para1Run.setBold(true);
        }

        //Address
        for (Object[] dataHistory : dataKaryawan) {
            XWPFParagraph p02 = doc.createParagraph();
            p02.setFirstLineIndent(500);
            p02.setAlignment(ParagraphAlignment.LEFT);
            p02.setWordWrapped(true);
            String string3 = "Address                           : " + dataHistory[2];
            XWPFRun para1Run = p02.createRun();
            para1Run.setText(string3);
            para1Run.setFontSize(12);
            para1Run.setBold(true);
        }

        try {

            for (Object[] dataHistory : dataKaryawan) {

                String converted = dataHistory[4].toString();
                String pattern = "dd MMMMM yyyy";
                String sDate = converted;
                SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateNow = formatter6.parse(sDate);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                String joinDate = simpleDateFormat.format(dateNow);

                XWPFParagraph para5 = doc.createParagraph();
                para5.setAlignment(ParagraphAlignment.LEFT);

                para5.setWordWrapped(true);
                String t5 = "has been employed by PT. Mitra Integrasi Informatika since " + joinDate + " as " + dataHistory[1] + " He/she will be visiting for " + dataHistory[6] + ". All travelling expenses accured will be home by his/herself.";
                XWPFRun r5 = para5.createRun();
                r5.setText(t5);
                r5.setFontSize(12);
            }
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        XWPFParagraph pp23 = doc.createParagraph();
        pp23.setAlignment(ParagraphAlignment.LEFT);
        pp23.setWordWrapped(true);
        XWPFRun r90 = pp23.createRun();
        String tutup = "I will guarantee that as soon as her trip is over. He/She will return to Indonesia without seeking for any kind employment or permanent residence and promise to obey every regulation in your country.";
        r90.setText(tutup);
        r90.setFontSize(12);

        XWPFParagraph pp2 = doc.createParagraph();
        pp2.setAlignment(ParagraphAlignment.LEFT);
        pp2.setWordWrapped(true);
        XWPFRun rpp2 = pp2.createRun();
        String uTy = "I would appreciate if you could grant the tourist visa for him/her. Thank you.";
        rpp2.setText(uTy);
        rpp2.setFontSize(12);

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

        XWPFParagraph ttdRelation = doc.createParagraph();
        ttdRelation.setAlignment(ParagraphAlignment.LEFT);
        ttdRelation.setWordWrapped(true);
        XWPFRun r60 = ttdRelation.createRun();
        String t60 = "Your sincerely,";
        r60.setText(t60);
        r60.setFontSize(12);

        XWPFParagraph namaPerusahaan = doc.createParagraph();
        namaPerusahaan.setAlignment(ParagraphAlignment.LEFT);
        namaPerusahaan.setWordWrapped(true);
        XWPFRun r61 = namaPerusahaan.createRun();
        String t61 = "PT. Mitra Integrasi Informatika";
        r61.setText(t61);
        r61.setFontSize(12);

        //Spasi Enter Kebawah
        XWPFParagraph space12134 = doc.createParagraph();
        space12134.setFirstLineIndent(400);
        space12134.setAlignment(ParagraphAlignment.CENTER);
        space12134.setWordWrapped(true);
        //End Spasi Enter Kebawah

        //Spasi Enter Kebawah
        XWPFParagraph space12136 = doc.createParagraph();
        space12136.setFirstLineIndent(400);
        space12136.setAlignment(ParagraphAlignment.CENTER);
        space12136.setWordWrapped(true);
        //End Spasi Enter Kebawah

        XWPFParagraph namaTTD = doc.createParagraph();
        namaTTD.setAlignment(ParagraphAlignment.LEFT);
        namaTTD.setWordWrapped(true);
        XWPFRun rNamaTTD = namaTTD.createRun();
        String tNamaTTD = "Ridwan Setiawan";
        rNamaTTD.setText(tNamaTTD);
        rNamaTTD.setBold(true);
        rNamaTTD.setUnderline(UnderlinePatterns.SINGLE);
        rNamaTTD.setFontSize(12);

        XWPFParagraph posisiTTD = doc.createParagraph();
        posisiTTD.setAlignment(ParagraphAlignment.LEFT);
        posisiTTD.setWordWrapped(true);
        XWPFRun rPosisiTTD = posisiTTD.createRun();
        String tPosisiTTD = "HR Manager";
        rPosisiTTD.setText(tPosisiTTD);
        rPosisiTTD.setFontSize(12);

        //Write
        String NIK_Requester = "9999";
        String File_name = "DocumentRequest_Visa" + NIK_Requester + ".docx";
        String file = File_name;
        //lokal & dev
        //FileOutputStream out = new FileOutputStream(file);
        //production
        FileOutputStream out = new FileOutputStream("/opt/tomcat/webapps/uploadedFiles/bin/" + file);
        doc.write(out);
        out.close();

        try {
            response.setContentType("application/vnd.ms-docx");
            response.setHeader("Content-Disposition", "attachment; filename=DocumentRequest_Visa.docx");
            response.setHeader("Content-Transfer-Encoding", "binary");
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            //lokal & dev
            //FileInputStream fis = new FileInputStream(File_name);
            //production
            FileInputStream fis = new FileInputStream("/opt/tomcat/webapps/uploadedFiles/bin/" + File_name);
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
    }
}
