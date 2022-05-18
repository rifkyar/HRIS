/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbTrWorkassignment;
import com.springboot.HRISNEW.interfaceservices.WorkAssignmentServiceInterface;
import com.springboot.HRISNEW.repositories.WorkAssignmentRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class WorkAssignmentServiceImpl implements WorkAssignmentServiceInterface{
    
    @Autowired
    private WorkAssignmentRepo workassignmentrepo;
    
    @Override
    public Iterable<TbTrWorkassignment>getAll(){
        return workassignmentrepo.findAll();
    }

    @Override
    public TbTrWorkassignment save(TbTrWorkassignment workassignment) {
        return workassignmentrepo.save(workassignment);
    }

    @Override
    public void delete(Integer id) {
        workassignmentrepo.deleteById(id);
    }

    @Override
    public TbTrWorkassignment getById(Integer id) {
        return workassignmentrepo.findById(id).get();
    }
    
    public Iterable<TbTrWorkassignment>tampilAktif(){
        return workassignmentrepo.tampilaktif();
    }

    @Override
    public List<TbTrWorkassignment> getWaByEmpl(Integer id) {
        return workassignmentrepo.getWaEmpbyId(id);
    }

}
