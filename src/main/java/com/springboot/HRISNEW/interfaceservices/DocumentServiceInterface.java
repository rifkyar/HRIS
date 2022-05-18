/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMForm;
import com.springboot.HRISNEW.entities.TbMStatus;
import com.springboot.HRISNEW.entities.TbTrApprovalDocumentRequest;
import com.springboot.HRISNEW.entities.TbTrEmployeeForm;
import com.springboot.HRISNEW.entities.TbTrEmployeeFormDetails;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Dharta
 */
public interface DocumentServiceInterface {

    TbTrEmployeeFormDetails save(TbTrEmployeeFormDetails dr);

    TbTrEmployeeForm save(TbTrEmployeeForm ar);

    TbTrApprovalDocumentRequest save(TbTrApprovalDocumentRequest sd);

    Iterable<TbMForm> findAll();

    public List<Object[]> alldoc(String id);

    public List<Object[]> allDetailss(String id);

    public TbMForm FormTypeById(Integer id);

    public List<Object[]> pendingApprovalAdm(String id);

    public int Countrowbynik(int id);

    public List<Object[]> viewPendingApproval(String id);

    void saveTbTrEmp(String formnuumber, String descadmin, int status, Date modifieddate, String req);

    void saveTbTrAproval(int status, String req);

    void rejectTbTrEmp(String formnuumber, String descadmin, int status, Date modifieddate, String req);

    void rejectTbTrEmp(int status, String req);

    public List<Object[]> executiveSummMSHR(String id);

    void deletedhistory(String id);

    public List<Object[]> historyRM(String id);

    public List<Object[]> viewDetailRequesterRm(String id);

    public List<Object[]> allDocumentRequestRm(String id);

    public List<Object[]> executiveSumPenugasan(String id);

    void uploadFile(byte[] fileupload, Date modDate, String req);

    public byte[] findBlobFile(String id);

    void doneStatusUpload(int status, String req);

    public List<Object[]> dataMSHR(String id);

    public List<Object[]> dataPenugasan(String id);

    public List<Object[]> approvalHistoryAdm(String id);

    public List<Object[]> approvalHistoryPenugasanAdm(String id);

    public List<Object[]> dataKaryawanPenugasan(String id);

//    public List<Object[]> validasiEF(String id);
    public List<Object[]> findEmployeeForm(String id);

    public List<Object[]> dataVisa(String id);

    public String createdBy(String id);

    void updateDataResubmit(int status, String req);

    void updateDataEmpForm(int status, String req);
}
