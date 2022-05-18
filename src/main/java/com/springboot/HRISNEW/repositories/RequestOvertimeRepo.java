/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrRequestovertime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 */
@Repository
public interface RequestOvertimeRepo extends CrudRepository<TrRequestovertime, Integer> {

    @Query(value = "SELECT * from tr_requestovertime where RequestOvertimeID=?1", nativeQuery = true)
    public TrRequestovertime findRequesterOTbyid(Integer id);

    @Query(value = "SELECT * FROM tr_requestovertime ro join tr_requestovertimedetail rod ON ro.RequestOvertimeID = rod.RequestOvertimeID "
            + "where rod.PMorLeader is null and ro.RequestOvertimeID =?1", nativeQuery = true)
    public TrRequestovertime findNullPM(Integer id);

    @Query(value = "SELECT * FROM tr_requestovertime ro join tr_requestovertimedetail rod ON ro.RequestOvertimeID = rod.RequestOvertimeID "
            + "where rod.RM is null and ro.RequestOvertimeID =?1", nativeQuery = true)
    public TrRequestovertime findNullRM(Integer id);

    @Query(value = "SELECT * FROM tr_requestovertime ro join tr_requestovertimedetail rod ON ro.RequestOvertimeID = rod.RequestOvertimeID "
            + "where rod.MSFC is null and ro.RequestOvertimeID =?1", nativeQuery = true)
    public TrRequestovertime findNullMsfc(Integer id);

    @Query(value = "SELECT count(tr.overtimedetail) as pending FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%done%\" and year(tr.CreateDate) = ?1", nativeQuery = true)
    public int totalDoneOvertime(Integer year);

    @Query(value = "SELECT count(tr.overtimedetail) as reject FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%reject%\" and year(tr.CreateDate) = ?1", nativeQuery = true)
    public int totalRejectOvertime(Integer year);

    @Query(value = "SELECT count(tr.overtimedetail) as done FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%waiting%\" and year(tr.CreateDate) = ?1", nativeQuery = true)
    public int totalWaitingOvertime(Integer year);

    @Query(value = "SELECT count(tr.overtimedetail) as pending FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%done%\" AND month(tr.CreateDate)= ?1 and year(tr.CreateDate)= ?2", nativeQuery = true)
    public int doneOvertime(Integer month, Integer year);

    @Query(value = "SELECT count(tr.overtimedetail) as reject FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%reject%\" AND month(tr.CreateDate)= ?1 and year(tr.CreateDate)= ?2", nativeQuery = true)
    public int rejectOvertime(Integer month, Integer year);

//    @Query(value = "SELECT count(tr.overtimedetail) as done FROM `tr_requestovertimedetail` tr\n"
//            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
//            + "join sakura_db.sales_order so on so.so_id=ot.so_id\n"
//            + "JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
//            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%waiting%\" AND month(tr.CreateDate) = ?1 and year(tr.CreateDate)= ?2", nativeQuery = true)
//    public int waitingOvertime(Integer month, Integer year);
    @Query(value = "SELECT count(tr.overtimedetail) as done FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus = \"Waiting From MSFC\" AND month(tr.CreateDate) = ?1 and year(tr.CreateDate)= ?2", nativeQuery = true)
    public int waitingFromMSFCOvertime(Integer month, Integer year);

    @Query(value = "SELECT count(tr.overtimedetail) as done FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus = \"Waiting From RM\" AND month(tr.CreateDate) = ?1 and year(tr.CreateDate)= ?2", nativeQuery = true)
    public int waitingFromRMOvertime(Integer month, Integer year);

    @Query(value = "SELECT count(tr.overtimedetail) as done FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus = \"Waiting From PM\" AND month(tr.CreateDate) = ?1 and year(tr.CreateDate)= ?2", nativeQuery = true)
    public int waitingFromPMOvertime(Integer month, Integer year);

