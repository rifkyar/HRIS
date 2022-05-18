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
import javax.persistence.Lob;
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
 * @author Dharta
 */
@Entity
@Table(name = "tb_tr_employee_form")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrEmployeeForm.findAll", query = "SELECT t FROM TbTrEmployeeForm t")
    , @NamedQuery(name = "TbTrEmployeeForm.findById", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrEmployeeForm.findByFormNumber", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.formNumber = :formNumber")
    , @NamedQuery(name = "TbTrEmployeeForm.findByStartDate", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.startDate = :startDate")
    , @NamedQuery(name = "TbTrEmployeeForm.findByEndDate", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.endDate = :endDate")
    , @NamedQuery(name = "TbTrEmployeeForm.findByApproveBy", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.approveBy = :approveBy")
    , @NamedQuery(name = "TbTrEmployeeForm.findByCreatedBy", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TbTrEmployeeForm.findByCreateByNik", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.createByNik = :createByNik")
    , @NamedQuery(name = "TbTrEmployeeForm.findByCreatedDate", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TbTrEmployeeForm.findByModifiedDate", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.modifiedDate = :modifiedDate")
    , @NamedQuery(name = "TbTrEmployeeForm.findByIsDeteled", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.isDeteled = :isDeteled")
    , @NamedQuery(name = "TbTrEmployeeForm.findByFileName", query = "SELECT t FROM TbTrEmployeeForm t WHERE t.fileName = :fileName")})
public class TbTrEmployeeForm implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id")
    private String id;
    @Size(max = 50)
    @Column(name = "form_number")
    private String formNumber;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Lob
    @Size(max = 65535)
    @Column(name = "description_by_admin")
    private String descriptionByAdmin;
    @Column(name = "approve_by")
    private Integer approveBy;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "created_by")
    private String createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "create_by_nik")
    private int createByNik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deteled")
    private boolean isDeteled;
    @Lob
    @Column(name = "file_upload")
    private byte[] fileUpload;
    @Size(max = 255)
    @Column(name = "file_name")
    private String fileName;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employeeForm", fetch = FetchType.LAZY)
    private List<TbTrEmployeeFormDetails> tbTrEmployeeFormDetailsList;
    @JoinColumn(name = "form", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMForm form;

    public TbTrEmployeeForm() {
    }

    public TbTrEmployeeForm(String id) {
        this.id = id;
    }

//    public TbTrEmployeeForm(String id, int status, String createdBy, int createByNik, Date createdDate, boolean isDeteled) {
//        this.id = id;
//        this.status = status;
//        this.createdBy = createdBy;
//        this.createByNik = createByNik;
//        this.createdDate = createdDate;
//        this.isDeteled = isDeteled;
//    }
    public TbTrEmployeeForm(String formNumber, String descriptionByAdmin, Date modifiedDate, int status) {
        this.formNumber = formNumber;
        this.descriptionByAdmin = descriptionByAdmin;
        this.modifiedDate = modifiedDate;
        this.status = status;
    }

    public TbTrEmployeeForm(String id, int status, String description, String createdBy, int createByNik, Date createdDate, boolean isDeteled, TbMForm form) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.createdBy = createdBy;
        this.createByNik = createByNik;
        this.createdDate = createdDate;
        this.isDeteled = isDeteled;
        this.form = form;
    }

    public TbTrEmployeeForm(String id, int status, String description, String createdBy, int createByNik, Date createdDate, boolean isDeteled, TbMForm form, int approveBy) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.createdBy = createdBy;
        this.createByNik = createByNik;
        this.createdDate = createdDate;
        this.isDeteled = isDeteled;
        this.form = form;
        this.approveBy = approveBy;
    }

    public TbTrEmployeeForm(String id, int status, String description, Date startDate, Date endDate, Integer approveBy, String createdBy, int createByNik, Date createdDate, TbMForm form) {
        this.id = id;
        this.status = status;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approveBy = approveBy;
        this.createdBy = createdBy;
        this.createByNik = createByNik;
        this.createdDate = createdDate;
        this.form = form;
    }

    public TbTrEmployeeForm(byte[] fileUpload, String id) {
        this.fileUpload = fileUpload;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFormNumber() {
        return formNumber;
    }

    public void setFormNumber(String formNumber) {
        this.formNumber = formNumber;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionByAdmin() {
        return descriptionByAdmin;
    }

    public void setDescriptionByAdmin(String descriptionByAdmin) {
        this.descriptionByAdmin = descriptionByAdmin;
    }

    public Integer getApproveBy() {
        return approveBy;
    }

    public void setApproveBy(Integer approveBy) {
        this.approveBy = approveBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getCreateByNik() {
        return createByNik;
    }

    public void setCreateByNik(int createByNik) {
        this.createByNik = createByNik;
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

    public boolean getIsDeteled() {
        return isDeteled;
    }

    public void setIsDeteled(boolean isDeteled) {
        this.isDeteled = isDeteled;
    }

    public byte[] getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(byte[] fileUpload) {
        this.fileUpload = fileUpload;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @XmlTransient
    public List<TbTrEmployeeFormDetails> getTbTrEmployeeFormDetailsList() {
        return tbTrEmployeeFormDetailsList;
    }

    public void setTbTrEmployeeFormDetailsList(List<TbTrEmployeeFormDetails> tbTrEmployeeFormDetailsList) {
        this.tbTrEmployeeFormDetailsList = tbTrEmployeeFormDetailsList;
    }

    public TbMForm getForm() {
        return form;
    }

    public void setForm(TbMForm form) {
        this.form = form;
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
        if (!(object instanceof TbTrEmployeeForm)) {
            return false;
        }
        TbTrEmployeeForm other = (TbTrEmployeeForm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrEmployeeForm[ id=" + id + " ]";
    }

}
