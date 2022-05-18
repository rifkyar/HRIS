/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.requester;

import com.google.gson.Gson;
import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import com.springboot.HRISNEW.entities.TbMCertificateType;
import com.springboot.HRISNEW.entities.TbMEmployee;
import com.springboot.HRISNEW.entities.TbMSkill;
import com.springboot.HRISNEW.entities.TbTrCertification;
import com.springboot.HRISNEW.entities.TbTrLogEmployee;
import com.springboot.HRISNEW.entities.TbTrProject;
import com.springboot.HRISNEW.entities.TbTrTechnical;
import com.springboot.HRISNEW.entities.TbTrTraining;
import com.springboot.HRISNEW.entities.TbTrWorkassignment;
import com.springboot.HRISNEW.implementservices.CategoryServiceImpl;
import com.springboot.HRISNEW.implementservices.CertificateTypeServiceImpl;
import com.springboot.HRISNEW.implementservices.CertificationServiceImpl;
import com.springboot.HRISNEW.implementservices.EmployeeServiceImpl;
import com.springboot.HRISNEW.implementservices.LogEmployeeServiceImpl;
import com.springboot.HRISNEW.implementservices.ProjectServiceImpl;
import com.springboot.HRISNEW.implementservices.SkillServiceImpl;
import com.springboot.HRISNEW.implementservices.TechnicalServiceImpl;
import com.springboot.HRISNEW.implementservices.TrainingServiceImpl;
import com.springboot.HRISNEW.implementservices.WorkAssignmentServiceImpl;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class ProfileRequestController {

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    private ServiceApi serviceApi;

    @Autowired
    private EmployeeServiceImpl esi;

    @Autowired
    private LogEmployeeServiceImpl lesi;

    @Autowired
    private EmailService emailService;

    @Autowired
    private WorkAssignmentServiceImpl wai;

    @Autowired
    private ProjectServiceImpl pasi;

    @Autowired
    private TrainingServiceImpl tsi;

    @Autowired
    private CertificateTypeServiceImpl ctsi;

    @Autowired
    private CertificationServiceImpl csi;

    @Autowired
    private CategoryServiceImpl cssi;

    @Autowired
    private SkillServiceImpl ssi;

    @Autowired
    private TechnicalServiceImpl tcsi;

    public static String userId, idnya;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable(value = "id") Integer id, Model model) throws ParseException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        model.addAttribute("roles", roles.toString());

        if (!id.equals(emp.getInt("Employee_Nik"))) {
            return "redirect:/home";
        } else if (emp != null) {
            String statusNikah = null, jenisKelamin = null, hpEmergency = null,
                    noNpwp = null, noBPJS = null, noBPJSTK = null, bankName = null,
                    noRekBank = null;
            SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatjoin = new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("nik", emp.getInt("Employee_Nik"));
            model.addAttribute("name", emp.getString("Name"));
            model.addAttribute("noKtp", emp.getString("No KTP"));
            model.addAttribute("pob", emp.getString("Place of Birth"));
            Date dateFormat = formatdate.parse(emp.getString("Date of Birth"));
            formatdate = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            model.addAttribute("dob", formatdate.format(dateFormat));
            model.addAttribute("email", emp.getString("Email"));
            if (emp.getInt("Marriage Status") == 0) {
                statusNikah = "Single";
            } else if (emp.getInt("Marriage Status") == 1) {
                statusNikah = "Marriage";
            } else if (emp.getInt("Marriage Status") == 2) {
                statusNikah = "Divorced";
            } else if (emp.getInt("Marriage Status") == 3) {
                statusNikah = "Divorced";
            }
            model.addAttribute("marriageStatus", statusNikah);
            if (emp.getInt("Gender") == 1) {
                jenisKelamin = "Male";
            } else if (emp.getInt("Gender") == 2) {
                jenisKelamin = "Female";
            }
            model.addAttribute("gender", jenisKelamin);
            model.addAttribute("addressCity", emp.getString("Address City"));
            model.addAttribute("addressStreet", emp.getString("Address Street"));
            model.addAttribute("noHp", emp.getString("Hp"));
            if (emp.getString("Hp Emergency").equalsIgnoreCase("null")) {
                hpEmergency = "N/A";
            } else {
                hpEmergency = emp.getString("Hp Emergency");
            }
            model.addAttribute("noHpEmergency", hpEmergency);
            if (emp.getString("No NPWP").equalsIgnoreCase("")) {
                noNpwp = "N/A";
            } else {
                noNpwp = emp.getString("No NPWP");
            }
            model.addAttribute("noNpwp", noNpwp);
            if (emp.getString("BPJS Kesehatan").equalsIgnoreCase("null")) {
                noBPJS = "N/A";
            } else {
                noBPJS = emp.getString("BPJS Kesehatan");
            }
            model.addAttribute("noBPJS", noBPJS);
            if (emp.getString("BPJS Ketenagakerjaan").equalsIgnoreCase("null")) {
                noBPJSTK = "N/A";
            } else {
                noBPJSTK = emp.getString("BPJS Ketenagakerjaan");
            }
            model.addAttribute("noBPJSTK", noBPJSTK);
            if (emp.getString("Bank Name").equalsIgnoreCase("null")) {
                bankName = "N/A";
            } else {
                bankName = emp.getString("Bank Name");
            }
            model.addAttribute("bankName", bankName);
            if (emp.getString("Bank Account Number").equalsIgnoreCase("null")) {
                noRekBank = "N/A";
            } else {
                noRekBank = emp.getString("Bank Account Number");
            }
            model.addAttribute("noRekBank", noRekBank);
            model.addAttribute("bankCabang", emp.getString("Bank Cabang"));
            model.addAttribute("bankNasabah", emp.getString("Bank Nasabah"));
            Date joinDate = formatjoin.parse(emp.getString("Join Date"));
            formatjoin = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            model.addAttribute("joinDate", formatjoin.format(joinDate));
            model.addAttribute("position", emp.getString("Position"));
            model.addAttribute("workAssignment", wai.getWaByEmpl(id));
            model.addAttribute("project", pasi.getProByEmpl(id));
            model.addAttribute("training", tsi.getTrainByEmpl(id));
            model.addAttribute("certificateType", ctsi.getAll());
            model.addAttribute("certification", csi.getCertifByEmpl(id));
            model.addAttribute("category", cssi.getAll());
            model.addAttribute("skill", ssi.getAll());
            model.addAttribute("technical",tcsi.getTechByEmpl(id));
            
        }
        return "requester/profile/employeeProfile";
    }

    @RequestMapping(value = "/saveChangeRequest", method = RequestMethod.POST)
    public String saveChangeRequest(
            @RequestParam(value = "nikEmpModal", required = false) String nikEmp,
            @RequestParam(value = "nameEmpModal", required = false) String nameEmp,
            @RequestParam(value = "tanggalLahirModal", required = false) String tanggalLahir,
            @RequestParam(value = "tempatLahirModal", required = false) String tempatLahir,
            @RequestParam(value = "marriageStatusModal", required = false) String marriageStatus,
            @RequestParam(value = "genderModal", required = false) String genderEmp,
            @RequestParam(value = "addressStreetModal", required = false) String addressStreet,
            @RequestParam(value = "addressCityModal", required = false) String addressCity,
            @RequestParam(value = "hpEmpModal", required = false) String hpEmp,
            @RequestParam(value = "hpEmergencyModal", required = false) String hpEmergency,
            @RequestParam(value = "ktpModal", required = false) String ktp,
            @RequestParam(value = "npwpEmpModal", required = false) String npwpEmp,
            @RequestParam(value = "bpjsEmpMOdal", required = false) String bpjsEmp,
            @RequestParam(value = "bpjstkEmpModal", required = false) String bpjstkEmp,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "bankName", required = false) String bankName,
            @RequestParam(value = "noRekBank", required = false) String noRekBank,
            @RequestParam(value = "bankCabang", required = false) String bankCabang,
            @RequestParam(value = "bankNasabah", required = false) String bankNasabah,
            @RequestParam(value = "joinDate", required = false) Date joinDate,
            @RequestParam(value = "position", required = false) String position
    ) throws ParseException {

        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nik);

        SimpleDateFormat formatDate1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date dateAfter = formatDate1.parse(tanggalLahir);
        SimpleDateFormat formatDate2 = new SimpleDateFormat("dd-MM-yyyy");
        String dateBirth = formatDate2.format(dateAfter);
        Date tglLahir = new SimpleDateFormat("dd-MM-yyyy").parse(dateBirth);

        if (Integer.parseInt(idnya) == nik) {

            TbMEmployee employee = new TbMEmployee(Integer.parseInt(nikEmp), nameEmp, tglLahir, tempatLahir, marriageStatus, genderEmp, addressStreet,
                    addressCity, hpEmp, hpEmergency, ktp, npwpEmp, bpjsEmp, bpjstkEmp, email, bankName, noRekBank, bankCabang, bankNasabah,
                    joinDate, position);
            esi.saveEmployee(employee);

            TbTrLogEmployee logEmp = new TbTrLogEmployee(employee, timeAdd6Hours(), "Waiting");
            lesi.saveLogEmployee(logEmp);

//            sendMailRequest("ezraip98@gmail.com", "Asri Setiowati", nameEmp, nikEmp);
            sendMailRequestHR("Admin.HR@mii.co.id", "Admin HR", nameEmp, nikEmp);
            sendMailRequest("Asri.Setiowati@mii.co.id", "Asri Setiowati", nameEmp, nikEmp);

        } else {
            return "redirect:/login?logout";
        }

        return "redirect:/profile/" + nik;
    }

    public Date timeAdd6Hours() {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 7);
        System.out.println("Penambahan" + calendar.getTime());
        return calendar.getTime();
    }

    public void sendMailRequest(String email, String name, String empName, String nik) {
        System.out.println(" >>>>> MAIL REQUEST");

        try {
            emailService.sendNotificationChangeSummaryRequest(email, name, empName, nik);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sendMailRequestHR(String email, String name, String empName, String nik) {
        System.out.println(" >>>>> MAIL REQUEST");

        try {
            emailService.sendNotificationChangeSummaryRequestHR(email, name, empName, nik);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    //Work Assignment
    @PostMapping("/addWorkAssignment")
    public String addWorkAssignment(@ModelAttribute("tb_tr_workassignment") TbTrWorkassignment workassignment,
            @RequestParam("company") String company,
            @RequestParam("position") String position,
            @RequestParam("description") String description,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate)
            throws ParseException {
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            String sDate1 = startDate;
            String sDate2 = endDate;
            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
            Date date2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate2);

            workassignment.setCompany(company);
            workassignment.setPosition(position);
            workassignment.setDescription(description);
            workassignment.setStartDate(date1);
            workassignment.setEndDate(date2);
            workassignment.setEmplNik(nik);
            workassignment.setIsdeleted(false);

            wai.save(workassignment);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @PostMapping("/updateWorkAssignment")
    public String updateWorkAssignment(
            @RequestParam(value = "id") Integer id,
            @RequestParam("company") String company,
            @RequestParam("position") String position,
            @RequestParam("description") String description,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate
    ) throws ParseException {
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }
        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            String sDate1 = startDate;
            String sDate2 = endDate;
            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
            Date date2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate2);

            TbTrWorkassignment work = wai.getById(id);
            work.setCompany(company);
            work.setPosition(position);
            work.setDescription(description);
            work.setStartDate(date1);
            work.setEndDate(date2);
            work.setIsdeleted(false);
            wai.save(work);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @RequestMapping("/deleteWorkAssignment")
    public String delete(@RequestParam(value = "id") Integer id) {
        int nik = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());
        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }
        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            TbTrWorkassignment workassignment = wai.getById(id);
            workassignment.setIsdeleted(true);
            wai.save(workassignment);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    //Project
    @PostMapping("/addProject")
    public String addProject(@ModelAttribute("tb_tr_project") TbTrProject project,
            @RequestParam("name") String name,
            @RequestParam("company") String company,
            @RequestParam("role") String role,
            @RequestParam("description") String description,
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) throws ParseException {
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            String sDate1 = startDate;
            String sDate2 = endDate;
            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
            Date date2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate2);

            project.setName(name);
            project.setCompany(company);
            project.setRole(role);
            project.setDescription(description);
            project.setStartDate(date1);
            project.setEndDate(date2);
            project.setEmplNik(nik);
            project.setIsdeleted(false);

            pasi.save(project);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @RequestMapping("/deleteProject")
    public String deleteProject(@RequestParam(value = "id") Integer id) {
        int nik = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());
        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }
        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            TbTrProject project = pasi.getById(id);
            project.setIsdeleted(true);
            pasi.save(project);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

//    @RequestMapping(value = "/updateProject", method = RequestMethod.POST)
//    public String updateProject(@RequestParam("id") int id,
//            @RequestParam("name") String name,
//            @RequestParam("company") String company,
//            @RequestParam("role") String role,
//            @RequestParam("description") String description,
//            @RequestParam("startDate") String startDate,
//            @RequestParam("endDate") String endDate) throws ParseException {
//
//        int nik = 0;
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());
//
//        if (emp != null) {
//            nik = emp.getInt("Employee_Nik");
//        } else {
//            return "redirect:/login?logout";
//        }
//
//        idnya = Integer.toString(nik);
//        if (Integer.parseInt(idnya) == nik) {
//            TbTrProject project = pasi.getById(id);
//            String sDate1 = startDate;
//            String sDate2 = endDate;
//            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
//            Date date2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate2);
//
//            project.setName(name);
//            project.setCompany(company);
//            project.setRole(role);
//            project.setDescription(description);
//            project.setStartDate(date1);
//            project.setEndDate(date2);
//            //project.setIsdeleted(false);
//
//            pasi.save(project);
//        } else {
//            return "redirect:/login?logout";
//        }
//        return "redirect:/profile/" + nik;
//
//    }
//
//    @GetMapping(value = "/editProject/ajax/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody
//    String getProjectById(@PathVariable("id") Integer id){
//        JSONObject json = new JSONObject();
//        TbTrProject project = pasi.getById(id);
//        
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
//         if (project != null) {
//
//            json.put("id", project.getId());
//            json.put("name", project.getName());
//            json.put("company", project.getCompany());
//            json.put("role", project.getRole());
//            json.put("description", project.getDescription());
//            String strDate = formatter.format(project.getStartDate());
//            json.put("startDate", strDate);
//
//            String endate = formatter.format(project.getEndDate());
//            json.put("endDate", endate);
//        }else {
//            json.put("id", "");
//            json.put("name", "");
//            json.put("company", "");
//            json.put("role", "");
//            json.put("description", "");
//            json.put("startdate", "");
//            json.put("endate", "");
//            
//        }
//        return json.toString();  
//    }
    //Training
    @PostMapping("/addTraining")
    public String addTraining(@ModelAttribute("tb_tr_training") TbTrTraining training,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("date") String date,
            @RequestParam("company") String company
    ) throws ParseException {
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
//            String sDate1 = date;
//            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
            SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            Date date1 = format.parse(date);

            training.setName(name);
            training.setDescription(description);
            training.setDate(date1);
            training.setCompany(company);
            training.setEmplNik(nik);
            training.setIsdeleted(false);

            tsi.save(training);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @RequestMapping("/deleteTraining")
    public String deleteTraining(@RequestParam(value = "id") Integer id) {
        int nik = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());
        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }
        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            TbTrTraining training = tsi.getById(id);
            training.setIsdeleted(true);
            tsi.save(training);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @RequestMapping("loadTrainingById/{id}")
    public @ResponseBody
    String loadTrainingById(@PathVariable("id") Integer id) {
        Gson gson = new Gson();
        System.out.println("ce");
        return gson.toJson(tsi.findTrainingById(id));
    }

