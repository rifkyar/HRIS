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
import com.springboot.HRISNEW.entities.TbTrVaccine;
import com.springboot.HRISNEW.implementservices.DosesTypeServiceImpl;
import com.springboot.HRISNEW.implementservices.ParamemtersServiceImpl;
import com.springboot.HRISNEW.implementservices.TbTrVaccineServiceImpl;
import com.springboot.HRISNEW.implementservices.VaccineTypeServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
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
 * @author RAR
 */
@Controller
public class VaccineAdmController {

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
    @Autowired
    @Qualifier("jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

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

    @GetMapping("/historyVaccineAdm")
    public String HistoryVaccination(Model model) {
        System.out.println("run history vaksin admin");
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
            model.addAttribute("nik", joUser.getInt("Employee_Nik"));
        }
        SimpleDateFormat formatdate = new SimpleDateFormat("dd MMMM yyyy");
        List<Object[]> HistoryVaksinRM = new ArrayList<>();
        for (Object[] object : tbTrVaccineServImp.getUnderRm(joUser.getInt("User_Id"))) {
            Object[] objects = new Object[9];
            objects[0] = object[0]; //id
            objects[1] = object[1]; //empnik
            objects[2] = object[2]; // name
            objects[3] = object[3]; // vaccname
            objects[4] = object[4]; // dose
            objects[5] = formatdate.format(object[5]); // date
            objects[6] = object[6]; // so
            objects[7] = object[7]; // date
            objects[8] = object[8]; // Loca

            HistoryVaksinRM.add(objects);
        }
        List<Object[]> PreviousDosesHistory = new ArrayList<>();
        for (Object[] object : tbTrVaccineServImp.getPreviousDose()) {
            Object[] objects = new Object[9];
            objects[0] = object[0]; //id
            objects[1] = object[1]; //emp
            objects[2] = object[2]; //vacname
            objects[3] = object[3]; //dose
            objects[4] = object[4]; //loc
            objects[5] = object[5]; //date

            PreviousDosesHistory.add(objects);
        }
        model.addAttribute("PrevHis", PreviousDosesHistory);
        model.addAttribute("historyVaksinRm", HistoryVaksinRM);
        return "approval/Vaccine/VaccineHistoryAdm";
    }

    @RequestMapping(value = "/download/SertifikatVaccine/{id}")
    public void downloadFileMCURecomendation(@PathVariable("id") int id,
            HttpServletResponse response) throws SQLException, IOException, JSchException, SftpException {
        
        String url = "/opt/tomcat/webapps/uploadedFiles/"; //folder sftp
        TbTrVaccine trVaccine = tbTrVaccineServImp.findOne(id);

        response.setHeader("Content-Disposition", "inline;filename=\"" + trVaccine.getCertificateName() + "\"");
        OutputStream out = response.getOutputStream();
        response.setContentType("application/vnd");

        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();

        String remoteDir = url + trVaccine.getCertificatePath();
        IOUtils.copy(channelSftp.get(remoteDir), out);
        channelSftp.exit();

        out.flush();
        out.close();

    }

    //Report
    @RequestMapping(value = "/ReportVaccination/{id}/Export", method = RequestMethod.GET)
    public @ResponseBody
    void ExportVaksin(
            HttpServletResponse response,
            @PathVariable("id") int id) throws JRException,
            IOException, SQLException {
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        InputStream jasperStream = this.getClass().getResourceAsStream("/templates/report/ReportVaccination.jrxml");
        Map<String, Object> params = new HashMap();
        params.put("id", id);
        JasperDesign jd = JRXmlLoader.load(jasperStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jd);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, conn);
        response.setContentType("application/x-xlsx");
        response.setHeader("Content-Disposition", " inline; filename= Report_Vaksinasi_" + id + ".xlsx");
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
