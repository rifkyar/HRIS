/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.traininganddevelopment;

import com.springboot.HRISNEW.configurations.ExportToPdf;
import com.springboot.HRISNEW.configurations.ExcelExport;
import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.entities.MOthers;
import com.springboot.HRISNEW.entities.TrainingCatalogTransactions;
import com.springboot.HRISNEW.entities.TrainingFeedbackResponse;
import com.springboot.HRISNEW.entities.TrainingTransactions;
import com.springboot.HRISNEW.entities.TrainingUserRegistration;
import com.springboot.HRISNEW.implementservices.HistoryTrainingServiceImpl;
import com.springboot.HRISNEW.implementservices.MParticipantsRegistrationServiceImpl;
import com.springboot.HRISNEW.implementservices.MTrainingRequestServiceImpl;
import com.springboot.HRISNEW.implementservices.TCSSImpl;
import com.springboot.HRISNEW.implementservices.TrainingSchedulingServiceImpl;
import com.springboot.HRISNEW.implementservices.TrainingTransactionFilterServiceImpl;
import com.springboot.HRISNEW.implementservices.TrainingUserRegistrationServiceImpl;
import com.springboot.HRISNEW.implementservices.tbMParameterServiceImplement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.springboot.HRISNEW.configurations.QRCode;
import com.springboot.HRISNEW.entities.TbMMejakerja;
import com.springboot.HRISNEW.entities.TrRequestovertimedetail;
import com.springboot.HRISNEW.entities.TrainingCatalogs;
import com.springboot.HRISNEW.entities.TrainingFeedbackQuestions;
import com.springboot.HRISNEW.entities.TrainingUserRequests;
import com.springboot.HRISNEW.implementservices.ParticipantFeedbackFormServiceImpl;
import com.springboot.HRISNEW.implementservices.TFeedbackServiceImplement;
import com.springboot.HRISNEW.implementservices.TrainingMejaKerjaServiceImpl;
import com.springboot.HRISNEW.implementservices.TrainingUserRequestsServiceImpl;
import com.springboot.HRISNEW.repositories.tbMParameterRepo;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author User
 */
@Controller
public class TrainingController {

    @Autowired
    private QRCode qrCode;

    @Autowired
    private TCSSImpl TCSSI;

    @Autowired
    private TrainingSchedulingServiceImpl TSchedulingSI;

    @Autowired
    private HistoryTrainingServiceImpl HTSI;

    @Autowired
    private MParticipantsRegistrationServiceImpl MPRSI;

    @Autowired
    private MTrainingRequestServiceImpl MTrainingRSI;

    @Autowired
    private TrainingTransactionFilterServiceImpl TTFSI;

    @Autowired
    private ServiceApi serviceAPI;

    @Autowired
    private TrainingUserRegistrationServiceImpl TURSI;

    @Autowired
    private EmailService emailservice;

    @Autowired
    private TFeedbackServiceImplement TFSI;

    @Autowired
    private ParticipantFeedbackFormServiceImpl PFSI;

    @Autowired
    private TrainingUserRequestsServiceImpl TURequestsService;

    @Autowired
    private tbMParameterServiceImplement tbMParamService;
    
    @Autowired
    private TrainingMejaKerjaServiceImpl mejaKerjaServiceImpl;
//    DISPLAY-------------------------------------------------------------------------------------------------------------------------------------
    //TESTING----------------------------------------------------------------------------------------
    @GetMapping("/testing")
    public String testingpage() {

        return "/traininganddevelopment/training/Testing";
    }

    @GetMapping("/testingEmail")
    public String testingpageEmail() {

        return "/email/emailPresenceConfirmation";
    }

    @GetMapping("/testingSendingEmail")
    public String testingpageSendingEmail() {

        for (int i = 1; i - 1 < 18; i++) {
            System.out.println(i);
        }

        return "email/emailPresenceConfirmation";
    }

    //create date
    public String getDate(String date) throws ParseException {
        SimpleDateFormat StringToDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateGet = StringToDate.parse(date);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");
        return dateFormat.format(dateGet);
    }
    //----------------------------------------------------------------------------------------------

    @GetMapping("/trainingScheduling")
    public String TrainingSchedulingMenu(String myId, String id, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formattime = new SimpleDateFormat("HH:mm");
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        List<Object[]> dataTrainingOnline = new ArrayList<>();
        for (Object[] dataTrainingOnlineClass : TSchedulingSI.getDataTrainingOnlineClassAdmin()) {
            Object[] objects = new Object[11];
            objects[0] = dataTrainingOnlineClass[0];
            objects[1] = dataTrainingOnlineClass[1];
            objects[2] = dataTrainingOnlineClass[2];
            objects[3] = dataTrainingOnlineClass[3];
            objects[4] = formatdate.format(dataTrainingOnlineClass[4]);
            objects[5] = formattime.format(dataTrainingOnlineClass[4]);
            objects[6] = formatdate.format(dataTrainingOnlineClass[5]);
            objects[7] = formattime.format(dataTrainingOnlineClass[5]);
            objects[8] = dataTrainingOnlineClass[6];
            objects[9] = dataTrainingOnlineClass[7];
            objects[10] = dataTrainingOnlineClass[8];
            dataTrainingOnline.add(objects);
        }

        List<Object[]> detailTrainingOnline = new ArrayList<>();
        for (Object[] object : TSchedulingSI.getDetailDataTrainingOnlineAdmin()) {
            Object[] objects = new Object[9];
            objects[0] = object[0]; // id
            objects[1] = object[1]; // judul training
            objects[2] = object[2]; // nama trainer
            objects[3] = object[3]; // quota
            objects[4] = formatdate.format(object[4]); // tanggal (RAW)
            objects[5] = formattime.format(object[4]); // tanggal (RAW)
            objects[6] = object[5]; // link
            detailTrainingOnline.add(objects);
        }

        List<Object[]> dataTrainingOffline = new ArrayList<>();
        for (Object[] dataTrainingOfflineClass : TSchedulingSI.getDataTrainingOfflineClassAdmin()) {
            Object[] objects = new Object[11];
            objects[0] = dataTrainingOfflineClass[0];
            objects[1] = dataTrainingOfflineClass[1];
            objects[2] = dataTrainingOfflineClass[2];
            objects[3] = dataTrainingOfflineClass[3];
            objects[4] = formatdate.format(dataTrainingOfflineClass[4]);
            objects[5] = formattime.format(dataTrainingOfflineClass[4]);
            objects[6] = formatdate.format(dataTrainingOfflineClass[5]);
            objects[7] = formattime.format(dataTrainingOfflineClass[5]);
            objects[8] = dataTrainingOfflineClass[6];
            objects[9] = dataTrainingOfflineClass[7];
            objects[10] = dataTrainingOfflineClass[8];

            dataTrainingOffline.add(objects);
        }

        List<Object[]> detailTrainingOffline = new ArrayList<>();
        for (Object[] object : TSchedulingSI.getDetailDataTrainingOfflineAdmin()) {
            Object[] objects = new Object[9];
            objects[0] = object[0]; // id
            objects[1] = object[1]; // judul training
            objects[2] = object[2]; // nama trainer
            objects[3] = object[3]; // quota
            objects[4] = object[4]; // lokasi training
            objects[5] = formatdate.format(object[5]); // tanggal (RAW)
            objects[6] = formattime.format(object[5]); // tanggal (RAW)
            objects[7] = object[6]; // kebutuhan
            detailTrainingOffline.add(objects);
        }

        List<Object[]> dataTraining = new ArrayList<>();
        for (Object[] object : TSchedulingSI.getDataTrainingWithType()) {
            Object[] objects = new Object[11];
            objects[0] = object[0]; // id
            objects[1] = object[1]; // judul training
            objects[2] = object[2]; // training_time
            objects[3] = object[3]; // is_active
            objects[4] = formatdate.format(object[4]); // created_date
            objects[5] = formattime.format(object[4]); // created_time
            objects[6] = formatdate.format(object[5]); // updated_date
            objects[7] = formattime.format(object[5]); // updated_time
            objects[8] = object[6]; // created by
            objects[9] = object[7]; // updated by
            objects[10] = object[8]; // updated by

            dataTraining.add(objects);
        }

//        System.out.println("Email List No : " + i + "Value : " + emailList[i]);
        List<Object[]> detailTraining = new ArrayList<>();

        for (Object[] object : TSchedulingSI.getDetailScheduleAdmin()) {
            Object[] objects = new Object[9];
            objects[0] = object[0]; // id
            objects[1] = object[1]; // judul training
            objects[2] = object[2]; // nama trainer
            objects[3] = object[3]; // quota
            objects[4] = object[4]; // lokasi training
            objects[5] = formatdate.format(object[5]); // tanggal (RAW)
            objects[6] = formattime.format(object[5]); // tanggal (RAW)
            objects[7] = object[6]; // kebutuhan
            detailTraining.add(objects);
        }

        model.addAttribute("trainingListOffline", dataTrainingOffline);
        model.addAttribute("detailListOffline", detailTrainingOffline);

        model.addAttribute("trainingListOnline", dataTrainingOnline);
        model.addAttribute("detailListOnline", detailTrainingOnline);

        model.addAttribute("trainingList", dataTraining);
        model.addAttribute("detailList", detailTraining);

//        model.addAttribute("detailList", detailSchedule);
        return "traininganddevelopment/training/trainingScheduling";
    }

    @GetMapping("/manageRegistrationSchedule")
    public String manageRegistrationTrainingScheduleMenu(String myId, String id, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formattime = new SimpleDateFormat("HH:mm");
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }
        //API
//        model.addAttribute("name", serviceAPI.EmployeeByNik(id).getString("name"));
//        model.addAttribute("empl_nik", serviceAPI.EmployeeByNik(id).getString("empl_nik"));
//        model.addAttribute("email", serviceAPI.EmployeeByNik(id).getString("email"));
//        model.addAttribute("hp", serviceAPI.EmployeeByNik(id).getString("phone"));
//        model.addAttribute("position", serviceAPI.EmployeeByNik(id).getString("position"));
//        model.addAttribute("customerName", serviceAPI.EmployeeByNik(id).getString("customer_name"));

