/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbTrWorkassignment;
import java.util.List;

/**
 *
 * @author RAR
 */
public interface WorkAssignmentServiceInterface {
    
    Iterable<TbTrWorkassignment>getAll();
    
    TbTrWorkassignment save(TbTrWorkassignment workassignment);
    
    void delete(Integer id);
    
    TbTrWorkassignment getById(Integer id);
    
    Iterable<TbTrWorkassignment>tampilAktif();
    
    List<TbTrWorkassignment>getWaByEmpl(Integer id);
    
}
