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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author User
 */
@Entity
@Table(name = "training_user_registration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingUserRegistration.findAll", query = "SELECT t FROM TrainingUserRegistration t")
    , @NamedQuery(name = "TrainingUserRegistration.findById", query = "SELECT t FROM TrainingUserRegistration t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingUserRegistration.findByEmplNik", query = "SELECT t FROM TrainingUserRegistration t WHERE t.emplNik = :emplNik")
    , @NamedQuery(name = "TrainingUserRegistration.findByCustomerId", query = "SELECT t FROM TrainingUserRegistration t WHERE t.customerId = :customerId")
    , @NamedQuery(name = "TrainingUserRegistration.findByTrainingTransactionId", query = "SELECT t FROM TrainingUserRegistration t WHERE t.trainingTransactionId = :trainingTransactionId")
    , @NamedQuery(name = "TrainingUserRegistration.findByTrainingFeedbackResponseId", query = "SELECT t FROM TrainingUserRegistration t WHERE t.trainingFeedbackResponseId = :trainingFeedbackResponseId")
    , @NamedQuery(name = "TrainingUserRegistration.findByMOtherId", query = "SELECT t FROM TrainingUserRegistration t WHERE t.mOtherId = :mOtherId")
    , @NamedQuery(name = "TrainingUserRegistration.findByRemark", query = "SELECT t FROM TrainingUserRegistration t WHERE t.remark = :remark")
    , @NamedQuery(name = "TrainingUserRegistration.findByFlag", query = "SELECT t FROM TrainingUserRegistration t WHERE t.flag = :flag")
    , @NamedQuery(name = "TrainingUserRegistration.findByUuid", query = "SELECT t FROM TrainingUserRegistration t WHERE t.uuid = :uuid")
    , @NamedQuery(name = "TrainingUserRegistration.findByCreatedBy", query = "SELECT t FROM TrainingUserRegistration t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingUserRegistration.findByCreatedDate", query = "SELECT t FROM TrainingUserRegistration t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingUserRegistration.findByUpdatedBy", query = "SELECT t FROM TrainingUserRegistration t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingUserRegistration.findByUpdatedDate", query = "SELECT t FROM TrainingUserRegistration t WHERE t.updatedDate = :updatedDate")})
public class TrainingUserRegistration implements Serializable {

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
    @Column(name = "training_transaction_id")
    private Integer trainingTransactionId;
    @Column(name = "training_feedback_response_id")
    private Integer trainingFeedbackResponseId;
    @Column(name = "m_other_id")
    private Integer mOtherId;
    @Lob
    @Column(name = "upload_trainer")
    private byte[] uploadTrainer;
    @Size(max = 250)
    @Column(name = "remark")
    private String remark;
    @Basic(optional = false)
    @NotNull
    @Column(name = "flag")
    private boolean flag;
    @Size(max = 10)
    @Column(name = "UUID")
    private String uuid;
    @Column(name = "created_by")
    private Integer createdBy;
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

    public TrainingUserRegistration() {
    }

    public TrainingUserRegistration(Integer id) {
        this.id = id;
    }

    public TrainingUserRegistration(Integer id, boolean flag, Date updatedDate) {
        this.id = id;
        this.flag = flag;
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
    
    public TrainingUserRegistration(Integer emplNik, String customerId, Integer trainingTransactionId, Integer trainingFeedbackResponseId, Integer mOtherId, boolean flag, Integer createdBy, Date createdDate, Integer updatedBy, Date updatedDate) {
        this.emplNik = emplNik;
        this.customerId = customerId;
        this.trainingTransactionId = trainingTransactionId;
        this.trainingFeedbackResponseId = trainingFeedbackResponseId;
        this.mOtherId = mOtherId;
        this.flag = flag;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
        
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getTrainingTransactionId() {
        return trainingTransactionId;
    }

    public void setTrainingTransactionId(Integer trainingTransactionId) {
        this.trainingTransactionId = trainingTransactionId;
    }

    public Integer getTrainingFeedbackResponseId() {
        return trainingFeedbackResponseId;
    }

    public void setTrainingFeedbackResponseId(Integer trainingFeedbackResponseId) {
        this.trainingFeedbackResponseId = trainingFeedbackResponseId;
    }

    public Integer getMOtherId() {
        return mOtherId;
    }

    public void setMOtherId(Integer mOtherId) {
        this.mOtherId = mOtherId;
    }

    public byte[] getUploadTrainer() {
        return uploadTrainer;
    }

    public void setUploadTrainer(byte[] uploadTrainer) {
        this.uploadTrainer = uploadTrainer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingUserRegistration)) {
            return false;
        }
        TrainingUserRegistration other = (TrainingUserRegistration) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingUserRegistration[ id=" + id + " ]";
    }
    
}
