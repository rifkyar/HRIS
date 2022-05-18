/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMMejakerja;
import java.util.Date;
import java.util.List;

/**
 *
 * @author adhin
 */
public interface TrainingMejaKerjaServiceInterface {
    
    public List<Object[]> getDataTrainingMejaKerja();
    
    public TbMMejakerja findByNikAndTrainingTitle(String email, String training_title);
    
    void updateTbMMejaKerja(Integer nik, String email, String name, String training_title, Date training_time, Date upload_date);
    
    void saveTbMMejaKerja(Integer nik, String email, String name, String training_title, Date training_time, Date upload_date);
    
}
