package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import retrofit2.Call;
import retrofit2.Response;

public class LoginFirstTimeActivity extends AppCompatActivity implements View.OnClickListener{

    private int LOGIN_REQUEST = 0;


    private EditText email_input;
    private EditText password_input;

    ParseInstallation currentInstallation;
    private FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        email_input = (EditText) findViewById(R.id.email_input);
        password_input = (EditText) findViewById(R.id.password_input);
        LinearLayout login_button = (LinearLayout) findViewById(R.id.login_button);
        LinearLayout signin_button = (LinearLayout) findViewById(R.id.signin_button);
        LinearLayout password_reset = (LinearLayout) findViewById(R.id.password_reset);

        LinearLayout sign_facebook_button = (LinearLayout) findViewById(R.id.sign_facebook_button);
        LinearLayout sign_twitter_button = (LinearLayout) findViewById(R.id.sign_twitter_button);


        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        currentInstallation = ParseInstallation.getCurrentInstallation();
        functionBase = new FunctionBase(this, getApplicationContext());


        login_button.setOnClickListener(this);
        signin_button.setOnClickListener(this);
        password_reset.setOnClickListener(this);
        sign_facebook_button.setOnClickListener(this);
        sign_twitter_button.setOnClickListener(this);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignFirstTimeActivity.class);
                startActivity(intent);

                finish();

            }
        });

        back_button_text.setText("로그인");




    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.login_button){

            String email = email_input.getText().toString();
            String password = password_input.getText().toString();

            if(email.length() == 0 || !email.contains("@")){

                email_input.setError("이메일을 정확히 입력하세요");

            } else if(password.length()==0){

                password_input.setError("비밀번호를 입력하세요");

            } else if(password.length() < 6){

                password_input.setError("비밀번호는 6자리 이상 입력해야 합니다");

            } else {

                Log.d("email", email);
                Log.d("password", password);

                ParseUser.logInInBackground(email, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser currentUser, ParseException e) {

                        if(e==null){

                            functionBase.enteranceWithGuideCheckFunction(LoginFirstTimeActivity.this, currentUser, currentInstallation);

                        } else {

                            Toast.makeText(LoginFirstTimeActivity.this, "로그인 중 문제가 발생했습니다. 다시 시도하세요", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();

                        }

                    }
                });

            }


        } else if(view.getId() == R.id.sign_facebook_button){

            //Toast.makeText(this, "facebook_login_button", Toast.LENGTH_SHORT).show();


            List<String> permissions = Arrays.asList("email","public_profile", "user_friends");


            ParseFacebookUtils.logInWithReadPermissionsInBackground(LoginFirstTimeActivity.this, permissions , new LogInCallback() {
                @Override
                public void done(final ParseUser currentUser, ParseException e) {

                    if(e==null){

                        if(currentUser.get("activity") != null){

                            functionBase.enteranceWithGuideCheckFunction(LoginFirstTimeActivity.this, currentUser, currentInstallation);

                        } else {

                            //Toast.makeText(LoginActivity.this, currentUser.getUsername(), Toast.LENGTH_SHORT).show();

                            GraphRequest request = GraphRequest.newMeRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(JSONObject object, GraphResponse response) {
                                            // Application code
                                            Log.d("userfb", object.toString());
                                            Log.d("userfbresponse", response.toString());

                                            try {

                                                String email = object.getString("email");
                                                String gender = object.getString("gender");
                                                String name = object.getString("name");
                                                JSONObject picture = object.getJSONObject("picture");
                                                JSONObject pic_data = picture.getJSONObject("data");
                                                String pic_url = pic_data.getString("url");

                                                if(email != null){

                                                    currentUser.put("email", email);

                                                }

                                                if(gender != null){

                                                    currentUser.put("gender", gender);

                                                }

                                                if(name != null){

                                                    currentUser.put("name", name);

                                                }

                                                if(pic_url != null){

                                                    currentUser.put("pic_url", pic_url);

                                                }

                                                currentUser.put("user_role_type", "patron");
                                                currentUser.put("visit", 0);
                                                currentUser.put("activity", "SignActivity");
                                                currentUser.put("signup_reward", false);
                                                currentUser.put("login_type", "facebook");
                                                currentUser.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if( e == null ){

                                                            Date date = new Date();

                                                            ParseObject lastVisitOb = new ParseObject("LastVisit");
                                                            lastVisitOb.put("user", currentUser);
                                                            lastVisitOb.put("last_reward_issue", date);
                                                            lastVisitOb.saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(ParseException e) {

                                                                    if(e==null){

                                                                        functionBase.enteranceWithGuideCheckFunction(LoginFirstTimeActivity.this, currentUser, currentInstallation);

                                                                    } else {

                                                                        e.printStackTrace();

                                                                    }

                                                                }
                                                            });

                                                        } else {

                                                            //이미 같은 메일을 사용하고 있는 경우 에러 발생
                                                            TastyToast.makeText(getApplicationContext(), "이미 가입한 이메일을 사용하거나 오류가 발생했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                            e.printStackTrace();


                                                        }

                                                    }
                                                });

                                            } catch (JSONException e1) {

                                                e1.printStackTrace();

                                            }


                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "email, cover, birthday, gender, name, age_range, picture");
                            request.setParameters(parameters);
                            request.executeAsync();

                        }




                    } else {

                        e.printStackTrace();
                        if(e.getMessage().contains("already exists")){

                            Toast.makeText(LoginFirstTimeActivity.this, "이메일이 이미 등록되어 있습니다.", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(LoginFirstTimeActivity.this, "실패", Toast.LENGTH_SHORT).show();

                        }
                    }

                }
            });

        } else if(view.getId() == R.id.sign_twitter_button){

            //Toast.makeText(this, "google_login_button", Toast.LENGTH_SHORT).show();
            ParseTwitterUtils.logIn(this, new LogInCallback() {
                @Override
                public void done(final ParseUser currentUser, ParseException e) {

                    if(e==null){

                        if(currentUser != null){

                            if(currentUser.get("activity") != null){

                                functionBase.enteranceWithGuideCheckFunction(LoginFirstTimeActivity.this, currentUser, currentInstallation);

                            } else {

                                TwitterAuthToken twitterAuthToken = new TwitterAuthToken(ParseTwitterUtils.getTwitter().getAuthToken(), ParseTwitterUtils.getTwitter().getAuthTokenSecret());
                                TwitterSession twitterSession = new TwitterSession(twitterAuthToken, Long.parseLong( ParseTwitterUtils.getTwitter().getUserId() ), ParseTwitterUtils.getTwitter().getScreenName());

                                TwitterCore.getInstance().getApiClient(twitterSession).getAccountService().verifyCredentials(true, true, true).enqueue(new retrofit2.Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {

                                        User user = response.body();

                                        String email = user.email;

                                        String name = user.name;
                                        String pic_url = user.profileImageUrl;

                                        if(email != null){

                                            currentUser.put("email", email);

                                        }

                                        if(name != null){

                                            currentUser.put("name", name);

                                        }

                                        if(pic_url != null){

                                            currentUser.put("pic_url", pic_url);

                                        }

                                        currentUser.put("user_role_type", "patron");
                                        currentUser.put("visit", 0);
                                        currentUser.put("activity", "SignActivity");
                                        currentUser.put("signup_reward", false);
                                        currentUser.put("login_type", "twitter");
                                        currentUser.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                if( e == null ){

                                                    Date date = new Date();

                                                    ParseObject lastVisitOb = new ParseObject("LastVisit");
                                                    lastVisitOb.put("user", currentUser);
                                                    lastVisitOb.put("last_reward_issue", date);
                                                    lastVisitOb.saveInBackground(new SaveCallback() {
                                                        @Override
                                                        public void done(ParseException e) {

                                                            if(e==null){

                                                                functionBase.enteranceWithGuideCheckFunction(LoginFirstTimeActivity.this, currentUser, currentInstallation);

                                                            } else {

                                                                e.printStackTrace();

                                                            }

                                                        }
                                                    });

                                                } else {
                                                    //이미 같은 메일을 사용하고 있는 경우 에러 발생

                                                    e.printStackTrace();
                                                }

                                            }
                                        });


                                    }

                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {

                                    }
                                });

                            }

                        } else {

                            Toast.makeText(LoginFirstTimeActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }



                    } else {

                        e.printStackTrace();
                        if(e.getMessage().contains("already exists")){

                            Toast.makeText(LoginFirstTimeActivity.this, "이메일이 이미 등록되어 있습니다.", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(LoginFirstTimeActivity.this, "실패", Toast.LENGTH_SHORT).show();

                        }
                    }


                }
            });


        } else if(view.getId() == R.id.signin_button){

            Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
            startActivity(intent);

            finish();

        } else if(view.getId() == R.id.password_reset){

            if(email_input.getText().toString().length() == 0 || !email_input.getText().toString().contains("@")){

                email_input.setError("이메일이 입력되지 않거나, 형식이 맞지 않습니다.");

            } else {

                String email_input_string = email_input.getText().toString();

                ParseUser.requestPasswordResetInBackground(email_input_string, new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e==null){

                            TastyToast.makeText(getApplicationContext(), "이메일이 발송되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                        } else {

                            TastyToast.makeText(getApplicationContext(), "문제가 발생했습니다. 이메일을 확인해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            e.printStackTrace();
                        }

                    }
                });

            }

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, "OnActivityResult", Toast.LENGTH_SHORT).show();

        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);


    }

}
