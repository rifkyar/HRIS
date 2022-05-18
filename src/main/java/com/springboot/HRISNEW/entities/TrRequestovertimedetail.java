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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author HARRY-PC
 */
@Entity
@Table(name = "tr_requestovertimedetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TrRequestovertimedetail.findAll", query = "SELECT t FROM TrRequestovertimedetail t")
    , @NamedQuery(name = "TrRequestovertimedetail.findByOvertimeDetail", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.overtimeDetail = :overtimeDetail")
    , @NamedQuery(name = "TrRequestovertimedetail.findByTicketNumber", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.ticketNumber = :ticketNumber")
    , @NamedQuery(name = "TrRequestovertimedetail.findByStartDate", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.startDate = :startDate")
    , @NamedQuery(name = "TrRequestovertimedetail.findByDateType", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.dateType = :dateType")
    , @NamedQuery(name = "TrRequestovertimedetail.findByStartTime", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.startTime = :startTime")
    , @NamedQuery(name = "TrRequestovertimedetail.findByEndDate", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.endDate = :endDate")
    , @NamedQuery(name = "TrRequestovertimedetail.findByEndTime", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.endTime = :endTime")
    , @NamedQuery(name = "TrRequestovertimedetail.findByTotal", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.total = :total")
    , @NamedQuery(name = "TrRequestovertimedetail.findByLokasi", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.lokasi = :lokasi")
    , @NamedQuery(name = "TrRequestovertimedetail.findByTask", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.task = :task")
    , @NamedQuery(name = "TrRequestovertimedetail.findByPMorLeader", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.pMorLeader = :pMorLeader")
    , @NamedQuery(name = "TrRequestovertimedetail.findByRm", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.rm = :rm")
    , @NamedQuery(name = "TrRequestovertimedetail.findByMsfc", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.msfc = :msfc")
    , @NamedQuery(name = "TrRequestovertimedetail.findByRequestStatus", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.requestStatus = :requestStatus")
    , @NamedQuery(name = "TrRequestovertimedetail.findByCreateBy", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.createBy = :createBy")
    , @NamedQuery(name = "TrRequestovertimedetail.findByCreateDate", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.createDate = :createDate")
    , @NamedQuery(name = "TrRequestovertimedetail.findByUpdateBy", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.updateBy = :updateBy")
    , @NamedQuery(name = "TrRequestovertimedetail.findByUpdateDate", query = "SELECT t FROM TrRequestovertimedetail t WHERE t.updateDate = :updateDate")})
public class TrRequestovertimedetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OvertimeDetail")
    private Integer overtimeDetail;
    @Size(max = 15)
    @Column(name = "TicketNumber")
    private String ticketNumber;
    @Column(name = "StartDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Size(max = 10)
    @Column(name = "DateType")
    private String dateType;
    @Column(name = "StartTime")
    @Temporal(TemporalType.TIME)
    private Date startTime;
    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "EndTime")
    @Temporal(TemporalType.TIME)
    private Date endTime;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Total")
    private Float total;
    @Size(max = 100)
    @Column(name = "lokasi")
    private String lokasi;
    @Size(max = 250)
    @Column(name = "Task")
    private String task;
    @Column(name = "PMorLeader")
    private Boolean pMorLeader;
    @Column(name = "RM")
    private Boolean rm;
    @Column(name = "MSFC")
    private Boolean msfc;
    @Size(max = 20)
    @Column(name = "RequestStatus")
    private String requestStatus;
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
    @JoinColumn(name = "RequestOvertimeID", referencedColumnName = "RequestOvertimeID")
    @ManyToOne(fetch = FetchType.LAZY)
    private TrRequestovertime requestOvertimeID;

    public TrRequestovertimedetail() {
    }

    public TrRequestovertimedetail(String ticketNumber, Date startDate, String dateType,
            Date startTime, Date endDate, Date endTime, Float total, String task,
            String requestStatus, String createBy, Date createDate, TrRequestovertime requestOvertimeID) {
        this.ticketNumber = ticketNumber;
        this.startDate = startDate;
        this.dateType = dateType;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.total = total;
        this.task = task;
        this.requestStatus = requestStatus;
        this.createBy = createBy;
        this.createDate = createDate;
        this.requestOvertimeID = requestOvertimeID;
    }

    public TrRequestovertimedetail(String ticketNumber, Date startDate, String dateType,
            Date startTime, Date endDate, Date endTime, Float total, String task,
            Boolean pMorLeader, String requestStatus, String createBy, Date createDate,
            TrRequestovertime requestOvertimeID, String lokasi) {
        this.ticketNumber = ticketNumber;
        this.startDate = startDate;
        this.dateType = dateType;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.total = total;
        this.task = task;
        this.pMorLeader = pMorLeader;
        this.requestStatus = requestStatus;
        this.createBy = createBy;
        this.createDate = createDate;
        this.requestOvertimeID = requestOvertimeID;
        this.lokasi = lokasi;
    }

    public TrRequestovertimedetail(Date startDate, String dateType, Date startTime,
            Date endDate, Date endTime, Float total, String task, String requestStatus,
            String createBy, Date createDate, TrRequestovertime requestOvertimeID) {
        this.startDate = startDate;
        this.dateType = dateType;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.total = total;
        this.task = task;
        this.requestStatus = requestStatus;
        this.createBy = createBy;
        this.createDate = createDate;
        this.requestOvertimeID = requestOvertimeID;
    }

    public TrRequestovertimedetail(Integer overtimeDetail) {
        this.overtimeDetail = overtimeDetail;
    }

    public Integer getOvertimeDetail() {
        return overtimeDetail;
    }

    public void setOvertimeDetail(Integer overtimeDetail) {
        this.overtimeDetail = overtimeDetail;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Boolean getPMorLeader() {
        return pMorLeader;
    }

    public void setPMorLeader(Boolean pMorLeader) {
        this.pMorLeader = pMorLeader;
    }

    public Boolean getRm() {
        return rm;
    }

    public void setRm(Boolean rm) {
        this.rm = rm;
    }

    public Boolean getMsfc() {
        return msfc;
    }

    public void setMsfc(Boolean msfc) {
        this.msfc = msfc;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
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

    public TrRequestovertime getRequestOvertimeID() {
        return requestOvertimeID;
    }

    public void setRequestOvertimeID(TrRequestovertime requestOvertimeID) {
        this.requestOvertimeID = requestOvertimeID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (overtimeDetail != null ? overtimeDetail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrRequestovertimedetail)) {
            return false;
        }
        TrRequestovertimedetail other = (TrRequestovertimedetail) object;
        if ((this.overtimeDetail == null && other.overtimeDetail != null) || (this.overtimeDetail != null && !this.overtimeDetail.equals(other.overtimeDetail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TrRequestovertimedetail[ overtimeDetail=" + overtimeDetail + " ]";
    }

}
