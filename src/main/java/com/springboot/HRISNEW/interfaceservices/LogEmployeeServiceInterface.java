/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbTrLogEmployee;
import java.util.List;

/**
 *
 * @author USER
 */
public interface LogEmployeeServiceInterface {

    TbTrLogEmployee saveLogEmployee(TbTrLogEmployee le);
    
    public List<TbTrLogEmployee> selectPendingChangeReq(int nikPending);
    
    TbTrLogEmployee doneChangeSummary (TbTrLogEmployee done);
    
    TbTrLogEmployee selectbyId(int id);

}
