package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommercialPurchaseListAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import in.myinnos.awesomeimagepicker.models.Image;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class CommercialPurchaseFragment extends Fragment {

    private RecyclerView purchase_list;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private String imagePath;

    private ArrayList<Image> images;
    private String finalImage;
    private RequestManager requestManager;

    private File tempFile;

    private ParseObject serieseOb;

    private String serieseId;

    private CommercialPurchaseListAdapter commercialPurchaseListAdapter;

    TextView progress_status;
    TextView title;
    TextView progress_num;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        serieseId = getArguments().getString("serieseId");

        return inflater.inflate(R.layout.fragment_commercial_purchase, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();

        purchase_list = (RecyclerView) view.findViewById(R.id.purchase_list);

        progress_status = (TextView) view.findViewById(R.id.progress_status);
        title = (TextView) view.findViewById(R.id.title);
        progress_num = (TextView) view.findViewById(R.id.progress_num);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                commercialPurchaseListAdapter.loadObjects(0);

                swipeLayout.setRefreshing(false);

            }

        });


        requestManager = Glide.with(getActivity());

        ParseQuery<ArtistPost> artistQuery = ParseQuery.getQuery(ArtistPost.class);
        artistQuery.include("user");
        artistQuery.getInBackground(serieseId, new GetCallback<ArtistPost>() {
            @Override
            public void done(final ArtistPost object, ParseException e) {

                if(e==null){

                    serieseOb = object;

                    final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);

                    purchase_list.setLayoutManager(layoutManager);
                    purchase_list.setHasFixedSize(true);

                    commercialPurchaseListAdapter = new CommercialPurchaseListAdapter(getActivity(), requestManager, serieseOb);

                    purchase_list.setAdapter(commercialPurchaseListAdapter);


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
