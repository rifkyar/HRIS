/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.RequestDetail;
import com.springboot.HRISNEW.entities.TbMEmployeeTransportation;
import java.sql.Blob;
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
public interface ParkingDetailRepo extends CrudRepository<TbMEmployeeTransportation, Integer> {

    @Query(value = "SELECT * FROM request_detail WHERE id=?1", nativeQuery = true)
    public RequestDetail requestEmpid(String id);

    @Query(value = "SELECT * FROM request_detail WHERE nik=?1 AND request_status NOT IN('Resubmit','Rejected') AND isdelete  NOT IN (1)", nativeQuery = true)
    public List<RequestDetail> usedRequest(Integer id);

    @Query(value = "SELECT COUNT(id) FROM request_detail WHERE nik = ?1", nativeQuery = true)
    public Integer jumlahId(Integer id);

    @Query(value = "SELECT * FROM request_detail WHERE (direct_report = ?1 OR next_role = ?2) "
            + "AND isdelete = 0 AND request_status = 'NotYet'", nativeQuery = true)
    public List<RequestDetail> findListLeaveRequest(Integer nikDirect, Integer nikNext);

    @Query(value = "Select COUNT(*) FROM tb_m_employee_transportation WHERE `transportation_type` =? ", nativeQuery = true)
    public int findListCar(String type_trans);

    @Query(value = "SELECT * FROM tb_m_employee_transportation WHERE nik = ?1  AND is_deleted = false", nativeQuery = true)
    public List<Object[]> findListTransportByNik(Integer nik_requester);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE `tb_tr_reimburse` SET `total_rate`= ?2  WHERE `id` =?1 ", nativeQuery = true)
    @Transactional
    public void changePriceByREQID(String REQID, int Price);

    @Query(value = "SELECT `total_rate` FROM `tb_tr_reimburse` WHERE `id` = ?1 ", nativeQuery = true)
    public int getTotalByREQ(String REQID);

    @Query(value = "SELECT `rate` FROM `tb_tr_reimburse_detail` WHERE `id`= ?1 ", nativeQuery = true)
    public int getPriceREIMByID(int id_detail);

    @Query(value = "SELECT * FROM tb_m_employee_transportation WHERE id = ?1 AND is_deleted = false ", nativeQuery = true)
    public List<Object[]> findTransportBynopol(String police_number);
    
    @Query(value = "SELECT parking_location FROM `tb_m_parking_location` WHERE UPPER(parking_location) = UPPER(?)  AND is_deteled = false ", nativeQuery = true)
    public List<Object[]> findParkLocByName(String parking_location);

    @Query(value = "SELECT * FROM tb_m_employee_transportation WHERE stnk_name = ?1 AND is_deleted = false ", nativeQuery = true)
    public List<Object[]> findTransportBystnkno(String no_stnk);

    @Query(value = "SELECT stnk FROM tb_m_employee_transportation WHERE id = ?1 ", nativeQuery = true)
    public byte[] findBlobFile(String police_number);

    @Query(value = "SELECT ticket FROM tb_tr_reimburse_detail WHERE  id = ?1 ", nativeQuery = true)
    public byte[] findBlobFileDetailReimByid(int id);

    @Modifying(flushAutomatically = true)
    @Query(value = "UPDATE tb_m_employee_transportation SET is_deleted = '1' WHERE id = ?1 ", nativeQuery = true)
    @Transactional
    public void deleteTransport(String police_number);

}
