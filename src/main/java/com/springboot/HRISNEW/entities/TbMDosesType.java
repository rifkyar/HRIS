/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author RAR
 */
@Entity
@Table(name = "tb_m_doses_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMDosesType.findAll", query = "SELECT t FROM TbMDosesType t")
    , @NamedQuery(name = "TbMDosesType.findById", query = "SELECT t FROM TbMDosesType t WHERE t.id = :id")
    , @NamedQuery(name = "TbMDosesType.findByDosesName", query = "SELECT t FROM TbMDosesType t WHERE t.dosesName = :dosesName")})
public class TbMDosesType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "Doses_Name")
    private String dosesName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dosesType", fetch = FetchType.LAZY)
    private List<TbTrVaccine> tbTrVaccineList;

    public TbMDosesType() {
    }

    public TbMDosesType(Integer id) {
        this.id = id;
    }

    public TbMDosesType(Integer id, String dosesName) {
        this.id = id;
        this.dosesName = dosesName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDosesName() {
        return dosesName;
    }

    public void setDosesName(String dosesName) {
        this.dosesName = dosesName;
    }

    @XmlTransient
    public List<TbTrVaccine> getTbTrVaccineList() {
        return tbTrVaccineList;
    }

    public void setTbTrVaccineList(List<TbTrVaccine> tbTrVaccineList) {
        this.tbTrVaccineList = tbTrVaccineList;
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
        if (!(object instanceof TbMDosesType)) {
            return false;
        }
        TbMDosesType other = (TbMDosesType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMDosesType[ id=" + id + " ]";
    }
    
}
