package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyRankingAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.RecommentPatronAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class MyRankingFragment extends Fragment {

    private RecyclerView new_content_list;
    private RequestManager requestManager;
    public RecommentPatronAdapter recommentPatronAdapter;
    private MyRankingAdapter myRankingAdapter;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private ParseUser currentUser;

    private boolean loading = true;
    private final String fanFilter = "my_fan";
    private final String creatorFilter = "my_creator";
    private final String total = "total_creator";

    private LinearLayout total_filter;
    private LinearLayout my_fan_filter;
    private LinearLayout my_creator_filter;

    private TextView total_ranking;
    private TextView my_fan_ranking;
    private TextView my_creator_ranking;
    private FunctionBase functionBase;

    private String filterFlag;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_patron_ranking, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if(filterFlag.equals("my_fan")){

                    myRankingAdapter = new MyRankingAdapter(getActivity(), requestManager, currentUser, fanFilter);
                    new_content_list.setAdapter(myRankingAdapter);

                } else if(filterFlag.equals("my_creator")){

                    myRankingAdapter = new MyRankingAdapter(getActivity(), requestManager, currentUser, creatorFilter);
                    new_content_list.setAdapter(myRankingAdapter);

                } else if(filterFlag.equals("total")){

                    myRankingAdapter = new MyRankingAdapter(getActivity(), requestManager, currentUser, total);
                    new_content_list.setAdapter(myRankingAdapter);

                }



                swipeLayout.setRefreshing(false);

            }

        });

        swipeLayout.setRefreshing(false);

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getActivity());

        new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);
        total_filter = (LinearLayout) view.findViewById(R.id.total_filter);
        my_fan_filter = (LinearLayout) view.findViewById(R.id.my_fan_filter);
        my_creator_filter = (LinearLayout) view.findViewById(R.id.my_creator_filter);

        total_ranking = (TextView) view.findViewById(R.id.total_ranking);
        my_fan_ranking = (TextView) view.findViewById(R.id.my_fan_ranking);
        my_creator_ranking = (TextView) view.findViewById(R.id.my_creator_ranking);



        requestManager = Glide.with(getActivity());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        new_content_list.setLayoutManager(layoutManager);
        new_content_list.setHasFixedSize(true);
        new_content_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        myRankingAdapter = new MyRankingAdapter(getActivity(), requestManager, currentUser, total);

        total_ranking.setTextColor(functionBase.likedColor);
        my_fan_ranking.setTextColor(functionBase.likeColor);
        my_creator_ranking.setTextColor(functionBase.likeColor);
        filterFlag = "total";

        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(myRankingAdapter);

        new_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        if(myRankingAdapter.getItemCount() > 20){

                            myRankingAdapter.loadNextPage();

                        }

                    }

                }

            }
        });

        total_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRankingAdapter = new MyRankingAdapter(getActivity(), requestManager, currentUser, total);
                new_content_list.setAdapter(myRankingAdapter);

                total_ranking.setTextColor(functionBase.likedColor);
                my_fan_ranking.setTextColor(functionBase.likeColor);
                my_creator_ranking.setTextColor(functionBase.likeColor);

                filterFlag = "total";

            }
        });

        my_fan_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRankingAdapter = new MyRankingAdapter(getActivity(), requestManager, currentUser, fanFilter);
                new_content_list.setAdapter(myRankingAdapter);

                total_ranking.setTextColor(functionBase.likeColor);
                my_fan_ranking.setTextColor(functionBase.likedColor);
                my_creator_ranking.setTextColor(functionBase.likeColor);

                filterFlag = "my_fan";
            }
        });

        my_creator_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRankingAdapter = new MyRankingAdapter(getActivity(), requestManager, currentUser, creatorFilter);
                new_content_list.setAdapter(myRankingAdapter);

                total_ranking.setTextColor(functionBase.likeColor);
                my_fan_ranking.setTextColor(functionBase.likeColor);
                my_creator_ranking.setTextColor(functionBase.likedColor);

                filterFlag = "my_creator";

            }
        });



        myRankingAdapter.addOnQueryLoadListener(new MyRankingAdapter.OnQueryLoadListener<ParseObject>() {
            @Override
            public void onLoading() {



            }

            @Override
            public void onLoaded(List<ParseObject> parseObjects, Exception e) {



            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "myPatronRankingFragment");


        super.onDestroyView();
    }

}
