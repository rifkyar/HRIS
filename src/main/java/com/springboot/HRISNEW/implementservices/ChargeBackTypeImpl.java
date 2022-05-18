/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.MChargebacktype;
import com.springboot.HRISNEW.interfaceservices.ChargebackTypeInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.HRISNEW.repositories.ChargeBackTypeRepo;

/**
 *
 * @author USER
 */
@Service
public class ChargeBackTypeImpl implements ChargebackTypeInterface {

    @Autowired
    ChargeBackTypeRepo chargeBackTyoeRepo;

    @Override
    public Iterable<MChargebacktype> getAll() {
        return chargeBackTyoeRepo.findAll();
    }

    @Override
    public MChargebacktype findById(Integer id) {
        return chargeBackTyoeRepo.findById(id).get();
    }
}
