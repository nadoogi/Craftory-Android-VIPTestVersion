package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
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
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FundingItemDetailForSaleActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.FundingJoinActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronUserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionLikeDislike;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.PatronDetailHeaderViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelinePatronViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class PatronDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private ArrayList<ParseObject> items;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages;
    private int objectsPerPage = 50;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;
    private FunctionBase functionBase;

    private int HeaderViewType = 0;
    private int ContentViewType = 1;
    private int ListViewType = 2;
    private ParseObject parentObject;
    private AppCompatActivity activity;

    public PatronDetailAdapter(AppCompatActivity activity, Context context, RequestManager requestManager, final ParseObject parentOb ) {

        this.requestManager = requestManager;
        this.context = context;
        this.activity = activity;
        this.currentUser = ParseUser.getCurrentUser();
        this.parentObject = parentOb;
        this.items = new ArrayList<>();
        this.objectPages =  new ArrayList<>();
        this.functionBase = new FunctionBase(activity, context);

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                query.include("user");
                query.include("item");
                query.whereEqualTo("status", true);
                query.whereEqualTo("post_type", "patron");
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

            if(parentObject.getString("content_type").equals("goods")){

                headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patron_detail_header_goods, parent, false);

                return new PatronDetailHeaderViewHolder(headerView);

            } else {

                headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_patron_detail_header, parent, false);

                return new PatronDetailHeaderViewHolder(headerView);

            }

        } else {

            View timelineView;

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_patron2, parent, false);

            return new TimelinePatronViewHolder(timelineView);

        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == 0){

            TextView current_point = ((PatronDetailHeaderViewHolder)holder).getCurrentPoint();
            TextView open_type = ((PatronDetailHeaderViewHolder)holder).getOpenType();
            TextView max_point_onprogress = ((PatronDetailHeaderViewHolder)holder).getMaxPointOnprogress();
            RoundCornerProgressBar progressbar= ((PatronDetailHeaderViewHolder)holder).getProgressbar();
            TextView total_profit_share= ((PatronDetailHeaderViewHolder)holder).getTotalProfitShare();
            TextView profit_share_ratio= ((PatronDetailHeaderViewHolder)holder).getProfitShareRatio();
            //TextView current_point_oninfo= ((PatronDetailHeaderViewHolder)holder).getCurrentPointOnInfo();
            TextView current_point_onprogress= ((PatronDetailHeaderViewHolder)holder).getMaxPointOnprogress();

            TextView patron_type= ((PatronDetailHeaderViewHolder)holder).getPatronType();
            TextView min_box= ((PatronDetailHeaderViewHolder)holder).getMinBox();
            LinearLayout user_info_layout= ((PatronDetailHeaderViewHolder)holder).getUserInfoLayout();

            CircleImageView user_image_1= ((PatronDetailHeaderViewHolder)holder).getUserImage1();
            CircleImageView user_image_2= ((PatronDetailHeaderViewHolder)holder).getUserImage2();
            CircleImageView user_image_3= ((PatronDetailHeaderViewHolder)holder).getUserImage3();

            TextView user_name_1= ((PatronDetailHeaderViewHolder)holder).getUserName1();
            TextView user_name_2= ((PatronDetailHeaderViewHolder)holder).getUserName2();
            TextView user_name_3= ((PatronDetailHeaderViewHolder)holder).getUserName3();

            TextView post_date_1= ((PatronDetailHeaderViewHolder)holder).getPostDate1();
            TextView post_date_2= ((PatronDetailHeaderViewHolder)holder).getPostDate2();
            TextView post_date_3= ((PatronDetailHeaderViewHolder)holder).getPostDate3();

            TextView comment_body_1= ((PatronDetailHeaderViewHolder)holder).getCommentBody1();
            TextView comment_body_2= ((PatronDetailHeaderViewHolder)holder).getCommentBody2();
            TextView comment_body_3= ((PatronDetailHeaderViewHolder)holder).getCommentBody3();


            LinearLayout comment_1= ((PatronDetailHeaderViewHolder)holder).getComment1();
            LinearLayout comment_2= ((PatronDetailHeaderViewHolder)holder).getComment2();
            LinearLayout comment_3= ((PatronDetailHeaderViewHolder)holder).getComment3();

            LinearLayout no_comment= ((PatronDetailHeaderViewHolder)holder).getNoComment();

            LinearLayout comment_layout= ((PatronDetailHeaderViewHolder)holder).getCommentLayout();

            EditText comment_input= ((PatronDetailHeaderViewHolder)holder).getCommentInput();

            LinearLayout patron_user_button= ((PatronDetailHeaderViewHolder)holder).getPatronUserButton();
            TextView pv_count = ((PatronDetailHeaderViewHolder)holder).getPvCount();

            LinearLayout join_button = ((PatronDetailHeaderViewHolder)holder).getJoinButton();
            final TextView current_box = ((PatronDetailHeaderViewHolder)holder).getCurrentBox();


            //header
            TextView patron_title= ((PatronDetailHeaderViewHolder)holder).getPatronTitle();
            ImageView patron_image= ((PatronDetailHeaderViewHolder)holder).getPatronImage();
            TextView patron_body= ((PatronDetailHeaderViewHolder)holder).getPatronBody();
            TextView max_point= ((PatronDetailHeaderViewHolder)holder).getMaxPoint();

            CircleImageView writter_photo= ((PatronDetailHeaderViewHolder)holder).getWritterPhoto();
            TextView writter_name= ((PatronDetailHeaderViewHolder)holder).getWritterName();
            TextView patron_updated= ((PatronDetailHeaderViewHolder)holder).getPatronUpdated();
            final LinearLayout like_button= ((PatronDetailHeaderViewHolder)holder).getLikeButton();
            final ImageView like_icon= ((PatronDetailHeaderViewHolder)holder).getLikeIcon();
            final TextView like_count= ((PatronDetailHeaderViewHolder)holder).getLikeCount();
            ImageView comment_icon= ((PatronDetailHeaderViewHolder)holder).getCommentIcon();
            TextView comment_count= ((PatronDetailHeaderViewHolder)holder).getCommentCount();
            ImageView share_icon= ((PatronDetailHeaderViewHolder)holder).getShareIcon();

            TagGroup tag_group= ((PatronDetailHeaderViewHolder)holder).getTagGroup();

            final TextView achieve_ratio= ((PatronDetailHeaderViewHolder)holder).getAchieveRatio();
            TextView patron_count= ((PatronDetailHeaderViewHolder)holder).getPatronCount();


            TextView patron_detail_info= ((PatronDetailHeaderViewHolder)holder).getPartronDetailInfo();
            LinearLayout patron_detail_info_edit= ((PatronDetailHeaderViewHolder)holder).getPatronDetailInfoEdit();

            LinearLayout share_button= ((PatronDetailHeaderViewHolder)holder).getShareButton();
            TextView share_count = ((PatronDetailHeaderViewHolder)holder).getShareCount();
            LinearLayout patron_tag_layout= ((PatronDetailHeaderViewHolder)holder).getPatronTagLayout();
            TextView current_point_oninfo = ((PatronDetailHeaderViewHolder)holder).getCurrentPointOnInfo();

            TextView price = ((PatronDetailHeaderViewHolder)holder).getPrice();

            ImageView item_image = ((PatronDetailHeaderViewHolder)holder).getItemImage();
            TextView item_name = ((PatronDetailHeaderViewHolder)holder).getItemName();

            RecyclerView detail_info_list = ((PatronDetailHeaderViewHolder)holder).getDetailInfoList();

            ImageView immoticon1 = ((PatronDetailHeaderViewHolder)holder).getImmoticon1();
            ImageView immoticon2 = ((PatronDetailHeaderViewHolder)holder).getImmoticon2();
            ImageView immoticon3 = ((PatronDetailHeaderViewHolder)holder).getImmoticon3();

            join_button.setVisibility(View.GONE);

            patron_image.setFocusableInTouchMode(true);
            patron_image.requestFocus();


            if(parentObject.getString("content_type").equals("goods")){

                final ParseObject itemOb = parentObject.getParseObject("item");

                functionBase.generalImageLoading(item_image, itemOb, requestManager);

                item_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, FundingItemDetailForSaleActivity.class);
                        intent.putExtra("objectId", itemOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

                item_name.setText( itemOb.getString("name") );

                price.setText(functionBase.makeComma( parentObject.getInt("price") ) + " BOX");

                //current_point_oninfo.setText( functionBase.makeComma( parentObject.getInt("patron_count") ) + " 명" );
                current_point_onprogress.setText(functionBase.makeComma( parentObject.getInt("patron_count") ) + " 명");
                max_point.setText(functionBase.makeComma(parentObject.getInt("target_amount")) + " 명");
                max_point_onprogress.setText(functionBase.makeComma(parentObject.getInt("target_amount")) + " 명");

                if(parentObject.getInt("target_amount") == 0){

                    achieve_ratio.setText( String.valueOf( 0 ) + "%" );

                } else {

                    achieve_ratio.setText( String.valueOf( parentObject.getInt("patron_count") / parentObject.getInt("target_amount") * 100 ) + "%" );

                }

                int currentProgress = functionBase.progressMaker( parentObject.getInt("patron_count"), parentObject.getInt("target_amount"));

                int progressBarInt = currentProgress;

                if(currentProgress > 100){

                    progressBarInt = 100;

                }

                progressbar.setProgress(progressBarInt);
                achieve_ratio.setText(String.valueOf(currentProgress) + "%");

                LinearLayoutManager layoutManagerForDetail = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
                detail_info_list.setLayoutManager(layoutManagerForDetail);
                detail_info_list.setHasFixedSize(true);

                FundingMarketItemEditorAdapter fundingMarketItemEditorAdapter = new FundingMarketItemEditorAdapter(context, requestManager, parentObject);
                detail_info_list.setAdapter(fundingMarketItemEditorAdapter);

                patron_detail_info.setVisibility(View.GONE);

                if(parentObject.getString("progress").equals("production")){

                    join_button.setVisibility(View.GONE);

                } else {

                    join_button.setVisibility(View.VISIBLE);

                }

                join_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, FundingJoinActivity.class);
                        intent.putExtra("patronId", parentObject.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

                if(currentUser != null){

                    currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject pointOb, ParseException e) {

                            if(e==null){

                                current_box.setText( "보유 BOX : (" + functionBase.makeComma( pointOb.getInt("current_point") ) + " BOX )" );

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });


                }

                pv_count.setText(functionBase.makeComma(parentObject.getInt("pv_count")));

            } else {

                if(open_type != null){

                    if(parentObject.get("patron_flag") != null){

                        if(parentObject.getBoolean("patron_flag")){

                            open_type.setText("후원자 독점");

                        } else {

                            open_type.setText("전체 공개");
                        }


                    } else {

                        if(parentObject.get("open_type") != null){

                            switch (parentObject.getString("open_type")){

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


                if(min_box != null){

                    min_box.setText(functionBase.makeComma(parentObject.getInt("min_box")));

                }


                if(patron_detail_info != null){

                    if(parentObject.get("reward_exist") != null){

                        if(parentObject.getBoolean("reward_exist")){

                            patron_detail_info.setText("있음");

                        } else {

                            patron_detail_info.setText("없음");

                        }

                    }

                }

                current_point_oninfo.setText( functionBase.makeComma( parentObject.getInt("achieve_amount") ) + " BOX" );
                current_point_onprogress.setText(functionBase.makeComma( parentObject.getInt("achieve_amount") ) + " BOX");
                max_point.setText(functionBase.makeComma(parentObject.getInt("target_amount")) + " BOX");
                max_point_onprogress.setText(functionBase.makeComma(parentObject.getInt("target_amount")) + " BOX");

                if(profit_share_ratio != null){

                    profit_share_ratio.setText(String.valueOf(parentObject.getInt("profit_share_ratio")) + "%");

                }

                if(parentObject.getInt("target_amount") == 0){

                    achieve_ratio.setText( String.valueOf( 0 ) + "%" );

                } else {

                    achieve_ratio.setText( String.valueOf( parentObject.getInt("achieve_amount") / parentObject.getInt("target_amount") * 100 ) + "%" );

                }

                int currentProgress = functionBase.progressMaker( parentObject.getInt("achieve_amount"), parentObject.getInt("target_amount"));

                int progressBarInt = currentProgress;

                if(currentProgress > 100){

                    progressBarInt = 100;

                }

                progressbar.setProgress(progressBarInt);
                achieve_ratio.setText(String.valueOf(currentProgress) + "%");


            }

            patron_count.setText( functionBase.makeComma( parentObject.getInt("patron_count") ) + "명" );

            comment_input.setFocusable(false);

            patron_user_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronUserActivity.class);
                    intent.putExtra("patronId", parentObject.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });




            if(patron_type != null){

                if(parentObject.get("content_type") != null){

                    if(parentObject.getString("content_type").equals("commission")){

                        patron_type.setText( "커미션" );

                    } else if(parentObject.getString("content_type").equals("goods")){

                        patron_type.setText( "굿즈제작" );

                    } else if(parentObject.getString("content_type").equals("extra")){

                        patron_type.setText( "기타" );

                    } else if(parentObject.getString("content_type").equals("post")){

                        patron_type.setText( "포스트" );

                    } else if(parentObject.getString("content_type").equals("webtoon")){

                        patron_type.setText( "웹툰" );

                    } else if(parentObject.getString("content_type").equals("album")){

                        patron_type.setText( "사진첩" );

                    } else if(parentObject.getString("content_type").equals("novel")){

                        patron_type.setText( "웹소설" );

                    } else if(parentObject.getString("content_type").equals("youtube")){

                        patron_type.setText( "영상" );

                    }



                }

            }

            //functionBase.patronManagerButtonSetOnclickFunction(patron_detail_info_edit, patron_stop, patron_cancel, patron_result_summit, patron_result_summit_text , patron_withdraw, patron_withdraw_text , parentObject);

            comment_layout.setVisibility(View.GONE);

            FunctionComment functionComment = new FunctionComment(context);
            functionComment.CommentPreviewFunctionBuilder(user_image_1, user_image_2, user_image_3, user_name_1, user_name_2, user_name_3, post_date_1, post_date_2, post_date_3, comment_body_1, comment_body_2, comment_body_3, comment_1, comment_2,comment_3, comment_layout, no_comment, parentObject, requestManager, immoticon1, immoticon2, immoticon3);

            comment_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, CommentActivity.class);
                    intent.putExtra("postId", parentObject.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

            patron_title.setText(parentObject.getString("title"));
            patron_body.setText(parentObject.getString("body"));

            functionBase.postIconColorFilterInit(comment_icon, like_icon, null , null, share_icon, null ,true);
            functionBase.postCounterColorFilterInit(comment_count, like_count, null, null, true);
            functionBase.generalImageLoading(patron_image, parentObject, requestManager);

            if(parentObject.get("patron_detail_info") == null){

                patron_detail_info.setText(R.string.patron_detail_default_text);

            } else {

                patron_detail_info.setText(parentObject.getString("patron_detail_info"));

            }

            tag_group.setTags( functionBase.jsonArrayToStringArray(parentObject.getJSONArray("tag_array")) );



            if( parentObject.get("user") != null){

                final ParseObject userOb = parentObject.getParseObject("user");

                functionBase.profileImageLoading(writter_photo, userOb, requestManager);
                functionBase.profileNameLoading(writter_name, userOb);

                patron_updated.setText(functionBase.dateToString(userOb.getCreatedAt()));

                if(total_profit_share != null){

                    total_profit_share.setText(functionBase.makeComma( userOb.getInt("total_profit_share") ) + " BOX");

                }

                user_info_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, UserActivity.class);
                        intent.putExtra("userId", userOb.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                        context.startActivity(intent);

                    }
                });

            }


            like_count.setText( functionBase.makeComma(parentObject.getInt("like_count")) );

            FunctionLikeDislike functionLikeDislike1 = new FunctionLikeDislike(context, parentObject, like_button, like_icon, like_count);
            functionLikeDislike1.likeButtonStatusCheck();

            like_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    like_button.setClickable(false);

                    FunctionLikeDislike functionLikeDislike = new FunctionLikeDislike(context, parentObject, like_button, like_icon, like_count);
                    functionLikeDislike.likeButtonFunction();


                }
            });

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

        } else {

            ParseObject timelineOb = getItem(position);

            FunctionPost functionPost = new FunctionPost(context);
            functionPost.TimelineArtistPatronAdapterBuilder( timelineOb, holder, requestManager, "recommend");

        }

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {

        //return items.size() + 2;

        return items.size()+1;

    }


    public ParseObject getItem(int position) {
        //return items.get(position-2);

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
