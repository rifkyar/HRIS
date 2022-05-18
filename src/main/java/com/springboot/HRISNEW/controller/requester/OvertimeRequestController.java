/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.requester;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.entities.Approval;
import com.springboot.HRISNEW.entities.TbMStatus;
import com.springboot.HRISNEW.entities.TempSo;
import com.springboot.HRISNEW.entities.TrRequestovertime;
import com.springboot.HRISNEW.entities.TrRequestovertimedetail;
import com.springboot.HRISNEW.entities.TrSoovertime;
import com.springboot.HRISNEW.implementservices.ApprovalServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestDetailOvertimeServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestOvertimeServiceImpl;
import com.springboot.HRISNEW.implementservices.SOOvertimeServiceImpl;
import com.springboot.HRISNEW.implementservices.StatusServiceImpl;
import com.springboot.HRISNEW.implementservices.TempoSoServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author USER
 */
@Controller
public class OvertimeRequestController {

    @Autowired
    private ServiceApi serviceApi;
    
    @Autowired
    private TempoSoServiceImpl tssi;
    
    @Autowired
    private RequestDetailOvertimeServiceImpl rdosi;
    
    @Autowired
    private RequestOvertimeServiceImpl rosi;
    
    @Autowired
    private StatusServiceImpl ssi;
    
    @Autowired
    private SOOvertimeServiceImpl soosi;
    
    @Autowired
    private ApprovalServiceImpl asi;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    private static Logger log = LoggerFactory.getLogger(OvertimeRequestController.class);
    
    int nik;
    String emailAuth, nikAuth, Customer_Address = "null";
    
