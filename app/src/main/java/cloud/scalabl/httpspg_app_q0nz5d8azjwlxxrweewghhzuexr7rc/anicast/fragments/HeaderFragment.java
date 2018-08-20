package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.GifNativeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PhotoContentsActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostingDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.WebtoonContentActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.YoutubeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;


/**
 * Created by ssamkyu on 2017. 4. 20..
 */

public class HeaderFragment extends Fragment {

    private FunctionBase functionBase;

    public HeaderFragment() {


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        functionBase = new FunctionBase(getActivity());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_header, container, false);

        String title = getArguments().getString("title");
        String sub_title = getArguments().getString("subtitle");
        String image = getArguments().getString("image");
        final String type = getArguments().getString("type");
        final String targetId = getArguments().getString("targetId");

        RelativeLayout header_layout = (RelativeLayout) view.findViewById(R.id.header_layout);

        ImageView header_image = (ImageView) view.findViewById(R.id.header_image);
        TextView header_title = (TextView) view.findViewById(R.id.header_title);
        TextView header_subtitle = (TextView) view.findViewById(R.id.header_subtitle);

        header_title.setText(title);
        header_subtitle.setText(sub_title);

        String imageUrlPhoto = functionBase.imageUrlBase600 + image;

        Glide.with(getActivity()).load(imageUrlPhoto).into(header_image);

        header_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("header_type", type);

                switch (type){

                    case "event":

                        Intent intent = new Intent(getActivity(), PostingDetailActivity.class);
                        intent.putExtra("postId", targetId);
                        startActivity(intent);

                        break;


                    case "youtube":

                        Intent intentYoutube = new Intent(getActivity(), YoutubeActivity.class);
                        intentYoutube.putExtra("cardId", targetId);
                        startActivity(intentYoutube);
                        break;

                    case "photo":

                        Intent intentPhoto = new Intent(getActivity(), PhotoContentsActivity.class);
                        intentPhoto.putExtra("cardId", targetId);
                        startActivity(intentPhoto);
                        break;


                    case "gif":

                        Intent intentGif = new Intent(getActivity(), GifNativeActivity.class);
                        intentGif.putExtra("cardId", targetId);
                        startActivity(intentGif);
                        break;

                    case "webtoon":

                        Intent intentWebtoon = new Intent(getActivity(), WebtoonContentActivity.class);
                        intentWebtoon.putExtra("cardId", targetId);
                        startActivity(intentWebtoon);
                        break;

                }

            }
        });

        return view;

    }


}