        //Data Training Online
//        List<Object[]> dataTrainingOnline = new ArrayList<>();
//        for (Object[] dataTrainingOnlineClass : TSchedulingSI.getDataTrainingOnlineClassAdmin()) {
//            Object[] objects = new Object[11];
//            objects[0] = dataTrainingOnlineClass[0];
//            objects[1] = dataTrainingOnlineClass[1];
//            objects[2] = dataTrainingOnlineClass[2];
//            objects[3] = dataTrainingOnlineClass[3];
//            objects[4] = formatdate.format(dataTrainingOnlineClass[4]);
//            objects[5] = formattime.format(dataTrainingOnlineClass[4]);
//            objects[6] = formatdate.format(dataTrainingOnlineClass[5]);
//            objects[7] = formattime.format(dataTrainingOnlineClass[5]);
//            objects[8] = dataTrainingOnlineClass[6];
//            objects[9] = dataTrainingOnlineClass[7];
//            objects[10] = dataTrainingOnlineClass[8];
//            dataTrainingOnline.add(objects);
//        }
//
//        //Detail Training Online
//        List<Object[]> detailTrainingOnline = new ArrayList<>();
//        for (Object[] object : TSchedulingSI.getDetailDataTrainingOnlineAdmin()) {
//            Object[] objects = new Object[9];
//            objects[0] = object[0]; // id
//            objects[1] = object[1]; // judul training
//            objects[2] = object[2]; // nama trainer
//            objects[3] = object[3]; // quota
//            objects[4] = formatdate.format(object[4]); // tanggal (RAW)
//            objects[5] = formattime.format(object[4]); // tanggal (RAW)
//            objects[6] = object[5]; // link
//            detailTrainingOnline.add(objects);
//        }
//
//        //Data Training Offline
//        List<Object[]> dataTrainingOffline = new ArrayList<>();
//        for (Object[] dataTrainingOfflineClass : TSchedulingSI.getDataTrainingOfflineClassAdmin()) {
//            Object[] objects = new Object[11];
//            objects[0] = dataTrainingOfflineClass[0];
//            objects[1] = dataTrainingOfflineClass[1];
//            objects[2] = dataTrainingOfflineClass[2];
//            objects[3] = dataTrainingOfflineClass[3];
//            objects[4] = formatdate.format(dataTrainingOfflineClass[4]);
//            objects[5] = formattime.format(dataTrainingOfflineClass[4]);
//            objects[6] = formatdate.format(dataTrainingOfflineClass[5]);
//            objects[7] = formattime.format(dataTrainingOfflineClass[5]);
//            objects[8] = dataTrainingOfflineClass[6];
//            objects[9] = dataTrainingOfflineClass[7];
//            objects[10] = dataTrainingOfflineClass[8];
//
//            dataTrainingOffline.add(objects);
//        }
//
//        //Detail Training Offline
//        List<Object[]> detailTrainingOffline = new ArrayList<>();
//        for (Object[] object : TSchedulingSI.getDetailDataTrainingOfflineAdmin()) {
//            Object[] objects = new Object[9];
//            objects[0] = object[0]; // id
//            objects[1] = object[1]; // judul training
//            objects[2] = object[2]; // nama trainer
//            objects[3] = object[3]; // quota
//            objects[4] = object[4]; // lokasi training
//            objects[5] = formatdate.format(object[5]); // tanggal (RAW)
//            objects[6] = formattime.format(object[5]); // tanggal (RAW)
//            objects[7] = object[6]; // kebutuhan
//            detailTrainingOffline.add(objects);
//        }
//        
//        //Ambil data utama
//        List<Object[]> dataTraining = new ArrayList<>();
//        for (Object[] object : TSchedulingSI.getDataTrainingAdmin()) {
//            Object[] objects = new Object[10];
//            objects[0] = object[0]; // id
//            objects[1] = object[1]; // judul training
//            objects[2] = object[2]; // training_time
//            objects[3] = object[3]; // is_active
//            objects[4] = formatdate.format(object[4]); // created_date
//            objects[5] = formattime.format(object[4]); // created_time
//            objects[6] = formatdate.format(object[5]); // updated_date
//            objects[7] = formattime.format(object[5]); // updated_time
//            objects[8] = object[6]; // created by
//            objects[9] = object[7]; // updated by
//
//            dataTraining.add(objects);
//        }
//
//        List<Object[]> detailTraining = new ArrayList<>();
//
//        //Ambil informasi lebih lanjut
//        for (Object[] object : TSchedulingSI.getDetailScheduleAdmin()) {
//            Object[] objects = new Object[9];
//            objects[0] = object[0]; // id
//            objects[1] = object[1]; // judul training
//            objects[2] = object[2]; // nama trainer
//            objects[3] = object[3]; // quota
//            objects[4] = object[4]; // lokasi training
//            objects[5] = formatdate.format(object[5]); // tanggal (RAW)
//            objects[6] = formattime.format(object[5]); // tanggal (RAW)
//            objects[7] = object[6]; // kebutuhan
//            detailTraining.add(objects);
//        }
        List<Object[]> detailListTrainingOnline = new ArrayList<>();
        for (Object[] object : TSchedulingSI.getListDataTrainingOnlineAdmin()) {
            Object[] objects = new Object[9];
            objects[0] = object[0]; // id
            objects[1] = object[1]; // judul training
            objects[2] = object[2]; // nama trainer
            objects[3] = formatdate.format(object[3]); // tanggal (RAW)
            objects[4] = formattime.format(object[3]); // tanggal (RAW)
            objects[5] = object[4]; // category
            detailListTrainingOnline.add(objects);
        }

