package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketSalesItemAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentPatronAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class FundingMarketFragment extends Fragment {


    private RecyclerView market_list;
    private GridLayoutManager layoutManager;

    int pastVisibleItems, visibleItemCount, totalItemCount;
    private RequestManager requestManager;

    private FundingMarketSalesItemAdapter fundingMarketSalesItemAdapter;
    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        position = getArguments().getInt("page");

        return inflater.inflate(R.layout.fragment_funding_market, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        market_list = (RecyclerView) view.findViewById(R.id.market_list);
        layoutManager = new GridLayoutManager(getActivity(),2);
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

        market_list.setLayoutManager(layoutManager);
        market_list.setHasFixedSize(true);
        market_list.setNestedScrollingEnabled(false);

        requestManager = Glide.with(getActivity());

        fundingMarketSalesItemAdapter = new FundingMarketSalesItemAdapter(getActivity(), requestManager, position);

        market_list.setItemAnimator(new SlideInUpAnimator());
        market_list.setAdapter(fundingMarketSalesItemAdapter);

        market_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy > 0) {

                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        fundingMarketSalesItemAdapter.loadNextPage();

                    }

                }

            }
        });



    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }






    @Override
    public void onResume() {
        super.onResume();

        if(fundingMarketSalesItemAdapter != null){

            fundingMarketSalesItemAdapter.loadObjects(0);
        }


    }





}
