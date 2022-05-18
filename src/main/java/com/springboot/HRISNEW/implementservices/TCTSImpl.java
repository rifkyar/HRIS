/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrainingCatalogTrainers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springboot.HRISNEW.repositories.TrainingCatalogTrainersRepo;
import java.util.List;
import com.springboot.HRISNEW.interfaceservices.TCTServiceInterface;
import java.sql.Timestamp;

/**
 *
 * @author User
 */
@Service
public class TCTSImpl implements TCTServiceInterface {

    @Autowired
    private TrainingCatalogTrainersRepo trainingCatalogTrainersRepo;

    @Override
    public List<Object[]> findtrainersbyactive(int isactive) {
        return trainingCatalogTrainersRepo.findtrainersbyactive(isactive);
    }

    @Override
    public TrainingCatalogTrainers save(TrainingCatalogTrainers t) {
        return trainingCatalogTrainersRepo.save(t);
    }

    @Override
    public void disableTrainer(Integer nik, Timestamp disableDate, Integer training_id) {
        trainingCatalogTrainersRepo.disableTrainer(nik, disableDate, training_id);
    }

    @Override
    public void deleteById(Integer id) {
        trainingCatalogTrainersRepo.deleteById(id);
    }

    @Override
    public List<Object[]> findTrainerAssignment() {
        return trainingCatalogTrainersRepo.findTrainerAssignment();
    }

    @Override
    public List<Object[]> findTrainerForAssignment() {
        return trainingCatalogTrainersRepo.findTrainerForAssignment();
    }

    @Override
    public List<Object[]> findTrainerForCheck(String name) {
        return trainingCatalogTrainersRepo.findTrainerForCheck(name);
    }

}
