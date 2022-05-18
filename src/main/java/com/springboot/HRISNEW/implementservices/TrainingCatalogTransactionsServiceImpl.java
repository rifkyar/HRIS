/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrainingCatalogTransactions;
import com.springboot.HRISNEW.interfaceservices.TrainingCatalogTransactionServiceInterface;
import com.springboot.HRISNEW.repositories.TrainingCatalogTransactionsRepo;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class TrainingCatalogTransactionsServiceImpl implements TrainingCatalogTransactionServiceInterface{
    
    @Autowired
    TrainingCatalogTransactionsRepo trainingCatalogTransactionsRepo;
    
    @Override
    public TrainingCatalogTransactions save(TrainingCatalogTransactions t) {
        return trainingCatalogTransactionsRepo.save(t);
    }

    @Override
    public List<Object[]> getAllAssignment() {
        return trainingCatalogTransactionsRepo.getAllAssignment();
    }

    @Override
    public void updateAssignment(Integer training_title, Integer trainer_name, Integer nik, Timestamp updateDate, Integer id) {
        trainingCatalogTransactionsRepo.updateAssignment(training_title, trainer_name, nik, updateDate, id);
    }

    @Override
    public void disableAssignment(Integer nik, Timestamp updateDate, Integer id) {
        trainingCatalogTransactionsRepo.disableAssignment(nik, updateDate, id);
    }
    
}
