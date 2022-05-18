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
@Table(name = "m_chargebacktype")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MChargebacktype.findAll", query = "SELECT m FROM MChargebacktype m")
    , @NamedQuery(name = "MChargebacktype.findByChargeBackTypeID", query = "SELECT m FROM MChargebacktype m WHERE m.chargeBackTypeID = :chargeBackTypeID")
    , @NamedQuery(name = "MChargebacktype.findByChargeBackType", query = "SELECT m FROM MChargebacktype m WHERE m.chargeBackType = :chargeBackType")})
public class MChargebacktype implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ChargeBackTypeID")
    private Integer chargeBackTypeID;
    @Size(max = 20)
    @Column(name = "ChargeBackType")
    private String chargeBackType;

    public MChargebacktype() {
    }

    public MChargebacktype(Integer chargeBackTypeID) {
        this.chargeBackTypeID = chargeBackTypeID;
    }

    public Integer getChargeBackTypeID() {
        return chargeBackTypeID;
    }

    public void setChargeBackTypeID(Integer chargeBackTypeID) {
        this.chargeBackTypeID = chargeBackTypeID;
    }

    public String getChargeBackType() {
        return chargeBackType;
    }

    public void setChargeBackType(String chargeBackType) {
        this.chargeBackType = chargeBackType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (chargeBackTypeID != null ? chargeBackTypeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MChargebacktype)) {
            return false;
        }
        MChargebacktype other = (MChargebacktype) object;
        if ((this.chargeBackTypeID == null && other.chargeBackTypeID != null) || (this.chargeBackTypeID != null && !this.chargeBackTypeID.equals(other.chargeBackTypeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.MChargebacktype[ chargeBackTypeID=" + chargeBackTypeID + " ]";
    }
    
}
