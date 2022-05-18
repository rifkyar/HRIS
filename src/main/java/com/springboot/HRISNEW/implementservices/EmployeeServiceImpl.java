/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMEmployee;
import com.springboot.HRISNEW.interfaceservices.EmployeeServiceInterface;
import com.springboot.HRISNEW.repositories.EmployeeRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author USER
 */
@Service
public class EmployeeServiceImpl implements EmployeeServiceInterface {

    @Autowired
    EmployeeRepo employeeRepo;

    @Override
    public TbMEmployee saveEmployee(TbMEmployee em) {
        return employeeRepo.save(em);
    }

    @Override
    public List<TbMEmployee> showPendingChangeReq() {
        return employeeRepo.pendingChangeReq();
    }

    @Override
    public List<TbMEmployee> selectPendingChangeReq(int nikPending) {
        return employeeRepo.selectPendingChangeReq(nikPending);
    }
}
