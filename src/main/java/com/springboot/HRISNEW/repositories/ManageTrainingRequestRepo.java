/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrainingUserRequests;
import java.sql.Timestamp;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author User
 */
public interface ManageTrainingRequestRepo extends CrudRepository<TrainingUserRequests, Integer> {

    @Query(value = "SELECT TUR.id,\n"
            + "TUR.empl_nik,\n"
            + "TUR.training_title,\n"
            + "TCS.category,\n"
            + "tms.value,\n"
            + "TUR.is_approved\n"
            + "FROM training_user_requests TUR\n"
            + "JOIN training_catalogs TC ON TC.id = TUR.training_catalog_id\n"
            + "JOIN tb_m_status tms ON tms.id = TUR.m_other_id\n"
            + "JOIN training_categories TCS ON TCS.id = TC.training_category_id", nativeQuery = true)
    public List<Object[]> findallrequest();

    @Modifying
    @Query(value = "UPDATE training_user_requests\n"
            + "SET m_other_id = 8,\n"
            + "is_approved = 1,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE id = ?1", nativeQuery = true)
    @Transactional
    public void accceptrequest(Integer id, Integer nik, Timestamp timestamp);

    @Modifying
    @Query(value = "UPDATE training_user_requests\n"
            + "SET m_other_id = 9,\n"
            + "is_approved = 0,\n"
            + "remark = ?4,\n"
            + "updated_by = ?2,\n"
            + "updated_date = ?3\n"
            + "WHERE	id = ?1", nativeQuery = true)
    @Transactional
    public void rejectRequest(Integer id, Integer nik, Timestamp timestamp, String remark);
}
