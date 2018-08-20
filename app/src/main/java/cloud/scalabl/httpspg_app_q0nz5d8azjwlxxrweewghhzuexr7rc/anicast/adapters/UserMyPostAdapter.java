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
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CheerActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentSocialActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.DMChatActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PokeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PokeResponseActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.RequestActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionFollow;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SocialViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemShareViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.UserHeaderViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class UserMyPostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    private ParseObject userOb;
    private FunctionBase functionBase;


    public UserMyPostAdapter(Context context, RequestManager requestManager, final ParseObject userOb) {

        Log.d("adapter", "started");
        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.userOb = userOb;
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
                query.whereEqualTo("userId", userOb.getObjectId());
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

        if(viewType == 0){

            View headerView;
            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user_header_renew, parent, false);

            return new UserHeaderViewHolder(headerView);

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

        if(position == 0){

            CircleImageView writter_photo = ((UserHeaderViewHolder)holder).getWritterPhoto();
            TextView writter_name = ((UserHeaderViewHolder)holder).getWritterName();
            TextView post_count = ((UserHeaderViewHolder)holder).getPostCount();
            TextView follower_count = ((UserHeaderViewHolder)holder).getFollowerCount();
            TextView following_count = ((UserHeaderViewHolder)holder).getFollowingCount();
            TextView creator_talk = ((UserHeaderViewHolder)holder).getCreatorTalk();
            final LinearLayout follow_button = ((UserHeaderViewHolder)holder).getFollowButton();
            final TextView follow_text = ((UserHeaderViewHolder)holder).getFollowText();
            //LinearLayout request_button = ((UserHeaderViewHolder)holder).getRequestButton();
            //LinearLayout cheer_button = ((UserHeaderViewHolder)holder).getCheerButton();
            //LinearLayout poke_button= ((UserHeaderViewHolder)holder).getPokeButton();
            TextView poke_text= ((UserHeaderViewHolder)holder).getPokeText();
            LinearLayout social_buttons = ((UserHeaderViewHolder)holder).getSocialButtons();
            TextView writter_body = ((UserHeaderViewHolder)holder).getWritterBody();
            LinearLayout dm_button = ((UserHeaderViewHolder)holder).getDMButton();

            functionBase.profileImageLoading(writter_photo, userOb, requestManager, "UserMyPostAdapter");
            functionBase.profileNameLoading(writter_name, userOb);

            dm_button.setFocusableInTouchMode(true);
            dm_button.requestFocus();

            if(follow_button != null){

                follow_button.setClickable(false);

            }

            if(userOb.get("my_talk") != null){

                if(userOb.getString("my_talk").length() != 0){

                    writter_body.setText(userOb.getString("my_talk"));

                } else {

                    writter_body.setText("작가님 말씀을 입력해 주세요.");

                }

            } else {

                writter_body.setText("작가님 말씀을 입력해 주세요.");
            }



            post_count.setText(String.valueOf(userOb.getInt("post_count")));
            follower_count.setText(functionBase.makeComma( userOb.getInt("follow_count")));
            following_count.setText( functionBase.makeComma( userOb.getInt("follower_count") ) );

            follow_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentUser != null){

                        FunctionFollow functionFollow = new FunctionFollow(context);
                        functionFollow.followFunctionForUserActivity( follow_button, follow_text,  userOb);

                    } else {

                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }

                }
            });

            if(currentUser != null){

                if(currentUser.getObjectId().equals(userOb.getObjectId())){

                    social_buttons.setVisibility(View.GONE);

                } else {

                    if(functionBase.parseArrayCheck(currentUser, "follow_array", userOb.getObjectId())){

                        follow_text.setText("팔로우 취소");

                    } else {

                        follow_text.setText("팔로우");

                    };

                }

            }

            /*
            request_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentUser != null){

                        Intent intent = new Intent(context, RequestActivity.class);
                        intent.putExtra("userId", userOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    } else {

                        TastyToast.makeText(context, "로그인이 필요 합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }



                }
            });


            cheer_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentUser != null){

                        Intent intent = new Intent(context, CheerActivity.class);
                        intent.putExtra("userId", userOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);


                    } else {

                        TastyToast.makeText(context, "로그인이 필요 합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    }

                }
            });

            poke_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(currentUser != null){

                        Intent intent = new Intent(context, PokeActivity.class);
                        intent.putExtra("userId", userOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    } else {

                        TastyToast.makeText(context, "로그인이 필요 합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }



                }
            });
            */

            dm_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, DMChatActivity.class);
                    intent.putExtra("user", userOb.getObjectId());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

            if(currentUser != null){

                if(currentUser.getObjectId().equals(userOb.getObjectId())){

                    dm_button.setVisibility(View.GONE);

                }

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


            /*
            final ParseObject socialMSGOb = getItem(position);

            //cheer
            TextView username_cheer = ((SocialViewHolder)holder).getUsernameCheer();
            TextView username_cheer_2 = ((SocialViewHolder)holder).getUsernameCheer2();
            TextView body_cheer = ((SocialViewHolder)holder).getBodyCheer();
            TextView send_box_amount = ((SocialViewHolder)holder).getSendBoXAmount();
            CircleImageView user_image_cheer = ((SocialViewHolder)holder).getUserImageCheer();
            LinearLayout cheer_layout = ((SocialViewHolder)holder).getCheerLayout();
            final LinearLayout accept_cheer_button = ((SocialViewHolder)holder).getAcceptCheerButton();
            TextView accept_cheer_button_text = ((SocialViewHolder)holder).getAcceptCheerButtonText();
            LinearLayout comment_cheer_button = ((SocialViewHolder)holder).getCommentCheerButton();
            TextView comment_cheer_count = ((SocialViewHolder)holder).getCommentCheerCount();

            //creator_comment
            LinearLayout creator_comment_layout = ((SocialViewHolder)holder).getCreatorCommentLayout();
            TextView creator_comment_text = ((SocialViewHolder)holder).getCreatorCommentText();

            //poke
            TextView username_poke = ((SocialViewHolder)holder).getUsernamePoke();
            CircleImageView user_image_poke = ((SocialViewHolder)holder).getUserImagePoke();
            LinearLayout poke_layout = ((SocialViewHolder)holder).getPokeLayout();
            ImageView image_poke = ((SocialViewHolder)holder).getImagePoke();
            TextView action_poke = ((SocialViewHolder)holder).getActionPoke();
            LinearLayout poke_button = ((SocialViewHolder)holder).getPokeButton();
            TextView poke_button_text = ((SocialViewHolder)holder).getPokeButtonText();
            LinearLayout comment_poke_button = ((SocialViewHolder)holder).getCommentPokeButton();
            TextView comment_poke_count = ((SocialViewHolder)holder).getCommentPokeCount();

            //request
            TextView username_request = ((SocialViewHolder)holder).getUsernameRequest();
            CircleImageView user_image_request = ((SocialViewHolder)holder).getUserImageRequest();
            LinearLayout request_layout = ((SocialViewHolder)holder).getRequestLayout();
            ImageView image_request = ((SocialViewHolder)holder).getImageRequest();
            TextView title_request = ((SocialViewHolder)holder).getTitleRequest();
            TextView body_request = ((SocialViewHolder)holder).getBodyRequest();
            LinearLayout request_detail_button = ((SocialViewHolder)holder).getRequestDetailButton();
            LinearLayout request_accept_button = ((SocialViewHolder)holder).getRequestAcceptButton();
            LinearLayout comment_request_button = ((SocialViewHolder)holder).getCommentRequestButton();
            TextView comment_request_count = ((SocialViewHolder)holder).getCommentRequestCount();
            LinearLayout request_button_layout = ((SocialViewHolder)holder).getRequestButtonLayout();

            //message
            TextView username_message = ((SocialViewHolder)holder).getUsernameMessage();
            CircleImageView user_image_message = ((SocialViewHolder)holder).getUserImageMessage();
            LinearLayout message_layout = ((SocialViewHolder)holder).getMessageLayout();
            ImageView image_message = ((SocialViewHolder)holder).getImageMessage();
            TextView message_text = ((SocialViewHolder)holder).getMessageText();

            cheer_layout.setVisibility(View.GONE);
            creator_comment_layout.setVisibility(View.GONE);
            poke_layout.setVisibility(View.GONE);
            request_layout.setVisibility(View.GONE);
            message_layout.setVisibility(View.GONE);

            FunctionBase functionBase = new FunctionBase(context);





            if(socialMSGOb.getString("type").equals("request")){

                request_layout.setVisibility(View.VISIBLE);
                final ParseObject userOb = socialMSGOb.getParseObject("user");
                ParseObject postOb = socialMSGOb.getParseObject("artist_post");
                functionBase.profileImageLoading(user_image_request, userOb, requestManager);
                functionBase.profileNameLoading(username_request,userOb);
                functionBase.generalImageLoading(image_request, postOb, requestManager);

                title_request.setText( postOb.getString("title") );
                body_request.setText( postOb.getString("body") );

                request_detail_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });


                request_accept_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                    }
                });

                comment_request_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, CommentSocialActivity.class);
                        intent.putExtra("postId", socialMSGOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

                comment_request_count.setText(functionBase.makeComma(socialMSGOb.getInt("comment_count")));


            } else if(socialMSGOb.getString("type").equals("cheer")){

                cheer_layout.setVisibility(View.VISIBLE);

                final ParseObject userOb = socialMSGOb.getParseObject("user");
                functionBase.profileImageLoading(user_image_cheer, userOb, requestManager);
                functionBase.profileNameLoading(username_cheer,userOb);
                functionBase.profileNameLoading(username_cheer_2, userOb);

                comment_cheer_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, CommentSocialActivity.class);
                        intent.putExtra("postId", socialMSGOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

                comment_cheer_count.setText(functionBase.makeComma(socialMSGOb.getInt("comment_count")));



                if(socialMSGOb.get("message") != null){

                    body_cheer.setText( socialMSGOb.getString("message") );

                }

                if(socialMSGOb.get("cheer_point") != null){

                    ParseObject cheerPointOb = socialMSGOb.getParseObject("cheer_point");
                    send_box_amount.setText( functionBase.makeComma(cheerPointOb.getInt("point")) + " BOX" );

                    if(cheerPointOb.getString("progress").equals("send")){

                        accept_cheer_button_text.setText("받기");

                        accept_cheer_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                accept_cheer_button.setClickable(false);

                                HashMap<String, Object> params = new HashMap<>();

                                params.put("key", currentUser.getSessionToken());
                                params.put("target", socialMSGOb.getObjectId());

                                ParseCloud.callFunctionInBackground("cheer_point_accept", params, new FunctionCallback<Map<String, Object>>() {

                                    public void done(Map<String, Object> mapObject, ParseException e) {

                                        if (e == null) {

                                            Log.d("parse result", mapObject.toString());

                                            if(mapObject.get("status").toString().equals("true")){

                                                String cheerPointId = mapObject.get("cheerPoint").toString();

                                                ParseQuery cheerPointQuery = ParseQuery.getQuery("CheerPoint");
                                                cheerPointQuery.getInBackground(cheerPointId, new GetCallback<ParseObject>() {
                                                    @Override
                                                    public void done(ParseObject cheerPointOb, ParseException e) {

                                                        if(e==null){

                                                            ParseObject responseMSGOb = new ParseObject("SocialMessage");
                                                            responseMSGOb.put("user",currentUser);
                                                            responseMSGOb.put("target", userOb);
                                                            responseMSGOb.put("type", "cheer_accept");
                                                            responseMSGOb.put("cheer_point", cheerPointOb);
                                                            responseMSGOb.put("social_message", socialMSGOb );
                                                            responseMSGOb.put("status", true);
                                                            responseMSGOb.saveInBackground(new SaveCallback() {
                                                                @Override
                                                                public void done(ParseException e) {

                                                                    if(e==null){


                                                                        TastyToast.makeText(context, "후원 받기 성공", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                        loadObjects(0);

                                                                    } else {

                                                                        TastyToast.makeText(context, "후원 받기기 실패 했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                        accept_cheer_button.setClickable(true);
                                                                        e.printStackTrace();
                                                                    }

                                                                }
                                                            });

                                                        } else {

                                                            accept_cheer_button.setClickable(true);
                                                            e.printStackTrace();
                                                            TastyToast.makeText(context, "응원하기 포인트를 찾을수 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                        }

                                                    }

                                                });


                                            } else {

                                                accept_cheer_button.setClickable(true);
                                                TastyToast.makeText( context, "포인트 저장 중 에러가 발생했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                            }

                                        } else {
                                            Log.d("error", "error");
                                            e.printStackTrace();
                                        }
                                    }
                                });

                            }
                        });

                    } else {


                        accept_cheer_button_text.setText("완료");
                        accept_cheer_button.setClickable(false);

                    }




                }





            } else if(socialMSGOb.getString("type").equals("poke")){

                poke_layout.setVisibility(View.VISIBLE);

                if(socialMSGOb.get("pokeItem") != null){

                    ParseObject pokeOb = socialMSGOb.getParseObject("pokeItem");

                    if(pokeOb.get("image") != null){

                        requestManager
                                .load( pokeOb.getParseFile("image").getUrl() )
                                .into(image_poke);

                    } else if(pokeOb.get("image_cdn") != null){

                        requestManager
                                .load( functionBase.imageUrlBase100 + pokeOb.getString("image_cdn") )
                                .into( image_poke );

                    } else {

                        image_poke.setImageResource(R.drawable.image_background);

                    }

                    action_poke.setText( pokeOb.getString("action") );

                }

                if(socialMSGOb.get("user") != null){

                    final ParseObject userOb = socialMSGOb.getParseObject("user");

                    functionBase.profileImageLoading(user_image_poke, userOb, requestManager);
                    functionBase.profileNameLoading(username_poke, userOb);


                    if(currentUser != null){

                        poke_button.setVisibility(View.VISIBLE);

                        if(functionBase.pokeExist(currentUser.getJSONArray("poke_response"), socialMSGOb.getObjectId())){

                            poke_button_text.setText("찌르기 완료");
                            poke_button.setOnClickListener(null);

                        } else {

                            poke_button_text.setText("나도 찌르기");

                            poke_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(context, PokeResponseActivity.class);
                                    intent.putExtra("userId", userOb.getObjectId());
                                    intent.putExtra("socialMSGId", socialMSGOb.getObjectId());
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);

                                }
                            });

                        }

                    } else {

                        poke_button.setVisibility(View.GONE);

                    }

                }

                comment_poke_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, CommentSocialActivity.class);
                        intent.putExtra("postId", socialMSGOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

                comment_poke_count.setText(functionBase.makeComma(socialMSGOb.getInt("comment_count")));


            } else if(socialMSGOb.getString("type").equals("creator")){

                creator_comment_layout.setVisibility(View.VISIBLE);
                final ParseObject userOb = socialMSGOb.getParseObject("user");
                functionBase.profileImageLoading(user_image_request, userOb, requestManager);
                functionBase.profileNameLoading(username_request,userOb);

                if(socialMSGOb.getString("message") != null){

                    creator_comment_text.setText(socialMSGOb.getString("message"));

                } else {

                    creator_comment_text.setText("메시지 없음");

                }



            } else if(socialMSGOb.getString("type").equals("cheer_accept")){

                message_layout.setVisibility(View.VISIBLE);
                final ParseObject userOb = socialMSGOb.getParseObject("user");
                functionBase.profileImageLoading(user_image_message, userOb, requestManager);
                functionBase.profileNameLoading(username_message,userOb);
                message_text.setText("후원을 수락 했습니다.");





            } else {

                Log.d("type", socialMSGOb.getString("type"));

            }

            poke_button.setVisibility(View.GONE);
            accept_cheer_button.setVisibility(View.GONE);
            request_button_layout.setVisibility(View.GONE);

            //기능 추가


            */


        }

        //기능 추가

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return items.size()+1;
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
