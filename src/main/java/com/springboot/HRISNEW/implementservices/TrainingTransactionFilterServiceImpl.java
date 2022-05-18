/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.interfaceservices.TrainingTransactionFilterServiceInterface;
import com.springboot.HRISNEW.repositories.TrainingTransactionFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class TrainingTransactionFilterServiceImpl implements TrainingTransactionFilterServiceInterface{
    
    @Autowired
    TrainingTransactionFilter trainingtransactionfilter;
    
    @Override
    public List<Object[]> getDataFilterList(Integer id) {
       return trainingtransactionfilter.getDataFilterList(id);
    }
    
}
