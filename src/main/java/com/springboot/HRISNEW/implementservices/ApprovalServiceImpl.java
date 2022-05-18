/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.Approval;
import com.springboot.HRISNEW.interfaceservices.ApprovalServiceInterface;
import com.springboot.HRISNEW.repositories.ApprovalRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class ApprovalServiceImpl implements ApprovalServiceInterface {

    @Autowired
    ApprovalRepo approvalRepo;

    @Override
    public Approval save(Approval ap) {
        return approvalRepo.save(ap);
    }

    @Override
    public Approval findAppByid(String id) {
        return approvalRepo.findAppByid(id);
    }

    @Override
    public List<Object[]> findListLeaveRequest(int nik) {
        return approvalRepo.findListLeaveRequest(nik);
    }

    @Override
    public List<Object[]> listApprovalHistoryByIdReq(String keyword) {
        return approvalRepo.listApprovalHistoryByIdReq(keyword);
    }

    @Override
    public Iterable<Approval> findAppByReqOTDetailId(String id) {
        return approvalRepo.findAppByReqOTDetailId(id);
    }

    @Override
    public Approval findbyid(Integer id) {
        return approvalRepo.findByIdApp(id);
    }

    @Override
    public Approval updateApproval(Approval app) {
        return approvalRepo.save(app);
    }

    @Override
    public Iterable<Approval> findAllAppGroup(Integer nik) {
        return approvalRepo.findAppAllByNikGroup(nik);
    }

    @Override
    public List<Object[]> findHistory(Integer id, int ids) {
        return approvalRepo.findHistori(id, ids);
    }

}
