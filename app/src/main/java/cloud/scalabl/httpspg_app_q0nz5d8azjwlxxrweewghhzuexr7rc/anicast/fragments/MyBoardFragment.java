package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ChargeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ContentManagerActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LibraryActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchActivity;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class MyBoardFragment extends Fragment{

    private ViewPager my_container;
    //private static FragmentPagerAdapter pageAdapter;
    public int currentPosition;

    private ParseUser currentUser;
    private FragmentPagerAdapter pageAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_my_board, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = ParseUser.getCurrentUser();

        ImageView app_logo = (ImageView) view.findViewById(R.id.app_logo);
        ImageView point_button = (ImageView) view.findViewById(R.id.point_button);
        ImageView search_button = (ImageView) view.findViewById(R.id.search_button);
        ImageView mymenu_button = (ImageView) view.findViewById(R.id.mymenu_button);
        ImageView library_button = (ImageView) view.findViewById(R.id.library_button);

        mymenu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });



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

        TabLayout my_tabs = (TabLayout) view.findViewById(R.id.my_tabs);
        my_container = (ViewPager) view.findViewById(R.id.my_container);
        my_container.setOffscreenPageLimit(5);


        //viewpager 관련 기능
        pageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{

                    "타임라인",
                    //"소통하기",
                    "추천장인",
                    "순위",
                    "이벤트"



            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        MyTimelineFragment myTimelineFragment = new MyTimelineFragment();

                        Bundle timelineBundle = new Bundle();
                        timelineBundle.putInt("page", position);
                        myTimelineFragment.setArguments(timelineBundle);


                        return myTimelineFragment;

                    /*
                    case 1:

                        MyRecentActionFragment myRecentActionFragment = new MyRecentActionFragment();

                        Bundle recordBundle = new Bundle();
                        recordBundle.putInt("page", position);
                        myRecentActionFragment.setArguments(recordBundle);

                        return myRecentActionFragment;
                    */

                    case 1:

                        MyRecommendCraftFragment myRecommendCraftFragment = new MyRecommendCraftFragment();

                        Bundle craftBundle = new Bundle();
                        craftBundle.putInt("page", position);
                        myRecommendCraftFragment.setArguments(craftBundle);

                        return myRecommendCraftFragment;



                    case 2:

                        MyRankingFragment myRankingFragment = new MyRankingFragment();

                        Bundle patronBundle = new Bundle();
                        patronBundle.putInt("page", position);
                        myRankingFragment.setArguments(patronBundle);

                        return myRankingFragment;


                    case 3:

                        MyEventFragment myEventFragment = new MyEventFragment();

                        Bundle eventBundle = new Bundle();
                        eventBundle.putInt("page", position);
                        myEventFragment.setArguments(eventBundle);

                        return myEventFragment;

                    default:

                        return null;

                }

            }



            @Override
            public int getCount() {
                return menuFragmentNames.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return menuFragmentNames[position];
            }

        };



        my_container.setAdapter(pageAdapter);
        my_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {



            }

            @Override
            public void onPageSelected(int position) {



            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        my_tabs.setupWithViewPager(my_container);


    }



    public int getCurrentPosition(){

        return currentPosition;

    }


    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onDetach() {

        Log.d("destroy2", "hello2");

        if(my_container != null){

            my_container.setAdapter(null);

        }

        if(pageAdapter != null){

            pageAdapter = null;

        }

        super.onDetach();
    }

    @Override
    public void onDestroyView() {

        Log.d("destroy", "hello");

        if(my_container != null){

            my_container.setAdapter(null);
            pageAdapter = null;

        }

        if(pageAdapter != null){

            pageAdapter = null;
            pageAdapter = null;

        }

        super.onDestroyView();






    }
}
