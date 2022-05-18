/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMParameter;

/**
 *
 * @author HARRY-PC
 */
public interface ParametersServiceInterface {
    TbMParameter findApiById(int id);
    TbMParameter selectOther(String param);
}
