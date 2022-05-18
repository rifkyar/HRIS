/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMCertificateType;
/**
 *
 * @author RAR
 */
public interface CertificateTypeServiceInterface  {
    
    Iterable<TbMCertificateType>getAll();
    
    TbMCertificateType save(TbMCertificateType certificateType);
    
    void delete(Integer id);
    
    TbMCertificateType getById(Integer id);
}
