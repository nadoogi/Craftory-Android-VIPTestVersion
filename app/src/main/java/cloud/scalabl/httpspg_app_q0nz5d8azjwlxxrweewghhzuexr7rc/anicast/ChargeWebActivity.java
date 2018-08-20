package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.parse.ParseUser;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.InicisWebViewClient;

public class ChargeWebActivity extends Activity {

    WebView webView;
    ProgressDialog progressDialog;

    private final String APP_SCHEME = "anicast";

    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_web);

        ParseUser currentUser = ParseUser.getCurrentUser();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("로딩 중입니다...");
        progressDialog.setCancelable(false);


        webView = (WebView) findViewById(R.id.charge_web);
        webView.setWebViewClient(new InicisWebViewClient(this));
        //webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        /*
        webSettings.setNeedInitialFocus(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDisplayZoomControls(true);

        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            CookieManager cookieManager = CookieManager.getInstance();
            cookieManager.setAcceptCookie(true);
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }



        Log.i("ChargetWebActivity1", "2");

        Intent intent = getIntent();
        String amount = intent.getExtras().getString("amount");
        String type = intent.getExtras().getString("type");

        Uri intentData = intent.getData();

        if(intentData == null){

            Log.i("ChargetWebActivity3", "1");

            webView.loadUrl("https://pg-app-q0nz5d8azjwlxxrweewghhzuexr7rc.scalabl.cloud/iamport?amount=" + amount + "&type=" + type + "&email=" + currentUser.getUsername() + "&name=" + currentUser.getObjectId());

        } else {

            Log.i("ChargetWebActivity2", "3");

            String url = intentData.toString();
            if ( url.startsWith(APP_SCHEME) ) {
                String redirectURL = url.substring(APP_SCHEME.length()+3);
                webView.loadUrl(redirectURL);
            }

        }





    }



    @Override
    protected void onNewIntent(Intent intent) {


        String url = intent.toString();
        if ( url.startsWith(APP_SCHEME) ) {
            String redirectURL = url.substring(APP_SCHEME.length()+3);
            webView.loadUrl(redirectURL);
        }
    }



}