//    @RequestMapping(value = "/updateTraining", method = RequestMethod.POST)
//    public String updateTraining(@ModelAttribute("tb_tr_training") TbTrTraining training,
//            @RequestParam("name") String name,
//            @RequestParam("description") String description,
//            @RequestParam("date") String date,
//            @RequestParam("company") String company
//    ) throws ParseException {
//        int nik = 0;
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());
//
//        if (emp != null) {
//            nik = emp.getInt("Employee_Nik");
//        } else {
//            return "redirect:/login?logout";
//        }
//
//        idnya = Integer.toString(nik);
//        if (Integer.parseInt(idnya) == nik){
//        String sDate1 = date;
//            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
//
//            training.setName(name);
//            training.setDescription(description);
//            training.setDate(date1);
//            training.setCompany(company);
//            training.setIsdeleted(false);
//            
//            tsi.save(training);
//        }else{
//            return "redirect:/login?logout";
//        }
//        return "redirect:/profile/" + nik;
//    }
//    @GetMapping(value = "/editTraining/ajax/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody
//    String getTrainingById(@PathVariable("id") Integer id){
//        JSONObject json = new JSONObject();
//        TbTrTraining training = tsi.findTrainingById(id);
//        System.out.println("teee");
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
//        
//         if (training != null){
//         json.put("id", training.getId());
//         json.put("name", training.getName());
//         json.put("description", training.getDescription());
//         String date1 = formatter.format(training.getDate());
//         json.put("date", date1);
//         json.put("company", training.getCompany());
//         }else {
//            json.put("id", "");
//            json.put("name", "");
//            json.put("description", "");
//            json.put("date", "");
//            json.put("company", "");
//         }
//        return json.toString();
//    }
    //Certification
    @PostMapping("/addCertification")
    public String addCertification(
            @RequestParam("certificateNumber") String certificateNumber,
            @RequestParam("softFile") MultipartFile file,
            @RequestParam("certificateDate") String certificateDate,
            @RequestParam("expiredDate") String expiredDate,
            @RequestParam("institution") String institution,
            @RequestParam("certificate") Integer idcertificate
    ) throws ParseException, IOException {
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            String sDate1 = certificateDate;
            String sDate2 = expiredDate;
            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
            Date date2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate2);
            TbTrCertification ttec = new TbTrCertification();

            if (!file.isEmpty()) {
                byte[] bs = file.getBytes();
                ttec.setSoftFile(bs);
                System.out.println("cer" + file.getOriginalFilename());
                ttec.setFileName(file.getOriginalFilename());
            }
            ttec.setCertificate(new TbMCertificateType(idcertificate));
            ttec.setCertificateNumber(certificateNumber);
            ttec.setCertificateDate(date1);
            ttec.setExpiredDate(date2);
            ttec.setFileName(file.getOriginalFilename());
            ttec.setInstitution(institution);
            ttec.setEmplNik(nik);
            csi.save(ttec);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @RequestMapping("/deleteCertification")
    public String deleteCertification(@RequestParam(value = "id") Integer id) {
        int nik = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());
        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }
        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            TbTrCertification certification = csi.getById(id);
            certification.setIsdeleted(true);
            csi.save(certification);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @RequestMapping(value = "/updateCertification", method = RequestMethod.POST)
    public String saveCertification(
            @RequestParam("certificateNumber") String certificateNumber,
            @RequestParam("id") Integer id,
            @RequestParam("softFile") MultipartFile file,
            @RequestParam("certificateDate") String certificateDate,
            @RequestParam("expiredDate") String expiredDate,
            @RequestParam("institution") String institution,
            @RequestParam("certificate") Integer idcertificate
    ) throws ParseException, IOException {
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            String sDate1 = certificateDate;
            String sDate2 = expiredDate;
            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
            Date date2 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate2);
            TbTrCertification ttec = csi.getById(id);

            if (!file.isEmpty()) {
                byte[] bs = file.getBytes();
                ttec.setSoftFile(bs);
                System.out.println("cer" + file.getOriginalFilename());
                ttec.setFileName(file.getOriginalFilename());
            }
            ttec.setCertificate(new TbMCertificateType(idcertificate));
            ttec.setCertificateNumber(certificateNumber);
            ttec.setCertificateDate(date1);
            ttec.setExpiredDate(date2);
            ttec.setFileName(file.getOriginalFilename());
            ttec.setInstitution(institution);
            csi.save(ttec);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

