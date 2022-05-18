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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "tr_requestovertime")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrRequestovertime.findAll", query = "SELECT t FROM TrRequestovertime t")
    , @NamedQuery(name = "TrRequestovertime.findByRequestOvertimeID", query = "SELECT t FROM TrRequestovertime t WHERE t.requestOvertimeID = :requestOvertimeID")
    , @NamedQuery(name = "TrRequestovertime.findByEmployeeNik", query = "SELECT t FROM TrRequestovertime t WHERE t.employeeNik = :employeeNik")
    , @NamedQuery(name = "TrRequestovertime.findByEmployeeName", query = "SELECT t FROM TrRequestovertime t WHERE t.employeeName = :employeeName")
    , @NamedQuery(name = "TrRequestovertime.findByEmployeePosition", query = "SELECT t FROM TrRequestovertime t WHERE t.employeePosition = :employeePosition")
    , @NamedQuery(name = "TrRequestovertime.findByContractStatus", query = "SELECT t FROM TrRequestovertime t WHERE t.contractStatus = :contractStatus")
    , @NamedQuery(name = "TrRequestovertime.findByPeriod", query = "SELECT t FROM TrRequestovertime t WHERE t.period = :period")
    , @NamedQuery(name = "TrRequestovertime.findByJobDesc", query = "SELECT t FROM TrRequestovertime t WHERE t.jobDesc = :jobDesc")
    , @NamedQuery(name = "TrRequestovertime.findByJobTargetOther", query = "SELECT t FROM TrRequestovertime t WHERE t.jobTargetOther = :jobTargetOther")
    , @NamedQuery(name = "TrRequestovertime.findByPICName", query = "SELECT t FROM TrRequestovertime t WHERE t.pICName = :pICName")
    , @NamedQuery(name = "TrRequestovertime.findByPICPosition", query = "SELECT t FROM TrRequestovertime t WHERE t.pICPosition = :pICPosition")
    , @NamedQuery(name = "TrRequestovertime.findByTotalOvertime", query = "SELECT t FROM TrRequestovertime t WHERE t.totalOvertime = :totalOvertime")
    , @NamedQuery(name = "TrRequestovertime.findByLocation", query = "SELECT t FROM TrRequestovertime t WHERE t.location = :location")
    , @NamedQuery(name = "TrRequestovertime.findByNextApproval", query = "SELECT t FROM TrRequestovertime t WHERE t.nextApproval = :nextApproval")
    , @NamedQuery(name = "TrRequestovertime.findByRequestStatus", query = "SELECT t FROM TrRequestovertime t WHERE t.requestStatus = :requestStatus")
    , @NamedQuery(name = "TrRequestovertime.findByCreatedBy", query = "SELECT t FROM TrRequestovertime t WHERE t.createdBy = :createdBy")
    , @NamedQuery(name = "TrRequestovertime.findByCreateDate", query = "SELECT t FROM TrRequestovertime t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TrRequestovertime.findByUpdateBy", query = "SELECT t FROM TrRequestovertime t WHERE t.updateBy = :updateBy")
    , @NamedQuery(name = "TrRequestovertime.findByUpdateDate", query = "SELECT t FROM TrRequestovertime t WHERE t.updateDate = :updateDate")})
