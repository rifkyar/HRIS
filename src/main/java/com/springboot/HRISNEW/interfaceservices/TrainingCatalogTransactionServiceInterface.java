/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrainingCatalogTransactions;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author User
 */
public interface TrainingCatalogTransactionServiceInterface {

    TrainingCatalogTransactions save(TrainingCatalogTransactions t);

    public List<Object[]> getAllAssignment();

    public void updateAssignment(Integer training_title, Integer trainer_name, Integer nik, Timestamp updateDate, Integer id);
    
    public void disableAssignment(Integer nik, Timestamp updateDate, Integer id);
}
