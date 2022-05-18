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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alexa
 */
@Entity
@Table(name = "tb_tr_reimburse")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrReimburse.findAll", query = "SELECT t FROM TbTrReimburse t")
    , @NamedQuery(name = "TbTrReimburse.findById", query = "SELECT t FROM TbTrReimburse t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrReimburse.findByNik", query = "SELECT t FROM TbTrReimburse t WHERE t.nik = :nik")
    , @NamedQuery(name = "TbTrReimburse.findByTotalRate", query = "SELECT t FROM TbTrReimburse t WHERE t.totalRate = :totalRate")
    , @NamedQuery(name = "TbTrReimburse.findByStartPeriod", query = "SELECT t FROM TbTrReimburse t WHERE t.startPeriod = :startPeriod")
    , @NamedQuery(name = "TbTrReimburse.findByEndPeriod", query = "SELECT t FROM TbTrReimburse t WHERE t.endPeriod = :endPeriod")
    , @NamedQuery(name = "TbTrReimburse.findByDirectReport1", query = "SELECT t FROM TbTrReimburse t WHERE t.directReport1 = :directReport1")
    , @NamedQuery(name = "TbTrReimburse.findByDirectReport2", query = "SELECT t FROM TbTrReimburse t WHERE t.directReport2 = :directReport2")
    , @NamedQuery(name = "TbTrReimburse.findBySo", query = "SELECT t FROM TbTrReimburse t WHERE t.so = :so")
    , @NamedQuery(name = "TbTrReimburse.findByCustomers", query = "SELECT t FROM TbTrReimburse t WHERE t.customers = :customers")
    , @NamedQuery(name = "TbTrReimburse.findByCreatedDate", query = "SELECT t FROM TbTrReimburse t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TbTrReimburse.findByModifiedDate", query = "SELECT t FROM TbTrReimburse t WHERE t.modifiedDate = :modifiedDate")})
public class TbTrReimburse implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "nik")
    private int nik;
    @Basic(optional = false)
    @NotNull()
    @Column(name = "total_rate")
    private long totalRate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "start_period")
    @Temporal(TemporalType.DATE)
    private Date startPeriod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "end_period")
    @Temporal(TemporalType.DATE)
    private Date endPeriod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "direct_report1")
    private int directReport1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "direct_report2")
    private int directReport2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "so")
    private String so;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "customers")
    private String customers;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reimburse", fetch = FetchType.LAZY)
    private List<TbTrApprovalReimburse> tbTrApprovalReimburseList;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id")
    private String id;
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMStatus status;

    public TbTrReimburse() {
    }

    public TbTrReimburse(String id) {
        this.id = id;
    }

    public TbTrReimburse(String id, int nik, long totalRate, Date startPeriod, Date endPeriod, int directReport1, int directReport2, String so, String customers, Date createdDate, Date modifiedDate, TbMStatus status) {
        this.id = id;
        this.nik = nik;
        this.totalRate = totalRate;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.directReport1 = directReport1;
        this.directReport2 = directReport2;
        this.so = so;
        this.customers = customers;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.status = status;
    }

    public TbTrReimburse(String id, int nik, long totalRate, Date startPeriod, Date endPeriod, int directReport1, int directReport2, String so, String customers, Date createdDate, Date modifiedDate) {
        this.id = id;
        this.nik = nik;
        this.totalRate = totalRate;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.directReport1 = directReport1;
        this.directReport2 = directReport2;
        this.so = so;
        this.customers = customers;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public long getTotalRate() {
        return totalRate;
    }

    public void setTotalRate(long totalRate) {
        this.totalRate = totalRate;
    }

    public Date getStartPeriod() {
        return startPeriod;
    }

    public void setStartPeriod(Date startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Date getEndPeriod() {
        return endPeriod;
    }

    public void setEndPeriod(Date endPeriod) {
        this.endPeriod = endPeriod;
    }

    public int getDirectReport1() {
        return directReport1;
    }

    public void setDirectReport1(int directReport1) {
        this.directReport1 = directReport1;
    }

    public int getDirectReport2() {
        return directReport2;
    }

    public void setDirectReport2(int directReport2) {
        this.directReport2 = directReport2;
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
        if (!(object instanceof TbTrReimburse)) {
            return false;
        }
        TbTrReimburse other = (TbTrReimburse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrReimburse[ id=" + id + " ]";
    }

    public int getNik() {
        return nik;
    }

    public void setNik(int nik) {
        this.nik = nik;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public String getCustomers() {
        return customers;
    }

    public void setCustomers(String customers) {
        this.customers = customers;
    }
    @XmlTransient
    public List<TbTrApprovalReimburse> getTbTrApprovalReimburseList() {
        return tbTrApprovalReimburseList;
    }

    public void setTbTrApprovalReimburseList(List<TbTrApprovalReimburse> tbTrApprovalReimburseList) {
        this.tbTrApprovalReimburseList = tbTrApprovalReimburseList;
    }
    
}
