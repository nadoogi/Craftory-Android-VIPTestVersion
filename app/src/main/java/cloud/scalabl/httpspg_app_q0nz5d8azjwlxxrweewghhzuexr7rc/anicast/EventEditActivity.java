package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
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
import java.util.Date;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SublimePickerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;


public class EventEditActivity extends AppCompatActivity {

    private EditText title;
    private ImageView title_reset;
    private EditText body;
    private ImageView body_reset;
    private LinearLayout save_button;

    private ImageView thumbnail;

    private ImageView preview;
    private ArrayList<Image> images;
    private TagGroup tagGroup;
    private ParseUser currentUser;
    private ParseObject artistPostOb;

    private String thumbFinalImage;
    private File thumbTempFile;


    private RequestManager requestManager;
    private File tempFile;


    private String imagePath;
    private String finalImage;


    private FunctionBase functionBase;

    private TextView start_date_text;
    private TextView end_date_text;

    int startYear, startDay, startMonth;

    SelectedDate startSelectedDate;
    int startHour, startMinute;
    String startRecurrenceOption, startRecurrenceRule;

    private TextView start_selected_date;
    Date startDate;
    Boolean startDateSelectFlag;

    int endYear, endDay, endMonth;

    SelectedDate endSelectedDate;
    int endHour, endMinute;
    String endRecurrenceOption, endRecurrenceRule;

    private TextView end_selected_date;
    Date endDate;
    Boolean endDateSelectFlag;

