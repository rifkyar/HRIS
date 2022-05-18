/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrainingTransactions;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
public interface TrainingSchedulingRepo extends CrudRepository<TrainingTransactions, Integer> {
    
    @Query(value = "SELECT * FROM `training_transactions` WHERE id = ?1", nativeQuery = true)
    public TrainingTransactions editTrainingTransactionById(int id);

    @Query(value = "SELECT TTS.Id,TCS.training_title,TTS.training_time,TTS.is_active,TTS.created_date,TTS.updated_date,TTS.created_by,TTS.updated_by "
            + "FROM training_transactions TTS JOIN training_catalog_transactions TCTS ON TCTS.id = TTS.training_catalog_transaction_id JOIN training_catalogs "
            + "TCS ON TCS.id = TCTS.training_catalog_id WHERE TTS.is_active = 1 and MONTH(TTS.training_time) = MONTH(CURDATE()) ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getDataTraining();
    
    @Query(value = "SELECT TTS.Id,TCS.training_title,TTS.training_time,TTS.is_active,TTS.created_date,TTS.updated_date,TTS.created_by,TTS.updated_by, "
            + "TTS.typeTraining FROM training_transactions TTS JOIN training_catalog_transactions TCTS ON TCTS.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TCS ON TCS.id = TCTS.training_catalog_id WHERE TTS.is_active = 1 ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getDataTrainingWithType();
    
    @Query(value = "SELECT TTS.Id,TCS.training_title,TTS.training_time,TTS.is_active,TTS.created_date,TTS.updated_date,TTS.created_by,TTS.updated_by,"
            + "TTS.typeTraining FROM training_transactions TTS JOIN training_catalog_transactions TCTS ON TCTS.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TCS ON TCS.id = TCTS.training_catalog_id WHERE TTS.is_active = 1 AND TTS.typeTraining = 1 AND TTS.training_time BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') AND LAST_DAY(DATE_ADD(NOW(), INTERVAL 3 MONTH)) ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getDataTrainingOnlineClass();
    
    @Query(value = "SELECT TTS.Id,TCS.training_title,TTS.training_time,TTS.is_active,TTS.created_date,TTS.updated_date,TTS.created_by,TTS.updated_by,"
            + "TTS.typeTraining FROM training_transactions TTS JOIN training_catalog_transactions TCTS ON TCTS.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TCS ON TCS.id = TCTS.training_catalog_id WHERE TTS.is_active = 1 AND TTS.typeTraining = 0 AND TTS.training_time BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') AND LAST_DAY(DATE_ADD(NOW(), INTERVAL 3 MONTH)) ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getDataTrainingOfflineClass();

    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_quota, TTS.training_location, TTS.training_time, TTS.training_needs "
            + "FROM training_transactions TTS JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers TCTS "
            + "ON TCTS.id = TCT.training_catalog_trainer_id WHERE TTS.is_active = 1 and MONTH(TTS.training_time) = MONTH(CURDATE())", nativeQuery = true)
    public List<Object[]> getDetailSchedule();
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_quota, TTS.training_location, TTS.training_time, TTS.training_needs FROM training_transactions "
            + "TTS JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers "
            + "TCTS ON TCTS.id = TCT.training_catalog_trainer_id WHERE TTS.is_active = 1 AND TTS.typeTraining = 0 AND TTS.training_time BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') AND LAST_DAY(DATE_ADD(NOW(), INTERVAL 3 MONTH))", nativeQuery = true)
    public List<Object[]> getDetailDataTrainingOffline();
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_quota, TTS.training_time, TTS.training_link FROM training_transactions TTS JOIN training_catalog_transactions TCT "
            + "ON TCT.id = TTS.training_catalog_transaction_id JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers TCTS ON TCTS.id = TCT.training_catalog_trainer_id "
            + "WHERE TTS.is_active = 1 AND TTS.typeTraining = 1 AND TTS.training_time BETWEEN DATE_FORMAT(NOW() ,'%Y-%m-01') AND LAST_DAY(DATE_ADD(NOW(), INTERVAL 3 MONTH))", nativeQuery = true)
    public List<Object[]> getDetailDataTrainingOnline();
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_quota, TTS.training_location, TTS.training_time, TTS.training_needs FROM training_transactions TTS JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers TCTS ON TCTS.id = TCT.training_catalog_trainer_id WHERE TTS.is_active = 1 AND TC.training_title = ?1", nativeQuery = true)
    public List<Object[]> getScheduleForCheck(String training_Title);
   
    @Modifying
    @Query(value = "UPDATE training_transactions SET is_active = 0, updated_by = ?2, updated_date = ?3, remark = ?4 WHERE id = ?1", nativeQuery = true)
    @Transactional
    public void canceltraining(Integer id, Integer nik, Timestamp cancelDate, String remark);
    
    
    //penambahan
    @Query(value = "SELECT TTS.Id,TCS.training_title,TTS.training_time,TTS.is_active,TTS.created_date,TTS.updated_date,TTS.created_by,TTS.updated_by,"
            + "TTS.typeTraining FROM training_transactions TTS JOIN training_catalog_transactions TCTS ON TCTS.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TCS ON TCS.id = TCTS.training_catalog_id WHERE TTS.is_active = 1 AND TTS.typeTraining = 1 ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getDataTrainingOnlineClassAdmin();
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_quota, TTS.training_time, TTS.training_link FROM training_transactions TTS JOIN training_catalog_transactions TCT "
            + "ON TCT.id = TTS.training_catalog_transaction_id JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers TCTS ON TCTS.id = TCT.training_catalog_trainer_id "
            + "WHERE TTS.is_active = 1 AND TTS.typeTraining = 1 ", nativeQuery = true)
    public List<Object[]> getDetailDataTrainingOnlineAdmin();
    
    @Query(value = "SELECT TTS.Id,TCS.training_title,TTS.training_time,TTS.is_active,TTS.created_date,TTS.updated_date,TTS.created_by,TTS.updated_by,"
            + "TTS.typeTraining FROM training_transactions TTS JOIN training_catalog_transactions TCTS ON TCTS.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TCS ON TCS.id = TCTS.training_catalog_id WHERE TTS.is_active = 1 AND TTS.typeTraining = 0 ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getDataTrainingOfflineClassAdmin();
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_quota, TTS.training_location, TTS.training_time, TTS.training_needs FROM training_transactions "
            + "TTS JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers "
            + "TCTS ON TCTS.id = TCT.training_catalog_trainer_id WHERE TTS.is_active = 1 AND TTS.typeTraining = 0", nativeQuery = true)
    public List<Object[]> getDetailDataTrainingOfflineAdmin();
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_quota, TTS.training_location, TTS.training_time, TTS.training_needs "
            + "FROM training_transactions TTS JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id JOIN training_catalog_trainers TCTS "
            + "ON TCTS.id = TCT.training_catalog_trainer_id WHERE TTS.is_active = 1", nativeQuery = true)
    public List<Object[]> getDetailScheduleAdmin();
    
