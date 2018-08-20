package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Environment;
import android.support.annotation.NonNull;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SerieseArtworkAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FileOpenClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ImageInfoClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

public class SerieseManagerActivity extends AppCompatActivity implements FileOpenClickListener, ImageInfoClickListener {

    int pastVisibleItems, visibleItemCount, totalItemCount;

    final int REQ_CODE_SELECT_IMAGE=200;

    private String finalImage;
    private RequestManager requestManager;

    private File tempFile;

    private ParseObject serieseOb;

    private ImageView seriese_image;
    private LinearLayout upload_image;
    private EditText title_text;
    private TagGroup tag_group;
    private LinearLayout save_button;
    private LinearLayout mon;
    private LinearLayout tue;
    private LinearLayout wen;
    private LinearLayout thu;
    private LinearLayout fri;
    private LinearLayout sat;
    private LinearLayout sun;

    private Boolean monFlag;
    private Boolean tueFlag;
    private Boolean wenFlag;
    private Boolean thuFlag;
    private Boolean friFlag;
    private Boolean satFlag;
    private Boolean sunFlag;

    private String commercial_status = "default";

    private FunctionBase functionBase;
    private LinearLayout delete_button;

    private ParseUser currentUser;

    private TextView file_status_text;


    private LinearLayout list_button;
    private LinearLayout tag_button;
    private String serieseId;

