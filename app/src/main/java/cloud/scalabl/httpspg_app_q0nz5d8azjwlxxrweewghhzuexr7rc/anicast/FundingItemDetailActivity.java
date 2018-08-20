package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingItemDetailAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PostDetailAdapter;

public class FundingItemDetailActivity extends AppCompatActivity {

    int pastVisibleItems, visibleItemCount, totalItemCount;
    protected RecyclerView comment_list;

    private FundingItemDetailAdapter fundingItemDetailAdapter;
    private GridLayoutManager layoutManager;

    private ParseObject itemObject;

    private TextView back_button_text;
    private LinearLayout back_button;


    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_item_detail);

        Intent intent = getIntent();

        objectId = intent.getExtras().getString("objectId");

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
            public void done(ParseObject itemOb, ParseException e) {

                if(e==null){

                    itemObject = itemOb;

                    layoutManager = new GridLayoutManager(getApplicationContext(),2);

                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

                        @Override
                        public int getSpanSize(int position) {
                            if(position == 0){

                                return 2;

                            } else {

                                return 1;

                            }
                        }
                    });

                    comment_list.setNestedScrollingEnabled(true);
                    comment_list.setLayoutManager(layoutManager);
                    comment_list.setHasFixedSize(true);

                    fundingItemDetailAdapter = new FundingItemDetailAdapter(getApplicationContext(), Glide.with(getApplicationContext()), itemOb);

                    comment_list.setAdapter(fundingItemDetailAdapter);


                } else {

                    e.printStackTrace();
                }

            }

        });

    }
}
