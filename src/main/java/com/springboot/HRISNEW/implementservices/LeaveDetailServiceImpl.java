/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.LeaveDetail;
import com.springboot.HRISNEW.interfaceservices.LeaveDetailServiceInterface;
import com.springboot.HRISNEW.repositories.LeaveDetailRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class LeaveDetailServiceImpl implements LeaveDetailServiceInterface{
    
    @Autowired
    LeaveDetailRepo leaveDetailRepo;

    @Override
    public List<LeaveDetail> findLeaveType(int gender, int marriage) {
        return leaveDetailRepo.findLeaveDetailbyGenderMarriage(gender, marriage);
    }
    
}
