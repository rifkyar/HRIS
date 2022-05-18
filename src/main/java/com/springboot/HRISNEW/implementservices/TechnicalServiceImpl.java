/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbTrTechnical;
import com.springboot.HRISNEW.interfaceservices.TechnicalServiceInterface;
import com.springboot.HRISNEW.repositories.TechnicalRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class TechnicalServiceImpl implements TechnicalServiceInterface{

    @Autowired
    private TechnicalRepo technicalrepo;
    
    @Override
    public Iterable<TbTrTechnical> getAll() {
        return technicalrepo.findAll();
    }

    @Override
    public TbTrTechnical save(TbTrTechnical technical) {
        return technicalrepo.save(technical);
    }

    @Override
    public void delete(Integer id) {
        technicalrepo.deleteById(id);
    }

    @Override
    public TbTrTechnical getById(Integer id) {
        return technicalrepo.findById(id).get();
    }

    @Override
    public Iterable<TbTrTechnical> tampilAktif() {
        return technicalrepo.tampilaktif();
    }

    @Override
    public List<TbTrTechnical> getTechByEmpl(Integer id) {
        return technicalrepo.getTechEmpbyId(id);
    }
    
}
