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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HARRY-PC
 */
@Entity
@Table(name = "tr_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrLog.findAll", query = "SELECT t FROM TrLog t")
    , @NamedQuery(name = "TrLog.findByTransactionLogID", query = "SELECT t FROM TrLog t WHERE t.transactionLogID = :transactionLogID")
    , @NamedQuery(name = "TrLog.findByUserNIK", query = "SELECT t FROM TrLog t WHERE t.userNIK = :userNIK")
    , @NamedQuery(name = "TrLog.findByTime", query = "SELECT t FROM TrLog t WHERE t.time = :time")})
public class TrLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "TransactionLogID")
    private Integer transactionLogID;
    @Column(name = "UserNIK")
    private Integer userNIK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @JoinColumn(name = "LogID", referencedColumnName = "logID")
    @ManyToOne(fetch = FetchType.LAZY)
    private MLog logID;

    public TrLog() {
    }

    public TrLog(Integer transactionLogID) {
        this.transactionLogID = transactionLogID;
    }

    public TrLog(Integer transactionLogID, Date time) {
        this.transactionLogID = transactionLogID;
        this.time = time;
    }

    public Integer getTransactionLogID() {
        return transactionLogID;
    }

    public void setTransactionLogID(Integer transactionLogID) {
        this.transactionLogID = transactionLogID;
    }

    public Integer getUserNIK() {
        return userNIK;
    }

    public void setUserNIK(Integer userNIK) {
        this.userNIK = userNIK;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public MLog getLogID() {
        return logID;
    }

    public void setLogID(MLog logID) {
        this.logID = logID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionLogID != null ? transactionLogID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrLog)) {
            return false;
        }
        TrLog other = (TrLog) object;
        if ((this.transactionLogID == null && other.transactionLogID != null) || (this.transactionLogID != null && !this.transactionLogID.equals(other.transactionLogID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrLog[ transactionLogID=" + transactionLogID + " ]";
    }
    
}
