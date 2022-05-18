/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.approval;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.entities.Approval;
import com.springboot.HRISNEW.entities.RequestDetail;
import com.springboot.HRISNEW.entities.RequesterInformation;
import com.springboot.HRISNEW.implementservices.ApprovalServiceImpl;
import com.springboot.HRISNEW.implementservices.LeaveTypeServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestDetailServiceImp;
import com.springboot.HRISNEW.implementservices.RequesterInformServiceImpl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author USER
 */
@Controller
public class LeaveApprovalController {

    @Autowired
    EmailService emailService;

    @Autowired
    ServiceApi serviceApi;

    @Autowired
    RequestDetailServiceImp rdsi;

    @Autowired
    RequesterInformServiceImpl risi;

    @Autowired
    ApprovalServiceImpl asi;

    @Autowired
    LeaveTypeServiceImpl ltsi;

    private static Logger log = LoggerFactory.getLogger(LeaveApprovalController.class);

    @GetMapping("/approvalRm/{email}")
    public String listLeave(@PathVariable(value = "email") String email, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (!email.equalsIgnoreCase(authentication.getName())) {
            return "redirect:/homeAdm";
        }else if (user != null) {

            int nikLogin = user.getInt("User_Id");
            //        System.out.println("Nik Login " + user.getInt("User_Id"));
            model.addAttribute("nikLogin", nikLogin);
            String role;
            List<JSONObject> listData1 = serviceApi.adminNik(); // Get data berdasarkan 
            //        System.out.println(serviceApi.adminNik());
            for (JSONObject jSONObject : listData1) {
                //            System.out.println("Ini Role " + jSONObject.getString("Role"));
                //            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");
                int nikAdmin = jSONObject.getInt("Nik");
                if (nikLogin == nikAdmin) {
                    role = jSONObject.getString("Role");
                    model.addAttribute("adminMSHR", role);
                }
            }

            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
            int nik = user.getInt("User_Id");
            model.addAttribute("listLeave", rdsi.findListLeaveRequest(nik, nik));
        }
        return "approval/leave/requesterLeaveAdm";
    }

    @GetMapping("/leaveAppAdm/{idLeave}")
    public String leaveReqAdm(@PathVariable(value = "idLeave") String idLeave, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (user != null) {
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
        }

        int nikLogin = user.getInt("User_Id");
//        System.out.println("Nik Login " + user.getInt("User_Id"));
        model.addAttribute("nikLogin", nikLogin);
        String role;
        List<JSONObject> listData1 = serviceApi.adminNik(); // Get data berdasarkan 
//        System.out.println(serviceApi.adminNik());
        for (JSONObject jSONObject : listData1) {
//            System.out.println("Ini Role " + jSONObject.getString("Role"));
//            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");
            int nikAdmin = jSONObject.getInt("Nik");
            if (nikLogin == nikAdmin) {
                role = jSONObject.getString("Role");
                model.addAttribute("adminMSHR", role);
            }
        }

        JSONObject alloc, so, findDept, findEmp;
        String noHp, department, position, remark = null;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        RequestDetail rd = rdsi.requestEmpid(idLeave);
        int nik = rd.getNik().getNik(), lastYear = 0;
        alloc = serviceApi.findAllocationbyNik(Integer.toString(nik));
        so = serviceApi.findSobyId(alloc.getString("So_Id"));
        findDept = serviceApi.findDeptbyId(so.getString("Department_Id"));
        findEmp = serviceApi.findEmpbyid(Integer.toString(nik));
        RequesterInformation reqInfo = risi.findRequesterbyid(nik);
        noHp = rd.getPhoneNumber();

        if (!so.getString("Department_Id").equals("0")) {
            department = findDept.getString("Name");
        } else {
            department = "N/A";
        }

        if (!findEmp.getString("Position").equals("null")) {
            position = findEmp.getString("Position");
        } else {
            position = "N/A";
        }

        if (reqInfo.getAvailableLeaveLastyear() == null) {
            lastYear = 0;
        } else {
            lastYear = reqInfo.getAvailableLeaveLastyear();
        }

        //Start Part Requester Information
        model.addAttribute("nikEmp", nik);
        model.addAttribute("namaEmp", reqInfo.getName());
        model.addAttribute("customerId", so.getString("Customer_Id"));
        model.addAttribute("customer", so.getString("customername"));
        model.addAttribute("department", department);
        model.addAttribute("position", position);
        model.addAttribute("lastYear", lastYear);
        model.addAttribute("expireDate", format.format(reqInfo.getExpireddateLeaveLastyear()));
        model.addAttribute("currentYear", reqInfo.getAvailableLeaveCurrentyear());
        model.addAttribute("currentExp", format.format(reqInfo.getExpireddateLeaveCurrentyear()));
        model.addAttribute("totalLeave", reqInfo.getTotalavailableleave());
        //End Part Requesetr Information

        //Start Part Request Detai
        Approval approval = asi.findAppByid(idLeave);
        if (approval == null) {
            remark = "N/A";
        } else {
            remark = approval.getRemarks();
        }
        model.addAttribute("id", idLeave);
        model.addAttribute("reqDetail", rd);
        model.addAttribute("noHp", noHp);
        model.addAttribute("IsiRemark", remark);
        //End Part Request Detail
        return "approval/leave/leaveReqAdm";
    }

