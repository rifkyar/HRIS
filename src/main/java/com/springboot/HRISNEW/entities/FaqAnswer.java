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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author RAR
 */
@Entity
@Table(name = "faq_answer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FaqAnswer.findAll", query = "SELECT f FROM FaqAnswer f")
    , @NamedQuery(name = "FaqAnswer.findById", query = "SELECT f FROM FaqAnswer f WHERE f.id = :id")
    , @NamedQuery(name = "FaqAnswer.findByAnswer", query = "SELECT f FROM FaqAnswer f WHERE f.answer = :answer")})
public class FaqAnswer implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "answer")
    private String answer;
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
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FaqQuestion questionId;

    public FaqAnswer() {
    }

    public FaqAnswer(Integer id) {
        this.id = id;
    }

    public FaqAnswer(Integer id, String answer) {
        this.id = id;
        this.answer = answer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public FaqQuestion getQuestionId() {
        return questionId;
    }

    public void setQuestionId(FaqQuestion questionId) {
        this.questionId = questionId;
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
        if (!(object instanceof FaqAnswer)) {
            return false;
        }
        FaqAnswer other = (FaqAnswer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.springboot.HRISNEW.entities.FaqAnswer[ id=" + id + " ]";
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    
}
