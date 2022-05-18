/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "tb_m_employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMEmployee.findAll", query = "SELECT t FROM TbMEmployee t")
    , @NamedQuery(name = "TbMEmployee.findByEmplNik", query = "SELECT t FROM TbMEmployee t WHERE t.emplNik = :emplNik")
    , @NamedQuery(name = "TbMEmployee.findByName", query = "SELECT t FROM TbMEmployee t WHERE t.name = :name")
    , @NamedQuery(name = "TbMEmployee.findByNoKtp", query = "SELECT t FROM TbMEmployee t WHERE t.noKtp = :noKtp")
    , @NamedQuery(name = "TbMEmployee.findByNoNpwp", query = "SELECT t FROM TbMEmployee t WHERE t.noNpwp = :noNpwp")
    , @NamedQuery(name = "TbMEmployee.findByBpjsKetenagakerjaanNumber", query = "SELECT t FROM TbMEmployee t WHERE t.bpjsKetenagakerjaanNumber = :bpjsKetenagakerjaanNumber")
    , @NamedQuery(name = "TbMEmployee.findByBpjsKesehatanNumber", query = "SELECT t FROM TbMEmployee t WHERE t.bpjsKesehatanNumber = :bpjsKesehatanNumber")
    , @NamedQuery(name = "TbMEmployee.findByBankAccountNumber", query = "SELECT t FROM TbMEmployee t WHERE t.bankAccountNumber = :bankAccountNumber")
    , @NamedQuery(name = "TbMEmployee.findByBankName", query = "SELECT t FROM TbMEmployee t WHERE t.bankName = :bankName")
    , @NamedQuery(name = "TbMEmployee.findByBankCabang", query = "SELECT t FROM TbMEmployee t WHERE t.bankCabang = :bankCabang")
    , @NamedQuery(name = "TbMEmployee.findByBankNasabah", query = "SELECT t FROM TbMEmployee t WHERE t.bankNasabah = :bankNasabah")
    , @NamedQuery(name = "TbMEmployee.findByEmail", query = "SELECT t FROM TbMEmployee t WHERE t.email = :email")
    , @NamedQuery(name = "TbMEmployee.findByJoinDate", query = "SELECT t FROM TbMEmployee t WHERE t.joinDate = :joinDate")
    , @NamedQuery(name = "TbMEmployee.findByDateofbirth", query = "SELECT t FROM TbMEmployee t WHERE t.dateofbirth = :dateofbirth")
    , @NamedQuery(name = "TbMEmployee.findByPlaceofbirth", query = "SELECT t FROM TbMEmployee t WHERE t.placeofbirth = :placeofbirth")
    , @NamedQuery(name = "TbMEmployee.findByAddressStreet", query = "SELECT t FROM TbMEmployee t WHERE t.addressStreet = :addressStreet")
    , @NamedQuery(name = "TbMEmployee.findByAddressCity", query = "SELECT t FROM TbMEmployee t WHERE t.addressCity = :addressCity")
    , @NamedQuery(name = "TbMEmployee.findByAddressPostcode", query = "SELECT t FROM TbMEmployee t WHERE t.addressPostcode = :addressPostcode")
    , @NamedQuery(name = "TbMEmployee.findByMarriageStatus", query = "SELECT t FROM TbMEmployee t WHERE t.marriageStatus = :marriageStatus")
    , @NamedQuery(name = "TbMEmployee.findByHp", query = "SELECT t FROM TbMEmployee t WHERE t.hp = :hp")
    , @NamedQuery(name = "TbMEmployee.findByHpEmergency", query = "SELECT t FROM TbMEmployee t WHERE t.hpEmergency = :hpEmergency")
    , @NamedQuery(name = "TbMEmployee.findByGender", query = "SELECT t FROM TbMEmployee t WHERE t.gender = :gender")
    , @NamedQuery(name = "TbMEmployee.findByPosition", query = "SELECT t FROM TbMEmployee t WHERE t.position = :position")
    , @NamedQuery(name = "TbMEmployee.findByEmplIsactive", query = "SELECT t FROM TbMEmployee t WHERE t.emplIsactive = :emplIsactive")
    , @NamedQuery(name = "TbMEmployee.findByDivision", query = "SELECT t FROM TbMEmployee t WHERE t.division = :division")})
