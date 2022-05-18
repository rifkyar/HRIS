/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbMEmployee;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author USER
 */
@Repository
public interface EmployeeRepo extends CrudRepository<TbMEmployee, Integer> {

    @Query(value = "SELECT * FROM tb_tr_log_employee ttle JOIN tb_m_employee tme ON tme.empl_nik = ttle.empl_nik WHERE ttle.status = 'Waiting'", nativeQuery = true)
    public List<TbMEmployee> pendingChangeReq();

    @Query(value = "SELECT * FROM tb_tr_log_employee ttle JOIN tb_m_employee tme ON tme.empl_nik = ttle.empl_nik WHERE ttle.status = 'Waiting' AND ttle.empl_nik = ?1", nativeQuery = true)
    public List<TbMEmployee> selectPendingChangeReq(int nikPending);
}
