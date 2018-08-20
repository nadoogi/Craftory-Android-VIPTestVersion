package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.parse.ParseUser;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistIllustAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistIllustOnlyImageAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtistIllustFragment extends Fragment {

    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private RecyclerView illust_list;
    private RequestManager requestManager;

    //ArtistIllustAdapter artistIllustAdapter;
    private ArtistIllustOnlyImageAdapter artistIllustOnlyImageAdapter;

    private ParseUser currentUser;

    private int currentItemCount;

    private FunctionBase functionBase;
    private FunctionPost functionPost;

    private Boolean loadingFlag;
    private Boolean illustLoadingFlag;
    private Boolean bestLoadingFlag;

    private Boolean recommand;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_illust_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        illust_list = (RecyclerView) view.findViewById(R.id.illust_list);
        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);

        recommand = true;

        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                artistIllustOnlyImageAdapter = new ArtistIllustOnlyImageAdapter(getActivity(), requestManager, recommand, functionBase, functionPost);
                illust_list.setAdapter(artistIllustOnlyImageAdapter);

                //artistIllustAdapter = new ArtistIllustAdapter(getActivity(), requestManager, false, functionBase, functionPost);
                //illust_list.setAdapter(artistIllustAdapter);

                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getActivity());
        loadingFlag = false;
        illustLoadingFlag = false;
        bestLoadingFlag =false;

        functionBase = new FunctionBase(getActivity());
        functionPost = new FunctionPost(getActivity());

        currentUser = ParseUser.getCurrentUser();

        final GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(getActivity(),2);

        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

            @Override
            public int getSpanSize(int position) {
                if(position == 0){
                    return 2;
                }

                return 1;
            }
        });


        illust_list.setLayoutManager(layoutManager);
        illust_list.setNestedScrollingEnabled(false);


        artistIllustOnlyImageAdapter = new ArtistIllustOnlyImageAdapter(getActivity(), requestManager, recommand, functionBase, functionPost);

        illust_list.setAdapter(artistIllustOnlyImageAdapter);
        illust_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();


                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        if(artistIllustOnlyImageAdapter.getItemCount() > 20){

                            artistIllustOnlyImageAdapter.loadNextPage();
                        }

                        /*
                        if(artistIllustAdapter.getItemCount() > 20){

                            artistIllustAdapter.loadNextPage();

                        }
                        */


                    }



                }
            }
        });









    }


    @Override
    public void onResume() {
        super.onResume();

        if(artistIllustOnlyImageAdapter != null){

            artistIllustOnlyImageAdapter.notifyDataSetChanged();
        }

        //if(artistIllustAdapter != null){

            //artistIllustAdapter.notifyDataSetChanged();

        //}

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "artistIllustFragment");


        functionBase = null;
        functionPost = null;
        requestManager = null;
        //artistIllustAdapter = null;
        artistIllustOnlyImageAdapter = null;
        illust_list.setAdapter(null);

        super.onDestroyView();
    }


}
