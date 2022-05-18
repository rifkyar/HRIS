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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HARRY-PC
 */
@Entity
@Table(name = "leave_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LeaveDetail.findAll", query = "SELECT l FROM LeaveDetail l")
    , @NamedQuery(name = "LeaveDetail.findById", query = "SELECT l FROM LeaveDetail l WHERE l.id = :id")
    , @NamedQuery(name = "LeaveDetail.findByGender", query = "SELECT l FROM LeaveDetail l WHERE l.gender = :gender")
    , @NamedQuery(name = "LeaveDetail.findByMarriageStatus", query = "SELECT l FROM LeaveDetail l WHERE l.marriageStatus = :marriageStatus")})
public class LeaveDetail implements Serializable {

    @JoinColumn(name = "type_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LeaveType typeId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "gender")
    private Boolean gender;
    @Column(name = "marriage_status")
    private Boolean marriageStatus;

    public LeaveDetail() {
    }

    public LeaveDetail(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Boolean getMarriageStatus() {
        return marriageStatus;
    }

    public void setMarriageStatus(Boolean marriageStatus) {
        this.marriageStatus = marriageStatus;
    }

    public LeaveType getTypeId() {
        return typeId;
    }

    public void setTypeId(LeaveType typeId) {
        this.typeId = typeId;
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
        if (!(object instanceof LeaveDetail)) {
            return false;
        }
        LeaveDetail other = (LeaveDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.LeaveDetail[ id=" + id + " ]";
    }
    
}
