/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbMParameter;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author User
 */
public interface tbMParameterRepo extends CrudRepository<TbMParameter, Integer> {

    @Query(value = "SELECT value FROM `tb_m_parameter` WHERE id = 5", nativeQuery = true)
    public String findCheckInURL();
    
    @Query(value = "SELECT value FROM `tb_m_parameter` WHERE id = 4", nativeQuery = true)
    public String findFeedbackFormURL();
    
    @Query(value = "SELECT value FROM `tb_m_parameter` WHERE id = 6", nativeQuery = true)
    public String findQRPath();
    
    @Query(value = "SELECT value FROM `tb_m_parameter` WHERE id = 7", nativeQuery = true)
    public String findQRSavePath();
    
    @Query(value = "SELECT value FROM `tb_m_parameter` WHERE id = 8", nativeQuery = true)
    public String findConfirmURL();
    
    @Query(value = "SELECT value FROM `tb_m_parameter` WHERE id = 9", nativeQuery = true)
    public String findDeclineURL();
}
