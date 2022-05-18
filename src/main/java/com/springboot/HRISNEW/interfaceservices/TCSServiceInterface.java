/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrainingCatalogs;
import java.util.List;

/**
 *
 * @author User
 */
public interface TCSServiceInterface {

    public List<Object[]> findalltraining();

    TrainingCatalogs save(TrainingCatalogs t);

    public List<Object[]> findallactivetraining();

    public List<Object[]> findallactivetrainingForAssignment();

//    public List<String> searchByNameandActiveTraining();
    public List<Object[]> searchByNameandActiveTraining(String term);

    public List<Object[]> searchByNameandActiveTrainingForCheck(String training_title);

    public List<Object[]> searchIDByNameandActiveTraining(String term);

    public void disabletraining(Integer id);
    
    List<Object[]> seletAllTrainingActived();
    
    public TrainingCatalogs findDataById(int id);

}
