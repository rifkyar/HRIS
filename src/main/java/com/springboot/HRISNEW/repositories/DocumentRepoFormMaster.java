/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbMForm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Dharta
 */
@Repository
public interface DocumentRepoFormMaster extends CrudRepository<TbMForm, Integer>{
     @Query(value = "SELECT * FROM tb_m_form WHERE id = ?1", nativeQuery = true)
    public TbMForm FormTypeById(Integer id);
}
