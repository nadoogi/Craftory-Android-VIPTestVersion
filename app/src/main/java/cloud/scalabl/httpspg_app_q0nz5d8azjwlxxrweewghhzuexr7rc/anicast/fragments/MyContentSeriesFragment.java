package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentSerieseAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class MyContentSeriesFragment extends Fragment {

    int pastVisibleItems, visibleItemCount, totalItemCount;

    public static MyContentSerieseAdapter myContentSerieseAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_my_content_seriese, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);
        LinearLayout seriese_add_button = (LinearLayout) view.findViewById(R.id.seriese_add_button);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myContentSerieseAdapter.loadObjects(0);

                swipeLayout.setRefreshing(false);

            }

        });

        RequestManager requestManager = Glide.with(getActivity());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        new_content_list.setLayoutManager(layoutManager);
        new_content_list.setHasFixedSize(true);
        new_content_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        myContentSerieseAdapter = new MyContentSerieseAdapter((AppCompatActivity) getActivity(),getActivity(), requestManager);



        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(myContentSerieseAdapter);

        new_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        myContentSerieseAdapter.loadNextPage();
                    }

                }

            }
        });


        seriese_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SerieseActivity.class);
                startActivity(intent);


            }
        });


    }



    @Override
    public void onResume() {
        super.onResume();

        if(myContentSerieseAdapter != null){

            myContentSerieseAdapter.loadObjects(0);

        }


    }

}
