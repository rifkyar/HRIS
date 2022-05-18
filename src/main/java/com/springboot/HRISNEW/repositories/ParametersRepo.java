/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbMParameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface ParametersRepo extends CrudRepository<TbMParameter, Integer>{
    
    @Query(value = "SELECT * FROM tb_m_parameter WHERE id = ?1", nativeQuery = true)
    public TbMParameter findApiById(int id);
    
    @Query(value = "SELECT * FROM tb_m_parameter WHERE id = ?1", nativeQuery = true)
    public TbMParameter findOnebyId(String id);
}
