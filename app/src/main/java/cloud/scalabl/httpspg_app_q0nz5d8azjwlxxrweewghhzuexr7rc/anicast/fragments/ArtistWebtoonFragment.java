package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtistWebtoonFragment extends Fragment implements View.OnClickListener{

    private ViewPager webtoon_fragment_layout;

    private LinearLayout seriese_button;
    private TextView seriese_button_text;
    private LinearLayout post_button;
    private TextView post_button_text;

    private FragmentManager fragmentManager;

    private ArtistWebtoonSerieseFragment artistWebtoonSerieseFragment;
    private ArtistWebtoonPostFragment artistWebtoonPostFragment;

    private FunctionBase functionBase;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artist_webtoon, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestManager requestManager = Glide.with(getActivity());
        functionBase = new FunctionBase(getActivity());
        webtoon_fragment_layout = (ViewPager) view.findViewById(R.id.webtoon_fragment_layout);

        seriese_button = (LinearLayout) view.findViewById(R.id.seriese_button);
        seriese_button_text = (TextView) view.findViewById(R.id.seriese_button_text);
        post_button = (LinearLayout) view.findViewById(R.id.post_button);
        post_button_text = (TextView) view.findViewById(R.id.post_button_text);

        seriese_button.setOnClickListener(this);
        post_button.setOnClickListener(this);

        post_button.setBackgroundResource(R.drawable.button_radius_0dp_point_button);
        post_button_text.setTextColor(functionBase.likedColor);


        if(artistWebtoonPostFragment == null){

            artistWebtoonPostFragment = new ArtistWebtoonPostFragment();
            fragmentManager = getActivity().getSupportFragmentManager();;
            fragmentManager.beginTransaction().add(R.id.webtoon_fragment_layout, artistWebtoonPostFragment).commitAllowingStateLoss();

        }

        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{
                    "회차별",
                    "작품별",
            };

            @Override
            public Fragment getItem(int position) {

                switch (position) {


                    case 0:

                        return new ArtistWebtoonPostFragment();

                    case 1:

                        return new ArtistWebtoonSerieseFragment();

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


        webtoon_fragment_layout.setAdapter(pageAdapter);
        webtoon_fragment_layout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:

                        post_button.setBackgroundResource(R.drawable.button_radius_0dp_point_button);
                        post_button_text.setTextColor(functionBase.likedColor);
                        seriese_button.setBackgroundColor(Color.parseColor("#ffffff"));
                        seriese_button_text.setTextColor(functionBase.notSelectedColor);


                        break;

                    case 1:

                        post_button.setBackgroundColor(Color.parseColor("#ffffff"));
                        post_button_text.setTextColor(functionBase.notSelectedColor);
                        seriese_button.setBackgroundResource(R.drawable.button_radius_0dp_point_button);
                        seriese_button_text.setTextColor(functionBase.likedColor);


                        break;

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        if(artistWebtoonPostFragment != null){

            fragmentManager.beginTransaction().replace(R.id.webtoon_fragment_layout, artistWebtoonPostFragment).commitAllowingStateLoss();

        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.seriese_button:

                post_button.setBackgroundColor(Color.parseColor("#ffffff"));
                post_button_text.setTextColor(functionBase.notSelectedColor);
                seriese_button.setBackgroundResource(R.drawable.button_radius_0dp_point_button);
                seriese_button_text.setTextColor(functionBase.likedColor);

                webtoon_fragment_layout.setCurrentItem(1);

                break;

            case R.id.post_button:

                post_button.setBackgroundResource(R.drawable.button_radius_0dp_point_button);
                post_button_text.setTextColor(functionBase.likedColor);
                seriese_button.setBackgroundColor(Color.parseColor("#ffffff"));
                seriese_button_text.setTextColor(functionBase.notSelectedColor);

                webtoon_fragment_layout.setCurrentItem(0);

                break;

        }

    }
}
