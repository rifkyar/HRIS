/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbTrTraining;
import java.util.List;

/**
 *
 * @author RAR
 */
public interface TrainingServiceInterface {
    Iterable<TbTrTraining>getAll();
    
    TbTrTraining save(TbTrTraining training);
    
    void delete(Integer id);
    
    TbTrTraining getById(Integer id);
    
    Iterable<TbTrTraining>tampilAktif();
    
    TbTrTraining findTrainingById(Integer id);
    
    List<TbTrTraining>getTrainByEmpl(Integer id);
}
