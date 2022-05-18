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
@Table(name = "training_feedback_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingFeedbackType.findAll", query = "SELECT t FROM TrainingFeedbackType t")
    , @NamedQuery(name = "TrainingFeedbackType.findById", query = "SELECT t FROM TrainingFeedbackType t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingFeedbackType.findByType", query = "SELECT t FROM TrainingFeedbackType t WHERE t.type = :type")
    , @NamedQuery(name = "TrainingFeedbackType.findByCreatedBy", query = "SELECT t FROM TrainingFeedbackType t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingFeedbackType.findByCreatedDate", query = "SELECT t FROM TrainingFeedbackType t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingFeedbackType.findByUpdatedBy", query = "SELECT t FROM TrainingFeedbackType t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingFeedbackType.findByUpdatedDate", query = "SELECT t FROM TrainingFeedbackType t WHERE t.updatedDate = :updatedDate")})
public class TrainingFeedbackType implements Serializable {

    @Size(max = 100)
    @Column(name = "type")
    private String type;
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
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @OneToMany(mappedBy = "trainingFeedbackTypeId", fetch = FetchType.LAZY)
    private List<TrainingFeedbackQuestions> trainingFeedbackQuestionsList;

    public TrainingFeedbackType() {
    }

    public TrainingFeedbackType(Integer id) {
        this.id = id;
    }

    public TrainingFeedbackType(Integer id, Date createdDate, Date updatedDate) {
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

    @XmlTransient
    public List<TrainingFeedbackQuestions> getTrainingFeedbackQuestionsList() {
        return trainingFeedbackQuestionsList;
    }

    public void setTrainingFeedbackQuestionsList(List<TrainingFeedbackQuestions> trainingFeedbackQuestionsList) {
        this.trainingFeedbackQuestionsList = trainingFeedbackQuestionsList;
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
        if (!(object instanceof TrainingFeedbackType)) {
            return false;
        }
        TrainingFeedbackType other = (TrainingFeedbackType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingFeedbackType[ id=" + id + " ]";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
