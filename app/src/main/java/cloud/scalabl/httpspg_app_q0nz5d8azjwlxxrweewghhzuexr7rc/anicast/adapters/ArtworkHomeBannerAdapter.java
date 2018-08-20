package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.ArtworkHomeBannerItemViewHolder;

/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class ArtworkHomeBannerAdapter extends RecyclerParseAdapter {

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

    public ArtworkHomeBannerAdapter(Context context, RequestManager requestManager) {

        super(context);
        ArtworkHomeBannerAdapter.requestManager = requestManager;
        onQueryLoadListeners = new ArrayList<>();
        currentPage = 0;
        objectPages = new ArrayList<>();
        items = new ArrayList<>();

        queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Banner");
                query.whereEqualTo("status", true);

                query.orderByDescending("order");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View photoView;

        photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artwork_home_banner_detail, parent, false);

        return new ArtworkHomeBannerItemViewHolder(photoView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject bannerOb = getItem(position);

        ImageView banner_list = ((ArtworkHomeBannerItemViewHolder)holder).getBannerImage();
        TextView title = ((ArtworkHomeBannerItemViewHolder)holder).getTitle();
        TextView body = ((ArtworkHomeBannerItemViewHolder)holder).getBody();

        if(bannerOb.get("image") != null){

            requestManager
                    .load(bannerOb.getParseFile("image").getUrl())
                    .into(banner_list);

        } else {

            requestManager
                    .load(R.drawable.image_background5)
                    .into(banner_list);

        }

        title.setText(bannerOb.getString("title"));
        body.setText(bannerOb.getString("body"));



    }


    @Override
    public int getItemViewType(int position) {
        return position;
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

        final ParseQuery<ParseObject> query = queryFactory.create();

        if (objectsPerPage > 0 && this.paginationEnabled) {

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

        query.setLimit(objectsPerPage + 1);

        query.setSkip(page * objectsPerPage);

    }

    public void addOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {

        onQueryLoadListeners.add(listener);

    }

    public void removeOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {

        onQueryLoadListeners.remove(listener);

    }

    public void notifyOnLoadingListeners() {

        for (OnQueryLoadListener<ParseObject> listener : onQueryLoadListeners) {

            listener.onLoading();

        }

    }

    public void notifyOnLoadedListeners(List<ParseObject> objects, Exception e) {

        for (OnQueryLoadListener<ParseObject> listener : onQueryLoadListeners) {

            listener.onLoaded(objects, e);

        }

    }



}
