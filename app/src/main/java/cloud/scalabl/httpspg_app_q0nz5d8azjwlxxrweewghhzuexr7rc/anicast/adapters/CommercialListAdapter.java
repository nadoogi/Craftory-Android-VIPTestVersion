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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ToTheTopListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.CommercialListItemViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SerieseBottomViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SerieseHeader2ViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class CommercialListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    private ToTheTopListener toTheTopListener;

    public void setToTheTopListener(ToTheTopListener toTheTopListener){

        this.toTheTopListener = toTheTopListener;
    }





    public CommercialListAdapter(Context context, RequestManager requestManager, final ParseObject parentOb ) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.parentObject = parentOb;
        this.functionBase = new FunctionBase(context);

        Log.d("adapter obid", parentOb.getObjectId());

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = parentOb.getRelation("seriese_item").getQuery();
                query.whereNotEqualTo("commercial", null);
                query.whereNotEqualTo("open_date", null);
                query.whereEqualTo("status", true);
                query.whereEqualTo("open_flag", true);
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

        Log.d("itemCount", String.valueOf(getItemCount()));
        Log.d("viewType", String.valueOf(viewType));

        if(viewType == HeaderViewType){

            View headerView;
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_seriese_header_2, parent, false);

            return new SerieseHeader2ViewHolder(headerView);

        } else if(viewType == FooterViewType ){

            View bottomView;
            bottomView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_seriese_bottom, parent, false);

            return new SerieseBottomViewHolder(bottomView);


        } else {

            View timelineView;

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_commercial_item, parent, false);

            return new CommercialListItemViewHolder(timelineView);

        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SerieseHeader2ViewHolder){

            ImageView seriese_image = ((SerieseHeader2ViewHolder)holder).getSerieseImage();
            TagGroup tag_group = ((SerieseHeader2ViewHolder)holder).getTagGroup();
            TextView title = ((SerieseHeader2ViewHolder)holder).getTitle();
            CircleImageView writter_photo = ((SerieseHeader2ViewHolder)holder).getWritterPhoto();
            TextView writter_name = ((SerieseHeader2ViewHolder)holder).getWritterName();
            TextView subs_count = ((SerieseHeader2ViewHolder)holder).getSubsCount();
            ImageView subs_count_icon = ((SerieseHeader2ViewHolder)holder).getSubsCountIcon();
            final LinearLayout follow_button = ((SerieseHeader2ViewHolder)holder).getFollowButton();
            final TextView follow_button_text = ((SerieseHeader2ViewHolder)holder).getFollowButtonText();
            final LinearLayout subscribe_button = ((SerieseHeader2ViewHolder)holder).getSubscribeButton();
            final LinearLayout init_artwork = ((SerieseHeader2ViewHolder)holder).getInitArtwork();
            final TextView subscribe_text = ((SerieseHeader2ViewHolder)holder).getSubscribeText();



            if(parentObject.get("image_cdn") != null){

                requestManager
                        .load(functionBase.imageUrlBase300 + parentObject.getString("image_cdn"))
                        .into(seriese_image);

            } else {

                seriese_image.setImageResource(R.drawable.image_background);

            }

            if(parentObject.get("tag_array") != null){

                String[] tagArray = functionBase.jsonArrayToStringArray( parentObject.getJSONArray("tag_array") );

                tag_group.setTags(tagArray);

            }

            if(parentObject.get("title") != null){

                title.setText(parentObject.getString("title"));

            } else {

                title.setText("제목 입력 안됨");
            }



            if(parentObject.get("user") != null){

                final ParseObject userOb = parentObject.getParseObject("user");

                if(userOb.get("image_cdn") != null){

                    requestManager
                            .load( functionBase.imageUrlBase100 + userOb.getString("image_cdn") )
                            .into(writter_photo);

                } else if(userOb.get("thumnail") != null) {

                    requestManager
                            .load( userOb.getParseFile("thumnail").getUrl() )
                            .into(writter_photo);

                } else {

                    writter_photo.setImageResource(R.drawable.ic_action_circle_profile);

                }

                if(userOb.get("name") != null){

                    writter_name.setText( userOb.getString("name") );

                } else {

                    writter_name.setText( userOb.getString("username") );

                }

                follow_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        FunctionFollow functionFollow = new FunctionFollow(context);
                        functionFollow.followFunction( follow_button, follow_button_text, userOb);

                    }
                });

                if(currentUser != null){

                    if(currentUser.getObjectId().equals(userOb.getObjectId())){

                        follow_button.setVisibility(View.GONE);

                    } else {

                        if(functionBase.parseArrayCheck(currentUser, "follow_array", userOb.getObjectId())){

                            follow_button_text.setText("팔로우 취소");

                        } else {

                            follow_button_text.setText("팔로우");

                        };

                    }



                } else {

                    follow_button.setVisibility(View.GONE);
                }

            }

            final ParseObject firstItem = parentObject.getParseObject("first_item");

            init_artwork.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(firstItem != null){

                        if(firstItem.getBoolean("status") == false || firstItem.getBoolean("open_flag") == false ){

                            TastyToast.makeText(context, "첫화가 설정되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        } else {

                            functionBase.chargeFollowPatronCheck(firstItem, init_artwork );

                        }

                    } else {

                        TastyToast.makeText(context, "첫화가 설정되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    }



                }
            });


            tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                @Override
                public void onTagClick(String tag) {

                    Intent intent = new Intent(context, SearchActivity.class);
                    intent.putExtra("query", tag);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });


            subscribeUIUpdate(subscribe_button, subscribe_text, subs_count, subs_count_icon);


        } else if(holder instanceof CommercialListItemViewHolder){

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

            post_updated.setText( functionBase.dateToStringForDisplayYear2String( commercialOb.getDate("open_date") ) );

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

                        functionBase.chargeFollowPatronCheck( timelineOb, image);

                    } else {
                        // not purchase

                        functionBase.chargeFollowPatronCheck( timelineOb, image);

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






        } else if(holder instanceof SerieseBottomViewHolder){

            Log.d("holder", "SerieseBottomViewHolder");

            LinearLayout bottomButton = ((SerieseBottomViewHolder)holder).getBottomButton();
            TextView bottomButtonText = ((SerieseBottomViewHolder)holder).getBottomButtonText();

            bottomButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    toTheTopListener.focusToTheTop();

                }
            });

        }

    }

    private void subscribeUIUpdate(final LinearLayout subscribe_button, final TextView subscribe_text, final TextView subs_count, final ImageView subs_count_icon){

        if(currentUser != null){

            if(parentObject.get("subscriber_count") != null){

                parentObject.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedOb, ParseException e) {

                        if(e==null){

                            parentObject = fetchedOb;
                            subs_count.setText(functionBase.makeComma(fetchedOb.getInt("subscriber_count")) + " 명");

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            }

            try {
                final ParseUser fetchedUser = currentUser.fetch();

                if( functionBase.parseArrayCheck(fetchedUser, "subscribe_array", parentObject.getObjectId()) ){

                    subscribe_text.setText("구독완료");
                    subs_count.setTextColor(functionBase.likedColor);
                    subs_count_icon.setColorFilter(functionBase.likedColor);
                    subscribe_button.setClickable(true);



                    subscribe_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            subscribe_button.setClickable(false);

                            ArrayList removeArray = new ArrayList();
                            removeArray.add(parentObject.getObjectId());

                            fetchedUser.removeAll("subscribe_array", removeArray);

                            ParseRelation relation = fetchedUser.getRelation("subscribe_item");
                            relation.remove(parentObject);

                            fetchedUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        HashMap<String, Object> params = new HashMap<>();

                                        params.put("key", fetchedUser.getSessionToken());

                                        Date uniqueIdDate = new Date();
                                        String uniqueId = uniqueIdDate.toString();

                                        params.put("uid", uniqueId);
                                        params.put("type", true);
                                        params.put("serieseId", parentObject.getObjectId());

                                        ParseCloud.callFunctionInBackground("subscription_count", params, new FunctionCallback<Map<String, Object>>() {

                                            public void done(Map<String, Object> mapObject, ParseException e) {

                                                if (e == null) {

                                                    Log.d("result1", mapObject.toString());

                                                    if(mapObject.get("status").toString().equals("true")){

                                                        TastyToast.makeText(context, "구독 취소", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                        subscribeUIUpdate(subscribe_button, subscribe_text, subs_count, subs_count_icon);
                                                        subscribe_button.setClickable(true);


                                                    } else {

                                                        TastyToast.makeText(context, "구독 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                        subscribe_button.setClickable(true);

                                                    }

                                                } else {

                                                    e.printStackTrace();
                                                    subscribe_button.setClickable(true);


                                                }
                                            }
                                        });


                                    } else {

                                        subscribe_button.setClickable(true);
                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });

                } else {

                    subscribe_text.setText("구독하기");

                    subs_count.setTextColor(functionBase.likeColor);
                    subs_count_icon.setColorFilter(functionBase.likeColor);
                    subscribe_button.setClickable(true);

                    subscribe_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            subscribe_button.setClickable(false);

                            fetchedUser.addUnique("subscribe_array", parentObject.getObjectId());
                            ParseRelation relation = fetchedUser.getRelation("subscribe_item");
                            relation.add(parentObject);

                            fetchedUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        HashMap<String, Object> params = new HashMap<>();

                                        params.put("key", fetchedUser.getSessionToken());

                                        Date uniqueIdDate = new Date();
                                        String uniqueId = uniqueIdDate.toString();

                                        params.put("uid", uniqueId);
                                        params.put("type", false);
                                        params.put("serieseId", parentObject.getObjectId());

                                        ParseCloud.callFunctionInBackground("subscription_count", params, new FunctionCallback<Map<String, Object>>() {

                                            public void done(Map<String, Object> mapObject, ParseException e) {

                                                if (e == null) {

                                                    Log.d("result2", mapObject.toString());

                                                    if(mapObject.get("status").toString().equals("true")){

                                                        TastyToast.makeText(context, "구독 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                        subscribeUIUpdate(subscribe_button, subscribe_text, subs_count, subs_count_icon);
                                                        subscribe_button.setClickable(true);


                                                    } else {

                                                        TastyToast.makeText(context, "구독 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                        subscribe_button.setClickable(true);

                                                    }

                                                } else {

                                                    e.printStackTrace();
                                                    subscribe_button.setClickable(true);


                                                }
                                            }
                                        });

                                    } else {

                                        subscribe_button.setClickable(true);
                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });

                }


            } catch (ParseException e) {
                e.printStackTrace();
            }


        } else {

            TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

            subscribe_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){

            return HeaderViewType;

        } else if(position == getItemCount()-1){

            return FooterViewType;

        } else {

            return position;

        }

    }


    @Override
    public int getItemCount() {
        return items.size() + 2;
    }


    public ParseObject getItem(int position) {

        Log.d("position", String.valueOf(position));
        Log.d("getitemcount", String.valueOf( getItemCount() ));

        return items.get( position - 1);

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
