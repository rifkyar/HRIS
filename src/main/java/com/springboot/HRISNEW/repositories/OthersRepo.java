/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.MOthers;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface OthersRepo extends CrudRepository<MOthers, Integer> {

    @Query(value = "SELECT * FROM `tb_m_status` WHERE id = ?1", nativeQuery = true)
    public MOthers findApiById(int id);
    
    @Query(value = "SELECT * FROM tb_m_status WHERE id=?1", nativeQuery = true)
    public MOthers findOnebyId(String id);

    
}
