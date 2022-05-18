/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.entities.RequestDetail;
import static com.springboot.HRISNEW.controller.requester.LeaveRequestController.userId;
import com.springboot.HRISNEW.entities.Division;
import com.springboot.HRISNEW.entities.FaqAnswer;
import com.springboot.HRISNEW.entities.FaqQuestion;
import com.springboot.HRISNEW.entities.RequesterInformation;
import com.springboot.HRISNEW.entities.TbTrAnnouncement;
import com.springboot.HRISNEW.implementservices.AnnouncementServiceImpl;
import com.springboot.HRISNEW.implementservices.AnswerServiceImpl;
import com.springboot.HRISNEW.implementservices.QuestionServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestDetailServiceImp;
import com.springboot.HRISNEW.implementservices.RequestOvertimeServiceImpl;
import com.springboot.HRISNEW.implementservices.RequesterInformServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author HARRY-PC
 */
@Controller
public class MainController {

    @Autowired
    ServiceApi serviceApi;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RequestOvertimeServiceImpl rosi;

    @Autowired
    private RequestDetailServiceImp rdsi;

    @Autowired
    private RequesterInformServiceImpl risi;

    @Autowired
    private AnnouncementServiceImpl asi;

    @Autowired
    private QuestionServiceImpl qsi;

    @Autowired
    private AnswerServiceImpl ansi;

    //LOGIN
    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @GetMapping("/index")
    public String index() {
        System.out.println("Running Index");
        return "index";
    }

    @GetMapping("/role")
    public String role() {
        System.out.println("Running Role");
        return "role";
    }

