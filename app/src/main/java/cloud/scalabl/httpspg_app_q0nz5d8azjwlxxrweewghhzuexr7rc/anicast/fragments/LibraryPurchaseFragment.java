package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.LibraryPurchaseAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;



/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class LibraryPurchaseFragment extends Fragment {

    RecyclerView library_list;
    RequestManager requestManager;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    LibraryPurchaseAdapter libraryPurchaseAdapter;

    String type;

    private FunctionBase functionBase;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_library_purchase, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        type = "all";

        library_list = (RecyclerView) view.findViewById(R.id.library_list);

        functionBase = new FunctionBase(getActivity());


        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                resetAdapter(type);
                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getActivity());

        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);

        library_list.setLayoutManager(layoutManager);
        library_list.setHasFixedSize(true);
        library_list.setNestedScrollingEnabled(false);

        libraryPurchaseAdapter = new LibraryPurchaseAdapter(getActivity(), requestManager, type);
        library_list.setItemAnimator(new SlideInUpAnimator());
        library_list.setAdapter(libraryPurchaseAdapter);

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
                        libraryPurchaseAdapter.loadNextPage();
                    }

                }

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

    }



    private void resetAdapter(String type){

        libraryPurchaseAdapter = new LibraryPurchaseAdapter(getActivity(), requestManager, type);
        library_list.setAdapter(libraryPurchaseAdapter);

    }


}
