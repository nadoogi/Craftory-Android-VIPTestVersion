package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.igaworks.IgawCommon;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SubscriptionHandling;
import com.sdsmdg.tastytoast.TastyToast;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.DMListdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.NotiAlertAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.AlarmBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtworkBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.DMBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.PatronBoardFragment;

public class MainBoardActivity extends AppCompatActivity {

    //private static MyBoardFragment myBoardFragment;
    //private static ArtworkBoardFragment artworkBoardFragment;
    private ArtistBoardFragment artistBoardFragment;
    private AlarmBoardFragment alarmBoardFragment;

    private FragmentManager fragmentManager;
    private int currentLocation;

    private ParseUser currentUser;
    private LinearLayout alert_count_layout;
    private LinearLayout dm_count_layout;
    private TextView alert_count;
    private TextView dm_count;
    private int currentAlertCount;

    ImageView my_icon;
    ImageView artist_icon;
    ImageView alert_icon;
    ImageView dm_icon;

    TextView my_text;
    TextView artist_text;
    TextView alert_text;
    TextView dm_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

        FrameLayout container = (FrameLayout) findViewById(R.id.container);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        currentUser = ParseUser.getCurrentUser();

        LinearLayout my_board_button = (LinearLayout) findViewById(R.id.my_board_button);
        LinearLayout artist_board_button = (LinearLayout) findViewById(R.id.artist_board_button);
        LinearLayout alarm_board_button = (LinearLayout) findViewById(R.id.alarm_board_button);
        LinearLayout dm_board_button = (LinearLayout) findViewById(R.id.dm_board_button);

        alert_count_layout = (LinearLayout) findViewById(R.id.alert_count_layout);
        alert_count_layout.setVisibility(View.GONE);
        alert_count = (TextView) findViewById(R.id.alert_count);
        dm_count_layout = (LinearLayout) findViewById(R.id.dm_count_layout);
        dm_count_layout.setVisibility(View.GONE);
        dm_count = (TextView) findViewById(R.id.dm_count);

        my_icon = (ImageView) findViewById(R.id.my_icon);
        artist_icon = (ImageView) findViewById(R.id.artist_icon);
        alert_icon = (ImageView) findViewById(R.id.alert_icon);
        dm_icon = (ImageView) findViewById(R.id.dm_icon);

        my_text = (TextView) findViewById(R.id.my_text);
        artist_text = (TextView) findViewById(R.id.artist_text);
        alert_text = (TextView) findViewById(R.id.alert_text);
        dm_text = (TextView) findViewById(R.id.dm_text);


        MyBoardFragment myBoardFragment = new MyBoardFragment();

        fragmentManager = getSupportFragmentManager();


        currentLocation = 0;
        fragmentManager.beginTransaction().add(R.id.container, myBoardFragment).commitAllowingStateLoss();


        final int pointColor = Color.parseColor("#EC6783");
        final int baseColor = Color.parseColor("#b1b1b1");
        //int pointColor = R.color.basic_point_color;

        my_icon.setColorFilter(pointColor);
        my_text.setTextColor(pointColor);

        dm_icon.setColorFilter(baseColor);
        dm_text.setTextColor(baseColor);

        my_board_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MyBoardFragment myBoardFragment = new MyBoardFragment();

                currentLocation = 0;
                FragmentTransaction myTransaction = fragmentManager.beginTransaction();

                myTransaction.replace(R.id.container, myBoardFragment);
                myTransaction.addToBackStack("0");
                myTransaction.commitAllowingStateLoss();

                my_icon.setColorFilter(pointColor);
                my_text.setTextColor(pointColor);

                artist_icon.setColorFilter(baseColor);
                artist_text.setTextColor(baseColor);

                alert_icon.setColorFilter(baseColor);
                alert_text.setTextColor(baseColor);

                dm_icon.setColorFilter(baseColor);
                dm_text.setTextColor(baseColor);

