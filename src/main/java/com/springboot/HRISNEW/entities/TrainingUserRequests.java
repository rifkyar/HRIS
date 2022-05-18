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
 * @author User
 */
@Entity
@Table(name = "training_user_requests")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingUserRequests.findAll", query = "SELECT t FROM TrainingUserRequests t")
    , @NamedQuery(name = "TrainingUserRequests.findById", query = "SELECT t FROM TrainingUserRequests t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingUserRequests.findByEmplNik", query = "SELECT t FROM TrainingUserRequests t WHERE t.emplNik = :emplNik")
    , @NamedQuery(name = "TrainingUserRequests.findByCustomerId", query = "SELECT t FROM TrainingUserRequests t WHERE t.customerId = :customerId")
    , @NamedQuery(name = "TrainingUserRequests.findByTrainingTitle", query = "SELECT t FROM TrainingUserRequests t WHERE t.trainingTitle = :trainingTitle")
    , @NamedQuery(name = "TrainingUserRequests.findByNotes", query = "SELECT t FROM TrainingUserRequests t WHERE t.notes = :notes")
    , @NamedQuery(name = "TrainingUserRequests.findByRemark", query = "SELECT t FROM TrainingUserRequests t WHERE t.remark = :remark")
    , @NamedQuery(name = "TrainingUserRequests.findByMOtherId", query = "SELECT t FROM TrainingUserRequests t WHERE t.mOtherId = :mOtherId")
    , @NamedQuery(name = "TrainingUserRequests.findByIsApproved", query = "SELECT t FROM TrainingUserRequests t WHERE t.isApproved = :isApproved")
    , @NamedQuery(name = "TrainingUserRequests.findByCreatedBy", query = "SELECT t FROM TrainingUserRequests t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingUserRequests.findByCreatedDate", query = "SELECT t FROM TrainingUserRequests t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingUserRequests.findByUpdatedBy", query = "SELECT t FROM TrainingUserRequests t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingUserRequests.findByUpdatedDate", query = "SELECT t FROM TrainingUserRequests t WHERE t.updatedDate = :updatedDate")})
public class TrainingUserRequests implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "empl_nik")
    private Integer emplNik;
    @Size(max = 100)
    @Column(name = "customer_id")
    private String customerId;
    @Size(max = 200)
    @Column(name = "training_title")
    private String trainingTitle;
    @Size(max = 250)
    @Column(name = "notes")
    private String notes;
    @Size(max = 250)
    @Column(name = "remark")
    private String remark;
    @Basic(optional = false)
    @NotNull
    @Column(name = "m_other_id")
    private Integer mOtherId;
    @Column(name = "is_approved")
    private Boolean isApproved;
    @Column(name = "created_by")
    private Integer createdBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @JoinColumn(name = "training_catalog_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingCatalogs trainingCatalogId;

    public TrainingUserRequests() {
    }

    public TrainingUserRequests(Integer emplNik, String customerId, TrainingCatalogs trainingCatalogId , String trainingTitle, String notes, Integer mOtherId, Boolean isApproved, Integer createdBy, Date createdDate, Integer updatedBy, Date updatedDate) {
        this.emplNik = emplNik;
        this.customerId = customerId;
        this.trainingCatalogId = trainingCatalogId;
        this.trainingTitle = trainingTitle;
        this.notes = notes;
//        , String remark
//        this.remark = remark;
        this.mOtherId = mOtherId;
        this.isApproved = isApproved;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        
    }
    
    
    
    public TrainingUserRequests(Integer id) {
        this.id = id;
    }

    public TrainingUserRequests(Integer id, Integer mOtherId, Date createdDate, Date updatedDate) {
        this.id = id;
        this.mOtherId = mOtherId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMOtherId() {
        return mOtherId;
    }

    public void setMOtherId(Integer mOtherId) {
        this.mOtherId = mOtherId;
    }

    public Boolean getIsApproved() {
        return isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public TrainingCatalogs getTrainingCatalogId() {
        return trainingCatalogId;
    }

    public void setTrainingCatalogId(TrainingCatalogs trainingCatalogId) {
        this.trainingCatalogId = trainingCatalogId;
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
        if (!(object instanceof TrainingUserRequests)) {
            return false;
        }
        TrainingUserRequests other = (TrainingUserRequests) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingUserRequests[ id=" + id + " ]";
    }
    
}
