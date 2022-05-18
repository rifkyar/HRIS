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
 * @author User
 */
@Entity
@Table(name = "training_feedback_response")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingFeedbackResponse.findAll", query = "SELECT t FROM TrainingFeedbackResponse t")
    , @NamedQuery(name = "TrainingFeedbackResponse.findById", query = "SELECT t FROM TrainingFeedbackResponse t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingFeedbackResponse.findByEmplNik", query = "SELECT t FROM TrainingFeedbackResponse t WHERE t.emplNik = :emplNik")
    , @NamedQuery(name = "TrainingFeedbackResponse.findByCustomerId", query = "SELECT t FROM TrainingFeedbackResponse t WHERE t.customerId = :customerId")
    , @NamedQuery(name = "TrainingFeedbackResponse.findByFeedbackResponse", query = "SELECT t FROM TrainingFeedbackResponse t WHERE t.feedbackResponse = :feedbackResponse")
    , @NamedQuery(name = "TrainingFeedbackResponse.findByCreatedBy", query = "SELECT t FROM TrainingFeedbackResponse t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingFeedbackResponse.findByCreatedDate", query = "SELECT t FROM TrainingFeedbackResponse t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingFeedbackResponse.findByUpdatedBy", query = "SELECT t FROM TrainingFeedbackResponse t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingFeedbackResponse.findByUpdatedDate", query = "SELECT t FROM TrainingFeedbackResponse t WHERE t.updatedDate = :updatedDate")})
public class TrainingFeedbackResponse implements Serializable {

    @Size(max = 100)
    @Column(name = "customer_id")
    private String customerId;
    @Size(max = 250)
    @Column(name = "feedback_response")
    private String feedbackResponse;
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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "empl_nik")
    private Integer emplNik;
    @Column(name = "training_transaction_id")
    private Integer trainingTransactionId;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @OneToMany(mappedBy = "trainingFeedbackResponseId", fetch = FetchType.LAZY)
    private List<TrainingUserRegistration> trainingUserRegistrationList;
    @JoinColumn(name = "feedback_question_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingFeedbackQuestions feedbackQuestionId;

    public TrainingFeedbackResponse() {
    }

    public TrainingFeedbackResponse(Integer id) {
        this.id = id;
    }

    public TrainingFeedbackResponse(Integer id, Date createdDate, Date updatedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public TrainingFeedbackResponse(Integer emplNik, String customerId, Integer trainingTransactionId,TrainingFeedbackQuestions feedbackQuestionId, String feedbackResponse,  Integer createdBy, Date createdDate, Integer updatedBy, Date updatedDate) {
        this.emplNik = emplNik;
        this.customerId = customerId;
        this.trainingTransactionId = trainingTransactionId;
        this.feedbackQuestionId = feedbackQuestionId;
        this.feedbackResponse = feedbackResponse;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
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

    public String getFeedbackResponse() {
        return feedbackResponse;
    }

    public void setFeedbackResponse(String feedbackResponse) {
        this.feedbackResponse = feedbackResponse;
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
    
    public Integer getTrainingTransactionId() {
        return trainingTransactionId;
    }

    public void setTrainingTransactionId(Integer trainingTransactionId) {
        this.trainingTransactionId = trainingTransactionId;
    }

    @XmlTransient
    public List<TrainingUserRegistration> getTrainingUserRegistrationList() {
        return trainingUserRegistrationList;
    }

    public void setTrainingUserRegistrationList(List<TrainingUserRegistration> trainingUserRegistrationList) {
        this.trainingUserRegistrationList = trainingUserRegistrationList;
    }

    public TrainingFeedbackQuestions getFeedbackQuestionId() {
        return feedbackQuestionId;
    }

    public void setFeedbackQuestionId(TrainingFeedbackQuestions feedbackQuestionId) {
        this.feedbackQuestionId = feedbackQuestionId;
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
        if (!(object instanceof TrainingFeedbackResponse)) {
            return false;
        }
        TrainingFeedbackResponse other = (TrainingFeedbackResponse) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingFeedbackResponse[ id=" + id + " ]";
    }

//    public String getCustomerId() {
//        return customerId;
//    }
//
//    public void setCustomerId(String customerId) {
//        this.customerId = customerId;
//    }
//
//    public String getFeedbackResponse() {
//        return feedbackResponse;
//    }
//
//    public void setFeedbackResponse(String feedbackResponse) {
//        this.feedbackResponse = feedbackResponse;
//    }
//
//    public Date getCreatedDate() {
//        return createdDate;
//    }
//
//    public void setCreatedDate(Date createdDate) {
//        this.createdDate = createdDate;
//    }
//
//    public Date getUpdatedDate() {
//        return updatedDate;
//    }
//
//    public void setUpdatedDate(Date updatedDate) {
//        this.updatedDate = updatedDate;
//    }
    
}
