package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketCategoryItemAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketCategoryPriceItemAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketDealerItemAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketDealerPriceItemAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class FundingMarketCategoryPriceFragment extends Fragment {


    private RecyclerView market_list;
    private GridLayoutManager layoutManager;

    int pastVisibleItems, visibleItemCount, totalItemCount;
    private RequestManager requestManager;

    private FundingMarketCategoryPriceItemAdapter fundingMarketCategoryItemAdapter;
    private FundingMarketDealerPriceItemAdapter fundingMarketDealerPriceItemAdapter;

    private String category;
    private String filter;
    private String dealerId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_funding_market, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        filter = getArguments().getString("filter");
        category = getArguments().getString("category");
        dealerId = getArguments().getString("dealerId");

        market_list = (RecyclerView) view.findViewById(R.id.market_list);
        layoutManager = new GridLayoutManager(getActivity(),2);

        market_list.setLayoutManager(layoutManager);
        market_list.setHasFixedSize(true);
        market_list.setNestedScrollingEnabled(false);

        requestManager = Glide.with(getActivity());


        if(category == null){

            ParseQuery query = ParseQuery.getQuery("FundingMarketDealer");
            query.getInBackground(dealerId, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject dealerOb, ParseException e) {

                    if(e==null){

                        //fundingMarketDealerItemAdapter = new FundingMarketDealerItemAdapter(getActivity(), requestManager, dealerOb);

                        fundingMarketDealerPriceItemAdapter = new FundingMarketDealerPriceItemAdapter(getActivity(), requestManager, dealerOb);

                        market_list.setItemAnimator(new SlideInUpAnimator());
                        market_list.setAdapter(fundingMarketDealerPriceItemAdapter);

                        market_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);


                                if(dy > 0) {

                                    visibleItemCount = layoutManager.getChildCount();
                                    totalItemCount = layoutManager.getItemCount();
                                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                                        fundingMarketDealerPriceItemAdapter.loadNextPage();

                                    }

                                }

                            }
                        });

                    } else {

                        e.printStackTrace();
                    }

                }

            });


        } else {

            fundingMarketCategoryItemAdapter = new FundingMarketCategoryPriceItemAdapter(getActivity(), requestManager, category);

            market_list.setItemAnimator(new SlideInUpAnimator());
            market_list.setAdapter(fundingMarketCategoryItemAdapter);

            market_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                    if(dy > 0) {

                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                            fundingMarketCategoryItemAdapter.loadNextPage();

                        }

                    }

                }
            });

        }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }






    @Override
    public void onResume() {
        super.onResume();

        if(fundingMarketCategoryItemAdapter != null){

            fundingMarketCategoryItemAdapter.loadObjects(0);
        }

        if(fundingMarketDealerPriceItemAdapter != null){

            fundingMarketDealerPriceItemAdapter.loadObjects(0);
        }

    }





}
