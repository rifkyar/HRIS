/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.controller.exportToDoc.ExportDoc;
import static com.springboot.HRISNEW.controller.requester.LeaveRequestController.userId;
import com.springboot.HRISNEW.entities.AdminAPI;
import com.springboot.HRISNEW.entities.RequesterInformation;
import com.springboot.HRISNEW.entities.TbMForm;
import com.springboot.HRISNEW.entities.TbMStatus;
import com.springboot.HRISNEW.entities.TbTrApprovalDocumentRequest;
import com.springboot.HRISNEW.entities.TbTrEmployeeForm;
import com.springboot.HRISNEW.entities.TbTrEmployeeFormDetails;
import com.springboot.HRISNEW.implementservices.ApprovalServiceImpl;
import com.springboot.HRISNEW.implementservices.DocumentServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestDetailServiceImp;
import com.springboot.HRISNEW.implementservices.RequesterInformServiceImpl;
import com.springboot.HRISNEW.repositories.DocumentRepo;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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

/**
 *
 * @author Dharta
 */
@Controller
public class DocumentRequestController {

    @Autowired
    private DocumentServiceImpl DSImplement;

    @Autowired
    private ServiceApi serviceAPI;

    @Autowired
    private EmailService emailService;

    public static String myId;

    @GetMapping(value = "/saveDocumentRequest")
    public String GetData(String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", id);
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            model.addAttribute("nik", id);
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        model.addAttribute("DocumentList", DSImplement.findAll());

        List<JSONObject> listData = serviceAPI.listAutoComplete(id); // Get data berdasarkan NIK
        List<String> listEmployee = new ArrayList<>();
        for (JSONObject jSONObject : listData) {
            try {
                listEmployee.add(jSONObject.getString("position"));
            } catch (Exception e) {
                listEmployee.add("-");
            }

            try {
                listEmployee.add(jSONObject.getString("no_ktp"));
            } catch (Exception e) {
                listEmployee.add("-");
            }
            listEmployee.add(jSONObject.getString("customer_name"));
            listEmployee.add(jSONObject.getString("name"));
            listEmployee.add(jSONObject.getInt("empl_nik") + "");
            listEmployee.add(jSONObject.getString("address_street"));
            listEmployee.add(jSONObject.getString("placeofbirth"));
            listEmployee.add(jSONObject.getString("join_date"));
            listEmployee.add(jSONObject.getString("dateofbirth"));
            listEmployee.add(jSONObject.getInt("gender") + "");

            // String -> Date   
            String pattern = "dd MMMM yyyy";
            String pattern2 = "dd-MMMM-yyyy";
            String sDate6 = jSONObject.getString("join_date");
            String sDate7 = jSONObject.getString("dateofbirth");
            SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date6 = formatter6.parse(sDate6);
            Date date7 = formatter6.parse(sDate7);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            String tanggalhasil = simpleDateFormat.format(date6);
            String tanggalhasil2 = simpleDateFormat.format(date7);

            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

            String tanggalh = simpleDateFormat2.format(date6);
            String tanggalhl2 = simpleDateFormat2.format(date7);

            model.addAttribute("join_date", tanggalhasil);
            model.addAttribute("tanggallahir", tanggalhasil2);

            model.addAttribute("joinDateDb", tanggalh);
            model.addAttribute("tglLahirDB", tanggalhl2);
        }
        model.addAttribute("data_employee", listEmployee);

        Integer ApproveBy;
        String email;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            String comparison = "ROLE_MSHR_ADMIN";

            if (comparison.equals(jSONObject.getString("Role"))) {
                ApproveBy = jSONObject.getInt("Nik");
                email = jSONObject.getString("Email");
//                System.out.println(ApproveBy);
                model.addAttribute("nikApproveBy", ApproveBy);
                model.addAttribute("emailAdm", email);
            } else {

            }

        }

        return "requester/documentrequest/documentRequest";
    }

