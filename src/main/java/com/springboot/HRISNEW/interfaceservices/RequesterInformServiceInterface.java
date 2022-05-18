/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.Division;
import com.springboot.HRISNEW.entities.RequesterInformation;
import java.util.List;

/**
 *
 * @author HARRY-PC
 */
public interface RequesterInformServiceInterface {
    RequesterInformation save(RequesterInformation req);
    RequesterInformation findRequesterbyid(int nik);
    List<Object[]> viewAllReqInfo();
    void saveDivision(String division, Integer nik);
}
