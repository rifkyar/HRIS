/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.interfaceservices.tbMParameterInterfaceService;
import com.springboot.HRISNEW.repositories.tbMParameterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *
 * @author User
 */
@Service
public class tbMParameterServiceImplement implements tbMParameterInterfaceService{
    
    @Autowired
    tbMParameterRepo tbMParameterRepo;
    
    @Override
    public String findCheckInURL() {
        return tbMParameterRepo.findCheckInURL();
    }

    @Override
    public String findFeedbackFormURL() {
        return tbMParameterRepo.findFeedbackFormURL();
    }

    @Override
    public String findQRPath() {
        return tbMParameterRepo.findQRPath();
    }

    @Override
    public String findQRSavePath() {
        return tbMParameterRepo.findQRSavePath();
    }

    @Override
    public String findConfirmURL() {
        return tbMParameterRepo.findConfirmURL();
    }

    @Override
    public String findDeclineURL() {
        return tbMParameterRepo.findDeclineURL();
    }

}
