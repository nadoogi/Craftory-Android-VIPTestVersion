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

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.DMChatActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FundingItemDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FundingMarketCategoryActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SocialMSGActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.FundingMarketHeaderHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.FundingMarketItemHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SearchUserViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class FundingMarketSalesItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    private FunctionBase functionBase;
    private int positionNum;



    public FundingMarketSalesItemAdapter(Context context, RequestManager requestManager, int position) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.functionBase = new FunctionBase(context);
        this.positionNum = position;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("FundingMarketItem");
                query.whereEqualTo("status", true);
                query.include("dealer");
                query.orderByDescending("createdAt");

                return query;

            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        if(positionNum == 0){

            if(viewType == 0){

                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_funding_header, parent, false);

                return new FundingMarketHeaderHolder(itemView);

            } else {

                itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_funding_sales_item, parent, false);

                return new FundingMarketItemHolder(itemView);

            }

        } else {

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_funding_sales_item, parent, false);

            return new FundingMarketItemHolder(itemView);

        }


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(positionNum == 0){

            if(position == 0){

                BannerSlider banner_slider = ((FundingMarketHeaderHolder)holder).getBannerSlider();

                List<Banner> banners = new ArrayList<>();
                banners.add(new RemoteBanner( "http://image.gmarket.co.kr/ad_seller/2015/11/30/20151130182439967928_640_260.jpg" ));
                banners.add(new RemoteBanner( "http://image.gmarket.co.kr/ad_seller/2015/04/02/20150402102359392686_640_260.jpg" ));
                banner_slider.setBanners(banners);


            } else {

                final ParseObject itemOb = getItem(position);
                ParseObject dealerOb = itemOb.getParseObject("dealer");

                ImageView sales_item_image = ((FundingMarketItemHolder)holder).getSalesItemImage();
                TextView title = ((FundingMarketItemHolder)holder).getTitle();
                TextView price = ((FundingMarketItemHolder)holder).getPrice();
                TextView dealer = ((FundingMarketItemHolder)holder).getDealer();
                LinearLayout post_image_layout = ((FundingMarketItemHolder)holder).getPostImageLayout();

                functionBase.generalImageLoading(sales_item_image, itemOb, requestManager);
                title.setText( itemOb.getString("name") );
                price.setText( functionBase.makeComma(itemOb.getInt("price")) + " BOX");
                dealer.setText(dealerOb.getString("name"));

                post_image_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, FundingItemDetailActivity.class);
                        intent.putExtra("objectId", itemOb.getObjectId());
                        context.startActivity(intent);

                    }
                });


            }

        } else {

            final ParseObject itemOb = getItem(position);
            ParseObject dealerOb = itemOb.getParseObject("dealer");

            ImageView sales_item_image = ((FundingMarketItemHolder)holder).getSalesItemImage();
            TextView title = ((FundingMarketItemHolder)holder).getTitle();
            TextView price = ((FundingMarketItemHolder)holder).getPrice();
            TextView dealer = ((FundingMarketItemHolder)holder).getDealer();
            LinearLayout post_image_layout = ((FundingMarketItemHolder)holder).getPostImageLayout();

            functionBase.generalImageLoading(sales_item_image, itemOb, requestManager);
            title.setText( itemOb.getString("name") );
            price.setText( functionBase.makeComma(itemOb.getInt("price")) + " BOX");
            dealer.setText(dealerOb.getString("name"));

            post_image_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, FundingItemDetailActivity.class);
                    intent.putExtra("objectId", itemOb.getObjectId());
                    context.startActivity(intent);

                }
            });

        }


    }

    @Override
    public int getItemViewType(int position) {

        return position ;
    }


    @Override
    public int getItemCount() {

        if(positionNum == 0){

            return items.size() + 1;

        } else {

            return items.size();
        }

    }


    public ParseObject getItem(int position) {

        if(positionNum == 0){

            return items.get(position-1);

        } else {

            return items.get(position);
        }

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
