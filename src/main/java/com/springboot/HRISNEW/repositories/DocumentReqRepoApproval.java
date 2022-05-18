/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrApprovalDocumentRequest;
import java.util.Date;
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
public interface DocumentReqRepoApproval extends CrudRepository<TbTrApprovalDocumentRequest, Integer> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_tr_approval_document_request SET status=? WHERE employee_form =?", nativeQuery = true)
    void saveTbTrAproval(int status, String req);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_tr_approval_document_request SET status=? WHERE employee_form =?", nativeQuery = true)
    void rejectTbTrEmp(int status, String req);

    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_tr_approval_document_request SET status=? WHERE employee_form =?", nativeQuery = true)
    void doneStatusUpload(int status, String req);
    
    @Modifying
    @Transactional
    @Query(value = "UPDATE tb_tr_approval_document_request SET status=? WHERE employee_form =?", nativeQuery = true)
    void updateDataResubmit(int status, String req);
}
