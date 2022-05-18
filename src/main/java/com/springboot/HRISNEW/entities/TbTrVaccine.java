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
 * @author RAR
 */
@Entity
@Table(name = "tb_tr_vaccine")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrVaccine.findAll", query = "SELECT t FROM TbTrVaccine t")
    , @NamedQuery(name = "TbTrVaccine.findById", query = "SELECT t FROM TbTrVaccine t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrVaccine.findByEmplNik", query = "SELECT t FROM TbTrVaccine t WHERE t.emplNik = :emplNik")
    , @NamedQuery(name = "TbTrVaccine.findByDate", query = "SELECT t FROM TbTrVaccine t WHERE t.date = :date")
    , @NamedQuery(name = "TbTrVaccine.findByLocation", query = "SELECT t FROM TbTrVaccine t WHERE t.location = :location")
    , @NamedQuery(name = "TbTrVaccine.findByCertificateName", query = "SELECT t FROM TbTrVaccine t WHERE t.certificateName = :certificateName")
    , @NamedQuery(name = "TbTrVaccine.findByCertificatePath", query = "SELECT t FROM TbTrVaccine t WHERE t.certificatePath = :certificatePath")
    , @NamedQuery(name = "TbTrVaccine.findByRelationManager", query = "SELECT t FROM TbTrVaccine t WHERE t.relationManager = :relationManager")})
public class TbTrVaccine implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "empl_nik")
    private int emplNik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Location")
    private String location;
    @Size(max = 100)
    @Column(name = "CertificateName")
    private String certificateName;
    @Size(max = 100)
    @Column(name = "CertificatePath")
    private String certificatePath;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RelationManager")
    private int relationManager;
    @JoinColumn(name = "DosesType", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMDosesType dosesType;
    @JoinColumn(name = "VaccineType", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMVaccineType vaccineType;

    public TbTrVaccine() {
    }

    public TbTrVaccine(Integer id) {
        this.id = id;
    }

    public TbTrVaccine(Integer id, int emplNik, Date date, String location, int relationManager) {
        this.id = id;
        this.emplNik = emplNik;
        this.date = date;
        this.location = location;
        this.relationManager = relationManager;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getEmplNik() {
        return emplNik;
    }

    public void setEmplNik(int emplNik) {
        this.emplNik = emplNik;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificatePath() {
        return certificatePath;
    }

    public void setCertificatePath(String certificatePath) {
        this.certificatePath = certificatePath;
    }

    public int getRelationManager() {
        return relationManager;
    }

    public void setRelationManager(int relationManager) {
        this.relationManager = relationManager;
    }

    public TbMDosesType getDosesType() {
        return dosesType;
    }

    public void setDosesType(TbMDosesType dosesType) {
        this.dosesType = dosesType;
    }

    public TbMVaccineType getVaccineType() {
        return vaccineType;
    }

    public void setVaccineType(TbMVaccineType vaccineType) {
        this.vaccineType = vaccineType;
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
        if (!(object instanceof TbTrVaccine)) {
            return false;
        }
        TbTrVaccine other = (TbTrVaccine) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrVaccine[ id=" + id + " ]";
    }
    
}
