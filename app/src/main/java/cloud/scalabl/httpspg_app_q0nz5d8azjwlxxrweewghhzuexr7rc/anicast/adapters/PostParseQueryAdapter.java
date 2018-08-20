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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostingDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.PostViewHolder;


/**
 * Created by ssamkyu on 2017. 3. 14..
 */

public class PostParseQueryAdapter extends RecyclerParseAdapter {

    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    private List<List<ParseObject>> objectPages;
    private ArrayList<ParseObject> items;
    private int currentPage;

    private String parentId;
    private Boolean commentFlag;
    private ParseObject channelOb;
    private FunctionBase functionBase;


    public PostParseQueryAdapter(Context context, final String orderBy, final Boolean ascending, final ParseObject queryOb) {

        super(context);
        this.channelOb = queryOb;

        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.objectPages = new ArrayList<>();
        this.items = new ArrayList<>();
        this.functionBase = new FunctionBase(context);



        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Post");
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.whereEqualTo("parent", queryOb);
                query.whereEqualTo("status", true);
                query.include("user");

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
        View postView;

        postView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_post, parent, false);


        return new PostViewHolder(postView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject postOb = getItem(position);

        final ParseImageView writter_photo = ((PostViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((PostViewHolder)holder).getWritterName();
        final TextView post_updated = ((PostViewHolder)holder).getPostUpdated();
        final TextView channel_owner = ((PostViewHolder)holder).getChannelOwner();

        final TextView post_body = ((PostViewHolder)holder).getPostBody();

        final LinearLayout post_comment_button = ((PostViewHolder)holder).getPostCommentButton();
        final LinearLayout post_like_button = ((PostViewHolder)holder).getPostLikeButton();
        final LinearLayout post_dislike_button = ((PostViewHolder)holder).getPostDislikeButton();

        final TextView comment_count = ((PostViewHolder)holder).getCommentCount();
        final TextView like_count = ((PostViewHolder)holder).getLikeCount();
        final TextView dislike_count = ((PostViewHolder)holder).getDislikeCount();

        final ImageView comment_icon = ((PostViewHolder)holder).getCommentIcon();
        final ImageView like_icon = ((PostViewHolder)holder).getLikeIcon();
        final ImageView dislike_icon = ((PostViewHolder)holder).getDislikeIcon();

        final TextView comment_text = ((PostViewHolder)holder).getCommentText();
        final TextView like_text = ((PostViewHolder)holder).getLikeText();
        final TextView dislike_text = ((PostViewHolder)holder).getDislikeText();

        ParseImageView post_image = ((PostViewHolder)holder).getPostImage();


        functionBase.postIconColorFilterInit(comment_icon, like_icon, dislike_icon , true);
        functionBase.postTextColorFilterInit(comment_text, like_text, dislike_text, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, dislike_count, true);



        if(channelOb.getParseObject("owner").getObjectId().equals(postOb.getParseObject("user").getObjectId())){

            channel_owner.setVisibility(View.VISIBLE);

        } else {

            channel_owner.setVisibility(View.GONE);

        }

        ParseObject userOb = postOb.getParseObject("user");

        if(userOb.get("thumnail") == null){

            writter_photo.setImageResource(R.drawable.image_background);

        } else {

            Glide.with(context).load(userOb.getParseFile("thumnail").getUrl()).into(writter_photo);

        }

        if(userOb.get("name") == null){

            writter_name.setText(userOb.getString("username"));

        } else {

            writter_name.setText(userOb.getString("name"));

        }

        post_updated.setText(postOb.getUpdatedAt().toString());
        post_body.setText(postOb.getString("body"));
        like_count.setText(String.valueOf(postOb.getInt("like_count")));
        dislike_count.setText( String.valueOf(postOb.getInt("dislike_count")) );
        comment_count.setText( String.valueOf(postOb.getInt("comment_count")) );

        if(currentUser!=null){

            ParseQuery postLikeQuery = postOb.getRelation("like").getQuery();
            postLikeQuery.whereEqualTo("objectId", currentUser.getObjectId());
            postLikeQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null){

                        if(objects.size() == 0){

                            like_count.setTextColor(functionBase.likePostColor);
                            like_text.setTextColor(functionBase.likePostColor);
                            like_icon.setColorFilter(functionBase.likePostColor);

                        } else {

                            like_count.setTextColor(functionBase.likedPostColor);
                            like_text.setTextColor(functionBase.likedPostColor);
                            like_icon.setColorFilter(functionBase.likedPostColor);

                        }

                    } else {

                        e.printStackTrace();

                    }


                }

            });

