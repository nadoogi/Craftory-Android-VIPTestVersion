package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import de.hdodenhof.circleimageview.CircleImageView;

public class CheerActivity extends AppCompatActivity {

    private ParseObject userObject;

    EditText comment_input;
    BootstrapButton send_button;
    TextView current_point;
    EditText point_input;
    TextView preview_text;
    CircleImageView user_image;
    TextView user_name;
    TextView cheer_point;
    int currentPoint = 0;

    private ParseUser currentUser;
    private RequestManager requestManager;

    private FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheer);

        Intent intent = getIntent();
        String userId = intent.getExtras().getString("userId");

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("응원하기");
        functionBase = new FunctionBase(this, getApplicationContext());

        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getApplicationContext());

        comment_input = (EditText) findViewById(R.id.comment_input);
        send_button = (BootstrapButton) findViewById(R.id.send_button);
        current_point = (TextView) findViewById(R.id.current_point);
        point_input = (EditText) findViewById(R.id.point_input);

        preview_text = (TextView) findViewById(R.id.preview_text);
        user_image = (CircleImageView) findViewById(R.id.user_image);
        user_name = (TextView) findViewById(R.id.user_name);

        cheer_point = (TextView) findViewById(R.id.cheer_point);


        ParseQuery userQuery = ParseQuery.getQuery("_User");
        userQuery.getInBackground(userId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject userOb, ParseException e) {

                if(e==null){

                    if(currentUser != null){

                        currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject fetchedPointOb, ParseException e) {

                                if(e==null){

                                    current_point.setText(functionBase.makeComma( fetchedPointOb.getInt("current_point") ) + " BOX");
                                    currentPoint = fetchedPointOb.getInt("current_point");

                                    functionBase.profileImageLoading(user_image, currentUser, requestManager);
                                    functionBase.profileNameLoading(user_name, currentUser);



                                    comment_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                        @Override
                                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                                            String inputString = comment_input.getText().toString();

                                            preview_text.setText(inputString);

                                            return false;
                                        }
                                    });

                                    point_input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                                        @Override
                                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                                            String inputPointString = point_input.getText().toString();

                                            cheer_point.setText( "+ " + functionBase.makeComma( Integer.parseInt( inputPointString ) ) + " BOX" );

                                            return false;
                                        }
                                    });


                                    send_button.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            send_button.setClickable(false);

                                            final String inputMsgString = comment_input.getText().toString();
                                            String inputPointString = point_input.getText().toString();

                                            int inputPoint = Integer.parseInt( inputPointString );

                                            if(inputMsgString.length() == 0 || inputPointString.length() == 0){

                                                TastyToast.makeText(getApplicationContext(), "메시지 또는 BOX가 입력되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                send_button.setClickable(true);

                                            } else if( currentPoint < inputPoint) {

                                                TastyToast.makeText(getApplicationContext(), "BOX가 부족 합니다. ", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                send_button.setClickable(true);

                                            } else {

                                                HashMap<String, Object> params = new HashMap<>();

                                                params.put("key", currentUser.getSessionToken());
                                                params.put("point", inputPointString);
                                                params.put("target", userOb.getObjectId());
                                                Date uniqueIdDate = new Date();
                                                String uniqueId = uniqueIdDate.toString();

                                                params.put("uid", uniqueId);

                                                ParseCloud.callFunctionInBackground("cheer_point", params, new FunctionCallback<Map<String, Object>>() {

                                                    public void done(Map<String, Object> mapObject, ParseException e) {

                                                        if (e == null) {

                                                            Log.d("parse result", mapObject.toString());

                                                            if(mapObject.get("status").toString().equals("true")){

                                                                String cheerPointId = mapObject.get("cheerPoint").toString();

                                                                ParseQuery cheerPointQuery = ParseQuery.getQuery("CheerPoint");
                                                                cheerPointQuery.getInBackground(cheerPointId, new GetCallback<ParseObject>() {
                                                                    @Override
                                                                    public void done(ParseObject cheerPointOb, ParseException e) {

                                                                        if(e==null){

                                                                            ParseObject socialMSGOb = new ParseObject("SocialMessage");
                                                                            socialMSGOb.put("user",currentUser);
                                                                            socialMSGOb.put("target", userOb);
                                                                            socialMSGOb.put("type", "cheer");
                                                                            socialMSGOb.put("status", true);
                                                                            socialMSGOb.put("message", inputMsgString);
                                                                            socialMSGOb.put("cheer_point", cheerPointOb);
                                                                            socialMSGOb.saveInBackground(new SaveCallback() {
                                                                                @Override
                                                                                public void done(ParseException e) {

                                                                                    if(e==null){


                                                                                        TastyToast.makeText(getApplicationContext(), "응원하기 성공", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                                        finish();

                                                                                    } else {

                                                                                        TastyToast.makeText(getApplicationContext(), "응원하기 저장에 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                                        send_button.setClickable(true);
                                                                                        e.printStackTrace();
                                                                                    }

                                                                                }
                                                                            });

                                                                        } else {

                                                                            send_button.setClickable(true);
                                                                            e.printStackTrace();
                                                                            TastyToast.makeText(getApplicationContext(), "응원하기 BOX를 찾을수 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                                        }

                                                                    }

                                                                });


                                                            } else {

                                                                send_button.setClickable(true);
                                                                TastyToast.makeText(getApplicationContext(), "BOX 저장 중 에러가 발생했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                            }

                                                        } else {
                                                            Log.d("error", "error");
                                                            e.printStackTrace();

                                                            send_button.setClickable(true);
                                                        }
                                                    }
                                                });

                                            }

                                        }
                                    });



                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });



                    }




                } else {

                    e.printStackTrace();
                }

            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            finish();

        }

        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

}
