/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrVaccine;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author RAR
 */
public interface TbTrVaccineRepo extends CrudRepository<TbTrVaccine, Integer> {

    @Query(value = "SELECT * FROM `tb_tr_vaccine` WHERE id = ?1", nativeQuery = true)
    TbTrVaccine findone(int id);

    @Modifying
    @Query(value = "UPDATE tb_tr_vaccine set CertificatePath=?,CertificateName=? WHERE id=?", nativeQuery = true)
    void updateVaccineFile(String path, String namefile, int VaccineId);

    @Query(value = "SELECT trVac.empl_nik,ri.name,vt.Vaccine_Name, dt.Doses_Name,trVac.Location,trVac.Date FROM tb_tr_vaccine trVac\n"
            + "Join requester_information ri on ri.nik = trVac.empl_nik\n"
            + "Join tb_m_vaccine_type vt on vt.id = trVac.VaccineType\n"
            + "join tb_m_doses_type dt on dt.id = trVac.DosesType\n"
            + "WHERE trVac.empl_nik = ?1", nativeQuery = true)
    public List<Object[]> getAllHistoryByNikEmp(Integer nik);

    @Query(value = "SELECT trvac.id,trvac.empl_nik, me.name, vt.Vaccine_Name,dt.Doses_Name,trvac.Date,so.so_id,cus.customer_name,trvac.Location FROM tb_tr_vaccine trvac\n"
            + "join tb_m_vaccine_type vt on vt.id = trvac.VaccineType\n"
            + "join tb_m_doses_type dt on dt.id = trvac.DosesType\n"
            + "INNER JOIN sakura_db.master_employee me ON me.empl_nik = trvac.empl_nik\n"
            + "INNER JOIN sakura_db.allocation al on al.empl_nik = trvac.empl_nik\n"
            + "INNER JOIN sakura_db.sales_order so ON so.so_id = al.so_id\n"
            + "INNER JOIN sakura_db.customers cus ON cus.customer_id = so.customer_id\n"
            + "WHERE allocation_id = ( SELECT max(allocation_id) FROM sakura_db.allocation AS a WHERE a.empl_nik = al.empl_nik )and trvac.id in (SELECT Max(id) FROM `tb_tr_vaccine` GROUP BY empl_nik) and trvac.RelationManager = ?1", nativeQuery = true)
    public List<Object[]> getUnderRm(Integer nik);

    @Query(value = "SELECT tv.id,tv.empl_nik,vt.Vaccine_Name,dt.Doses_Name,tv.Location,tv.Date FROM tb_tr_vaccine tv \n"
            + "join tb_m_vaccine_type vt on vt.id = tv.VaccineType\n"
            + "join tb_m_doses_type dt on dt.id = tv.DosesType", nativeQuery = true)
    public List<Object[]> getPreviousDose();

}
