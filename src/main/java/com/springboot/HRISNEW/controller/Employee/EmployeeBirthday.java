/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.controller.Employee;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.configurations.EmailService;
import static com.springboot.HRISNEW.controller.requester.LeaveRequestController.userId;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author adhin
 */
@Controller
public class EmployeeBirthday {
    
    @Autowired
    ServiceApi serviceApi;
    
    @Autowired
    private EmailService emailservice;
    
    @GetMapping("/employeeBirthday")
    public String EmployeeBirthday(Model model) {
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
        List<JSONObject> listEmployee = serviceApi.employeeBirthday();
        List<Object[]> emplBirthday = new ArrayList<>();
        for (JSONObject jSONObject : listEmployee) {
            Object[] objects = new Object[7];
            objects[0] = jSONObject.getInt("NIK"); 
            objects[1] = jSONObject.getString("EmployeeName"); 
            objects[2] = jSONObject.getString("EmployeeEmail"); 
            objects[3] = jSONObject.getString("RmName"); 
            objects[4] = jSONObject.getString("EmailRm"); 
            objects[5] = jSONObject.getString("CustomerName"); 
            objects[6] = jSONObject.getString("BirthdayDate"); 

            emplBirthday.add(objects);
        }
        
        model.addAttribute("emplBirthday", emplBirthday);
        return "employee/employeeBirthday";
    }
    
    @PostMapping(value = "/sendBirthdayMail")
    public String sendEmployeeBirthday() {
        
        List<JSONObject> listEmployee = serviceApi.employeeBirthday();
        List<Object[]> emplBirthday = new ArrayList<>();
        for (JSONObject jSONObject : listEmployee) {
            Object[] objects = new Object[7];
            objects[0] = jSONObject.getInt("NIK"); 
            objects[1] = jSONObject.getString("EmployeeName"); 
            objects[2] = jSONObject.getString("EmployeeEmail"); 
            objects[3] = jSONObject.getString("RmName"); 
            objects[4] = jSONObject.getString("EmailRm"); 
            objects[5] = jSONObject.getString("CustomerName"); 
            objects[6] = jSONObject.getString("BirthdayDate"); 
            emplBirthday.add(objects);
            
            System.out.println(Arrays.toString(objects));
            
            try {
                emailservice.sendBirthdayNotification(objects[2].toString(), objects[1].toString(), objects[4].toString());
            } catch (Exception e) {
                System.out.println("Error Sending Email: " + e.getMessage());
            }
        }
        

        return "redirect:/employeeBirthday";
    }
    
}
