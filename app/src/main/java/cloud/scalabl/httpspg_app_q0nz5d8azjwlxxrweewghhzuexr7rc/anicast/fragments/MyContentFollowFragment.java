package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseUser;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyRecentFollowListAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class MyContentFollowFragment extends Fragment {

    int pastVisibleItems, visibleItemCount, totalItemCount;
    private MyRecentFollowListAdapter myRecentFollowListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_my_content_dashboard, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView my_content_list = (RecyclerView) view.findViewById(R.id.my_content_list);


        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myRecentFollowListAdapter.loadObjects(0);

                swipeLayout.setRefreshing(false);

            }

        });

        RequestManager requestManager = Glide.with(getActivity());
        ParseUser currentUser = ParseUser.getCurrentUser();

        if(currentUser != null){

            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
            //final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);

            my_content_list.setLayoutManager(layoutManager);
            my_content_list.setHasFixedSize(true);
            my_content_list.setNestedScrollingEnabled(false);

            //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
            //myTimelineAdapter = new MyTimelineAdapter(getActivity(), requestManager);
            myRecentFollowListAdapter = new MyRecentFollowListAdapter(getActivity(), requestManager);


            my_content_list.setItemAnimator(new SlideInUpAnimator());
            my_content_list.setAdapter(myRecentFollowListAdapter);

            my_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);


                    if(dy > 0){
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            myRecentFollowListAdapter.loadNextPage();
                        }

                    }

                }
            });


        } else {


        }





    }



    @Override
    public void onResume() {
        super.onResume();


    }

}