    @GetMapping("/forgetPass")
    public String forgetPass() {
        System.out.println("Running Forget Password");
        return "forgetPassword";
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
    public String forgotPassword(@RequestParam("emailUser") String email, String pwd, String uuid, String mailContext,
            String appUrl, JSONObject user, JSONObject empl, JSONObject employee, Model model, RedirectAttributes direct,
            HttpServletRequest request) throws Exception {
        int nik = 0;
        appUrl = request.getScheme() + "://" + request.getServerName() + ":8080";
        user = serviceApi.findUserByEmail(email);
        empl = serviceApi.findEmployeeByEmail(email);
        employee = serviceApi.SELECTEMPLBYEMAIL(email);
        uuid = UUID.randomUUID().toString();
        mailContext = "To reset your password, click the link below:\n" + appUrl + "/HRIS/reset?token=" + uuid;
        if (user != null) {
            nik = user.getInt("User_Id");
            pwd = user.getString("Password");
            serviceApi.resetPassUser(Integer.toString(nik), pwd, uuid);
            emailService.sendNotificationService(email, mailContext);
        } else {
            if (empl == null) {
                System.out.println("Email tidak ada ");
                direct.addFlashAttribute("exist", 0);
            } else {
                nik = empl.getInt("Employee_Nik");
                if (employee != null) {
                    pwd = employee.getString("Password");
                    serviceApi.resetPassEmployee(Integer.toString(nik), pwd, uuid);
                    emailService.sendNotificationService(email, mailContext);
                } else {
                    if (empl != null) {
                        insertEmployee(Integer.toString(nik), email, uuid);
                        emailService.sendNotificationService(email, mailContext);
                    } else {
                        System.out.println("Employee is empty" + empl);
                    }
                    model.addAttribute("errorMessage", "We didn't find an account for that e-mail address.");
                }
            }
        }
        return "redirect:/loginPage";
    }

    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String resetPasswordPage(Model model,
            @RequestParam("token") String token) {
        int guest;
        JSONObject tokenEmpl = serviceApi.emplbyToken(token);
        JSONObject tokenUser = serviceApi.userbyToken(token);
        if (tokenUser != null) {
            guest = tokenUser.getInt("User_Id");
            model.addAttribute("userId", guest);
        } else if (tokenEmpl != null) {
            guest = tokenEmpl.getInt("Employee_Nik");
            model.addAttribute("userId", guest);
        } else {
            model.addAttribute("errorMessage", "Oops!  This is an invalid password reset link.");
        }
        return "reset";
    }

    @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
    public String newResetPassword(Model model,
            @RequestParam("nik") String nik,
            @RequestParam("new_password") String newPassword,
            @RequestParam("confirm_pass") String confirmPassword) {
        JSONObject cariUser = serviceApi.userbyid(Integer.parseInt(nik));
        JSONObject cariEmpl = serviceApi.findEmpbyid(nik);
        String newpas = new BCryptPasswordEncoder().encode(newPassword);
        if (cariUser != null) {
            serviceApi.resetPassUser(nik, newpas, null);
        } else if (cariEmpl != null) {
            serviceApi.resetPassEmployee(nik, newpas, null);
        } else {
            return "reset";
        }
        return "redirect:/loginPage";
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
    public String changePasswordPage(Model model) {
        int nik;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        JSONObject empl = serviceApi.findEmployeeByEmail(authentication.getName());
        if (user != null) {
            nik = user.getInt("User_Id");
            model.addAttribute("nik", nik);
            System.out.println("NIK User : " + nik);
        } else if (empl != null) {
            nik = empl.getInt("Employee_Nik");
            model.addAttribute("nik", nik);
            System.out.println("NIK Employee : " + nik);
        }
        return "changePassword";
    }

    @RequestMapping(value = "/changePass", method = RequestMethod.POST)
    public String changePass(@RequestParam("oldPassword") String oldPassword,
            @RequestParam("nik") int nik,
            @RequestParam("newPassword") String newPassword,
            RedirectAttributes direct) {
        int nikEmp;
        String password, passwordNew;
        boolean put;
        JSONObject user, empl;
        user = serviceApi.userbyid(nik);
        empl = serviceApi.findEmpLoginById(Integer.toString(nik));
        if (user != null) {
            System.out.println("User : " + user.getString("Name"));
            nikEmp = user.getInt("User_Id");
            password = user.getString("Password");
            put = new BCryptPasswordEncoder().matches(oldPassword, password);
            if (put == false) {
                direct.addFlashAttribute("oldtrue", 0);
                return "redirect:changePassword";
            } else {
                passwordNew = new BCryptPasswordEncoder().encode(newPassword);
                insertnewpassEmployee(Integer.toString(nik), "", passwordNew);
            }
        } else if (empl != null) {
            nikEmp = empl.getInt("Employee_Nik");
            password = empl.getString("Password");
            put = new BCryptPasswordEncoder().matches(oldPassword, password);
            System.out.println("NIK = " + nik + " old password = " + oldPassword + " password from db = " + password);
            if (put == false) {
                direct.addFlashAttribute("oldtrue", 0);
                return "redirect:changePassword";
            } else {
                passwordNew = new BCryptPasswordEncoder().encode(newPassword);
                insertnewpassEmployee(Integer.toString(nikEmp), "", passwordNew);
            }
        } else {
            return "redirect:changePassword";
        }
        return "redirect:/home";
    }

    public String insertEmployee(String nik, String usrnm, String token) {
        String pswd;
        pswd = "$2a$10$DRjZBg74iW06ICSzm1Ir6O9vCMmR06e5WSC.pfc02P49e28VEuupy";
        System.out.println("Insert empl NIK = " + nik + " Password " + pswd);
        JSONObject addEmp = serviceApi.addEmployee(nik, pswd, token);
        return loginPage();
    }

    public String insertnewpassEmployee(String nik, String usrnm, String pswd) {
        System.out.println("Insert empl NIK = " + nik + " Password " + pswd);
        serviceApi.addEmployee(nik, pswd, "");
        return loginPage();
    }

    @GetMapping(value = "/getName", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String getNameByLogin() {
        JSONObject jo = new JSONObject();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (serviceApi.findUserByEmail(authentication.getName()) != null) {
            jo.put("username", serviceApi.findUserByEmail(authentication.getName()).getString("Name"));
            jo.put("nikUser", serviceApi.findUserByEmail(authentication.getName()).getInt("User_Id"));
        } else if (serviceApi.findEmployeeByEmail(authentication.getName()) != null) {
            jo.put("username", serviceApi.findEmployeeByEmail(authentication.getName()).getString("Name"));
        } else {
            jo.put("username", " ");
        }
        return jo.toString();
    }

    @GetMapping("/home")
    public String home(Model model, HttpSession session, RedirectAttributes direct) throws ParseException {
        System.out.println("Running Home Employee");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        Date now = new Date();
        JSONObject joUser = serviceApi.findUserByEmail(user);
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(user);
        JSONObject jmlhJointHoliday = serviceApi.jmlhJoinHoliday();

        List publicHoliday = new ArrayList();
        List Ph = new ArrayList();

        int nik, jumlahCurrent = 12, jumlahJoinHoliday = Integer.parseInt(jmlhJointHoliday.getString("count")),
                total = jumlahCurrent + 0;
        long diffInMillies = 0;
        String startdate, statusKontrak, endcontract, nama, div;
        LocalDate today = LocalDate.now();
        if (joUser != null) {
            model.addAttribute("nik", joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            nik = joEmpl.getInt("Employee_Nik");
            LocalDate expcurrentt = LocalDate.of(today.getYear() + 1, 6, 30);
            LocalDate explast = LocalDate.of(today.getYear() + 1, 6, 30);
            JSONObject stskntrk = serviceApi.statusKontrak(Integer.toString(nik));
            statusKontrak = stskntrk.getString("Start_Contract_Date");
            LocalDate endContract = LocalDate.parse(stskntrk.getString("End_Contract_Date"));
//            System.out.println("END KONTRAK : " + endContract);
            long monthsBetween = ChronoUnit.MONTHS.between(
                    LocalDate.parse(stskntrk.getString("Start_Contract_Date")).withDayOfMonth(1),
                    today.withDayOfMonth(1));
            long monthsBetweenContract = ChronoUnit.MONTHS.between(
                    LocalDate.parse(stskntrk.getString("Start_Contract_Date")).withDayOfMonth(1),
                    LocalDate.parse(stskntrk.getString("End_Contract_Date")).withDayOfMonth(1).withDayOfMonth(1));
            if (monthsBetweenContract > 12) {
                if (monthsBetween < 12) {
                    LocalDate after1year = LocalDate.parse(stskntrk.getString("Start_Contract_Date")).plusMonths(12).withDayOfMonth(1);
//                    after1year.plusMonths(12);
                    endContract = after1year;
                }
            }
            nama = joEmpl.getString("Name");
            div = joEmpl.getString("Division");
            Division division = new Division(div);
            JSONObject all = serviceApi.findAllocationbyNik(Integer.toString(nik));
            JSONObject stcon = serviceApi.statusKontrak(Integer.toString(nik));
            if (risi.findRequesterbyid(nik) != null) {
                JSONObject firstContracts = serviceApi.firstContract(nik);
                System.out.println(nik + " = First Contract : " + firstContracts.getString("Start_Contract_Date"));
                monthsBetween = ChronoUnit.MONTHS.between(
                        LocalDate.parse(firstContracts.getString("Start_Contract_Date")).withDayOfMonth(1),
                        today.withDayOfMonth(1));
                System.out.println("Month Between : " + monthsBetween + " Bulan");
                int lastYear = risi.findRequesterbyid(nik).getAvailableLeaveCurrentyear();
                if (risi.findRequesterbyid(nik).getFlag() == true) {
                    expcurrentt = endContract;
                    if (today.getMonthValue() < 6) {
                        explast = LocalDate.of(today.getYear(), 6, 30);
                    }
                    if (serviceApi.findIncludeJoinById(all.getString("So_Id")).getBoolean("includejointholiday") == true) {
                        jumlahCurrent = 12 - jumlahJoinHoliday;
                    }
                    if (risi.findRequesterbyid(nik).getAvailableLeaveCurrentyear() < 0) {
                        lastYear = 0;
                        jumlahCurrent = jumlahCurrent + risi.findRequesterbyid(nik).getAvailableLeaveCurrentyear();

                    }
//                    System.out.println(expcurrentt + "-------" + explast);
                    RequesterInformation req = new RequesterInformation(nik, risi.findRequesterbyid(nik).getName(),
                            jumlahCurrent, lastYear,
                            convertToDateViaInstant(expcurrentt), convertToDateViaInstant(explast), (jumlahCurrent + lastYear), false, division);
                    risi.save(req);
                }
                else if(risi.findRequesterbyid(nik).getDivision()== null){
                    risi.saveDivision(div, nik);
                }
            }
            startdate = stcon.getString("Start_Contract_Date");
            Date fromdate = new SimpleDateFormat("yyyy-MM-dd").parse(startdate);
            diffInMillies = Math.abs(fromdate.getMonth() - now.getMonth());
            System.out.println("SO ID = " + all.getString("So_Id"));
            if (serviceApi.findIncludeJoinById(all.getString("So_Id")).getBoolean("includejointholiday") == true) {
                publicHoliday = serviceApi.allHoliday();
//                System.out.println("All HOLIDAY");
            } else {
                publicHoliday = serviceApi.publicHolidaydate();
//                System.out.println("NOT INCLUDE");
            }
            for (Object object : publicHoliday) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyy");
                Ph.add(format.format(format.parse((String) object)));
            }

//            System.out.println("Status Kontrak : " + statusKontrak);
            if (statusKontrak.equals("1st Contract")) {
                if (monthsBetween <= 12 && risi.findRequesterbyid(nik) == null) {
//                    System.out.println("Masuk yang Nol 00");
                    RequesterInformation rqinfo = new RequesterInformation(nik, nama, 0, 0, convertToDateViaInstant(endContract), convertToDateViaInstant(endContract), 0, false, division);
//                    System.out.println("Here : " + monthsBetween);
                    risi.save(rqinfo);
                } else {
                    if (monthsBetween > 12 && risi.findRequesterbyid(nik) == null) {
                        System.out.println(all.getString("So_Id") + "-> Boolean JOINDATE : " + serviceApi.findIncludeJoinById(all.getString("So_Id")).getBoolean("includejointholiday"));
                        if (serviceApi.findIncludeJoinById(all.getString("So_Id")).getBoolean("includejointholiday") == true) {
                            jumlahCurrent = 12 - jumlahJoinHoliday;
                            total = jumlahCurrent;
//                            System.out.println("Total 1 = " + total);
                        }
                        System.out.println("Jumlah C-tidak hitungs joindate" + jumlahCurrent);
                        endcontract = stskntrk.getString("End_Contract_Date");
                        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endcontract);
                        if (endDate.compareTo(convertToDateViaInstant(expcurrentt)) > 0) {
                            expcurrentt = convertToLocalDateViaMilisecond(endDate);
                        }
//                        System.out.println(endDate + "total" + total + "" + endDate.compareTo(convertToDateViaInstant(expcurrentt)));
                        RequesterInformation rqinfo1 = new RequesterInformation(nik, nama, jumlahCurrent, 0, convertToDateViaInstant(endContract), convertToDateViaInstant(endContract), total, false, division);
                        risi.save(rqinfo1);
                    }
                }
            } else {
                if (risi.findRequesterbyid(nik) == null) {
                    if (serviceApi.findIncludeJoinById(all.getString("So_Id")).getBoolean("includejointholiday") == true) {
                        jumlahCurrent = 12 - jumlahJoinHoliday;
                        total = jumlahCurrent;
                    }
//                    System.out.println("Jumlah Current : " + jumlahCurrent);

                    JSONObject endcon = serviceApi.statusKontrak(Integer.toString(nik));
                    endcontract = endcon.getString("End_Contract_Date");

                    if (today.getMonthValue() < 6) {
                        explast = LocalDate.of(today.getYear(), 6, 30);
                    }
                    Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endcontract);
                    if (endDate.compareTo(convertToDateViaInstant(expcurrentt)) < 0) {
                        expcurrentt = convertToLocalDateViaMilisecond(endDate);
                    }
//                    System.out.println("Tanggal Current = " + expcurrentt);

                    RequesterInformation rqinfo1 = new RequesterInformation(nik, nama, jumlahCurrent, 0, convertToDateViaInstant(endContract), convertToDateViaInstant(explast), total, false, division);
                    risi.save(rqinfo1);
                }
            }
//            
//            long diffday = 0;
//            RequestDetail lastReq = rdsi.lastRequestEmp(nik);
//            if (lastReq != null) {
//                System.out.println("LAST REQUEST : " + lastReq.getId());
//            } else {
//                System.out.println("KOSONG");
//            }
//            
//            if (lastReq != null) {
//                System.out.println("Last Request = " + lastReq.getId());
//                diffday = Math.abs(((lastReq.getSubmittedDate()).getTime() - now.getTime()) / 86400000);
//
//                if (diffday >= 7 && lastReq.getIsdelete() == false) {
//                    session.setAttribute("permit", 0);
//                } else {
//                    session.setAttribute("permit", 1);
//                }
//            } else {
//                session.setAttribute("permit", 0);
//            }

            model.addAttribute("nik", nik);
        }

        return "home";
    }

    @GetMapping("/announcement")
    public @ResponseBody String getAnnouncement(){
        JSONArray jSONArray = new JSONArray();
        JSONObject data = new JSONObject();
        for (Object[] objects : asi.selectAll()) {
            JSONObject jo = new JSONObject();
            jo.put("id", objects[0]);
            jo.put("Judul", objects[1]);
            jo.put("Content", objects[2]);
            jo.put("Start_Date", objects[3]);
            jo.put("End_Date", objects[4]);
            jo.put("Create_Date", objects[5]);
            jo.put("isActive", objects[6]);
            jSONArray.put(jo);
        }
        data.put("data", jSONArray);
        return data.toString();
    }

    //ADMIN
    @GetMapping("/homeAdm")
    public String homeadm(Model model, HttpSession session) {
        System.out.println("Runnning Home Admin");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (user != null) {
            model.addAttribute("nik", user.getInt("User_Id"));
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("roleUser", user.getInt("Role_Id"));
        }

        int nikLogin = user.getInt("User_Id");

//        System.out.println("Nik Login " + user.getInt("User_Id"));
        model.addAttribute("nikLogin", nikLogin);

        String role = null;

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

        if (role.equalsIgnoreCase("ROLE_MSHR_STAFF") || role.equalsIgnoreCase("ROLE_MSHR_MAN")
                || role.equalsIgnoreCase("ROLE_MSHR_TRA_DEV") || role.equalsIgnoreCase("ROLE_MSHR_STAFF_COM_BEN") || role.equalsIgnoreCase("ROLE_MSHR_PCN")) {
            session.setAttribute("permit", 0);
        }//akses menu pending approver
        else if(role.equalsIgnoreCase("ROLE_MSDEL_MAN")||role.equalsIgnoreCase("ROLE_DM")||role.equalsIgnoreCase("ROLE_MSBID_MAN")
                ||role.equalsIgnoreCase("ROLE_MSAD_MAN")||role.equalsIgnoreCase("ROLE_MSCS_MAN")||role.equalsIgnoreCase("ROLE_MSPM_MAN")
                ||role.equalsIgnoreCase("ROLE_MSHR_MAN")||role.equalsIgnoreCase("ROLE_ADD2_MAN")||role.equalsIgnoreCase("ROLE_DM_ADD")){
            session.setAttribute("permit", 2);
        }
        else {
            session.setAttribute("permit", 1);
        }

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int month2 = month;
        int year = localDate.getYear();
//        System.out.println("year now ==> " + year);
        int year2 = year;

        List<Object[]> peopleTrendsLeave = new ArrayList<>();
        List<Object[]> soTrendsLeave = new ArrayList<>();
        List<Object[]> peopleTrendsOvertime = new ArrayList<>();
        List<Object[]> soTrendsOvertime = new ArrayList<>();

        for (Object[] object : rdsi.findLeavePeopleTrends(month, year2)) {
            Object[] objects = new Object[5];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];

            peopleTrendsLeave.add(objects);
        }

        for (Object[] object : rdsi.findSOTrendsLeave(month, year2)) {
            Object[] objects = new Object[3];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];

            soTrendsLeave.add(objects);
        }

