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
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistWebtoonAdapter;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtworkWebtoonWeeklyFragment extends Fragment {

    private int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artist_webtoon_seriese, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final BannerSlider bannerSlider = (BannerSlider) view.findViewById(R.id.banner_slider);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeLayout.setRefreshing(false);

            }

        });

        RequestManager requestManager = Glide.with(getActivity());

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

        RecyclerView seriese_novel_list = (RecyclerView) view.findViewById(R.id.webtoon_seriese_list);

        final GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(getActivity(),3);

        seriese_novel_list.setLayoutManager(layoutManager);
        //seriese_list.setHasFixedSize(true);
        seriese_novel_list.setNestedScrollingEnabled(false);

        ArtistWebtoonAdapter artistWebtoonAdapter = new ArtistWebtoonAdapter(getActivity(), requestManager, "");

        seriese_novel_list.setAdapter(artistWebtoonAdapter);





    }


    @Override
    public void onResume() {
        super.onResume();

    }

}
