/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrainingUserRegistration;
import com.springboot.HRISNEW.entities.TrainingUserRequests;
import com.springboot.HRISNEW.interfaceservices.TrainingUserRequestsServiceInterface;
import com.springboot.HRISNEW.repositories.TrainingUserRequestsRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class TrainingUserRequestsServiceImpl implements TrainingUserRequestsServiceInterface{
    
    @Autowired
    TrainingUserRequestsRepo trainingUserRequestsRepo;

    @Override
    public TrainingUserRequests save(TrainingUserRequests t) {
      return  trainingUserRequestsRepo.save(t);
    }

    
}