                alarmStatusCheck();
                dmStatusCheck();

            }
        });

        artist_board_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArtistBoardFragment artistBoardFragment = new ArtistBoardFragment();

                currentLocation = 1;
                FragmentTransaction artistTransaction = fragmentManager.beginTransaction();

                artistTransaction.replace(R.id.container, artistBoardFragment);
                artistTransaction.addToBackStack("2");
                artistTransaction.commitAllowingStateLoss();

                my_icon.setColorFilter(baseColor);
                my_text.setTextColor(baseColor);

                artist_icon.setColorFilter(pointColor);
                artist_text.setTextColor(pointColor);


                alert_icon.setColorFilter(baseColor);
                alert_text.setTextColor(baseColor);

                dm_icon.setColorFilter(baseColor);
                dm_text.setTextColor(baseColor);

                alarmStatusCheck();
                dmStatusCheck();

            }
        });



        alarm_board_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlarmBoardFragment alarmBoardFragment = new AlarmBoardFragment();

                currentLocation = 3;
                FragmentTransaction alarmTransaction = fragmentManager.beginTransaction();

                alarmTransaction.replace(R.id.container, alarmBoardFragment);
                alarmTransaction.addToBackStack("3");
                alarmTransaction.commitAllowingStateLoss();



                if(alarmBoardFragment != null){

                    //refresh function

                }

                my_icon.setColorFilter(baseColor);
                my_text.setTextColor(baseColor);

                artist_icon.setColorFilter(baseColor);
                artist_text.setTextColor(baseColor);


                alert_icon.setColorFilter(pointColor);
                alert_text.setTextColor(pointColor);

                dm_icon.setColorFilter(baseColor);
                dm_text.setTextColor(baseColor);

                alarmStatusCheck();
                dmStatusCheck();

            }
        });


        dm_board_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DMBoardFragment dmBoardFragment = new DMBoardFragment();

                currentLocation = 5;
                FragmentTransaction dmTransaction = fragmentManager.beginTransaction();

                dmTransaction.replace(R.id.container, dmBoardFragment);
                dmTransaction.addToBackStack("3");
                dmTransaction.commitAllowingStateLoss();



                if(dmBoardFragment != null){

                    //refresh function

                }

                my_icon.setColorFilter(baseColor);
                my_text.setTextColor(baseColor);

                artist_icon.setColorFilter(baseColor);
                artist_text.setTextColor(baseColor);

                dm_icon.setColorFilter(pointColor);
                dm_text.setTextColor(pointColor);


                alert_icon.setColorFilter(baseColor);
                alert_text.setTextColor(baseColor);

                alarmStatusCheck();
                dmStatusCheck();

            }
        });

        dmStatusCheck();
        alarmStatusCheck();
        loginStatusDisplay(currentUser);


        if(currentUser != null){

            ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
            ParseQuery query = ParseQuery.getQuery("DMList");
            query.whereContains("room", currentUser.getObjectId());
            query.whereEqualTo("status", true);
            query.whereEqualTo("open_flag", true);
            query.orderByDescending("createdAt");

            SubscriptionHandling<ParseObject> subscriptionHandling = parseLiveQueryClient.subscribe(query);

            subscriptionHandling.handleEvent(SubscriptionHandling.Event.UPDATE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                @Override
                public void onEvent(ParseQuery<ParseObject> query, ParseObject object) {
                    Log.d("message", "dm update");

                    DMListdapter adapter = DMBoardFragment.dmListdapter;

                    if(adapter != null){

                        DMBoardFragment.dmListdapter.loadObjects(0);

                    }

                    dmStatusCheck();

                }
            });


            ParseQuery notiQuery = ParseQuery.getQuery("MyAlert");
            notiQuery.whereEqualTo("to", currentUser);
            notiQuery.whereNotEqualTo("from", currentUser);
            notiQuery.whereEqualTo("status", true);
            notiQuery.orderByDescending("createdAt");

            SubscriptionHandling<ParseObject> subscriptionHandlingForNoti = parseLiveQueryClient.subscribe(notiQuery);

            subscriptionHandlingForNoti.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                @Override
                public void onEvent(ParseQuery<ParseObject> query, ParseObject object) {
                    Log.d("message", "noti update");

                    NotiAlertAdapter adapter = AlarmBoardFragment.notiAlertAdapter;

                    if(adapter != null){

                        adapter.loadObjects(0);

                    }

                    alarmStatusCheck();

                }
            });

        }


    }







    @Override
    protected void onPostResume() {
        super.onPostResume();

        alarmStatusCheck();
        dmStatusCheck();

    }

    public void alarmStatusCheck(){

        if(currentUser != null){

            Date now = new Date();

            if(currentUser.get("last_alert_check") == null){

                currentUser.put("last_alert_check", now);
                currentUser.saveInBackground();

            } else {

                //ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();

                ParseQuery myAlertQuery = ParseQuery.getQuery("MyAlert");
                myAlertQuery.whereEqualTo("to", currentUser);
                myAlertQuery.whereEqualTo("status", true);
                myAlertQuery.whereGreaterThanOrEqualTo("createdAt", currentUser.getDate("last_alert_check"));
                myAlertQuery.orderByDescending("createdAt");

                myAlertQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {



                        if(e==null){

                            Log.d("alert count",String.valueOf(objects.size()));

                            if(objects.size() == 0 ){

                                alert_count_layout.setVisibility(View.GONE);
                                currentAlertCount = 0;

                            } else {

                                alert_count_layout.setVisibility(View.VISIBLE);
                                alert_count.setText(String.valueOf(objects.size()));
                                currentAlertCount = objects.size();

                            }

                        } else {

                            Log.d("alert count","error");

                            e.printStackTrace();
                        }

                    }

                });

            }

        }


    }


    private void dmStatusCheck(){

        if(currentUser != null){

            currentUser.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject fetchedUserOb, ParseException e) {

                    if(e==null){

                        if(fetchedUserOb.getInt("dm_count") != 0){

                            dm_count_layout.setVisibility(View.VISIBLE);

                            int dmCount = fetchedUserOb.getInt("dm_count");

                            if(fetchedUserOb.getInt("dm_count") > 9){



                            }

                            dm_count.setText(String.valueOf(fetchedUserOb.getInt("dm_count")));

                        } else {

                            dm_count_layout.setVisibility(View.GONE);

                        }

                    } else {

                        e.printStackTrace();
                    }

                }
            });



        }

    }


    protected void loginStatusDisplay(ParseUser currentUser){

        if(currentUser != null){

            IgawCommon.setUserId(getApplicationContext(), currentUser.getObjectId());

            freePointManager();

        }

    }


    private void freePointManager(){

        if(currentUser != null){

            if(currentUser.get("point") == null){

                HashMap<String, Object> params = new HashMap<>();

                params.put("key", currentUser.getSessionToken());
                Date uniqueIdDate = new Date();
                String uniqueId = uniqueIdDate.toString();

                params.put("uid", uniqueId);

                ParseCloud.callFunctionInBackground("signup_point_maker", params, new FunctionCallback<Boolean>() {
                    public void done(Boolean result, ParseException e) {
                        if (e == null) {

                            if(result){

                                //TastyToast.makeText(getApplicationContext(), "회원가입을 축하 합니다. 500BOX가 지급 되었습니다", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                Log.d("message","point made");

                            } else {

                                Log.d("message","no reward");

                            }

                        } else {

                            e.printStackTrace();
                        }
                    }
                });

            }

        }


        HashMap<String, Object> params = new HashMap<>();

        params.put("key", currentUser.getSessionToken());
        Date uniqueIdDate = new Date();
        String uniqueId = uniqueIdDate.toString();

        params.put("uid", uniqueId);

        ParseCloud.callFunctionInBackground("signup_reward", params, new FunctionCallback<Boolean>() {
            public void done(Boolean result, ParseException e) {
                if (e == null) {

                    if(result){

                        //TastyToast.makeText(getApplicationContext(), "회원가입을 축하 합니다. 500BOX가 지급 되었습니다", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                        Log.d("message","sign reward alright");

                    } else {

                        Log.d("message","no reward");

                    }

                } else {

                    e.printStackTrace();
                }
            }
        });


        HashMap<String, Object> params2 = new HashMap<>();

        params2.put("key", currentUser.getSessionToken());
        Date uniqueIdDate2 = new Date();
        String uniqueId2 = uniqueIdDate2.toString();

        params2.put("uid", uniqueId2);

        ParseCloud.callFunctionInBackground("daily_reward", params2, new FunctionCallback<Boolean>() {
            public void done(Boolean result, ParseException e) {
                if (e == null) {

                    if(result){

                        TastyToast.makeText(getApplicationContext(), "일일 출석 보상 100 BOX가 지급 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                    } else {

                        Log.d("message","no reward");

                    }


                } else {

                    e.printStackTrace();
                }
            }
        });




    }


    public void resetAlertCount(){

        currentAlertCount = 0;

    };


    @Override
    public void onBackPressed() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);

        builder.title("확인");
        builder.content("앱을 종료 하시겠습니까?");
        builder.positiveText("예");
        builder.negativeText("아니요");
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {



            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                finish();

            }
        });
        builder.show();



    }
}
