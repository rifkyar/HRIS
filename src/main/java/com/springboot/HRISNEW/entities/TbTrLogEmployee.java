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
 * @author USER
 */
@Entity
@Table(name = "tb_tr_log_employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrLogEmployee.findAll", query = "SELECT t FROM TbTrLogEmployee t")
    , @NamedQuery(name = "TbTrLogEmployee.findById", query = "SELECT t FROM TbTrLogEmployee t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrLogEmployee.findBySubmittedDate", query = "SELECT t FROM TbTrLogEmployee t WHERE t.submittedDate = :submittedDate")
    , @NamedQuery(name = "TbTrLogEmployee.findByStatus", query = "SELECT t FROM TbTrLogEmployee t WHERE t.status = :status")})
public class TbTrLogEmployee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "submitted_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date submittedDate;
    @Size(max = 10)
    @Column(name = "status")
    private String status;
    @JoinColumn(name = "empl_nik", referencedColumnName = "empl_nik")
    @ManyToOne(fetch = FetchType.LAZY)
    private TbMEmployee emplNik;

    public TbTrLogEmployee() {
    }

    public TbTrLogEmployee(TbMEmployee empl_nik, Date submitted_date, String status) {
        this.emplNik = empl_nik;
        this.submittedDate = submitted_date;
        this.status = status;
    }

    public TbTrLogEmployee(String done) {
        this.status = done;
    }

    public TbTrLogEmployee(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TbMEmployee getEmplNik() {
        return emplNik;
    }

    public void setEmplNik(TbMEmployee emplNik) {
        this.emplNik = emplNik;
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
        if (!(object instanceof TbTrLogEmployee)) {
            return false;
        }
        TbTrLogEmployee other = (TbTrLogEmployee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrLogEmployee[ id=" + id + " ]";
    }

}
