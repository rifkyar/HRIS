/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrProject;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAR
 */
@Repository
public interface ProjectRepo extends CrudRepository<TbTrProject, Integer>{
    @Query(value = "SELECT * FROM `tb_tr_project` WHERE isdeleted = 0", nativeQuery = true)
    public Iterable<TbTrProject> tampilaktif();
    
    @Query(value = "select * from tb_tr_project where isdeleted = 0 AND empl_nik = ?1", nativeQuery = true)
    public List<TbTrProject> getProEmpbyId(Integer id);
}
