/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.approval;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import static com.springboot.HRISNEW.controller.requester.LeaveRequestController.userId;
import com.springboot.HRISNEW.entities.Approval;
import com.springboot.HRISNEW.entities.RequestDetail;
import com.springboot.HRISNEW.entities.RequesterInformation;
import com.springboot.HRISNEW.entities.TbTrLogEmployee;
import com.springboot.HRISNEW.implementservices.ApprovalServiceImpl;
import com.springboot.HRISNEW.implementservices.EmployeeServiceImpl;
import com.springboot.HRISNEW.implementservices.ExcelGenerator;
import com.springboot.HRISNEW.implementservices.LeaveTypeServiceImpl;
import com.springboot.HRISNEW.implementservices.LogEmployeeServiceImpl;
import com.springboot.HRISNEW.implementservices.RequestDetailServiceImp;
import com.springboot.HRISNEW.implementservices.RequesterInformServiceImpl;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
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
import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class employeeDataController {

    @Autowired
    private ServiceApi serviceApi;

    @Autowired
    private EmployeeServiceImpl esi;

    @Autowired
    private LogEmployeeServiceImpl lesi;

    @Autowired
    private RequesterInformServiceImpl risi;

    @Autowired
    private ApprovalServiceImpl asi;

    @Autowired
    LeaveTypeServiceImpl ltsi;

    @Autowired
    private EmailService emailService;

    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private RequestDetailServiceImp rdsi;

    public static int idnya;

    @GetMapping("/employeedata/{id}")
    public String employeedata(@PathVariable(value = "id") Integer id, Model model) {

        int nik;
        String role = null, accept_access;
        idnya = id;

//        System.out.println("idnya yang pertama = " + idnya);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();
        JSONObject jobjUser = serviceApi.findUserByEmail(userId);
        JSONObject user = serviceApi.userbyid(id);

        role = user.getString("Role_Id");
//        System.out.println("rolenya adalah " + role);

        if (role.equalsIgnoreCase("11") || role.equalsIgnoreCase("13")) {
            accept_access = "yes";
        } else {
            accept_access = "no";
        }

//        System.out.println("accept access >> " + accept_access);

        if (jobjUser != null) {
            nik = jobjUser.getInt("User_Id");
            model.addAttribute("nik", jobjUser.getInt("User_Id"));
            model.addAttribute("email", jobjUser.getString("Email"));

            int nikLogin = jobjUser.getInt("User_Id");
            model.addAttribute("nikLogin", nikLogin);
            String roleManager;
            List<JSONObject> listData1 = serviceApi.adminNik(); // Get data berdasarkan 
            for (JSONObject jSONObject : listData1) {
                int nikAdmin = jSONObject.getInt("Nik");
                if (nikLogin == nikAdmin) {
                    roleManager = jSONObject.getString("Role");
                    model.addAttribute("adminMSHR", roleManager);
                }
            }

        } else {
            return "redirect:/login?logout";
        }

        if (nik == id) {

//            System.out.println("ini " + esi.showPendingChangeReq());
            model.addAttribute("pendingChangeReq", esi.showPendingChangeReq());
            model.addAttribute("acceptAccess", accept_access);

            return "approval/profil/employeeData";
        } else {
            return "redirect:/login?logout";
        }

    }

    @RequestMapping(value = "/employeedatamodal/{nik}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    String employeedatamodal(@PathVariable("nik") Integer nik, Model model, HttpServletResponse response) throws IOException {

//        System.out.println("ini nik ==>" + nik);
//        System.out.println("idnya yang kedua = " + idnya);
        List<TbTrLogEmployee> pcr = lesi.selectPendingChangeReq(nik);

        JSONArray jsona = new JSONArray();
        JSONObject jsono = new JSONObject();

        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");

        for (TbTrLogEmployee tbMEmployee : pcr) {
            JSONObject jo = new JSONObject();
            jo.put("id", tbMEmployee.getId());
            jo.put("name", tbMEmployee.getEmplNik().getName());
            jo.put("dob", formatdate.format(tbMEmployee.getEmplNik().getDateofbirth()));
            jo.put("pob", tbMEmployee.getEmplNik().getPlaceofbirth());
            jo.put("marriage", tbMEmployee.getEmplNik().getMarriageStatus());
            jo.put("gender", tbMEmployee.getEmplNik().getGender());
            jo.put("addressStreet", tbMEmployee.getEmplNik().getAddressStreet());
            jo.put("addressCity", tbMEmployee.getEmplNik().getAddressCity());
            jo.put("hp", tbMEmployee.getEmplNik().getHp());
            jo.put("hpEmergency", tbMEmployee.getEmplNik().getHpEmergency());
            jo.put("ktp", tbMEmployee.getEmplNik().getNoKtp());
            jo.put("npwp", tbMEmployee.getEmplNik().getNoNpwp());
            jo.put("bpjs", tbMEmployee.getEmplNik().getBpjsKesehatanNumber());
            jo.put("bpjskt", tbMEmployee.getEmplNik().getBpjsKetenagakerjaanNumber());
            jo.put("email", tbMEmployee.getEmplNik().getEmail());

            jsona.put(jo);

        }
        jsono.put("data", jsona);

        return jsono.toString();
    }

    @RequestMapping(value = "/acceptChangeRequest", method = RequestMethod.POST)
    public String saveChangeRequest(
            @RequestParam(value = "idReq", required = false) int idReq,
            @RequestParam(value = "empName2", required = false) String empName,
            @RequestParam(value = "email2", required = false) String email
    ) {
        System.out.println("idReq = " + idReq);
        System.out.println("emp name = " + empName);
        System.out.println("email = " + email);

        int nik = 0;
        String name = null;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();
        System.out.println("user id >> " + userId);
        JSONObject jobjUser = serviceApi.findUserByEmail(userId);

        if (jobjUser != null) {
            nik = jobjUser.getInt("User_Id");
            name = jobjUser.getString("Name");
        } else {
            return "redirect:/login?logout";
        }

        System.out.println("namenya adalah " + name);
        if (nik == idnya) {
            TbTrLogEmployee findRequest = lesi.selectbyId(idReq);
            findRequest.setStatus("Done");
            lesi.doneChangeSummary(findRequest);

            sendMailDone(email, empName);
        }

        return "redirect:/employeedata/" + idnya;
    }

    public void sendMailDone(String email, String name) {
        System.out.println(" >>>>> MAIL DONE");

        try {
            emailService.sendNotificationChangeSummaryDone(email, name);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @GetMapping("/reqInfo/{id}")
    public String reqInfo(@PathVariable(value = "id") Integer id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JSONObject user = serviceApi.findUserByEmail(authentication.getName());
        if (id != user.getInt("User_Id")) {
            return "redirect:/homeAdm";
        } else if (user != null) {
            int nikLogin = user.getInt("User_Id");
            model.addAttribute("nikLogin", nikLogin);
            String roleManager;
            List<JSONObject> listData1 = serviceApi.adminNik(); // Get data berdasarkan 
            for (JSONObject jSONObject : listData1) {
                int nikAdmin = jSONObject.getInt("Nik");
                if (nikLogin == nikAdmin) {
                    roleManager = jSONObject.getString("Role");
                    model.addAttribute("adminMSHR", roleManager);
                }
            }

            List<Object[]> listReqInfo = new ArrayList<>();
            for (Object[] object : risi.viewAllReqInfo()) {
                Object[] list = new Object[9];
                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                list[0] = object[0];
                list[1] = object[1];
                list[2] = object[2];
                list[3] = format.format(object[3]);
                list[4] = object[4];
                list[5] = format.format(object[5]);
                list[6] = object[6];
                listReqInfo.add(list);
            }

            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
            model.addAttribute("listReqInfo", listReqInfo);
        }
        return "approval/profil/requestInformation";
    }

    @GetMapping("/leaveHistory/{id}")
    public String historyLeave(@PathVariable(value = "id") String nik, Model model) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            JSONObject user = serviceApi.findUserByEmail(authentication.getName());

            int nikLogin = user.getInt("User_Id");
            model.addAttribute("nikLogin", nikLogin);
            String roleManager;
            List<JSONObject> listData1 = serviceApi.adminNik(); // Get data berdasarkan 
            for (JSONObject jSONObject : listData1) {
                int nikAdmin = jSONObject.getInt("Nik");
                if (nikLogin == nikAdmin) {
                    roleManager = jSONObject.getString("Role");
                    model.addAttribute("adminMSHR", roleManager);
                }
            }

            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            List<Object[]> listHistoryLeave = new ArrayList<>();
            for (Object[] object : asi.findListLeaveRequest(Integer.parseInt(nik))) {
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
            model.addAttribute("nikEmp", nik);
            model.addAttribute("listLeaveHistory", listHistoryLeave);
            model.addAttribute("email", user.getString("Email"));
            model.addAttribute("nik", user.getInt("User_Id"));
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return "approval/profil/historyLeave";
    }

    //Excel Download Employee Leave
    @RequestMapping(value = "/downloadRequesterInformation", method = RequestMethod.GET)
    public @ResponseBody
    void downloadExcelListReqInfo(HttpServletResponse response) throws JRException, IOException, SQLException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        Map<String, Object> params = new HashMap();
        InputStream jasperStream = this.getClass().getResourceAsStream("/templates/report/ReportRequesterInformation.jrxml");
        JasperDesign jd = JRXmlLoader.load(jasperStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jd);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        response.setContentType("application/x-xlsx");
        response.setHeader("Content-Disposition", " inline; filename= Data_Leave_Employee.xlsx");
        final OutputStream outStream = response.getOutputStream();
        ArrayList<JasperPrint> sheets = new ArrayList<JasperPrint>();
        sheets.add(jasperPrint);
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(sheets));

        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(false);
        configuration.setOnePagePerSheet(true);
        configuration.setCollapseRowSpan(false);
        configuration.setWhitePageBackground(false);

        configuration.setShowGridLines(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }

    @RequestMapping(value = "/leaveHistory/{id}/ExportDetails", method = RequestMethod.GET)
    public @ResponseBody
    void LeaveDetails(
            HttpServletResponse response,
            @PathVariable("id") int id)throws JRException,
            IOException, SQLException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        InputStream jasperStream = this.getClass().getResourceAsStream("/templates/report/ReportLeaveDetails.jrxml");
        Map<String, Object> params = new HashMap();
        params.put("id", id);
        JasperDesign jd = JRXmlLoader.load(jasperStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jd);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        response.setContentType("application/x-xlsx");
        response.setHeader("Content-Disposition", " inline; filename= Data_Leave_"+ id +".xlsx");
        final OutputStream outStream = response.getOutputStream();
        ArrayList<JasperPrint> sheets = new ArrayList<JasperPrint>();
        sheets.add(jasperPrint);
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setExporterInput(SimpleExporterInput.getInstance(sheets));

        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));

        SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
        configuration.setDetectCellType(false);
        configuration.setOnePagePerSheet(true);
        configuration.setCollapseRowSpan(false);
        configuration.setWhitePageBackground(false);

        configuration.setShowGridLines(true);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        
    }
}
