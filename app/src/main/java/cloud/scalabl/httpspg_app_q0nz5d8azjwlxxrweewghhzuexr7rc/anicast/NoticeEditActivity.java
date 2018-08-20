package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

public class NoticeEditActivity extends AppCompatActivity {

    private EditText body;
    private ImageView body_reset;
    private LinearLayout save_button;
    private ImageView preview;

    private ArrayList<Image> images;

    private ParseUser currentUser;

    private ArtistPost artistPostOb;

    //private static TextView progress_text;

    private String imagePath;

    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;

    private FunctionBase functionBase;

    private EditText title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_edit);

        requestManager = Glide.with(getApplicationContext());

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(this, getApplicationContext());

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button_text.setText("공지사항 관리");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        LinearLayout tag_layout = (LinearLayout) findViewById(R.id.tag_layout);
        tag_layout.setVisibility(View.GONE);

        body = (EditText) findViewById(R.id.post_body);
        body_reset = (ImageView) findViewById(R.id.body_reset);
        body_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                body.setText("");

            }
        });

        title = (EditText) findViewById(R.id.title);
        ImageView title_reset = (ImageView) findViewById(R.id.title_reset);

        title_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title.setText("");

            }
        });

        LinearLayout image_upload = (LinearLayout) findViewById(R.id.represent_upload);
        preview = (ImageView) findViewById(R.id.represent_image);

        TagGroup tagGroup = (TagGroup) findViewById(R.id.tag_group);
        save_button = (LinearLayout) findViewById(R.id.save_button);


        finalImage = null;
        imagePath = null;
        images = new ArrayList<>();

        artistPostOb = new ArtistPost();

        image_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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



        tagGroup.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
            @Override
            public void onAppend(TagGroup tagGroup, String tag) {

            }

            @Override
            public void onDelete(TagGroup tagGroup, String tag) {

            }
        });


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                save_button.setClickable(false);

                if(finalImage == null){

                    TastyToast.makeText(getApplicationContext(), "작품이 등록되지 않았습니다. 작품을 등록해 주세요!", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {

                    startParseObject();



                    String fileType = "";

                    if(functionBase.gifChecker(finalImage)){

                        fileType = "gif";

                    } else {

                        fileType = "png";

                    }

                    Date now = new Date();
                    artistPostOb.put("open_date", now);

                    String bodyString = body.getText().toString();
                    String titleString = title.getText().toString();

                    artistPostOb.put("body", bodyString);
                    artistPostOb.put("title", titleString);
                    artistPostOb.put("image_cdn", finalImage);
                    artistPostOb.put("status", true);
                    artistPostOb.put("open_flag", true);
                    artistPostOb.put("image_type", fileType);

                    artistPostOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                currentUser.increment("post_count");

                                TastyToast.makeText(getApplicationContext(), "저장 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                                startActivity(intent);

                                finish();

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }


            }
        });


    }

    private ArtistPost startParseObject(){


        artistPostOb.put("status", false);
        artistPostOb.put("user", currentUser);
        artistPostOb.put("userId", currentUser.getObjectId());
        artistPostOb.put("comment_count", 0);
        artistPostOb.put("like_count", 0);
        artistPostOb.put("patron_count", 0);
        artistPostOb.put("post_type", "notice");
        artistPostOb.saveInBackground();

        return artistPostOb;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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

                    if(tempFile != null){

                        tempFile.delete();

                    }

                    requestManager
                            .load(finalUploadTaget)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {


                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {


                                    return false;
                                }
                            })
                            .into(preview);

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
            tempFile = new File(sdPath);
            tempFile.mkdirs();

            tempFile = new File(sdPath, "temp.png");

            try {

                functionBase.copy(gifFile, tempFile);
                MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).callback(callback).dispatch();

            } catch (IOException e) {
                e.printStackTrace();
            }



        }



    }

    @Override
    public void onBackPressed() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);

        builder.title("확인");
        builder.content("이 페이지에서 나가시겠습니까?");
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

                finish();

            }
        });
        builder.show();

    }

}
