/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMSkill;
import java.util.List;

/**
 *
 * @author RAR
 */
public interface SkillServiceInterface {
    Iterable<TbMSkill>getAll();
    
    TbMSkill save(TbMSkill category);
    
    void delete(Integer id);
    
    TbMSkill getById(Integer id);
    
    List<TbMSkill> findByCategory(Integer id);
    
}
