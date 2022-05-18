/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.controller.exportToDoc.ExportDoc;
import com.springboot.HRISNEW.controller.exportToDoc.ExportDocPenugasan;
import com.springboot.HRISNEW.controller.exportToDoc.ExportDocVisa;
import com.springboot.HRISNEW.implementservices.DocumentServiceImpl;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Dharta
 */
@Controller
public class DocumentReqAdmController {

    @Autowired
    private DocumentServiceImpl DSImplement;

    @Autowired
    private ServiceApi serviceAPI;

    @Autowired
    private EmailService emailService;

    public static String myId;

    public static String fo;

    @GetMapping("DocumentApproveAdm")
    public String DocumentReqAdm(String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", joUser.getInt("User_Id"));
            model.addAttribute("email", joUser.getString("Email"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        List<Object[]> DocApproval = new ArrayList<>();
        for (Object[] object : DSImplement.pendingApprovalAdm(id)) {
            Object[] objects = new Object[9];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            objects[6] = object[6];
            objects[7] = object[7];
            objects[8] = object[8];

            model.addAttribute("nikApproveBy", object[8].toString());

            DocApproval.add(objects);
        }

        model.addAttribute("nikLogin", id);
        model.addAttribute("DocumentApproval", DocApproval);

        int nikLogin = joUser.getInt("User_Id");
        model.addAttribute("nikLogin", nikLogin);

        String role;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            int nikAdmin = jSONObject.getInt("Nik");

            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);

            }

        }
        return "approval/documentrequest/documentReqAdm";
    }

    //    DISPLAY Details-------------------------------------------------------------------------------------------------------------
    @GetMapping("viewDocumentRequestAdm/{id}")
    public String ViewDocumentReqAdm(@PathVariable("id") String id, String Nik, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            Nik = String.valueOf(joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            Nik = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }
        List<Object[]> viewDocDetails = new ArrayList<>();
        for (Object[] object : DSImplement.viewPendingApproval(id)) {
            Object[] objects = new Object[16];
            model.addAttribute("name", object[13]);
            model.addAttribute("nikReq", object[1]);
            model.addAttribute("detail_names", object[2]);
            model.addAttribute("ktp", object[3]);
            model.addAttribute("position", object[4]);
            model.addAttribute("birth_place", object[5]);
            model.addAttribute("birth_date", object[6]);
            model.addAttribute("address", object[7]);
            model.addAttribute("company", object[8]);
            model.addAttribute("Passport_number", object[9]);
            model.addAttribute("description", object[11]);
            model.addAttribute("join_date", object[10]);
            model.addAttribute("descadmin", object[12]);
            model.addAttribute("createdBy", object[14]);

            model.addAttribute("nikApproveBy", object[15].toString());

            viewDocDetails.add(objects);
        }

        model.addAttribute("nikLogin", Nik);

        model.addAttribute("DocumentViewAdmin", viewDocDetails);

        int nikLogin = joUser.getInt("User_Id");
        model.addAttribute("nikLogin", nikLogin);

        String role;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            int nikAdmin = jSONObject.getInt("Nik");

            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);

            }

        }
        model.addAttribute("nik", joUser.getInt("User_Id"));
        model.addAttribute("email", joUser.getString("Email"));

        return "approval/documentrequest/viewDocumentRequest";
    }

    //Upload File Approval Requester 
    @RequestMapping(value = "/uploadFileMSHR", method = RequestMethod.POST)
    public String uploadFileRequesterMSHR(
            @RequestParam("fileUpload") MultipartFile filedocument,
            @RequestParam("reqID") String Req,
            @RequestParam("nameRequester") String nameReq,
            String id,
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

        List<JSONObject> listData = serviceAPI.listAutoComplete(id); // Get data berdasarkan NIK
        List<JSONObject> listEmp = serviceAPI.listAutoComplete(nameReq); // Get data berdasarkan NIK
        for (JSONObject jSONObject : listData) {
            for (JSONObject jSONObj : listEmp) {

                String admName = jSONObject.getString("name");

                String email = jSONObj.getString("email");
                String name = nameReq; //Created_By
                String adminName = admName;

                try {
                    emailService.notifikasiMSHRDone(email, name, adminName);
                } catch (Exception e) {
                    System.out.println("Error " + e);
                }
            }
        }

        Timestamp modDate = new Timestamp(System.currentTimeMillis());
        byte[] bytefile = filedocument.getBytes();
        DSImplement.uploadFile(bytefile, timeAdd6Hours(), Req);
        DSImplement.doneStatusUpload(4, Req);
        return "redirect:/approvalHistoryMSHRAdm";
    }

    //Upload File Approval Requester RM
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(
            @RequestParam("myfile") MultipartFile filedocument,
            @RequestParam("reqID") String Req,
            String id,
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

        String createdBy = DSImplement.createdBy(Req);

        List<JSONObject> listData = serviceAPI.listAutoComplete(id); // Get data berdasarkan NIK
        List<JSONObject> listEmp = serviceAPI.listAutoComplete(createdBy); // Get data berdasarkan NIK
        for (JSONObject jSONObject : listData) {
            for (JSONObject jSONObj : listEmp) {

                String admName = jSONObject.getString("name");

                String email = jSONObj.getString("email");
                String name = createdBy; //Created_By
                String adminName = admName;

                try {
                    emailService.notifikasiPenugasanDone(email, name, adminName);
                } catch (Exception e) {
                    System.out.println("Error " + e);
                }
            }
        }

//        String nameFile = filedocument.getOriginalFilename();
        Timestamp modDate = new Timestamp(System.currentTimeMillis());
        byte[] bytefile = filedocument.getBytes();
        DSImplement.uploadFile(bytefile, timeAdd6Hours(), Req);
        DSImplement.doneStatusUpload(4, Req);
        return "redirect:/approvalHistoryPenAdm";
    }

    //    Update DB HRIS-------------------------------------------------------------------------------------------------------------
    @PostMapping(value = "/update")
    public String saveDocReq(
            @RequestParam("formnuumber") String formnuumber,
            @RequestParam("descadmin") String descadmin,
            @RequestParam("req") String req,
            Model model, String id)
            throws ParseException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Integer statss = 23;

        DSImplement.saveTbTrEmp(formnuumber, descadmin, statss, timeAdd6Hours(), req);
        DSImplement.saveTbTrAproval(statss, req);

        return "redirect:/DocumentApproveAdm";
    }

    //    Reject DB HRIS-------------------------------------------------------------------------------------------------------------
    @PostMapping(value = "/reject")
    public String Reject(
            @RequestParam("notes") String notes,
            @RequestParam("req") String req,
            @RequestParam("nik") String nikRequester,
            @RequestParam("nameRequester") String nameReq,
            String id, Model model)
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

        //List<JSONObject> listData = serviceAPI.listAutoComplete(id); // Get data berdasarkan NIK
        List<JSONObject> listEmp = serviceAPI.listAutoComplete(nikRequester);
        System.out.println("notes" + notes);
