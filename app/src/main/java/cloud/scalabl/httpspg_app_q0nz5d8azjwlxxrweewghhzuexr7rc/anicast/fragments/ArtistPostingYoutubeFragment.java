package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.ClipboardManager;
import android.content.Context;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.AppConfig;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MainBoardActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.TagInputActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FileOpenClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

import static android.app.Activity.RESULT_OK;
import cz.msebera.android.httpclient.Header;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ArtistPostingYoutubeFragment extends Fragment implements FileOpenClickListener {

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

    private Boolean commercialUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        commercialUser = false;

        return inflater.inflate(R.layout.fragment_artist_post_youtube, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (EditText) view.findViewById(R.id.title);
        body = (EditText) view.findViewById(R.id.post_body);
        youtube_button_text = (TextView)  view.findViewById(R.id.youtube_button_text);

        tagString = new ArrayList<>();

        ImageView title_reset = (ImageView) view.findViewById(R.id.title_reset);
        ImageView body_reset = (ImageView) view.findViewById(R.id.body_reset);

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

        represent_image = (ImageView) view.findViewById(R.id.represent_image);
        LinearLayout represent_upload = (LinearLayout) view.findViewById(R.id.represent_upload);

        save_button = (LinearLayout) view.findViewById(R.id.save_button);

        currentUser = ParseUser.getCurrentUser();

        youtube_url = (EditText) view.findViewById(R.id.youtube_url);
        final LinearLayout youtube_url_input = (LinearLayout) view.findViewById(R.id.youtube_url_input);

        file_status_text = (TextView) view.findViewById(R.id.file_status_text);

        artistPostOb = new ArtistPost();
        functionBase = new FunctionBase(getActivity());

        requestManager = Glide.with(getActivity());


        open_range = (Switch) view.findViewById(R.id.open_range);
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

        tag_input_button = (LinearLayout) view.findViewById(R.id.tag_input_button);
        tag_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TagInputActivity.class);
                intent.putStringArrayListExtra("tags",tagString);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });

        youtube_url_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                youtube_url_input.setClickable(false);
                youtube_button_text.setText("로딩중..");

                ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                String urlString = clipboard.getText().toString();

                if(!urlString.contains("youtu.be/")){

                    TastyToast.makeText(getActivity(), "유튜브 영상 하단의 '공유 > 링크복사'를 이용하세요 ", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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

                                        TastyToast.makeText(getActivity(), "에러3이 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                        e.printStackTrace();

                                        youtube_button_text.setText("붙이기");
                                        youtube_url_input.setClickable(true);

                                    }


                                } else {

                                    TastyToast.makeText(getActivity(), "에러2가 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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

                                TastyToast.makeText(getActivity(), "에러1이 발생했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            }
                        });





                    } else {

                        TastyToast.makeText(getActivity(), "내용이 입력되지 않았습니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                }

            }
        });

        represent_upload.setVisibility(View.GONE);
        represent_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, functionBase.REPRESENT_CODE);
                */

            }
        });

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



        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_button.setClickable(false);

                String bodyString = body.getText().toString();
                String titleString = title.getText().toString();


                if(imageExist){


                    if(titleString == null || titleString.length() == 0){

                        TastyToast.makeText(getActivity(), "제목 입력은 필수 입니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        save_button.setClickable(true);

                    } else if(youtube_id == null || youtube_id.length() == 0){

                        TastyToast.makeText(getActivity(), "유튜브 영상이 입력되지 않았습니다. 다시 입력해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                        params.put("lastAction", "youtubeSave");


                        ParseCloud.callFunctionInBackground("youtube_save", params, new FunctionCallback<Map<String, Object>>() {

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

                } else {

                    TastyToast.makeText(getActivity(), "이미지가 첨부되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);


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

                    RequestManager requestManager = Glide.with(getActivity());

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

            TastyToast.makeText(getActivity(), "취소 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);
            file_status_text.setText("파일 선택 안됨");

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





    public String getPath(Uri uri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        getActivity().startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(columnIndex);

    }





}
