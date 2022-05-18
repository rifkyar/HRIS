/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.interfaceservices;

import com.springboot.HRISNEW.entities.TbMEmployeeTransportation;
import com.springboot.HRISNEW.entities.TbMParkingLocation;
import com.springboot.HRISNEW.entities.TbTrApprovalReimburse;
import com.springboot.HRISNEW.entities.TbTrReimburse;
import com.springboot.HRISNEW.entities.TbTrReimburseDetail;
import java.sql.Blob;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HARRY-PC
 */
public interface ParkingDetailServiceInterface {

    TbMEmployeeTransportation saveRegis(TbMEmployeeTransportation tb_transport);

    TbMParkingLocation saveParkingLocation(TbMParkingLocation tb_parklocation);

    public List<Object[]> findTransportByNik(int nik_requester);

    public List<Object[]> findParkLocByName(String parking_location);

    public List<Object[]> findLocationParkingById(int id);

    public List<Object[]> findLocationParkingByName(String name);

    public List<Object[]> findListParkingLocation();

    public List<Object[]> findTransportBynopol(String nik);

    public List<Object[]> findTransportBystnk(String stnk_no);

    public List<Object[]> findListDetailByApprovalForExcel(int approval_nik, String date_periode);

    public List<Object[]> findListDetailByNIKForExcel(int nik, String periode);

    public List findCustomerByNIK(String nik);

    public void deleteTransportByNoPol(String police_number);

    public void deleteParking(int id);

    public void updateParkingWOImage(String transportType, int stnk_name, Date modified, String id);

    public void updateREIMdetail_status(String reason, int status, int id, Date modified);

    public byte[] getFileByNopol(String police_number);

    public byte[] getReimById(int id_reim);

    //parking request 
    public List<Object[]> findListParkingReimbursementRequestByNik(Integer nik_requester);

    public List<Object[]> findListParkingReimbursementDetailRequestByNik(Integer nik_requester);

    public List<Object[]> findListParkingReimbursementDetailApprovalByNikAndStatus(Integer nik_requester, Integer status_id);

    public List<Object[]> findListParkingReimbursementDetailMFSCByNikAndStatus(Integer nik_requester, Integer status_id);

    public List<Object[]> findListParkingReimbursementDetailApprovalByNik(Integer nik_requester);

    public List<Object[]> findListParkingReimbursementDetaiMSFCByNik(Integer nik_requester);

    public List<Object[]> findListParkingReimbursementDetailRequestByREQID(String REQID);

    public List<Object[]> findListParkingReimbursementRequestByREQID(String REQID);

    TbTrReimburse saveParkingReimburse(TbTrReimburse tb_tr_reimburse);

    TbTrReimburseDetail saveParkingReimburseDetail(TbTrReimburseDetail tb_tr_detail);

    TbTrApprovalReimburse saveApprovalREIM(TbTrApprovalReimburse tb_tr_approval_reim);

    public void deleteReimDetailById(int id);

    public int findEmployeeTransport(String type_trans);

    public int getTotalPriceByREQID(String REQ_ID);

    public int getPriceREIMByID(int id_detail);

    public void changePriceByREQID(String REQ_ID, int Price);

    public List<Object[]> findListDetailByMSFCForExcel(int approval_nik, String date_periode);
}
