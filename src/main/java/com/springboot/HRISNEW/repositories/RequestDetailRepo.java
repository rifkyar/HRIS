/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.RequestDetail;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface RequestDetailRepo extends CrudRepository<RequestDetail, Integer> {

    @Query(value = "SELECT * FROM request_detail WHERE id=?1", nativeQuery = true)
    public RequestDetail requestEmpid(String id);

    @Query(value = "SELECT * FROM request_detail WHERE submitted_date = (SELECT MAX(submitted_date) FROM request_detail WHERE nik = ?1 ) AND "
            + "isdelete NOT IN (1) AND request_status NOT IN ('resubmit') AND nik = ?1", nativeQuery = true)
    public RequestDetail lastRequestEmp(Integer id);

    @Query(value = "SELECT * FROM request_detail WHERE nik=?1 AND request_status NOT IN('Resubmit','Rejected') AND isdelete  NOT IN (1)", nativeQuery = true)
    public List<RequestDetail> usedRequest(Integer id);

    @Query(value = "SELECT COUNT(id) FROM request_detail WHERE nik = ?1", nativeQuery = true)
    public Integer jumlahId(Integer id);

    @Query(value = "SELECT * FROM request_detail WHERE (direct_report = ?1 OR next_role = ?2) "
            + "AND isdelete = 0 AND request_status = 'NotYet'", nativeQuery = true)
    public List<RequestDetail> findListLeaveRequest(Integer nikDirect, Integer nikNext);

    @Query(value = "SELECT COUNT(*) FROM `request_detail` WHERE year(submitted_date) = ?1 and request_status = \"Accepted\"", nativeQuery = true)
    public int findTotalDoneLeave(Integer year);

    @Query(value = "SELECT COUNT(*) FROM `request_detail` WHERE year(submitted_date) = ?1 and request_status = \"Rejected\"", nativeQuery = true)
    public int findTotalRejectLeave(Integer year);

    @Query(value = "SELECT COUNT(*) FROM `request_detail` WHERE year(submitted_date) = ?1 and request_status = \"NotYet\"", nativeQuery = true)
    public int findTotalWaitingLeave(Integer year);

    @Query(value = "SELECT request_detail.so_id,COUNT(*) as co,cus.customer_name from request_detail join sakura_db.sales_order so on "
            + "so.so_id=request_detail.so_id JOIN sakura_db.customers cus on cus.customer_id = so.customer_id WHERE month(submitted_date)= ?1 "
            + "AND year(submitted_date) = ?2 and isdelete !=1 GROUP BY request_detail.so_id ORDER BY co DESC LIMIT 10", nativeQuery = true)
    public List<Object[]> findSOTrends(Integer month, Integer year);

    @Query(value = "SELECT ad.nik,req.name,request, bln,ad.leave_days from ( SELECT COUNT(*) as request,sum(leave_days) as leave_days, "
            + "monthname(submitted_date) as bln, nik FROM `request_detail` WHERE month(submitted_date)= ?1 AND year(submitted_date) = ?2 and isdelete !=1 GROUP by nik ) "
            + "as ad JOIN requester_information req on req.nik=ad.nik ORDER BY leave_days DESC LIMIT 10", nativeQuery = true)
    public List<Object[]> findLeavePeopleTrends(Integer month, Integer year);

    @Query(value = "SELECT count(*) FROM request_detail rd JOIN leave_type lt on rd.leave_detail_id = lt.id WHERE month(submitted_date) = ?1 AND year(submitted_date) = ?2 AND isdelete !=1 and rd.leave_detail_id = 1", nativeQuery = true)
    public int findCountCutiNormal(Integer month, Integer year);

    @Query(value = "SELECT count(*) FROM request_detail rd JOIN leave_type lt on rd.leave_detail_id = lt.id WHERE month(submitted_date) = ?1 AND year(submitted_date) = ?2 AND isdelete !=1 and rd.leave_detail_id = 3", nativeQuery = true)
    public int findCountPerjalananBisnis(Integer month, Integer year);

    @Query(value = "SELECT count(*) FROM request_detail rd JOIN leave_type lt on rd.leave_detail_id = lt.id WHERE month(submitted_date) = ?1 AND year(submitted_date) = ?2 AND isdelete !=1 and rd.leave_detail_id = 9", nativeQuery = true)
    public int findCountIstriMelahirkan(Integer month, Integer year);

    @Query(value = "SELECT count(*) FROM request_detail rd JOIN leave_type lt on rd.leave_detail_id = lt.id WHERE month(submitted_date) = ?1 AND year(submitted_date) = ?2 AND isdelete !=1 and rd.leave_detail_id = 10", nativeQuery = true)
    public int findCountCutiBersalin(Integer month, Integer year);

    @Query(value = "SELECT count(*) FROM request_detail rd JOIN leave_type lt on rd.leave_detail_id = lt.id WHERE month(submitted_date) = ?1 AND year(submitted_date) = ?2 AND isdelete !=1 and rd.leave_detail_id = 8", nativeQuery = true)
    public int findCountCutiSakit(Integer month, Integer year);

    @Query(value = "SELECT count(*) FROM request_detail rd JOIN leave_type lt on rd.leave_detail_id = lt.id WHERE month(submitted_date) = ?1 AND year(submitted_date) = ?2 AND isdelete !=1 and rd.leave_detail_id = 13", nativeQuery = true)
    public int findCountCutiIjin(Integer month, Integer year);

    @Query(value = "SELECT * FROM request_detail WHERE (direct_report=?1 OR next_role=?2) AND isdelete=0 AND (request_status='Accepted' OR request_status='Rejected')", nativeQuery = true)
    public List<RequestDetail> historyRm(Integer id, Integer ids);
    
    //Approver's Pending Leave
    @Query(value = "SELECT request_detail.direct_report,us.name, COUNT(*) AS \"Pending Approval\" FROM request_detail\n"
            + "inner join sakura_db.user us on us.user_id = request_detail.direct_report\n"
            + "WHERE request_detail.request_status = \"NotYet\" AND us.active = 1 AND year(request_detail.submitted_date) = ?1\n"
            + "GROUP BY request_detail.direct_report", nativeQuery = true)
    public List<Object[]> ApproverPending(Integer year);

    //Approver's Pending Detail Leave
    @Query(value = "SELECT request_detail.nik,requester_information.name, request_detail.so_id,request_detail.id,leave_type.type,request_detail.startdate,request_detail.enddate,request_detail.notes,request_detail.submitted_date,approval.remarks,request_detail.request_status,request_detail.direct_report FROM approval approval, request_detail\n"
            + "join requester_information on requester_information.nik = request_detail.nik\n"
            + "join leave_type on leave_type.id = request_detail.leave_detail_id\n"
            + "WHERE approval.request_id = request_detail.id AND approval.id IN (SELECT MAX(approval.id) FROM approval WHERE request_id IN (SELECT request_detail.id FROM request_detail WHERE nik IN (SELECT nik FROM requester_information WHERE source_nik IN (SELECT source_nik FROM requester_information))) and request_detail.direct_report = ?1 and year(request_detail.submitted_date) = ?2 and request_detail.request_status = \"NotYet\" GROUP BY approval.request_id)  \n"
            + "ORDER BY `request_detail`.`submitted_date` DESC", nativeQuery = true)
    public List<Object[]> PendingDetail(Integer id,Integer year);


}
