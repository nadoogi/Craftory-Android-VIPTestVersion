package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PhoneInputActivity extends AppCompatActivity {

    private LinearLayout back_button;
    private TextView back_button_text;

    private EditText phone;
    private ImageView phone_reset;

    private LinearLayout sms_button;

    private EditText confirm_num;
    private ImageView confirm_num_reset;

    private LinearLayout confirm_button;

    private ParseUser currentUser;
    private Boolean confirm_msg_sent;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_input);

        currentUser = ParseUser.getCurrentUser();

        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);

        phone = (EditText) findViewById(R.id.phone);
        phone_reset = (ImageView) findViewById(R.id.phone_reset);

        sms_button = (LinearLayout) findViewById(R.id.sms_button);

        confirm_num = (EditText) findViewById(R.id.confirm_num);
        confirm_num_reset = (ImageView) findViewById(R.id.confirm_num_reset);
        confirm_button = (LinearLayout) findViewById(R.id.confirm_button);

        confirm_msg_sent = false;

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText( "전화번호 인증" );

        sms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNum = phone.getText().toString();
                phoneNum = phoneNum.replace("-", "");


                String secondNum = phoneNum.substring(3, 6);
                String thirdNum = phoneNum.substring(6, 9);

                if(phoneNum.length() != 0){

                    String firstNum = phoneNum.substring(0, 3);

                    if(firstNum.equals("010")){

                        if(phoneNum.length() == 11){

                            HashMap<String, Object> params = new HashMap<>();

                            params.put("key", currentUser.getSessionToken());

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);
                            params.put("phone", phoneNum);

                            final String finalPhoneNum = phoneNum;
                            ParseCloud.callFunctionInBackground("sms_send", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        Log.d("tag", mapObject.toString());

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(getApplicationContext(), "인증번호가 성공적으로 발송되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                            confirm_msg_sent = true;
                                            phoneNumber = finalPhoneNum;

                                        } else {

                                            TastyToast.makeText(getApplicationContext(), "발송 실패 했습니다. 전화번호를 확인하시고 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                        }

                                    } else {

                                        e.printStackTrace();

                                    }
                                }
                            });

                        } else {

                            phone.setError("전화번호 자릿수가 맞지 않습니다.");

                        }

                    } else if(firstNum.equals("011") || firstNum.equals("016") || firstNum.equals("017") || firstNum.equals("018") || firstNum.equals("019") ) {


                    } else {

                        phone.setError("전화번호 앞자리가 맞지 않습니다. 010 또는 011을 입력하세요.");

                    }

                } else {

                    phone.setError("전화번호를 입력하세요");

                }

            }
        });

        phone_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.setText("");
            }
        });

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentUser != null){

                    if(confirm_msg_sent){

                        final String confirmNumber = confirm_num.getText().toString();

                        currentUser.fetchInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject fetchedUser, ParseException e) {

                                if(e== null){

                                    if(confirmNumber.equals(fetchedUser.getString("phone_confirm_code"))){

                                        TastyToast.makeText(getApplicationContext(), "인증번호 완료. 전화번호를 저장 합니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                        HashMap<String, Object> params = new HashMap<>();

                                        params.put("key", currentUser.getSessionToken());

                                        Date uniqueIdDate = new Date();
                                        String uniqueId = uniqueIdDate.toString();

                                        params.put("uid", uniqueId);
                                        params.put("phone", phoneNumber);

                                        ParseCloud.callFunctionInBackground("phone_num_save", params, new FunctionCallback<Map<String, Object>>() {

                                            public void done(Map<String, Object> mapObject, ParseException e) {

                                                if (e == null) {

                                                    Log.d("tag", mapObject.toString());

                                                    if(mapObject.get("status").toString().equals("true")){

                                                        TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                        finish();

                                                    } else {

                                                        TastyToast.makeText(getApplicationContext(), "저장 실패, 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                    }

                                                } else {

                                                    e.printStackTrace();

                                                }
                                            }
                                        });

                                    } else {

                                        TastyToast.makeText(getApplicationContext(), "인증번호가 다릅니다. 다시 확인해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }

                                } else {



                                }

                            }
                        });


                    } else {

                        TastyToast.makeText(getApplicationContext(), "인증번호가 아직 발송되지 않았습니다. 확인해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                } else {

                    TastyToast.makeText(getApplicationContext(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);

                }



            }
        });

        confirm_num_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirm_num.setText("");

            }
        });

    }
}
