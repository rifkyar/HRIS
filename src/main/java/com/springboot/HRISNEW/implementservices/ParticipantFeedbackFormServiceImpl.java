/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrainingFeedbackResponse;
import com.springboot.HRISNEW.interfaceservices.ParticipantFeedbackFormServiceInterface;
import com.springboot.HRISNEW.repositories.ParticipantFeedbackFormRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class ParticipantFeedbackFormServiceImpl implements ParticipantFeedbackFormServiceInterface{
    
    @Autowired
    ParticipantFeedbackFormRepo participantfeedbackformrepo;
    
    @Override
    public TrainingFeedbackResponse save(TrainingFeedbackResponse t) {
       return participantfeedbackformrepo.save(t);
    }
    
    @Override
    public Integer getFeedbackId(String id, int employeeid) {
        return participantfeedbackformrepo.getFeedbackId(id, employeeid);
    }}
