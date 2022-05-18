/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrainingTransactions;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author User
 */
public interface TrainingSchedulingServiceInterface {
    
    public List<Object[]> getDataTraining();
    
    public List<Object[]> getDetailSchedule();
    
    public List<Object[]> getScheduleForCheck(String training_Title);
    
    TrainingTransactions save(TrainingTransactions t);
    
    public void canceltraining(Integer id, Integer nik, Timestamp cancelDate, String remark);
    
    public List<Object[]> getDataTrainingWithType();
    
    public List<Object[]> getDataTrainingOnlineClass();
    
    public List<Object[]> getDataTrainingOfflineClass();
    
    public List<Object[]> getDetailDataTrainingOffline();
    
    public List<Object[]> getDetailDataTrainingOnline();
    
    public TrainingTransactions editTrainingTransactionById(int id);
    
    //penambahan untuk admin
    public List<Object[]> getDetailDataTrainingOnlineAdmin();
    
    public List<Object[]> getDataTrainingOnlineClassAdmin();
    
    public List<Object[]> getDataTrainingOfflineClassAdmin();
    
    public List<Object[]> getDetailDataTrainingOfflineAdmin();
    
    public List<Object[]> getDetailScheduleAdmin();
    
    public List<Object[]> getDataTrainingAdmin();
    
    public List<Object[]> getListDataTrainingOnlineAdmin();
    
    public List<Object[]> getListDataTrainingOfflineAdmin();
    
    
}