        List<Object[]> detailListTrainingOffline = new ArrayList<>();
        for (Object[] object : TSchedulingSI.getListDataTrainingOfflineAdmin()) {
            Object[] objects = new Object[9];
            objects[0] = object[0]; // id
            objects[1] = object[1]; // judul training
            objects[2] = object[2]; // nama trainer
            objects[3] = formatdate.format(object[3]); // tanggal (RAW)
            objects[4] = formattime.format(object[3]); // tanggal (RAW)
            objects[5] = object[4]; // category
            detailListTrainingOffline.add(objects);
        }

//        model.addAttribute("trainingListOffline", dataTrainingOffline);
//        model.addAttribute("detailListOffline", detailTrainingOffline);
//        
//        model.addAttribute("trainingListOnline", dataTrainingOnline);
//        model.addAttribute("detailListOnline", detailTrainingOnline);
//
//        model.addAttribute("trainingList", dataTraining);
//        model.addAttribute("detailList", detailTraining);
        model.addAttribute("detailListTrainingOnline", detailListTrainingOnline);
        model.addAttribute("detailListTrainingOffline", detailListTrainingOffline);

//        model.addAttribute("detailList", detailSchedule);
        return "traininganddevelopment/training/manageRegistrationSchedules";
    }

    @GetMapping("/requestTraining")
    public String ReguestTrainingMenu(String myId, String id, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        JSONObject allocation = serviceAPI.findAllocationbyNik(String.valueOf(joEmpl.getInt("Employee_Nik")));
        String idSO = allocation.getString("So_Id");

        JSONObject SO = serviceAPI.findSobyId(idSO);
        String customerName = SO.getString("customername");

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }
        //API
        model.addAttribute("name", joEmpl.getString("Name"));
        model.addAttribute("empl_nik", joEmpl.getInt("Employee_Nik"));
        model.addAttribute("email", joEmpl.getString("Email"));
        model.addAttribute("hp", joEmpl.getString("Hp"));
        model.addAttribute("position", joEmpl.getString("Position"));
        model.addAttribute("customerName", customerName);
        model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));

        return "/requester/traininganddevelopment/requestTraining";
    }

    @GetMapping("/trainingSchedule")
    public String TrainingScheduleMenu(String myId, String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        //menambahkan akses schedule training untuk user
        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
//            JSONObject allocation = serviceAPI.findAllocationbyNik(String.valueOf(joUser.getInt("User_Id")));
//            String idSO = allocation.getString("So_Id");
//            JSONObject SO = serviceAPI.findSobyId(idSO);
//            String customerName = SO.getString("customername");

            model.addAttribute("name", joUser.getString("Name"));
//            model.addAttribute("empl_nik", joUser.getInt("User_Id"));

            Integer nik = joUser.getInt("User_Id");
            model.addAttribute("empl_nik", nik.toString());
            model.addAttribute("email", joUser.getString("Email"));
            model.addAttribute("hp", joUser.getString("No_Hp"));
            //model.addAttribute("position", joUser.getString("Position"));
//            model.addAttribute("customerName", customerName);
            model.addAttribute("condition","disable");

//            List<Object[]> dataTraining = new ArrayList<>();
//            for (Object[] object : TSchedulingSI.getDataTraining()) {
//                Object[] objects = new Object[4];
//                objects[0] = object[0].toString(); // id
//                objects[1] = object[1]; // judul training
//                objects[2] = object[2]; // training_time
//                objects[3] = object[3]; // is_active
//
//    //            System.out.println("Data Object 1 : " + objects[0]);
//    //            System.out.println("Data object 2 : " + objects[1]);
//    //            System.out.println("Data object 3 : " + objects[2]);
//    //            System.out.println("Data object 4 : " + objects[3]);
//                dataTraining.add(objects);
//            }

            SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat formattime = new SimpleDateFormat("HH:mm");

            //check dia sudah pernah daftar apa saja
//            List<Object[]> detailTraining = new ArrayList<>();
//            for (Object[] object : TSchedulingSI.getDetailSchedule()) {
//                Object[] objects = new Object[11];
//                objects[0] = object[0].toString(); // id
//                objects[1] = object[1]; // judul training
//                objects[2] = object[2]; // nama trainer
//                objects[3] = object[3]; // quota
//                objects[4] = object[4]; // lokasi training
//                objects[5] = formatdate.format(object[5]); // tanggal (RAW)
//                objects[6] = object[6]; // kebutuhan
//                objects[7] = formattime.format(object[5]); // tanggal (RAW)
//
//                objects[9] = MPRSI.getTransactionIdForCheck(nik, Integer.parseInt(objects[0].toString()));
//                objects[10] = MPRSI.getRegisteredUserNikForCheck(nik, Integer.parseInt(objects[0].toString()));
//
//    //            System.out.println("Data Object 1 : " + objects[0]);
//    //            System.out.println("Data object 2 : " + objects[1]);
//    //            System.out.println("Data object 3 : " + objects[2]);
//    //            System.out.println("Data object 4 : " + objects[3]);
//    //            System.out.println("Data object 5 : " + objects[4]);
//    //            System.out.println("Data object 6 : " + objects[5]);
//    //            System.out.println("Data object 7 : " + objects[6]);
//    //            System.out.println("Data object 8 : " + objects[7]);
//    //            System.out.println("Data object 9 : " + objects[9]);
//                detailTraining.add(objects);
//
//            }
            List<Object[]> dataTrainingOnline = new ArrayList<>();
            for (Object[] dataTrainingOnlineClass : TSchedulingSI.getDataTrainingOnlineClass()) {
                Object[] objects = new Object[11];
                objects[0] = dataTrainingOnlineClass[0];
                objects[1] = dataTrainingOnlineClass[1];
                objects[2] = dataTrainingOnlineClass[2];
                objects[3] = dataTrainingOnlineClass[3];
                objects[4] = formatdate.format(dataTrainingOnlineClass[4]);
                objects[5] = formattime.format(dataTrainingOnlineClass[4]);
                objects[6] = formatdate.format(dataTrainingOnlineClass[5]);
                objects[7] = formattime.format(dataTrainingOnlineClass[5]);
                objects[8] = dataTrainingOnlineClass[6];
                objects[9] = dataTrainingOnlineClass[7];
                objects[10] = dataTrainingOnlineClass[8];
                dataTrainingOnline.add(objects);
            }

            List<Object[]> detailTrainingOnline = new ArrayList<>();
            for (Object[] object : TSchedulingSI.getDetailDataTrainingOnline()) {
                Object[] objects = new Object[9];
                objects[0] = object[0]; // id
                objects[1] = object[1]; // judul training
                objects[2] = object[2]; // nama trainer
                objects[3] = object[3]; // quota
                objects[4] = formatdate.format(object[4]); // tanggal (RAW)
                objects[5] = formattime.format(object[4]); // tanggal (RAW)
                objects[6] = object[5]; // link
                objects[7] = MPRSI.getTransactionIdForCheck(nik, Integer.parseInt(objects[0].toString()));
                objects[8] = MPRSI.getRegisteredUserNikForCheck(nik, Integer.parseInt(objects[0].toString()));
                //            System.out.println("RETURN = " + objects[7] + " " + objects[0]);
                //            System.out.println("RETURN = " + objects[8] + " " + objects[0]);
                detailTrainingOnline.add(objects);
            }

            List<Object[]> dataTrainingOffline = new ArrayList<>();
            for (Object[] dataTrainingOfflineClass : TSchedulingSI.getDataTrainingOfflineClass()) {
                Object[] objects = new Object[11];
                objects[0] = dataTrainingOfflineClass[0];
                objects[1] = dataTrainingOfflineClass[1];
                objects[2] = dataTrainingOfflineClass[2];
                objects[3] = dataTrainingOfflineClass[3];
                objects[4] = formatdate.format(dataTrainingOfflineClass[4]);
                objects[5] = formattime.format(dataTrainingOfflineClass[4]);
                objects[6] = formatdate.format(dataTrainingOfflineClass[5]);
                objects[7] = formattime.format(dataTrainingOfflineClass[5]);
                objects[8] = dataTrainingOfflineClass[6];
                objects[9] = dataTrainingOfflineClass[7];
                objects[10] = dataTrainingOfflineClass[8];

                dataTrainingOffline.add(objects);
            }

            List<Object[]> detailTrainingOffline = new ArrayList<>();
            for (Object[] object : TSchedulingSI.getDetailDataTrainingOffline()) {
                Object[] objects = new Object[11];
                objects[0] = object[0]; // id
                objects[1] = object[1]; // judul training
                objects[2] = object[2]; // nama trainer
                objects[3] = object[3]; // quota
                objects[4] = object[4]; // lokasi training
                objects[5] = formatdate.format(object[5]); // tanggal (RAW)
                objects[6] = formattime.format(object[5]); // tanggal (RAW)
                objects[7] = object[6]; // kebutuhan
                objects[9] = MPRSI.getTransactionIdForCheck(nik, Integer.parseInt(objects[0].toString()));
                objects[10] = MPRSI.getRegisteredUserNikForCheck(nik, Integer.parseInt(objects[0].toString()));
                //            System.out.println("RETURN ALL= " + objects[9] + " " + objects[0]);
                //            System.out.println("RETURN ALL= " + objects[10] + " " + objects[0]);
                detailTrainingOffline.add(objects);
            }

            model.addAttribute("trainingListOffline", dataTrainingOffline);
            model.addAttribute("detailListOffline", detailTrainingOffline);

            model.addAttribute("trainingListOnline", dataTrainingOnline);
            model.addAttribute("detailListOnline", detailTrainingOnline);

            //model.addAttribute("trainingList", dataTraining);
//            model.addAttribute("detailList", detailTraining);
//            model.addAttribute("nik", joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            JSONObject allocation = serviceAPI.findAllocationbyNik(String.valueOf(joEmpl.getInt("Employee_Nik")));
            String idSO = allocation.getString("So_Id");
            JSONObject SO = serviceAPI.findSobyId(idSO);
            String customerName = SO.getString("customername");
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            model.addAttribute("name", joEmpl.getString("Name"));
//            model.addAttribute("empl_nik", joEmpl.getInt("Employee_Nik"));

            Integer nik = joEmpl.getInt("Employee_Nik");
            model.addAttribute("empl_nik", nik.toString());
            model.addAttribute("email", joEmpl.getString("Email"));
            model.addAttribute("hp", joEmpl.getString("Hp"));
            model.addAttribute("position", joEmpl.getString("Position"));
            model.addAttribute("customerName", customerName);
            model.addAttribute("condition","enable");


            SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat formattime = new SimpleDateFormat("HH:mm");

            //check dia sudah pernah daftar apa saja

            List<Object[]> dataTrainingOnline = new ArrayList<>();
            for (Object[] dataTrainingOnlineClass : TSchedulingSI.getDataTrainingOnlineClass()) {
                Object[] objects = new Object[11];
                objects[0] = dataTrainingOnlineClass[0];
                objects[1] = dataTrainingOnlineClass[1];
                objects[2] = dataTrainingOnlineClass[2];
                objects[3] = dataTrainingOnlineClass[3];
                objects[4] = formatdate.format(dataTrainingOnlineClass[4]);
                objects[5] = formattime.format(dataTrainingOnlineClass[4]);
                objects[6] = formatdate.format(dataTrainingOnlineClass[5]);
                objects[7] = formattime.format(dataTrainingOnlineClass[5]);
                objects[8] = dataTrainingOnlineClass[6];
                objects[9] = dataTrainingOnlineClass[7];
                objects[10] = dataTrainingOnlineClass[8];
                dataTrainingOnline.add(objects);
            }

            List<Object[]> detailTrainingOnline = new ArrayList<>();
            for (Object[] object : TSchedulingSI.getDetailDataTrainingOnline()) {
                Object[] objects = new Object[9];
                objects[0] = object[0]; // id
                objects[1] = object[1]; // judul training
                objects[2] = object[2]; // nama trainer
                objects[3] = object[3]; // quota
                objects[4] = formatdate.format(object[4]); // tanggal (RAW)
                objects[5] = formattime.format(object[4]); // tanggal (RAW)
                objects[6] = object[5]; // link
                objects[7] = MPRSI.getTransactionIdForCheck(nik, Integer.parseInt(objects[0].toString()));
                objects[8] = MPRSI.getRegisteredUserNikForCheck(nik, Integer.parseInt(objects[0].toString()));
                //            System.out.println("RETURN = " + objects[7] + " " + objects[0]);
                //            System.out.println("RETURN = " + objects[8] + " " + objects[0]);
                detailTrainingOnline.add(objects);
            }

            List<Object[]> dataTrainingOffline = new ArrayList<>();
            for (Object[] dataTrainingOfflineClass : TSchedulingSI.getDataTrainingOfflineClass()) {
                Object[] objects = new Object[11];
                objects[0] = dataTrainingOfflineClass[0];
                objects[1] = dataTrainingOfflineClass[1];
                objects[2] = dataTrainingOfflineClass[2];
                objects[3] = dataTrainingOfflineClass[3];
                objects[4] = formatdate.format(dataTrainingOfflineClass[4]);
                objects[5] = formattime.format(dataTrainingOfflineClass[4]);
                objects[6] = formatdate.format(dataTrainingOfflineClass[5]);
                objects[7] = formattime.format(dataTrainingOfflineClass[5]);
                objects[8] = dataTrainingOfflineClass[6];
                objects[9] = dataTrainingOfflineClass[7];
                objects[10] = dataTrainingOfflineClass[8];

                dataTrainingOffline.add(objects);
            }

            List<Object[]> detailTrainingOffline = new ArrayList<>();
            for (Object[] object : TSchedulingSI.getDetailDataTrainingOffline()) {
                Object[] objects = new Object[11];
                objects[0] = object[0]; // id
                objects[1] = object[1]; // judul training
                objects[2] = object[2]; // nama trainer
                objects[3] = object[3]; // quota
                objects[4] = object[4]; // lokasi training
                objects[5] = formatdate.format(object[5]); // tanggal (RAW)
                objects[6] = formattime.format(object[5]); // tanggal (RAW)
                objects[7] = object[6]; // kebutuhan
                objects[9] = MPRSI.getTransactionIdForCheck(nik, Integer.parseInt(objects[0].toString()));
                objects[10] = MPRSI.getRegisteredUserNikForCheck(nik, Integer.parseInt(objects[0].toString()));
                //            System.out.println("RETURN ALL= " + objects[9] + " " + objects[0]);
                //            System.out.println("RETURN ALL= " + objects[10] + " " + objects[0]);
                detailTrainingOffline.add(objects);
            }

            model.addAttribute("trainingListOffline", dataTrainingOffline);
            model.addAttribute("detailListOffline", detailTrainingOffline);

            model.addAttribute("trainingListOnline", dataTrainingOnline);
            model.addAttribute("detailListOnline", detailTrainingOnline);

//            model.addAttribute("trainingList", dataTraining);
//            model.addAttribute("detailList", detailTraining);
//            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        }

        
        return "requester/traininganddevelopment/scheduleTraining";
    }

    @RequestMapping(value = "/getDropdownList", method = RequestMethod.GET)
    @ResponseBody
    public List<Object[]> dropdownMenu(@RequestParam(value = "term", required = false, defaultValue = "") Integer term) {
        List<Object[]> dropdownTrainer = new ArrayList<>();
        try {
//            System.out.println("term: " + term);
            List<Object[]> listTrainer = TTFSI.getDataFilterList(term);
            for (Object[] object : listTrainer) {
                Object[] objects = new Object[3];

                objects[0] = object[0];
                objects[1] = object[1].toString();
                objects[2] = object[2];
//                System.out.println("Data:");
//                System.out.println(objects[1]);
//                System.out.println(objects[2]);
//                System.out.println(objects[0]);
//                System.out.println("End data");

                dropdownTrainer.add(objects);
            }

        } catch (Exception e) {
            e.printStackTrace();
//                log.error("Exception in Dropdown", e);
        }

        return dropdownTrainer;
    }

    @RequestMapping(value = "/trainingAutocomplete")
    @ResponseBody
    public List<String> autocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> trainingSuggestion = new ArrayList<String>();

        try {
            System.out.println("term: " + term);
            List<Object[]> listTraining = TCSSI.searchByNameandActiveTraining(term);
            System.out.println("test");
            System.out.println(listTraining);
            for (Object[] object : listTraining) {
                Object[] objects = new Object[4];
                objects[0] = object[0];
                objects[1] = object[1];
                objects[2] = object[2];
                objects[3] = object[3];

                trainingSuggestion.add(object[1].toString());
                System.out.println("data:");
                System.out.println(object);

            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("Exception in autocomplete", e);
        }

        return trainingSuggestion;
    }

    @RequestMapping(value = "/trainingSelect2", method = RequestMethod.GET)
    public @ResponseBody
    String select2() {
        JSONArray array = new JSONArray();
        JSONObject data = new JSONObject();
        try {
            for (Object[] object : TCSSI.seletAllTrainingActived()) {
                JSONObject jo = new JSONObject();
                jo.put("id", object[1]);
                jo.put("text", object[1]);
//                jo.put("category", object[2]);
//                jo.put("is_available", object[3]);
                array.put(jo);
            }
            data.put("data", array);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    @RequestMapping(value = "/scheduleCheck")
    @ResponseBody
    public String[] scheduleCheck(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        String[] schedule = new String[2];
        try {
            System.out.println("term: " + term);
            List<Object[]> listTraining = TSchedulingSI.getScheduleForCheck(term);
            for (Object[] object : listTraining) {
                Object[] objects = new Object[10];
                objects[0] = object[0]; //id
                objects[1] = object[1]; //training_title
                objects[2] = object[2]; //trainer_name
                objects[3] = object[3]; //training_quota
                objects[4] = object[4]; //training_location
                objects[5] = object[5]; //training_time
                objects[6] = object[6]; //training_needs

//                //Split Date dan Time
                String converted = object[5].toString();
                String[] parts = converted.split(" ");
                String part1 = parts[0];
                String part2 = parts[1];

                SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                SimpleDateFormat formattime = new SimpleDateFormat("HH:mm");
                Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(part1);
                Date parseTime = formattime.parse(part2);
                String dateTraining = formatdate.format(parseDate);
                String timeTraining = formattime.format(parseTime);

                System.out.println("Data part 1 = " + dateTraining);
                System.out.println("Data part 2 = " + timeTraining);

//
//                //Reformat Date
//                String[] dateParts = part1.split("-");
//                String datePart1 = dateParts[0];
//                String datePart2 = dateParts[1];
//                String datePart3 = dateParts[2];
//
//                System.out.println("Date Part 1 : " + datePart1);
//                System.out.println("Date Part 2 : " + datePart2);
//                System.out.println("Date Part 3 : " + datePart3);
//                String formed = datePart3 + "-" + datePart2 + "-" + datePart1;
//                System.out.println("Date formed : " + formed);
//
//                //Rapihin Time
//                StringBuilder sb = new StringBuilder(part2);
//                sb.deleteCharAt(8);
//                sb.deleteCharAt(8);
//                String clean = sb.toString();
//
//                System.out.println("Time CLean:" + clean);
//
////10:00
//                System.out.println(part1);
//                System.out.println(part2);
                objects[7] = dateTraining; // tanggal (Bersih)
                objects[8] = timeTraining; //jam
//                
                schedule[0] = objects[1].toString();
                schedule[1] = objects[7].toString();
//                schedule.add(object[1].toString());
//                schedule.add(object[7].toString());
//                schedule.add("IKAn");
//                System.out.println("data:");
//                System.out.println(object);

            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("Exception in autocomplete", e);
        }

        return schedule;
    }

    @RequestMapping(value = "/trainingSearchID")
    @ResponseBody
    public List<String> SearchID(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
        List<String> IDSuggestion = new ArrayList<String>();
        try {
//            System.out.println("term: " + term);
            List<Object[]> listID = TCSSI.searchIDByNameandActiveTraining(term);
//            System.out.println("test");
//            System.out.println(listID);
            for (Object[] object : listID) {
                Object[] objects = new Object[1];
                objects[0] = object[0];

                IDSuggestion.add(object[0].toString());
//                System.out.println("data:");
//                System.out.println(object);

            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("Exception in autocomplete", e);
        }

        return IDSuggestion;
    }

    @GetMapping(value = "/manageRegistration/{id}")
    public String viewCatalogTrainers(String myId, String id, Model model, @PathVariable("id") Integer training) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        model.addAttribute("empl_nik", id);

        List<Object[]> listRequester = new ArrayList<>();

        for (Object[] object : MPRSI.getDataParticipants(training)) {
            Object[] objects = new Object[13];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            objects[6] = object[6]; //transaction id
            objects[7] = serviceAPI.findEmpbyid((objects[1].toString())).getString("Name");
            objects[8] = serviceAPI.findEmpbyid((objects[1].toString())).getString("Email");
            objects[9] = serviceAPI.findEmpbyid((objects[1].toString())).getString("Hp");
            objects[10] = serviceAPI.findEmpbyid((objects[1].toString())).getString("Position");
            JSONObject allocation = serviceAPI.findAllocationbyNik(objects[1].toString());
            String idSO = allocation.getString("So_Id");
            JSONObject SO = serviceAPI.findSobyId(idSO);
            String customerName = SO.getString("customername");
            objects[11] = customerName;
            objects[12] = getDate(object[7].toString());

//            System.out.println(serviceAPI.EmployeeByNik(objects[1].toString()).getname());
// satu orang bisa banyak history,
            listRequester.add(objects);
        }

//        List<Object[]> historyList = new ArrayList<>();
//        for (Object[] history : MPRSI.getDataTrainingHistory()) {
//            Object[] histories = new Object[3];
//
//            histories[0] = history[0];//judul
//            histories[1] = history[1];//tanggal
//            histories[2] = history[2];//nik
//
////            System.out.println("TEST: " + histories[0]);
//            //Split Date dan Time
//            String converted = histories[1].toString();
//            String[] parts = converted.split(" ");
//            String part1 = parts[0];
//            String part2 = parts[1];
//
//            //Reformat Date
//            String[] dateParts = part1.split("-");
//            String datePart1 = dateParts[0];
//            String datePart2 = dateParts[1];
//            String datePart3 = dateParts[2];
//
////            System.out.println("Date Part 1 : " + datePart1);
////            System.out.println("Date Part 2 : " + datePart2);
////            System.out.println("Date Part 3 : " + datePart3);
//            String formed = datePart3 + "-" + datePart2 + "-" + datePart1;
////            System.out.println("Date formed : " + formed);
//
//            //Rapihin Time
////            String[] trim = part2.split(".");
//            StringBuilder sb = new StringBuilder(part2);
//            sb.deleteCharAt(8);
//            sb.deleteCharAt(8);
//            String clean = sb.toString();
////            String clean = trim[0];
////            String dummy = trim[1];
////            
////            System.out.println("Time CLean: " + clean);
////            System.out.println("Time CLean:" + dummy);
////            Timestamp stamp = new Timestamp(Long.parseLong(withoutspace));
////            Date date = stamp;
//
////10:00
////            System.out.println(part1);
////            System.out.println(part2);
//            histories[1] = formed;
//
//            historyList.add(histories);
//
//            model.addAttribute("requesterHistoryList", historyList);
//        }

//        List<Object[]> detailTraining = new ArrayList<>();
////        String tanggal = new String();
////        String jam = new String();
//        for (Object[] object : TSchedulingSI.getDetailSchedule()) {
//            Object[] objects = new Object[9];
//            objects[0] = object[0]; // id
//            objects[1] = object[1]; // judul training
//            objects[2] = object[2]; // nama trainer
//            objects[3] = object[3]; // quota
//            objects[4] = object[4]; // lokasi training
//            objects[5] = object[5]; // tanggal (RAW)
//            objects[6] = object[6]; // kebutuhan
//
//            //Split Date dan Time
//            String converted = object[5].toString();
//            String[] parts = converted.split(" ");
//            String part1 = parts[0];
//            String part2 = parts[1];
//
//            //Reformat Date
//            String[] dateParts = part1.split("-");
//            String datePart1 = dateParts[0];
//            String datePart2 = dateParts[1];
//            String datePart3 = dateParts[2];
//
////            System.out.println("Date Part 1 : " + datePart1);
////            System.out.println("Date Part 2 : " + datePart2);
////            System.out.println("Date Part 3 : " + datePart3);
//            String formed = datePart3 + "-" + datePart2 + "-" + datePart1;
////            System.out.println("Date formed : " + formed);
//
//            //Rapihin Time
////            String[] trim = part2.split(".");
//            StringBuilder sb = new StringBuilder(part2);
//            sb.deleteCharAt(8);
//            sb.deleteCharAt(8);
//            String clean = sb.toString();
////            String clean = trim[0];
////            String dummy = trim[1];
////
////            System.out.println("Time CLean:" + clean);
////            System.out.println("Time CLean:" + dummy);
////            Timestamp stamp = new Timestamp(Long.parseLong(withoutspace));
////            Date date = stamp;
//
////10:00
//            objects[7] = formed;
//            objects[8] = clean;
//
////            System.out.println("Data Object 1 : " + objects[0]);
////            System.out.println("Data object 2 : " + objects[1]);
////            System.out.println("Data object 3 : " + objects[2]);
////            System.out.println("Data object 4 : " + objects[3]);
////            System.out.println("Data object 5 : " + objects[4]);
////            System.out.println("Data object 6 : " + objects[5]);
////            System.out.println("Data object 7 : " + objects[6]);
////            System.out.println("Data object 8 : " + objects[7]);
////            System.out.println("Data object 9 : " + objects[8]);
//            detailTraining.add(objects);
//        }

        String TrainTime = MPRSI.getTrainTime(training).toString();
        model.addAttribute("id", training);
        model.addAttribute("requesterList", listRequester);
//        model.addAttribute("detailList", detailTraining);
        model.addAttribute("TrainName", MPRSI.getTrainName(training));
        System.out.println("nama training" + MPRSI.getTrainName(training));
        model.addAttribute("TrainTime", getDate(TrainTime));
        System.out.println("nama training" + getDate(MPRSI.getTrainTime(training)));
        model.addAttribute("TrainTime", MPRSI.getTrainTime(training));

        return "traininganddevelopment/training/ManageRegistration";
    }

//    @GetMapping("/participantFilloutForm/{id}/{number}")
    @GetMapping("/participantFilloutForm/{name}/{training}")
    public String userFeedbackFormtoFill(String myId, String id, Model model, @PathVariable("name") String hash, @PathVariable("training") Integer transaction) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        JSONObject allocation = serviceAPI.findAllocationbyNik(String.valueOf(joEmpl.getInt("Employee_Nik")));
        String idSO = allocation.getString("So_Id");

        JSONObject SO = serviceAPI.findSobyId(idSO);
        String customerName = SO.getString("customername");

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        String training = MPRSI.TrainingTitleForm(transaction);

        model.addAttribute("title", training);
        model.addAttribute("transaction_id", transaction);

        model.addAttribute("name", joEmpl.getString("Name"));
        model.addAttribute("empl_nik", joEmpl.getInt("Employee_Nik"));
        model.addAttribute("email", joEmpl.getString("Email"));
        model.addAttribute("hp", joEmpl.getString("Hp"));
        model.addAttribute("position", joEmpl.getString("Position"));
        model.addAttribute("customerName", customerName);

        List<Object[]> questionLists = new ArrayList<>();
        for (Object[] object : TFSI.findallfeedback()) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            questionLists.add(objects);
        }

        try {
            boolean flag = TURSI.checkFlag(transaction.toString(), joEmpl.getInt("Employee_Nik"));
            if (flag) {
                model.addAttribute("questions", questionLists);
                model.addAttribute("questionType", TFSI.findAll());
                return "requester/traininganddevelopment/participantFilloutForm";
            } else {
                return "requester/traininganddevelopment/landingDummyAlreadyFilledForm";
            }
        } catch (Exception e) {
            return "requester/traininganddevelopment/landingDummyAlreadyFilledForm";
        }

    }

    @GetMapping("/manageTrainingRequest")
    public String ManageTrainingRequestMenu(String myId, String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }
        model.addAttribute("empl_nik", id);

//        String training = MPRSI.TrainingTitleForm(transaction);
//        model.addAttribute("title", training);
//        model.addAttribute("email", serviceAPI.EmployeeByNik(id).getString("email"));
//        model.addAttribute("hp", serviceAPI.EmployeeByNik(id).getString("phone"));
//        model.addAttribute("position", serviceAPI.EmployeeByNik(id).getString("position"));
//        model.addAttribute("customerName", serviceAPI.EmployeeByNik(id).getString("customer_name"));
        List<Object[]> trainingRequest = new ArrayList<>();
        for (Object[] object : MTrainingRSI.findallrequest()) {
            Object[] objects = new Object[10];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            //simpan data API dalam object
            objects[6] = serviceAPI.findEmpbyid(objects[1].toString()).getString("Name");
            objects[7] = serviceAPI.findEmpbyid(objects[1].toString()).getString("Email");
//            objects[7] = serviceAPI.EmployeeByNik(objects[1].toString()).getjoin_date();

            //Split Date dan Time
            String converted = serviceAPI.findEmpbyid(objects[1].toString()).getString("Join Date");
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

            //Rapihin Time
//            String[] trim = part2.split(".");
            StringBuilder sb = new StringBuilder(part2);
            sb.deleteCharAt(8);
            sb.deleteCharAt(8);
            String clean = sb.toString();
//            String clean = trim[0];
//            String dummy = trim[1];
//            
            System.out.println("Time CLean:" + clean);
//            System.out.println("Time CLean:" + dummy);
//            Timestamp stamp = new Timestamp(Long.parseLong(withoutspace));
//            Date date = stamp;

//10:00
            System.out.println(part1);
            System.out.println(part2);
            objects[8] = formed;
            objects[9] = serviceAPI.findEmpbyid(objects[1].toString()).getString("Position");

            trainingRequest.add(objects);
        }

        model.addAttribute("requestList", trainingRequest);
        return "traininganddevelopment/training/manageTrainingRequest";
    }

    @GetMapping("/viewTrainingHistory")
    public String requesterTrainingHistoryMenu(String myId, String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        JSONObject allocation = serviceAPI.findAllocationbyNik(String.valueOf(joEmpl.getInt("Employee_Nik")));
        String idSO = allocation.getString("So_Id");

        JSONObject SO = serviceAPI.findSobyId(idSO);
        String customerName = SO.getString("customername");

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        model.addAttribute("name", joEmpl.getString("Name"));
        model.addAttribute("empl_nik", joEmpl.getInt("Employee_Nik"));
        model.addAttribute("email", joEmpl.getString("Email"));
        model.addAttribute("hp", joEmpl.getString("Hp"));
        model.addAttribute("position", joEmpl.getString("Position"));
        model.addAttribute("customerName", customerName);

        List<Object[]> trainingHistoryAttendanceList = new ArrayList<>();
        for (Object[] object : HTSI.getAllHistoryAttendance(joEmpl.getInt("Employee_Nik"))) {
            Object[] objects = new Object[8];
            objects[0] = object[0]; //id
            objects[1] = object[1]; //title
            objects[2] = object[2]; // trainer name
            objects[3] = object[3]; // date
            objects[4] = object[4]; // location
            objects[5] = object[5]; // value
            objects[6] = object[6]; // training transaction id

            //Split Date dan Time
            String converted = objects[3].toString();
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

            //Rapihin Time
//            String[] trim = part2.split(".");
            StringBuilder sb = new StringBuilder(part2);
            sb.deleteCharAt(8);
            sb.deleteCharAt(8);
            String clean = sb.toString();
//            String clean = trim[0];
//            String dummy = trim[1];
//            
            System.out.println("Time CLean:" + clean);
//            System.out.println("Time CLean:" + dummy);
//            Timestamp stamp = new Timestamp(Long.parseLong(withoutspace));
//            Date date = stamp;

            System.out.println(part1);
            System.out.println(part2);

            objects[3] = formed; //tanggal
            objects[7] = clean; //Jam

            trainingHistoryAttendanceList.add(objects);
        }

        model.addAttribute("attendanceHistoryList", trainingHistoryAttendanceList);
        model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        return "requester/traininganddevelopment/requesterTrainingHistory";
    }

    @GetMapping("/viewTrainingRequestHistory")
    public String requesterTrainingRequestHistoryMenu(String myId, String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        JSONObject allocation = serviceAPI.findAllocationbyNik(String.valueOf(joEmpl.getInt("Employee_Nik")));
        String idSO = allocation.getString("So_Id");

        JSONObject SO = serviceAPI.findSobyId(idSO);
        String customerName = SO.getString("customername");

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        model.addAttribute("name", joEmpl.getString("Name"));
        model.addAttribute("empl_nik", joEmpl.getInt("Employee_Nik"));
        model.addAttribute("email", joEmpl.getString("Email"));
        model.addAttribute("hp", joEmpl.getString("Hp"));
        model.addAttribute("position", joEmpl.getString("Position"));
        model.addAttribute("customerName", customerName);

        List<Object[]> trainingRequestHistoryList = new ArrayList<>();
        for (Object[] object : HTSI.getAllhistoryRequested(joEmpl.getInt("Employee_Nik"))) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            //Split Date dan Time
            String converted = objects[2].toString();
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

            //Rapihin Time
//            String[] trim = part2.split(".");
            StringBuilder sb = new StringBuilder(part2);
            sb.deleteCharAt(8);
            sb.deleteCharAt(8);
            String clean = sb.toString();
//            String clean = trim[0];
//            String dummy = trim[1];
//            
            System.out.println("Time CLean:" + clean);
//            System.out.println("Time CLean:" + dummy);
//            Timestamp stamp = new Timestamp(Long.parseLong(withoutspace));
//            Date date = stamp;

//10:00
            System.out.println(part1);
            System.out.println(part2);

            objects[2] = formed;

            trainingRequestHistoryList.add(objects);
        }

        model.addAttribute("requestedTrainingList", trainingRequestHistoryList);
        model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        return "requester/traininganddevelopment/viewRequesterTrainingRequestHistory";
    }

    @GetMapping("/historyTraining")
    public String HistoryTrainingMenu(Model model) {

        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
        SimpleDateFormat formattime = new SimpleDateFormat("HH:mm");

        List<Object[]> trainingHistoryList = new ArrayList<>();
        for (Object[] object : HTSI.getDataTraining()) {
            Object[] objects = new Object[9];
            objects[0] = object[0]; // id
            objects[1] = object[1]; // judul training
            objects[2] = object[2]; // nama trainer
            objects[3] = formatdate.format(object[3]); // tanggal (RAW)
            objects[4] = formattime.format(object[3]); // tanggal (RAW)
            objects[5] = object[4]; // category
            trainingHistoryList.add(objects);
        }

//        List<Object[]> trainingHistoryDetail = new ArrayList<>();
//        for (Object[] object : HTSI.getDetailTraining()) {
//            Object[] objects = new Object[8];
//            objects[0] = object[0];
//            objects[1] = object[1];
//            objects[2] = object[2];
//            objects[3] = object[3];
//            objects[4] = object[4];
//            objects[5] = object[5];
//            objects[6] = object[6];
//
//            //Split Date dan Time
//            String converted = object[2].toString();
//            String[] parts = converted.split(" ");
//            String part1 = parts[0];
//            String part2 = parts[1];
//
//            //Reformat Date
//            String[] dateParts = part1.split("-");
//            String datePart1 = dateParts[0];
//            String datePart2 = dateParts[1];
//            String datePart3 = dateParts[2];
//
//            System.out.println("Date Part 1 : " + datePart1);
//            System.out.println("Date Part 2 : " + datePart2);
//            System.out.println("Date Part 3 : " + datePart3);
//
//            String formed = datePart3 + "-" + datePart2 + "-" + datePart1;
//            System.out.println("Date formed : " + formed);
//
//            //Rapihin Time
//            StringBuilder sb = new StringBuilder(part2);
//            sb.deleteCharAt(8);
//            sb.deleteCharAt(8);
//            String clean = sb.toString();
//
//            System.out.println("Time CLean:" + clean);
//
//            System.out.println(part1);
//            System.out.println(part2);
//
//            objects[2] = formed;
//            objects[7] = clean;
//
//            trainingHistoryDetail.add(objects);
//        }

        model.addAttribute("historyList", trainingHistoryList);
//        model.addAttribute("historyDetail", trainingHistoryDetail);
        return "traininganddevelopment/training/historyTrainings";
    }

    @GetMapping("/manageHistory/{id}/{training_title}")
    public String manageHistoryTrainingMenu(String myId, String id, Model model, @PathVariable("id") Integer training_id, @PathVariable("training_title") String training_title) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

