package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;


public class WebActivity extends AppCompatActivity {

    WebView webView;
    private static String youtubeId;
    ProgressDialog progressDialog;

    private static String contentId;
    private static String position;
    private static String type;
    private static String origin;

    private TextView back_button_text;
    private LinearLayout back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Intent intent = getIntent();

        type = intent.getExtras().getString("type");

        if(type.equals("youtube")){

            youtubeId = intent.getExtras().getString("youtubeId");
            contentId = intent.getExtras().getString("contentId");

        } else {

            origin = intent.getExtras().getString("origin");
            contentId = intent.getExtras().getString("contentId");
        }



        progressDialog = new ProgressDialog(WebActivity.this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("로딩 중입니다...");
        progressDialog.setCancelable(false);

        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button_text.setText("웹페이지 보기");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });



        webView = (WebView) findViewById(R.id.web_view);
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        WebSettings webSettings = webView.getSettings();
        webSettings.setNeedInitialFocus(true);
        webSettings.setJavaScriptEnabled(true);
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


        webView.setWebViewClient(new WebViewClient(){


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                Log.d("msg1", "start");
                progressDialog.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                Log.d("msg2", "finish");
                progressDialog.hide();

            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);



                Log.d("progressinwebacitivity", String.valueOf(newProgress));


            }
        });

        if(type.equals("youtube")){

            webView.loadUrl("https://www.youtube.com/watch?v=" + youtubeId );

        } else {

            webView.loadUrl(origin);
        }






    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){


            switch (type){

                case "youtube":

                    Intent intent = new Intent(getApplicationContext(), YoutubeActivity.class);

                    intent.putExtra("cardId", contentId);
                    //intent.putExtra("position", position);

                    startActivity(intent);


                    WebActivity.this.finish();

                    break;

                case "photo":

                    Intent intent1 = new Intent(getApplicationContext(), PhotoContentsActivity.class);

                    intent1.putExtra("cardId", contentId);
                    //intent.putExtra("position", position);

                    startActivity(intent1);


                    WebActivity.this.finish();


                    break;


                case "gif":

                    Intent intent2 = new Intent(getApplicationContext(), GifNativeActivity.class);

                    intent2.putExtra("cardId", contentId);
                    //intent.putExtra("position", position);

                    startActivity(intent2);


                    WebActivity.this.finish();


                    break;


            }



        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        switch (type){

            case "youtube":

                Intent intent = new Intent(getApplicationContext(), YoutubeActivity.class);

                intent.putExtra("cardId", contentId);
                //intent.putExtra("position", position);

                startActivity(intent);


                WebActivity.this.finish();

                break;

            case "photo":

                Intent intent1 = new Intent(getApplicationContext(), PhotoContentsActivity.class);

                intent1.putExtra("cardId", contentId);
                //intent.putExtra("position", position);

                startActivity(intent1);


                WebActivity.this.finish();


                break;


            case "gif":

                Intent intent2 = new Intent(getApplicationContext(), GifNativeActivity.class);

                intent2.putExtra("cardId", contentId);
                //intent.putExtra("position", position);

                startActivity(intent2);


                WebActivity.this.finish();


                break;


        }


    }
}
