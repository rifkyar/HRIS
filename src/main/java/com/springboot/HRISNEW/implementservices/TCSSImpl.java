/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrainingCatalogs;
import com.springboot.HRISNEW.interfaceservices.TCSServiceInterface;
import com.springboot.HRISNEW.repositories.TrainingCatalogRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class TCSSImpl implements TCSServiceInterface {

    @Autowired
    private TrainingCatalogRepo trainingCatalogRepo;

    @Override
    public List<Object[]> findalltraining() {
        return trainingCatalogRepo.findalltraining();
    }

    @Override
    public TrainingCatalogs save(TrainingCatalogs t) {
        return trainingCatalogRepo.save(t);
    }

    @Override
    public void disabletraining(Integer id) {
        trainingCatalogRepo.disabletraining(id);
    }
//    
//    @Override
//    public List<String> searchByNameandActiveTraining(){
//        return trainingCatalogRepo.searchByNameandActiveTraining();
//    }

    @Override
    public List<Object[]> searchByNameandActiveTraining(String term) {
        return trainingCatalogRepo.searchByNameandActiveTraining(term);
    }

    @Override
    public List<Object[]> searchByNameandActiveTrainingForCheck(String training_title) {
        return trainingCatalogRepo.searchByNameandActiveTrainingForCheck(training_title);
    }

    @Override
    public List<Object[]> findallactivetraining() {
        return trainingCatalogRepo.findallactivetraining();
    }

    @Override
    public List<Object[]> findallactivetrainingForAssignment() {
        return trainingCatalogRepo.findallactivetrainingForAssignment();
    }

    @Override
    public List<Object[]> searchIDByNameandActiveTraining(String term) {
        return trainingCatalogRepo.searchIDByNameandActiveTraining(term);
    }

    @Override
    public List<Object[]> seletAllTrainingActived() {
        return trainingCatalogRepo.seletAllTrainingActived();
    }

    @Override
    public TrainingCatalogs findDataById(int id) {
        return trainingCatalogRepo.findDataById(id);
    }

}
