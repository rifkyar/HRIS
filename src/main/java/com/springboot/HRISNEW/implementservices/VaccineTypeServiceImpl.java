/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMVaccineType;
import com.springboot.HRISNEW.interfaceservices.VaccineTypeServiceInterface;
import com.springboot.HRISNEW.repositories.LeaveTypeRepo;
import com.springboot.HRISNEW.repositories.VaccineTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class VaccineTypeServiceImpl implements VaccineTypeServiceInterface {

    @Autowired
    private VaccineTypeRepo vaccineTypeRepo;
    
    @Override
    public TbMVaccineType VaccineTypeById(int id) {
        return vaccineTypeRepo.VaccineTypeById(id);
    }

    @Override
    public Iterable<TbMVaccineType> getAll() {
        return vaccineTypeRepo.findAll();
    }

    
}
