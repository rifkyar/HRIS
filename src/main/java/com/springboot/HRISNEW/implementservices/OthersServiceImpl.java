/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.MOthers;
import com.springboot.HRISNEW.interfaceservices.OthersServiceInterface;
import com.springboot.HRISNEW.repositories.OthersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class OthersServiceImpl implements OthersServiceInterface{

    @Autowired
    private OthersRepo othersRepo;
    
    @Override
    public MOthers findApiById(int id) {
        return othersRepo.findApiById(id);
    }

    @Override
    public MOthers selectOther(String param) {
        return othersRepo.findOnebyId(param);
    }
    
}
