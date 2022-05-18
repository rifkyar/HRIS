/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrainingUserRegistration;
import com.springboot.HRISNEW.interfaceservices.TrainingUserRegistrationServiceInterface;
import com.springboot.HRISNEW.repositories.TrainingUserRegistrationRepo;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class TrainingUserRegistrationServiceImpl implements TrainingUserRegistrationServiceInterface {

    @Autowired
    TrainingUserRegistrationRepo traininguserregistrationrepo;

    @Override
    public TrainingUserRegistration save(TrainingUserRegistration t) {
        return traininguserregistrationrepo.save(t);
    }

    @Override
    public void disableFlag(Date updatedDate, Integer nik, Integer transaction_id, Integer nik2) {
        traininguserregistrationrepo.disableFlag(updatedDate, nik, transaction_id, nik2);
    }

    @Override
    public boolean checkFlag(String req, Integer nik) {
        return traininguserregistrationrepo.checkFlag(req, nik);
    }

    @Override
    public void uploadFile(byte[] fileupload, Date updatedDate, Integer nik, String req) {
        traininguserregistrationrepo.uploadFile(fileupload, updatedDate, nik, req);
    }

    @Override
    public String checkUpload(String req) {
        return traininguserregistrationrepo.checkUpload(req);
    }

    @Override
    public byte[] findBlobFile(String id) {
        return traininguserregistrationrepo.findBlobFile(id);
    }

    @Override
    public List<Object[]> getUserRegisteredTraining(int id) {
        return traininguserregistrationrepo.getUserRegisteredTraining(id);
    }

    @Override
    public TrainingUserRegistration findById(int id) {
        return traininguserregistrationrepo.findById(id);
    }

}
