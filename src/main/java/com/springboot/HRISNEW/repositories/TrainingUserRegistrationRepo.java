/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrainingUserRegistration;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
public interface TrainingUserRegistrationRepo extends CrudRepository<TrainingUserRegistration, Integer>{
    
    //upload
    @Modifying
    @Transactional
    @Query(value = "UPDATE training_user_registration SET upload_trainer=?, updated_date=?, updated_by=? WHERE training_transaction_id =?", nativeQuery = true)
    void uploadFile(byte[] fileupload, Date updatedDate, Integer nik, String req);
    
    //disable flag
    @Modifying
    @Transactional
    @Query(value = "UPDATE training_user_registration SET flag = 0, updated_date=?, updated_by=? WHERE training_transaction_id =? AND empl_nik = ?", nativeQuery = true)
    void disableFlag(Date updatedDate, Integer nik, Integer transaction_id, Integer nik2);
    
    @Query(value = "SELECT flag FROM `training_user_registration` WHERE training_transaction_id = ? AND empl_nik = ? LIMIT 1", nativeQuery = true)
    public boolean checkFlag(String req, Integer nik);
    
    @Query(value = "SELECT upload_trainer FROM `training_user_registration` WHERE training_transaction_id = ? LIMIT 1", nativeQuery = true)
    public String checkUpload(String req);
    
    //Downloads
    @Query(value = "SELECT upload_trainer FROM training_user_registration WHERE training_transaction_id = ?1 ", nativeQuery = true)
    public byte[] findBlobFile(String id);
    
    
    @Query(value = "SELECT tur.id, tur.empl_nik, tur.customer_id as customer_name, s.value as statusTraining, tc.training_title, tcr.trainer_name, tt.id as transaction, tt.typeTraining FROM training_user_registration tur JOIN tb_m_status s ON tur.m_other_id = s.id JOIN training_transactions tt ON tur.training_transaction_id = tt.id JOIN training_catalog_transactions tct ON tt.training_catalog_transaction_id = tct.id JOIN training_catalogs tc ON tct.training_catalog_id = tc.id JOIN training_catalog_trainers tcr ON tct.training_catalog_trainer_id = tcr.id WHERE tur.id = ?1", nativeQuery = true)
    public List<Object[]> getUserRegisteredTraining(int id);
    
    @Query(value = "SELECT * FROM `training_user_registration` WHERE id = ?1", nativeQuery = true)
    public TrainingUserRegistration findById(int id);
}
