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
@Table(name = "m_officehour")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MOfficehour.findAll", query = "SELECT m FROM MOfficehour m")
    , @NamedQuery(name = "MOfficehour.findByOfficeHourID", query = "SELECT m FROM MOfficehour m WHERE m.officeHourID = :officeHourID")
    , @NamedQuery(name = "MOfficehour.findByOfficeHour", query = "SELECT m FROM MOfficehour m WHERE m.officeHour = :officeHour")
    , @NamedQuery(name = "MOfficehour.findByCreateBy", query = "SELECT m FROM MOfficehour m WHERE m.createBy = :createBy")
    , @NamedQuery(name = "MOfficehour.findByCreateDate", query = "SELECT m FROM MOfficehour m WHERE m.createDate = :createDate")
    , @NamedQuery(name = "MOfficehour.findByUpdateBy", query = "SELECT m FROM MOfficehour m WHERE m.updateBy = :updateBy")
    , @NamedQuery(name = "MOfficehour.findByUpdateDate", query = "SELECT m FROM MOfficehour m WHERE m.updateDate = :updateDate")})
public class MOfficehour implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OfficeHourID")
    private Integer officeHourID;
    @Size(max = 20)
    @Column(name = "OfficeHour")
    private String officeHour;
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
    @OneToMany(mappedBy = "officeHourID", fetch = FetchType.LAZY)
    private List<TrSoovertime> trSoovertimeList;

    public MOfficehour() {
    }

    public MOfficehour(Integer officeHourID) {
        this.officeHourID = officeHourID;
    }

    public Integer getOfficeHourID() {
        return officeHourID;
    }

    public void setOfficeHourID(Integer officeHourID) {
        this.officeHourID = officeHourID;
    }

    public String getOfficeHour() {
        return officeHour;
    }

    public void setOfficeHour(String officeHour) {
        this.officeHour = officeHour;
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
        hash += (officeHourID != null ? officeHourID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MOfficehour)) {
            return false;
        }
        MOfficehour other = (MOfficehour) object;
        if ((this.officeHourID == null && other.officeHourID != null) || (this.officeHourID != null && !this.officeHourID.equals(other.officeHourID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.MOfficehour[ officeHourID=" + officeHourID + " ]";
    }
    
}
