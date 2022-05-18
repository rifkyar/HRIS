/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.approval;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.entities.Approval;
import com.springboot.HRISNEW.entities.MChargebackstatus;
import com.springboot.HRISNEW.entities.MChargebacktype;
import com.springboot.HRISNEW.entities.MOfficehour;
import com.springboot.HRISNEW.entities.MPaidtostaff;
import com.springboot.HRISNEW.entities.MReportovertime;
import com.springboot.HRISNEW.entities.Recap;
import com.springboot.HRISNEW.entities.TbMStatus;
import com.springboot.HRISNEW.entities.TrRequestovertime;
import com.springboot.HRISNEW.entities.TrRequestovertimedetail;
import com.springboot.HRISNEW.entities.TrSoovertime;
import com.springboot.HRISNEW.implementservices.ApprovalServiceImpl;
import com.springboot.HRISNEW.implementservices.ChargeBackStatusImpl;
import com.springboot.HRISNEW.implementservices.ChargeBackTypeImpl;
import com.springboot.HRISNEW.implementservices.ExcelGenerator;
import com.springboot.HRISNEW.implementservices.OfficeHourServiceImpl;
import com.springboot.HRISNEW.implementservices.OthersServiceImpl;
import com.springboot.HRISNEW.implementservices.PaidToStaffServiceImpl;
import com.springboot.HRISNEW.implementservices.ReportOvertimeServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestDetailOvertimeServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestOvertimeServiceImpl;
import com.springboot.HRISNEW.implementservices.SOOvertimeServiceImpl;
import com.springboot.HRISNEW.implementservices.StatusServiceImpl;
import com.springboot.HRISNEW.repositories.SOOvertimeRepo;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author USER
 */
@Controller
public class OvertimeApprovalController {

    @Autowired
    private ServiceApi serviceApi;

    @Autowired
    private SOOvertimeRepo oOvertimeRepo;

    @Autowired
    private OfficeHourServiceImpl ohsi;

    @Autowired
    private PaidToStaffServiceImpl ptssi;

    @Autowired
    private ChargeBackStatusImpl cbsi;

    @Autowired
    private ChargeBackTypeImpl cbti;

    @Autowired
    private RequestDetailOvertimeServiceImpl rdosi;

    @Autowired
    private SOOvertimeServiceImpl soosiImpl;

    @Autowired
    private RequestOvertimeServiceImpl rosi;

    @Autowired
    private ApprovalServiceImpl asi;

    @Autowired
    private OthersServiceImpl osi;
    
    @Autowired
    private StatusServiceImpl ssi;

    @Autowired
    private ReportOvertimeServiceImpl rosi1;

    @Autowired
    private EmailService emailService;

    public static String userId, idnya;

    private String remoteHost = "103.18.133.235";
    private String username = "miiuploader";
    private String password = "CentralPark@18";

    @GetMapping("/dataCustomer/{id}")
    public String dataCustomer(@PathVariable(value = "id") Integer id, Model model) throws ParseException {

        int nikdataCustomer;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(userId);

        if (jobjUser != null) {
            nikdataCustomer = jobjUser.getInt("User_Id");
            model.addAttribute("nik", jobjUser.getInt("User_Id"));
            model.addAttribute("email", jobjUser.getString("Email"));
            
            int nikLogin = jobjUser.getInt("User_Id");
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

        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nikdataCustomer);
        String editPermission;

        if (nikdataCustomer == 6715 || nikdataCustomer == 3672 || nikdataCustomer == 14921) {
            editPermission = "yes";
        } else {
            editPermission = "no";
        }

        if (nikdataCustomer == id) {
            String[] meals = {"Yes", "No"};

            model.addAttribute("meals", meals);
            model.addAttribute("editPermission", editPermission);
            model.addAttribute("editCustomer", new TrSoovertime());
            model.addAttribute("penddingList", rdosi.showPenddingSoOt());
            model.addAttribute("officeHour", ohsi.getAll());
            model.addAttribute("paidToStaff", ptssi.getAll());
            model.addAttribute("chargebackstatus", cbsi.getAll());
            model.addAttribute("chargebacktype", cbti.getAll());

            return "approval/overtime/dataCustomerAdm";
        } else {
            return "redirect:/login?logout";
        }
    }

    @GetMapping(value = "/dataCustomer/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String dataCus() {

        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();

        for (Object[] objects : oOvertimeRepo.soOtListAdm()) {
            JSONObject jsono1 = new JSONObject();

            if (objects[0] != null) {
                jsono1.put("soId", objects[0]);

            } else {
                jsono1.put("soId", "N/A");
            }

            if (objects[1] != null) {
                jsono1.put("customerName", objects[1]);
            } else {
                jsono1.put("customerName", "N/A");
            }

            if (objects[3] != null) {
                jsono1.put("relationManager", objects[3] + " - " + objects[2]);
            } else {
                jsono1.put("relationManager", "N/A");
            }

            if (objects[4] != null) {
                jsono1.put("officeHourId", ohsi.findById(Integer.parseInt(objects[4].toString())).getOfficeHour());
            } else {
                jsono1.put("officeHourId", "N/A");
            }

            if (objects[5] != null) {
                jsono1.put("paidToStaffId", ptssi.findById(Integer.parseInt(objects[5].toString())).getPaidToStaff());
            } else {
                jsono1.put("paidToStaffId", "N/A");
            }

            if (objects[6] != null) {
                jsono1.put("mealsTransportStatus", objects[6]);
            } else {
                jsono1.put("mealsTransportStatus", "N/A");
            }

            if (objects[7] != null) {
                jsono1.put("chargebackStatus", cbsi.findById(Integer.parseInt(objects[7].toString())).getChargeBackStatus());
            } else {
                jsono1.put("chargebackStatus", "N/A");
            }

            if (objects[8] != null) {
                jsono1.put("chargebackType", cbti.findById(Integer.parseInt(objects[8].toString())).getChargeBackType());
            } else {
                jsono1.put("chargebackType", "N/A");
            }

            if (objects[9] != null) {
                jsono1.put("rateWeekday", "Weekday: Rp. " + objects[9]);
            } else {
                jsono1.put("rateWeekday", "Weekday: Rp. ");
            }

            if (objects[10] != null) {
                jsono1.put("rateWeekend", "Weekday: Rp. " + objects[10]);
            } else {
                jsono1.put("rateWeekend", "Weekday: Rp. ");
            }

//            System.out.println(objects[2]);
            jsona.put(jsono1);
        }
        jsono.put("data", jsona);
        return jsono.toString();
    }

