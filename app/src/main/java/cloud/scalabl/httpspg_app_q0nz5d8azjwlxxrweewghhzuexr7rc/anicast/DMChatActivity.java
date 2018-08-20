package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.cloudinary.android.policy.TimeWindow;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SubscriptionHandling;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.DMChatdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyTimelineAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class DMChatActivity extends AppCompatActivity {

    RecyclerView chat_list;
    LinearLayout image_preview_layout;
    ImageView delete_button;
    ImageView preview_image;
    ImageView image_button;
    EditText comment_input;
    LinearLayout send_button;

    private File tempFile;
    private String imagePath;
    private ArrayList<Image> images;
    private String finalImage;

    private FunctionBase functionBase;
    private RequestManager requestManager;

    private TextView file_status_text;
    private ParseUser currentUser;
    private String userId;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private DMChatdapter dmChatdapter;
    private ParseObject currentDMList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmchat);

        Intent intent = getIntent();

        userId = intent.getExtras().getString("user");

        finalImage = null;

        chat_list = (RecyclerView) findViewById(R.id.chat_list);
        image_preview_layout = (LinearLayout) findViewById(R.id.image_preview_layout);
        delete_button = (ImageView) findViewById(R.id.delete_button);
        preview_image = (ImageView) findViewById(R.id.preview_image);
        image_button = (ImageView) findViewById(R.id.image_button);
        comment_input = (EditText) findViewById(R.id.comment_input);
        send_button = (LinearLayout) findViewById(R.id.send_button);
        file_status_text = (TextView) findViewById(R.id.file_status_text);

        image_preview_layout.setVisibility(View.GONE);

        functionBase = new FunctionBase(getApplicationContext());
        requestManager = Glide.with(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();



        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                file_status_text.setText("파일 선택 안됨");
                finalImage = null;

                requestManager
                        .load(R.drawable.image_background_500)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .into(preview_image);

                image_preview_layout.setVisibility(View.GONE);

            }
        });

        ParseQuery userQuery = ParseQuery.getQuery("_User");
        userQuery.getInBackground(userId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject userOb, ParseException e) {

                if(e==null){

                    final ArrayList<String> queryString = new ArrayList<>();
                    queryString.add(userOb.getObjectId() + "-" + currentUser.getObjectId());
                    queryString.add(currentUser.getObjectId() + "-" + userOb.getObjectId());

                    ParseLiveQueryClient parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
                    ParseQuery query = ParseQuery.getQuery("DMChat");
                    query.whereContainedIn("room", queryString);
                    query.whereEqualTo("status", true);
                    query.whereEqualTo("open_flag", true);
                    query.orderByDescending("createdAt");

                    SubscriptionHandling<ParseObject> subscriptionHandling = parseLiveQueryClient.subscribe(query);

                    subscriptionHandling.handleEvent(SubscriptionHandling.Event.CREATE, new SubscriptionHandling.HandleEventCallback<ParseObject>() {
                        @Override
                        public void onEvent(ParseQuery<ParseObject> query, ParseObject object) {
                            // HANDLING create event

                            Log.d("message", "hello");
                            dmChatdapter.loadObjects(0);
                        }
                    });





                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
                    //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    //layoutManager.setStackFromEnd(true);
                    layoutManager.setSmoothScrollbarEnabled(true);
                    layoutManager.setReverseLayout(true);
                    int lastVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();

                    chat_list.setLayoutManager(layoutManager);
                    chat_list.setHasFixedSize(true);
                    chat_list.setNestedScrollingEnabled(false);



                    dmChatdapter = new DMChatdapter(getApplicationContext(), requestManager, functionBase, userOb);



                    chat_list.setItemAnimator(new SlideInUpAnimator());
                    chat_list.setAdapter(dmChatdapter);

                    chat_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            if(dy < 0) //check for scroll down
                            {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();


                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                                    Log.d("check", "hello");

                                    if(dmChatdapter.getItemCount() >20){

                                        dmChatdapter.loadNextPage();
                                        Log.d("check", "hello2");

                                    }

                                }



                            }

                        }



                    });

                    ArrayList<String> queryArray = new ArrayList<>();
                    queryArray.add(currentUser.getObjectId() + "-" + userOb.getObjectId());
                    queryArray.add(userOb.getObjectId() + "-" +currentUser.getObjectId());

                    ParseQuery DMListQuery = ParseQuery.getQuery("DMList");
                    DMListQuery.whereContainedIn("room", queryArray);
                    DMListQuery.include("last_chat");
                    DMListQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject dmListOb, ParseException e) {

                            if(e==null){

                                if(dmListOb != null){

                                    currentDMList = dmListOb;
                                    currentMessageCountStatusTrueChange();

                                }

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


        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send_button.setClickable(false);

                String messageText = comment_input.getText().toString();

                if(messageText.length() == 0 && finalImage == null){

                    TastyToast.makeText(getApplicationContext(), "메시지가 입력되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    send_button.setClickable(true);

                } else {

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("body", messageText);
                    params.put("image_cdn", finalImage);
                    params.put("from", currentUser.getObjectId());
                    params.put("to", userId);

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("dm_send", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("tag", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){


                                    send_button.setClickable(true);
                                    dmChatdapter.loadObjects(0);
                                    comment_input.setText("");
                                    functionBase.hideKeyboard(DMChatActivity.this);

                                    file_status_text.setText("파일 선택 안됨");
                                    finalImage = null;

                                    requestManager
                                            .load(R.drawable.image_background_500)
                                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                                            .into(preview_image);

                                    image_preview_layout.setVisibility(View.GONE);



                                } else {

                                    Log.d("step", "chat fail");
                                    send_button.setClickable(true);


                                }

                            } else {


                                Log.d("step", "request fail");
                                e.printStackTrace();
                                send_button.setClickable(true);

                            }
                        }
                    });


                }


            }
        });



    }

    private void currentMessageCountStatusTrueChange(){



        try {
            ParseObject fetchedCurrentDMList = currentDMList.fetch();
            ParseObject fetchedUser = currentUser.fetch();

            Boolean fromUser = false;
            if(fetchedCurrentDMList.getParseObject("from").getObjectId().equals(fetchedUser.getObjectId())){

                fromUser = true;

            } else {

                fromUser = false;

            }

            if(fromUser){

                int dmCount = fetchedCurrentDMList.getInt("from_dm_count");
                fetchedCurrentDMList.addUnique("current_member",fetchedUser.getObjectId());
                fetchedCurrentDMList.put("from_dm_count", 0);
                fetchedCurrentDMList.saveInBackground();

                fetchedUser.increment("dm_count", -dmCount);
                fetchedUser.saveInBackground();

            } else {

                int dmCount = fetchedCurrentDMList.getInt("to_dm_count");
                fetchedCurrentDMList.addUnique("current_member",fetchedUser.getObjectId());
                fetchedCurrentDMList.put("to_dm_count", 0);
                fetchedCurrentDMList.saveInBackground();

                fetchedUser.increment("dm_count", -dmCount);
                fetchedUser.saveInBackground();

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void currentMessageCountStatusFalseChange(){

        try {

            ParseObject fetchedCurrentDMList = currentDMList.fetch();

            ParseObject fetchedUser = currentUser.fetch();

            Boolean fromUser = false;
            if(fetchedCurrentDMList.getParseObject("from").getObjectId().equals(fetchedUser.getObjectId())){

                fromUser = true;

            } else {

                fromUser = false;

            }

            if(fromUser){

                int dmCount = fetchedCurrentDMList.getInt("from_dm_count");
                ArrayList<String> removeArray = new ArrayList<>();
                removeArray.add(fetchedUser.getObjectId());

                fetchedCurrentDMList.removeAll("current_member", removeArray);

                fetchedCurrentDMList.put("from_dm_count", 0);
                fetchedCurrentDMList.saveInBackground();

                fetchedUser.increment("dm_count", -dmCount);
                fetchedUser.saveInBackground();

            } else {

                int dmCount = fetchedCurrentDMList.getInt("to_dm_count");
                ArrayList<String> removeArray = new ArrayList<>();
                removeArray.add(fetchedUser.getObjectId());

                fetchedCurrentDMList.removeAll("current_member", removeArray);
                fetchedCurrentDMList.put("to_dm_count", 0);
                fetchedCurrentDMList.saveInBackground();

                fetchedUser.increment("dm_count", -dmCount);
                fetchedUser.saveInBackground();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        image_preview_layout.setVisibility(View.VISIBLE);
        file_status_text.setText("파일 선택 완료 대기 중..");

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
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
                            .into(preview_image);

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
                file_status_text.setText("업로드 실패");
                e.printStackTrace();
            }



        } else {

            file_status_text.setText("파일 선택 안됨");
        }



    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(dmChatdapter != null){

            dmChatdapter.loadObjects(0);
        }

        if(currentDMList != null){

            currentMessageCountStatusTrueChange();

        }


    }

    @Override
    protected void onPause() {

        if(currentDMList != null){

            currentMessageCountStatusFalseChange();

        }

        super.onPause();

    }

    @Override
    protected void onDestroy() {

        if(currentDMList != null){

            currentMessageCountStatusFalseChange();

        }

        super.onDestroy();

    }
}