//    @GetMapping(value = "/editCertificate/ajax/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
//    public @ResponseBody
//    String getCerById(@PathVariable("id") Integer id) {
//
//        JSONObject json = new JSONObject();
//        TbTrCertification certification = csi.getById(id);
//
//        if (certification != null) {
//            Date date = new Date();
//            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
//
//            json.put("id", certification.getId());
//            json.put("certificateNumber", certification.getCertificateNumber());
//            String certificateDate = formatter.format(certification.getCertificate());
//            json.put("certificateDate", certificateDate);
//            String expiredDate = formatter.format(certification.getExpiredDate());
//            json.put("expiredDate", expiredDate);
//            json.put("certificate", certification.getCertificate().getId());
//            json.put("fileName", certification.getFileName());
//            json.put("institution", certification.getInstitution());
//        } else {
//            json.put("id", "");
//            json.put("certificateNumber", "");
//            json.put("certificateDate", "");
//            json.put("expiredDate", "");
//            json.put("certificate", "");
//            json.put("fileName", "");
//            json.put("institution", "");
//        }
//        return json.toString();
//    }

    //delete Technical
    @RequestMapping("/deleteTechnical")
    public String deleteTechnical(@RequestParam(value = "id") Integer id) {
        int nik = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());
        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }
        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
            TbTrTechnical technical = tcsi.getById(id);
            technical.setIsdeleted(true);
            tcsi.save(technical);

        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    //add technical
    @PostMapping("/addTechnical")
    public String addTechnical(
            @RequestParam("skill") Integer skill
    ) throws ParseException, IOException {
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
//            String sDate1 = date;
//            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
            TbTrTechnical technical = new TbTrTechnical();
            technical.setSkill(new TbMSkill(skill));
            technical.setEmplNik(nik);
            technical.setIsdeleted(false);
            tcsi.save(technical);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @PostMapping("/updateTechnical")
    public String updateTechnical(
            @RequestParam(value = "id") Integer id,
            @RequestParam(value = "skill") Integer skill
    ) throws ParseException, IOException {
        int nik = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        JSONObject emp = serviceApi.findEmployeeByEmail(auth.getName());

        if (emp != null) {
            nik = emp.getInt("Employee_Nik");
        } else {
            return "redirect:/login?logout";
        }

        idnya = Integer.toString(nik);
        if (Integer.parseInt(idnya) == nik) {
//            String sDate1 = date;
//            Date date1 = new SimpleDateFormat("dd MMMM yyyy", Locale.US).parse(sDate1);
            TbTrTechnical technical = tcsi.getById(id);
            technical.setSkill(new TbMSkill(skill));
            technical.setIsdeleted(false);
            tcsi.save(technical);
        } else {
            return "redirect:/login?logout";
        }
        return "redirect:/profile/" + nik;
    }

    @GetMapping(value = "loadSkillByCategory/{id}")
    public @ResponseBody
    String loadSkillByCategory(@PathVariable("id") Integer id) {

        Gson gson = new Gson();
        System.out.println("ce");
        return gson.toJson(ssi.findByCategory(id));
    }

}
