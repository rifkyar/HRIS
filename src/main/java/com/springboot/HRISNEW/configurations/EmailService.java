/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.configurations;

import com.springboot.HRISNEW.implementservices.MParticipantsRegistrationServiceImpl;
import freemarker.template.Configuration;
import freemarker.template.Template;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

/**
 *
 * @author HARRY-PC
 */
@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    @Autowired
    private Configuration freemarkerConfig;

    @Autowired
    private MParticipantsRegistrationServiceImpl MPRSI;

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendNotificationService(String email, String context) throws Exception {

        // Send template message
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Map<String, Object> model = new HashMap();
        model.put("user", "qpt");
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("emailReset.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(context);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("HRIS - RESET PASSWORD");

        javaMailSender.send(message);
    }

    public void sendNotificationService(String email, String name, String target, Date from, Date to, String note, int nik) throws Exception {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fromdate = df.format(from);
        String todate = df.format(to);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        System.out.println(to + " -" + from);
        Map<String, Object> model = new HashMap();
        model.put("user", name);
        model.put("target", target);
        model.put("from", fromdate);
        model.put("to", todate);
        model.put("note", note);
        model.put("nik", Integer.toString(nik));
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("email.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
//        helper.setFrom("semestertuati@gmail.com");
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Leave Request");

        javaMailSender.send(message);
//        javaMailSender.send(mail);
    }

    public void sendResponse(String leave, String response, String user, String email) throws Exception {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        Map<String, Object> model = new HashMap();
        model.put("user", user);
        model.put("Response", response);
        model.put("Leave", leave);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("response.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);

//        helper.setFrom("semestertuati@gmail.com");
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Leave Request Response");

        javaMailSender.send(message);
    }

    public void sendNotificationServiceOvertime(String so_id, String id_request, String email, String name, String target,
            String position, String customer, String Picname,
            String picposition, String totalhour, String Period,
            int nik) throws Exception {
        System.out.println("totalhour " + totalhour);
        String alertCuti = null;
        double totaltemp = Double.parseDouble(totalhour);
        System.out.println("totaltemp " + totaltemp);
        if (totaltemp > 40.0) {
            alertCuti = "JUMLAH CUTI LEBIH DARI 40 JAM. HARAP MENJADI PERHATIAN !";
        } else {
            alertCuti = "-";
        }

        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);
        System.out.println("target " + target);
        System.out.println("position " + position);
        System.out.println("customer " + customer);
        System.out.println("Picname " + Picname);
        System.out.println("picposition " + picposition);
        System.out.println("totalhour " + totalhour);
        System.out.println("Period " + Period);
        System.out.println("nik " + nik);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("user", name);
        model.put("target", target);
        model.put("nik", Integer.toString(nik));
        model.put("position", position);
        model.put("Customer", customer);
        model.put("Picname", Picname);
        model.put("picposition", picposition);
        model.put("totalhour", totalhour);
        model.put("Period", Period);
        model.put("alertCuti", alertCuti);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("emailRequestOT.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Overtime Request - " + id_request + " - " + nik + " - " + target + " - " + so_id + " - " + customer);

        javaMailSender.send(message);
    }

    public void sendNotificationServiceOvertimeDone(String email, String name, String otID) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);
        System.out.println("otID " + otID);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("user", name);
        model.put("IdOT", otID);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("emailDoneOT.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Overtime Request - " + name + " - Request Overtime ID - " + otID);

        javaMailSender.send(message);
    }

    public void sendNotificationServiceOvertimeReject(String email, String name, String otID,
            String rmName, String remarks) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);
        System.out.println("otID " + otID);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("user", name);
        model.put("IdOT", otID);
        model.put("rmName", rmName);
        model.put("remarks", remarks);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("emailRejectOvertime.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Overtime Request - " + name + " - Request Overtime ID - " + otID);

        javaMailSender.send(message);
    }

//    , String training_title, String training_date
    public void sendNotificationServiceTrainingCancelation(String email, String name, String training_title, String training_date, String reason) throws Exception {
//        System.out.println("SEND MAIL");
//        System.out.println("email " + email);
//        System.out.println("name " + name);
//        System.out.println("title " + training_title);
//        System.out.println("title " + training_date);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("training_title", training_title);
        model.put("training_date", training_date);
        model.put("reason", reason);
//        model.put("remarks", remarks);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailTrainingCancelation.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
//        helper.setSubject("Overtime Request - " + name + " - Request Overtime ID - " + otID);
        helper.setSubject("A Training has been canceled");

        javaMailSender.send(message);
    }

    public void sendNotificationChangeSummaryDone(String email, String name) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("emailDoneChangeSummary.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Change Summary Request - " + name);

        javaMailSender.send(message);
    }

    public void sendNotificationServiceDetailRegistration(String email, String name, String qrPath) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);
