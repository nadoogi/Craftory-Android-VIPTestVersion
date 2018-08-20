package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.CommentViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;

/**
 * Created by ssamkyu on 2017. 3. 14..
 */

public class CommentHeaderParseQueryAdapter extends RecyclerParseAdapter {

    private ParseObject parentObject;
    private String type;
    private Boolean commentFlag;
    private int currentPosition;

    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private List<List<ParseObject>> objectPages;
    private ArrayList<ParseObject> items;

    private int currentPage;

    private RequestManager requestManager;

    public CommentHeaderParseQueryAdapter(Context context, final String orderBy, final Boolean ascending, ParseObject parentOb , String typeString, final Boolean commentFlag, RequestManager requestManager) {

        super(context);

        type = typeString;
        parentObject = parentOb;
        this.commentFlag = commentFlag;

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();
        this.requestManager = requestManager;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
                //query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.include("user");
                query.include("pointManager");
                query.whereEqualTo("status", true);

                if(type.equals("post")){

                    Log.d("CommentHeader","post");

                    query.whereEqualTo("parent", parentObject.getObjectId());
                    query.whereEqualTo("type", "post");

                } else {

                    Log.d("CommentHeader","reply");

                    query.whereEqualTo("parent_comment", parentObject.getObjectId());
                    query.whereEqualTo("type", "reply");


                }


                if(ascending){

                    query.orderByAscending(orderBy);

                } else {

                    query.orderByDescending(orderBy);

                }




                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType == 0){

            View headerView;
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_header, parent, false);

            return new TimelineItemViewHolder(headerView);

        } else {

            View commentView;

            commentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);

            return new CommentViewHolder(commentView);

        }


    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){

            int headerViewType = 0;
            return headerViewType;
        } else {

            int contentViewType = 1;
            return contentViewType;
        }
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(position == 0){

            FunctionPost functionPost = new FunctionPost(context);
            functionPost.TimelineArtistPostAdapterBuilder( parentObject, holder, requestManager, "my");

        } else {

            final ParseObject commentOb = getItem(position);

            if(commentOb != null){

                Log.d("commentOb", commentOb.getObjectId());

            }

            FunctionComment functionComment = new FunctionComment(context);
            functionComment.CommentHeaderAdapterBuilder(commentOb, holder, commentFlag, type, parentObject.getObjectId(), this);

        }






    }




    @Override
    public int getItemCount() {
        return items.size() + 1;
    }

    @Override
    public ParseObject getItem(int position) {
        return items.get(position-1);
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
