/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.FaqAnswer;
import com.springboot.HRISNEW.interfaceservices.AnswerServiceInterface;
import com.springboot.HRISNEW.repositories.AnswerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class AnswerServiceImpl implements AnswerServiceInterface {

    @Autowired
    private AnswerRepo answerRepo;

    @Override
    public Iterable<FaqAnswer> getAll() {
        return answerRepo.findAll();
    }

    public FaqAnswer save(FaqAnswer faqanswer) {
        return answerRepo.save(faqanswer);
    }

    public void delete(Integer id) {
        answerRepo.deleteById(id);
    }

    public FaqAnswer getById(Integer id) {
        return answerRepo.findById(id).get();
    }

    public Iterable<FaqAnswer> tampilAnswer() {
        return answerRepo.getanswer();
    }

    public Iterable<FaqAnswer> tampilAktif() {
        return answerRepo.tampilaktif();
    }
}
