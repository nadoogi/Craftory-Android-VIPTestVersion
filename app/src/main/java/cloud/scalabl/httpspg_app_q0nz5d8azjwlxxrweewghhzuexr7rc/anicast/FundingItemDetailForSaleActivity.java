package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketItemEditorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import me.gujun.android.taggroup.TagGroup;

public class FundingItemDetailForSaleActivity extends AppCompatActivity {

    int pastVisibleItems, visibleItemCount, totalItemCount;
    protected RecyclerView comment_list;

    private GridLayoutManager layoutManager;

    private ParseObject itemObject;

    private TextView back_button_text;
    private LinearLayout back_button;


    private String objectId;

    private ImageView item_image;
    private LinearLayout item_image_layout;
    private TextView name;

    private LinearLayout tag_layout;
    private TagGroup tag_group;

    private RecyclerView item_detail_list;
    private LinearLayout funding_button;
    private FunctionBase functionBase;
    private RequestManager requestManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_item_detail_for_sale);

        Intent intent = getIntent();

        objectId = intent.getExtras().getString("objectId");
        functionBase = new FunctionBase(getApplicationContext());
        requestManager = Glide.with(getApplicationContext());

        comment_list = (RecyclerView) findViewById(R.id.comment_list);

        back_button_text = (TextView) findViewById(R.id.back_button_text);
        back_button = (LinearLayout) findViewById(R.id.back_button);

        back_button_text.setText("상품 정보");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        ParseQuery itemQuery = ParseQuery.getQuery("FundingMarketItem");
        itemQuery.include("dealer");
        itemQuery.getInBackground(objectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parentObject, ParseException e) {

                if(e==null){

                    itemObject = parentObject;

                    item_image = (ImageView) findViewById(R.id.item_image);
                    item_image_layout = (LinearLayout) findViewById(R.id.item_image_layout);
                    name = (TextView) findViewById(R.id.name);

                    tag_layout = (LinearLayout) findViewById(R.id.tag_layout);
                    tag_group = (TagGroup) findViewById(R.id.tag_group);

                    item_detail_list = (RecyclerView) findViewById(R.id.item_detail_list);
                    funding_button = (LinearLayout) findViewById(R.id.funding_button);


                    functionBase.generalImageLoading(item_image, parentObject, requestManager);
                    name.setText(parentObject.getString("name"));

                    String[] tagArray = functionBase.jsonArrayToStringArray(parentObject.getJSONArray("tag_array"));

                    if(tagArray.length != 0){

                        tag_group.setTags(functionBase.jsonArrayToStringArray(parentObject.getJSONArray("tag_array")));
                        tag_layout.setVisibility(View.VISIBLE);

                    } else {

                        tag_layout.setVisibility(View.GONE);

                    }



                    LinearLayoutManager layoutManagerForDetail = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
                    item_detail_list.setLayoutManager(layoutManagerForDetail);
                    item_detail_list.setHasFixedSize(true);

                    FundingMarketItemEditorAdapter fundingMarketItemEditorAdapter = new FundingMarketItemEditorAdapter(getApplicationContext(), requestManager, parentObject);
                    item_detail_list.setAdapter(fundingMarketItemEditorAdapter);


                } else {

                    e.printStackTrace();
                }

            }

        });

    }
}
