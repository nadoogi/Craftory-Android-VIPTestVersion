package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "SplashActivity";
    private FunctionBase functionBase;
    private ParseUser currentUser;
    private ParseInstallation parseInstallation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splash_image = (ImageView) findViewById(R.id.splash_image);
        TextView app_version = (TextView) findViewById(R.id.app_version);


        PackageInfo pInfo = null;

        try {
            pInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);

            //final int versionCode = pInfo.versionCode;
            String versionName = pInfo.versionName;

            app_version.setText("VERSION || " + versionName);

        } catch (PackageManager.NameNotFoundException e) {

            e.printStackTrace();

        }

        Log.d("check_error", "0");

        currentUser = ParseUser.getCurrentUser();


        functionBase = new FunctionBase(this, getApplicationContext());

        Log.d("check_error", "0.5");

        parseInstallation = ParseInstallation.getCurrentInstallation();

        if(currentUser != null){

            Log.d("step", "1. user login");

            HashMap<String, Object> params = new HashMap<>();

            params.put("key", currentUser.getSessionToken());

            Date uniqueIdDate = new Date();
            String uniqueId = uniqueIdDate.toString();

            params.put("uid", uniqueId);

            ParseCloud.callFunctionInBackground("installation_data_check", params, new FunctionCallback<Map<String, Object>>() {

                public void done(Map<String, Object> mapObject, ParseException e) {

                    if (e == null) {

                        if(mapObject.get("status").toString().equals("true")){

                            Log.d("step", "installation data check success");

                            saveParseInstallationThenEnter(parseInstallation);

                        } else {

                            Log.d("step", "installation data check fail");

                            saveParseInstallationThenEnter(parseInstallation);

                        }

                    } else {

                        saveParseInstallationThenEnter(parseInstallation);

                        Log.d("step", "installation data check request fail");
                        e.printStackTrace();

                    }
                }
            });

        } else {

            Log.d("step", "2. user not login");

            saveParseInstallationThenEnter(parseInstallation);

        }

    }


    private void saveParseInstallationThenEnter(final ParseInstallation parseInstallation){

        if(currentUser != null){

            parseInstallation.put("user", currentUser);
            parseInstallation.put("userId", currentUser.getObjectId());

        }

        parseInstallation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){

                    Log.d("check_error", "0.6");

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                            if(parseInstallation.get("firstTime") == null){

                                Log.d("check_error", "1");
                                Intent intent = new Intent(getApplicationContext(), SignFirstTimeActivity.class);
                                startActivity(intent);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                SplashActivity.this.finish();


                            } else {

                                Log.d("check_error", "2");

                                if(currentUser != null){

                                    HashMap<String, Object> params = new HashMap<>();

                                    params.put("key", currentUser.getSessionToken());

                                    ParseCloud.callFunctionInBackground("mytag_reset", params, new FunctionCallback<Map<String, Object>>() {

                                        public void done(Map<String, Object> mapObject, ParseException e) {

                                            if (e == null) {

                                                Log.d("result", mapObject.toString());

                                                if(mapObject.get("status").toString().equals("true")){

                                                    try {

                                                        Log.d("check_error", "3");

                                                        ParseUser fetchedUser = currentUser.fetch();

                                                        functionBase.enteranceWithGuideCheckFunction(SplashActivity.this, fetchedUser, parseInstallation);

                                                    } catch (ParseException e1) {

                                                        Log.d("check_error", "4");

                                                        TastyToast.makeText(getApplicationContext(), "입장하는데 실패 했습니다. 다시 시도해주세요!!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                        e1.printStackTrace();

                                                    }

                                                } else {

                                                    TastyToast.makeText(getApplicationContext(), "댓글 입력이 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                }

                                            } else {

                                                e.printStackTrace();

                                            }
                                        }
                                    });




                                } else {

                                    Log.d("check_error", "5");

                                    Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                                    startActivity(intent);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                    SplashActivity.this.finish();

                                }

                            }



                        }

                    }, 1000);

                } else {

                    Log.d("check_error", "0.7");
                    e.printStackTrace();

                    ParseUser.logOut();
                    recreate();


                }


            }
        });


    }


    @Override
    protected void onDestroy() {

        functionBase = null;


        super.onDestroy();
    }
}