public class TbMEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "empl_nik")
    private Integer emplNik;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 40)
    @Column(name = "no_ktp")
    private String noKtp;
    @Size(max = 40)
    @Column(name = "no_npwp")
    private String noNpwp;
    @Size(max = 15)
    @Column(name = "bpjs_ketenagakerjaan_number")
    private String bpjsKetenagakerjaanNumber;
    @Size(max = 15)
    @Column(name = "bpjs_kesehatan_number")
    private String bpjsKesehatanNumber;
    @Size(max = 50)
    @Column(name = "bank_account_number")
    private String bankAccountNumber;
    @Size(max = 100)
    @Column(name = "bank_name")
    private String bankName;
    @Size(max = 100)
    @Column(name = "bank_cabang")
    private String bankCabang;
    @Size(max = 100)
    @Column(name = "bank_nasabah")
    private String bankNasabah;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Column(name = "join_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date joinDate;
    @Column(name = "dateofbirth")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateofbirth;
    @Size(max = 40)
    @Column(name = "placeofbirth")
    private String placeofbirth;
    @Size(max = 60)
    @Column(name = "address_street")
    private String addressStreet;
    @Size(max = 40)
    @Column(name = "address_city")
    private String addressCity;
    @Column(name = "address_postcode")
    private Integer addressPostcode;
    @Size(max = 20)
    @Column(name = "marriage_status")
    private String marriageStatus;
    @Size(max = 14)
    @Column(name = "hp")
    private String hp;
    @Size(max = 14)
    @Column(name = "hp_emergency")
    private String hpEmergency;
    @Size(max = 20)
    @Column(name = "gender")
    private String gender;
    @Size(max = 255)
    @Column(name = "position")
    private String position;
    @Column(name = "empl_isactive")
    private Integer emplIsactive;
    @Size(max = 100)
    @Column(name = "division")
    private String division;
    @OneToMany(mappedBy = "emplNik", fetch = FetchType.LAZY)
    private List<TbTrLogEmployee> tbTrLogEmployeeList;

    public TbMEmployee() {
    }

    public TbMEmployee(int empl_nik, String name, Date dateofbirth, String placeofbirth, String mariage_status, String gender, String address_street, String addressCity,
            String hp, String hp_emergency, String no_ktp, String no_npwp, String bpjs_kesehatan_number, String bpjs_ketenagakerjaan_number, String email, String bankName, 
            String noRekBank, String bankCabang, String bankNasabah, Date joinDate, String position) {
        this.emplNik = empl_nik;
        this.name = name;
        this.dateofbirth = dateofbirth;
        this.placeofbirth = placeofbirth;
        this.marriageStatus = mariage_status;
        this.gender = gender;
        this.addressStreet = address_street;
        this.addressCity = addressCity;
        this.hp = hp;
        this.hpEmergency = hp_emergency;
        this.noKtp = no_ktp;
        this.noNpwp = no_npwp;
        this.bpjsKesehatanNumber = bpjs_kesehatan_number;
        this.bpjsKetenagakerjaanNumber = bpjs_ketenagakerjaan_number;
        this.email = email;
        this.bankName = bankName;
        this.bankAccountNumber = noRekBank;
        this.bankCabang = bankCabang;
        this.bankNasabah = bankNasabah;
        this.joinDate = joinDate;
        this.position = position;
    }

    public TbMEmployee(Integer emplNik) {
        this.emplNik = emplNik;
    }

    public Integer getEmplNik() {
        return emplNik;
    }

    public void setEmplNik(Integer emplNik) {
        this.emplNik = emplNik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoKtp() {
        return noKtp;
    }

    public void setNoKtp(String noKtp) {
        this.noKtp = noKtp;
    }

    public String getNoNpwp() {
        return noNpwp;
    }

    public void setNoNpwp(String noNpwp) {
        this.noNpwp = noNpwp;
    }

    public String getBpjsKetenagakerjaanNumber() {
        return bpjsKetenagakerjaanNumber;
    }

    public void setBpjsKetenagakerjaanNumber(String bpjsKetenagakerjaanNumber) {
        this.bpjsKetenagakerjaanNumber = bpjsKetenagakerjaanNumber;
    }

    public String getBpjsKesehatanNumber() {
        return bpjsKesehatanNumber;
    }

    public void setBpjsKesehatanNumber(String bpjsKesehatanNumber) {
        this.bpjsKesehatanNumber = bpjsKesehatanNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCabang() {
        return bankCabang;
    }

    public void setBankCabang(String bankCabang) {
        this.bankCabang = bankCabang;
    }

    public String getBankNasabah() {
        return bankNasabah;
    }

    public void setBankNasabah(String bankNasabah) {
        this.bankNasabah = bankNasabah;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getPlaceofbirth() {
        return placeofbirth;
    }

    public void setPlaceofbirth(String placeofbirth) {
        this.placeofbirth = placeofbirth;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public Integer getAddressPostcode() {
        return addressPostcode;
    }

    public void setAddressPostcode(Integer addressPostcode) {
        this.addressPostcode = addressPostcode;
    }

    public String getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(String marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getHpEmergency() {
        return hpEmergency;
    }

    public void setHpEmergency(String hpEmergency) {
        this.hpEmergency = hpEmergency;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getEmplIsactive() {
        return emplIsactive;
    }

    public void setEmplIsactive(Integer emplIsactive) {
        this.emplIsactive = emplIsactive;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emplNik != null ? emplNik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbMEmployee)) {
            return false;
        }
        TbMEmployee other = (TbMEmployee) object;
        if ((this.emplNik == null && other.emplNik != null) || (this.emplNik != null && !this.emplNik.equals(other.emplNik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMEmployee[ emplNik=" + emplNik + " ]";
    }
    
}
