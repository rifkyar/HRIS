/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.Division;
import com.springboot.HRISNEW.entities.RequesterInformation;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface RequesterInformationRepo extends CrudRepository<RequesterInformation, Integer>{
    
    @Query(value="SELECT * from requester_information WHERE nik = ?1", nativeQuery=true)
    public RequesterInformation findRequesterbyid(Integer nik);
    
    @Query(value = "SELECT * FROM requester_information ORDER BY nik DESC", nativeQuery = true)
    public List<Object[]> viewAllReqInfo();
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE requester_information SET division=?1 WHERE nik =?2", nativeQuery = true)
    public void saveDivision(String division, Integer nik);
    
}
