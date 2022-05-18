/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Dharta
 */
public class SakuraAPI {

    private String dateofbirth;

    private boolean gender;
    
    private String phone;

    private String join_date;

    private String address_street;

    private String no_ktp;

    private String name;

    private String position;

    private String customer_name;

    private String placeofbirth;
    
    private int empl_nik;

    private String email;

    public SakuraAPI(){
        
    }
    
    public SakuraAPI(String dateofbirth ,boolean gender,String phone, String join_date, String address_street, String no_ktp, String name, String position, String customer_name , String placeofbirth, int empl_nik, String email) {
        this.dateofbirth = dateofbirth;
        this.gender = gender;
        this.phone = phone;
        this.join_date = join_date;
        this.address_street = address_street;
        this.no_ktp = no_ktp;
        this.name = name; 
        this.position = position;
        this.customer_name = customer_name;
        this.placeofbirth = placeofbirth;
        this.empl_nik = empl_nik;
        this.email = email;
    }
    
    public String getdateofbirth() {
        return dateofbirth;
    }

    public void setdateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public boolean getgender() {
        return gender;
    }

    public void setgender(boolean gender) {
        this.gender = gender;
    }

    public String getphone() {
        return phone;
    }

    public void setphone(String phone) {
        this.phone = phone;
    }
    
    public String getjoin_date() {
        return join_date;
    }

    public void setjoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getaddress_street() {
        return address_street;
    }

    public void setaddress_street(String address_street) {
        this.address_street = address_street;
    }

    public String getno_ktp() {
        return no_ktp;
    }

    public void setno_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getposition() {
        return position;
    }

    public void setposition(String position) {
        this.position = position;
    }

    public String getcustomer_name() {
        return customer_name;
    }

    public void setcustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    
     public String placeofbirth() {
        return placeofbirth;
    }

    public void setplaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public int getempl_nik() {
        return empl_nik;
    }

    public void setempl_nik(int empl_nik) {
        this.empl_nik = empl_nik;
    }

    public String getemail() {
        return email;
    }

    public void setemail(String email) {
        this.email = email;
    }

}
