/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.Vaccine;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import com.springboot.HRISNEW.APIRepo.ServiceApi;
import static com.springboot.HRISNEW.controller.requester.LeaveRequestController.userId;
import com.springboot.HRISNEW.entities.TbMDosesType;
import com.springboot.HRISNEW.entities.TbMVaccineType;
import com.springboot.HRISNEW.entities.TbTrVaccine;
import com.springboot.HRISNEW.implementservices.DosesTypeServiceImpl;
import com.springboot.HRISNEW.implementservices.ParamemtersServiceImpl;
import com.springboot.HRISNEW.implementservices.TbTrVaccineServiceImpl;
import com.springboot.HRISNEW.implementservices.VaccineTypeServiceImpl;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author RAR
 */
@Controller
public class VaccineRequesterController {

    @Autowired
    ServiceApi serviceApi;
    @Autowired
    private VaccineTypeServiceImpl vtsi;
    @Autowired
    private DosesTypeServiceImpl dtsi;
    @Autowired
    private TbTrVaccineServiceImpl tbTrVaccineServImp;
    @Autowired
    private ParamemtersServiceImpl paramServImp;
    
    public static String myId;
//setup jsch

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
//        jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
        Session jschSession = jsch.getSession("adduploader", "103.101.224.29", 22);
        jschSession.setPassword("B0ro8uDuR-21");

        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);

        jschSession.connect();

        return (ChannelSftp) jschSession.openChannel("sftp");
    }
