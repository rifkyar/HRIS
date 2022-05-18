/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrReimburse;
import com.springboot.HRISNEW.entities.TbTrReimburseDetail;
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
public interface ReimburseDetailParkingRepo extends CrudRepository<TbTrReimburseDetail, Integer> {

    @Query(value = 
            "SELECT \n"
            + "detail.id,\n"
            + "detail.ticket,\n"
            + "detail.employee_transportation,\n"
            + "detail.rate,\n"
            + "detail.building,\n"
            + "detail.parking_location as locid,\n"
            + "location.parking_location,\n"
            + "location.parking_owner,\n"
            + "location.contact,\n"
            + "detail.status\n"
            + "FROM tb_tr_reimburse_detail detail\n"
            + "JOIN tb_m_parking_location location ON location.id = detail.parking_location WHERE reimburse  = ?1 AND detail.is_deleted =0", nativeQuery = true)
    public List<Object[]> findListDetailReimburseByREQID(String REQID);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE tb_tr_reimburse_detail SET is_deleted=1  WHERE id =?1", nativeQuery = true)
    @Transactional
    public void deleteDetailParkingById(int id);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE tb_tr_reimburse_detail\n"
            + "SET \n"
            + "employee_transportation=?,\n"
            + "parking_location=?,\n"
            + "rate=?,\n"
            + "building=?,\n"
            //            + "reason=?,\n"
            //            + "status=?,\n"
            //            + "ticket=?,\n"
            + "reimburse=?,\n"
            //            + "is_deleted=?,\n"
            //            + "created_date=?,\n"
            + "modified_date=?\n"
            + "WHERE id=?", nativeQuery = true)
    @Transactional
    public void updateDetailParkingByID(int id);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE tb_tr_reimburse_detail SET reason=? , status= ?  , modified_date= ?  WHERE id =? ", nativeQuery = true)
    @Transactional
    public void updateREIMDetailStatus(String reason, int status, Date tanggal, int id);
}