    @GetMapping("/requestOvertime/{nik}")
    public String requestOvertime(@PathVariable(value = "nik") int nik, Model model, HttpSession session) throws ParseException {
        
        String nama_requester, position_requester, soid_requester, typeAllocation_requester, overtimeStatus, emailActiveUser, responden;
        String customerId_requester, projectName_requester, projectManager_requester, departmentId_requester, 
                rmName_requester, actRelationManager_requester, customerName_Requester, nameUser_requester, tunjanganOT;
        String sifting = null, statusPiloting = null;
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date e = cal.getTime();
        List<TrRequestovertimedetail> lastreq = null;
        
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        String monthNow = f.format(d);
        String monthPrev = f.format(e);
        List holidayPublic = new ArrayList();
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        emailActiveUser = auth.getName();
        
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(emailActiveUser);
        JSONObject all = serviceApi.findAllocationbyNik(Integer.toString(nik));
        JSONObject so = serviceApi.findSobyId(all.getString("So_Id"));
        responden = so.getString("RM_Name");
        if(responden.equalsIgnoreCase("No_Relation_Manager")){
            responden = so.getString("Act_Relation_Manager");
        }
        JSONObject user = serviceApi.userbyid(Integer.parseInt(responden));
        
        if (joEmpl != null) {
            nama_requester = joEmpl.getString("Name");
            soid_requester = all.getString("So_Id");
//            System.out.println("SO ID : " + soid_requester);
            typeAllocation_requester = all.getString("Type");
            
            position_requester = joEmpl.getString("Position");
            if (!position_requester.equalsIgnoreCase("null")) {
                position_requester = position_requester;
            } else {
                position_requester = "N/A";
            }
            
            String sft = all.getString("shifting");
            if (sft.equalsIgnoreCase("false")) {
                sifting = "Non Shifting";
            } else {
                sifting = "Shifting";
            }
            
            overtimeStatus = so.getString("Overtime_Status");
//            System.out.println("Status Overtime : " + overtimeStatus);
//            List<TempSo> soApprove = tssi.findAllSoPiloting();
//            for (TempSo tempSo : soApprove) {
//                if (tempSo.getSoId().equalsIgnoreCase(soid_requester)) {
//                    statusPiloting = "YES";
//                    System.out.println("Status Piloting : " + statusPiloting);
//                    break;
//                } else if (!tempSo.getSoId().equalsIgnoreCase(soid_requester)) {
//                    statusPiloting = "NO";
//                    System.out.println("Status Piloting : " + statusPiloting);
//                }
//            }
            
//            if ((!overtimeStatus.equalsIgnoreCase("NO")) && (!statusPiloting.equalsIgnoreCase("NO"))) {
            if ((!overtimeStatus.equalsIgnoreCase("NO"))) {
                customerId_requester = so.getString("Customer_Id");
                System.out.println("Customer ID : " + customerId_requester);
                projectName_requester = so.getString("Project_Name");
                projectManager_requester = so.getString("Project_Manager");
                departmentId_requester = so.getString("Department_Id");
                rmName_requester = so.getString("RM_Name");
                if (!projectManager_requester.equalsIgnoreCase("null")) {
                    rmName_requester = so.getString("Project_Manager");
                }
                actRelationManager_requester = so.getString("Act_Relation_Manager");
                if (typeAllocation_requester.equalsIgnoreCase("BackUp")) {
                    List location = serviceApi.findAllCustomerByNik(Integer.toString(nik));
                    System.out.println("Customer Name 1 : " + location);
                    model.addAttribute("Customer_Name", location);
                } else {
                    JSONObject location = serviceApi.findCustomerById(customerId_requester);
                    customerName_Requester = location.getString("Customer_Name");
                    System.out.println("Customer Name 2 : " + customerName_Requester);
                    model.addAttribute("Customer_Name", customerName_Requester);
                }
                if (rmName_requester.equalsIgnoreCase("0")) {
                    rmName_requester = "1";
                }
                if (user != null) {
                    nameUser_requester = user.getString("Name");
                } else {
                    user = serviceApi.findEmpbyid(rmName_requester);
                    nameUser_requester = user.getString("Name");
                }
                lastreq = rdosi.checkRequestLastMonth(Integer.toString(nik));
                
                for (Object object : serviceApi.allHoliday()) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
                    holidayPublic.add(format.format(format.parse((String) object)));
                }
                
            } else {
                return "redirect:/home";
            }
            
            List ph = new ArrayList();
            for (TrRequestovertimedetail object : lastreq) {
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                ph.add(format.format(object.getStartDate()));
            }
            
//            System.out.println("LIST PH : " + ph);
            
            JSONObject allowance_exist = serviceApi.allowanceExist(Integer.toString(nik));
            tunjanganOT = allowance_exist.getString("check");
            if (!tunjanganOT.equalsIgnoreCase("false")) {
                String message = "Anda Memiliki Tunjangan Overtime Yang Masih Berlaku";
                model.addAttribute("message", message);
            }
            
            session.setAttribute("listlastreq", ph);
            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
            model.addAttribute("nama_requester", nama_requester);
            model.addAttribute("position", position_requester);
            model.addAttribute("RM_Name", nameUser_requester);
            model.addAttribute("Project_Manager", projectManager_requester);
            model.addAttribute("sifting", sifting);
            model.addAttribute("monthNow", monthNow);
            model.addAttribute("monthPrev", monthPrev);
            model.addAttribute("holidayPublic", holidayPublic);
            
            return "requester/overtime/overtimeRequest";
            
        } else {
            System.out.println("Method -> formRequestOvertime : Forbidden Access - Kill Session | value = " + joEmpl + " | emailActiveUser = " + emailActiveUser);
            SecurityContextHolder.clearContext();
            return "redirect:/login?logout";
        }
    } 
    
    @Transactional
    @RequestMapping(value = "/requestOt/{id}", method = RequestMethod.POST)
    public String saveRequestOvertime(@PathVariable Integer id,
            @RequestParam(value = "ticket[]", required = false) String[] ticketNumber,
            @RequestParam(value = "location[]", required = false) String[] lokasi,
            @RequestParam(value = "picName", required = false) String PICname,
            @RequestParam(value = "picJabatan", required = false) String PICpos,
            @RequestParam(value = "total", required = false) String totalOvertime,
            @RequestParam(value = "duty[]", required = false) String[] task,
            @RequestParam(value = "stardate[]", required = false) String[] startDate,
            @RequestParam(value = "typeday[]", required = false) String[] typeDate,
            @RequestParam(value = "starttime[]", required = false) String[] startTime,
            @RequestParam(value = "enddate[]", required = false) String[] endDate,
            @RequestParam(value = "endtime[]", required = false) String[] endTime,
            @RequestParam(value = "hourstime[]", required = false) String[] totalPerRow,
            @RequestParam(value = "periode", required = false) String periode,
            @RequestParam(value = "customerName", required = false) String Customer_Name
    ) throws ParseException{
        String Project_Manager_requester, typeAllocation_requester, sifting;
        String nikActiveUser, soid_requester, nama_requester, position_requester;
        String url = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailActiveUser = auth.getName();
        JSONObject jobjUser = serviceApi.findEmployeeByEmail(emailActiveUser);
        if (jobjUser != null) {
            int s = jobjUser.getInt("Employee_Nik");
            nikActiveUser = Integer.toString(s);
            nama_requester = jobjUser.getString("Name");
            position_requester = jobjUser.getString("Position");
            if (!position_requester.equalsIgnoreCase("null")) {
                position_requester = position_requester;
            } else {
                position_requester = "N/A";
            }

        } else {
            System.out.println("--> Failed to Auth Cek | method = saveRequestOvertime | method = Auth ");
            return "redirect:/login?logout";
        }
        
        JSONObject all = serviceApi.findAllocationbyNik(nikActiveUser);
        soid_requester = all.getString("So_Id");
        typeAllocation_requester = all.getString("Type");
        String sft = all.getString("shifting");
        if (sft.equalsIgnoreCase("false")) {
            sifting = "Non Shifting";
        } else {
            sifting = "Shifting";
        }
        
        JSONObject so = serviceApi.findSobyId(soid_requester);
        String overtimeStatus = so.getString("Overtime_Status");
        Project_Manager_requester = so.getString("Project_Manager");
        
        String RM_Name_requester = so.getString("RM_Name");
        if (RM_Name_requester.equalsIgnoreCase("No_Relation_Manager")) {
            RM_Name_requester = so.getString("Act_Relation_Manager");
        }else if (!Project_Manager_requester.equalsIgnoreCase("null")) {
            RM_Name_requester = so.getString("Project_Manager");
        }
        
        String location = "";
        String st_crnt_app, st_data_cust;
        boolean st_pm;
        
        String EmplName = null, EmplPosition = null, emailRM = null, namaRM = null;
        if (id == Integer.parseInt(nikActiveUser)) {
            int nextApproval;
            
            //untuk ganti format tanggal
            SimpleDateFormat fromUser = new SimpleDateFormat("dd-mm-yyyy");
            SimpleDateFormat PeriodefromUser = new SimpleDateFormat("MMMM yyyy", Locale.US);
            SimpleDateFormat myFormat = new SimpleDateFormat("dd-mm-yyyy");
            
            //get total overtime backend
            float totalOvertime1 = 0;

            for (int i = 0; i < totalPerRow.length; i++) {
                totalOvertime1 = totalOvertime1 + Float.parseFloat(totalPerRow[i]);
//                System.out.println("------------ totalOvertime1 = " + totalOvertime1);
            }

            if (Project_Manager_requester.equalsIgnoreCase("null")) {
                TbMStatus status1 = ssi.findOnebyId("6");
                nextApproval = Integer.parseInt(RM_Name_requester);
                st_crnt_app = status1.getValue();
                st_pm = true;
            } else {
                TbMStatus status2 = ssi.findOnebyId("5");
                nextApproval = Integer.parseInt(RM_Name_requester);
                st_crnt_app = status2.getValue();
                st_pm = false;
            }
            
            TbMStatus status3 = ssi.findOnebyId("3");
            st_data_cust = status3.getValue();
            
            Date tempPeriode0 = PeriodefromUser.parse(periode);
//            System.out.println(" *********** Periode After Format 0 = " + tempPeriode0);
            Calendar cal = Calendar.getInstance();
            cal.setTime(tempPeriode0);
            String formatedDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
            
            JSONObject findNIKEmployee = serviceApi.findEmpbyid(Integer.toString(Integer.parseInt(nikActiveUser)));
            String Employee_Email = findNIKEmployee.getString("Email");
            JSONObject findLastContract = serviceApi.kontrakOvertime(Employee_Email, formatedDate);
            String Contract_Status;
            if (findLastContract != null) {
                Contract_Status = findLastContract.getString("Contract_Status");
            } else {
                Contract_Status = "N/A";
            }
            
            TrSoovertime soidnew = new TrSoovertime(soid_requester);
//            System.out.println("soid new " + soidnew);
            TrSoovertime searchSoid = soosi.showSoOt(soid_requester);
            
            String getCustomerName = searchSoid.getCustomerName();
            String getMealsTransportStatus = searchSoid.getMealsTransportStatus();
            String getSoId = searchSoid.getSoId();
            
            
            if (totalOvertime1 < 1) {
                System.out.println("Jumlah Overtime Kurang dari 1 Jam");
                url = "redirect:/home";

            } else {
                TrRequestovertime saveRequester = new TrRequestovertime(Integer.parseInt(nikActiveUser), 
                        nama_requester, position_requester, Contract_Status, tempPeriode0, PICname, 
                        PICpos, totalOvertime1, location, nextApproval, st_data_cust, nikActiveUser, 
                        timeAdd6Hours(), searchSoid);
                rosi.saveRequestOT(saveRequester);
                int noReqOT_saveRequester = saveRequester.getRequestOvertimeID();
                String so_id = saveRequester.getSoId().getSoId();
                
                //Save OT array
                for (int i = 0; i < totalPerRow.length; i++) {
                    Date startDateOT = new SimpleDateFormat("dd-MM-yyyy").parse(startDate[i]);
                    
                    SimpleDateFormat format12Hours = new SimpleDateFormat("hh : mm aaa");
                    SimpleDateFormat format24Hours = new SimpleDateFormat("HH:mm");
                    String convertStartTime = format24Hours.format(format12Hours.parse(startTime[i]));
//                    System.out.println("Start Time Convert Format = " + convertStartTime);
                    String convertEndTime = format24Hours.format(format12Hours.parse(endTime[i]));
                    
                    Date stTime = new SimpleDateFormat("HH:mm").parse(convertStartTime);
//                    System.out.println("Start Time Parsing = " + stTime);
                    Date edTime = new SimpleDateFormat("HH:mm").parse(convertEndTime);

                    Date endDateOT = new SimpleDateFormat("dd-MM-yyyy").parse(endDate[i]);
                    String tempDataType = typeDate[i];
                    Float tempTotalPerRow = Float.parseFloat(totalPerRow[i]);
                    String tempTask = task[i];
                    String tiket = "";
                    if (ticketNumber[i].equalsIgnoreCase("-")) {
                        tiket = "";
                    } else {
                        tiket = ticketNumber[i];
                    }
//                    System.out.println("TIKET NUMBER ARRAY : " + tiket);
                    String lokasinya = lokasi[i];
                    
//                    System.out.println("---------------- Data Save ke = " + i + "---------------------");
//                    System.out.println("startDateOT = " + startDateOT);
//                    System.out.println("stTime = " + stTime);
//                    System.out.println("edTime = " + edTime);
//                    System.out.println("endDateOT = " + endDateOT);
//                    System.out.println("tempDataType = " + tempDataType);
//                    System.out.println("tempTotalPerRow = " + tempTotalPerRow);
//                    System.out.println("tempTask = " + tempTask);
//                    System.out.println("tiket = " + tiket);
//                    System.out.println("lokasinya = " + lokasinya);
//                    System.out.println("Status = " + st_crnt_app);
//                    System.out.println("st_pmOrLeader = " + st_pm);
//                    System.out.println("----------------------------------------------------------");
                    
                    TrRequestovertimedetail tr = new TrRequestovertimedetail(tiket, startDateOT, tempDataType, stTime,
                            endDateOT, edTime, tempTotalPerRow, tempTask, st_pm, st_crnt_app,
                            nikActiveUser, timeAdd6Hours(), new TrRequestovertime(noReqOT_saveRequester), lokasinya);
                    rdosi.saveOvertimeDetail(tr);

                    TbMStatus status = ssi.findOnebyId("10");
                    String app_status = status.getValue();
                    Approval approve = new Approval(Integer.parseInt(nikActiveUser), app_status, timeAdd6Hours(), "-", new TrRequestovertimedetail(tr.getOvertimeDetail()));
                    asi.save(approve);
                    
//                    System.out.println("Succsess Save = " + tr.getOvertimeDetail());

                    JSONObject user = serviceApi.userbyid(nextApproval);
                    JSONObject empl = serviceApi.findEmpbyid(nikActiveUser);
                    EmplName = empl.getString("Name");
                    EmplPosition = empl.getString("Position");

                    if (user != null) {
                        emailRM = user.getString("Email");
                        namaRM = user.getString("Name");
                    } else {
                        user = serviceApi.findEmpbyid(Integer.toString(nextApproval));
                        emailRM = user.getString("Email");
                        namaRM = user.getString("Name");
                    }

                    String id_request = Integer.toString(noReqOT_saveRequester);
                    sendMail(so_id, id_request, emailRM, namaRM, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOvertime, periode, Integer.parseInt(nikActiveUser));
//                    sendMail("gabrielbintangtimur@gmail.com", namaRM, EmplName, EmplPosition, getCustomerName, PICname, PICpos, totalOvertime, periode, nik);
                    url = "redirect:/home";
                }
            }
        } else {
            System.out.println("Method -> saveRequestOvertime : Forbidden Access - Kill Session | value = " + jobjUser + " | emailActiveUser = " + emailActiveUser);
            SecurityContextHolder.clearContext();
            url = "redirect:/loginPage?logout";
        }
        return url;
    }
    
    @GetMapping("/formResubmitOvertime/{id}/{appId}")
    public String formResubmitOvertime(@PathVariable Integer id, @PathVariable Integer appId,
            Model model, HttpSession session){
        String nikActiveUser, typeAllocation_requester, soid_requester, nama_requester, position_requester;
        String sifting;
        String customerId_requester, ProjectName_requester, Project_Manager_requester, Department_Id_requester;
        String RM_Name_requester, Act_Relation_Manager_requester, Customer_Name_requester, name_user_requester;
        List<TrRequestovertimedetail> lastreq = null;
        Auth();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailActiveUser = auth.getName();
        
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        
        JSONObject jobjUserResubmit = serviceApi.findEmployeeByEmail(emailActiveUser);
        
        if (jobjUserResubmit != null) {
            int s = jobjUserResubmit.getInt("Employee_Nik");
            nikActiveUser = Integer.toString(s);
            nama_requester = jobjUserResubmit.getString("Name");
            position_requester = jobjUserResubmit.getString("Position");
            if (!position_requester.equalsIgnoreCase("null")) {
                position_requester = position_requester;
            } else {
                position_requester = "N/A";
            }
        } else {
            System.out.println("Method -> formResubmitOvertime : Forbidden Access - Kill Session | value = " + jobjUserResubmit + " | emailActiveUser = " + emailActiveUser);
            SecurityContextHolder.clearContext();
            return "redirect:/login?logout";
        }
        
        JSONObject all = serviceApi.findAllocationbyNik(nikActiveUser);
        soid_requester = all.getString("So_Id");
        String sft = all.getString("shifting");
        if (sft.equalsIgnoreCase("false")) {
            sifting = "Non Shifting";
        } else {
            sifting = "Shifting";
        }
        typeAllocation_requester = all.getString("Type");
        
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date e = cal.getTime();
        
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        String monthNow = f.format(d);
        String monthPrev = f.format(e);
        
        String pic_name = null, pic_post = null, address = null;
        int requestOTID = 0;
        Date periode = null;
        Approval app = asi.findbyid(appId);
        if (app.getRequestovertimedetailId().getRequestOvertimeID().getEmployeeNik().equals(id)) {
            requestOTID = app.getRequestovertimedetailId().getRequestOvertimeID().getRequestOvertimeID();
            pic_name = app.getRequestovertimedetailId().getRequestOvertimeID().getPICName();
            pic_post = app.getRequestovertimedetailId().getRequestOvertimeID().getPICPosition();
            address = app.getRequestovertimedetailId().getRequestOvertimeID().getLocation();
            periode = app.getRequestovertimedetailId().getRequestOvertimeID().getPeriod();
        } else if (app == null) {
            System.out.println("--> Failed to find app || method = resubmitRequestOvertime / id_approval = incorrect");
        }
        
        LocalDate date = LocalDate.now();
        Month now = date.getMonth();
        LocalDate earlier = date.minusMonths(1);
        Month prev = earlier.getMonth();
        
        int nikny;

        JSONObject jobjUser = serviceApi.findEmpbyid(Integer.toString(id));
        nikny = jobjUser.getInt("Employee_Nik");
        
        JSONObject so = serviceApi.findSobyId(soid_requester);
        String overtimeStatus = so.getString("Overtime_Status");
        System.out.println("NIK  =  " + nikActiveUser + " ,  NAMA  =  " + nama_requester + " ,  Type  =  " + typeAllocation_requester + " ,  Overtime Status = " + overtimeStatus);
        
        if (!overtimeStatus.equalsIgnoreCase("NO")) {
            customerId_requester = so.getString("Customer_Id");
            ProjectName_requester = so.getString("Project_Name");
            Project_Manager_requester = null;

            Department_Id_requester = so.getString("Department_Id");
            RM_Name_requester = so.getString("RM_Name");
            Act_Relation_Manager_requester = so.getString("Act_Relation_Manager");

//            System.out.println("SO_ID = " + soid_requester + ",  Customer_Id = " + customerId_requester + ","
//                    + "  Project Name = " + ProjectName_requester + ",  Department Id = " + Department_Id_requester);
            
            if (typeAllocation_requester.equalsIgnoreCase("BackUp")) {
                List lction = serviceApi.findAllCustomerByNik(nikActiveUser);
                model.addAttribute("Customer_Name", lction);
            } else {
                JSONObject lctn = serviceApi.findCustomerById(customerId_requester);
                Customer_Name_requester = lctn.getString("Customer_Name");
                model.addAttribute("Customer_Name", Customer_Name_requester);
            }
            if (RM_Name_requester.equalsIgnoreCase("0")) {
                RM_Name_requester = "1";
            }
            
            JSONObject user = serviceApi.userbyid(Integer.parseInt(RM_Name_requester));
            
             if (user != null) {
                name_user_requester = user.getString("Name");
            } else {
                user = serviceApi.findEmpbyid(RM_Name_requester);
                name_user_requester = user.getString("Name");
            }
            lastreq = rdosi.checkRequestLastMonth(nikActiveUser);
//            if (!lastreq.equals(null)) {
////                System.out.println("last Request Is Empty");
//                lastreq = null;
//            }
        } else {
                return "redirect:/";
        }
        
        List<Month> month = new ArrayList<Month>();
        month.add(now);
//        System.out.println("BULAN SEKARANG : " + now);
        month.add(prev);
//        System.out.println("BULAN SEBELUMNYA : " + prev);

        int p = 4;

        List ph = new ArrayList();
        for (TrRequestovertimedetail object : lastreq) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            ph.add(format.format(object.getStartDate()));
//            System.out.println("START DATE : " + ph);
        }
        
        JSONObject allowance_exist = serviceApi.allowanceExist(nikActiveUser);
        String tunjanganOT = allowance_exist.getString("check");
