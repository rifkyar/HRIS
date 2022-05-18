/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrRequestovertime;
import com.springboot.HRISNEW.entities.TrSoovertime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 */
@Repository
public interface SOOvertimeRepo extends CrudRepository<TrSoovertime, String> {

    @Query(value = "SELECT so_id, CustomerName, RmName, RelationManager, OfficeHourID, PaidToStaffID, "
            + "MealsTransportStatus, ChargebackStatus, ChargerBackType, RateWeekday, RateWeekend FROM tr_soovertime", nativeQuery = true)
    public List<Object[]> soOtListAdm();

    @Query(value = "SELECT * FROM tr_soovertime WHERE so_id=?1", nativeQuery = true)
    public TrSoovertime selectbyId(String id);

    @Query(value = "SELECT rot.RequestOvertimeID, rot.employee_nik, rot.EmployeeName, rot.RequestStatus, rot.period, rot.CreateDate, soot.RmName, soot.RelationManager, rot.so_id\n"
            + "FROM tr_requestovertime rot JOIN tr_soovertime soot on rot.so_id = soot.so_id\n"
            + "JOIN tr_requestovertimedetail otd on rot.RequestOvertimeID = otd.RequestOvertimeID\n"
            + "WHERE soot.so_id =?1 and rot.RequestStatus = \"Waiting From Confirmation\" AND otd.RequestStatus != \"Canceled\"\n"
            + "AND otd.RequestStatus != \"Rejected by PM\" AND otd.RequestStatus != \"Rejected by RM\" AND otd.RequestStatus != \"Rejected by MSFC\" \n"
            + "GROUP by rot.RequestOvertimeID order by otd.RequestOvertimeID desc", nativeQuery = true)
    public List<Object[]> soOtListRequester(String id);

}