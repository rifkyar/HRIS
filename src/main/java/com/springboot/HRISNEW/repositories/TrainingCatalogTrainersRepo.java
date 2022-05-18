/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import com.springboot.HRISNEW.entities.TrainingCatalogTrainers;
import java.sql.Timestamp;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
@Repository
public interface TrainingCatalogTrainersRepo extends CrudRepository<TrainingCatalogTrainers, Integer> {

//    private Connection connection;  
    @Query(value = "SELECT id, trainer_name, trainer_status, trainer_email, trainer_phone, created_date from training_catalog_trainers where is_active = ?1 ORDER BY created_date DESC", nativeQuery = true)
    public List<Object[]> findtrainersbyactive(Integer isactive);

    @Query(value = "SELECT TCT.id, TCT.trainer_name, TCT.trainer_status, TCT.trainer_email \n"
            + "FROM training_catalog_trainers AS TCT\n"
            + "JOIN training_catalog_transactions AS TCTS ON TCTS.training_catalog_trainer_id = TCT.id\n"
            + "JOIN training_catalogs AS TC ON TC.id = TCTS.training_catalog_id\n"
            + "JOIN training_categories AS TCS ON TCS.id = TC.training_category_id\n"
            + "WHERE TCT.is_active = 1\n"
            + "AND TCTS.is_active = 1\n"
            + "AND TC.is_available = 1\n"
            + "GROUP BY TCT.id", nativeQuery = true)
    public List<Object[]> findTrainerAssignment();

    @Query(value = "SELECT TCT.id, TCT.trainer_name, TCT.trainer_status, TCT.trainer_email \n"
            + "FROM training_catalog_trainers AS TCT\n"
            + "WHERE TCT.is_active = 1\n"
            + "ORDER BY TCT.trainer_name", nativeQuery = true)
    public List<Object[]> findTrainerForAssignment();
    
    @Query(value = "SELECT TCT.trainer_name\n"
            + "FROM training_catalog_trainers AS TCT\n"
            + "WHERE TCT.is_active = 1 AND TCT.trainer_name = ?1\n"
            + "ORDER BY TCT.trainer_name", nativeQuery = true)
    public List<Object[]> findTrainerForCheck(String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE training_catalog_trainers SET  is_active = 0,\n"
            + "updated_by = ?1,\n"
            + "updated_date = ?2\n"
            + "WHERE id = ?3", nativeQuery = true)
    public void disableTrainer(Integer nik, Timestamp disableDate, Integer training_id);

}
