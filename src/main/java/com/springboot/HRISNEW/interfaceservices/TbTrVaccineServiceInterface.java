/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbTrVaccine;
import java.util.List;

/**
 *
 * @author RAR
 */
public interface TbTrVaccineServiceInterface {
    void saveUp(TbTrVaccine tbTrVaccine);
    TbTrVaccine findOne(Integer id);
    void updateVaccineFile(String path, String namefile, int VaccineId);
    public List<Object[]> getAllHistoryByNikEmp(Integer nik);
    public List<Object[]> getUnderRm(Integer nik);
    public List<Object[]> getPreviousDose();
}
