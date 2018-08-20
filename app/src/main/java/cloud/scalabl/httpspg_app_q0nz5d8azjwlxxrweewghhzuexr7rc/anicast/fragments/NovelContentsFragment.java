package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.AppConfig;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.NovelActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.NovelContentParseAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.BackKeyOnFragmentListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.NovelEditListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.NovelContent;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static android.app.Activity.RESULT_OK;


/**
 * Created by ssamkyu on 2017. 3. 21..
 */

public class NovelContentsFragment extends Fragment implements NovelEditListener, BackKeyOnFragmentListener {


    private String contentId;
    private String type;

    private WebView content_container;
    private ParseObject contentOb;


    private RequestManager requestManager;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private Boolean novelDataExist = false;
    int order = 0;

    ArrayList<NovelContent> novelListArray;

    private String finalImage;

    private ArtistPost postObject;
    private RecyclerView novel_list;
    private NovelContentParseAdapter novelContentAdapter;
    private EditText novel_input;
    private ParseUser currentUser;
    private Boolean editMode = false;
    private TextView send_button_text;

    private ParseObject editOb;

    private FunctionBase functionBase;

    public NovelContentsFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contentId = getArguments().getString("cardId");
        requestManager = Glide.with(getActivity());
        novelListArray = new ArrayList<>();
        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getActivity());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_novel_contents, container, false);

        novel_list = (RecyclerView) view.findViewById(R.id.novel_list);
        final LinearLayout input_layout = (LinearLayout) view.findViewById(R.id.input_layout);
        novel_input = (EditText) view.findViewById(R.id.novel_input);
        final LinearLayout send_button = (LinearLayout) view.findViewById(R.id.send_button);
        LinearLayout image_upload = (LinearLayout) view.findViewById(R.id.image_upload);
        send_button_text = (TextView) view.findViewById(R.id.send_button_text);

        input_layout.setVisibility(View.GONE);

        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

            }
        });

        ParseQuery<ArtistPost> query = ParseQuery.getQuery(ArtistPost.class);
        query.include("user");
        query.getInBackground(contentId, new GetCallback<ArtistPost>() {
            @Override
            public void done(final ArtistPost postOb, ParseException e) {

                if(e== null){

                    if(currentUser != null){

                        if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                            input_layout.setVisibility(View.VISIBLE);

                        } else {

                            input_layout.setVisibility(View.GONE);

                        }

                    } else {

                        input_layout.setVisibility(View.GONE);

                    }



                    novel_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View v, boolean hasFocus) {

                            if(hasFocus){

                                novel_list.scrollToPosition(novelContentAdapter.getItemCount()-1);

                            }

                        }
                    });

                    postObject = postOb;

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

                    novel_list.setLayoutManager(layoutManager);
                    novel_list.setHasFixedSize(true);
                    novel_list.setNestedScrollingEnabled(false);

                    novelContentAdapter = new NovelContentParseAdapter(getActivity(), requestManager, postOb);
                    novelContentAdapter.setNovelEditListener(NovelContentsFragment.this);

                    novel_list.setItemAnimator(new SlideInUpAnimator());
                    novel_list.setAdapter(novelContentAdapter);


                    send_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            send_button.setClickable(false);

                            final String inputString = novel_input.getText().toString();

                            if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId()) ){

                                if(editMode){

                                    editOb.put("content", inputString);
                                    editOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                TastyToast.makeText(getActivity(), "수정 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                novelContentAdapter.loadObjects(0);

                                                novel_input.setText("");
                                                functionBase.hideKeyboard(getActivity());

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





                                    final ParseObject novelContentOb = new ParseObject("NovelContent");
                                    novelContentOb.put("order", order);
                                    novelContentOb.put("content", inputString);
                                    novelContentOb.put("type", "String");
                                    novelContentOb.put("artist_post", postOb);
                                    novelContentOb.put("status", true);
                                    novelContentOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){


                                                ParseRelation novelContentRelationOb = postOb.getRelation("novel_content");
                                                novelContentRelationOb.add(novelContentOb);
                                                postOb.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if(e==null){

                                                            TastyToast.makeText(getActivity(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            novelContentAdapter.loadObjects(0);

                                                            novel_input.setText("");
                                                            functionBase.hideKeyboard(getActivity());

                                                            novel_list.scrollToPosition(order -1);

                                                            send_button.setClickable(true);

                                                        } else {

                                                            TastyToast.makeText(getActivity(), "저장 실패. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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

                            } else {

                                TastyToast.makeText(getActivity(), "편집 권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                            }


                        }
                    });


                } else {

                    e.printStackTrace();
                }

            }
        });


        return view;

    }

    private ArrayList<HashMap<String, String>> convertToHashMap(ArrayList<NovelContent> inputContent){

        ArrayList<HashMap<String, String>> result = new ArrayList<>();

        for(int i=0;inputContent.size()>i;i++){

            NovelContent data = inputContent.get(i);
            String order = String.valueOf(data.getOrder());
            String content = data.getContent();
            String type = data.getType();

            HashMap<String ,String> newData = new HashMap<>();
            newData.put("order", order);
            newData.put("content", content);
            newData.put("type", type);

            Log.d("convertToHashMap", newData.toString());

            result.add(newData);
        }

        return result;
    }

    private ArrayList<NovelContent> converToNovelContentList(JSONArray inputContent){

        ArrayList<NovelContent> result = new ArrayList<>();

        for(int i=0;inputContent.length()>i;i++){

            try {

                JSONObject data = inputContent.getJSONObject(i);

                String orderString = data.getString("order");
                String content = data.getString("content");
                String type = data.getString("type");

                NovelContent newData = new NovelContent(Integer.parseInt(orderString), content, type);

                Log.d("converToNovel", newData.getContent());

                result.add(newData);

            } catch (JSONException e1) {

                e1.printStackTrace();

            }

        }

        return result;

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ArrayList<Image> images;
        String imagePath;
        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            imagePath = images.get(0).path;
            String imageName = images.get(0).name;

            UploadCallback callback = new UploadCallback() {
                @Override
                public void onStart(String requestId) {

                    Log.d("id", requestId);
                    //progress_text.setVisibility(View.VISIBLE);

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {

                    Log.d("pregress", String.valueOf(bytes/totalBytes * 100) + "%");
                    final String progressString = String.valueOf(bytes/totalBytes * 100) + "%";
                    //progress_text.setText(progressString);

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

                    if(editMode){

                        editOb.put("content", uploadTarget);
                        editOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    TastyToast.makeText(getActivity(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    novelContentAdapter.loadObjects(0);

                                    novel_input.setText("");
                                    functionBase.hideKeyboard(getActivity());

                                    editMode = false;

                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });

                    } else {

                        final ParseObject novelContentOb = new ParseObject("NovelContent");
                        novelContentOb.put("order", order);
                        novelContentOb.put("content", uploadTarget);
                        novelContentOb.put("type", "Image");
                        novelContentOb.put("artist_post", postObject);
                        novelContentOb.put("status", true);
                        novelContentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    ParseRelation novelContentRelationOb = postObject.getRelation("novel_content");
                                    novelContentRelationOb.add(novelContentOb);
                                    postObject.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                TastyToast.makeText(getActivity(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                novelContentAdapter.loadObjects(0);

                                                novel_input.setText("");
                                                functionBase.hideKeyboard(getActivity());

                                                novel_list.scrollToPosition(order -1);


                                            } else {

                                                TastyToast.makeText(getActivity(), "저장 실패. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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
                    TastyToast.makeText(getActivity(), "업로드가 실패 했습니다 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                    Log.d("error2", error.toString());
                    TastyToast.makeText(getActivity(), "업로드가 실패 했습니다 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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
                MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).callback(callback).dispatch();

            } catch (IOException e) {
                e.printStackTrace();
            }



        } else {

            images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            imagePath = images.get(0).path;
            String imageName = images.get(0).name;

            UploadCallback callback = new UploadCallback() {
                @Override
                public void onStart(String requestId) {

                    Log.d("id", requestId);
                    //progress_text.setVisibility(View.VISIBLE);

                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {

                    Log.d("pregress", String.valueOf(bytes/totalBytes * 100) + "%");
                    final String progressString = String.valueOf(bytes/totalBytes * 100) + "%";
                    //progress_text.setText(progressString);

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

                    if(editMode){

                        editOb.put("content", uploadTarget);
                        editOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    TastyToast.makeText(getActivity(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    novelContentAdapter.loadObjects(0);

                                    novel_input.setText("");
                                    functionBase.hideKeyboard(getActivity());

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

                                    TastyToast.makeText(getActivity(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                    novelContentAdapter.loadObjects(0);

                                    novel_input.setText("");
                                    functionBase.hideKeyboard(getActivity());
                                    editMode = false;
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
                    TastyToast.makeText(getActivity(), "업로드가 실패 했습니다 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {

                    Log.d("error2", error.toString());
                    TastyToast.makeText(getActivity(), "업로드가 실패 했습니다 다시 시도해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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
                MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).callback(callback).dispatch();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        ((NovelActivity)context).setBackKeyOnFragmentListener(this);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        super.onResume();



    }


    @Override
    public void onEditorModeOpen(final ParseObject editTargetOb) {

        editMode = true;
        editOb = editTargetOb;

        if(editTargetOb.getString("type").equals("String")){

            send_button_text.setText("수정");
            novel_input.setText(editTargetOb.getString("content"));


        } else {

            Intent intent = new Intent(getActivity(), AlbumSelectActivity.class);
            intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
            int editPhotoRequest = 1000;
            startActivityForResult(intent, editPhotoRequest);


        }





    }


    @Override
    public void onBack() {

        if(editMode){

            novel_input.setText("");
            send_button_text.setText("저장");
            functionBase.hideKeyboard(getActivity());
            editMode = false;
            editOb = null;

        } else {

            getActivity().finish();

        }

    }
}
