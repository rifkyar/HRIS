/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbTrProject;
import java.util.List;

/**
 *
 * @author RAR
 */
public interface ProjectServiceInterface {
    
    Iterable<TbTrProject>getAll();
    
    TbTrProject save(TbTrProject project);
    
    void delete(Integer id);
    
    TbTrProject getById(Integer id);
    
    Iterable<TbTrProject>tampilAktif();
    
    List<TbTrProject>getProByEmpl(Integer id);
    
}
