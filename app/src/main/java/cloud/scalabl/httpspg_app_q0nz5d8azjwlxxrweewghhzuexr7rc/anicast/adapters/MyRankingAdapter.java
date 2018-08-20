package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.RankingViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class MyRankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private static ArrayList<ParseObject> items;
    private static ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private static List<List<ParseObject>> objectPages;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;

    private String filter;


    public MyRankingAdapter(Context context, RequestManager requestManager, final ParseUser currentUser, final String filter) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.filter = filter;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                if(filter.equals("my_fan")){

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("RelationLog");
                    query.whereEqualTo("to", currentUser);
                    query.include("from");
                    query.orderByDescending("score");

                    return query;

                } else if( filter.equals("my_creator") ){

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("RelationLog");
                    query.whereEqualTo("from", currentUser);
                    query.include("to");
                    query.orderByDescending("score");

                    return query;


                } else {


                    ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                    query.whereNotEqualTo("total_score", null);
                    query.whereGreaterThan("total_score", 1);
                    query.orderByDescending("total_score");

                    return query;

                }

            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rankingView;

        rankingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_ranking, parent, false);

        return new RankingViewHolder(rankingView);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //기능 추가
        final ParseObject rankingOb = getItem(position);

        TextView ranking_text = ((RankingViewHolder)holder).getRankingText();
        CircleImageView ranker_photo = ((RankingViewHolder)holder).getRankerPhoto();
        TextView ranker_name = ((RankingViewHolder)holder).getRankingName();
        TextView gifted_point = ((RankingViewHolder)holder).getGiftedPoint();
        LinearLayout ranking_layout = ((RankingViewHolder)holder).getRankingLayout();
        ImageView icon_ranking = ((RankingViewHolder)holder).getIconRanking();

        ranking_text.setText(String.valueOf(position+1));

        if(position == 0){

            icon_ranking.setVisibility(View.VISIBLE);

        } else {

            icon_ranking.setVisibility(View.GONE);
        }

        FunctionBase functionBase = new FunctionBase(context);



        ParseObject userOb;
        int score = 0;

        if(filter.equals("my_fan")){

            userOb = rankingOb.getParseObject("from");
            score = rankingOb.getInt("score");

        } else if(filter.equals("my_creator")){

            userOb = rankingOb.getParseObject("to");
            score = rankingOb.getInt("score");

        } else {

            userOb = rankingOb;
            score = rankingOb.getInt("total_score");

        }

        functionBase.profileImageLoading(ranker_photo, userOb, requestManager);
        functionBase.profileNameLoading(ranker_name, userOb);
        gifted_point.setText(functionBase.makeComma(score));

        ranking_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ParseObject intentUserOb;

                if(filter.equals("my_fan")){

                    intentUserOb = rankingOb.getParseObject("from");

                } else if(filter.equals("my_creator")){

                    intentUserOb = rankingOb.getParseObject("to");

                } else {

                    intentUserOb = rankingOb;

                }

                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("userId", intentUserOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });

        //기능 추가

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public ParseObject getItem(int position) {
        return items.get(position);
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
