/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbMDosesType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAR
 */
@Repository
public interface DosesTypeRepo extends CrudRepository<TbMDosesType, Integer> {
    @Query(value = "SELECT * FROM `tb_m_doses_type` WHERE id = ?1", nativeQuery = true)
    public TbMDosesType DosesTypeById(Integer id);
}
