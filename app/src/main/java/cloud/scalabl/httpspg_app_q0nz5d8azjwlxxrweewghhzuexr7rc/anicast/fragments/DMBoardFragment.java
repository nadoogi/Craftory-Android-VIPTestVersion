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
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.parse.ParseLiveQueryClient;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SubscriptionHandling;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Date;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ChargeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ContentManagerActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FindUserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LibraryActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MainBoardActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.DMListdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.NotiAlertAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class DMBoardFragment extends Fragment {


    int pastVisibleItems, visibleItemCount, totalItemCount;
    private ParseUser currentUser;
    public static DMListdapter dmListdapter;
    private FunctionBase functionBase;

    private FloatingActionButton find_user_button;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();

        return inflater.inflate(R.layout.fragment_dm_board, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Date lastCheckTime;
        ImageView search_button;
        ImageView point_button;
        ImageView library_button;
        ImageView mymenu_button;
        ImageView app_logo;

        app_logo = (ImageView) view.findViewById(R.id.app_logo);
        point_button = (ImageView) view.findViewById(R.id.point_button);
        search_button = (ImageView) view.findViewById(R.id.search_button);
        mymenu_button = (ImageView) view.findViewById(R.id.mymenu_button);

        functionBase = new FunctionBase(getActivity());

        mymenu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("mymenu", "artwork clicked");

                if(currentUser != null){

                    Intent intent = new Intent(getActivity(), ContentManagerActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);

                } else {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

            }
        });


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        point_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(currentUser != null){

                    Intent intent = new Intent(getActivity(), ChargeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }



            }
        });

        library_button = (ImageView) view.findViewById(R.id.library_button);
        library_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(currentUser != null){

                    Intent intent = new Intent(getActivity(), LibraryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                    TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }




            }
        });

        if(currentUser != null){


            RecyclerView dm_list = (RecyclerView) view.findViewById(R.id.dm_list);

            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

            dm_list.setLayoutManager(layoutManager);
            dm_list.setHasFixedSize(true);
            dm_list.setNestedScrollingEnabled(false);

            dmListdapter = new DMListdapter(getActivity(), Glide.with(getActivity()), functionBase);
            dm_list.setAdapter(dmListdapter);

            dm_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                            dmListdapter.loadNextPage();
                        }

                    }

                }
            });



            find_user_button = (FloatingActionButton) view.findViewById(R.id.find_user_button);
            find_user_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentUser != null){

                        Intent intent = new Intent(getActivity(), FindUserActivity.class);
                        intent.putExtra("location", "dm");
                        startActivity(intent);

                    } else {

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);

                        TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                }
            });






        }



    }


    @Override
    public void onResume() {
        super.onResume();

        if(dmListdapter != null){

            dmListdapter.loadObjects(0);

        }

    }


}
