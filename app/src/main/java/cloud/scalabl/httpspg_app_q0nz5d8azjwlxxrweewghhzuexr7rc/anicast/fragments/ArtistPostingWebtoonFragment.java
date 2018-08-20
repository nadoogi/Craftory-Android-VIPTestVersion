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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.beardedhen.androidbootstrap.BootstrapButton;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MainBoardActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.TagInputActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FileOpenClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ImageInfoClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ImageUploadSelector2Adapter;
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

public class ArtistPostingWebtoonFragment extends Fragment implements FileOpenClickListener , ImageInfoClickListener{

    private EditText title;
    private EditText body;

    private ImageView represent_image;

    private LinearLayout save_button;

    private TagGroup tagGroup;

    private RecyclerView image_list;
    private ImageUploadSelector2Adapter imageAdapter;

    private ArrayList<Image> represent_images;
    private String representImageUrl;

    private ParseUser currentUser;

    private RequestManager requestManager;

    private File file;
    private List myList;

    final int REQ_CODE_SELECT_IMAGE=200;

    private int orderCount;

    private ArrayList<Image> images;
    private String finalImage;
    private String imagePath;
    private ArrayList<HashMap<String ,String>> savedImages;

    private File tempFile;

    private FunctionBase functionBase;

    private Switch open_range;
    private Boolean openRangeFlag;

    private Switch ad_button;
    private Boolean adOnOffFlag;

    private TextView file_status_text;
    private TextView illust_status_text;


    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;

    private Boolean commercialUser;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        requestManager = Glide.with(getActivity());
        functionBase = new FunctionBase(getActivity());

        commercialUser = false;

