/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.LeaveDetail;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface LeaveDetailRepo extends CrudRepository<LeaveDetail, Integer>{
    
    @Query(value = "SELECT * FROM leave_detail WHERE gender=?1 AND marriage_status=?2", nativeQuery = true)
    public List<LeaveDetail> findLeaveDetailbyGenderMarriage(Integer gender, Integer marriage);
    
}
