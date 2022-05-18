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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
 * @author alexa
 */
@Entity
@Table(name = "tb_m_parking_location")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMParkingLocation.findAll", query = "SELECT t FROM TbMParkingLocation t")
    , @NamedQuery(name = "TbMParkingLocation.findById", query = "SELECT t FROM TbMParkingLocation t WHERE t.id = :id")
    , @NamedQuery(name = "TbMParkingLocation.findByParkingLocation", query = "SELECT t FROM TbMParkingLocation t WHERE t.parkingLocation = :parkingLocation")
    , @NamedQuery(name = "TbMParkingLocation.findByParkingOwner", query = "SELECT t FROM TbMParkingLocation t WHERE t.parkingOwner = :parkingOwner")
    , @NamedQuery(name = "TbMParkingLocation.findByReimburseType", query = "SELECT t FROM TbMParkingLocation t WHERE t.reimburseType = :reimburseType")
    , @NamedQuery(name = "TbMParkingLocation.findByContact", query = "SELECT t FROM TbMParkingLocation t WHERE t.contact = :contact")
    , @NamedQuery(name = "TbMParkingLocation.findByIsDeteled", query = "SELECT t FROM TbMParkingLocation t WHERE t.isDeteled = :isDeteled")
    , @NamedQuery(name = "TbMParkingLocation.findByCreatedDate", query = "SELECT t FROM TbMParkingLocation t WHERE t.createdDate = :createdDate")
    , @NamedQuery(name = "TbMParkingLocation.findByModifiedDate", query = "SELECT t FROM TbMParkingLocation t WHERE t.modifiedDate = :modifiedDate")})
public class TbMParkingLocation implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "parking_location")
    private String parkingLocation;
    @Basic(optional = false)
    @NotNull()
    @Lob()
    @Size(min = 1, max = 65535)
    @Column(name = "parking_address")
    private String parkingAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "parking_owner")
    private String parkingOwner;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "reimburse_type")
    private String reimburseType;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "contact")
    private String contact;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_deteled")
    private boolean isDeteled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "modified_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parkingLocation", fetch = FetchType.LAZY)
    private List<TbTrReimburseDetail> tbTrReimburseDetailList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public TbMParkingLocation() {
    }

    public TbMParkingLocation(Integer id) {
        this.id = id;
    }

    public TbMParkingLocation(Integer id, String parkingLocation, String parkingAddress, String parkingOwner, String reimburseType, String contact, boolean isDeteled, Date createdDate, Date modifiedDate) {
        this.id = id;
        this.parkingLocation = parkingLocation;
        this.parkingAddress = parkingAddress;
        this.parkingOwner = parkingOwner;
        this.reimburseType = reimburseType;
        this.contact = contact;
        this.isDeteled = isDeteled;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
    public TbMParkingLocation(Integer id, String parkingLocation, String parkingAddress, String parkingOwner, String reimburseType, String contact, boolean isDeteled, Date modifiedDate) {
        this.id = id;
        this.parkingLocation = parkingLocation;
        this.parkingAddress = parkingAddress;
        this.parkingOwner = parkingOwner;
        this.reimburseType = reimburseType;
        this.contact = contact;
        this.isDeteled = isDeteled;
        this.modifiedDate = modifiedDate;
    }
    public TbMParkingLocation( String parkingLocation, String parkingAddress, String parkingOwner, String reimburseType, String contact, boolean isDeteled, Date createdDate, Date modifiedDate) {
        this.parkingLocation = parkingLocation;
        this.parkingAddress = parkingAddress;
        this.parkingOwner = parkingOwner;
        this.reimburseType = reimburseType;
        this.contact = contact;
        this.isDeteled = isDeteled;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getParkingLocation() {
        return parkingLocation;
    }

    public void setParkingLocation(String parkingLocation) {
        this.parkingLocation = parkingLocation;
    }

    public String getParkingAddress() {
        return parkingAddress;
    }

    public void setParkingAddress(String parkingAddress) {
        this.parkingAddress = parkingAddress;
    }

    public String getParkingOwner() {
        return parkingOwner;
    }

    public void setParkingOwner(String parkingOwner) {
        this.parkingOwner = parkingOwner;
    }

    public String getReimburseType() {
        return reimburseType;
    }

    public void setReimburseType(String reimburseType) {
        this.reimburseType = reimburseType;
    }


    public boolean getIsDeteled() {
        return isDeteled;
    }

    public void setIsDeteled(boolean isDeteled) {
        this.isDeteled = isDeteled;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TbMParkingLocation)) {
            return false;
        }
        TbMParkingLocation other = (TbMParkingLocation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMParkingLocation[ id=" + id + " ]";
    }



    @XmlTransient
    public List<TbTrReimburseDetail> getTbTrReimburseDetailList() {
        return tbTrReimburseDetailList;
    }

    public void setTbTrReimburseDetailList(List<TbTrReimburseDetail> tbTrReimburseDetailList) {
        this.tbTrReimburseDetailList = tbTrReimburseDetailList;
    }


    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }


}
