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

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistWebtoonAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtworkWebtoonCommercialAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommercialGeneralAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.ArtworkCommercialHeaderViewHolder;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtworkWebtoonGeneralFragment extends Fragment {

    RequestManager requestManager;

    RecyclerView webtoon_post_list;
    TagGroup tag_group;

    int pastVisibleItems, visibleItemCount, totalItemCount;


    private ArtworkWebtoonCommercialAdapter artworkWebtoonCommercialAdapter;

    private ParseUser currentUser;

    private FunctionBase functionBase;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artwork_webtoon, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestManager = Glide.with(getActivity());
        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getActivity());

        webtoon_post_list = (RecyclerView) view.findViewById(R.id.webtoon_post_list);
        tag_group = (TagGroup) view.findViewById(R.id.tag_group);

        //태그 세팅

        final TagGroup tag_group = (TagGroup) view.findViewById(R.id.tag_group);



        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                artworkWebtoonCommercialAdapter = new ArtworkWebtoonCommercialAdapter(getActivity(), requestManager);
                webtoon_post_list.setAdapter(artworkWebtoonCommercialAdapter);
                swipeLayout.setRefreshing(false);

            }

        });

        final GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(getActivity(),3);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

            @Override
            public int getSpanSize(int position) {
                if(position == 0){

                    return 3;

                } else {

                    return 1;

                }

            }
        });

        webtoon_post_list.setLayoutManager(layoutManager);
        webtoon_post_list.setNestedScrollingEnabled(false);

        artworkWebtoonCommercialAdapter = new ArtworkWebtoonCommercialAdapter(getActivity(), requestManager);

        webtoon_post_list.setAdapter(artworkWebtoonCommercialAdapter);

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
                        artworkWebtoonCommercialAdapter.loadNextPage();
                    }

                }

            }
        });

        artworkWebtoonCommercialAdapter.addOnQueryLoadListener(new ArtworkWebtoonCommercialAdapter.OnQueryLoadListener<ParseObject>() {
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

        if(artworkWebtoonCommercialAdapter != null){

            artworkWebtoonCommercialAdapter.notifyDataSetChanged();

        }

    }


}
