/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.FaqQuestion;
import com.springboot.HRISNEW.interfaceservices.QuestionServiceInterface;
import com.springboot.HRISNEW.repositories.QuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class QuestionServiceImpl implements QuestionServiceInterface{
    
    @Autowired
    private QuestionRepo questionRepo;
    
    @Override
    public Iterable<FaqQuestion>getAll(){
        return questionRepo.findAll();
    }
    public FaqQuestion save(FaqQuestion faqquestion){
        return questionRepo.save(faqquestion);
    }
    public  void delete(Integer id){
        questionRepo.deleteById(id);
    }
    public FaqQuestion getById(Integer id){
        return questionRepo.findById(id).get();
    }
    public Iterable<FaqQuestion>tampildata(){
        return questionRepo.tampildata();
    }
}
