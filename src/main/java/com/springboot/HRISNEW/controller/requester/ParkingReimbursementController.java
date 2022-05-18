/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.requester;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import static com.springboot.HRISNEW.controller.requester.LeaveRequestController.userId;
import com.springboot.HRISNEW.entities.RequestDetail;
import com.springboot.HRISNEW.entities.TbMEmployeeTransportation;
import com.springboot.HRISNEW.entities.TbMParkingLocation;
import com.springboot.HRISNEW.entities.TbMStatus;
import com.springboot.HRISNEW.entities.TbTrReimburse;
import com.springboot.HRISNEW.entities.TbTrReimburseDetail;
import com.springboot.HRISNEW.implementservices.ParkingDetailServiceImp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.text.Document;
import javax.xml.bind.DatatypeConverter;
import org.hibernate.Hibernate;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SerializationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author alexa
 */
@Controller
public class ParkingReimbursementController {

    @Autowired
    private ServiceApi serviceApi;
    @Autowired
    private ParkingDetailServiceImp pdsi;
    public static String myId;

    public String getBulanTahun(String date) throws ParseException {
        SimpleDateFormat StringToDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateGet = StringToDate.parse(date);
        DateFormat dateFormat = new SimpleDateFormat("MMMM-yyyy");
        return dateFormat.format(dateGet);
    }

    public String thousandSeparator(Object rate) {
        return String.format("Rp. %,d ,-", rate);
    }

    public int getNikRequester() {
        int nik_requester = 0;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
        JSONObject jobjUser = serviceApi.findUserByEmail(myId);
        if (joEmpl != null) {
           nik_requester = joEmpl.getInt("Employee_Nik");
        } else if (jobjUser != null) {
            nik_requester = jobjUser.getInt("User_Id");
        }
        return nik_requester;
    }

    @GetMapping("/parking/request")
    public String parkirReq(Model model) {
        String nama_requester = "";
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
            nama_requester = joEmpl.getString("Name");
            nik_requester = joEmpl.getInt("Employee_Nik");
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
//                List<JSONObject> listRM = serviceApi.getRMById(Integer.toString(jSONObject.getInt("nikrm"))); // List all data admin
//                for (JSONObject RMData : listRM) {
//                    try {
//                        model.addAttribute("RMName", RMData.getString("Name"));
//                        System.out.println("RM NAME : " + RMData.getString("Name"));
//                        
//                        model.addAttribute("NIKRM", RMData.getInt("Nik"));
//                        System.out.println("RM NAME : " + RMData.getString("Name"));
//                    } catch (Exception e) {
//                        model.addAttribute("RMName", null);
//                        model.addAttribute("NIKRM", null);
//                    }
//                }
            }
        } else if (jobjUser != null) {
            nama_requester = jobjUser.getString("Name");
            nik_requester = jobjUser.getInt("User_Id");

            List<JSONObject> listData = serviceApi.listGetUserInformation(Integer.toString(nik_requester)); // List all data admin
            for (JSONObject jSONObject : listData) {
                model.addAttribute("customer_requester", jSONObject.getString("customer_name"));
                model.addAttribute("so_id", jSONObject.getString("soid"));
                List<JSONObject> listRM = serviceApi.getRMById(Integer.toString(jSONObject.getInt("nikrm"))); // List all data admin
                for (JSONObject RMData : listRM) {
                    try {
                        model.addAttribute("RMName", RMData.getString("Name"));
                        model.addAttribute("NIKRM", RMData.getInt("Nik"));
                    } catch (Exception e) {
                        model.addAttribute("RMName", null);
                        model.addAttribute("NIKRM", null);
                    }
                }
            }
        }
        model.addAttribute("nik_requester", nik_requester);
        model.addAttribute("nama_requester", nama_requester);

