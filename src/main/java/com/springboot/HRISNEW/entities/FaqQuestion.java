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
@Table(name = "faq_question")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FaqQuestion.findAll", query = "SELECT f FROM FaqQuestion f")
    , @NamedQuery(name = "FaqQuestion.findById", query = "SELECT f FROM FaqQuestion f WHERE f.id = :id")
    , @NamedQuery(name = "FaqQuestion.findByQuestion", query = "SELECT f FROM FaqQuestion f WHERE f.question = :question")})
public class FaqQuestion implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "question")
    private String question;
    @Basic(optional = false)
    @NotNull
    @Column(name = "isdeleted")
    private boolean isdeleted;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionId", fetch = FetchType.LAZY)
    private List<FaqAnswer> faqAnswerList;

    public FaqQuestion() {
    }

    public FaqQuestion(Integer id) {
        this.id = id;
    }

    public FaqQuestion(Integer id, String question) {
        this.id = id;
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    @XmlTransient
    public List<FaqAnswer> getFaqAnswerList() {
        return faqAnswerList;
    }

    public void setFaqAnswerList(List<FaqAnswer> faqAnswerList) {
        this.faqAnswerList = faqAnswerList;
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
        if (!(object instanceof FaqQuestion)) {
            return false;
        }
        FaqQuestion other = (FaqQuestion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.FaqQuestion[ id=" + id + " ]";
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    
}
