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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author adhin
 */
@Entity
@Table(name = "tb_m_mejakerja")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMMejakerja.findAll", query = "SELECT t FROM TbMMejakerja t")
    , @NamedQuery(name = "TbMMejakerja.findById", query = "SELECT t FROM TbMMejakerja t WHERE t.id = :id")
    , @NamedQuery(name = "TbMMejakerja.findByEmplNik", query = "SELECT t FROM TbMMejakerja t WHERE t.emplNik = :emplNik")
    , @NamedQuery(name = "TbMMejakerja.findByEmail", query = "SELECT t FROM TbMMejakerja t WHERE t.email = :email")
    , @NamedQuery(name = "TbMMejakerja.findByName", query = "SELECT t FROM TbMMejakerja t WHERE t.name = :name")
    , @NamedQuery(name = "TbMMejakerja.findByTrainingTitle", query = "SELECT t FROM TbMMejakerja t WHERE t.trainingTitle = :trainingTitle")
    , @NamedQuery(name = "TbMMejakerja.findByTrainingTime", query = "SELECT t FROM TbMMejakerja t WHERE t.trainingTime = :trainingTime")
    , @NamedQuery(name = "TbMMejakerja.findByUploadDate", query = "SELECT t FROM TbMMejakerja t WHERE t.uploadDate = :uploadDate")})
public class TbMMejakerja implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "empl_nik")
    private Integer emplNik;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "email")
    private String email;
    @Size(max = 255)
    @Column(name = "name")
    private String name;
    @Size(max = 200)
    @Column(name = "training_title")
    private String trainingTitle;
    @Column(name = "training_time")
    @Temporal(TemporalType.DATE)
    private Date trainingTime;
    @Column(name = "upload_date")
    @Temporal(TemporalType.DATE)
    private Date uploadDate;

    public TbMMejakerja() {
    }

    public TbMMejakerja(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmplNik() {
        return emplNik;
    }

    public void setEmplNik(Integer emplNik) {
        this.emplNik = emplNik;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public Date getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(Date trainingTime) {
        this.trainingTime = trainingTime;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
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
        if (!(object instanceof TbMMejakerja)) {
            return false;
        }
        TbMMejakerja other = (TbMMejakerja) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMMejakerja[ id=" + id + " ]";
    }
    
}
