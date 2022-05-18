/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMVaccineType;

/**
 *
 * @author RAR
 */
public interface VaccineTypeServiceInterface {
    Iterable<TbMVaccineType>getAll();
    TbMVaccineType VaccineTypeById(int id);
}
