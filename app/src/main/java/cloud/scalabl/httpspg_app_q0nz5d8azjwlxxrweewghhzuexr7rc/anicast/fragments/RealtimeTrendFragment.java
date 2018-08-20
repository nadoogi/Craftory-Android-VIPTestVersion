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
import com.parse.ParseObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistWebtoonAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.RealTimeRankingAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class RealtimeTrendFragment extends Fragment {

    private int pastVisibleItems, visibleItemCount, totalItemCount;

    RealTimeRankingAdapter realTimeRankingAdapter;
    ArrayList<Object> realtimeRanking;

    private RecyclerView trend_list;
    private FunctionBase functionBase;
    private RequestManager requestManager;
    private LinearLayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_realtime_trend, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getActivity());
        trend_list = (RecyclerView) view.findViewById(R.id.trend_list);


        HashMap<String, Object> params = new HashMap<>();

        ParseCloud.callFunctionInBackground("realtime_tag", params, new FunctionCallback<Map<String, Object>>() {

            public void done(Map<String, Object> mapObject, ParseException e) {

                if (e == null) {

                    if(mapObject.get("status").toString().equals("true")){

                        realtimeRanking = (ArrayList<Object>) mapObject.get("message");

                        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

                        trend_list.setLayoutManager(layoutManager);
                        trend_list.setHasFixedSize(true);
                        trend_list.setNestedScrollingEnabled(false);

                        realTimeRankingAdapter = new RealTimeRankingAdapter(getActivity(), requestManager, realtimeRanking );


                        trend_list.setAdapter(realTimeRankingAdapter);

                    } else {

                        Log.d("step", "realtime favor tag make fail");

                    }

                } else {

                    Log.d("step", "realtime tag fail");
                    e.printStackTrace();

                }
            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "myTimelineFragment");

        //functionBase = null;

        super.onDestroyView();
    }

}
