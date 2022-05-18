/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Dharta
 */
@Entity
@Table(name = "tb_tr_employee_form_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrEmployeeFormDetails.findAll", query = "SELECT t FROM TbTrEmployeeFormDetails t")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findById", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByKtp", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.ktp = :ktp")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByNik", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.nik = :nik")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByName", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.name = :name")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByGender", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.gender = :gender")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByPassportNumber", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.passportNumber = :passportNumber")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByPosition", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.position = :position")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByCompany", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.company = :company")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByBirthPlace", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.birthPlace = :birthPlace")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByBirthDate", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.birthDate = :birthDate")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByCreatedDate", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByModifiedDate", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.modifiedDate = :modifiedDate")
    , @NamedQuery(name = "TbTrEmployeeFormDetails.findByJoinDate", query = "SELECT t FROM TbTrEmployeeFormDetails t WHERE t.joinDate = :joinDate")})
public class TbTrEmployeeFormDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 30)
    @Column(name = "ktp")
    private String ktp;
    @Column(name = "nik")
    private Integer nik;
    @Size(max = 100)
    @Column(name = "name")
    private String name;
    @Column(name = "gender")
    private Integer gender;
    @Size(max = 30)
    @Column(name = "passport_number")
    private String passportNumber;
    @Size(max = 50)
    @Column(name = "position")
    private String position;
    @Size(max = 100)
    @Column(name = "company")
    private String company;
    @Size(max = 100)
    @Column(name = "birth_place")
    private String birthPlace;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
//    private String birthDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "address")
    private String address;
    @Basic(optional = false)
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @Column(name = "join_date")
    @Temporal(TemporalType.DATE)
    private Date joinDate;
//    private String joinDate;
    @JoinColumn(name = "employee_form", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbTrEmployeeForm employeeForm;

    public TbTrEmployeeFormDetails() {
    }

    public TbTrEmployeeFormDetails(Integer id) {
        this.id = id;
    }

//    public TbTrEmployeeFormDetails(Integer id, Date createdDate, Date joinDate) {
//        this.id = id;
//        this.createdDate = createdDate;
//        this.joinDate = joinDate;
//    }
    public TbTrEmployeeFormDetails(String ktp, Integer nik, String name, Integer gender, String passportNumber, String position, String company, String birthPlace, Date birthDate, String address, Date createdDate, Date joinDate, TbTrEmployeeForm employeeForm) {
        this.ktp = ktp;
        this.nik = nik;
        this.name = name;
        this.gender = gender;
        this.passportNumber = passportNumber;
        this.position = position;
        this.company = company;
        this.birthPlace = birthPlace;
        this.birthDate = birthDate;
        this.address = address;
        this.createdDate = createdDate;
        this.joinDate = joinDate;
        this.employeeForm = employeeForm;
    }

    public TbTrEmployeeFormDetails(String ktp, Integer nik, String name, String position, String company, TbTrEmployeeForm employeeForm) {
        this.ktp = ktp;
        this.nik = nik;
        this.name = name;
        this.position = position;
        this.company = company;
        this.employeeForm = employeeForm;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public Integer getNik() {
        return nik;
    }

    public void setNik(Integer nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

//    public String getBirthDate() {
//        return birthDate;
//    }
//
//    public void setBirthDate(String birthDate) {
//        this.birthDate = birthDate;
//    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

//     public String getJoinDate() {
//        return joinDate;
//    }
//
//    public void setJoinDate(String joinDate) {
//        this.joinDate = joinDate;
//    }
    public TbTrEmployeeForm getEmployeeForm() {
        return employeeForm;
    }

    public void setEmployeeForm(TbTrEmployeeForm employeeForm) {
        this.employeeForm = employeeForm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbTrEmployeeFormDetails)) {
            return false;
        }
        TbTrEmployeeFormDetails other = (TbTrEmployeeFormDetails) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrEmployeeFormDetails[ id=" + id + " ]";
    }

}
