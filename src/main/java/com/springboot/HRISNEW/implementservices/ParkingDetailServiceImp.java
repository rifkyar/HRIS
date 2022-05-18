/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.implementservices;

import com.springboot.HRISNEW.entities.TbMEmployeeTransportation;
import com.springboot.HRISNEW.entities.TbMParkingLocation;
import com.springboot.HRISNEW.entities.TbTrApprovalReimburse;
import com.springboot.HRISNEW.entities.TbTrReimburse;
import com.springboot.HRISNEW.entities.TbTrReimburseDetail;
import com.springboot.HRISNEW.interfaceservices.ParkingDetailServiceInterface;
import com.springboot.HRISNEW.repositories.ParkingDetailRepo;
import com.springboot.HRISNEW.repositories.ParametersRepo;
import com.springboot.HRISNEW.repositories.ParkingApprovalRepo;
import com.springboot.HRISNEW.repositories.ParkingLocationRepo;
import com.springboot.HRISNEW.repositories.ReimburseDetailParkingRepo;
import com.springboot.HRISNEW.repositories.ReimburseParkingRepo;
import com.springboot.HRISNEW.util.OkHttpUtil;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author HARRY-PC
 */
@Service
public class ParkingDetailServiceImp implements ParkingDetailServiceInterface {

    @Autowired
    private ParkingDetailRepo parkingRepo;
    @Autowired
    private ParkingLocationRepo parkingLocationRepo;
    @Autowired
    private ParametersRepo parametersRepo;
    @Autowired
    private ReimburseParkingRepo reimburseParkingRepo;
    @Autowired
    private ReimburseDetailParkingRepo reimburseDetailParkingRepo;
    @Autowired
    private ParkingApprovalRepo REIMApprovalRepo;

    @Override
    public TbMEmployeeTransportation saveRegis(TbMEmployeeTransportation tb_transport) {
        return parkingRepo.save(tb_transport);
    }

    @Override
    public List<Object[]> findTransportByNik(int nik) {
        return parkingRepo.findListTransportByNik(nik);
    }

    @Override
    public List<Object[]> findTransportBynopol(String police_number) {
        return parkingRepo.findTransportBynopol(police_number);
    }
    @Override
    public List<Object[]> findParkLocByName(String parking_location) {
        return parkingRepo.findParkLocByName(parking_location);
    }

    @Override
    public List<Object[]> findTransportBystnk(String stnk_no) {
        return parkingRepo.findTransportBystnkno(stnk_no);
    }

    @Override
    public void deleteTransportByNoPol(String police_number) {
        parkingRepo.deleteTransport(police_number);
    }

    @Override
    public byte[] getFileByNopol(String police_number) {
        return parkingRepo.findBlobFile(police_number);
    }

    @Override
    public byte[] getReimById(int id_reim) {
        return parkingRepo.findBlobFileDetailReimByid(id_reim);
    }

    @Override
    public TbMParkingLocation saveParkingLocation(TbMParkingLocation tb_parklocation) {
        System.out.println("data:" + tb_parklocation);
        return parkingLocationRepo.save(tb_parklocation);
    }

    @Override
    public List<Object[]> findListParkingLocation() {
        return parkingLocationRepo.findListLocationParking();
    }

    @Override
    public void deleteParking(int id) {
        parkingLocationRepo.deleteParking(id);
    }

    @Override
    public List<Object[]> findLocationParkingById(int id) {
        return parkingLocationRepo.findLocationParkingById(id);
    }

    @Override
    public void updateParkingWOImage(String transportType, int stnk_name, Date modified, String id) {
        parkingLocationRepo.updateParkingWOImage(transportType, stnk_name, modified, id);
    }

    @Override
    public List<Object[]> findLocationParkingByName(String name) {
        return parkingLocationRepo.findLocationParkingByName(name);
    }

    @Override
    public List findCustomerByNIK(String nik) {
        String url = "http://116.254.101.228:8080/APISAKURAJWT/getListUserInformation?nik=" + nik;
        List isi = new ArrayList();
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);

            Request request = new Request.Builder().url(url).get().addHeader("Authorization", parametersRepo.findApiById(3).getValue()).build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            JSONObject jsonobj = new JSONObject(response.body().string());
            JSONArray data = jsonobj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                isi.add(data.getJSONObject(i));
            }
        } catch (Exception e) {
            System.out.println("Erorr is = " + e);
        }
        return isi;
