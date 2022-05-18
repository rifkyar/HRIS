/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.requester;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.entities.Approval;
import com.springboot.HRISNEW.entities.LeaveType;
import com.springboot.HRISNEW.entities.RequestDetail;
import com.springboot.HRISNEW.entities.RequesterInformation;
import com.springboot.HRISNEW.implementservices.ApprovalServiceImpl;
import com.springboot.HRISNEW.implementservices.LeaveDetailServiceImpl;
import com.springboot.HRISNEW.implementservices.LeaveTypeServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestDetailServiceImp;
import com.springboot.HRISNEW.implementservices.RequesterInformServiceImpl;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
 * @author HARRY-PC
 */
@Controller
public class LeaveRequestController {

    @Autowired
    private ServiceApi serviceApi;

    @Autowired
    private RequestDetailServiceImp rdsi;

    @Autowired
    private RequesterInformServiceImpl risi;

    @Autowired
    private LeaveDetailServiceImpl ldsi;

    @Autowired
    private ApprovalServiceImpl asi;

    @Autowired
    private EmailService emailService;

    private static Logger log = LoggerFactory.getLogger(LeaveRequestController.class);

    @Autowired
    LeaveTypeServiceImpl ltsi;

    public static String userId;

    @GetMapping("/requestLeave/{id}")
    public String requestLeave(@PathVariable(value = "id") Integer id, Model model, HttpSession session) throws ParseException {
        System.out.println("Running Leave Requester");
        String sifting = null;
        //Start Part Requester Information
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        JSONObject joUser = serviceApi.findUserByEmail(userId);
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(userId);

        if (joUser != null) {
            model.addAttribute("nik", joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
            infoRequester(id, "", model);
        }

        JSONObject firstContracts = serviceApi.firstContract(id);
        LocalDate todays = LocalDate.now();
        long canUsed = ChronoUnit.MONTHS.between(LocalDate.parse(
                firstContracts.getString("Start_Contract_Date")).withDayOfMonth(1),
                todays.withDayOfMonth(1)) + 1;
        for (RequestDetail requestDetail
                : rdsi.usedRequest(risi.findRequesterbyid(id).getSourceNik())) {
            int leaveDay = requestDetail.getLeaveDays();
            canUsed -= leaveDay;
        }
        model.addAttribute("eligible", canUsed);
        //End Part Requester Information

        //Start Part Form
        String responder, soId, gender, mrgStatus, noHp, thisRm = null;
        int maxId = rdsi.jumlahId(id) + 1, idRm = 0, ada = 0;
//        System.out.println("max ID : " + maxId);
        List publicHoliday = new ArrayList();
        List holidayPublic = new ArrayList();
        JSONObject all, so, rm, emp;

        all = serviceApi.findAllocationbyNik(Integer.toString(id)); //Get data all Allocation berdasarkan NIK Karyawan
        soId = all.getString("So_Id"); //Get data ID Sales Order di data allocation
        so = serviceApi.findSobyId(soId); //Get data all Sales Order berdasarkan ID SO
        responder = so.getString("RM_Name"); //Get data Nama RM di data Sales Order
        if (responder.equalsIgnoreCase("No_Relation_Manager")) {
            responder = so.getString("Act_Relation_Manager");
        } else if (responder == null) {
            responder = so.getString("Act_Relation_Manager");
        }
        rm = serviceApi.userbyid(Integer.parseInt(responder)); //Get all data RM berdasarkan nama RM
        emp = serviceApi.findEmpbyid(Integer.toString(id)); //Get all data Master Employee berdasarkan NIK
        gender = emp.getString("Gender"); //Get data gender di data Master Emlpoyee
        mrgStatus = emp.getString("Marriage Status"); //Get data Marriage Status di data Master Employee

        if (rm != null) { //RM
            thisRm = rm.getString("Name");
            idRm = rm.getInt("User_Id");
//            System.out.println(thisRm + " - " + idRm);
            ada = 1;
        } else {
            thisRm = "N/A";
            idRm = 0;
        }
        List<JSONObject> rmList = serviceApi.listRm(); // List all data RM
        List<String> listRm = new ArrayList<>();
        for (JSONObject jSONObject : rmList) {
            listRm.add(jSONObject.getInt("User_Id") + "-" + jSONObject.getString("Name"));
        }

        if (!emp.getString("Hp").equals("null")) { //Handphone Number
            noHp = emp.getString("Hp");
        } else {
            noHp = "N/A";
        }
        
        if (serviceApi.findIncludeJoinById(soId).getBoolean("includejointholiday") == true) { //Holiday
            publicHoliday = serviceApi.allHoliday();
        } else {
            publicHoliday = serviceApi.publicHolidaydate();
        }
        for (Object object : publicHoliday) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            holidayPublic.add(format.format(format.parse((String) object)));
        }

        Date dateNow = new Date();
        long diffday = 0;
        RequestDetail lastReq = rdsi.lastRequestEmp(id);
        if (lastReq != null) {
            System.out.println("LAST REQUEST : " + lastReq.getId());
            diffday = Math.abs(((lastReq.getSubmittedDate()).getTime() - dateNow.getTime()) / 86400000);
            System.out.println("Diffrent Days = " + diffday + " Day");
            if (diffday >= 7 && lastReq.getIsdelete() == false) {
                session.setAttribute("permit", 0);
            } else {
                session.setAttribute("permit", 1);
            }
        } else {
            session.setAttribute("permit", 0);
        }
        //sifting
        String sft = all.getString("shifting");
        if (sft.equalsIgnoreCase("false")) {
            sifting = "Non Shifting";
        } else {
            sifting = "Shifting";
        }

        LocalDate now = LocalDate.now();

        model.addAttribute("idLeave", "LEAVE-" + id + now.getYear() + maxId + "A");
        model.addAttribute("soId", soId);
        model.addAttribute("adaRM", ada);
        model.addAttribute("rmName", idRm + "-" + thisRm);
        model.addAttribute("listRm", listRm);
        model.addAttribute("leaveType", ldsi.findLeaveType(Integer.parseInt(gender), Integer.parseInt(mrgStatus)));
        model.addAttribute("noHp", noHp);
        model.addAttribute("publicHoliday", holidayPublic);
        model.addAttribute("reqInfo", risi.findRequesterbyid(id));
        model.addAttribute("sifting", sifting);
        System.out.println("sifting" +sifting);
        //End Part Form

        return "/requester/leave/requesterLeave";
    }

