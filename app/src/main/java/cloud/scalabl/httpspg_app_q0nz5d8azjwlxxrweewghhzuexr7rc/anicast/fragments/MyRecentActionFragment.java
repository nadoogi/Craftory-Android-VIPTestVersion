package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyTimlineSocialMessageAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;



/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class MyRecentActionFragment extends Fragment {

    private RecyclerView new_content_list;
    private RequestManager requestManager;
    //public static MyRecentActionAdapter myRecentActionAdapter;
    public MyTimlineSocialMessageAdapter myTimlineSocialMessageAdapter;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private boolean loading = true;

    LinearLayout login_button_layout;
    LinearLayout login_button;

    ParseUser currentUser;

    private FunctionBase functionBase;
    private FloatingActionButton find_user_button;
    private LinearLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_socialmsg_content, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myTimlineSocialMessageAdapter = new MyTimlineSocialMessageAdapter(getActivity(), requestManager, functionBase);
                new_content_list.setAdapter(myTimlineSocialMessageAdapter);

                swipeLayout.setRefreshing(false);

            }

        });


        new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);
        login_button_layout = (LinearLayout) view.findViewById(R.id.login_button_layout);
        login_button = (LinearLayout) view.findViewById(R.id.login_button);
        currentUser = ParseUser.getCurrentUser();

        login_button_layout.setVisibility(View.GONE);

        requestManager = Glide.with(getActivity());
        functionBase = new FunctionBase(getActivity());

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });

        if(currentUser != null){

            login_button_layout.setVisibility(View.GONE);

        } else {

            login_button_layout.setVisibility(View.VISIBLE);

        }

        find_user_button = (FloatingActionButton) view.findViewById(R.id.find_user_button);
        find_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentUser != null){

                    Intent intent = new Intent(getActivity(), FindUserActivity.class);
                    intent.putExtra("location", "social");
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);

                    TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

            }
        });

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

        new_content_list.setLayoutManager(layoutManager);
        new_content_list.setHasFixedSize(true);
        //new_content_list.setNestedScrollingEnabled(false);
        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        //myRecentActionAdapter = new MyRecentActionAdapter(getActivity(), requestManager);

        myTimlineSocialMessageAdapter = new MyTimlineSocialMessageAdapter(getActivity(), requestManager, functionBase);

        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(myTimlineSocialMessageAdapter);

        new_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        if(myTimlineSocialMessageAdapter.getItemCount() > 20){

                            myTimlineSocialMessageAdapter.loadNextPage();

                        }

                    }


                }

            }
        });

        myTimlineSocialMessageAdapter.addOnQueryLoadListener(new MyTimlineSocialMessageAdapter.OnQueryLoadListener<ParseObject>() {
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

        if(myTimlineSocialMessageAdapter != null){

            myTimlineSocialMessageAdapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "myRecentActionFragment");

        functionBase = null;
        requestManager = null;
        myTimlineSocialMessageAdapter = null;
        new_content_list.setAdapter(null);

        super.onDestroyView();
    }

}
