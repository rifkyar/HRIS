/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.configurations;

import com.springboot.HRISNEW.APIRepo.ServiceApi;
import com.springboot.HRISNEW.entities.ThisUser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

/**
 *
 * @author HARRY-PC
 */
@Controller
public class UserDetailsServiceImp implements UserDetailsService{
    
    @Autowired
    private ServiceApi serviceApi;
    
    @Autowired
    private SecurityConfig securityConfig;
    
    @Autowired
    private CustomSuccessHandler successHandler;
    
    JSONObject usrlgn, emplgn, checkEmp, CheckEmplLogin, addEmp, addUser, checkContract;
    int nik;
    String pwd = null, role = null;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("loadUserByUsername " + username);

        ThisUser user = userConfig(username);

        User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(user.getPassword());
            builder.roles(user.getRoles());
//            System.out.println("User builder = "+username+" Password = "+user.getPassword()+" roles = "+user.getRoles());
        } else {
//            throw new UsernameNotFoundException("User not found.");
            System.out.println("User Salah");
        }
        return builder.build();
    }
    
    private ThisUser userConfig(String username) {

       
        System.out.println("User config = " + username);
        usrlgn = serviceApi.SELECTUSERBYEMAIL(username);
        if (usrlgn != null) {
            usrlgn = serviceApi.SELECTUSERBYEMAIL(username);
            if (usrlgn != null) {
                username = usrlgn.getString("Email");
                pwd = usrlgn.getString("Password");
                role = usrlgn.getString("Authorities");
                System.out.println("User Find " + username+ "Authorities = "+role);
            } else {
                System.out.println("Access Denied");
            }
        } else if (usrlgn == null) {
            checkEmp = serviceApi.findEmployeeByEmail(username);
            System.out.println("CHECK EMP = " + checkEmp);
            checkContract = serviceApi.lastKontrak(username);
            System.out.println("CHECK Contract = " + checkContract);
            if (checkEmp != null && checkContract!=null) {
                nik = checkEmp.getInt("Employee_Nik");
                CheckEmplLogin = serviceApi.findEmpLoginById(Integer.toString(nik));
                if (CheckEmplLogin != null) {
                    emplgn = serviceApi.SELECTEMPLBYEMAIL(username);
                    username = emplgn.getString("Email");
                    pwd = emplgn.getString("Password");
                    role = emplgn.getString("Authorities");
                    System.out.println("Employee Find " + username + " Password = " + pwd);
                } 
            } else {
                System.out.println("Access Denied");
            }
        } else {
            System.out.println("Access Denied");
        }

        if (username.equalsIgnoreCase(username)) {
            System.out.println("------------------- Employee Find " + username + " Password = " + pwd+" role = "+role);
            return new ThisUser(username, pwd, role);
        }
        return null;
    }
}
