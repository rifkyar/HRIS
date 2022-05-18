/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMStatus;
import com.springboot.HRISNEW.interfaceservices.StatusServiceInterface;
import com.springboot.HRISNEW.repositories.StatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class StatusServiceImpl implements StatusServiceInterface{
    
    @Autowired
    private StatusRepo statusRepo;

    @Override
    public TbMStatus findOnebyId(String id) {
        return statusRepo.findOnebyId(id);
    }
    
}
