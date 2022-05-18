/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrRequestovertime;
import com.springboot.HRISNEW.entities.TrSoovertime;
import com.springboot.HRISNEW.interfaceservices.SOOvertimeServiceInterface;
import com.springboot.HRISNEW.repositories.SOOvertimeRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class SOOvertimeServiceImpl implements SOOvertimeServiceInterface {

    @Autowired
    SOOvertimeRepo soOvertimeRepo;

    @Override
    public List<Object[]> soOtListAdm() {
        return soOvertimeRepo.soOtListAdm();
    }

//    @Override
//    public TrSoovertime getById(String soId) {
//        try {
//            return soOvertimeRepo.findById(soId).get();
//        } catch (Exception e) {
//            return null;
//        }
//    }
    @Override
    public TrSoovertime showSoOt(String id) {
        return soOvertimeRepo.selectbyId(id);
    }

    @Override
    public TrSoovertime updateSoOt(TrSoovertime soot) {
        return soOvertimeRepo.save(soot);
    }

    @Override
    public List<Object[]> soOtListRequester(String id) {
        return soOvertimeRepo.soOtListRequester(id);
    }

}
