package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketItemEditorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SublimePickerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

public class GoodsFundingActivity extends AppCompatActivity {

    private ImageView represent_image;
    private LinearLayout represent_upload;

    private EditText price;
    private EditText target_head_count;

    private EditText title;
    private ImageView title_reset;
    private EditText body;
    private ImageView body_reset;
    private TagGroup tagGroup;

    private LinearLayout save_button;

    private TextView selected_date;

    SelectedDate mSelectedDate;
    int mHour, mMinute;
    String mRecurrenceOption, mRecurrenceRule;

    int year, day, month;

    private String contentType = "goods";
    private Date endDate;

    private Boolean dateSelectFlag;
    private ParseUser currentUser;


    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;


    private FunctionBase functionBase;

    private TextView file_status_text;

    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;

    private BootstrapButton calendar_button;

    private String itemId;


    private ImageView selected_item_image;
    private TextView item_name;
    private TextView item_price;
    private ParseObject itemObject;

    private LinearLayout detail_button;

    private Boolean editMode;
    private String editPostId;

    private RecyclerView detail_info_list;
    private String postId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_funding);

        Intent intent = getIntent();

        itemId = intent.getExtras().getString("itemId");

        editMode = false;
        editPostId = null;

        functionBase = new FunctionBase(this, getApplicationContext());

        represent_image = (ImageView) findViewById(R.id.represent_image);
        represent_upload = (LinearLayout) findViewById(R.id.represent_upload);

        selected_item_image = (ImageView) findViewById(R.id.selected_item_image);
        item_name = (TextView) findViewById(R.id.item_name);
        item_price = (TextView) findViewById(R.id.item_price);

        detail_info_list = (RecyclerView) findViewById(R.id.detail_info_list);

        detail_button = (LinearLayout) findViewById(R.id.detail_button);
        detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editMode){

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("goods_funding_premake", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                if(mapObject.get("status").toString().equals("true")){

                                    postId = (String) mapObject.get("postId");
                                    editMode = true;

                                    Intent intent1 = new Intent(getApplicationContext(), PatronDetailEditorActivity.class);
                                    intent1.putExtra("itemId", postId);
                                    startActivityForResult(intent1, functionBase.DETAILINFO_REQUEST_CODE);

                                } else {

                                    Log.d("msg", "server response false");

                                }

                            } else {


                                Log.d("step", "request fail");
                                e.printStackTrace();

                            }
                        }
                    });

                } else {

                    Intent intent1 = new Intent(getApplicationContext(), PatronDetailEditorActivity.class);
                    intent1.putExtra("itemId", postId);
                    startActivityForResult(intent1, functionBase.DETAILINFO_REQUEST_CODE);

                }

            }
        });



        ParseQuery query = ParseQuery.getQuery("FundingMarketItem");
        query.getInBackground(itemId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject itemOb, ParseException e) {

                itemObject = itemOb;

                functionBase.generalImageLoading(selected_item_image, itemOb, requestManager);
                item_name.setText(itemOb.getString("name"));
                item_price.setText(functionBase.makeComma(itemOb.getInt("price")) + " BOX");

            }

        });


        price = (EditText) findViewById(R.id.price);
        target_head_count = (EditText) findViewById(R.id.target_head_count);

        calendar_button = (BootstrapButton) findViewById(R.id.calendar_opendate_button);
        title = (EditText) findViewById(R.id.title);
        title_reset = (ImageView) findViewById(R.id.title_reset);
        title_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title.setText("");

            }
        });

        body = (EditText) findViewById(R.id.body);
        body_reset = (ImageView) findViewById(R.id.body_reset);
        body_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                body.setText("");

            }
        });
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

        selected_date = (TextView) findViewById(R.id.selected_date);


        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        file_status_text = (TextView) findViewById(R.id.file_status_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });


        back_button_text.setText("굿즈펀딩 만들기");


        represent_image.setFocusableInTouchMode(true);
        represent_image.requestFocus();
        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getApplicationContext());
        tagString = new ArrayList<>();

        endDate = null;
        dateSelectFlag = false;
        tempFile = null;



        represent_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

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
                String priceString = price.getText().toString();
                String targetString = target_head_count.getText().toString();


                if(titleString == null){

                    TastyToast.makeText(getApplicationContext(), "제목을 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    save_button.setClickable(true);

                } else if(!dateSelectFlag){

                    TastyToast.makeText(getApplicationContext(), "종료일을 선택하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    save_button.setClickable(true);

                } else if(targetString == null || targetString.length() == 0){

                    TastyToast.makeText(getApplicationContext(), "후원 목표를 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    save_button.setClickable(true);

                } else if(priceString == null || priceString.length() == 0){

                    TastyToast.makeText(getApplicationContext(), "후원 목표를 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    save_button.setClickable(true);

                } else if(finalImage == null){

                    TastyToast.makeText(getApplicationContext(), "작품이 등록되지 않았습니다. 작품을 등록해 주세요!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    save_button.setClickable(true);

                } else {

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

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("uid", uniqueId);
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
                    params.put("price", Integer.parseInt(priceString));

                    params.put("item", itemObject.getObjectId());

                    if(editMode){

                        params.put("postId", itemId);
                        params.put("lastAction", "patronEdit");

                    } else {

                        params.put("lastAction", "patronSave");
                    }

                    params.put("search_string", searchString + "," +bodyString);

                    params.put("content_type", contentType);
                    params.put("endDate", endDate);
                    params.put("target_amount", Integer.parseInt(targetString));
                    params.put("progress", "start");

                    ParseCloud.callFunctionInBackground("goods_funding_save", params, new FunctionCallback<Map<String, Object>>() {

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


        calendar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(mFragmentCallback);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");

            }
        });

        if(postId != null){

            detailInfoDisplay(postId);

        }


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


    private void detailInfoDisplay(String postId){

        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parentObject, ParseException e) {

                LinearLayoutManager layoutManagerForDetail = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
                detail_info_list.setLayoutManager(layoutManagerForDetail);
                detail_info_list.setHasFixedSize(true);

                FundingMarketItemEditorAdapter fundingMarketItemEditorAdapter = new FundingMarketItemEditorAdapter(getApplicationContext(), requestManager, parentObject);
                detail_info_list.setAdapter(fundingMarketItemEditorAdapter);

            }

        });

    }

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

        } else if (requestCode == functionBase.DETAILINFO_REQUEST_CODE && resultCode == RESULT_OK && data != null){

            Log.d("callbackdata", data.getExtras().getString("postId"));
            postId = data.getExtras().getString("postId");
            detailInfoDisplay(postId);

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
