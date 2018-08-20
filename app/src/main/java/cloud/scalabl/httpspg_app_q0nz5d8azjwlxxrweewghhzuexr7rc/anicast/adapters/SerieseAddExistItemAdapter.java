package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class SerieseAddExistItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    public static ArrayList<ParseObject> items;
    private static ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private static List<List<ParseObject>> objectPages;
    private int objectsPerPage = 25;
    private boolean paginationEnabled;
    private boolean hasNextPage;
    private int currentPage;
    private static RequestManager requestManager;
    protected static Context context;
    protected static ParseUser currentUser;

    private static final int patronType = 0;
    private static final int postType = 1;

    private static ParseObject parentObject;
    private static AppCompatActivity activity;

    public SerieseAddExistItemAdapter(AppCompatActivity activity,Context context, RequestManager requestManager, final ParseObject parentOb) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.currentPage = 0;
        this.paginationEnabled = true;
        this.hasNextPage = true;
        this.onQueryLoadListeners = new ArrayList<>();
        this.parentObject = parentOb;
        this.activity = activity;

        final ArrayList<String> queryItem = new ArrayList<>();
        queryItem.add("patron");
        queryItem.add("seriese");

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                query.whereEqualTo("user", currentUser);
                query.whereNotEqualTo("seriese_in", true);
                query.whereEqualTo("status", true);
                query.whereEqualTo("open_flag", true);
                query.include("user");
                query.whereEqualTo("post_type", parentOb.getString("content_type") );
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final ParseObject timelineOb = getItem(viewType);

        View timelineView;

        if(timelineOb.getString("post_type").equals("patron")){

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_patron3, parent, false);

        } else {

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item4, parent, false);

        }


        return new TimelineItemViewHolder(timelineView);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject timelineOb = getItem(position);

        FunctionPost functionPost = new FunctionPost(activity, context);
        functionPost.SerieseExistAddItemBuilder(timelineOb, holder, requestManager, "all", parentObject ,activity );

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