//   Save
    @PostMapping(value = "/saveDocumentRequest")
    public String saveDocReq(
            @RequestParam("no_ktp") String ktp,
            @RequestParam("gender") Integer gender,
            @RequestParam("empl_nik") Integer nik,
            @RequestParam("name") String nama,
            @RequestParam("passport") String passport,
            @RequestParam("position") String position,
            @RequestParam("customer_name") String company,
            @RequestParam("placeofbirth") String birthPlace,
            @RequestParam("tmpTgl") String birthDate,
            @RequestParam("address") String address,
            @RequestParam("type") Integer type,
            @RequestParam("join_date") String joinDate,
            @RequestParam("nikApproveBy") Integer approveBy,
            @RequestParam("emailAdm") String emailAdm,
            @RequestParam("notes") String description, Model model, String id)
            throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        //ID REQUESTER
        Integer REQID = DSImplement.Countrowbynik(nik);
        int IDRequester = REQID + 1;

        String Req = "REQ-" + nik + IDRequester;

        //Save Date
        String sDate8 = joinDate;
        String sDate9 = birthDate;
        SimpleDateFormat formatter6 = new SimpleDateFormat("dd-MMMM-yyyy");

        //+1 Join Date
//        String[] seperate = sDate8.split("-");
//        String pisah1 = seperate[0];
//        String pisah2 = seperate[1];
//        String pisah3 = seperate[2];
//
//        String s = pisah1;
//        int n = Integer.parseInt(s);
//        int tanggal = n + 1;
//
//        String tglJoinD = tanggal + "-" + pisah2 + "-" + pisah3;
        //+1 BirthDate
//        String endpisah = sDate9;
//        String[] berpisah = endpisah.split("-");
//        String splot1 = berpisah[0];
//        String splot2 = berpisah[1];
//        String splot3 = berpisah[2];
//
//        String l = splot1;
//        int m = Integer.parseInt(l);
//        int tanggalBirthDate = m + 1;
//
//        String tglLahir = tanggalBirthDate + "-" + splot2 + "-" + splot3;
        Date date8 = formatter6.parse(sDate8);
        Date date9 = formatter6.parse(sDate9);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Boolean isDelete = false;

        int emp = type;
        TbMForm Tbmform;
        Tbmform = new TbMForm(emp);

        Integer stats = 3;
        TbMStatus status;
        status = new TbMStatus(stats);

        String emp2 = Req;
        TbTrEmployeeForm ReqID;
        ReqID = new TbTrEmployeeForm(emp2);
        String remark = "REQ " + nik;

        String email = emailAdm;
        String name = nama;
        String Enik = String.valueOf(nik);
        String typee = String.valueOf(DSImplement.FormTypeById(emp).getName());

        try {
            emailService.sendNotificationSuratKeterangan(email, name, Enik, typee);
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        model.addAttribute("DocumentList", DSImplement.FormTypeById(emp).getId());

        DSImplement.save(new TbTrEmployeeForm(Req, stats, description, nama, nik, timeAdd6Hours(), isDelete, Tbmform, approveBy));
        DSImplement.save(new TbTrEmployeeFormDetails(ktp, nik, nama, gender, passport, position, company, birthPlace, date9, address, timeAdd6Hours(), date8, ReqID));
        DSImplement.save(new TbTrApprovalDocumentRequest(nik, timeAdd6Hours(), ReqID, status, remark));

        return "redirect:/viewDocumentReqEmployee/" + Req;
    }

    //Resubmit1
    @GetMapping(value = "/resubmitDocumentRequest/{id}")
    public String resubmitDocumentRequest(@PathVariable("id") String id, String uID, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            uID = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", uID);
        } else if (joEmpl != null) {
            uID = String.valueOf(joEmpl.getInt("Employee_Nik"));
            model.addAttribute("nik", uID);
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        model.addAttribute("DocumentList", DSImplement.findAll());

        List<JSONObject> listData = serviceAPI.listAutoComplete(uID); // Get data berdasarkan NIK
        List<String> listEmployee = new ArrayList<>();
        for (JSONObject jSONObject : listData) {
            try {
                listEmployee.add(jSONObject.getString("position"));
            } catch (Exception e) {
                listEmployee.add("-");
            }

            try {
                listEmployee.add(jSONObject.getString("no_ktp"));
            } catch (Exception e) {
                listEmployee.add("-");
            }
            listEmployee.add(jSONObject.getString("customer_name"));
            listEmployee.add(jSONObject.getString("name"));
            listEmployee.add(jSONObject.getInt("empl_nik") + "");
            listEmployee.add(jSONObject.getString("address_street"));
            listEmployee.add(jSONObject.getString("placeofbirth"));
            listEmployee.add(jSONObject.getString("join_date"));
            listEmployee.add(jSONObject.getString("dateofbirth"));
            listEmployee.add(jSONObject.getInt("gender") + "");

            // String -> Date   
            String pattern = "dd MMMM yyyy";
            String pattern2 = "dd-MMMM-yyyy";

            String sDate6 = jSONObject.getString("join_date");
            String sDate7 = jSONObject.getString("dateofbirth");
            SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date6 = formatter6.parse(sDate6);
            Date date7 = formatter6.parse(sDate7);

            Date jDate = formatter6.parse(sDate6);
            Date dBirthDate = formatter6.parse(sDate7);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

            String tglJDate = simpleDateFormat2.format(jDate);
            String tglBirtDate = simpleDateFormat2.format(dBirthDate);

            String tanggalhasil = simpleDateFormat.format(date6);
            String tanggalhasil2 = simpleDateFormat.format(date7);

            model.addAttribute("jDateDB", tglJDate);
            model.addAttribute("tglLahirDB", tglBirtDate);

            model.addAttribute("joindate", tanggalhasil);
            model.addAttribute("tanggallahir", tanggalhasil2);
        }
        model.addAttribute("data_employee", listEmployee);

        List<Object[]> DocDetails = new ArrayList<>();
        for (Object[] objectss : DSImplement.alldoc(id)) {
            Object[] objectssss = new Object[13];
            model.addAttribute("nik", objectss[0]);
            model.addAttribute("detail_name", objectss[1]);
            model.addAttribute("ktp", objectss[2]);
            model.addAttribute("position", objectss[3]);
            model.addAttribute("birth_place", objectss[4]);
            model.addAttribute("birth_date", objectss[5]);
            model.addAttribute("address", objectss[6]);
            model.addAttribute("company", objectss[7]);
            model.addAttribute("Passport_number", objectss[8]);
            model.addAttribute("description", objectss[9]);
            model.addAttribute("name", objectss[10]);
            model.addAttribute("idTipe", objectss[11]);
            model.addAttribute("join_date", objectss[12]);
            DocDetails.add(objectssss);
        }

        model.addAttribute("idRequest", id);

        model.addAttribute("DocumentAll", DocDetails);

        Integer ApproveBy;
        String email;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());
        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            String comparison = "ROLE_MSHR_ADMIN";

            if (comparison.equals(jSONObject.getString("Role"))) {
                ApproveBy = jSONObject.getInt("Nik");
                email = jSONObject.getString("Email");
//                System.out.println(ApproveBy);
                model.addAttribute("nikApproveBy", ApproveBy);
                model.addAttribute("emailAdm", email);
            } else {

            }

        }

        return "requester/documentrequest/reSubmitMSHR";
    }

