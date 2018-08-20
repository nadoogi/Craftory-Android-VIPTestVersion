package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.AlchemyTagItemAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.RealTimeRankingAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.TagItemManagerAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class AlchemyFragment extends Fragment {

    private int pastVisibleItems, visibleItemCount, totalItemCount;

    RealTimeRankingAdapter realTimeRankingAdapter;
    ArrayList<Object> realtimeRanking;

    private RecyclerView alchemy_list;
    private FunctionBase functionBase;
    private RequestManager requestManager;
    private GridLayoutManager layoutManager;
    private AlchemyTagItemAdapter alchemyTagItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_alchemy, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                alchemyTagItemAdapter = new AlchemyTagItemAdapter( getActivity(), requestManager);
                alchemy_list.setAdapter(alchemyTagItemAdapter);

                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getActivity());
        alchemy_list = (RecyclerView) view.findViewById(R.id.alchemy_list);


        layoutManager = new GridLayoutManager(getActivity(),3);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(position==0){

                    return 3;

                } else {

                    return 1;

                }
            }
        });


        alchemy_list.setLayoutManager(layoutManager);
        alchemy_list.setHasFixedSize(true);
        alchemy_list.setNestedScrollingEnabled(false);

        alchemyTagItemAdapter = new AlchemyTagItemAdapter( getActivity(), requestManager);


        alchemy_list.setItemAnimator(new SlideInUpAnimator());
        alchemy_list.setAdapter(alchemyTagItemAdapter);

        alchemy_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy > 0){
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        alchemyTagItemAdapter.loadNextPage();
                    }

                }

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        if(alchemyTagItemAdapter != null){

            alchemyTagItemAdapter.loadObjects(0);
        }

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "myTimelineFragment");

        //functionBase = null;

        super.onDestroyView();
    }

}
