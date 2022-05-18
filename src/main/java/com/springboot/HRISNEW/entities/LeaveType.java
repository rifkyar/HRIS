/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

import java.io.Serializable;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HARRY-PC
 */
@Entity
@Table(name = "leave_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LeaveType.findAll", query = "SELECT l FROM LeaveType l")
    , @NamedQuery(name = "LeaveType.findById", query = "SELECT l FROM LeaveType l WHERE l.id = :id")
    , @NamedQuery(name = "LeaveType.findByType", query = "SELECT l FROM LeaveType l WHERE l.type = :type")
    , @NamedQuery(name = "LeaveType.findByLeaveGiven", query = "SELECT l FROM LeaveType l WHERE l.leaveGiven = :leaveGiven")})
public class LeaveType implements Serializable {

    @Size(max = 50)
    @Column(name = "type")
    private String type;
    @OneToMany(mappedBy = "leaveDetailId", fetch = FetchType.LAZY)
    private List<RequestDetail> requestDetailList;
    @OneToMany(mappedBy = "typeId", fetch = FetchType.LAZY)
    private List<LeaveDetail> leaveDetailList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "leave_given")
    private Integer leaveGiven;

    public LeaveType() {
    }

    public LeaveType(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getLeaveGiven() {
        return leaveGiven;
    }

    public void setLeaveGiven(Integer leaveGiven) {
        this.leaveGiven = leaveGiven;
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
        if (!(object instanceof LeaveType)) {
            return false;
        }
        LeaveType other = (LeaveType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.LeaveType[ id=" + id + " ]";
    }


    @XmlTransient
    public List<LeaveDetail> getLeaveDetailList() {
        return leaveDetailList;
    }

    public void setLeaveDetailList(List<LeaveDetail> leaveDetailList) {
        this.leaveDetailList = leaveDetailList;
    }


    @XmlTransient
    public List<RequestDetail> getRequestDetailList() {
        return requestDetailList;
    }

    public void setRequestDetailList(List<RequestDetail> requestDetailList) {
        this.requestDetailList = requestDetailList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
