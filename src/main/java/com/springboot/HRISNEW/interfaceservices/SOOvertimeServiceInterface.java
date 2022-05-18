/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TrRequestovertime;
import com.springboot.HRISNEW.entities.TrSoovertime;
import java.util.List;

/**
 *
 * @author USER
 */
public interface SOOvertimeServiceInterface {

    public List<Object[]> soOtListAdm();

//    public TrSoovertime getById(String soId);

    public TrSoovertime showSoOt(String id);

    public TrSoovertime updateSoOt(TrSoovertime soot);

    public List<Object[]> soOtListRequester(String id);

}
