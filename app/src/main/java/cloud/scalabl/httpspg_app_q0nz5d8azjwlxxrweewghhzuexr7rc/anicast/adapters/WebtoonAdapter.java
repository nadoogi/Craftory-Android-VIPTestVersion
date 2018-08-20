package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 5. 11..
 */

public class WebtoonAdapter extends RecyclerParseAdapter {


    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private List<List<ParseObject>> objectPages;
    private ArrayList<ParseObject> items;
    private int currentPage;

    private ParseObject currentContentOb;
    private FunctionBase functionBase;

    public WebtoonAdapter(  Context context, final String orderBy, final Boolean ascending, final ParseObject contentOb) {

        super(context);
        this.currentContentOb = contentOb;

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();
        this.functionBase = new FunctionBase(context);

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Content");
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.whereEqualTo("category", contentOb.getParseObject("category"));
                query.whereEqualTo("status", true);
                query.include("channel");

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
        View cardView;

        cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.webtoon_list_item, parent, false);

        return new WebtoonViewHolder(cardView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject contentOb = getItem(position);

        ImageView webtoon_image = ((WebtoonViewHolder)holder).webtoon_image ;
        TextView webtoon_name = ((WebtoonViewHolder)holder).webtoon_name;
        TextView webtoon_like = ((WebtoonViewHolder)holder).webtoon_like;
        TextView webtoon_comment = ((WebtoonViewHolder)holder).webtoon_comment;
        TextView webtoon_view = ((WebtoonViewHolder)holder).webtoon_view;
        LinearLayout webtoon_layout = ((WebtoonViewHolder)holder).webtoon_layout;
        Boolean purchase = ((WebtoonViewHolder)holder).purchase;
        LinearLayout price_layout = ((WebtoonViewHolder)holder).price_layout;
        TextView price_text = ((WebtoonViewHolder)holder).price_text;

        if(currentUser != null){

            try {

                purchase = functionBase.doublePuchaseCheck(contentOb, currentUser);

            } catch (JSONException e) {

                e.printStackTrace();

            }

        }


        //기능 추가

        bindingDataToSmallLayout(contentOb, webtoon_name, webtoon_image, webtoon_comment, webtoon_like, webtoon_view, webtoon_layout, purchase, price_text, price_layout);

    }

    private void bindingDataToSmallLayout(final ParseObject contentOb, TextView webtoon_name, ImageView webtoon_image, final TextView webtoon_comment,TextView webtoon_like , TextView webtoon_view , LinearLayout webtoon_layout, final Boolean purchase, final TextView price_text, LinearLayout price_layout){



        functionBase.priceLayoutBuilder(contentOb, price_text, price_layout, purchase);

        String imageUrlPhoto = functionBase.imageUrlBase300 + contentOb.getString("image_cdn");

        Glide.with(context).load( imageUrlPhoto ).into(webtoon_image);
        webtoon_name.setText( contentOb.getString("name") );
        webtoon_comment.setText( String.valueOf(contentOb.getInt("comment_count")) );
        webtoon_view.setText( String.valueOf(contentOb.getInt("total_count")) );
        webtoon_like.setText(String.valueOf(contentOb.getInt("like_count")));

        if(currentContentOb.getObjectId().equals(contentOb.getObjectId())){

            int color1 = Color.parseColor("#50ffffff");
            webtoon_layout.setBackgroundColor(color1);
            webtoon_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    TastyToast.makeText(context, "현재 보고 계신 웹툰 입니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }
            });



        } else {

            int color2 = Color.parseColor("#ffffffff");
            webtoon_layout.setBackgroundColor(color2);

            webtoon_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    functionBase.onClickFunction(purchase, contentOb, price_text, currentUser, context);

                    ((Activity) context).finish();

                }
            });

        }




    }

    public static class WebtoonViewHolder extends RecyclerView.ViewHolder {

        public ImageView webtoon_image;
        public TextView webtoon_name;
        public TextView webtoon_like;
        public TextView webtoon_comment;
        public TextView webtoon_view;
        public LinearLayout webtoon_layout;
        public Boolean purchase;
        public LinearLayout price_layout;
        public TextView price_text;



        public WebtoonViewHolder(View cardView) {

            super(cardView);

            webtoon_image = (ImageView) cardView.findViewById(R.id.webtoon_image);
            webtoon_name = (TextView) cardView.findViewById(R.id.webtoon_name);
            webtoon_like = (TextView) cardView.findViewById(R.id.webtoon_like);
            webtoon_comment = (TextView) cardView.findViewById(R.id.webtoon_comment);
            webtoon_view = (TextView) cardView.findViewById(R.id.webtoon_view);
            webtoon_layout = (LinearLayout) cardView.findViewById(R.id.webtoon_layout);
            purchase = false;
            price_layout = (LinearLayout) cardView.findViewById(R.id.price_layout);
            price_text = (TextView) cardView.findViewById(R.id.price_text);


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
