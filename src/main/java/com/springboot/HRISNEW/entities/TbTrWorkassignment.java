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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author RAR
 */
@Entity
@Table(name = "tb_tr_workassignment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrWorkassignment.findAll", query = "SELECT t FROM TbTrWorkassignment t")
    , @NamedQuery(name = "TbTrWorkassignment.findById", query = "SELECT t FROM TbTrWorkassignment t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrWorkassignment.findByCompany", query = "SELECT t FROM TbTrWorkassignment t WHERE t.company = :company")
    , @NamedQuery(name = "TbTrWorkassignment.findByPosition", query = "SELECT t FROM TbTrWorkassignment t WHERE t.position = :position")
    , @NamedQuery(name = "TbTrWorkassignment.findByDescription", query = "SELECT t FROM TbTrWorkassignment t WHERE t.description = :description")
    , @NamedQuery(name = "TbTrWorkassignment.findByStartDate", query = "SELECT t FROM TbTrWorkassignment t WHERE t.startDate = :startDate")
    , @NamedQuery(name = "TbTrWorkassignment.findByEndDate", query = "SELECT t FROM TbTrWorkassignment t WHERE t.endDate = :endDate")
    , @NamedQuery(name = "TbTrWorkassignment.findByIsdeleted", query = "SELECT t FROM TbTrWorkassignment t WHERE t.isdeleted = :isdeleted")})
public class TbTrWorkassignment implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "company")
    private String company;
    @Basic(optional = false)
    @NotNull()
    @Size(min = 1, max = 255)
    @Column(name = "position")
    private String position;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 355)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "empl_nik")
    private Integer emplNik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isdeleted")
    private boolean isdeleted;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public TbTrWorkassignment() {
    }

    public TbTrWorkassignment(Integer id) {
        this.id = id;
    }

    public TbTrWorkassignment(Integer id, String company, String position, String description, Date startDate, Date endDate, boolean isdeleted) {
        this.id = id;
        this.company = company;
        this.position = position;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isdeleted = isdeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof TbTrWorkassignment)) {
            return false;
        }
        TbTrWorkassignment other = (TbTrWorkassignment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrWorkassignment[ id=" + id + " ]";
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getEmplNik() {
        return emplNik;
    }

    public void setEmplNik(Integer emplNik) {
        this.emplNik = emplNik;
    }

    public boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    
}
