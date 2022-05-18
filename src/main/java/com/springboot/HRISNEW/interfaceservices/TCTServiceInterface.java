/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrainingCatalogTrainers;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author User
 */
public interface TCTServiceInterface {

    public List<Object[]> findtrainersbyactive(int isactive);

    TrainingCatalogTrainers save(TrainingCatalogTrainers t);

    public List<Object[]> findTrainerAssignment();

    public List<Object[]> findTrainerForAssignment();
    
    public List<Object[]> findTrainerForCheck(String name);

    public void disableTrainer(Integer nik, Timestamp disableDate, Integer training_id);

    void deleteById(Integer id);

}
