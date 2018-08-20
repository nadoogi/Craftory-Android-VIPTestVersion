package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketDealerAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketSalesItemAddAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

import static com.igaworks.adbrix.db.ViralCPIDAO.getActivity;

public class FundingMarketItemAddActivity extends AppCompatActivity {

    private String dealerId;
    private CircleImageView dealer_image;
    private TextView dealer_name;

    private FunctionBase functionBase;
    private RequestManager requestManager;

    private RecyclerView item_list;
    private Button item_add_button;

    private GridLayoutManager layoutManager;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private FundingMarketSalesItemAddAdapter fundingMarketSalesItemAddAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_maket_item_add);

        Intent intent = getIntent();
        dealerId = intent.getExtras().getString("dealerId");
        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());

        item_list = (RecyclerView) findViewById(R.id.item_list);
        item_add_button = (Button) findViewById(R.id.item_add_button);

        dealer_image = (CircleImageView) findViewById(R.id.dealer_image);
        dealer_name = (TextView) findViewById(R.id.dealer_name);

        item_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(), FundingMarketSalesItemMakerActivity.class);
                intent1.putExtra("dealerId", dealerId);
                startActivity(intent1);

            }
        });

        ParseQuery dealerQuery = ParseQuery.getQuery("FundingMarketDealer");
        dealerQuery.getInBackground(dealerId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dealerOb, ParseException e) {

                if(e==null){

                    functionBase.profileImageLoading(dealer_image, dealerOb, requestManager);
                    dealer_name.setText(dealerOb.getString("name"));

                    layoutManager = new GridLayoutManager(getActivity(),2);
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                    layoutManager.setSmoothScrollbarEnabled(true);

                    item_list.setLayoutManager(layoutManager);
                    item_list.setHasFixedSize(true);
                    item_list.setNestedScrollingEnabled(false);

                    fundingMarketSalesItemAddAdapter = new FundingMarketSalesItemAddAdapter(getApplicationContext(), requestManager, dealerOb);

                    item_list.setItemAnimator(new SlideInUpAnimator());
                    item_list.setAdapter(fundingMarketSalesItemAddAdapter );

                    item_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                                    if(fundingMarketSalesItemAddAdapter .getItemCount() >20){

                                        fundingMarketSalesItemAddAdapter .loadNextPage();
                                        Log.d("check", "hello2");

                                    }

                                }



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
