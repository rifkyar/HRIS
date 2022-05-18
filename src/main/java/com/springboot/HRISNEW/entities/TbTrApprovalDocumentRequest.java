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
 * @author Dharta
 */
@Entity
@Table(name = "tb_tr_approval_document_request")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrApprovalDocumentRequest.findAll", query = "SELECT t FROM TbTrApprovalDocumentRequest t")
    , @NamedQuery(name = "TbTrApprovalDocumentRequest.findById", query = "SELECT t FROM TbTrApprovalDocumentRequest t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrApprovalDocumentRequest.findByNik", query = "SELECT t FROM TbTrApprovalDocumentRequest t WHERE t.nik = :nik")
    , @NamedQuery(name = "TbTrApprovalDocumentRequest.findByCreatedDate", query = "SELECT t FROM TbTrApprovalDocumentRequest t WHERE t.createdDate = :createdDate")})
public class TbTrApprovalDocumentRequest implements Serializable {

    @Column(name = "nik")
    private Integer nik;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Lob
    @Size(max = 65535)
    @Column(name = "remark")
    private String remark;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "employee_form", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbTrEmployeeForm employeeForm;
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMStatus status;

    public TbTrApprovalDocumentRequest() {
    }

    public TbTrApprovalDocumentRequest(Integer id) {
        this.id = id;
    }

//    public TbTrApprovalDocumentRequest(Integer id, int nik, Date createdDate, String remark) {
//        this.id = id;
//        this.nik = nik;
//        this.createdDate = createdDate;
//        this.remark = remark;
//    }
    public TbTrApprovalDocumentRequest(int nik, Date createdDate, TbTrEmployeeForm employeeForm, TbMStatus status, String remark) {
        this.nik = nik;
        this.createdDate = createdDate;
        this.employeeForm = employeeForm;
        this.status = status;
        this.remark = remark;
    }

    public TbTrApprovalDocumentRequest(Date createdDate, TbTrEmployeeForm employeeForm, TbMStatus status, String remark) {
        this.createdDate = createdDate;
        this.employeeForm = employeeForm;
        this.status = status;
        this.remark = remark;
    }

    public TbTrApprovalDocumentRequest(Integer nik, Date createdDate, TbTrEmployeeForm employeeForm, TbMStatus status) {
        this.nik = nik;
        this.createdDate = createdDate;
        this.employeeForm = employeeForm;
        this.status = status;
    }

    public TbTrApprovalDocumentRequest(TbMStatus status, TbTrEmployeeForm employeeForm) {
        this.status = status;
        this.employeeForm = employeeForm;
    }

    public TbTrApprovalDocumentRequest(TbMStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public TbTrEmployeeForm getEmployeeForm() {
        return employeeForm;
    }

    public void setEmployeeForm(TbTrEmployeeForm employeeForm) {
        this.employeeForm = employeeForm;
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
        if (!(object instanceof TbTrApprovalDocumentRequest)) {
            return false;
        }
        TbTrApprovalDocumentRequest other = (TbTrApprovalDocumentRequest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrApprovalDocumentRequest[ id=" + id + " ]";
    }

    public Integer getNik() {
        return nik;
    }

    public void setNik(Integer nik) {
        this.nik = nik;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
