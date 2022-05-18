/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "training_catalogs")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingCatalogs.findAll", query = "SELECT t FROM TrainingCatalogs t")
    , @NamedQuery(name = "TrainingCatalogs.findById", query = "SELECT t FROM TrainingCatalogs t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingCatalogs.findByTrainingTitle", query = "SELECT t FROM TrainingCatalogs t WHERE t.trainingTitle = :trainingTitle")
    , @NamedQuery(name = "TrainingCatalogs.findByIsAvailable", query = "SELECT t FROM TrainingCatalogs t WHERE t.isAvailable = :isAvailable")
    , @NamedQuery(name = "TrainingCatalogs.findByCreatedBy", query = "SELECT t FROM TrainingCatalogs t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingCatalogs.findByCreatedDate", query = "SELECT t FROM TrainingCatalogs t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingCatalogs.findByUpdatedBy", query = "SELECT t FROM TrainingCatalogs t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingCatalogs.findByUpdatedDate", query = "SELECT t FROM TrainingCatalogs t WHERE t.updatedDate = :updatedDate")})
public class TrainingCatalogs implements Serializable {

    @Size(max = 200)
    @Column(name = "training_title")
    private String trainingTitle;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @OneToMany(mappedBy = "trainingCatalogId", fetch = FetchType.LAZY)
    private List<TrainingCatalogTransactions> trainingCatalogTransactionsList;
    @OneToMany(mappedBy = "trainingCatalogId", fetch = FetchType.LAZY)
    private List<TrainingUserRequests> trainingUserRequestsList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_available")
    private Boolean isAvailable;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "updated_by")
    private Integer updatedBy;
    @JoinColumn(name = "training_category_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingCategories trainingCategoryId;

    public TrainingCatalogs() {
    }

    public TrainingCatalogs(Integer id, Boolean isAvailable) {
        this.id = id;
        this.isAvailable = isAvailable;
    }

    public TrainingCatalogs(Integer id) {
        this.id = id;
    }

    public TrainingCatalogs(Integer id, Date createdDate, Date updatedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public TrainingCatalogs(String trainingTitle, Boolean isAvailable, Date createdDate, Date updatedDate, TrainingCategories trainingCategoryId) {
        this.trainingTitle = trainingTitle;
        this.isAvailable = isAvailable;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.trainingCategoryId = trainingCategoryId;
    }
    
    public TrainingCatalogs(Integer id, String trainingTitle, Boolean isAvailable, Date createdDate, Date updatedDate, TrainingCategories trainingCategoryId) {
        this.id = id;
        this.trainingTitle = trainingTitle;
        this.isAvailable = isAvailable;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.trainingCategoryId = trainingCategoryId;
    }
    
    public TrainingCatalogs(Integer id, String trainingTitle, Boolean isAvailable, Date updatedDate, TrainingCategories trainingCategoryId) {
        this.id = id;
        this.trainingTitle = trainingTitle;
        this.isAvailable = isAvailable;
        this.updatedDate = updatedDate;
        this.trainingCategoryId = trainingCategoryId;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrainingTitle() {
        return trainingTitle;
    }

    public void setTrainingTitle(String trainingTitle) {
        this.trainingTitle = trainingTitle;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
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

    public TrainingCategories getTrainingCategoryId() {
        return trainingCategoryId;
    }

    public void setTrainingCategoryId(TrainingCategories trainingCategoryId) {
        this.trainingCategoryId = trainingCategoryId;
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
        if (!(object instanceof TrainingCatalogs)) {
            return false;
        }
        TrainingCatalogs other = (TrainingCatalogs) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

 

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingCatalogs[ id=" + id + " ]";
    }

    @XmlTransient
    public List<TrainingUserRequests> getTrainingUserRequestsList() {
        return trainingUserRequestsList;
    }

    public void setTrainingUserRequestsList(List<TrainingUserRequests> trainingUserRequestsList) {
        this.trainingUserRequestsList = trainingUserRequestsList;
    }

    @XmlTransient
    public List<TrainingCatalogTransactions> getTrainingCatalogTransactionsList() {
        return trainingCatalogTransactionsList;
    }

    public void setTrainingCatalogTransactionsList(List<TrainingCatalogTransactions> trainingCatalogTransactionsList) {
        this.trainingCatalogTransactionsList = trainingCatalogTransactionsList;
    }

//    public String getTrainingTitle() {
//        return trainingTitle;
//    }
//
//    public void setTrainingTitle(String trainingTitle) {
//        this.trainingTitle = trainingTitle;
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
//
//    public String getTrainingTitle() {
//        return trainingTitle;
//    }
//
//    public void setTrainingTitle(String trainingTitle) {
//        this.trainingTitle = trainingTitle;
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
//
//    public String getTrainingTitle() {
//        return trainingTitle;
//    }
//
//    public void setTrainingTitle(String trainingTitle) {
//        this.trainingTitle = trainingTitle;
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