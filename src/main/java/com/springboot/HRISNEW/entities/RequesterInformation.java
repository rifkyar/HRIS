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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author HARRY-PC
 */
@Entity
@Table(name = "requester_information")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequesterInformation.findAll", query = "SELECT r FROM RequesterInformation r")
    , @NamedQuery(name = "RequesterInformation.findByNik", query = "SELECT r FROM RequesterInformation r WHERE r.nik = :nik")
    , @NamedQuery(name = "RequesterInformation.findByName", query = "SELECT r FROM RequesterInformation r WHERE r.name = :name")
    , @NamedQuery(name = "RequesterInformation.findByAvailableLeaveLastyear", query = "SELECT r FROM RequesterInformation r WHERE r.availableLeaveLastyear = :availableLeaveLastyear")
    , @NamedQuery(name = "RequesterInformation.findByExpireddateLeaveLastyear", query = "SELECT r FROM RequesterInformation r WHERE r.expireddateLeaveLastyear = :expireddateLeaveLastyear")
    , @NamedQuery(name = "RequesterInformation.findByAvailableLeaveCurrentyear", query = "SELECT r FROM RequesterInformation r WHERE r.availableLeaveCurrentyear = :availableLeaveCurrentyear")
    , @NamedQuery(name = "RequesterInformation.findByExpireddateLeaveCurrentyear", query = "SELECT r FROM RequesterInformation r WHERE r.expireddateLeaveCurrentyear = :expireddateLeaveCurrentyear")
    , @NamedQuery(name = "RequesterInformation.findByTotalavailableleave", query = "SELECT r FROM RequesterInformation r WHERE r.totalavailableleave = :totalavailableleave")
    , @NamedQuery(name = "RequesterInformation.findBySourceNik", query = "SELECT r FROM RequesterInformation r WHERE r.sourceNik = :sourceNik")
    , @NamedQuery(name = "RequesterInformation.findByFlag", query = "SELECT r FROM RequesterInformation r WHERE r.flag = :flag")})
public class RequesterInformation implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "nik")
    private Integer nik;
    @Column(name = "available_leave_lastyear")
    private Integer availableLeaveLastyear;
    @Column(name = "expireddate_leave_lastyear")
    @Temporal(TemporalType.DATE)
    private Date expireddateLeaveLastyear;
    @Column(name = "available_leave_currentyear")
    private Integer availableLeaveCurrentyear;
    @Column(name = "Total_available_leave")
    private Integer totalavailableleave;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expireddate_leave_currentyear")
    @Temporal(TemporalType.DATE)
    private Date expireddateLeaveCurrentyear;
    @Basic(optional = false)
    @NotNull
    @Column(name = "source_nik")
    private int sourceNik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flag")
    private boolean flag;
    @JoinColumn(name = "division", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Division division;
    @OneToMany(mappedBy = "nik", fetch = FetchType.LAZY)
    private List<RequestDetail> requestDetailList;

    public RequesterInformation() {
    }

    public RequesterInformation(Integer nik) {
        this.nik = nik;
    }

    public RequesterInformation(Integer nik, String name, Date expireddateLeaveCurrentyear, int sourceNik, boolean flag) {
        this.nik = nik;
        this.name = name;
        this.expireddateLeaveCurrentyear = expireddateLeaveCurrentyear;
        this.sourceNik = sourceNik;
        this.flag = flag;
    }
    
    public RequesterInformation(Integer nik, String name, Integer availableLeaveCurrentyear, Integer availableLeaveLastyear, Date expireddateLeaveCurrentyear, Date expireddateLeaveLastyear, Integer totalavailableleave,boolean flag, Division division) {
        this.expireddateLeaveCurrentyear = expireddateLeaveCurrentyear;
        this.nik = nik;
        this.name = name;
        this.availableLeaveLastyear = availableLeaveLastyear;
        this.expireddateLeaveLastyear = expireddateLeaveLastyear;
        this.availableLeaveCurrentyear = availableLeaveCurrentyear;
        this.totalavailableleave = totalavailableleave;
        this.flag=flag;
        this.division = division;
    }

    public Integer getNik() {
        return nik;
    }

    public void setNik(Integer nik) {
        this.nik = nik;
    }


    public Integer getAvailableLeaveLastyear() {
        return availableLeaveLastyear;
    }

    public void setAvailableLeaveLastyear(Integer availableLeaveLastyear) {
        this.availableLeaveLastyear = availableLeaveLastyear;
    }

    public Date getExpireddateLeaveLastyear() {
        return expireddateLeaveLastyear;
    }

    public void setExpireddateLeaveLastyear(Date expireddateLeaveLastyear) {
        this.expireddateLeaveLastyear = expireddateLeaveLastyear;
    }

    public Integer getAvailableLeaveCurrentyear() {
        return availableLeaveCurrentyear;
    }

    public void setAvailableLeaveCurrentyear(Integer availableLeaveCurrentyear) {
        this.availableLeaveCurrentyear = availableLeaveCurrentyear;
    }

    public Date getExpireddateLeaveCurrentyear() {
        return expireddateLeaveCurrentyear;
    }

    public void setExpireddateLeaveCurrentyear(Date expireddateLeaveCurrentyear) {
        this.expireddateLeaveCurrentyear = expireddateLeaveCurrentyear;
    }

    public Integer getTotalavailableleave() {
        return totalavailableleave;
    }

    public void setTotalavailableleave(Integer totalavailableleave) {
        this.totalavailableleave = totalavailableleave;
    }

    public int getSourceNik() {
        return sourceNik;
    }

    public void setSourceNik(int sourceNik) {
        this.sourceNik = sourceNik;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nik != null ? nik.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RequesterInformation)) {
            return false;
        }
        RequesterInformation other = (RequesterInformation) object;
        if ((this.nik == null && other.nik != null) || (this.nik != null && !this.nik.equals(other.nik))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.RequesterInformation[ nik=" + nik + " ]";
    }


    @XmlTransient
    public List<RequestDetail> getRequestDetailList() {
        return requestDetailList;
    }

    public void setRequestDetailList(List<RequestDetail> requestDetailList) {
        this.requestDetailList = requestDetailList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
}
