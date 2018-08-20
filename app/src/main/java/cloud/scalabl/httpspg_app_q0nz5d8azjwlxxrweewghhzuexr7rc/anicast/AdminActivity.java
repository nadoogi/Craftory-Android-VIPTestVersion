package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminActivity extends AppCompatActivity {

    private Button event_posting;
    Button notice_posting;
    private Button box_send_button;

    private Button test_button;
    private Button alchemy_button;
    private Button funding_market_button;
    private ParseUser currentUser;
    private ParseInstallation currentInstallation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        currentUser = ParseUser.getCurrentUser();
        currentInstallation = ParseInstallation.getCurrentInstallation();

        event_posting = (Button) findViewById(R.id.event_posting);
        event_posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EventManagerActivity.class);
                startActivity(intent);

            }
        });

        notice_posting = (Button) findViewById(R.id.notice_posting);
        notice_posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), NoticeManagerActivity.class);
                startActivity(intent);

            }
        });

        box_send_button =(Button) findViewById(R.id.box_send_button);
        box_send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), BoxManagerActivity.class);
                startActivity(intent);

            }
        });

        alchemy_button = (Button) findViewById(R.id.alchemy_manager);
        alchemy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlchemyItemManagerActivity.class);
                startActivity(intent);

            }
        });

        funding_market_button = (Button) findViewById(R.id.funding_market_button);
        funding_market_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), FundingMarketManagerActivity.class);
                startActivity(intent);

            }
        });

        test_button = (Button) findViewById(R.id.test_button);
        test_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TastyToast.makeText(getApplicationContext(), "클릭", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                HashMap<String, Object> params = new HashMap<>();
                params.put("key", currentUser.getSessionToken());
                Date uniqueIdDate = new Date();
                String uniqueId = uniqueIdDate.toString();

                params.put("uid", uniqueId);


                ParseCloud.callFunctionInBackground("test", params, new FunctionCallback<Map<String, Object>>() {

                    public void done(Map<String, Object> mapObject, ParseException e) {

                        if (e == null) {

                            Log.d( "result", mapObject.toString());
                            TastyToast.makeText(getApplicationContext(), "오키", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                        } else {

                            TastyToast.makeText(getApplicationContext(), "클릭실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                            e.printStackTrace();

                        }
                    }
                });

                /*
                Intent intent = new Intent(getApplicationContext(), RewardVideoActivity.class);
                startActivity(intent);
                */

                /*
                Intent intent = new Intent(getApplicationContext(), InterstitialAdActivity.class);
                intent.putExtra("postId", "hello");
                startActivity(intent);
                */




            }
        });


    }
}
