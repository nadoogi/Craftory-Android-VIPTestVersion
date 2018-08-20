package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistYoutubeAdapter;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtistYoutubeFragment extends Fragment {

    ArtistYoutubeAdapter artistYoutubeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artist_youtube, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestManager requestManager = Glide.with(getActivity());
        RecyclerView youtube_list = (RecyclerView) view.findViewById(R.id.youtube_list);

        final GridLayoutManager layoutManager;

        layoutManager = new GridLayoutManager(getActivity(),1);

        youtube_list.setLayoutManager(layoutManager);
        youtube_list.setHasFixedSize(true);
        youtube_list.setNestedScrollingEnabled(false);

        artistYoutubeAdapter = new ArtistYoutubeAdapter(getActivity(), requestManager);

        youtube_list.setAdapter(artistYoutubeAdapter);


    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