//        model.addAttribute("name", serviceAPI.EmployeeByNik(id).getname());
//        model.addAttribute("empl_nik", serviceAPI.EmployeeByNik(id).getempl_nik());
//        model.addAttribute("email", serviceAPI.EmployeeByNik(id).getemail());
//        model.addAttribute("hp", serviceAPI.EmployeeByNik(id).getphone());
//        model.addAttribute("position", serviceAPI.EmployeeByNik(id).getposition());
//        model.addAttribute("customerName", serviceAPI.EmployeeByNik(id).getcustomer_name());
        List<Object[]> trainingHistoryList = new ArrayList<>();
        for (Object[] object : HTSI.getDataParticipants(training_id)) {
            Object[] objects = new Object[8];
            objects[0] = object[0]; //id
            objects[1] = object[1]; //nik
            objects[2] = object[2]; //customer id
            objects[3] = object[3]; //status
            objects[4] = serviceAPI.findEmpbyid(objects[1].toString()).getString("Name"); //name
            objects[5] = serviceAPI.findEmpbyid(objects[1].toString()).getString("Email"); //email
            objects[6] = serviceAPI.findEmpbyid(objects[1].toString()).getString("Hp"); //hp
            objects[7] = serviceAPI.findEmpbyid(objects[1].toString()).get("Position"); //position

            trainingHistoryList.add(objects);
        }

        //Untuk check apakah sudah ada file yang di upload
        model.addAttribute("checkUpload", TURSI.checkUpload(training_id.toString()));
