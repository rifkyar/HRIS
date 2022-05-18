/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.FaqQuestion;

/**
 *
 * @author RAR
 */
public interface QuestionServiceInterface {
    
    Iterable<FaqQuestion>getAll();
    
    FaqQuestion save(FaqQuestion faqquestion);
    
    void delete(Integer id);
    
    FaqQuestion getById(Integer id);
    
    Iterable<FaqQuestion>tampildata();
}
