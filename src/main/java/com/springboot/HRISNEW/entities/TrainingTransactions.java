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
@Table(name = "training_transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingTransactions.findAll", query = "SELECT t FROM TrainingTransactions t")
    , @NamedQuery(name = "TrainingTransactions.findById", query = "SELECT t FROM TrainingTransactions t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingTransactions.findByTrainingQuota", query = "SELECT t FROM TrainingTransactions t WHERE t.trainingQuota = :trainingQuota")
    , @NamedQuery(name = "TrainingTransactions.findByTrainingLocation", query = "SELECT t FROM TrainingTransactions t WHERE t.trainingLocation = :trainingLocation")
    , @NamedQuery(name = "TrainingTransactions.findByTrainingTime", query = "SELECT t FROM TrainingTransactions t WHERE t.trainingTime = :trainingTime")
    , @NamedQuery(name = "TrainingTransactions.findByTrainingNeeds", query = "SELECT t FROM TrainingTransactions t WHERE t.trainingNeeds = :trainingNeeds")
    , @NamedQuery(name = "TrainingTransactions.findByIsActive", query = "SELECT t FROM TrainingTransactions t WHERE t.isActive = :isActive")
    , @NamedQuery(name = "TrainingTransactions.findByRemark", query = "SELECT t FROM TrainingTransactions t WHERE t.remark = :remark")
    , @NamedQuery(name = "TrainingTransactions.findByTrainingFeedbackResponseId", query = "SELECT t FROM TrainingTransactions t WHERE t.trainingFeedbackResponseId = :trainingFeedbackResponseId")
    , @NamedQuery(name = "TrainingTransactions.findByCreatedBy", query = "SELECT t FROM TrainingTransactions t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingTransactions.findByCreatedDate", query = "SELECT t FROM TrainingTransactions t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingTransactions.findByUpdatedBy", query = "SELECT t FROM TrainingTransactions t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingTransactions.findByUpdatedDate", query = "SELECT t FROM TrainingTransactions t WHERE t.updatedDate = :updatedDate")})
public class TrainingTransactions implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "training_quota")
    private Integer trainingQuota;
    @Column(name = "training_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date trainingTime;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @Column(name = "typeTraining")
    private Boolean typeTraining;
    @Size(max = 200)
    @Column(name = "training_location")
    private String trainingLocation;
    @Size(max = 200)
    @Column(name = "training_needs")
    private String trainingNeeds;
    @Size(max = 255)
    @Column(name = "training_link")
    private String trainingLink;
    @Size(max = 250)
    @Column(name = "remark")
    private String remark;
    @Basic(optional = false)
    @NotNull
    @Column(name = "training_feedback_response_id")
    private int trainingFeedbackResponseId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @JoinColumn(name = "training_catalog_transaction_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingCatalogTransactions trainingCatalogTransactionId;

    public TrainingTransactions() {
    }

    public TrainingTransactions(Integer id) {
        this.id = id;
    }
    
    public TrainingTransactions(String trainingLocation, String trainingNeeds, Date createdDate, Integer trainingQuota, Date trainingTime, TrainingCatalogTransactions trainingCatalogTransactionId, Date updatedDate, Boolean isActive, Integer createdBy, int trainingFeedbackResponseId, Boolean typeTraining) {
        this.trainingLocation = trainingLocation;
        this.trainingNeeds = trainingNeeds;
        this.createdDate = createdDate;
        this.trainingQuota = trainingQuota;
        this.trainingTime = trainingTime;
        this.trainingCatalogTransactionId = trainingCatalogTransactionId;
        this.updatedDate = updatedDate;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.trainingFeedbackResponseId = trainingFeedbackResponseId;
        this.typeTraining = typeTraining;
    }

    public TrainingTransactions(Integer trainingQuota, Date trainingTime, Boolean isActive, Integer createdBy, Boolean typeTraining, String trainingLink, int trainingFeedbackResponseId, Date createdDate, Date updatedDate, TrainingCatalogTransactions trainingCatalogTransactionId) {
        this.trainingQuota = trainingQuota;
        this.trainingTime = trainingTime;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.typeTraining = typeTraining;
        this.trainingLink = trainingLink;
        this.trainingFeedbackResponseId = trainingFeedbackResponseId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.trainingCatalogTransactionId = trainingCatalogTransactionId;
    }
    
    public TrainingTransactions(Integer id, String trainingLocation, String trainingNeeds, Date createdDate, Integer trainingQuota, Date trainingTime, TrainingCatalogTransactions trainingCatalogTransactionId, Date updatedDate, Boolean isActive, Integer createdBy, Integer updatedBy) {
        this.id = id;
        this.trainingLocation = trainingLocation;
        this.trainingNeeds = trainingNeeds;
        this.createdDate = createdDate;
        this.trainingQuota = trainingQuota;
        this.trainingTime = trainingTime;
        this.trainingCatalogTransactionId = trainingCatalogTransactionId;
        this.updatedDate = updatedDate;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }

    public TrainingTransactions(Integer id, Integer updatedBy, Date updatedDate, String remark) {
        this.id = id;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        this.remark = remark;
    }

    public TrainingTransactions(Integer id, Date createdDate, Date updatedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public TrainingTransactions(Integer id, int trainingFeedbackResponseId, Date createdDate, Date updatedDate) {
        this.id = id;
        this.trainingFeedbackResponseId = trainingFeedbackResponseId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainingQuota() {
        return trainingQuota;
    }

    public void setTrainingQuota(Integer trainingQuota) {
        this.trainingQuota = trainingQuota;
    }

    public String getTrainingLocation() {
        return trainingLocation;
    }

    public void setTrainingLocation(String trainingLocation) {
        this.trainingLocation = trainingLocation;
    }

    public Date getTrainingTime() {
        return trainingTime;
    }

    public void setTrainingTime(Date trainingTime) {
        this.trainingTime = trainingTime;
    }

    public String getTrainingNeeds() {
        return trainingNeeds;
    }

    public void setTrainingNeeds(String trainingNeeds) {
        this.trainingNeeds = trainingNeeds;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


    public int getTrainingFeedbackResponseId() {
        return trainingFeedbackResponseId;
    }

    public void setTrainingFeedbackResponseId(int trainingFeedbackResponseId) {
        this.trainingFeedbackResponseId = trainingFeedbackResponseId;
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

    public TrainingCatalogTransactions getTrainingCatalogTransactionId() {
        return trainingCatalogTransactionId;
    }

    public void setTrainingCatalogTransactionId(TrainingCatalogTransactions trainingCatalogTransactionId) {
        this.trainingCatalogTransactionId = trainingCatalogTransactionId;
    }
    

    public Boolean getTypeTraining() {
        return typeTraining;
    }

    public void setTypeTraining(Boolean typeTraining) {
        this.typeTraining = typeTraining;
    }

    public String getTrainingLink() {
        return trainingLink;
    }

    public void setTrainingLink(String trainingLink) {
        this.trainingLink = trainingLink;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        if (!(object instanceof TrainingTransactions)) {
            return false;
        }
        TrainingTransactions other = (TrainingTransactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingTransactions[ id=" + id + " ]";
    }

}
