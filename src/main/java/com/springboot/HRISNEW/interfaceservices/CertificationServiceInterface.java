/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbTrCertification;
import java.util.List;

/**
 *
 * @author RAR
 */
public interface CertificationServiceInterface {
    
    Iterable<TbTrCertification>getAll();
    
    TbTrCertification save(TbTrCertification certification);
    
    void delete(Integer id);
    
    TbTrCertification getById(Integer id);
    
    Iterable<TbTrCertification>tampilAktif();
    
    List<TbTrCertification>getCertifByEmpl(Integer id);
    
    List<TbTrCertification>findCertifById(Integer id);
}