//Resubmit
    @PostMapping(value = "/resubmitDocumentRequest")
    public String resubmitDocReq(
            @RequestParam("no_ktp") String ktp,
            @RequestParam("gender") Integer gender,
            @RequestParam("empl_nik") Integer nik,
            @RequestParam("name") String nama,
            @RequestParam("passport") String passport,
            @RequestParam("position") String position,
            @RequestParam("customer_name") String company,
            @RequestParam("placeofbirth") String birthPlace,
            @RequestParam("tmpTgl") String birthDate,
            @RequestParam("address") String address,
            @RequestParam("type") Integer type,
            @RequestParam("join_date") String joinDate,
            @RequestParam("nikApproveBy") Integer approveBy,
            @RequestParam("idRequest") String idRequest,
            @RequestParam("emailAdm") String emailAdm,
            @RequestParam("notes") String description, Model model, String id)
            throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", id);
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            model.addAttribute("nik", id);
        }

        //Save Date
        String sDate8 = joinDate;
        String sDate9 = birthDate;
        SimpleDateFormat formatter6 = new SimpleDateFormat("dd-MMMM-yyyy");
        Date date8 = formatter6.parse(sDate8);
        Date date9 = formatter6.parse(sDate9);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Boolean isDelete = false;

        Integer REQID = DSImplement.Countrowbynik(nik);
        int IDRequester = REQID + 1;

        String Req = "REQ-" + nik + IDRequester;

        int emp = type;
        TbMForm Tbmform;
        Tbmform = new TbMForm(emp);

        Integer statusss = 3;
        TbMStatus status;
        status = new TbMStatus(statusss);

        Integer statusss2 = 15;
        TbMStatus sstatus;
        sstatus = new TbMStatus(statusss2);

        String emp2 = Req;
        TbTrEmployeeForm ReqID;
        ReqID = new TbTrEmployeeForm(emp2);

        String emp23 = idRequest;
        TbTrEmployeeForm idRequester;
        idRequester = new TbTrEmployeeForm(emp23);

        String remark = "REQ " + nik;

        String email = emailAdm;
        String name = nama;
        String Enik = String.valueOf(nik);
        String typee = String.valueOf(DSImplement.FormTypeById(emp).getName());

        try {
            emailService.sendNotificationSuratKeterangan(email, name, Enik, typee);
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        model.addAttribute("DocumentList", DSImplement.FormTypeById(emp).getId());

        DSImplement.save(new TbTrEmployeeForm(Req, statusss, description, nama, nik, timeAdd6Hours(), isDelete, Tbmform, approveBy));
        DSImplement.save(new TbTrEmployeeFormDetails(ktp, nik, nama, gender, passport, position, company, birthPlace, date9, address, timeAdd6Hours(), date8, ReqID));
        DSImplement.save(new TbTrApprovalDocumentRequest(nik, timeAdd6Hours(), ReqID, status, remark));
        
        DSImplement.updateDataEmpForm(15, idRequest);
        DSImplement.updateDataResubmit(15, idRequest);

        return "redirect:/viewDocumentReqEmployee/" + Req;
    }

