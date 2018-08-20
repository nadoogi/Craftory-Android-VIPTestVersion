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
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class FundingJoinActivity extends AppCompatActivity {

    private LinearLayout address_button;
    private LinearLayout exist_button;

    private String patronId;

    private EditText to;
    private ImageView to_reset;

    private EditText address;
    private ImageView address_reset;

    private EditText address_detail;
    private ImageView address_detail_reset;

    private EditText phone;
    private ImageView phone_reset;

    private LinearLayout my_phone_button;

    private EditText message;
    private ImageView message_reset;

    private LinearLayout save_button;

    private FunctionBase functionBase;

    private TextView current_box;
    private TextView item_price;
    private TextView delivery_cost;
    private TextView remain_box;
    private LinearLayout charge_button;

    private ParseUser currentUser;

    private LinearLayout back_button;
    private TextView back_button_text;

    private Boolean myPhoneExist = false;
    private ParseObject personalOb;
    private ParseObject patronObject;

    private HashMap<String, String> phoneData;

    private TextView save_button_text;
    private int deliveryCost;

    private Boolean deliveryInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_join);

        Intent intent = getIntent();

        patronId = intent.getExtras().getString("patronId");
        deliveryCost = 0;
        deliveryInput = false;

        functionBase = new FunctionBase(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        address_button = (LinearLayout) findViewById(R.id.address_button);

        to = (EditText) findViewById(R.id.to);
        to_reset = (ImageView) findViewById(R.id.to_reset);

        address = (EditText) findViewById(R.id.address);
        address_reset = (ImageView) findViewById(R.id.address_reset);

        address_detail = (EditText) findViewById(R.id.address_detail);
        address_detail_reset = (ImageView) findViewById(R.id.address_detail_reset);

        phone = (EditText) findViewById(R.id.phone);
        phone_reset = (ImageView) findViewById(R.id.phone_reset);

        my_phone_button = (LinearLayout) findViewById(R.id.my_phone_button);

        message = (EditText) findViewById(R.id.message);
        message_reset = (ImageView) findViewById(R.id.message_reset);

        save_button = (LinearLayout) findViewById(R.id.save_button);

        charge_button = (LinearLayout) findViewById(R.id.charge_button);
        current_box = (TextView) findViewById(R.id.current_box);

        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);

        save_button_text = (TextView) findViewById(R.id.save_button_text);

        item_price = (TextView) findViewById(R.id.price);
        delivery_cost = (TextView) findViewById(R.id.delivery_cost);
        remain_box = (TextView) findViewById(R.id.remain_box);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("굿즈펀딩 참여");


        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.include("item");
        query.getInBackground(patronId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject patronOb, ParseException e) {

                if(e==null){

                    patronObject = patronOb;
                    initPage(patronObject);

                } else {

                    e.printStackTrace();
                }

            }

        });

        address.setFocusable(false);
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(), NewAddressActivity.class);
                intent1.putExtra( "patronId", patronId );
                startActivityForResult(intent1, functionBase.REQUEST_CODE);

            }
        });

        address_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(), NewAddressActivity.class);
                intent1.putExtra( "patronId", patronId );
                startActivityForResult(intent1, functionBase.REQUEST_CODE);

            }
        });



        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(patronObject.getString("progress").equals("production")){

                    TastyToast.makeText(getApplicationContext(), "제작 중 입니다. 제작에 들어간 펀딩은 참여할수 없습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else {

                    save_button.setClickable(false);
                    save_button_text.setText("저장 중...");

                    //주문서 저장
                    String toName = to.getText().toString();
                    String addressString = address.getText().toString();
                    String addressDetailString = address_detail.getText().toString();
                    String phoneString = phone.getText().toString();
                    String messageString = message.getText().toString();

                    if(toName.length() == 0){

                        to.setError("받는 사람을 입력하세요");
                        save_button.setClickable(true);
                        save_button_text.setText("저장 하기");

                    } else if(phoneString.length() == 0){

                        phone.setError("연락처를 입력하세요");
                        save_button.setClickable(true);
                        save_button_text.setText("저장 하기");

                    } else if(addressString.length() == 0){

                        address.setError("주소를 입력하세요");
                        save_button.setClickable(true);
                        save_button_text.setText("저장 하기");

                    } else {

                        HashMap<String, Object> params = new HashMap<>();

                        params.put("key", currentUser.getSessionToken());

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();

                        params.put("uid", uniqueId);

                        params.put("toName", toName);
                        params.put("phone", phoneString);
                        params.put("message", messageString);
                        params.put("patronId", patronId);
                        params.put("item_price", patronObject.getParseObject("item").getInt("price"));
                        params.put("delivery_cost", deliveryCost);
                        params.put("address_detail", addressDetailString);

                        Log.d("patronObject", String.valueOf(patronObject.getParseObject("item").getInt("price")));
                        Log.d("patronObject", String.valueOf(deliveryCost));


                        //주소 정보
                        params.put("roadAddr", (String) phoneData.get("roadAddr"));
                        params.put("roadAddrPart1", (String) phoneData.get("roadAddrPart1") );
                        params.put("roadAddrPart2", (String) phoneData.get("roadAddrPart2") );
                        params.put("jibunAddr", (String) phoneData.get("jibunAddr"));
                        params.put("engAddr", (String) phoneData.get("engAddr"));
                        params.put("zipNo", (String) phoneData.get("zipNo"));
                        params.put("admCd", (String) phoneData.get("admCd"));
                        params.put("rnMgtSn", (String) phoneData.get("rnMgtSn"));
                        params.put("bdMgtSn", (String) phoneData.get("bdMgtSn"));
                        params.put("detBdNmList", (String) phoneData.get("detBdNmList"));
                        params.put("bdNm", (String) phoneData.get("bdNm"));
                        params.put("bdKdcd", (String) phoneData.get("bdKdcd"));
                        params.put("siNm", (String) phoneData.get("siNm"));
                        params.put("sggNm", (String) phoneData.get("sggNm"));
                        params.put("emdNm", (String) phoneData.get("emdNm"));
                        params.put("liNm", (String) phoneData.get("liNm"));
                        params.put("rn", (String) phoneData.get("rn"));
                        params.put("udrtYn", (String) phoneData.get("udrtYn"));
                        params.put("buldMnnm" , (String) phoneData.get("buldMnnm"));
                        params.put("buldSlno", (String) phoneData.get("buldSlno"));
                        params.put("mtYn", (String) phoneData.get("mtYn"));
                        params.put("lnbrMnnm", (String) phoneData.get("lnbrMnnm"));
                        params.put("lnbrSlno", (String) phoneData.get("lnbrSlno"));
                        params.put("emdNo", (String) phoneData.get("emdNo"));

                        ParseCloud.callFunctionInBackground("funding_join", params, new FunctionCallback<Map<String, Object>>() {

                            public void done(Map<String, Object> mapObject, ParseException e) {

                                if (e == null) {

                                    if(mapObject.get("status").toString().equals("true")){

                                        TastyToast.makeText(getApplicationContext(), "참여가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        finish();

                                    } else {

                                        TastyToast.makeText(getApplicationContext(), "참여가 실패했습니다. 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        save_button.setClickable(true);
                                        save_button_text.setText("저장 하기");

                                    }

                                } else {

                                    e.printStackTrace();
                                    save_button.setClickable(true);
                                    save_button_text.setText("저장 하기");

                                }
                            }
                        });

                    }

                }

            }
        });


        my_phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(myPhoneExist == true){

                    phone.setText(personalOb.getString("phone"));

                } else {

                    TastyToast.makeText(getApplicationContext(), "내 연락처가 없습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    Intent phoneIntent = new Intent(getApplicationContext(), PhoneInputActivity.class);
                    startActivity(phoneIntent);

                }

            }
        });

    }


    private void initPage(final ParseObject patronObject){

        if(currentUser != null){

            currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject pointOb, ParseException e) {

                    if(e==null){

                        int currentBox = pointOb.getInt("current_point");
                        int priceBox = patronObject.getInt("price");
                        int remainBox = currentBox - priceBox - deliveryCost;

                        current_box.setText(functionBase.makeComma( currentBox ) + " BOX");
                        item_price.setText(functionBase.makeComma( priceBox ) + " BOX");
                        delivery_cost.setText(functionBase.makeComma(deliveryCost) + " BOX");
                        remain_box.setText(functionBase.makeComma(remainBox) + " BOX");

                    } else {

                        e.printStackTrace();
                    }

                }
            });

            if(currentUser.get("personal") != null){

                currentUser.getParseObject("personal").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject personalObject, ParseException e) {

                        if(e==null){

                            if(personalObject.get("phone") == null || personalObject.getString("phone").length() == 0){

                                myPhoneExist = false;

                            } else {

                                myPhoneExist = true;
                                personalOb = personalObject;
                                phone.setText(personalOb.getString("phone"));

                            }

                        } else {

                            myPhoneExist = false;
                            e.printStackTrace();
                        }

                    }
                });

            } else {

                myPhoneExist = false;

            }



            charge_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent chargeIntent = new Intent(getApplicationContext(), ChargeActivity.class);
                    startActivity(chargeIntent);

                }
            });

        } else {

            if(current_box != null){

                current_box.setText("로그인이 필요 합니다.");

            }

            if(charge_button != null){

                charge_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(loginIntent);

                    }
                });

            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == functionBase.REQUEST_CODE && resultCode == RESULT_OK && data != null){

            //도로명주소 전체
            String roadAddr = data.getExtras().getString("roadAddr");
            //도로명주소
            String roadAddrPart1 = data.getExtras().getString("roadAddrPart1");
            //참고주소
            String roadAddrPart2 = data.getExtras().getString("roadAddrPart2");
            //지번주소
            String jibunAddr = data.getExtras().getString("jibunAddr");
            //영문도로명주소
            String engAddr = data.getExtras().getString("engAddr");
            //우편번호
            String zipNo = data.getExtras().getString("zipNo");
            //행정구역코드
            String admCd = data.getExtras().getString("admCd");
            //도로명 코드
            String rnMgtSn = data.getExtras().getString("rnMgtSn");
            //건물관리번호
            String bdMgtSn = data.getExtras().getString("bdMgtSn");
            //상세건물명
            String detBdNmList = data.getExtras().getString("detBdNmList");
            //건물명
            String bdNm = data.getExtras().getString("bdNm");
            //공동주택여부
            String bdKdcd = data.getExtras().getString("bdKdcd");
            //시도명
            String siNm = data.getExtras().getString("siNm");
            //시군구명
            String sggNm = data.getExtras().getString("sggNm");
            //읍면동면
            String emdNm = data.getExtras().getString("emdNm");
            //법정리명
            String liNm = data.getExtras().getString("liNm");
            //도로명
            String rn = data.getExtras().getString("rn");
            //지하여부
            String udrtYn =data.getExtras().getString("udrtYn");
            //건물본번
            String buldMnnm = data.getExtras().getString("buldMnnm");
            //건물부번
            String buldSlno = data.getExtras().getString("buldSlno");
            //산여부
            String mtYn = data.getExtras().getString("mtYn");
            //지번본번
            String lnbrMnnm = data.getExtras().getString("lnbrMnnm");
            //지번부번
            String lnbrSlno = data.getExtras().getString("lnbrSlno");
            //읍면동일련번호
            String emdNo = data.getExtras().getString("emdNo");

            address.setText(roadAddr);
            Log.d("unique", admCd);

            if(siNm.contains("제주")){

                deliveryCost = patronObject.getParseObject("item").getInt("delivery_island");
                deliveryInput = true;

            } else {

                deliveryCost = patronObject.getParseObject("item").getInt("delivery_local");
                deliveryInput = true;

            }

            phoneData = new HashMap<>();

            phoneData.put("roadAddr", roadAddr);
            phoneData.put("roadAddrPart1", roadAddrPart1);
            phoneData.put("roadAddrPart2", roadAddrPart2);
            phoneData.put("jibunAddr", jibunAddr);
            phoneData.put("engAddr", engAddr);
            phoneData.put("zipNo", zipNo);
            phoneData.put("admCd", admCd);
            phoneData.put("rnMgtSn", rnMgtSn);
            phoneData.put("bdMgtSn", bdMgtSn);
            phoneData.put("detBdNmList", detBdNmList);
            phoneData.put("bdNm", bdNm);
            phoneData.put("bdKdcd", bdKdcd);
            phoneData.put("siNm", siNm);
            phoneData.put("sggNm", sggNm);
            phoneData.put("emdNm", emdNm);
            phoneData.put("liNm", liNm);
            phoneData.put("rn", rn);
            phoneData.put("udrtYn", udrtYn);
            phoneData.put("buldMnnm" ,buldMnnm);
            phoneData.put("buldSlno", buldSlno);
            phoneData.put("mtYn", mtYn);
            phoneData.put("lnbrMnnm", lnbrMnnm);
            phoneData.put("lnbrSlno", lnbrSlno);
            phoneData.put("emdNo", emdNo);

            initPage(patronObject);

        }


    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(patronObject != null){

            initPage(patronObject);

        }

    }
}
