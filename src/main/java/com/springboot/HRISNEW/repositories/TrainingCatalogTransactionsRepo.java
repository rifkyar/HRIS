/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrainingCatalogTransactions;
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
public interface TrainingCatalogTransactionsRepo extends CrudRepository<TrainingCatalogTransactions, Integer> {

    @Query(value = "SELECT TCTS.id,\n"
            + "TCTS.training_catalog_trainer_id, \n"
            + "TC.training_title, TCT.trainer_name, \n"
            + "TCS.category FROM training_catalog_transactions AS TCTS \n"
            + "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id \n"
            + "JOIN training_catalog_trainers AS TCT ON TCT.id = TCTS.training_catalog_trainer_id \n"
            + "JOIN training_categories AS TCS ON TCS.id = TC.training_category_id \n"
            + "WHERE TCT.is_active = 1 AND TCTS.is_active = 1 AND TC.is_available = 1", nativeQuery = true)
    public List<Object[]> getAllAssignment();

    @Modifying
    @Query(value = "UPDATE training_catalog_transactions\n"
            + "SET  training_catalog_id = ?1,\n"
            + "training_catalog_trainer_id = ?2,\n"
            + "updated_by = ?3,\n"
            + "updated_date = ?4\n"
            + "WHERE id = ?5", nativeQuery = true)
    @Transactional
    public void updateAssignment(Integer training_title, Integer trainer_name, Integer nik, Timestamp updateDate, Integer id);
    
    @Modifying
    @Query(value = "UPDATE training_catalog_transactions SET is_active = 0, updated_by = ?1, updated_date = ?2\n"
            + "WHERE id = ?3", nativeQuery = true)
    @Transactional
    public void disableAssignment(Integer nik, Timestamp updateDate, Integer id);

}
