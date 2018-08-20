package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FileOpenClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import cz.msebera.android.httpclient.Header;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;


public class SerieseItemAddYoutubeActivity extends AppCompatActivity implements FileOpenClickListener {

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

    private Boolean openRangeFlag;
    private Switch open_range;

    private TextView file_status_text;


    private TagGroup recommend_tag_group;
    private ArrayList<String> tagString;

    private Boolean commercialUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seriese_item_add_youtube);

        //getSupportActionBar().setTitle("연재 작품 추가");

        Intent intent = getIntent();

        String serieseId = intent.getExtras().getString("serieseId");


        title = (EditText) findViewById(R.id.title);
        body = (EditText) findViewById(R.id.post_body);

        ImageView title_reset = (ImageView) findViewById(R.id.title_reset);
        ImageView body_reset = (ImageView) findViewById(R.id.body_reset);

        represent_image = (ImageView) findViewById(R.id.represent_image);
        LinearLayout represent_upload = (LinearLayout) findViewById(R.id.represent_upload);
        represent_image.setFocusableInTouchMode(true);
        represent_image.requestFocus();

        commercialUser = false;

        save_button = (LinearLayout) findViewById(R.id.save_button);

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(this, getApplicationContext());


        youtube_url = (EditText) findViewById(R.id.youtube_url);
        LinearLayout youtube_url_input = (LinearLayout) findViewById(R.id.youtube_url_input);

        file_status_text = (TextView) findViewById(R.id.file_status_text);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        title_reset = (ImageView) findViewById(R.id.title_reset);
        body_reset =(ImageView) findViewById(R.id.body_reset);

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

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("신규 작품 추가");

        artistPostOb = new ArtistPost();

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


        requestManager = Glide.with(getApplicationContext());

        youtube_url_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                String urlString = clipboard.getText().toString();

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

                                } catch (JSONException e) {

                                    TastyToast.makeText(getApplicationContext(), "에러3이 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    e.printStackTrace();

                                }


                            } else {

                                TastyToast.makeText(getApplicationContext(), "에러2가 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                            }

                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                            Log.d("status", String.valueOf(statusCode));
                            Log.d("error", error.toString());

                            TastyToast.makeText(getApplicationContext(), "에러1이 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }
                    });





                } else {

                    TastyToast.makeText(getApplicationContext(), "내용이 입력되지 않았습니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }



            }
        });

        represent_upload.setVisibility(View.GONE);
        represent_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                //intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent, functionBase.REPRESENT_CODE);

                /*
                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, functionBase.REPRESENT_CODE);
                */

            }
        });


        tagGroup = (TagGroup) findViewById(R.id.tag_group);


        recommend_tag_group = (TagGroup) findViewById(R.id.recommend_tag_group);
        LinearLayout tag_recommend_layout = (LinearLayout) findViewById(R.id.tag_recommend_layout);

        if(currentUser != null){

            tag_recommend_layout.setVisibility(View.VISIBLE);
            tagString = new ArrayList<>();

            ParseQuery tagQuery = ParseQuery.getQuery("TagLog");
            tagQuery.whereEqualTo("user", currentUser);
            tagQuery.orderByDescending("createdAt");
            tagQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> tagObs, ParseException e) {

                    String[] recommendTag = functionBase.tagMaker(tagObs);

                    recommend_tag_group.setTags(recommendTag);

                    recommend_tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                        @Override
                        public void onTagClick(String tag) {

                            tagString.add(tag);

                            tagGroup.setTags(tagString);

                        }
                    });

                }

            });

        } else {

            tag_recommend_layout.setVisibility(View.GONE);

        }


        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.getInBackground(serieseId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject parentOb, ParseException e) {

                if(e==null){

                    save_button.setClickable(true);
                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            save_button.setClickable(false);

                            startParseObject();

                            String[] taglist = tagGroup.getTags();
                            String searchString = "";

                            ArrayList<String> tagsArray = new ArrayList<>();

                            for(String tagItem : taglist){


                                if(tagItem.length() != 0){

                                    tagsArray.add(tagItem);
                                    searchString += tagItem + ",";

                                }

                            }

                            String bodyString = body.getText().toString();
                            String titleString = title.getText().toString();


                            if(imageExist){


                                if(titleString == null || titleString.length() == 0){

                                    TastyToast.makeText(getApplicationContext(), "제목 입력은 필수 입니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                } else if(youtube_id == null || youtube_id.length() == 0){

                                    TastyToast.makeText(getApplicationContext(), "유튜브 영상이 입력되지 않았습니다. 다시 입력해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                } else {

                                    if(youtubeImageUse){

                                        String image = youtube_image;
                                        artistPostOb.put("image_youtube", image);

                                    } else {

                                        String image = finalImage;
                                        artistPostOb.put("image_cdn", image);

                                    }

                                    artistPostOb.put("title", titleString.trim());
                                    artistPostOb.put("body", bodyString.trim());
                                    artistPostOb.put("seriese_parent", parentOb);

                                    if(tagsArray.size() != 0){

                                        artistPostOb.put("tag_array", tagsArray);

                                    }

                                    Date uniqueIdDate = new Date();
                                    String uniqueId = uniqueIdDate.toString();
                                    artistPostOb.put("uniqueId", uniqueId);

                                    artistPostOb.put("status", true);
                                    artistPostOb.put("open_flag", false);
                                    artistPostOb.put("youtubeId", youtube_id);
                                    artistPostOb.put("level", parentOb.getString("level"));
                                    artistPostOb.put("seriese_in", true);
                                    artistPostOb.put("follow_flag", openRangeFlag);
                                    artistPostOb.put("lastAction", "youtubeSaveFromSerise");
                                    artistPostOb.put("search_string", searchString + bodyString + "," + titleString);

                                    artistPostOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                ParseRelation<ParseObject> relationOb = parentOb.getRelation("seriese_item");
                                                relationOb.add(artistPostOb);
                                                parentOb.increment("seriese_count");
                                                parentOb.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if(e==null){


                                                            save_button.setClickable(true);
                                                            TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                            finish();

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

    private ParseObject startParseObject(){


        artistPostOb.put("status", false);
        artistPostOb.put("user", currentUser);
        artistPostOb.put("userId", currentUser.getObjectId());
        artistPostOb.put("comment_count", 0);
        artistPostOb.put("like_count", 0);
        artistPostOb.put("patron_count", 0);
        artistPostOb.put("post_type", "youtube");
        artistPostOb.saveInBackground();

        return artistPostOb;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == functionBase.REPRESENT_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
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


                    Log.d("resultdata", resultData.get("secure_url").toString());

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    finalImage = uploadTarget;
                    imageExist = true;
                    youtubeImageUse = false;

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

            String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/craftory";

            File gifFile = new File(imagePath);
            tempFile = new File(sdPath);
            tempFile.mkdirs();

            tempFile = new File(sdPath, "temp.png");

            try {

                functionBase.copy(gifFile, tempFile);
                MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).callback(callback).dispatch();

            } catch (IOException e) {

                file_status_text.setText("업로드 실패");
                e.printStackTrace();
            }


        }


    }



    @Override
    public void onResume() {
        super.onResume();



    }


    @Override
    public void onFileOpenClicked() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

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