    @Query(value = "SELECT TTS.Id,TCS.training_title,TTS.training_time,TTS.is_active,TTS.created_date,TTS.updated_date,TTS.created_by,TTS.updated_by "
            + "FROM training_transactions TTS JOIN training_catalog_transactions TCTS ON TCTS.id = TTS.training_catalog_transaction_id JOIN training_catalogs "
            + "TCS ON TCS.id = TCTS.training_catalog_id WHERE TTS.is_active = 1 ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getDataTrainingAdmin();
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_time,TCTG.category FROM training_transactions TTS "
            + "JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id "
            + "JOIN training_catalog_trainers TCTS ON TCTS.id = TCT.training_catalog_trainer_id "
            + "JOIN training_categories TCTG ON TCTG.id = TC.training_category_id "
            + "WHERE TTS.is_active = 1 AND TTS.typeTraining = 1 ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getListDataTrainingOnlineAdmin();
    
    @Query(value = "SELECT TTS.id, TC.training_title, TCTS.trainer_name, TTS.training_time,TCTG.category FROM training_transactions TTS "
            + "JOIN training_catalog_transactions TCT ON TCT.id = TTS.training_catalog_transaction_id "
            + "JOIN training_catalogs TC ON TC.id = TCT.training_catalog_id "
            + "JOIN training_catalog_trainers TCTS ON TCTS.id = TCT.training_catalog_trainer_id "
            + "JOIN training_categories TCTG ON TCTG.id = TC.training_category_id "
            + "WHERE TTS.is_active = 1 AND TTS.typeTraining = 0 ORDER BY TTS.training_time", nativeQuery = true)
    public List<Object[]> getListDataTrainingOfflineAdmin();
    
}