public class TrRequestovertime implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "RequestOvertimeID")
    private Integer requestOvertimeID;
    @Column(name = "employee_nik")
    private Integer employeeNik;
    @Size(max = 100)
    @Column(name = "EmployeeName")
    private String employeeName;
    @Size(max = 100)
    @Column(name = "EmployeePosition")
    private String employeePosition;
    @Size(max = 60)
    @Column(name = "contract_status")
    private String contractStatus;
    @Column(name = "period")
    @Temporal(TemporalType.DATE)
    private Date period;
    @Size(max = 150)
    @Column(name = "JobDesc")
    private String jobDesc;
    @Column(name = "JobTargetOther")
    private Integer jobTargetOther;
    @Size(max = 100)
    @Column(name = "PICName")
    private String pICName;
    @Size(max = 100)
    @Column(name = "PICPosition")
    private String pICPosition;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TotalOvertime")
    private Float totalOvertime;
    @Size(max = 100)
    @Column(name = "Location")
    private String location;
    @Column(name = "NextApproval")
    private Integer nextApproval;
    @Size(max = 50)
    @Column(name = "RequestStatus")
    private String requestStatus;
    @Size(max = 100)
    @Column(name = "CreatedBy")
    private String createdBy;
    @Column(name = "CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @Size(max = 100)
    @Column(name = "UpdateBy")
    private String updateBy;
    @Column(name = "UpdateDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;
    @JoinColumn(name = "so_id", referencedColumnName = "so_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrSoovertime soId;
    @OneToMany(mappedBy = "requestOvertimeID", fetch = FetchType.LAZY)
    private List<TrRequestovertimedetail> trRequestovertimedetailList;

    public TrRequestovertime() {
    }

    public TrRequestovertime(Integer employeeNik, String employeeName, String employeePosition, String location, 
            Date createDate, TrSoovertime soId) {
        this.employeeNik = employeeNik;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.location = location;
        this.createDate = createDate;
        this.soId = soId;
    }

    public TrRequestovertime(Integer employeeNik, String employeeName, String employeePosition, Date period, 
            String pICName, String pICPosition, Float totalOvertime, String location, Integer nextApproval, 
            String requestStatus, String createdBy, Date createDate, TrSoovertime soId) {
        this.employeeNik = employeeNik;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.period = period;
        this.pICName = pICName;
        this.pICPosition = pICPosition;
        this.totalOvertime = totalOvertime;
        this.location = location;
        this.nextApproval = nextApproval;
        this.requestStatus = requestStatus;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.soId = soId;
    }

    public TrRequestovertime(Integer employeeNik, String employeeName, String employeePosition, 
            String contractStatus, Date period, String pICName, String pICPosition, Float totalOvertime, 
            String location, Integer nextApproval, String requestStatus, String createdBy, Date createDate, 
            TrSoovertime soId) {
        this.employeeNik = employeeNik;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.contractStatus = contractStatus;
        this.period = period;
        this.pICName = pICName;
        this.pICPosition = pICPosition;
        this.totalOvertime = totalOvertime;
        this.location = location;
        this.nextApproval = nextApproval;
        this.requestStatus = requestStatus;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.soId = soId;
    }

    public TrRequestovertime(Integer requestOvertimeID, Integer employeeNik, String employeeName, String employeePosition, Date period, String jobDesc, Integer jobTargetOther, String pICName, String pICPosition, Float totalOvertime, String location, Integer nextApproval, String requestStatus, String createdBy, Date createDate, String updateBy, Date updateDate, TrSoovertime soId, List<TrRequestovertimedetail> trRequestovertimedetailList) {
        this.requestOvertimeID = requestOvertimeID;
        this.employeeNik = employeeNik;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.period = period;
        this.jobDesc = jobDesc;
        this.jobTargetOther = jobTargetOther;
        this.pICName = pICName;
        this.pICPosition = pICPosition;
        this.totalOvertime = totalOvertime;
        this.location = location;
        this.nextApproval = nextApproval;
        this.requestStatus = requestStatus;
        this.createdBy = createdBy;
        this.createDate = createDate;
        this.updateBy = updateBy;
        this.updateDate = updateDate;
        this.soId = soId;
        this.trRequestovertimedetailList = trRequestovertimedetailList;
    }
    
    public TrRequestovertime(Integer requestOvertimeID) {
        this.requestOvertimeID = requestOvertimeID;
    }

    public Integer getRequestOvertimeID() {
        return requestOvertimeID;
    }

    public void setRequestOvertimeID(Integer requestOvertimeID) {
        this.requestOvertimeID = requestOvertimeID;
    }

    public Integer getEmployeeNik() {
        return employeeNik;
    }

    public void setEmployeeNik(Integer employeeNik) {
        this.employeeNik = employeeNik;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public Integer getJobTargetOther() {
        return jobTargetOther;
    }

    public void setJobTargetOther(Integer jobTargetOther) {
        this.jobTargetOther = jobTargetOther;
    }

    public String getPICName() {
        return pICName;
    }

    public void setPICName(String pICName) {
        this.pICName = pICName;
    }

    public String getPICPosition() {
        return pICPosition;
    }

    public void setPICPosition(String pICPosition) {
        this.pICPosition = pICPosition;
    }

    public Float getTotalOvertime() {
        return totalOvertime;
    }

    public void setTotalOvertime(Float totalOvertime) {
        this.totalOvertime = totalOvertime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getNextApproval() {
        return nextApproval;
    }

    public void setNextApproval(Integer nextApproval) {
        this.nextApproval = nextApproval;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public TrSoovertime getSoId() {
        return soId;
    }

    public void setSoId(TrSoovertime soId) {
        this.soId = soId;
    }

    @XmlTransient
    public List<TrRequestovertimedetail> getTrRequestovertimedetailList() {
        return trRequestovertimedetailList;
    }

    public void setTrRequestovertimedetailList(List<TrRequestovertimedetail> trRequestovertimedetailList) {
        this.trRequestovertimedetailList = trRequestovertimedetailList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (requestOvertimeID != null ? requestOvertimeID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrRequestovertime)) {
            return false;
        }
        TrRequestovertime other = (TrRequestovertime) object;
        if ((this.requestOvertimeID == null && other.requestOvertimeID != null) || (this.requestOvertimeID != null && !this.requestOvertimeID.equals(other.requestOvertimeID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrRequestovertime[ requestOvertimeID=" + requestOvertimeID + " ]";
    }
    
}
