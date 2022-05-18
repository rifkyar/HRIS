/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.repositories;

import com.springboot.HRISNEW.entities.TbTrApprovalDocumentRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Dharta
 */
public interface DocumentDeleteRepo extends CrudRepository<TbTrApprovalDocumentRequest, Integer>{
    @Query(value = "DELETE FROM tb_tr_approval_document_request WHERE employee_form = ?1", nativeQuery = true)
    void deletebyString (String id);
}
