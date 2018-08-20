package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

public class TagItemEditorActivity extends AppCompatActivity {

    private EditText body;
    private ImageView body_reset;
    private LinearLayout save_button;
    private ImageView preview;

    private ArrayList<Image> images;

    private ParseUser currentUser;

    private String imagePath;

    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;


    private FunctionBase functionBase;


    private TextView file_status_text;

    private ArrayList<String> tagString;
    private TagGroup tagGroup;

    private String objectId;
    private ParseObject tagItemObject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_item_maker);

        Intent intent = getIntent();

        objectId = intent.getExtras().getString("objectId");

        requestManager = Glide.with(getApplicationContext());

        currentUser = ParseUser.getCurrentUser();

        body = (EditText) findViewById(R.id.post_body);
        body_reset = (ImageView) findViewById(R.id.body_reset);
        body_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                body.setText("");

            }
        });
        LinearLayout image_upload = (LinearLayout) findViewById(R.id.image_upload);
        preview = (ImageView) findViewById(R.id.preview);

        preview.setFocusableInTouchMode(true);
        preview.requestFocus();

        save_button = (LinearLayout) findViewById(R.id.save_button);
        file_status_text = (TextView) findViewById(R.id.file_status_text);
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


        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("연성 태그 추가");

        finalImage = null;
        imagePath = null;
        images = new ArrayList<>();

        functionBase = new FunctionBase(this, getApplicationContext());


        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

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

        body_reset.setVisibility(View.GONE);

        body_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                body.setText("");

            }
        });

        ParseQuery query = ParseQuery.getQuery("TagItem");
        query.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject tagItemOb, ParseException e) {

                if(e==null){

                    tagItemObject = tagItemOb;

                    finalImage = tagItemObject.getString("image_cdn");
                    functionBase.generalImageLoading(preview, tagItemObject, requestManager);
                    body.setText(tagItemObject.getString("tag"));
                    String[] savedTag = functionBase.jsonArrayToStringArray(tagItemObject.getJSONArray("tag_array"));
                    tagGroup.setTags(savedTag);
                    tagString = functionBase.jsonArrayToArrayList(tagItemObject.getJSONArray("tag_array"));


                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            save_button.setClickable(false);

                            String bodyString = body.getText().toString();

                            if(finalImage == null){

                                save_button.setClickable(true);
                                TastyToast.makeText(getApplicationContext(), "이미지가 선택되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);


                            } else if(bodyString == null || bodyString.length() ==0){

                                save_button.setClickable(true);
                                TastyToast.makeText(getApplicationContext(), "아이템 이름이 입력되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            } else {

                                tagItemObject.put("tag", bodyString);
                                tagItemObject.put("image_cdn", finalImage);
                                tagItemObject.put("status", true);
                                tagItemObject.put("tag_array", tagString);
                                tagItemObject.put("type", "alchemy" );
                                tagItemObject.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                            finish();

                                        } else {

                                            TastyToast.makeText(getApplicationContext(), "저장 실패", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
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

        file_status_text.setText("파일 선택 완료 대기 중..");

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            imagePath = images.get(0).path;
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

                    file_status_text.setText("업로드 완료 || 이미지 미리보기 불러오는 중..");
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
                            .into(preview);

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
                file_status_text.setText("업로드 실패");
                e.printStackTrace();
            }



        } else {

            file_status_text.setText("파일 선택 안됨");
        }



    }


}
