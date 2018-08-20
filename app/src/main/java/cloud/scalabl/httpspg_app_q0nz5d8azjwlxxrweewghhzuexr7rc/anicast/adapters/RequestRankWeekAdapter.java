package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.RankingViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class RequestRankWeekAdapter extends RecyclerParseAdapter {

    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private static ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private static List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private static List<List<ParseObject>> objectPages;
    private static ArrayList<ParseObject> items;
    private static int currentPage;


    private static RequestManager requestManager;


    public RequestRankWeekAdapter(Context context, RequestManager requestManager) {

        super(context);
        this.requestManager = requestManager;

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();



        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("MyInfo");
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.whereEqualTo("status", true);
                query.include("user");
                query.orderByDescending("weekly_gifted_point");

                return query;
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

        final ParseObject rankingOb = getItem(position);

        TextView ranking_text = ((RankingViewHolder)holder).getRankingText();
        CircleImageView ranker_photo = ((RankingViewHolder)holder).getRankerPhoto();
        TextView ranker_name = ((RankingViewHolder)holder).getRankingName();
        TextView gifted_point = ((RankingViewHolder)holder).getGiftedPoint();

        //기능 추가
        ranking_text.setText(String.valueOf(position + 1));

        ParseObject userOb = rankingOb.getParseObject("user");

        if(userOb.get("thumnail") != null){

            requestManager.load( userOb.getParseFile("thumnail").getUrl() ).into(ranker_photo);

        } else {

            requestManager.load( R.drawable.user_profile ).into(ranker_photo);

        }

        if(userOb.get("name") != null){

            ranker_name.setText( userOb.getString("name") );

        } else {

            String[] usernameSplit = userOb.getString("username").split("@");
            String username = usernameSplit[0];

            ranker_name.setText( username );

        }

        gifted_point.setText( String.valueOf( rankingOb.getInt("weekly_gifted_point") ) );


    }



    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ParseObject getItem(int position) {
        return items.get(position);
    }

    @Override
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
                    syncObjectsWithPages(items, objectPages);

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



    public void syncObjectsWithPages(ArrayList<ParseObject> items, List<List<ParseObject>> objectPages) {

        items.clear();

        for (List<ParseObject> pageOfObjects : objectPages) {

            items.addAll(pageOfObjects);

        }
    }

    protected void setPageOnQuery(int page, ParseQuery<ParseObject> query) {

        query.setLimit(this.objectsPerPage + 1);

        query.setSkip(page * this.objectsPerPage);

    }

    public void addOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {

        this.onQueryLoadListeners.add(listener);

    }

    public void removeOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {

        this.onQueryLoadListeners.remove(listener);

    }

    public void notifyOnLoadingListeners() {

        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {

            listener.onLoading();

        }

    }

    public void notifyOnLoadedListeners(List<ParseObject> objects, Exception e) {

        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {

            listener.onLoaded(objects, e);

        }

    }



}