//        for (JSONObject jSONObj : listEmp) {
//            for (JSONObject jSONObject : listData) {
//                
//                String admName = jSONObject.getString("name");
//
//                String email = jSONObj.getString("email");
//                String name = nameReq; //Created_By
//                String adminName = admName;
//                String notesReject = notes;
//
//                try {
//                    emailService.notifikasiReject(email, name, adminName, notesReject);
//                } catch (Exception e) {
//                    System.out.println("Error " + e);
//                }
//            }
//        }
        for (JSONObject jSONObj : listEmp) {
                String admName = "Admin HR";

                String email = jSONObj.getString("email");
                String name = nameReq; //Created_By
                String adminName = admName;
                String notesReject = notes;

                try {
                    emailService.notifikasiReject(email, name, adminName, notesReject);
                } catch (Exception e) {
                    System.out.println("Error " + e);
                }
        }
        Integer statsss = 9;

        String formNumber = null;
        Timestamp modDate = new Timestamp(System.currentTimeMillis());

        DSImplement.rejectTbTrEmp(formNumber, notes, statsss, timeAdd6Hours(), req);
        DSImplement.saveTbTrAproval(statsss, req);

        return "redirect:/executiveSumMSHR";
    }

    //    ExecutiveSummary Adm MSHR DB HRIS-------------------------------------------------------------------------------------------------------------
    @GetMapping("executiveSumMSHR")
    public String executiveSummAdmMSHR(String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", joUser.getInt("User_Id"));
            model.addAttribute("email", joUser.getString("Email"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        List<Object[]> DocMSHR = new ArrayList<>();
        for (Object[] object : DSImplement.executiveSummMSHR(id)) {
            Object[] objects = new Object[10];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            objects[6] = object[6];
            objects[7] = object[7];
            objects[8] = object[8];
            objects[9] = object[9];

            model.addAttribute("nikApproveBy", object[9].toString());

            String Status = object[7].toString();

            if (objects[0] == null) {
                objects[0] = "-";
            }

            if (Status == "Rejected") {
                objects[6] = "-";
            };

            DocMSHR.add(objects);

        }
        model.addAttribute("nikLogin", id);
        model.addAttribute("DocumentMSHR", DocMSHR);

        int nikLogin = joUser.getInt("User_Id");
        model.addAttribute("nikLogin", nikLogin);

        String role;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            int nikAdmin = jSONObject.getInt("Nik");

            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);

            }

        }
        return "approval/documentrequest/documentReqHistoryAdm";
    }

    //=============================================ADMIN Requester RM====================================================
    @GetMapping(value = "/documentRequestAdminRm")
    public String documentRequestRmAdmin(String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", joUser.getInt("User_Id"));
            model.addAttribute("email", joUser.getString("Email"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        List<Object[]> DocApprovalRm = new ArrayList<>();
        for (Object[] object : DSImplement.allDocumentRequestRm(id)) {
            Object[] objects = new Object[10];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            objects[6] = object[6];
            objects[7] = object[7];
            objects[8] = object[8];
            objects[9] = object[9];

            model.addAttribute("nikApproveBy", object[9].toString());

            DocApprovalRm.add(objects);
        }
        model.addAttribute("nikLogin", id);
        model.addAttribute("documentApprovalRm", DocApprovalRm);

        int nikLogin = joUser.getInt("User_Id");
        model.addAttribute("nikLogin", nikLogin);

        String role;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            int nikAdmin = jSONObject.getInt("Nik");

            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);

            }

        }
        return "approval/documentrequest/documentrequestRmAdm/documentReqRmAdm";
    }

    //History Penugasan
    @GetMapping(value = "/executiveSumPenugasan")
    public String executiveSummaryRm(String id, Model model)
            throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", joUser.getInt("User_Id"));
            model.addAttribute("email", joUser.getString("Email"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        List<Object[]> exSumPenugasan = new ArrayList<>();
        for (Object[] object : DSImplement.executiveSumPenugasan(id)) {
            Object[] objects = new Object[9];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            objects[6] = object[6];
            objects[7] = object[7];
            objects[8] = object[8];

            model.addAttribute("nikApproveBy", object[8].toString());
            String status = object[6].toString();

            if (objects[3] == null) {
                objects[3] = "-";
            }

            if (status == "Rejected") {
                objects[5] = "-";
            };
            exSumPenugasan.add(objects);
        }
        model.addAttribute("nikLogin", id);
        model.addAttribute("exSummPenugasan", exSumPenugasan);

        int nikLogin = joUser.getInt("User_Id");
        model.addAttribute("nikLogin", nikLogin);

        String role;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            int nikAdmin = jSONObject.getInt("Nik");

            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);

            }

        }

        return "approval/documentrequest/documentrequestRmAdm/documentRequestHistoryRmAdm";
    }

    @GetMapping(value = "/viewDetailsDocumentRequestRmAdm/{id}")
    public String viewDetailsRm(@PathVariable("id") String id, String Nik, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            Nik = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", joUser.getInt("User_Id"));
            model.addAttribute("email", joUser.getString("Email"));
        } else if (joEmpl != null) {
            Nik = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        List<Object[]> viewDetailsRequesterRm = new ArrayList<>();
        for (Object[] object : DSImplement.viewDetailRequesterRm(id)) {
            Object[] objects = new Object[12];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            model.addAttribute("description", object[5]);
            model.addAttribute("endDate", object[9]);
            model.addAttribute("startDate", object[8]);
            model.addAttribute("type", object[7]);
            model.addAttribute("descadmin", object[10]);
            model.addAttribute("createdBy", object[11]);

            model.addAttribute("nikApproveBy", object[6].toString());

            viewDetailsRequesterRm.add(objects);
        }

        model.addAttribute("nikLogin", Nik);
        model.addAttribute("detailsRequesterRm", viewDetailsRequesterRm);

        int nikLogin = joUser.getInt("User_Id");
        model.addAttribute("nikLogin", nikLogin);

        String role;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            int nikAdmin = jSONObject.getInt("Nik");

            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);

            }

        }
        return "approval/documentrequest/documentrequestRmAdm/viewDocumentReqRmAdmDetails";
    }

    //    Update DB HRIS RM-------------------------------------------------------------------------------------------------------------
    @PostMapping(value = "/updateRm")
    public String updateDocReqRM(
            @RequestParam("formnuumber") String formnuumber,
            @RequestParam("descadmin") String descadmin,
            @RequestParam("req") String req,
            Model model,
             String id)
            throws ParseException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        Integer statss = 23;

        DSImplement.saveTbTrEmp(formnuumber, descadmin, statss, timeAdd6Hours(), req);
        DSImplement.saveTbTrAproval(statss, req);

        return "redirect:/documentRequestAdminRm";
    }

    //    Reject DB HRIS RM-------------------------------------------------------------------------------------------------------------
    @PostMapping(value = "/rejectRm")
    public String rejectRM(
            @RequestParam("notes") String notes,
            @RequestParam("req") String req,
            @RequestParam("nameRequester") String nameReq,
            String id,
             Model model)
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

        List<JSONObject> listData = serviceAPI.listAutoComplete(id);
        List<JSONObject> listEmp = serviceAPI.listAutoComplete(nameReq);// Get data berdasarkan NIK
        for (JSONObject jSONObj : listEmp) {
            for (JSONObject jSONObject : listData) {

                String admName = jSONObject.getString("name");

                String email = jSONObj.getString("email");;
                String name = nameReq; //Created_By
                String adminName = admName;
                String notesReject = notes;

                try {
                    emailService.notifikasiReject(email, name, adminName, notesReject);
                } catch (Exception e) {
                    System.out.println("Error " + e);
                }
            }
        }

        Integer statsss = 9;
        String formNumber = null;
        Timestamp modDate = new Timestamp(System.currentTimeMillis());

        DSImplement.rejectTbTrEmp(formNumber, notes, statsss, timeAdd6Hours(), req);
        DSImplement.saveTbTrAproval(statsss, req);

        return "redirect:/executiveSumPenugasan";
    }

    //Download Word MSHR---------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/surat/mshr/download/Others/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String suratMSHRDownload(@PathVariable("id") String id, HttpServletResponse response) throws ParseException, IOException {
        List<Object[]> listRequester = new ArrayList<>();
        for (Object[] object : DSImplement.dataMSHR(id)) {
            try {
                Object[] objects = new Object[14];
                objects[0] = object[0].toString();
                objects[1] = object[1].toString();
                objects[2] = object[2].toString();
                objects[3] = object[3].toString();
                objects[4] = object[4].toString();
                objects[5] = object[5].toString();
                objects[6] = object[6].toString();
                objects[7] = object[7].toString();
                objects[8] = object[8].toString();
                objects[9] = object[9].toString();
                objects[10] = object[10].toString();
                objects[11] = object[11].toString();
                objects[12] = object[12].toString();
                objects[13] = object[13].toString();

//                System.out.println("Hasil " + object[2].toString());
                listRequester.add(objects);
            } catch (Exception e) {
                System.out.println("Error " + e);
            }

        }

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        ExportDoc export = new ExportDoc();

        export.exportDoc(timeAdd6Hours(), listRequester, response);
        return "redirect:/DocumentApproveAdm";
    }

    //Download Visa MSHR---------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/surat/mshr/download/Visa/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String suratMSHRVisa(@PathVariable("id") String id, HttpServletResponse response) throws ParseException, IOException {
        List<Object[]> listRequester = new ArrayList<>();
        for (Object[] object : DSImplement.dataVisa(id)) {
            try {
                Object[] objects = new Object[7];
                objects[0] = object[0].toString();
                objects[1] = object[1].toString();
                objects[2] = object[2].toString();
                objects[3] = object[3].toString();
                objects[4] = object[4].toString();
                objects[5] = object[5].toString();
                objects[6] = object[6].toString();

                listRequester.add(objects);
            } catch (Exception e) {
                System.out.println("Error " + e);
            }

        }

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        ExportDocVisa export = new ExportDocVisa();

        export.exportDocVia(timeAdd6Hours(), listRequester, response);
        return "redirect:/DocumentApproveAdm";
    }

    //Download Word Penugasan---------------------------------------------------------------------------------------------------------------------
    @RequestMapping(value = "/surat/penugasan/download/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String suratPenugasanDownload(@PathVariable("id") String id, HttpServletResponse response) throws ParseException, IOException {
        List<Object[]> listRequester = new ArrayList<>();
        for (Object[] object : DSImplement.dataPenugasan(id)) {
            try {
                Object[] objects = new Object[6];
                objects[0] = object[0].toString();
                objects[1] = object[1].toString();
                objects[2] = object[2].toString();
                objects[3] = object[3].toString();
                objects[4] = object[4].toString();
                objects[5] = object[5].toString();

                listRequester.add(objects);
            } catch (Exception e) {
                System.out.println("Error " + e);
            }

        }

        List<Object[]> listKaryawanPenugasan = new ArrayList<>();
        for (Object[] object : DSImplement.dataKaryawanPenugasan(id)) {
            try {
                Object[] objects = new Object[5];
                objects[0] = object[0].toString();
                objects[1] = object[1].toString();
                objects[2] = object[2].toString();
                objects[3] = object[3].toString();
                objects[4] = serviceAPI.findEmpbyid(objects[0].toString()).getString("Hp");

                listKaryawanPenugasan.add(objects);
            } catch (Exception e) {
                System.out.println("Error " + e);
            }

        }

        Timestamp currentDate = new Timestamp(System.currentTimeMillis());
        ExportDocPenugasan export = new ExportDocPenugasan();

        export.exportDocPenugasan(timeAdd6Hours(), listRequester, listKaryawanPenugasan, response);
        return "redirect:/documentRequestAdminRm";
    }

    // Approval MSHR
    @GetMapping("approvalHistoryMSHRAdm")
    public String approvalHistoryHRAdm(String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", joUser.getInt("User_Id"));
            model.addAttribute("email", joUser.getString("Email"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        List<Object[]> DocApproval = new ArrayList<>();
        for (Object[] object : DSImplement.approvalHistoryAdm(id)) {
            Object[] objects = new Object[9];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            objects[6] = object[6];
            objects[7] = object[7];
            objects[8] = object[8];

            model.addAttribute("nikApproveBy", object[8].toString());
            DocApproval.add(objects);
        }

        model.addAttribute("nikLogin", id);
        model.addAttribute("DocumentApproval", DocApproval);

        int nikLogin = joUser.getInt("User_Id");
        model.addAttribute("nikLogin", nikLogin);

        String role;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            int nikAdmin = jSONObject.getInt("Nik");

            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);

            }

        }
        return "approval/documentrequest/approvalHistoryMSHR";
    }

    //Approval Penugasan
    @GetMapping(value = "/approvalHistoryPenAdm")
    public String approvalHistoryPenugasanAdm(String id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceAPI.findUserByEmail(myId);
        JSONObject joEmpl = serviceAPI.findEmployeeByEmail(myId);

        if (joUser != null) {
            id = String.valueOf(joUser.getInt("User_Id"));
            model.addAttribute("nik", joUser.getInt("User_Id"));
            model.addAttribute("email", joUser.getString("Email"));
        } else if (joEmpl != null) {
            id = String.valueOf(joEmpl.getInt("Employee_Nik"));
        }

        List<Object[]> approvalHistoryRm = new ArrayList<>();
        for (Object[] object : DSImplement.approvalHistoryPenugasanAdm(id)) {
            Object[] objects = new Object[8];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            objects[6] = object[6];
            objects[7] = object[7];

            model.addAttribute("nikApproveBy", object[7].toString());
            approvalHistoryRm.add(objects);
        }
        model.addAttribute("nikLogin", id);
        model.addAttribute("documentApprovalRm", approvalHistoryRm);

        int nikLogin = joUser.getInt("User_Id");
        model.addAttribute("nikLogin", nikLogin);

        String role;

        List<JSONObject> listData1 = serviceAPI.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceAPI.adminNik());

        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");

            int nikAdmin = jSONObject.getInt("Nik");

            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);

            }

        }
        return "approval/documentrequest/documentrequestRmAdm/approvalHistoryPenugasan";
    }

    //Validasi Form Number
    @RequestMapping(value = "/formNumber/check")
    @ResponseBody
    public Boolean formCheck(@RequestParam(value = "no", required = false, defaultValue = "") String formNumber
    ) {
        System.out.println("police" + formNumber);
        Boolean isAvailable = false;
        List<Object[]> objects = DSImplement.findEmployeeForm(formNumber);
        if (objects.isEmpty()) {
            return false;
        } else {
            return true;
        }
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
