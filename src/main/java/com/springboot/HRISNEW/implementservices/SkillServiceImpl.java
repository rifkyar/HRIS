/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMSkill;
import com.springboot.HRISNEW.interfaceservices.SkillServiceInterface;
import com.springboot.HRISNEW.repositories.SkillRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class SkillServiceImpl implements SkillServiceInterface{
    
    @Autowired
    private SkillRepo skillrepo;

    @Override
    public Iterable<TbMSkill> getAll() {
        return skillrepo.findAll();
    }

    @Override
    public TbMSkill save(TbMSkill skill) {
        return skillrepo.save(skill);
    }

    @Override
    public void delete(Integer id) {
        skillrepo.deleteById(id);
    }

    @Override
    public TbMSkill getById(Integer id) {
        return skillrepo.findById(id).get();
    }

    @Override
    public List<TbMSkill> findByCategory(Integer id) {
        return skillrepo.findByCategory(id);
    }

}
