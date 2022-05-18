/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrainingFeedbackType;
import com.springboot.HRISNEW.interfaceservices.TFeedbackServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.HRISNEW.repositories.TrainingFeedbackRepo;
import com.springboot.HRISNEW.repositories.TrainingFeedbackTypeRepo;
import java.util.List;

/**
 *
 * @author User
 */
@Service
public class TFeedbackServiceImplement implements TFeedbackServiceInterface {

    @Autowired
    TrainingFeedbackRepo trainingFeedbackRepo;
    
    @Autowired
    TrainingFeedbackTypeRepo typeRepo;
    
    @Override
    public List<Object[]> findallfeedback() {
        return trainingFeedbackRepo.findallfeedback();
    }

    @Override
    public Iterable<TrainingFeedbackType> findAll() {
        return typeRepo.findAll();
    }
    
    
    

}
