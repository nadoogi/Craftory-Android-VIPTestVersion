package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.parse.CountCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
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
import de.hdodenhof.circleimageview.CircleImageView;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;


public class MyInfoActivity extends AppCompatActivity {

    LinearLayout logout;
    EditText username;
    EditText my_talk;
    private File tempFile;

    LinearLayout photo_upload;
    LinearLayout save_button;

    CircleImageView profile_image;
    private ParseUser currentUser;
    private ParseObject mileageOb;


    final int REQ_CODE_SELECT_IMAGE=100;

    private int REPRESENT_CODE = 10;

    private String finalImage;

    private FunctionBase functionBase;
    private TextView file_status_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);


        currentUser = ParseUser.getCurrentUser();
        ParseInstallation currentInstallation = ParseInstallation.getCurrentInstallation();
        functionBase = new FunctionBase(this, getApplicationContext());


        logout = (LinearLayout) findViewById(R.id.logout);
        username = (EditText) findViewById(R.id.username);
        my_talk = (EditText) findViewById(R.id.my_talk);
        profile_image = (CircleImageView) findViewById(R.id.profile_image);
        file_status_text = (TextView) findViewById(R.id.file_status_text);

        save_button = (LinearLayout) findViewById(R.id.save_button);


        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);


        photo_upload = (LinearLayout) findViewById(R.id.photo_upload);

        photo_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, REPRESENT_CODE);

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("내 정보 관리");


        if(currentUser !=null){

            currentUser.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject fetchedUser, ParseException e) {


                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            final String nameInputString = username.getText().toString();
                            final String myTalk = my_talk.getText().toString();

                            ParseQuery query = ParseQuery.getQuery("_User");
                            query.whereEqualTo("name", nameInputString);
                            if(currentUser != null){

                                query.whereNotEqualTo("objectId", currentUser.getObjectId() );

                            }
                            query.countInBackground(new CountCallback() {
                                @Override
                                public void done(int count, ParseException e) {

                                    if(e==null){

                                        if(count == 0){

                                            if(nameInputString.length() == 0){

                                                TastyToast.makeText(getApplicationContext(), "닉네임을 입력하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                            } else {

                                                currentUser.put("name", nameInputString);
                                                currentUser.put("my_talk", myTalk);
                                                currentUser.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if(e==null){

                                                            TastyToast.makeText(getApplicationContext(), "저장이 완료되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                            Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                                                            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            } else {
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                            }
                                                            startActivity(intent);

                                                        } else {

                                                            e.printStackTrace();

                                                        }



                                                    }
                                                });

                                            }

                                        } else {

                                            username.setError("중복된 닉네임이 있습니다.");

                                        }

                                    } else {

                                        e.printStackTrace();
                                    }

                                }
                            });



                        }
                    });


                    functionBase.profileImageLoading(profile_image, fetchedUser, Glide.with(getApplicationContext()));
                    functionBase.profileNameLoading(username, fetchedUser);

                    if(fetchedUser.get("my_talk") != null){

                        my_talk.setText(fetchedUser.getString("my_talk"));

                    }


                    logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            logout.setClickable(false);
                            if(currentUser != null){

                                ParseUser.logOut();

                                Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                } else {
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                }
                                startActivity(intent);

                                logout.setClickable(true);

                            } else {

                                logout.setClickable(true);

                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(intent);

                            }

                        }
                    });



                }
            });


        } else {

            username.setText("로그인이 필요 합니다.");


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == REPRESENT_CODE && resultCode == RESULT_OK && data != null) {
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

                    file_status_text.setText("업로드 완료");

                    Log.d("resultdata", resultData.get("secure_url").toString());

                    String uploadImg = resultData.get("secure_url").toString();
                    String[] splitString = uploadImg.split("/");
                    String uploadTarget = splitString[splitString.length-2] + "/" + splitString[splitString.length-1];
                    String finalUploadTaget = functionBase.imageUrlBase300 + uploadTarget;

                    finalImage = uploadTarget;

                    if(tempFile != null){

                        tempFile.delete();

                    }

                    currentUser.put("image_cdn", finalImage);
                    currentUser.saveInBackground();

                    RequestManager requestManager = Glide.with(getApplicationContext());

                    requestManager
                            .load(finalUploadTaget)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
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
                            .into(profile_image);

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
                MediaManager.get().upload(sdPath+"/temp.png").unsigned(AppConfig.image_preset).callback(callback).dispatch();

            } catch (IOException e) {
                file_status_text.setText("업로드 실패");
                e.printStackTrace();
            }


        }

    }


    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(columnIndex);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(username !=null && currentUser != null){

            if(currentUser.get("name") != null){

                username.setText(currentUser.getString("name"));

            } else {

                username.setText(currentUser.getUsername());

            }



        }

    }
}
