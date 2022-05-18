/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.exportExcel;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.xmlbeans.XmlException;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

/**
 *
 * @author alexa
 */
public class ExportDocAlex {

    public static void exportDOC1() throws IOException, InvalidFormatException {
        CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream("TemplateDOC.doc"));
        FileOutputStream fos = new FileOutputStream("test2.pdf");

        document = replaceTextFor(document, "NAMA_PESERTA", "Endiko Juan chandra");
        document = replaceTextFor(document, "TANDA_TGN", "Endriko");
        document = replaceTextFor(document, "JUDUL_LATIHAN", "\"Pemakaian Bootstrap 02020\"");
        document = replaceTextFor(document, "TANGGAL_ACARA", "January 2020");
        document = replaceTextFor(document, "TAHUN", "2020");

//        document.write(fos);
        PdfOptions options = null;
        PdfConverter.getInstance().convert(document, fos, options);
        fos.flush();
        fos.close();
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