    public void infoRequester(Integer id, String idLeave, Model model) {

        int lastYear = 0;
        String department, position;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        JSONObject alloc = serviceApi.findAllocationbyNik(Integer.toString(id));
        JSONObject so = serviceApi.findSobyId(alloc.getString("So_Id"));
        JSONObject findUser = serviceApi.findUserByEmail(userId);
        JSONObject findEmp = serviceApi.findEmployeeByEmail(userId);
        JSONObject findDept = serviceApi.findDeptbyId(so.getString("Department_Id"));
        RequesterInformation reqInfo = risi.findRequesterbyid(id);

        if (findUser != null) { // Approval Information
            System.out.println("Name : " + id + " - " + findUser.getString("Name"));
            System.out.println("SO ID : " + alloc.getString("So_Id"));
            System.out.println("COMPANY : " + so.getString("Customer_Id") + " - " + so.getString("customername"));
            if (!so.getString("Department_Id").equals("0")) {
                System.out.println("DEPARTMENT : " + findDept.getString("Name"));
            } else {
                System.out.println("DEPARTMENT : " + "N/A");
            }
        } else if (findEmp != null) { // Requester Information
//            System.out.println("SO ID : " + alloc.getString("So_Id"));
            model.addAttribute("id", id);
            model.addAttribute("name", findEmp.getString("Name"));
            model.addAttribute("customerId", so.getString("Customer_Id"));
            model.addAttribute("customer", so.getString("customername"));
            if (!so.getString("Department_Id").equals("0")) {
                department = findDept.getString("Name");
            } else {
                department = "N/A";
            }
            model.addAttribute("department", department);
            if (!findEmp.getString("Position").equals("null")) {
                position = findEmp.getString("Position");
            } else {
                position = "N/A";
            }
            model.addAttribute("position", position);
            if (reqInfo.getAvailableLeaveLastyear() == null) {
                lastYear = 0;
            } else {
                lastYear = reqInfo.getAvailableLeaveLastyear();
            }
            model.addAttribute("lastYear", lastYear);
            model.addAttribute("expireDate", format.format(reqInfo.getExpireddateLeaveLastyear()));
            model.addAttribute("currentYear", reqInfo.getAvailableLeaveCurrentyear());
            model.addAttribute("currentExp", format.format(reqInfo.getExpireddateLeaveCurrentyear()));
            model.addAttribute("totalLeave", reqInfo.getTotalavailableleave());
        }

    }