//        DISPLAY History-------------------------------------------------------------------------------------------------------------
    @GetMapping(value = "/historyDocumentRequest")
    public String ViewHistory(String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", id);
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            model.addAttribute("nik", id);
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        List<Object[]> DocHistory = new ArrayList<>();
        for (Object[] object : DSImplement.allDetailss(id)) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            DocHistory.add(objects);
        }

        model.addAttribute("DocumentHistory", DocHistory);
        return "requester/documentrequest/historyDocumentRequest";
    }

//    DELETE------------------------------------------------------------------------------------------------------------
    @PostMapping(value = "/disabledhistory/{id}")
    public String deletebyid(@PathVariable("id") String id)
            throws ParseException {

        DSImplement.deletedhistory(id);
        return "redirect:/historyDocumentRequest";
    }

//    DISPLAY Details-------------------------------------------------------------------------------------------------------------
    @GetMapping("viewDocumentReqEmployee/{id}")
    public String ViewDocReq(@PathVariable("id") String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        List<Object[]> DocDetails = new ArrayList<>();
        for (Object[] object : DSImplement.alldoc(id)) {
            Object[] objects = new Object[12];
            model.addAttribute("nik", object[0]);
            model.addAttribute("detail_name", object[1]);
            model.addAttribute("ktp", object[2]);
            model.addAttribute("position", object[3]);
            model.addAttribute("birth_place", object[4]);
            model.addAttribute("birth_date", object[5]);
            model.addAttribute("address", object[6]);
            model.addAttribute("company", object[7]);
            model.addAttribute("Passport_number", object[8]);
            model.addAttribute("description", object[9]);
            model.addAttribute("name", object[10]);
            model.addAttribute("idTipe", object[11]);
            model.addAttribute("join_date", object[12]);
            DocDetails.add(objects);
        }

        model.addAttribute("DocumentAll", DocDetails);
        return "requester/documentrequest/viewDocReqHistoryEmployee";
    }

    //======================================================== Requester RM ================================================================
    @GetMapping(value = "/FormRequester")
    public String GetDocRmRequester(String id, Model model) throws ParseException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", id);
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            model.addAttribute("nik", id);
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        List<JSONObject> listData = serviceAPI.listAutoComplete(id); // Get data berdasarkan NIK
        List<String> listEmployee = new ArrayList<>();
        String userloginname = "";

        int nikkkkki = Integer.parseInt(id);

        for (JSONObject jSONObj : listData) {
            if (jSONObj.getInt("empl_nik") == nikkkkki) {
                userloginname = jSONObj.getString("name");
            }
        }
        model.addAttribute("userlog", userloginname);

        List<JSONObject> rmList = serviceAPI.listAdminData(); // List all data admin
        List<String> listRm = new ArrayList<>();
        for (JSONObject jSONObject : rmList) {
            listRm.add(jSONObject.getInt("Nik") + (" - ") + jSONObject.getString("Name"));
        }

        model.addAttribute("DocumentTP", DSImplement.findAll());
