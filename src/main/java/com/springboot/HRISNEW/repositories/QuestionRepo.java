/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.FaqQuestion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAR
 */
@Repository
public interface QuestionRepo extends CrudRepository<FaqQuestion, Integer>{
    @Query(value="select * from faq_question where isdeleted = 0",nativeQuery = true)
    public Iterable<FaqQuestion> tampildata();
}
