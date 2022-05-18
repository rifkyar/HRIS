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
@Table(name = "m_reportovertime")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MReportovertime.findAll", query = "SELECT m FROM MReportovertime m")
    , @NamedQuery(name = "MReportovertime.findById", query = "SELECT m FROM MReportovertime m WHERE m.id = :id")
    , @NamedQuery(name = "MReportovertime.findByFileName", query = "SELECT m FROM MReportovertime m WHERE m.fileName = :fileName")
    , @NamedQuery(name = "MReportovertime.findByCreateDate", query = "SELECT m FROM MReportovertime m WHERE m.createDate = :createDate")})
public class MReportovertime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 100)
    @Column(name = "file_name")
    private String fileName;
    @Lob
    @Column(name = "file")
    private byte[] file;
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    public MReportovertime() {
    }
    
    public MReportovertime(String fileName, byte[] file, Date createDate) {
        this.fileName = fileName;
        this.file = file;
        this.createDate = createDate;
    }

   
    public MReportovertime(Integer id, String fileName, byte[] file, Date createDate) {
        this.id = id;
        this.fileName = fileName;
        this.file = file;
        this.createDate = createDate;
    }

    public MReportovertime(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
        if (!(object instanceof MReportovertime)) {
            return false;
        }
        MReportovertime other = (MReportovertime) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.MReportovertime[ id=" + id + " ]";
    }
    
}
