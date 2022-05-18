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
@Table(name = "tb_m_category")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TbMCategory.findAll", query = "SELECT t FROM TbMCategory t")
    , @NamedQuery(name = "TbMCategory.findById", query = "SELECT t FROM TbMCategory t WHERE t.id = :id")
    , @NamedQuery(name = "TbMCategory.findByCategory", query = "SELECT t FROM TbMCategory t WHERE t.category = :category")})
public class TbMCategory implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "category")
    private String category;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "category", fetch = FetchType.LAZY)
    private List<TbMSkill> tbMSkillList;

    public TbMCategory() {
    }

    public TbMCategory(Integer id) {
        this.id = id;
    }

    public TbMCategory(Integer id, String category) {
        this.id = id;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<TbMSkill> getTbMSkillList() {
        return tbMSkillList;
    }

    public void setTbMSkillList(List<TbMSkill> tbMSkillList) {
        this.tbMSkillList = tbMSkillList;
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
        if (!(object instanceof TbMCategory)) {
            return false;
        }
        TbMCategory other = (TbMCategory) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.TbMCategory[ id=" + id + " ]";
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
}