//        model.addAttribute("Userlogin", serviceAPI.EmployeeByNik(id).getname());
        model.addAttribute("adminName", listRm);
        model.addAttribute("id", id);

        return "requester/documentrequest/docRMRequester/FormReqRM";
    }

    //AutoComplete
    @RequestMapping(value = "/employeeAutoComplete")
    @ResponseBody
    public List<Map<String, String>> getdata(@RequestParam(value = "term", required = false, defaultValue = "") String term, Model model) {
        List<Map<String, String>> customerlist = new ArrayList<>();

        List<JSONObject> acList = serviceAPI.listAutoComplete(term);
        for (JSONObject jSONObject : acList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("label", jSONObject.getInt("empl_nik") + (" - ") + jSONObject.getString("name"));
            map.put("value", jSONObject.getInt("empl_nik") + (" - ") + jSONObject.getString("name"));
            try {
                map.put("customerName", jSONObject.getString("customer_name"));
                map.put("Nik", ("") + jSONObject.getInt("empl_nik"));
                map.put("Posisi", jSONObject.getString("position"));
                map.put("noktp", jSONObject.getString("no_ktp"));
                map.put("name", jSONObject.getString("name"));
                customerlist.add(map);
            } catch (Exception e) {
                e.getMessage();
            }
        }
        return customerlist;
    }

    //Mulai Save Requester
    @PostMapping("/SubmitEmployee")
    public String saveRMData(
            @RequestParam(name = "Nik") List<String> Nik,
            @RequestParam(name = "name") List<String> name,
            @RequestParam(name = "customerName") List<String> customerName,
            @RequestParam(name = "Posisi") List<String> Posisi,
            @RequestParam("employeenik") Integer enik,
            @RequestParam("adminName") String adminName,
            @RequestParam("type") Integer type,
            @RequestParam("startDate") String startddate,
            @RequestParam("EndDate") String EndDate,
            @RequestParam("description") String description,
            @RequestParam("Userlogin") String createdby,
            @RequestParam(name = "ktp") List<String> ktp, Model model, String id) throws ParseException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", id);
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            model.addAttribute("nik", id);
        }

        System.out.println("Tanggal " + startddate);

        //Pisah Nik admin untuk Approve_By
        String pisah = adminName;
        String[] nikadmin = pisah.split(" ", 0);
        String niksadm = nikadmin[0];

        //Convert Nik admin to int
        String number = niksadm;
        int hasilnikadmin = Integer.parseInt(number);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //Split Date dan Time, String -> ID Requester
//        String converted = timestamp.toString();
//        String[] parts = converted.split(" ");
//        String part1 = parts[0];
//        String part2 = parts[1];
//        String startPisah = startddate;
//        String[] seperate = startPisah.split("-");
//        String pisah1 = seperate[0];
//        String pisah2 = seperate[1];
//        String pisah3 = seperate[2];
//
//        String s = pisah3;
//        int n = Integer.parseInt(s);
//        int tanggal = n + 1;
//
//        String tglStartDate = pisah1 + "-" + pisah2 + "-" + tanggal;
//        String endpisah = EndDate;
//        String[] berpisah = endpisah.split("-");
//        String splot1 = berpisah[0];
//        String splot2 = berpisah[1];
//        String splot3 = berpisah[2];
//
//        String l = splot3;
//        int m = Integer.parseInt(l);
//        int tanggalEnd = m + 1;
//
//        String tglendDate = splot1 + "-" + splot2 + "-" + tanggalEnd;
        // String -> Date   
        String sDate = startddate;
        String eDate = EndDate;
        SimpleDateFormat formatter6 = new SimpleDateFormat("dd-MM-yyyy");
        Date date6 = formatter6.parse(sDate);
        Date date7 = formatter6.parse(eDate);

        //Reformat Date
