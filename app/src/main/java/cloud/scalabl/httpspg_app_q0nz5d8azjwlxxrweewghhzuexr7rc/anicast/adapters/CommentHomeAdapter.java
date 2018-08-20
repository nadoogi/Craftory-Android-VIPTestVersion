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

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostingDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.CommentViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class CommentHomeAdapter extends RecyclerParseAdapter {

    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private List<List<ParseObject>> objectPages;
    private ArrayList<ParseObject> items;

    private int currentPage;

    private FunctionBase functionBase;


    public CommentHomeAdapter(Context context, final String orderBy, final Boolean ascending, final Boolean homeFlag) {

        super(context);
        Boolean homeFlag1 = homeFlag;

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
                //query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.whereEqualTo("status", true);
                query.include("user");

                if(homeFlag){

                    query.setLimit(5);

                }

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
        View commentView;

        commentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_comment, parent, false);

        return new CommentViewHolder(commentView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject commentOb = getItem(position);

        final Boolean commentFlag;

        commentFlag = commentOb.getString("type").equals("comment");

        final CircleImageView comment_photo = ((CommentViewHolder)holder).getCommentPhoto();
        final TextView comment_name = ((CommentViewHolder)holder).getCommentName();
        final TextView comment_updated = ((CommentViewHolder)holder).getCommentUpdated();
        final TextView comment_body = ((CommentViewHolder)holder).getCommentBody();

        final LinearLayout comment_like = ((CommentViewHolder)holder).getCommentLike();
        final TextView comment_likeCount = ((CommentViewHolder)holder).getCommentLikeCount();

        final LinearLayout comment_dislike = ((CommentViewHolder)holder).getCommentDislike();
        final TextView comment_dislikeCount = ((CommentViewHolder)holder).getCommentDislikeCount();

        final Boolean likeButtonStatus = ((CommentViewHolder)holder).getLikeButtonStatus();
        Boolean dislikeButtonStatus = ((CommentViewHolder)holder).getDislikeButtonStatus();

        LinearLayout comment_reply = ((CommentViewHolder)holder).getCommentReply();
        TextView comment_replyCount = ((CommentViewHolder)holder).getCommentReplyCount();

        ImageView comment_icon = ((CommentViewHolder)holder).getCommentIcon();
        final ImageView comment_like_icon = ((CommentViewHolder)holder).getCommentLikeIcon();
        final ImageView comment_dislike_icon = ((CommentViewHolder)holder).getCommentDislikeIcon();

        final LinearLayout comment_body_layout = ((CommentViewHolder)holder).getCommentBodyLayout();
        final LinearLayout comment_layout = ((CommentViewHolder)holder).getCommentLayout();

        LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();


        ParseObject userOb = commentOb.getParseObject("user");

        if(userOb.get("thumnail") == null){

            comment_photo.setImageResource(R.drawable.image_background);

        } else {

            Glide.with(context).load( userOb.getParseFile("thumnail").getUrl() ).into(comment_photo);

        }

        delete_button.setVisibility(View.GONE);

        if(userOb.get("name") == null){

            comment_name.setText(userOb.getString("username"));

        } else {

            comment_name.setText(userOb.getString("name"));

        }

        String  comment_type = "";

        if(commentOb.getString("type").equals("comment")){

            comment_type = "댓글";

        } else if (commentOb.getString("type").equals("post")){

            comment_type = "타임라인";

        } else if (commentOb.getString("type").equals("reply")){

            comment_type = "답글";

        }

        comment_updated.setText(commentOb.getUpdatedAt().toString());
        comment_body.setText( "[" + comment_type + "]" + commentOb.getString("body"));
        comment_likeCount.setText(String.valueOf(commentOb.getInt("like_count")));
        comment_dislikeCount.setText( String.valueOf(commentOb.getInt("dislike_count")) );

        String parent = commentOb.getString("parent");

        if(commentOb.getString("type").equals("reply")){

            comment_reply.setVisibility(View.GONE);

        } else {

            comment_reply.setVisibility(View.VISIBLE);

            comment_replyCount.setText( String.valueOf( commentOb.getInt("comment_count") ) );

        }

        comment_layout.setClickable(false);

        if(commentOb.getString("type").equals("comment") || commentOb.getString("type").equals("reply") ){

            ParseQuery contentQuery = ParseQuery.getQuery("Content");
            contentQuery.getInBackground(parent, new GetCallback<ParseObject>() {
                @Override
                public void done(final ParseObject contentOb, ParseException e) {

                    comment_layout.setClickable(true);

                    if(e==null){

                        comment_layout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                Log.d("type", commentOb.getString("type"));

                                if(commentOb.getString("type").equals("comment")){

                                    Intent intent = new Intent(context, CommentDetailActivity.class);
                                    intent.putExtra("commentId", commentOb.getObjectId());
                                    intent.putExtra("parentId", commentOb.getString("parent"));
                                    intent.putExtra("type", contentOb.getString("type"));
                                    context.startActivity(intent);

                                } else if(commentOb.getString("type").equals("reply")){

                                    Intent intent = new Intent(context, CommentDetailActivity.class);
                                    intent.putExtra("commentId", commentOb.getObjectId());
                                    intent.putExtra("parentId", commentOb.getString("parent"));
                                    intent.putExtra("type", contentOb.getString("type"));
                                    context.startActivity(intent);

                                }

                            }
                        });


                    } else {

                        e.printStackTrace();
                    }

                }
            });


        } else if (commentOb.getString("type").equals("post")){

            comment_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (commentOb.getString("type").equals("post")){

                        Intent intent = new Intent(context, PostingDetailActivity.class);
                        intent.putExtra("postId", commentOb.getString("parent"));
                        context.startActivity(intent);

                    }

                }
            });



        }

        functionBase.iconColorFilterInit(comment_icon, comment_like_icon, comment_dislike_icon);

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

        query.setLimit(10);

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
