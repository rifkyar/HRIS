/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbTrTechnical;
import java.util.List;

/**
 *
 * @author RAR
 */
public interface TechnicalServiceInterface {
    Iterable<TbTrTechnical>getAll();
    
    TbTrTechnical save(TbTrTechnical category);
    
    void delete(Integer id);
    
    TbTrTechnical getById(Integer id);
    
    Iterable<TbTrTechnical>tampilAktif();
    
    List<TbTrTechnical>getTechByEmpl(Integer id);
    
}