    @Query(value = "SELECT onee.jumlah as total ,onee.employeename,onee.employee_nik, thr.jumlah as done from \n"
            + "(SELECT count(tr.overtimedetail) as jumlah, ot.employeename, ot.employee_nik FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "WHERE tr.requeststatus != \"Canceled\" and month(tr.createdate)= ?1 and year(tr.createdate) = ?3\n"
            + "GROUP by ot.employee_nik\n"
            + "order by jumlah desc ) as onee\n"
            + "LEFT JOIN\n"
            + "(SELECT count(tr.overtimedetail) as jumlah, ot.employeename, ot.employee_nik FROM `tr_requestovertimedetail` tr\n"
            + "JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%done%\" and month(tr.createdate)= ?2 and year(tr.createdate) = ?4\n"
            + "GROUP by ot.employee_nik\n"
            + "order by jumlah desc ) as thr\n"
            + "on onee.employee_nik=thr.employee_nik ORDER BY thr.jumlah DESC LIMIT 10", nativeQuery = true)
    public List<Object[]> peopleTrendsOvertime(Integer month, Integer month2, Integer year, Integer year2);

    @Query(value = "select onee.so_id,onee.customer_name, firstt.done from\n"
            + "            (SELECT ot.so_id,cus.customer_name FROM `tr_requestovertimedetail` tr\n"
            + "            JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "            join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "            JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "            WHERE tr.requeststatus != \"Canceled\" and month(tr.CreateDate) = ?1 and year(tr.CreateDate) = ?2\n"
            + "            GROUP by ot.so_id) as onee\n"
            + "            left join\n"
            + "            (SELECT count(tr.overtimedetail) as done, ot.so_id FROM `tr_requestovertimedetail` tr\n"
            + "            JOIN tr_requestovertime ot on ot.RequestOvertimeID=tr.RequestOvertimeID\n"
            + "            join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "            JOIN sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "            WHERE tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%waiting%\"\n"
            + "            GROUP by ot.so_id) as firstt\n"
            + "            on onee.so_id = firstt.so_id ORDER BY firstt.done DESC LIMIT 10", nativeQuery = true)
    public List<Object[]> soTrendsOvertime(Integer month, Integer year);
    
    //Approver's Pending Overtime
    @Query(value = "select so.rm_name, us.name, count(tr.overtimedetail) as \"Pending Approval\" from tr_requestovertimedetail tr \n"
            + "            join tr_requestovertime ot on ot.requestovertimeid=tr.requestovertimeid\n"
            + "            join sakura_db.sales_order so on so.so_id=ot.so_id\n"
            + "            join sakura_db.user us on us.user_id = so.rm_name\n"
            + "            join sakura_db.customers cus on cus.customer_id = so.customer_id\n"
            + "            where tr.requeststatus != \"Canceled\" and tr.requeststatus like \"%waiting%\" and year(tr.CreateDate) = ?1\n"
            + "            group by so.rm_name", nativeQuery = true)
    public List<Object[]> OvertimeApproverPending(Integer year);

    //Approver's Pending Overtime Detail
    @Query(value = "Select rot.employee_nik, rot.employeename, rot.requestovertimeid, tr.StartDate, tr.EndDate, tr.StartTime, tr.EndTime, tr.Total, tr.DateType, tr.Task,cus.customer_name,rot.so_id\n" 
            + "             from tr_requestovertime rot \n" 
            + "             join tr_requestovertimedetail tr on rot.RequestOvertimeID = tr.RequestOvertimeID\n" 
            + "             join sakura_db.sales_order so on so.so_id=rot.so_id\n" 
            + "             join sakura_db.user us on us.user_id = so.rm_name\n" 
            + "             join sakura_db.customers cus on cus.customer_id = so.customer_id\n" 
            + "             where so.rm_name = ?1 "
            + "             and tr.requeststatus != \"Canceled\" "
            + "             and tr.requeststatus like \"%waiting%\" "
            + "             and year(tr.CreateDate) = ?2", nativeQuery = true)
    public List<Object[]> OvertimePendingDetail(Integer id, Integer year);


}
