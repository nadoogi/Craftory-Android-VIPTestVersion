package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.bumptech.glide.load.Option;
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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import me.gujun.android.taggroup.TagGroup;

public class FundingMarketSalesItemMakerActivity extends AppCompatActivity {

    private ImageView preview;
    private EditText name;
    private EditText description;
    private EditText price;

    private EditText delivery_local;
    private EditText delivery_island;

    private Button save_button;

    private ArrayList<Image> images;
    private String imagePath;

    private String finalImage;

    private RequestManager requestManager;

    private File tempFile;
    private FunctionBase functionBase;

    private String dealerId;
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

    private EditText option_1;
    private EditText option_2;
    private EditText option_3;
    private Button option_price_button;
    private Button option_save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_market_sales_item_maker);

        final Intent intent = getIntent();

        dealerId = intent.getExtras().getString("dealerId");

        preview = (ImageView) findViewById(R.id.preview);
        name = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        price = (EditText) findViewById(R.id.price);

        delivery_local = (EditText) findViewById(R.id.delivery_local);
        delivery_island = (EditText) findViewById(R.id.delivery_island);

        open_type = (BootstrapDropDown) findViewById(R.id.open_type);
        openTypeSelectFlag = false;

        option_1 = (EditText) findViewById(R.id.option_1);
        option_2 = (EditText) findViewById(R.id.option_2);
        option_3 = (EditText) findViewById(R.id.option_3);
        option_price_button = (Button) findViewById(R.id.option_price_button);
        option_save_button = (Button) findViewById(R.id.option_save_button);

        option_save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String option1 = option_1.getText().toString();
                final String option2 = option_2.getText().toString();
                final String option3 = option_3.getText().toString();

                if(marketItemOb == null){

                    marketItemOb = new ParseObject("FundingMarketItem");
                    marketItemOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            itemObExist = true;

                            optionDataSave(marketItemOb, option1, option2, option3, new SaveCallback() {
                                @Override
                                public void done(ParseException e) {


                                //옵션 저장완료

                                }
                            });
                        }
                    });

                } else {


                    optionDataSave(marketItemOb, option1, option2, option3, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            //옵션 저장완료

                        }
                    });


                }

            }
        });

        option_price_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(marketItemOb != null){

                    Intent intent1 = new Intent(getApplicationContext(), OptionPriceSettingActivity.class);
                    intent1.putExtra("itemId", marketItemOb.getObjectId());
                    startActivity(intent1);

                } else {


                    //아직 옵션이 저장되지 않았습니다.

                }

            }
        });

        detail_button = (LinearLayout) findViewById(R.id.detail_button);
        detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(marketItemOb == null){

                    marketItemOb = new ParseObject("FundingMarketItem");
                    marketItemOb.put("option_exist", false);
                    marketItemOb.saveInBackground();

                    itemObExist = true;

                }

                Intent intent1 = new Intent(getApplicationContext(), FundingMarketItemDetailEditorActivity.class);
                intent1.putExtra("itemId", marketItemOb.getObjectId());
                startActivity(intent1);

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

        finalImage = null;
        imagePath = null;
        images = new ArrayList<>();

        save_button = (Button) findViewById(R.id.save_button);
        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());



        ParseQuery dealerQuery= ParseQuery.getQuery("FundingMarketDealer");
        dealerQuery.getInBackground(dealerId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dealerOb, ParseException e) {

                if(e==null){

                    dealerObject = dealerOb;

                    save_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            String nameString = name.getText().toString();
                            String descriptionString = description.getText().toString();
                            String priceString = price.getText().toString();
                            String delivery_local_string = delivery_local.getText().toString();
                            String delivery_island_string = delivery_island.getText().toString();

                            if(!itemObExist){

                                marketItemOb = new ParseObject("FundingMarketItem");
                                marketItemOb.put("option_exist", false);

                            }

                            marketItemOb.put("name", nameString);
                            marketItemOb.put("description", descriptionString);
                            marketItemOb.put("tag_array", tagString);
                            marketItemOb.put("image_cdn", finalImage);
                            marketItemOb.put("type", openType);
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



        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AlbumSelectActivity.class);
                intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

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


    private ArrayList<String> makeStringToArray(String options){

        ArrayList<String> result = new ArrayList<>();

        if(options == null){

            return result;

        } else if(options.length() == 0) {

            return result;

        } else {

            String[] items = options.split(",");

            for(int i=0;items.length>i;i++){

                String item = items[i];

                result.add(item);

            }

            return result;

        }

    }

    private void optionDataSave(ParseObject object, String option1, String option2, String option3, SaveCallback saveCallback){

        int count = 0;
        int totalSize = 0;

        ArrayList<String> option1List = makeStringToArray(option1);
        ArrayList<String> option2List = makeStringToArray(option2);
        ArrayList<String> option3List = makeStringToArray(option3);

        int option1Size = option1List.size();
        int option2Size = option2List.size();
        int option3Size = option3List.size();

        if(option1Size != 0 && option2Size != 0 && option3Size != 0){

            object.put("option1", option1List);
            object.put("option2", option2List);
            object.put("option3", option3List);
            object.saveInBackground();

            totalSize = option1Size * option2Size * option3Size;

            for(int i=0; option1Size>i;i++){

                for(int j=0; option2Size>j;j++){

                    for(int k=0;option3Size>k;k++){

                        count += 1;

                        ArrayList<String> targetArray = new ArrayList<>();
                        targetArray.add(option1List.get(i));
                        targetArray.add(option2List.get(j));
                        targetArray.add(option3List.get(k));

                        ParseObject optionOb = new ParseObject("SalesItemOption");
                        optionOb.put("option", targetArray);
                        optionOb.put("option_price", 0);
                        optionOb.put("market_item", object);
                        optionOb.put("status", true);

                        if(count == totalSize){

                            optionOb.saveInBackground(saveCallback);

                        } else {

                            optionOb.saveInBackground();

                        }


                    }

                }

            }

        } else if(option1Size != 0 && option2Size != 0 && option3Size == 0) {

            totalSize = option1Size * option2Size;

            object.put("option1", option1List);
            object.put("option2", option2List);
            object.saveInBackground();

            for(int i=0; option1Size>i;i++){

                for(int j=0; option2Size>j;j++){

                    count += 1;

                    ArrayList<String> targetArray = new ArrayList<>();
                    targetArray.add(option1List.get(i));
                    targetArray.add(option2List.get(j));

                    ParseObject optionOb = new ParseObject("SalesItemOption");
                    optionOb.put("option", targetArray);
                    optionOb.put("option_price", 0);
                    optionOb.put("market_item", object);
                    optionOb.put("status", true);

                    if(count == totalSize){

                        optionOb.saveInBackground(saveCallback);

                    } else {

                        optionOb.saveInBackground();

                    }


                }

            }

        } else if(option1Size != 0 && option2Size == 0 && option3Size == 0){

            totalSize = option1Size;

            object.put("option1", option1List);
            object.saveInBackground();

            JSONArray result = new JSONArray();

            for(int i=0; option1Size>i;i++){

                for(int j=0; option2Size>j;j++){

                    count += 1;

                    ArrayList<String> targetArray = new ArrayList<>();
                    targetArray.add(option1List.get(i));
                    targetArray.add(option2List.get(j));

                    ParseObject optionOb = new ParseObject("SalesItemOption");
                    optionOb.put("option", targetArray);
                    optionOb.put("option_price", 0);
                    optionOb.put("market_item", object);
                    optionOb.put("status", true);

                    if(count == totalSize){

                        optionOb.saveInBackground(saveCallback);

                    } else {

                        optionOb.saveInBackground();

                    }

                }

            }

            Log.d("result", result.toString());

        } else {

            Log.d("result", "no data");

        }
    }

    private void optionSettingReset(ParseObject itemOb, final SaveCallback saveCallback){

        ParseQuery savedOptionObs = ParseQuery.getQuery("SalesItemOption");
        savedOptionObs.whereEqualTo("market_item", itemOb);
        savedOptionObs.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> optionObs, ParseException e) {

                if(e==null){

                    int counter = 0;

                    for(int i=0;optionObs.size()>i;i++){

                        counter += 1;

                        ParseObject optionOb = optionObs.get(i);
                        optionOb.put("status", false);

                        if(optionObs.size() == counter){

                            optionOb.saveInBackground(saveCallback);

                        } else {

                            optionOb.saveInBackground();

                        }


                    }

                } else {

                    e.printStackTrace();

                }


            }

        });


    }


}
