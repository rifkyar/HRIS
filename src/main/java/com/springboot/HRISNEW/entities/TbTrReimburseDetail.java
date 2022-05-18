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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import jdk.nashorn.internal.ir.annotations.Ignore;

/**
 *
 * @author alexa
 */
@Entity
@Table(name = "tb_tr_reimburse_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrReimburseDetail.findAll", query = "SELECT t FROM TbTrReimburseDetail t")
    , @NamedQuery(name = "TbTrReimburseDetail.findById", query = "SELECT t FROM TbTrReimburseDetail t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrReimburseDetail.findByRate", query = "SELECT t FROM TbTrReimburseDetail t WHERE t.rate = :rate")
    , @NamedQuery(name = "TbTrReimburseDetail.findByBuilding", query = "SELECT t FROM TbTrReimburseDetail t WHERE t.building = :building")
    , @NamedQuery(name = "TbTrReimburseDetail.findByIsDeleted", query = "SELECT t FROM TbTrReimburseDetail t WHERE t.isDeleted = :isDeleted")
    , @NamedQuery(name = "TbTrReimburseDetail.findByCreatedDate", query = "SELECT t FROM TbTrReimburseDetail t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TbTrReimburseDetail.findByModifiedDate", query = "SELECT t FROM TbTrReimburseDetail t WHERE t.modifiedDate = :modifiedDate")})
public class TbTrReimburseDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Ignore
    @Column(name = "rate")
    private long rate;
    @Basic(optional = false)
    @NotNull
    @Ignore
    @Size(min = 1, max = 50)
    @Column(name = "building")
    private String building;
    @Lob
    @Size(max = 65535)
    @Column(name = "reason")
    private String reason;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Ignore
    @Column(name = "ticket")
    private byte[] ticket;
    @Basic(optional = false)
    @NotNull
    @Ignore
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @Basic(optional = false)
    @NotNull
    @Ignore
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Ignore
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @JoinColumn(name = "employee_transportation", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMEmployeeTransportation employeeTransportation;
    @JoinColumn(name = "parking_location", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMParkingLocation parkingLocation;
    @JoinColumn(name = "reimburse", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbTrReimburse reimburse;
    @JoinColumn(name = "status", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMStatus status;

    public TbTrReimburseDetail() {
    }

    public TbTrReimburseDetail(Integer id, String reason, TbMStatus status,Date modifiedDate) {
        this.id = id;
        this.reason = reason;
        this.status = status;
         this.modifiedDate = modifiedDate;
    }

    public TbTrReimburseDetail(Integer id) {
        this.id = id;
    }

    public TbTrReimburseDetail(Integer id, TbMStatus status) {
        this.id = id;
        this.status = status;
    }

    public TbTrReimburseDetail(Integer id, long rate, String building, byte[] ticket, boolean isDeleted, Date createdDate, Date modifiedDate) {
        this.id = id;
        this.rate = rate;
        this.building = building;
        this.ticket = ticket;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public TbTrReimburseDetail(long rate, String building, boolean isDeleted, Date createdDate, Date modifiedDate, TbTrReimburse id, TbMEmployeeTransportation employeeTransportation, TbMParkingLocation parkingLocation, TbMStatus status, byte[] ticket) {
        this.rate = rate;
        this.building = building;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.reimburse = id;
        this.employeeTransportation = employeeTransportation;
        this.parkingLocation = parkingLocation;
        this.status = status;
        this.ticket = ticket;
    }

//    public TbTrReimburseDetail(Integer id, long rate, String building, boolean isDeleted, Date createdDate, Date modifiedDate, TbTrReimburse id, TbMEmployeeTransportation employeeTransportation, TbMParkingLocation parkingLocation, TbMStatus status, byte[] ticket) {
//        this.id = id;
//        this.rate = rate;
//        this.building = building;
//        this.isDeleted = isDeleted;
//        this.createdDate = createdDate;
//        this.modifiedDate = modifiedDate;
//        this.reimburse = id;
//        this.employeeTransportation = employeeTransportation;
//        this.parkingLocation = parkingLocation;
//        this.status = status;
//        this.ticket = ticket;
//    }
    public TbTrReimburseDetail(Integer id, long rate, String building, boolean isDeleted, Date createdDate, Date modifiedDate, TbTrReimburse reimburse, TbMEmployeeTransportation employeeTransportation, TbMParkingLocation parkingLocation, TbMStatus status, byte[] ticket) {
        this.id = id;
        this.rate = rate;
        this.building = building;
        this.isDeleted = isDeleted;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.reimburse = reimburse;
        this.employeeTransportation = employeeTransportation;
        this.parkingLocation = parkingLocation;
        this.status = status;
        this.ticket = ticket;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getRate() {
        return rate;
    }

    public void setRate(long rate) {
        this.rate = rate;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public byte[] getTicket() {
        return ticket;
    }

    public void setTicket(byte[] ticket) {
        this.ticket = ticket;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public TbMEmployeeTransportation getEmployeeTransportation() {
        return employeeTransportation;
    }

    public void setEmployeeTransportation(TbMEmployeeTransportation employeeTransportation) {
        this.employeeTransportation = employeeTransportation;
    }

    public TbMParkingLocation getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(TbMParkingLocation parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    public TbTrReimburse getReimburse() {
        return reimburse;
    }

    public void setReimburse(TbTrReimburse reimburse) {
        this.reimburse = reimburse;
    }

    public TbMStatus getStatus() {
        return status;
    }

    public void setStatus(TbMStatus status) {
        this.status = status;
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
        if (!(object instanceof TbTrReimburseDetail)) {
            return false;
        }
        TbTrReimburseDetail other = (TbTrReimburseDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrReimburseDetail[ id=" + id + " ]";
    }

}
