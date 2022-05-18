/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.MOfficehour;
import com.springboot.HRISNEW.interfaceservices.OfficeHourServiceInterface;
import com.springboot.HRISNEW.repositories.OfficeHourRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class OfficeHourServiceImpl implements OfficeHourServiceInterface {

    @Autowired
    private OfficeHourRepo officeHourRepo;

    @Override
    public Iterable<MOfficehour> getAll() {
        return officeHourRepo.findAll();
    }

    @Override
    public MOfficehour findById(Integer id) {
        return officeHourRepo.findById(id).get();
    }

}
