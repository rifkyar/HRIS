/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;
import com.springboot.HRISNEW.entities.TrainingTransactions;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author User
 */


public interface TrainingTransactionFilter extends CrudRepository<TrainingTransactions, Integer>{
    @Query(value = "SELECT TCTS.id, TCTS.trainer_name, TCT.id AS 'training_id'  FROM training_catalog_trainers TCTS JOIN training_catalog_transactions TCT ON TCT.training_catalog_trainer_id = TCTS.id JOIN training_catalogs TCS ON TCS.id = TCT.training_catalog_id WHERE TCS.id = ?1 AND TCT.is_active = 1", nativeQuery = true)
    public List<Object[]> getDataFilterList(Integer id);
    
}
