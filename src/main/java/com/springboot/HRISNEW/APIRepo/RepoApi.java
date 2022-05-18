
package com.springboot.HRISNEW.APIRepo;

import com.google.gson.JsonObject;
import com.springboot.HRISNEW.repositories.ParametersRepo;
import com.springboot.HRISNEW.util.OkHttpUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author HARRY-PC
 */
@RestController
public class RepoApi {

    @Autowired
    private ParametersRepo parametersRepo;

    public JSONObject readApi(String url) {
        JSONObject jObj = null;
        String pwd = null, mail = null, id = null;
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);

            Request request = new Request.Builder().url(url).get().addHeader("Authorization", parametersRepo.findApiById(3).getValue()).build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            JSONObject jsonobj = new JSONObject(response.body().string());
            JSONArray data = jsonobj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                jObj = data.getJSONObject(i);
            }
        } catch (Exception e) {
            System.out.println("Erorr is = " + e);
        }
        return jObj;
    }

    public List readListApi(String url) {
        List isi = new ArrayList();
        String pwd = null, mail = null, id = null;
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);

            Request request = new Request.Builder().url(url).get().addHeader("Authorization", parametersRepo.findApiById(3).getValue()).build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            JSONObject jsonobj = new JSONObject(response.body().string());
            JSONArray data = jsonobj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {

                isi.add(data.getJSONObject(i).getString("Date"));
            }
        } catch (Exception e) {
            System.out.println("Erorr is = " + e);
        }
        return isi;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public JSONObject resetPwdUser(String url, String nik, String pwd, String uuid) {
        JSONObject jo = null;
        JsonObject json = new JsonObject();
        json.addProperty("User_id", nik);
        json.addProperty("Password", pwd);
        json.addProperty("ResetToken", uuid);
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            String jsonString = json.toString();
            RequestBody body = RequestBody.create(JSON, jsonString);

            System.out.println(" body " + json.toString());
            System.out.println(" Repo API NIK = " + nik + " PWD " + pwd);

            Request request = new Request.Builder().url(url).put(body).addHeader("Authorization", parametersRepo.findApiById(3).getValue()).build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            jo = new JSONObject(response.body().string());
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            System.out.println("Erorr is = " + e);
        }
        return jo;
    }

    public JSONObject resetPwdEmpl(String url, String nik, String pwd, String uuid) {
        JSONObject jo = null;
        JsonObject json = new JsonObject();
        json.addProperty("Employee_Nik", nik);
        json.addProperty("Password", pwd);
        json.addProperty("ResetToken", uuid);
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            String jsonString = json.toString();
            RequestBody body = RequestBody.create(JSON, jsonString);

            System.out.println(" body " + json.toString());
            System.out.println(" Repo API NIK = " + nik + " PWD " + pwd);

            Request request = new Request.Builder().url(url).put(body).addHeader("Authorization", parametersRepo.findApiById(3).getValue()).build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            jo = new JSONObject(response.body().string());
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
        } catch (Exception e) {
            System.out.println("Erorr is = " + e);
        }
        return jo;
    }

    public JSONObject insertNewEmpLogin(String url, String nik, String pwd, String token) {
        JSONObject jsonobj = null;
        JsonObject json = new JsonObject();
        json.addProperty("Employee_Nik", nik);
        json.addProperty("Password", pwd);
        json.addProperty("Token", token);
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            String jsonString = json.toString();
            RequestBody body = RequestBody.create(JSON, jsonString);

            System.out.println(" body " + json.toString());
            System.out.println(" Repo API NIK = " + nik + " PWD " + pwd);

            Request request = new Request.Builder().url(url).post(body).addHeader("Authorization", parametersRepo.findApiById(3).getValue()).build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            jsonobj = new JSONObject(response.body().string());
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            System.out.println(response.body().string());
        } catch (Exception e) {
            System.out.println("Erorr is = " + e);
        }
        return jsonobj;
    }

    public List readListRmApi(String url) {
        List isi = new ArrayList();
        String pwd = null, mail = null, id = null;
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
    }

    public List listCustomer(String url) {
        List isi = new ArrayList();
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);
            
            Request request = new Request.Builder().url(url).get().addHeader("Authorization", parametersRepo.findApiById(3).getValue()).build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            JSONObject jsonobj = new JSONObject(response.body().string());
            JSONArray data = jsonobj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                isi.add(data.getJSONObject(i).getString("Customer_Name"));
            }
        } catch (Exception e) {
            System.out.println("Erorr is = " + e);
        }
        return isi;
    }

    public List readAdminAPI(String url) {
        List isi = new ArrayList();
        String pwd = null, mail = null, id = null;
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
    }

    public List readAutoCompleteAPI(String url) {
        List isi = new ArrayList();
        String pwd = null, mail = null, id = null;
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
    }

    public List employeeByNik(String url) {
        List isi = new ArrayList();
        String pwd = null, mail = null, id = null;
        try {
            OkHttpUtil okHttpUtil = new OkHttpUtil();
            okHttpUtil.init(true);

            Request request = new Request.Builder().url(url).get().addHeader("Authorization", parametersRepo.findApiById(3).getValue()).build();
            Response response = okHttpUtil.getClient().newCall(request).execute();
            JSONObject jsonobj = new JSONObject(response.body().string());
            JSONArray data = jsonobj.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {

                isi.add(data.getJSONObject(i).getString("Date"));
            }
        } catch (Exception e) {
            System.out.println("Erorr is = " + e);
        }
        return isi;
    }
    
    public List readAllUsers(String url) {
        List isi = new ArrayList();
        String pwd = null, mail = null, id = null;
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
    }
  
     public List getC(String url) {
        List isi = new ArrayList();
        String pwd = null, mail = null, id = null;
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
    }
     public List getUserInformation(String url) {
        List isi = new ArrayList();
        String pwd = null, mail = null, id = null;
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
    }
}