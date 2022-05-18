/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.FaqAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAR
 */
@Repository
public interface AnswerRepo extends CrudRepository<FaqAnswer, Integer>{
    
    @Query(value = "SELECT q.question, GROUP_CONCAT( an.answer) FROM faq_answer an JOIN faq_question q ON an.question_id = q.id GROUP BY q.question", nativeQuery = true)
    public Iterable<FaqAnswer> getanswer();
    
    @Query(value = "select * from faq_answer where isdeleted = 0", nativeQuery = true)
    public Iterable<FaqAnswer> tampilaktif();
    }