        return inflater.inflate(R.layout.fragment_artist_post_webtoon, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (EditText) view.findViewById(R.id.title);
        body = (EditText) view.findViewById(R.id.post_body);

        ImageView title_reset = (ImageView) view.findViewById(R.id.title_reset);
        ImageView body_reset = (ImageView) view.findViewById(R.id.body_reset);
        illust_status_text = (TextView) view.findViewById(R.id.illust_status_text);

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
        open_range = (Switch) view.findViewById(R.id.open_range);

        represent_image.setFocusableInTouchMode(true);
        represent_image.requestFocus();


        image_list = (RecyclerView) view.findViewById(R.id.image_list);

        save_button = (LinearLayout) view.findViewById(R.id.save_button);

        file_status_text = (TextView) view.findViewById(R.id.file_status_text);

        currentUser = ParseUser.getCurrentUser();


        finalImage = null;
        imagePath = null;
        images = new ArrayList<>();
        savedImages = new ArrayList<>();
        tagString = new ArrayList<>();

        orderCount = 0;

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


        represent_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, functionBase.REPRESENT_CODE);

            }
        });

        tag_input_button = (LinearLayout) view.findViewById(R.id.tag_input_button);
        tag_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TagInputActivity.class);
                intent.putStringArrayListExtra("tags",tagString);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });


        tagGroup = (TagGroup) view.findViewById(R.id.tag_group);
        tagGroup.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
            @Override
            public void onAppend(TagGroup tagGroup, String tag) {

                if(!tagString.contains(tag)){

                    tagString.add(tag);

                } else {

                    TastyToast.makeText(getActivity(), "중복 태그는 제외 됩니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }

            }

            @Override
            public void onDelete(TagGroup tagGroup, String tag) {

                tagString.remove(tag);
                String[] currentTagArray = functionBase.arrayListToString(tagString);
                tagGroup.setTags(currentTagArray);

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



        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_button.setClickable(false);

                if(savedImages.size() == 0){

                    TastyToast.makeText(getActivity(), "앨범에 이미지가 등록되지 않았습니다. 이미지를 등록하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    save_button.setClickable(true);

                } else if(finalImage == null){

                    TastyToast.makeText(getActivity(), "대표 이미지가 등록되지 않았습니다. 이미지를 등록하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
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
                    String titleString = title.getText().toString();

                    params.put("body", bodyString);
                    params.put("title", titleString);
                    params.put("image_cdn", finalImage);
                    params.put("status", true);
                    params.put("open_flag", true);
                    params.put("ad_flag", adOnOffFlag);
                    params.put("image_type", fileType);

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    params.put("search_string", searchString + "," +bodyString);
                    params.put("follow_flag", openRangeFlag);
                    params.put("lastAction", "webtoonSave");
                    params.put("image_array", savedImages);

                    ParseCloud.callFunctionInBackground("webtoon_save", params, new FunctionCallback<Map<String, Object>>() {

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



        final LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);

        image_list.setLayoutManager(layoutManager);
        image_list.setHasFixedSize(true);

        requestManager = Glide.with(getActivity());

        imageAdapter = new ImageUploadSelector2Adapter(getActivity(), savedImages, requestManager, illust_status_text);
        imageAdapter.setFileOpenClickListener(this);
        image_list.setAdapter(imageAdapter);



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        file_status_text.setText("파일 선택 완료 대기 중..");

        if(resultCode == RESULT_OK && data != null){


            if (requestCode == REQ_CODE_SELECT_IMAGE ) {

                images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

                imagePath = images.get(0).path;

                UploadCallback callback = new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {

                        illust_status_text.setText("파일 업로드 시작");

                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {

                        float progress = functionBase.makeProgress(Float.parseFloat(String.valueOf(bytes)), Float.parseFloat(String.valueOf(totalBytes)));
                        illust_status_text.setText("업로드 중 : " + String.valueOf(bytes) + "/" + String.valueOf(totalBytes) + " || "  + String.valueOf(progress * 100) + "%");

                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {


                        String uploadImg = resultData.get("secure_url").toString();
                        String[] splitString = uploadImg.split("/");
                        String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                        String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                        orderCount = savedImages.size();

                        HashMap<String, String> newMap = new HashMap();
                        newMap.put("order", String.valueOf(orderCount));
                        newMap.put("url", uploadTarget);

                        savedImages.add(newMap);

                        illust_status_text.setText("업로드 완료 || 이미지 미리보기 불러오는 중..");


                        if(imageAdapter != null){

                            imageAdapter = new ImageUploadSelector2Adapter(getActivity(), savedImages, requestManager, illust_status_text);
                            imageAdapter.setFileOpenClickListener(ArtistPostingWebtoonFragment.this);
                            imageAdapter.setImageInfoClickListener(ArtistPostingWebtoonFragment.this);
                            image_list.setAdapter(imageAdapter);

                        }

                        if(tempFile != null){

                            tempFile.delete();

                        }

                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {

                        illust_status_text.setText("업로드 실패");

                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {

                        illust_status_text.setText("업로드 실패");

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
                    illust_status_text.setText("업로드 실패");
                    e.printStackTrace();
                }


            } else if( requestCode == functionBase.REPRESENT_CODE ){


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


            } else if(requestCode == functionBase.REQUEST_CODE ){

                Log.d("callbackdata", data.getStringArrayListExtra("tags").toString());
                tagString = data.getStringArrayListExtra("tags");
                tagGroup.setTags(tagString);

            }

        } else {

            TastyToast.makeText(getActivity(), "취소 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            file_status_text.setText("파일 선택 안됨");


        }


    }



    @Override
    public void onResume() {
        super.onResume();

        if(imageAdapter != null){

            imageAdapter.notifyDataSetChanged();

        }

        if(represent_image != null){

            represent_image.requestFocus();

        }

    }


    @Override
    public void onFileOpenClicked() {

        //Intent intent = new Intent(Intent.ACTION_PICK);
        //intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        //intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

        Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

    }

    @Override
    public void onImageInfoOpened(final HashMap<String, String> data) {

        boolean wrapInScrollView = true;

        final MaterialDialog imageDialog = new MaterialDialog.Builder(getActivity())
                .title("이미지 정보")
                .customView(R.layout.layout_image_info, wrapInScrollView)
                .show();

        final String url = data.get("url");
        String order = data.get("order");

        ImageView currentImage = (ImageView) imageDialog.getCustomView().findViewById(R.id.current_image);
        BootstrapButton deleteButton = (BootstrapButton) imageDialog.getCustomView().findViewById(R.id.delete_button);

        Glide.with(getActivity()).load(functionBase.imageUrlBase300 + data.get("url")).into(currentImage);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;savedImages.size()>i;i++){

                    if(savedImages.get(i).get("url").equals(url)){

                        savedImages.remove(i);

                    }


                }

                if(imageAdapter != null){

                    imageAdapter = new ImageUploadSelector2Adapter(getActivity(), savedImages, requestManager, illust_status_text);
                    imageAdapter.setFileOpenClickListener(ArtistPostingWebtoonFragment.this);
                    imageAdapter.setImageInfoClickListener(ArtistPostingWebtoonFragment.this);
                    image_list.setAdapter(imageAdapter);

                }

                imageDialog.hide();

            }
        });





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
