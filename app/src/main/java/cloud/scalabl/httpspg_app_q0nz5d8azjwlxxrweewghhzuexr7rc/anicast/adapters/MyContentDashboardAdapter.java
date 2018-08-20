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
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;


import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MyInfoActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.MyHeaderViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemShareViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class MyContentDashboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    public ArrayList<ParseObject> items;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage;
    private RequestManager requestManager;
    protected Context context;
    private final int Header_View_Type = 1000;
    private ParseUser currentUser;

    private FunctionBase functionBase;

    public MyContentDashboardAdapter(Context context, RequestManager requestManager) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.functionBase = new FunctionBase(context);

        final ArrayList<String> postType = new ArrayList<>();

        postType.add("notice");
        postType.add("event");
        postType.add("patron");
        postType.add("post");
        postType.add("webtoon");
        postType.add("album");
        postType.add("seriese");
        postType.add("share");


        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");

                query.whereContainedIn("post_type", postType);
                query.whereEqualTo("userId", currentUser.getObjectId());
                query.whereEqualTo("status", true);
                query.whereEqualTo("open_flag", true);
                query.include("user");
                query.include("share_post");
                query.include("item");
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        if(viewType == Header_View_Type){

            View timelineView;

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_header, parent, false);

            return new MyHeaderViewHolder(timelineView);

        } else {

            ParseObject timelineOb = getItem(viewType);

            View timelineView;

            if(timelineOb.getString("post_type").equals("patron")){

                if(timelineOb.getString("content_type").equals("goods")){

                    timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_patron_goods, parent, false);

                    TimelineItemViewHolder timelineItemViewHolder = new TimelineItemViewHolder(timelineView);

                    return new TimelineItemViewHolder(timelineView);

                } else {

                    timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_patron, parent, false);

                    TimelineItemViewHolder timelineItemViewHolder = new TimelineItemViewHolder(timelineView);

                    return new TimelineItemViewHolder(timelineView);

                }

            } else if(timelineOb.getString("post_type").equals("share")){

                timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item_share, parent, false);

                TimelineItemShareViewHolder timelineItemShareViewHolder = new TimelineItemShareViewHolder(timelineView);

                return timelineItemShareViewHolder;

            } else {

                timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item, parent, false);

                TimelineItemViewHolder timelineItemViewHolder = new TimelineItemViewHolder(timelineView);

                return new TimelineItemViewHolder(timelineView);

            }

        }



    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



        if(holder instanceof MyHeaderViewHolder){

            CircleImageView writter_photo = ((MyHeaderViewHolder)holder).getWritterPhoto();
            TextView writter_name = ((MyHeaderViewHolder)holder).getWritterName();
            TextView writter_email = ((MyHeaderViewHolder)holder).getWritterEmail();
            TextView writter_body = ((MyHeaderViewHolder)holder).getWritterBody();
            TextView post_count = ((MyHeaderViewHolder)holder).getPostCount();
            TextView follower_count = ((MyHeaderViewHolder)holder).getFollowerCount();
            TextView following_count = ((MyHeaderViewHolder)holder).getFollowingCount();
            final TextView current_point = ((MyHeaderViewHolder)holder).getCurrentPoint();
            TextView gift_point = ((MyHeaderViewHolder)holder).getGiftPoint();
            LinearLayout following_button = ((MyHeaderViewHolder)holder).getFollowingButton();
            LinearLayout point_manage_button = ((MyHeaderViewHolder)holder).getPointManageButton();
            LinearLayout myinfo_setting_button = ((MyHeaderViewHolder)holder).getMyinfoSettingButton();
            TextView following_text = ((MyHeaderViewHolder)holder).getFollowingText();
            final TextView like_count = ((MyHeaderViewHolder)holder).getLikeCount();
            LinearLayout myinfo_setting = ((MyHeaderViewHolder)holder).getMyInfoSetting();

            myinfo_setting.setFocusableInTouchMode(true);
            myinfo_setting.requestFocus();

            if(currentUser != null){

                try {

                    currentUser = currentUser.fetch();

                    functionBase.profileImageLoading(writter_photo, currentUser, requestManager, "MyCountentDashboard");
                    functionBase.profileNameLoading(writter_name, currentUser);

                    writter_email.setText("( " + currentUser.getEmail() + " )");
                    post_count.setText( functionBase.makeComma( currentUser.getInt("post_count") ) );
                    following_count.setText( functionBase.makeComma( currentUser.getInt("follower_count") ) );
                    follower_count.setText( functionBase.makeComma( currentUser.getInt("follow_count") ) );

                    if(currentUser.get("my_talk") != null){

                        if(currentUser.getString("my_talk").length() != 0){

                            writter_body.setText(currentUser.getString("my_talk"));

                        } else {

                            writter_body.setText("작가님 말씀을 입력해 주세요.");

                        }

                    } else {

                        writter_body.setText("작가님 말씀을 입력해 주세요.");
                    }





                    if(currentUser.get("point") != null){

                        currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {

                                if(e==null){

                                    current_point.setText( functionBase.makeComma( object.getInt("current_point") ) );

                                } else {

                                    e.printStackTrace();

                                }

                            }
                        });

                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }


            if(myinfo_setting != null ){

                myinfo_setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, MyInfoActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }

        } else {

            ParseObject timelineOb = getItem(position);

            if(holder instanceof TimelineItemShareViewHolder){

                ParseObject postOb = timelineOb.getParseObject("share_post");

                FunctionPost functionPost = new FunctionPost(context);

                functionPost.TimelineArtistMainPostAdapterBuilder( postOb, timelineOb.getParseObject("user") ,holder, requestManager, "my", functionBase);

            } else {

                FunctionPost functionPost = new FunctionPost(context);
                functionPost.TimelineArtistMainPostAdapterBuilder( timelineOb, holder, requestManager, "my", functionBase);

            }

        }

        //기능 추가

    }

    @Override
    public int getItemViewType(int position) {

        if(position == 0){

            return Header_View_Type;

        } else {

            return position;

        }


    }


    @Override
    public int getItemCount() {
        Log.d("itemCount", String.valueOf(items.size()));
        return items.size()+1;
    }


    public ParseObject getItem(int position) {
        return items.get(position -1);
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

        Log.d("loadNestPage", String.valueOf(items.size()));
        Log.d("currentPage", String.valueOf(currentPage));

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
