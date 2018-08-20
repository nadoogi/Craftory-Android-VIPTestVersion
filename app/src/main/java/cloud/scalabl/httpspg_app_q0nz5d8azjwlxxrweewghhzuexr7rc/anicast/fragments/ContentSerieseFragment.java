package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ArtistWebtoonAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ContentRecommendAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ContentSerieseListAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ContentSerieseFragment extends Fragment {

    private String cardId;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private RecyclerView content_list;
    private RequestManager requestManager;
    //private RelativeLayout background_layout;
    private ContentSerieseListAdapter contentSerieseListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        cardId = getArguments().getString("postId");

        return inflater.inflate(R.layout.fragment_content_end, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ParseUser currentUser = ParseUser.getCurrentUser();

        content_list = (RecyclerView) view.findViewById(R.id.content_list);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                contentSerieseListAdapter.loadObjects(0);
                contentSerieseListAdapter.notifyDataSetChanged();

                swipeLayout.setRefreshing(false);

            }

        });


        content_list = (RecyclerView) view.findViewById(R.id.content_list);

        requestManager = Glide.with(getActivity());

        ParseQuery postQuery = ParseQuery.getQuery("ArtistPost");
        postQuery.include("user");
        postQuery.include("seriese_parent");
        postQuery.getInBackground(cardId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e==null){

                    ParseObject serieseOb = object.getParseObject("seriese_parent");

                    final GridLayoutManager layoutManager;

                    layoutManager = new GridLayoutManager(getActivity(),3);

                    content_list.setLayoutManager(layoutManager);
                    content_list.setHasFixedSize(true);

                    contentSerieseListAdapter = new ContentSerieseListAdapter(getActivity(), requestManager, serieseOb, object);
                    content_list.setAdapter(contentSerieseListAdapter);

                    content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            if(dy > 0){

                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                                    contentSerieseListAdapter.loadNextPage();

                                }



                            }

                        }
                    });

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
