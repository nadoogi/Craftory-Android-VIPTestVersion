package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapWell;
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
import com.parse.GetCallback;
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

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.AppConfig;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.DMChatActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MainBoardActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.TagInputActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ArtistPostingFragment extends Fragment {

    private EditText body;
    private ImageView body_reset;
    private LinearLayout save_button;
    private ImageView preview;

    private ArrayList<Image> images;

    private TagGroup tagGroup;

    private ParseUser currentUser;


    private String imagePath;

    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;

    private FunctionBase functionBase;

    private Switch open_range;
    private Boolean openRangeFlag;


    private Switch ad_button;
    private Boolean adOnOffFlag;

    private TextView file_status_text;


    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;

    private BootstrapWell image_layout;

    private Boolean commercialUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        requestManager = Glide.with(getActivity());

        commercialUser = false;

        return inflater.inflate(R.layout.fragment_artist_posts, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getActivity());

        ad_button = (Switch) view.findViewById(R.id.ad_button);

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

                                        TastyToast.makeText(getActivity(), "팔로워 30명 이상, 사이포인트 500 이상이 되면 유료화를 신청하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                                    } else {

                                        ad_button.setChecked(false);
                                        adOnOffFlag = false;

                                        TastyToast.makeText(getActivity(), "팔로워 30명 이상, 사이포인트 500 이상이 되면 유료화를 신청하세요.", TastyToast.LENGTH_LONG, TastyToast.INFO);

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

        body = (EditText) view.findViewById(R.id.post_body);
        body_reset = (ImageView) view.findViewById(R.id.body_reset);

        tag_input_button = (LinearLayout) view.findViewById(R.id.tag_input_button);
        tag_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TagInputActivity.class);
                intent.putStringArrayListExtra("tags",tagString);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });


        ScrollView scroll_view = (ScrollView) view.findViewById(R.id.scroll_view);
        body_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                body.setText("");

            }
        });


        LinearLayout image_upload = (LinearLayout) view.findViewById(R.id.image_upload);
        preview = (ImageView) view.findViewById(R.id.preview);

        preview.setFocusableInTouchMode(true);
        preview.requestFocus();


        tagGroup = (TagGroup) view.findViewById(R.id.tag_group);

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

                    TastyToast.makeText(getActivity(), "중복 태그는 제외 됩니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }

            }

            @Override
            public void onDelete(TagGroup group, String tag) {

                tagString.remove(tag);
                String[] currentTagArray = functionBase.arrayListToString(tagString);
                tagGroup.setTags(currentTagArray);

            }
        });

        save_button = (LinearLayout) view.findViewById(R.id.save_button);

        file_status_text = (TextView) view.findViewById(R.id.file_status_text);

        tagString = new ArrayList<>();
        finalImage = null;
        imagePath = null;
        images = new ArrayList<>();
        openRangeFlag = true;
        open_range = (Switch) view.findViewById(R.id.open_range);



        image_layout = (BootstrapWell) view.findViewById(R.id.image_layout);
        image_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
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



        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_button.setClickable(false);

                if(finalImage == null){

                    TastyToast.makeText(getActivity(), "작품이 등록되지 않았습니다. 작품을 등록해 주세요!", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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

                    String fileType = "";

                    if(functionBase.gifChecker(finalImage)){

                        fileType = "gif";

                    } else {

                        fileType = "png";

                    }

                    Date now = new Date();
                    params.put("open_date", now);

                    String bodyString = body.getText().toString();

                    params.put("body", bodyString);
                    params.put("image_cdn", finalImage);
                    params.put("status", true);
                    params.put("open_flag", true);
                    params.put("image_type", fileType);

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    params.put("search_string", searchString + "," +bodyString);
                    params.put("follow_flag", openRangeFlag);
                    params.put("ad_flag", adOnOffFlag);
                    params.put("lastAction", "postSave");

                    ParseCloud.callFunctionInBackground("post_save", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("tag", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){


                                    TastyToast.makeText(getActivity(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    save_button.setClickable(true);

                                    Intent intent = new Intent(getActivity(), MainBoardActivity.class);
                                    startActivity(intent);

                                    getActivity().finish();

                                } else {

                                    save_button.setClickable(true);

                                    TastyToast.makeText(getActivity(), "저장 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);


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


    }

    Date second1;
    Date second2;
    Date second3;
    Date second4;
    Date second5;
    Date second6;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        file_status_text.setText("파일 선택 완료 대기 중..");


        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            imagePath = images.get(0).path;

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

                    file_status_text.setText("업로드 완료 || 이미지 미리보기 불러오는 중..");

                    if(tempFile != null){

                        tempFile.delete();

                    }

                    Log.d("resultImage", finalUploadTaget);



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
                MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).constrain(TimeWindow.immediate()).callback(callback).dispatch();

            } catch (IOException e) {
                e.printStackTrace();
            }



        } else if(requestCode == functionBase.REQUEST_CODE && resultCode == RESULT_OK && data != null){

            Log.d("callbackdata", data.getStringArrayListExtra("tags").toString());
            tagString = data.getStringArrayListExtra("tags");
            tagGroup.setTags(tagString);

        } else {

            file_status_text.setText("파일 선택 안됨");

        }



    }

    public String getRealPathFromURI(Uri contentUri) {

        String res = null;

        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);

        if(cursor.moveToFirst()){;

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            res = cursor.getString(column_index);

        }

        cursor.close();

        return res;
    }




    @Override
    public void onResume() {
        super.onResume();



    }





}
