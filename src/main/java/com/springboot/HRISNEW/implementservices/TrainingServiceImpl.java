/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbTrTraining;
import com.springboot.HRISNEW.interfaceservices.TrainingServiceInterface;
import com.springboot.HRISNEW.repositories.TrainingRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class TrainingServiceImpl implements TrainingServiceInterface{
    
    @Autowired
    private TrainingRepo trainingrepo;

    @Override
    public Iterable<TbTrTraining> getAll() {
        return trainingrepo.findAll();
    }

    @Override
    public TbTrTraining save(TbTrTraining training) {
        return trainingrepo.save(training);
    }

    @Override
    public void delete(Integer id) {
        trainingrepo.deleteById(id);
    }

    @Override
    public TbTrTraining getById(Integer id) {
        return trainingrepo.findById(id).get();
    }

    @Override
    public Iterable<TbTrTraining> tampilAktif() {
        return trainingrepo.tampilaktif();
    }

    @Override
    public TbTrTraining findTrainingById(Integer id) {
        return trainingrepo.findTrainingByID(id);
    }

    @Override
    public List<TbTrTraining> getTrainByEmpl(Integer id) {
        return trainingrepo.getTrainEmpbyId(id);
    }
}
