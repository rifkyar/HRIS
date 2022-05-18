/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbTrVaccine;
import com.springboot.HRISNEW.interfaceservices.TbTrVaccineServiceInterface;
import com.springboot.HRISNEW.repositories.TbTrVaccineRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class TbTrVaccineServiceImpl implements TbTrVaccineServiceInterface {

    @Autowired
    private TbTrVaccineRepo tbTrVaccineRepo;
    
    @Override
    public void saveUp(TbTrVaccine tbTrVaccine) {
        tbTrVaccineRepo.save(tbTrVaccine);
    }

    @Override
    public TbTrVaccine findOne(Integer id) {
        return tbTrVaccineRepo.findone(id);
    }

    @Override
    public void updateVaccineFile(String path, String namefile, int VaccineId) {
        tbTrVaccineRepo.updateVaccineFile(path, namefile, VaccineId);
    }

    @Override
    public List<Object[]> getAllHistoryByNikEmp(Integer nik) {
        return tbTrVaccineRepo.getAllHistoryByNikEmp(nik);
    }

    @Override
    public List<Object[]> getUnderRm(Integer nik) {
        return tbTrVaccineRepo.getUnderRm(nik);
    }

    @Override
    public List<Object[]> getPreviousDose() {
        return tbTrVaccineRepo.getPreviousDose();
    }
    
}
