/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.LeaveType;
import com.springboot.HRISNEW.entities.TbMVaccineType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAR
 */
@Repository
public interface VaccineTypeRepo extends CrudRepository<TbMVaccineType, Integer> {
    
    @Query(value = "SELECT * FROM `tb_m_vaccine_type` WHERE id = ?1", nativeQuery = true)
    public TbMVaccineType VaccineTypeById(Integer id);
}
