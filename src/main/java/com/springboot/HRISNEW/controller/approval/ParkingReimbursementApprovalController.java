/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.approval;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import static com.springboot.HRISNEW.controller.approval.OvertimeApprovalController.userId;
import com.springboot.HRISNEW.controller.exportExcel.ExportDocAlex;
import com.springboot.HRISNEW.controller.exportExcel.ExportExcel;
import static com.springboot.HRISNEW.controller.requester.ParkingReimbursementController.myId;
import com.springboot.HRISNEW.entities.TbMParkingLocation;
import com.springboot.HRISNEW.entities.TbMStatus;
import com.springboot.HRISNEW.entities.TbTrApprovalReimburse;
import com.springboot.HRISNEW.entities.TbTrReimburse;
import com.springboot.HRISNEW.entities.TbTrReimburseDetail;
import com.springboot.HRISNEW.implementservices.ParkingDetailServiceImp;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
 * @author alexa
 */
@Controller
public class ParkingReimbursementApprovalController {

    @Autowired
    private ParkingDetailServiceImp pdsi;
    @Autowired
    private ServiceApi serviceApi;

    @GetMapping("/parking/summary")
    public String parkirSummary() {
        return "approval/parkingreimbursement/parkingreimbursementSummary";
    }

    @GetMapping("/parking/addloc")
    public String parkingAddLocation(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(myId);
        if (jobjUser != null) {
            model.addAttribute("roleUser", jobjUser.getInt("Role_Id"));
        }
        model.addAttribute("dataParking", null);
        return "approval/parkingreimbursement/ParkingAddLocation";
    }
    // history alll transport and request by customer

    @GetMapping("/parking/historyapproval")
    public String parkingREIMHistory(Model model) throws ParseException {
        List<Object[]> listRequest = new ArrayList<>();
        List<Object[]> listDetailRequest = new ArrayList<>();

        int jumlahCar = pdsi.findEmployeeTransport("Car");
        int jumlahMotor = pdsi.findEmployeeTransport("Motorcyle");
        model.addAttribute("jumlahCar", jumlahCar);
        model.addAttribute("jumlahMotor", jumlahMotor);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(myId);
        if (jobjUser != null) {
            model.addAttribute("roleUser", jobjUser.getInt("Role_Id"));
            if (jobjUser.getInt("Role_Id") == 10) {
                for (Object[] object : pdsi.findListParkingReimbursementDetaiMSFCByNik(jobjUser.getInt("User_Id"))) {
                    int len_data = object.length;
                    Object[] objects = object;

                    objects[1] = getBulanTahun(object[1].toString());
                    objects[2] = getBulanTahun(object[2].toString());
                    objects[3] = thousandSeparator(object[3]);

                    String nik_requester = object[12].toString();
                    JSONObject joEmpl_req = serviceApi.findEmpbyid(nik_requester);
                    if (joEmpl_req != null) {
                        objects[len_data - 1] = joEmpl_req.get("Name");
                    } else {
                        JSONObject jobjUserREQ = serviceApi.userbyid(Integer.parseInt(nik_requester));
                        objects[len_data - 1] = jobjUserREQ.getString("Name");
                    }
                    listRequest.add(objects);
                }
            } else {
                for (Object[] object : pdsi.findListParkingReimbursementDetailApprovalByNik(jobjUser.getInt("User_Id"))) {
                    int len_data = object.length;
                    Object[] objects = object;

                    objects[1] = getBulanTahun(object[1].toString());
                    objects[2] = getBulanTahun(object[2].toString());
                    objects[3] = thousandSeparator(object[3]);

                    String nik_requester = object[12].toString();
                    JSONObject joEmpl_req = serviceApi.findEmpbyid(nik_requester);
                    if (joEmpl_req != null) {
                        objects[len_data - 1] = joEmpl_req.get("Name");
                    } else {
                        JSONObject jobjUserREQ = serviceApi.userbyid(Integer.parseInt(nik_requester));
                        objects[len_data - 1] = jobjUserREQ.getString("Name");
                    }
                    listRequest.add(objects);
                }
            }
            model.addAttribute("listReimbursement", listRequest);
        }
        return "approval/parkingreimbursement/ParkingReimbursementHistory";
    }
//parkingReimbursementApprovalDetail

