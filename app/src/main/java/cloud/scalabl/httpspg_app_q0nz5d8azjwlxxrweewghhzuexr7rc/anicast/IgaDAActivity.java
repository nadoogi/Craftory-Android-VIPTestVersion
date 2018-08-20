package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.igaworks.displayad.DAErrorCode;
import com.igaworks.displayad.IgawDisplayAd;
import com.igaworks.displayad.part.onespot.listener.IInterstitialEventCallbackListener;
import com.igaworks.ssp.SSPErrorCode;
import com.igaworks.ssp.part.interstitial.InterstitialAd;
import com.igaworks.ssp.part.interstitial.listener.IInterstitialLoadEventCallbackListener;
import com.igaworks.ssp.part.interstitial.listener.IInterstitialShowEventCallbackListener;
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

public class IgaDAActivity extends AppCompatActivity {

    private String postId;
    private ParseObject postObject;
    private FunctionBase functionBase;
    private ParseUser currentUser;

    private Boolean rewarded;


    private InterstitialAd interstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iga_da);

        Intent intent = getIntent();
        postId = intent.getExtras().getString("postId");

        functionBase = new FunctionBase(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        interstitialAd = new InterstitialAd (IgaDAActivity.this);
        interstitialAd.setPlacementId("ijshe04ikkhlasf");
        interstitialAd.loadAd();

        if(currentUser != null){

            rewarded = false;
            ParseQuery query = ParseQuery.getQuery("ArtistPost");
            query.getInBackground(postId, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject postOb, ParseException e) {

                    if (e == null) {

                        postObject = postOb;


                        interstitialAd.setInterstitialLoadEventCallbackListener(new IInterstitialLoadEventCallbackListener() {
                            @Override
                            public void OnInterstitialLoaded() {

                                Log.d("messge", "loaded");



                            }

                            @Override
                            public void OnInterstitialReceiveFailed(SSPErrorCode sspErrorCode) {

                                Log.d("messge", "receive fail");

                            }
                        });

                        interstitialAd.showAd();

                        interstitialAd.setInterstitialShowEventCallbackListener(new IInterstitialShowEventCallbackListener() {
                            @Override
                            public void OnInterstitialOpened() {

                                Log.d("messge", "opened");

                            }

                            @Override
                            public void OnInterstitialOpenFailed(SSPErrorCode sspErrorCode) {

                                Log.d("messge", "fail");

                            }

                            @Override
                            public void OnInterstitialClosed(int i) {

                                if(rewarded){

                                    Log.d("messge", "rewarded");

                                    finish();

                                } else {

                                    Log.d("messge", "not reward");

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
                        });

                        /*
                        IgawDisplayAd.init(IgaDAActivity.this);
                        IgawDisplayAd.showInterstitialAd(IgaDAActivity.this, "b16ae2b139");
                        IgawDisplayAd.setInterstitialEventCallbackListener(IgaDAActivity.this, "b16ae2b139", new IInterstitialEventCallbackListener() {
                            @Override
                            public void OnInterstitialReceiveSuccess() {

                                Log.d("message", "success");

                            }

                            @Override
                            public void OnInterstitialReceiveFailed(DAErrorCode daErrorCode) {

                                Log.d("message", "fail");
                                Log.d("message", String.valueOf(daErrorCode.getErrorMessage()));

                                Log.d("InterstitialAdAcitivity", "fail");

                                TastyToast.makeText(getApplicationContext(), "광고를 불러오는데 실패 했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                finish();

                            }

                            @Override
                            public void OnInterstitialClosed() {

                                Log.d("message", "close");
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
                        });
                        */

                    } else {

                        e.printStackTrace();
                    }

                }

            });

        } else {

            TastyToast.makeText(getApplicationContext(), "광고가 삽입된 게시물을 보려면 로그인이 필요합니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent1);

        }



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        //IgawDisplayAd.destroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        interstitialAd.closeIGAWInterstitialAd();
    }
}