    private int thumnail_code = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);

        Intent intent = getIntent();

        String postId = intent.getExtras().getString("postId");

        requestManager = Glide.with(getApplicationContext());

        currentUser = ParseUser.getCurrentUser();

        title = (EditText) findViewById(R.id.title);
        title_reset = (ImageView) findViewById(R.id.title_reset);

        body = (EditText) findViewById(R.id.post_body);
        body_reset = (ImageView) findViewById(R.id.body_reset);
        LinearLayout image_upload = (LinearLayout) findViewById(R.id.image_upload);
        preview = (ImageView) findViewById(R.id.preview);

        LinearLayout thumbnail_image_upload = (LinearLayout) findViewById(R.id.thumbnail_image_upload);
        thumbnail = (ImageView) findViewById(R.id.thumbnail);

        tagGroup = (TagGroup) findViewById(R.id.tag_group);
        save_button = (LinearLayout) findViewById(R.id.save_button);

        start_date_text = (TextView) findViewById(R.id.start_date_text);
        end_date_text = (TextView) findViewById(R.id.end_date_text);

        Button start_date_button = (Button) findViewById(R.id.start_date_button);
        Button end_date_button = (Button) findViewById(R.id.end_date_button);

        functionBase = new FunctionBase(this, getApplicationContext());


        finalImage = null;
        imagePath = null;
        images = new ArrayList<>();

        artistPostOb = new ArtistPost();
        functionBase = new FunctionBase(this, getApplicationContext());

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        back_button_text.setText("이벤트 관리");

        preview.requestFocus();

        requestManager = Glide.with(getApplicationContext());

        start_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(startFragmentCallback);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");

            }
        });


        end_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SublimePickerFragment pickerFrag = new SublimePickerFragment();
                pickerFrag.setCallback(endFragmentCallback);

                pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
                pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");

            }
        });

        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

            }
        });

        thumbnail_image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, thumnail_code);

            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_button.setClickable(false);



                if(finalImage == null){

                    TastyToast.makeText(getApplicationContext(), "작품이 등록되지 않았습니다. 작품을 등록해 주세요!", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {

                    artistPostOb = startParseObject();


                    String[] taglist = tagGroup.getTags();
                    String searchString = "";

                    ArrayList<String> tagsArray = new ArrayList<>();

                    for(String tagItem : taglist){


                        if(tagItem.length() != 0){

                            tagsArray.add(tagItem);
                            searchString += tagItem + ",";

                        }

                    }

                    if(tagsArray.size() != 0){

                        artistPostOb.put("tag_array", tagsArray);

                    }

                    String fileType = "";

                    if(functionBase.gifChecker(finalImage)){

                        fileType = "gif";

                    } else {

                        fileType = "png";

                    }

                    Date now = new Date();
                    artistPostOb.put("open_time", now);

                    String bodyString = body.getText().toString();
                    String titleString = title.getText().toString();

                    artistPostOb.put("title", titleString);
                    artistPostOb.put("body", bodyString);
                    artistPostOb.put("thumbnail_cdn", thumbFinalImage);
                    artistPostOb.put("image_cdn", finalImage);
                    artistPostOb.put("status", true);
                    artistPostOb.put("open_flag", true);
                    artistPostOb.put("image_type", fileType);
                    artistPostOb.put("endDate", endDate);
                    artistPostOb.put("open_flag", true);
                    artistPostOb.put("startDate", startDate);


                    artistPostOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                currentUser.increment("post_count");
                                currentUser.saveInBackground();

                                save_button.setClickable(true);
                                TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                finish();

                            } else {


                                e.printStackTrace();
                            }

                        }
                    });

                }


            }
        });

        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){

                    title_reset.setVisibility(View.VISIBLE);

                } else {

                    title_reset.setVisibility(View.GONE);

                }

            }
        });

        body.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                if(b){

                    body_reset.setVisibility(View.VISIBLE);

                } else {

                    body_reset.setVisibility(View.GONE);

                }

            }
        });

        title_reset.setVisibility(View.GONE);

        title_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title.setText("");

            }
        });

        body_reset.setVisibility(View.GONE);

        body_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                body.setText("");

            }
        });

        tagGroup.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
            @Override
            public void onAppend(TagGroup tagGroup, String tag) {

            }

            @Override
            public void onDelete(TagGroup tagGroup, String tag) {

            }
        });


        ParseQuery<ArtistPost> postQuery = ParseQuery.getQuery("ArtistPost");
        postQuery.getInBackground(postId, new GetCallback<ArtistPost>() {

            @Override
            public void done(ArtistPost postOb, ParseException e) {

                artistPostOb = postOb;

                if(e==null){

                    if(artistPostOb.get("title") != null){

                        title.setText(artistPostOb.getString("title"));

                    }

                    if(artistPostOb.get("body") != null){

                        body.setText( artistPostOb.getString("body") );

                    }

                    if(artistPostOb.get("tags") != null){

                        tagGroup.setTags( artistPostOb.getString("tags").trim().split(",") );

                    }

                    functionBase.generalImageLoading(preview, artistPostOb, requestManager);

                    finalImage = postOb.getString("image_cdn");

                } else {

                    e.printStackTrace();
                }

            }

        });

    }

    private ParseObject startParseObject(){

        ParseObject artistPostOb = ParseObject.create("ArtistPost");

        artistPostOb.put("status", false);
        artistPostOb.put("user", currentUser);
        artistPostOb.put("userId", currentUser.getObjectId());
        artistPostOb.put("comment_count", 0);
        artistPostOb.put("like_count", 0);
        artistPostOb.put("post_type", "event");
        artistPostOb.saveInBackground();

        return artistPostOb;

    }

    SublimePickerFragment.Callback startFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {

            startSelectedDate = selectedDate;
            startHour = hourOfDay;
            startMinute = minute;
            startRecurrenceOption = recurrenceOption != null ? recurrenceOption.name() : "n/a";
            startRecurrenceRule = recurrenceRule != null ? recurrenceRule : "n/a";




            Date currentSelectedDate = startSelectedDate.getStartDate().getTime();
            startYear = currentSelectedDate.getYear();
            startMonth = currentSelectedDate.getMonth();
            startDay = currentSelectedDate.getDate();

            String selectedDateString = String.valueOf(startYear + 1900) + "/" + String.valueOf(startMonth + 1) + "/" + String.valueOf(startDay);
            start_date_text.setText(selectedDateString);
            startDate = currentSelectedDate;
            startDateSelectFlag = true;


        }
    };


    SublimePickerFragment.Callback endFragmentCallback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate, int hourOfDay, int minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {

            endSelectedDate = selectedDate;
            endHour = hourOfDay;
            endMinute = minute;
            endRecurrenceOption = recurrenceOption != null ? recurrenceOption.name() : "n/a";
            endRecurrenceRule = recurrenceRule != null ? recurrenceRule : "n/a";




            Date currentSelectedDate = endSelectedDate.getStartDate().getTime();
            endYear = currentSelectedDate.getYear();
            endMonth = currentSelectedDate.getMonth();
            endDay = currentSelectedDate.getDate();

            String selectedDateString = String.valueOf(endYear + 1900) + "/" + String.valueOf(endMonth + 1) + "/" + String.valueOf(endDay);
            end_date_text.setText(selectedDateString);
            endDate = currentSelectedDate;
            endDateSelectFlag = true;


        }
    };



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            imagePath = images.get(0).path;
            String imageName = images.get(0).name;

            UploadCallback callback = new UploadCallback() {
                @Override
                public void onStart(String requestId) {



                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {

                    Log.d("pregress", String.valueOf(bytes/totalBytes * 100) + "%");
                    final String progressString = String.valueOf(bytes/totalBytes * 100) + "%";
                    //progress_text.setText(progressString);

                }

                @Override
                public void onSuccess(String requestId, Map resultData) {


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
                            .into(preview);

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



        } else if(requestCode == thumnail_code && resultCode == RESULT_OK && data != null){

            ArrayList<Image> thumbImages = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            String thumbImagePath = thumbImages.get(0).path;
            String imageName = thumbImages.get(0).name;

            UploadCallback callback = new UploadCallback() {
                @Override
                public void onStart(String requestId) {


                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {


                }

                @Override
                public void onSuccess(String requestId, Map resultData) {


                    Log.d("resultdata", resultData.get("secure_url").toString());

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    thumbFinalImage = uploadTarget;

                    if(thumbTempFile != null){

                        thumbTempFile.delete();

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
                            .into(thumbnail);

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

            // 한글이 포함된 문자열
            Log.d("korean", "true");

            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/craftory";

            File gifFile = new File(thumbImagePath);
            thumbTempFile = new File(sdPath);
            thumbTempFile.mkdirs();

            thumbTempFile = new File(sdPath, "temp2.png");

            try {

                functionBase.copy(gifFile, thumbTempFile);
                MediaManager.get().upload(sdPath+"/temp2.png").unsigned(AppConfig.image_preset).callback(callback).dispatch();

            } catch (IOException e) {
                e.printStackTrace();
            }

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
