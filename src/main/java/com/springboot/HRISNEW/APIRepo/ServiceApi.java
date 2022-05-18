/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springboot.HRISNEW.APIRepo;

import com.springboot.HRISNEW.entities.AdminAPI;
import com.springboot.HRISNEW.entities.SakuraAPI;
import com.springboot.HRISNEW.repositories.ParametersRepo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author HARRY-PC
 */
@Service
public class ServiceApi {

    @Autowired
    RepoApi repoApi;

    @Autowired
    ParametersRepo parametersRepo;
    
    int getId = 1;

    public JSONObject SELECTUSERBYEMAIL(String param) {
        return repoApi.readApi(parametersRepo.findById(getId).get().getValue() + "" + param);
    }

    public JSONObject SELECTEMPLBYEMAIL(String param) {
        return repoApi.readApi(parametersRepo.findById(getId).get().getValue() + "employee/" + param);
    }

    public JSONObject findEmployeeByEmail(String param) {
        return repoApi.readApi(parametersRepo.findById(getId).get().getValue() + "masterEmployee/" + param);
    }

    public JSONObject findStatusContract(String param) {
        String url = "https://sakura.msbuonline.com:8080/APISAKURA/CekKontrak/" + param;
        return repoApi.readApi(url);
    }

    public JSONObject statusKontrak(String param) {
        return repoApi.readApi(parametersRepo.findById(getId).get().getValue() + "contract/" + param);
    }

    public JSONObject firstContract(int param) {
        return repoApi.readApi(parametersRepo.findById(getId).get().getValue() + "contractnewleave/" + param);
    }

    public JSONObject lastKontrak(String email) {
        return repoApi.readApi(parametersRepo.findById(getId).get().getValue() + "CekKontrak/" + email);
    }

    public JSONObject kontrakOvertime(String email, String periode) {
        return repoApi.readApi(parametersRepo.findById(getId).get().getValue() + "CekOtKontrak/" + email + "/" + periode);
    }

    public JSONObject findEmpLoginById(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "emplLogin/" + param);
    }

    public JSONObject findUserByEmail(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "usere/" + param);
    }

    //reset pasword user
    public JSONObject resetPassUser(String nik, String pwd, String uuid) {
        return repoApi.resetPwdUser(parametersRepo.findApiById(getId).getValue() + "userResetPass/" + nik, nik, pwd, uuid);
    }

    //reset pasword employee
    public JSONObject resetPassEmployee(String nik, String pwd, String uuid) {
        return repoApi.resetPwdEmpl(parametersRepo.findApiById(getId).getValue() + "ResetEmplLogin/" + nik, nik, pwd, uuid);
    }

    //insert to employee login
    public JSONObject addEmployee(String nik, String pwd, String token) {
        return repoApi.insertNewEmpLogin(parametersRepo.findApiById(getId).getValue() + "insertEmplLogin", nik, pwd, token);
    }

    public JSONObject userbyToken(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "usernRT/" + param);
    }

    public JSONObject emplbyToken(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "emplLoginRT/" + param);
    }

    public JSONObject findEmpbyid(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "masterEmpl/" + param);
    }

    public JSONObject userbyid(Integer param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "user/" + param);
    }

    //Allocation
    public JSONObject findAllocationbyNik(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "allocation/" + param);
    }

    //Sales Order
    public JSONObject findSobyId(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "salesorder/" + param);
    }

    //Department
    public JSONObject findDeptbyId(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "department1/" + param);
    }

    //List RM
    public List listRm() {
        return repoApi.readListRmApi(parametersRepo.findApiById(getId).getValue() + "showRM/");
    }

    public List getRMById(String param) {
        return repoApi.readListRmApi(parametersRepo.findApiById(getId).getValue() + "getrmbyuserid/" + param);
    }

    //list admin
    public List listAdmin() {
        String url = "http://116.254.101.228:8080/APISAKURAJWT/getusermsadmin/";
        return repoApi.readListRmApi(url);
    }

    //Holiday
    public JSONObject jmlhJoinHoliday() {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "holidays/count/Joint Holiday");
    }

    public List publicHolidaydate() {
        return repoApi.readListApi(parametersRepo.findApiById(getId).getValue() + "holiday/Public Holiday");
    }

    public JSONObject findIncludeJoinById(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "salesjoin/" + param);
    }

    public List allHoliday() {
        return repoApi.readListApi(parametersRepo.findApiById(getId).getValue() + "AllHoliday");
    }
   public JSONObject GetUserInformation(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "getListUserInformation?nik=" + param);
    }
    //find all customer by nik
    public List findAllCustomerByNik(String param) {
        return repoApi.listCustomer(parametersRepo.findApiById(getId).getValue() + "showCustNamebynik/" + param);
    }

    //Customer
    public JSONObject findCustomerById(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "customer/" + param);
    }

    public JSONObject allowanceExist(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "allowancedetail/" + param);
    }

    public JSONObject admindata() {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "getusermsadmin");
    }

    public List listAutoComplete(String param) {
        return repoApi.readAutoCompleteAPI(parametersRepo.findApiById(getId).getValue() + "getListUserInformation?nik=" + param);
    }

    public List listGetUserInformation(String param) {
//        System.out.println("url; " + parametersRepo.findApiById(getId).getValue() + "getListUserInformation?nik=" + param);
        return repoApi.readListRmApi(parametersRepo.findApiById(getId).getValue() + "getListUserInformation?nik=" + param);
    }
    
    public  JSONObject EmployeeByNik(String param) {
        return repoApi.readApi(parametersRepo.findApiById(getId).getValue() + "getUserInformation?nik=" + param);
    }

    // comment by alex
