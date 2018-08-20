package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class InterstitialAdActivity extends AppCompatActivity {

    private InterstitialAd interstitialAd;
    private Boolean notReady;

    private String postId;
    private ParseObject postObject;
    private FunctionBase functionBase;
    private ParseUser currentUser;

    private Boolean rewarded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial_ad);

        Intent intent = getIntent();
        postId = intent.getExtras().getString("postId");

        functionBase = new FunctionBase(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        rewarded = false;


        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject postOb, ParseException e) {

                if(e==null){

                    postObject = postOb;

                } else {

                    e.printStackTrace();
                }

            }

        });


        MobileAds.initialize(this, "ca-app-pub-8115892791740395~1186685064");

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-8115892791740395/4311179401");
        interstitialAd.loadAd(new AdRequest.Builder().build());

        Log.d("messsage", "start wait");

        interstitialAd.setAdListener(new AdListener(){

            @Override
            public void onAdClosed() {
                super.onAdClosed();

                Log.d("InterstitialAdAcitivity", "close");

                if(rewarded){

                    finish();

                } else {

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);
                    params.put("postId", postObject.getObjectId());
                    params.put("type", "close");


                    ParseCloud.callFunctionInBackground("ad_log_save", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("tag", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){

                                    TastyToast.makeText(getApplicationContext(), "작가님께 도움이 되어 주셔서 감사합니다!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    functionBase.artistPostIntentFunction(getApplicationContext(), postObject);

                                    finish();

                                } else {

                                    TastyToast.makeText(getApplicationContext(), "광고조회 기록 저장에 실패했습니다. 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    finish();
                                }

                            } else {

                                TastyToast.makeText(getApplicationContext(), "광고조회 기록 저장에 실패했습니다. 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                e.printStackTrace();
                                finish();

                            }
                        }
                    });

                }



            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);

                Log.d("InterstitialAdAcitivity", "fail");

                TastyToast.makeText(getApplicationContext(), "광고를 불러오는데 실패 했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                finish();

            }

            @Override
            public void onAdLeftApplication() {
                super.onAdLeftApplication();

                Log.d("InterstitialAdAcitivity", "left");



            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();

                Log.d("InterstitialAdAcitivity", "opened");
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();

                interstitialAd.show();

            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();

                Log.d("InterstitialAdAcitivity", "Click");

                if(rewarded){

                    TastyToast.makeText(getApplicationContext(), "이미 작가님에게 도움을 주셨어요! 감사합니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                } else {

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);
                    params.put("postId", postObject.getObjectId());
                    params.put("type", "click");


                    ParseCloud.callFunctionInBackground("ad_log_save", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("tag", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){

                                    TastyToast.makeText(getApplicationContext(), "작가님께 도움이 되어 주셔서 감사합니다!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    rewarded = true;

                                } else {

                                    TastyToast.makeText(getApplicationContext(), "광고조회 기록 저장에 실패했습니다. 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    finish();
                                }

                            } else {

                                TastyToast.makeText(getApplicationContext(), "광고조회 기록 저장에 실패했습니다. 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                e.printStackTrace();
                                finish();

                            }
                        }
                    });

                }




            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();

                Log.d("InterstitialAdAcitivity", "Impression");
            }
        });




    }


}