    private ArrayList<String> tagStringArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seriese_manager);

        Intent intent = getIntent();

        serieseId = intent.getExtras().getString("serieseId");


        list_button = (LinearLayout) findViewById(R.id.list_button);
        tag_button = (LinearLayout) findViewById(R.id.tag_button);

        seriese_image = (ImageView) findViewById(R.id.patron_image);
        upload_image = (LinearLayout) findViewById(R.id.upload_image);
        title_text = (EditText) findViewById(R.id.title_text);
        tag_group = (TagGroup) findViewById(R.id.tag_group);
        save_button = (LinearLayout) findViewById(R.id.save_button);
        mon = (LinearLayout) findViewById(R.id.mon);
        tue = (LinearLayout) findViewById(R.id.tue);;
        wen = (LinearLayout) findViewById(R.id.wen);;
        thu = (LinearLayout) findViewById(R.id.thu);;
        fri = (LinearLayout) findViewById(R.id.fri);;
        sat = (LinearLayout) findViewById(R.id.sat);;
        sun = (LinearLayout) findViewById(R.id.sun);;
        ImageView title_reset = (ImageView) findViewById(R.id.title_reset);
        delete_button = (LinearLayout) findViewById(R.id.delete_button);

        file_status_text = (TextView) findViewById(R.id.file_status_text);


        currentUser = ParseUser.getCurrentUser();

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeLayout.setRefreshing(false);

            }

        });


        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(this, getApplicationContext());

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        back_button_text.setText("연재 관리");

        title_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                title_text.setText("");

            }
        });

        tag_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TagInputForSerieseActivity.class);
                intent.putStringArrayListExtra("tags", tagStringArray);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });

        seriese_image.setFocusableInTouchMode(true);
        seriese_image.requestFocus();

        monFlag = false;
        tueFlag = false;
        wenFlag = false;
        thuFlag = false;
        friFlag = false;
        satFlag = false;
        sunFlag = false;

        ParseQuery<ArtistPost> artistQuery = ParseQuery.getQuery(ArtistPost.class);
        artistQuery.getInBackground(serieseId, new GetCallback<ArtistPost>() {
            @Override
            public void done(final ArtistPost object, ParseException e) {

                if(e==null){

                    serieseOb = object;

                    if(delete_button != null){

                        delete_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                MaterialDialog.Builder builder = new MaterialDialog.Builder(SerieseManagerActivity.this);

                                builder.title("확인");
                                builder.content("삭제 하시겠습니까?");
                                builder.positiveText("예");
                                builder.negativeText("아니요");
                                builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    }
                                });

                                builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                        serieseOb.put("status", false);
                                        serieseOb.put("lastAction", "serieseDelete");
                                        serieseOb.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                if(e==null){

                                                    if( currentUser != null){

                                                        currentUser.increment("serise_count",-1);
                                                        currentUser.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(ParseException e) {

                                                                if(e==null){

                                                                    TastyToast.makeText(getApplicationContext(), "삭제 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                                    finish();

                                                                } else {

                                                                    e.printStackTrace();
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
                                });
                                builder.show();



                            }
                        });

                    }





                    final ArrayList<Integer> dayArrayList = new ArrayList<>();

                    if(serieseOb.get("image_cdn_temp") != null){

                        if(serieseOb.getString("image_cdn_temp").length() != 0 ){

                            requestManager
                                    .load( functionBase.imageUrlBase300 + serieseOb.getString("image_cdn_temp") )
                                    .into(seriese_image);

                        } else {

                            if(serieseOb.get("image_cdn") != null){

                                functionBase.generalImageLoading(seriese_image, serieseOb, requestManager);

                            } else {

                                requestManager
                                        .load( R.drawable.image_background )
                                        .into(seriese_image);

                            }

                        }

                    } else if(serieseOb.get("image_cdn") != null){

                        requestManager
                                .load( functionBase.imageUrlBase300 + serieseOb.getString("image_cdn") )
                                .into(seriese_image);

                    } else {

                        requestManager
                                .load( R.drawable.image_background )
                                .into(seriese_image);

                    }

                    if(serieseOb.get("title") != null){

                        title_text.setText( serieseOb.getString("title") );
                        title_text.setSelection( title_text.getText().length() );

                    } else {

                        title_text.setText("");

                    }

                    if(serieseOb.get("tag_array") != null){

                        tagStringArray = functionBase.jsonArrayToArrayList( serieseOb.getJSONArray("tag_array") );
                        tag_group.setTags(tagStringArray);

                    }

                    upload_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                            intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                            startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

                        }
                    });

                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            serieseOb.fetchInBackground(new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject fetchedParentOb, ParseException e) {

                                    if(fetchedParentOb.get("image_cdn_temp") != null){

                                        if(fetchedParentOb.getString("image_cdn_temp").length() != 0){

                                            fetchedParentOb.put("image_cdn", fetchedParentOb.getString("image_cdn_temp") );
                                            fetchedParentOb.put("image_cdn_temp", "");

                                        }

                                    }

                                    String title = title_text.getText().toString();

                                    String[] taglist = tag_group.getTags();

                                    ArrayList<String> tagsArray = new ArrayList<>();

                                    for(String tagItem : taglist){

                                        if(tagItem.length() != 0){

                                            tagsArray.add(tagItem);

                                        }

                                    }

                                    if(tagsArray.size() != 0){

                                        fetchedParentOb.put("tag_array", tagsArray);

                                    }

                                    fetchedParentOb.put("title", title);
                                    fetchedParentOb.put("day_array", dayArrayList);
                                    fetchedParentOb.put("lastAction", "serieseEdit");
                                    fetchedParentOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){


                                                TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            } else {

                                                TastyToast.makeText(getApplicationContext(), "저장 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                e.printStackTrace();
                                            }


                                        }
                                    });

                                }
                            });

                        }
                    });


                    list_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent1 = new Intent(getApplicationContext(), SerieseManagerListActivity.class);
                            intent1.putExtra("serieseId", serieseId);
                            startActivity(intent1);

                        }
                    });



                    tag_group.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
                        @Override
                        public void onAppend(TagGroup tagGroup, String tag) {

                        }

                        @Override
                        public void onDelete(TagGroup tagGroup, String tag) {

                        }
                    });


                    if(serieseOb.get("day_array") != null){

                        JSONArray dayArray = serieseOb.getJSONArray("day_array");

                        for(int i=0;dayArray.length()>i;i++){

                            try {

                                int day = dayArray.getInt(i);

                                switch (day){

                                    case 0:

                                        mon.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                        monFlag = true;
                                        dayArrayList.add(0);

                                        break;

                                    case 1:

                                        tue.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                        tueFlag = true;
                                        dayArrayList.add(1);
                                        break;


                                    case 2:

                                        wen.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                        wenFlag = true;
                                        dayArrayList.add(2);
                                        break;


                                    case 3:

                                        thu.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                        thuFlag = true;
                                        dayArrayList.add(3);
                                        break;


                                    case 4:

                                        fri.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                        friFlag = true;
                                        dayArrayList.add(4);
                                        break;

                                    case 5:

                                        sat.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                        satFlag = true;
                                        dayArrayList.add(5);
                                        break;

                                    case 6:

                                        sun.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                        sunFlag = true;
                                        dayArrayList.add(6);
                                        break;


                                }


                            } catch (JSONException e1) {

                                e.printStackTrace();

                            }


                        }

                    }

                    mon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(monFlag){

                                mon.setBackgroundResource(R.drawable.button_radius_0dp_whitegray_default);
                                int index = dayArrayList.indexOf(0);
                                dayArrayList.remove(index);
                                monFlag = false;

                            } else {

                                mon.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                dayArrayList.add(0);
                                monFlag = true;

                            }


                        }
                    });

                    tue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(tueFlag){

                                tue.setBackgroundResource(R.drawable.button_radius_0dp_whitegray_default);
                                int index = dayArrayList.indexOf(1);
                                dayArrayList.remove(index);
                                tueFlag = false;

                            } else {

                                tue.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                dayArrayList.add(1);
                                tueFlag = true;

                            }

                        }
                    });

                    wen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(wenFlag){

                                wen.setBackgroundResource(R.drawable.button_radius_0dp_whitegray_default);
                                int index = dayArrayList.indexOf(2);
                                dayArrayList.remove(index);
                                wenFlag = false;

                            } else {

                                wen.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                dayArrayList.add(2);
                                wenFlag = true;

                            }


                        }
                    });

                    thu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(thuFlag){

                                thu.setBackgroundResource(R.drawable.button_radius_0dp_whitegray_default);
                                int index = dayArrayList.indexOf(3);
                                dayArrayList.remove(index);
                                thuFlag = false;

                            } else {

                                thu.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                dayArrayList.add(3);
                                thuFlag = true;

                            }


                        }
                    });

                    fri.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(friFlag){

                                fri.setBackgroundResource(R.drawable.button_radius_0dp_whitegray_default);
                                int index = dayArrayList.indexOf(4);
                                dayArrayList.remove(index);
                                friFlag = false;

                            } else {

                                fri.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                dayArrayList.add(4);
                                friFlag = true;

                            }

                        }
                    });

                    sat.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(satFlag){

                                sat.setBackgroundResource(R.drawable.button_radius_0dp_whitegray_default);
                                int index = dayArrayList.indexOf(5);
                                dayArrayList.remove(index);
                                satFlag = false;

                            } else {

                                sat.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                dayArrayList.add(5);
                                satFlag = true;

                            }

                        }
                    });

                    sun.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(sunFlag){

                                sun.setBackgroundResource(R.drawable.button_radius_0dp_whitegray_default);
                                int index = dayArrayList.indexOf(6);
                                dayArrayList.remove(index);
                                sunFlag = false;

                            } else {

                                sun.setBackgroundResource(R.drawable.button_radius_0dp_point);
                                dayArrayList.add(6);
                                sunFlag = true;

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
    public void onFileOpenClicked() {

        Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
        startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == REQ_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            file_status_text.setText("파일 선택 완료 대기 중..");

            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);

            String imagePath = images.get(0).path;
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

                    file_status_text.setText("업로드 완료");

                    Log.d("resultdata", resultData.get("secure_url").toString());

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    finalImage = uploadTarget;

                    serieseOb.put("image_cdn_temp", finalImage);
                    serieseOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e== null){

                                if(tempFile != null){

                                    tempFile.delete();

                                }

                                TastyToast.makeText(getApplicationContext(), "이미지 저장됨", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            } else {

                                e.printStackTrace();
                            }



                        }
                    });



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



        } else if(requestCode == functionBase.REQUEST_CODE && resultCode == RESULT_OK && data != null){

            tagStringArray = data.getStringArrayListExtra("tags");
            tag_group.setTags(tagStringArray);

            serieseOb.put("tag_array", tagStringArray);
            serieseOb.saveInBackground();

        } else {

            file_status_text.setText("파일 선택 안됨");

        }



    }


    @Override
    public void onImageInfoOpened(HashMap<String, String> data) {

    }


    @Override
    protected void onDestroy() {

        serieseOb.put("image_cdn_temp", "");
        serieseOb.saveInBackground();

        super.onDestroy();

    }

    @Override
    protected void onPostResume() {

        super.onPostResume();

    }
}