//        System.out.println("title " + training_title);
//        System.out.println("title " + training_date);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
//        MimeBodyPart qrcode = new MimeBodyPart();
//        qrcode.attachFile("/templates/qrcode/Earth.jpg");

//        model.put("remarks", remarks);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailDetailRegistrationandQR.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        FileSystemResource file = new FileSystemResource(new File(qrPath));

        helper.setTo(email);
        helper.setText(text, true);
        helper.addAttachment("QRCODE.png", file);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Thank you for your registration");

        javaMailSender.send(message);
    }

    public void sendNotificationChangeSummaryRequest(String email, String name, String empName, String nik) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("hr name " + name);
        System.out.println("name " + empName);
        System.out.println("nik " + nik);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("empName", empName);
        model.put("nik", nik);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("emailChangeSummary.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("New Change Summary Request - " + nik);

        javaMailSender.send(message);
    }

    public void notifikasiMSHRDone(String email, String namee, String adminName) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + namee);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", namee);
        model.put("admName", adminName);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("doneEmailSuratKeteranganMSHR.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");

        helper.setSubject("Request Surat Keterangan");

        javaMailSender.send(message);
    }

    public void notifikasiReject(String email, String namee, String adminName, String notesReject) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + namee);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", namee);
        model.put("admName", adminName);
        model.put("notes", notesReject);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("rejectEmailSurat.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");

        helper.setSubject("Request Surat Keterangan");

        javaMailSender.send(message);
    }

    public void sendNotificationServiceRequesterRegistration(String email, String name, String training_title, Integer registrator_nik) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);
        System.out.println("title " + training_title);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("training_title", training_title);
        model.put("nik", Integer.toString(registrator_nik));

//        model.put("remarks", remarks);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailNotifikasiRegistrasiRequester.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
//        helper.setSubject("Overtime Request - " + name + " - Request Overtime ID - " + otID);
        helper.setSubject("Someone has registered for a training");

        javaMailSender.send(message);

    }
    
    public void sendNotificationServiceAttendanceRegistration(String email, String name, String training_title,String training_time, Integer id, Integer registrator_nik, String link_Confirm, String link_Decline) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);
        System.out.println("title " + training_title);
        System.out.println("id " + id);
//        System.out.println("title " + training_date);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("training_title", training_title);
        //convert int to string
        model.put("id", Integer.toString(id));
        model.put("number", Integer.toString(registrator_nik));
        model.put("confirm", link_Confirm);
        model.put("decline", link_Decline);