//        System.out.println("check " + TURSI.checkUpload(training_id.toString()));

        model.addAttribute("selectedTrainingId", training_id);
        model.addAttribute("selectedTrainingTitle", training_title);
        model.addAttribute("historyList", trainingHistoryList);
        return "traininganddevelopment/training/manageHistory";
    }

    @GetMapping("/participantFeedbackForm/{id}/{training}/{nik}")
    public String userFeedbackFormt(String myId, String id, Model model, @PathVariable("id") String training_id, @PathVariable("training") String training_title, @PathVariable("nik") Integer empl_nik) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

//        String training = MPRSI.TrainingTitleForm(transaction);
        model.addAttribute("title", training_title);

//        model.addAttribute("name", serviceAPI.EmployeeByNik(id).getname());
//        model.addAttribute("empl_nik", serviceAPI.EmployeeByNik(id).getempl_nik());
//        model.addAttribute("email", serviceAPI.EmployeeByNik(id).getemail());
//        model.addAttribute("hp", serviceAPI.EmployeeByNik(id).getphone());
//        model.addAttribute("position", serviceAPI.EmployeeByNik(id).getposition());
//        model.addAttribute("customerName", serviceAPI.EmployeeByNik(id).getcustomer_name());
        List<Object[]> questionLists = new ArrayList<>();
        for (Object[] object : TFSI.findallfeedback()) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            questionLists.add(objects);
        }

        List<Object[]> responseList = new ArrayList<>();
        for (Object[] object : HTSI.getUserFeedback(Integer.parseInt(training_id), empl_nik)) {
            Object[] objects = new Object[7];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            objects[6] = object[6];

            model.addAttribute("name", serviceAPI.findEmpbyid(objects[0].toString()).getString("Name"));

            responseList.add(objects);
        }

        model.addAttribute("questions", questionLists);
        model.addAttribute("questionType", TFSI.findAll());
        model.addAttribute("responseList", responseList);
        return "traininganddevelopment/training/userFeedback";
    }

    @GetMapping("/viewTrainingFeedbackResponse/{id}/{training_title}")
    public String TrainingFeedbackSummary(String myId, String id, Model model, @PathVariable("id") Integer training_id, @PathVariable("training_title") String training_title) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

//        model.addAttribute("name", serviceAPI.EmployeeByNik(id).getname());
//        model.addAttribute("empl_nik", serviceAPI.EmployeeByNik(id).getempl_nik());
//        model.addAttribute("email", serviceAPI.EmployeeByNik(id).getemail());
//        model.addAttribute("hp", serviceAPI.EmployeeByNik(id).getphone());
//        model.addAttribute("position", serviceAPI.EmployeeByNik(id).getposition());
//        model.addAttribute("customerName", serviceAPI.EmployeeByNik(id).getcustomer_name());
        List<Object[]> feedbackSummaryParticipants = new ArrayList<>();
        // 1. lu get semua partisipan, by training id
        for (Object[] object : HTSI.getFeedbackSummaryParticipants(training_id)) {
            Object[] objects = new Object[20];
            objects[0] = object[0];//nik
            // get name by API
            JSONObject json_userName = serviceAPI.GetUserInformation(object[0].toString());
            objects[1] = json_userName.getString("name");//name

            // get nilai by NIK
            List<Object[]> feedbackSummary = new ArrayList<>();
            int counter = 2;
            for (Object[] sub_object : HTSI.getFeedbackSummary(training_id, Integer.parseInt(objects[0].toString()))) {
                Object[] sub_objects = new Object[7];

                if (sub_object[4].toString().equals("")) {
                    objects[counter] = "N/A";
                } else {
                    objects[counter] = sub_object[4];
                    System.out.println("RESULT = " + objects[counter]);
                }

                sub_objects[0] = sub_object[0]; //title
                sub_objects[1] = sub_object[1]; //trainer_name
                sub_objects[2] = sub_object[2]; //nik
                sub_objects[3] = sub_object[3]; //customer_id                
                sub_objects[4] = sub_object[4]; //feedback response
                sub_objects[5] = sub_object[5]; //question content
                sub_objects[6] = sub_object[6]; //type

                counter++;
                feedbackSummary.add(sub_objects);

            }

            System.out.println("Size " + feedbackSummary.size());
            if (feedbackSummary.size() <= 2) {
                for (int i = 2; i < 20; i++) {
                    System.out.println("Test : ");
                    objects[i] = "N/A";
                }
            }

            // masukin d
//            if (feedbackSummary.size() <= 0) {
//                objects[2] = "N/A";
//                objects[2] = "N/A";
//                objects[2] = "N/A";
//                objects[2] = "N/A";
//                objects[2] = "N/A";
//            } else {
//                int counter =2;
//                for (int i = 0; i < feedbackSummary.size(); i++) {
//                objects[counter] = feedbackSummary[i][4];
//                }
////                objects[2] = sub_object[4];
//
//            }
//            model.addAttribute("Feedback", feedbackSummary);
            feedbackSummaryParticipants.add(objects);
        }

//        model.addAttribute("checkUpload", TURSI.checkUpload(training_id.toString()));
//        System.out.println("check " + TURSI.checkUpload(training_id.toString()));
        model.addAttribute("selectedTrainingId", training_id);
        model.addAttribute("selectedTrainingTitle", training_title);
        model.addAttribute("participants", feedbackSummaryParticipants);

//        System.out.println("Training " + training_title);
//        model.addAttribute("historyList", trainingHistoryList);
        return "traininganddevelopment/training/trainingFeedbackSummary";
    }

    //Download
    @RequestMapping(value = "/absense/download/{id}/{training_title}", method = RequestMethod.POST)
    @ResponseBody
    public String absenseDownload(@PathVariable("id") String id, @PathVariable("training_title") String training_title, HttpServletResponse response) throws ParseException, IOException {

        try {
            byte[] fileBytes = TURSI.findBlobFile(id);
            String filename = training_title + "_TrainingAbsense.pdf";

            response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            response.setHeader("cache-control", "no-cache");
            response.setHeader("cache-control", "must-revalidate");

            ServletOutputStream outs = response.getOutputStream();
            outs.write(fileBytes);
            outs.flush();
            outs.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        return "redirect:/manageHistory/" + id + "/" + training_title;
    }

    //Upload File
    @RequestMapping(value = "/uploadAbsenseForm", method = RequestMethod.POST)
    public String uploadFileRequesterMSHR(
            @RequestParam("fileUpload") MultipartFile filedocument,
            @RequestParam("trainingID") String Req,
            @RequestParam("trainingTitle") String title,
            @RequestParam("nik") Integer nik,
            String id,
            String myId,
            Model model)
            throws IOException, ParseException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        Timestamp updatedDate = new Timestamp(System.currentTimeMillis());
        byte[] bytefile = filedocument.getBytes();
        TURSI.uploadFile(bytefile, updatedDate, nik, Req);

        return "redirect:/manageHistory/" + Req + "/" + title;
    }

//    Export PDF
    @RequestMapping(value = "/download/certificate/{training_title}/{date}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String suratMSHRDownload(String myId, String id, Model model, HttpServletResponse response, @PathVariable("training_title") String training_title, @PathVariable("date") String training_date, @PathVariable("id") String training_id) throws ParseException, IOException, FileNotFoundException, InvalidFormatException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);
        String reuqester_name = "";
        Integer reuqester_nik = 0;
        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            reuqester_name = joUser.getString("Name");
            reuqester_nik = joUser.getInt("User_id");
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            reuqester_name = joEmpl.getString("Name");
            reuqester_nik = joEmpl.getInt("Employee_Nik");
        }

        Integer feedback_id = PFSI.getFeedbackId(training_id, reuqester_nik);

