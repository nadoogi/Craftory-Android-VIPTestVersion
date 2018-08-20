package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.GetCallback;
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
 * Created by ssamkyu on 2017. 5. 8..
 */

public class ImageAdapter extends RecyclerParseAdapter {

    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private List<List<ParseObject>> objectPages;
    private ArrayList<ParseObject> items;
    private int currentPage;

    private Boolean homeFlag;
    private RequestManager requestManager;
    private RequestManager requestManager1;
    private FunctionBase functionBase;

    public ImageAdapter(Context context, final String orderBy, final Boolean ascending, final Boolean homeFlag, RequestManager requestManager) {

        super(context);
        this.homeFlag = homeFlag;
        this.requestManager = requestManager;

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();
        this.functionBase = new FunctionBase(context);

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
                query.whereEqualTo("status", true);
                query.whereEqualTo("type", "photo");
                query.whereEqualTo("payment", "free");
                query.include("content");


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


    public ImageAdapter(Context context, final String orderBy, final Boolean ascending, final Boolean homeFlag, final ParseObject channelOb, RequestManager requestManager1) {

        super(context);
        this.homeFlag = homeFlag;
        this.requestManager1 = requestManager1;
        this.functionBase = new FunctionBase(context);

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Image");
                query.whereEqualTo("status", true);
                query.whereEqualTo("type", "photo");
                query.whereEqualTo("payment", "free");
                query.whereEqualTo("channel", channelOb);
                query.whereNotEqualTo("content", null);
                query.include("content");

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
        View photoView;

        photoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_gallary, parent, false);

        return new GallaryItemViewHolder(photoView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject photoOb = getItem(position);

        final ImageView image = ((GallaryItemViewHolder)holder).image;
        TextView image_name = ((GallaryItemViewHolder)holder).image_name;
        TextView image_description = ((GallaryItemViewHolder)holder).image_description;
        RelativeLayout image_layout = ((GallaryItemViewHolder)holder).image_layout;

        LinearLayout price_layout = ((GallaryItemViewHolder)holder).price_layout;
        final TextView price_text = ((GallaryItemViewHolder)holder).price_text;
        Boolean purchase = ((GallaryItemViewHolder)holder).purchase;

        if(currentUser != null){

            try {

                purchase = functionBase.doublePuchaseCheck(photoOb.getParseObject("content"), currentUser);

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }


        functionBase.priceLayoutBuilder(photoOb.getParseObject("content"), price_text, price_layout, purchase);

        String imageUrlPhoto = functionBase.imageUrlBase300 + photoOb.getString("image_cdn");

        if(photoOb.get("name") == null || photoOb.getString("name").length() == 0){

            image_name.setVisibility(View.GONE);

        } else {

            image_name.setVisibility(View.VISIBLE);

        }

        if(photoOb.get("description") == null || photoOb.getString("description").length() == 0){

            image_description.setVisibility(View.GONE);

        } else {

            image_description.setVisibility(View.VISIBLE);

        }

        image_name.setText(photoOb.getString("name"));
        image_description.setText(photoOb.getString("description"));

        if(homeFlag){

            functionBase.glideFunction(context, imageUrlPhoto, image, requestManager);

        } else {

            functionBase.glideFunction(context, imageUrlPhoto, image, requestManager1);

        }



        final Boolean itemPurchase = purchase;

        final ParseObject contentOb = photoOb.getParseObject("content");

        if(contentOb != null){

            contentOb.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(final ParseObject fetchedContentOb, ParseException e) {

                    image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            functionBase.onClickFunction(itemPurchase, fetchedContentOb, price_text, currentUser, context);

                        }
                    });

                }
            });

        }

        //기능 추가


    }

    public static class GallaryItemViewHolder extends RecyclerView.ViewHolder{

        public RelativeLayout image_layout;
        public TextView image_name;
        public TextView image_description;
        public ImageView image;
        public TextView price_text;
        public LinearLayout price_layout;
        public Boolean purchase;

        public GallaryItemViewHolder(View gallaryView){

            super(gallaryView);

            image_layout = (RelativeLayout) gallaryView.findViewById(R.id.image_layout);
            image_name = (TextView) gallaryView.findViewById(R.id.image_name);
            image_description = (TextView) gallaryView.findViewById(R.id.image_description);
            image = (ImageView) gallaryView.findViewById(R.id.patron_image);
            price_layout = (LinearLayout) gallaryView.findViewById(R.id.price_layout);
            price_text = (TextView) gallaryView.findViewById(R.id.price_text);
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

        if(homeFlag){

            query.setLimit(10);

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
