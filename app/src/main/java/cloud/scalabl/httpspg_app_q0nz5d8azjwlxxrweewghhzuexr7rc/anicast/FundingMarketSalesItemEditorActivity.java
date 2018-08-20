package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapDropDown;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
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

public class FundingMarketSalesItemEditorActivity extends AppCompatActivity {

    private ImageView preview;
    private EditText name;
    private EditText description;
    private EditText type;
    private EditText price;
    private Button save_button;

    private ArrayList<Image> images;
    private String imagePath;

    private EditText delivery_local;
    private EditText delivery_island;

    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;
    private FunctionBase functionBase;

    private String dealerId;
    private String itemId;
    private ParseObject dealerObject;

    private ParseObject marketItemOb;

    private LinearLayout detail_button;

    private Boolean itemObExist = false;

    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;
    private TagGroup tagGroup;

    private String openType;
    private Boolean openTypeSelectFlag;
    private BootstrapDropDown open_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_market_sales_item_maker);

        Intent intent = getIntent();

        dealerId = intent.getExtras().getString("dealerId");
        itemId = intent.getExtras().getString("itemId");

        preview = (ImageView) findViewById(R.id.preview);
        name = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        type = (EditText) findViewById(R.id.type);
        price = (EditText) findViewById(R.id.price);

        open_type = (BootstrapDropDown) findViewById(R.id.open_type);

        finalImage = null;
        imagePath = null;
        images = new ArrayList<>();

        delivery_local = (EditText) findViewById(R.id.delivery_local);
        delivery_island = (EditText) findViewById(R.id.delivery_island);

        save_button = (Button) findViewById(R.id.save_button);
        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());

        detail_button = (LinearLayout) findViewById(R.id.detail_button);
        detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(marketItemOb != null){

                    Intent intent1 = new Intent(getApplicationContext(), FundingMarketItemDetailEditorActivity.class);
                    intent1.putExtra("itemId", marketItemOb.getObjectId());
                    startActivity(intent1);

                }

            }
        });

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



        ParseQuery dealerQuery= ParseQuery.getQuery("FundingMarketDealer");
        dealerQuery.getInBackground(dealerId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dealerOb, ParseException e) {

                if(e==null){

                    dealerObject = dealerOb;


                    ParseQuery itemQuery = ParseQuery.getQuery("FundingMarketItem");
                    itemQuery.getInBackground(itemId, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject itemOb, ParseException e) {

                            if(e==null){

                                marketItemOb = itemOb;

                                name.setText(itemOb.getString("name"));
                                description.setText(itemOb.getString("description"));
                                type.setText(itemOb.getString("type"));
                                delivery_local.setText(String.valueOf(itemOb.getInt("delivery_local")));
                                delivery_island.setText(String.valueOf(itemOb.getInt("delivery_island")));
                                tagGroup.setTags(functionBase.jsonArrayToArrayList(itemOb.getJSONArray("tag_array")));

                                ArrayList<String> existTagStrings = functionBase.jsonArrayToArrayList(itemOb.getJSONArray("tag_array"));

                                String tagResult = "";

                                for(String tag: existTagStrings){

                                    tagResult += "#" + tag + " ";

                                }

                                finalImage = itemOb.getString("image_cdn");
                                functionBase.generalImageLoading(preview, itemOb, requestManager);

                                price.setText(String.valueOf(itemOb.getInt("price")));
                                dealerObject = itemOb.getParseObject("dealer");



                                save_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        String nameString = name.getText().toString();
                                        String descriptionString = description.getText().toString();
                                        String typeString = type.getText().toString();
                                        String priceString = price.getText().toString();
                                        String delivery_local_string = delivery_local.getText().toString();
                                        String delivery_island_string = delivery_island.getText().toString();

                                        marketItemOb.put("name", nameString);
                                        marketItemOb.put("description", descriptionString);
                                        marketItemOb.put("type", typeString);
                                        marketItemOb.put("tag_array", tagString);
                                        marketItemOb.put("image_cdn", finalImage);
                                        marketItemOb.put("dealer", dealerObject);
                                        marketItemOb.put("price", Integer.parseInt(priceString));
                                        marketItemOb.put("delivery_local", Integer.parseInt(delivery_local_string));
                                        marketItemOb.put("delivery_island", Integer.parseInt(delivery_island_string));
                                        marketItemOb.put("status", true);
                                        marketItemOb.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                if(e==null){

                                                    ParseRelation dealerRelation = dealerObject.getRelation("sales_item");
                                                    dealerRelation.add(marketItemOb);
                                                    dealerObject.saveInBackground();

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

        open_type.setOnDropDownItemClickListener(new BootstrapDropDown.OnDropDownItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int id) {

                switch (id){

                    case 0:

                        open_type.setText("Books");
                        openType = "books";
                        openTypeSelectFlag = true;

                        break;

                    case 1:

                        open_type.setText("패션용품");
                        openType = "fashion";
                        openTypeSelectFlag = true;

                        break;

                    case 2:

                        open_type.setText("기념품");
                        openType = "souvenir";
                        openTypeSelectFlag = true;

                        break;

                    case 3:

                        open_type.setText("뱃지류");
                        openType = "badge";
                        openTypeSelectFlag = true;


                        break;

                    case 4:

                        open_type.setText("쿠션");
                        openType = "cushion";
                        openTypeSelectFlag = true;

                        break;

                    case 5:

                        open_type.setText("문구/펜시");
                        openType = "cushion";
                        openTypeSelectFlag = true;

                        break;

                    case 6:

                        open_type.setText("이벤트");
                        openType = "event";
                        openTypeSelectFlag = true;

                        break;

                }

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
