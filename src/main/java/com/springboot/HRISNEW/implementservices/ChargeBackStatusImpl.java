/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.MChargebackstatus;
import com.springboot.HRISNEW.interfaceservices.ChargeBackStatusInterface;
import com.springboot.HRISNEW.repositories.ChargeBackStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class ChargeBackStatusImpl implements ChargeBackStatusInterface {

    @Autowired
    ChargeBackStatusRepo chargeBackStatusRepo;

    @Override
    public Iterable<MChargebackstatus> getAll() {
        return chargeBackStatusRepo.findAll();
    }

    @Override
    public MChargebackstatus findById(Integer Id) {
        return chargeBackStatusRepo.findById(Id).get();
    }
}
