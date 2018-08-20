package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
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

public class RewardVideoActivity extends AppCompatActivity implements RewardedVideoAdListener {


    private RewardedVideoAd mRewardedVideoAd;

    private String postId;
    private ParseObject postObject;
    private FunctionBase functionBase;
    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);

        Intent intent = getIntent();
        postId = intent.getExtras().getString("postId");

        functionBase = new FunctionBase(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

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

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

    }

    private void loadRewardedVideoAd(){

        mRewardedVideoAd.loadAd("ca-app-pub-8115892791740395/2663418269", new AdRequest.Builder().build());

    }


    @Override
    public void onRewardedVideoAdLoaded() {

        mRewardedVideoAd.show();


    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {


    }

    @Override
    public void onRewardedVideoAdClosed() {

        finish();

    }

    @Override
    public void onRewarded(RewardItem rewardItem) {

        mRewardedVideoAd.destroy(this);



        HashMap<String, Object> params = new HashMap<>();

        params.put("key", currentUser.getSessionToken());

        Date uniqueIdDate = new Date();
        String uniqueId = uniqueIdDate.toString();

        params.put("uid", uniqueId);
        params.put("postId", postObject.getObjectId());


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

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Log.d("location rewardVideo", "applicationleft");

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

        TastyToast.makeText(getApplicationContext(), "광고를 불러오는데 실패 했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
        finish();

    }

    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }


}
