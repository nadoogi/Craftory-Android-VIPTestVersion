package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommercialRecommendAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.models.Image;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class CommercialRecommendFragment extends Fragment {

    private static RecyclerView recommend_artwork_list;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private static CommercialRecommendAdapter commercialRecommendAdapter;

    private static String imagePath;

    private static ArrayList<Image> images;
    private static String finalImage;

    private static File tempFile;

    private static ParseObject serieseOb;

    private static String serieseId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        serieseId = getArguments().getString("serieseId");

        return inflater.inflate(R.layout.fragment_commercial_recommend, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();

        recommend_artwork_list = (RecyclerView) view.findViewById(R.id.recommend_artwork_list);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                commercialRecommendAdapter.loadObjects(0);

                swipeLayout.setRefreshing(false);

            }

        });


        RequestManager requestManager = Glide.with(getActivity());

        ParseQuery<ArtistPost> artistQuery = ParseQuery.getQuery(ArtistPost.class);
        artistQuery.include("user");
        artistQuery.getInBackground(serieseId, new GetCallback<ArtistPost>() {
            @Override
            public void done(final ArtistPost object, ParseException e) {

                if(e==null){

                    serieseOb = object;

                    final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

                    recommend_artwork_list.setLayoutManager(layoutManager);
                    recommend_artwork_list.setHasFixedSize(true);

                    commercialRecommendAdapter = new CommercialRecommendAdapter(getActivity(), Glide.with(getActivity()), object);
                    recommend_artwork_list.setAdapter(commercialRecommendAdapter);


                } else {

                    e.printStackTrace();
                }

            }
        });





    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