    @Transactional
    @PostMapping("/saveLeaveRequest")
    public String saveLeave(@RequestParam(value = "idLeave", required = false) String leaveId,
            @RequestParam(value = "nik", required = false) String nik,
            @RequestParam(value = "listRm", required = false) String nameRm,
            @RequestParam(value = "soId", required = false) String soId,
            @RequestParam(value = "grupType", required = false) String typeLeave,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "noHp", required = false) String noHp,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam(value = "leaveDayValue", required = false) String leaveDays,
            @RequestParam(value = "holidayValue", required = false) String holiday,
            @RequestParam(value = "leavegiven", required = false) String leaveGiven,
            @RequestParam(value = "reduceLast", required = false) String reduceLast,
            @RequestParam(value = "reduceCurrent", required = false) String reduceCurrent,
            @RequestParam(value = "cboxBackup", required = false) String backup,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException, ParseException {
        boolean backUp = false;
        if (backup != null) {
            backUp = true;
        }

        byte[] byteFile = file.getBytes();
        String nameFile = file.getOriginalFilename();
        int nikRM = Integer.parseInt(nameRm.split("\\-")[0]);

        Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
        Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);

        RequestDetail requestDetail = new RequestDetail(leaveId, fromDate, toDate, notes,
                Integer.parseInt(leaveDays), Integer.parseInt(holiday), Integer.parseInt(leaveGiven),
                Integer.parseInt(reduceLast), Integer.parseInt(reduceCurrent), timeAdd6Hours(),
                "NotYet", new LeaveType(Integer.parseInt(typeLeave.split("\\-")[0])),
                new RequesterInformation(Integer.parseInt(nik)), backUp, nikRM,
                soId, false, nikRM, false, noHp, byteFile, nameFile);
        rdsi.save(requestDetail);

        RequesterInformation reqInfo = risi.findRequesterbyid(Integer.parseInt(nik));
        int availCurrent = reqInfo.getAvailableLeaveCurrentyear();
        int availLast = reqInfo.getAvailableLeaveLastyear();
        int total = reqInfo.getTotalavailableleave();
        reqInfo.setAvailableLeaveCurrentyear(availCurrent - Integer.parseInt(reduceCurrent));
        reqInfo.setAvailableLeaveLastyear(availLast - Integer.parseInt(reduceLast));
        reqInfo.setTotalavailableleave(total - (Integer.parseInt(reduceCurrent) + Integer.parseInt(reduceLast)));
        risi.save(reqInfo);

        Approval approval = new Approval(requestDetail.getId().toString(), 0, "NotYet", timeAdd6Hours(), "N/A");
        asi.save(approval);

        String email = serviceApi.userbyid(requestDetail.getDirectReport()).getString("Email");
        System.out.println("email" + email);
        sendMail(email, serviceApi.userbyid(requestDetail.getDirectReport()).getString("Name"), reqInfo.getName(), fromDate, toDate, notes, Integer.parseInt(nik));

        System.out.println("SUCCESS");

