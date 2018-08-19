package com.ap.fmcgr;

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

    @Override
    protected String doInBackground(String... dane) {
        String strURL = "http://localhost:58845/home/test";


        String ret = send_Product(dane[0]);
        return ret;
    }

    public String send_Product(String zamowienie) {

//        try{
//            String strURL = "http://localhost:58845/api/zamowienie";
//
//            URL url = new URL(strURL);
//        }
//        catch (Exception e)
//        {
//            System.out.println(e);
//        }
        String connection = "http://192.168.0.20:44371/api/zamowienie";
        String content = "";

        InputStream is = null;

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

//        ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("dupa", "Dupa"));
        nameValuePairs.add(new BasicNameValuePair("cycki", "cycki"));

        String testowe = "ANDROIDTEST";

        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost(connection);
//            HttpGet httpget = new HttpGet(connection);

            httppost.setHeader(HTTP.CONTENT_TYPE, "application/json");

//            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));


            HttpEntity zamowienieEntity = new StringEntity(zamowienie);
            httppost.setEntity(zamowienieEntity);

//            HttpResponse resp = httpclient.execute(httpget);

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            content = convertInputStreamToString(is);


            httpclient.getConnectionManager().shutdown();


            JSONObject jsonObject = new JSONObject(content);
            String error = jsonObject.getString("error");

//            if (!error.equals("ok")) {
//                Toast.makeText(globalVariable.getBaseContext(), R.string.error_marker, Toast.LENGTH_LONG).show();
//                globalVariable.setStatusMarker(false);
//            } else {
//                globalVariable.setStatusMarker(true);
//            }
//            Log.d("HTTP", "content Marker: " + content);
        } catch (Exception e) {
            String error = e.toString();
//            ("HTTP", "Error in http connection " + e.toString());
        }
        return content;
    }


    public JSONObject Products2JSON(List<ProductInfo> produktList) throws JSONException {
        JSONObject zam = new JSONObject();

        zam.put("dataZamowienia", "2018-08-09T15:23:15.361");
        zam.put("typZamowienia", 0);
        zam.put("uzytkownikName", "JACEK12345@JACEK.COM");
        zam.put("lodowkaName", "debesciaki");

        JSONArray produkty = new JSONArray();

        for (ProductInfo prod :
                produktList) {
            JSONObject produkt = new JSONObject();

            String UID = prod.getMapOfData().get("UID").toString();

            produkt.put("nazwa", prod.getProductName().toString());
            produkt.put("rfidTag", UID);
//            produkt.put("dataWaznosci", prod.getDateOfRead().getTime().toString());
            produkt.put("producent", prod.getManufacturerName().toString());
            produkt.put("globalnyNumerJednostkiHandlowej", 500);
            produkt.put("numerPartiiProdukcyjnej",20);
            produkt.put("cena", prod.getAmount());

            produkty.put(produkt);
        }
        zam.put("produkty", produkty);

        return zam;
    }


//    public HttpClient getNewHttpClient() {
//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//
//            SSLSocketFactory sf = new SSLSocketFactory(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//            HttpParams params = new BasicHttpParams();
//            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//
//            SchemeRegistry registry = new SchemeRegistry();
//            // registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            registry.register(new Scheme("https", sf, 443));
//
//            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
//
//            return new DefaultHttpClient(ccm, params);
//        } catch (Exception e) {
//            return new DefaultHttpClient();
//        }
//    }



    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }



}
