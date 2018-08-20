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
import com.parse.CountCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;
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

public class SignFirstTimeActivity extends AppCompatActivity implements View.OnClickListener{

    private static int LOGIN_REQUEST = 0;


    private EditText email_input;
    private EditText password_input;
    private EditText nickname_input;


    private ParseUser currentUser;
    private ParseInstallation currentInstallation;

    private FunctionBase functionBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_first_time);

        email_input = (EditText) findViewById(R.id.email_input);
        password_input = (EditText) findViewById(R.id.password_input);
        nickname_input = (EditText) findViewById(R.id.nickname_input);

        LinearLayout sign_facebook_button = (LinearLayout) findViewById(R.id.sign_facebook_button);
        LinearLayout sign_twitter_button = (LinearLayout) findViewById(R.id.sign_twitter_button);

        LinearLayout signin_button = (LinearLayout) findViewById(R.id.signin_button);
        TextView skip_button = (TextView) findViewById(R.id.skip_button);
        TextView extra_button = (TextView) findViewById(R.id.extra_button);
        TextView login_button = (TextView) findViewById(R.id.login_button);

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(this, getApplicationContext());

        signin_button.setOnClickListener(this);
        skip_button.setOnClickListener(this);
        extra_button.setOnClickListener(this);
        login_button.setOnClickListener(this);
        sign_facebook_button.setOnClickListener(this);
        sign_twitter_button.setOnClickListener(this);

        currentInstallation = ParseInstallation.getCurrentInstallation();

        currentInstallation.put("firstTime", false);
        currentInstallation.saveInBackground();


    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.signin_button){

            final String email = email_input.getText().toString();
            final String password = password_input.getText().toString();
            final String name = nickname_input.getText().toString();

            ParseQuery query = ParseQuery.getQuery("_User");
            query.whereEqualTo("name", name);

            if(currentUser != null){

                query.whereNotEqualTo("objectId", currentUser.getObjectId() );

            }

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

                                nickname_input.setError("닉네임을 입력하세요");

                            } else {

                                final ParseUser user = new ParseUser();
                                user.setUsername(email);
                                user.setPassword(password);
                                user.setEmail(email);
                                user.put("name", name);
                                user.put("visit", 0);
                                user.put("activity", "SignFirstTimeActivity");
                                user.put("signup_reward", false);
                                user.put("login_type", "email");
                                user.put("user_role_type", "patron");

                                user.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            Log.d("LoginFirestTime", "1");

                                            Date date = new Date();

                                            ParseObject lastVisitOb = new ParseObject("LastVisit");
                                            lastVisitOb.put("user", user);
                                            lastVisitOb.put("last_reward_issue", date);
                                            lastVisitOb.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        Log.d("LoginFirestTime", "2");

                                                        functionBase.enteranceWithGuideCheckFunction(SignFirstTimeActivity.this, user, currentInstallation);

                                                    } else {

                                                        e.printStackTrace();

                                                    }

                                                }
                                            });

                                        } else {

                                            TastyToast.makeText(getApplicationContext(), "이미 가입 했습니다. 로그인 해주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }

                        } else {

                            nickname_input.setError("중복되는 닉네임이 있습니다.");

                        }

                    } else {

                        e.printStackTrace();
                    }

                }
            });


        } else if(view.getId() == R.id.skip_button){

            if(currentUser != null){

                functionBase.enteranceWithGuideCheckFunction(SignFirstTimeActivity.this, currentUser, currentInstallation);

            } else {

                if(currentInstallation.get("guide_check") == null){

                    Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
                    startActivity(intent);

                    finish();

                } else {

                    if(currentInstallation.getBoolean("guide_check")){

                        Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                        startActivity(intent);

                        finish();

                    } else {

                        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
                        startActivity(intent);

                        finish();

                    }

                }



            }


        } else if(view.getId() == R.id.sign_facebook_button){

            List<String> permissions = Arrays.asList("email","public_profile", "user_friends");

            ParseFacebookUtils.logInWithReadPermissionsInBackground(SignFirstTimeActivity.this, permissions , new LogInCallback() {
                @Override
                public void done(final ParseUser currentUser, ParseException e) {

                    if(e==null){

                        if(currentUser.get("activity") != null){

                            functionBase.enteranceCheckFunction(SignFirstTimeActivity.this, currentUser);

                        } else {

                            Toast.makeText(SignFirstTimeActivity.this, currentUser.getUsername(), Toast.LENGTH_SHORT).show();

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
                                                final String gender = object.getString("gender");
                                                final String name = object.getString("name");
                                                JSONObject picture = object.getJSONObject("picture");
                                                JSONObject pic_data = picture.getJSONObject("data");
                                                final String pic_url = pic_data.getString("url");

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
                                                currentUser.put("activity", "SignFirstTimeActivity");
                                                currentUser.put("signup_reward", false);
                                                currentUser.put("login_type", "facebook");
                                                currentUser.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if( e == null ){

                                                            functionBase.enteranceCheckFunction(SignFirstTimeActivity.this, currentUser);

                                                        } else {

                                                            e.printStackTrace();

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
                                                            currentUser.put("activity", "SignFirstTimeActivity");
                                                            currentUser.put("signup_reward", false);
                                                            currentUser.put("login_type", "facebook");
                                                            currentUser.saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(ParseException e) {

                                                                    if( e == null ){

                                                                        functionBase.enteranceCheckFunction(SignFirstTimeActivity.this, currentUser);

                                                                    } else {

                                                                        e.printStackTrace();

                                                                    }

                                                                }
                                                            });

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
                        Log.d("error message1", e.getMessage());
                        if(e.getMessage().contains("already exists")){

                            Toast.makeText(SignFirstTimeActivity.this, "이메일이 이미 등록되어 있습니다.", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(SignFirstTimeActivity.this, "실패", Toast.LENGTH_SHORT).show();

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

                        if(currentUser.get("activity") != null){

                            functionBase.enteranceWithGuideCheckFunction(SignFirstTimeActivity.this, currentUser, currentInstallation);

                        } else {

                            TwitterAuthToken twitterAuthToken = new TwitterAuthToken(ParseTwitterUtils.getTwitter().getAuthToken(), ParseTwitterUtils.getTwitter().getAuthTokenSecret());
                            TwitterSession twitterSession = new TwitterSession(twitterAuthToken, Long.parseLong( ParseTwitterUtils.getTwitter().getUserId() ), ParseTwitterUtils.getTwitter().getScreenName());

                            TwitterCore.getInstance().getApiClient(twitterSession).getAccountService().verifyCredentials(true, true, true).enqueue(new retrofit2.Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {

                                    User user = response.body();

                                    String email = user.email;

                                    final String name = user.name;
                                    final String pic_url = user.profileImageUrl;

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
                                    currentUser.put("activity", "SignFirstTimeActivity");
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

                                                            functionBase.enteranceWithGuideCheckFunction(SignFirstTimeActivity.this, currentUser, currentInstallation);

                                                        } else {

                                                            e.printStackTrace();

                                                        }

                                                    }
                                                });

                                            } else {
                                                //이미 같은 메일을 사용하고 있는 경우 에러 발생
                                                e.printStackTrace();
                                                Toast.makeText(SignFirstTimeActivity.this, "이메일이 이미 등록되어 있습니다.", Toast.LENGTH_SHORT).show();

                                                if(name != null){

                                                    currentUser.put("name", name + "(트위터)");

                                                }

                                                if(pic_url != null){

                                                    currentUser.put("pic_url", pic_url);

                                                }

                                                currentUser.put("user_role_type", "patron");
                                                currentUser.put("visit", 0);
                                                currentUser.put("activity", "SignFirstTimeActivity");
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

                                                                        functionBase.enteranceWithGuideCheckFunction(SignFirstTimeActivity.this, currentUser, currentInstallation);

                                                                    } else {

                                                                        e.printStackTrace();

                                                                    }

                                                                }
                                                            });

                                                        } else {
                                                            //이미 같은 메일을 사용하고 있는 경우 에러 발생
                                                            e.printStackTrace();
                                                            Toast.makeText(SignFirstTimeActivity.this, "문제가 발생 했습니다.", Toast.LENGTH_SHORT).show();

                                                        }

                                                    }
                                                });


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

                        e.printStackTrace();
                        Log.d("error message2", e.getMessage());
                        if(e.getMessage().contains("already exists")){

                            Toast.makeText(SignFirstTimeActivity.this, "이메일이 이미 등록되어 있습니다.", Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(SignFirstTimeActivity.this, "실패", Toast.LENGTH_SHORT).show();

                        }


                    }


                }
            });

        } else if(view.getId() == R.id.login_button){

            Intent intent = new Intent(getApplicationContext(), LoginFirstTimeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            finish();

        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, "OnActivityResult", Toast.LENGTH_SHORT).show();

        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);

    }

}
