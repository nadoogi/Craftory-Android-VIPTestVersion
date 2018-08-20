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

public class ArtistBoardFragment extends Fragment {

    private ViewPager artist_container;
    private FragmentPagerAdapter pageAdapter;
    private int currentPosition = 0;

    private ParseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();

        return inflater.inflate(R.layout.fragment_artist_board, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TabLayout artist_tabs = (TabLayout) view.findViewById(R.id.artwork_tabs);
        artist_container = (ViewPager) view.findViewById(R.id.artist_container);

        ImageView app_logo = (ImageView) view.findViewById(R.id.app_logo);
        ImageView point_button = (ImageView) view.findViewById(R.id.point_button);
        ImageView mymenu_button = (ImageView) view.findViewById(R.id.mymenu_button);

        mymenu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("mymenu", "artistboard clicked");

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

        ImageView search_button = (ImageView) view.findViewById(R.id.search_button);
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

        ImageView library_button = (ImageView) view.findViewById(R.id.library_button);
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


        pageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {



            private final String[] menuFragmentNames = new String[]{

                    "일러스트",
                    "웹툰",
                    "연성",
                    "인기태그"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        ArtistIllustFragment artistIllustFragment = new ArtistIllustFragment();

                        Bundle illustBundle = new Bundle();
                        illustBundle.putInt("page", position);
                        artistIllustFragment.setArguments(illustBundle);

                        return artistIllustFragment;

                    case 1:

                        ArtistWebtoonSerieseFragment artistWebtoonSerieseFragment = new ArtistWebtoonSerieseFragment();

                        Bundle webtoonBundle = new Bundle();
                        webtoonBundle.putInt("page", position);
                        artistWebtoonSerieseFragment.setArguments(webtoonBundle);

                        return artistWebtoonSerieseFragment;

                    case 2:

                        AlchemyFragment alchemyFragment = new AlchemyFragment();

                        Bundle alchemyBundle = new Bundle();
                        alchemyBundle.putInt("page", position);
                        alchemyFragment.setArguments(alchemyBundle);

                        return alchemyFragment;


                    case 3:

                        RealtimeTrendFragment realtimeTrendFragment = new RealtimeTrendFragment();

                        Bundle realTimeBundle = new Bundle();
                        realTimeBundle.putInt("page", position);
                        realtimeTrendFragment.setArguments(realTimeBundle);

                        return realtimeTrendFragment;


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


        artist_container.setAdapter(pageAdapter);
        artist_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                currentPosition = position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        artist_tabs.setupWithViewPager(artist_container);


    }


    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onDetach() {
        if(artist_container != null){

            artist_container.setAdapter(null);
            pageAdapter = null;

        }


        super.onDetach();
    }

    @Override
    public void onDestroyView() {

        if(artist_container != null){

            artist_container.setAdapter(null);
            pageAdapter = null;
        }



        super.onDestroyView();
    }
}
