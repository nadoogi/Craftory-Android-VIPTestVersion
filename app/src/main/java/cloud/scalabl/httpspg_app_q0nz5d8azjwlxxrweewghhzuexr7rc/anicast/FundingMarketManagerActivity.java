package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.DMChatdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketDealerAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class FundingMarketManagerActivity extends AppCompatActivity {

    private RecyclerView dealer_list;
    private Button dealer_add_button;
    private LinearLayoutManager layoutManager;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private FundingMarketDealerAdapter fundingMarketDealerAdapter;
    private RequestManager requestManager;
    private FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_market_manager);

        dealer_list = (RecyclerView) findViewById(R.id.dealer_list);
        dealer_add_button = (Button) findViewById(R.id.dealer_add_button);

        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());

        dealer_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DealerMakerActivity.class);
                startActivity(intent);

            }
        });

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //layoutManager.setStackFromEnd(true);
        layoutManager.setSmoothScrollbarEnabled(true);

        dealer_list.setLayoutManager(layoutManager);
        dealer_list.setHasFixedSize(true);
        dealer_list.setNestedScrollingEnabled(false);



        fundingMarketDealerAdapter = new FundingMarketDealerAdapter(getApplicationContext(), requestManager);



        dealer_list.setItemAnimator(new SlideInUpAnimator());
        dealer_list.setAdapter(fundingMarketDealerAdapter);

        dealer_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                        if(fundingMarketDealerAdapter.getItemCount() >20){

                            fundingMarketDealerAdapter.loadNextPage();
                            Log.d("check", "hello2");

                        }

                    }



                }

            }



        });


    }
}
