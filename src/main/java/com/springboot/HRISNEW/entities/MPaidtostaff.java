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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HARRY-PC
 */
@Entity
@Table(name = "m_paidtostaff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MPaidtostaff.findAll", query = "SELECT m FROM MPaidtostaff m")
    , @NamedQuery(name = "MPaidtostaff.findByPaidToStaffID", query = "SELECT m FROM MPaidtostaff m WHERE m.paidToStaffID = :paidToStaffID")
    , @NamedQuery(name = "MPaidtostaff.findByPaidToStaff", query = "SELECT m FROM MPaidtostaff m WHERE m.paidToStaff = :paidToStaff")
    , @NamedQuery(name = "MPaidtostaff.findByCreateBy", query = "SELECT m FROM MPaidtostaff m WHERE m.createBy = :createBy")
    , @NamedQuery(name = "MPaidtostaff.findByCreateDate", query = "SELECT m FROM MPaidtostaff m WHERE m.createDate = :createDate")
    , @NamedQuery(name = "MPaidtostaff.findByUpdateBy", query = "SELECT m FROM MPaidtostaff m WHERE m.updateBy = :updateBy")
    , @NamedQuery(name = "MPaidtostaff.findByUpdateDate", query = "SELECT m FROM MPaidtostaff m WHERE m.updateDate = :updateDate")})
public class MPaidtostaff implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PaidToStaffID")
    private Integer paidToStaffID;
    @Size(max = 50)
    @Column(name = "PaidToStaff")
    private String paidToStaff;
    @Size(max = 100)
    @Column(name = "CreateBy")
    private String createBy;
    @Column(name = "CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 100)
    @Column(name = "UpdateBy")
    private String updateBy;
    @Column(name = "UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @OneToMany(mappedBy = "paidToStaffID", fetch = FetchType.LAZY)
    private List<TrSoovertime> trSoovertimeList;

    public MPaidtostaff() {
    }

    public MPaidtostaff(Integer paidToStaffID) {
        this.paidToStaffID = paidToStaffID;
    }

    public Integer getPaidToStaffID() {
        return paidToStaffID;
    }

    public void setPaidToStaffID(Integer paidToStaffID) {
        this.paidToStaffID = paidToStaffID;
    }

    public String getPaidToStaff() {
        return paidToStaff;
    }

    public void setPaidToStaff(String paidToStaff) {
        this.paidToStaff = paidToStaff;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @XmlTransient
    public List<TrSoovertime> getTrSoovertimeList() {
        return trSoovertimeList;
    }

    public void setTrSoovertimeList(List<TrSoovertime> trSoovertimeList) {
        this.trSoovertimeList = trSoovertimeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paidToStaffID != null ? paidToStaffID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MPaidtostaff)) {
            return false;
        }
        MPaidtostaff other = (MPaidtostaff) object;
        if ((this.paidToStaffID == null && other.paidToStaffID != null) || (this.paidToStaffID != null && !this.paidToStaffID.equals(other.paidToStaffID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.MPaidtostaff[ paidToStaffID=" + paidToStaffID + " ]";
    }
    
}