    @GetMapping("/parking/pending")
    public String parkingREIMPending(Model model) throws ParseException {
        List<Object[]> listRequest = new ArrayList<>();

        int jumlahCar = pdsi.findEmployeeTransport("Car");
        int jumlahMotor = pdsi.findEmployeeTransport("Motorcyle");
        model.addAttribute("jumlahCar", jumlahCar);
        model.addAttribute("jumlahMotor", jumlahMotor);

//        status 6 = waiting for rm
        int status_id = 6;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();

        JSONObject jobjUser = serviceApi.findUserByEmail(myId);
        System.out.println("USER = " + jobjUser);
        if (jobjUser != null) {
            model.addAttribute("roleUser", jobjUser.getInt("Role_Id"));
            System.out.println("USER = " + jobjUser.getInt("Role_Id"));
            if (jobjUser.getInt("Role_Id") == 10) {
                for (Object[] object : pdsi.findListParkingReimbursementDetailMFSCByNikAndStatus(jobjUser.getInt("User_Id"), 7)) {
                    int len_data = object.length;
                    Object[] objects = object;
                    String nik_requester = object[12].toString();

                    objects[1] = getBulanTahun(object[1].toString());
                    objects[2] = getBulanTahun(object[2].toString());
                    objects[3] = thousandSeparator(object[3]);

                    JSONObject joEmpl_req = serviceApi.findEmpbyid(nik_requester);
                    if (joEmpl_req != null) {
                        objects[len_data - 1] = joEmpl_req.get("Name");
                    } else {
                        JSONObject jobjUserREQ = serviceApi.userbyid(Integer.parseInt(nik_requester));
                        objects[len_data - 1] = jobjUserREQ.getString("Name");
                    }
                    listRequest.add(objects);
                }
            } else {
                for (Object[] object : pdsi.findListParkingReimbursementDetailApprovalByNikAndStatus(jobjUser.getInt("User_Id"), status_id)) {
                    int len_data = object.length;
                    Object[] objects = object;
                    String nik_requester = object[12].toString();

                    objects[1] = getBulanTahun(object[1].toString());
                    objects[2] = getBulanTahun(object[2].toString());
                    objects[3] = thousandSeparator(object[3]);

                    JSONObject joEmpl_req = serviceApi.findEmpbyid(nik_requester);
                    if (joEmpl_req != null) {
                        objects[len_data - 1] = joEmpl_req.get("Name");
                    } else {
                        JSONObject jobjUserREQ = serviceApi.userbyid(Integer.parseInt(nik_requester));
                        objects[len_data - 1] = jobjUserREQ.getString("Name");
                    }
                    listRequest.add(objects);
                }
            }

            model.addAttribute("listReimbursement", listRequest);
        }

        return "approval/parkingreimbursement/ParkingReimbursementPending";
    }
// Approval detail ticket form

    public String getBulanTahun(String date) throws ParseException {
        SimpleDateFormat StringToDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateGet = StringToDate.parse(date);
        DateFormat dateFormat = new SimpleDateFormat("MMMM-yyyy");
        return dateFormat.format(dateGet);
    }
    //create date
    public String getCreateDate(String date) throws ParseException {
        SimpleDateFormat StringToDate = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date dateGet = StringToDate.parse(date);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:mm");
        return dateFormat.format(dateGet);
    }

    public String thousandSeparator(Object rate) {
        return String.format("Rp. %,d ,-", rate);
    }

    public String ConvertByteToImage(byte[] file) {
        String image = Base64.getEncoder().encodeToString(file);
        return image;
    }

