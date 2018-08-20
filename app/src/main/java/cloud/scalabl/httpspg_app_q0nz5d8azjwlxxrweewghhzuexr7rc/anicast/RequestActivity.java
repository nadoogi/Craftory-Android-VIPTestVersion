package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SublimePickerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;



public class RequestActivity extends AppCompatActivity {

    private ImageView represent_image;
    private LinearLayout represent_upload;
    private BootstrapDropDown creation_type;
    private BootstrapDropDown open_type;
    private BootstrapDropDown price_type;
    private EditText profit_share_ratio;
    private EditText target_point;
    private LinearLayout calendar_button;

    private EditText title;
    private EditText body;
    private TagGroup tagGroup;

    private LinearLayout save_button;

    private TextView selected_date;


    SelectedDate mSelectedDate;
    int mHour, mMinute;
    String mRecurrenceOption, mRecurrenceRule;

    int year, day, month;
    String requestType;
    String contentType;
    String openType;
    String priceType;
    Date endDate;


    Boolean dateSelectFlag;
    Boolean requestTypeSelectFlag;
    Boolean contentTypeSelectFlag;
    Boolean openTypeSelectFlag;
    Boolean priceTypeSelectFlag;

    private ParseUser currentUser;
    private ArtistPost artistPostOb;

    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;

    private ParseObject userObject;

    private FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        Intent intent = getIntent();
        String userId = intent.getExtras().getString("userId");

        //getSupportActionBar().setTitle("제작요청");

        represent_image = (ImageView) findViewById(R.id.represent_image);
        represent_upload = (LinearLayout) findViewById(R.id.represent_upload);
        creation_type = (BootstrapDropDown) findViewById(R.id.creation_type);
        open_type = (BootstrapDropDown) findViewById(R.id.open_type);
        price_type = (BootstrapDropDown) findViewById(R.id.price_type);
        profit_share_ratio = (EditText) findViewById(R.id.profit_share_ratio);
        target_point = (EditText) findViewById(R.id.target_point);
        calendar_button = (LinearLayout) findViewById(R.id.calendar_opendate_button);
        title = (EditText) findViewById(R.id.title);
        body = (EditText) findViewById(R.id.post_body);
        tagGroup = (TagGroup) findViewById(R.id.tag_group);

        save_button = (LinearLayout) findViewById(R.id.save_button);

        selected_date = (TextView) findViewById(R.id.selected_date);
        ScrollView sv = (ScrollView) findViewById(R.id.sv);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        back_button_text.setText("제작요청");

        represent_image.setFocusableInTouchMode(true);
        represent_image.requestFocus();
        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(this, getApplicationContext());

        endDate = null;
        dateSelectFlag = false;
        requestTypeSelectFlag = false;
        contentTypeSelectFlag = false;
        openTypeSelectFlag = false;
        priceTypeSelectFlag = false;
        tempFile = null;

        artistPostOb = new ArtistPost();

