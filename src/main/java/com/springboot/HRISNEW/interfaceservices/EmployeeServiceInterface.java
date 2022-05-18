/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMEmployee;
import java.util.List;

/**
 *
 * @author USER
 */
public interface EmployeeServiceInterface {

    TbMEmployee saveEmployee(TbMEmployee em);

    public List<TbMEmployee> showPendingChangeReq();

    public List<TbMEmployee> selectPendingChangeReq(int nikPending);
}