    @RequestMapping(value = "/saveEditCustomer", method = RequestMethod.POST)
    public String saveEditCustomer(
            @ModelAttribute("editCustomer") TrSoovertime trSoovertime,
            @RequestParam(value = "soNumberEdit2", required = false) String soNumberEd,
            @RequestParam(value = "cusNameEdit2", required = false) String cusNameEd,
            @RequestParam(value = "rmEdit2", required = false) String rmEd,
            @RequestParam(value = "chargebackstatusEdit", required = false) int chargebackstatusEd,
            @RequestParam(value = "chargebacktypeEdit", required = false) int chargebacktypeEd,
            @RequestParam(value = "mealsEdit", required = false) String mealsEd,
            @RequestParam(value = "officeHourEdit", required = false) int officeHourEd,
            @RequestParam(value = "paidToStaffEdit", required = false) int paidToStaffEd,
            @RequestParam(value = "rateWeekdayEdit", required = false) String rateWeekdayEd,
            @RequestParam(value = "rateWeekendEdit", required = false) String rateWeekendEd
    ) {

        System.out.println("officeED= " + officeHourEd);
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();

        JSONObject joUser = serviceApi.findUserByEmail(userId);
        if (joUser != null) {
            nik = joUser.getInt("User_Id");
        } else {
            return "redirect:/login?logout";
        }
//        System.out.println("So Number : " + soNumberEd);
//        System.out.println("Cus Name : " + cusNameEd);
//        System.out.println("Rm : " + rmEd);
//        System.out.println("CB Stat : " + chargebackstatusEd);
//        System.out.println("CB Type : " + chargebacktypeEd);
//        System.out.println("Meals : " + mealsEd);
//        System.out.println("Off Hour : " + officeHourEd);
//        System.out.println("Paid to staff : " + paidToStaffEd);
//        System.out.println("Rate Weekday : " + rateWeekdayEd);
//        System.out.println("Rate Weekend : " + rateWeekendEd);

        System.out.println("idnya adalah : " + idnya);
        System.out.println("nik : " + nik);

        if (Integer.parseInt(idnya) == nik) {
            String rwd_edit = rateWeekdayEd.replace(",", "");
            String rwe_edit = rateWeekendEd.replace(",", "");

            TrSoovertime findonemuser = soosiImpl.showSoOt(soNumberEd);
            findonemuser.setOfficeHourID(new MOfficehour(officeHourEd));
            findonemuser.setPaidToStaffID(new MPaidtostaff(paidToStaffEd));
            findonemuser.setRateWeekday(Integer.parseInt(rwd_edit));
            findonemuser.setRateWeekend(Integer.parseInt(rwe_edit));
            findonemuser.setChargebackStatus(new MChargebackstatus(chargebackstatusEd));
            findonemuser.setChargerBackType(new MChargebacktype(chargebacktypeEd));
            findonemuser.setMealsTransportStatus(mealsEd);
            findonemuser.setUpdateBy(Integer.toString(nik));
            findonemuser.setUpdateDate(timeAdd6Hours());
            soosiImpl.updateSoOt(findonemuser);
            idnya = "";

        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/dataCustomer/" + nik;
    }

    @GetMapping("/pendingapproval/{id}")
    public String pendingapproval(@PathVariable(value = "id") Integer id, Model model) throws ParseException {

        int nikdataCustomer;
//        System.out.println("id====" + id);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(userId);

        if (jobjUser != null) {
            nikdataCustomer = jobjUser.getInt("User_Id");
            model.addAttribute("nik", jobjUser.getInt("User_Id"));
            model.addAttribute("email", jobjUser.getString("Email"));
            
            int nikLogin = jobjUser.getInt("User_Id");
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

        } else {
            return "redirect:/login?logout";
        }

        model.addAttribute("penddingList", rdosi.showPenddingSoOt());

        return "approval/overtime/pendingApproval";
    }

    public Date timeAdd6Hours() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, +7);
//        System.out.println("pengurangan" + calendar.getTime());
        return calendar.getTime();
    }

    public String Auth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(userId);
        if (jobjUser != null) {
            int s = jobjUser.getInt("User_Id");
            idnya = Integer.toString(s);
        } else {
            return "redirect:/login?logout";
        }
        return idnya;
    }

    @RequestMapping(value = "/detailCustomerAdm/{id}", method = RequestMethod.GET)
    public String detailCustomerAdm(@PathVariable String id, Model model) throws ParseException {

        Auth();
        System.out.println("--> method = detailCustomer / id SO " + id + " idnya = " + idnya);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(userId);

        if (jobjUser != null) {
            model.addAttribute("nik", jobjUser.getInt("User_Id"));
            model.addAttribute("email", jobjUser.getString("Email"));
            
            int nikLogin = jobjUser.getInt("User_Id");
            model.addAttribute("nikLogin", nikLogin);
            String role;
            List<JSONObject> listData1 = serviceApi.adminNik(); // Get data berdasarkan 
            for (JSONObject jSONObject : listData1) {
                int nikAdmin = jSONObject.getInt("Nik");
                if (nikLogin == nikAdmin) {
                    role = jSONObject.getString("Role");
                    model.addAttribute("adminMSHR", role);
                }
            }

        } else {
            return "redirect:/login?logout";
        }

//        TrSoovertime getSoOT = soosiImpl.showSoOt(id);
//        String namaCustomer = getSoOT.getCustomerName();
//        System.out.println("nama cus = " + namaCustomer);
//        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        List<Object[]> listRequesterOvertime = new ArrayList<>();
        List<Integer> listRM = new ArrayList<>();
        List<String> soID = new ArrayList<>();
//        JSONObject jo = new JSONObject();

        for (Object[] object : soosiImpl.soOtListRequester(id)) {
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

//            System.out.println("ini object ==== " + objects[8].toString());
            SimpleDateFormat typeFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateInString = objects[5].toString();
            Calendar c = Calendar.getInstance();
            c.setTime(typeFormat.parse(dateInString));
            c.add(Calendar.DATE, -1);
            String strDate = typeFormat.format(c.getTime());
//            Date newStrDate = typeFormat.parse(strDate);
//            System.out.println("hasil" + strDate);

//            objects[5] = strDate;
//            System.out.println("ini objects 5 : " +objects[5].toString());
            listRequesterOvertime.add(objects);
            listRM.add((Integer) objects[7]);
            soID.add((String) objects[8]);
        }

        String RM_Name = null;
        String SO_ID = null;

        for (int i = 0; i < listRM.size(); i++) {
            System.out.println("ini RMnya ==== " + listRM.get(i));
            RM_Name = Integer.toString(listRM.get(i));
            break;
        }

        for (int i = 0; i < listRM.size(); i++) {
//           
            SO_ID = soID.get(i);
        }
//        System.out.println("ini SOnya ==== " + soID.get(0));

//        String RM_Name = listRequesterOvertime.get(6).toString();
//        System.out.println("RMnya adalah ==== " + listRM);
        String msfc = "6715";
        String add = "14921";
        String recapAccess;
        
        if (idnya.equalsIgnoreCase(msfc) || idnya.equalsIgnoreCase("3672") || idnya.equalsIgnoreCase(add) || idnya.equalsIgnoreCase(RM_Name)) {
            recapAccess = "yes";
        } else {
            recapAccess = "no";
        }
//        System.out.println("idnya ==== " + idnya + " & msfc ==== " + msfc + " & RM ==== " + RM_Name);
        System.out.println("recapAccess ==== " + recapAccess);

        model.addAttribute("listRequestOveertime", listRequesterOvertime);
//        System.out.println("ini = " + listRequesterOvertime);
//        model.addAttribute("detailCustomer", soosiImpl.soOtListRequester(id));
        model.addAttribute("recapAccess", recapAccess);
        model.addAttribute("soid", SO_ID);

        return "approval/overtime/detailCustomerAdm";
    }

    @RequestMapping(value = "/detailEmployeeAdm/{id}", method = RequestMethod.GET)
    public String detailEmployeeAdm(@PathVariable("id") String id, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailNow = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(emailNow);
        int nikNow = jobjUser.getInt("User_Id");
        System.out.println("nik sekarang = " + nikNow);

        userId = auth.getName();
        JSONObject jobjUserId = serviceApi.findUserByEmail(userId);

        if (jobjUser != null) {
            model.addAttribute("nik", jobjUserId.getInt("User_Id"));
            model.addAttribute("email", jobjUserId.getString("Email"));
            
            int nikLogin = jobjUser.getInt("User_Id");
            model.addAttribute("nikLogin", nikLogin);
            String role;
            List<JSONObject> listData1 = serviceApi.adminNik(); // Get data berdasarkan 
            for (JSONObject jSONObject : listData1) {
                int nikAdmin = jSONObject.getInt("Nik");
                if (nikLogin == nikAdmin) {
                    role = jSONObject.getString("Role");
                    model.addAttribute("adminMSHR", role);
                }
            }

        } else {
            return "redirect:/login?logout";
        }

        String niknya, name, customer_name, total_lembur, approval_path, status;
        String thisRM = null, thisPM = null, thisMSFC = null, thisADD = null;

        TrRequestovertime employee = rosi.showRequesterOTbyid(id);
        name = employee.getEmployeeName();
        niknya = Integer.toString(employee.getEmployeeNik());
        customer_name = employee.getSoId().getCustomerName();
        total_lembur = Float.toString(employee.getTotalOvertime()) + " Hours";

        if (employee.getSoId().getOfficeHourID() != null) {
            status = employee.getSoId().getOfficeHourID().getOfficeHour();
//            System.out.println("STATUSSSSSSSSSSSSSSSSSSSS ================ " +status);
        } else {
            status = "N/A";
        }

//        System.out.println("EMPOOOOOOOO ===== " + employee.getSoId().getOfficeHourID().getOfficeHour());
//        if (employee.getSoId().getOfficeHourID().getOfficeHour() != null) {
//            status = employee.getSoId().getOfficeHourID().getOfficeHour();
//        } else {
//            status = "N/A";
//        }
        JSONObject all = serviceApi.findAllocationbyNik(niknya);
        System.out.println("all = " + all);
        String soId = all.getString("So_Id");

        JSONObject so = serviceApi.findSobyId(soId);

        String Project_Manager = so.getString("Project_Manager");
        System.out.println("nama pm adalah : " + Project_Manager);

        String RM_Name = so.getString("RM_Name");
        System.out.println("nama rm adalah : " + RM_Name);

        String msfc = "6715";
        String add = "14921";

        String nikExist = Integer.toString(nikNow);
        System.out.println("nik yang sesuai : " + nikExist);

        if (nikExist.equalsIgnoreCase(Project_Manager) || nikExist.equalsIgnoreCase(RM_Name) || nikExist.equalsIgnoreCase(msfc) || nikExist.equalsIgnoreCase(add)) {
            approval_path = "yes";
            if (nikExist.equalsIgnoreCase(Project_Manager)) {
                thisPM = "pm";
                System.out.println("thisPM = " + thisPM + " thisRM = " + thisRM + " thisMSFC = " + thisMSFC);
            } else if (nikExist.equalsIgnoreCase(RM_Name)) {
                thisRM = "rm";
                System.out.println("thisPM = " + thisPM + " thisRM = " + thisRM + " thisMSFC = " + thisMSFC);
            } else if (nikExist.equalsIgnoreCase(msfc)) {
                thisMSFC = "msfc";
                System.out.println("thisPM = " + thisPM + " thisRM = " + thisRM + " thisMSFC = " + thisMSFC);
            } else if (nikExist.equalsIgnoreCase(add)) {
                thisADD = "add";
                System.out.println("thisPM = " + thisPM + " thisRM = " + thisRM + " thisADD = " + thisADD);
            }
//            System.out.println("niknya === " + nikExist + " PM = "
//                    + "" + Project_Manager + " RM = " + RM_Name + " MSFC = " + msfc + " approval_path = " + approval_path);
        } else {
            approval_path = "no";
            System.out.println(">>>>>niknya = " + nikExist + " PM = "
                    + "" + Project_Manager + " RM = " + RM_Name + " MSFC = " + msfc + " approval_path = " + approval_path);
        }

        Iterable<TrRequestovertimedetail> detail = rdosi.findDetailbyId(id);
        for (TrRequestovertimedetail det : detail) {
            String m = det.getRequestOvertimeID().getRequestStatus();
            Integer ma = det.getOvertimeDetail();
            System.out.println("--> getRequestOvertimeID().getRequestStatus() == " + m + " - - " + ma);
        }

        JSONObject findNIKEmployee = serviceApi.findEmpbyid(niknya);
        String Employee_Email = findNIKEmployee.getString("Email");
        String Contract_Status;
        if (serviceApi.findStatusContract(Employee_Email) != null) {
            JSONObject findLastContract = serviceApi.findStatusContract(Employee_Email);
            Contract_Status = findLastContract.getString("Contract_Status");
        } else {
            System.out.println("CONTRACT STATUS NULL");
            Contract_Status = "N/A";
        }
//        String Contract_Status = findLastContract.getString("Contract_Status");

        JSONObject allowance = serviceApi.allowanceExist(niknya);
        System.out.println("tunjangan = " + allowance);
        String status_tunjangan;
        String tunjangan = allowance.getString("check");
        if (tunjangan == "true") {
            status_tunjangan = "Memiliiki tunjangan overtime";
        } else {
            status_tunjangan = "Tidak memiliiki tunjangan overtime";
        }

        System.out.println(">>>>>>>>>approval_path : " + approval_path);
        System.out.println(">>>>>>>>>thisPM : " + thisPM);
        System.out.println(">>>>>>>>>thisRM : " + thisRM);
        System.out.println(">>>>>>>>>thisMSFC : " + thisMSFC);
        System.out.println(">>>>>>>>>findDetail : " + rdosi.findDetailbyId(id));

        model.addAttribute("niknya", niknya);
        model.addAttribute("name", name);
        model.addAttribute("custName", customer_name);
        model.addAttribute("tunjangan", status_tunjangan);
        model.addAttribute("listDetailOT", rdosi.findDetailbyId(id));
        model.addAttribute("soId", soId);
        model.addAttribute("Contract_Status", Contract_Status);
        model.addAttribute("thisPM", thisPM);
        model.addAttribute("thisRM", thisRM);
        model.addAttribute("thisMSFC", thisMSFC);
        model.addAttribute("thisADD", thisADD);
        model.addAttribute("approval_path", approval_path);
        model.addAttribute("total_lembur", total_lembur);
        model.addAttribute("status", status);

        return "approval/overtime/detailEmployeeAdm";
    }

    @RequestMapping(value = "/approvalHistory/{id}", method = RequestMethod.GET)
    public String viewApprovalHistory(@PathVariable("id") Integer id, Model model, HttpServletResponse response) throws IOException {
        String nama = null;
        int c = 0;
        List<String> namaUser = new ArrayList<String>();
        Auth();

        Iterable<Approval> ap = asi.findAppByReqOTDetailId(Integer.toString(id));
        for (Approval approval : ap) {
            System.out.println("id approvalnya  = " + approval.getId() + " status approval = " + approval.getStatus() + "yang approve = " + approval.getUserId());
            JSONObject jobjUser = serviceApi.userbyid(approval.getUserId());

            if (jobjUser != null) {
                nama = jobjUser.getString("Name");
                namaUser.add(c++, nama);
            } else {
                JSONObject employee = serviceApi.findEmpbyid(Integer.toString(approval.getUserId()));
                System.out.println("employee " + employee);
                if (employee != null) {
                    nama = employee.getString("Name");
                    namaUser.add(c++, nama);
                } else {
                    nama = "N/A";
                }
            }
            System.out.println("nama " + nama);
        }

        model.addAttribute("approvalHistory", asi.findAppByReqOTDetailId(Integer.toString(id)));
        model.addAttribute("namaUser", namaUser);

        return "approval/overtime/detailEmployeeAdm :: detailHistoryApproval";
    }

    @Transactional
    @RequestMapping(value = "/acceptReq/{reqid}", method = RequestMethod.GET)
    public String acceptReq(@PathVariable("reqid") String reqid) {
        Auth();
        System.out.println("masuk 1");
        TbMStatus status = ssi.findOnebyId("8");
        String request_status = status.getValue();
        System.out.println("masuk 2");
        Optional<TrRequestovertimedetail> detilRequestOvertime = rdosi.findReqDetailbyID(Integer.parseInt(reqid));
        int nik_empl = detilRequestOvertime.get().getRequestOvertimeID().getEmployeeNik();
        int current_nik = detilRequestOvertime.get().getRequestOvertimeID().getNextApproval();
        System.out.println("masuk 3");

        JSONObject all = serviceApi.findAllocationbyNik(Integer.toString(nik_empl));
        String soId = all.getString("So_Id");
        JSONObject so = serviceApi.findSobyId(soId);
        String Project_Manager = so.getString("Project_Manager");
        String RM_Name = so.getString("RM_Name");
        String msfc = "6715";
        String add = "14921";

//        cek
        System.out.println("accept====> Project_Manager : " + Project_Manager);
        System.out.println("accept====> RM_Name : " + RM_Name);
        System.out.println("accept====> ID yang Login : " + idnya);

        Approval approve = new Approval(Integer.parseInt(idnya), request_status, timeAdd6Hours(), "-", new TrRequestovertimedetail(Integer.parseInt(reqid)));
        asi.save(approve);
        System.out.println("====> method = acceptReq / reqid = " + reqid + " / nik_empl = " + nik_empl);

        TrRequestovertimedetail tr = rdosi.findRequestOTDetailbyID(reqid);

        System.out.println("xx PM xx : " + tr.getPMorLeader());
        System.out.println("xx RM xx : " + tr.getRm());
        System.out.println("xx MFSC xx : " + tr.getMsfc());

        if (tr.getPMorLeader() == Boolean.FALSE && tr.getRm() == null && tr.getMsfc() == null) {

            if (Project_Manager.equals(idnya)) {
                TrRequestovertimedetail tr0 = rdosi.findRequestOTDetailbyID(reqid);
                Integer nikEMP = tr0.getRequestOvertimeID().getEmployeeNik();
                JSONObject empByNik = serviceApi.findEmpbyid(nikEMP.toString());
                String division = empByNik.getString("Division");
                if (division.equalsIgnoreCase("80067210")) {
                    System.out.println("MSBU DIVISION");
                    TbMStatus Status1 = ssi.findOnebyId("7");
                    String update_statusPM = Status1.getValue();
    //                TrRequestovertimedetail tr0 = rdosi.findRequestOTDetailbyID(reqid);
                    tr0.setPMorLeader(Boolean.TRUE);
                    tr0.setRm(Boolean.TRUE);
                    tr0.setRequestStatus(update_statusPM);
                    tr0.setUpdateBy(idnya);
                    tr0.setUpdateDate(timeAdd6Hours());
                    rdosi.updateOvertimeDetail(tr0);

                    Integer idReqOT = tr0.getRequestOvertimeID().getRequestOvertimeID();
                    TrRequestovertime req = rosi.showRequesterOTbyid(Integer.toString(idReqOT));
                    if (rosi.findPMStatus(idReqOT) == null) {
                        System.out.println("================= PM Status : " + rosi.findPMStatus(idReqOT));
                        req.setNextApproval(Integer.parseInt(msfc));
                        req.setUpdateBy(idnya);
                        req.setUpdateDate(timeAdd6Hours());
                        rosi.updateRequestOT(req);
                    }

                    int nik = req.getEmployeeNik();
                    JSONObject user = serviceApi.userbyid(Integer.parseInt(msfc));
                    JSONObject empl = serviceApi.findEmpbyid(Integer.toString(nik));
                    String EmplName = empl.getString("Name");
                    String EmplPosition = empl.getString("Position");

                    String id_request = Integer.toString(req.getRequestOvertimeID());
                    String getCustomerName = req.getSoId().getCustomerName();
                    String so_id = req.getSoId().getSoId();
                    String PICname = req.getPICName();
                    String PICpos = req.getPICPosition();
                    Float totalOvertime = req.getTotalOvertime();
                    String totalOTTemp = Float.toString(totalOvertime);
                    Date periodeTemp = req.getPeriod();
                    DateFormat df = new SimpleDateFormat("MMM yyyy");
                    String periode = df.format(periodeTemp);

                    String emailMSFC, namaMSFC;
                    if (user != null) {
                        emailMSFC = user.getString("Email");
                        namaMSFC = user.getString("Name");
                    } else {
                        user = serviceApi.findEmpbyid(msfc);
                        emailMSFC = user.getString("Email");
                        namaMSFC = user.getString("Name");
                    }
                    sendMail(so_id, id_request, emailMSFC, namaMSFC, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOTTemp, periode, nik);
                } else if (division.equalsIgnoreCase("80067220")) {
                    System.out.println("ADD DIVISION");
                    TbMStatus Status1 = ssi.findOnebyId("7");
                    String update_statusPM = Status1.getValue();
    //                TrRequestovertimedetail tr0 = rdosi.findRequestOTDetailbyID(reqid);
                    tr0.setPMorLeader(Boolean.TRUE);
                    tr0.setRm(Boolean.TRUE);
                    tr0.setRequestStatus(update_statusPM);
                    tr0.setUpdateBy(idnya);
                    tr0.setUpdateDate(timeAdd6Hours());
                    rdosi.updateOvertimeDetail(tr0);

                    Integer idReqOT = tr0.getRequestOvertimeID().getRequestOvertimeID();
                    TrRequestovertime req = rosi.showRequesterOTbyid(Integer.toString(idReqOT));
                    if (rosi.findPMStatus(idReqOT) == null) {
                        System.out.println("================= PM Status : " + rosi.findPMStatus(idReqOT));
                        req.setNextApproval(Integer.parseInt(add));
                        req.setUpdateBy(idnya);
                        req.setUpdateDate(timeAdd6Hours());
                        rosi.updateRequestOT(req);
                    }

                    int nik = req.getEmployeeNik();
                    JSONObject user = serviceApi.userbyid(Integer.parseInt(add));
                    JSONObject empl = serviceApi.findEmpbyid(Integer.toString(nik));
                    String EmplName = empl.getString("Name");
                    String EmplPosition = empl.getString("Position");

                    String id_request = Integer.toString(req.getRequestOvertimeID());
                    String getCustomerName = req.getSoId().getCustomerName();
                    String so_id = req.getSoId().getSoId();
                    String PICname = req.getPICName();
                    String PICpos = req.getPICPosition();
                    Float totalOvertime = req.getTotalOvertime();
                    String totalOTTemp = Float.toString(totalOvertime);
                    Date periodeTemp = req.getPeriod();
                    DateFormat df = new SimpleDateFormat("MMM yyyy");
                    String periode = df.format(periodeTemp);

                    String emailMSFC, namaMSFC;
                    if (user != null) {
                        emailMSFC = user.getString("Email");
                        namaMSFC = user.getString("Name");
                    } else {
                        user = serviceApi.findEmpbyid(add);
                        emailMSFC = user.getString("Email");
                        namaMSFC = user.getString("Name");
                    }
                    sendMail(so_id, id_request, emailMSFC, namaMSFC, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOTTemp, periode, nik);
                }
            } else {
                System.out.println("accept====> Project Manager Tidak sesuai dengan yang seharusnya");
            }

        } else if (tr.getPMorLeader() == Boolean.TRUE && tr.getRm() == null && tr.getMsfc() == null) {
            
            if (RM_Name.equals(idnya)) {
                TrRequestovertimedetail tr1 = rdosi.findRequestOTDetailbyID(reqid);
                Integer nikEMP = tr1.getRequestOvertimeID().getEmployeeNik();
                JSONObject empByNik = serviceApi.findEmpbyid(nikEMP.toString());
                String division = empByNik.getString("Division");
                System.out.println("DIVISI = " + division);
                if (division.equalsIgnoreCase("80067210")) {
                    TbMStatus Status2 = ssi.findOnebyId("7");
                    String update_statusRM = Status2.getValue();
                    tr1.setRm(Boolean.TRUE);
                    tr1.setRequestStatus(update_statusRM);
                    tr1.setUpdateBy(idnya);
                    tr1.setUpdateDate(timeAdd6Hours());
                    rdosi.updateOvertimeDetail(tr1);
    //
                    Integer idReqOT = tr1.getRequestOvertimeID().getRequestOvertimeID();
                    TrRequestovertime req = rosi.showRequesterOTbyid(Integer.toString(idReqOT));
                    if (rosi.findRMStatus(idReqOT) == null) {
                        System.out.println("----------- " + rosi.findRMStatus(idReqOT));
                        req.setNextApproval(Integer.parseInt(msfc));
                        req.setUpdateBy(idnya);
                        req.setUpdateDate(timeAdd6Hours());
                        rosi.updateRequestOT(req);
                    }
                    checkApproval(reqid);

                    int nik = req.getEmployeeNik();
                    JSONObject user = serviceApi.userbyid(Integer.parseInt(msfc));
                    JSONObject empl = serviceApi.findEmpbyid(Integer.toString(nik));
                    String EmplName = empl.getString("Name");
                    String EmplPosition = empl.getString("Position");

                    String id_request = Integer.toString(req.getRequestOvertimeID());
                    String getCustomerName = req.getSoId().getCustomerName();
                    String so_id = req.getSoId().getSoId();
                    String PICname = req.getPICName();
                    String PICpos = req.getPICPosition();
                    Float totalOvertime = req.getTotalOvertime();
                    String totalOTTemp = Float.toString(totalOvertime);
                    Date periodeTemp = req.getPeriod();
                    DateFormat df = new SimpleDateFormat("MMM yyyy");
                    String periode = df.format(periodeTemp);

                    String emailMSFC, namaMSFC;
                    if (user != null) {
                        emailMSFC = user.getString("Email");
                        namaMSFC = user.getString("Name");
                    } else {
                        user = serviceApi.findEmpbyid(msfc);
                        emailMSFC = user.getString("Email");
                        namaMSFC = user.getString("Name");
                    }
//                sendMail("ezraip98@gmail.com", namaMSFC, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOTTemp, periode, nik);
                sendMail(so_id, id_request, emailMSFC, namaMSFC, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOTTemp, periode, nik);
//                sendMail(so_id, id_request, "ezraip98@gmail.com", namaMSFC, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOTTemp, periode, nik);
                } else if (division.equalsIgnoreCase("80067220")) {
                    System.out.println("ADD");
                    TbMStatus Status2 = ssi.findOnebyId("7");
                    String update_statusRM = Status2.getValue();
                    tr1.setRm(Boolean.TRUE);
                    tr1.setRequestStatus(update_statusRM);
                    tr1.setUpdateBy(idnya);
                    tr1.setUpdateDate(timeAdd6Hours());
                    rdosi.updateOvertimeDetail(tr1);
    //
                    Integer idReqOT = tr1.getRequestOvertimeID().getRequestOvertimeID();
                    TrRequestovertime req = rosi.showRequesterOTbyid(Integer.toString(idReqOT));
                    if (rosi.findRMStatus(idReqOT) == null) {
                        System.out.println("----------- " + rosi.findRMStatus(idReqOT));
                        req.setNextApproval(Integer.parseInt(add));
                        req.setUpdateBy(idnya);
                        req.setUpdateDate(timeAdd6Hours());
                        rosi.updateRequestOT(req);
                    }
                    checkApproval(reqid);

                    int nik = req.getEmployeeNik();
                    JSONObject user = serviceApi.userbyid(Integer.parseInt(add));
                    JSONObject empl = serviceApi.findEmpbyid(Integer.toString(nik));
                    String EmplName = empl.getString("Name");
                    String EmplPosition = empl.getString("Position");

                    String id_request = Integer.toString(req.getRequestOvertimeID());
                    String getCustomerName = req.getSoId().getCustomerName();
                    String so_id = req.getSoId().getSoId();
                    String PICname = req.getPICName();
                    String PICpos = req.getPICPosition();
                    Float totalOvertime = req.getTotalOvertime();
                    String totalOTTemp = Float.toString(totalOvertime);
                    Date periodeTemp = req.getPeriod();
                    DateFormat df = new SimpleDateFormat("MMM yyyy");
                    String periode = df.format(periodeTemp);

                    String emailMSFC, namaMSFC;
                    if (user != null) {
                        emailMSFC = user.getString("Email");
                        namaMSFC = user.getString("Name");
                    } else {
                        user = serviceApi.findEmpbyid(add);
                        emailMSFC = user.getString("Email");
                        namaMSFC = user.getString("Name");
                    }
//                sendMail("ezraip98@gmail.com", namaMSFC, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOTTemp, periode, nik);
                sendMail(so_id, id_request, emailMSFC, namaMSFC, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOTTemp, periode, nik);
//                sendMail(so_id, id_request, "ezraip98@gmail.com", namaMSFC, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOTTemp, periode, nik);
                }
            } else {
                System.out.println("accept=====> Relation Manager Tidak sesuai dengan yang seharusnya");
            }

        } else if (tr.getPMorLeader() == Boolean.TRUE && tr.getRm() == Boolean.TRUE && tr.getMsfc() == null) {
            if (msfc.equals(idnya)) {
                TbMStatus Status3 = ssi.findOnebyId("4");
                String update_statusMFSC = Status3.getValue();

//                  Update Request status  - tabel Overtime Detail
                TrRequestovertimedetail tr2 = rdosi.findRequestOTDetailbyID(reqid);
                tr2.setMsfc(Boolean.TRUE);
                tr2.setRequestStatus(update_statusMFSC);
                tr2.setUpdateBy(idnya);
                tr2.setUpdateDate(timeAdd6Hours());
                rdosi.updateOvertimeDetail(tr2);

                Approval app_cek = asi.findbyid(approve.getId());
                app_cek.setStatus(update_statusMFSC);
                asi.updateApproval(app_cek);

                Integer idReqOT = tr2.getRequestOvertimeID().getRequestOvertimeID();
                System.out.println("ID REQ OT ===== " + idReqOT);
                TrRequestovertime req = rosi.showRequesterOTbyid(Integer.toString(idReqOT));
                if (rosi.findMsfcStatus(idReqOT) == null) {
                    System.out.println("----------- " + rosi.findMsfcStatus(idReqOT));
                    req.setRequestStatus(update_statusMFSC);
                    req.setUpdateBy(idnya);
                    req.setUpdateDate(timeAdd6Hours());
                    rosi.updateRequestOT(req);
                }

                checkApproval(reqid);
            } else if (add.equalsIgnoreCase(idnya)) {
                TbMStatus Status3 = ssi.findOnebyId("4");
                String update_statusMFSC = Status3.getValue();

//                  Update Request status  - tabel Overtime Detail
                TrRequestovertimedetail tr2 = rdosi.findRequestOTDetailbyID(reqid);
                tr2.setMsfc(Boolean.TRUE);
                tr2.setRequestStatus(update_statusMFSC);
                tr2.setUpdateBy(idnya);
                tr2.setUpdateDate(timeAdd6Hours());
                rdosi.updateOvertimeDetail(tr2);

                Approval app_cek = asi.findbyid(approve.getId());
                app_cek.setStatus(update_statusMFSC);
                asi.updateApproval(app_cek);

                Integer idReqOT = tr2.getRequestOvertimeID().getRequestOvertimeID();
                System.out.println("ID REQ OT ===== " + idReqOT);
                TrRequestovertime req = rosi.showRequesterOTbyid(Integer.toString(idReqOT));
                if (rosi.findMsfcStatus(idReqOT) == null) {
                    System.out.println("----------- " + rosi.findMsfcStatus(idReqOT));
                    req.setRequestStatus(update_statusMFSC);
                    req.setUpdateBy(idnya);
                    req.setUpdateDate(timeAdd6Hours());
                    rosi.updateRequestOT(req);
                }

                checkApproval(reqid);
            }

        } else {
            System.out.println("accept====> Approve Tidak sesuai dengan yang seharusnya");
        }
        return "redirect:/detailEmployeeAdm";
    }

    public void checkApproval(String reqdetail) {
        TbMStatus Status3 = ssi.findOnebyId("4");
        String done_status = Status3.getValue();
        System.out.println("reqdetail " + reqdetail);

        TrRequestovertimedetail tr2 = rdosi.findRequestOTDetailbyID(reqdetail);
        int OvertimeID = tr2.getRequestOvertimeID().getRequestOvertimeID();
        System.out.println("OvertimeID " + OvertimeID);

        int hitungAllData = rdosi.countRequestOTbyOTid(Integer.toString(OvertimeID));
        int hitungDataStatus = rdosi.countRequestOTStatusbyOTid(Integer.toString(OvertimeID));
        System.out.println("hitungAllData = " + hitungAllData);
        System.out.println("hitungDataStatus = " + hitungDataStatus);

        if (hitungAllData == hitungDataStatus) {
            System.out.println("update status to = done");
            TrRequestovertime req = rosi.showRequesterOTbyid(Integer.toString(OvertimeID));
            req.setRequestStatus(done_status);
            req.setUpdateBy(idnya);
            req.setUpdateDate(timeAdd6Hours());
            rosi.updateRequestOT(req);

            JSONObject empl = serviceApi.findEmpbyid(Integer.toString(req.getEmployeeNik()));
            String emailRequester = empl.getString("Email");
//            String emailRequester = "ezraip98@gmail.com";
            String namaRequester = empl.getString("Name");
            sendMailDone(emailRequester, namaRequester, Integer.toString(OvertimeID));
        }
    }

    @Transactional
    @RequestMapping("/rejectReq/{rejid}/{cmnt}")
    public String rejectReq(@PathVariable String rejid, @PathVariable String cmnt) {

        String nikRM, namaRM;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(userId);
        if (jobjUser != null) {
            int s = jobjUser.getInt("User_Id");
            namaRM = jobjUser.getString("Name");
            nikRM = Integer.toString(s);
        } else {
            System.out.println("======> method = Auth / nik not in DB USER");
            return "redirect:/login?logout";
        }
        System.out.println("namaRM ================ " + namaRM);
        System.out.println("nikRM ================ " + nikRM);

        Auth();
        TbMStatus Status4 = ssi.findOnebyId("9");
        String rejectStatus = Status4.getValue();

        Optional<TrRequestovertimedetail> detilRequestOvertime = rdosi.findReqDetailbyID(Integer.parseInt(rejid));
        int nik_empl = detilRequestOvertime.get().getRequestOvertimeID().getEmployeeNik();
        int current_nik = detilRequestOvertime.get().getRequestOvertimeID().getNextApproval();

        JSONObject all = serviceApi.findAllocationbyNik(Integer.toString(nik_empl));
        String soId = all.getString("So_Id");
        JSONObject so = serviceApi.findSobyId(soId);
        String Project_Manager = so.getString("Project_Manager");
        String RM_Name = so.getString("RM_Name");
        String msfc = "6715";

        Approval approve = new Approval(Integer.parseInt(idnya), rejectStatus, timeAdd6Hours(), cmnt, new TrRequestovertimedetail(Integer.parseInt(rejid)));
        asi.save(approve);
        Integer id_This_Approval = approve.getId();
        System.out.println("=====> method = rejectReq / rejid = " + rejid + " / nik_empl = " + nik_empl + " / Comment = " + cmnt);

        TrRequestovertimedetail tr = rdosi.findRequestOTDetailbyID(rejid);

        if (tr.getPMorLeader() == Boolean.FALSE && tr.getRm() == null && tr.getMsfc() == null) {

            if (Project_Manager.equals(idnya)) {
                TbMStatus Status1 = ssi.findOnebyId("11");
                String update_statusPM = Status1.getValue();
                TrRequestovertimedetail tr0 = rdosi.findRequestOTDetailbyID(rejid);
                tr0.setPMorLeader(Boolean.FALSE);
                tr0.setRequestStatus(update_statusPM);
                tr0.setUpdateBy(idnya);
                tr0.setUpdateDate(timeAdd6Hours());
                rdosi.updateOvertimeDetail(tr0);
            } else {
                System.out.println("reject=====> Project Manager Tidak sesuai dengan yg seharusnya / Reject");
            }
            checkApproval(rejid);

        } else if (tr.getPMorLeader() == Boolean.TRUE && tr.getRm() == null && tr.getMsfc() == null) {

            if (RM_Name.equals(idnya)) {
                TbMStatus Status2 = ssi.findOnebyId("12");
                String update_statusRM = Status2.getValue();
                TrRequestovertimedetail tr1 = rdosi.findRequestOTDetailbyID(rejid);
                tr1.setRm(Boolean.FALSE);
                tr1.setRequestStatus(update_statusRM);
                tr1.setUpdateBy(idnya);
                tr1.setUpdateDate(timeAdd6Hours());
                rdosi.updateOvertimeDetail(tr1);
            } else {
                System.out.println("reject=====> Relation Manager Tidak sesuai dengan yg seharusnya  / Reject");
            }
            checkApproval(rejid);

        } else if (tr.getPMorLeader() == Boolean.TRUE && tr.getRm() == Boolean.TRUE && tr.getMsfc() == null) {

            TrRequestovertime req = null;
            if (msfc.equals(idnya)) {
                TbMStatus Status3 = ssi.findOnebyId("13");
                String update_statusMFSC = Status3.getValue();
                TrRequestovertimedetail tr2 = rdosi.findRequestOTDetailbyID(rejid);
                tr2.setMsfc(Boolean.FALSE);
                tr2.setRequestStatus(update_statusMFSC);
                tr2.setUpdateBy(idnya);
                tr2.setUpdateDate(timeAdd6Hours());
                rdosi.updateOvertimeDetail(tr2);

                Integer idReqOT = tr2.getRequestOvertimeID().getRequestOvertimeID();
                req = rosi.showRequesterOTbyid(Integer.toString(idReqOT));

            } else {
                System.out.println("reject=====> MFSC Tidak sesuai dengan yg seharusnya  / Reject");
            }

            JSONObject empl = serviceApi.findEmpbyid(Integer.toString(req.getEmployeeNik()));
            String emailRequester = empl.getString("Email");
//            String emailRequester = "ezraip98@gmail.com";
            String namaRequester = empl.getString("Name");

            sendMailReject(emailRequester, namaRequester, rejid, namaRM, cmnt);
            checkApproval(rejid);
        } else {
            System.out.println("reject=====> Reject Failed");
        }

        return "redirect:/detailEmployeeAdm";
    }

    public void whenUploadFileUsingJsch_thenSuccess(MultipartFile file) throws JSchException, SftpException, IOException {
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();

        String localFile = "src/main/resources/coba.txt";
        String remoteDir = "/tomcat/webapps/_SAP";
        System.out.println("ongoing");
        channelSftp.cd(remoteDir);
        channelSftp.put(file.getInputStream(), file.getOriginalFilename());

        System.out.println("--> method = whenUploadFileUsingJsch_thenSuccess / uploadRecap - file directory  = " + remoteDir);
        channelSftp.exit();
    }

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        jsch.setKnownHosts("/tomcat/webapps/_SAP");
        Session jschSession = jsch.getSession(username, remoteHost, 1282);

        java.util.Properties config = new java.util.Properties();
        jschSession.setPassword("CentralPark@18");
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);

        jschSession.connect();

        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    @RequestMapping(value = "/uploadRecap", method = RequestMethod.POST)
    public String saveUploadReport(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException, JSchException, SftpException {

        byte[] bytefile = file.getBytes();
        String filename = file.getOriginalFilename();
        System.out.println("=====> method = saveUploadRepoer / uploadRecap - file name = " + file.getOriginalFilename());
        MReportovertime uploadReport = new MReportovertime(filename, bytefile, timeAdd6Hours());
        rosi1.saveMReportovertime(uploadReport);
        whenUploadFileUsingJsch_thenSuccess(file);

        return "redirect:/dataCustomer/" + idnya;
    }

    @RequestMapping(value = "/downloadAllSO", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> dowloadAllSO(@RequestParam(value = "periode", required = false) String periode,
            HttpServletResponse respons) throws IOException {
        System.out.println("PERIODE === " + periode);

        List<Recap> listRecap = new ArrayList<>();
        DateFormat reportPeriode;
        String periodeName = null;
        int no = 0;
        System.out.println("periode = " + periode);
        List<TrRequestovertimedetail> datasaja = rdosi.showAllForRecap(periode);
//        for (int i = 0; i < datasaja.size(); i++) {
//            System.out.println("ini data saja" + datasaja.get(i));
//        }
//        System.out.println("datasaja  " + datasaja);

        for (TrRequestovertimedetail trRequestovertimedetail : datasaja) {
            no++;
            Recap rec = new Recap();
            rec.setNo(Integer.toString(no));
            rec.setIdEmployee(Integer.toString(trRequestovertimedetail.getRequestOvertimeID().getEmployeeNik()));
            rec.setNamaEmployee(trRequestovertimedetail.getRequestOvertimeID().getEmployeeName());
            rec.setPositionEmployee(trRequestovertimedetail.getRequestOvertimeID().getEmployeePosition());
            rec.setSoId(trRequestovertimedetail.getRequestOvertimeID().getSoId().getSoId());
            rec.setNamePIC(trRequestovertimedetail.getRequestOvertimeID().getPICName());
            rec.setPositionPIC(trRequestovertimedetail.getRequestOvertimeID().getPICPosition());
            rec.setTicketNumber(trRequestovertimedetail.getTicketNumber());
            
            rec.setLocation(trRequestovertimedetail.getRequestOvertimeID().getLocation());
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String start_date = dateFormat.format(trRequestovertimedetail.getStartDate());
            String end_date = dateFormat.format(trRequestovertimedetail.getEndDate());
            rec.setStartDate(start_date);
            rec.setEndDate(end_date);
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String start_time = timeFormat.format(trRequestovertimedetail.getStartTime());

            String end_time = timeFormat.format(trRequestovertimedetail.getEndTime());
            rec.setStartTime(start_time);
            rec.setEndTime(end_time);

            rec.setTotalHour(Float.toString(trRequestovertimedetail.getTotal()));
            rec.setDateType(trRequestovertimedetail.getDateType());
            
            String create_date = dateFormat.format(trRequestovertimedetail.getCreateDate());
            rec.setCreateDate(create_date);
            rec.setUpdateBy(trRequestovertimedetail.getUpdateBy());
            String update_date = "N/A";
            if (trRequestovertimedetail.getUpdateDate() != null) {
                update_date = dateFormat.format(trRequestovertimedetail.getUpdateDate());
            }else if (trRequestovertimedetail.getUpdateDate() == null) {
                update_date = "N/A";
            }
            rec.setUpdateDate(update_date);
            
            if (trRequestovertimedetail.getRequestOvertimeID().getSoId().getMealsTransportStatus() == null) {
                rec.setMealsTransport("N/A");
            } else {
                rec.setMealsTransport(trRequestovertimedetail.getRequestOvertimeID().getSoId().getMealsTransportStatus());
            }
            if (trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargebackStatus() == null) {
                rec.setChargerbackStatus("N/A");
            } else {
                rec.setChargerbackStatus(trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargebackStatus().getChargeBackStatus());
            }
            if (trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargerBackType() == null) {
                rec.setChargerBackType("N/A");
            } else {
                rec.setChargerBackType(trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargerBackType().getChargeBackType());
            }
//            rec.setChargerbackStatus(trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargebackStatus().getChargeBackStatus());
//            rec.setChargerBackType(trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargerBackType().getChargeBackType());

            DateFormat monthFormat = new SimpleDateFormat("MMM");
            reportPeriode = new SimpleDateFormat("MMM-yyyy");
            String month_periode = monthFormat.format(trRequestovertimedetail.getRequestOvertimeID().getPeriod());
            periodeName = reportPeriode.format(trRequestovertimedetail.getRequestOvertimeID().getPeriod());
            rec.setPeriode(month_periode);

            rec.setAssignment(trRequestovertimedetail.getTask());
            rec.setNamaCustomer(trRequestovertimedetail.getRequestOvertimeID().getSoId().getCustomerName());
            String status = "N/A";
            if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Waiting From PM")) {
                status = "Waiting Approval from PM";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Waiting From RM")) {
                status = "Waiting Approval from RM";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Waiting From MSFC")) {
                status = "Waiting Approval from MSFC";
            } else if(trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Done")) {
                status = "Done Approved by RM & MSFC";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Rejected by RM")) {
                status = "Rejected by RM";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Rejected by PM")) {
                status = "Rejected by PM";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Rejected by MSFC")) {
                status = "Rejected by MSFC";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Canceled")) {
                status = "Canceled by Employee";                
            }
            rec.setStatus(status);

//            JSONObject findNIKEmployee = serviceApi.findEmpbyid(Integer.toString(trRequestovertimedetail.getRequestOvertimeID().getEmployeeNik()));
//            String Employee_Email = findNIKEmployee.getString("Email");
//            JSONObject findLastContract = serviceApi.findStatusContract(Employee_Email);
//            String Contract_Status = findLastContract.getString("Contract_Status");
//            rec.setEmployeeContractStatus(Contract_Status);

            String fieldKosong = "";
            rec.setCell1(fieldKosong);
            rec.setCell2(fieldKosong);
            rec.setCell3(fieldKosong);
            rec.setCell4(fieldKosong);
            rec.setCell5(fieldKosong);
            rec.setCell6(fieldKosong);
            rec.setTotalRounded(fieldKosong);
            rec.setTotalFee(fieldKosong);
//            System.out.println("CHARGEBACK TYPE ==== " + trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargebackStatus().getChargeBackStatus());
//            System.out.println("recap number = " + rec.getNo() + " recap start date = " + rec.getStartDate());
//            System.out.println("recap end time  = " + rec.getEndTime() + " recap start time = " + rec.getStartTime());
//
//            System.out.println("No === " + Integer.toString(no));
//            System.out.println("Id Employee === " + Integer.toString(trRequestovertimedetail.getRequestOvertimeID().getEmployeeNik()));
//            System.out.println("Nama Employee === " + trRequestovertimedetail.getRequestOvertimeID().getEmployeeName());
//            System.out.println("Start Date === " + start_date);
//            System.out.println("End Date === " + end_date);
//            System.out.println("Total Hour === " + Float.toString(trRequestovertimedetail.getTotal()));
//            System.out.println("Date Type === " + trRequestovertimedetail.getDateType());
//            System.out.println("Meals Transport === " + trRequestovertimedetail.getRequestOvertimeID().getSoId().getMealsTransportStatus());

//            System.out.println("Chargeback Status === " + trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargebackStatus().toString());
//            System.out.println("Chargeback Type === " + trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargerBackType().toString());
//System.out.println("Periode === " + month_periode);
//            System.out.println("Assigment === " + trRequestovertimedetail.getTask());
//            System.out.println("Nama Customer === " + trRequestovertimedetail.getTask());
//            System.out.println("Contract Status === " + Contract_Status);
            listRecap.add(rec);
        }

        String FILE_NAME = null;

        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("HH;mm");
        String tgl = f.format(d);

        FILE_NAME = "Overtime_" + periode + "_" + tgl + ".xlsx";
        System.out.println("file name     =     " + FILE_NAME);

        ByteArrayInputStream in = ExcelGenerator.recapToExcel(listRecap);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + FILE_NAME + "\"");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @RequestMapping(value = "/recapOT", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> excelCustomersReport(
            @RequestParam(value = "nomorSo", required = false) String soid,
            @RequestParam(value = "periode", required = false) String periode,
            HttpServletResponse respons) throws IOException {

        List<Recap> listRecap = new ArrayList<>();
        DateFormat reportPeriode;
        String periodeName = null;
        int no = 0;
        System.out.println("--> method = excelCustomersReport / recapOT - Report maker = so_id === " + soid + " , periode === " + periode);
        List<TrRequestovertimedetail> datasaja = rdosi.showForRecap(soid, periode);
        System.out.println("datasaja  " + datasaja);

        for (TrRequestovertimedetail trRequestovertimedetail : datasaja) {
            System.out.println("ID = " + trRequestovertimedetail.getOvertimeDetail());
            no++;
            Recap rec = new Recap();
            rec.setNo(Integer.toString(no));
            rec.setIdEmployee(Integer.toString(trRequestovertimedetail.getRequestOvertimeID().getEmployeeNik()));
            rec.setNamaEmployee(trRequestovertimedetail.getRequestOvertimeID().getEmployeeName());
            rec.setPositionEmployee(trRequestovertimedetail.getRequestOvertimeID().getEmployeePosition());
            rec.setSoId(trRequestovertimedetail.getRequestOvertimeID().getSoId().getSoId());
            rec.setNamePIC(trRequestovertimedetail.getRequestOvertimeID().getPICName());
            rec.setPositionPIC(trRequestovertimedetail.getRequestOvertimeID().getPICPosition());
            rec.setTicketNumber(trRequestovertimedetail.getTicketNumber());
            
            rec.setLocation(trRequestovertimedetail.getRequestOvertimeID().getLocation());
            DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            String start_date = dateFormat.format(trRequestovertimedetail.getStartDate());
            String end_date = dateFormat.format(trRequestovertimedetail.getEndDate());
            rec.setStartDate(start_date);
            rec.setEndDate(end_date);
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String start_time = timeFormat.format(trRequestovertimedetail.getStartTime());

            String end_time = timeFormat.format(trRequestovertimedetail.getEndTime());
            rec.setStartTime(start_time);
            rec.setEndTime(end_time);

            rec.setTotalHour(Float.toString(trRequestovertimedetail.getTotal()));
            rec.setDateType(trRequestovertimedetail.getDateType());
            String create_date = dateFormat.format(trRequestovertimedetail.getCreateDate());
            rec.setCreateDate(create_date);
            rec.setUpdateBy(trRequestovertimedetail.getUpdateBy());
            String update_date = "N/A";
            if (trRequestovertimedetail.getUpdateDate() != null) {
                update_date = dateFormat.format(trRequestovertimedetail.getUpdateDate());
            }else if (trRequestovertimedetail.getUpdateDate() == null) {
                update_date = "N/A";
            }
            rec.setUpdateDate(update_date);
            
            if (trRequestovertimedetail.getRequestOvertimeID().getSoId().getMealsTransportStatus() == null) {
                rec.setMealsTransport("N/A");
            } else {
                rec.setMealsTransport(trRequestovertimedetail.getRequestOvertimeID().getSoId().getMealsTransportStatus());
            }

            if (trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargebackStatus() == null) {
                rec.setChargerbackStatus("N/A");
            } else {
                rec.setChargerbackStatus(trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargebackStatus().getChargeBackStatus());
            }

            if (trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargerBackType() == null) {
                rec.setChargerBackType("N/A");
            } else {
                rec.setChargerBackType(trRequestovertimedetail.getRequestOvertimeID().getSoId().getChargerBackType().getChargeBackType());
            }

            DateFormat monthFormat = new SimpleDateFormat("MMM");
            reportPeriode = new SimpleDateFormat("MMM-yyyy");
            String month_periode = monthFormat.format(trRequestovertimedetail.getRequestOvertimeID().getPeriod());
            periodeName = reportPeriode.format(trRequestovertimedetail.getRequestOvertimeID().getPeriod());
            rec.setPeriode(month_periode);

            rec.setAssignment(trRequestovertimedetail.getTask());
            rec.setNamaCustomer(trRequestovertimedetail.getRequestOvertimeID().getSoId().getCustomerName());
            
            String status = "N/A";
            if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Waiting From PM")) {
                status = "Waiting Approval from PM";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Waiting From RM")) {
                status = "Waiting Approval from RM";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Waiting From MSFC")) {
                status = "Waiting Approval from MSFC";
            } else if(trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Done")) {
                status = "Done Approved by RM & MSFC";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Rejected by RM")) {
                status = "Rejected by RM";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Rejected by PM")) {
                status = "Rejected by PM";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Rejected by MSFC")) {
                status = "Rejected by MSFC";
            } else if (trRequestovertimedetail.getRequestStatus().equalsIgnoreCase("Canceled")) {
                status = "Canceled by Employee";                
            }
            rec.setStatus(status);

            JSONObject findNIKEmployee = serviceApi.findEmpbyid(Integer.toString(trRequestovertimedetail.getRequestOvertimeID().getEmployeeNik()));
            String Employee_Email = findNIKEmployee.getString("Email");
            System.out.println("ini email ==== " + Employee_Email);
            JSONObject findLastContract = serviceApi.findStatusContract(Employee_Email);

            if (findLastContract != null) {
                String Contract_Status = findLastContract.getString("Contract_Status");
                rec.setEmployeeContractStatus(Contract_Status);
                System.out.println("Ini contract status ==== " + Contract_Status);
            } else {
                System.out.println("=====Contract Status NULL=====");
                rec.setEmployeeContractStatus("N/A");
            }

            String fieldKosong = " ";
            rec.setCell1(fieldKosong);
            rec.setCell2(fieldKosong);
            rec.setCell3(fieldKosong);
            rec.setCell4(fieldKosong);
            rec.setCell5(fieldKosong);
            rec.setCell6(fieldKosong);
            rec.setTotalRounded(fieldKosong);
            rec.setTotalFee(fieldKosong);

            System.out.println("recap number = " + rec.getNo() + " recap start date = " + rec.getStartDate());
            System.out.println("request ID Detil =" + trRequestovertimedetail.getOvertimeDetail() + " request ID == " + trRequestovertimedetail.getRequestOvertimeID().getRequestOvertimeID());
            System.out.println("recap end time  = " + rec.getEndTime() + " recap start time = " + rec.getStartTime());

            listRecap.add(rec);
        }

        String FILE_NAME = null;

        Date d = new Date();
        SimpleDateFormat f = new SimpleDateFormat("HH;mm");
        String tgl = f.format(d);

        TrSoovertime getSoOT = soosiImpl.showSoOt(soid);
        String namaCustomer = getSoOT.getCustomerName();

        FILE_NAME = "Overtime_" + namaCustomer + "_" + periode + "_" + tgl + ".xlsx";
        System.out.println("file name     =     " + FILE_NAME);

        ByteArrayInputStream in = ExcelGenerator.recapToExcel(listRecap);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + FILE_NAME + "\"");

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    public void sendMail(String so_id, String id_request, String email, String name, String target,
            String position, String customer, String Picname,
            String picposition, String totalhour, String Period,
            int nik) {
        System.out.println(" >>>>> MAIL Request");
        try {
            emailService.sendNotificationServiceOvertime(so_id, id_request, email, name, target, position, customer, Picname,
                    picposition, totalhour, Period, nik);
        } catch (Exception ex) {
            Logger.getLogger(OvertimeApprovalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendMailDone(String email, String name, String otID) {
        System.out.println(" >>>>> MAIL DONE");
        try {
            emailService.sendNotificationServiceOvertimeDone(email, name, otID);
        } catch (Exception ex) {
            Logger.getLogger(OvertimeApprovalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sendMailReject(String email, String name, String otID, String rmName, String remarks) {
        System.out.println(" >>>>> MAIL Reject");
        try {
            emailService.sendNotificationServiceOvertimeReject(email, name, otID, rmName, remarks);
        } catch (Exception ex) {
            Logger.getLogger(OvertimeApprovalController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
