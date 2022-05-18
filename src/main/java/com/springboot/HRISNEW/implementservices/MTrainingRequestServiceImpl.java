/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.interfaceservices.MTrainingRequestServiceInterface;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.springboot.HRISNEW.repositories.ManageTrainingRequestRepo;
import java.sql.Timestamp;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class MTrainingRequestServiceImpl implements MTrainingRequestServiceInterface{
    
    @Autowired
    ManageTrainingRequestRepo manageTrainingRequest;
    
    @Override
    public List<Object[]> findallrequest() {
        return manageTrainingRequest.findallrequest();
    }

    @Override
    public void acceptrequest(Integer id, Integer nik, Timestamp timestamp) {
        manageTrainingRequest.accceptrequest(id, nik, timestamp);
    }

    @Override
    public void rejectRequest(Integer id, Integer nik, Timestamp timestamp, String remark) {
        manageTrainingRequest.rejectRequest(id, nik, timestamp, remark);
    }

    }