//        you think it's done? hohoho get transport list pleaase!
        List<Object[]> listTransport = new ArrayList<>();
        for (Object[] object : pdsi.findTransportByNik(nik_requester)) {
            Object[] objects = new Object[5];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            listTransport.add(objects);

        }
        model.addAttribute("listTransportation", listTransport);
        //get jumlah dia request
        int jumlahRequest = 0;
        List<Object[]> listRequest = new ArrayList<>();
        for (Object[] object : pdsi.findListParkingReimbursementRequestByNik(nik_requester)) {
            jumlahRequest++;
            Object[] objects = new Object[5];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            listRequest.add(objects);

        }
        String requestID = "PARKING-" + nik_requester + jumlahRequest;
        model.addAttribute("requestID", requestID);
        model.addAttribute("nik", nik_requester);

        return "requester/parkingreimbursement/parkingReimbursementRequest";
    }

    @GetMapping("/parking/history")
    public String parkirHistory(Model model) throws ParseException {
        List<Object[]> listRequest = new ArrayList<>();
        int nik_requester = 0;
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        myId = auth.getName();
//        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
//        if (joEmpl != null) {
            // nik_requester = getNikRequester();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        model.addAttribute("roles", roles.toString());
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
        if (joEmpl != null) {
            nik_requester = joEmpl.getInt("Employee_Nik");
            System.out.println("nik: " + nik_requester);
            for (Object[] object : pdsi.findListParkingReimbursementDetailRequestByNik(nik_requester)) {
                Object[] objects = object;
                objects[1] = getBulanTahun(object[1].toString());
                objects[2] = getBulanTahun(object[2].toString());
                objects[3] = thousandSeparator(object[3]);
                listRequest.add(object);
            }
            model.addAttribute("listReimbursement", listRequest);
//        }

            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        }
        
        return "requester/parkingreimbursement/parkingReimbursementHistory";
    }

    // resubmit ticket form
    @GetMapping("/parking/resubmit/{reqid}/{id}")
    public String resubmitTicket(Model model, @PathVariable(value = "reqid") String REQID, @PathVariable(value = "id") String id_detail) throws ParseException {
        int nik_requester = 0;
        String nama_requester;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        model.addAttribute("roles", roles.toString());
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
        if (joEmpl != null) {
            List<JSONObject> listData = serviceApi.listGetUserInformation(Integer.toString(joEmpl.getInt("Employee_Nik"))); // List all data admin
            for (JSONObject jSONObject : listData) {
                model.addAttribute("customer_requester", jSONObject.getString("customer_name"));
                model.addAttribute("so_id", jSONObject.getString("soid"));
                List<JSONObject> listRM = serviceApi.getRMById(Integer.toString(jSONObject.getInt("nikrm"))); // List all data admin
                for (JSONObject RMData : listRM) {
                    try {
                        model.addAttribute("RMName", RMData.getString("Name"));
                        model.addAttribute("NIKRM", RMData.getInt("Nik"));
                    } catch (Exception e) {
                        model.addAttribute("RMName", null);
                        model.addAttribute("NIKRM", null);
                    }
                }
            }

            nama_requester = joEmpl.getString("Name");
            nik_requester = joEmpl.getInt("Employee_Nik");

            model.addAttribute("nik_requester", nik_requester);
            model.addAttribute("nama_requester", nama_requester);

            Object[] listRequest = new Object[13];
            //list transport
            List<Object[]> listTransport = new ArrayList<>();
            for (Object[] object : pdsi.findTransportByNik(nik_requester)) {
                Object[] objects = new Object[5];
                objects[0] = object[0];
                objects[1] = object[1];
                objects[2] = object[2];
                objects[3] = object[3];
                objects[4] = object[4];
                listTransport.add(objects);

            }
            model.addAttribute("listTransportation", listTransport);
            //data main
            for (Object[] object : pdsi.findListParkingReimbursementRequestByREQID(REQID)) {
                Object[] objects = object;

                String sDate = object[3].toString();
                String eDate = object[4].toString();
                objects[1] = getBulanTahun(sDate);
                objects[2] = getBulanTahun(eDate);
                listRequest = object;
            }
            model.addAttribute("dataREIM", listRequest);
            //data detail,
            List<String[]> listDetailRequest = new ArrayList<>();
            int rejectedCounter = 0;
            int counterData = 0;
            for (Object[] object : pdsi.findListParkingReimbursementDetailRequestByREQID(REQID)) {
                String[] data = new String[10];
                counterData++;
                System.out.println(object[9].toString());
                if (Integer.parseInt(object[9].toString()) == 9) {
                    System.out.println("Rejected");
                    rejectedCounter++;
                }
                data[0] = object[0].toString();
                data[2] = object[2].toString();
                data[3] = object[3].toString();
                data[4] = object[4].toString();
                data[5] = object[5].toString();
                data[6] = object[6].toString();
                data[7] = object[7].toString();
                data[8] = object[8].toString();
                data[9] = object[9].toString();
                byte[] image = pdsi.getReimById(Integer.parseInt(data[0]));
                data[1] = ConvertByteToImage(image);
                listDetailRequest.add(data);
            }
            System.out.println("r: " + rejectedCounter);
            System.out.println("c: " + counterData);
            if (rejectedCounter >= counterData) {
                System.out.println("Yes ok");
                model.addAttribute("changeDate", "yes");
            } else {
                System.out.println("No");
                model.addAttribute("changeDate", "no");
            }
            model.addAttribute("detailREIM", listDetailRequest);
            model.addAttribute("counterDetail", counterData);
            model.addAttribute("id_detail", id_detail);
            model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        }
        return "requester/parkingreimbursement/parkingReimbursementResubmit";
    }

    @GetMapping("/parking/d/{nopol}")
    public String deleteTransport(
            @PathVariable(value = "nopol") String nopol
    ) {
        System.out.println("Delete:" + nopol);
        pdsi.deleteTransportByNoPol(nopol);
        return "redirect:/parking/transport";
    }

    @GetMapping("/parking/e/{nopol}")
    public String updateTransport(
            @PathVariable(value = "nopol") String nopol,
            Model model
    ) throws IOException, SQLException {
        System.out.println("update:" + nopol);

        String nama_requester, jabatan_requester;
        int nik_requester;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        model.addAttribute("roles", roles.toString());
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
        if (joEmpl != null) {
            List<JSONObject> listData = serviceApi.listGetUserInformation(Integer.toString(joEmpl.getInt("Employee_Nik")));
            for (JSONObject jSONObject : listData) {
                model.addAttribute("customer_requester", jSONObject.getString("customer_name"));
            }
            nama_requester = joEmpl.getString("Name");
            nik_requester = joEmpl.getInt("Employee_Nik");

            model.addAttribute("nik_requester", nik_requester);
            model.addAttribute("nama_requester", nama_requester);
        }
        byte[] imageBytes = pdsi.getFileByNopol(nopol);
        model.addAttribute("pic", ConvertByteToImage(imageBytes));
        Object[] objects = new Object[5];
        for (Object[] object : pdsi.findTransportBynopol(nopol)) {
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
        }
        model.addAttribute("dataTransport", objects);
        model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        return "requester/parkingreimbursement/parkingReimbursementRegister";
    }

    @RequestMapping(value = "/parking/getListParking")
    @ResponseBody
    public List<Map<String, String>> autocomplete2(@RequestParam(value = "term", required = false, defaultValue = "") String term, Model model
    ) {

        List<Map<String, String>> listParking = new ArrayList<>();

        for (Object[] object : pdsi.findLocationParkingByName(term)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("label", object[1].toString());
            map.put("value", object[1].toString());
            map.put("owner", object[3].toString());
            map.put("contact", object[5].toString());
            map.put("park_id", object[0].toString());
            listParking.add(map);
        }

        return listParking;
    }

    @GetMapping("/parking/getimage")
    public String getImageSample(@RequestParam(value = "term", required = false, defaultValue = "") String term, Model model
    ) throws IOException {
//        BufferedImage bImage = ImageIO.read(new File("sample.jpg"));
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        ImageIO.write(bImage, "jpg", bos);
//        byte[] data = pdsi.getFileByNopol("PARKING-154020");
//        ByteArrayInputStream bis = new ByteArrayInputStream(data);
//        BufferedImage bImage2 = ImageIO.read(bis);
//        ImageIO.write(bImage2, "jpg", new File("output.jpg"));
//        Blob imageBlob = pdsi.getFileByNopol("PARKING-154020");
//        byte[] imageBytes = pdsi.getFileByNopol("PARKING-154020");
//        model.addAttribute("images", ConvertByteToImage(imageBytes));

        return "requester/parkingreimbursement/sample";
    }

    public String ConvertByteToImage(byte[] file) {
        String image = Base64.getEncoder().encodeToString(file);
        return image;
    }

    @RequestMapping(value = "/parking/resubmit/{anyParkId}/getListParking")
    @ResponseBody
    public List<Map<String, String>> autocomplete3(@RequestParam(value = "term", required = false, defaultValue = "") String term, Model model
    ) {

        List<Map<String, String>> listParking = new ArrayList<>();

        for (Object[] object : pdsi.findLocationParkingByName(term)) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("label", object[1].toString());
            map.put("value", object[1].toString());
            map.put("owner", object[3].toString());
            map.put("contact", object[5].toString());
            map.put("park_id", object[0].toString());
            listParking.add(map);
        }
        return listParking;
    }

    @RequestMapping(value = "/parking/getlistrm")
    @ResponseBody
    public List<Map<String, String>> listRMAutocomplete(@RequestParam(value = "term", required = false, defaultValue = "") String term, Model model
    ) {

        List<Map<String, String>> listRM = new ArrayList<>();
        List<JSONObject> rmList = serviceApi.listGetUserInformation(term); // List all data admin
        for (JSONObject jSONObject : rmList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("label", jSONObject.getString("name"));
            map.put("value", jSONObject.getString("name"));
            map.put("nik_rm", jSONObject.getInt("empl_nik") + "");
            listRM.add(map);
        }

        return listRM;
    }

    //contoh buat dartha
    @RequestMapping(value = "/parking/example2")
    @ResponseBody
    public List<Map<String, String>> example2(@RequestParam(value = "term", required = false, defaultValue = "") String term, Model model
    ) {
        List<Map<String, String>> customerlist = new ArrayList<>();
        List<JSONObject> rmList = serviceApi.listGetUserInformation(term); // List all data admin
        for (JSONObject jSONObject : rmList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("label", jSONObject.getString("name") + (" - ") + jSONObject.getInt("empl_nik"));
            map.put("value", jSONObject.getString("name") + (" - ") + jSONObject.getInt("empl_nik"));
            map.put("customerName", jSONObject.getString("customer_name"));
            map.put("soid", jSONObject.getString("soid"));
            map.put("soid", jSONObject.getString("soid"));
            customerlist.add(map);
        }

        return customerlist;
    }

    @RequestMapping(value = "/parking/example1")
    @ResponseBody
    public JSONObject example1(@RequestParam(value = "term", required = false, defaultValue = "") String term, Model model
    ) {
        List<Map<String, String>> customerlist = new ArrayList<>();
        JSONObject rmList = serviceApi.GetUserInformation(term);
//        for (JSONObject jSONObject : rmList) {
//            Map<String, String> map = new HashMap<String, String>();
//            map.put("label", jSONObject.getString("name") + (" - ") + jSONObject.getInt("empl_nik"));
//            map.put("value", jSONObject.getString("name") + (" - ") + jSONObject.getInt("empl_nik"));
//            map.put("customerName", jSONObject.getString("customer_name"));
//            map.put("soid", jSONObject.getString("soid"));
//            map.put("nikrm", jSONObject.getString("nikrm"));
//            customerlist.add(map);
//        }

        return rmList;
    }

    @GetMapping("/parking/transport")
    public String parkingTransport(Model model
    ) {
        int nik_requester = getNikRequester();
        int counter = 0;

//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        myId = auth.getName();
//        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
//        JSONObject joUser = serviceApi.findUserByEmail(userId);
//        if (joEmpl != null) {
//            nik_requester = joEmpl.getInt("Employee_Nik");
//        } else if (joUser != null) {
//            nik_requester = joUser.getInt("User_Id");
//        }
// System.out.println("Data NIK TRanask: "+nik_requester);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        myId = auth.getName();
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        List<String> roles = new ArrayList<String>();

        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }
        model.addAttribute("roles", roles.toString());
        JSONObject joEmpl = serviceApi.findEmployeeByEmail(myId);
        JSONObject joUser = serviceApi.findUserByEmail(userId);
        if (joEmpl != null) {
            nik_requester = joEmpl.getInt("Employee_Nik");
        } else if (joUser != null) {
            nik_requester = joUser.getInt("User_Id");
        }

        List<Object[]> listTransport = new ArrayList<>();
        for (Object[] object : pdsi.findTransportByNik(nik_requester)) {
            counter++;
            Object[] objects = new Object[5];
            objects[0] = object[0];
            objects[1] = object[1];
            objects[2] = object[2];
            objects[3] = object[3];
            objects[4] = object[4];
            listTransport.add(objects);
        }
        model.addAttribute("listTransportation", listTransport);
        model.addAttribute("totalTransport", counter);
        model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));
        return "requester/parkingreimbursement/parkingReimbursementListTransport";
    }

    @PostMapping("/parking/s/regis")
    public String parkirRegisterSave(
            Model model,
            @RequestParam("policeNumber") String police_number,
            @RequestParam("foto_stnk") MultipartFile foto_stnk,
            @RequestParam("typeTransport") String tipeKendaraan,
            @RequestParam("stnk") int namaSTNK) throws IOException, ParseException {

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

        System.out.println("Save");
        System.out.println("tipe" + tipeKendaraan);
        System.out.println("nama" + namaSTNK);
        byte[] byteStnk = foto_stnk.getBytes();
        System.out.println("foto: " + byteStnk.length);
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        if (byteStnk.length == 0) {
            System.out.println("Update without image");
            try {
                byteStnk = pdsi.getFileByNopol(police_number);
                TbMEmployeeTransportation tb_transport = new TbMEmployeeTransportation(
                        police_number, nik_requester, tipeKendaraan, byteStnk, namaSTNK, false, createdDate, createdDate);
                pdsi.saveRegis(tb_transport);
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        } else {
            try {
                TbMEmployeeTransportation tb_transport = new TbMEmployeeTransportation(
                        police_number, nik_requester, tipeKendaraan, byteStnk, namaSTNK, false, createdDate, createdDate);
                pdsi.saveRegis(tb_transport);
            } catch (Exception e) {
                System.out.println("Error" + e);
            }
        }

        return "redirect:/parking/transport";
    }

    @GetMapping("/parking/register")
    public String parkirRegister(Model model
    ) {
        String nama_requester = "", jabatan_requester = "";
//Data:{"Address Postcode":"94115","Email":"ACHMAD.AZYHARY@MS.MII.CO.ID","Hp Emergency":"null","Place of Birth":"Tarrama Tekkeng",
//"Position":"APPLICATION DEVELOPER JR.","Hp":"6285342689448","Address City":"Sigi","Bank Cabang":"Sam Ratulangi, Palu","Gender":"1",
//"No KTP":"7210011710960002","No NPWP":"917143802831000","BPJS Kesehatan":"null","Bank Name":"Mandiri","Name":"Achmad Azyhary","Employee Active":"1",
//"Employee_Nik":15402,"BPJS Ketenagakerjaan":"19054442934","Bank Account Number":"1510011443824",
//"Address Street":"Jl. Karanja Lembah lrg. Permata Hijau No. 9 RT. 002/RW. 001","Bank Nasabah":"Achmad Azyhary","Join Date":"2019-06-17 12:00:00.0",
//"Date of Birth":"1996-10-17 12:00:00.0","Marriage Status":"0"}

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
        String nik_req = "";
        if (joEmpl != null) {
            nik_req = Integer.toString(joEmpl.getInt("Employee_Nik"));
            nama_requester = joEmpl.getString("Name");
        } else if (jobjUser != null) {
            nik_req = Integer.toString(jobjUser.getInt("User_Id"));
            nama_requester = jobjUser.getString("Username");
        }
        List<JSONObject> listData = serviceApi.listGetUserInformation(nik_req); // List all data admin
        for (JSONObject jSONObject : listData) {
            model.addAttribute("customer_requester", jSONObject.getString("customer_name"));
        }

        System.out.println("Data:" + nama_requester + nik_req);
        model.addAttribute("nik_requester", nik_req);
        model.addAttribute("nama_requester", nama_requester);
        model.addAttribute("dataTransport", null);
        model.addAttribute("nik", joEmpl.getInt("Employee_Nik"));

        return "requester/parkingreimbursement/parkingReimbursementRegister";
    }

    @RequestMapping(value = "/parking/check")
    @ResponseBody
    public Boolean parkingCheck(@RequestParam(value = "no", required = false, defaultValue = "") String police_number
    ) {
        System.out.println("police" + police_number);
        Boolean isAvailable = false;
        List<Object[]> objects = pdsi.findTransportBynopol(police_number);
        if (objects.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    // check no stnk
    @RequestMapping(value = "/stnk/check")
    @ResponseBody
    public Boolean stnkCheck(@RequestParam(value = "no", required = false, defaultValue = "") String stnk_number
    ) {
        System.out.println("stnk" + stnk_number);
        Boolean isAvailable = false;
        List<Object[]> objects = pdsi.findTransportBystnk(stnk_number);
        if (objects.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
//delete parking detail

    @GetMapping("/parking/reim/c/{id}/{req_id}")
    public String cancelDetailReim(
            @PathVariable(value = "id") String id,
            @PathVariable(value = "req_id") String req_id,
            Model model
    ) throws IOException {

        int ids = Integer.parseInt(id);
        int Total = pdsi.getTotalPriceByREQID(req_id);
        int price = pdsi.getPriceREIMByID(ids);
        System.out.println("REQ DELTE: " + req_id);
        pdsi.deleteReimDetailById(ids);
        pdsi.changePriceByREQID(req_id, (Total - price));
        return "redirect:/parking/history";
    }

//resubmit ticket form
    //save ticket form
    @PostMapping("/parking/s/form")
    public String parkingTickerSave(
            @RequestParam(name = "IDREQ") String IDREQ,
            @RequestParam(name = "nama_requester") String nama_requester,
            @RequestParam(name = "nik_requester") int nik_requester,
            @RequestParam(name = "so_id") String so_id,//jadi int ya

            @RequestParam(name = "customer_requester") String customer_requester,
            @RequestParam(name = "nikRM") String nikRM,//jadiin int

            @RequestParam(name = "periodestart") String periodestart,
            @RequestParam(name = "periodeend") String periodeend,
            @RequestParam(name = "policeNumberData") List<String> policeNumberData,
            @RequestParam(name = "customerBuildingData") List<String> customerBuildingData,
            @RequestParam(name = "park_id") List<String> park_id_Data,
            @RequestParam(name = "rateData") List<String> rateData,
            @RequestParam(name = "noTelpData") List<String> noTelpData,
            @RequestParam(name = "pengelolaData") List<String> pengelolaData,
            @RequestParam(name = "fotoTicketData") List<MultipartFile> foto_ticket
    ) throws ParseException, IOException {
        String sDate = periodestart;
        String eDate = periodeend;
        SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");
        Date date6 = formatter6.parse(sDate);
        Date date7 = formatter6.parse(eDate);

//        System.out.println("IDREQ :" + IDREQ);
//        System.out.println("nama_requester :" + nama_requester);
//        System.out.println("nik_requester :" + nik_requester);
//        System.out.println("so_id :" + so_id);
//        System.out.println("customer_requester :" + customer_requester);
//        System.out.println("nikRM :" + nikRM);
//        System.out.println("periodeend :" + periodeend);
//        System.out.println("periodestart :" + periodestart);
        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        long totalRate = 0;
        int NIKRM;
        NIKRM = Integer.parseInt(nikRM);
        for (int i = 0; i < policeNumberData.size(); i++) {
            long ratePrice = Long.parseLong(rateData.get(i));
            totalRate = totalRate + ratePrice;
        }
        System.out.println("total fare: " + totalRate);
        //save reimbursement
        TbMStatus tbm_status = new TbMStatus(3);
        TbTrReimburse tb_tr_reimburse = new TbTrReimburse(
                IDREQ,
                nik_requester,
                totalRate,
                date6,
                date7,
                NIKRM,
                6715,
                so_id,
                customer_requester,
                createdDate,
                createdDate,
                tbm_status
        );
        pdsi.saveParkingReimburse(tb_tr_reimburse);
        //save reimburse detail
        for (int i = 0; i < policeNumberData.size(); i++) {

            long ratePrice = Long.parseLong(rateData.get(i));
            String Building = customerBuildingData.get(i);
            int park_id = Integer.parseInt(park_id_Data.get(i));
            String police_number = policeNumberData.get(i);
//            getReimById
            byte[] byteFoto = foto_ticket.get(i).getBytes();
            System.out.println("BYTE DATA" + byteFoto);
            TbMStatus tbm_status_detail = new TbMStatus(6);
            TbMEmployeeTransportation tbm_transport = new TbMEmployeeTransportation(police_number);
            TbMParkingLocation tbm_Parking_loc = new TbMParkingLocation(park_id);
            TbTrReimburse tbr_reimbursement = new TbTrReimburse(IDREQ);
            TbTrReimburseDetail tb_tr_reimburseDetail = new TbTrReimburseDetail(ratePrice, Building, false, createdDate, createdDate, tbr_reimbursement, tbm_transport, tbm_Parking_loc, tbm_status_detail, byteFoto);
            pdsi.saveParkingReimburseDetail(tb_tr_reimburseDetail);
        }

        return "redirect:/parking/history";
    }
    //RESUBMIT ticket form

    @PostMapping("/parking/resubmit/form")
    public String reimbursementResubmit(
            @RequestParam(name = "IDREQ") String IDREQ,
            @RequestParam(name = "nama_requester") String nama_requester,
            @RequestParam(name = "nik_requester") int nik_requester,
            @RequestParam(name = "so_id") String so_id,//jadi int ya

            @RequestParam(name = "customer_requester") String customer_requester,
            @RequestParam(name = "nikRM") String nikRM,//jadiin int

            @RequestParam(name = "periodestart") String periodestart,
            @RequestParam(name = "periodeend") String periodeend,
            @RequestParam(name = "edit_id") int edit_id,
            @RequestParam(name = "detailID") List<String> detailID,
            @RequestParam(name = "policNumber") List<String> policeNumberData,
            @RequestParam(name = "customerBuilding") List<String> customerBuildingData,
            @RequestParam(name = "park_id") List<String> park_id_Data,
            @RequestParam(name = "rate") List<String> rateData,
            //            @RequestParam(name = "noTelp") List<String> noTelpData,
            //            @RequestParam(name = "pengelola") List<String> pengelolaData
            @RequestParam(name = "foto_ticket") List<MultipartFile> foto_ticket
    ) throws ParseException, IOException {
        String sDate = periodestart;
        String eDate = periodeend;
        System.out.println("PEND: " + periodeend);
        SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd");
        Date date6 = formatter6.parse(sDate);
        Date date7 = formatter6.parse(eDate);
        System.out.println("PEND: " + date7);

        System.out.println("IDREQ :" + IDREQ);
        System.out.println("nama_requester :" + nama_requester);
        System.out.println("nik_requester :" + nik_requester);
        System.out.println("so_id :" + so_id);
        System.out.println("customer_requester :" + customer_requester);
        System.out.println("nikRM :" + nikRM);
        System.out.println("periodeend :" + date6);
        System.out.println("periodestart :" + date7);

        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
        long totalRate = 0;
        int NIKRM;
        NIKRM = Integer.parseInt(nikRM);
        System.out.println("Polic data; " + policeNumberData.size());
        for (int i = 0; i < policeNumberData.size(); i++) {
            long ratePrice = Long.parseLong(rateData.get(i));
            totalRate = totalRate + ratePrice;
        }
        System.out.println("total fare: " + totalRate);
        //save reimbursement
        TbMStatus tbm_status = new TbMStatus(3);
        TbTrReimburse tb_tr_reimburse = new TbTrReimburse(
                IDREQ,
                nik_requester,
                totalRate,
                date6,
                date7,
                NIKRM,
                6715,
                so_id,
                customer_requester,
                createdDate,
                createdDate,
                tbm_status
        );
        System.out.println("Data: " + tb_tr_reimburse.getEndPeriod());
        pdsi.saveParkingReimburse(tb_tr_reimburse);
        //save reimburse detail
        for (int i = 0; i < policeNumberData.size(); i++) {
            int detail_id = Integer.parseInt(detailID.get(i));
            if (edit_id == detail_id) {
                long ratePrice = Long.parseLong(rateData.get(i));
                String Building = customerBuildingData.get(i);
                int park_id = Integer.parseInt(park_id_Data.get(i));
                String police_number = policeNumberData.get(i);
                System.out.println(foto_ticket.size());
                byte[] byteFoto = foto_ticket.get(i).getBytes();
                if (byteFoto.length == 0) {
                    byteFoto = pdsi.getReimById(detail_id);
                }
                TbMStatus tbm_status_detail = new TbMStatus(6);
                TbMEmployeeTransportation tbm_transport = new TbMEmployeeTransportation(police_number);
                TbMParkingLocation tbm_Parking_loc = new TbMParkingLocation(park_id);
                TbTrReimburse tbr_reimbursement = new TbTrReimburse(IDREQ);
                TbTrReimburseDetail tb_tr_reimburseDetail = new TbTrReimburseDetail(detail_id, ratePrice, Building, false, createdDate, createdDate, tbr_reimbursement, tbm_transport, tbm_Parking_loc, tbm_status_detail, byteFoto);
                pdsi.saveParkingReimburseDetail(tb_tr_reimburseDetail);
            }
        }

        return "redirect:/parking/history";
    }

    @GetMapping("/listadmin")
    public String listadm() {
        System.out.println("Running pr admn Requester");

        List<JSONObject> rmList = serviceApi.listAdmin(); // List all data RM
        List<String> listRm = new ArrayList<>();
        for (JSONObject jSONObject : rmList) {
//            listRm.add(jSONObject.getInt("User_Id") + "-" + jSONObject.getString("Name"));
            System.out.println("Data1:" + jSONObject.getString("Name"));
        }
        return "requester/parkingreimbursement/parkingReimbursementRegister";
    }

//    @GetMapping(value = "/data")
//    public String data(Model model) {
////        for (Object[] readListAPIRegion: serviceAPI.readListAPIRegions("http://localhost:8087/api/v1/getallregion")){
////         System.out.println("id: "+readListAPIRegion[0]);
////     }
//        model.addAttribute("dataregion", serviceAPI.readListAPIRegions("http://localhost:8087/api/v1/getallregion"));
////        model.addAttribute("dataregion", regionService.findAll());
////        model.addAttribute("dataregion", regionService.findbyIde());
//        return "data";
//    }
}
