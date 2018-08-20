package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
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
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ContentRecommendAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ContentRecommendFragment extends Fragment {

    private String cardId;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private RecyclerView content_list;
    private RequestManager requestManager;
    //private RelativeLayout background_layout;
    private ContentRecommendAdapter contentRecommendAdapter;

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

                contentRecommendAdapter.loadObjects(0);
                contentRecommendAdapter.notifyDataSetChanged();

                swipeLayout.setRefreshing(false);

            }

        });


        content_list = (RecyclerView) view.findViewById(R.id.content_list);
        //background_layout = (RelativeLayout) view.findViewById(R.id.background_layout);

        requestManager = Glide.with(getActivity());

        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        content_list.setLayoutManager(layoutManager);
        content_list.setHasFixedSize(false);
        content_list.setNestedScrollingEnabled(true);

        ParseQuery postQuery = ParseQuery.getQuery("ArtistPost");
        postQuery.include("user");
        postQuery.getInBackground(cardId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e==null){

                    contentRecommendAdapter = new ContentRecommendAdapter(getActivity(),  requestManager, object);

                    contentRecommendAdapter.addOnQueryLoadListener(new ContentRecommendAdapter.OnQueryLoadListener<ParseObject>() {
                        @Override
                        public void onLoading() {

                            //background_layout.setBackgroundResource(R.drawable.timeline_loading_background);

                        }

                        @Override
                        public void onLoaded(List<ParseObject> parseObjects, Exception e) {

                            //background_layout.setBackgroundColor(FunctionBase.timelineBackgroundColor);

                        }
                    });

                    content_list.setItemAnimator(new SlideInUpAnimator());
                    content_list.setAdapter(contentRecommendAdapter);

                    content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);


                            if(dy > 0) //check for scroll down
                            {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                                    contentRecommendAdapter.loadNextPage();

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
