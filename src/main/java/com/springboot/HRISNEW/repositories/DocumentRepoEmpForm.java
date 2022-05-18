/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrEmployeeForm;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Dharta
 */
@Repository
public interface DocumentRepoEmpForm extends CrudRepository<TbTrEmployeeForm, Integer> {

    @Query(value = "SELECT Empform.id , F.name, Detail.created_date, Strat.value FROM tb_tr_employee_form Empform "
            + "JOIN tb_tr_employee_form_details Detail ON Detail.employee_form = Empform.id JOIN tb_m_form F ON F.id = Empform.form "
            + "JOIN tb_m_status Strat ON Strat.id = Empform.status WHERE Detail.nik = ?1 AND create_by_nik = ?1 AND is_deteled = 0 "
            + "GROUP BY Empform.id ORDER BY Empform.created_date DESC", nativeQuery = true)
    public List<Object[]> allDetailss(String id);

    @Query(value = "SELECT detail.employee_form,detail.nik, detail.name AS detail_name, detail.company, f.name , ef.description, detail.created_date, s.value,ef.approve_by\n"
            + "     FROM tb_tr_employee_form_details detail\n"
            + "      JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "JOIN tb_m_form f on f.id=ef.form\n"
            + "     JOIN tb_tr_approval_document_request ap on ap.employee_form=ef.id\n"
            + "      JOIN tb_m_status s on s.id = ap.status\n"
            + "   WHERE ef.status in (3,23) AND ef.approve_by = ?1\n"
            + " AND ef.is_deteled=0 GROUP BY detail.employee_form ORDER BY detail.created_date DESC", nativeQuery = true)
    public List<Object[]> pendingApprovalAdm(String id);

    @Query(value = "SELECT detail.employee_form,detail.nik, detail.name AS detail_name, detail.company, f.name , ef.description, detail.created_date, s.value, ef.approve_by\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "JOIN tb_m_form f on f.id=ef.form\n"
            + "JOIN tb_tr_approval_document_request ap on ap.employee_form=ef.id\n"
            + "JOIN tb_m_status s on s.id = ap.status\n"
            + "WHERE ef.status = 4 AND ef.approve_by=?1 \n"
            + "AND ef.is_deteled=0 GROUP BY detail.employee_form ORDER BY detail.created_date DESC", nativeQuery = true)
    public List<Object[]> approvalHistoryAdm(String id);

    @Query(value = "SELECT COUNT(*) FROM `tb_tr_employee_form` WHERE create_by_nik=?1", nativeQuery = true)
    public int Countrowbynik(int id);

    @Query(value = "SELECT\n"
            + "f.id,\n"
            + "detail.nik,\n"
            + "detail.name AS detail_names,\n"
            + "detail.ktp,\n"
            + "detail.position,\n"
            + "detail.birth_place,\n"
            + "detail.birth_date,\n"
            + "detail.address,\n"
            + "detail.company,\n"
            + "detail.Passport_number,\n"
            + "detail.join_date,\n"
            + "ef.description,\n"
            + "ef.description_by_admin,\n"
            + "f.name,\n"
            + "ef.created_by,\n"
            + "ef.approve_by AS approve\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "JOIN tb_m_form f on f.id=ef.form\n"
            + "WHERE ef.id=?1", nativeQuery = true)
    public List<Object[]> viewPendingApproval(String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_tr_employee_form SET form_number=?, description_by_admin=?,status=?, modified_date=? WHERE id =?", nativeQuery = true)
    void saveTbTrEmp(String formnuumber, String descadmin, int status, Date modifieddate, String req);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_tr_employee_form SET form_number=?, description_by_admin=?,status=?, modified_date=? WHERE id =?", nativeQuery = true)
    void rejectTbTrEmp(String formnuumber, String descadmin, int status, Date modifieddate, String req);

    @Modifying
    @Query(value = "UPDATE tb_tr_employee_form SET is_deteled = 1 WHERE id = ?1", nativeQuery = true)
    @Transactional
    void deletedhistory(String id);

    @Query(value = "SELECT\n"
            + "Empform.id,\n"
            + "F.name,\n"
            + "Empform.created_date,\n"
            + "Stats.value\n"
            + "FROM tb_tr_employee_form Empform\n"
            + "JOIN tb_tr_employee_form_details Detail ON Detail.employee_form=Empform.id\n"
            + "JOIN tb_m_status Stats ON Stats.id=Empform.status\n"
            + "JOIN tb_m_form F on F.id=Empform.form\n"
            + "WHERE Empform.create_by_nik=?1 AND Empform.is_deteled=0\n"
            + "GROUP BY Empform.id ORDER BY Empform.created_date DESC", nativeQuery = true)
    public List<Object[]> historyRM(String id);

    @Query(value = "SELECT\n"
            + "detail.nik,\n"
            + "detail.name AS detail_name,\n"
            + "detail.ktp,\n"
            + "detail.position,\n"
            + "detail.company,\n"
            + "ef.description,\n"
            + "ef.approve_by,\n"
            + "f.name,\n"
            + "ef.start_date,\n"
            + "ef.end_date,\n"
            + "ef.description_by_admin,\n"
            + "ef.created_by\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "JOIN tb_m_form f on f.id=ef.form\n"
            + "WHERE ef.id=?1", nativeQuery = true)
    public List<Object[]> viewDetailRequesterRm(String id);

    @Query(value = "SELECT\n"
            + "form_number\n"
            + "FROM tb_tr_employee_form WHERE form_number =?1", nativeQuery = true)
    public List<Object[]> findEmployeeForm(String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_tr_employee_form SET file_upload=?, status=4, modified_date=? WHERE id =?", nativeQuery = true)
    void uploadFile(byte[] fileupload, Date modDate, String req);

    @Query(value = "SELECT file_upload FROM tb_tr_employee_form WHERE id = ?1 ", nativeQuery = true)
    public byte[] findBlobFile(String id);

    @Query(value = "SELECT created_by FROM `tb_tr_employee_form` WHERE id=?1", nativeQuery = true)
    public String createdBy(String id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_tr_employee_form SET status=? WHERE id =?", nativeQuery = true)
    void updateDataEmpForm(int status, String req);

}
