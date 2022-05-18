/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbTrProject;
import com.springboot.HRISNEW.interfaceservices.ProjectServiceInterface;
import com.springboot.HRISNEW.repositories.ProjectRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class ProjectServiceImpl implements ProjectServiceInterface {
    
    @Autowired
    private ProjectRepo projectrepo;

    @Override
    public Iterable<TbTrProject> getAll() {
        return projectrepo.findAll();
    }

    @Override
    public TbTrProject save(TbTrProject project) {
        return projectrepo.save(project);
    }

    @Override
    public void delete(Integer id) {
        projectrepo.deleteById(id);
    }

    @Override
    public TbTrProject getById(Integer id) {
        return projectrepo.findById(id).get();
    }

    @Override
    public Iterable<TbTrProject> tampilAktif() {
        return projectrepo.tampilaktif();
    }

    @Override
    public List<TbTrProject> getProByEmpl(Integer id) {
        return projectrepo.getProEmpbyId(id);
    }


    
    
}
