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
@Table(name = "training_catalog_trainers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrainingCatalogTrainers.findAll", query = "SELECT t FROM TrainingCatalogTrainers t")
    , @NamedQuery(name = "TrainingCatalogTrainers.findById", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.id = :id")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByTrainerName", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.trainerName = :trainerName")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByTrainerStatus", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.trainerStatus = :trainerStatus")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByTrainerPhone", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.trainerPhone = :trainerPhone")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByTrainerEmail", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.trainerEmail = :trainerEmail")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByIsActive", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.isActive = :isActive")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByCreatedBy", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByCreatedDate", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByUpdatedBy", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.updatedBy = :updatedBy")
    , @NamedQuery(name = "TrainingCatalogTrainers.findByUpdatedDate", query = "SELECT t FROM TrainingCatalogTrainers t WHERE t.updatedDate = :updatedDate")})
public class TrainingCatalogTrainers implements Serializable {

    @Size(max = 100)
    @Column(name = "trainer_name")
    private String trainerName;
    @Size(max = 9)
    @Column(name = "trainer_status")
    private String trainerStatus;
    @Size(max = 20)
    @Column(name = "trainer_phone")
    private String trainerPhone;
    @Size(max = 100)
    @Column(name = "trainer_email")
    private String trainerEmail;
    @Basic(optional = false)
    @NotNull
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToMany(mappedBy = "trainingCatalogTrainerId", fetch = FetchType.LAZY)
    private List<TrainingCatalogTransactions> trainingCatalogTransactionsList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "created_by")
    private Integer createdBy;
    @Column(name = "updated_by")
    private Integer updatedBy;

    public TrainingCatalogTrainers() {
    }

    public TrainingCatalogTrainers(Boolean isActive) {
        this.isActive = isActive;
    }

    public TrainingCatalogTrainers(Integer id, String trainerName, String trainerStatus, String trainerPhone, String trainerEmail) {
        this.id = id;
        this.trainerName = trainerName;
        this.trainerStatus = trainerStatus;
        this.trainerPhone = trainerPhone;
        this.trainerEmail = trainerEmail;
    }

    public TrainingCatalogTrainers(String trainerName, String trainerStatus, String trainerPhone, String trainerEmail, Date updatedDate, Integer id) {

        this.trainerName = trainerName;
        this.trainerStatus = trainerStatus;
        this.trainerPhone = trainerPhone;
        this.trainerEmail = trainerEmail;
        this.updatedDate = updatedDate;
        this.id = id;
    }

    public TrainingCatalogTrainers(Integer id, String trainerName, String trainerStatus, String trainerPhone, String trainerEmail, Boolean isActive, Integer createdBy, Date createdDate, Integer updatedBy, Date updatedDate) {
        this.id = id;
        this.trainerName = trainerName;
        this.trainerStatus = trainerStatus;
        this.trainerPhone = trainerPhone;
        this.trainerEmail = trainerEmail;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    public TrainingCatalogTrainers(String trainerName, String trainerStatus, String trainerPhone, String trainerEmail, Boolean isActive, Integer createdBy, Date createdDate, Integer updatedBy, Date updatedDate) {
        this.trainerName = trainerName;
        this.trainerStatus = trainerStatus;
        this.trainerPhone = trainerPhone;
        this.trainerEmail = trainerEmail;
        this.isActive = isActive;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.updatedBy = updatedBy;
        this.updatedDate = updatedDate;
    }

    public TrainingCatalogTrainers(String trainerName) {
        this.trainerName = trainerName;
    }

    public TrainingCatalogTrainers(Date createdDate) {
        this.createdDate = createdDate;
    }

    public TrainingCatalogTrainers(Integer id) {
        this.id = id;
    }

    public TrainingCatalogTrainers(Integer id, Date createdDate, Date updatedDate) {
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public TrainingCatalogTrainers(Integer id, String trainerName, String trainerStatus, String trainerPhone, String trainerEmail, Date createdDate, Date updatedDate, Boolean isActive) {
        this.id = id;
        this.trainerName = trainerName;
        this.trainerStatus = trainerStatus;
        this.trainerPhone = trainerPhone;
        this.trainerEmail = trainerEmail;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isActive = isActive;
    }

    public TrainingCatalogTrainers(String trainerName, String trainerStatus, String trainerPhone, String trainerEmail, Date createdDate, Date updatedDate, Boolean isActive) {
        this.trainerName = trainerName;
        this.trainerStatus = trainerStatus;
        this.trainerPhone = trainerPhone;
        this.trainerEmail = trainerEmail;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isActive = isActive;
    }

    public TrainingCatalogTrainers(String judul, String category, boolean bool, Timestamp timestamp, Timestamp timestamp0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
    }

    public String getTrainerStatus() {
        return trainerStatus;
    }

    public void setTrainerStatus(String trainerStatus) {
        this.trainerStatus = trainerStatus;
    }

    public String getTrainerPhone() {
        return trainerPhone;
    }

    public void setTrainerPhone(String trainerPhone) {
        this.trainerPhone = trainerPhone;
    }

    public String getTrainerEmail() {
        return trainerEmail;
    }

    public void setTrainerEmail(String trainerEmail) {
        this.trainerEmail = trainerEmail;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
        if (!(object instanceof TrainingCatalogTrainers)) {
            return false;
        }
        TrainingCatalogTrainers other = (TrainingCatalogTrainers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrainingCatalogTrainers[ id=" + id + " ]";
    }

    @XmlTransient
    public List<TrainingCatalogTransactions> getTrainingCatalogTransactionsList() {
        return trainingCatalogTransactionsList;
    }

    public void setTrainingCatalogTransactionsList(List<TrainingCatalogTransactions> trainingCatalogTransactionsList) {
        this.trainingCatalogTransactionsList = trainingCatalogTransactionsList;
    }

//    public String getTrainerName() {
//        return trainerName;
//    }
//
//    public void setTrainerName(String trainerName) {
//        this.trainerName = trainerName;
//    }
//
//    public String getTrainerStatus() {
//        return trainerStatus;
//    }
//
//    public void setTrainerStatus(String trainerStatus) {
//        this.trainerStatus = trainerStatus;
//    }
//
//    public String getTrainerPhone() {
//        return trainerPhone;
//    }
//
//    public void setTrainerPhone(String trainerPhone) {
//        this.trainerPhone = trainerPhone;
//    }
//
//    public String getTrainerEmail() {
//        return trainerEmail;
//    }
//
//    public void setTrainerEmail(String trainerEmail) {
//        this.trainerEmail = trainerEmail;
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
//    public String getTrainerName() {
//        return trainerName;
//    }
//
//    public void setTrainerName(String trainerName) {
//        this.trainerName = trainerName;
//    }
//
//    public String getTrainerStatus() {
//        return trainerStatus;
//    }
//
//    public void setTrainerStatus(String trainerStatus) {
//        this.trainerStatus = trainerStatus;
//    }
//
//    public String getTrainerPhone() {
//        return trainerPhone;
//    }
//
//    public void setTrainerPhone(String trainerPhone) {
//        this.trainerPhone = trainerPhone;
//    }
//
//    public String getTrainerEmail() {
//        return trainerEmail;
//    }
//
//    public void setTrainerEmail(String trainerEmail) {
//        this.trainerEmail = trainerEmail;
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
}
