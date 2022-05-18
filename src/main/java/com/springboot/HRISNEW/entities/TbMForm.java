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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dharta
 */
@Entity
@Table(name = "tb_m_form")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMForm.findAll", query = "SELECT t FROM TbMForm t")
    , @NamedQuery(name = "TbMForm.findById", query = "SELECT t FROM TbMForm t WHERE t.id = :id")
    , @NamedQuery(name = "TbMForm.findByName", query = "SELECT t FROM TbMForm t WHERE t.name = :name")
    , @NamedQuery(name = "TbMForm.findByCreatedDate", query = "SELECT t FROM TbMForm t WHERE t.createdDate = :createdDate")})
public class TbMForm implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "form", fetch = FetchType.LAZY)
    private List<TbTrEmployeeForm> tbTrEmployeeFormList;

    public TbMForm() {
    }

    public TbMForm(Integer id) {
        this.id = id;
    }

    public TbMForm(Integer id, String name, Date createdDate) {
        this.id = id;
        this.name = name;
        this.createdDate = createdDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @XmlTransient
    public List<TbTrEmployeeForm> getTbTrEmployeeFormList() {
        return tbTrEmployeeFormList;
    }

    public void setTbTrEmployeeFormList(List<TbTrEmployeeForm> tbTrEmployeeFormList) {
        this.tbTrEmployeeFormList = tbTrEmployeeFormList;
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
        if (!(object instanceof TbMForm)) {
            return false;
        }
        TbMForm other = (TbMForm) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMForm[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
