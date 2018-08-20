package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FollowListActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.MyRecentActionViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelinePatronViewHolder;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class MyRecentActionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private static ArrayList<ParseObject> items = new ArrayList<>();
    private static ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private static List<List<ParseObject>> objectPages = new ArrayList<>();;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private static RequestManager requestManager;
    protected static Context context;
    protected static ParseUser currentUser;


    public MyRecentActionAdapter(Context context, RequestManager requestManager) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("MyLog");
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.whereEqualTo("user", currentUser);
                query.whereEqualTo("status", true);
                query.include("user");
                query.include("artist_post");
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0){

            View recentView;

            recentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recentaction_header, parent, false);
            return new MyRecentActionViewHolder(recentView);

        } else {

            final ParseObject timelineOb = getItem(viewType).getParseObject("artist_post");

            if(timelineOb.getString("post_type").equals("patron")){

                View timelineView;

                timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_patron2, parent, false);

                return new TimelinePatronViewHolder(timelineView);

            } else {

                View timelineView;

                timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item2, parent, false);

                return new TimelineItemViewHolder(timelineView);

            }

        }


    }



    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if(position == 0){

            RecyclerView follow_list = ((MyRecentActionViewHolder)holder).getFollowList();
            TextView more_info_button = ((MyRecentActionViewHolder)holder).getMoreInfoButton();
            LinearLayout follow_detail_button = ((MyRecentActionViewHolder)holder).getFollowDetailButton();

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);

            follow_list.setLayoutManager(layoutManager);
            follow_list.setHasFixedSize(true);
            follow_list.setNestedScrollingEnabled(false);

            MyRecentFollowAdapter myRecentFollowAdapter = new MyRecentFollowAdapter(context, requestManager);

            follow_list.setItemAnimator(new SlideInUpAnimator());
            follow_list.setAdapter(myRecentFollowAdapter);

            follow_detail_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, FollowListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
            });



        } else {

            final ParseObject timelineOb = getItem(position).getParseObject("artist_post");

            if(holder instanceof TimelinePatronViewHolder){

                FunctionPost functionPost = new FunctionPost(context);
                functionPost.TimelineArtistPatronAdapterBuilder( timelineOb, holder, requestManager, "recent");



            } else {

                FunctionPost functionPost = new FunctionPost(context);
                functionPost.TimelineArtistPostAdapterBuilder( timelineOb, holder, requestManager, "recent");

            }

        }

        //기능 추가

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return items.size()+1;
    }


    public ParseObject getItem(int position) {
        return items.get(position-1);
    }



    public void loadObjects(final int page) {

        final ParseQuery<ParseObject> query = this.queryFactory.create();

        if (this.objectsPerPage > 0 && this.paginationEnabled) {
            this.setPageOnQuery(page, query);
        }

        this.notifyOnLoadingListeners();

        if (page >= objectPages.size()) {
            objectPages.add(page, new ArrayList<ParseObject>());
        }


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> foundObjects, ParseException e) {

                if ((e != null) && ((e.getCode() == ParseException.CONNECTION_FAILED) || (e.getCode() != ParseException.CACHE_MISS))) {

                    hasNextPage = true;

                } else if (foundObjects != null) {

                    // Only advance the page, this prevents second call back from CACHE_THEN_NETWORK to
                    // reset the page.
                    if (page >= currentPage) {
                        currentPage = page;

                        // since we set limit == objectsPerPage + 1
                        hasNextPage = (foundObjects.size() > objectsPerPage);
                    }

                    if (paginationEnabled && foundObjects.size() > objectsPerPage) {
                        // Remove the last object, fetched in order to tell us whether there was a "next page"
                        foundObjects.remove(objectsPerPage);
                    }

                    List<ParseObject> currentPage = objectPages.get(page);
                    currentPage.clear();
                    currentPage.addAll(foundObjects);

                    syncObjectsWithPages();

                    // executes on the UI thread
                    notifyDataSetChanged();
                }

                notifyOnLoadedListeners(foundObjects, e);

            }
        });
    }


    public void loadNextPage() {


        if (items.size() == 0) {

            loadObjects(0);

        } else {

            loadObjects(currentPage + 1);

        }
    }


    public void setObjectsPerPage(int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }

    private void syncObjectsWithPages() {
        items.clear();
        for (List<ParseObject> pageOfObjects : objectPages) {
            items.addAll(pageOfObjects);
        }
    }



    protected void setPageOnQuery(int page, ParseQuery<ParseObject> query) {
        query.setLimit(this.objectsPerPage);
        query.setSkip(page * this.objectsPerPage);
    }

    public void addOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {
        this.onQueryLoadListeners.add(listener);
    }

    public void removeOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {
        this.onQueryLoadListeners.remove(listener);
    }

    private void notifyOnLoadingListeners() {
        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {
            listener.onLoading();
        }
    }

    private void notifyOnLoadedListeners(List<ParseObject> objects, Exception e) {
        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {
            listener.onLoaded(objects, e);
        }
    }




}
