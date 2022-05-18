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
 * @author RAR
 */
@Entity
@Table(name = "tb_tr_certification")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrCertification.findAll", query = "SELECT t FROM TbTrCertification t")
    , @NamedQuery(name = "TbTrCertification.findById", query = "SELECT t FROM TbTrCertification t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrCertification.findByCertificateNumber", query = "SELECT t FROM TbTrCertification t WHERE t.certificateNumber = :certificateNumber")
    , @NamedQuery(name = "TbTrCertification.findByFileName", query = "SELECT t FROM TbTrCertification t WHERE t.fileName = :fileName")
    , @NamedQuery(name = "TbTrCertification.findByCertificateDate", query = "SELECT t FROM TbTrCertification t WHERE t.certificateDate = :certificateDate")
    , @NamedQuery(name = "TbTrCertification.findByExpiredDate", query = "SELECT t FROM TbTrCertification t WHERE t.expiredDate = :expiredDate")
    , @NamedQuery(name = "TbTrCertification.findByInstitution", query = "SELECT t FROM TbTrCertification t WHERE t.institution = :institution")
    , @NamedQuery(name = "TbTrCertification.findByEmplNik", query = "SELECT t FROM TbTrCertification t WHERE t.emplNik = :emplNik")
    , @NamedQuery(name = "TbTrCertification.findByIsdeleted", query = "SELECT t FROM TbTrCertification t WHERE t.isdeleted = :isdeleted")})
public class TbTrCertification implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "certificate_number")
    private String certificateNumber;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "soft_file")
    private byte[] softFile;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "file_name")
    private String fileName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "certificate_date")
    @Temporal(TemporalType.DATE)
    private Date certificateDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "expired_date")
    @Temporal(TemporalType.DATE)
    private Date expiredDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "institution")
    private String institution;
    @Column(name = "empl_nik")
    private Integer emplNik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isdeleted")
    private boolean isdeleted;
    @JoinColumn(name = "certificate", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMCertificateType certificate;

    public TbTrCertification() {
    }

    public TbTrCertification(Integer id) {
        this.id = id;
    }

    public TbTrCertification(Integer id, String certificateNumber, byte[] softFile, String fileName, Date certificateDate, Date expiredDate, String institution, boolean isdeleted) {
        this.id = id;
        this.certificateNumber = certificateNumber;
        this.softFile = softFile;
        this.fileName = fileName;
        this.certificateDate = certificateDate;
        this.expiredDate = expiredDate;
        this.institution = institution;
        this.isdeleted = isdeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public byte[] getSoftFile() {
        return softFile;
    }

    public void setSoftFile(byte[] softFile) {
        this.softFile = softFile;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getCertificateDate() {
        return certificateDate;
    }

    public void setCertificateDate(Date certificateDate) {
        this.certificateDate = certificateDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
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

    public TbMCertificateType getCertificate() {
        return certificate;
    }

    public void setCertificate(TbMCertificateType certificate) {
        this.certificate = certificate;
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
        if (!(object instanceof TbTrCertification)) {
            return false;
        }
        TbTrCertification other = (TbTrCertification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrCertification[ id=" + id + " ]";
    }
    
}
