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

/**
 *
 * @author HARRY-PC
 */
@Entity
@Table(name = "request_detail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RequestDetail.findAll", query = "SELECT r FROM RequestDetail r")
    , @NamedQuery(name = "RequestDetail.findById", query = "SELECT r FROM RequestDetail r WHERE r.id = :id")
    , @NamedQuery(name = "RequestDetail.findByLeaveDetailId", query = "SELECT r FROM RequestDetail r WHERE r.leaveDetailId = :leaveDetailId")
    , @NamedQuery(name = "RequestDetail.findByStartdate", query = "SELECT r FROM RequestDetail r WHERE r.startdate = :startdate")
    , @NamedQuery(name = "RequestDetail.findByEnddate", query = "SELECT r FROM RequestDetail r WHERE r.enddate = :enddate")
    , @NamedQuery(name = "RequestDetail.findByLeaveDays", query = "SELECT r FROM RequestDetail r WHERE r.leaveDays = :leaveDays")
    , @NamedQuery(name = "RequestDetail.findByHoliday", query = "SELECT r FROM RequestDetail r WHERE r.holiday = :holiday")
    , @NamedQuery(name = "RequestDetail.findByLeaveGiven", query = "SELECT r FROM RequestDetail r WHERE r.leaveGiven = :leaveGiven")
    , @NamedQuery(name = "RequestDetail.findByReduceLast", query = "SELECT r FROM RequestDetail r WHERE r.reduceLast = :reduceLast")
    , @NamedQuery(name = "RequestDetail.findByReduceCurrent", query = "SELECT r FROM RequestDetail r WHERE r.reduceCurrent = :reduceCurrent")
    , @NamedQuery(name = "RequestDetail.findBySubmittedDate", query = "SELECT r FROM RequestDetail r WHERE r.submittedDate = :submittedDate")
    , @NamedQuery(name = "RequestDetail.findByNik", query = "SELECT r FROM RequestDetail r WHERE r.nik = :nik")
    , @NamedQuery(name = "RequestDetail.findByPhoneNumber", query = "SELECT r FROM RequestDetail r WHERE r.phoneNumber = :phoneNumber")
    , @NamedQuery(name = "RequestDetail.findByRequestStatus", query = "SELECT r FROM RequestDetail r WHERE r.requestStatus = :requestStatus")
    , @NamedQuery(name = "RequestDetail.findByNeedbackupStatus", query = "SELECT r FROM RequestDetail r WHERE r.needbackupStatus = :needbackupStatus")
    , @NamedQuery(name = "RequestDetail.findByDirectReport", query = "SELECT r FROM RequestDetail r WHERE r.directReport = :directReport")
    , @NamedQuery(name = "RequestDetail.findByNextRole", query = "SELECT r FROM RequestDetail r WHERE r.nextRole = :nextRole")
    , @NamedQuery(name = "RequestDetail.findBySoId", query = "SELECT r FROM RequestDetail r WHERE r.soId = :soId")
    , @NamedQuery(name = "RequestDetail.findByFlag", query = "SELECT r FROM RequestDetail r WHERE r.flag = :flag")
    , @NamedQuery(name = "RequestDetail.findByIsdelete", query = "SELECT r FROM RequestDetail r WHERE r.isdelete = :isdelete")
    , @NamedQuery(name = "RequestDetail.findByFileName", query = "SELECT r FROM RequestDetail r WHERE r.fileName = :fileName")})
