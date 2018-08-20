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
import com.parse.ParseObject;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PatronFinishAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class PatronFinishFragment extends Fragment {

    private RecyclerView new_content_list;
    private RequestManager requestManager;
    public PatronFinishAdapter patronFinishAdapter;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    private boolean loading = true;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_content_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                patronFinishAdapter =  new PatronFinishAdapter(getActivity(), requestManager, "production");
                new_content_list.setAdapter(patronFinishAdapter);

                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getActivity());


        new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        new_content_list.setLayoutManager(layoutManager);
        new_content_list.setHasFixedSize(true);
        new_content_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        patronFinishAdapter = new PatronFinishAdapter(getActivity(), requestManager, "production");

        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(patronFinishAdapter);

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

                            patronFinishAdapter.loadNextPage();

                            loading = true;

                        }

                    }




                }

            }
        });

        patronFinishAdapter.addOnQueryLoadListener(new PatronFinishAdapter.OnQueryLoadListener<ParseObject>() {
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

        if(patronFinishAdapter != null){

            patronFinishAdapter.loadObjects(0);

        }

    }

}