//        return parkingRepo.readAutoCompleteAPI(url);
    }

    @Override
    public List<Object[]> findListParkingReimbursementRequestByNik(Integer nik_requester) {
        return reimburseParkingRepo.findListParkingReimbursementRequestByNik(nik_requester);
    }

    @Override
    public TbTrReimburse saveParkingReimburse(TbTrReimburse tb_tr_reimburse) {
        return reimburseParkingRepo.save(tb_tr_reimburse);
    }

    @Override
    public TbTrReimburseDetail saveParkingReimburseDetail(TbTrReimburseDetail tb_tr_detail) {
        return reimburseDetailParkingRepo.save(tb_tr_detail);
    }

    @Override
    public List<Object[]> findListParkingReimbursementDetailRequestByNik(Integer nik_requester) {
        return reimburseParkingRepo.findListParkingReimbursementDetailRequestByNik(nik_requester);
    }

    @Override
    public void deleteReimDetailById(int id) {
        reimburseDetailParkingRepo.deleteDetailParkingById(id);
    }

    @Override
    public List<Object[]> findListParkingReimbursementDetailRequestByREQID(String REQID) {
        return reimburseDetailParkingRepo.findListDetailReimburseByREQID(REQID);
    }

    @Override
    public List<Object[]> findListParkingReimbursementRequestByREQID(String REQID) {
        return reimburseParkingRepo.findListParkingReimbursementRequestREQID(REQID);
    }

    @Override
    public int findEmployeeTransport(String type_trans) {
        return parkingRepo.findListCar(type_trans);
    }

    @Override
    public List<Object[]> findListParkingReimbursementDetailApprovalByNikAndStatus(Integer nik_requester, Integer status_id) {
        return reimburseParkingRepo.findListParkingReimbursementDetailApprovalByNikAndStatus(nik_requester, status_id);
    }

    @Override
    public List<Object[]> findListParkingReimbursementDetailApprovalByNik(Integer nik_requester) {
        return reimburseParkingRepo.findListParkingReimbursementDetailApprovalByNik(nik_requester);
    }

    @Override
    public void updateREIMdetail_status(String reason, int status, int id, Date modified) {
        reimburseDetailParkingRepo.updateREIMDetailStatus(reason, status, modified, id);
    }

    @Override
    public TbTrApprovalReimburse saveApprovalREIM(TbTrApprovalReimburse tb_tr_approval_reim) {
        return REIMApprovalRepo.save(tb_tr_approval_reim);
    }

    @Override
    public List<Object[]> findListDetailByNIKForExcel(int nik, String periode) {
        return reimburseParkingRepo.findListDetailByNIKForExcel(nik, periode);
    }

    @Override
    public List<Object[]> findListDetailByApprovalForExcel(int approval_nik, String date_periode) {
        return reimburseParkingRepo.findListDetailByApprovalNIKForExcel(approval_nik, date_periode);
    }
    @Override
    public List<Object[]> findListDetailByMSFCForExcel(int approval_nik, String date_periode) {
        return reimburseParkingRepo.findListDetailByMSFCNIKForExcel(approval_nik, date_periode);
    }

    @Override
    public int getTotalPriceByREQID(String REQ_ID) {
        return parkingRepo.getTotalByREQ(REQ_ID);
    }

    @Override
    public int getPriceREIMByID(int id_detail) {
        return parkingRepo.getPriceREIMByID(id_detail);
    }

    @Override
    public void changePriceByREQID(String REQ_ID, int Price) {
        parkingRepo.changePriceByREQID(REQ_ID, Price);
    }

    @Override
    public List<Object[]> findListParkingReimbursementDetailMFSCByNikAndStatus(Integer nik_requester, Integer status_id) {
        return reimburseParkingRepo.findListParkingReimbursementDetailMFSCByNikAndStatus(nik_requester, status_id);
    }

    @Override
    public List<Object[]> findListParkingReimbursementDetaiMSFCByNik(Integer nik_requester) {
        return reimburseParkingRepo.findListParkingReimbursementDetaiMSFCByNik(nik_requester);
    }

}
