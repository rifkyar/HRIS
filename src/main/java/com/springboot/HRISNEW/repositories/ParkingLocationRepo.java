/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbMParkingLocation;
import java.util.Date;
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
public interface ParkingLocationRepo extends CrudRepository<TbMParkingLocation, Integer> {

    @Query(value = "SELECT * FROM tb_m_parking_location WHERE is_deteled = 0", nativeQuery = true)
    public List<Object[]> findListLocationParking();

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE tb_m_parking_location SET is_deteled = '1' WHERE id = ?1 ", nativeQuery = true)
    @Transactional
    public void deleteParking(int id);
    
    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE tb_m_employee_transportation "
            + "SET transportation_type=?,"
            + "stnk_name=?,"
            + "modified_date=?"
            + "WHERE id = ?", nativeQuery = true)
    @Transactional
    public void updateParkingWOImage(String transportType,int stnk_name,Date modified,String id);
    
     @Query(value = "SELECT * FROM tb_m_parking_location WHERE id = ?1 ", nativeQuery = true)
    public List<Object[]> findLocationParkingById(int id);
    
     @Query(value = "SELECT * FROM tb_m_parking_location WHERE is_deteled=0 AND `parking_location` LIKE %?1% ", nativeQuery = true)
    public List<Object[]> findLocationParkingByName(String name);
}