        for (Object[] object : rosi.peopleTrendsOvertime(month, month2, year, year2)) {
            Object[] objects = new Object[4];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];

            peopleTrendsOvertime.add(objects);
        }

        for (Object[] object : rosi.soTrendsOvertime(month, year2)) {
            Object[] objects = new Object[3];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];

            soTrendsOvertime.add(objects);
        }

        int cutNormal = rdsi.findCountCutiNormal(month, year2);
        int cutPejalanan = rdsi.findCountPerjalananBisnis(month, year2);
        int cutMelahirkan = rdsi.findCountIstriMelahirkan(month, year2);
        int cutBersalin = rdsi.findCountCutiBersalin(month, year2);
        int cutSakit = rdsi.findCountCutiSakit(month, year2);
        int cutIjin = rdsi.findCountCutiIjin(month, year2);
        int tot = cutNormal + cutPejalanan + cutMelahirkan + cutBersalin + cutSakit + cutIjin;

        int totDoneLeave = rdsi.findTotalDoneLeave(year2);
        int totRejectLeave = rdsi.findTotalRejectLeave(year2);
        int totWaitingLeave = rdsi.findTotalWaitingLeave(year2);
        int totReqLeave = totDoneLeave + totRejectLeave + totWaitingLeave;
//        System.out.println("total leave request ==> " + totReqLeave);

        int done = rosi.doneOvertime(month, year2);
        int reject = rosi.rejectOvertime(month, year2);
        int waitingMSFC = rosi.waitingFromMSFCOvertime(month, year2);
        int waitingRM = rosi.waitingFromRMOvertime(month, year2);
        int waitingPM = rosi.waitingFromPMOvertime(month, year2);
        int totalOver = done + reject + waitingMSFC + waitingRM + waitingPM;

        int totDoneOvertime = rosi.totalDoneOvertime(year2);
        int totRejectOvertime = rosi.totalRejectOvertime(year2);
        int totWaitingOvertime = rosi.totalWaitingOvertime(year2);
        int totReqOver = totDoneOvertime + totRejectOvertime + totWaitingOvertime;

        model.addAttribute("totalCuti", tot);
        model.addAttribute("cutiNormal", cutNormal);
        model.addAttribute("perjalananBisnis", cutPejalanan);
        model.addAttribute("istriMelahirkan", cutMelahirkan);
        model.addAttribute("cutiBersalin", cutBersalin);
        model.addAttribute("sakit", cutSakit);
        model.addAttribute("ijin", cutIjin);

        model.addAttribute("totalReqLeave", totReqLeave);
        model.addAttribute("totalDoneLeave", rdsi.findTotalDoneLeave(year2));
        model.addAttribute("totalRejectLeave", rdsi.findTotalRejectLeave(year2));
        model.addAttribute("totalWaitingLeave", rdsi.findTotalWaitingLeave(year2));

        model.addAttribute("leavePeopleTrends", peopleTrendsLeave);
        model.addAttribute("leaveSOTrends", soTrendsLeave);
        model.addAttribute("overtimePeopleTrends", peopleTrendsOvertime);
        model.addAttribute("overtimeSOTrends", soTrendsOvertime);

        model.addAttribute("doneOvertime", done);
        model.addAttribute("rejectOvertime", reject);
        model.addAttribute("waitingMSFCOvertime", waitingMSFC);
        model.addAttribute("waitingRMOvertime", waitingRM);
        model.addAttribute("waitingPMOvertime", waitingPM);
        model.addAttribute("totalOver", totalOver);

        model.addAttribute("totalReqOvertime", totReqOver);
        model.addAttribute("totalWaitingOvertime", rosi.totalWaitingOvertime(year2));
        model.addAttribute("totalRejectOvertime", rosi.totalRejectOvertime(year2));
        model.addAttribute("totalDoneOvertime", rosi.totalDoneOvertime(year2));

        // end of comment
        return "homeAdm";

    }

    @GetMapping(value = "/peopletrendsleave/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String peopletrendsleave(@PathVariable int month, Model model) {
        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int year = localDate.getYear();
        int year2 = year;

        for (Object[] objects : rdsi.findLeavePeopleTrends(month, year2)) {
            JSONObject jo = new JSONObject();

            jo.put("nik", objects[0]);
            jo.put("name", objects[1]);
            jo.put("request", objects[2]);
            jo.put("bln", objects[3]);
            jo.put("leave_days", objects[4]);

            jsona.put(jo);
        }

        jsono.put("data", jsona);

        return jsono.toString();
    }

    @GetMapping(value = "/sotrendsleave/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String sotrendsleave(@PathVariable int month, Model model) {

        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int year = localDate.getYear();
        int year2 = year;

        for (Object[] objects : rdsi.findSOTrendsLeave(month, year2)) {

            JSONObject jo = new JSONObject();

            jo.put("so_id", objects[0]);
            jo.put("co", objects[1]);
            jo.put("customer_name", objects[2]);

            jsona.put(jo);
        }
        jsono.put("data", jsona);
        return jsono.toString();
    }

    @RequestMapping(value = "/leavereport/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String leavereport(@PathVariable(value = "id") int month, RedirectAttributes model) {

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int year = localDate.getYear();
        int year2 = year;

        JSONObject jo = new JSONObject();
        jo.put("cutiNormal", rdsi.findCountCutiNormal(month, year2));
        jo.put("perjalananBisnis", rdsi.findCountPerjalananBisnis(month, year2));
        jo.put("istriMelahirkan", rdsi.findCountIstriMelahirkan(month, year2));
        jo.put("cutiBersalin", rdsi.findCountCutiBersalin(month, year2));
        jo.put("sakit", rdsi.findCountCutiSakit(month, year2));
        jo.put("ijin", rdsi.findCountCutiIjin(month, year2));
        jo.put("datenow", "");

        return jo.toString();
    }

    @GetMapping(value = "/peopletrendsovertime/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String peopletrendsOvertime(@PathVariable int month, Model model) {

        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();
        int month2 = month;

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int year = localDate.getYear();
        int year2 = year;

        for (Object[] objects : rosi.peopleTrendsOvertime(month, month2, year, year2)) {

            JSONObject jo = new JSONObject();

            jo.put("total", objects[0]);
            jo.put("employeename", objects[1]);
            jo.put("employee_nik", objects[2]);
            jo.put("done", objects[3]);

            jsona.put(jo);
        }
        jsono.put("data", jsona);
        return jsono.toString();
    }

    @GetMapping(value = "/sotrendsovertime/{month}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String sotrendsOvertime(@PathVariable int month, Model model) {

        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int year = localDate.getYear();
        int year2 = year;

        for (Object[] objects : rosi.soTrendsOvertime(month, year2)) {

            JSONObject jo = new JSONObject();

            jo.put("so_id", objects[0]);
            jo.put("customer_name", objects[1]);
            jo.put("done", objects[2]);

            jsona.put(jo);
        }
        jsono.put("data", jsona);
        return jsono.toString();
    }

    @RequestMapping(value = "/overtimereport/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String overtimereport(@PathVariable(value = "id") int month, RedirectAttributes model) {

        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        int year = localDate.getYear();
        int year2 = year;

        JSONObject jo = new JSONObject();
        jo.put("doneOvertime", rosi.doneOvertime(month, year2));
        jo.put("rejectOverime", rosi.rejectOvertime(month, year2));
        jo.put("waitingMSFCOvertime", rosi.waitingFromMSFCOvertime(month, year2));
        jo.put("waitingRMOvertime", rosi.waitingFromRMOvertime(month, year2));
        jo.put("waitingPMOvertime", rosi.waitingFromPMOvertime(month, year2));
        jo.put("datenow", "");

        return jo.toString();
    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login?logout";
//    }
    @GetMapping("/termAndCondition")
    public String termAndCondition(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findEmployeeByEmail(auth.getName());

        if (user != null) {
            model.addAttribute("nik", user.getInt("Employee_Nik"));
            model.addAttribute("email", user.getString("Email"));
        }

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());
        System.out.println("roles == " + roles.toString());

        return "termAndCondition";
    }
    //client view faq
    @GetMapping("/faq")
   public String questionList(Model model){
        System.out.println("tes");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        JSONObject joUser = serviceApi.findUserByEmail(userId);
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(userId);

        if (joUser != null) {
            model.addAttribute("nik", joUser.getInt("User_Id"));
        } else if (joEmpl != null) {
            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        }

        model.addAttribute("roles", roles.toString());
        model.addAttribute("answer", ansi.tampilAktif());
        return "faq";

    }
    //Faq Manage
    @GetMapping("/manageFaq")
    public String getAll(Model model) {
        System.out.println("--faq run--");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (user != null) {
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
        }
        model.addAttribute("answer", ansi.tampilAktif());
        model.addAttribute("question", qsi.getAll());
        return "approval/announcement/manageFaq";
    }

    @PostMapping("/addQuestion")
    public String addQuestion(FaqQuestion faqQuestion){
        qsi.save(faqQuestion);
        return "redirect:/manageFaq";
    }

    @PostMapping("/addFaq")
    public String addAnswer(FaqAnswer faqAnswer){
        ansi.save(faqAnswer);
        return "redirect:/manageFaq";
    }

    @RequestMapping("/deleteFaq")
    public String delete(@RequestParam(value="id")Integer id){
        FaqAnswer an = ansi.getById(id);
        an.setIsdeleted(true);
        ansi.save(an);
        return "redirect:/manageFaq";
    }
