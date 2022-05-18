/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

/**
 *
 * @author Dharta
 */
public class AdminAPI {

    private int Nik;

    private String Email;

    private String RolesDescription;

    private String RolesName;

    private int NOHP;

    private String Name;

    public AdminAPI() {

    }

    public AdminAPI(int Nik, String Email, String RolesDescription, String RolesName, int NOHP, String Name) {
        this.Nik = Nik;
        this.Email = Email;
        this.RolesDescription = RolesDescription;
        this.RolesName = RolesName;
        this.NOHP = NOHP;
        this.Name = Name;
    }

    public int getNik() {
        return Nik;
    }

    public void setNik(int Nik) {
        this.Nik = Nik;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getRolesDescription() {
        return RolesDescription;
    }

    public void setRolesDescription(String RolesDescription) {
        this.RolesDescription = RolesDescription;
    }

    public String getRolesName() {
        return RolesName;
    }

    public void setRolesName(String RolesName) {
        this.RolesName = RolesName;
    }

    public int getNOHP() {
        return NOHP;
    }

    public void setNOHP(int NOHP) {
        this.NOHP = NOHP;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
}
