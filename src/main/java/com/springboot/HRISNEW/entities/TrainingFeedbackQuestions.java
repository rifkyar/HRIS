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
@Table(name = "training_feedback_questions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingFeedbackQuestions.findAll", query = "SELECT t FROM TrainingFeedbackQuestions t")
    , @NamedQuery(name = "TrainingFeedbackQuestions.findById", query = "SELECT t FROM TrainingFeedbackQuestions t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingFeedbackQuestions.findByQuestionContent", query = "SELECT t FROM TrainingFeedbackQuestions t WHERE t.questionContent = :questionContent")
    , @NamedQuery(name = "TrainingFeedbackQuestions.findByQuestionType", query = "SELECT t FROM TrainingFeedbackQuestions t WHERE t.questionType = :questionType")
    , @NamedQuery(name = "TrainingFeedbackQuestions.findByCreatedBy", query = "SELECT t FROM TrainingFeedbackQuestions t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingFeedbackQuestions.findByCreatedDate", query = "SELECT t FROM TrainingFeedbackQuestions t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingFeedbackQuestions.findByUpdatedBy", query = "SELECT t FROM TrainingFeedbackQuestions t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingFeedbackQuestions.findByUpdatedDate", query = "SELECT t FROM TrainingFeedbackQuestions t WHERE t.updatedDate = :updatedDate")})
public class TrainingFeedbackQuestions implements Serializable {

    @Size(max = 250)
    @Column(name = "question_content")
    private String questionContent;
    @Size(max = 9)
    @Column(name = "question_type")
    private String questionType;
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
    @OneToMany(mappedBy = "feedbackQuestionId", fetch = FetchType.LAZY)
    private List<TrainingFeedbackResponse> trainingFeedbackResponseList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @JoinColumn(name = "training_feedback_type_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingFeedbackType trainingFeedbackTypeId;

    public TrainingFeedbackQuestions() {
    }

    public TrainingFeedbackQuestions(Integer id) {
        this.id = id;
    }

    public TrainingFeedbackQuestions(Integer id, Date createdDate, Date updatedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
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

    public TrainingFeedbackType getTrainingFeedbackTypeId() {
        return trainingFeedbackTypeId;
    }

    public void setTrainingFeedbackTypeId(TrainingFeedbackType trainingFeedbackTypeId) {
        this.trainingFeedbackTypeId = trainingFeedbackTypeId;
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
        if (!(object instanceof TrainingFeedbackQuestions)) {
            return false;
        }
        TrainingFeedbackQuestions other = (TrainingFeedbackQuestions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingFeedbackQuestions[ id=" + id + " ]";
    }

    @XmlTransient
    public List<TrainingFeedbackResponse> getTrainingFeedbackResponseList() {
        return trainingFeedbackResponseList;
    }

    public void setTrainingFeedbackResponseList(List<TrainingFeedbackResponse> trainingFeedbackResponseList) {
        this.trainingFeedbackResponseList = trainingFeedbackResponseList;
    }

//    public String getQuestionContent() {
//        return questionContent;
//    }
//
//    public void setQuestionContent(String questionContent) {
//        this.questionContent = questionContent;
//    }
//
//    public String getQuestionType() {
//        return questionType;
//    }
//
//    public void setQuestionType(String questionType) {
//        this.questionType = questionType;
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
