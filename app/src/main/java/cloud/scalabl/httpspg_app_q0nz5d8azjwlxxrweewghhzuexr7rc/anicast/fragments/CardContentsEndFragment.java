package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseUser;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;


public class CardContentsEndFragment extends Fragment {

    private ParseObject channelOb;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    public CardContentsEndFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        String cardId = getArguments().getString("cardId");
        Log.d("cardId", cardId);
        ParseUser currentUser = ParseUser.getCurrentUser();

        RequestManager requestManager = Glide.with(this);


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_card_contents_end, container, false);

        ParseImageView content_thumb = (ParseImageView) view.findViewById(R.id.content_thumbnail);
        TextView content_name = (TextView) view.findViewById(R.id.content_name);
        TextView channel_name = (TextView) view.findViewById(R.id.channel_name);
        LinearLayout channel_subscribe = (LinearLayout) view.findViewById(R.id.channel_subscribe);
        TextView subscribe_text = (TextView) view.findViewById(R.id.subscribe_text);

        RecyclerView contents_list = (RecyclerView) view.findViewById(R.id.contents_list);






        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onResume() {
        super.onResume();



    }



}