    public String CheckingIsApproval(String url) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
        System.out.println("EMP = " + joEmpl);
        if (joEmpl != null) {
            return "redirect:/loginPage?unauth";
        } else {
            return url;
        }
    }

    @RequestMapping(value = "/parking/pending/r")
    @ResponseBody
    public Boolean parkingCheck(
            @RequestParam(value = "id", defaultValue = "") String REIM_ID,
            @RequestParam(value = "reason", defaultValue = "") String reason,
            @RequestParam(value = "nik", defaultValue = "") String nik,
            @RequestParam(value = "reqid", defaultValue = "") String REQID,
            @RequestParam(value = "status", defaultValue = "") String status_id
    ) {
        System.out.println("DataR: " + REIM_ID);
        System.out.println("DataR: " + reason);
        System.out.println("DataR: " + REQID);
        System.out.println("DataR: " + status_id);
        System.out.println("DataR: " + nik);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceApi.findUserByEmail(myId);
        try {
            String status_id_Approval = status_id;
            System.out.println("Data NIK: " + joUser.getInt("User_Id"));
            if (Integer.parseInt(status_id) == 8) {
                if (joUser.getInt("Role_Id") == 10) {
                    status_id_Approval = status_id;
                    reason = "Approved by MSFC";
                } else {
                    status_id_Approval = "7";
                    reason = "Approved by RM";
                }
            } else if (Integer.parseInt(status_id) == 9) {
                if (joUser.getInt("Role_Id") == 10) {
                    status_id_Approval = "9";
                    reason = "Rejected by MSFC, note: " + reason;
                } else {
                    status_id_Approval = "9";
                    reason = "Rejected by RM, note: " + reason;
                }
            } else {
                status_id_Approval = status_id;
            }
            Timestamp createdDate = new Timestamp(System.currentTimeMillis());
            pdsi.updateREIMdetail_status(reason, Integer.parseInt(status_id_Approval), Integer.parseInt(REIM_ID), createdDate);
            TbMStatus status = new TbMStatus(Integer.parseInt(status_id_Approval));
            TbTrReimburse reim = new TbTrReimburse(REQID);
            pdsi.saveApprovalREIM(new TbTrApprovalReimburse(Integer.parseInt(REIM_ID), Integer.parseInt(nik), createdDate, reim, status));
//                 return "redirect:/parking/pending/{reqid}";
            return true;
        } catch (Exception e) {
            System.out.println("Err: " + e);
            return false;
        }
    }

    @PostMapping("/parking/pending/checkpage")
    public String checkParking(
            @RequestParam(name = "IDREQ") String IDREQ
    ) {
        System.out.println("Get " + IDREQ);
        return "redirect:/parking/pending/" + IDREQ;
    }

    @GetMapping("/parking/pending/{reqid}")
    public String resubmitTicket(Model model, @PathVariable(value = "reqid") String REQID) throws ParseException {
        int nik_requester = 0;
        String nama_requester;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joUser = serviceApi.findUserByEmail(myId);

//        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
//        System.out.println("joeml: "+joEmpl);
//        System.out.println("jouml: "+joUser);
//        if (joEmpl != null) {
//            List<JSONObject> listData = serviceApi.listGetUserInformation(Integer.toString(joEmpl.getInt("Employee_Nik"))); // List all data admin
//            for (JSONObject jSONObject : listData) {
//                model.addAttribute("customer_requester", jSONObject.getString("customer_name"));
//                model.addAttribute("so_id", jSONObject.getString("soid"));
//                List<JSONObject> listRM = serviceApi.listGetUserInformation(Integer.toString(jSONObject.getInt("nikrm"))); // List all data admin
//                for (JSONObject RMData : listRM) {
//                    try {
//                        model.addAttribute("RMName", RMData.getString("name"));
//                        model.addAttribute("NIKRM", RMData.getInt("empl_nik"));
//                    } catch (Exception e) {
//                        model.addAttribute("RMName", null);
//                        model.addAttribute("NIKRM", null);
//                    }
//                }
//            }
//            nama_requester = joEmpl.getString("Name");
//            nik_requester = joEmpl.getInt("Employee_Nik");
//            model.addAttribute("nik_requester", nik_requester);
//            model.addAttribute("nama_requester", nama_requester);
        Object[] listRequest = new Object[13];
        int CounterData = 0;
//            //list transport
//            List<Object[]> listTransport = new ArrayList<>();
//            for (Object[] object : pdsi.findTransportByNik(nik_requester)) {
//                Object[] objects = new Object[5];
//                objects[0] = object[0];
//                objects[1] = object[1];
//                objects[2] = object[2];
//                objects[3] = object[3];
//                objects[4] = object[4];
//                listTransport.add(objects);
//
//            }
//            model.addAttribute("listTransportation", listTransport);
        //data main
        for (Object[] object : pdsi.findListParkingReimbursementRequestByREQID(REQID)) {
            Object[] objects = object;
            JSONObject joEmpl = serviceApi.GetUserInformation(object[1].toString());

            String sDate = object[3].toString();
            String eDate = object[4].toString();
            objects[3] = getBulanTahun(sDate);
            objects[4] = getBulanTahun(eDate);
            objects[6] = joEmpl.getString("name");
//            System.out.println("DATA END: "+object[4].toString());
//            System.out.println("DATA RM: "+object[5].toString());
            JSONObject user = serviceApi.userbyid(Integer.parseInt(object[5].toString()));
            if (user != null) {
                model.addAttribute("RMName", user.getString("Name"));
                model.addAttribute("NIKRM", user.getInt("User_Id"));
            }
//            List<JSONObject> listRM = serviceApi.getRMById(object[5].toString()); // List all data admin
//            for (JSONObject RMData : listRM) {
//                try {
//                    model.addAttribute("RMName", RMData.getString("Name"));
//                } catch (Exception e) {
//
//                }
//            }
            listRequest = object;
        }
        model.addAttribute("dataREIM", listRequest);
        model.addAttribute("roleUser", joUser.getInt("Role_Id"));
        //data detail,
        List<String[]> listDetailRequest = new ArrayList<>();
        for (Object[] object : pdsi.findListParkingReimbursementDetailRequestByREQID(REQID)) {
            CounterData++;
            int status_derail = Integer.parseInt(object[9].toString());
            String[] data = new String[9];
            data[0] = object[0].toString();
            data[2] = object[2].toString();
            data[3] = object[3].toString();
            data[4] = object[4].toString();
            data[5] = object[5].toString();
            data[6] = object[6].toString();
            data[7] = object[7].toString();
            data[8] = object[8].toString();
            if (joUser.getInt("Role_Id") == 10) {
                // Hello Endriko
                if (status_derail == 7) {
                    byte[] image = pdsi.getReimById(Integer.parseInt(data[0]));
                    data[1] = ConvertByteToImage(image);
                    listDetailRequest.add(data);
                }
            } else {
                if (status_derail == 6) {
                    byte[] image = pdsi.getReimById(Integer.parseInt(data[0]));
                    data[1] = ConvertByteToImage(image);
                    listDetailRequest.add(data);
                }
            }

        }
        if (listDetailRequest.size() == 0) {
            return "redirect:/parking/historyapproval";
        }
        model.addAttribute("detailREIM", listDetailRequest);
        model.addAttribute("counterDetail", CounterData);

//        }
        return "approval/parkingreimbursement/parkingReimbursementApprovalDetail";
    }

    @GetMapping("/parking/loc/e/{id}")
    public String updateTransport(
            @PathVariable(value = "id") int id,
            Model model
    ) throws IOException {
        System.out.println("update:" + id);
        List<Object[]> listTransport = new ArrayList<>();
        Object[] objects = new Object[6];
        for (Object[] object : pdsi.findLocationParkingById(id)) {
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            listTransport.add(objects);
            System.out.println("data:" + object[2]);
        }
        model.addAttribute("dataParking", objects);
        return "approval/parkingreimbursement/ParkingAddLocation";
    }

    @GetMapping("/parking/loc/d/{id}")
    public String deleteTransport(
            @PathVariable(value = "id") int id,
            Model model
    ) throws IOException {
        pdsi.deleteParking(id);
        return "redirect:/parking/listLocation";
    }

    @GetMapping("/parking/listLocation")
    public String parkingListLocation(Model model
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(myId);
        if (jobjUser != null) {
            model.addAttribute("roleUser", jobjUser.getInt("Role_Id"));
        }

        List<Object[]> listParking = new ArrayList<>();
        for (Object[] object : pdsi.findListParkingLocation()) {
            Object[] objects = new Object[6];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            objects[5] = object[5];
            listParking.add(objects);
        }
        model.addAttribute("listParking", listParking);
        return "approval/parkingreimbursement/ParkingLocationList";
    }

    @PostMapping("/parking/s/loc")
    public String parkingSaveLocation(
            @RequestParam("parkid") int parkid,
            @RequestParam("parkloc") String park_location,
            @RequestParam("typeClaim") String type_claim,
            @RequestParam("parkown") String park_owner,
            @RequestParam("contactPerson") String contact_person,
            @RequestParam("parkaddress") String park_address
    ) {
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        try {
            System.out.println("Test val2 " + parkid);
            if (parkid == 0) {
                TbMParkingLocation tb_parkingLoc = new TbMParkingLocation(
                        park_location, park_address, park_owner, type_claim, contact_person, false, createdDate, createdDate
                );
                pdsi.saveParkingLocation(tb_parkingLoc);
            } else {
                System.out.println("Test 211212 " + parkid);
                TbMParkingLocation tb_parkingLoc = new TbMParkingLocation(parkid, park_location, park_address, park_owner, type_claim, contact_person, false, createdDate, createdDate);
                pdsi.saveParkingLocation(tb_parkingLoc);
            }
        } catch (Exception e) {
            System.out.println("Error" + e);
        }
        return "redirect:/parking/listLocation";
    }

    @RequestMapping(value = "/parking/summary/download/{periode}", method = RequestMethod.GET)
    @ResponseBody
    public String parkirSummaryDownload(HttpServletResponse response, @PathVariable(value = "periode") String periode
    ) throws ParseException, IOException {
        List<Object[]> listRequest = new ArrayList<>();
        int nik_requester = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();

        JSONObject jobjUser = serviceApi.findUserByEmail(myId);
        if (jobjUser != null) {

            if (jobjUser.getInt("Role_Id") == 10) {
                int nik_approval = jobjUser.getInt("User_Id");
                for (Object[] object : pdsi.findListDetailByMSFCForExcel(nik_approval, periode)) {
                    Object[] objects = object;
                    JSONObject json_userName = serviceApi.GetUserInformation(object[1].toString());
                    objects[2] = json_userName.getString("name");
                    objects[3] = getBulanTahun(object[3].toString());
                    objects[7] = thousandSeparator(object[7]);
                    objects[11] = object[11].toString();
                    //colum createdate - request status
                    objects[13] = getCreateDate(object[13].toString());
                    objects[14] = getCreateDate(object[14].toString());
                    objects[15] = object[15].toString() + " - " + object[16].toString();
                    objects[16] = object[17].toString();
                    objects[17] = object[18].toString();
                    objects[18] = object[19].toString();
                    listRequest.add(objects);
                }
                int lenData = listRequest.size();
                ExportExcel export = new ExportExcel();
                
                export.exportExcel3(response, listRequest, Integer.toString(nik_approval), lenData);
            } else {
                int nik_approval = jobjUser.getInt("User_Id");
                for (Object[] object : pdsi.findListDetailByApprovalForExcel(nik_approval, periode)) {
                    Object[] objects = object;
                    JSONObject json_userName = serviceApi.GetUserInformation(object[1].toString());
                    objects[2] = json_userName.getString("name");
                    objects[3] = getBulanTahun(object[3].toString());
                    objects[7] = thousandSeparator(object[7]);
                    objects[11] = object[11].toString();
                    listRequest.add(objects);
                }
                int lenData = listRequest.size();
                ExportExcel export = new ExportExcel();
                
                export.exportExcel4(response, listRequest, Integer.toString(nik_approval), lenData);
            }

        }

//        return new FileSystemResource(new File("howtodoinjava_demo.xlsx"));
        return "approval/parkingreimbursement/parkingreimbursementSummary";
    }

    @RequestMapping(value = "/parking/ParkLocCheck")
    @ResponseBody
    public Boolean parkingCheck(@RequestParam(value = "loc", required = false, defaultValue = "") String parking_location
    ) {
        System.out.println("parking_location: " + parking_location);
        List<Object[]> objects = pdsi.findParkLocByName(parking_location);
        if (objects.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @RequestMapping(value = "/parking/summary/download/req/{periode}", method = RequestMethod.GET)
    @ResponseBody
    public String parkirSummaryREQDownload(HttpServletResponse response, @PathVariable(value = "periode") String periode
    ) throws ParseException, IOException {
        List<Object[]> listRequest = new ArrayList<>();
        int nik_requester = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
        if (joEmpl != null) {
            nik_requester = joEmpl.getInt("Employee_Nik");
            System.out.println("nik: " + nik_requester);
            for (Object[] object : pdsi.findListDetailByNIKForExcel(nik_requester, periode)) {
                Object[] objects = object;
                objects[2] = joEmpl.getString("Name");
                objects[3] = getBulanTahun(object[3].toString());
                objects[7] = thousandSeparator(object[7]);
                objects[11] = object[11].toString();
                listRequest.add(objects);
            }
            int lenData = listRequest.size();
            ExportExcel export = new ExportExcel();
            export.exportExcel4(response, listRequest, Integer.toString(nik_requester), lenData);
        } else {
            JSONObject jobjUser = serviceApi.findUserByEmail(myId);
            nik_requester = jobjUser.getInt("User_Id");
            System.out.println("nik: " + nik_requester);
            for (Object[] object : pdsi.findListDetailByNIKForExcel(nik_requester, periode)) {
                Object[] objects = object;
                objects[2] = jobjUser.getString("Name");
                objects[3] = getBulanTahun(object[3].toString());
                objects[7] = thousandSeparator(object[7]);
                objects[11] = object[11].toString();
                listRequest.add(objects);
            }
            int lenData = listRequest.size();
            ExportExcel export = new ExportExcel();
            System.out.println("Data LEN: " + lenData);
            export.exportExcel4(response, listRequest, Integer.toString(nik_requester), lenData);
        }

//        return new FileSystemResource(new File("howtodoinjava_demo.xlsx"));
        return "approval/parkingreimbursement/parkingreimbursementSummary";
    }

//    @RequestMapping(value = "/down", method = RequestMethod.GET)
//    @ResponseBody
//    public String DocDownload(HttpServletResponse response
//    ) throws ParseException, IOException, InvalidFormatException {
//            ExportDocAlex export = new ExportDocAlex();
//            export.exportDOC1();
//        
//
////        return new FileSystemResource(new File("howtodoinjava_demo.xlsx"));
//        return this.CheckingIsApproval("approval/parkingreimbursement/parkingreimbursementSummary");
//    }
    @RequestMapping(value = "/parking/pending/ticket/download/{ID}/{REQID}", method = RequestMethod.GET)
    @ResponseBody
    public String downloadFileTicket(
            @PathVariable("ID") String id, HttpServletResponse response,
            @PathVariable("REQID") String req_id
    ) throws ParseException, IOException {

        try {
            byte[] fileBytes = pdsi.getReimById(Integer.parseInt(id));
            String filename = req_id + "_" + id + ".pdf";
            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.setHeader("cache-control", "no-cache");
            response.setHeader("cache-control", "must-revalidate");

            ServletOutputStream outs = response.getOutputStream();
            outs.write(fileBytes);
            outs.flush();
            outs.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        return "redirect:/historyRM";
    }
}
