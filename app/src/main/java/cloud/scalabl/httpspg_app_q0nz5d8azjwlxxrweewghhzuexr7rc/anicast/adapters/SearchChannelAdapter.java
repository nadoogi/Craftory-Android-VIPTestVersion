package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
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

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 5. 15..
 */

public class SearchChannelAdapter extends RecyclerParseAdapter{

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
    private FunctionBase functionBase;

    public SearchChannelAdapter(Context context, final String orderBy, final Boolean ascending, final String queryString, String type, RequestManager requestManager) {

        super(context);

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();
        this.functionBase = new FunctionBase(context);

        this.requestManager = requestManager;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {


                ParseQuery<ParseObject> nameQuery = ParseQuery.getQuery("Channel");
                nameQuery.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);

                if(queryString != null){

                    nameQuery.whereContains("name", queryString);

                }

                nameQuery.whereEqualTo("status", true);

                return nameQuery;

            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView;

        cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_channel_item, parent, false);

        return new SearchViewHolder(cardView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject contentOb = getItem(position);


        ImageView search_image = ((SearchViewHolder)holder).search_image ;
        TextView search_name = ((SearchViewHolder)holder).search_name;
        TextView search_like = ((SearchViewHolder)holder).search_like;
        TextView search_comment = ((SearchViewHolder)holder).search_comment;
        TextView search_view = ((SearchViewHolder)holder).search_view;
        LinearLayout search_layout = ((SearchViewHolder)holder).search_layout;
        Boolean purchase = ((SearchViewHolder)holder).purchase;


        if(currentUser != null){

            try {

                purchase = functionBase.doublePuchaseCheck(contentOb, currentUser);

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }
        //기능 추가

        bindingDataToSmallLayout(contentOb, search_name, search_image, search_comment, search_view, search_layout, purchase, search_like);

    }

    private void bindingDataToSmallLayout(final ParseObject contentOb, TextView search_name, ImageView search_image, final TextView search_comment, TextView search_view , LinearLayout search_layout, final Boolean purchase, TextView search_like){


        String imageUrl = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

        functionBase.glideFunction(context, imageUrl,search_image, requestManager);

        search_name.setText( contentOb.getString("name") );
        search_comment.setText( String.valueOf( contentOb.getInt("comment_count") ) );
        search_view.setText( String.valueOf( contentOb.getInt("channel_subscribe") ) );
        search_like.setText( String.valueOf( contentOb.getInt("like_count") ) );

        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        public ImageView search_image;
        public TextView search_name;
        public TextView search_like;
        public TextView search_comment;
        public TextView search_view;
        public LinearLayout search_layout;
        public Boolean purchase;




        public SearchViewHolder(View cardView) {

            super(cardView);

            search_image = (ImageView) cardView.findViewById(R.id.search_image);
            search_name = (TextView) cardView.findViewById(R.id.search_name);
            search_like = (TextView) cardView.findViewById(R.id.search_like);
            search_comment = (TextView) cardView.findViewById(R.id.search_comment);
            search_view = (TextView) cardView.findViewById(R.id.search_view);
            search_layout = (LinearLayout) cardView.findViewById(R.id.search_layout);
            purchase = false;



        }
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
