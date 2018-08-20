package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SublimePickerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import me.gujun.android.taggroup.TagGroup;

public class SerieseManagerDetailActivity extends AppCompatActivity {

    private String artworkId;
    private String parentId;
    private String parent_status;
    private ImageView artwork_image;
    private TextView title;
    private BootstrapDropDown price_type;
    private EditText price;
    private TextView selected_open_date;
    private BootstrapButton calendar_opendate_button;
    private TextView selected_free_date;
    private BootstrapButton calendar_freedate_button;
    private LinearLayout free_date_picker;

    private LinearLayout status_change_button;
    private LinearLayout delete_button;
    private LinearLayout save_button;

    private RequestManager requestManager;

    private SelectedDate mSelectedDate;
    private int mHour, mMinute;
    private String mRecurrenceOption, mRecurrenceRule;

    private int year, day, month;
    private String requestType;
    private String contentType;
    private String openType;
    private String priceType;
    private Date openDate;
    private Date freeDate;
    private String openDateString;
    private String freeDateString;


    private Boolean openFlag;
    private Boolean openSelectedFlag;
    private Boolean freeFlag;
    private Boolean freeSelectedFlag;

    private ParseUser currentUser;

    private FunctionBase functionBase;

    private ParseObject parentObject;
    private Boolean parentObFlag;
    private Boolean ad_flag;

    private int currentOrder;
    private ParseObject pointObject;

