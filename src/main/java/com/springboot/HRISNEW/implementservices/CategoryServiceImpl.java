/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMCategory;
import com.springboot.HRISNEW.interfaceservices.CategoryServiceInterface;
import com.springboot.HRISNEW.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class CategoryServiceImpl implements CategoryServiceInterface{

    
    @Autowired
    private CategoryRepo categoryrepo;
    
    @Override
    public Iterable<TbMCategory> getAll() {
        return categoryrepo.findAll();
    }

    @Override
    public TbMCategory save(TbMCategory category) {
        return categoryrepo.save(category);
    }

    @Override
    public void delete(Integer id) {
        categoryrepo.deleteById(id);
    }

    @Override
    public TbMCategory getById(Integer id) {
        return categoryrepo.findById(id).get();
    }
    
}