/////////////Requester
    @GetMapping("/vaksin")
    public String vaccination(Model model) throws ParseException {
        System.out.println("run vaksin");
        String nama_requester = "";
        int nik_requester = 0;
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
            nama_requester = joEmpl.getString("Name");
            nik_requester = joEmpl.getInt("Employee_Nik");
            System.out.println("nik piro = " + nik_requester);
            System.out.println("nama sopo = " + nama_requester);

            List<JSONObject> listData = serviceApi.listGetUserInformation(Integer.toString(nik_requester)); // List all data admin
            for (JSONObject jSONObject : listData) {
                model.addAttribute("customer_requester", jSONObject.getString("customer_name"));
                model.addAttribute("so_id", jSONObject.getString("soid"));
                String responden = jSONObject.getString("nikrm");
                JSONObject so = serviceApi.findSobyId(jSONObject.getString("soid"));
                if (responden.equalsIgnoreCase("No_Relation_Manager")) {
                    responden = so.getString("Act_Relation_Manager");
                }
                JSONObject user = serviceApi.userbyid(Integer.parseInt(responden));
                if (user != null) {
                    model.addAttribute("RMName", user.getString("Name"));
                    model.addAttribute("NIKRM", user.getInt("User_Id"));
                }

            }
            SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFormat = formatdate.parse(joEmpl.getString("Date of Birth"));
            formatdate = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            model.addAttribute("ktp", joEmpl.getString("No KTP"));
            model.addAttribute("dob", formatdate.format(dateFormat));
            model.addAttribute("name", joEmpl.getString("Name"));
            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        }
        model.addAttribute("VaccineType", vtsi.getAll());
        model.addAttribute("DosesType", dtsi.getAll());
        return "requester/vaccine/vaccineRegistration";
    }

    @PostMapping("/vaksin/save")
    public String VaksinSave(Model model, @ModelAttribute("tb_tr_vaccine") TbTrVaccine TbTrVaccine,
            @RequestParam("nik") int nik,
            @RequestParam("VaccType") int vaccType,
            @RequestParam("VaccineDate") Date vaccDate,
            @RequestParam("VaccineLocation") String vaccLoc,
            @RequestParam("DosesType") int DosType,
            @RequestParam("rm") int rm,
            @RequestParam(value = "fileVaccine", required = false) MultipartFile file
    ) throws JSchException, IOException, SftpException {
        int nik_requester = 0;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        model.addAttribute("roles", roles.toString());
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
        JSONObject jobjUser = serviceApi.findUserByEmail(myId);
        if (joEmpl != null) {
            nik_requester = joEmpl.getInt("Employee_Nik");
        } else if (jobjUser != null) {
            nik_requester = jobjUser.getInt("User_Id");
        }
        String nameEmp = joEmpl.getString("Name");
        String vdateformat = new SimpleDateFormat("dd-MMMM-yyyy").format(vaccDate);
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String saveFileName = nameEmp + "_Dosis_" + DosType + "_" + vdateformat + ".jpg"; //Rename File
        String url = "/opt/tomcat/webapps/uploadedFiles/"; //folder sftp
        filename = saveFileName;
        TbTrVaccine.setEmplNik(nik);
        TbTrVaccine.setVaccineType(new TbMVaccineType(vaccType));
        TbTrVaccine.setDate(vaccDate);
        TbTrVaccine.setLocation(vaccLoc);
        TbTrVaccine.setDosesType(new TbMDosesType(DosType));
        TbTrVaccine.setRelationManager(rm);
        //save sftp
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        System.out.println(file.getInputStream());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        String remoteDir = url + "vaksin/";
        System.out.println(remoteDir);
        channelSftp.put(file.getInputStream(), remoteDir + filename);
        channelSftp.exit();
        //
        TbTrVaccine.setCertificateName(filename);
        TbTrVaccine.setCertificatePath("vaksin/"+ filename);
        tbTrVaccineServImp.saveUp(TbTrVaccine);
        System.out.println("savefile" +saveFileName);
        System.out.println("berhasil simpan");
        return "redirect:/historyVaksin";
    }
    
    @GetMapping("/historyVaksin")
    public String HistoryVaccination(Model model) throws ParseException{
        System.out.println("run history vaksin");
        String nama_requester = "";
        int nik_requester = 0;
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
            nama_requester = joEmpl.getString("Name");
            nik_requester = joEmpl.getInt("Employee_Nik");
            System.out.println("nik piro = " + nik_requester);
            
//            List<JSONObject> listData = serviceApi.listGetUserInformation(Integer.toString(nik_requester)); // List all data admin
//            for (JSONObject jSONObject : listData) {
//                model.addAttribute("customer_requester", jSONObject.getString("customer_name"));
//                model.addAttribute("so_id", jSONObject.getString("soid"));
//                String responden = jSONObject.getString("nikrm");
//                JSONObject so = serviceApi.findSobyId(jSONObject.getString("soid"));
//                if (responden.equalsIgnoreCase("No_Relation_Manager")) {
//                    responden = so.getString("Act_Relation_Manager");
//                }
//                JSONObject user = serviceApi.userbyid(Integer.parseInt(responden));
//                if (user != null) {
//                    model.addAttribute("RMName", user.getString("Name"));
//                    model.addAttribute("NIKRM", user.getInt("User_Id"));
//                }
//
//            }
//            SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd");
//            Date dateFormat = formatdate.parse(joEmpl.getString("Date of Birth"));
//            formatdate = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
//            model.addAttribute("ktp", joEmpl.getString("No KTP"));
//            model.addAttribute("dob", formatdate.format(dateFormat));
//            model.addAttribute("name", joEmpl.getString("Name"));
            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        }
        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
        List<Object[]> HistoryVaksin = new ArrayList<>();
        for (Object[] object : tbTrVaccineServImp.getAllHistoryByNikEmp(joEmpl.getInt("Employee_Nik"))){
            Object[] objects = new Object[6];
            objects[0] = object[0]; //empnik
            objects[1] = object[1]; //name
            objects[2] = object[2]; // vac name
            objects[3] = object[3]; // doses
            objects[4] = object[4]; // location
            objects[5] = formatdate.format(object[5]); // date
            
            HistoryVaksin.add(objects);
        }
        model.addAttribute("historyVaksin", HistoryVaksin);
        return "requester/vaccine/vaccineHistoryRequester";
    }
}
