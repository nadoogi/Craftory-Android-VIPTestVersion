package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.parse.ParseException;
import com.parse.ParseObject;
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

public class DealerMakerActivity extends AppCompatActivity {

    private ImageView preview;
    private EditText name;
    private EditText description;
    private Button save_button;

    private EditText cs_phone;
    private EditText email;
    private EditText homepage;

    private ArrayList<Image> images;
    private String imagePath;

    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;
    private FunctionBase functionBase;

    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;
    private TagGroup tagGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_maker);

        preview = (ImageView) findViewById(R.id.preview);
        name = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);

        cs_phone = (EditText) findViewById(R.id.cs_phone);
        email = (EditText) findViewById(R.id.email);
        homepage = (EditText) findViewById(R.id.homepage);


        finalImage = null;
        imagePath = null;
        images = new ArrayList<>();

        save_button = (Button) findViewById(R.id.save_button);
        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());

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

        tagString = new ArrayList<>();

        tag_input_button = (LinearLayout) findViewById(R.id.tag_input_button);
        tag_input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TagInputActivity.class);
                intent.putStringArrayListExtra("tags",tagString);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameString = name.getText().toString();
                String descriptionString = description.getText().toString();
                String csPhoneString = cs_phone.getText().toString();
                String emailString = email.getText().toString();
                String homepageString = homepage.getText().toString();

                ParseObject dealerOb = new ParseObject("FundingMarketDealer");
                dealerOb.put("name", nameString);
                dealerOb.put("description", descriptionString);
                dealerOb.put("cs_phone", csPhoneString);
                dealerOb.put("email", emailString);
                dealerOb.put("homepage",homepageString);
                dealerOb.put("tag_array", tagString);
                dealerOb.put("image_cdn", finalImage);
                dealerOb.put("status", true);
                dealerOb.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e==null){

                            TastyToast.makeText(getApplicationContext(), "저장 완료",TastyToast.LENGTH_LONG, TastyToast.SUCCESS );
                            finish();

                        } else {

                            TastyToast.makeText(getApplicationContext(), "저장 실패",TastyToast.LENGTH_LONG, TastyToast.ERROR );
                            e.printStackTrace();
                        }

                    }
                });




            }
        });

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

            }
        });

    }

    private ArrayList<String> tagMaker(String tagString){

        ArrayList<String> tagResult = new ArrayList<>();

        String[] tagList = tagString.split("#");

        for(String tag:tagList){

            if(tag.length() != 0){

                tagResult.add(tag);

            }

        }


        return tagResult;

    };


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


                }

                @Override
                public void onProgress(String requestId, long bytes, long totalBytes) {


                }

                @Override
                public void onSuccess(String requestId, Map resultData) {

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


                }

                @Override
                public void onReschedule(String requestId, ErrorInfo error) {


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

        }

    }

}
