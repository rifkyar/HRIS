/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.Approval;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface ApprovalRepo extends CrudRepository<Approval, Integer> {

    @Query(value = "SELECT * FROM approval WHERE request_id = ?1  AND id = (SELECT id FROM approval WHERE request_id = ?1 AND status NOT IN ('Resubmit','NotYet','Canceled'))", nativeQuery = true)
    public Approval findAppByid(String requestId);

    public static final String FIND_REQUEST = "SELECT request_detail.id,request_detail.leave_detail_id,request_detail.startdate,request_detail.enddate,"
            + "request_detail.notes,request_detail.submitted_date,approval.remarks,request_detail.request_status,request_detail.direct_report, request_detail.isdelete "
            + "FROM approval approval, request_detail WHERE approval.request_id = request_detail.id AND approval.id IN (SELECT MAX(approval.id) FROM approval "
            + "WHERE request_id IN (SELECT request_detail.id FROM request_detail WHERE nik IN (SELECT nik FROM requester_information WHERE source_nik "
            + "IN (SELECT source_nik FROM requester_information WHERE nik = ?1))) GROUP BY approval.request_id) "
            + "ORDER BY request_detail.submitted_date DESC";

    public static final String FIND_DATE = "SELECT request_detail.id,request_detail.nik,request_detail.so_id,request_detail.leave_detail_id,request_detail.startdate,"
            + "request_detail.enddate,request_detail.notes,request_detail.submitted_date,approval.remarks,request_detail.request_status,request_detail.isdelete, "
            + "approval.modified_date FROM approval approval, request_detail request_detail WHERE approval.request_id=request_detail.id AND approval.id IN "
            + "(SELECT MAX(approval.id) FROM approval WHERE request_id IN (SELECT request_detail.id FROM request_detail WHERE (direct_report = ?1 OR next_role = ?2) AND "
            + "request_status NOT IN ('Request')) GROUP BY approval.request_id) ORDER by request_detail.submitted_date DESC";
    
    @Query(value = FIND_REQUEST, nativeQuery = true)
    public List<Object[]> findListLeaveRequest(Integer nik);
    
    @Query(value = FIND_DATE, nativeQuery = true)
    public List<Object[]> findHistori(Integer id, Integer ids);

    @Query(value = "SELECT * FROM approval WHERE request_id like ?1% and status not in ('Resubmit','Request','Canceled','NotYet')", nativeQuery = true)
    public List<Object[]> listApprovalHistoryByIdReq(String keyword);

    @Query(value = "SELECT * FROM approval WHERE requestovertimedetail_id=?1", nativeQuery = true)
    public Iterable<Approval> findAppByReqOTDetailId(String requestovertimedetail_id);
    
    @Query(value = "SELECT * FROM approval WHERE id=?1", nativeQuery = true)
    public Approval findByIdApp(Integer id);
    
    @Query(value = "SELECT * FROM approval ap JOIN tr_requestovertimedetail rotd "
            + "ON ap.requestovertimedetail_id = rotd.OvertimeDetail JOIN tr_requestovertime ro "
            + "ON rotd.RequestOvertimeID = ro.RequestOvertimeID WHERE ro.employee_nik = ?1 "
            + "AND ap.id IN (SELECT max(app.id) FROM approval app GROUP BY app.requestovertimedetail_id) "
            + "ORDER BY rotd.CreateDate DESC", nativeQuery = true)
    public Iterable<Approval> findAppAllByNikGroup(Integer id);
}