//        String[] dateParts = part1.split("-");
//        String datePart1 = dateParts[0];
//        String datePart2 = dateParts[1];
//        String datePart3 = dateParts[2];
//
//        String formed = datePart3 + datePart2 + datePart1;
        //ID REQUESTER
        Integer REQID = DSImplement.Countrowbynik(enik);
        int IDRequester = REQID + 1;

        String Req = "REQ-" + enik + IDRequester;  //Formed Ganti sama Enik

        int emp = type;
        TbMForm Tbmform;
        Tbmform = new TbMForm(emp);

        Integer stats = 3;
        TbMStatus status;
        status = new TbMStatus(stats);

        int ApproveBy = hasilnikadmin;

        String emp2 = Req;
        TbTrEmployeeForm ReqID;
        ReqID = new TbTrEmployeeForm(emp2);

        DSImplement.save(new TbTrEmployeeForm(Req, stats, description, date6, date7, ApproveBy, createdby, enik, timeAdd6Hours(), Tbmform));

        for (int i = 0; i < Nik.size(); i++) {

            String noktp = ktp.get(i);
            String nameinput = name.get(i);
            String kustomerName = customerName.get(i);
            int Niktable = Integer.parseInt(Nik.get(i));
            String position = Posisi.get(i);

            TbTrEmployeeFormDetails tbmdetails = new TbTrEmployeeFormDetails(noktp, Niktable, nameinput, position, kustomerName, ReqID);
            DSImplement.save(tbmdetails);
        }

        DSImplement.save(new TbTrApprovalDocumentRequest(enik, timeAdd6Hours(), ReqID, status));
        model.addAttribute("DocumentList", DSImplement.FormTypeById(emp).getId());

        List<JSONObject> listData = serviceAPI.listAutoComplete(number); // Get data berdasarkan NIK
        for (JSONObject jSONObject : listData) {

            String requesterName = createdby;

            String emailAdmin = jSONObject.getString("email");
            String nameRequester = requesterName;
            String typee = String.valueOf(DSImplement.FormTypeById(emp).getName());

            try {
                emailService.sendNotificationSuratPenugasan(emailAdmin, nameRequester, typee);
            } catch (Exception e) {
                System.out.println("Error " + e);
            }
        }

        return "redirect:/viewDocumentReqRM/" + Req;
    }

    //History Requester RM
    @GetMapping(value = "/historyRM")
    public String historyRM(String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", id);
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
            model.addAttribute("nik", id);
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        List<Object[]> DocHistoryRM = new ArrayList<>();
        for (Object[] object : DSImplement.historyRM(id)) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            DocHistoryRM.add(objects);

        }

