/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.Approval;
import java.util.List;

/**
 *
 * @author HARRY-PC
 */
public interface ApprovalServiceInterface {

    Approval save(Approval ap);

    Approval findAppByid(String id);

    List<Object[]> findListLeaveRequest(int nik);

    List<Object[]> listApprovalHistoryByIdReq(String keyword);
    
    List<Object[]> findHistory(Integer id, int ids);

    Iterable<Approval> findAppByReqOTDetailId(String requestovertimedetail_id);

    Approval findbyid(Integer id);

    Approval updateApproval(Approval app);
    
    Iterable<Approval> findAllAppGroup(Integer nik);
}
