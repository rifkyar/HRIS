/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrApprovalReimburse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface ParkingApprovalRepo extends CrudRepository<TbTrApprovalReimburse, Integer> {

   
}
