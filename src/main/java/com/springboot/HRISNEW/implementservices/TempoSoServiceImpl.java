/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TempSo;
import com.springboot.HRISNEW.interfaceservices.TempoSoServiceInterface;
import com.springboot.HRISNEW.repositories.TempoSoRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class TempoSoServiceImpl implements TempoSoServiceInterface{
    
    @Autowired
    private TempoSoRepo tempoSoRepo;

    @Override
    public List<TempSo> findAllSoPiloting() {
        return tempoSoRepo.findAllSoPiloting();
    }
    
}
