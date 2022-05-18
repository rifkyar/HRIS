/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.MPaidtostaff;
import com.springboot.HRISNEW.interfaceservices.PaidToStaffServiceInterface;
import com.springboot.HRISNEW.repositories.PaidtoStarffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class PaidToStaffServiceImpl implements PaidToStaffServiceInterface {

    @Autowired
    private PaidtoStarffRepo paidtoStarffRepo;

    @Override
    public Iterable<MPaidtostaff> getAll() {
       return paidtoStarffRepo.findAll();
    }

    public MPaidtostaff findById(Integer id) {
        return paidtoStarffRepo.findById(id).get();
    }

}
