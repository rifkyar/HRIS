/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrainingUserRegistration;
import java.util.Date;
import java.util.List;

/**
 *
 * @author User
 */
public interface TrainingUserRegistrationServiceInterface {

    TrainingUserRegistration save(TrainingUserRegistration t);
    
    void disableFlag(Date updatedDate, Integer nik, Integer transaction_id, Integer nik2);
    
    public boolean checkFlag(String req, Integer nik);
    
    public String checkUpload(String req);

    void uploadFile(byte[] fileupload, Date updatedDate, Integer nik, String req);
    
    public byte[] findBlobFile(String id);
    
    List<Object[]> getUserRegisteredTraining(int id);
    
    TrainingUserRegistration findById(int id);

}
