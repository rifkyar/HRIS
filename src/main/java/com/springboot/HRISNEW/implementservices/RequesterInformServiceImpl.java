/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.Division;
import com.springboot.HRISNEW.entities.RequesterInformation;
import com.springboot.HRISNEW.interfaceservices.RequesterInformServiceInterface;
import com.springboot.HRISNEW.repositories.RequesterInformationRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class RequesterInformServiceImpl implements RequesterInformServiceInterface {

    @Autowired
    RequesterInformationRepo rir;

    @Override
    public RequesterInformation findRequesterbyid(int nik) {
        return rir.findRequesterbyid(nik);
    }

    @Override
    public RequesterInformation save(RequesterInformation req) {
        return rir.save(req);
    }

    @Override
    public List<Object[]> viewAllReqInfo() {
        return rir.viewAllReqInfo();
    }
 
    @Override
    public void saveDivision(String division, Integer nik) {
        rir.saveDivision(division,nik);
    }}