//        String reuqester_name = serviceAPI.EmployeeByNik(id).getname();
//        Integer reuqester_nik = serviceAPI.EmployeeByNik(id).getempl_nik();
        ExportToPdf export = new ExportToPdf();

        export.exportPDF1(response, reuqester_name, training_title, training_date, training_id, feedback_id.toString(), reuqester_nik.toString());
//        export.exportDOC1();
        return "redirect:/viewTrainingHistory";
    }

    //Export Excel
    @RequestMapping(value = "/download/Feedbacksummary/{training_title}/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String parkirSummaryDownload(String myId, HttpServletResponse response, Model model, @PathVariable("training_title") String training_title, @PathVariable("id") String training_id
    ) throws ParseException, IOException {

        List<Object[]> feedbackSummaryParticipants = new ArrayList<>();
        // 1. lu get semua partisipan, by training id, 16
        for (Object[] object : HTSI.getFeedbackSummaryParticipants(Integer.parseInt(training_id))) {
            Object[] objects = new Object[21];
            objects[0] = object[0];//nik
            // get name by API
            JSONObject json_userName = serviceAPI.GetUserInformation(object[0].toString());
            objects[1] = json_userName.getString("name");//name
            objects[2] = object[0];//nik

            // get nilai by NIK
            List<Object[]> feedbackSummary = new ArrayList<>();
            int counter = 3;
            for (Object[] sub_object : HTSI.getFeedbackSummary(Integer.parseInt(training_id), Integer.parseInt(objects[0].toString()))) {
                Object[] sub_objects = new Object[7];

                if (sub_object[4].toString().equals("")) {
                    objects[counter] = "N/A";
                } else {
                    objects[counter] = sub_object[4];
                }

                objects[counter] = sub_object[4];
                sub_objects[0] = sub_object[0]; //title
                sub_objects[1] = sub_object[1]; //trainer_name
                sub_objects[2] = sub_object[2]; //nik
                sub_objects[3] = sub_object[3]; //customer_id
                sub_objects[4] = sub_object[4]; //feedback response
                sub_objects[5] = sub_object[5]; //question content
                sub_objects[6] = sub_object[6]; //type
                counter++;
                feedbackSummary.add(sub_objects);

            }
            System.out.println("Size " + feedbackSummary.size());
//            System.out.println("Size " + feedbackSummary.size());
            if (feedbackSummary.size() <= 2) {
                for (int i = 3; i < 21; i++) {
                    System.out.println("Test : ");
                    objects[i] = "N/A";
                }
            }

            feedbackSummaryParticipants.add(objects);
        }

        ExcelExport export = new ExcelExport();
        export.exportExcel3(response, feedbackSummaryParticipants, training_title);
//        export.ExcelExport(response, listRequest, Integer.toString(nik_requester));
        return "traininganddevelopment/training/trainingFeedbackSummary";
    }

    //    CREATE & UPDATE-------------------------------------------------------------------------------------------------------------------------------------
    // ADMIN--------------------------------------------------------------------------
    @PostMapping("/AddSchedule")
    public String saveSchedule(@RequestParam("term") String title, @RequestParam("reqTrainer") String catalog_transaction_id, @RequestParam("trainerName") String trainer_name, @RequestParam("reqDate") String date, @RequestParam("reqTime") String time, @RequestParam("reqQuota") Integer quota, @RequestParam("reqLocation") String location, @RequestParam("reqRequirement") String requirement, @RequestParam("reqCreatedby") Integer createdby) {
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        Timestamp updatedDate = new Timestamp(System.currentTimeMillis());
        int active = 1;
        boolean bool = (active == 1);
//        DateTime test = new DateTime("2004-12-13T21:39:45.618-08:00");

        System.out.println(catalog_transaction_id);
        System.out.println(date);
        System.out.println(time);
        System.out.println(quota);
        System.out.println(location);
        System.out.println(requirement);

//        Integer def = 0;
//
//        Integer cast;
//        cast = Integer.valueOf(catalog_transaction_id);
//
//        TrainingCatalogTransactions transaction;
//        transaction = new TrainingCatalogTransactions(cast);
//
//        //2020-05-11 12:56:20
//        try {
//            System.out.println("Date:");
//            System.out.println(date + " " + time);
//
//            String dateTime = date + " " + time + ":00";
//            Timestamp timestamp = Timestamp.valueOf(dateTime);
//            System.out.println(timestamp);
//
//            TSchedulingSI.save(new TrainingTransactions(location, requirement, createdDate, quota, timestamp, transaction, updatedDate, bool, createdby, def));
//        } catch (Exception e) { //this generic but you can control another types of exception
//            e.printStackTrace();
//            // look the origin of excption 
//        }
//
//        String[] emailList = new String[99];
//
//        int a = 0;
//        List<JSONObject> listData = serviceAPI.adminNik();
//        for (JSONObject jSONObject : listData) {
//
//            if (jSONObject.getString("Role").equals("ROLE_RM")) {
//                String email = jSONObject.getString("Email");
//                System.out.println("email yang di get: " + email);
//                emailList[a] = email;
//
//            }
//            a++;
//        }
//
//        System.out.println("length : " + emailList.length);
//        for (Integer i = 0; i < emailList.length; i++) {
//            System.out.println("Email No : " + i + " Value : " + emailList[i]);
//        }
//
//        try {
//            String[] dateParts = date.split("-");
//            String datePart1 = dateParts[0];
//            String datePart2 = dateParts[1];
//            String datePart3 = dateParts[2];
//
//            System.out.println("Date Part 1 : " + datePart1);
//            System.out.println("Date Part 2 : " + datePart2);
//            System.out.println("Date Part 3 : " + datePart3);
//
//            String formed = datePart3 + "-" + datePart2 + "-" + datePart1;
//            System.out.println("Date formed to email : " + formed);
//
//            emailservice.sendNotificationServiceNewTrainingSchedule(emailList, title, formed, time, quota, trainer_name);
//        } catch (Exception e) {
//            System.out.println("Error Sending Email: " + e.getMessage());
//        }
        // date dan time di ambil lalu di parse jadi time stamp
        return "redirect:/trainingScheduling";
    }

    @PostMapping("/saveScheduleTraining")
    public String saveScheduleTraining(@RequestParam(value = "typeTraining") String typeTraining,
            @RequestParam(value = "term") String tittleTrining,
            @RequestParam(value = "reqDate") String dateTraining,
            @RequestParam(value = "reqTime") String timeTraining,
            @RequestParam(value = "reqQuota") String quotaTraining,
            @RequestParam(value = "reqTrainer") String idCatalogaTrasnaction,
            @RequestParam(value = "reqLocation") String location,
            @RequestParam(value = "linkTraining") String linkTraining,
            @RequestParam(value = "reqRequirement") String requirement) {
//        System.out.println("Type of Training = " + typeTraining);
//        System.out.println("Tittle Training = " + tittleTrining);
//        System.out.println("Date Training = " + dateTraining);
//        System.out.println("Time Training = " + timeTraining);
//        System.out.println("Quota Training = " + quotaTraining);
//        System.out.println("ID Catalog Transaction = " + idCatalogaTrasnaction);
//        System.out.println("Location = " + location);
//        System.out.println("Link = " + linkTraining);

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            JSONObject user = serviceAPI.findUserByEmail(email);
            int nikUser = user.getInt("User_Id");

            int active = 1;
            boolean bool = (active == 1);

            SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM-yyyy");
            Date date1 = new SimpleDateFormat("dddd MMMM yyyy", Locale.US).parse(dateTraining);
            String parseDate1 = formatDate.format(date1);
            Date date2 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").parse(parseDate1 + " " + timeTraining + ":00");

            int cast = Integer.valueOf(idCatalogaTrasnaction);
            TrainingCatalogTransactions transaction = new TrainingCatalogTransactions(cast);
            if (typeTraining.equalsIgnoreCase("0")) {
                TSchedulingSI.save(new TrainingTransactions(location, requirement, timeAdd6Hours(), Integer.valueOf(quotaTraining), date2, transaction, timeAdd6Hours(), bool, nikUser, 0, Boolean.FALSE));
            } else {
                TSchedulingSI.save(new TrainingTransactions(Integer.valueOf(quotaTraining), date2, bool, nikUser, Boolean.TRUE, linkTraining, 0, timeAdd6Hours(), timeAdd6Hours(), transaction));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/trainingScheduling";
    }

    @PostMapping("/EditScheduleOnline/{id}")
    public String editScheduleOnline(@PathVariable(value = "id") int id,
            @RequestParam(value = "upTrainer") String nameTrainer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        JSONObject user = serviceAPI.findUserByEmail(email);
        int nikUser = user.getInt("User_Id");
        int cast = Integer.valueOf(nameTrainer);
        TrainingCatalogTransactions catalogTransaction = new TrainingCatalogTransactions(cast);
        TrainingTransactions transactions = TSchedulingSI.editTrainingTransactionById(id);
        transactions.setTrainingCatalogTransactionId(catalogTransaction);
        transactions.setUpdatedBy(nikUser);
        TSchedulingSI.save(transactions);
        return "redirect:/trainingScheduling";
    }

    @RequestMapping(value = "/cancelScheduleOnline", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String cancelScheduleOnline(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        JSONObject user = serviceAPI.findUserByEmail(email);
        int nikUser = user.getInt("User_Id");

        String getId = request.getParameter("id");
        int id = Integer.valueOf(getId);
        String remark = request.getParameter("reason");

        TrainingTransactions transactions = TSchedulingSI.editTrainingTransactionById(id);
        transactions.setIsActive(Boolean.FALSE);
        transactions.setRemark(remark);
        transactions.setUpdatedBy(nikUser);
        transactions.setUpdatedDate(timeAdd6Hours());
        TSchedulingSI.save(transactions);

        List<Object[]> listRequester = new ArrayList<>();
        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
        for (Object[] object : MPRSI.getRegisteredUserForCancelation(id)) {
            Object[] objects = new Object[8];
            objects[0] = object[0]; // id
            objects[1] = object[1]; // empl_nik
            objects[2] = object[2]; // training_title
            objects[3] = object[3]; // value
            objects[4] = object[4]; // transaction_id
            objects[5] = formatdate.format(object[5]); // training_time
            objects[6] = serviceAPI.findEmpbyid((objects[1].toString())).getString("Name");
            objects[7] = serviceAPI.findEmpbyid((objects[1].toString())).getString("Email");

//            System.out.println(serviceAPI.EmployeeByNik(objects[1].toString()).getString("Name"));
//            System.out.println(serviceAPI.EmployeeByNik(objects[1].toString()).getString("Email"));
            String name = objects[6].toString();
            String emailRequest = objects[7].toString();

            try {
                emailservice.sendNotificationServiceTrainingCancelation(emailRequest, name, objects[2].toString(), objects[5].toString(), remark);
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }
            listRequester.add(objects);
        }

        String result = "SUCCESS CANCEL";
        return result;
    }

    @PostMapping("/EditScheduleOffline/{id}")
    public String editSchedule(@PathVariable("id") int id,
            @RequestParam("upTrainer") String namaTrainer) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        JSONObject user = serviceAPI.findUserByEmail(email);
        int nikUser = user.getInt("User_Id");

        System.out.println(namaTrainer);
        int cast = Integer.valueOf(namaTrainer);

        TrainingCatalogTransactions catalogTransactions = new TrainingCatalogTransactions(cast);
        TrainingTransactions transactions = TSchedulingSI.editTrainingTransactionById(id);
        transactions.setTrainingCatalogTransactionId(catalogTransactions);
        transactions.setUpdatedBy(nikUser);
        transactions.setUpdatedDate(timeAdd6Hours());
        TSchedulingSI.save(transactions);

        //2020-05-11 12:56:20
//        try {
//
//            String converted = date;
//            String[] parts = converted.split("-");
//            String part1 = parts[0];
//            String part2 = parts[1];
//            String part3 = parts[2];
//
//            String reformat = part3 + "-" + part2 + "-" + part1;
//
//            System.out.println("Date:");
//            System.out.println(reformat + " " + time);
//
//            String dateTime = reformat + " " + time;
//            Timestamp timestamp = Timestamp.valueOf(dateTime);
//            System.out.println(timestamp);
//
//            TSchedulingSI.save(new TrainingTransactions(id, location, requirement, createdDate, quota, timestamp, transaction, updatedDate, bool, createdby, updatedby));
//        } catch (Exception e) { //this generic but you can control another types of exception
//            e.printStackTrace();
//            // look the origin of excption 
//        }
        // date dan time di ambil lalu di parse jadi time stamp
        return "redirect:/trainingScheduling";
    }

    @PostMapping(value = "manageRegistration/Approved/{id}/{name}/{email}")
    public String approveRegistration(@PathVariable("id") Integer id, @RequestParam("admin") Integer approvedBy,
            @RequestParam("training_title") String training_title,@RequestParam("training_time") String training_time, @RequestParam("registrator_nik") Integer registrator_nik,
            @RequestParam("id") Integer refreshId, @PathVariable("name") String name, @PathVariable("email") String req_email) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (TURSI.findById(id) != null & TURSI.findById(id).getMOtherId().equals(16) || TURSI.findById(id).getMOtherId().equals(24)) {
            //req_email exist
            String email = req_email;

            //Get Link to confirm / decline
            String link_Confirm = tbMParamService.findConfirmURL();
            String link_Decline = tbMParamService.findDeclineURL();

            try {
                emailservice.sendNotificationServiceAttendanceRegistration(email, name, training_title, training_time, id, registrator_nik, link_Confirm, link_Decline);
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }

            MPRSI.approved(id, approvedBy, timestamp);
        } else {
            System.out.println("Data NULL, Tidak Berhasil");
        }

        return "redirect:/manageRegistration/" + refreshId;
    }


    @GetMapping(value = "manageRegistration/sendQRCodeandDetail/{id}/{user}/{admin}/{rId}/{nik}/{email}")
    public String sendQRCode(@PathVariable("id") Integer id, @PathVariable("user") String user, @PathVariable("admin") Integer sendBy, @PathVariable("rId") Integer refreshId, @PathVariable("nik") Integer nik, @PathVariable("email") String req_email) throws WriterException,
            IOException,
            NotFoundException {
        if (TURSI.findById(id) != null && TURSI.findById(id).getMOtherId().equals(8)) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String name = user;
            // req_email exist
            String email = req_email;
            // The path where the image will get saved 
            String savePath = tbMParamService.findQRSavePath();
            //Generate UUID
            String random = UUID.randomUUID().toString().substring(0, 10);
            //Generate QR Code
            qrCode.generateQRCodeImage(random, 450, 450, savePath + random + ".png");

            //Get QRCode Path
            String qrPath = tbMParamService.findQRPath() + random + ".png";

            try {
                emailservice.sendNotificationServiceDetailRegistration(email, name, qrPath);
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }

            MPRSI.sentDetail(id, sendBy, timestamp, random);

            Path fileDeletePath = FileSystems.getDefault().getPath(tbMParamService.findQRPath() + random + ".png");
            Files.deleteIfExists(fileDeletePath);
        } else {
            System.out.println("ERROR DATA NULL");
        }
        return "redirect:/manageRegistration/" + refreshId;
    }

    @GetMapping(value = "manageRegistration/sendFeedback/{id}/{user}/{admin}/{training}/{rId}/{email}/{nik}")
    public String sendFeedbackForm(@PathVariable("id") Integer id, @PathVariable("user") String user, @PathVariable("admin") Integer sendBy, @PathVariable("training") Integer transaction, @PathVariable("rId") Integer refreshId, @PathVariable("email") String req_email, @PathVariable("nik") Integer nik) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        int active = 0;