//        System.out.println("Tes :" + DocHistoryRM);
        model.addAttribute("DocumentHistoryRM", DocHistoryRM);

        return "requester/documentrequest/docRMRequester/historyDocumentRequestRM";
    }

    //Disable History RM
    @PostMapping(value = "/disabledhistoryrm/{id}")
    public String disableRmById(@PathVariable("id") String id)
            throws ParseException {

        DSImplement.deletedhistory(id);
        return "redirect:/historyRM";
    }

    //View Details Requester RM
    @GetMapping("viewDocumentReqRM/{id}")
    public String ViewDocReRM(@PathVariable("id") String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        List<Object[]> DocReqRmDetails = new ArrayList<>();
        for (Object[] object : DSImplement.viewDetailRequesterRm(id)) {
            Object[] objects = new Object[10];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            model.addAttribute("description", object[5]);
            model.addAttribute("type", object[7]);
            model.addAttribute("startDate", object[8]);
            model.addAttribute("endDate", object[9]);

            String admName = object[6].toString();
            int nikAdmin = Integer.parseInt(admName);

            List<JSONObject> listData = serviceAPI.listAutoComplete(Integer.toString(nikAdmin)); // Get data berdasarkan NIK
            List<String> listEmployee = new ArrayList<>();
            for (JSONObject jSONObject : listData) {
                listEmployee.add(jSONObject.getString("name"));
            }
            model.addAttribute("data_employee", listEmployee);
            DocReqRmDetails.add(objects);
        }

        model.addAttribute("DetailsViewRm", DocReqRmDetails);
        return "requester/documentrequest/docRMRequester/viewFormReqRM";
    }

    //Download File
    //Download Word Penugasan---------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/download/{id}/{tipe}", method = RequestMethod.GET)
    @ResponseBody
    public String suratMSHRDownload(@PathVariable("id") String id, @PathVariable("tipe") String tipe, HttpServletResponse response, Model model) throws ParseException, IOException {

        List<Object[]> DocDetails = new ArrayList<>();
        for (Object[] object : DSImplement.alldoc(id)) {
            Object[] objects = new Object[12];
            model.addAttribute("nik", object[0]);
            model.addAttribute("detail_name", object[1]);
            model.addAttribute("ktp", object[2]);
            model.addAttribute("position", object[3]);
            model.addAttribute("birth_place", object[4]);
            model.addAttribute("birth_date", object[5]);
            model.addAttribute("address", object[6]);
            model.addAttribute("company", object[7]);
            model.addAttribute("Passport_number", object[8]);
            model.addAttribute("description", object[9]);
            model.addAttribute("name", object[10]);
            model.addAttribute("idTipe", object[11]);
            model.addAttribute("join_date", object[12]);
            DocDetails.add(objects);

            String nik = object[0].toString();
            String namaReq = object[1].toString();
            String tipeSurat = object[10].toString();

            model.addAttribute("DocumentAll", DocDetails);

            try {
                byte[] fileBytes = DSImplement.findBlobFile(id);
                String filename = nik + "_" + namaReq + "_" + tipeSurat + ".pdf";

                response.setHeader("Content-Disposition", "attachment; filename=\"" + filename);
                response.setHeader("cache-control", "no-cache");
                response.setHeader("cache-control", "must-revalidate");

                ServletOutputStream outs = response.getOutputStream();
                outs.write(fileBytes);
                outs.flush();
                outs.close();
            } catch (Exception e) {
                System.out.println("Error " + e);
            }
        }

//        FileOutputStream out = new FileOutputStream(id);
//        out.close();
//
//        try {
//            response.setContentType("application/vnd.ms-docx");
//            response.setHeader("Content-Disposition", "attachment; filename=FileJadi.docx");
//            response.setHeader("Content-Transfer-Encoding", "binary");
//            BufferedOutputStream fileBytes = new BufferedOutputStream(response.getOutputStream());
//            FileInputStream fis = new FileInputStream(id);
//            int len;
//            byte[] buf = new byte[1024];
//            while ((len = fis.read(buf)) > 0) {
//                fileBytes.write(buf, 0, len);
//            }
//            fileBytes.close();
//            response.flushBuffer();
//
//            System.out.println("DocumentRequest.docx written successfully on disk.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "redirect:/historyDocumentRequest";
    }

    @RequestMapping(value = "surat/download/{id}/{tipe}", method = RequestMethod.GET)
    @ResponseBody
    public String suratPenugasanDownload(@PathVariable("id") String id, @PathVariable("tipe") String tipe, HttpServletResponse response) throws ParseException, IOException {

        try {
            byte[] fileBytes = DSImplement.findBlobFile(id);
            String filename = "SURAT " + tipe + ".pdf";

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

//        FileOutputStream out = new FileOutputStream(id);
//        out.close();
//
//        try {
//            response.setContentType("application/vnd.ms-docx");
//            response.setHeader("Content-Disposition", "attachment; filename=FileJadi.docx");
//            response.setHeader("Content-Transfer-Encoding", "binary");
//            BufferedOutputStream fileBytes = new BufferedOutputStream(response.getOutputStream());
//            FileInputStream fis = new FileInputStream(id);
//            int len;
//            byte[] buf = new byte[1024];
//            while ((len = fis.read(buf)) > 0) {
//                fileBytes.write(buf, 0, len);
//            }
//            fileBytes.close();
//            response.flushBuffer();
//
//            System.out.println("DocumentRequest.docx written successfully on disk.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "redirect:/historyRM";
    }
    
    public Date timeAdd6Hours() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 7);
        System.out.println("Penambahan" + calendar.getTime());
        return calendar.getTime();
    }

}
