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
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author alexa
 */
@Entity
@Table(name = "tb_m_employee_transportation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMEmployeeTransportation.findAll", query = "SELECT t FROM TbMEmployeeTransportation t")
    , @NamedQuery(name = "TbMEmployeeTransportation.findById", query = "SELECT t FROM TbMEmployeeTransportation t WHERE t.id = :id")
    , @NamedQuery(name = "TbMEmployeeTransportation.findByNik", query = "SELECT t FROM TbMEmployeeTransportation t WHERE t.nik = :nik")
    , @NamedQuery(name = "TbMEmployeeTransportation.findByTransportationType", query = "SELECT t FROM TbMEmployeeTransportation t WHERE t.transportationType = :transportationType")
    , @NamedQuery(name = "TbMEmployeeTransportation.findByStnkName", query = "SELECT t FROM TbMEmployeeTransportation t WHERE t.stnkName = :stnkName")
    , @NamedQuery(name = "TbMEmployeeTransportation.findByIsDeleted", query = "SELECT t FROM TbMEmployeeTransportation t WHERE t.isDeleted = :isDeleted")
    , @NamedQuery(name = "TbMEmployeeTransportation.findByCreatedDate", query = "SELECT t FROM TbMEmployeeTransportation t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TbMEmployeeTransportation.findByModifiedDate", query = "SELECT t FROM TbMEmployeeTransportation t WHERE t.modifiedDate = :modifiedDate")})
public class TbMEmployeeTransportation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nik")
    private int nik;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "transportation_type")
    private String transportationType;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "stnk")
    private byte[] stnk;
    @Basic(optional = false)
    @NotNull
    @Column(name = "stnk_name")
    private int stnkName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public TbMEmployeeTransportation() {
    }

    public TbMEmployeeTransportation(String id) {
        this.id = id;
    }

  public TbMEmployeeTransportation(String id, int nik, String transportationType, byte[] stnk, int stnkName, boolean isDeleted, Date createdDate, Date modifiedDate) {
        this.id = id;
        this.nik = nik;
        this.transportationType = transportationType;
        this.stnk = stnk;
        this.stnkName = stnkName;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
    public TbMEmployeeTransportation(String id, int nik, String transportationType, byte[] stnk, int stnkName, boolean isDeleted) {
        this.id = id;
        this.nik = nik;
        this.transportationType = transportationType;
        this.stnk = stnk;
        this.stnkName = stnkName;
        this.isDeleted = isDeleted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNik() {
        return nik;
    }

    public void setNik(int nik) {
        this.nik = nik;
    }

    public String getTransportationType() {
        return transportationType;
    }

    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    public byte[] getStnk() {
        return stnk;
    }

    public void setStnk(byte[] stnk) {
        this.stnk = stnk;
    }

    public int getStnkName() {
        return stnkName;
    }

    public void setStnkName(int stnkName) {
        this.stnkName = stnkName;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbMEmployeeTransportation)) {
            return false;
        }
        TbMEmployeeTransportation other = (TbMEmployeeTransportation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMEmployeeTransportation[ id=" + id + " ]";
    }
    
}
