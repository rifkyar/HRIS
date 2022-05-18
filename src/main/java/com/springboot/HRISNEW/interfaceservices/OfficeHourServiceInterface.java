/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.MOfficehour;

/**
 *
 * @author USER
 */
public interface OfficeHourServiceInterface {

    Iterable<MOfficehour> getAll();

    MOfficehour findById(Integer id);
}
