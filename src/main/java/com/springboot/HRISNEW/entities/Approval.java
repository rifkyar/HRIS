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
 * @author Bintang Timur
 */
@Entity
@Table(name = "approval")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Approval.findAll", query = "SELECT a FROM Approval a")
    , @NamedQuery(name = "Approval.findById", query = "SELECT a FROM Approval a WHERE a.id = :id")
    , @NamedQuery(name = "Approval.findByRequestId", query = "SELECT a FROM Approval a WHERE a.requestId = :requestId")
    , @NamedQuery(name = "Approval.findByRequestovertimedetailId", query = "SELECT a FROM Approval a WHERE a.requestovertimedetailId = :requestovertimedetailId")
    , @NamedQuery(name = "Approval.findByUserId", query = "SELECT a FROM Approval a WHERE a.userId = :userId")
    , @NamedQuery(name = "Approval.findByStatus", query = "SELECT a FROM Approval a WHERE a.status = :status")
    , @NamedQuery(name = "Approval.findByModifiedDate", query = "SELECT a FROM Approval a WHERE a.modifiedDate = :modifiedDate")
    , @NamedQuery(name = "Approval.findByRemarks", query = "SELECT a FROM Approval a WHERE a.remarks = :remarks")})
public class Approval implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Size(max = 10)
    @Column(name = "status")
    private String status;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Size(max = 500)
    @Column(name = "remarks")
    private String remarks;
    @JoinColumn(name = "requestovertimedetail_id", referencedColumnName = "OvertimeDetail")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrRequestovertimedetail requestovertimedetailId;
    @Size(max = 50)
    @Column(name = "request_id")
    private String requestId;

    public Approval() {
    }

    public Approval(Integer id) {
        this.id = id;
    }

    public Approval(String requestId, Integer userId, String status, Date modifiedDate, String remarks) {
        this.requestId = requestId;
        this.userId = userId;
        this.status = status;
        this.modifiedDate = modifiedDate;
        this.remarks = remarks;
    }

    public Approval(Integer userId, String status, Date modifiedDate, String remarks, TrRequestovertimedetail requestovertimedetailId) {
        this.userId = userId;
        this.status = status;
        this.modifiedDate = modifiedDate;
        this.remarks = remarks;
        this.requestovertimedetailId = requestovertimedetailId;
    }

    public Approval(Integer id, Date modifiedDate) {
        this.id = id;
        this.modifiedDate = modifiedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public TrRequestovertimedetail getRequestovertimedetailId() {
        return requestovertimedetailId;
    }

    public void setRequestovertimedetailId(TrRequestovertimedetail requestovertimedetailId) {
        this.requestovertimedetailId = requestovertimedetailId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
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
        if (!(object instanceof Approval)) {
            return false;
        }
        Approval other = (Approval) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "springboot.springboot.entities2.Approval[ id=" + id + " ]";
    }

}
