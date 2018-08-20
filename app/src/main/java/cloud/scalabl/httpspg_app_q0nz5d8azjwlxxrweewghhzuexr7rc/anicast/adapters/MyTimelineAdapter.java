package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.RequestManager;
import com.google.android.gms.ads.InterstitialAd;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemShareViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class MyTimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private ArrayList<ParseObject> items;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage;
    private RequestManager requestManager;
    private FunctionBase functionBase;
    private FunctionPost functionPost;
    private Context context;
    private ParseUser currentUser;


    public MyTimelineAdapter(Context context, RequestManager requestManager, FunctionBase functionBase, FunctionPost functionPost) {

        this.requestManager = requestManager;
        this.context = context;
        ParseUser currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.functionBase = functionBase;
        this.functionPost = functionPost;
        this.currentUser = ParseUser.getCurrentUser();

        final ArrayList<String> postType = new ArrayList<>();
        postType.add("notice");
        postType.add("event");
        postType.add("patron");
        postType.add("post");
        postType.add("webtoon");
        postType.add("album");
        postType.add("seriese");
        postType.add("share");
        postType.add("youtube");

        if(currentUser != null && currentUser.get("follow_array") != null){

            JSONArray followArray = currentUser.getJSONArray("follow_array");
            final ArrayList<String> followList = new ArrayList<>();
            for(int i=0;followArray.length()>i;i++){

                try {
                    String item = followArray.getString(i);
                    followList.add(item);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            followList.add(currentUser.getObjectId());

            this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                @Override
                public ParseQuery<ParseObject> create() {

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");

                    query.whereContainedIn("post_type", postType);
                    query.whereContainedIn("userId", followList);
                    query.whereEqualTo("status", true);
                    query.whereEqualTo("open_flag", true);
                    query.include("user");
                    query.include("share_post");
                    query.include("item");
                    query.orderByDescending("createdAt");

                    return query;
                }
            };

            loadObjects(currentPage);

        } else {


            this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                @Override
                public ParseQuery<ParseObject> create() {

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");

                    query.whereContainedIn("post_type", postType);
                    query.whereEqualTo("status", true);
                    query.whereEqualTo("open_flag", true);
                    query.include("user");
                    query.include("share_post");
                    query.include("item");
                    query.orderByDescending("createdAt");

                    return query;
                }
            };

            loadObjects(currentPage);

        }



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ParseObject timelineOb = getItem(viewType);

        View timelineView;

        if(timelineOb.getString("post_type").equals("patron")){

            if(timelineOb.getString("content_type").equals("goods")){

                timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_patron_goods, parent, false);

                TimelineItemViewHolder timelineItemViewHolder = new TimelineItemViewHolder(timelineView);

                return new TimelineItemViewHolder(timelineView);

            } else {

                timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_patron, parent, false);

                TimelineItemViewHolder timelineItemViewHolder = new TimelineItemViewHolder(timelineView);

                return new TimelineItemViewHolder(timelineView);

            }

        } else if(timelineOb.getString("post_type").equals("share")){

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item_share, parent, false);

            TimelineItemShareViewHolder timelineItemShareViewHolder = new TimelineItemShareViewHolder(timelineView);

            return timelineItemShareViewHolder;

        } else {

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item, parent, false);

            TimelineItemViewHolder timelineItemViewHolder = new TimelineItemViewHolder(timelineView);

            return new TimelineItemViewHolder(timelineView);

        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ParseObject timelineOb = getItem(position);

        if(holder instanceof TimelineItemShareViewHolder){

            ParseObject postOb = timelineOb.getParseObject("share_post");
            String postUserId = postOb.getParseObject("user").getObjectId();

            holder.itemView.setVisibility(View.VISIBLE);
            functionPost.TimelineArtistMainPostAdapterBuilder( postOb, timelineOb.getParseObject("user") ,holder, requestManager, "my", functionBase);

        } else {

            functionPost.TimelineArtistMainPostAdapterBuilder( timelineOb, holder, requestManager, "my", functionBase);

        }

    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

        if(holder instanceof TimelineItemViewHolder){

            ImageView patron_image = ((TimelineItemViewHolder)holder).getPostImage();
            ImageView profile_image = ((TimelineItemViewHolder)holder).getWritterPhoto();
            requestManager.clear(profile_image);

            if(patron_image != null){

                requestManager.clear(patron_image);

            }

        } else {

            ImageView patron_image = ((TimelineItemShareViewHolder)holder).getPostImage();
            ImageView profile_image = ((TimelineItemShareViewHolder)holder).getWritterPhoto();
            requestManager.clear(profile_image);
            if(patron_image != null){

                requestManager.clear(patron_image);

            }

        }



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

    /*
    private List<ParseObject> duplicationRemove(List<ParseObject> foundOb){

        List<ParseObject> result = new ArrayList<>();

        for(int i=0;foundOb.size()>i;i++){

            ParseObject item = foundOb.get(i);

            if(item.getString("post_type").equals("share")){


            }


        }


    }
    */

    public void loadNextPage() {

        Log.d("loadNestPage", String.valueOf(items.size()));
        Log.d("currentPage", String.valueOf(currentPage));

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