//        boolean bool = (active == 0);

        String training = MPRSI.TrainingTitleForm(transaction);
        String feedbackLink = tbMParamService.findFeedbackFormURL();

        System.out.println("id " + id);
        System.out.println("When " + timestamp);

        String name = user;
        // req_email exist
        String email = req_email;

        try {
            emailservice.sendNotificationServiceFeedbackForm(email, name, training, transaction, nik, feedbackLink);
        } catch (Exception e) {
            System.out.println("Error Sending Email: " + e.getMessage());
        }

        MPRSI.sentFeedback(id, sendBy, timestamp);
        return "redirect:/manageRegistration/" + refreshId;
    }

    @PostMapping(value = "manageRegistration/rejected/{id}/{name}/{email}")
    public String rejectedRegistration(@PathVariable("id") Integer id, @RequestParam("admin") Integer approvedBy, @RequestParam("training_title") String training_title, @RequestParam("reason") String reason, @RequestParam("id") Integer refreshId, @PathVariable("name") String name, @PathVariable("email") String req_email) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        int active = 0;
//        boolean bool = (active == 0);

        System.out.println("title " + training_title);
        System.out.println("id " + id);
        System.out.println("Approved By " + approvedBy);
        System.out.println("reason " + reason);
        System.out.println("When " + timestamp);

//      req_email exist
        String email = req_email;

        try {
            emailservice.sendNotificationServiceRegistrationRejected(email, name);
        } catch (Exception e) {
            System.out.println("Error Sending Email: " + e.getMessage());
        }

        MPRSI.rejected(id, approvedBy, timestamp, reason);

        return "redirect:/manageRegistration/" + refreshId;
    }

    @PostMapping(value = "manageRegistration/sendToQueue/{id}/{name}/{email}")
    public String sendToWaiting(@PathVariable("id") Integer id, @RequestParam("admin") Integer approvedBy, @RequestParam("training_title") String training_title, @RequestParam("training_time") String training_time, @RequestParam("id") Integer refreshId, @PathVariable("name") String name, @PathVariable("email") String req_email) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        int active = 0;
//        boolean bool = (active == 0);

        System.out.println("title " + training_title);
        System.out.println("id " + id);
        System.out.println("Approved By " + approvedBy);
        System.out.println("When " + timestamp);

//      req_email exist
        String email = req_email;
//        String subject = "Test Send";

        try {
            emailservice.sendNotificationServiceWaitingList(email, name,training_title,training_time);
        } catch (Exception e) {
            System.out.println("Error Sending Email: " + e.getMessage());
        }

        MPRSI.waitingList(id, approvedBy, timestamp);

        return "redirect:/manageRegistration/" + refreshId;
    }

    @GetMapping(value = "manageTrainingRequest/Approve/{id}/{admin}/{training}/{requester}")
    public String approveRequest(@PathVariable("id") Integer id, @PathVariable("admin") Integer approvedBy, @PathVariable("training") String training_title, @PathVariable("requester") String requester_name) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        int active = 0;
//        boolean bool = (active == 0);

        List<JSONObject> listEmp = serviceAPI.listAutoComplete(requester_name);// Get data berdasarkan NIK
        for (JSONObject jSONObj : listEmp) {

            String email = jSONObj.getString("email");

            System.out.println("id " + id);
            System.out.println("Approved By " + approvedBy);
            System.out.println("Training Title " + training_title);
            System.out.println("Requester " + requester_name);
            System.out.println("When " + timestamp);

            try {
                emailservice.sendNotificationServiceRequestAccepted(email, requester_name, training_title);
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }

        }
        MTrainingRSI.acceptrequest(id, approvedBy, timestamp);

        return "redirect:/manageTrainingRequest";
    }

    @PostMapping(value = "manageTrainingRequest/Reject/{id}/{admin}/{requester}")
    public String rejectRequest(@PathVariable("id") Integer id, @PathVariable("admin") Integer approvedBy, @RequestParam("reason") String remark, @PathVariable("requester") String requester_name) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<JSONObject> listEmp = serviceAPI.listAutoComplete(requester_name);// Get data berdasarkan NIK
        for (JSONObject jSONObj : listEmp) {

            String email = jSONObj.getString("email");

            System.out.println("id " + id);
            System.out.println("Approved By " + approvedBy);
            System.out.println("Requester " + requester_name);
            System.out.println("When " + timestamp);

            try {
                emailservice.sendNotificationServiceRequestRejected(email, requester_name, remark);
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }
        }
        MTrainingRSI.rejectRequest(id, approvedBy, timestamp, remark);

        return "redirect:/manageTrainingRequest";
    }

    //Participant------------------------------------------------------------------
    @PostMapping("/Register/{name}/{email}/{training_title}")
    public String registerTraining(@RequestParam("employee_nik") Integer nik, @RequestParam("employee_allocation") String customer, @RequestParam("transaction") Integer transaction, @PathVariable("name") String name, @PathVariable("email") String req_email, @PathVariable("training_title") String training_title) {
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        Timestamp updatedDate = new Timestamp(System.currentTimeMillis());

        Integer feedback_response = 0;
        Integer other_id = 16;
        int active = 1;
        boolean bool = (active == 1);
        Integer createdby = nik;
        Integer updatedby = nik;

        //Adapt FK
//        Integer cast1;
//        cast1 = transaction;
//
//        TrainingTransactions transactionId;
//        transactionId = new TrainingTransactions(cast1);
//
//        TrainingFeedbackResponse responseId;
//        responseId = new TrainingFeedbackResponse(feedback_response);
//
//        MOthers othersId;
//        othersId = new MOthers(other_id);
        //req_email exist//
//        String email = req_email;
        //Ambil Email HR
        List<JSONObject> listData = serviceAPI.adminNik();

        for (JSONObject jSONObject : listData) {
            if (jSONObject.getString("Role").equals("ROLE_MSHR_TRA_DEV")) {
                String email = jSONObject.getString("Email");
                System.out.println("Email HR : " + email);
                //kirim email
                try {
                    emailservice.sendNotificationServiceRequesterRegistration(email, name, training_title, nik);
                } catch (Exception e) {
                    System.out.println("Error Sending Email: " + e.getMessage());
                }
            }
        }

//        DateTime test = new DateTime("2004-12-13T21:39:45.618-08:00");
        System.out.println("Nik : " + nik);
        System.out.println("Customer : " + customer);
        System.out.println("transaction : " + transaction);
        System.out.println("feedback : " + feedback_response);
        System.out.println("other_id : " + other_id);
        System.out.println("Created by : " + createdby);
        System.out.println("Created date : " + createdDate);
        System.out.println("Created by : " + updatedby);
        System.out.println("Updated by : " + updatedDate);

        TURSI.save(new TrainingUserRegistration(nik, customer, transaction, feedback_response, other_id, bool, createdby, createdDate, updatedby, updatedDate));

        // date dan time di ambil lalu di parse jadi time stamp
        return "redirect:/trainingSchedule";
    }

    @PostMapping("/submitRequest/{nik}/{customerId}")
    public String requestTraining(String myId, String id, @PathVariable("nik") Integer nik, @PathVariable("customerId") String customer, @RequestParam("term") String title, @RequestParam("TrainingId") Integer trainingId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

//        String requester_name = serviceAPI.EmployeeByNik(nik.toString()).getname();

        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        Timestamp updatedDate = new Timestamp(System.currentTimeMillis());

        Integer check = null;

        if (Objects.equals(trainingId, check)) {

            trainingId = 99;

        }

        int approved = 1;
        boolean isApproved = (approved == 1);

        String notes = "";
        Integer feedback_response = 0;
        Integer other_id = 16;
        Integer createdby = nik;
        Integer updatedby = nik;

        //Adapt FK
        TrainingCatalogs catalogId;
        catalogId = new TrainingCatalogs(trainingId);

        MOthers othersId;
        othersId = new MOthers(other_id);

        //Ambil Email HR
        List<JSONObject> listData = serviceAPI.adminNik();

//        for (JSONObject jSONObject : listData) {
//            if (jSONObject.getString("Role").equals("ROLE_MSHR_TRA_DEV")) {
//                String email = jSONObject.getString("Email");
//                System.out.println("Email HR : " + email);
//                //kirim email
//                try {
//                    emailservice.sendNotificationServiceNewTrainingRequest(email, requester_name, nik.toString());
//                } catch (Exception e) {
//                    System.out.println("Error Sending Email: " + e.getMessage());
//                }
//            }
//        }
        System.out.println("Nik : " + nik);
        System.out.println("Customer : " + customer);
        System.out.println("Training catalog : " + catalogId);
        System.out.println("Training title : " + title);
        System.out.println("Notes : " + notes);
        System.out.println("M_other : " + othersId);
        System.out.println("Created by : " + createdby);
        System.out.println("Created date : " + createdDate);
        System.out.println("Created by : " + updatedby);
        System.out.println("Updated by : " + updatedDate);

        TURequestsService.save(new TrainingUserRequests(nik, customer, catalogId, title, notes, other_id, isApproved, createdby, createdDate, updatedby, updatedDate));

        // date dan time di ambil lalu di parse jadi time stamp
        return "redirect:/requestTraining";
    }

    @GetMapping(value = "/confirmPresence/{id}/{number}")
    public String confirmPresence(@PathVariable("id") Integer id, @PathVariable("number") Integer send_by_number) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

