package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import java.util.ArrayList;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.TagTimelineFavorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.TagTimelineRecentAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class TagTimelineRecentFragment extends Fragment {

    private RecyclerView new_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private TagTimelineRecentAdapter tagTimelineRecentAdapter;
    private RequestManager requestManager;

    private boolean loading = true;

    RelativeLayout background_layout;

    private final String TAG = "MyTimelineFragment";

    private FunctionBase functionBase;
    private FunctionPost functionPost;
    private ArrayList<String> tagString;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        tagString = new ArrayList<>();

        tagString = getArguments().getStringArrayList("tag");

        return inflater.inflate(R.layout.fragment_content_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                tagTimelineRecentAdapter = new TagTimelineRecentAdapter(getActivity(), requestManager, functionBase, functionPost, tagString);
                new_content_list.setAdapter(tagTimelineRecentAdapter);

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


        tagTimelineRecentAdapter = new TagTimelineRecentAdapter(getActivity(), requestManager, functionBase, functionPost, tagString);


        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(tagTimelineRecentAdapter);

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

                        if(tagTimelineRecentAdapter.getItemCount() >20){

                            tagTimelineRecentAdapter.loadNextPage();

                        }

                    }



                }

            }



        });


    }



    @Override
    public void onResume() {
        super.onResume();

        if(tagTimelineRecentAdapter != null){

            tagTimelineRecentAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onDestroyView() {

        functionBase = null;
        functionPost = null;
        requestManager = null;
        tagTimelineRecentAdapter = null;
        new_content_list.setAdapter(null);

        super.onDestroyView();
    }
}
