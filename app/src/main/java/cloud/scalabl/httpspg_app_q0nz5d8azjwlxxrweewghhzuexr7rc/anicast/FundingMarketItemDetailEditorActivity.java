package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketItemEditorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyTimelineAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FundingItemDetailEditListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.NovelContent;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class FundingMarketItemDetailEditorActivity extends AppCompatActivity implements FundingItemDetailEditListener{

    private String contentId;
    private String type;

    private WebView content_container;
    private ParseObject contentOb;


    int pastVisibleItems, visibleItemCount, totalItemCount;
    private Boolean novelDataExist = false;
    int order = 0;

    ArrayList<NovelContent> infoListArray;

    private String finalImage;

    private ArtistPost postObject;
    private RecyclerView info_list;
    //private NovelContentParseAdapter novelContentAdapter;
    private FundingMarketItemEditorAdapter fundingMarketItemEditorAdapter;

    private EditText info_input;
    private ParseUser currentUser;
    private Boolean editMode = false;
    private TextView send_button_text;

    private ParseObject editOb;
    private LinearLayout input_layout;
    private LinearLayout send_button;

    private FunctionBase functionBase;

    private String itemId;
    private ParseObject itemObject;

    private LinearLayoutManager layoutManager;
    private RequestManager requestManager;
    private TextView back_button_text;
    private LinearLayout back_button;

    private final int editPhotoRequest = 1000;

    private TextView file_status_text;
    private LinearLayout upload_preview;
    private ImageView preview_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_item_detail_editor);

        Intent intent = getIntent();

        itemId = intent.getExtras().getString("itemId");

        info_list = (RecyclerView) findViewById(R.id.info_list);
        input_layout = (LinearLayout) findViewById(R.id.input_layout);
        info_input = (EditText) findViewById(R.id.info_input);
        send_button = (LinearLayout) findViewById(R.id.send_button);
        send_button_text = (TextView) findViewById(R.id.send_button_text);

        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button_text.setText("상품 상세설명 편집");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        file_status_text = (TextView) findViewById(R.id.file_status_text);
        upload_preview = (LinearLayout) findViewById(R.id.upload_preview);
        upload_preview.setVisibility(View.GONE);

        preview_image = (ImageView) findViewById(R.id.preview_image);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                fundingMarketItemEditorAdapter = new FundingMarketItemEditorAdapter( FundingMarketItemDetailEditorActivity.this,  getApplicationContext(), requestManager, itemObject);
                fundingMarketItemEditorAdapter.setEditListener(FundingMarketItemDetailEditorActivity.this);

                info_list.setAdapter(fundingMarketItemEditorAdapter);

                swipeLayout.setRefreshing(false);

            }

        });

        functionBase = new FunctionBase(getApplicationContext());
        requestManager = Glide.with(getApplicationContext());

        LinearLayout image_upload = (LinearLayout) findViewById(R.id.image_upload);
        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

            }
        });

        ParseQuery query = ParseQuery.getQuery("FundingMarketItem");
        query.include("dealer");
        query.getInBackground(itemId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject itemOb, ParseException e) {

                if(e== null){


                    info_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {

                            if(hasFocus){

                                info_list.scrollToPosition(fundingMarketItemEditorAdapter.getItemCount()-1);

                            }

                        }
                    });

                    itemObject = itemOb;

                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

                    info_list.setLayoutManager(layoutManager);
                    info_list.setHasFixedSize(true);
                    info_list.setNestedScrollingEnabled(false);


                    fundingMarketItemEditorAdapter = new FundingMarketItemEditorAdapter( FundingMarketItemDetailEditorActivity.this,  getApplicationContext(), requestManager, itemOb);
                    fundingMarketItemEditorAdapter.setEditListener(FundingMarketItemDetailEditorActivity.this);

                    info_list.setItemAnimator(new SlideInUpAnimator());
                    info_list.setAdapter(fundingMarketItemEditorAdapter);

                    send_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            send_button.setClickable(false);

                            final String inputString = info_input.getText().toString();

                            if(editMode){

                                editOb.put("content", inputString);
                                editOb.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            TastyToast.makeText(getApplicationContext(), "수정 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            fundingMarketItemEditorAdapter.loadObjects(0);

                                            info_input.setText("");
                                            functionBase.hideKeyboard(FundingMarketItemDetailEditorActivity.this);

                                            editMode = false;
                                            editOb = null;
                                            send_button_text.setText("저장");
                                            send_button.setClickable(true);


                                        } else {

                                            e.printStackTrace();
                                        }

                                    }
                                });

                            } else {

                                final ParseObject fundingItemDetailOb = new ParseObject("FundingMarketDetail");
                                fundingItemDetailOb.put("order", order);
                                fundingItemDetailOb.put("content", inputString);
                                fundingItemDetailOb.put("type", "String");
                                fundingItemDetailOb.put("item", itemOb);
                                fundingItemDetailOb.put("status", true);
                                fundingItemDetailOb.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){


                                            ParseRelation infoContentRelationOb = itemOb.getRelation("info_content");
                                            infoContentRelationOb.add(fundingItemDetailOb);
                                            itemOb.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                        fundingMarketItemEditorAdapter.loadObjects(0);

                                                        info_input.setText("");
                                                        functionBase.hideKeyboard(FundingMarketItemDetailEditorActivity.this);

                                                        info_list.scrollToPosition(order -1);

                                                        send_button.setClickable(true);

                                                    } else {

                                                        TastyToast.makeText(getApplicationContext(), "저장 실패. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                        send_button.setClickable(true);
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

        ArrayList<Image> images;
        String imagePath;

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images

            upload_preview.setVisibility(View.VISIBLE);

            file_status_text.setText("파일 선택 완료 대기 중..");

            images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            imagePath = images.get(0).path;
            String imageName = images.get(0).name;

            requestManager
                    .load(imagePath)
                    .into(preview_image);

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

                    file_status_text.setText("업로드 완료 || 이미지 미리보기 불러오는 중..");

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    finalImage = uploadTarget;

                    if(editMode){

                        editOb.put("content", uploadTarget);
                        editOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    fundingMarketItemEditorAdapter.loadObjects(0);

                                    info_input.setText("");
                                    functionBase.hideKeyboard(FundingMarketItemDetailEditorActivity.this);

                                    editMode = false;

                                    previewImageReset(upload_preview, file_status_text, preview_image);

                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });

                    } else {

                        final ParseObject fundingItemDetailOb = new ParseObject("FundingMarketDetail");
                        fundingItemDetailOb.put("order", order);
                        fundingItemDetailOb.put("content", uploadTarget);
                        fundingItemDetailOb.put("type", "Image");
                        fundingItemDetailOb.put("item", itemObject);
                        fundingItemDetailOb.put("status", true);
                        fundingItemDetailOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    ParseRelation infoContentRelationOb = itemObject.getRelation("info_content");
                                    infoContentRelationOb.add(fundingItemDetailOb);
                                    itemObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                fundingMarketItemEditorAdapter.loadObjects(0);

                                                info_input.setText("");
                                                functionBase.hideKeyboard(FundingMarketItemDetailEditorActivity.this);
                                                previewImageReset(upload_preview, file_status_text, preview_image);

                                                info_list.scrollToPosition(order -1);


                                            } else {

                                                TastyToast.makeText(getApplicationContext(), "저장 실패. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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
            File newFile = new File(sdPath);
            newFile.mkdirs();

            newFile = new File(sdPath, "temp.png");

            try {

                functionBase.copy(gifFile, newFile);
                MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).constrain(TimeWindow.immediate()).callback(callback).dispatch();

            } catch (IOException e) {
                e.printStackTrace();
            }



        } else if(requestCode == editPhotoRequest && resultCode == RESULT_OK && data != null) {

            upload_preview.setVisibility(View.VISIBLE);
            file_status_text.setText("파일 선택 완료 대기 중..");

            images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            imagePath = images.get(0).path;
            String imageName = images.get(0).name;

            requestManager
                    .load(imagePath)
                    .into(preview_image);

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

                    file_status_text.setText("업로드 완료 || 이미지 미리보기 불러오는 중..");

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    finalImage = uploadTarget;

                    if(editMode){

                        editOb.put("content", uploadTarget);
                        editOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    fundingMarketItemEditorAdapter.loadObjects(0);

                                    info_input.setText("");
                                    functionBase.hideKeyboard(FundingMarketItemDetailEditorActivity.this);
                                    previewImageReset(upload_preview, file_status_text, preview_image);

                                    editMode = false;

                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });

                    } else {


                        editOb.put("content", uploadTarget);
                        editOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    fundingMarketItemEditorAdapter.loadObjects(0);

                                    info_input.setText("");
                                    functionBase.hideKeyboard(FundingMarketItemDetailEditorActivity.this);
                                    editMode = false;
                                    previewImageReset(upload_preview, file_status_text, preview_image);
                                    editOb = null;



                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });

                    }


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
            File newFile = new File(sdPath);
            newFile.mkdirs();

            newFile = new File(sdPath, "temp.png");

            try {

                functionBase.copy(gifFile, newFile);
                MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).constrain(TimeWindow.immediate()).callback(callback).dispatch();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }

    private void previewImageReset( LinearLayout upload_preview,  TextView file_status_text, ImageView preview_image){

        upload_preview.setVisibility(View.GONE);
        file_status_text.setText("파일 선택 안됨");
        preview_image.setImageResource(R.drawable.image_background_1px);
        finalImage = null;


    }

    @Override
    public void onEditorModeOpen(ParseObject editTargetOb) {

        editMode = true;
        editOb = editTargetOb;

        if(editTargetOb.getString("type").equals("String")){

            send_button_text.setText("수정");
            info_input.setText(editTargetOb.getString("content"));


        } else {

            Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
            intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection

            startActivityForResult(intent, editPhotoRequest);


        }

    }
}
