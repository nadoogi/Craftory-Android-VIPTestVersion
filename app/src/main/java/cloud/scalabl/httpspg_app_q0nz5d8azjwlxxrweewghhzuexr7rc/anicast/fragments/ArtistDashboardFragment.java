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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtworkDashBoardBestCommentAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtworkDashBoardIllustRankingAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtworkDashBoardNewSerieseAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtworkDashBoardWebnovelRankingAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtworkDashBoardWebtoonRankingAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.NewCraftorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.TimelineAdapter;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtistDashboardFragment extends Fragment {

    //public static RecyclerView new_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    public static TimelineAdapter timelineAdapter;
    private static RequestManager requestManager;

    private boolean loading = true;
    RecyclerView creator_list;
    NewCraftorAdapter newCraftorAdapter;

    RecyclerView new_seriese_list;
    ArtworkDashBoardNewSerieseAdapter artworkDashBoardNewSerieseAdapter;

    RecyclerView best_comment_list;
    ArtworkDashBoardBestCommentAdapter artworkDashBoardBestCommentAdapter;

    RecyclerView webtoon_ranking_list;
    ArtworkDashBoardWebtoonRankingAdapter artworkDashBoardWebtoonRankingAdapter;

    RecyclerView webnovel_ranking_list;
    ArtworkDashBoardWebnovelRankingAdapter artworkDashBoardWebnovelRankingAdapter;

    RecyclerView illust_ranking_list;
    ArtworkDashBoardIllustRankingAdapter artworkDashBoardIllustRankingAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        requestManager = Glide.with(getActivity());

        return inflater.inflate(R.layout.fragment_artist_dashboard, container, false);

    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final BannerSlider bannerSlider = (BannerSlider) view.findViewById(R.id.banner_slider);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //timelineAdapter.loadObjects(0);

                swipeLayout.setRefreshing(false);

            }

        });

        ParseQuery bannerQuery = ParseQuery.getQuery("Banner");
        bannerQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> bannerObs, ParseException e) {

                if(e==null){


                    List<Banner> banners=new ArrayList<>();

                    for(int i=0;bannerObs.size()>i;i++){

                        banners.add(new RemoteBanner(bannerObs.get(i).getParseFile("image").getUrl()));

                    }

                    bannerSlider.setBanners(banners);

                } else {

                    e.printStackTrace();
                }


            }
        });

        //new_seriese
        new_seriese_list = (RecyclerView) view.findViewById(R.id.new_seriese_list);
        final LinearLayoutManager newSerieseLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        new_seriese_list.setLayoutManager(newSerieseLayoutManager);
        new_seriese_list.setHasFixedSize(true);
        new_seriese_list.setNestedScrollingEnabled(false);

        artworkDashBoardNewSerieseAdapter = new ArtworkDashBoardNewSerieseAdapter(getActivity(), requestManager);
        new_seriese_list.setAdapter(artworkDashBoardNewSerieseAdapter);


        //best comment
        best_comment_list = (RecyclerView) view.findViewById(R.id.best_comment_list);
        final LinearLayoutManager bestCommentLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        best_comment_list.setLayoutManager(bestCommentLayoutManager);
        best_comment_list.setHasFixedSize(true);
        best_comment_list.setNestedScrollingEnabled(false);
        artworkDashBoardBestCommentAdapter = new ArtworkDashBoardBestCommentAdapter(getActivity(), requestManager);
        best_comment_list.setAdapter(artworkDashBoardBestCommentAdapter);



        //illust ranking
        illust_ranking_list = (RecyclerView) view.findViewById(R.id.illust_ranking_list);
        final LinearLayoutManager illustRankingLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        illust_ranking_list.setLayoutManager(illustRankingLayoutManager);
        illust_ranking_list.setHasFixedSize(true);
        illust_ranking_list.setNestedScrollingEnabled(false);
        artworkDashBoardIllustRankingAdapter = new ArtworkDashBoardIllustRankingAdapter(getActivity(), requestManager);

        illust_ranking_list.setAdapter(artworkDashBoardIllustRankingAdapter);


        //webtoon ranking
        webtoon_ranking_list = (RecyclerView) view.findViewById(R.id.webtoon_ranking_list);
        final LinearLayoutManager webtoonRankingLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        webtoon_ranking_list.setLayoutManager(webtoonRankingLayoutManager);
        webtoon_ranking_list.setHasFixedSize(true);
        webtoon_ranking_list.setNestedScrollingEnabled(false);
        artworkDashBoardWebtoonRankingAdapter = new ArtworkDashBoardWebtoonRankingAdapter(getActivity(), requestManager);
        webtoon_ranking_list.setAdapter(artworkDashBoardWebtoonRankingAdapter);



        //webnovel ranking
        webnovel_ranking_list = (RecyclerView) view.findViewById(R.id.webnovel_ranking_list);
        final LinearLayoutManager webnovelRankingLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        webnovel_ranking_list.setLayoutManager(webnovelRankingLayoutManager);
        webnovel_ranking_list.setHasFixedSize(true);
        webnovel_ranking_list.setNestedScrollingEnabled(false);
        artworkDashBoardWebnovelRankingAdapter = new ArtworkDashBoardWebnovelRankingAdapter(getActivity(), requestManager);
        webnovel_ranking_list.setAdapter(artworkDashBoardWebnovelRankingAdapter);


        //creator list function
        creator_list = (RecyclerView) view.findViewById(R.id.creator_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        creator_list.setLayoutManager(layoutManager);
        creator_list.setHasFixedSize(true);
        creator_list.setNestedScrollingEnabled(false);

        newCraftorAdapter = new NewCraftorAdapter(getActivity(), requestManager);
        creator_list.setAdapter(newCraftorAdapter);




        /*
        RecyclerView new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);




        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        new_content_list.setLayoutManager(layoutManager);
        new_content_list.setHasFixedSize(true);
        new_content_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        timelineAdapter = new TimelineAdapter(getActivity(), requestManager);


        new_content_list.setAdapter(timelineAdapter);


        new_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if(loading){

                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                            loading = false;

                            timelineAdapter.loadNextPage();

                            loading = true;

                        }

                    }

                }

            }
        });

        */




    }




    @Override
    public void onResume() {
        super.onResume();

        //timelineAdapter.loadObjects(0);

    }

}