//        System.out.println(" tunjangan aktif ?   =    " + tunjanganOT);
        
        if (!tunjanganOT.equalsIgnoreCase("false")) {
//                attribute.addFlashAttribute("message", "Anda Memiliki Tunjangan Overtime Yang Masih Berlaku");
            String m = "Anda Memiliki Tunjangan Overtime Yang Masih Berlaku";
            model.addAttribute("m", m);
        }
        
        session.setAttribute("listlastreq", ph);
        model.addAttribute("approval_id", appId);
        model.addAttribute("requestOTID", requestOTID);
        model.addAttribute("pic_name", pic_name);
        model.addAttribute("pic_post", pic_post);
        model.addAttribute("address", address);
        model.addAttribute("sifting", sifting);
        model.addAttribute("p", p);
        model.addAttribute("nama", nama_requester);
        model.addAttribute("position", position_requester);
        model.addAttribute("Customer_Address", Customer_Address);
        model.addAttribute("RM_Name", name_user_requester);
        model.addAttribute("Project_Manager", Project_Manager_requester);
        model.addAttribute("monthNow", monthNow);
        model.addAttribute("monthPrev", monthPrev);
        model.addAttribute("nik", jobjUserResubmit.getInt("Employee_Nik"));
        model.addAttribute("nama_requester", nama_requester);
        return "requester/overtime/resubmitOvertimeRequest"; 
    }
    
    @RequestMapping(value = "/resubmitOt/{id}", method = RequestMethod.POST)
    public String saveResubmitOvertime(@PathVariable Integer id,
            @RequestParam(value = "requestOTID", required = false) String requestOTID,
            @RequestParam(value = "approval_id", required = false) String approval_id,
            @RequestParam(value = "ticket[]", required = false) String[] ticketNumber,
            @RequestParam(value = "location[]", required = false) String[] lokasi,
            @RequestParam(value = "picName", required = false) String PICname,
            @RequestParam(value = "picJabatan", required = false) String PICpos,
            @RequestParam(value = "total", required = false) String totalOvertime,
            @RequestParam(value = "duty[]", required = false) String[] task,
            @RequestParam(value = "stardate[]", required = false) String[] startDate,
            @RequestParam(value = "typeday[]", required = false) String[] typeDate,
            @RequestParam(value = "starttime[]", required = false) String[] startTime,
            @RequestParam(value = "enddate[]", required = false) String[] endDate,
            @RequestParam(value = "endtime[]", required = false) String[] endTime,
            @RequestParam(value = "hourstime[]", required = false) String[] totalPerRow,
            @RequestParam(value = "periode", required = false) String periode,
            @RequestParam(value = "customerName", required = false) String Customer_Name) throws ParseException{
        String Project_Manager_requester, typeAllocation_requester;
        String nikActiveUser, soid_requester, nama_requester, position_requester;
        int nik_empl;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailActiveUser = auth.getName();
        JSONObject jobjUser = serviceApi.findEmployeeByEmail(emailActiveUser);
        if (jobjUser != null) {
            nik_empl = jobjUser.getInt("Employee_Nik");
            nikActiveUser = Integer.toString(nik_empl);
            nama_requester = jobjUser.getString("Name");
            position_requester = jobjUser.getString("Position");
            if (!position_requester.equalsIgnoreCase("null")) {
                position_requester = position_requester;
            } else {
                position_requester = "N/A";
            }
        } else {
            System.out.println("Method -> saveResubmitOvertime : Forbidden Access - Kill Session | value = " + jobjUser + " | emailActiveUser = " + emailActiveUser);
            SecurityContextHolder.clearContext();
            return "redirect:/login?logout";
        }
        
        JSONObject all = serviceApi.findAllocationbyNik(nikActiveUser);
        soid_requester = all.getString("So_Id");
        typeAllocation_requester = all.getString("Type");
        
        JSONObject so = serviceApi.findSobyId(soid_requester);
        String overtimeStatus = so.getString("Overtime_Status");
        String RM_Name_requester = so.getString("RM_Name");

        Auth();
        Project_Manager_requester = "null";
        String url, st_crnt_app, st_data_cust;
        boolean st_pm;
//        System.out.println("approval_id " + approval_id);
        
        if (id == nik_empl) {
            int nextApproval;
            
            //untuk ganti format tanggal
            SimpleDateFormat fromUser = new SimpleDateFormat("dd-mm-yyyy");
            SimpleDateFormat myFormat = new SimpleDateFormat("dd-mm-yyyy");
            
            //get total overtime backend
            float totalOvertime1 = 0;
            
            for (int i = 0; i < totalPerRow.length; i++) {
                totalOvertime1 = totalOvertime1 + Float.parseFloat(totalPerRow[i]);
//                System.out.println("------------ totalOvertime1 = " + totalOvertime1);
            }
            
            if (Project_Manager_requester.equalsIgnoreCase("null")) {
                TbMStatus status1 = ssi.findOnebyId("6");
                nextApproval = Integer.parseInt(RM_Name_requester);
                st_crnt_app = status1.getValue();
                st_pm = true;
            } else {
                TbMStatus status2 = ssi.findOnebyId("5");
                nextApproval = Integer.parseInt(RM_Name_requester);
                st_crnt_app = status2.getValue();
                st_pm = false;
            }
            
            TbMStatus status3 = ssi.findOnebyId("3");
            st_data_cust = status3.getValue();
            TrRequestovertime idThisOT = rosi.showRequesterOTbyid(requestOTID);
            
            //Save OT array
            for (int i = 0; i < totalPerRow.length; i++) {
                
                    Date startDateOT = new SimpleDateFormat("dd-MM-yyyy").parse(startDate[i]);
                    SimpleDateFormat format12Hours = new SimpleDateFormat("hh : mm aaa");
                    SimpleDateFormat format24Hours = new SimpleDateFormat("HH:mm");
                    String convertStartTime = format24Hours.format(format12Hours.parse(startTime[i]));
                    String convertEndTime = format24Hours.format(format12Hours.parse(endTime[i]));
                    Date stTime = new SimpleDateFormat("HH:mm").parse(convertStartTime);
                    Date edTime = new SimpleDateFormat("HH:mm").parse(convertEndTime);
                    Date endDateOT = new SimpleDateFormat("dd-MM-yyyy").parse(endDate[i]);
                    String tempDataType = typeDate[i];
                    Float tempTotalPerRow = Float.parseFloat(totalPerRow[i]);
                    String tempTask = task[i];
                    String lokasinya = lokasi[i];
                    String tiket = ticketNumber[i];
                    
//                    System.out.println("---------------- Save Resubmit ke = " + i + "---------------------");
//                    System.out.println("approval_id = " + approval_id);
//                    System.out.println("requestOTID = " + requestOTID);
//                    System.out.println("idThisOT = " + idThisOT);
//                    System.out.println("startDateOT = " + startDateOT);
//                    System.out.println("stTime = " + stTime);
//                    System.out.println("edTime = " + edTime);
//                    System.out.println("endDateOT = " + endDateOT);
//                    System.out.println("tempDataType = " + tempDataType);
//                    System.out.println("tempTotalPerRow = " + tempTotalPerRow);
//                    System.out.println("tempTask = " + tempTask);
//                    System.out.println("tiket = " + tiket);
//                    System.out.println("Status = " + st_crnt_app);
//                    System.out.println("st_pmOrLeader = " + st_pm);
//                    System.out.println("----------------------------------------------------------");
                    
                    Approval searchApproval = asi.findbyid(Integer.parseInt(approval_id));
                    TrRequestovertimedetail searchDetilId = searchApproval.getRequestovertimedetailId();
                    int thisRequestovertimedetailId = searchDetilId.getOvertimeDetail();
                    
                    TrRequestovertime thisRequestovertime = searchDetilId.getRequestOvertimeID();
                    thisRequestovertime.setRequestStatus(st_data_cust);
                    rosi.saveRequestOT(thisRequestovertime);
                    
                    TrRequestovertimedetail uppdateRequestOTDetil = rdosi.findRequestOTDetailbyID(Integer.toString(thisRequestovertimedetailId));
//                    System.out.println("uppdateRequestOTDetil : " + uppdateRequestOTDetil);
                    uppdateRequestOTDetil.setStartDate(startDateOT);
                    uppdateRequestOTDetil.setDateType(tempDataType);
                    uppdateRequestOTDetil.setStartTime(stTime);
                    uppdateRequestOTDetil.setEndDate(endDateOT);
                    uppdateRequestOTDetil.setEndTime(edTime);
                    uppdateRequestOTDetil.setTotal(tempTotalPerRow);
                    uppdateRequestOTDetil.setTask(tempTask);
                    uppdateRequestOTDetil.setLokasi(lokasinya);
                    uppdateRequestOTDetil.setPMorLeader(st_pm);
                    uppdateRequestOTDetil.setRequestStatus(st_crnt_app);
                    uppdateRequestOTDetil.setUpdateBy(Integer.toString(nik));
                    uppdateRequestOTDetil.setUpdateDate(timeAdd6Hours());
                    uppdateRequestOTDetil.setRm(null);
                    uppdateRequestOTDetil.setMsfc(null);
                    rdosi.updateOvertimeDetail(uppdateRequestOTDetil);
                    
                    TbMStatus status = ssi.findOnebyId("15");
                    String app_status_resubmit = status.getValue();
                    
                    Approval updateApp = asi.findbyid(Integer.parseInt(approval_id));
                    updateApp.setUserId(Integer.parseInt(nikActiveUser));
                    updateApp.setModifiedDate(timeAdd6Hours());
                    updateApp.setStatus(app_status_resubmit);
                    updateApp.setRequestovertimedetailId(new TrRequestovertimedetail(uppdateRequestOTDetil.getOvertimeDetail()));
                    asi.updateApproval(updateApp);
                    
//                    System.out.println("Succsess Save = " + uppdateRequestOTDetil.getOvertimeDetail() + " with approval ID = " + updateApp.getId());
//                    System.out.println("----------------------------------------------------------");
            }
            url = "redirect:/historyOvertime/" + id;
        } else {
            System.out.println("Method -> saveResubmitOvertime : Forbidden Access - Kill Session | value = " + jobjUser + " | emailActiveUser = " + emailActiveUser);
            SecurityContextHolder.clearContext();
            url = "redirect:/login?logout";
        }
        return url;
    }

    @RequestMapping(value = "/historyOvertime/{id}", method = RequestMethod.GET)
    public String historyOvertime(@PathVariable Integer id, Model model) {
        String nikActiveUser, soid_requester, nama_requester, position_requester;
        Auth();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailActiveUser = auth.getName();
        
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        
        JSONObject jobjUser = serviceApi.findEmployeeByEmail(emailActiveUser);
        int s;
        if (jobjUser != null) {
            s = jobjUser.getInt("Employee_Nik");
            nikActiveUser = Integer.toString(s);
            nama_requester = jobjUser.getString("Name");
            position_requester = jobjUser.getString("Position");
            if (!position_requester.equalsIgnoreCase("null")) {
                position_requester = position_requester;
            } else {
                position_requester = "N/A";
            }

        } else {
            System.out.println("Method -> historyRequestOvertime : Forbidden Access - Kill Session | value = " + jobjUser + " | emailActiveUser = " + emailActiveUser);
            SecurityContextHolder.clearContext();
            return "redirect:/login?logout";
        }
        
        JSONObject findNIKEmployee = serviceApi.findEmpbyid(nikActiveUser);
        String Employee_Email = findNIKEmployee.getString("Email");
        JSONObject findLastContract = serviceApi.lastKontrak(Employee_Email);
        String Contract_Status = findLastContract.getString("Contract_Status");
//
        model.addAttribute("nik", nikActiveUser);
        model.addAttribute("Contract_Status", Contract_Status);
        model.addAttribute("listHistory", asi.findAllAppGroup(Integer.parseInt(nikActiveUser)));
        return "requester/overtime/historyOvertime";
    }
    
    @RequestMapping(value = "/cancelRequestOvertime/{id}", method = RequestMethod.GET)
    public String cancelRequestOvertime(@PathVariable String id, Model model) {
        String nikActiveUser, soid_requester, nama_requester, position_requester;
        Auth();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailActiveUser = auth.getName();
        JSONObject jobjUser = serviceApi.findEmployeeByEmail(emailActiveUser);
        int s;
        if (jobjUser != null) {
            s = jobjUser.getInt("Employee_Nik");
            nikActiveUser = Integer.toString(s);
            nama_requester = jobjUser.getString("Name");
            position_requester = jobjUser.getString("Position");
            if (!position_requester.equalsIgnoreCase("null")) {
                position_requester = position_requester;
            } else {
                position_requester = "N/A";
            }

        } else {
            System.out.println("572 Method -> cancelRequestOvertime : Forbidden Access - Kill Session | value = " + jobjUser + " | emailActiveUser = " + emailActiveUser);
            SecurityContextHolder.clearContext();
            return "redirect:/login?logout";
        }
        
        TbMStatus statusCancel = ssi.findOnebyId("14");
        String st_cancel = statusCancel.getValue();
        Approval approval = asi.findbyid(Integer.parseInt(id));
        Integer id_approval = approval.getId();
        System.out.println("nik " + approval.getRequestovertimedetailId().getRequestOvertimeID().getEmployeeNik() + " nik auth = " + nikActiveUser);
        if (!approval.getRequestovertimedetailId().getRequestOvertimeID().getEmployeeNik().equals(nikActiveUser)) {
            approval.setStatus(st_cancel);
            TrRequestovertimedetail reqDetilId = approval.getRequestovertimedetailId();
            reqDetilId.setPMorLeader(Boolean.FALSE);
            reqDetilId.setRm(Boolean.FALSE);
            reqDetilId.setMsfc(Boolean.FALSE);
            reqDetilId.setRequestStatus(st_cancel);
            reqDetilId.setUpdateDate(timeAdd6Hours());
            reqDetilId.setUpdateBy(nikActiveUser);
            rdosi.updateOvertimeDetail(reqDetilId);
            asi.updateApproval(approval);
            System.out.println("--> Success cancel || method = cancelRequestOvertime / id_approval = " + id_approval + " / st_cancel = " + st_cancel);
        } else if (approval == null) {
            System.out.println("--> Failed to cancel || method = cancelRequestOvertime / id_approval = incorrect");
        }
        return "requester/overtime/historyOvertime";
    }
    
    @RequestMapping(value = "/approvalHistoryReq/{id}", method = RequestMethod.GET)
    public String viewApprovalHistoryReq(@PathVariable("id") Integer id, Model model, HttpServletResponse response) throws IOException {
        String nama = null;
        int c = 0;
        List<String> namaUser = new ArrayList<String>();
        Auth();
//        System.out.println("approvalHistoryReq /id == " + id);
        Iterable<Approval> ap = asi.findAppByReqOTDetailId(Integer.toString(id));
        for (Approval approval : ap) {
//            System.out.println("app id = " + approval.getId() + " st app = " + approval.getStatus() + " approver = " + approval.getUserId());
            JSONObject jobjUser = serviceApi.userbyid(approval.getUserId());
            if (jobjUser != null) {
                nama = jobjUser.getString("Name");
                namaUser.add(c++, nama);
            } else {
                JSONObject employ = serviceApi.findEmpbyid(Integer.toString(approval.getUserId()));
//                System.out.println("employ " + employ);
                if (employ != null) {
                    nama = employ.getString("Name");
                    namaUser.add(c++, nama);
                } else {
                    nama = "N/A";
                }
            }
        }
        model.addAttribute("appHistory", asi.findAppByReqOTDetailId(Integer.toString(id)));
        model.addAttribute("namaUser", namaUser);
        return "requester/overtime/historyOvertime :: detailHistoryApproval";
    }
    
    @RequestMapping(value = "/reportRequester", method = RequestMethod.POST)
    public @ResponseBody 
    void reportRequester(
            @RequestParam("periode") String periode,
            @RequestParam("nik") String emplnik,
            HttpServletResponse response) throws SQLException, JRException, IOException, ParseException{
        String picname = null, picpos = null;
        String monthPeriode = null;
        String periodeName = null;
        String custName = null;
        String month_periode = null;
        DateFormat reportPeriode;
        DateFormat monthFormat = new SimpleDateFormat("MMMM");
        reportPeriode = new SimpleDateFormat("MMM-yyyy");
        
        List<TrRequestovertimedetail> dataRecap = rdosi.showBynikAndPeriode(emplnik, periode);
        if (dataRecap.isEmpty()) {
            JSONObject all = serviceApi.findAllocationbyNik(emplnik);
            JSONObject so = serviceApi.findSobyId(all.getString("So_Id"));
            custName = so.getString("customername");
            picname = "N/A";
            picpos = "N/A";
            SimpleDateFormat formatMonth =  new SimpleDateFormat("yyyy-MM");
            Date bulan = formatMonth.parse(periode);
            monthPeriode = monthFormat.format(bulan);
        } else {
            for (TrRequestovertimedetail dataTemp : dataRecap) {
                picname = dataTemp.getRequestOvertimeID().getPICName();
                picpos = dataTemp.getRequestOvertimeID().getPICPosition();
                monthPeriode = monthFormat.format(dataTemp.getRequestOvertimeID().getPeriod());
                periodeName = reportPeriode.format(dataTemp.getRequestOvertimeID().getPeriod());
                custName = dataTemp.getRequestOvertimeID().getSoId().getCustomerName();
            }
        }
        
        month_periode = periode + "%";
        
//        System.out.println("montPeriode = " + monthPeriode);
//        System.out.println("picname = " + picname);
//        System.out.println("picpos = " + picpos);
//        System.out.println("month_periode = " + month_periode);
//        System.out.println("nik = " + emplnik);
//        System.out.println("Customer Name = " + custName);
        
        JSONObject all = serviceApi.findAllocationbyNik(emplnik);
        String sifting;
        String sft = all.getString("shifting");
        if (sft.equalsIgnoreCase("false")) {
            sifting = "Non Sifting";
        } else {
            sifting = "Sifting";
        }
        
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        InputStream jasperStream = this.getClass().getResourceAsStream("/templates/report/ReportEmployee.jrxml");
        JasperDesign jd = JRXmlLoader.load(jasperStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jd);
        Map<String, Object> params = new HashMap();
        
        params.put("custName", custName);
        params.put("monthPeriode", monthPeriode);
        params.put("picname", picname);
        params.put("picpos", picpos);
        params.put("sifting", sifting);

        params.put("nik", emplnik);
        params.put("periode", month_periode);

        params.put(JRParameter.IS_IGNORE_PAGINATION, true);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        response.setContentType("application/x-xlsx");
        response.setHeader("Content-Disposition", " inline; filename=Monthly Overtime Report - Periode = " + monthPeriode + ".xlsx");
        final OutputStream outStream = response.getOutputStream();
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(false);

        configuration.setCollapseRowSpan(false);
        configuration.setWhitePageBackground(false);

        configuration.setShowGridLines(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
    
    public String Auth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        emailAuth = auth.getName();
        JSONObject jobjUser = serviceApi.findEmployeeByEmail(emailAuth);
        if (jobjUser != null) {
            int s = jobjUser.getInt("Employee_Nik");
            nikAuth = Integer.toString(s);
        } else {
            nikAuth = null;
            emailAuth = null;
            System.out.println("--> Failed to Auth Cek || method = Auth ");
            return "redirect:/login?logout";
        }
        System.out.println("--> method = Auth Cek / id = " + nikAuth + " email = " + emailAuth);
        return nikAuth;
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
    
    public void sendMail(String so_id, String id_request, String email, String name, String target,
            String position, String customer, String Picname,
            String picposition, String totalhour, String Period,
            int nik) {
        try {
            emailService.sendNotificationServiceOvertime(so_id, id_request, email, name, target, position, customer, Picname,
                    picposition, totalhour, Period, nik);
        } catch (Exception e) {
            log.info("Error sending email = " + e.getMessage());
        }
    }
}
