/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrEmployeeFormDetails;
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
public interface DocumentRepo extends CrudRepository<TbTrEmployeeFormDetails, Integer> {

    @Query(value = "SELECT detail.nik, detail.name AS detail_name ,detail.ktp, detail.position, detail.birth_place, detail.birth_date, detail.address,"
            + " detail.company, detail.Passport_number, ef.description, f.name, f.id, detail.join_date FROM tb_tr_employee_form_details "
            + "detail JOIN tb_tr_employee_form ef on ef.id = detail.employee_form JOIN tb_m_form f on f.id=ef.form WHERE ef.id=?1", nativeQuery = true)
    public List<Object[]> alldoc(String id);

    @Query(value = "SELECT\n"
            + "detail.nik,\n"
            + "detail.employee_form,\n"
            + "detail.name AS detail_name,\n"
            + "detail.company,\n"
            + "ef.created_date,\n"
            + "ef.created_by,\n"
            + "f.name,\n"
            + "ef.description,\n"
            + "s.value,\n"
            + "ef.approve_by\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "JOIN tb_m_form f on f.id=ef.form\n"
            + "JOIN tb_tr_approval_document_request ap on ap.employee_form=ef.id\n"
            + "JOIN tb_m_status s on s.id = ap.status\n"
            + "WHERE ef.status in (3,15,23) AND ef.approve_by=?1\n"
            + "AND ef.is_deteled=0 GROUP BY ef.id ORDER BY ef.created_date DESC", nativeQuery = true)
    public List<Object[]> allDocumentRequestRm(String id);

    @Query(value = "SELECT\n"
            + "detail.nik,\n"
            + "detail.employee_form,\n"
            + "detail.name AS detail_name,\n"
            + "detail.company,\n"
            + "ef.created_date,\n"
            + "f.name,\n"
            + "s.value,\n"
            + "ef.approve_by\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "JOIN tb_m_form f on f.id=ef.form\n"
            + "JOIN tb_tr_approval_document_request ap on ap.employee_form=ef.id\n"
            + "JOIN tb_m_status s on s.id = ap.status\n"
            + "WHERE ef.status = 4 AND ef.approve_by=?1\n"
            + "AND ef.is_deteled=0 GROUP BY ef.id ORDER BY ef.created_date DESC", nativeQuery = true)
    public List<Object[]> approvalHistoryPenugasanAdm(String id);

    @Query(value = "SELECT\n"
            + "ef.created_by,\n"
            + "detail.company,\n"
            + "f.name,\n"
            + "ef.form_number,\n"
            + "ef.created_date,\n"
            + "ef.modified_date,\n"
            + "s.value,\n"
            + "ef.id,\n"
            + "ef.approve_by\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "JOIN tb_m_form f on f.id=ef.form\n"
            + "JOIN tb_tr_approval_document_request ap on ap.employee_form=ef.id\n"
            + "JOIN tb_m_status s on s.id = ap.status\n"
            + "WHERE ef.status in (23,9,4) AND ef.approve_by=?1\n"
            + "GROUP BY ef.id ORDER BY ef.created_date DESC", nativeQuery = true)
    public List<Object[]> executiveSumPenugasan(String id);

    @Query(value = "SELECT\n"
            + "ef.form_number,\n"
            + "detail.nik,\n"
            + "detail.name AS detail_name, \n"
            + "detail.company,\n"
            + "f.name ,\n"
            + "ef.created_date,\n"
            + "ef.modified_date,\n"
            + "s.value,\n"
            + "ef.id,\n"
            + "ef.approve_by\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "JOIN tb_m_form f on f.id=ef.form\n"
            + "JOIN tb_tr_approval_document_request ap on ap.employee_form=ef.id\n"
            + "JOIN tb_m_status s on s.id = ap.status\n"
            + "WHERE ef.status in (23,9,4) AND ef.approve_by=?1\n"
            + "GROUP BY ef.id ORDER BY ef.created_date DESC", nativeQuery = true)
    public List<Object[]> executiveSummMSHR(String id);

//    @Query(value = "SELECT \n"
//            + "         detail.nik,\n"
//            + "         detail.name AS detail_names,\n"
//            + "          detail.ktp,\n"
//            + "           detail.position,\n"
//            + "        detail.birth_place,\n"
//            + "       detail.birth_date,\n"
//            + "          detail.address,\n"
//            + "       detail.company,\n"
//            + "        detail.Passport_number,\n"
//            + "        detail.join_date,\n"
//            + "            ef.description,\n"
//            + "            ef.description_by_admin,\n"
//            + "           ef.form_number,\n"
//            + "          ef.created_by\n"
//            + "          FROM tb_tr_employee_form_details detail\n"
//            + "          JOIN tb_tr_employee_form ef on ef.id = detail.employee_form \n"
//            + "        WHERE detail.employee_form=?1\n"
//            + "         ORDER BY detail.created_date DESC\n"
//            + "         LIMIT 1",
//            nativeQuery = true)
//    public List<Object[]> dataMSHR(String id);
    @Query(value = "SELECT \n"
            + "         detail.nik,\n"
            + "         detail.name AS detail_names,\n"
            + "          detail.ktp,\n"
            + "           detail.position,\n"
            + "        detail.birth_place,\n"
            + "       detail.birth_date,\n"
            + "          detail.address,\n"
            + "       detail.company,\n"
            + "        detail.Passport_number,\n"
            + "        detail.join_date,\n"
            + "            ef.description,\n"
            + "            ef.description_by_admin,\n"
            + "           ef.form_number,\n"
            + "          ef.created_by\n"
            + "          FROM tb_tr_employee_form_details detail\n"
            + "          JOIN tb_tr_employee_form ef on ef.id = detail.employee_form \n"
            + "        WHERE detail.employee_form=?1\n"
            + "         GROUP BY detail.employee_form",
            nativeQuery = true)
    public List<Object[]> dataMSHR(String id);

    @Query(value = "SELECT\n"
            + "ef.description_by_admin,\n"
            + "ef.form_number,\n"
            + "ef.start_date,\n"
            + "ef.end_date,\n"
            + "detail.company,\n"
            + "ef.created_by\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "WHERE detail.employee_form=?1\n"
            + "GROUP BY ef.id\n",
            nativeQuery = true)
    public List<Object[]> dataPenugasan(String id);

    @Query(value = "SELECT\n"
            + "detail.nik,\n"
            + "detail.name AS detail_names,\n"
            + "detail.ktp,\n"
            + "detail.position\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "WHERE detail.employee_form=?1",
            nativeQuery = true)
    public List<Object[]> dataKaryawanPenugasan(String id);

    @Query(value = "SELECT\n"
            + "detail.name,\n"
            + "detail.position,\n"
            + "detail.address,\n"
            + "detail.passport_number,\n"
            + "detail.join_date,\n"
            + "ef.form_number,\n"
            + "ef.description_by_admin\n"
            + "FROM tb_tr_employee_form_details detail\n"
            + "JOIN tb_tr_employee_form ef on ef.id = detail.employee_form\n"
            + "WHERE detail.employee_form=?1",
            nativeQuery = true)
    public List<Object[]> dataVisa(String id);
}
