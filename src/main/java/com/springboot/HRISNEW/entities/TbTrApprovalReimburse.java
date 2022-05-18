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
 * @author alexa
 */
@Entity
@Table(name = "tb_tr_approval_reimburse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrApprovalReimburse.findAll", query = "SELECT t FROM TbTrApprovalReimburse t")
    , @NamedQuery(name = "TbTrApprovalReimburse.findById", query = "SELECT t FROM TbTrApprovalReimburse t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrApprovalReimburse.findByNik", query = "SELECT t FROM TbTrApprovalReimburse t WHERE t.nik = :nik")
    , @NamedQuery(name = "TbTrApprovalReimburse.findByCreatedDate", query = "SELECT t FROM TbTrApprovalReimburse t WHERE t.createdDate = :createdDate")})
public class TbTrApprovalReimburse implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "nik")
    private int nik;
    @Lob
    @Size(max = 65535)
    @Column(name = "remark")
    private String remark;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @JoinColumn(name = "reimburse", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbTrReimburse reimburse;
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMStatus status;

    public TbTrApprovalReimburse() {
    }

    public TbTrApprovalReimburse(Integer id,int nik, Date createdDate, TbTrReimburse reimburse, TbMStatus status) {
        this.id = id;
        this.nik = nik;
        this.createdDate = createdDate;
        this.reimburse = reimburse;
        this.status = status;
    }

    public TbTrApprovalReimburse(Integer id) {
        this.id = id;
    }

    public TbTrApprovalReimburse(Integer id, int nik, Date createdDate) {
        this.id = id;
        this.nik = nik;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNik() {
        return nik;
    }

    public void setNik(int nik) {
        this.nik = nik;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public TbTrReimburse getReimburse() {
        return reimburse;
    }

    public void setReimburse(TbTrReimburse reimburse) {
        this.reimburse = reimburse;
    }

    public TbMStatus getStatus() {
        return status;
    }

    public void setStatus(TbMStatus status) {
        this.status = status;
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
        if (!(object instanceof TbTrApprovalReimburse)) {
            return false;
        }
        TbTrApprovalReimburse other = (TbTrApprovalReimburse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrApprovalReimburse[ id=" + id + " ]";
    }
    
}
