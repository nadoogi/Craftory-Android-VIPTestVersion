package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentFavorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;

/**
 * Created by ssamkyu on 2016. 12. 17..
 */

public class CommentBestFragment extends Fragment {

    static final boolean GRID_LAYOUT = false;
    private final int ITEM_COUNT = 100;
    protected RecyclerView mRecyclerView;


    protected RecyclerView.Adapter mAdapter;


    private int pager;
    private String channelId;
    private String categoryId;
    private String parentId;
    private String type;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private TextView no_comment_msg;
    //public static CommentParseFavorAdapter commentAdapter2;
    public CommentFavorAdapter commentFavorAdapter;
    private RequestManager requestManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        parentId = getArguments().getString("parentId");
        requestManager = Glide.with(getActivity());

        return inflater.inflate(R.layout.fragment_youtube_comment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        LinearLayout inputLayout = (LinearLayout) view.findViewById(R.id.input_layout);
        inputLayout.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.comment_list);
        no_comment_msg = (TextView) view.findViewById(R.id.no_comment_msg);

        ParseQuery<ArtistPost> query = ParseQuery.getQuery(ArtistPost.class);
        query.getInBackground(parentId, new GetCallback<ArtistPost>() {
            @Override
            public void done(ArtistPost postOb, ParseException e) {

                if(e==null){

                    Log.d("postId", postOb.getObjectId());

                    final LinearLayoutManager layoutManager;

                    layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);


                    commentFavorAdapter = new CommentFavorAdapter(getActivity(), requestManager, postOb);

                    commentFavorAdapter.setObjectsPerPage(20);
                    mRecyclerView.setAdapter(commentFavorAdapter);
                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                            super.onScrolled(recyclerView, dx, dy);
                            Log.d("dx", String.valueOf(dx));
                            Log.d("dy", String.valueOf(dy));

                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                    commentFavorAdapter.loadNextPage();
                                }

                            }

                        }
                    });

                    if(postOb.getInt("comment_count")==0){

                        no_comment_msg.setVisibility(View.VISIBLE);

                    } else {

                        no_comment_msg.setVisibility(View.GONE);

                    }

                } else {

                    e.printStackTrace();
                }

            }
        });







    }


    @Override
    public void onResume() {
        super.onResume();

        if(commentFavorAdapter != null){

            commentFavorAdapter.loadObjects(0);

        }
    }
}
