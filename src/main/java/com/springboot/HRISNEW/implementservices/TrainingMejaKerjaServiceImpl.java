/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMMejakerja;
import com.springboot.HRISNEW.interfaceservices.TrainingMejaKerjaServiceInterface;
import com.springboot.HRISNEW.repositories.TrainingMejaKerjaRepo;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author adhin
 */
@Service
public class TrainingMejaKerjaServiceImpl implements TrainingMejaKerjaServiceInterface{

    @Autowired
    TrainingMejaKerjaRepo mejaKerjaRepo;
    
    @Override
    public List<Object[]> getDataTrainingMejaKerja() {
        
        return mejaKerjaRepo.getDataTrainingMejaKerja();
        
    }

    @Override
    public TbMMejakerja findByNikAndTrainingTitle(String email, String training_title) {
        return mejaKerjaRepo.findByNikAndTrainingTitle(email, training_title);
    }

    @Override
    public void updateTbMMejaKerja(Integer nik, String email, String name, String training_title, Date training_time, Date upload_date) {
        mejaKerjaRepo.updateTbMMejakerja(nik, email, name, training_title, training_time, upload_date);
    }

    @Override
    public void saveTbMMejaKerja(Integer nik, String email, String name, String training_title, Date training_time, Date upload_date) {
        mejaKerjaRepo.saveTbMMejakerja(nik, email, name, training_title, training_time, upload_date);
    }
    
    
}
