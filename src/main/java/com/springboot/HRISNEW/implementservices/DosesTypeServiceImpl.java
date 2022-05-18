/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMDosesType;
import com.springboot.HRISNEW.interfaceservices.DosesTypeServiceInterface;
import com.springboot.HRISNEW.interfaceservices.VaccineTypeServiceInterface;
import com.springboot.HRISNEW.repositories.DosesTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class DosesTypeServiceImpl implements DosesTypeServiceInterface {
   @Autowired
    private DosesTypeRepo DosesTypeRepo;

    @Override
    public Iterable<TbMDosesType> getAll() {
        return DosesTypeRepo.findAll();
    }

    @Override
    public TbMDosesType DosesTypeById(int id) {
        return DosesTypeRepo.DosesTypeById(id);
    }

}