        ParseQuery userQuery = ParseQuery.getQuery("_User");
        userQuery.getInBackground(userId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject userOb, ParseException e) {

                if(e==null){

                    userObject = userOb;

                    represent_upload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                            intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                            startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

                        }
                    });


                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            save_button.setClickable(false);

                            startParseObject();

                            String[] taglist = tagGroup.getTags();
                            String tagString = "";

                            for(String tagItem : taglist){


                                tagString += tagItem + ",";

                            }


                            String bodyString = body.getText().toString();
                            String titleString = title.getText().toString();
                            String profitShareRatio = profit_share_ratio.getText().toString();
                            String targetPointString = target_point.getText().toString();

                            if(titleString == null){

                                TastyToast.makeText(getApplicationContext(), "제목을 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                save_button.setClickable(true);

                            } else if(!contentTypeSelectFlag){

                                TastyToast.makeText(getApplicationContext(), "작품 형태를 선택하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                save_button.setClickable(true);


                            } else if(!openTypeSelectFlag){

                                TastyToast.makeText(getApplicationContext(), "공개 범위를 선택하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                save_button.setClickable(true);


                            } else if(!priceTypeSelectFlag){

                                TastyToast.makeText(getApplicationContext(), "가격 정책을 선택하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                save_button.setClickable(true);

                            } else if(!dateSelectFlag){

                                TastyToast.makeText(getApplicationContext(), "종료일을 선택하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                save_button.setClickable(true);

                            } else if(targetPointString == null || targetPointString.length() == 0){

                                TastyToast.makeText(getApplicationContext(), "후원 목표를 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                save_button.setClickable(true);

                            } else if(finalImage == null){

                                TastyToast.makeText(getApplicationContext(), "작품이 등록되지 않았습니다. 작품을 등록해 주세요!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                save_button.setClickable(true);

                            } else {

                                artistPostOb.put("title", titleString);
                                artistPostOb.put("body", bodyString);
                                artistPostOb.put("tags", tagString);
                                artistPostOb.put("status", false);
                                artistPostOb.put("endDate", endDate);
                                artistPostOb.put("content_type", contentType);
                                artistPostOb.put("open_type", openType);
                                artistPostOb.put("price_type", priceType);
                                artistPostOb.put("target_amount", Integer.parseInt(targetPointString));
                                artistPostOb.put("image_cdn", finalImage);

                                if(priceType.equals("free")){

                                    artistPostOb.put("profit_share_ratio", 0);

                                } else {

                                    artistPostOb.put("profit_share_ratio", Integer.parseInt(profitShareRatio));

                                }

                                artistPostOb.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            currentUser.increment("post_count");
                                            currentUser.increment("patron_request_count");
                                            currentUser.saveInBackground();

                                            final ParseObject socialMSGOb = new ParseObject("SocialMessage");
                                            socialMSGOb.put("user",currentUser);
                                            socialMSGOb.put("target", userOb);
                                            socialMSGOb.put("type", "request");
                                            socialMSGOb.put("progress", "offer");
                                            socialMSGOb.put("status", true);
                                            socialMSGOb.put("artist_post", artistPostOb);
                                            socialMSGOb.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        save_button.setClickable(true);
                                                        TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                        if(!functionBase.parseArrayCheck(currentUser, "my_alert", socialMSGOb.getObjectId())){


                                                            if(!currentUser.getObjectId().equals(socialMSGOb.getParseObject("user").getObjectId())){

                                                                ParseObject myAlert = new ParseObject("MyAlert");
                                                                myAlert.put("from", currentUser);
                                                                myAlert.put("to", socialMSGOb.getParseObject("user"));
                                                                myAlert.put("type", "social_request");
                                                                myAlert.put("artist_post", artistPostOb);
                                                                myAlert.put("social", socialMSGOb);
                                                                myAlert.put("status", true);
                                                                myAlert.saveInBackground();

                                                                currentUser.addAllUnique("my_alert", Arrays.asList(socialMSGOb.getObjectId()));
                                                                currentUser.saveInBackground();

                                                            }



                                                        }


                                                        finish();

                                                    } else {

                                                        e.printStackTrace();
                                                    }

                                                }
                                            });




                                        } else {
                                            save_button.setClickable(true);

                                            e.printStackTrace();
                                        }


                                    }
                                });

                            }


                        }
                    });

                    creation_type.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View v, int id) {

                            switch (id){

                                case 0:

                                    creation_type.setText("웹툰");
                                    contentType = "webtoon";
                                    contentTypeSelectFlag = true;

                                    break;



                                case 1:

                                    creation_type.setText("일러스트/사진");
                                    contentType = "post";
                                    contentTypeSelectFlag = true;

                                    break;

                                case 2:

                                    creation_type.setText("일러스트집/사진집");
                                    contentType = "album";
                                    contentTypeSelectFlag = true;

                                    break;

                                /*
                                case 1:

                                    creation_type.setText("웹소설");
                                    contentType = "novel";
                                    contentTypeSelectFlag = true;

                                    break;

                                case 2:

                                    creation_type.setText("유튜브 영상");
                                    contentType = "youtube";
                                    contentTypeSelectFlag = true;

                                    break;

                                */

                            }

                        }
                    });

                    open_type.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View v, int id) {

                            switch (id){

                                case 0:

                                    open_type.setText("전체 공개");
                                    openType = "openToAll";
                                    openTypeSelectFlag = true;

                                    break;

                                case 1:

                                    open_type.setText("후원자 공개");
                                    openType = "openToPatron";
                                    openTypeSelectFlag = true;

                                    break;


                            }

                        }
                    });

                    price_type.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
                        @Override
                        public void onItemClick(ViewGroup parent, View v, int id) {

                            switch (id){

                                case 0:

                                    price_type.setText("무료");
                                    priceType = "free";
                                    priceTypeSelectFlag = true;

                                    break;

                                case 1:

                                    price_type.setText("유료");
                                    priceType = "charge";
                                    priceTypeSelectFlag = true;

                                    break;


                            }

                        }
                    });


                    calendar_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SublimePickerFragment pickerFrag = new SublimePickerFragment();
                            pickerFrag.setCallback(mFragmentCallback);

                            // Valid options
                            //Bundle bundle = new Bundle();
                            //bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
                            //pickerFrag.setArguments(bundle);

                            pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                            pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");

                        }
                    });

                } else {

                    e.printStackTrace();

                }

            }

        });


    }

    private ArtistPost startParseObject(){


        artistPostOb.put("status", false);
        artistPostOb.put("user", currentUser);
        artistPostOb.put("userId", currentUser.getObjectId());
        artistPostOb.put("comment_count", 0);
        artistPostOb.put("like_count", 0);
        artistPostOb.put("patron_count", 0);
        artistPostOb.put("post_type", "patron");
        artistPostOb.put("request_type", "produceRequest");
        artistPostOb.put("request_to", userObject);
        artistPostOb.saveInBackground();

        return artistPostOb;

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

            String selectedDateString = String.valueOf(year + 1900) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(day);
            selected_date.setText(selectedDateString);
            endDate = currentSelectedDate;
            dateSelectFlag = true;


        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            String imagePath = images.get(0).path;
            String imageName = images.get(0).name;

            UploadCallback callback = new UploadCallback() {
                @Override
                public void onStart(String requestId) {

                    Log.d("id", requestId);
                    //progress_text.setVisibility(View.VISIBLE);

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {

                    Log.d("pregress", String.valueOf(bytes/totalBytes * 100) + "%");
                    final String progressString = String.valueOf(bytes/totalBytes * 100) + "%";
                    //progress_text.setText(progressString);

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {

                    //progress_text.setVisibility(View.GONE);

                    Log.d("resultdata", resultData.get("secure_url").toString());

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    finalImage = uploadTarget;

                    if(tempFile != null){

                        tempFile.delete();

                    }

                    requestManager
                            .load(finalUploadTaget)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {


                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                                    return false;
                                }
                            })
                            .into(represent_image);

                }

                @Override
                public void onError(String requestId, ErrorInfo error) {

                    Log.d("error1", error.toString());
                    TastyToast.makeText(getApplicationContext(), "업로드가 실패 했습니다 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                    Log.d("error2", error.toString());
                    TastyToast.makeText(getApplicationContext(), "업로드가 실패 했습니다 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }
            };

            if(imagePath.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣]+.*")) {

                // 한글이 포함된 문자열
                Log.d("korean", "true");

                String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/craftory";

                File gifFile = new File(imagePath);
                tempFile = new File(sdPath);
                tempFile.mkdirs();

                tempFile = new File(sdPath, "temp.png");

                try {

                    functionBase.copy(gifFile, tempFile);
                    MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).callback(callback).dispatch();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                // 한글이 포함되지 않은 문자열
                Log.d("korean", "false");
                MediaManager.get().upload(imagePath).unsigned(AppConfig.image_preset).callback(callback).dispatch();

            }



        }



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
