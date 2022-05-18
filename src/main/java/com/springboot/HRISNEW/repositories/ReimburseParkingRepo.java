/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrReimburse;
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
public interface ReimburseParkingRepo extends CrudRepository<TbTrReimburse, Integer> {

    @Query(value = "SELECT * FROM tb_tr_reimburse WHERE nik = ?1 ", nativeQuery = true)
    public List<Object[]> findListParkingReimbursementRequestByNik(Integer nik_requester);

    @Query(value = "SELECT * FROM tb_tr_reimburse WHERE id = ?1 ", nativeQuery = true)
    public List<Object[]> findListParkingReimbursementRequestREQID(String REQID);

//    @Query(value = "SELECT\n"
//            + "    reim.id AS reqID,\n"
//            + "    reim.nik AS NIK,\n"
//            + "    reim.nik AS NamaStaff,\n"
//            + "    reim.start_period AS Periode_Start,\n"
//            + "    detail.employee_transportation AS police_number,\n"
//            + "    trans.transportation_type AS transport_type,\n"
//            + "    park_loc.reimburse_type AS tipe_pembayaran,\n"
//            + "    reim.total_rate AS total_rate,\n"
//            + "    trans.stnk_name AS no_stnk,\n"
//            + "    park_loc.parking_owner AS Park_owner,\n"
//            + "    park_loc.parking_address AS alamat_parking,\n"
//            + "    park_loc.contact AS no_telp_park,\n"
//            + "    detail.building AS customer_building,\n"
//            + "    reim.direct_report1 AS DR_1\n"
//            + "FROM\n"
//            + "    tb_tr_reimburse_detail AS detail\n"
//            + "JOIN tb_tr_reimburse reim ON\n"
//            + "    reim.id = detail.reimburse\n"
//            + "JOIN tb_m_employee_transportation trans ON\n"
//            + "    trans.id = detail.employee_transportation\n"
//            + "JOIN tb_m_status stat ON\n"
//            + "    stat.id = detail.status\n"
//            + "JOIN tb_m_parking_location park_loc ON\n"
//            + "    detail.parking_location = park_loc.id\n"
//            + "WHERE\n"
//            + "    reim.nik = ?1 AND detail.is_deleted = 0 AND  start_period LIKE %?2%", nativeQuery = true)
    //Baru
    @Query(value = "SELECT reim.id AS reqID, reim.nik AS NIK, reim.nik AS NamaStaff, reim.start_period AS Periode_Start, employee_transportation AS police_number, trans.transportation_type AS transport_type, park_loc.reimburse_type AS tipe_pembayaran, reim.total_rate AS total_rate, trans.stnk_name AS no_stnk, park_loc.parking_owner AS Park_owner, park_loc.parking_address AS alamat_parking, park_loc.contact AS no_telp_park, detail.building AS customer_building,  reim.direct_report1 AS DR_1 FROM tb_tr_reimburse_detail AS detail \n"
            + "JOIN tb_tr_reimburse reim ON reim.id = detail.reimburse \n"
            + "JOIN tb_m_employee_transportation trans ON trans.id = detail.employee_transportation \n"
            + "JOIN tb_m_status stat ON stat.id = detail.status \n"
            + "JOIN tb_m_parking_location park_loc ON detail.parking_location = park_loc.id \n"
            + "WHERE reim.nik IN (SELECT nik FROM requester_information WHERE source_nik IN (SELECT source_nik FROM requester_information WHERE nik = ?1)) AND detail.is_deleted = 0 AND  start_period LIKE %?2%", nativeQuery = true)
    public List<Object[]> findListDetailByNIKForExcel(int NIK, String periode);

    
    @Query(value = "SELECT\n"
            + "    reim.id AS reqID,\n"
            + "    reim.nik AS NIK,\n"
            + "    reim.nik AS NamaStaff,\n"
            + "    reim.start_period AS Periode_Start,\n"
            + "    detail.employee_transportation AS police_number,\n"
            + "    trans.transportation_type AS transport_type,\n"
            + "    park_loc.reimburse_type AS tipe_pembayaran,\n"
            + "    reim.total_rate AS total_rate,\n"
            + "    trans.stnk_name AS no_stnk,\n"
            + "    park_loc.parking_owner AS Park_owner,\n"
            + "    park_loc.parking_address AS alamat_parking,\n"
            + "    park_loc.contact AS no_telp_park,\n"
            + "    detail.building AS customer_building\n"
            + "FROM\n"
            + "    tb_tr_reimburse_detail AS detail\n"
            + "JOIN tb_tr_reimburse reim ON\n"
            + "    reim.id = detail.reimburse\n"
            + "JOIN tb_m_employee_transportation trans ON\n"
            + "    trans.id = detail.employee_transportation\n"
            + "JOIN tb_m_status stat ON\n"
            + "    stat.id = detail.status\n"
            + "JOIN tb_m_parking_location park_loc ON\n"
            + "    detail.parking_location = park_loc.id\n"
            + "WHERE\n"
            + "    reim.direct_report1 = ?1 AND detail.is_deleted = 0 AND  start_period LIKE %?2%", nativeQuery = true)
    public List<Object[]> findListDetailByApprovalNIKForExcel(int NIK_approval,String periode);

//    @Query(value = "SELECT\n"
//            + "    reim.id AS reqID,\n"
//            + "    reim.nik AS NIK,\n"
//            + "    reim.nik AS NamaStaff,\n"
//            + "    reim.start_period AS Periode_Start,\n"
//            + "    detail.employee_transportation AS police_number,\n"
//            + "    trans.transportation_type AS transport_type,\n"
//            + "    park_loc.reimburse_type AS tipe_pembayaran,\n"
//            + "    reim.total_rate AS total_rate,\n"
//            + "    trans.stnk_name AS no_stnk,\n"
//            + "    park_loc.parking_owner AS Park_owner,\n"
//            + "    park_loc.parking_address AS alamat_parking,\n"
//            + "    park_loc.contact AS no_telp_park,\n"
//            + "    detail.building AS customer_building\n"
//            + "FROM\n"
//            + "    tb_tr_reimburse_detail AS detail\n"
//            + "JOIN tb_tr_reimburse reim ON\n"
//            + "    reim.id = detail.reimburse\n"
//            + "JOIN tb_m_employee_transportation trans ON\n"
//            + "    trans.id = detail.employee_transportation\n"
//            + "JOIN tb_m_status stat ON\n"
//            + "    stat.id = detail.status\n"
//            + "JOIN tb_m_parking_location park_loc ON\n"
//            + "    detail.parking_location = park_loc.id\n"
//            + "WHERE\n"
//            + "    reim.direct_report2 = ?1 AND detail.is_deleted = 0 AND  start_period LIKE %?2%", nativeQuery = true)
    //Baru
    @Query(value = "SELECT reim.id AS reqID, reim.nik AS NIK, reim.nik AS NamaStaff, reim.start_period AS Periode_Start,    detail.employee_transportation AS police_number, trans.transportation_type AS transport_type,    park_loc.reimburse_type AS tipe_pembayaran, reim.total_rate AS total_rate, trans.stnk_name AS no_stnk, park_loc.parking_owner AS Park_owner, park_loc.parking_address AS alamat_parking, park_loc.contact AS no_telp_park, detail.building AS customer_building,detail.created_date, detail.modified_date,reim.direct_report1,us.name,reim.so,cus.customer_name,stats.value FROM tb_tr_reimburse_detail AS detail \n"
            + "JOIN tb_tr_reimburse reim ON reim.id = detail.reimburse \n"
            + "JOIN tb_m_employee_transportation trans ON trans.id = detail.employee_transportation \n"
            + "JOIN tb_m_status stat ON stat.id = detail.status \n"
            + "JOIN tb_m_parking_location park_loc ON detail.parking_location = park_loc.id\n"
            + "join tb_m_status stats on stats.id = detail.status\n"
            + "join sakura_db.user us on us.user_id = reim.direct_report1\n"
            + "join sakura_db.sales_order so on so.so_id = reim.so\n"
            + "join sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "WHERE reim.direct_report2 = ?1 AND detail.is_deleted = 0 AND start_period LIKE %?2%", nativeQuery = true)
    public List<Object[]> findListDetailByMSFCNIKForExcel(int NIK_approval, String periode);

    
    @Query(value = "SELECT \n"
            + "reim.id as reqID,\n"
            + "reim.start_period,\n"
            + "reim.end_period,\n"
            + "detail.rate,\n"
            + "detail.created_date, \n"
            + "stat.value,\n"
            + "reim.so,\n"
            + "reim.customers,\n"
            + "detail.employee_transportation,\n"
            + "trans.transportation_type,\n"
            + "detail.reason,\n"
            + "detail.id,\n"
            + "reim.nik,\n"
            + "reim.nik as name,\n"
            + "detail.status as status_code\n"
            + "FROM tb_tr_reimburse_detail detail\n"
            + "JOIN tb_tr_reimburse reim ON reim.id = detail.reimburse\n"
            + "JOIN tb_m_employee_transportation trans ON trans.id=detail.employee_transportation\n"
            + "JOIN tb_m_status stat ON stat.id=detail.status\n"
            + "WHERE reim.nik= ?1 AND detail.is_deleted = 0 ", nativeQuery = true)
    public List<Object[]> findListParkingReimbursementDetailRequestByNik(Integer nik_requester);

    @Query(value
            = "SELECT \n"
            + "reim.id as reqID,\n"
            + "reim.start_period,\n"
            + "reim.end_period,\n"
            + "detail.rate,\n"
            + "detail.created_date, \n"
            + "stat.value,\n"
            + "reim.so,\n"
            + "reim.customers,\n"
            + "detail.employee_transportation,\n"
            + "trans.transportation_type,\n"
            + "detail.reason,\n"
            + "detail.id,\n"
            + "reim.nik,\n"
            + "reim.nik as name\n"
            + "FROM tb_tr_reimburse_detail detail\n"
            + "JOIN tb_tr_reimburse reim ON reim.id = detail.reimburse\n"
            + "JOIN tb_m_employee_transportation trans ON trans.id=detail.employee_transportation\n"
            + "JOIN tb_m_status stat ON stat.id=detail.status\n"
            + "WHERE reim.direct_report1= ?1 AND detail.is_deleted = 0  AND detail.status = ?2", nativeQuery = true)
    public List<Object[]> findListParkingReimbursementDetailApprovalByNikAndStatus(Integer nik_requester, Integer status_id);

    @Query(value
            = "SELECT \n"
            + "reim.id as reqID,\n"
            + "reim.start_period,\n"
            + "reim.end_period,\n"
            + "detail.rate,\n"
            + "detail.created_date, \n"
            + "stat.value,\n"
            + "reim.so,\n"
            + "reim.customers,\n"
            + "detail.employee_transportation,\n"
            + "trans.transportation_type,\n"
            + "detail.reason,\n"
            + "detail.id,\n"
            + "reim.nik,\n"
            + "reim.nik as name\n"
            + "FROM tb_tr_reimburse_detail detail\n"
            + "JOIN tb_tr_reimburse reim ON reim.id = detail.reimburse\n"
            + "JOIN tb_m_employee_transportation trans ON trans.id=detail.employee_transportation\n"
            + "JOIN tb_m_status stat ON stat.id=detail.status\n"
            + "WHERE reim.direct_report1= ?1 AND detail.is_deleted = 0", nativeQuery = true)
    public List<Object[]> findListParkingReimbursementDetailApprovalByNik(Integer nik_requester);

    
    @Query(value
            = "SELECT \n"
            + "reim.id as reqID,\n"
            + "reim.start_period,\n"
            + "reim.end_period,\n"
            + "detail.rate,\n"
            + "detail.created_date, \n"
            + "stat.value,\n"
            + "reim.so,\n"
            + "reim.customers,\n"
            + "detail.employee_transportation,\n"
            + "trans.transportation_type,\n"
            + "detail.reason,\n"
            + "detail.id,\n"
            + "reim.nik,\n"
            + "reim.nik as name\n"
            + "FROM tb_tr_reimburse_detail detail\n"
            + "JOIN tb_tr_reimburse reim ON reim.id = detail.reimburse\n"
            + "JOIN tb_m_employee_transportation trans ON trans.id=detail.employee_transportation\n"
            + "JOIN tb_m_status stat ON stat.id=detail.status\n"
            + "WHERE reim.direct_report2= ?1 AND detail.is_deleted = 0  AND detail.status = ?2", nativeQuery = true)
    public List<Object[]> findListParkingReimbursementDetailMFSCByNikAndStatus(Integer nik_requester, Integer status_id);

    @Query(value
            = "SELECT \n"
            + "reim.id as reqID,\n"
            + "reim.start_period,\n"
            + "reim.end_period,\n"
            + "detail.rate,\n"
            + "detail.created_date, \n"
            + "stat.value,\n"
            + "reim.so,\n"
            + "reim.customers,\n"
            + "detail.employee_transportation,\n"
            + "trans.transportation_type,\n"
            + "detail.reason,\n"
            + "detail.id,\n"
            + "reim.nik,\n"
            + "reim.nik as name\n"
            + "FROM tb_tr_reimburse_detail detail\n"
            + "JOIN tb_tr_reimburse reim ON reim.id = detail.reimburse\n"
            + "JOIN tb_m_employee_transportation trans ON trans.id=detail.employee_transportation\n"
            + "JOIN tb_m_status stat ON stat.id=detail.status\n"
            + "WHERE reim.direct_report2= ?1 AND detail.is_deleted = 0", nativeQuery = true)
    public List<Object[]> findListParkingReimbursementDetaiMSFCByNik(Integer nik_requester);
}
