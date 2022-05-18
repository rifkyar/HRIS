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
 * @author USER
 */
@Entity
@Table(name = "tr_soovertime")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrSoovertime.findAll", query = "SELECT t FROM TrSoovertime t")
    , @NamedQuery(name = "TrSoovertime.findBySoId", query = "SELECT t FROM TrSoovertime t WHERE t.soId = :soId")
    , @NamedQuery(name = "TrSoovertime.findByCustomerName", query = "SELECT t FROM TrSoovertime t WHERE t.customerName = :customerName")
    , @NamedQuery(name = "TrSoovertime.findByRmName", query = "SELECT t FROM TrSoovertime t WHERE t.rmName = :rmName")
    , @NamedQuery(name = "TrSoovertime.findByRelationManager", query = "SELECT t FROM TrSoovertime t WHERE t.relationManager = :relationManager")
    , @NamedQuery(name = "TrSoovertime.findByMealsTransportStatus", query = "SELECT t FROM TrSoovertime t WHERE t.mealsTransportStatus = :mealsTransportStatus")
    , @NamedQuery(name = "TrSoovertime.findByRateWeekday", query = "SELECT t FROM TrSoovertime t WHERE t.rateWeekday = :rateWeekday")
    , @NamedQuery(name = "TrSoovertime.findByRateWeekend", query = "SELECT t FROM TrSoovertime t WHERE t.rateWeekend = :rateWeekend")
    , @NamedQuery(name = "TrSoovertime.findByIsEdit", query = "SELECT t FROM TrSoovertime t WHERE t.isEdit = :isEdit")
    , @NamedQuery(name = "TrSoovertime.findByCreateBy", query = "SELECT t FROM TrSoovertime t WHERE t.createBy = :createBy")
    , @NamedQuery(name = "TrSoovertime.findByCreateDate", query = "SELECT t FROM TrSoovertime t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TrSoovertime.findByUpdateBy", query = "SELECT t FROM TrSoovertime t WHERE t.updateBy = :updateBy")
    , @NamedQuery(name = "TrSoovertime.findByUpdateDate", query = "SELECT t FROM TrSoovertime t WHERE t.updateDate = :updateDate")})
public class TrSoovertime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "so_id")
    private String soId;
    @Size(max = 50)
    @Column(name = "CustomerName")
    private String customerName;
    @Size(max = 100)
    @Column(name = "RmName")
    private String rmName;
    @Column(name = "RelationManager")
    private Integer relationManager;
    @Size(max = 20)
    @Column(name = "MealsTransportStatus")
    private String mealsTransportStatus;
    @Column(name = "RateWeekday")
    private Integer rateWeekday;
    @Column(name = "RateWeekend")
    private Integer rateWeekend;
    @Column(name = "IsEdit")
    private Boolean isEdit;
    @Size(max = 100)
    @Column(name = "CreateBy")
    private String createBy;
    @Column(name = "CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 100)
    @Column(name = "UpdateBy")
    private String updateBy;
    @Column(name = "UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @JoinColumn(name = "ChargebackStatus", referencedColumnName = "ChargeBackStatusID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MChargebackstatus chargebackStatus;
    @JoinColumn(name = "ChargerBackType", referencedColumnName = "ChargeBackTypeID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MChargebacktype chargerBackType;
    @JoinColumn(name = "OfficeHourID", referencedColumnName = "OfficeHourID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MOfficehour officeHourID;
    @JoinColumn(name = "PaidToStaffID", referencedColumnName = "PaidToStaffID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MPaidtostaff paidToStaffID;

    public TrSoovertime() {
    }

    public TrSoovertime(String soId) {
        this.soId = soId;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRmName() {
        return rmName;
    }

    public void setRmName(String rmName) {
        this.rmName = rmName;
    }

    public Integer getRelationManager() {
        return relationManager;
    }

    public void setRelationManager(Integer relationManager) {
        this.relationManager = relationManager;
    }

    public String getMealsTransportStatus() {
        return mealsTransportStatus;
    }

    public void setMealsTransportStatus(String mealsTransportStatus) {
        this.mealsTransportStatus = mealsTransportStatus;
    }

    public Integer getRateWeekday() {
        return rateWeekday;
    }

    public void setRateWeekday(Integer rateWeekday) {
        this.rateWeekday = rateWeekday;
    }

    public Integer getRateWeekend() {
        return rateWeekend;
    }

    public void setRateWeekend(Integer rateWeekend) {
        this.rateWeekend = rateWeekend;
    }

    public Boolean getIsEdit() {
        return isEdit;
    }

    public void setIsEdit(Boolean isEdit) {
        this.isEdit = isEdit;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public MChargebackstatus getChargebackStatus() {
        return chargebackStatus;
    }

    public void setChargebackStatus(MChargebackstatus chargebackStatus) {
        this.chargebackStatus = chargebackStatus;
    }

    public MChargebacktype getChargerBackType() {
        return chargerBackType;
    }

    public void setChargerBackType(MChargebacktype chargerBackType) {
        this.chargerBackType = chargerBackType;
    }

    public MOfficehour getOfficeHourID() {
        return officeHourID;
    }

    public void setOfficeHourID(MOfficehour officeHourID) {
        this.officeHourID = officeHourID;
    }

    public MPaidtostaff getPaidToStaffID() {
        return paidToStaffID;
    }

    public void setPaidToStaffID(MPaidtostaff paidToStaffID) {
        this.paidToStaffID = paidToStaffID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (soId != null ? soId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrSoovertime)) {
            return false;
        }
        TrSoovertime other = (TrSoovertime) object;
        if ((this.soId == null && other.soId != null) || (this.soId != null && !this.soId.equals(other.soId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrSoovertime[ soId=" + soId + " ]";
    }
    
}