    @RequestMapping(value = "/accepted", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String accepetReq(HttpServletRequest response) {
        String hasil = "Success", id, remark, user;
        id = response.getParameter("id");
        remark = response.getParameter("remarks");
        user = response.getParameter("userId");

        RequestDetail rd = rdsi.requestEmpid(id);
        rd.setRequestStatus("Accepted");
        rdsi.updateReq(rd);
        Approval approval = new Approval(rd.getId().toString(), Integer.parseInt(user),
                rd.getRequestStatus(), timeAdd6Hours(), remark);
        asi.save(approval);
        int getNik = rd.getNik().getNik();
        JSONObject me = serviceApi.findEmpbyid(Integer.toString(getNik));
        String getName = me.getString("Name");
        String email = serviceApi.findEmpbyid(Integer.toString(rd.getNik().getNik())).getString("Email");
        emailResponse(id, "Accept", getName, email);
        return hasil;
    }

    @RequestMapping(value = "/rejected", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String rejectReq(HttpServletRequest response) {
        String hasil = "Success", id, remark, user;
        int current, last, total;
        id = response.getParameter("id");
        remark = response.getParameter("remarks");
        user = response.getParameter("userId");

        RequestDetail rd = rdsi.requestEmpid(id);
        rd.setRequestStatus("Rejected");
        rdsi.updateReq(rd);

        RequesterInformation reqInfo = risi.findRequesterbyid(rd.getNik().getNik());
        current = reqInfo.getAvailableLeaveCurrentyear();
        last = reqInfo.getAvailableLeaveLastyear();
        total = reqInfo.getTotalavailableleave();
        reqInfo.setAvailableLeaveCurrentyear(current + rd.getReduceCurrent());
        reqInfo.setAvailableLeaveLastyear(last + rd.getReduceLast());
        reqInfo.setTotalavailableleave(total + (rd.getReduceLast() + rd.getReduceCurrent()));
        risi.save(reqInfo);

        Approval approval = new Approval(rd.getId().toString(), Integer.parseInt(user),
                rd.getRequestStatus(), timeAdd6Hours(), remark);
        asi.save(approval);
        String email = serviceApi.findEmpbyid(Integer.toString(rd.getNik().getNik())).getString("Email");
        String me = serviceApi.findEmpbyid(Integer.toString(rd.getNik().getNik())).getString("Name");
        emailResponse(id, "Reject", me, email);
        return hasil;
    }

    @GetMapping("/leaveHistoryAdm/{email}")
    public String leaveHistoryAdm(@PathVariable(value = "email") String email, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (!email.equalsIgnoreCase(authentication.getName())) {
            return "redirect:/homeAdm";
        } else if (user != null) {

            int nikLogin = user.getInt("User_Id");
            //        System.out.println("Nik Login " + user.getInt("User_Id"));
            model.addAttribute("nikLogin", nikLogin);
            String role;
            List<JSONObject> listData1 = serviceApi.adminNik(); // Get data berdasarkan 
            //        System.out.println(serviceApi.adminNik());
            for (JSONObject jSONObject : listData1) {
                //            System.out.println("Ini Role " + jSONObject.getString("Role"));
                //            System.out.println("Ini Nik " + jSONObject.getInt("Nik") + "");
                int nikAdmin = jSONObject.getInt("Nik");
                if (nikLogin == nikAdmin) {
                    role = jSONObject.getString("Role");
                    model.addAttribute("adminMSHR", role);
                }
            }

            int nik = user.getInt("User_Id");
            model.addAttribute("nik", user.getInt("User_Id"));
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("reqRm", rdsi.historyRequestRm(nik, nik));
            List<Object[]> listIsiHistori = new ArrayList<>();
            for (Object[] objects : asi.findHistory(nik, nik)) {
                Object[] objects1 = new Object[13];
                objects1[0] = objects[0];
                objects1[1] = objects[1];
                objects1[2] = risi.findRequesterbyid((Integer) objects[1]).getName();
                objects1[3] = objects[2];
                objects1[4] = ltsi.leaveTypeById((Integer) objects[3]).getType();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                objects1[5] = format.format(objects[4]);
                objects1[6] = format.format(objects[5]);
                objects1[7] = objects[6];
                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//                System.out.println("" + objects[7]);
                objects1[8] = format1.format(objects[7]);
                objects1[9] = objects[8];
                objects1[10] = objects[9];
                objects1[11] = format1.format(objects[11]);
//            System.out.println("is delete" + objects[8]);
                listIsiHistori.add(objects1);
            }
            model.addAttribute("histori", listIsiHistori);
        }
        return "approval/leave/leaveHistoryAdm";
    }

    public Date timeAdd6Hours() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 7);
        System.out.println("Penambahan" + calendar.getTime());
        return calendar.getTime();
    }

    public String emailResponse(String leave, String response, String user, String email) {

        String akun = "vsmallgift@gmail.com";
        try {
            emailService.sendResponse(leave, response, user, email);
        } catch (Exception e) {
            log.info("Error sending email = " + e.getMessage());
        }
        return "Email Sent";
    }
}
