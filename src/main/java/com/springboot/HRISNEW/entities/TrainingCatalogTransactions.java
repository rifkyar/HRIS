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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author User
 */
@Entity
@Table(name = "training_catalog_transactions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingCatalogTransactions.findAll", query = "SELECT t FROM TrainingCatalogTransactions t")
    , @NamedQuery(name = "TrainingCatalogTransactions.findById", query = "SELECT t FROM TrainingCatalogTransactions t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingCatalogTransactions.findByCreatedBy", query = "SELECT t FROM TrainingCatalogTransactions t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingCatalogTransactions.findByCreatedDate", query = "SELECT t FROM TrainingCatalogTransactions t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingCatalogTransactions.findByUpdatedBy", query = "SELECT t FROM TrainingCatalogTransactions t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingCatalogTransactions.findByUpdatedDate", query = "SELECT t FROM TrainingCatalogTransactions t WHERE t.updatedDate = :updatedDate")})
public class TrainingCatalogTransactions implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "is_active")
    private Boolean isActive;

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
    @JoinColumn(name = "training_catalog_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingCatalogs trainingCatalogId;
    @JoinColumn(name = "training_catalog_trainer_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingCatalogTrainers trainingCatalogTrainerId;
    @OneToMany(mappedBy = "trainingCatalogTransactionId", fetch = FetchType.LAZY)
    private List<TrainingTransactions> trainingTransactionsList;

    public TrainingCatalogTransactions() {
    }

    public TrainingCatalogTransactions(Integer id) {
        this.id = id;
    }

    public TrainingCatalogTransactions(TrainingCatalogs trainingCatalogId, TrainingCatalogTrainers trainingCatalogTrainerId, Boolean isActive, Integer createdBy, Date createdDate, Date updatedDate) {
        this.trainingCatalogId = trainingCatalogId;
        this.trainingCatalogTrainerId = trainingCatalogTrainerId;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
    
    public TrainingCatalogTransactions(TrainingCatalogs trainingCatalogId, TrainingCatalogTrainers trainingCatalogTrainerId, Integer createdBy, Date updatedDate, Integer id) {
        this.trainingCatalogId = trainingCatalogId;
        this.trainingCatalogTrainerId = trainingCatalogTrainerId;
        this.createdBy = createdBy;
        this.updatedDate = updatedDate;
        this.id = id;
    }

    public TrainingCatalogTransactions(Integer id, Date createdDate, Date updatedDate) {
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

    public TrainingCatalogs getTrainingCatalogId() {
        return trainingCatalogId;
    }

    public void setTrainingCatalogId(TrainingCatalogs trainingCatalogId) {
        this.trainingCatalogId = trainingCatalogId;
    }

    public TrainingCatalogTrainers getTrainingCatalogTrainerId() {
        return trainingCatalogTrainerId;
    }

    public void setTrainingCatalogTrainerId(TrainingCatalogTrainers trainingCatalogTrainerId) {
        this.trainingCatalogTrainerId = trainingCatalogTrainerId;
    }

    @XmlTransient
    public List<TrainingTransactions> getTrainingTransactionsList() {
        return trainingTransactionsList;
    }

    public void setTrainingTransactionsList(List<TrainingTransactions> trainingTransactionsList) {
        this.trainingTransactionsList = trainingTransactionsList;
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
        if (!(object instanceof TrainingCatalogTransactions)) {
            return false;
        }
        TrainingCatalogTransactions other = (TrainingCatalogTransactions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingCatalogTransactions[ id=" + id + " ]";
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
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
