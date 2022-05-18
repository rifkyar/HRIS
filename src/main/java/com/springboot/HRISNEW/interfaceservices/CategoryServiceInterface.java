/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMCategory;

/**
 *
 * @author RAR
 */
public interface CategoryServiceInterface {
    Iterable<TbMCategory>getAll();
    
    TbMCategory save(TbMCategory category);
    
    void delete(Integer id);
    
    TbMCategory getById(Integer id);
}
