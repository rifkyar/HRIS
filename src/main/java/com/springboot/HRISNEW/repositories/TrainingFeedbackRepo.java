/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;


import com.springboot.HRISNEW.entities.TrainingFeedbackQuestions;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


/**
 *
 * @author User
 */
public interface TrainingFeedbackRepo extends CrudRepository<TrainingFeedbackQuestions, Integer>{
    
    @Query(value="SELECT TFQ.id, TFT.type, TFQ.question_content, TFQ.question_type FROM training_feedback_questions AS TFQ JOIN training_feedback_type AS TFT ON TFT.id = TFQ.training_feedback_type_id", nativeQuery=true)
    public List<Object[]> findallfeedback();
        
}
