package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class CommercialRequestActivity extends AppCompatActivity {

    private LinearLayout commercial_request_button;
    private ParseUser currentUser;

    private TextView current_email;
    private TextView email_status;
    private LinearLayout email_button;
    private LinearLayout verify_button;
    private EditText email_input;

    private Boolean emailVerified;
    private Boolean phoneVerified;

    private FunctionBase functionBase;


    private LinearLayout phone_button;
    private TextView current_phone;
    private TextView phone_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial_request);

        commercial_request_button = (LinearLayout) findViewById(R.id.commercial_request_button);
        email_status = (TextView) findViewById(R.id.email_status);
        email_button = (LinearLayout) findViewById(R.id.email_button);
        verify_button = (LinearLayout) findViewById(R.id.verify_button);
        phone_button = (LinearLayout) findViewById(R.id.phone_button);

        email_input = (EditText) findViewById(R.id.email_input);
        current_phone = (TextView) findViewById(R.id.current_phone);
        phone_status = (TextView) findViewById(R.id.phone_status);

        current_email = (TextView) findViewById(R.id.current_email);


        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getApplicationContext());
        emailVerified = false;
        phoneVerified = false;

        emailStatusUpdate();
        phoneStatusUpdate();


        email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEmailString = email_input.getText().toString();

                if(inputEmailString.length() == 0){

                    TastyToast.makeText(getApplicationContext(), "이메일을 입력해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else if(!inputEmailString.contains("@")){

                    TastyToast.makeText(getApplicationContext(), "이메일 형식이 맞지 않습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {

                    currentUser.put("email", inputEmailString);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                TastyToast.makeText(getApplicationContext(), "이메일이 발송되었습니다. 인증해주세요.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            } else {

                                e.printStackTrace();

                            }

                        }
                    });

                }

            }
        });

        verify_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailStatusUpdate();

            }
        });

        phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PhoneInputActivity.class);
                startActivity(intent);

            }
        });

        commercial_request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentUser.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(final ParseObject fetchedUser, ParseException e) {

                        if(e==null){

                            if(fetchedUser.getBoolean("commercial_request")){

                                TastyToast.makeText(getApplicationContext(), "이미 신청했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            } else {

                                ParseObject commercialRqOb = new ParseObject("CommercialRequest");
                                commercialRqOb.put("user", fetchedUser);
                                commercialRqOb.put("request", true);
                                commercialRqOb.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            fetchedUser.put("commercial_request", true);
                                            fetchedUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        TastyToast.makeText(getApplicationContext(), "유료화 신청이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                    } else {

                                                        e.printStackTrace();
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
        });


    }

    private void emailStatusUpdate(){

        currentUser.fetchInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser fetchedUser, ParseException e) {

                String myEmail = fetchedUser.getEmail();

                if(myEmail != null){

                    current_email.setText(myEmail);

                    if(fetchedUser.getBoolean("emailVerified")){

                        email_status.setText("이메일 인증 완료");
                        email_status.setTextColor(functionBase.likeColor);
                        emailVerified = true;

                    } else {

                        email_status.setText("이메일이 인증되지 않았습니다.");
                        email_status.setTextColor(functionBase.likedColor);
                        emailVerified = false;

                    }

                } else {

                    current_email.setText("이메일이 입력되지 않았습니다.");
                    email_status.setText("이메일 미입력");
                    email_status.setTextColor(functionBase.likedColor);
                    emailVerified = false;

                }


            }
        });

    }

    private void phoneStatusUpdate(){

        final ParseObject personalOb = currentUser.getParseObject("personal");

        if(personalOb == null){

            current_phone.setText("전화번호가 입력되지 않았습니다.");
            phone_status.setText("전화번호 미입력");
            phoneVerified = false;

        } else {

            personalOb.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject fetchedPersonalOb, ParseException e) {

                    if(e==null){

                        current_phone.setText(personalOb.getString("phone"));
                        phone_status.setText("인증 완료");
                        phoneVerified = true;

                    } else {

                        e.printStackTrace();

                    }

                }
            });

        }

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(currentUser != null){

            emailStatusUpdate();
            phoneStatusUpdate();

        }

    }
}
