package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommercialActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.GoodsFundingEditActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LikeUserListActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.NovelActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronManagerActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronUserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PostingDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchResultActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketTimelineImageAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentPatronAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentPostAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.UserPostAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.PostViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemShareViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelinePatronViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 5. 30..
 */

public class FunctionPost {

    private Context context;
    private ParseUser currentUser;

    private FunctionBase functionBase;
    private AppCompatActivity activity;

    public FunctionPost(Context context) {

        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = new FunctionBase(context);

    }


    public FunctionPost(AppCompatActivity activity, Context context) {

        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = new FunctionBase(context);
        this.activity = activity;

    }


    public void PostAdapterBuilder(final ParseObject postOb, final RecyclerView.ViewHolder holder, RequestManager requestManager){

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

        TextView share_text = null;

        functionBase.postIconColorFilterInit(comment_icon, like_icon, dislike_icon , true);
        functionBase.postTextColorFilterInit(comment_text, like_text, dislike_text, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, dislike_count,share_text, true);



        channel_owner.setVisibility(View.GONE);

        ParseObject userOb = postOb.getParseObject("user");

        if(userOb.get("thumnail") == null){

            writter_photo.setImageResource(R.drawable.ic_action_circle_profile);

        } else {

            functionBase.glideFunction(context, userOb.getParseFile("thumnail").getUrl(), writter_photo, requestManager);

        }

        if(userOb.get("name") == null){

            writter_name.setText(userOb.getString("username"));

        } else {

            writter_name.setText(userOb.getString("name"));

        }

        if(postOb.get("post_image") != null){

            functionBase.glideFunction(context, postOb.getString("post_image"), post_image, requestManager);

        }

        String postDate = functionBase.dateToString(postOb.getCreatedAt());

        post_updated.setText(postDate);
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


                functionBase.artistPostSetOnClickFunction(postOb , post_body);


            }
        });

        post_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent = new Intent(context, PostingDetailActivity.class);
                intent.putExtra("postId", postOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });




        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }

        post_dislike_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser != null){

                    //FunctionLikeDislike.DislikeButtonFunction(context,  postOb, currentUser, false, false, post_dislike_button, dislike_count,  dislike_icon);

                } else {

                    TastyToast.makeText(context, "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }


            }
        });

    }


    public void TimelineArtistPostAdapterBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, String type){

        final CircleImageView writter_photo = ((TimelineItemViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelineItemViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemViewHolder)holder).getPostImageLayout();

        LinearLayout post_comment_button = ((TimelineItemViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemViewHolder)holder).getCommentIcon();
        final TextView comment_count = ((TimelineItemViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemViewHolder)holder).getRecordButton();
        ImageView record_icon = ((TimelineItemViewHolder)holder).getRecordIcon();
        TextView record_count = ((TimelineItemViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemViewHolder)holder).getUserInfoLayout();


        LinearLayout share_button = ((TimelineItemViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelineItemViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelineItemViewHolder)holder).getShareCount();

        LinearLayout post_tag_layout = ((TimelineItemViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((TimelineItemViewHolder)holder).getTagGroup();

        ImageView post_type_image = ((TimelineItemViewHolder)holder).getPostTypeImage();
        LinearLayout post_type_image_layout = ((TimelineItemViewHolder)holder).getPostTypeImageLayout();

        LinearLayout seriese_icon_layout = ((TimelineItemViewHolder)holder).getSerieseIconLayout();
        ImageView seriese_icon = ((TimelineItemViewHolder)holder).getSerieseIcon();
        TextView seriese_text = ((TimelineItemViewHolder)holder).getSerieseText();

        final LinearLayout option_button =((TimelineItemViewHolder)holder).getOptionButton();
        ImageView option_icon = ((TimelineItemViewHolder)holder).getOptionIcon();

        final TextView total_profit_share = ((TimelineItemViewHolder)holder).getTotalProfitShare();
        TextView dday_text = ((TimelineItemViewHolder)holder).getDdayText();
        TextView current_point = ((TimelineItemViewHolder)holder).getCurrentPoint();
        TextView max_point = ((TimelineItemViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelineItemViewHolder)holder).getProgressbar();
        TextView title = ((TimelineItemViewHolder)holder).getTitle();

        final LinearLayout delete_button = ((TimelineItemViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelineItemViewHolder)holder).getDeleteIcon();

        TextView post_detail = ((TimelineItemViewHolder)holder).getPostDetail();
        TextView last_post = ((TimelineItemViewHolder)holder).getLastPost();

        LinearLayout ad_badge_layout = ((TimelineItemViewHolder)holder).getAdBadgeLayout();


        final FunctionBase functionBase = new FunctionBase(context);

        //작성자 프로필 정보

        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, option_icon, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);

        final ParseObject userOb = postOb.getParseObject("user");

        if(ad_badge_layout != null){

            if(postOb.getBoolean("ad_flag")){

                ad_badge_layout.setVisibility(View.VISIBLE);

            } else {

                ad_badge_layout.setVisibility(View.GONE);
            }

        }



        if(post_detail != null){

            if(postOb.get("body") != null){

                post_detail.setText(postOb.getString("body"));

            } else {

                post_detail.setText("입력 안됨");

            }

        }

        if(last_post != null){


        }

        if(writter_name != null ){
            writter_name.setText("불러오는 중...");

            if(writter_photo != null){

                writter_photo.setImageResource(R.drawable.ic_action_circle_profile);

            }


            if(type.equals("recent") || type.equals("cm")){

                userOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedUserOb, ParseException e) {

                        functionBase.profileImageLoading(writter_photo, fetchedUserOb, requestManager);
                        functionBase.profileNameLoading(writter_name, fetchedUserOb);

                        if(total_profit_share != null){

                            total_profit_share.setText(functionBase.makeComma( fetchedUserOb.getInt("total_profit_share") ) + "P");

                        }


                    }
                });

            } else {

                functionBase.profileImageLoading(writter_photo, userOb, requestManager);
                functionBase.profileNameLoading(writter_name, userOb);

                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

                }


            }

            String postDate = functionBase.dateToString(postOb.getCreatedAt());

            if(post_updated != null){

                post_updated.setText(postDate);

            }


        }



        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    postOb.put("status", false);
                    postOb.put("lastAction", "delete");
                    postOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){



                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }
            });

        }




        if(dday_text != null){

            //dday maker
            Date tagetDate = postOb.getDate("endDate");
            String progressStatus = postOb.getString("progress");



            int dday = functionBase.dday(tagetDate.getYear() + 1900, tagetDate.getMonth() , tagetDate.getDate());

            if(progressStatus != null){

                if(progressStatus.equals("start")){

                    if(dday < 0){

                        dday_text.setText("마감");

                    } else {

                        dday_text.setText(String.valueOf(dday));

                    }



                } else if(progressStatus.equals("production")) {

                    dday_text.setText("제작중");

                } else{

                    dday_text.setText("종료");

                }

            }

        }

        if(title != null){

            title.setText(postOb.getString("title"));

        }

        if(max_point != null){

            max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " BOX" );
            current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " BOX");

            int currentProgress = functionBase.progressMaker(postOb.getInt("achieve_amount"), postOb.getInt("target_amount"));

            Log.d("currentProgress", String.valueOf(currentProgress));

            if(currentProgress >100){

                currentProgress = 100;

            }

            progressbar.setProgress(currentProgress);

        }


        if(type.equals("user")){

            if(user_info_layout != null){

                user_info_layout.setVisibility(View.GONE);

            }

        }

        if(post_body != null){

            if(postOb.get("body") != null){

                post_body.setText(postOb.getString("body"));
            }

        }



        if(like_count != null){

            like_count.setText(String.valueOf(postOb.getInt("like_count")));
        }

        if(comment_count != null){

            comment_count.setText( functionBase.makeComma(postOb.getInt("comment_count")) );

        }


        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);


                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tagGroup.setTags(tagArray);

                tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }

        if(postOb.get("post_type") != null){

            functionBase.chargeFollowPatronCheck( postOb, post_image);
            functionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);

            if(type.equals("small")){

                functionBase.generalImageLoading(post_image, postOb, requestManager, "250");

            } else {

                functionBase.generalImageLoading(post_image, postOb, requestManager);

            }



            if(postOb.getString("post_type").equals("youtube")){

                post_body.setText(postOb.getString("title"));

            } else if(postOb.getString("post_type").equals("novel")){

                String bodyString = "";

                if(postOb.getString("progress").equals("start")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필을 시작했습니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("open")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "'이 오픈 됐습니다. 많은 관심 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("finish")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필이 완료되었습니다. 많은 구독 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                }

                post_body.setText(bodyString);

            } else if(postOb.getString("post_type").equals("seriese")){

                String bodyString = "";

                if(postOb.getInt("seriese_count")==0){

                    bodyString += "[시리즈] '" + postOb.getString("title") + "' 연재 준비를 시작했습니다. \n";
                    bodyString += postOb.getString("body");

                } else {

                    bodyString += "[시리즈] '" + postOb.getString("title") + "'연재가 시작되었습니다. 많은 사랑 부탁 드립니다. \n";
                    bodyString += postOb.getString("body");

                }

                if(post_body != null){

                    post_body.setText(bodyString);
                }




            } else if(postOb.getString("post_type").equals("event")){

                if(writter_name != null){

                    writter_name.setText("이벤트의 요정");

                }


                functionBase.generalImageLoading(post_image, postOb, requestManager);
                post_body.setText(postOb.getString("body"));


            } else if(postOb.getString("post_type").equals("notice")){

                if(writter_name != null){

                    writter_name.setText("공지사항의 요정");

                }

                if(writter_photo != null){

                    requestManager
                            .load(R.drawable.icon_notice_profile)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .transition(new DrawableTransitionOptions().crossFade())
                            .into(writter_photo);
                }

                if(post_image != null){

                    requestManager
                            .load(R.drawable.icon_notice_banner)
                            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                            .transition(new DrawableTransitionOptions().crossFade())
                            .into(post_image);
                }

                if(post_body != null){

                    post_body.setText(postOb.getString("body"));
                }





            }

        } else {

            Log.d("objectId", postOb.getObjectId() );

        }

        if(post_body != null){

            post_body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    functionBase.artistPostSetOnClickFunction(postOb , post_body);


                }
            });

        }


        if(post_comment_button != null){



            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }

        if(share_button != null){

            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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
                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }

        }


        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }




        if(option_button != null){

            option_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    functionBase.OptionButtonFunction(context, option_button, currentUser, postOb);

                }
            });


        }

    }


    public void TimelineArtistMainPostAdapterBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, String type, final FunctionBase functionBase){

        final CircleImageView writter_photo = ((TimelineItemViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelineItemViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemViewHolder)holder).getPostImageLayout();

        LinearLayout post_comment_button = ((TimelineItemViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemViewHolder)holder).getCommentIcon();
        final TextView comment_count = ((TimelineItemViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemViewHolder)holder).getRecordButton();
        ImageView record_icon = ((TimelineItemViewHolder)holder).getRecordIcon();
        TextView record_count = ((TimelineItemViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemViewHolder)holder).getUserInfoLayout();


        LinearLayout share_button = ((TimelineItemViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelineItemViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelineItemViewHolder)holder).getShareCount();

        LinearLayout post_tag_layout = ((TimelineItemViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((TimelineItemViewHolder)holder).getTagGroup();

        ImageView post_type_image = ((TimelineItemViewHolder)holder).getPostTypeImage();
        LinearLayout post_type_image_layout = ((TimelineItemViewHolder)holder).getPostTypeImageLayout();

        LinearLayout seriese_icon_layout = ((TimelineItemViewHolder)holder).getSerieseIconLayout();
        ImageView seriese_icon = ((TimelineItemViewHolder)holder).getSerieseIcon();
        TextView seriese_text = ((TimelineItemViewHolder)holder).getSerieseText();

        final LinearLayout option_button =((TimelineItemViewHolder)holder).getOptionButton();
        ImageView option_icon = ((TimelineItemViewHolder)holder).getOptionIcon();

        final TextView total_profit_share = ((TimelineItemViewHolder)holder).getTotalProfitShare();
        TextView dday_text = ((TimelineItemViewHolder)holder).getDdayText();
        TextView current_point = ((TimelineItemViewHolder)holder).getCurrentPoint();
        TextView max_point = ((TimelineItemViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelineItemViewHolder)holder).getProgressbar();
        TextView title = ((TimelineItemViewHolder)holder).getTitle();

        final LinearLayout delete_button = ((TimelineItemViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelineItemViewHolder)holder).getDeleteIcon();

        TextView post_detail = ((TimelineItemViewHolder)holder).getPostDetail();
        TextView last_post = ((TimelineItemViewHolder)holder).getLastPost();
        LinearLayout ad_badge_layout = ((TimelineItemViewHolder)holder).getAdBadgeLayout();

        RecyclerView image_list = ((TimelineItemViewHolder)holder).getImageList();

        final TextView pv_count = ((TimelineItemViewHolder)holder).getPvCount();

        //작성자 프로필 정보

        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, option_icon, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);

        final ParseObject userOb = postOb.getParseObject("user");



        if(ad_badge_layout != null){

            if(postOb.getBoolean("ad_flag")){

                ad_badge_layout.setVisibility(View.VISIBLE);

            } else {

                ad_badge_layout.setVisibility(View.GONE);
            }

        }

        if(post_detail != null){

            if(postOb.get("body") != null){

                post_detail.setText(postOb.getString("body"));

            } else {

                post_detail.setText("입력 안됨");

            }

        }

        if(last_post != null){


        }

        if(writter_name != null ){

            writter_name.setText("불러오는 중...");

            if(writter_photo != null){

                writter_photo.setImageResource(R.drawable.ic_action_circle_profile);

            }


            if(type.equals("recent") || type.equals("cm")){

                userOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedUserOb, ParseException e) {

                        functionBase.profileImageLoading(writter_photo, fetchedUserOb, requestManager);
                        functionBase.profileNameLoading(writter_name, fetchedUserOb);

                        if(total_profit_share != null){

                            total_profit_share.setText(functionBase.makeComma( fetchedUserOb.getInt("total_profit_share") ) + "P");

                        }


                    }
                });

            } else {

                functionBase.profileImageLoading(writter_photo, userOb, requestManager);
                functionBase.profileNameLoading(writter_name, userOb);

                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

                }


            }

            String postDate = functionBase.dateToString(postOb.getCreatedAt());

            if(post_updated != null){

                post_updated.setText(postDate);

            }


        }



        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    postOb.put("status", false);
                    postOb.put("lastAction", "delete");
                    postOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){



                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }
            });

        }




        if(dday_text != null){

            //dday maker
            Date tagetDate = postOb.getDate("endDate");
            String progressStatus = postOb.getString("progress");



            int dday = functionBase.dday(tagetDate.getYear() + 1900, tagetDate.getMonth() , tagetDate.getDate());

            if(progressStatus != null){

                if(progressStatus.equals("start")){

                    if(dday < 0){

                        dday_text.setText("마감");

                    } else {

                        dday_text.setText(String.valueOf(dday));

                    }



                } else if(progressStatus.equals("production")) {

                    dday_text.setText("제작중");

                } else{

                    dday_text.setText("종료");

                }

            }

        }

        if(title != null){

            title.setText(postOb.getString("title"));

        }




        if(type.equals("user")){

            if(user_info_layout != null){

                user_info_layout.setVisibility(View.GONE);

            }

        }

        if(post_body != null){

            if(postOb.get("body") != null){

                post_body.setText(postOb.getString("body"));
            }

        }

        postOb.fetchInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e==null){

                    if(like_count != null){

                        like_count.setText(String.valueOf( object.getInt("like_count") ));
                    }

                    if(comment_count != null){

                        comment_count.setText( functionBase.makeComma( object.getInt("comment_count")) );

                    }

                    if(pv_count != null){

                        pv_count.setText( functionBase.makeComma(object.getInt("pv_count")) );
                    }

                } else {

                    e.printStackTrace();
                }

            }
        });



        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);


                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tagGroup.setTags(tagArray);

                tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }

        if(postOb.get("post_type") != null){

            if(postOb.getString("post_type").equals("patron") && postOb.getString("content_type").equals("goods")){

                functionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);

                //recycler list

                Log.d("postId", postOb.getObjectId());

                ParseObject itemOb = postOb.getParseObject("item");

                if(itemOb != null) {

                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
                    image_list.setLayoutManager(layoutManager);
                    image_list.setHasFixedSize(true);

                    ArrayList<String> images = new ArrayList<>();
                    images.add(postOb.getString("image_cdn"));
                    images.add(itemOb.getString("image_cdn"));

                    FundingMarketTimelineImageAdapter fundingMarketTimelineImageAdapter = new FundingMarketTimelineImageAdapter(context, requestManager, images, postOb);
                    image_list.setAdapter(fundingMarketTimelineImageAdapter);


                    if(max_point != null){

                        max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " 명" );
                        current_point.setText(functionBase.makeComma(postOb.getInt("patron_count")) + " 명");

                        int currentProgress = functionBase.progressMaker(postOb.getInt("patron_count"), postOb.getInt("target_amount"));

                        Log.d("currentProgress", String.valueOf(currentProgress));

                        if(currentProgress >100){

                            currentProgress = 100;

                        }

                        progressbar.setProgress(currentProgress);

                    }

                }


            } else {

                functionBase.chargeFollowPatronCheck( postOb, post_image);
                functionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);
                functionBase.generalImageLoading(post_image, postOb, requestManager);

                if(max_point != null){

                    max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " BOX" );
                    current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " BOX");

                    int currentProgress = functionBase.progressMaker(postOb.getInt("achieve_amount"), postOb.getInt("target_amount"));

                    Log.d("currentProgress", String.valueOf(currentProgress));

                    if(currentProgress >100){

                        currentProgress = 100;

                    }

                    progressbar.setProgress(currentProgress);

                }

            }

            if(postOb.getString("post_type").equals("youtube")){

                post_body.setText(postOb.getString("title"));

            } else if(postOb.getString("post_type").equals("novel")){

                String bodyString = "";

                if(postOb.getString("progress").equals("start")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필을 시작했습니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("open")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "'이 오픈 됐습니다. 많은 관심 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("finish")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필이 완료되었습니다. 많은 구독 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                }

                post_body.setText(bodyString);

            } else if(postOb.getString("post_type").equals("seriese")){

                String bodyString = "";

                if(postOb.getInt("seriese_count")==0){

                    bodyString += "[시리즈] '" + postOb.getString("title") + "' 연재 준비를 시작했습니다. \n";
                    bodyString += postOb.getString("body");

                } else {

                    bodyString += "[시리즈] '" + postOb.getString("title") + "'연재가 시작되었습니다. 많은 사랑 부탁 드립니다. \n";
                    bodyString += postOb.getString("body");

                }

                post_body.setText(bodyString);


            } else if(postOb.getString("post_type").equals("event")){

                if(writter_name != null){

                    writter_name.setText("이벤트의 요정");

                }


                functionBase.generalImageLoading(post_image, postOb, requestManager);
                post_body.setText(postOb.getString("body"));


            } else if(postOb.getString("post_type").equals("notice")){

                if(writter_name != null){

                    writter_name.setText("공지사항의 요정");

                }

                requestManager
                        .load(R.drawable.icon_notice_profile)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(writter_photo);

                requestManager
                        .load(R.drawable.icon_notice_banner)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(post_image);

                post_body.setText(postOb.getString("body"));

            }

        } else {

            Log.d("objectId", postOb.getObjectId() );

        }

        if(post_body != null){

            post_body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    functionBase.chargeFollowPatronCheck(postOb , post_body);


                }
            });

        }


        if(post_comment_button != null){



            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }

        if(share_button != null){



            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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
                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }



        }




        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {

                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }




        if(option_button != null){

            option_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    functionBase.OptionButtonFunction(context, option_button, currentUser, postOb);

                }
            });


        }

    }


    public void TimelineArtistMainPostAdapterBuilder(final ParseObject postOb, final ParseObject shareUserOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, String type, final FunctionBase functionBase){

        final CircleImageView writter_photo = ((TimelineItemShareViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelineItemShareViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemShareViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemShareViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemShareViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemShareViewHolder)holder).getPostImageLayout();

        LinearLayout post_comment_button = ((TimelineItemShareViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemShareViewHolder)holder).getCommentIcon();
        final TextView comment_count = ((TimelineItemShareViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemShareViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemShareViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemShareViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemShareViewHolder)holder).getRecordButton();
        ImageView record_icon = ((TimelineItemShareViewHolder)holder).getRecordIcon();
        TextView record_count = ((TimelineItemShareViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemShareViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemShareViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemShareViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemShareViewHolder)holder).getUserInfoLayout();


        LinearLayout share_button = ((TimelineItemShareViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelineItemShareViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelineItemShareViewHolder)holder).getShareCount();

        LinearLayout post_tag_layout = ((TimelineItemShareViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((TimelineItemShareViewHolder)holder).getTagGroup();

        ImageView post_type_image = ((TimelineItemShareViewHolder)holder).getPostTypeImage();
        LinearLayout post_type_image_layout = ((TimelineItemShareViewHolder)holder).getPostTypeImageLayout();

        LinearLayout seriese_icon_layout = ((TimelineItemShareViewHolder)holder).getSerieseIconLayout();
        ImageView seriese_icon = ((TimelineItemShareViewHolder)holder).getSerieseIcon();
        TextView seriese_text = ((TimelineItemShareViewHolder)holder).getSerieseText();

        final LinearLayout option_button =((TimelineItemShareViewHolder)holder).getOptionButton();
        ImageView option_icon = ((TimelineItemShareViewHolder)holder).getOptionIcon();

        final TextView total_profit_share = ((TimelineItemShareViewHolder)holder).getTotalProfitShare();
        TextView dday_text = ((TimelineItemShareViewHolder)holder).getDdayText();
        TextView current_point = ((TimelineItemShareViewHolder)holder).getCurrentPoint();
        TextView max_point = ((TimelineItemShareViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelineItemShareViewHolder)holder).getProgressbar();
        TextView title = ((TimelineItemShareViewHolder)holder).getTitle();

        final LinearLayout delete_button = ((TimelineItemShareViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelineItemShareViewHolder)holder).getDeleteIcon();

        TextView post_detail = ((TimelineItemShareViewHolder)holder).getPostDetail();
        TextView last_post = ((TimelineItemShareViewHolder)holder).getLastPost();
        TextView share_user_name = ((TimelineItemShareViewHolder)holder).getShareUserName();
        LinearLayout ad_badge_layout = ((TimelineItemShareViewHolder)holder).getAdBadgeLayout();

        LinearLayout share_layout = ((TimelineItemShareViewHolder)holder).getShareLayout();
        TextView target_user_name = ((TimelineItemShareViewHolder)holder).getTargetUserName();
        TextView target_post = ((TimelineItemShareViewHolder)holder).getTargetPost();

        String postUserId = postOb.getParseObject("user").getObjectId();

        if(currentUser != null){

            if(share_layout != null){

                if(functionBase.parseArrayCheck(currentUser, "follow_array", postUserId)){

                    share_layout.setVisibility(View.GONE);

                } else {

                    share_layout.setVisibility(View.VISIBLE);

                }

            }

        }


        //작성자 프로필 정보

        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, option_icon, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);

        final ParseObject userOb = postOb.getParseObject("user");



        if(ad_badge_layout != null){

            if(postOb.getBoolean("ad_flag")){

                ad_badge_layout.setVisibility(View.VISIBLE);
            } else {

                ad_badge_layout.setVisibility(View.GONE);
            }

        }


        if(post_detail != null){

            if(postOb.get("body") != null){

                post_detail.setText(postOb.getString("body"));

            } else {

                post_detail.setText("입력 안됨");

            }

        }

        if(last_post != null){


        }

        if(writter_name != null ){
            writter_name.setText("불러오는 중...");

            if(writter_photo != null){

                writter_photo.setImageResource(R.drawable.ic_action_circle_profile);

            }


            ParseObject fetchedUserOb = null;
            try {

                fetchedUserOb = userOb.fetch();
                functionBase.profileImageLoading(writter_photo, fetchedUserOb, requestManager);
                functionBase.profileNameLoading(writter_name, fetchedUserOb);

                if(target_user_name != null){

                    functionBase.profileNameLoading(target_user_name, fetchedUserOb);
                    final ParseObject finalFetchedUserOb = fetchedUserOb;
                    target_user_name.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, UserActivity.class);
                            intent.putExtra("userId", finalFetchedUserOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            context.startActivity(intent);

                        }
                    });

                }





                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( fetchedUserOb.getInt("total_profit_share") ) + "P");

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }




            String postDate = functionBase.dateToString(postOb.getCreatedAt());

            if(post_updated != null){

                post_updated.setText(postDate);

            }


        }



        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    postOb.put("status", false);
                    postOb.put("lastAction", "delete");
                    postOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){



                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }
            });

        }




        if(dday_text != null){

            //dday maker
            Date tagetDate = postOb.getDate("endDate");
            String progressStatus = postOb.getString("progress");



            int dday = functionBase.dday(tagetDate.getYear() + 1900, tagetDate.getMonth() , tagetDate.getDate());

            if(progressStatus != null){

                if(progressStatus.equals("start")){

                    if(dday < 0){

                        dday_text.setText("마감");

                    } else {

                        dday_text.setText(String.valueOf(dday));

                    }



                } else if(progressStatus.equals("production")) {

                    dday_text.setText("제작중");

                } else{

                    dday_text.setText("종료");

                }

            }

        }

        if(title != null){

            title.setText(postOb.getString("title"));

        }

        if(max_point != null){

            max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " BOX" );
            current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " BOX");

            int currentProgress = functionBase.progressMaker(postOb.getInt("achieve_amount"), postOb.getInt("target_amount"));

            Log.d("currentProgress", String.valueOf(currentProgress));

            if(currentProgress >100){

                currentProgress = 100;

            }

            progressbar.setProgress(currentProgress);

        }


        if(type.equals("user")){

            if(user_info_layout != null){

                user_info_layout.setVisibility(View.GONE);

            }

        }

        if(post_body != null){

            if(postOb.get("body") != null){

                post_body.setText(postOb.getString("body"));
            }

        }



        if(like_count != null){

            like_count.setText(String.valueOf(postOb.getInt("like_count")));
        }

        if(comment_count != null){

            comment_count.setText( functionBase.makeComma(postOb.getInt("comment_count")) );

        }


        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);


                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tagGroup.setTags(tagArray);

                tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }

        if(postOb.get("post_type") != null){

            functionBase.chargeFollowPatronCheck( postOb, post_image);
            functionBase.chargeFollowPatronCheck(postOb, target_post);
            functionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);
            functionBase.generalImageLoading(post_image, postOb, requestManager);

            if(postOb.getString("post_type").equals("youtube")){

                post_body.setText(postOb.getString("title"));

            } else if(postOb.getString("post_type").equals("novel")){

                String bodyString = "";

                if(postOb.getString("progress").equals("start")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필을 시작했습니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("open")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "'이 오픈 됐습니다. 많은 관심 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("finish")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필이 완료되었습니다. 많은 구독 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                }

                post_body.setText(bodyString);

            } else if(postOb.getString("post_type").equals("seriese")){

                String bodyString = "";

                if(postOb.getInt("seriese_count")==0){

                    bodyString += "[시리즈] '" + postOb.getString("title") + "' 연재 준비를 시작했습니다. \n";
                    bodyString += postOb.getString("body");

                } else {

                    bodyString += "[시리즈] '" + postOb.getString("title") + "'연재가 시작되었습니다. 많은 사랑 부탁 드립니다. \n";
                    bodyString += postOb.getString("body");

                }

                post_body.setText(bodyString);


            } else if(postOb.getString("post_type").equals("event")){

                if(writter_name != null){

                    writter_name.setText("이벤트의 요정");

                }


                functionBase.generalImageLoading(post_image, postOb, requestManager);
                post_body.setText(postOb.getString("body"));


            } else if(postOb.getString("post_type").equals("notice")){

                if(writter_name != null){

                    writter_name.setText("공지사항의 요정");

                }

                requestManager
                        .load(R.drawable.icon_notice_profile)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(writter_photo);

                requestManager
                        .load(R.drawable.icon_notice_banner)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(post_image);

                post_body.setText(postOb.getString("body"));

            }

        } else {

            Log.d("objectId", postOb.getObjectId() );

        }

        if(post_body != null){

            post_body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    functionBase.artistPostSetOnClickFunction(postOb , post_body);


                }
            });

        }


        if(post_comment_button != null){



            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }

        if(share_button != null){



            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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

                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }

            if(share_user_name != null){

                share_user_name.setText(shareUserOb.getString("name"));

                share_user_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, UserActivity.class);
                        intent.putExtra("userId", shareUserOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);

                    }
                });

            }

        }




        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {

                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }




        if(option_button != null){

            option_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    functionBase.OptionButtonFunction(context, option_button, currentUser, postOb);

                }
            });


        }

    }



    public void TimelineArtistPatronAdapterBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, final String type){

        final CircleImageView writter_photo = ((TimelinePatronViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelinePatronViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelinePatronViewHolder)holder).getPostUpdated();
        ImageView post_image = ((TimelinePatronViewHolder)holder).getPostImage();
        LinearLayout user_info_layout = ((TimelinePatronViewHolder)holder).getUserInfoLayout();
        LinearLayout post_tag_layout = ((TimelinePatronViewHolder)holder).getPostTagLayout();

        TagGroup tag_group = ((TimelinePatronViewHolder)holder).getTagGroup();
        TextView max_point = ((TimelinePatronViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelinePatronViewHolder)holder).getProgressbar();
        TextView title = ((TimelinePatronViewHolder)holder).getTitle();
        TextView body = ((TimelinePatronViewHolder)holder).getBody();

        LinearLayout post_comment_button = ((TimelinePatronViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelinePatronViewHolder)holder).getCommentIcon();
        TextView comment_count = ((TimelinePatronViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelinePatronViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelinePatronViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelinePatronViewHolder)holder).getLikeCount();

        LinearLayout share_button = ((TimelinePatronViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelinePatronViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelinePatronViewHolder)holder).getShareCount();

        LinearLayout comment_input_layout = ((TimelinePatronViewHolder)holder).getCommentSubLayout();
        LinearLayout content_layout = ((TimelinePatronViewHolder)holder).getContentLayout();

        LinearLayout post_type_image_layout = ((TimelinePatronViewHolder)holder).getPostTypeImageLayout();
        ImageView post_type_image = ((TimelinePatronViewHolder)holder).getPostTypeImage();
        TextView profit_share = ((TimelinePatronViewHolder)holder).getProfitShare();
        TextView dday_text = ((TimelinePatronViewHolder)holder).getDdayText();
        TextView current_point = ((TimelinePatronViewHolder)holder).getCurrentPoint();
        final TextView total_profit_share = ((TimelinePatronViewHolder)holder).getTotalProfitShare();

        TextView open_type = ((TimelinePatronViewHolder)holder).getOpenType();
        TextView achieve_ratio = ((TimelinePatronViewHolder)holder).getAchieveRatio();
        TextView patron_count = ((TimelinePatronViewHolder)holder).getPatronCount();
        TextView reward_detail = ((TimelinePatronViewHolder)holder).getRewardDetail();

        LinearLayout seriese_icon_layout = ((TimelinePatronViewHolder)holder).getSerieseIconLayout();
        ImageView seriese_icon = ((TimelinePatronViewHolder)holder).getSerieseIcon();
        TextView seriese_text = ((TimelinePatronViewHolder)holder).getSerieseText();

        TextView min_box = ((TimelinePatronViewHolder)holder).getMinBox();
        TextView patron_type = ((TimelinePatronViewHolder)holder).getPatronType();
        LinearLayout patron_user_button = ((TimelinePatronViewHolder)holder).getPatronUserButton();

        LinearLayout delete_button = ((TimelinePatronViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelinePatronViewHolder)holder).getDeleteIcon();

        RecyclerView image_list = ((TimelinePatronViewHolder)holder).getImageList();
        TextView price = ((TimelinePatronViewHolder)holder).getPrice();


        final FunctionBase functionBase = new FunctionBase(context);

        if(patron_user_button != null){

            patron_user_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronUserActivity.class);
                    intent.putExtra("patronId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }



        if(post_type_image_layout != null || post_image != null){

            post_type_image_layout.setVisibility(View.VISIBLE);

            functionBase.chargeFollowPatronCheck( postOb, post_image);
            functionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);
            functionBase.generalImageLoading(post_image, postOb, requestManager);

            max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " BOX" );
            current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " BOX");

            int currentProgress = functionBase.progressMaker(postOb.getInt("achieve_amount"), postOb.getInt("target_amount"));

            if(currentProgress > 100){

                currentProgress = 100;

            }

            Log.d("currentProgress", String.valueOf(currentProgress));

            if(progressbar != null){

                progressbar.setProgress(currentProgress);
            }

        } else {

            ParseObject itemOb = postOb.getParseObject("item");

            if(itemOb != null){

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
                image_list.setLayoutManager(layoutManager);
                image_list.setHasFixedSize(true);

                ArrayList<String> images = new ArrayList<>();
                images.add(postOb.getString("image_cdn"));
                images.add(itemOb.getString("image_cdn"));

                FundingMarketTimelineImageAdapter fundingMarketTimelineImageAdapter = new FundingMarketTimelineImageAdapter(context, requestManager, images, postOb);
                image_list.setAdapter(fundingMarketTimelineImageAdapter);

            }

            max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " 명" );

            if(current_point != null){

                current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " 명");

            }

            int currentProgress = functionBase.progressMaker(postOb.getInt("achieve_amount"), postOb.getInt("target_amount"));

            if(currentProgress > 100){

                currentProgress = 100;

            }

            Log.d("currentProgress", String.valueOf(currentProgress));

            if(progressbar != null){

                progressbar.setProgress(currentProgress);
            }

        }

        if( profit_share != null){

            profit_share.setText( String.valueOf( postOb.getInt("profit_share_ratio") )  + "%" );

        }

        if(post_tag_layout != null){

            if(type.equals("recommend") || type.equals("mycontent")){

                post_tag_layout.setVisibility(View.GONE);

            } else {

                post_tag_layout.setVisibility(View.VISIBLE);
            }

        }



        //dday maker

        if(dday_text != null){

            //dday maker
            Date tagetDate = postOb.getDate("endDate");
            String progressStatus = postOb.getString("progress");


            int dday = functionBase.dday(tagetDate.getYear() + 1900, tagetDate.getMonth() , tagetDate.getDate());

            if(progressStatus != null){

                if(progressStatus.equals("start")){

                    if(dday < 0){

                        dday_text.setText("마감");

                    } else {

                        dday_text.setText(String.valueOf(dday));

                    }



                } else if(progressStatus.equals("production")) {

                    dday_text.setText("제작중");

                } else{

                    dday_text.setText("종료");

                }

            }

        }

        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                    builder.title("확인");
                    builder.content("삭제 하시겠습니까?");
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

                            postOb.put("status", false);
                            postOb.put("lastAction", "delete");
                            postOb.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        if(currentUser != null){

                                            currentUser.increment("post_count", -1);
                                            currentUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        //myContentPostAdapter.loadObjects(0);

                                                    } else {

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

                        }
                    });
                    builder.show();



                }
            });

        }


        if(open_type != null){

            if(postOb.get("patron_flag") != null){

                if(postOb.getBoolean("patron_flag")){

                    open_type.setText("후원자 독점");

                } else {

                    open_type.setText("전체 공개");
                }


            } else {

                if(postOb.get("open_type") != null){

                    switch (postOb.getString("open_type")){

                        case "openToPatron":

                            open_type.setText("후원자 공개");

                            break;

                        case "openToAll":

                            open_type.setText("전체 공개");

                            break;

                    }

                }

            }

        }

        if(achieve_ratio != null){

            if(postOb.get("achieve_amount") == null){

                achieve_ratio.setText( "0%" );

            } else {

                int progress = functionBase.progressMaker( postOb.getInt("achieve_amount"), postOb.getInt("target_amount") );
                achieve_ratio.setText( functionBase.makeComma( progress )  + "%");

            }

        }

        if(patron_count != null){

            patron_count.setText( functionBase.makeComma( postOb.getInt("patron_count") ) + "명");

        }

        if(reward_detail != null){

            if(postOb.get("reward_exist") != null){

                if(postOb.getBoolean("reward_exist")){

                    reward_detail.setText("있음");

                } else {

                    reward_detail.setText("없음");

                }

            }

        }


        if(min_box != null){

            if(postOb.get("min_box") != null){

                min_box.setText( functionBase.makeComma( postOb.getInt("min_box") ) +  " BOX" );

            }


        }

        if(price != null){

            if(postOb.get("price") != null){

                price.setText(functionBase.makeComma(postOb.getInt("price")) +  " BOX" );
            }
        }

        if(patron_type != null){

            if(postOb.get("content_type") != null){

                if(postOb.getString("content_type").equals("commission")){

                    patron_type.setText( "커미션" );

                } else if(postOb.getString("content_type").equals("goods")){

                    patron_type.setText( "굿즈제작" );

                } else if(postOb.getString("content_type").equals("extra")){

                    patron_type.setText( "기타" );

                } else if(postOb.getString("content_type").equals("post")){

                    patron_type.setText( "포스트" );

                } else if(postOb.getString("content_type").equals("webtoon")){

                    patron_type.setText( "웹툰" );

                } else if(postOb.getString("content_type").equals("album")){

                    patron_type.setText( "사진첩" );

                } else if(postOb.getString("content_type").equals("novel")){

                    patron_type.setText( "웹소설" );

                } else if(postOb.getString("content_type").equals("youtube")){

                    patron_type.setText( "영상" );

                }



            }

        }


        ImageView record_icon = null;
        ImageView request_icon = null;
        TextView record_count = null;

        //작성자 프로필 정보
        Log.d("colorfilter", "init");
        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, null ,true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);


        final ParseObject userOb = postOb.getParseObject("user");

        if(writter_name != null){

            writter_photo.setImageResource(R.drawable.ic_action_circle_profile);
            writter_name.setText("불러오는 중...");

            if(type.equals("recent")){

                userOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedUserOb, ParseException e) {


                        functionBase.profileImageLoading(writter_photo, userOb, requestManager);

                        if(fetchedUserOb.get("name") == null){

                            writter_name.setText(fetchedUserOb.getString("username"));

                        } else {

                            writter_name.setText(fetchedUserOb.getString("name"));

                        }

                        if(total_profit_share != null){

                            total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + " BOX");

                        }

                    }
                });



            } else {

                functionBase.profileImageLoading(writter_photo, userOb, requestManager);

                if(userOb.get("name") == null){

                    writter_name.setText(userOb.getString("username"));

                } else {

                    writter_name.setText(userOb.getString("name"));

                }

                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + " BOX");

                }


            }



            String postDate = functionBase.dateToString(postOb.getCreatedAt());
            post_updated.setText(postDate);


        }



        body.setText(postOb.getString("body"));

        title.setText(postOb.getString("title"));



        if(like_count != null){

            like_count.setText(functionBase.makeComma(postOb.getInt("like_count")));

        }
        if(comment_count != null){

            comment_count.setText( functionBase.makeComma(postOb.getInt("comment_count")) );
        }


        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);

                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tag_group.setTags(tagArray);

                tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }





        if(post_comment_button != null){

            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });


        }


        if(share_button != null){

            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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
                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }

        }


        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }



    }

    public void TimelineArtistPatronForGoodAdapterBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, final String type){

        final CircleImageView writter_photo = ((TimelinePatronViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelinePatronViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelinePatronViewHolder)holder).getPostUpdated();
        ImageView post_image = ((TimelinePatronViewHolder)holder).getPostImage();
        LinearLayout user_info_layout = ((TimelinePatronViewHolder)holder).getUserInfoLayout();
        LinearLayout post_tag_layout = ((TimelinePatronViewHolder)holder).getPostTagLayout();

        TagGroup tag_group = ((TimelinePatronViewHolder)holder).getTagGroup();
        TextView max_point = ((TimelinePatronViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelinePatronViewHolder)holder).getProgressbar();
        TextView title = ((TimelinePatronViewHolder)holder).getTitle();
        TextView body = ((TimelinePatronViewHolder)holder).getBody();

        LinearLayout post_comment_button = ((TimelinePatronViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelinePatronViewHolder)holder).getCommentIcon();
        TextView comment_count = ((TimelinePatronViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelinePatronViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelinePatronViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelinePatronViewHolder)holder).getLikeCount();

        LinearLayout share_button = ((TimelinePatronViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelinePatronViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelinePatronViewHolder)holder).getShareCount();

        LinearLayout comment_input_layout = ((TimelinePatronViewHolder)holder).getCommentSubLayout();
        LinearLayout content_layout = ((TimelinePatronViewHolder)holder).getContentLayout();

        LinearLayout post_type_image_layout = ((TimelinePatronViewHolder)holder).getPostTypeImageLayout();
        ImageView post_type_image = ((TimelinePatronViewHolder)holder).getPostTypeImage();
        TextView profit_share = ((TimelinePatronViewHolder)holder).getProfitShare();
        TextView dday_text = ((TimelinePatronViewHolder)holder).getDdayText();
        TextView current_point = ((TimelinePatronViewHolder)holder).getCurrentPoint();
        final TextView total_profit_share = ((TimelinePatronViewHolder)holder).getTotalProfitShare();

        TextView open_type = ((TimelinePatronViewHolder)holder).getOpenType();
        TextView achieve_ratio = ((TimelinePatronViewHolder)holder).getAchieveRatio();
        TextView patron_count = ((TimelinePatronViewHolder)holder).getPatronCount();
        TextView reward_detail = ((TimelinePatronViewHolder)holder).getRewardDetail();

        LinearLayout seriese_icon_layout = ((TimelinePatronViewHolder)holder).getSerieseIconLayout();
        ImageView seriese_icon = ((TimelinePatronViewHolder)holder).getSerieseIcon();
        TextView seriese_text = ((TimelinePatronViewHolder)holder).getSerieseText();

        TextView min_box = ((TimelinePatronViewHolder)holder).getMinBox();
        TextView patron_type = ((TimelinePatronViewHolder)holder).getPatronType();
        LinearLayout patron_user_button = ((TimelinePatronViewHolder)holder).getPatronUserButton();

        LinearLayout delete_button = ((TimelinePatronViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelinePatronViewHolder)holder).getDeleteIcon();

        RecyclerView image_list = ((TimelinePatronViewHolder)holder).getImageList();
        TextView price = ((TimelinePatronViewHolder)holder).getPrice();


        final FunctionBase functionBase = new FunctionBase(context);

        if(patron_user_button != null){

            patron_user_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronUserActivity.class);
                    intent.putExtra("patronId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }



        if(post_type_image_layout != null || post_image != null){

            post_type_image_layout.setVisibility(View.VISIBLE);

            functionBase.chargeFollowPatronCheck( postOb, post_image);
            functionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);
            functionBase.generalImageLoading(post_image, postOb, requestManager);

            max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " BOX" );
            current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " BOX");

            int currentProgress = functionBase.progressMaker(postOb.getInt("order_count"), postOb.getInt("target_amount"));

            if(currentProgress > 100){

                currentProgress = 100;

            }

            Log.d("currentProgress", String.valueOf(currentProgress));

            if(progressbar != null){

                progressbar.setProgress(currentProgress);
            }

        } else {

            ParseObject itemOb = postOb.getParseObject("item");

            if(itemOb != null){

                LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false);
                image_list.setLayoutManager(layoutManager);
                image_list.setHasFixedSize(true);

                ArrayList<String> images = new ArrayList<>();
                images.add(postOb.getString("image_cdn"));
                images.add(itemOb.getString("image_cdn"));

                FundingMarketTimelineImageAdapter fundingMarketTimelineImageAdapter = new FundingMarketTimelineImageAdapter(context, requestManager, images, postOb);
                image_list.setAdapter(fundingMarketTimelineImageAdapter);

            }

            max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " 명" );

            if(current_point != null){

                current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " 명");

            }

            int currentProgress = functionBase.progressMaker(postOb.getInt("order_count"), postOb.getInt("target_amount"));

            if(currentProgress > 100){

                currentProgress = 100;

            }

            Log.d("currentProgress", String.valueOf(currentProgress));

            if(progressbar != null){

                progressbar.setProgress(currentProgress);
            }

        }

        if( profit_share != null){

            profit_share.setText( String.valueOf( postOb.getInt("profit_share_ratio") )  + "%" );

        }

        if(post_tag_layout != null){

            if(type.equals("recommend") || type.equals("mycontent")){

                post_tag_layout.setVisibility(View.GONE);

            } else {

                post_tag_layout.setVisibility(View.VISIBLE);
            }

        }



        //dday maker

        if(dday_text != null){

            //dday maker
            Date tagetDate = postOb.getDate("endDate");
            String progressStatus = postOb.getString("progress");


            int dday = functionBase.dday(tagetDate.getYear() + 1900, tagetDate.getMonth() , tagetDate.getDate());

            if(progressStatus != null){

                if(progressStatus.equals("start")){

                    if(dday < 0){

                        dday_text.setText("마감");

                    } else {

                        dday_text.setText(String.valueOf(dday));

                    }



                } else if(progressStatus.equals("production")) {

                    dday_text.setText("제작중");

                } else{

                    dday_text.setText("종료");

                }

            }

        }

        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                    builder.title("확인");
                    builder.content("삭제 하시겠습니까?");
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

                            postOb.put("status", false);
                            postOb.put("lastAction", "delete");
                            postOb.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        if(currentUser != null){

                                            currentUser.increment("post_count", -1);
                                            currentUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        //myContentPostAdapter.loadObjects(0);

                                                    } else {

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

                        }
                    });
                    builder.show();



                }
            });

        }


        if(open_type != null){

            if(postOb.get("patron_flag") != null){

                if(postOb.getBoolean("patron_flag")){

                    open_type.setText("후원자 독점");

                } else {

                    open_type.setText("전체 공개");
                }


            } else {

                if(postOb.get("open_type") != null){

                    switch (postOb.getString("open_type")){

                        case "openToPatron":

                            open_type.setText("후원자 공개");

                            break;

                        case "openToAll":

                            open_type.setText("전체 공개");

                            break;

                    }

                }

            }

        }

        if(achieve_ratio != null){

            if(postOb.get("achieve_amount") == null){

                achieve_ratio.setText( "0%" );

            } else {

                int progress = functionBase.progressMaker( postOb.getInt("order_count"), postOb.getInt("target_amount") );
                achieve_ratio.setText( functionBase.makeComma( progress )  + "%");

            }

        }

        if(patron_count != null){

            patron_count.setText( functionBase.makeComma( postOb.getInt("patron_count") ) + "명");

        }

        if(reward_detail != null){

            if(postOb.get("reward_exist") != null){

                if(postOb.getBoolean("reward_exist")){

                    reward_detail.setText("있음");

                } else {

                    reward_detail.setText("없음");

                }

            }

        }


        if(min_box != null){

            if(postOb.get("min_box") != null){

                min_box.setText( functionBase.makeComma( postOb.getInt("min_box") ) +  " BOX" );

            }


        }

        if(price != null){

            if(postOb.get("price") != null){

                price.setText(functionBase.makeComma(postOb.getInt("price")) +  " BOX" );
            }
        }

        if(patron_type != null){

            if(postOb.get("content_type") != null){

                if(postOb.getString("content_type").equals("commission")){

                    patron_type.setText( "커미션" );

                } else if(postOb.getString("content_type").equals("goods")){

                    patron_type.setText( "굿즈제작" );

                } else if(postOb.getString("content_type").equals("extra")){

                    patron_type.setText( "기타" );

                } else if(postOb.getString("content_type").equals("post")){

                    patron_type.setText( "포스트" );

                } else if(postOb.getString("content_type").equals("webtoon")){

                    patron_type.setText( "웹툰" );

                } else if(postOb.getString("content_type").equals("album")){

                    patron_type.setText( "사진첩" );

                } else if(postOb.getString("content_type").equals("novel")){

                    patron_type.setText( "웹소설" );

                } else if(postOb.getString("content_type").equals("youtube")){

                    patron_type.setText( "영상" );

                }



            }

        }


        ImageView record_icon = null;
        ImageView request_icon = null;
        TextView record_count = null;

        //작성자 프로필 정보
        Log.d("colorfilter", "init");
        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, null ,true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);


        final ParseObject userOb = postOb.getParseObject("user");

        if(writter_name != null){

            writter_photo.setImageResource(R.drawable.ic_action_circle_profile);
            writter_name.setText("불러오는 중...");

            if(type.equals("recent")){

                userOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedUserOb, ParseException e) {


                        functionBase.profileImageLoading(writter_photo, userOb, requestManager);

                        if(fetchedUserOb.get("name") == null){

                            writter_name.setText(fetchedUserOb.getString("username"));

                        } else {

                            writter_name.setText(fetchedUserOb.getString("name"));

                        }

                        if(total_profit_share != null){

                            total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + " BOX");

                        }

                    }
                });



            } else {

                functionBase.profileImageLoading(writter_photo, userOb, requestManager);

                if(userOb.get("name") == null){

                    writter_name.setText(userOb.getString("username"));

                } else {

                    writter_name.setText(userOb.getString("name"));

                }

                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + " BOX");

                }


            }



            String postDate = functionBase.dateToString(postOb.getCreatedAt());
            post_updated.setText(postDate);


        }



        body.setText(postOb.getString("body"));

        title.setText(postOb.getString("title"));



        if(like_count != null){

            like_count.setText(functionBase.makeComma(postOb.getInt("like_count")));

        }
        if(comment_count != null){

            comment_count.setText( functionBase.makeComma(postOb.getInt("comment_count")) );
        }


        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);

                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tag_group.setTags(tagArray);

                tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }





        if(post_comment_button != null){

            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });


        }


        if(share_button != null){

            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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
                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }

        }


        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }



    }

    public void TimelineArtistPatronAdapterWithDeleteBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, final String type , final MyContentPatronAdapter myContentPatronAdapter){

        final CircleImageView writter_photo = ((TimelinePatronViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelinePatronViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelinePatronViewHolder)holder).getPostUpdated();
        ImageView post_image = ((TimelinePatronViewHolder)holder).getPostImage();
        LinearLayout user_info_layout = ((TimelinePatronViewHolder)holder).getUserInfoLayout();
        LinearLayout post_tag_layout = ((TimelinePatronViewHolder)holder).getPostTagLayout();

        TagGroup tag_group = ((TimelinePatronViewHolder)holder).getTagGroup();
        TextView max_point = ((TimelinePatronViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelinePatronViewHolder)holder).getProgressbar();
        TextView title = ((TimelinePatronViewHolder)holder).getTitle();
        TextView body = ((TimelinePatronViewHolder)holder).getBody();

        LinearLayout post_comment_button = ((TimelinePatronViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelinePatronViewHolder)holder).getCommentIcon();
        TextView comment_count = ((TimelinePatronViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelinePatronViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelinePatronViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelinePatronViewHolder)holder).getLikeCount();

        LinearLayout share_button = ((TimelinePatronViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelinePatronViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelinePatronViewHolder)holder).getShareCount();

        LinearLayout comment_input_layout = ((TimelinePatronViewHolder)holder).getCommentSubLayout();
        LinearLayout content_layout = ((TimelinePatronViewHolder)holder).getContentLayout();

        LinearLayout post_type_image_layout = ((TimelinePatronViewHolder)holder).getPostTypeImageLayout();
        ImageView post_type_image = ((TimelinePatronViewHolder)holder).getPostTypeImage();
        TextView profit_share = ((TimelinePatronViewHolder)holder).getProfitShare();
        TextView dday_text = ((TimelinePatronViewHolder)holder).getDdayText();
        TextView current_point = ((TimelinePatronViewHolder)holder).getCurrentPoint();
        final TextView total_profit_share = ((TimelinePatronViewHolder)holder).getTotalProfitShare();

        TextView open_type = ((TimelinePatronViewHolder)holder).getOpenType();
        TextView achieve_ratio = ((TimelinePatronViewHolder)holder).getAchieveRatio();
        TextView patron_count = ((TimelinePatronViewHolder)holder).getPatronCount();
        TextView reward_detail = ((TimelinePatronViewHolder)holder).getRewardDetail();

        LinearLayout seriese_icon_layout = ((TimelinePatronViewHolder)holder).getSerieseIconLayout();
        ImageView seriese_icon = ((TimelinePatronViewHolder)holder).getSerieseIcon();
        TextView seriese_text = ((TimelinePatronViewHolder)holder).getSerieseText();

        TextView min_box = ((TimelinePatronViewHolder)holder).getMinBox();
        TextView patron_type = ((TimelinePatronViewHolder)holder).getPatronType();
        LinearLayout patron_user_button = ((TimelinePatronViewHolder)holder).getPatronUserButton();

        LinearLayout delete_button = ((TimelinePatronViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelinePatronViewHolder)holder).getDeleteIcon();

        LinearLayout post_button = ((TimelinePatronViewHolder)holder).getPostButton();
        LinearLayout edit_button = ((TimelinePatronViewHolder)holder).getEditButton();
        LinearLayout manage_button = ((TimelinePatronViewHolder)holder).getManageButton();

        final ParseObject itemOb = postOb.getParseObject("item");
        final FunctionBase functionBase = new FunctionBase(context);

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PatronDetailActivity.class);
                intent.putExtra("postId", postOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, GoodsFundingEditActivity.class);
                intent.putExtra("postId", postOb.getObjectId());
                intent.putExtra("itemId", itemOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }

        });

        manage_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PatronManagerActivity.class);
                intent.putExtra("postId", postOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


        if(patron_user_button != null){

            patron_user_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronUserActivity.class);
                    intent.putExtra("patronId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }



        if(post_type_image_layout != null){

            post_type_image_layout.setVisibility(View.VISIBLE);

            //functionBase.chargeFollowPatronCheck( postOb, post_image);
            post_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronManagerActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

            functionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);

        }

        functionBase.generalImageLoading(post_image, postOb, requestManager);

        if( profit_share != null){

            profit_share.setText( String.valueOf( postOb.getInt("profit_share_ratio") )  + "%" );

        }

        if(post_tag_layout != null){

            if(type.equals("recommend") || type.equals("mycontent")){

                post_tag_layout.setVisibility(View.GONE);

            } else {

                post_tag_layout.setVisibility(View.VISIBLE);
            }

        }



        //dday maker

        if(dday_text != null){

            //dday maker
            Date tagetDate = postOb.getDate("endDate");
            String progressStatus = postOb.getString("progress");


            int dday = functionBase.dday(tagetDate.getYear() + 1900, tagetDate.getMonth() , tagetDate.getDate());

            if(progressStatus != null){

                if(progressStatus.equals("start")){

                    if(dday < 0){

                        dday_text.setText("마감");

                    } else {

                        dday_text.setText(String.valueOf(dday));

                    }



                } else if(progressStatus.equals("production")) {

                    dday_text.setText("제작중");

                } else{

                    dday_text.setText("종료");

                }

            }

        }

        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

                    builder.title("확인");
                    builder.content("삭제 하시겠습니까?");
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

                            postOb.put("status", false);
                            postOb.put("lastAction", "delete");
                            postOb.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        if(currentUser != null){

                                            currentUser.increment("post_count", -1);
                                            currentUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        myContentPatronAdapter.loadObjects(0);

                                                    } else {

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

                        }
                    });
                    builder.show();



                }
            });

        }


        if(open_type != null){

            if(postOb.get("patron_flag") != null){

                if(postOb.getBoolean("patron_flag")){

                    open_type.setText("후원자 독점");

                } else {

                    open_type.setText("전체 공개");
                }


            } else {

                switch (postOb.getString("open_type")){

                    case "openToPatron":

                        open_type.setText("후원자 공개");

                        break;

                    case "openToAll":

                        open_type.setText("전체 공개");

                        break;

                }

            }

        }

        if(achieve_ratio != null){

            if(postOb.get("achieve_amount") == null){

                achieve_ratio.setText( "0%" );

            } else {

                int progress = functionBase.progressMaker( postOb.getInt("achieve_amount"), postOb.getInt("target_amount") );
                achieve_ratio.setText( functionBase.makeComma( progress )  + "%");

            }

        }

        if(patron_count != null){

            patron_count.setText( functionBase.makeComma( postOb.getInt("patron_count") ) + "명");

        }

        if(reward_detail != null){

            if(postOb.get("reward_exist") != null){

                if(postOb.getBoolean("reward_exist")){

                    reward_detail.setText("있음");

                } else {

                    reward_detail.setText("없음");

                }

            }

        }


        if(min_box != null){

            if(postOb.get("min_box") != null){

                min_box.setText( String.valueOf( postOb.getInt("min_box") ) +  " BOX" );

            }


        }

        if(patron_type != null){

            if(postOb.get("content_type") != null){

                if(postOb.getString("content_type").equals("commission")){

                    patron_type.setText( "커미션" );

                } else if(postOb.getString("content_type").equals("goods")){

                    patron_type.setText( "굿즈제작" );

                } else if(postOb.getString("content_type").equals("extra")){

                    patron_type.setText( "기타" );

                } else if(postOb.getString("content_type").equals("post")){

                    patron_type.setText( "포스트" );

                } else if(postOb.getString("content_type").equals("webtoon")){

                    patron_type.setText( "웹툰" );

                } else if(postOb.getString("content_type").equals("album")){

                    patron_type.setText( "사진첩" );

                } else if(postOb.getString("content_type").equals("novel")){

                    patron_type.setText( "웹소설" );

                } else if(postOb.getString("content_type").equals("youtube")){

                    patron_type.setText( "영상" );

                }



            }

        }


        ImageView record_icon = null;
        ImageView request_icon = null;
        TextView record_count = null;

        //작성자 프로필 정보
        Log.d("colorfilter", "init");
        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, null ,true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);


        final ParseObject userOb = postOb.getParseObject("user");

        if(writter_name != null){

            writter_photo.setImageResource(R.drawable.ic_action_circle_profile);
            writter_name.setText("불러오는 중...");

            if(type.equals("recent")){

                userOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedUserOb, ParseException e) {


                        functionBase.profileImageLoading(writter_photo, userOb, requestManager);

                        if(fetchedUserOb.get("name") == null){

                            writter_name.setText(fetchedUserOb.getString("username"));

                        } else {

                            writter_name.setText(fetchedUserOb.getString("name"));

                        }

                        if(total_profit_share != null){

                            total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + " BOX");

                        }

                    }
                });



            } else {

                functionBase.profileImageLoading(writter_photo, userOb, requestManager);

                if(userOb.get("name") == null){

                    writter_name.setText(userOb.getString("username"));

                } else {

                    writter_name.setText(userOb.getString("name"));

                }

                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + " BOX");

                }


            }



            String postDate = functionBase.dateToString(postOb.getCreatedAt());
            post_updated.setText(postDate);


        }


        if(body != null){

            body.setText(postOb.getString("body"));

        }

        if(title != null){

            title.setText(postOb.getString("title"));
        }


        max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " BOX" );
        current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " BOX");

        int currentProgress = functionBase.progressMaker(postOb.getInt("achieve_amount"), postOb.getInt("target_amount"));

        if(currentProgress > 100){

            currentProgress = 100;

        }

        Log.d("currentProgress", String.valueOf(currentProgress));

        if(progressbar != null){

            progressbar.setProgress(currentProgress);
        }


        if(like_count != null){

            like_count.setText(functionBase.makeComma(postOb.getInt("like_count")));

        }
        if(comment_count != null){

            comment_count.setText( functionBase.makeComma(postOb.getInt("comment_count")) );
        }


        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);

                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tag_group.setTags(tagArray);

                tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }





        if(post_comment_button != null){

            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, PostingDetailActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });


        }


        if(share_button != null){

            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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
                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }

        }


        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }



    }


    public void TimelinePostAdapterBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, RequestManager requestManager){

        CircleImageView writter_photo = ((TimelineItemViewHolder)holder).getWritterPhoto();
        TextView writter_name = ((TimelineItemViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemViewHolder)holder).getPostImageLayout();
        //IconRoundCornerProgressBar progress_item = ((TimelineItemViewHolder)holder).getProgressItem();

        LinearLayout post_comment_button = ((TimelineItemViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemViewHolder)holder).getCommentIcon();
        TextView comment_count = ((TimelineItemViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemViewHolder)holder).getRecordButton();
        final ImageView record_icon = ((TimelineItemViewHolder)holder).getRecordIcon();
        final TextView record_count = ((TimelineItemViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemViewHolder)holder).getUserInfoLayout();


        final ParseObject userOb = postOb.getParseObject("user");

        functionBase.profileImageLoading(writter_photo, userOb, requestManager);

        if(userOb.get("name") == null){

            writter_name.setText(userOb.getString("username"));

        } else {

            writter_name.setText(userOb.getString("name"));

        }



        String postDate = functionBase.dateToString(postOb.getCreatedAt());
        post_updated.setText(postDate);
        post_body.setText(postOb.getString("body"));
        like_count.setText(String.valueOf(postOb.getInt("like_count")));
        record_count.setText( String.valueOf(postOb.getInt("record_count")) );
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
                            like_icon.setColorFilter(functionBase.likePostColor);

                        } else {

                            like_count.setTextColor(functionBase.likedPostColor);
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

                            record_count.setTextColor(functionBase.likePostColor);
                            record_icon.setColorFilter(functionBase.likePostColor);

                        } else {

                            record_count.setTextColor(functionBase.likedPostColor);
                            record_icon.setColorFilter(functionBase.likedPostColor);

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


                functionBase.artistPostSetOnClickFunction(postOb , post_body);


            }
        });

        post_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, PostingDetailActivity.class);
                intent.putExtra("postId", postOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });



        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }



        request_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser != null){

                } else {

                    TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }



            }
        });

        function_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        user_info_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

    }


    public void PostCMAdapterBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, String type, final MyContentPostAdapter myContentPostAdapter){

        final CircleImageView writter_photo = ((TimelineItemViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelineItemViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemViewHolder)holder).getPostImageLayout();

        LinearLayout post_comment_button = ((TimelineItemViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemViewHolder)holder).getCommentIcon();
        TextView comment_count = ((TimelineItemViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemViewHolder)holder).getRecordButton();
        final ImageView record_icon = ((TimelineItemViewHolder)holder).getRecordIcon();
        final TextView record_count = ((TimelineItemViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemViewHolder)holder).getUserInfoLayout();


        LinearLayout share_button = ((TimelineItemViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelineItemViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelineItemViewHolder)holder).getShareCount();

        LinearLayout post_tag_layout = ((TimelineItemViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((TimelineItemViewHolder)holder).getTagGroup();

        ImageView post_type_image = ((TimelineItemViewHolder)holder).getPostTypeImage();
        LinearLayout post_type_image_layout = ((TimelineItemViewHolder)holder).getPostTypeImageLayout();
        final LinearLayout option_button =((TimelineItemViewHolder)holder).getOptionButton();
        ImageView option_icon = ((TimelineItemViewHolder)holder).getOptionIcon();

        final TextView total_profit_share = ((TimelineItemViewHolder)holder).getTotalProfitShare();
        TextView dday_text = ((TimelineItemViewHolder)holder).getDdayText();
        TextView current_point = ((TimelineItemViewHolder)holder).getCurrentPoint();
        TextView max_point = ((TimelineItemViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelineItemViewHolder)holder).getProgressbar();
        TextView title = ((TimelineItemViewHolder)holder).getTitle();

        LinearLayout delete_button = ((TimelineItemViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelineItemViewHolder)holder).getDeleteIcon();

        //작성자 프로필 정보

        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, option_icon, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);


        final ParseObject userOb = postOb.getParseObject("user");

        if(writter_name != null ){

            writter_photo.setImageResource(R.drawable.ic_action_circle_profile);
            writter_name.setText("불러오는 중...");

            if(type.equals("recent") || type.equals("cm")){

                userOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedUserOb, ParseException e) {


                        functionBase.profileImageLoading(writter_photo, fetchedUserOb, requestManager);
                        functionBase.profileNameLoading(writter_name, fetchedUserOb);

                        if(total_profit_share != null){

                            total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

                        }


                    }
                });

            } else {

                functionBase.profileImageLoading(writter_photo, userOb, requestManager);
                functionBase.profileNameLoading(writter_name, userOb);


                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

                }


            }

            String postDate = functionBase.dateToString(postOb.getCreatedAt());
            post_updated.setText(postDate);

        }

        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                    builder.title("확인");
                    builder.content("삭제 하시겠습니까?");
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

                            postOb.put("status", false);
                            postOb.put("lastAction", "delete");
                            postOb.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        if(currentUser != null){

                                            currentUser.increment("post_count", -1);
                                            currentUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        myContentPostAdapter.loadObjects(0);

                                                    } else {

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

                        }
                    });
                    builder.show();



                }
            });

        }




        if(dday_text != null){

            //dday maker
            Date tagetDate = postOb.getDate("endDate");
            String progressStatus = postOb.getString("progress");

            Log.d("progressStatus", progressStatus);

            int dday = functionBase.dday(tagetDate.getYear() + 1900, tagetDate.getMonth() , tagetDate.getDate());
            Log.d("target year", String.valueOf(tagetDate.getYear() + 1900));
            Log.d("target month", String.valueOf(tagetDate.getMonth() + 1));
            Log.d("target year", String.valueOf(tagetDate.getDate() ));

            if(progressStatus != null){

                if(progressStatus.equals("start")){

                    if(dday < 0){

                        dday_text.setText("마감");

                    } else {

                        dday_text.setText(String.valueOf(dday));

                    }



                } else if(progressStatus.equals("production")) {

                    dday_text.setText("제작중");

                } else{

                    dday_text.setText("종료");

                }

            }

        }

        if(title != null){

            title.setText(postOb.getString("title"));

        }

        if(max_point != null){

            max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + "P" );
            current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + "P");

            int currentProgress = functionBase.progressMaker(postOb.getInt("achieve_amount"), postOb.getInt("target_amount"));

            Log.d("currentProgress", String.valueOf(currentProgress));

            progressbar.setProgress(currentProgress);

        }







        if(type.equals("user")){

            user_info_layout.setVisibility(View.GONE);

        }



        post_body.setText(postOb.getString("body"));

        if(like_count != null){

            like_count.setText(String.valueOf(postOb.getInt("like_count")));
        }

        if(comment_count != null){

            comment_count.setText( String.valueOf(postOb.getInt("comment_count")) );
        }




        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);

                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tagGroup.setTags(tagArray);

                tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }

        if(postOb.get("post_type") != null){

            functionBase.artistPostEditSetOnClickFunction(context,postOb, post_image );
            functionBase.generalImageLoading(post_image, postOb, requestManager);

            Log.d("post type", postOb.getString("post_type"));

            if(postOb.getString("post_type").equals("post")){

                if(!type.equals("post")){

                    post_type_image.setImageResource(R.drawable.icon_post);

                }

            } else if(postOb.getString("post_type").equals("webtoon") ){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_webtoon);

                }


            } else if(postOb.getString("post_type").equals("album") ){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_album);

                }

            } else if(postOb.getString("post_type").equals("youtube")){


                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_youtube);


                }

                post_body.setText(postOb.getString("title"));

            } else if(postOb.getString("post_type").equals("novel")){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_novel);


                }

                String bodyString = "";

                post_body.setText(bodyString);

            } else if(postOb.getString("post_type").equals("seriese")){

                Log.d("post content type", postOb.getString("content_type"));

                if(postOb.getString("content_type").equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_post);


                } else if(postOb.getString("content_type").equals("album")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_album);

                } else if(postOb.getString("content_type").equals("novel")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_novel);

                } else if(postOb.getString("content_type").equals("webtoon")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_webtoon);

                } else if(postOb.getString("content_type").equals("youtube")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_youtube);

                }


                String bodyString = "";

                if(postOb.getInt("seriese_count")==0){

                    bodyString += "[시리즈] '" + postOb.getString("title") + "' 연재 준비를 시작했습니다. \n";
                    bodyString += postOb.getString("body");

                    post_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            TastyToast.makeText(context, "작가님이 막 연재를 시작하셨어요. 첫 작품이 시작될 때까지 많은 기대 부탁 드립니다", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        }
                    });

                } else {

                    bodyString += "[시리즈] '" + postOb.getString("title") + "'연재가 시작되었습니다. 많은 사랑 부탁 드립니다. \n";
                    bodyString += postOb.getString("body");

                    post_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(context, NovelActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                }

                post_body.setText(bodyString);


            } else if(postOb.getString("post_type").equals("patron")){

                if(post_type_image_layout != null){

                    post_type_image_layout.setVisibility(View.VISIBLE);
                }



                if(postOb.get("content_type") != null){

                    Log.d("content_type", postOb.getString("content_type"));

                    if(postOb.getString("content_type").equals("post")){

                        post_type_image.setImageResource(R.drawable.icon_post);

                    } else if(postOb.getString("content_type").equals("webtoon")){

                        post_type_image.setImageResource(R.drawable.icon_webtoon);


                    } else if(postOb.getString("content_type").equals("album")){

                        post_type_image.setImageResource(R.drawable.icon_album);

                    } else if(postOb.getString("content_type").equals("youtube")){

                        post_type_image.setImageResource(R.drawable.ic_action_play);

                    } else if(postOb.getString("content_type").equals("novel")){

                        post_type_image.setImageResource(R.drawable.icon_novel);

                    } else {

                        post_type_image.setImageResource(R.drawable.image_background);
                    }

                } else {

                    post_type_image_layout.setVisibility(View.GONE);

                }

            }

        } else {

            Log.d("objectId", postOb.getObjectId() );

        }



        post_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                functionBase.artistPostSetOnClickFunction(postOb , post_body);


            }
        });

        if(post_comment_button != null){

            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }

        if(share_button != null){

            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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
                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }

        }

        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }


        if(option_button != null){

            option_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    functionBase.OptionButtonFunction(context, option_button, currentUser, postOb);

                }
            });


        }

    }

    public void UserPostBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, String type, final UserPostAdapter userPostAdapter){

        final CircleImageView writter_photo = ((TimelineItemViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelineItemViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemViewHolder)holder).getPostImageLayout();

        LinearLayout post_comment_button = ((TimelineItemViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemViewHolder)holder).getCommentIcon();
        TextView comment_count = ((TimelineItemViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemViewHolder)holder).getRecordButton();
        final ImageView record_icon = ((TimelineItemViewHolder)holder).getRecordIcon();
        final TextView record_count = ((TimelineItemViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemViewHolder)holder).getUserInfoLayout();


        LinearLayout share_button = ((TimelineItemViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelineItemViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelineItemViewHolder)holder).getShareCount();

        LinearLayout post_tag_layout = ((TimelineItemViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((TimelineItemViewHolder)holder).getTagGroup();

        ImageView post_type_image = ((TimelineItemViewHolder)holder).getPostTypeImage();
        LinearLayout post_type_image_layout = ((TimelineItemViewHolder)holder).getPostTypeImageLayout();
        final LinearLayout option_button =((TimelineItemViewHolder)holder).getOptionButton();
        ImageView option_icon = ((TimelineItemViewHolder)holder).getOptionIcon();

        final TextView total_profit_share = ((TimelineItemViewHolder)holder).getTotalProfitShare();
        TextView dday_text = ((TimelineItemViewHolder)holder).getDdayText();
        TextView current_point = ((TimelineItemViewHolder)holder).getCurrentPoint();
        TextView max_point = ((TimelineItemViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelineItemViewHolder)holder).getProgressbar();
        TextView title = ((TimelineItemViewHolder)holder).getTitle();

        LinearLayout delete_button = ((TimelineItemViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelineItemViewHolder)holder).getDeleteIcon();

        //작성자 프로필 정보

        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, option_icon, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);


        final ParseObject userOb = postOb.getParseObject("user");

        if(writter_name != null ){

            writter_photo.setImageResource(R.drawable.ic_action_circle_profile);
            writter_name.setText("불러오는 중...");

            if(type.equals("recent") || type.equals("cm")){

                userOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedUserOb, ParseException e) {

                        functionBase.profileImageLoading(writter_photo, fetchedUserOb, requestManager);

                        if(fetchedUserOb.get("name") == null){

                            writter_name.setText(fetchedUserOb.getString("username"));

                        } else {

                            writter_name.setText(fetchedUserOb.getString("name"));

                        }

                        if(total_profit_share != null){

                            total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

                        }


                    }
                });

            } else {


                functionBase.profileImageLoading(writter_photo, userOb, requestManager);

                if(userOb.get("name") == null){

                    writter_name.setText(userOb.getString("username"));

                } else {

                    writter_name.setText(userOb.getString("name"));

                }

                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

                }


            }

            String postDate = functionBase.dateToString(postOb.getCreatedAt());
            post_updated.setText(postDate);

        }

        if(title != null){

            title.setText(postOb.getString("title"));

        }

        post_body.setText(postOb.getString("body"));


        if(postOb.get("post_type") != null){

            functionBase.chargeFollowPatronCheck( postOb, post_image);
            //FunctionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);
            functionBase.generalImageLoading(post_image, postOb, requestManager);

            if(postOb.getString("post_type").equals("post")){

                if(!type.equals("post")){

                    post_type_image.setImageResource(R.drawable.icon_post);

                }

            } else if(postOb.getString("post_type").equals("webtoon") ){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_webtoon);

                }


            } else if(postOb.getString("post_type").equals("album") ){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_album);

                }


            } else if(postOb.getString("post_type").equals("youtube")){


                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_youtube);


                }

                post_body.setText(postOb.getString("title"));

            } else if(postOb.getString("post_type").equals("novel")){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_novel);


                }

                String bodyString = "";

                if(postOb.getString("progress").equals("start")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필을 시작했습니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("open")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "'이 오픈 됐습니다. 많은 관심 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("finish")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필이 완료되었습니다. 많은 구독 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                }

                post_body.setText(bodyString);

            }

        } else {

            Log.d("objectId", postOb.getObjectId() );

        }





    }


    public void SerieseExistAddItemBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, String type, final ParseObject parentOb, final Activity activity){

        final CircleImageView writter_photo = ((TimelineItemViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelineItemViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemViewHolder)holder).getPostImageLayout();

        LinearLayout post_comment_button = ((TimelineItemViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemViewHolder)holder).getCommentIcon();
        TextView comment_count = ((TimelineItemViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemViewHolder)holder).getRecordButton();
        final ImageView record_icon = ((TimelineItemViewHolder)holder).getRecordIcon();
        final TextView record_count = ((TimelineItemViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemViewHolder)holder).getUserInfoLayout();


        LinearLayout share_button = ((TimelineItemViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelineItemViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelineItemViewHolder)holder).getShareCount();

        LinearLayout post_tag_layout = ((TimelineItemViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((TimelineItemViewHolder)holder).getTagGroup();

        ImageView post_type_image = ((TimelineItemViewHolder)holder).getPostTypeImage();
        LinearLayout post_type_image_layout = ((TimelineItemViewHolder)holder).getPostTypeImageLayout();
        final LinearLayout option_button =((TimelineItemViewHolder)holder).getOptionButton();
        ImageView option_icon = ((TimelineItemViewHolder)holder).getOptionIcon();

        final TextView total_profit_share = ((TimelineItemViewHolder)holder).getTotalProfitShare();
        TextView dday_text = ((TimelineItemViewHolder)holder).getDdayText();
        TextView current_point = ((TimelineItemViewHolder)holder).getCurrentPoint();
        TextView max_point = ((TimelineItemViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelineItemViewHolder)holder).getProgressbar();
        TextView title = ((TimelineItemViewHolder)holder).getTitle();

        LinearLayout delete_button = ((TimelineItemViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelineItemViewHolder)holder).getDeleteIcon();

        //작성자 프로필 정보

        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, option_icon, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);


        final ParseObject userOb = postOb.getParseObject("user");

        if(writter_name != null ){

            writter_photo.setImageResource(R.drawable.ic_action_circle_profile);
            writter_name.setText("불러오는 중...");


            functionBase.profileImageLoading(writter_photo, userOb, requestManager);
            functionBase.profileNameLoading(writter_name, userOb);

            if(total_profit_share != null){

                total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

            }

            String postDate = functionBase.dateToString(postOb.getCreatedAt());
            post_updated.setText(postDate);

        }

        if(title != null){

            title.setText(postOb.getString("title"));

        }

        if(type.equals("user")){

            user_info_layout.setVisibility(View.GONE);

        }



        post_body.setText(postOb.getString("body"));



        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);

                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tagGroup.setTags(tagArray);

                tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }

        if(postOb.get("post_type") != null){

            functionBase.generalImageLoading(post_image, postOb, requestManager);

            if(postOb.getString("post_type").equals("post")){

                if(!type.equals("post")){

                    post_type_image.setImageResource(R.drawable.icon_post);

                }



                post_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        post_image.setClickable(false);

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                        builder.title("확인");
                        builder.content("작품을 연재에 추가하시겠습니까?");
                        builder.positiveText("예");
                        builder.negativeText("아니요");
                        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                post_image.setClickable(true);

                            }
                        });

                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                onSelectClick(activity, post_image, parentOb, postOb, new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        post_image.setClickable(true);

                                        TastyToast.makeText(context, "선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        activity.finish();

                                    }
                                });

                            }
                        });
                        builder.show();




                    }
                });



            } else if(postOb.getString("post_type").equals("webtoon") ){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_webtoon);

                }

                post_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        post_image.setClickable(false);

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                        builder.title("확인");
                        builder.content("작품을 연재에 추가하시겠습니까?");
                        builder.positiveText("예");
                        builder.negativeText("아니요");
                        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                post_image.setClickable(true);


                            }
                        });

                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                onSelectClick(activity, post_image, parentOb, postOb, new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        post_image.setClickable(true);

                                        TastyToast.makeText(context, "선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        activity.finish();

                                    }
                                });

                            }
                        });
                        builder.show();



                    }
                });

            } else if(postOb.getString("post_type").equals("album") ){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_album);

                }

                post_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        post_image.setClickable(false);

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                        builder.title("확인");
                        builder.content("작품을 연재에 추가하시겠습니까?");
                        builder.positiveText("예");
                        builder.negativeText("아니요");
                        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                post_image.setClickable(true);

                            }
                        });

                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                onSelectClick(activity, post_image, parentOb, postOb, new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        post_image.setClickable(true);

                                        TastyToast.makeText(context, "선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        activity.finish();

                                    }
                                });

                            }
                        });
                        builder.show();



                    }
                });

            } else if(postOb.getString("post_type").equals("youtube")){


                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_youtube);


                }

                post_body.setText(postOb.getString("title"));
                post_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        post_image.setClickable(false);

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                        builder.title("확인");
                        builder.content("작품을 연재에 추가하시겠습니까?");
                        builder.positiveText("예");
                        builder.negativeText("아니요");
                        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                post_image.setClickable(true);


                            }
                        });

                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                onSelectClick(activity, post_image, parentOb, postOb, new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        post_image.setClickable(true);

                                        TastyToast.makeText(context, "선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        activity.finish();

                                    }
                                });

                            }
                        });
                        builder.show();



                    }
                });

            } else if(postOb.getString("post_type").equals("novel")){

                if(!type.equals("post")){

                    post_type_image_layout.setVisibility(View.VISIBLE);

                    post_type_image.setImageResource(R.drawable.icon_novel);


                }

                String bodyString = "";

                post_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        post_image.setClickable(false);

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                        builder.title("확인");
                        builder.content("작품을 연재에 추가하시겠습니까?");
                        builder.positiveText("예");
                        builder.negativeText("아니요");
                        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                post_image.setClickable(true);


                            }
                        });

                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                onSelectClick(activity, post_image, parentOb, postOb, new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        post_image.setClickable(true);

                                        TastyToast.makeText(context, "선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        activity.finish();

                                    }
                                });

                            }
                        });
                        builder.show();



                    }
                });


                if(postOb.get("title") != null){

                    post_body.setText(postOb.getString("title"));

                } else {

                    post_body.setText(postOb.getString("body"));

                }

            }

        } else {

            Log.d("objectId", postOb.getObjectId() );

        }


        post_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                functionBase.artistPostSetOnClickFunction(postOb , post_body);


            }
        });

        if(post_comment_button != null){

            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }

        if(share_button != null){

            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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
                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }

        }

        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }


        if(option_button != null){

            option_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    functionBase.OptionButtonFunction(context, option_button, currentUser, postOb);

                }
            });


        }

    }


    public void onSelectClick(Activity activity, View v, final ParseObject parentOb, final ParseObject childOb, final SaveCallback saveCallback ) {


        final MaterialDialog.Builder aa = new MaterialDialog.Builder(activity);

        aa.title("메시지");
        aa.content("선택하시면 연재작품에 포함됩니다. 선택하시겠습니까?");
        aa.positiveText("선택");
        aa.showListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {

                ParseRelation<ParseObject> relationOb = parentOb.getRelation("seriese_item");
                relationOb.add(childOb);
                parentOb.increment("seriese_count");
                parentOb.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        childOb.put("seriese_parent", parentOb);
                        childOb.put("level", parentOb.getString("level"));
                        childOb.put("seriese_in", true);
                        childOb.put("open_flag", false);
                        childOb.saveInBackground(saveCallback);


                    }
                });

            }
        });
        aa.cancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
            }
        });

        aa.dismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {




            }
        });
        aa.show();

    }



    private void existItemAddInSeriese(final ParseObject parentOb, final ParseObject childOb, final SaveCallback saveCallback ){

        ParseRelation<ParseObject> relationOb = parentOb.getRelation("seriese_item");
        relationOb.add(childOb);
        parentOb.increment("seriese_count");
        parentOb.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                childOb.put("seriese_parent", parentOb);
                childOb.saveInBackground(saveCallback);

            }
        });

    }



    public void ArtworkAdapterBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager, String type){

        final CircleImageView writter_photo = ((TimelineItemViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelineItemViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemViewHolder)holder).getPostImageLayout();

        LinearLayout post_comment_button = ((TimelineItemViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemViewHolder)holder).getCommentIcon();
        TextView comment_count = ((TimelineItemViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemViewHolder)holder).getRecordButton();
        final ImageView record_icon = ((TimelineItemViewHolder)holder).getRecordIcon();
        final TextView record_count = ((TimelineItemViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemViewHolder)holder).getUserInfoLayout();


        LinearLayout share_button = ((TimelineItemViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelineItemViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelineItemViewHolder)holder).getShareCount();

        LinearLayout post_tag_layout = ((TimelineItemViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((TimelineItemViewHolder)holder).getTagGroup();

        ImageView post_type_image = ((TimelineItemViewHolder)holder).getPostTypeImage();
        LinearLayout post_type_image_layout = ((TimelineItemViewHolder)holder).getPostTypeImageLayout();
        final LinearLayout option_button =((TimelineItemViewHolder)holder).getOptionButton();
        ImageView option_icon = ((TimelineItemViewHolder)holder).getOptionIcon();

        final TextView total_profit_share = ((TimelineItemViewHolder)holder).getTotalProfitShare();
        TextView dday_text = ((TimelineItemViewHolder)holder).getDdayText();
        TextView current_point = ((TimelineItemViewHolder)holder).getCurrentPoint();
        TextView max_point = ((TimelineItemViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelineItemViewHolder)holder).getProgressbar();
        TextView title = ((TimelineItemViewHolder)holder).getTitle();

        LinearLayout delete_button = ((TimelineItemViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelineItemViewHolder)holder).getDeleteIcon();

        //작성자 프로필 정보

        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, option_icon, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);


        final ParseObject userOb = postOb.getParseObject("user");

        if(writter_name != null ){

            writter_photo.setImageResource(R.drawable.ic_action_circle_profile);
            writter_name.setText("불러오는 중...");

            if(type.equals("recent") || type.equals("cm")){

                userOb.fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject fetchedUserOb, ParseException e) {

                        functionBase.profileImageLoading(writter_photo, fetchedUserOb, requestManager);
                        functionBase.profileNameLoading(writter_name, fetchedUserOb);


                        if(total_profit_share != null){

                            total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

                        }


                    }
                });

            } else {

                functionBase.profileImageLoading(writter_photo, userOb, requestManager);

                functionBase.profileNameLoading(writter_name, userOb);

                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + "P");

                }


            }

            String postDate = functionBase.dateToString(postOb.getCreatedAt());
            post_updated.setText(postDate);

        }



        if(title != null){

            title.setText(postOb.getString("title"));

        }




        if(type.equals("user")){

            user_info_layout.setVisibility(View.GONE);

        }


        post_body.setText(postOb.getString("body"));

        if(like_count != null){

            like_count.setText(String.valueOf(postOb.getInt("like_count")));
        }

        if(comment_count != null){

            comment_count.setText( String.valueOf(postOb.getInt("comment_count")) );
        }


        if(postOb.get("post_type") != null){

            if(postOb.getString("content_type").equals("post")){

                post_type_image_layout.setVisibility(View.VISIBLE);
                post_type_image.setImageResource(R.drawable.icon_post);


            } else if(postOb.getString("content_type").equals("album")){

                post_type_image_layout.setVisibility(View.VISIBLE);
                post_type_image.setImageResource(R.drawable.icon_album);

            } else if(postOb.getString("content_type").equals("novel")){

                post_type_image_layout.setVisibility(View.VISIBLE);
                post_type_image.setImageResource(R.drawable.icon_novel);

            } else if(postOb.getString("content_type").equals("webtoon")){

                post_type_image_layout.setVisibility(View.VISIBLE);
                post_type_image.setImageResource(R.drawable.icon_webtoon);

            } else if(postOb.getString("content_type").equals("youtube")){

                post_type_image_layout.setVisibility(View.VISIBLE);
                post_type_image.setImageResource(R.drawable.icon_youtube);

            }

            functionBase.generalImageLoading(post_image, postOb, requestManager);

        } else {

            Log.d("objectId", postOb.getObjectId() );

        }


        post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, CommercialActivity.class);
                intent.putExtra("serieseId", postOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


        post_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                Intent intent = new Intent(context, CommercialActivity.class);
                intent.putExtra("serieseId", postOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });



        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }


        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });

                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }

    }

    public void WebtoonSeriesePostBuilder(final ParseObject postOb, RecyclerView.ViewHolder holder, final RequestManager requestManager){

        final CircleImageView writter_photo = ((TimelineItemViewHolder)holder).getWritterPhoto();
        final TextView writter_name = ((TimelineItemViewHolder)holder).getWritterName();
        TextView post_updated = ((TimelineItemViewHolder)holder).getPostUpdated();
        final TextView post_body = ((TimelineItemViewHolder)holder).getPostBody();
        final ImageView post_image = ((TimelineItemViewHolder)holder).getPostImage();
        LinearLayout post_image_layout = ((TimelineItemViewHolder)holder).getPostImageLayout();

        LinearLayout post_comment_button = ((TimelineItemViewHolder)holder).getPostCommentButton();
        ImageView comment_icon = ((TimelineItemViewHolder)holder).getCommentIcon();
        final TextView comment_count = ((TimelineItemViewHolder)holder).getCommentCount();

        final LinearLayout post_like_button = ((TimelineItemViewHolder)holder).getPostLikeButton();
        final ImageView like_icon = ((TimelineItemViewHolder)holder).getLikeIcon();
        final TextView like_count = ((TimelineItemViewHolder)holder).getLikeCount();

        final LinearLayout record_button = ((TimelineItemViewHolder)holder).getRecordButton();
        ImageView record_icon = ((TimelineItemViewHolder)holder).getRecordIcon();
        TextView record_count = ((TimelineItemViewHolder)holder).getRecordCount();

        LinearLayout request_button = ((TimelineItemViewHolder)holder).getRequestButton();
        ImageView request_icon = ((TimelineItemViewHolder)holder).getRequestIcon();

        LinearLayout function_button= ((TimelineItemViewHolder)holder).getFunctionButton();
        LinearLayout user_info_layout = ((TimelineItemViewHolder)holder).getUserInfoLayout();


        LinearLayout share_button = ((TimelineItemViewHolder)holder).getShareButton();
        ImageView share_icon = ((TimelineItemViewHolder)holder).getShareIcon();
        TextView share_count = ((TimelineItemViewHolder)holder).getShareCount();

        LinearLayout post_tag_layout = ((TimelineItemViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((TimelineItemViewHolder)holder).getTagGroup();

        ImageView post_type_image = ((TimelineItemViewHolder)holder).getPostTypeImage();
        LinearLayout post_type_image_layout = ((TimelineItemViewHolder)holder).getPostTypeImageLayout();

        LinearLayout seriese_icon_layout = ((TimelineItemViewHolder)holder).getSerieseIconLayout();
        ImageView seriese_icon = ((TimelineItemViewHolder)holder).getSerieseIcon();
        TextView seriese_text = ((TimelineItemViewHolder)holder).getSerieseText();

        final LinearLayout option_button =((TimelineItemViewHolder)holder).getOptionButton();
        ImageView option_icon = ((TimelineItemViewHolder)holder).getOptionIcon();

        final TextView total_profit_share = ((TimelineItemViewHolder)holder).getTotalProfitShare();
        TextView dday_text = ((TimelineItemViewHolder)holder).getDdayText();
        TextView current_point = ((TimelineItemViewHolder)holder).getCurrentPoint();
        TextView max_point = ((TimelineItemViewHolder)holder).getMaxPoint();
        RoundCornerProgressBar progressbar = ((TimelineItemViewHolder)holder).getProgressbar();
        TextView title = ((TimelineItemViewHolder)holder).getTitle();

        final LinearLayout delete_button = ((TimelineItemViewHolder)holder).getDeleteButton();
        ImageView delete_icon = ((TimelineItemViewHolder)holder).getDeleteIcon();

        TextView post_detail = ((TimelineItemViewHolder)holder).getPostDetail();
        TextView last_post = ((TimelineItemViewHolder)holder).getLastPost();

        final FunctionBase functionBase = new FunctionBase(context);

        //작성자 프로필 정보

        functionBase.postIconColorFilterInit(comment_icon, like_icon, record_icon , request_icon, share_icon, option_icon, true);
        functionBase.postCounterColorFilterInit(comment_count, like_count, record_count, share_count, true);

        final ParseObject userOb = postOb.getParseObject("user");

        if(post_detail != null){

            if(postOb.get("body") != null){

                post_detail.setText(postOb.getString("body"));

            } else {

                post_detail.setText("입력 안됨");

            }

        }

        if(last_post != null){


        }

        if(writter_name != null ){

            functionBase.profileNameLoading(writter_name, userOb);

        }



        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    postOb.put("status", false);
                    postOb.put("lastAction", "delete");
                    postOb.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){



                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }
            });

        }




        if(dday_text != null){

            //dday maker
            Date tagetDate = postOb.getDate("endDate");
            String progressStatus = postOb.getString("progress");



            int dday = functionBase.dday(tagetDate.getYear() + 1900, tagetDate.getMonth() , tagetDate.getDate());

            if(progressStatus != null){

                if(progressStatus.equals("start")){

                    if(dday < 0){

                        dday_text.setText("마감");

                    } else {

                        dday_text.setText(String.valueOf(dday));

                    }



                } else if(progressStatus.equals("production")) {

                    dday_text.setText("제작중");

                } else{

                    dday_text.setText("종료");

                }

            }

        }

        if(title != null){

            title.setText(postOb.getString("title"));

        }

        if(max_point != null){

            max_point.setText(functionBase.makeComma(postOb.getInt("target_amount")) + " BOX" );
            current_point.setText(functionBase.makeComma(postOb.getInt("achieve_amount")) + " BOX");

            int currentProgress = functionBase.progressMaker(postOb.getInt("achieve_amount"), postOb.getInt("target_amount"));

            Log.d("currentProgress", String.valueOf(currentProgress));

            if(currentProgress >100){

                currentProgress = 100;

            }

            progressbar.setProgress(currentProgress);

        }




        if(post_body != null){

            if(postOb.get("body") != null){

                post_body.setText(postOb.getString("body"));
            }

        }



        if(like_count != null){

            like_count.setText(String.valueOf(postOb.getInt("like_count")));
        }

        if(comment_count != null){

            comment_count.setText( functionBase.makeComma(postOb.getInt("comment_count")) );

        }


        if(post_tag_layout != null){

            if(postOb.get("tag_array") != null){

                post_tag_layout.setVisibility(View.VISIBLE);


                String[] tagArray = functionBase.jsonArrayToStringArray( postOb.getJSONArray("tag_array") );

                tagGroup.setTags(tagArray);

                tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        Intent intent = new Intent(context, SearchResultActivity.class);
                        intent.putExtra("query", tag);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            } else {

                post_tag_layout.setVisibility(View.GONE);

            }

        }

        if(postOb.get("post_type") != null){

            functionBase.chargeFollowPatronCheck( postOb, post_image);
            functionBase.artistPostBadgeBuilder(postOb, post_type_image_layout, post_type_image, seriese_icon_layout, seriese_icon, seriese_text);
            functionBase.generalImageLoading(post_image, postOb, requestManager);

            if(postOb.getString("post_type").equals("youtube")){

                post_body.setText(postOb.getString("title"));

            } else if(postOb.getString("post_type").equals("novel")){

                String bodyString = "";

                if(postOb.getString("progress").equals("start")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필을 시작했습니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("open")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "'이 오픈 됐습니다. 많은 관심 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                } else if(postOb.getString("progress").equals("finish")){

                    bodyString += "웹소설 '" + postOb.getString("title") + "' 집필이 완료되었습니다. 많은 구독 부탁 드립니다. \n";
                    bodyString += "[" + postOb.getString("body") + "]";

                }

                post_body.setText(bodyString);

            } else if(postOb.getString("post_type").equals("seriese")){

                String bodyString = "";

                if(postOb.getInt("seriese_count")==0){

                    bodyString += "[준비중]";
                    bodyString += postOb.getString("title");

                } else {

                    bodyString += postOb.getString("title");

                }

                post_body.setText(bodyString);


            } else if(postOb.getString("post_type").equals("event")){

                if(writter_name != null){

                    writter_name.setText("이벤트의 요정");

                }


                functionBase.generalImageLoading(post_image, postOb, requestManager);
                post_body.setText(postOb.getString("body"));


            } else if(postOb.getString("post_type").equals("notice")){

                if(writter_name != null){

                    writter_name.setText("공지사항의 요정");

                }

                requestManager
                        .load(R.drawable.icon_notice_profile)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .apply(RequestOptions.placeholderOf(R.drawable.image_background_500))
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(writter_photo);


                requestManager
                        .load(R.drawable.icon_notice_banner)
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                        .apply(RequestOptions.placeholderOf(R.drawable.image_background_500))
                        .transition(new DrawableTransitionOptions().crossFade())
                        .into(post_image);

                post_body.setText(postOb.getString("body"));

            }

        } else {

            Log.d("objectId", postOb.getObjectId() );

        }

        if(post_body != null){

            post_body.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    functionBase.artistPostSetOnClickFunction(postOb , post_body);

                }
            });

        }


        if(post_comment_button != null){



            post_comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

        if(user_info_layout != null){

            user_info_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("userId", userOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(intent);

                }
            });

        }

        if(share_button != null){

            share_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder((AppCompatActivity)context);

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
                            params.put("shareObId", postOb.getObjectId() );

                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

                            ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText(context, "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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
            });

            if(share_count != null){

                share_count.setText( String.valueOf(postOb.getInt("share_count")) );

            }

        }


        if(post_like_button != null){

            if(currentUser != null){

                if(postOb.getParseObject("user").getObjectId().equals(currentUser.getObjectId())){

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, LikeUserListActivity.class);
                            intent.putExtra("postId", postOb.getObjectId());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);

                        }
                    });



                } else {

                    FunctionLikeDislike likeFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                    likeFunction.likeButtonStatusCheck();

                    post_like_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(context, postOb, post_like_button, like_icon, like_count);
                            likeClickFunction.likeButtonFunction();

                        }
                    });

                }

            } else {


                post_like_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }


        }




        if(option_button != null){

            option_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    functionBase.OptionButtonFunction(context, option_button, currentUser, postOb);

                }
            });


        }

    }

}
