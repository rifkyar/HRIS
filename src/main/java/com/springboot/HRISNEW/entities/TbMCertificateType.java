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
@Table(name = "tb_m_certificate_type")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMCertificateType.findAll", query = "SELECT t FROM TbMCertificateType t")
    , @NamedQuery(name = "TbMCertificateType.findById", query = "SELECT t FROM TbMCertificateType t WHERE t.id = :id")
    , @NamedQuery(name = "TbMCertificateType.findByType", query = "SELECT t FROM TbMCertificateType t WHERE t.type = :type")})
public class TbMCertificateType implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "type")
    private String type;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "certificate", fetch = FetchType.LAZY)
    private List<TbTrCertification> tbTrCertificationList;

    public TbMCertificateType() {
    }

    public TbMCertificateType(Integer id) {
        this.id = id;
    }

    public TbMCertificateType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<TbTrCertification> getTbTrCertificationList() {
        return tbTrCertificationList;
    }

    public void setTbTrCertificationList(List<TbTrCertification> tbTrCertificationList) {
        this.tbTrCertificationList = tbTrCertificationList;
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
        if (!(object instanceof TbMCertificateType)) {
            return false;
        }
        TbMCertificateType other = (TbMCertificateType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMCertificateType[ id=" + id + " ]";
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
