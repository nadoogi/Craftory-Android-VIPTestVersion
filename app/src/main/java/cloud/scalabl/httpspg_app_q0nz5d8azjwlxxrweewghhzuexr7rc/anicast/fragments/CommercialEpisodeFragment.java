package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommercialListAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ToTheTopListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.models.Image;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class CommercialEpisodeFragment extends Fragment implements ToTheTopListener{

    private RecyclerView artwork_list;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private CommercialListAdapter commercialListAdapter;

    private String imagePath;

    private ArrayList<Image> images;
    private String finalImage;
    private RequestManager requestManager;

    private File tempFile;

    private ParseObject serieseOb;

    private String serieseId;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        serieseId = getArguments().getString("serieseId");

        return inflater.inflate(R.layout.fragment_commercial_episode, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();

        artwork_list = (RecyclerView) view.findViewById(R.id.artwork_list);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                commercialListAdapter = new CommercialListAdapter(getActivity(), requestManager, serieseOb);
                artwork_list.setAdapter(commercialListAdapter);
                swipeLayout.setRefreshing(false);

            }

        });


        requestManager = Glide.with(getActivity());

        ParseQuery<ArtistPost> artistQuery = ParseQuery.getQuery(ArtistPost.class);
        artistQuery.include("user");
        artistQuery.include("first_item");
        artistQuery.getInBackground(serieseId, new GetCallback<ArtistPost>() {
            @Override
            public void done(final ArtistPost object, ParseException e) {

                if(e==null){

                    serieseOb = object;

                    final GridLayoutManager layoutManager;

                    layoutManager = new GridLayoutManager(getActivity(),3);

                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

                        @Override
                        public int getSpanSize(int position) {

                            if(commercialListAdapter != null){

                                if(position == 0){

                                    return 3;

                                } else if(position == commercialListAdapter.getItemCount()-1){

                                    return 3;

                                }

                                return 1;

                            } else {

                                return 3;

                            }

                        }
                    });

                    artwork_list.setLayoutManager(layoutManager);
                    artwork_list.setHasFixedSize(true);

                    commercialListAdapter = new CommercialListAdapter(getActivity(), Glide.with(getActivity()), object);
                    commercialListAdapter.setToTheTopListener(CommercialEpisodeFragment.this);

                    artwork_list.setAdapter(commercialListAdapter);


                } else {

                    Log.d("error", "error");
                    e.printStackTrace();
                }

            }
        });





    }


    @Override
    public void onResume() {
        super.onResume();

        if(commercialListAdapter != null){

            commercialListAdapter.loadObjects(0);
        }


    }


    @Override
    public void focusToTheTop() {

        artwork_list.getLayoutManager().scrollToPosition(0);

    }
}
