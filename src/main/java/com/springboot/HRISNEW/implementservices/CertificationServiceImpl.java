/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbTrCertification;
import com.springboot.HRISNEW.interfaceservices.CertificationServiceInterface;
import com.springboot.HRISNEW.repositories.CertificationRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class CertificationServiceImpl implements CertificationServiceInterface{

    @Autowired
    private CertificationRepo certificationrepo;
    
    @Override
    public Iterable<TbTrCertification> getAll() {
       return certificationrepo.findAll();
    }

    @Override
    public TbTrCertification save(TbTrCertification certification) {
        return certificationrepo.save(certification);
    }

    @Override
    public void delete(Integer id) {
        certificationrepo.deleteById(id);
    }

    @Override
    public TbTrCertification getById(Integer id) {
        return certificationrepo.findById(id).get();
    }

    @Override
    public Iterable<TbTrCertification> tampilAktif() {
        return certificationrepo.tampilaktif();
    }

    @Override
    public List<TbTrCertification> getCertifByEmpl(Integer id) {
        return certificationrepo.getCertifEmpbyId(id);
    }

    @Override
    public List<TbTrCertification> findCertifById(Integer id) {
        return certificationrepo.findCertifById(id);
    }
    
}
