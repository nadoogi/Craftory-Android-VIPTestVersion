package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseObject;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistWebtoonAdapter;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtistWebtoonSerieseFragment extends Fragment {

    private int pastVisibleItems, visibleItemCount, totalItemCount;

    private ArtistWebtoonAdapter artistWebtoonAdapter;
    private RequestManager requestManager;
    private RecyclerView seriese_novel_list;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artist_webtoon_seriese, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestManager = Glide.with(getActivity());

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                artistWebtoonAdapter = new ArtistWebtoonAdapter(getActivity(), requestManager, "");

                seriese_novel_list.setAdapter(artistWebtoonAdapter);

                swipeLayout.setRefreshing(false);

            }

        });



        seriese_novel_list = (RecyclerView) view.findViewById(R.id.webtoon_seriese_list);

        final GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(getActivity(),3);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

            @Override
            public int getSpanSize(int position) {

                if(position == 0){

                    return 3;

                }

                return 1;
            }
        });

        seriese_novel_list.setLayoutManager(layoutManager);
        //seriese_list.setHasFixedSize(true);
        seriese_novel_list.setNestedScrollingEnabled(false);

        artistWebtoonAdapter = new ArtistWebtoonAdapter(getActivity(), requestManager, "");

        seriese_novel_list.setAdapter(artistWebtoonAdapter);

        seriese_novel_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        artistWebtoonAdapter.loadNextPage();
                    }

                }

            }
        });

        artistWebtoonAdapter.addOnQueryLoadListener(new ArtistWebtoonAdapter.OnQueryLoadListener<ParseObject>() {
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

        if(artistWebtoonAdapter != null){

            artistWebtoonAdapter.loadObjects(0);
        }

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "myTimelineFragment");

        //functionBase = null;

        super.onDestroyView();
    }

}