//    @GetMapping("/editFaq")
//    public String edit(@RequestParam(value = "id") Integer id, Model model) {
//        model.addAttribute("question", qsi.getAll());
//        model.addAttribute("answer", ansi.getById(id));
//        return "redirect:/manageFaq";
//    }

    
    
    
   
   
    //Part of Announcement Manage
    @GetMapping("/manageAnnouncement")
   public String manageAnnouncement(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (user != null) {
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
        }
        model.addAttribute("dataAnnouncement",asi.selectAll());
        return "approval/announcement/manageAnnouncement";
    }

    @PostMapping("/addAnnoucement")
    public String addAnnoucement(@RequestParam(value = "tittle", required = false) String tittle,
            @RequestParam(value = "contentText", required = false) String content,
            @RequestParam(value = "startDateAnnoun", required = false) String startDateAnnoun,
           @RequestParam(value = "endDateAnnoun", required = false) String endDateAnnoun) throws ParseException{

        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date startDate = format.parse(startDateAnnoun);
        Date endDate = format.parse(endDateAnnoun);

        TbTrAnnouncement announcement = new TbTrAnnouncement(tittle, content, startDate, endDate, timeAdd6Hours(), Boolean.TRUE);
        asi.save(announcement);

        return "redirect:/manageAnnouncement";
    }

    @RequestMapping(value = "/getAnnouncement/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody String getAnnouncementById(@PathVariable(value = "id") int id){
//       System.out.println("ID = " + id);
        List<TbTrAnnouncement> announcements = asi.findAnnouncementById(id);
        JSONArray ja = new JSONArray();
        JSONObject jo = new JSONObject();
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        for (TbTrAnnouncement announcement : announcements) {
            JSONObject job = new JSONObject();
            job.put("id", announcement.getId());
            job.put("judul", announcement.getJudul());
            job.put("content", announcement.getInformation());
            job.put("start_date", format.format(announcement.getStartDate()));
            job.put("end_date", format.format(announcement.getEndDate()));
            job.put("isActive", announcement.getIsactive());
            ja.put(job);
        }
        jo.put("data", ja);
        return jo.toString();
    }

    @PostMapping("/editAnnouncement")
    public String editAnnouncement(@RequestParam(value = "idAnnouncement") int id,
            @RequestParam(value = "tittleEdit") String tittleEdit,
            @RequestParam(value = "contentTextEdit") String contentEdit,
            @RequestParam(value = "startDateAnnounEdit") String startDateAnnounEdit,
           @RequestParam(value = "endDateAnnounEdit") String endDateAnnounEdit) throws ParseException{
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date startDate = format.parse(startDateAnnounEdit);
        Date endDate = format.parse(endDateAnnounEdit);
        TbTrAnnouncement announcement = asi.editAnnouncementById(id);
        announcement.setJudul(tittleEdit);
        announcement.setInformation(contentEdit);
        announcement.setStartDate(startDate);
        announcement.setEndDate(endDate);
        asi.save(announcement);
        return "redirect:/manageAnnouncement";
    }

    @RequestMapping(value = "/disableAnnouncement", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
   public @ResponseBody String disableAnnouncement(HttpServletRequest response){
        String result, id = response.getParameter("id");
        result = "SUCCESS DISABLED";
        TbTrAnnouncement announcement = asi.editAnnouncementById(Integer.parseInt(id));
        announcement.setIsactive(Boolean.FALSE);
        asi.save(announcement);
        return result;
    }

    //Approver's Pending Leave
    @GetMapping("/ApproverPending")
    public String ApprovalPending(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (user != null) {
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
        }
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
//        System.out.println("year now ==> " + year);
        int year2 = year;

        List<Object[]> AppPending = new ArrayList<>();
        for (Object[] datas : rdsi.ApproverPending(year2)) {
            Object[] data = new Object[3];
            data[0] = datas[0];
            data[1] = datas[1];
            data[2] = datas[2]+" Request Pending";

            AppPending.add(data);
        }
        List<Object[]> OTPending = new ArrayList<>();
        for (Object[] datas : rosi.OvertimeApproverPending(year2)) {
            Object[] data = new Object[3];
            data[0] = datas[0];
            data[1] = datas[1];
            data[2] = datas[2]+" Request Pending";

            OTPending.add(data);
        }

        model.addAttribute("AppPendingg", AppPending);
        model.addAttribute("OtPending", OTPending);
        return "approval/ApproverPending/ApproverPending";

    }
    //Approver Pending Leave detail
    @GetMapping("/ApproverPending/LeavePending/{id}")
    public String PendingLeave(@PathVariable(value = "id") String nik, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (user != null) {
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
        }
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
//        System.out.println("year now ==> " + year);
        int year2 = year;

        List<Object[]> LeavePending = new ArrayList<>();
        for (Object[] datas : rdsi.PendingDetail(Integer.parseInt(nik),year2)) {
            Object[] data = new Object[10];
            data[0] = datas[0];
            data[1] = datas[1];
            data[2] = datas[2];
            data[3] = datas[3];
            data[4] = datas[4];
            data[5] = datas[5];
            data[6] = datas[6];
            data[7] = datas[7];
            data[8] = datas[8];

            LeavePending.add(data);
        }
        model.addAttribute("LeavePend", LeavePending);
        return "approval/ApproverPending/LeavePending";
    }
    //Approver's Pending Overtime Detail
    @GetMapping("/ApproverPending/OvertimePending/{id}")
    public String ApprovalOvertimePending(@PathVariable(value = "id") String nik, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (user != null) {
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
        }
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
//        System.out.println("year now ==> " + year);
        int year2 = year;

        List<Object[]> OTPendingDetail = new ArrayList<>();
        for (Object[] datas : rosi.OvertimePendingDetail(Integer.parseInt(nik), year2)) {
            Object[] data = new Object[12];
            data[0] = datas[0];
            data[1] = datas[1];
            data[2] = datas[2];
            data[3] = datas[3];
            data[4] = datas[4];
            data[5] = datas[5];
            data[6] = datas[6];
            data[7] = datas[7];
            data[8] = datas[8];
            data[9] = datas[9];
            data[10] = datas[10];
            data[11] = datas[11];

            OTPendingDetail.add(data);
        }

        model.addAttribute("OtPendingDetail", OTPendingDetail);
        return "approval/ApproverPending/OvertimePending";

    }

    public Date timeAdd6Hours() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 7);
        System.out.println("Penambahan" + calendar.getTime());
        return calendar.getTime();
    }

    public LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

}
