/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TrRequestovertimedetail;
import com.springboot.HRISNEW.interfaceservices.RequestDetailOvertimeServiceInterface;
import com.springboot.HRISNEW.repositories.RequestDetailOvertimeRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class RequestDetailOvertimeServiceImpl implements RequestDetailOvertimeServiceInterface {

    @Autowired
    private RequestDetailOvertimeRepo requestDetailOvertimeRepo;

    @Override
    public List<TrRequestovertimedetail> checkRequestLastMonth(String nik) {
        return requestDetailOvertimeRepo.checkRequestLastMonth(nik);
    }

    @Override
    public List<TrRequestovertimedetail> showPenddingSoOt() {
        return requestDetailOvertimeRepo.pendding();
    }

    @Override
    public Iterable<TrRequestovertimedetail> findDetailbyId(String id) {
        return requestDetailOvertimeRepo.findDetailbyId(id);
    }

    @Override
    public Optional<TrRequestovertimedetail> findReqDetailbyID(int id) {
        return requestDetailOvertimeRepo.findById(id);
    }

    @Override
    public TrRequestovertimedetail findRequestOTDetailbyID(String id) {
        return requestDetailOvertimeRepo.findOnebyId(id);
    }

    @Override
    public TrRequestovertimedetail updateOvertimeDetail(TrRequestovertimedetail trdet) {
        return requestDetailOvertimeRepo.save(trdet);
    }

    @Override
    public int countRequestOTbyOTid(String id) {
      return requestDetailOvertimeRepo.countRequestOTbyOTid(id);
    }

    @Override
    public int countRequestOTStatusbyOTid(String id) {
        return requestDetailOvertimeRepo.countRequestOTStatusbyOTid(id);
    }

    @Override
    public List<TrRequestovertimedetail> showAllForRecap(String periode) {
        return requestDetailOvertimeRepo.showAllBySoidAndPeriode(periode);
    }

    @Override
    public List<TrRequestovertimedetail> showForRecap(String soid, String periode) {
        return requestDetailOvertimeRepo.showBySoidAndPeriode(soid, periode);
    }

    @Override
    public TrRequestovertimedetail saveOvertimeDetail(TrRequestovertimedetail trdet) {
        return requestDetailOvertimeRepo.save(trdet);
    }

    @Override
    public List<TrRequestovertimedetail> showBynikAndPeriode(String nik, String periode) {
        return requestDetailOvertimeRepo.showBynikAndPeriode(nik, periode);
    }
    
}
