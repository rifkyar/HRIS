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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HARRY-PC
 */
@Entity
@Table(name = "tb_tr_announcement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrAnnouncement.findAll", query = "SELECT t FROM TbTrAnnouncement t")
    , @NamedQuery(name = "TbTrAnnouncement.findById", query = "SELECT t FROM TbTrAnnouncement t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrAnnouncement.findByJudul", query = "SELECT t FROM TbTrAnnouncement t WHERE t.judul = :judul")})
public class TbTrAnnouncement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 255)
    @Column(name = "judul")
    private String judul;
    @Lob
    @Size(max = 65535)
    @Column(name = "information")
    private String information;
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "createDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Column(name = "isactive")
    private Boolean isactive;
    
    public TbTrAnnouncement() {
    }

    public TbTrAnnouncement(Integer id) {
        this.id = id;
    }

    public TbTrAnnouncement(Integer id, String judul) {
        this.id = id;
        this.judul = judul;
    }

    public TbTrAnnouncement(Integer id, String judul, String information, Date startDate, Date endDate, Date createDate, Boolean isactive) {
        this.id = id;
        this.judul = judul;
        this.information = information;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.isactive = isactive;
    }

    public TbTrAnnouncement(String judul, String information, Date startDate, Date endDate, Date createDate, Boolean isactive) {
        this.judul = judul;
        this.information = information;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.isactive = isactive;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
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
        if (!(object instanceof TbTrAnnouncement)) {
            return false;
        }
        TbTrAnnouncement other = (TbTrAnnouncement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrAnnouncement[ id=" + id + " ]";
    }

}
