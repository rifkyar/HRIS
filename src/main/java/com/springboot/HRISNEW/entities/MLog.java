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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author HARRY-PC
 */
@Entity
@Table(name = "m_log")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MLog.findAll", query = "SELECT m FROM MLog m")
    , @NamedQuery(name = "MLog.findByLogID", query = "SELECT m FROM MLog m WHERE m.logID = :logID")
    , @NamedQuery(name = "MLog.findByActivity", query = "SELECT m FROM MLog m WHERE m.activity = :activity")
    , @NamedQuery(name = "MLog.findByCreateBy", query = "SELECT m FROM MLog m WHERE m.createBy = :createBy")
    , @NamedQuery(name = "MLog.findByCreateDate", query = "SELECT m FROM MLog m WHERE m.createDate = :createDate")
    , @NamedQuery(name = "MLog.findByUpdateBy", query = "SELECT m FROM MLog m WHERE m.updateBy = :updateBy")
    , @NamedQuery(name = "MLog.findByUpdateDate", query = "SELECT m FROM MLog m WHERE m.updateDate = :updateDate")})
public class MLog implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "logID")
    private Integer logID;
    @Size(max = 100)
    @Column(name = "Activity")
    private String activity;
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
    @OneToMany(mappedBy = "logID", fetch = FetchType.LAZY)
    private List<TrLog> trLogList;

    public MLog() {
    }

    public MLog(Integer logID) {
        this.logID = logID;
    }

    public Integer getLogID() {
        return logID;
    }

    public void setLogID(Integer logID) {
        this.logID = logID;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
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

    @XmlTransient
    public List<TrLog> getTrLogList() {
        return trLogList;
    }

    public void setTrLogList(List<TrLog> trLogList) {
        this.trLogList = trLogList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logID != null ? logID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MLog)) {
            return false;
        }
        MLog other = (MLog) object;
        if ((this.logID == null && other.logID != null) || (this.logID != null && !this.logID.equals(other.logID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.MLog[ logID=" + logID + " ]";
    }
    
}
