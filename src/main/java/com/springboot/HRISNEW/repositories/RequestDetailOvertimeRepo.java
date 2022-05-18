/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TrRequestovertimedetail;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author HARRY-PC
 */
@Repository
public interface RequestDetailOvertimeRepo extends CrudRepository<TrRequestovertimedetail, Integer> {

    @Query(value = "SELECT * FROM tr_requestovertimedetail WHERE (StartDate BETWEEN CURDATE() - INTERVAL 2 MONTH  AND CURDATE() AND CreateBy = ?1) "
            + "AND RequestStatus NOT IN ('Canceled','Rejected%') GROUP BY StartDate ORDER BY tr_requestovertimedetail.CreateDate ASC", nativeQuery = true)
    public List<TrRequestovertimedetail> checkRequestLastMonth(String nik);

    @Query(value = "SELECT * FROM tr_requestovertimedetail trq JOIN tr_requestovertime rq on rq.RequestOvertimeID= trq.RequestOvertimeID JOIN tr_soovertime so on so.so_id=rq.so_id WHERE trq.MSFC IS NULL or trq.RM IS NULL or trq.PMorLeader IS NULL GROUP by rq.so_id", nativeQuery = true)
    public List<TrRequestovertimedetail> pendding();

    @Query(value = "SELECT * FROM tr_requestovertimedetail WHERE RequestOvertimeID=?1 order by OvertimeDetail desc", nativeQuery = true)
    public Iterable<TrRequestovertimedetail> findDetailbyId(String id);

    @Query(value = "SELECT * FROM tr_requestovertimedetail WHERE OvertimeDetail =?1", nativeQuery = true)
    public TrRequestovertimedetail findOnebyId(String id);

    @Query(value = "SELECT count(*) FROM tr_requestovertimedetail where RequestOvertimeID=?1", nativeQuery = true)
    public int countRequestOTbyOTid(String otid);

    @Query(value = "SELECT count(*) FROM tr_requestovertimedetail where RequestOvertimeID=?1 and RequestStatus in ('Canceled','done','Rejected','Rejected by PM','Rejected by RM','Rejected by MSFC')", nativeQuery = true)
    public int countRequestOTStatusbyOTid(String otid);

    @Query(value = "SELECT * FROM tr_requestovertimedetail r JOIN tr_requestovertime s on r.RequestOvertimeID = s.RequestOvertimeID WHERE s.period like ?1% ORDER by r.StartDate", nativeQuery = true)
    public List<TrRequestovertimedetail> showAllBySoidAndPeriode(String periode);

    @Query(value = "SELECT * FROM tr_requestovertimedetail r JOIN tr_requestovertime s on r.RequestOvertimeID = s.RequestOvertimeID WHERE (s.so_id = ?1 AND s.period like ?2%) AND (r.RequestStatus like 'Done%'  or  r.RequestStatus like 'Waiting From%') ORDER by r.StartDate", nativeQuery = true)
    public List<TrRequestovertimedetail> showBySoidAndPeriode(String soid, String periode);
//old query //sama dengan yang ada untuk jesper reportnya
//    @Query(value = "SELECT * FROM tr_requestovertimedetail r JOIN tr_requestovertime s on r.RequestOvertimeID = s.RequestOvertimeID WHERE s.employee_nik = ?1 AND s.period like ?2% AND r.RequestStatus like 'Done%'", nativeQuery = true)
    //Baru
    @Query(value = "SELECT * FROM tr_requestovertimedetail r \n"
            + "JOIN tr_requestovertime s on r.RequestOvertimeID = s.RequestOvertimeID \n"
            + "WHERE s.employee_nik IN (SELECT nik FROM requester_information WHERE source_nik IN (SELECT source_nik FROM requester_information WHERE nik = ?1))\n"
            + "AND s.period like ?2%\n"
            + "AND r.RequestStatus like 'Done%'", nativeQuery = true)
    public List<TrRequestovertimedetail> showBynikAndPeriode(String nik, String periode);
}
