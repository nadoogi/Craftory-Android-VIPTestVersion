package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FindUserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyCraftAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class MyRecommendCraftFragment extends Fragment {

    private RecyclerView new_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    public MyCraftAdapter myCraftAdapter;
    private RequestManager requestManager;
    private FunctionBase functionBase;

    private FloatingActionButton find_user_button;

    private ParseUser currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();

        return inflater.inflate(R.layout.fragment_craft_layout, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myCraftAdapter = new MyCraftAdapter(getActivity(), requestManager, functionBase);
                new_content_list.setAdapter(myCraftAdapter);

                swipeLayout.setRefreshing(false);

            }

        });


        new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);


        FragmentManager manager = getActivity().getSupportFragmentManager();

        requestManager = Glide.with(getActivity());
        functionBase = new FunctionBase(getActivity());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        new_content_list.setLayoutManager(layoutManager);
        new_content_list.setHasFixedSize(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        myCraftAdapter = new MyCraftAdapter(getActivity(), requestManager, functionBase);


        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(myCraftAdapter);

        new_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("dx", String.valueOf(dx));
                Log.d("dy", String.valueOf(dy));

                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        if(myCraftAdapter.getItemCount() > 20){

                            myCraftAdapter.loadNextPage();

                        }

                    }



                }

            }
        });





        myCraftAdapter.addOnQueryLoadListener(new MyCraftAdapter.OnQueryLoadListener<ParseObject>() {
            @Override
            public void onLoading() {


            }

            @Override
            public void onLoaded(List<ParseObject> parseObjects, Exception e) {


            }
        });


        find_user_button = (FloatingActionButton) view.findViewById(R.id.find_user_button);
        find_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentUser != null){

                    Intent intent = new Intent(getActivity(), FindUserActivity.class);
                    intent.putExtra("location", "user");
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        if(myCraftAdapter != null){

            myCraftAdapter.loadObjects(0);

        }

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "MyRecommendCraftFragment");
        functionBase = null;
        requestManager = null;
        new_content_list.setAdapter(null);
        myCraftAdapter = null;

        super.onDestroyView();
    }

}
