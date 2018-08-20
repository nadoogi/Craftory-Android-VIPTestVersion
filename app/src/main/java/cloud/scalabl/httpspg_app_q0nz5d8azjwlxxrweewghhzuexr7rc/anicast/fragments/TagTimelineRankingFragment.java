package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FindUserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyCraftAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.RealTimeRankingAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.TagTimelineRankingAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class TagTimelineRankingFragment extends Fragment {

    private RecyclerView new_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private RequestManager requestManager;
    private FunctionBase functionBase;

    private ParseUser currentUser;

    private TagTimelineRankingAdapter tagTimelineRankingAdapter;
    private ArrayList<String> tagString;
    private ArrayList<Object> resultArray;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();
        tagString = getArguments().getStringArrayList("tag");

        return inflater.inflate(R.layout.fragment_tag_timeline_ranking, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                tagTimelineRankingAdapter = new TagTimelineRankingAdapter(getActivity(), requestManager, resultArray);
                new_content_list.setAdapter(tagTimelineRankingAdapter);

                swipeLayout.setRefreshing(false);

            }

        });


        new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);


        FragmentManager manager = getActivity().getSupportFragmentManager();

        requestManager = Glide.with(getActivity());
        functionBase = new FunctionBase(getActivity());


        HashMap<String, Object> params = new HashMap<>();
        params.put("tag_array", tagString);

        ParseCloud.callFunctionInBackground("tagitem_user_ranking", params, new FunctionCallback<Map<String, Object>>() {

            public void done(Map<String, Object> mapObject, ParseException e) {

                if (e == null) {

                    if(mapObject.get("status").toString().equals("true")){

                        resultArray = (ArrayList<Object>) mapObject.get("result");

                        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
                        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

                        new_content_list.setLayoutManager(layoutManager);
                        new_content_list.setHasFixedSize(false);

                        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
                        tagTimelineRankingAdapter = new TagTimelineRankingAdapter(getActivity(), requestManager, resultArray);


                        new_content_list.setItemAnimator(new SlideInUpAnimator());
                        new_content_list.setAdapter(tagTimelineRankingAdapter);


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

        Log.d("onDestoroyView", "MyRecommendCraftFragment");
        functionBase = null;
        requestManager = null;
        new_content_list.setAdapter(null);
        tagTimelineRankingAdapter = null;

        super.onDestroyView();
    }

}
