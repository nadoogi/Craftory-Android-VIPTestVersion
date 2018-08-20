package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.RequestManager;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionLikeDislike;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.PostDetailCommentViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelinePatronViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelinePostDetailHeaderViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.YoutubeHeaderViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class YoutubeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private ArrayList<ParseObject> items = new ArrayList<>();
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages = new ArrayList<>();;
    private int objectsPerPage = 25;
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
    private YouTubeBaseActivity activity;

    public YoutubeDetailAdapter(YouTubeBaseActivity activity, Context context, RequestManager requestManager, final ParseObject parentOb ) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.parentObject = parentOb;
        this.functionBase = new FunctionBase(context);
        this.activity = activity;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                query.include("user");
                query.whereEqualTo("post_type", "post");

                if(parentOb.get("tag_array") != null){

                    if(parentOb.getJSONArray("tag_array").length() != 0) {

                        ArrayList<String> tagQuery = functionBase.jsonArrayToArrayList(parentOb.getJSONArray("tag_array"));
                        query.whereContainedIn("tag_array", tagQuery);

                    }

                }

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
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_youtube_header, parent, false);

            return new YoutubeHeaderViewHolder(headerView);

        } else if (viewType == 1) {

            View commentView;

            commentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment_recent, parent, false);

            return new PostDetailCommentViewHolder(commentView);

        } else {

            View timelineView;

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item2_2, parent, false);

            return new TimelineItemViewHolder(timelineView);

        }



    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == 0){

            TextView title = ((YoutubeHeaderViewHolder)holder).getTitle();
            TextView description = ((YoutubeHeaderViewHolder)holder).getDescription();
            TextView post_date = ((YoutubeHeaderViewHolder)holder).getPostDate();

            final LinearLayout post_like_button = ((YoutubeHeaderViewHolder)holder).getPostLikeButton();
            final ImageView like_icon = ((YoutubeHeaderViewHolder)holder).getLikeIcon();
            final TextView like_count = ((YoutubeHeaderViewHolder)holder).getLikeCount();

            LinearLayout share_button = ((YoutubeHeaderViewHolder)holder).getShareButton();
            final ImageView share_icon = ((YoutubeHeaderViewHolder)holder).getShareIcon();
            final TextView share_count = ((YoutubeHeaderViewHolder)holder).getShareCount();

            TextView pv_count = ((YoutubeHeaderViewHolder)holder).getPvCount();

            title.setText(parentObject.getString("title"));
            description.setText(parentObject.getString("body"));

            String postDateString = functionBase.dateToString(parentObject.getCreatedAt());
            post_date.setText(postDateString);


            title.setFocusableInTouchMode(true);
            title.requestFocus();

            functionBase.postIconColorFilterInit(null, like_icon, null , null, share_icon, null, true);
            functionBase.postCounterColorFilterInit(null, like_count, null, share_count, true);

            if(like_count != null){

                like_count.setText(functionBase.makeComma(parentObject.getInt("like_count")));
                pv_count.setText(functionBase.makeComma(parentObject.getInt("pv_count")));
            }


            if(share_button != null){

                share_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(functionBase.parseArrayCheck(parentObject, "share_user", currentUser.getObjectId())){

                            TastyToast.makeText(context, "이미 공유 했습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        } else {

                            MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                            builder.title("확인");
                            builder.content("내 타임라인에 공유하시겠습니까?");
                            builder.positiveText("예");
                            builder.negativeText("아니요");
                            builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                }
                            });

                            builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                    HashMap<String, Object> params = new HashMap<>();

                                    params.put("key", currentUser.getSessionToken());
                                    params.put("shareObId", parentObject.getObjectId() );

                                    Date uniqueIdDate = new Date();
                                    String uniqueId = uniqueIdDate.toString();

                                    params.put("uid", uniqueId);

                                    ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                        public void done(Map<String, Object> mapObject, ParseException e) {

                                            if (e == null) {

                                                if(mapObject.get("status").toString().equals("true")){

                                                    TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                    parentObject.fetchInBackground(new GetCallback<ParseObject>() {
                                                        @Override
                                                        public void done(ParseObject object, ParseException e) {

                                                            if(e==null){

                                                                if(functionBase.parseArrayCheck(object, "share_user", currentUser.getObjectId())){

                                                                    share_icon.setColorFilter(functionBase.likedColor);
                                                                    share_count.setTextColor(functionBase.likedColor);

                                                                    share_count.setText( String.valueOf(object.getInt("share_count")) );

                                                                } else {

                                                                    share_icon.setColorFilter(functionBase.mainColor);
                                                                    share_count.setTextColor(functionBase.mainColor);

                                                                    share_count.setText( String.valueOf(object.getInt("share_count")) );

                                                                }

                                                            } else {


                                                            }

                                                        }
                                                    });

                                                } else {

                                                    TastyToast.makeText(context, "실패 했어요 ㅜㅜ", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                }

                                            } else {

                                                e.printStackTrace();


                                            }
                                        }
                                    });

                                }
                            });
                            builder.show();

                        }

                    }
                });

                if(share_count != null){

                    if(functionBase.parseArrayCheck(parentObject, "share_user", currentUser.getObjectId())){

                        share_icon.setColorFilter(functionBase.likedColor);
                        share_count.setTextColor(functionBase.likedColor);

                        share_count.setText( String.valueOf(parentObject.getInt("share_count")) );

                    } else {

                        share_icon.setColorFilter(functionBase.mainColor);
                        share_count.setTextColor(functionBase.mainColor);

                        share_count.setText( String.valueOf(parentObject.getInt("share_count")) );

                    }

                }

            }


            if(post_like_button != null){

                FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, parentObject, post_like_button, like_icon, like_count);
                likeFunction.likeButtonStatusCheck();

                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, parentObject, post_like_button, like_icon, like_count);
                        likeClickFunction.likeButtonFunction();
                    }
                });


            }


        } else if(position == 1){

            FunctionComment functionComment = new FunctionComment(context);
            functionComment.CommentPreviewBuilder(holder, parentObject, requestManager);

        } else {

            final ParseObject timelineOb = getItem(position);

            if(timelineOb.getString("post_type").equals("patron")){

                FunctionPost functionPost = new FunctionPost(context);
                functionPost.TimelineArtistPatronAdapterBuilder( timelineOb, holder, requestManager, "all");



            } else {

                FunctionPost functionPost = new FunctionPost(context);
                functionPost.TimelineArtistPostAdapterBuilder( timelineOb, holder, requestManager, "all");

            }


        }

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return items.size() + 2;
    }


    public ParseObject getItem(int position) {
        return items.get(position-2);
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
