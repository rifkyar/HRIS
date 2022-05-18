/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrainingFeedbackResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author User
 */
public interface ParticipantFeedbackFormRepo extends CrudRepository<TrainingFeedbackResponse, Integer>{
    
    @Query(value = "select id from training_feedback_response where training_transaction_id = ?1 and empl_nik in (SELECT nik FROM requester_information WHERE source_nik IN (SELECT source_nik FROM requester_information WHERE nik = ?2)) order by id limit 1", nativeQuery = true)
    public Integer getFeedbackId(String id, int employeeid);
    
}
