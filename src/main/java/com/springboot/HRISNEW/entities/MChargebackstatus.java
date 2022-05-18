/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "m_chargebackstatus")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MChargebackstatus.findAll", query = "SELECT m FROM MChargebackstatus m")
    , @NamedQuery(name = "MChargebackstatus.findByChargeBackStatusID", query = "SELECT m FROM MChargebackstatus m WHERE m.chargeBackStatusID = :chargeBackStatusID")
    , @NamedQuery(name = "MChargebackstatus.findByChargeBackStatus", query = "SELECT m FROM MChargebackstatus m WHERE m.chargeBackStatus = :chargeBackStatus")})
public class MChargebackstatus implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ChargeBackStatusID")
    private Integer chargeBackStatusID;
    @Size(max = 20)
    @Column(name = "ChargeBackStatus")
    private String chargeBackStatus;

    public MChargebackstatus() {
    }

    public MChargebackstatus(Integer chargeBackStatusID) {
        this.chargeBackStatusID = chargeBackStatusID;
    }

    public Integer getChargeBackStatusID() {
        return chargeBackStatusID;
    }

    public void setChargeBackStatusID(Integer chargeBackStatusID) {
        this.chargeBackStatusID = chargeBackStatusID;
    }

    public String getChargeBackStatus() {
        return chargeBackStatus;
    }

    public void setChargeBackStatus(String chargeBackStatus) {
        this.chargeBackStatus = chargeBackStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chargeBackStatusID != null ? chargeBackStatusID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MChargebackstatus)) {
            return false;
        }
        MChargebackstatus other = (MChargebackstatus) object;
        if ((this.chargeBackStatusID == null && other.chargeBackStatusID != null) || (this.chargeBackStatusID != null && !this.chargeBackStatusID.equals(other.chargeBackStatusID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.MChargebackstatus[ chargeBackStatusID=" + chargeBackStatusID + " ]";
    }
    
}
