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
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.parse.FindCallback;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
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

public class ArtistPostingNovelFragment extends Fragment implements FileOpenClickListener {

    private EditText title;
    private EditText body;

    private ImageView represent_image;

    private LinearLayout save_button;

    private TagGroup tagGroup;


    private ArrayList<HashMap<String ,String>> savedImages;
    private String representImageUrl;

    private ParseUser currentUser;


    private ParseObject artistPostOb;

    private File file;
    private List myList;

    final int REQ_CODE_SELECT_IMAGE=200;

    private int orderCount = 0;

    private Button youtube_url_input;
    private EditText youtube_url;


    private String youtube_name;
    private String youtube_description;
    private String youtube_id;
    private String youtube_image;


    private Boolean imageExist = false;

    private String finalImage;

    private File tempFile;

    private FunctionBase functionBase;

    private Switch open_range;
    private Boolean openRangeFlag;

    private TextView file_status_text;


    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_artist_post_novel, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title = (EditText) view.findViewById(R.id.title);
        body = (EditText) view.findViewById(R.id.post_body);

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


        file_status_text = (TextView) view.findViewById(R.id.file_status_text);

        artistPostOb = new ArtistPost();
        functionBase = new FunctionBase(getActivity());

        RequestManager requestManager = Glide.with(getActivity());


        represent_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(Intent.ACTION_PICK);
                //intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                //intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //startActivityForResult(intent, REPRESENT_CODE);

                Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, functionBase.REPRESENT_CODE);

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


        tagGroup = (TagGroup) view.findViewById(R.id.tag_group);

        tag_input_button = (LinearLayout) view.findViewById(R.id.tag_input_button);
        tag_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), TagInputActivity.class);
                intent.putStringArrayListExtra("tags",tagString);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });


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



                String bodyString = body.getText().toString();
                String titleString = title.getText().toString();


                if(imageExist){


                    if(titleString == null || titleString.length() == 0){

                        TastyToast.makeText(getActivity(), "제목 입력은 필수 입니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        save_button.setClickable(true);

                    } else if(finalImage == null ){

                        TastyToast.makeText(getActivity(), "대표 이미지는 필수 입니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        save_button.setClickable(true);

                    } else {

                        Date now = new Date();

                        String image = finalImage;

                        if(tagsArray.size() != 0){

                            artistPostOb.put("tag_array", tagsArray);

                        }

                        Date uniqueIdDate = new Date();
                        String uniqueId = uniqueIdDate.toString();
                        artistPostOb.put("uniqueId", uniqueId);

                        artistPostOb.put("image_cdn", image);
                        artistPostOb.put("title", titleString.trim());
                        artistPostOb.put("body", bodyString.trim());
                        artistPostOb.put("status", true);
                        artistPostOb.put("open_flag", true);
                        artistPostOb.put("progress", "start");
                        artistPostOb.put("open_date", now);
                        artistPostOb.put("lastAction", "novelSave");
                        artistPostOb.put("follow_flag", openRangeFlag);
                        artistPostOb.put("search_string", searchString + bodyString + "," + titleString);

                        artistPostOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    save_button.setClickable(true);

                                    TastyToast.makeText(getActivity(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    Intent intent = new Intent(getActivity(), MainBoardActivity.class);
                                    startActivity(intent);

                                    getActivity().finish();


                                } else {

                                    save_button.setClickable(true);

                                    e.printStackTrace();
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

    private ParseObject startParseObject(){


        artistPostOb.put("status", false);
        artistPostOb.put("user", currentUser);
        artistPostOb.put("userId", currentUser.getObjectId());
        artistPostOb.put("comment_count", 0);
        artistPostOb.put("like_count", 0);
        artistPostOb.put("patron_count", 0);
        artistPostOb.put("post_type", "novel");
        artistPostOb.saveInBackground();

        return artistPostOb;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        file_status_text.setText("파일 선택 완료 대기 중..");

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

                    file_status_text.setText("업로드 완료 || 이미지 미리보기 불러오는 중..");

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

                    Log.d("error1", error.toString());
                    TastyToast.makeText(getActivity(), "업로드가 실패 했습니다 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                    Log.d("error2", error.toString());
                    TastyToast.makeText(getActivity(), "업로드가 실패 했습니다 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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
