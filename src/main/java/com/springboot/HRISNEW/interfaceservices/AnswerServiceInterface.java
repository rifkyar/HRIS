/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.FaqAnswer;

/**
 *
 * @author RAR
 */
public interface AnswerServiceInterface {
    
    Iterable<FaqAnswer> getAll();
    
    FaqAnswer save(FaqAnswer faqanswer);
    
    void delete(Integer id);
    
    FaqAnswer getById(Integer id);
    
    Iterable<FaqAnswer> tampilAnswer();
    
    Iterable<FaqAnswer> tampilAktif();
}
