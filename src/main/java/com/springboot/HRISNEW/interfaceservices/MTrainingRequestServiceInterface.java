/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author User
 */
public interface MTrainingRequestServiceInterface {

    public List<Object[]> findallrequest();

    public void acceptrequest(Integer id, Integer nik, Timestamp timestamp);

    public void rejectRequest(Integer id, Integer nik, Timestamp timestamp, String remark);
    
}
