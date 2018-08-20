package com.ap.fmcgr;

import android.app.Activity;
import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity{

    public WebView superWebView;
    public String user;
    public String grupa;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_activity);

        superWebView = (WebView) findViewById(R.id.myWebView);

        String url = "https://192.168.0.20:44371";
        superWebView.getSettings().setJavaScriptEnabled(true);
        superWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        superWebView.setWebViewClient(new MyWebViewClient());
        superWebView.loadUrl(url);
    }

    boolean kill = false;

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if(url.contains("Congrats"))
            {
                view.addJavascriptInterface(new WebAppInterface(WebViewActivity.this), "Android");
                view.loadUrl(url);
                kill = true;
//                finish();
            }
            else if(kill){
                finish();
            }
            else
                view.loadUrl(url);
            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }


    }

    private class WebAppInterface {
        Context mContext;
        String data;

        WebAppInterface(Context ctx){
            this.mContext =ctx;
        }

        @JavascriptInterface
        public void sendData(String data){
            this.data = data;
            Toast.makeText(mContext, "WEBINTERFACE" + data, Toast.LENGTH_LONG).show();
            user = data;
        }

    }

}