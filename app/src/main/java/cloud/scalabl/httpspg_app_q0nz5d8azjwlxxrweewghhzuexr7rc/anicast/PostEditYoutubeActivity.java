package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import cz.msebera.android.httpclient.Header;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

public class PostEditYoutubeActivity extends AppCompatActivity {

    private EditText title;
    private EditText body;

    private ImageView represent_image;

    private LinearLayout save_button;

    private TagGroup tagGroup;


    private ArrayList<HashMap<String ,String>> savedImages;
    private String representImageUrl;

    private ParseUser currentUser;


    private ParseObject artistPostOb;
    private RequestManager requestManager;

    private File file;
    private List myList;

    final int REQ_CODE_SELECT_IMAGE=200;

    private int orderCount = 0;

    private EditText youtube_url;


    private String youtube_name;
    private String youtube_description;
    private String youtube_id;
    private String youtube_image;


    private Boolean imageExist = false;
    private Boolean youtubeImageUse = false;

    private String finalImage;

    private File tempFile;

    private FunctionBase functionBase;

    private Switch open_range;
    private Boolean openRangeFlag;

    private Switch ad_button;
    private Boolean adOnOffFlag;

    private TextView file_status_text;

    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;

    private TextView youtube_button_text;

    private String postId;

    private Boolean commercialUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_edit_youtube);

        Intent intent = getIntent();

        postId = intent.getExtras().getString("cardId");

        title = (EditText) findViewById(R.id.title);
        body = (EditText) findViewById(R.id.post_body);
        youtube_button_text = (TextView) findViewById(R.id.youtube_button_text);

        tagString = new ArrayList<>();
        commercialUser = false;

        ImageView title_reset = (ImageView) findViewById(R.id.title_reset);
        ImageView body_reset = (ImageView) findViewById(R.id.body_reset);

        title_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title.setText("");

            }
        });

        body_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                body.setText("");

            }
        });

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        back_button_text.setText("유튜브 수정하기");

        represent_image = (ImageView) findViewById(R.id.represent_image);
        LinearLayout represent_upload = (LinearLayout) findViewById(R.id.represent_upload);

        save_button = (LinearLayout) findViewById(R.id.save_button);

        currentUser = ParseUser.getCurrentUser();

        youtube_url = (EditText) findViewById(R.id.youtube_url);
        final LinearLayout youtube_url_input = (LinearLayout) findViewById(R.id.youtube_url_input);

        file_status_text = (TextView) findViewById(R.id.file_status_text);

        artistPostOb = new ArtistPost();
        functionBase = new FunctionBase(getApplicationContext());

        requestManager = Glide.with(getApplicationContext());


        open_range = (Switch) findViewById(R.id.open_range);
        openRangeFlag = false;
        open_range.setChecked(openRangeFlag);

        open_range.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){

                    open_range.setText("팔로워");
                    openRangeFlag = true;


                } else {

                    open_range.setText("전체");
                    openRangeFlag = false;

                }

            }
        });

        ad_button = (Switch) findViewById(R.id.ad_button);

        if(currentUser != null){

            currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject pointOb, ParseException e) {

                    if(e==null){

                        commercialUser = pointOb.getBoolean("commercial_user_flag");

                        adOnOffFlag = false;
                        ad_button.setChecked(adOnOffFlag);

                        if(commercialUser){

                            ad_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                    if(isChecked){

                                        ad_button.setText("켜짐");
                                        adOnOffFlag = true;

                                    } else {

                                        ad_button.setText("꺼짐");
                                        adOnOffFlag = false;

                                    }

                                }
                            });

                        } else {

                            ad_button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                    if(isChecked){

                                        ad_button.setChecked(false);
                                        adOnOffFlag = false;

                                        TastyToast.makeText(getApplicationContext(), "팔로워 30명 이상, 사이포인트 500 이상이 되면 유료화를 신청하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    } else {

                                        ad_button.setChecked(false);
                                        adOnOffFlag = false;

                                        TastyToast.makeText(getApplicationContext(), "팔로워 30명 이상, 사이포인트 500 이상이 되면 유료화를 신청하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    }

                                }
                            });

                        }




                    } else {

                        e.printStackTrace();
                    }

                }
            });

        }

        tag_input_button = (LinearLayout) findViewById(R.id.tag_input_button);
        tag_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TagInputActivity.class);
                intent.putStringArrayListExtra("tags",tagString);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });

        youtube_url_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                youtube_url_input.setClickable(false);
                youtube_button_text.setText("로딩중..");

                ClipboardManager clipboard = (ClipboardManager) getApplicationContext().getSystemService(Context.CLIPBOARD_SERVICE);
                String urlString = clipboard.getText().toString();

                if(!urlString.contains("youtu.be/")){

                    TastyToast.makeText(getApplicationContext(), "유튜브 영상 하단의 '공유 > 링크복사'를 이용하세요 ", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {

                    youtube_url.setText(urlString);

                    String[] youtubeUrlArray = urlString.split("youtu.be/");
                    youtube_id = youtubeUrlArray[youtubeUrlArray.length-1];
                    Log.d("youtube_id", youtube_id);

                    if(urlString.length() != 0){

                        String baseUrl = AppConfig.SERVER_URL + "youtube_data?url=" + urlString ;

                        Log.d("baseUrl", baseUrl);

                        AsyncHttpClient client = new AsyncHttpClient();
                        client.get(baseUrl, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                                if(statusCode == 200){

                                    Log.d("reponse", new String(responseBody));

                                    try {
                                        JSONObject responseDict= new JSONObject(new String(responseBody));

                                        youtube_name = responseDict.getString("title").trim();
                                        youtube_description = responseDict.getString("description").trim();
                                        youtube_image = "http://img.youtube.com/vi/" + youtube_id + "/0.jpg";
                                        youtubeImageUse = true;
                                        imageExist = true;

                                        title.setText(youtube_name);
                                        body.setText(youtube_description);

                                        requestManager.load(youtube_image).into(represent_image);

                                        youtube_button_text.setText("붙이기");
                                        youtube_url_input.setClickable(true);

                                    } catch (JSONException e) {

                                        TastyToast.makeText(getApplicationContext(), "에러3이 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        e.printStackTrace();

                                        youtube_button_text.setText("붙이기");
                                        youtube_url_input.setClickable(true);

                                    }


                                } else {

                                    TastyToast.makeText(getApplicationContext(), "에러2가 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    youtube_button_text.setText("붙이기");
                                    youtube_url_input.setClickable(true);

                                }

                            }

                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                                Log.d("status", String.valueOf(statusCode));
                                Log.d("error", error.toString());

                                youtube_button_text.setText("붙이기");
                                youtube_url_input.setClickable(true);

                                TastyToast.makeText(getApplicationContext(), "에러1이 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        });





                    } else {

                        TastyToast.makeText(getApplicationContext(), "내용이 입력되지 않았습니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                }

            }
        });

        represent_upload.setVisibility(View.GONE);
        represent_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, functionBase.REPRESENT_CODE);
                */

            }
        });

        tagGroup = (TagGroup) findViewById(R.id.tag_group);

        tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {



            }
        });

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


        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject postOb, ParseException e) {


                if(e==null){

                    if(postOb.get("image_cdn") != null){

                        String imageUrl = postOb.getString("image_cdn");
                        finalImage = imageUrl;

                        youtubeImageUse = false;
                        imageExist = true;

                        requestManager
                                .load(functionBase.imageUrlBase300 + "/" + imageUrl)
                                .into(represent_image);

                    } else if(postOb.get("image_youtube") != null){

                        String imageUrl = postOb.getString("image_youtube");
                        youtube_image = imageUrl;

                        youtubeImageUse = true;
                        imageExist = true;

                        requestManager
                                .load(youtube_image)
                                .into(represent_image);


                    } else {

                        imageExist = false;

                    }

                    title.setText( postOb.getString("title") );
                    body.setText( postOb.getString("body") );
                    openRangeFlag = postOb.getBoolean("open_flag");
                    adOnOffFlag = postOb.getBoolean("ad_flag");
                    tagString = functionBase.jsonArrayToArrayList( postOb.getJSONArray("tag_array") );
                    tagGroup.setTags(tagString);

                    youtube_id = postOb.getString("youtubeId");


                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            save_button.setClickable(false);

                            String bodyString = body.getText().toString();
                            String titleString = title.getText().toString();


                            if(imageExist){


                                if(titleString == null || titleString.length() == 0){

                                    TastyToast.makeText(getApplicationContext(), "제목 입력은 필수 입니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    save_button.setClickable(true);

                                } else if(youtube_id == null || youtube_id.length() == 0){

                                    TastyToast.makeText(getApplicationContext(), "유튜브 영상이 입력되지 않았습니다. 다시 입력해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    save_button.setClickable(true);

                                } else {

                                    String searchString = "";

                                    for(String tagItem : tagString){


                                        if(tagItem.length() != 0){

                                            searchString += tagItem + ",";

                                            ParseObject tagLog = new ParseObject("TagLog");
                                            tagLog.put("tag", tagItem);
                                            tagLog.put("user", currentUser);
                                            tagLog.put("type", "write");
                                            tagLog.put("place", "Post");
                                            tagLog.put("status", true);
                                            tagLog.put("add", true);
                                            tagLog.saveInBackground();

                                        }

                                    }

                                    HashMap<String, Object> params = new HashMap<>();
                                    params.put("key", currentUser.getSessionToken());
                                    params.put("tag_array", tagString);

                                    if(youtubeImageUse){

                                        String image = youtube_image;

                                        params.put("image_youtube", image);

                                    } else {

                                        String image = finalImage;

                                        params.put("image_cdn", image);

                                    }


                                    params.put("title", titleString.trim());
                                    params.put("body", bodyString.trim());

                                    params.put("search_string", searchString + "," + bodyString.trim() + "," + titleString.trim());


                                    if(tagString.size() != 0){

                                        params.put("tag_array", tagString);

                                    }

                                    Date uniqueIdDate = new Date();
                                    String uniqueId = uniqueIdDate.toString();

                                    params.put("uid", uniqueId);

                                    params.put("follow_flag", openRangeFlag);
                                    params.put("ad_flag", adOnOffFlag);
                                    params.put("youtubeId", youtube_id);
                                    params.put("lastAction", "youtubeEdit");
                                    params.put("postId", postId);


                                    ParseCloud.callFunctionInBackground("youtube_save", params, new FunctionCallback<Map<String, Object>>() {

                                        public void done(Map<String, Object> mapObject, ParseException e) {

                                            if (e == null) {

                                                Log.d("tag", mapObject.toString());

                                                if(mapObject.get("status").toString().equals("true")){


                                                    TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                    save_button.setClickable(true);

                                                    Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                                                    startActivity(intent);

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

                            } else {

                                TastyToast.makeText(getApplicationContext(), "이미지가 첨부되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);


                            }

                        }
                    });

                } else {

                    e.printStackTrace();
                }

            }

        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == functionBase.REPRESENT_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images

            file_status_text.setText("파일 선택 완료 대기 중..");

            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            String imagePath = images.get(0).path;


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

                    //progress_text.setVisibility(View.GONE);

                    Log.d("resultdata", resultData.get("secure_url").toString());

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    finalImage = uploadTarget;
                    imageExist = true;
                    youtubeImageUse = false;

                    file_status_text.setText("업로드 완료");

                    if(tempFile != null){

                        tempFile.delete();

                    }

                    RequestManager requestManager = Glide.with(getApplicationContext());

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


        } else if(requestCode == functionBase.REQUEST_CODE && resultCode == RESULT_OK && data != null){

            Log.d("callbackdata", data.getStringArrayListExtra("tags").toString());
            tagString = data.getStringArrayListExtra("tags");
            tagGroup.setTags(tagString);

        } else {

            TastyToast.makeText(getApplicationContext(), "취소 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);
            file_status_text.setText("파일 선택 안됨");

        }


    }

}
