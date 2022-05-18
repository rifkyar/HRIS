/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrainingFeedbackResponse;


/**
 *
 * @author User
 */
public interface ParticipantFeedbackFormServiceInterface {
    
    TrainingFeedbackResponse save(TrainingFeedbackResponse t);
    
    public Integer getFeedbackId(String id, int employeeid);
    
}
