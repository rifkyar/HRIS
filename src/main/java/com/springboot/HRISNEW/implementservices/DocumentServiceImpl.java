/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMForm;
import com.springboot.HRISNEW.entities.TbMStatus;
import com.springboot.HRISNEW.entities.TbTrApprovalDocumentRequest;
import com.springboot.HRISNEW.entities.TbTrEmployeeForm;
import com.springboot.HRISNEW.entities.TbTrEmployeeFormDetails;
import com.springboot.HRISNEW.interfaceservices.DocumentServiceInterface;
import com.springboot.HRISNEW.repositories.DocumentDeleteRepo;
import com.springboot.HRISNEW.repositories.DocumentRepo;
import com.springboot.HRISNEW.repositories.DocumentRepoEmpForm;
import com.springboot.HRISNEW.repositories.DocumentRepoFormMaster;
import com.springboot.HRISNEW.repositories.DocumentReqRepoApproval;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Dharta
 */
@Service
public class DocumentServiceImpl implements DocumentServiceInterface {

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    DocumentRepoFormMaster documentRepoForm;

    @Autowired
    DocumentRepoEmpForm documentRepoEmpForm;

    @Autowired
    DocumentReqRepoApproval documentRepoEmpApproval;

    @Autowired
    DocumentDeleteRepo documentDelete;

    @Override
    public TbTrEmployeeFormDetails save(TbTrEmployeeFormDetails dr) {
        return documentRepo.save(dr);
    }

    @Override
    public Iterable<TbMForm> findAll() {
        return documentRepoForm.findAll();
    }

    @Override
    public TbTrEmployeeForm save(TbTrEmployeeForm ar) {
        return documentRepoEmpForm.save(ar);
    }

    @Override
    public TbTrApprovalDocumentRequest save(TbTrApprovalDocumentRequest sd) {
        return documentRepoEmpApproval.save(sd);
    }

    @Override
    public List<Object[]> allDetailss(String id) {
        return documentRepoEmpForm.allDetailss(id);
    }

    @Override
    public List<Object[]> alldoc(String id) {
        return documentRepo.alldoc(id);
    }

    @Override
    public TbMForm FormTypeById(Integer id) {
        return documentRepoForm.FormTypeById(id);
    }

    @Override
    public List<Object[]> pendingApprovalAdm(String id) {
        return documentRepoEmpForm.pendingApprovalAdm(id);
    }

    @Override
    public int Countrowbynik(int id) {
        return documentRepoEmpForm.Countrowbynik(id);
    }

    @Override
    public List<Object[]> viewPendingApproval(String id) {
        return documentRepoEmpForm.viewPendingApproval(id);
    }

    @Override
    public void saveTbTrEmp(String formnuumber, String descadmin, int status, Date modifieddate, String req) {
        documentRepoEmpForm.saveTbTrEmp(formnuumber, descadmin, status, modifieddate, req);
    }

    @Override
    public void rejectTbTrEmp(int status, String req) {
        documentRepoEmpApproval.rejectTbTrEmp(status, req);
    }

    @Override
    public List<Object[]> executiveSummMSHR(String id) {
        return documentRepo.executiveSummMSHR(id);
    }

    @Override
    public void deletedhistory(String id) {
        documentRepoEmpForm.deletedhistory(id);
    }

    @Override
    public List<Object[]> historyRM(String id) {
        return documentRepoEmpForm.historyRM(id);
    }

    @Override
    public List<Object[]> viewDetailRequesterRm(String id) {
        return documentRepoEmpForm.viewDetailRequesterRm(id);
    }

    @Override
    public List<Object[]> allDocumentRequestRm(String id) {
        return documentRepo.allDocumentRequestRm(id);
    }

    @Override
    public List<Object[]> executiveSumPenugasan(String id) {
        return documentRepo.executiveSumPenugasan(id);
    }

    @Override
    public byte[] findBlobFile(String id) {
        return documentRepoEmpForm.findBlobFile(id);
    }

    @Override
    public void doneStatusUpload(int status, String req) {
        documentRepoEmpApproval.doneStatusUpload(status, req);
    }

    @Override
    public void saveTbTrAproval(int status, String req) {
        documentRepoEmpApproval.saveTbTrAproval(status, req);
    }

    @Override
    public void rejectTbTrEmp(String formnuumber, String descadmin, int status, Date modifieddate, String req) {
        documentRepoEmpForm.rejectTbTrEmp(formnuumber, descadmin, status, modifieddate, req);
    }

//    @Override
//    public void reSubmitrm(String req) {
//        documentRepo.reSubmitrm(req);
//    }
    @Override
    public void uploadFile(byte[] fileupload, Date modDate, String req) {
        documentRepoEmpForm.uploadFile(fileupload, modDate, req);
    }

    @Override
    public List<Object[]> dataMSHR(String id) {
        return documentRepo.dataMSHR(id);
    }

    @Override
    public List<Object[]> dataPenugasan(String id) {
        return documentRepo.dataPenugasan(id);
    }

    @Override
    public List<Object[]> approvalHistoryAdm(String id) {
        return documentRepoEmpForm.approvalHistoryAdm(id);
    }

    @Override
    public List<Object[]> approvalHistoryPenugasanAdm(String id) {
        return documentRepo.approvalHistoryPenugasanAdm(id);
    }

    @Override
    public List<Object[]> dataKaryawanPenugasan(String id) {
        return documentRepo.dataKaryawanPenugasan(id);
    }

//    @Override
//    public List<Object[]> validasiEF(String id) {
//        return documentRepoEmpForm.validasiEF(id);
//    }
    @Override
    public List<Object[]> findEmployeeForm(String id) {
        return documentRepoEmpForm.findEmployeeForm(id);
    }

    @Override
    public List<Object[]> dataVisa(String id) {
        return documentRepo.dataVisa(id);
    }

    @Override
    public String createdBy(String id) {
        return documentRepoEmpForm.createdBy(id);
    }

    @Override
    public void updateDataResubmit(int status, String req) {
        documentRepoEmpApproval.updateDataResubmit(status, req);
    }

    @Override
    public void updateDataEmpForm(int status, String req) {
        documentRepoEmpForm.updateDataEmpForm(status, req);
    }

}
