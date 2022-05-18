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
 * @author HARRY-PC
 */
@Entity
@Table(name = "temp_so")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TempSo.findAll", query = "SELECT t FROM TempSo t")
    , @NamedQuery(name = "TempSo.findById", query = "SELECT t FROM TempSo t WHERE t.id = :id")
    , @NamedQuery(name = "TempSo.findBySoId", query = "SELECT t FROM TempSo t WHERE t.soId = :soId")
    , @NamedQuery(name = "TempSo.findByBatch", query = "SELECT t FROM TempSo t WHERE t.batch = :batch")})
public class TempSo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 25)
    @Column(name = "so_id")
    private String soId;
    @Size(max = 25)
    @Column(name = "batch")
    private String batch;

    public TempSo() {
    }
    
    public TempSo(Integer id, String soId, String batch) {
        this.id = id;
        this.soId = soId;
        this.batch = batch;
    }

    public TempSo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
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
        if (!(object instanceof TempSo)) {
            return false;
        }
        TempSo other = (TempSo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TempSo[ id=" + id + " ]";
    }
    
}