public class RequestDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "id")
    private String id;
    @Column(name = "startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "enddate")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Lob
    @Size(max = 65535)
    @Column(name = "notes")
    private String notes;
    @Column(name = "leave_days")
    private Integer leaveDays;
    @Column(name = "holiday")
    private Integer holiday;
    @Column(name = "leave_given")
    private Integer leaveGiven;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reduce_last")
    private int reduceLast;
    @Basic(optional = false)
    @NotNull
    @Column(name = "reduce_current")
    private int reduceCurrent;
    @Column(name = "submitted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedDate;
    @Size(max = 14)
    @Column(name = "phone_number")
    private String phoneNumber;
    @Size(max = 10)
    @Column(name = "request_status")
    private String requestStatus;
    @Column(name = "needbackup_status")
    private Boolean needbackupStatus;
    @Column(name = "direct_report")
    private Integer directReport;
    @Column(name = "next_role")
    private Integer nextRole;
    @Size(max = 100)
    @Column(name = "so_id")
    private String soId;
    @Lob
    @Column(name = "file")
    private byte[] file;
    @Size(max = 201)
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "flag")
    private Boolean flag;
    @Column(name = "isdelete")
    private Boolean isdelete;
    @JoinColumn(name = "leave_detail_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private LeaveType leaveDetailId;
    @JoinColumn(name = "nik", referencedColumnName = "nik")
    @ManyToOne(fetch = FetchType.LAZY)
    private RequesterInformation nik;

    public RequestDetail() {
    }

    public RequestDetail(String id) {
        this.id = id;
    }

    public RequestDetail(String id, int reduceLast, int reduceCurrent) {
        this.id = id;
        this.reduceLast = reduceLast;
        this.reduceCurrent = reduceCurrent;
    }
    
    public RequestDetail(String id, Date startdate, Date enddate, String notes, Integer leaveDays, Integer holiday, Integer leaveGiven, Integer reduceLast,
            Integer reduceCurrent, Date submittedDate, String requestStatus, LeaveType leaveDetailId,
            RequesterInformation nik, Boolean needbackupStatus, Integer directReport,
            String soId, boolean flag, Integer nextRole,boolean isdelete, String phonenumber, byte[] file, String fileName) {
        this.id = id;
        this.startdate = startdate;
        this.enddate = enddate;
        this.notes = notes;
        this.leaveDays = leaveDays;
        this.holiday = holiday;
        this.leaveGiven = leaveGiven;
        this.reduceLast = reduceLast;
        this.reduceCurrent = reduceCurrent;
        this.submittedDate = submittedDate;
        this.requestStatus = requestStatus;
        this.leaveDetailId = leaveDetailId;
        this.nik = nik;
        this.needbackupStatus = needbackupStatus;
        this.directReport = directReport;
        this.soId = soId;
        this.flag = flag;
        this.nextRole = nextRole;
        this.isdelete=isdelete;
        this.phoneNumber=phonenumber;
        this.file=file;
        this.fileName=fileName;
        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }


    public Integer getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveDays(Integer leaveDays) {
        this.leaveDays = leaveDays;
    }

    public Integer getHoliday() {
        return holiday;
    }

    public void setHoliday(Integer holiday) {
        this.holiday = holiday;
    }

    public Integer getLeaveGiven() {
        return leaveGiven;
    }

    public void setLeaveGiven(Integer leaveGiven) {
        this.leaveGiven = leaveGiven;
    }

    public int getReduceLast() {
        return reduceLast;
    }

    public void setReduceLast(int reduceLast) {
        this.reduceLast = reduceLast;
    }

    public int getReduceCurrent() {
        return reduceCurrent;
    }

    public void setReduceCurrent(int reduceCurrent) {
        this.reduceCurrent = reduceCurrent;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public RequesterInformation getNik() {
        return nik;
    }

    public void setNik(RequesterInformation nik) {
        this.nik = nik;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Boolean getNeedbackupStatus() {
        return needbackupStatus;
    }

    public void setNeedbackupStatus(Boolean needbackupStatus) {
        this.needbackupStatus = needbackupStatus;
    }

    public Integer getDirectReport() {
        return directReport;
    }

    public void setDirectReport(Integer directReport) {
        this.directReport = directReport;
    }

    public Integer getNextRole() {
        return nextRole;
    }

    public void setNextRole(Integer nextRole) {
        this.nextRole = nextRole;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Boolean getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Boolean isdelete) {
        this.isdelete = isdelete;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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
        if (!(object instanceof RequestDetail)) {
            return false;
        }
        RequestDetail other = (RequestDetail) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.RequestDetail[ id=" + id + " ]";
    }


    public LeaveType getLeaveDetailId() {
        return leaveDetailId;
    }

    public void setLeaveDetailId(LeaveType leaveDetailId) {
        this.leaveDetailId = leaveDetailId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}
