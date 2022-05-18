/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMCertificateType;
import com.springboot.HRISNEW.interfaceservices.CertificateTypeServiceInterface;
import com.springboot.HRISNEW.repositories.CertificateTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author RAR
 */
@Service
public class CertificateTypeServiceImpl implements CertificateTypeServiceInterface{

    @Autowired
    private CertificateTypeRepo certificateTyperepo;
    
    @Override
    public Iterable<TbMCertificateType> getAll() {
        return certificateTyperepo.findAll();
    }

    @Override
    public TbMCertificateType save(TbMCertificateType certificateType) {
        return certificateTyperepo.save(certificateType);
    }

    @Override
    public void delete(Integer id) {
        certificateTyperepo.deleteById(id);
    }

    @Override
    public TbMCertificateType getById(Integer id) {
        return certificateTyperepo.findById(id).get();
    }
    
}
