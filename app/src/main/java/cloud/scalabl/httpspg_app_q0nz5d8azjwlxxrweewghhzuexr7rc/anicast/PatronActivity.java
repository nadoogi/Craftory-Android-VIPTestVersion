package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapDropDown;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SublimePickerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;


public class PatronActivity extends AppCompatActivity {

    private ImageView represent_image;
    private BootstrapDropDown creation_type;

    private EditText target_point;

    private EditText title;
    private EditText body;
    private TagGroup tagGroup;

    private LinearLayout save_button;

    private TextView selected_date;

    SelectedDate mSelectedDate;
    int mHour, mMinute;
    String mRecurrenceOption, mRecurrenceRule;

    int year, day, month;

    String contentType;
    String priceType;
    String requestType;

    Date endDate;


    Boolean dateSelectFlag;
    Boolean contentTypeSelectFlag;
    Boolean openTypeSelectFlag;
    Boolean priceTypeSelectFlag;
    Boolean rewardTypeSelectFlag;

    private ParseUser currentUser;


    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;


    private EditText patron_reward;


    private FunctionBase functionBase;

    private Switch open_range;
    private Boolean openRangeFlag;

    private EditText min_box;

    private TextView file_status_text;

    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron);

        represent_image = (ImageView) findViewById(R.id.represent_image);
        LinearLayout represent_upload = (LinearLayout) findViewById(R.id.represent_upload);
        creation_type = (BootstrapDropDown) findViewById(R.id.creation_type);
        BootstrapDropDown open_type = (BootstrapDropDown) findViewById(R.id.open_type);


        target_point = (EditText) findViewById(R.id.target_point);
        BootstrapButton calendar_button = (BootstrapButton) findViewById(R.id.calendar_opendate_button);
        title = (EditText) findViewById(R.id.title);
        body = (EditText) findViewById(R.id.post_body);
        tagGroup = (TagGroup) findViewById(R.id.tag_group);
        tagGroup.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
            @Override
            public void onAppend(TagGroup group, String tag) {



                if(!tagString.contains(tag)){

                    tagString.add(tag);

                } else {

                    TastyToast.makeText(getApplicationContext(), "중복 태그는 제외 됩니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }

            }

            @Override
            public void onDelete(TagGroup group, String tag) {

                tagString.remove(tag);
                String[] currentTagArray = functionBase.arrayListToString(tagString);
                tagGroup.setTags(currentTagArray);

            }
        });

        save_button = (LinearLayout) findViewById(R.id.save_button);

        patron_reward = (EditText) findViewById(R.id.patron_reward);
        ImageView reward_reset = (ImageView) findViewById(R.id.reward_reset);
        min_box = (EditText) findViewById(R.id.min_box);


        selected_date = (TextView) findViewById(R.id.selected_date);
        ScrollView sv = (ScrollView) findViewById(R.id.sv);


        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        file_status_text = (TextView) findViewById(R.id.file_status_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        back_button_text.setText("후원 만들기");


        represent_image.setFocusableInTouchMode(true);
        represent_image.requestFocus();
        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getApplicationContext());
        tagString = new ArrayList<>();

        endDate = null;
        dateSelectFlag = false;
        contentTypeSelectFlag = false;
        openTypeSelectFlag = false;
        priceTypeSelectFlag = false;
        tempFile = null;

        functionBase = new FunctionBase(this, getApplicationContext());

        represent_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

            }
        });

        open_range = (Switch) findViewById(R.id.open_range);

        openRangeFlag = false;
        open_range.setChecked(openRangeFlag);


        open_range.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    open_range.setText("후원자");
                    openRangeFlag = true;


                } else {

                    open_range.setText("전체");
                    openRangeFlag = false;

                }

            }
        });


        tag_input_button = (LinearLayout) findViewById(R.id.tag_input_button);
        tag_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TagInputActivity.class);
                intent.putStringArrayListExtra("tags",tagString);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bodyString = body.getText().toString();
                String titleString = title.getText().toString();
                String targetPointString = target_point.getText().toString();
                String patronRewardString = patron_reward.getText().toString();
                String minboxString = min_box.getText().toString();

                if(titleString == null){

                    TastyToast.makeText(getApplicationContext(), "제목을 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    save_button.setClickable(true);

                } else if(!contentTypeSelectFlag){

                    TastyToast.makeText(getApplicationContext(), "작품 형태를 선택하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                    save_button.setClickable(false);

                    int minInt = 0;

                    if(minboxString.equals("0") || minboxString == null || minboxString.length() == 0){

                        minInt = 0;

                    } else {

                        minInt = Integer.parseInt( minboxString );

                    }

                    save_button.setClickable(false);

                    String searchString = "";

                    for(String tagItem : tagString){


                        if(tagItem.length() != 0){

                            searchString += tagItem + ",";

                            ParseObject tagLog = new ParseObject("TagLog");
                            tagLog.put("tag", tagItem);
                            tagLog.put("user", currentUser);
                            tagLog.put("type", "write");
                            tagLog.put("place", "patron");
                            tagLog.put("status", true);
                            tagLog.put("add", true);
                            tagLog.saveInBackground();

                        }

                    }

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("tag_array", tagString);

                    String fileType = "";

                    if(functionBase.gifChecker(finalImage)){

                        fileType = "gif";

                    } else {

                        fileType = "png";

                    }


                    params.put("title", titleString);
                    params.put("body", bodyString);
                    params.put("image_cdn", finalImage);
                    params.put("image_type", fileType);

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    params.put("search_string", searchString + "," +bodyString);
                    //params.put("follow_flag", openRangeFlag);
                    params.put("lastAction", "patronSave");

                    params.put("content_type", contentType);
                    params.put("endDate", endDate);
                    params.put("target_amount", Integer.parseInt(targetPointString));
                    params.put("progress", "start");
                    params.put("patron_flag", openRangeFlag);
                    params.put("min_box", minInt );
                    params.put("reward_detail_info", patronRewardString );

                    ParseCloud.callFunctionInBackground("patron_save", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("tag", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){


                                    save_button.setClickable(true);
                                    TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                    finish();

                                } else {

                                    save_button.setClickable(true);

                                    TastyToast.makeText(getApplicationContext(), "저장 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);


                                }

                            } else {


                                Log.d("step", "request fail");
                                e.printStackTrace();
                                save_button.setClickable(true);

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

                    case 3:

                        creation_type.setText("굿즈");
                        contentType = "goods";
                        contentTypeSelectFlag = true;

                        break;

                    case 4:

                        creation_type.setText("커미션");
                        contentType = "commission";
                        contentTypeSelectFlag = true;

                        break;

                    case 5:

                        creation_type.setText("기타");
                        contentType = "extra";
                        contentTypeSelectFlag = true;

                        break;

                }

            }
        });




        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(mFragmentCallback);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");

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

            String selectedDateString = String.valueOf(year + 1900) + "/" + String.valueOf(month + 1) + "/" + String.valueOf(day);
            selected_date.setText(selectedDateString);
            endDate = currentSelectedDate;
            dateSelectFlag = true;


        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        file_status_text.setText("파일 선택 완료 대기 중..");

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images


            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            String imagePath = images.get(0).path;
            String imageName = images.get(0).name;

            UploadCallback callback = new UploadCallback() {
                @Override
                public void onStart(String requestId) {

                    file_status_text.setText("파일 업로드 시작");

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {

                    float progress = functionBase.makeProgress(Float.parseFloat(String.valueOf(bytes)), Float.parseFloat(String.valueOf(totalBytes)));
                    file_status_text.setText("업로드 중 : " + String.valueOf(bytes) + "/" + String.valueOf(totalBytes) + " || "  + String.valueOf(progress * 100) + "%");

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {

                    Log.d("resultdata", resultData.get("secure_url").toString());

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    finalImage = uploadTarget;

                    file_status_text.setText("업로드 완료 || 이미지 미리보기 불러오는 중..");

                    if(tempFile != null){

                        tempFile.delete();

                    }

                    requestManager
                            .load(finalUploadTaget)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .transition(new DrawableTransitionOptions().crossFade())
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                                    file_status_text.setText("업로드 완료 || 이미지 미리보기 실패");

                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                    file_status_text.setText("업로드 완료 || 이미지 미리보기 완료");

                                    return false;
                                }
                            })
                            .into(represent_image);

                }

                @Override
                public void onError(String requestId, ErrorInfo error) {

                    file_status_text.setText("업로드 실패");

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                    file_status_text.setText("업로드 실패");
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
                    MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).constrain(TimeWindow.immediate()).callback(callback).dispatch();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                Log.d("korean", "false");
                MediaManager.get().upload(imagePath).unsigned(AppConfig.image_preset).constrain(TimeWindow.immediate()).callback(callback).dispatch();

            }



        } else if (requestCode == functionBase.REQUEST_CODE && resultCode == RESULT_OK && data != null){

            Log.d("callbackdata", data.getStringArrayListExtra("tags").toString());
            tagString = data.getStringArrayListExtra("tags");
            tagGroup.setTags(tagString);

        } else {

            file_status_text.setText("파일 선택 안됨");
        }



    }


    @Override
    public void onBackPressed() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);

        builder.title("확인");
        builder.content("이 페이지에서 나가시겠습니까?");
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

                finish();

            }
        });
        builder.show();

    }




}
