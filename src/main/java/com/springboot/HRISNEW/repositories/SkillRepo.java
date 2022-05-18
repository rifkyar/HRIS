/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbMSkill;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RAR
 */
@Repository
public interface SkillRepo extends CrudRepository<TbMSkill, Integer>{
    
    @Query("select new TbMSkill(id, skill) from TbMSkill where category.id = :id")
    public List<TbMSkill> findByCategory(@Param("id") Integer id);
}
