/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
 * @author HARRY-PC
 */
@Entity
@Table(name = "m_others")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MOthers.findAll", query = "SELECT m FROM MOthers m")
    , @NamedQuery(name = "MOthers.findById", query = "SELECT m FROM MOthers m WHERE m.id = :id")
    , @NamedQuery(name = "MOthers.findByName", query = "SELECT m FROM MOthers m WHERE m.name = :name")
    , @NamedQuery(name = "MOthers.findByValue", query = "SELECT m FROM MOthers m WHERE m.value = :value")})
public class MOthers implements Serializable {

    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @Size(max = 100)
    @Column(name = "value")
    private String value;
    @OneToMany(mappedBy = "mOtherId", fetch = FetchType.LAZY)
    private List<TrainingUserRegistration> trainingUserRegistrationList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public MOthers() {
    }

    public MOthers(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        if (!(object instanceof MOthers)) {
            return false;
        }
        MOthers other = (MOthers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.MOthers[ id=" + id + " ]";
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
    public List<TrainingUserRegistration> getTrainingUserRegistrationList() {
        return trainingUserRegistrationList;
    }

    public void setTrainingUserRegistrationList(List<TrainingUserRegistration> trainingUserRegistrationList) {
        this.trainingUserRegistrationList = trainingUserRegistrationList;
    }
    
}
