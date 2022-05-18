/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrRequestovertimedetail;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author HARRY-PC
 */
public interface RequestDetailOvertimeServiceInterface {

    List<TrRequestovertimedetail> checkRequestLastMonth(String nik);

    List<TrRequestovertimedetail> showPenddingSoOt();

    Iterable<TrRequestovertimedetail> findDetailbyId(String id);

    Optional<TrRequestovertimedetail> findReqDetailbyID(int id);

    TrRequestovertimedetail findRequestOTDetailbyID(String id);

    TrRequestovertimedetail updateOvertimeDetail(TrRequestovertimedetail trdet);
    
    TrRequestovertimedetail saveOvertimeDetail(TrRequestovertimedetail trdet);

    int countRequestOTbyOTid(String id);

    int countRequestOTStatusbyOTid(String id);
    
    List<TrRequestovertimedetail> showAllForRecap(String periode);
    
    List<TrRequestovertimedetail> showForRecap(String soid, String periode);
    
    List<TrRequestovertimedetail> showBynikAndPeriode(String nik, String periode);
}
