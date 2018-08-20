package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.AdminActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommercialRequestActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CreateArtworkActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CreateFundingActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CreateSerieseActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FundingActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyTimelineAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class MyTimelineFragment extends Fragment {

    private RecyclerView new_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private MyTimelineAdapter myTimelineAdapter;
    private RequestManager requestManager;

    private boolean loading = true;

    RelativeLayout background_layout;

    private final String TAG = "MyTimelineFragment";

    private FunctionBase functionBase;
    private FunctionPost functionPost;

    private ParseUser currentUser;


    private FloatingActionButton floatingActionButton_1;
    private FloatingActionButton floatingActionButton_3;
    private FloatingActionButton admin_button;
    private FloatingActionButton commercial_button;

    private FloatingActionsMenu multiple_actions_left;

    private Boolean commercialUser;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();

        return inflater.inflate(R.layout.fragment_content_my_timeline, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                Log.d(TAG, "swipe" );

                myTimelineAdapter = new MyTimelineAdapter(getActivity(), requestManager, functionBase, functionPost);
                new_content_list.setAdapter(myTimelineAdapter);

                swipeLayout.setRefreshing(false);

            }

        });



        multiple_actions_left = (FloatingActionsMenu) view.findViewById(R.id.multiple_actions_left);
        multiple_actions_left.setVisibility(View.VISIBLE);

        //floating 버튼 관련 기능
        floatingActionButton_1 = (FloatingActionButton) view.findViewById(R.id.write_button_default);
        floatingActionButton_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), CreateArtworkActivity.class);
                startActivity(intent);

            }
        });


        floatingActionButton_3 = (FloatingActionButton) view.findViewById(R.id.write_button_second);
        floatingActionButton_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), CreateSerieseActivity.class);
                startActivity(intent);

            }
        });


        admin_button = (FloatingActionButton) view.findViewById(R.id.admin_button);
        admin_button.setVisibility(View.GONE);

        commercial_button = (FloatingActionButton) view.findViewById(R.id.commercial_button);
        commercial_button.setVisibility(View.GONE);

        if(currentUser != null){

            commercialUser = false;

            if(currentUser.get("point") != null){

                currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject pointOb, ParseException e) {

                        Boolean commercialUser = pointOb.getBoolean("commercial_user_flag");

                        if(commercialUser){




                        } else {


                            final int currentFollowerLeft = currentUser.getInt("follower_count");
                            final int currentTotalScore = currentUser.getInt("total_score");

                            if(currentFollowerLeft >= 30 && currentTotalScore >= 500){

                                commercial_button.setVisibility(View.VISIBLE);
                                commercial_button.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        Intent intent = new Intent(getActivity(), CommercialRequestActivity.class);
                                        startActivity(intent);

                                    }
                                });

                            } else {


                                 commercial_button.setVisibility(View.VISIBLE);
                                 commercial_button.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {

                                         int targetFollower = 30 - currentFollowerLeft;

                                         if(targetFollower <0){

                                             targetFollower = 0;

                                         }

                                         int targetTotalScore = 500 - currentTotalScore;

                                         if(targetTotalScore <0){


                                             targetTotalScore = 0;
                                         }

                                         TastyToast.makeText(getActivity(), "유료화까지 팔로워 " + String.valueOf(targetFollower) + "명 남음," + "사이포인트" + String.valueOf(targetTotalScore) + " 남음", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                     }
                                 });


                            }

                        }

                        Boolean adminUser = pointOb.getBoolean("admin_user_flag");

                        if(adminUser){

                            admin_button.setVisibility(View.VISIBLE);
                            admin_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getActivity(), AdminActivity.class);
                                    startActivity(intent);


                                }
                            });

                        }


                    }
                });


            }

        }



        new_content_list = (RecyclerView) view.findViewById(R.id.new_content_list);
        background_layout = (RelativeLayout) view.findViewById(R.id.background_layout);

        requestManager = Glide.with(getActivity());
        functionBase  = new FunctionBase(getActivity());
        functionPost = new FunctionPost(getActivity());


        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        new_content_list.setLayoutManager(layoutManager);
        //new_content_list.setHasFixedSize(true);
        //new_content_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        myTimelineAdapter = new MyTimelineAdapter(getActivity(), requestManager, functionBase, functionPost);
        myTimelineAdapter.addOnQueryLoadListener(new MyTimelineAdapter.OnQueryLoadListener<ParseObject>() {
            @Override
            public void onLoading() {


            }

            @Override
            public void onLoaded(List<ParseObject> parseObjects, Exception e) {


            }
        });

        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(myTimelineAdapter);

        new_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                multiple_actions_left.collapse();

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();


                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        if(myTimelineAdapter.getItemCount() >20){

                            myTimelineAdapter.loadNextPage();

                        }

                    }



                }

            }



        });


        myTimelineAdapter.addOnQueryLoadListener(new MyTimelineAdapter.OnQueryLoadListener<ParseObject>() {
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

        if(myTimelineAdapter != null){

            Log.d(TAG, "resume" );

            myTimelineAdapter.notifyDataSetChanged();


        }

    }

    @Override
    public void onDestroyView() {

        Log.d("onDestoroyView", "myTimelineFragment");

        functionBase = null;
        functionPost = null;
        requestManager = null;
        myTimelineAdapter = null;
        new_content_list.setAdapter(null);

        super.onDestroyView();
    }
}
