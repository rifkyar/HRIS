/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbTrLogEmployee;
import com.springboot.HRISNEW.interfaceservices.LogEmployeeServiceInterface;
import com.springboot.HRISNEW.repositories.LogEmployeeRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class LogEmployeeServiceImpl implements LogEmployeeServiceInterface {

    @Autowired
    LogEmployeeRepo logEmployeeRepo;

    @Override
    public TbTrLogEmployee saveLogEmployee(TbTrLogEmployee le) {
        return logEmployeeRepo.save(le);
    }

    @Override
    public List<TbTrLogEmployee> selectPendingChangeReq(int nikPending) {
        return logEmployeeRepo.selectPendingChangeReq(nikPending);
    }

    @Override
    public TbTrLogEmployee doneChangeSummary(TbTrLogEmployee done) {
        return logEmployeeRepo.save(done);
    }

    @Override
    public TbTrLogEmployee selectbyId(int id) {
        return logEmployeeRepo.selectbyId(id);
    }

}