//        System.out.println("accept ID " + id);
//        System.out.println("When " + timestamp);
        if (TURSI.findById(id) != null && TURSI.findById(id).getMOtherId().equals(17)) {
            MPRSI.accepted(id, send_by_number, timestamp);
        }

        return "/requester/traininganddevelopment/landingDummyConfirmationAccept";
    }

//    @GetMapping(value = "/checkin/{id}/{nik}/{uuid}")
//    public String checkin(@PathVariable("id") Integer id, @PathVariable("nik") Integer nik, @PathVariable("uuid") String uuid) {
//        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//
//        System.out.println("accept ID " + id);
//        System.out.println("When " + timestamp);
//
//        MPRSI.checkIn(id, nik, timestamp);
//
//        return "redirect:/home";
//
//    }
    @PostMapping(value = "/submitParticipantForm/{number}/{customer}/{transaction_id}")
    public String filloutFormParticipant(@PathVariable("number") Integer nik, @PathVariable("customer") String customer, @PathVariable("transaction_id") Integer transaction_id, @RequestParam("questionNumber") Integer questionNumber, @RequestParam("response1") String response1, @RequestParam("response2") String response2, @RequestParam("response3") String response3, @RequestParam("response4") String response4, @RequestParam("response5") String response5,
            @RequestParam("response6") String response6, @RequestParam("response7") String response7, @RequestParam("response8") String response8, @RequestParam("response9") String response9, @RequestParam("response10") String response10,
            @RequestParam("response11") String response11, @RequestParam("response12") String response12, @RequestParam("response13") String response13, @RequestParam("response14") String response14, @RequestParam("response15") String response15,
            @RequestParam("response16") String response16, @RequestParam("response17") String response17, @RequestParam("response18") String response18) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String[] temp = {response1, response2, response3, response4, response5, response6, response7, response8, response9, response10, response11, response12, response13, response14, response15, response16, response17, response18, "buffer"};

        System.out.println("When " + timestamp);

        for (int i = 1; questionNumber - 1 < 18; questionNumber++) {
            Integer cast;
            cast = questionNumber;

            TrainingFeedbackQuestions questionID;
            questionID = new TrainingFeedbackQuestions(cast);

            PFSI.save(new TrainingFeedbackResponse(nik, customer, transaction_id,questionID, temp[questionNumber - 1], nik, timestamp, nik, timestamp));
        }

        TURSI.disableFlag(timestamp, nik, transaction_id, nik);
        MPRSI.submitFeedbackUpdateStatus(transaction_id, nik, timestamp);
        return "/requester/traininganddevelopment/landingDummyForm";

    }

    //    DELETE -------------------------------------------------------------------------------------------------------------------------------------------
    @PostMapping(value = "/canceltraining/{id}")
    public String disableTraining(@PathVariable("id") Integer id,
            @RequestParam("reason") String remark) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Cancel ID " + id);
        System.out.println("Remarks " + remark);
        System.out.println("When " + timestamp);

//        List<Object[]> listRequester = new ArrayList<>();
//
//        for (Object[] object : MPRSI.getRegisteredUserForCancelation(id)) {
//            Object[] objects = new Object[7];
//            objects[0] = object[0]; // id
//            objects[1] = object[1]; // empl_nik
//            objects[2] = object[2]; // training_title
//            objects[3] = object[3]; // value
//            objects[4] = object[4]; // transaction_id
//            objects[5] = serviceAPI.EmployeeByNik((objects[1].toString())).getname();
//            objects[6] = serviceAPI.EmployeeByNik((objects[1].toString())).getemail();
//
//            System.out.println(serviceAPI.EmployeeByNik(objects[1].toString()).getname());
//            System.out.println(serviceAPI.EmployeeByNik(objects[1].toString()).getemail());
//
//            listRequester.add(objects);
//
//            String name = objects[5].toString();
//            //email pake objects[6]
//            String email = objects[6].toString();
//
//            try {
//                emailservice.sendNotificationServiceTrainingCancelation(email, name, training_title, training_date, remark);
//            } catch (Exception e) {
//                System.out.println("Error Sending Email: " + e.getMessage());
//            }
//        }
//        TSchedulingSI.canceltraining(id, canceledby, timestamp, remark);
        return "redirect:/trainingScheduling";
    }

    @GetMapping(value = "/declinePresence/{id}/{number}")
    public String declinePresence(@PathVariable("id") Integer id, @PathVariable("number") Integer send_by_number) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        System.out.println("accept ID " + id);
        System.out.println("When " + timestamp);

        if (TURSI.findById(id) != null && TURSI.findById(id).getMOtherId().equals(17)) {
            MPRSI.decline(id, send_by_number, timestamp);
        }

        return "/requester/traininganddevelopment/landingDummyConfirmationDecline";
    }

    //CHECKBOX SELECT ALL
    @PostMapping(value = "manageRegistration/reject")
    public String rejectRegistration(@RequestParam("idIn") Integer[] id, @RequestParam("admin") Integer approvedBy, @RequestParam("id") Integer refreshId,
            @RequestParam("training_title") String training_title, @RequestParam("training_time") String training_time, @RequestParam("registrator_nik") Integer registrator_nik) {
        System.out.println("coba delet");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Object[]> namas = new ArrayList<>();
        for (Object[] datas : MPRSI.getName(refreshId,id)) {
            Object[] data = new Object[4];
            data[0] = datas[0];//name
            data[1] = datas[1];//email
            data[2] = datas[2];//nik
            data[3] = datas[3];//tur.id
            namas.add(datas);

            System.out.println(Arrays.toString(data));

            try {
                emailservice.sendNotificationServiceRegistrationRejected(data[1].toString(), data[0].toString());
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }
        }
        MPRSI.bulkReject(id, approvedBy, timestamp, refreshId);

        return "redirect:/manageRegistration/" + refreshId;
    }

    @PostMapping(value = "manageRegistration/accept")
    public String acceptRegistration(@RequestParam("idIn") Integer[] id, @RequestParam("admin") Integer approvedBy, @RequestParam("id") Integer refreshId,
            @RequestParam("training_title") String training_title, @RequestParam("training_time") String training_time, @RequestParam("registrator_nik") Integer registrator_nik
    ) {
        System.out.println("coba accept");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Object[]> namas = new ArrayList<>();
        for (Object[] datas : MPRSI.getName(refreshId,id)) {
            Object[] data = new Object[4];
            data[0] = datas[0];//name
            data[1] = datas[1];//email
            data[2] = datas[2];//nik
            data[3] = datas[3];//tur.id
            namas.add(datas);

            System.out.println(Arrays.toString(data));

            String link_Confirm = tbMParamService.findConfirmURL();
            String link_Decline = tbMParamService.findDeclineURL();

            try {
                emailservice.sendNotificationServiceAttendanceRegistration(data[1].toString(), data[0].toString(), training_title, training_time, Integer.parseInt(data[3].toString()), Integer.parseInt(data[2].toString()), link_Confirm, link_Decline);
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }
        }
//        String email = MPRSI.getEmail(id).toString();
//        Integer nik = MPRSI.getNik(id);
        //Get Link to confirm / decline

        MPRSI.bulkAccept(id, approvedBy, timestamp, refreshId);

        return "redirect:/manageRegistration/" + refreshId;
    }

    @PostMapping(value = "manageRegistration/waitingList")
    public String waitingRegistration(@RequestParam("idIn") Integer[] id, @RequestParam("admin") Integer approvedBy, @RequestParam("id") Integer refreshId,
            @RequestParam("training_title") String training_title, @RequestParam("training_time") String training_time, @RequestParam("registrator_nik") Integer registrator_nik
    ) {
        System.out.println("coba waiting");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        List<Object[]> namas = new ArrayList<>();
        for (Object[] datas : MPRSI.getName(refreshId,id)) {
            Object[] data = new Object[4];
            data[0] = datas[0];//name
            data[1] = datas[1];//email
            data[2] = datas[2];//nik
            data[3] = datas[3];//tur.id
            namas.add(datas);

            System.out.println(Arrays.toString(data));

            try {
                emailservice.sendNotificationServiceWaitingList(data[1].toString(), data[0].toString(), training_title, training_time);
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }
        }
        MPRSI.bulkWaiting(id, approvedBy, timestamp, refreshId);

        return "redirect:/manageRegistration/" + refreshId;
    }
    // MEJA KERJA
    @GetMapping("/trainingMejaKerja")
    public String trainingMejaKerja(String myId, String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        DateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);
        Integer nikUser = null;
        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            nikUser = joUser.getInt("User_Id");
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }
        model.addAttribute("empl_nik", id);
        
        String editPermission;
        if(nikUser == 12801 || nikUser == 13435){
            editPermission = "yes";
        } else {
            editPermission = "no";
        }
        List<Object[]> trainingMejaKerja = new ArrayList<>();
        for (Object[] object : mejaKerjaServiceImpl.getDataTrainingMejaKerja()) {
            Object[] objects = new Object[5];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = formatdate.format(object[4]); // tanggal (RAW)
            trainingMejaKerja.add(objects);
        }

        model.addAttribute("ListTrainingMejaKerja", trainingMejaKerja);
        model.addAttribute("editPermission", editPermission);
        return "traininganddevelopment/training/trainingMejaKerja";
    }

    @Transactional
    @RequestMapping(value = "/uploadDataTraining", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadBast(RedirectAttributes redirect, @RequestParam("file") MultipartFile file) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            System.out.println(sheet.getLastRowNum());
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                if (row.getCell(0).toString().length() == 0) {
                    break;
                }
                String employeeNIK = row.getCell(0).getRawValue();
                String email = row.getCell(1).toString();
                String name = row.getCell(2).toString();
                String trainingTitle = row.getCell(3).toString();
                Date trainingDate = row.getCell(4).getDateCellValue();
                long millis=System.currentTimeMillis();
                java.sql.Date date=new java.sql.Date(millis);
                    
                TbMMejakerja tbMMejakerja = mejaKerjaServiceImpl.findByNikAndTrainingTitle(email, trainingTitle);
                if(tbMMejakerja != null){
                    
                    mejaKerjaServiceImpl.updateTbMMejaKerja(Integer.parseInt(employeeNIK), email, name, trainingTitle, trainingDate, date);
                } else{
                    mejaKerjaServiceImpl.saveTbMMejaKerja(Integer.parseInt(employeeNIK), email, name, trainingTitle, trainingDate, date);
                }

                System.out.println("CEK BERHASIL !!!");
            }
//            redirect.addFlashAttribute("successUploadTrainingMejaKerja", "succes");
//            redirect.addFlashAttribute("message", "Data Saved Successfully");
        } catch (Exception e) {
            System.out.println("exception : " + e);
            System.out.println("CEK GAGAL !!!");
//            redirect.addFlashAttribute("failedTrainingMejaKerja", "Failed to Save Data");
        }
        return "redirect:/trainingMejaKerja";
    }
    
    @RequestMapping(value = "/historyTrainingEmp/{id}", method = RequestMethod.GET)
    public String getViewRiwayatBastDetail(@PathVariable("id") Integer id, Model model,
            HttpServletResponse response) throws IOException {
        
        List<Object[]> historyList = new ArrayList<>();
        for (Object[] history : MPRSI.getDataTrainingHistory(id)) {
            Object[] histories = new Object[3];

            histories[0] = history[0];//judul
            histories[1] = history[1];//tanggal
            histories[2] = history[2];//nik

            historyList.add(histories);

            
        }
        
        model.addAttribute("detailhistory", historyList);
        return "/traininganddevelopment/training/ManageRegistration :: detailhistorytraining";

    }
    public Date timeAdd6Hours() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, +7);
        System.out.println("now ==  " + now);
//        System.out.println("calendar = "+calendar);
        System.out.println("pengurangan " + calendar.getTime());
        return calendar.getTime();
    }
}
