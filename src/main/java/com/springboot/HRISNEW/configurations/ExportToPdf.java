/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.configurations;

import com.springboot.HRISNEW.configurations.CustomXWPFDocument;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import java.text.ParseException;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author User
 */
public class ExportToPdf {

    public static void exportPDF1(HttpServletResponse response, String requester_name, String training_title, String training_date, String training_id,String feedback_id, String empl_nik) throws IOException, InvalidFormatException, ParseException {
        //CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream("TemplateDOC.doc"));
        CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream("/opt/tomcat/webapps/uploadedFiles/bin/TemplateDOC.doc"));


        document = replaceTextFor(document, "NAMA_PESERTA", requester_name);
//        document = replaceTextFor(document, "TANDA_TGN", "Endriko TTD"); "\"Pemakaian Bootstrap 02020\"" "" + training_title + ""
        document = replaceTextFor(document, "JUDUL_LATIHAN", "\" " + training_title + " \"");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = inputFormat.parse(training_date);
        String outputText = outputFormat.format(date);
        document = replaceTextFor(document, "TANGGAL_ACARA", outputText);
        String [] dateParts = training_date.split("-");
        String day = dateParts[0];
        String month = dateParts[1];
        String year = dateParts[2];
        //document = replaceTextFor(document, "TAHUN", year);
        document = replaceTextFor(document, "Kode_seti", "MII/L&D/" + year + "/" + month + "/" + feedback_id);
//        document.write(fos);
        PdfOptions options = null;
//        PdfConverter.getInstance().convert(document, fos, options);
//        fos.flush();
//        fos.close();

        String NIK_Requester = empl_nik;
        String File_name = "Sertifikat" + NIK_Requester + ".pdf";
        String file = File_name;
        //lokal & dev
        //FileOutputStream out = new FileOutputStream(file);
        //production
        FileOutputStream out = new FileOutputStream("/opt/tomcat/webapps/uploadedFiles/bin/"+file);
        PdfConverter.getInstance().convert(document, out, options);
//        document.write(out);
        out.close();
        try {
            String title = training_title.replaceAll(",",".");
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=Sertifikat_" + title + "_" + empl_nik + ".pdf");
            response.setHeader("Content-Transfer-Encoding", "binary");
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            //lokal & dev
            //FileInputStream fis = new FileInputStream(File_name);
            //production
            FileInputStream fis = new FileInputStream("/opt/tomcat/webapps/uploadedFiles/bin/"+File_name);
            PdfConverter.getInstance().convert(document, bos, options);
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

    private static CustomXWPFDocument replaceTextFor(CustomXWPFDocument doc, String findText, String replaceText) {
        doc.getParagraphs().forEach(p -> {
            p.getRuns().forEach(run -> {
                String text = run.text();
                if (text.contains(findText)) {
                    run.setText(text.replace(findText, replaceText), 0);
                }
            });
        });

        return doc;
    }

}
