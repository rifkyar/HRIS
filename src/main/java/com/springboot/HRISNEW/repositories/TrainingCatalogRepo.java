/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrainingCatalogs;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
public interface TrainingCatalogRepo extends CrudRepository<TrainingCatalogs, Integer> {
    
    @Query(value = "SELECT * FROM `training_catalogs` WHERE id = ?1", nativeQuery = true)
    public TrainingCatalogs findDataById(int id);

    @Query(value = "SELECT TCS.id, TCS.training_title, TCG.category, TCS.is_available from training_catalogs TCS JOIN training_categories TCG ON TCG.id = TCS.training_category_id", nativeQuery = true)
    public List<Object[]> findalltraining();

    @Query(value = "SELECT TCS.id, TCS.training_title, TCG.category, TCS.is_available from training_catalogs TCS JOIN training_categories TCG ON TCG.id = TCS.training_category_id WHERE TCS.is_available = 1", nativeQuery = true)
    public List<Object[]> findallactivetraining();

    @Query(value = "SELECT TCS.id, TCS.training_title, TCG.category, TCS.is_available \n"
            + "FROM training_catalogs TCS \n"
            + "JOIN training_categories TCG ON TCG.id = TCS.training_category_id \n"
            + "WHERE TCS.is_available = 1\n"
            + "ORDER BY TCS.training_title", nativeQuery = true)
    public List<Object[]> findallactivetrainingForAssignment();

    @Query(value = "SELECT TCS.id, TCS.training_title, TCG.category, TCS.is_available from training_catalogs TCS JOIN training_categories TCG ON TCG.id = TCS.training_category_id WHERE TCS.is_available = 1 AND TCS.training_title LIKE %?1%", nativeQuery = true)
    public List<Object[]> searchByNameandActiveTraining(String term);
    
    @Query(value = "SELECT TCS.id, TCS.training_title, TCG.category, TCS.is_available from training_catalogs TCS JOIN training_categories TCG ON TCG.id = TCS.training_category_id WHERE TCS.is_available = 1", nativeQuery = true)
    public List<Object[]> seletAllTrainingActived();
    
    @Query(value = "SELECT TCS.id, TCS.training_title, TCG.category, TCS.is_available from training_catalogs TCS JOIN training_categories TCG ON TCG.id = TCS.training_category_id WHERE TCS.is_available = 1 AND TCS.training_title = ?1", nativeQuery = true)
    public List<Object[]> searchByNameandActiveTrainingForCheck(String training_title);

    @Query(value = "SELECT TCS.id from training_catalogs TCS JOIN training_categories TCG ON TCG.id = TCS.training_category_id WHERE TCS.is_available = 1 AND TCS.training_title LIKE %?1%", nativeQuery = true)
    public List<Object[]> searchIDByNameandActiveTraining(String term);

    @Modifying
    @Query(value = "UPDATE training_catalogs SET is_available = 0 WHERE id = ?1", nativeQuery = true)
    @Transactional
    public void disabletraining(Integer id);


//NOTE : Meng update sesuatu perlu Annotation Modifying dan Transactional (Tapi Importnya harus "import org.springframework.transaction.annotation.Transactional;" masalah dari Spring Framework 4.7.1)
//Modifying buat Kasih tau sistem mau update atau modify sesuatu yang ada di DB, Transactional buat ngasih tau klo ada data yang mau masuk / keluar (Dri spring mereka bikin proxy dari @ nya yang mirip dari Interface kita)
}