        return "redirect:/historyLeave/" + nik;
    }

    public Date timeAdd6Hours() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 7);
        System.out.println("Penambahan" + calendar.getTime());
        return calendar.getTime();
    }

    @GetMapping("/historyLeave/{id}")
    public String historyLeave(@PathVariable(value = "id") int nik, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        JSONObject joUser = serviceApi.findUserByEmail(userId);
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(userId);
        if (joUser != null) {
            model.addAttribute("nik", joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            List<Object[]> listHistoryLeave = new ArrayList<>();
            for (Object[] object : asi.findListLeaveRequest(nik)) {
                Object[] objects = new Object[10];
                objects[0] = object[0];
                objects[1] = ltsi.leaveTypeById((Integer) object[1]).getType();
                objects[2] = format.format(object[2]);
                objects[3] = format.format(object[3]);
                objects[4] = object[4];
                objects[5] = format1.format(object[5]);
                objects[6] = object[6];
                objects[7] = object[7];
                objects[8] = object[9];
                listHistoryLeave.add(objects);
            }
            model.addAttribute("listRequestLeave", listHistoryLeave);
        }
        return "/requester/leave/historyLeave";
    }

    @GetMapping("/viewHistory/{id}")
    public String viewHistory(@PathVariable(value = "id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        JSONObject joUser = serviceApi.findUserByEmail(userId);
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(userId);
        if (joUser != null) {
            model.addAttribute("nik", joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            String gender, mrgStatus;
            gender = joEmpl.getString("Gender");
            mrgStatus = joEmpl.getString("Marriage Status");

            List<JSONObject> rmList = serviceApi.listRm(); // List all data RM
            List<String> listRm = new ArrayList<>();
            for (JSONObject jSONObject : rmList) {
                listRm.add(jSONObject.getInt("User_Id") + "-" + jSONObject.getString("Name"));
            }

            RequestDetail rd = rdsi.requestEmpid(id);
            Object[] objects = new Object[2];
            objects[0] = "" + rd.getLeaveDetailId().getId() + "-" + rd.getLeaveDetailId().getLeaveGiven();
            objects[1] = "" + rd.getDirectReport();

            List<Object[]> listHistoryApp = new ArrayList<>();
            for (Object[] object : asi.listApprovalHistoryByIdReq(id.substring(0, id.length() - 1))) {
                JSONObject RM = serviceApi.userbyid(asi.findAppByid((String) object[1]).getUserId());
                Object[] appHistory = new Object[7];
                appHistory[0] = object[1];
                appHistory[1] = RM.getString("Name");
                appHistory[2] = object[4];
                appHistory[3] = object[5];
                appHistory[4] = object[6];
                listHistoryApp.add(appHistory);
            }

            infoRequester(joEmpl.getInt("Employee_Nik"), id, model);

            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
            model.addAttribute("idLeave", id);
            model.addAttribute("listRm", listRm);
            model.addAttribute("leaveType", ldsi.findLeaveType(Integer.parseInt(gender), Integer.parseInt(mrgStatus)));
            model.addAttribute("listRD", objects);
            model.addAttribute("rd", rd);
            model.addAttribute("historyApprov", listHistoryApp);
        }
        return "/requester/leave/viewLeaveRequest";
    }

    @RequestMapping(value = "/canceled", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String cancelReq(HttpServletRequest response) {
        String result = "SUCCEC CANCEL", idLeave;
        int current, last, total;
        idLeave = response.getParameter("id");

        RequestDetail rd = rdsi.requestEmpid(idLeave);

        if(rd.getRequestStatus().equalsIgnoreCase("NotYet")){
            RequesterInformation ri = risi.findRequesterbyid(rd.getNik().getNik());
            current = ri.getAvailableLeaveCurrentyear();
            last = ri.getAvailableLeaveLastyear();
            total = ri.getTotalavailableleave();
            ri.setAvailableLeaveCurrentyear(current + rd.getReduceCurrent());
            ri.setAvailableLeaveLastyear(last + rd.getReduceLast());
            ri.setTotalavailableleave(total + (rd.getReduceLast() + rd.getReduceCurrent()));
            risi.save(ri);
        }

        rd.setIsdelete(true);
        rdsi.save(rd);

        Approval ap = new Approval(rd.getId().toString(), 0, "Canceled", timeAdd6Hours(), "N/A");
        asi.save(ap);

        return result;
    }

    @RequestMapping(value = "/resubmit/{idLeave}", method = RequestMethod.GET)
    public String resubmitReq(@PathVariable(value = "idLeave") String idLeave, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        JSONObject joEmpl = serviceApi.findEmployeeByEmail(userId);
        if (joEmpl != null) {
            infoRequester(joEmpl.getInt("Employee_Nik"), idLeave, model);

            JSONObject firstContracts = serviceApi.firstContract(joEmpl.getInt("Employee_Nik"));
            JSONObject all = serviceApi.findAllocationbyNik(Integer.toString(joEmpl.getInt("Employee_Nik"))); //Get data all Allocation berdasarkan NIK Karyawan
            LocalDate todays = LocalDate.now();
            long canUsed = ChronoUnit.MONTHS.between(LocalDate.parse(
                    firstContracts.getString("Start_Contract_Date")).withDayOfMonth(1),
                    todays.withDayOfMonth(1)) + 1;
            for (RequestDetail requestDetail
                    : rdsi.usedRequest(risi.findRequesterbyid(joEmpl.getInt("Employee_Nik")).getSourceNik())) {
                int leaveDay = requestDetail.getLeaveDays();
                canUsed -= leaveDay;
            }

            RequestDetail rd = rdsi.requestEmpid(idLeave);
            String gender, mrgStatus, soId;
            gender = joEmpl.getString("Gender");
            mrgStatus = joEmpl.getString("Marriage Status");
            soId = all.getString("So_Id");

            char index = rd.getId().charAt(rd.getId().length() - 1);
            char variable = (char) ((int) index + 1);
            String idLeaveNew = rd.getId().substring(0, rd.getId().length() - 1) + "" + variable;

            List<JSONObject> rmList = serviceApi.listRm(); // List all data RM
            List<String> listRm = new ArrayList<>();
            for (JSONObject jSONObject : rmList) {
                listRm.add(jSONObject.getInt("User_Id") + "-" + jSONObject.getString("Name"));
            }

            Object[] objects = new Object[2];
            objects[0] = "" + rd.getLeaveDetailId().getId() + "-" + rd.getLeaveDetailId().getLeaveGiven();
            objects[1] = "" + rd.getDirectReport();

            List<Object[]> listHistoryApp = new ArrayList<>();
            for (Object[] object : asi.listApprovalHistoryByIdReq(idLeave.substring(0, idLeave.length() - 1))) {
                JSONObject RM = serviceApi.userbyid(asi.findAppByid((String) object[1]).getUserId());
                Object[] appHistory = new Object[7];
                appHistory[0] = object[1];
                appHistory[1] = RM.getString("Name");
                appHistory[2] = object[4];
                appHistory[3] = object[5];
                appHistory[4] = object[6];
                listHistoryApp.add(appHistory);
            }

            List publicHoliday = new ArrayList();
            List holidayPublic = new ArrayList();

            if (serviceApi.findIncludeJoinById(soId).getBoolean("includejointholiday") == true) { //Holiday
                publicHoliday = serviceApi.allHoliday();
//                System.out.println("All Holiday" + publicHoliday);
            } else {
                publicHoliday = serviceApi.publicHolidaydate();
//                System.out.println("Not Include" + publicHoliday);
            }
            for (Object object : publicHoliday) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                holidayPublic.add(format.format(format.parse((String) object)));
            }
//            System.out.println("Holiday : " + holidayPublic);
            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
            model.addAttribute("eligible", canUsed);
            model.addAttribute("idLeaveOld", idLeave);
            model.addAttribute("idLeaveNew", idLeaveNew);
            model.addAttribute("listRm", listRm);
            model.addAttribute("listRD", objects);
            model.addAttribute("leaveType", ldsi.findLeaveType(Integer.parseInt(gender), Integer.parseInt(mrgStatus)));
            model.addAttribute("rd", rd);
            model.addAttribute("historyApprov", listHistoryApp);
            model.addAttribute("publicHoliday", holidayPublic);
            model.addAttribute("reqInfo", risi.findRequesterbyid(joEmpl.getInt("Employee_Nik")));
            model.addAttribute("soId", soId);
        }
        return "/requester/leave/resubmitLeave";
    }

    @Transactional
    @PostMapping("/updateLeaveRequest")
    public String updateLeave(@RequestParam(value = "idLeave", required = false) String leaveId,
            @RequestParam(value = "idLeaveOld", required = false) String idLeaveOld,
            @RequestParam(value = "nik", required = false) String nik,
            @RequestParam(value = "listRm", required = false) String nameRm,
            @RequestParam(value = "soId", required = false) String soId,
            @RequestParam(value = "grupType", required = false) String typeLeave,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "noHp", required = false) String noHp,
            @RequestParam(value = "notes", required = false) String notes,
            @RequestParam(value = "leaveDayValue", required = false) String leaveDays,
            @RequestParam(value = "holidayValue", required = false) String holiday,
            @RequestParam(value = "leavegiven", required = false) String leaveGiven,
            @RequestParam(value = "reduceLast", required = false) String reduceLast,
            @RequestParam(value = "reduceCurrent", required = false) String reduceCurrent,
            @RequestParam(value = "cboxBackup", required = false) String backup,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException, ParseException {
        boolean backUp = false;
        if (backup != null) {
            backUp = true;
        }

        byte[] byteFile = file.getBytes();
        String nameFile = file.getOriginalFilename();
        int nikRM = Integer.parseInt(nameRm.split("\\-")[0]);

        Date fromDate = new SimpleDateFormat("dd-MM-yyyy").parse(startDate);
        Date toDate = new SimpleDateFormat("dd-MM-yyyy").parse(endDate);

        RequestDetail rd = rdsi.requestEmpid(idLeaveOld);
        rd.setRequestStatus("Resubmit");
        rd.setSubmittedDate(timeAdd6Hours());
        rdsi.updateReq(rd);

        RequestDetail requestDetail = new RequestDetail(leaveId, fromDate, toDate, notes,
                Integer.parseInt(leaveDays), Integer.parseInt(holiday), Integer.parseInt(leaveGiven),
                Integer.parseInt(reduceLast), Integer.parseInt(reduceCurrent), timeAdd6Hours(),
                "NotYet", new LeaveType(Integer.parseInt(typeLeave.substring(0, 1))),
                new RequesterInformation(Integer.parseInt(nik)), backUp, nikRM,
                soId, false, nikRM, false, noHp, byteFile, nameFile);
        rdsi.save(requestDetail);

        RequesterInformation reqInfo = risi.findRequesterbyid(Integer.parseInt(nik));
        int availCurrent = reqInfo.getAvailableLeaveCurrentyear();
        int availLast = reqInfo.getAvailableLeaveLastyear();
        int total = reqInfo.getTotalavailableleave();
        reqInfo.setAvailableLeaveCurrentyear(availCurrent - Integer.parseInt(reduceCurrent));
        reqInfo.setAvailableLeaveLastyear(availLast - Integer.parseInt(reduceLast));
        reqInfo.setTotalavailableleave(total - (Integer.parseInt(reduceCurrent) + Integer.parseInt(reduceLast)));
        risi.save(reqInfo);

        Approval approvalOld = new Approval(idLeaveOld, 0, "Resubmit", timeAdd6Hours(), "N/A");
        asi.save(approvalOld);
        Approval approval = new Approval(requestDetail.getId().toString(), 0, "NotYet", timeAdd6Hours(), "N/A");
        asi.save(approval);
        sendMail(serviceApi.userbyid(requestDetail.getDirectReport()).getString("Email"), serviceApi.userbyid(requestDetail.getDirectReport()).getString("Name"), reqInfo.getName(), fromDate, toDate, notes, rd.getNik().getNik());
        System.out.println("SUCCESS");

        return "redirect:/historyLeave/" + nik;
    }

    public String sendMail(String mail, String name, String target, Date from, Date to, String note, int nik) {
        try {
            emailService.sendNotificationService(mail, name, target, from, to, note, nik);
        } catch (Exception e) {
            log.info("Error sending email = " + e.getMessage());
        }
        return "Email Sent";
    }
}
