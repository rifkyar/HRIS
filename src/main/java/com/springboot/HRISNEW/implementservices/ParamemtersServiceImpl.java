/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMParameter;
import com.springboot.HRISNEW.interfaceservices.ParametersServiceInterface;
import com.springboot.HRISNEW.repositories.ParametersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class ParamemtersServiceImpl implements ParametersServiceInterface{

    @Autowired
    private ParametersRepo parametersRepo;
    
    @Override
    public TbMParameter findApiById(int id) {
        return parametersRepo.findApiById(id);
    }

    @Override
    public TbMParameter selectOther(String param) {
        return parametersRepo.findOnebyId(param);
    }
    
}
