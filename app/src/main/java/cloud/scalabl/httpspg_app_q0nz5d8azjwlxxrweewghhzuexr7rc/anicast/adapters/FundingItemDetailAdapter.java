package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FundingItemDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.GoodsFundingActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.OriginalIllustActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchResultActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SellerActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionFollow;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionLikeDislike;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.FundingItemDetailHeaderViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.FundingMarketItemHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.PostDetailCommentViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelinePatronViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelinePostDetailHeaderViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class FundingItemDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private ArrayList<ParseObject> items = new ArrayList<>();
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages = new ArrayList<>();;
    private int objectsPerPage = 10;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;
    private ParseObject parentObject;

    private int HeaderViewType = 0;
    private int ContentViewType = 1;
    private int ListViewType = 2;

    private FunctionBase functionBase;

    public FundingItemDetailAdapter(Context context, RequestManager requestManager, final ParseObject parentOb ) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.parentObject = parentOb;
        this.functionBase = new FunctionBase(context);

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ArrayList<String> queryArray = new ArrayList<>();

                String[] tags = functionBase.jsonArrayToStringArray(parentObject.getJSONArray("tag_array"));

                for(String tag:tags){

                    queryArray.add(tag);

                }

                ParseQuery<ParseObject> query = ParseQuery.getQuery("FundingMarketItem");
                query.include("dealer");
                query.whereContainedIn("tag_array", queryArray);
                query.whereEqualTo("status", true);
                query.whereNotEqualTo("objectId", parentOb.getObjectId());
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0){

            View headerView;
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_funding_item_header, parent, false);

            return new FundingItemDetailHeaderViewHolder(headerView);

        } else {

            View itemView;

            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_funding_sales_item, parent, false);

            return new FundingMarketItemHolder(itemView);

        }



    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == 0){

            final ParseObject dealerOb = parentObject.getParseObject("dealer");

            ImageView item_image = ((FundingItemDetailHeaderViewHolder)holder).getItemImage();
            LinearLayout item_image_layout = ((FundingItemDetailHeaderViewHolder)holder).getItemImageLayout();
            TextView name = ((FundingItemDetailHeaderViewHolder)holder).getName();
            TextView price = ((FundingItemDetailHeaderViewHolder)holder).getPrice();

            LinearLayout tag_layout = ((FundingItemDetailHeaderViewHolder)holder).getTagLayout();
            TagGroup tag_group = ((FundingItemDetailHeaderViewHolder)holder).getTagGroup();

            RecyclerView item_detail_list = ((FundingItemDetailHeaderViewHolder)holder).getItemDetailList();
            CircleImageView dealer_photo = ((FundingItemDetailHeaderViewHolder)holder).getDealerPhoto() ;
            TextView dealer_name  = ((FundingItemDetailHeaderViewHolder)holder).getDealerName();
            RecyclerView dealer_item_list = ((FundingItemDetailHeaderViewHolder)holder).getDealerItemList();
            LinearLayout move_to_dealer = ((FundingItemDetailHeaderViewHolder)holder).getMoveToDealer();
            LinearLayout dealer_info_layout = ((FundingItemDetailHeaderViewHolder)holder).getDealerInfoLayout();
            LinearLayout funding_button = ((FundingItemDetailHeaderViewHolder)holder).getFundingButton();

            move_to_dealer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SellerActivity.class);
                    intent.putExtra("dealerId", dealerOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

            functionBase.generalImageLoading(item_image, parentObject, requestManager);
            name.setText(parentObject.getString("name"));
            price.setText(functionBase.makeComma(parentObject.getInt("price")) + " BOX");

            String[] tagArray = functionBase.jsonArrayToStringArray(parentObject.getJSONArray("tag_array"));

            if(tagArray.length != 0){

                tag_group.setTags(functionBase.jsonArrayToStringArray(parentObject.getJSONArray("tag_array")));
                tag_layout.setVisibility(View.VISIBLE);

            } else {

                tag_layout.setVisibility(View.GONE);

            }

            item_image.setFocusableInTouchMode(true);
            item_image.requestFocus();

            dealer_name.setText( dealerOb.getString("name") );
            functionBase.profileImageLoading(dealer_photo, dealerOb, requestManager);

            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
            dealer_item_list.setLayoutManager(layoutManager);
            dealer_item_list.setHasFixedSize(true);
            FundingDealerItemAdapter fundingDealerItemAdapter = new FundingDealerItemAdapter(context, requestManager, dealerOb);
            dealer_item_list.setAdapter(fundingDealerItemAdapter);



            LinearLayoutManager layoutManagerForDetail = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
            item_detail_list.setLayoutManager(layoutManagerForDetail);
            item_detail_list.setHasFixedSize(true);

            FundingMarketItemEditorAdapter fundingMarketItemEditorAdapter = new FundingMarketItemEditorAdapter(context, requestManager, parentObject);
            item_detail_list.setAdapter(fundingMarketItemEditorAdapter);


            funding_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, GoodsFundingActivity.class);
                    intent.putExtra("itemId", parentObject.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

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

        return position;
    }


    @Override
    public int getItemCount() {
        return items.size() + 1;
    }


    public ParseObject getItem(int position) {
        return items.get(position-1);
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
