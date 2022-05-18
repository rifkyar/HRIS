/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbMMejakerja;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author adhin
 */

@Repository
public interface TrainingMejaKerjaRepo extends CrudRepository<TbMMejakerja, Integer>{
    
    @Query(value = "SELECT empl_nik, name, email, training_title, training_time FROM tb_m_mejakerja order by training_time", nativeQuery = true)
    public List<Object[]> getDataTrainingMejaKerja();
    
    @Query(value = "SELECT * FROM tb_m_mejakerja where email = ?1 and training_title = ?2", nativeQuery = true)
    public TbMMejakerja findByNikAndTrainingTitle(String email, String training_title);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_m_mejakerja set empl_nik = ?1, email = ?2, name = ?3, training_title = ?4, training_time = ?5, upload_date = ?6 where email = ?2 and training_title = ?4", nativeQuery = true)
    void updateTbMMejakerja(Integer nik, String email, String name, String training_title, Date training_time, Date upload_date);
    
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tb_m_mejakerja (empl_nik, email, name, training_title, training_time, upload_date) \n" 
                   + "VALUES (?1, ?2, ?3, ?4, ?5, ?6)", nativeQuery = true)
    void saveTbMMejakerja(Integer nik, String email, String name, String training_title, Date training_time, Date upload_date);
    
}
