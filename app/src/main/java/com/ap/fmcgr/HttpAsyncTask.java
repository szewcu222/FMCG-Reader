package com.ap.fmcgr;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.CookieStore;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import javax.net.ssl.SSLSocketFactory;

public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    App globalVariable;

    public interface AsyncResponse {
        void processFinish(String output);
    }

    public AsyncResponse delegate = null;
    public Context context = null;

    public HttpAsyncTask(Context context, AsyncResponse delegate){
        this.delegate = delegate;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... dane) {

        String ret = send_Product(dane[0]);
        return ret;
    }



    public String send_Product(String zamowienie) {

        // not working CHECK in
        //  C:\Users\Damian\source\repos\MarketNFC\.vs\config
        //String connection = "http://192.168.0.20:58845/api/zamowienie";
        String connection = "http://marketnfc.azurewebsites.net/api/zamowienie";
        String content = "";
        InputStream is = null;

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(connection);

            httppost.setHeader(HTTP.CONTENT_TYPE, "application/json");

            HttpEntity zamowienieEntity = new StringEntity(zamowienie);
            httppost.setEntity(zamowienieEntity);


            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            content = convertInputStreamToString(is);

            httpclient.getConnectionManager().shutdown();

            JSONObject jsonObject = new JSONObject(content);
//            String error = jsonObject.getString("error");

        } catch (Exception e) {
            String error = e.toString();
            Toast.makeText(context, e.toString(), Toast.LENGTH_LONG);

        }
        return content;
    }


    public static JSONObject Products2JSON(List<ProductInfo> produktList, String userName, String grupaName) throws JSONException {



        JSONObject zam = new JSONObject();

        zam.put("dataZamowienia", "2018-08-09T15:23:15.361");
        zam.put("typZamowienia", 0);

        zam.put("lodowkaName", "debesciaki");

        JSONObject user = new JSONObject();
        user.put("userName", userName);

        JSONObject grupa = new JSONObject();
        grupa.put("nazwa", grupaName);
        JSONObject lodowka = new JSONObject();
        lodowka.put("grupa",grupa);

        zam.put("uzytkownik", user);
        zam.put("lodowka",lodowka);

        JSONArray produkty = new JSONArray();

        for (ProductInfo prod : produktList) {
            JSONObject produkt = new JSONObject();

            String UID = prod.getMapOfData().get("UID").toString();

            produkt.put("nazwa", prod.getProductName().toString());
            produkt.put("rfidTag", UID);
//            produkt.put("dataWaznosci", prod.getDateOfRead().getTime().toString());
            produkt.put("producent", prod.getManufacturerName().toString());
            produkt.put("globalnyNumerJednostkiHandlowej", prod.getGtinNumber().toString());
            produkt.put("numerPartiiProdukcyjnej",prod.getSerialNumber());
            produkt.put("cena", prod.getAmount());

            produkty.put(produkt);
        }
        zam.put("produkty", produkty);

        return zam;
    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    @Override
    protected void onPostExecute(String result){
        //super.onPostExecute(result);

        delegate.processFinish(result);

    }


}