    private Boolean commercialUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seriese_manager_detail);

        Intent intent = getIntent();

        artworkId = intent.getExtras().getString("artworkId");
        parentId = intent.getExtras().getString("parentId");
        parent_status = intent.getExtras().getString("parent_status");

        requestManager = Glide.with(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(this, getApplicationContext());

        artwork_image = (ImageView) findViewById(R.id.artwork_image);
        title = (TextView) findViewById(R.id.title);
        price_type = (BootstrapDropDown) findViewById(R.id.price_type);
        price = (EditText) findViewById(R.id.price);
        selected_open_date = (TextView) findViewById(R.id.selected_open_date);
        calendar_opendate_button = (BootstrapButton) findViewById(R.id.calendar_opendate_button);
        selected_free_date = (TextView) findViewById(R.id.selected_free_date);
        calendar_freedate_button = (BootstrapButton) findViewById(R.id.calendar_freedate_button);
        free_date_picker = (LinearLayout) findViewById(R.id.free_date_picker);
        free_date_picker.setVisibility(View.GONE);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("연재 작품 관리");

        status_change_button = (LinearLayout) findViewById(R.id.status_change_button);
        delete_button = (LinearLayout) findViewById(R.id.delete_button);
        save_button = (LinearLayout) findViewById(R.id.save_button);

        openFlag = false;
        openSelectedFlag = false;
        freeFlag = false;
        freeSelectedFlag = false;
        parentObFlag = false;
        ad_flag = false;

        commercialUser = false;

        ParseQuery parentQuery = ParseQuery.getQuery("ArtistPost");
        parentQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e==null){

                    parentObject = object;
                    parentObFlag = true;



                } else {

                    parentObFlag = false;
                    e.printStackTrace();
                }

            }

        });

        if(currentUser != null){

            currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject pointOb, ParseException e) {

                    if(e==null){

                        pointObject = pointOb;
                        commercialUser = pointObject.getBoolean("commercial_user_flag");

                    } else {

                        e.printStackTrace();
                    }

                }
            });

        }


        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.include("commercial");
        query.getInBackground(artworkId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject postOb, ParseException e) {

                if(e==null){

                    if(postOb.get("commercial") != null){

                        ParseObject commercial = postOb.getParseObject("commercial");

                        if(commercial.get("open_date") != null){

                            openDate = commercial.getDate("open_date");
                            openDateString = functionBase.dateToStringForDisplay(openDate).replace(".", "-");;
                            selected_open_date.setText( openDateString );
                            openSelectedFlag = true;

                        } else {

                            Date currentTime = new Date();

                            settingDateData(currentTime);

                        }


                        if(postOb.get("ad_flag") != null){

                            ad_flag = postOb.getBoolean("ad_flag");

                        } else {

                            ad_flag = false;
                        }



                        if(commercial.get("type") != null){

                            if(commercial.getString("type").equals("free")){

                                price_type.setText("무료");
                                priceType = "free";
                                price.setText("");

                                price.setFocusableInTouchMode(false);
                                price.setFocusable(false);
                                price.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        TastyToast.makeText(getApplicationContext(), "가격정책이 무료로 선택되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    }
                                });

                                freeSelectedFlag = false;

                            } else if( commercial.getString("type").equals("charge") ){

                                price_type.setText("유료");
                                priceType = "charge";
                                price.setText( String.valueOf( commercial.getInt("price") ) );

                                freeSelectedFlag = false;

                            } else if( commercial.getString("type").equals("preview_charge") ){

                                price_type.setText("부분유료(미리보기 구매)");
                                priceType = "preview_charge";
                                price.setText( String.valueOf( commercial.getInt("price") ) );

                                free_date_picker.setVisibility(View.VISIBLE);
                                freeDate = commercial.getDate("free_date");
                                freeDateString = functionBase.dateToStringForDisplay(freeDate).replace(".", "-");
                                selected_free_date.setText( freeDateString );

                                freeSelectedFlag = true;

                            }



                        } else {

                            freeSelectedFlag = false;
                        }

                    } else {

                        if(postOb.get("ad_flag") != null){

                            ad_flag = postOb.getBoolean("ad_flag");

                        } else {

                            ad_flag = false;
                        }

                        Date currentTime = new Date();

                        settingDateData(currentTime);

                    }





                    if(postOb.get("image_cdn") != null){

                        requestManager
                                .load(functionBase.imageUrlBase300 + postOb.getString("image_cdn") )
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                .transition(new DrawableTransitionOptions().crossFade())
                                .into(artwork_image);

                    } else if(postOb.get("image_youtube") != null){

                        requestManager
                                .load(postOb.getString("image_youtube") )
                                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                .transition(new DrawableTransitionOptions().crossFade())
                                .into(artwork_image);

                    } else {

                        artwork_image.setImageResource(R.drawable.image_background);

                    }

                    if(postOb.get("title") != null){

                        title.setText( postOb.getString("title") );

                    } else {

                        title.setText( postOb.getString("body") );

                    }


                    price_type.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View v, int id) {

                            if(id==0){

                                priceType = "free";
                                price_type.setText("무료");
                                free_date_picker.setVisibility(View.GONE);

                                price.setText("");
                                price.setFocusableInTouchMode(false);
                                price.setFocusable(false);
                                price.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        TastyToast.makeText(getApplicationContext(), "가격정책이 무료로 선택되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    }
                                });


                            } else if(id==1){

                                Log.d("adFlag", ad_flag.toString());

                                if(commercialUser){

                                    if(ad_flag){

                                        TastyToast.makeText(getApplicationContext(), "광고가 설정된 게시물은 유료화 할 수 없습니다. 자동으로 무료가 선택됩니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        priceType = "free";
                                        price_type.setText("무료");
                                        free_date_picker.setVisibility(View.GONE);

                                        price.setText("");
                                        price.setFocusableInTouchMode(false);
                                        price.setFocusable(false);
                                        price.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                TastyToast.makeText(getApplicationContext(), "가격정책이 무료로 선택되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                            }
                                        });

                                    } else {

                                        priceType = "charge";
                                        price_type.setText("유료");
                                        free_date_picker.setVisibility(View.GONE);

                                        price.setFocusableInTouchMode(true);
                                        price.setFocusable(true);
                                        price.setOnClickListener(null);

                                    }

                                } else {

                                    TastyToast.makeText(getApplicationContext(), "팔로워 30명 이상, 사이포인트 500 이상이 되면 유료화를 신청하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                    priceType = "free";
                                    price_type.setText("무료");
                                    free_date_picker.setVisibility(View.GONE);

                                    price.setText("");
                                    price.setFocusableInTouchMode(false);
                                    price.setFocusable(false);
                                    price.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            TastyToast.makeText(getApplicationContext(), "가격정책이 무료로 선택되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        }
                                    });

                                }



                            } else if(id==2){

                                Log.d("adFlag", ad_flag.toString());

                                if(commercialUser){

                                    if(ad_flag){

                                        TastyToast.makeText(getApplicationContext(), "광고가 설정된 게시물은 유료화 할 수 없습니다. 자동으로 무료가 선택됩니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                        priceType = "free";
                                        price_type.setText("무료");
                                        free_date_picker.setVisibility(View.GONE);

                                        price.setText("");
                                        price.setFocusableInTouchMode(false);
                                        price.setFocusable(false);
                                        price.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                TastyToast.makeText(getApplicationContext(), "가격정책이 무료로 선택되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                            }
                                        });

                                    } else {

                                        priceType = "preview_charge";
                                        price_type.setText("부분유료(미리보기 구매)");
                                        free_date_picker.setVisibility(View.VISIBLE);

                                        price.setFocusableInTouchMode(true);
                                        price.setFocusable(true);
                                        price.setOnClickListener(null);

                                    }

                                } else {

                                    TastyToast.makeText(getApplicationContext(), "팔로워 30명 이상, 사이포인트 500 이상이 되면 유료화를 신청하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);
                                    priceType = "free";
                                    price_type.setText("무료");
                                    free_date_picker.setVisibility(View.GONE);

                                    price.setText("");
                                    price.setFocusableInTouchMode(false);
                                    price.setFocusable(false);
                                    price.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            TastyToast.makeText(getApplicationContext(), "가격정책이 무료로 선택되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                        }
                                    });

                                }

                            }

                        }
                    });


                    calendar_opendate_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            openFlag = true;
                            freeFlag = false;

                            SublimePickerFragment pickerFrag = new SublimePickerFragment();
                            pickerFrag.setCallback(mFragmentCallback);


                            pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                            pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");

                        }
                    });

                    calendar_freedate_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            openFlag = false;
                            freeFlag = true;

                            SublimePickerFragment pickerFrag = new SublimePickerFragment();
                            pickerFrag.setCallback(mFragmentCallback);


                            pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                            pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");


                        }
                    });


                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(parentObFlag){

                                Log.d("current_order2", String.valueOf(parentObject.getJSONArray("order_array")));

                                String inputPrice = price.getText().toString();

                                HashMap<String, Object> params = new HashMap<>();

                                params.put("artworkId", artworkId);
                                params.put("key", currentUser.getSessionToken());
                                params.put("open_date", openDateString);
                                params.put("seriese_status", parent_status);


                                if(priceType != null){

                                    if(!ad_flag){

                                        //광고가 없는 경우 유료 가능
                                        if(priceType.equals("charge")) {

                                            params.put("type", priceType);
                                            params.put("price", inputPrice);



                                        } else if(priceType.equals("preview_charge")){

                                            params.put("type", priceType);
                                            params.put("price", inputPrice);
                                            params.put("free_date", freeDateString);



                                        }

                                    } else {
                                        //광고가 있는 경우 무료여야만 함
                                        if(priceType.equals("free")){

                                            params.put("type", priceType);

                                        }

                                    }

                                }


                                ParseCloud.callFunctionInBackground("seriese_info_save", params, new FunctionCallback<Map<String, Object>>() {

                                    public void done(Map<String, Object> mapObject, ParseException e) {

                                        if (e == null) {

                                            if(mapObject.get("status").toString().equals("true")){

                                                TastyToast.makeText(getApplicationContext(), "저장 성공", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                finish();

                                            } else {

                                                TastyToast.makeText(getApplicationContext(), "저장에 실패 했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                            }

                                        } else {

                                            Log.d("error", "error");
                                            e.printStackTrace();

                                        }
                                    }
                                });



                            } else {

                                TastyToast.makeText(getApplicationContext(), "연재 정보를 아직 불러오고 있습니다. 조금만 기다려주세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                            }



                        }
                    });


                    status_change_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            MaterialDialog.Builder builder = new MaterialDialog.Builder(SerieseManagerDetailActivity.this);

                            builder.title("확인");
                            builder.content("비공개로 전환 하시겠습니까?");
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

                                    postOb.put("open_flag", false);
                                    postOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            TastyToast.makeText(getApplicationContext(), "비공개로 전환되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                            finish();

                                        }
                                    });

                                }
                            });
                            builder.show();

                        }
                    });


                    ParseQuery parentQuery = ParseQuery.getQuery("ArtistPost");
                    parentQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(final ParseObject parentObject, ParseException e) {

                            if(e==null){


                                delete_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        MaterialDialog.Builder builder = new MaterialDialog.Builder(SerieseManagerDetailActivity.this);

                                        builder.title("확인");
                                        builder.content("시리즈에서 제외 하시겠습니까?");
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


                                                parentObject.increment("seriese_count" , -1);
                                                ParseRelation serieseRelation = parentObject.getRelation("seriese_item");
                                                serieseRelation.remove(postOb);

                                                parentObject.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if(e==null){

                                                            postOb.put("seriese_in", false);
                                                            postOb.put("open_flag", true);
                                                            postOb.put("seriese_parent", JSONObject.NULL);
                                                            postOb.put("level", JSONObject.NULL);
                                                            postOb.saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(ParseException e) {

                                                                    if(e==null){

                                                                        TastyToast.makeText(getApplicationContext(), "시리즈에서 제외 되어 작품관리로 이동했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                        finish();

                                                                    } else {

                                                                        TastyToast.makeText(getApplicationContext(), "문제가 발생했습니다. 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                    }

                                                                }
                                                            });


                                                        } else {

                                                            e.printStackTrace();
                                                        }


                                                    }
                                                });




                                            }
                                        });
                                        builder.show();


                                    }
                                });

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

    SublimePickerFragment.Callback mFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {

            mSelectedDate = selectedDate;
            mHour = hourOfDay;
            mMinute = minute;
            mRecurrenceOption = recurrenceOption != null ? recurrenceOption.name() : "n/a";
            mRecurrenceRule = recurrenceRule != null ? recurrenceRule : "n/a";


            Date currentSelectedDate = mSelectedDate.getStartDate().getTime();
            year = currentSelectedDate.getYear();
            month = currentSelectedDate.getMonth();
            day = currentSelectedDate.getDate();


            Date now = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.set(now.getYear()+1900, now.getMonth(), now.getDate(), 0, 0, 1);
            Date compareDate = calendar.getTime();

            Log.d("compareDate", compareDate.toString());
            Log.d("currentSelectedDate", currentSelectedDate.toString());

            if(currentSelectedDate.before(compareDate)){
                //선택된 시간이 지금보다 이전일때 --> no no no 안돼 돌아가
                TastyToast.makeText(getApplicationContext(), "오늘보다 이후 시간을 다시 선택해 주세요", TastyToast.LENGTH_LONG, TastyToast.INFO);


            } else {
                //선택된 시간이 지금보다 이후 일때 --> 뾰로롱~ 통과~!
                settingDateData(currentSelectedDate);

            }

        }

    };


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


    private void settingDateData(Date inputDate){

        int year = inputDate.getYear();
        int month = inputDate.getMonth();
        int date = inputDate.getDate();

        String initDateString = String.valueOf(year + 1900) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(date);

        if(openFlag){

            selected_open_date.setText(initDateString);
            openDate = inputDate;
            openDateString = initDateString;
            openSelectedFlag = true;

        } else {

            selected_free_date.setText(initDateString);
            freeDate = inputDate;
            freeDateString = initDateString;
            freeSelectedFlag = true;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

}
