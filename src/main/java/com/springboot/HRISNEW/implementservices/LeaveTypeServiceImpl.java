/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.LeaveType;
import com.springboot.HRISNEW.interfaceservices.LeaveTypeServiceInterface;
import com.springboot.HRISNEW.repositories.LeaveTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class LeaveTypeServiceImpl implements LeaveTypeServiceInterface{
    
    @Autowired
    private LeaveTypeRepo typeRepo;

    @Override
    public LeaveType leaveTypeById(int id) {
        return typeRepo.leaveTypeById(id);
    }
}
