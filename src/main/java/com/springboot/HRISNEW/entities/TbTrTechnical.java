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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RAR
 */
@Entity
@Table(name = "tb_tr_technical")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbTrTechnical.findAll", query = "SELECT t FROM TbTrTechnical t")
    , @NamedQuery(name = "TbTrTechnical.findById", query = "SELECT t FROM TbTrTechnical t WHERE t.id = :id")
    , @NamedQuery(name = "TbTrTechnical.findByEmplNik", query = "SELECT t FROM TbTrTechnical t WHERE t.emplNik = :emplNik")
    , @NamedQuery(name = "TbTrTechnical.findByIsdeleted", query = "SELECT t FROM TbTrTechnical t WHERE t.isdeleted = :isdeleted")})
public class TbTrTechnical implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "empl_nik")
    private Integer emplNik;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isdeleted")
    private boolean isdeleted;
    @JoinColumn(name = "skill", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private TbMSkill skill;

    public TbTrTechnical() {
    }

    public TbTrTechnical(Integer id) {
        this.id = id;
    }

    public TbTrTechnical(Integer id, boolean isdeleted) {
        this.id = id;
        this.isdeleted = isdeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmplNik() {
        return emplNik;
    }

    public void setEmplNik(Integer emplNik) {
        this.emplNik = emplNik;
    }

    public boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public TbMSkill getSkill() {
        return skill;
    }

    public void setSkill(TbMSkill skill) {
        this.skill = skill;
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
        if (!(object instanceof TbTrTechnical)) {
            return false;
        }
        TbTrTechnical other = (TbTrTechnical) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbTrTechnical[ id=" + id + " ]";
    }
    
}
