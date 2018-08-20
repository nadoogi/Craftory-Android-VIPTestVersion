package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseUser;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistNovelPostAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtistNovelPostFragment extends Fragment {

    RequestManager requestManager;

    RecyclerView webtoon_post_list;
    TagGroup tag_group;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    ArtistNovelPostAdapter artistNovelPostAdapter;

    ParseUser currentUser;
    FunctionBase functionBase;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artist_novel_post, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestManager = Glide.with(getActivity());
        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getActivity());

        webtoon_post_list = (RecyclerView) view.findViewById(R.id.webtoon_post_list);
        tag_group = (TagGroup) view.findViewById(R.id.tag_group);

        if(currentUser != null){

            tag_group.setTags(functionBase.jsonArrayToArrayList(currentUser.getJSONArray("tags")));

        }



        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                artistNovelPostAdapter.loadObjects(0);
                swipeLayout.setRefreshing(false);

            }

        });

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),1);

        webtoon_post_list.setLayoutManager(layoutManager);
        //webtoon_post_list.setHasFixedSize(true);
        webtoon_post_list.setNestedScrollingEnabled(false);

        artistNovelPostAdapter = new ArtistNovelPostAdapter(getActivity(), requestManager);

        webtoon_post_list.setItemAnimator(new SlideInUpAnimator());
        webtoon_post_list.setAdapter(artistNovelPostAdapter);

        webtoon_post_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                    {
                        artistNovelPostAdapter.loadNextPage();
                    }

                }

            }
        });



    }


    @Override
    public void onResume() {
        super.onResume();
        if(artistNovelPostAdapter != null){

            artistNovelPostAdapter.loadObjects(0);

        }

    }


}