//    public JSONObject GetUserInformation(String param) {
//        System.out.println("url; " + parametersRepo.findApiById(4).getValue()+ "getUserInformation?nik=" + param);
//        return repoApi.readApi(parametersRepo.findApiById(4).getValue() + "getUserInformation?nik=" + param);
//    }

    private String uri = "https://sakura.mii.co.id:8080/APISAKURAJWT/getUserInformation/";
//    private String uri = "http://116.254.101.228:8080/APISAKURAJWT/getUserInformation/";

    private String urirm = "https://sakura.mii.co.id:8080/APISAKURAJWT/APISAKURAJWT/listrm/";
//    private String urirm = "http://116.254.101.228:8080/APISAKURAJWT/listrm/";

//    private String uri = "http://116.254.101.228:8080/APISAKURAJWT/getListUserInformation/";
    private String uris = "https://sakura.mii.co.id:8080/APISAKURAJWT/getusermsadmin";
//    private String uris = "http://116.254.101.228:8080/APISAKURAJWT/getusermsadmin";

    private String key = "sakura eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzYWt1cmEiLCJpYXQiOjE1ODAxOTYxMzJ9.zYvu8-qr48lmTx7_3tZMmVmIBuGPVXmgHbFOwSdTAdYZO9FFQWa7rUeKodtfOMkzfnnjVQSl6f_t54_qvlo7cA";
//nned attention by alex:
//    http://116.254.101.228:8080/APISAKURAJWT/getUserInformation?nik=13860 
//    not equal to:
//    http://116.254.101.228:8080/APISAKURAJWT/getListUserInformation?nik=15402
    private static final RestTemplate restTemplate = new RestTemplate();

    private HttpHeaders getHeaders() {
        return new HttpHeaders() {
            {
                String authHeader = key;
                set("Authorization", authHeader);
                setContentType(MediaType.APPLICATION_JSON);

            }
        };
    }

//    public SakuraAPI EmployeeByNik(String id) {
//        HttpEntity<String> request = new HttpEntity<>(getHeaders());
//        Map<String, String> params = new HashMap<>();
//        params.put("id", id);
//
//        ResponseEntity<SakuraAPI> response = restTemplate.exchange(
//                uri + "?nik={id}",
//                HttpMethod.GET,
//                request,
//                new ParameterizedTypeReference<SakuraAPI>() {
//
//        },
//                params
//        );
//        SakuraAPI result = response.getBody();
//        return result;
//    }

    public AdminAPI AdminRM() {
        HttpEntity<AdminAPI> request = new HttpEntity<>(getHeaders());

        ResponseEntity<AdminAPI> response = restTemplate.exchange(
                uris,
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<AdminAPI>() {

        }
        );
        AdminAPI result = response.getBody();
        return result;
    }

    public List listAdminData() {
        return repoApi.readAdminAPI(parametersRepo.findApiById(getId).getValue() + "getusermsadmin");
    }

    public List employeeByNik(String param) {
        return repoApi.employeeByNik(parametersRepo.findApiById(getId).getValue() + "getListUserInformation?nik=" + param);
    }

    public List adminNik() {
        return repoApi.readAllUsers(parametersRepo.findApiById(getId).getValue() + "getallusers");
    }
    
    public List employeeBirthday() {
        return repoApi.readAllUsers(parametersRepo.findApiById(getId).getValue() + "getEmployeeBirthday");
    }}
