package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.NotiAlertViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class NotiAlertAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private static ArrayList<ParseObject> items = new ArrayList<>();
    private static ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private static List<List<ParseObject>> objectPages = new ArrayList<>();;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private static RequestManager requestManager;
    protected static Context context;
    protected static ParseUser currentUser;
    private static Date laskCheckTime;
    private FunctionBase functionBase;


    public NotiAlertAdapter(Context context, RequestManager requestManager, Date laskCheckTime) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.laskCheckTime = laskCheckTime;
        this.functionBase = new FunctionBase(context);

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("MyAlert");
                query.whereEqualTo("to", currentUser);
                query.whereNotEqualTo("from", currentUser);
                query.whereEqualTo("status", true);
                query.include("from");
                query.include("to");
                query.include("artist_post");
                query.include("social");
                query.include("comment");
                query.include("pokeItem");
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final ParseObject timelineOb = getItem(viewType);

        View notiView;

        notiView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_noti, parent, false);

        return new NotiAlertViewHolder(notiView);



    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject notiOb = getItem(position);

        CircleImageView noti_image = ((NotiAlertViewHolder)holder).getNotiImage();
        TextView action = ((NotiAlertViewHolder)holder).getAction();
        TextView message = ((NotiAlertViewHolder)holder).getMessage();
        TextView noti_time = ((NotiAlertViewHolder)holder).getNotiTime();
        final LinearLayout noti_layout = ((NotiAlertViewHolder)holder).getNotiLayout();


        int defaultColor = Color.parseColor("#ffffff");
        int newColor = Color.parseColor("#ffffcc");

        switch (notiOb.getString("type")){

            case "like_post":

                Log.d("case2", "like_post");

                ParseObject sendUser = notiOb.getParseObject("from");

                functionBase.profileImageLoading(noti_image, sendUser, requestManager);

                String senderName = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    senderName = notiOb.getParseObject("from").getString("name");

                } else {

                    senderName = notiOb.getParseObject("from").getString("username");


                }

                action.setText("[좋아요] " +senderName + "님이 좋아요를 눌렀습니다.");



                if(notiOb.get("artist_post") != null){

                    final ParseObject postOb = notiOb.getParseObject("artist_post");


                    if(postOb.get("body")!= null){

                        message.setText( notiOb.getParseObject("artist_post").getString("body") );

                    } else {

                        message.setText( "메시지가 존재하지 않습니다." );

                    }

                    final String postId = postOb.getObjectId();
                    final String post_type = postOb.getString("type");

                    functionBase.chargeFollowPatronCheck( postOb, noti_layout );

                } else {

                    message.setText( "메시지가 존재하지 않습니다." );

                }

                noti_time.setText(notiOb.getCreatedAt().toString());

                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }

                break;


            case "like_comment":

                ParseObject sendUser2 = notiOb.getParseObject("from");

                functionBase.profileImageLoading(noti_image, sendUser2, requestManager);

                String senderNameCommentLike = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    senderNameCommentLike = notiOb.getParseObject("from").getString("name");

                } else {

                    senderNameCommentLike = notiOb.getParseObject("from").getString("username");


                }

                action.setText("[좋아요] " +senderNameCommentLike + "님이 좋아요를 눌렀습니다.");


                if(notiOb.get("comment") != null){

                    ParseObject commentOb = notiOb.getParseObject("comment");
                    ParseObject postOb = notiOb.getParseObject("artist_post");

                    if(commentOb.get("body")!= null){

                        message.setText( commentOb.getString("body") );

                    } else {

                        message.setText( "메시지가 존재하지 않습니다." );

                    }

                    if(postOb != null){

                        functionBase.chargeFollowPatronCheck(  postOb, noti_layout );
                    }


                } else {

                    message.setText( "메시지가 존재하지 않습니다." );

                }

                noti_time.setText(notiOb.getCreatedAt().toString());


                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }


                break;

            case "comment_comment":

                ParseObject sendUser20 = notiOb.getParseObject("from");

                functionBase.profileImageLoading(noti_image, sendUser20, requestManager);

                String senderNameCommentComment = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    senderNameCommentLike = notiOb.getParseObject("from").getString("name");

                } else {

                    senderNameCommentLike = notiOb.getParseObject("from").getString("username");


                }

                action.setText("[답글] " +senderNameCommentLike + "님이 댓글에 답글을 남겼습니다.");


                if(notiOb.get("comment") != null){

                    final ParseObject commentOb = notiOb.getParseObject("comment");
                    ParseObject replyOb = notiOb.getParseObject("reply");
                    ParseObject pokeItem = replyOb.getParseObject("poke_item");

                    if(pokeItem == null){

                        if(commentOb.get("body")!= null){

                            message.setText( commentOb.getString("body") );

                        } else {

                            message.setText( "메시지가 존재하지 않습니다." );

                        }

                    } else {

                        message.setText("이미티콘을 남겼습니다.");

                    }



                    if(commentOb.get("parentOb") != null){

                        commentOb.getParseObject("parentOb").fetchInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(final ParseObject parentObject, ParseException e) {

                                if(e==null){

                                    noti_layout.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent intent = new Intent(context, CommentDetailActivity.class);
                                            intent.putExtra("commentId", commentOb.getObjectId());
                                            intent.putExtra("postId", parentObject.getObjectId());
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            context.startActivity(intent);

                                        }
                                    });


                                } else {

                                    e.printStackTrace();
                                }

                            }
                        });

                    };


                } else {

                    message.setText( "메시지가 존재하지 않습니다." );

                }

                noti_time.setText(notiOb.getCreatedAt().toString());


                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }




                break;


            case "comment_post":

                Log.d("case5", "comment_post");

                ParseObject sendUser3 = notiOb.getParseObject("from");

                functionBase.profileImageLoading(noti_image, sendUser3, requestManager);

                String senderNameComment = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    senderNameComment = notiOb.getParseObject("from").getString("name");

                } else {

                    senderNameComment = notiOb.getParseObject("from").getString("username");


                }

                action.setText("[댓글] " +senderNameComment + "님이 댓글을 남겼습니다.");


                if(notiOb.get("comment") != null){

                    ParseObject commentOb = notiOb.getParseObject("comment");
                    ParseObject pokeItem = commentOb.getParseObject("poke_item");

                    if(pokeItem == null){

                        if(commentOb.get("body")!= null){

                            message.setText( notiOb.getParseObject("comment").getString("body") );

                        } else {

                            message.setText( "메시지가 존재하지 않습니다." );

                        }

                    } else {

                        message.setText( "이모티콘을 남겼습니다." );

                    }

                if(notiOb.get("artist_post") != null){

                    ParseObject postOb = notiOb.getParseObject("artist_post");
                    functionBase.chargeFollowPatronCheck( postOb, noti_layout);

                }



                } else {

                    message.setText( "메시지가 존재하지 않습니다." );

                }

                noti_time.setText(notiOb.getCreatedAt().toString());


                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }


                break;


            case "social_request":

                ParseObject sendUser4 = notiOb.getParseObject("from");

                functionBase.profileImageLoading(noti_image, sendUser4, requestManager);

                String senderRequestComment = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    senderNameComment = notiOb.getParseObject("from").getString("name");

                } else {

                    senderNameComment = notiOb.getParseObject("from").getString("username");


                }

                action.setText("[제작요청] " +senderNameComment + "님이 제작요청을 했어요.");


                if(notiOb.get("social") != null){

                    ParseObject socialMsgOb = notiOb.getParseObject("social");
                    ParseObject postOb = socialMsgOb.getParseObject("artist_post");

                    if(socialMsgOb.get("body")!= null){

                        message.setText( socialMsgOb.getString("body") );

                    } else {

                        message.setText( "메시지가 존재하지 않습니다." );

                    }

                    functionBase.chargeFollowPatronCheck( postOb, noti_layout);


                } else {

                    message.setText( "메시지가 존재하지 않습니다." );

                }

                noti_time.setText(notiOb.getCreatedAt().toString());


                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }

                break;

            case "social_poke":

                ParseObject sendUser5 = notiOb.getParseObject("from");

                functionBase.profileImageLoading(noti_image, sendUser5, requestManager);

                String senderPokeComment = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    senderNameComment = notiOb.getParseObject("from").getString("name");

                } else {

                    senderNameComment = notiOb.getParseObject("from").getString("username");

                }


                if(notiOb.get("social") != null){

                    ParseObject socialOb = notiOb.getParseObject("social");

                    try {

                        ParseObject fetchedPokeOb = socialOb.getParseObject("pokeItem").fetch();
                        String behavior = fetchedPokeOb.getString("action");

                        action.setText("[찌르기] " +senderNameComment + "님이 " + behavior);

                        noti_time.setText(notiOb.getCreatedAt().toString());


                    } catch (ParseException e) {

                        action.setText("[찌르기] " +senderNameComment + "님이 소통하기를 하셨습니다.");
                        e.printStackTrace();
                    }

                    message.setText( "" );

                } else {

                    message.setText( "메시지가 존재하지 않습니다." );

                }

                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }





                break;

            case "social_poke_response":

                ParseObject sendUser6 = notiOb.getParseObject("from");

                functionBase.profileImageLoading(noti_image, sendUser6, requestManager);

                String senderPokeReponseComment = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    senderNameComment = notiOb.getParseObject("from").getString("name");

                } else {

                    senderNameComment = notiOb.getParseObject("from").getString("username");


                }

                if(notiOb.get("social") != null){

                    ParseObject socialOb = notiOb.getParseObject("social");

                    try {

                        ParseObject fetchedPokeOb = socialOb.getParseObject("pokeItem").fetch();
                        String behavior = fetchedPokeOb.getString("action");

                        action.setText("[찌르기] " +senderNameComment + "님이 " + behavior);

                        noti_time.setText(notiOb.getCreatedAt().toString());


                    } catch (ParseException e) {

                        action.setText("[찌르기] " +senderNameComment + "님이 소통하기를 하셨습니다.");
                        e.printStackTrace();
                    }

                    message.setText( "" );

                } else {

                    message.setText( "메시지가 존재하지 않습니다." );

                }



                noti_time.setText(notiOb.getCreatedAt().toString());


                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }

                break;

            case "purchase":

                ParseObject purchaseUser = notiOb.getParseObject("from");

                functionBase.profileImageLoading(noti_image, purchaseUser, requestManager);

                String purchaseName = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    purchaseName = notiOb.getParseObject("from").getString("name");

                } else {

                    purchaseName = notiOb.getParseObject("from").getString("username");


                }

                if(notiOb.get("artist_post") != null){

                    final ParseObject postOb = notiOb.getParseObject("artist_post");

                    action.setText("[구매] " + purchaseName + "님이" + postOb.getString("title") +"을 구매했습니다." );


                    if(postOb.get("body")!= null){

                        message.setText( notiOb.getParseObject("artist_post").getString("body") );

                    } else {

                        message.setText( "메시지가 존재하지 않습니다." );

                    }

                    final String postId = postOb.getObjectId();
                    final String post_type = postOb.getString("type");

                    functionBase.chargeFollowPatronCheck( postOb, noti_layout );

                } else {

                    message.setText( "메시지가 존재하지 않습니다." );

                }

                noti_time.setText(notiOb.getCreatedAt().toString());

                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }

                break;


            case "follow":

                ParseObject followUser = notiOb.getParseObject("from");
                ParseObject toUser = notiOb.getParseObject("to");

                functionBase.profileImageLoading(noti_image, followUser, requestManager);

                String followName = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    followName = notiOb.getParseObject("from").getString("name");

                } else {

                    followName = notiOb.getParseObject("from").getString("username");


                }

                action.setText("[팔로우] " + followName + "님이" + toUser.getString("name") +"님을 팔로우 했습니다." );
                message.setText( "" );

                noti_time.setText(notiOb.getCreatedAt().toString());

                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }


                break;

            case "patron":

                ParseObject patronUser = notiOb.getParseObject("from");
                ParseObject toPatronUser = notiOb.getParseObject("to");
                ParseObject postOb = notiOb.getParseObject("artist_post");

                functionBase.profileImageLoading(noti_image, patronUser, requestManager);

                String patronName = "";

                if(notiOb.getParseObject("from").get("name") != null){

                    patronName = notiOb.getParseObject("from").getString("name");

                } else {

                    patronName = notiOb.getParseObject("from").getString("username");


                }

                action.setText("[후원받음] " + patronName + "님이" + toPatronUser.getString("name") +"님에게 후원 했습니다." );

                message.setText( String.valueOf(notiOb.getInt("price")) + "BOX 후원 받았습니다." );

                noti_time.setText(notiOb.getCreatedAt().toString());

                functionBase.chargeFollowPatronCheck( postOb, noti_layout );

                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }


                break;

            case "share":

                ParseObject from = notiOb.getParseObject("from");
                ParseObject to = notiOb.getParseObject("to");
                ParseObject postObject = notiOb.getParseObject("artist_post");

                functionBase.profileImageLoading(noti_image, from, requestManager);
                action.setText( "[공유]" + from.getString("name") + "님이 공유 했습니다." );
                message.setText( postObject.getString("body") );
                functionBase.chargeFollowPatronCheck(postObject, noti_layout);
                noti_time.setText(notiOb.getCreatedAt().toString());

                if(laskCheckTime.before(notiOb.getCreatedAt())){

                    noti_layout.setBackgroundColor(newColor);

                } else {

                    noti_layout.setBackgroundColor(defaultColor);

                }

                break;


        }

        //기능 추가

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
        return items.get(position);
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