            ParseQuery postDislikeQuery = postOb.getRelation("dislike").getQuery();
            postDislikeQuery.whereEqualTo("objectId", currentUser.getObjectId());
            postDislikeQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null){

                        if(objects.size() == 0){

                            dislike_count.setTextColor(functionBase.likePostColor);
                            dislike_text.setTextColor(functionBase.likePostColor);
                            dislike_icon.setColorFilter(functionBase.likePostColor);

                        } else {

                            dislike_count.setTextColor(functionBase.likedPostColor);
                            dislike_text.setTextColor(functionBase.likedPostColor);
                            dislike_icon.setColorFilter(functionBase.likedPostColor);

                        }

                    } else {

                        e.printStackTrace();

                    }


                }

            });

        }



        post_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context, PostingDetailActivity.class);
                intent.putExtra("postId", postOb.getObjectId());
                context.startActivity(intent);


            }
        });

        post_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PostingDetailActivity.class);
                intent.putExtra("postId", postOb.getObjectId());
                context.startActivity(intent);

            }
        });



        post_like_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(currentUser != null){

                    post_like_button.setClickable(false);

                    ParseQuery likeQuery = postOb.getRelation("like").getQuery();
                    likeQuery.whereEqualTo("objectId", currentUser.getObjectId());
                    likeQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> likeObs, ParseException e) {

                            if(e==null){

                                int count = likeObs.size();

                                if(count == 0){


                                    ParseRelation postLikeRelation = postOb.getRelation("like");
                                    postLikeRelation.add(currentUser);
                                    postOb.increment("like_count", 1);
                                    postOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                if(e==null){

                                                    ParseObject channelOb = postOb.getParseObject("parent");
                                                    channelOb.increment("like_count", 1);
                                                    channelOb.saveInBackground();

                                                    Toast.makeText(context, "좋아요 선택", Toast.LENGTH_SHORT).show();
                                                    like_count.setText(String.valueOf( postOb.getInt("like_count") ));
                                                    post_like_button.setClickable(true);

                                                    like_count.setTextColor(functionBase.likedPostColor);
                                                    like_text.setTextColor(functionBase.likedPostColor);
                                                    like_icon.setColorFilter(functionBase.likedPostColor);

                                                    ParseQuery notiQuery = ParseQuery.getQuery("Noti");
                                                    notiQuery.whereEqualTo("sender", currentUser);
                                                    notiQuery.whereEqualTo("post", postOb);
                                                    notiQuery.findInBackground(new FindCallback<ParseObject>() {
                                                        @Override
                                                        public void done(List<ParseObject> objects, ParseException e) {

                                                            if(objects.size() == 0){

                                                                ParseObject notiOb = new ParseObject("Noti");
                                                                notiOb.put("sender", currentUser);
                                                                notiOb.put("to", postOb.getParseObject("user"));
                                                                notiOb.put("post",postOb );
                                                                notiOb.put("from", "post_like");
                                                                notiOb.put("status", true );
                                                                notiOb.saveInBackground();

                                                            }

                                                        }

                                                    });





                                                } else {

                                                    Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                    post_like_button.setClickable(true);
                                                    e.printStackTrace();

                                                }


                                            } else {

                                                Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                post_like_button.setClickable(true);
                                                e.printStackTrace();

                                            }


                                        }
                                    });

                                } else {

                                    ParseRelation postLikeRelation = postOb.getRelation("like");
                                    postLikeRelation.remove(currentUser);
                                    postOb.increment("like_count", -1);
                                    postOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                if(e==null){

                                                    ParseObject channelOb = postOb.getParseObject("parent");
                                                    channelOb.increment("like_count", -1);
                                                    channelOb.saveInBackground();

                                                    Toast.makeText(context, "좋아요 취소", Toast.LENGTH_SHORT).show();
                                                    like_count.setText(String.valueOf( postOb.getInt("like_count") ));
                                                    post_like_button.setClickable(true);

                                                    like_count.setTextColor(functionBase.likePostColor);
                                                    like_text.setTextColor(functionBase.likePostColor);
                                                    like_icon.setColorFilter(functionBase.likePostColor);



                                                } else {

                                                    Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                    post_like_button.setClickable(true);
                                                    e.printStackTrace();

                                                }


                                            } else {

                                                Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                post_like_button.setClickable(true);
                                                e.printStackTrace();

                                            }


                                        }
                                    });


                                }

                            } else {

                                e.printStackTrace();
                            }


                        }
                    });

                } else {

                    TastyToast.makeText(context, "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

            }
        });

        post_dislike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser != null){

                    post_dislike_button.setClickable(false);

                    ParseQuery likeQuery = postOb.getRelation("dislike").getQuery();
                    likeQuery.whereEqualTo("objectId", currentUser.getObjectId());
                    likeQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> dislikeObs, ParseException e) {

                            if(e==null){

                                int count = dislikeObs.size();

                                if(count == 0){


                                    ParseRelation postDislikeRelation = postOb.getRelation("dislike");
                                    postDislikeRelation.add(currentUser);
                                    postOb.increment("dislike_count", 1);
                                    postOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                if(e==null){

                                                    ParseObject channelOb = postOb.getParseObject("parent");
                                                    channelOb.increment("dislike_count", 1);
                                                    channelOb.saveInBackground();

                                                    Toast.makeText(context, "좋아요 선택", Toast.LENGTH_SHORT).show();
                                                    dislike_count.setText(String.valueOf( postOb.getInt("dislike_count") ));
                                                    post_dislike_button.setClickable(true);

                                                    dislike_count.setTextColor(functionBase.likedPostColor);
                                                    dislike_text.setTextColor(functionBase.likedPostColor);
                                                    dislike_icon.setColorFilter(functionBase.likedPostColor);

                                                } else {

                                                    Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                    post_dislike_button.setClickable(true);
                                                    e.printStackTrace();

                                                }


                                            } else {

                                                Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                post_dislike_button.setClickable(true);
                                                e.printStackTrace();

                                            }


                                        }
                                    });

                                } else {

                                    ParseRelation postLikeRelation = postOb.getRelation("dislike");
                                    postLikeRelation.remove(currentUser);
                                    postOb.increment("dislike_count", -1);
                                    postOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                if(e==null){

                                                    ParseObject channelOb = postOb.getParseObject("parent");
                                                    channelOb.increment("dislike_count", 1);
                                                    channelOb.saveInBackground();

                                                    Toast.makeText(context, "좋아요 취소", Toast.LENGTH_SHORT).show();
                                                    dislike_count.setText(String.valueOf( postOb.getInt("like_count") ));
                                                    post_dislike_button.setClickable(true);

                                                    dislike_count.setTextColor(functionBase.likePostColor);
                                                    dislike_text.setTextColor(functionBase.likePostColor);
                                                    dislike_icon.setColorFilter(functionBase.likePostColor);

                                                } else {

                                                    Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                    post_dislike_button.setClickable(true);
                                                    e.printStackTrace();

                                                }


                                            } else {

                                                Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                                post_dislike_button.setClickable(true);
                                                e.printStackTrace();

                                            }


                                        }
                                    });


                                }

                            } else {

                                e.printStackTrace();
                            }


                        }
                    });

                } else {

                    TastyToast.makeText(context, "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }


            }
        });






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