//        model.put("remarks", remarks);
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailPresenceConfirmation.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
//        helper.setSubject("Overtime Request - " + name + " - Request Overtime ID - " + otID);
        helper.setSubject(training_title+ " , " +training_time);

        javaMailSender.send(message);

    }

    public void sendNotificationChangeSummaryRequestHR(String email, String name, String empName, String nik) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("hr name " + name);
        System.out.println("name " + empName);
        System.out.println("nik " + nik);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("empName", empName);
        model.put("nik", nik);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/");

        Template t = freemarkerConfig.getTemplate("emailChangeSummary.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("New Change Summary Request - " + nik);

        javaMailSender.send(message);
    }

    public void sendNotificationServiceFeedbackForm(String email, String name, String training_title, Integer transaction, Integer nik, String feedbackLink) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);
        System.out.println("training title " + training_title);

        String customerId = MPRSI.getCustomerIdbyId(transaction, nik);
        String trainerName = MPRSI.getTrainingTrainerNamebyId(transaction);
        String schedule = MPRSI.getTrainingSchedulebyId(transaction);

        //Split Date dan Time
        String converted = schedule;
        String[] parts = converted.split(" ");
        String part1 = parts[0];
        String part2 = parts[1];

        //Reformat Date
        String[] dateParts = part1.split("-");
        String datePart1 = dateParts[0];
        String datePart2 = dateParts[1];
        String datePart3 = dateParts[2];

        System.out.println("Date Part 1 : " + datePart1);
        System.out.println("Date Part 2 : " + datePart2);
        System.out.println("Date Part 3 : " + datePart3);

        String formed = datePart3 + "-" + datePart2 + "-" + datePart1;
        System.out.println("Date formed : " + formed);

        String md5Hex = DigestUtils
                .md5Hex(name);

        System.out.println("md5Hex = " + md5Hex);
        
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("materi", training_title);
        model.put("link", feedbackLink + md5Hex + "/" + transaction);
        model.put("customer", customerId);
        model.put("tanggal", formed);
        model.put("trainer", trainerName);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailFeedbackForm.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Thank you for your registration");

        javaMailSender.send(message);
    }

    public void sendNotificationServiceWaitingList(String email, String name,String training_title, String training_time) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailWaitingList.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject(training_title+ " , " +training_time);

        javaMailSender.send(message);

    }

    public void sendNotificationSuratKeterangan(String email, String name, String Enik, String typee) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("nik", Enik);
        model.put("type", typee);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailSuratKeteranganMSHR.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Request Surat Keterangan");

        javaMailSender.send(message);
    }

    public void sendNotificationServiceRegistrationRejected(String email, String name) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailRejectRegistration.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Your registration has been rejected");

        javaMailSender.send(message);
    }

    public void sendNotificationSuratPenugasan(String emailAdmin, String nameRequester, String typee) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + emailAdmin);
        System.out.println("name " + nameRequester);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", nameRequester);
        model.put("type", typee);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailSuratKeteranganTipePenugasan.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(emailAdmin);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");

        helper.setSubject("Request Surat Penugasan");

        javaMailSender.send(message);
    }

    public void notifikasiPenugasanDone(String email, String namee, String adminName) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + namee);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", namee);
        model.put("admName", adminName);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("doneEmailSuratTipePenugasan.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");

        helper.setSubject("Request Surat Penugasan");

        javaMailSender.send(message);
    }

    public void sendNotificationServiceNewTrainingSchedule(String[] email, String training_title, String training_date, String training_time, Integer training_quota, String training_trainer) throws Exception {
        
        System.out.println("SEND MAIL");
        for(int i = 0; i < email.length ; i++){
        System.out.println("email " + email[i]);
        }
        System.out.println("title " + training_title);
        System.out.println("trainer " + training_trainer);
        System.out.println("date " + training_date);
        System.out.println("time " + training_time);
        System.out.println("date " + training_quota);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("training_title", training_title);
        model.put("training_date", training_date);
        model.put("training_time", training_time);
        model.put("training_quota", training_quota);
        model.put("training_trainer", training_trainer);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailTrainingNotification.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("New Training Has been Scheduled");

        javaMailSender.send(message);
    }

    public void sendNotificationServiceNewTrainingRequest(String email, String requester_name, String nik) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("requester_name", requester_name);
        model.put("nik", nik);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailNewTrainingRequest.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("New Training has been requested");

        javaMailSender.send(message);
    }

    public void sendNotificationServiceRequestAccepted(String email, String name, String training_title) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("training_title", training_title);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailAcceptTrainingRequest.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Your request has been Accepted");

        javaMailSender.send(message);
    }

    public void sendNotificationServiceRequestRejected(String email, String name, String remark) throws Exception {
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("reason", remark);

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");

        Template t = freemarkerConfig.getTemplate("emailRejectTrainingRequest.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Your request has been rejected");

        javaMailSender.send(message);
    }
    
    public void sendBirthdayNotification(String email, String name, String rmMail) throws Exception {
        
//        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy");
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = inputFormat.parse(dateofbirth);
//        String outputText = outputFormat.format(date);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMMM yyyy");  
        LocalDateTime now = LocalDateTime.now();  
        
        System.out.println("SEND MAIL");
        System.out.println("email " + email);
        System.out.println("name " + name);
        System.out.println("rmMail " + rmMail);
        System.out.println("tanggal " + dtf.format(now));

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        Map<String, Object> model = new HashMap();
        model.put("name", name);
        model.put("tanggal", dtf.format(now));

        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates/email");
//
        Template t = freemarkerConfig.getTemplate("emailBirthday.html");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

        helper.setTo(email);
        helper.setText(text, true);
        helper.setCc(rmMail);
        helper.setFrom("sakura@ms.mii.co.id");
        helper.setSubject("Happy Birthday" + "-" + name);

        javaMailSender.send(message);

    }
}
