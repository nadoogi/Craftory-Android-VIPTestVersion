package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.Date;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email_input;
    private EditText password_input;
    private EditText name_input;

    private ParseInstallation currentInstallation;

    private FunctionBase functionBase;
    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //getSupportActionBar().setTitle("회원가입");

        email_input = (EditText) findViewById(R.id.email_input);
        password_input = (EditText) findViewById(R.id.password_input);
        name_input = (EditText) findViewById(R.id.name_input);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        LinearLayout signin_button = (LinearLayout) findViewById(R.id.signin_button);
        signin_button.setOnClickListener(this);

        currentInstallation = ParseInstallation.getCurrentInstallation();
        functionBase = new FunctionBase(this, getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("회원가입");




    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.signin_button){

            final String email = email_input.getText().toString();
            final String password = password_input.getText().toString();
            final String name = name_input.getText().toString();

            ParseQuery query = ParseQuery.getQuery("_User");
            if(currentUser != null){

                query.whereNotEqualTo("objectId", currentUser.getObjectId() );

            }
            query.whereEqualTo("name", name);
            query.countInBackground(new CountCallback() {
                @Override
                public void done(int count, ParseException e) {

                    if(e==null){

                        if(count == 0){

                            if(email.length() == 0){

                                email_input.setError("이메일을 입력하세요");

                            } else if(!email.contains("@")){

                                email_input.setError("이메일 형식에 맞지 않습니다. 확인해주세요");

                            } else if(password.length() == 0 ){

                                password_input.setError("비밀번호를 입력하세요");

                            } else if(password.length() < 6){

                                password_input.setError("비밀번호는 6자리를 넘어야 합니다.");

                            } else if(name.length() == 0){

                                name_input.setError("닉네임을 입력하세요");

                            } else {

                                final ParseUser user = new ParseUser();
                                user.setUsername(email);
                                user.setPassword(password);
                                user.setEmail(email);
                                user.put("name", name);
                                user.put("visit", 0);
                                user.put("activity", "SignActivity");
                                user.put("signup_reward", false);
                                user.put("login_type", "facebook");
                                user.put("user_role_type", "patron");

                                user.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            Date date = new Date();

                                            ParseObject lastVisitOb = new ParseObject("LastVisit");
                                            lastVisitOb.put("user", user);
                                            lastVisitOb.put("last_reward_issue", date);
                                            lastVisitOb.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        //FunctionBase.enteranceCheckFunction(SigninActivity.this, user);

                                                        functionBase.enteranceWithGuideCheckFunction(SigninActivity.this, user, currentInstallation);

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

                            name_input.setError("중복되는 닉네임이 있습니다.");

                        }

                    } else {

                        e.printStackTrace();
                    }
                }
            });




        }


    }



}
