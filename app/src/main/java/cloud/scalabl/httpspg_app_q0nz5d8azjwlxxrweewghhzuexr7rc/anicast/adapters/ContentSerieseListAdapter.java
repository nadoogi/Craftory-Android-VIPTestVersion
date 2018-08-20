package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionFollow;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.CommercialListItemViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SerieseBottomViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SerieseHeader2ViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class ContentSerieseListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private ArrayList<ParseObject> items = new ArrayList<>();
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages = new ArrayList<>();;
    private int objectsPerPage = 1000;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;
    private ParseObject parentObject;

    private int HeaderViewType = 0;
    private int FooterViewType = 1000;
    private int TYPEINT;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private String commercial_status = "default";
    private FunctionBase functionBase;
    private ParseObject itemObject;




    public ContentSerieseListAdapter(Context context, RequestManager requestManager, final ParseObject parentOb, ParseObject itemOb) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = new FunctionBase(context);
        this.itemObject = itemOb;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = parentOb.getRelation("seriese_item").getQuery();
                query.whereNotEqualTo("commercial", null);
                query.include("commercial");
                query.include("user");
                query.orderByDescending("order");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View timelineView;

        timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_commercial_item, parent, false);

        return new CommercialListItemViewHolder(timelineView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject timelineOb = getItem(position);

        final ImageView image = ((CommercialListItemViewHolder)holder).getSerieseImage();
        TextView title= ((CommercialListItemViewHolder)holder).getTitle();
        TextView post_updated= ((CommercialListItemViewHolder)holder).getUpdated();
        final LinearLayout purchase= ((CommercialListItemViewHolder)holder).getPurchase();
        TextView price= ((CommercialListItemViewHolder)holder).getPrice();
        LinearLayout charge_status_layout = ((CommercialListItemViewHolder)holder).getChargeStatusLayout();
        ImageView commercial_status = ((CommercialListItemViewHolder)holder).getCommercialStatus();
        TextView dday = ((CommercialListItemViewHolder)holder).getDday();

        LinearLayout price_layout = ((CommercialListItemViewHolder)holder).getPriceLayout();
        LinearLayout dday_layout = ((CommercialListItemViewHolder)holder).getDdayLayout();
        LinearLayout update_layout = ((CommercialListItemViewHolder)holder).getUpdateLayout();

        LinearLayout current_page_layout = ((CommercialListItemViewHolder)holder).getCurrentPageLayout();

        current_page_layout.setVisibility(View.GONE);



        price_layout.setVisibility(View.GONE);
        dday_layout.setVisibility(View.VISIBLE);
        update_layout.setVisibility(View.VISIBLE);

        functionBase.generalImageLoading(image, timelineOb, requestManager);

        if(timelineOb.get("title") != null){

            if(timelineOb.getString("title").length() > 50){

                title.setText( timelineOb.getString("title").substring(1, 49) + "..." );

            } else {

                title.setText( timelineOb.getString("title") );

            }

        } else {

            title.setText( timelineOb.getString("body") );

        }


        final ParseObject commercialOb = timelineOb.getParseObject("commercial");

        if(commercialOb.get("open_date") != null){

            post_updated.setText( functionBase.dateToStringForDisplayYear2String( commercialOb.getDate("open_date") ) );

        } else {

            post_updated.setText("입력안됨");
        }

        if(commercialOb.get("type") != null){

            if(commercialOb.getString("type").equals("free")){

                charge_status_layout.setVisibility(View.GONE);

                dday.setText( "" );
                price.setText( "" );

                price_layout.setVisibility(View.GONE);
                dday_layout.setVisibility(View.GONE);
                update_layout.setVisibility(View.VISIBLE);

            } else if(commercialOb.getString("type").equals("charge")) {


                charge_status_layout.setVisibility(View.VISIBLE);
                price.setText( functionBase.makeComma( commercialOb.getInt("price") ) );

                dday.setText( "0" );

                price_layout.setVisibility(View.VISIBLE);
                dday_layout.setVisibility(View.GONE);
                update_layout.setVisibility(View.GONE);


            } else if(commercialOb.getString("type").equals("preview_charge")){

                charge_status_layout.setVisibility(View.VISIBLE);

                Date freeDate = commercialOb.getDate("free_date");
                int dday_count = functionBase.dday( freeDate.getYear() + 1900, freeDate.getMonth(), freeDate.getDate() );

                dday.setText( String.valueOf(dday_count) );
                price.setText( functionBase.makeComma( commercialOb.getInt("price") )  );

                price_layout.setVisibility(View.VISIBLE);
                dday_layout.setVisibility(View.VISIBLE);
                update_layout.setVisibility(View.GONE);

            }

        } else {

            price_layout.setVisibility(View.VISIBLE);
            dday_layout.setVisibility(View.VISIBLE);
            update_layout.setVisibility(View.VISIBLE);

        }

        if(currentUser != null){

            try {

                ParseObject pointOb = currentUser.getParseObject("point").fetch();

                if(functionBase.parseArrayCheck(pointOb, "purchase_array", commercialOb.getObjectId() )){

                    //already purchase

                    charge_status_layout.setVisibility(View.GONE);

                    dday.setText( "" );
                    price.setText( "" );

                    price_layout.setVisibility(View.GONE);
                    dday_layout.setVisibility(View.GONE);
                    update_layout.setVisibility(View.VISIBLE);

                    if(timelineOb.getObjectId().equals(itemObject.getObjectId())){

                        current_page_layout.setVisibility(View.VISIBLE);
                        image.setClickable(false);

                    } else {

                        current_page_layout.setVisibility(View.GONE);
                        functionBase.chargeFollowPatronCheck( timelineOb, image);

                    }



                } else {
                    // not purchase

                    if(timelineOb.getObjectId().equals(itemObject.getObjectId())){

                        current_page_layout.setVisibility(View.VISIBLE);
                        image.setClickable(false);

                    } else {

                        current_page_layout.setVisibility(View.GONE);
                        functionBase.chargeFollowPatronCheck( timelineOb, image);

                    }



                }

            } catch (ParseException e) {

                e.printStackTrace();

            }


        } else {

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        return items.size();
    }


    public ParseObject getItem(int position) {


        return items.get( position );

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
