package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.AdminActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CreateArtworkActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CreateFundingActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CreateSerieseActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.DealerTimelineAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyTimelineAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class DealerTimelineFragment extends Fragment {

    private RecyclerView new_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    private DealerTimelineAdapter dealerTimelineAdapter;

    private RequestManager requestManager;

    private boolean loading = true;

    RelativeLayout background_layout;

    private final String TAG = "DealerTimelineFragment";

    private FunctionBase functionBase;
    private FunctionPost functionPost;

    private ParseUser currentUser;
    private String dealerId;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();

        return inflater.inflate(R.layout.fragment_content_dealer_timeline, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dealerId = getArguments().getString("dealerId");

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.d(TAG, "swipe" );

                dealerTimelineAdapter = new DealerTimelineAdapter(getActivity(), requestManager, functionBase, functionPost, dealerId);
                new_content_list.setAdapter(dealerTimelineAdapter);

                swipeLayout.setRefreshing(false);

            }

        });


        new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);
        background_layout = (RelativeLayout) view.findViewById(R.id.background_layout);

        requestManager = Glide.with(getActivity());
        functionBase  = new FunctionBase(getActivity());
        functionPost = new FunctionPost(getActivity());


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        new_content_list.setLayoutManager(layoutManager);
        new_content_list.setHasFixedSize(true);
        new_content_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        dealerTimelineAdapter = new DealerTimelineAdapter(getActivity(), requestManager, functionBase, functionPost, dealerId);


        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(dealerTimelineAdapter);

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

                        if(dealerTimelineAdapter.getItemCount() >20){

                            dealerTimelineAdapter.loadNextPage();

                        }

                    }



                }

            }



        });





    }



    @Override
    public void onResume() {
        super.onResume();

        if(dealerTimelineAdapter != null){

            Log.d(TAG, "resume" );

            dealerTimelineAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "myTimelineFragment");

        functionBase = null;
        functionPost = null;
        requestManager = null;
        dealerTimelineAdapter = null;
        new_content_list.setAdapter(null);

        super.onDestroyView();
    }
}
