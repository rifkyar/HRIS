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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Dharta
 */
@Entity
@Table(name = "tb_m_status")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMStatus.findAll", query = "SELECT t FROM TbMStatus t")
    , @NamedQuery(name = "TbMStatus.findById", query = "SELECT t FROM TbMStatus t WHERE t.id = :id")
    , @NamedQuery(name = "TbMStatus.findByName", query = "SELECT t FROM TbMStatus t WHERE t.name = :name")
    , @NamedQuery(name = "TbMStatus.findByValue", query = "SELECT t FROM TbMStatus t WHERE t.value = :value")})
public class TbMStatus implements Serializable {

    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "value")
    private String value;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status", fetch = FetchType.LAZY)
    private List<TbTrApprovalReimburse> tbTrApprovalReimburseList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status", fetch = FetchType.LAZY)
    private List<TbTrReimburseDetail> tbTrReimburseDetailList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status", fetch = FetchType.LAZY)
    private List<TbTrReimburse> tbTrReimburseList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "status", fetch = FetchType.LAZY)
    private List<TbTrApprovalDocumentRequest> tbTrApprovalDocumentRequestList;

    public TbMStatus() {
    }

    public TbMStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<TbTrApprovalDocumentRequest> getTbTrApprovalDocumentRequestList() {
        return tbTrApprovalDocumentRequestList;
    }

    public void setTbTrApprovalDocumentRequestList(List<TbTrApprovalDocumentRequest> tbTrApprovalDocumentRequestList) {
        this.tbTrApprovalDocumentRequestList = tbTrApprovalDocumentRequestList;
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
        if (!(object instanceof TbMStatus)) {
            return false;
        }
        TbMStatus other = (TbMStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMStatus[ id=" + id + " ]";
    }


    @XmlTransient
    public List<TbTrReimburseDetail> getTbTrReimburseDetailList() {
        return tbTrReimburseDetailList;
    }

    public void setTbTrReimburseDetailList(List<TbTrReimburseDetail> tbTrReimburseDetailList) {
        this.tbTrReimburseDetailList = tbTrReimburseDetailList;
    }

    @XmlTransient
    public List<TbTrReimburse> getTbTrReimburseList() {
        return tbTrReimburseList;
    }

    public void setTbTrReimburseList(List<TbTrReimburse> tbTrReimburseList) {
        this.tbTrReimburseList = tbTrReimburseList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlTransient
    public List<TbTrApprovalReimburse> getTbTrApprovalReimburseList() {
        return tbTrApprovalReimburseList;
    }

    public void setTbTrApprovalReimburseList(List<TbTrApprovalReimburse> tbTrApprovalReimburseList) {
        this.tbTrApprovalReimburseList = tbTrApprovalReimburseList;
    }
    
}
