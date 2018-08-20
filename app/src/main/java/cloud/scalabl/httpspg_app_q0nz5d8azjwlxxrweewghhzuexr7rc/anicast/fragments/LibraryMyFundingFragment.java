package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.LibraryLikeAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PatronHistoryAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class LibraryMyFundingFragment extends Fragment {

    RecyclerView library_list;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private PatronHistoryAdapter patronHistoryAdapter;

    private FunctionBase functionBase;
    private RequestManager requestManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_library_my_funding, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        library_list = (RecyclerView) view.findViewById(R.id.library_list);

        functionBase = new FunctionBase(getActivity());
        requestManager = Glide.with(getActivity());

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                patronHistoryAdapter.loadObjects(0);
                swipeLayout.setRefreshing(false);

            }

        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

        library_list.setLayoutManager(layoutManager);
        library_list.setHasFixedSize(true);
        library_list.setNestedScrollingEnabled(false);

        patronHistoryAdapter = new PatronHistoryAdapter(getActivity(),requestManager );

        library_list.setItemAnimator(new SlideInUpAnimator());
        library_list.setAdapter(patronHistoryAdapter);

        library_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        patronHistoryAdapter.loadNextPage();
                    }

                }

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        if(patronHistoryAdapter!= null){

            patronHistoryAdapter.loadObjects(0);
        }

    }



}
