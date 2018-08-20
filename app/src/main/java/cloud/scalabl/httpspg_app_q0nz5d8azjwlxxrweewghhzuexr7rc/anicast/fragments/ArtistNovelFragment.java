package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtistNovelFragment extends Fragment implements View.OnClickListener{

    private FrameLayout webtoon_fragment_layout;

    private LinearLayout seriese_button;
    private TextView seriese_button_text;
    private LinearLayout post_button;
    private TextView post_button_text;

    private FragmentManager fragmentManagerNovel;

    private ArtistNovelSerieseFragment artistNovelSerieseFragment;
    private ArtistNovelPostFragment artistNovelPostFragment;
    private FunctionBase functionBase;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artist_novel, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestManager requestManager = Glide.with(getActivity());
        functionBase = new FunctionBase(getActivity());

        seriese_button = (LinearLayout) view.findViewById(R.id.seriese_button);
        seriese_button_text = (TextView) view.findViewById(R.id.seriese_button_text);
        post_button = (LinearLayout) view.findViewById(R.id.post_button);
        post_button_text = (TextView) view.findViewById(R.id.post_button_text);

        seriese_button.setBackgroundResource(R.drawable.button_radius_0dp_point_button);
        seriese_button_text.setTextColor(functionBase.likedColor);

        seriese_button.setOnClickListener(this);
        post_button.setOnClickListener(this);

        artistNovelSerieseFragment = new ArtistNovelSerieseFragment();
        fragmentManagerNovel = getActivity().getSupportFragmentManager();
        fragmentManagerNovel.beginTransaction().add(R.id.novel_fragment_layout, artistNovelSerieseFragment).commitAllowingStateLoss();

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.seriese_button:

                seriese_button.setBackgroundResource(R.drawable.button_radius_0dp_point_button);
                seriese_button_text.setTextColor(functionBase.likedColor);
                post_button.setBackgroundColor(Color.parseColor("#ffffff"));
                post_button_text.setTextColor(functionBase.notSelectedColor);



                if(artistNovelSerieseFragment == null){

                    artistNovelSerieseFragment = new ArtistNovelSerieseFragment();

                }

                fragmentManagerNovel.beginTransaction().replace(R.id.novel_fragment_layout, artistNovelSerieseFragment ).commitAllowingStateLoss();

                break;

            case R.id.post_button:

                post_button.setBackgroundResource(R.drawable.button_radius_0dp_point_button);
                post_button_text.setTextColor(functionBase.likedColor);
                seriese_button.setBackgroundColor(Color.parseColor("#ffffff"));
                seriese_button_text.setTextColor(functionBase.notSelectedColor);

                if(artistNovelPostFragment == null){

                    artistNovelPostFragment = new ArtistNovelPostFragment();

                }

                fragmentManagerNovel.beginTransaction().replace(R.id.novel_fragment_layout, artistNovelPostFragment ).commitAllowingStateLoss();

                break;

        }

    }
}
