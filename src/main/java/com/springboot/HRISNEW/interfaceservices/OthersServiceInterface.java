/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.MOthers;

/**
 *
 * @author HARRY-PC
 */
public interface OthersServiceInterface {
    MOthers findApiById(int id);
    
    MOthers selectOther(String param);
}
