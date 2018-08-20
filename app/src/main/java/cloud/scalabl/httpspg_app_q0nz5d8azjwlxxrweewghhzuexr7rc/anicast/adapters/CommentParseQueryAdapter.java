package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.CommentViewHolder;


/**
 * Created by ssamkyu on 2017. 3. 14..
 */

public class CommentParseQueryAdapter extends RecyclerParseAdapter {

    private static String parentId;
    private static String type;
    private static Boolean commentFlag;
    private static int currentPosition;

    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private static ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private static List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private static List<List<ParseObject>> objectPages;
    private static ArrayList<ParseObject> items;

    private static int currentPage;

    public CommentParseQueryAdapter(Context context, final String orderBy, final String parentId, String typeString, final Boolean commentFlag ) {

        super(context);

        this.type = typeString;
        this.parentId = parentId;
        this.commentFlag = commentFlag;

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.include("user");
                query.include("pointManager");
                query.whereEqualTo("status", true);
                query.orderByDescending(orderBy);


                if(type.equals("post")){

                    query.whereEqualTo("parent", parentId);
                    query.whereEqualTo("type", "post");

                } else {

                    if(commentFlag){

                        query.whereEqualTo("parent", parentId);
                        query.whereEqualTo("type", "comment");



                    } else {

                        Log.d("type", "replay");

                        query.whereEqualTo("parent_comment", parentId);
                        query.whereEqualTo("type", "reply");

                    }


                }




                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View commentView;

        commentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);

        return new CommentViewHolder(commentView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ParseObject commentOb = getItem(position);

        FunctionComment functionComment = new FunctionComment(context);
        functionComment.CommentAdapterBuilder(commentOb, holder, commentFlag, type, parentId, this);


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
