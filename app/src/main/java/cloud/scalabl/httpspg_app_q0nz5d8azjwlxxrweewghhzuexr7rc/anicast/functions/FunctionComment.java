package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentDefaultAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentFavorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentHeaderParseQueryAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentParseFavorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentParseQueryAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentRecentAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentSocialFavorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentSocialRecentAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.CommentViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.PostDetailCommentViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 5. 24..
 */

public class FunctionComment {

    private Context context;
    private ParseUser currentUser;
    private FunctionBase functionBase;

    public FunctionComment(Context context) {

        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = new FunctionBase(context);

    }


    public void CommentPreviewBuilder(RecyclerView.ViewHolder holder, final ParseObject parentObject, final RequestManager requestManager){


        final CircleImageView user_image_1 = ((PostDetailCommentViewHolder)holder).getUserImage1();
        final CircleImageView user_image_2 = ((PostDetailCommentViewHolder)holder).getUserImage2();
        final CircleImageView user_image_3 = ((PostDetailCommentViewHolder)holder).getUserImage3();

        final TextView user_name_1 = ((PostDetailCommentViewHolder)holder).getUserName1();
        final TextView user_name_2 = ((PostDetailCommentViewHolder)holder).getUserName2();
        final TextView user_name_3 = ((PostDetailCommentViewHolder)holder).getUserName3();

        final TextView post_date_1 = ((PostDetailCommentViewHolder)holder).getPostDate1();
        final TextView post_date_2 = ((PostDetailCommentViewHolder)holder).getPostDate2();
        final TextView post_date_3 = ((PostDetailCommentViewHolder)holder).getPostDate3();

        final TextView comment_body_1 = ((PostDetailCommentViewHolder)holder).getCommentBody1();
        final TextView comment_body_2 = ((PostDetailCommentViewHolder)holder).getCommentBody2();
        final TextView comment_body_3 = ((PostDetailCommentViewHolder)holder).getCommentBody3();


        final LinearLayout comment_1 = ((PostDetailCommentViewHolder)holder).getComment1();
        final LinearLayout comment_2 = ((PostDetailCommentViewHolder)holder).getComment2();
        final LinearLayout comment_3 = ((PostDetailCommentViewHolder)holder).getComment3();

        ImageView immoticon1 = ((PostDetailCommentViewHolder)holder).getImmoticon1();
        ImageView immoticon2 = ((PostDetailCommentViewHolder)holder).getImmoticon2();
        ImageView immoticon3 = ((PostDetailCommentViewHolder)holder).getImmoticon3();


        LinearLayout input = ((PostDetailCommentViewHolder)holder).getInput();

        EditText comment_input = ((PostDetailCommentViewHolder)holder).getCommentInput();

        comment_input.setFocusable(false);
        comment_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, CommentActivity.class);
                intent.putExtra("postId", parentObject.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        final LinearLayout comment_layout = ((PostDetailCommentViewHolder)holder).getCommentLayout();

        comment_layout.setVisibility(View.GONE);
        immoticon1.setVisibility(View.GONE);
        immoticon2.setVisibility(View.GONE);
        immoticon3.setVisibility(View.GONE);

        final LinearLayout no_comment = ((PostDetailCommentViewHolder)holder).getNoComment();

        CommentPreviewFunctionBuilder(user_image_1, user_image_2, user_image_3, user_name_1, user_name_2, user_name_3, post_date_1, post_date_2, post_date_3, comment_body_1, comment_body_2, comment_body_3, comment_1, comment_2,comment_3, comment_layout, no_comment, parentObject, requestManager, immoticon1, immoticon2, immoticon3);


    }

    public void CommentPreviewFunctionBuilder(final CircleImageView user_image_1, final CircleImageView user_image_2, final CircleImageView user_image_3, final TextView user_name_1, final TextView user_name_2, final TextView user_name_3, final TextView post_date_1, final TextView post_date_2, final TextView post_date_3, final TextView comment_body_1, final TextView comment_body_2, final TextView comment_body_3, final LinearLayout comment_1, final LinearLayout comment_2, final LinearLayout comment_3, final LinearLayout comment_layout, final LinearLayout no_comment, final ParseObject parentObject, final RequestManager requestManager , final ImageView immoticon1, final ImageView immoticon2, final ImageView immoticon3 ){

        ParseQuery commentQuery = ParseQuery.getQuery("Comment");

        commentQuery.whereEqualTo("status", true);
        commentQuery.whereEqualTo("parentOb", parentObject);
        commentQuery.setLimit(3);
        commentQuery.include("user");
        commentQuery.include("poke_item");
        commentQuery.orderByDescending("createdAt");
        commentQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> commentObs, ParseException e) {

                if(e==null){

                    if(commentObs.size() == 0){

                        no_comment.setVisibility(View.VISIBLE);
                        comment_1.setVisibility(View.GONE);
                        comment_2.setVisibility(View.GONE);
                        comment_3.setVisibility(View.GONE);

                        comment_layout.setVisibility(View.VISIBLE);

                    } else if(commentObs.size() == 1) {

                        no_comment.setVisibility(View.GONE);
                        comment_1.setVisibility(View.VISIBLE);
                        comment_2.setVisibility(View.GONE);
                        comment_3.setVisibility(View.GONE);



                        ParseObject commentOb1 = commentObs.get(0);
                        ParseObject userOb1 = commentOb1.getParseObject("user");
                        ParseObject pokeItem1 = commentOb1.getParseObject("poke_item");

                        String date1 = functionBase.dateToStringForDisplay(commentOb1.getCreatedAt());
                        post_date_1.setText(date1);

                        functionBase.profileImageLoading(user_image_1, userOb1, requestManager);
                        functionBase.profileNameLoading(user_name_1, userOb1);

                        if(pokeItem1 != null){

                            immoticon1.setVisibility(View.VISIBLE);
                            functionBase.generalImageLoading(immoticon1, pokeItem1, requestManager);

                            comment_body_1.setVisibility(View.GONE);

                        } else {

                            immoticon1.setVisibility(View.GONE);
                            String body1 = commentOb1.getString("body");
                            comment_body_1.setText(body1);

                        }

                        comment_layout.setVisibility(View.VISIBLE);

                    } else if(commentObs.size() == 2) {

                        no_comment.setVisibility(View.GONE);
                        comment_1.setVisibility(View.VISIBLE);
                        comment_2.setVisibility(View.VISIBLE);
                        comment_3.setVisibility(View.GONE);


                        ParseObject commentOb1 = commentObs.get(0);
                        ParseObject userOb1 = commentOb1.getParseObject("user");
                        ParseObject pokeItem1 = commentOb1.getParseObject("poke_item");

                        String date1 = functionBase.dateToStringForDisplay(commentOb1.getCreatedAt());
                        post_date_1.setText(date1);

                        functionBase.profileImageLoading(user_image_1, userOb1, requestManager);
                        functionBase.profileNameLoading(user_name_1, userOb1);

                        if(pokeItem1 != null){


                            functionBase.generalImageLoading(immoticon1, pokeItem1, requestManager);
                            comment_body_1.setVisibility(View.GONE);
                            immoticon1.setVisibility(View.VISIBLE);

                        } else {

                            immoticon1.setVisibility(View.GONE);
                            comment_1.setVisibility(View.VISIBLE);
                            String body1 = commentOb1.getString("body");
                            comment_body_1.setText(body1);

                        }


                        ParseObject commentOb2 = commentObs.get(1);
                        ParseObject userOb2 = commentOb2.getParseObject("user");
                        ParseObject pokeItem2 = commentOb2.getParseObject("poke_item");

                        String date2 = functionBase.dateToStringForDisplay(commentOb2.getCreatedAt());
                        post_date_2.setText(date2);

                        functionBase.profileImageLoading(user_image_2, userOb2, requestManager);
                        functionBase.profileNameLoading(user_name_2, userOb2);



                        if(pokeItem2 != null){

                            functionBase.generalImageLoading(immoticon2, pokeItem2, requestManager);
                            immoticon2.setVisibility(View.VISIBLE);
                            comment_body_2.setVisibility(View.GONE);

                        } else {

                            immoticon2.setVisibility(View.GONE);
                            comment_body_2.setVisibility(View.VISIBLE);
                            String body2 = commentOb2.getString("body");
                            comment_body_2.setText(body2);

                        }

                        comment_layout.setVisibility(View.VISIBLE);

                    } else {

                        no_comment.setVisibility(View.GONE);
                        comment_1.setVisibility(View.VISIBLE);
                        comment_2.setVisibility(View.VISIBLE);
                        comment_3.setVisibility(View.VISIBLE);

                        ParseObject commentOb1 = commentObs.get(0);
                        ParseObject userOb1 = commentOb1.getParseObject("user");
                        ParseObject pokeItem1 = commentOb1.getParseObject("poke_item");

                        String date1 = functionBase.dateToStringForDisplay(commentOb1.getCreatedAt());
                        post_date_1.setText(date1);

                        functionBase.profileImageLoading(user_image_1, userOb1, requestManager);
                        functionBase.profileNameLoading(user_name_1, userOb1);

                        if(pokeItem1 != null){


                            functionBase.generalImageLoading(immoticon1, pokeItem1, requestManager);
                            comment_body_1.setVisibility(View.GONE);
                            immoticon1.setVisibility(View.VISIBLE);

                        } else {

                            immoticon1.setVisibility(View.GONE);
                            comment_body_1.setVisibility(View.VISIBLE);
                            String body1 = commentOb1.getString("body");
                            comment_body_1.setText(body1);

                        }

                        ParseObject commentOb2 = commentObs.get(1);
                        ParseObject userOb2 = commentOb2.getParseObject("user");
                        ParseObject pokeItem2 = commentOb2.getParseObject("poke_item");

                        String date2 = functionBase.dateToStringForDisplay(commentOb2.getCreatedAt());
                        post_date_2.setText(date2);

                        functionBase.profileImageLoading(user_image_2, userOb2, requestManager);
                        functionBase.profileNameLoading(user_name_2, userOb2);

                        if(pokeItem2 != null){

                            functionBase.generalImageLoading(immoticon2, pokeItem2, requestManager);
                            immoticon2.setVisibility(View.VISIBLE);
                            comment_body_2.setVisibility(View.GONE);

                        } else {

                            immoticon2.setVisibility(View.GONE);
                            comment_body_2.setVisibility(View.VISIBLE);
                            String body2 = commentOb2.getString("body");
                            comment_body_2.setText(body2);

                        }

                        ParseObject commentOb3 = commentObs.get(2);
                        ParseObject userOb3 = commentOb3.getParseObject("user");
                        ParseObject pokeItem3 = commentOb3.getParseObject("poke_item");

                        String date3 = functionBase.dateToStringForDisplay(commentOb3.getCreatedAt());
                        post_date_3.setText(date3);

                        functionBase.profileImageLoading(user_image_3, userOb3, requestManager);
                        functionBase.profileNameLoading(user_name_3, userOb3);

                        if(pokeItem3 != null){

                            functionBase.generalImageLoading(immoticon3, pokeItem3, requestManager);
                            immoticon3.setVisibility(View.VISIBLE);
                            comment_body_3.setVisibility(View.GONE);

                        } else {

                            immoticon3.setVisibility(View.GONE);
                            comment_body_3.setVisibility(View.VISIBLE);

                            String body3 = commentOb3.getString("body");

                            comment_body_3.setText(body3);

                        }

                        comment_layout.setVisibility(View.VISIBLE);

                    }

                    comment_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(context, CommentActivity.class);
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


    }


    public void CommentFavorAdapterBuilder( final ParseObject commentOb, RecyclerView.ViewHolder holder, final ParseObject parentOb , final CommentFavorAdapter adapter, String from, RequestManager requestManager){

        functionBuilder(commentOb, holder, parentOb, from, requestManager);

        final LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();

        if(currentUser!= null){

            if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                delete_button.setVisibility(View.VISIBLE);

            } else {

                delete_button.setVisibility(View.GONE);
            }

            Log.d("delete_parent", parentOb.getObjectId());

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    delete_button.setClickable(false);

                    if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                        commentOb.put("status", false);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){
                                    Log.d("parent_comment_count1", String.valueOf(parentOb.getInt("comment_count")));
                                    parentOb.increment("comment_count", -1);
                                    parentOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                Log.d("parent_comment_count2", String.valueOf(parentOb.getInt("comment_count")));
                                                delete_button.setClickable(true);
                                                adapter.loadObjects(0);

                                            } else {

                                                delete_button.setClickable(true);
                                                e.printStackTrace();
                                            }

                                        }
                                    });

                                } else {

                                    delete_button.setClickable(true);
                                    e.printStackTrace();
                                }

                            }
                        });


                    } else {

                        TastyToast.makeText(context, "권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }



                }
            });

        } else {

            delete_button.setVisibility(View.GONE);

        }


    }

    public void CommentFavorAdapterBuilder(final ParseObject commentOb, RecyclerView.ViewHolder holder, final ParseObject parentOb , final CommentSocialFavorAdapter adapter, String from, RequestManager requestManager){

        functionBuilder(commentOb, holder, parentOb, from, requestManager);

        final LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();

        if(currentUser!= null){

            if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                delete_button.setVisibility(View.VISIBLE);

            } else {

                delete_button.setVisibility(View.GONE);
            }

            Log.d("delete_parent", parentOb.getObjectId());

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    delete_button.setClickable(false);

                    if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                        commentOb.put("status", false);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){
                                    Log.d("parent_comment_count1", String.valueOf(parentOb.getInt("comment_count")));
                                    parentOb.increment("comment_count", -1);
                                    parentOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                Log.d("parent_comment_count2", String.valueOf(parentOb.getInt("comment_count")));
                                                delete_button.setClickable(true);
                                                adapter.loadObjects(0);

                                            } else {

                                                delete_button.setClickable(true);
                                                e.printStackTrace();
                                            }

                                        }
                                    });

                                } else {

                                    delete_button.setClickable(true);
                                    e.printStackTrace();
                                }

                            }
                        });


                    } else {

                        TastyToast.makeText(context, "권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }



                }
            });

        } else {

            delete_button.setVisibility(View.GONE);

        }


    }

    public void CommentFavorAdapterBuilder( final ParseObject commentOb, RecyclerView.ViewHolder holder, final Boolean commentFlag, final String type, final String parentId, final CommentParseFavorAdapter adapter){

        functionBuilder(commentOb, holder, commentFlag, type, parentId);

        final LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();

        if(currentUser!= null){

            if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                delete_button.setVisibility(View.VISIBLE);

            } else {

                delete_button.setVisibility(View.GONE);
            }

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    delete_button.setClickable(false);

                    if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                        commentOb.put("status", false);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                ParseQuery parentQuery = ParseQuery.getQuery("artist_post");
                                parentQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject parentObject, ParseException e) {

                                        if(e==null){

                                            parentObject.increment("comment_count", -1);
                                            parentObject.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    adapter.loadObjects(0);
                                                    delete_button.setClickable(true);

                                                }
                                            });



                                        } else {
                                            delete_button.setClickable(true);
                                            e.printStackTrace();
                                        }

                                    }


                                });


                            }
                        });


                    } else {

                        TastyToast.makeText(context, "권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        delete_button.setClickable(true);
                    }



                }
            });

        } else {

            delete_button.setVisibility(View.GONE);

        }


    }


    public void CommentRecentAdapterBuilder(final ParseObject commentOb, RecyclerView.ViewHolder holder, final ParseObject parentOb, final CommentRecentAdapter adapter, String from, RequestManager requestManager){

        functionBuilder(commentOb, holder, parentOb, from, requestManager);

        final LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();

        if(currentUser!= null){

            if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                delete_button.setVisibility(View.VISIBLE);

            } else {

                delete_button.setVisibility(View.GONE);
            }

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    delete_button.setClickable(false);

                    if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                        commentOb.put("status", false);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    Log.d("parent_comment_count1", String.valueOf(parentOb.getInt("comment_count")));

                                    CommentCountAdjust(currentUser, parentOb, "delete", new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                adapter.loadObjects(0);

                                                delete_button.setClickable(true);

                                            } else {


                                                delete_button.setClickable(true);
                                                e.printStackTrace();

                                            }



                                        }
                                    });



                                } else {

                                    delete_button.setClickable(true);
                                    e.printStackTrace();

                                }

                            }
                        });


                    } else {

                        delete_button.setClickable(true);
                        TastyToast.makeText(context, "권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }


                }
            });

        }


    }


    public void CommentRecentAdapterBuilder(final ParseObject commentOb, RecyclerView.ViewHolder holder, final ParseObject parentOb, final CommentSocialRecentAdapter adapter, final String from, RequestManager requestManager){

        functionBuilder(commentOb, holder, parentOb, from, requestManager);

        final LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();

        if(currentUser!= null){

            if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                delete_button.setVisibility(View.VISIBLE);

            } else {

                delete_button.setVisibility(View.GONE);
            }

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    delete_button.setClickable(false);

                    if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                        commentOb.put("status", false);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    Log.d("parent_comment_count1", String.valueOf(parentOb.getInt("comment_count")));

                                    if(from.equals("social")){

                                        CommentCountAdjust(currentUser, parentOb, "social_delete", new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                if(e==null){

                                                    adapter.loadObjects(0);

                                                    delete_button.setClickable(true);

                                                } else {


                                                    delete_button.setClickable(true);
                                                    e.printStackTrace();

                                                }



                                            }
                                        });

                                    } else {

                                        CommentCountAdjust(currentUser, parentOb, "delete", new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                if(e==null){

                                                    adapter.loadObjects(0);

                                                    delete_button.setClickable(true);

                                                } else {


                                                    delete_button.setClickable(true);
                                                    e.printStackTrace();

                                                }



                                            }
                                        });

                                    }





                                } else {

                                    delete_button.setClickable(true);
                                    e.printStackTrace();

                                }

                            }
                        });


                    } else {

                        delete_button.setClickable(true);
                        TastyToast.makeText(context, "권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }


                }
            });

        }


    }



    public void CommentAdapterBuilder( final ParseObject commentOb, RecyclerView.ViewHolder holder, final Boolean commentFlag, final String type, final String parentId, final CommentParseQueryAdapter adapter){

        functionBuilder(commentOb, holder, commentFlag, type, parentId);

        LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();

        if(currentUser!= null){

            if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                delete_button.setVisibility(View.VISIBLE);

            } else {

                delete_button.setVisibility(View.GONE);
            }

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                        deleteObject( commentOb, "comment", true, parentId, new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    adapter.loadObjects(0);

                                } else {

                                    e.printStackTrace();

                                }


                            }
                        });


                    } else {

                        TastyToast.makeText(context, "권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }



                }
            });

        }


    }

    public void CommentHeaderAdapterBuilder(final ParseObject commentOb, RecyclerView.ViewHolder holder, Boolean commentFlag, String type, final String parentId, final CommentHeaderParseQueryAdapter adapter){

        functionBuilder(commentOb, holder,commentFlag, type, parentId);

        LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();

        if(currentUser!= null){

            if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                delete_button.setVisibility(View.VISIBLE);

            } else {

                delete_button.setVisibility(View.GONE);
            }

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                        deleteObject( commentOb, "comment", true, parentId, new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    adapter.loadObjects(0);

                                } else {

                                    e.printStackTrace();

                                }


                            }
                        });


                    } else {

                        TastyToast.makeText(context, "권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }



                }
            });


        }


    }



    public void CommentHeaderAdapterBuilder(final ParseObject commentOb, RecyclerView.ViewHolder holder, final ParseObject parentObject, final CommentDefaultAdapter adapter, String from, RequestManager requestManager){

        functionBuilder(commentOb, holder, parentObject, from, requestManager);

        LinearLayout delete_button = ((CommentViewHolder)holder).getDeleteButton();

        if(currentUser!= null){

            if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                delete_button.setVisibility(View.VISIBLE);

            } else {

                delete_button.setVisibility(View.GONE);
            }

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(currentUser.getObjectId().equals( commentOb.getParseObject("user").getObjectId() )){

                        commentOb.put("status", false);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                adapter.loadObjects(0);

                            }
                        });


                    } else {

                        TastyToast.makeText(context, "권한이 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }



                }
            });


        }


    }





    private void functionBuilder(final ParseObject commentOb, RecyclerView.ViewHolder holder, final ParseObject parentObject, String from, RequestManager requestManager){

        final CircleImageView comment_photo = ((CommentViewHolder)holder).getCommentPhoto();
        final TextView comment_name = ((CommentViewHolder)holder).getCommentName();
        final TextView comment_updated = ((CommentViewHolder)holder).getCommentUpdated();
        final TextView comment_body = ((CommentViewHolder)holder).getCommentBody();
        LinearLayout thumbnail_layout = ((CommentViewHolder)holder).getThumbnailLayout();

        final LinearLayout comment_like = ((CommentViewHolder)holder).getCommentLike();
        final TextView comment_likeCount = ((CommentViewHolder)holder).getCommentLikeCount();

        final LinearLayout comment_dislike = ((CommentViewHolder)holder).getCommentDislike();
        final TextView comment_dislikeCount = ((CommentViewHolder)holder).getCommentDislikeCount();

        final Boolean likeButtonStatus = ((CommentViewHolder)holder).getLikeButtonStatus();
        Boolean dislikeButtonStatus = ((CommentViewHolder)holder).getDislikeButtonStatus();

        LinearLayout comment_reply = ((CommentViewHolder)holder).getCommentReply();
        TextView comment_replyCount = ((CommentViewHolder)holder).getCommentReplyCount();

        final ImageView comment_icon = ((CommentViewHolder)holder).getCommentIcon();
        final ImageView like_icon = ((CommentViewHolder)holder).getCommentLikeIcon();
        final ImageView comment_dislike_icon = ((CommentViewHolder)holder).getCommentDislikeIcon();
        ImageView immoticon = ((CommentViewHolder)holder).getImmoticon();

        LinearLayout comment_body_layout = ((CommentViewHolder)holder).getCommentBodyLayout();

        LinearLayout best_layout = ((CommentViewHolder)holder).getBestLayout();

        immoticon.setVisibility(View.GONE);

        if(best_layout != null){

            best_layout.setVisibility(View.GONE);

        }
        

        final ParseObject userOb = commentOb.getParseObject("user");

        functionBase.profileImageLoading(comment_photo, userOb, requestManager);
        functionBase.profileNameLoading(comment_name, userOb);

        if(commentOb.get("poke_item") != null){

            ParseObject pokeItemOb = commentOb.getParseObject("poke_item");

            immoticon.setVisibility(View.VISIBLE);
            comment_body.setVisibility(View.GONE);

            functionBase.generalImageLoading(immoticon, pokeItemOb, requestManager);

        } else {

            immoticon.setVisibility(View.GONE);
            comment_body.setVisibility(View.VISIBLE);
            comment_body.setText(commentOb.getString("body"));

        }

        thumbnail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("userId", userOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });

        like_icon.setColorFilter(functionBase.likeColor);
        comment_likeCount.setTextColor(functionBase.likeColor);


        comment_updated.setText(functionBase.dateToString(commentOb.getUpdatedAt()));

        comment_likeCount.setText(String.valueOf(commentOb.getInt("like_count")));

        if(comment_replyCount != null){

            comment_replyCount.setText(String.valueOf(commentOb.getInt("comment_count")));

        }


        if(currentUser != null){
            //my like status check

            if(functionBase.parseArrayCheck(commentOb, "like_array", currentUser.getObjectId())){

                Log.d("like","yes");

                like_icon.setColorFilter(functionBase.likedColor);

                if(comment_likeCount != null){

                    comment_likeCount.setTextColor(functionBase.likedColor);
                }


            } else {

                Log.d("like","no");

                like_icon.setColorFilter(functionBase.likeColor);
                if(comment_likeCount != null){

                    comment_likeCount.setTextColor(functionBase.likeColor);
                }

            }

            //like function binding

            //FunctionLikeDislike functionLikeDislike = new FunctionLikeDislike(context, commentOb, comment_like, like_icon, comment_likeCount);
            //functionLikeDislike.likeButtonFunction(from, parentObject);



            comment_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FunctionLikeDislike functionLikeDislike = new FunctionLikeDislike(context, commentOb, comment_like, like_icon, comment_likeCount);
                    functionLikeDislike.likeButtonFunctionComment(parentObject);

                }
            });


            //comment function binding

            if(comment_reply != null){

                comment_reply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(context, CommentDetailActivity.class);
                        intent.putExtra("commentId", commentOb.getObjectId());
                        intent.putExtra("postId", parentObject.getObjectId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    }
                });

            }




        }



    }



    private void functionBuilder(final ParseObject commentOb, final RecyclerView.ViewHolder holder, final Boolean commentFlag, final String type, final String parentId){

        final CircleImageView comment_photo = ((CommentViewHolder)holder).getCommentPhoto();
        final TextView comment_name = ((CommentViewHolder)holder).getCommentName();
        final TextView comment_updated = ((CommentViewHolder)holder).getCommentUpdated();
        final TextView comment_body = ((CommentViewHolder)holder).getCommentBody();
        LinearLayout thumbnail_layout = ((CommentViewHolder)holder).getThumbnailLayout();

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

        LinearLayout comment_body_layout = ((CommentViewHolder)holder).getCommentBodyLayout();

        final ParseObject userOb = commentOb.getParseObject("user");

        if(userOb.get("thumnail") == null){

            comment_photo.setImageResource(R.drawable.image_background);

        } else {

            Glide.with(context).load( commentOb.getParseObject("user").getParseFile("thumnail").getUrl() ).into(comment_photo);

        }

        thumbnail_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("userId", userOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });

        if(userOb.get("name") == null){

            comment_name.setText(userOb.getString("username"));

        } else {

            comment_name.setText(userOb.getString("name"));

        }

        if(currentUser != null){

            //댓글 지우기 기능 세팅

            //좋아요 싫어요 헀는지 여부 표시

            ParseQuery commentLikeQuery = commentOb.getRelation("like").getQuery();
            commentLikeQuery.whereEqualTo("objectId", currentUser.getObjectId());
            commentLikeQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null){

                        if(objects.size() == 0){

                            comment_likeCount.setTextColor(functionBase.likeColor);
                            comment_like_icon.setColorFilter(functionBase.likeColor);

                        } else {

                            comment_likeCount.setTextColor(functionBase.likedColor);
                            comment_like_icon.setColorFilter(functionBase.likedColor);

                        }

                    } else {

                        Toast.makeText(context, "e", Toast.LENGTH_SHORT).show();

                        e.printStackTrace();

                    }


                }


            });

            ParseQuery commentDisLikeQuery = commentOb.getRelation("dislike").getQuery();
            commentDisLikeQuery.whereEqualTo("objectId", currentUser.getObjectId());
            commentDisLikeQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {

                    if(e==null){

                        if(objects.size() == 0){

                            comment_dislikeCount.setTextColor(functionBase.likeColor);
                            comment_dislike_icon.setColorFilter(functionBase.likeColor);

                        } else {

                            comment_dislikeCount.setTextColor(functionBase.likedColor);
                            comment_dislike_icon.setColorFilter(functionBase.likedColor);

                        }

                    } else {

                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();

                    }


                }


            });

        }


        comment_updated.setText(functionBase.dateToString(commentOb.getUpdatedAt()));
        comment_body.setText(commentOb.getString("body"));
        comment_likeCount.setText(String.valueOf(commentOb.getInt("like_count")));
        comment_dislikeCount.setText( String.valueOf(commentOb.getInt("dislike_count")) );

        if(commentFlag){

            comment_body_layout.setClickable(true);

            comment_body_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(type.equals("post")){

                        Intent intent = new Intent(context, CommentDetailActivity.class);
                        intent.putExtra("commentId", commentOb.getObjectId());
                        intent.putExtra("parentId", parentId);
                        intent.putExtra("type", type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);

                    } else {

                        Intent intent = new Intent(context, CommentDetailActivity.class);
                        intent.putExtra("commentId", commentOb.getObjectId());
                        intent.putExtra("parentId", parentId);
                        intent.putExtra("type", type);
                        context.startActivity(intent);

                        ((Activity)context).finish();

                    }




                }
            });

            comment_reply.setVisibility(View.VISIBLE);

            comment_replyCount.setText( String.valueOf( commentOb.getInt("comment_count") ) );

            comment_reply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(type.equals("post")){

                        Intent intent = new Intent(context, CommentDetailActivity.class);
                        intent.putExtra("commentId", commentOb.getObjectId());
                        intent.putExtra("parentId", parentId);
                        intent.putExtra("type", type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);



                    } else {

                        Intent intent = new Intent(context, CommentDetailActivity.class);
                        intent.putExtra("commentId", commentOb.getObjectId());
                        intent.putExtra("parentId", parentId);
                        intent.putExtra("type", type);
                        context.startActivity(intent);

                        ((Activity)context).finish();

                    }



                }
            });

        } else {

            comment_body_layout.setClickable(false);

            comment_reply.setVisibility(View.GONE);

        }


        comment_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser != null){

                    FunctionLikeDislike functionLikeDislike = new FunctionLikeDislike(context, commentOb, comment_like, comment_like_icon, comment_likeCount );
                    functionLikeDislike.LikeButtonFunction(context, "comment_like", commentOb, currentUser, true ,commentFlag, comment_like, comment_likeCount, null, comment_like_icon, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                TastyToast.makeText(context, "좋아요", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                            } else {

                                e.printStackTrace();

                            }


                        }
                    });

                } else {

                    TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

            }
        });

        comment_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(currentUser != null){

                    FunctionLikeDislike functionLikeDislike = new FunctionLikeDislike(context, commentOb, comment_dislike, comment_dislike_icon, comment_dislikeCount);
                    functionLikeDislike.DislikeButtonFunction(context,  commentOb, currentUser, true, commentFlag, comment_dislike, comment_dislikeCount, null, comment_dislike_icon);

                } else {

                    TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }

            }
        });

        functionBase.iconColorFilterInit(comment_icon, comment_like_icon, comment_dislike_icon);


    }



    public void CommentCounterMinus( ParseObject commentOb, String type, Boolean commentFlag, String parentId, SaveCallback saveCallback){

        if(type.equals("post")){

            Log.d("currentLocation", "post");

            ParseQuery postQuery = ParseQuery.getQuery("Post");
            postQuery.include("parent");
            postQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject postOb, ParseException e) {

                    if(e==null){

                        if(postOb.getString("from").equals("channel")){

                            ParseObject channelOb = postOb.getParseObject("parent");
                            channelOb.increment("comment_count", -1);
                            channelOb.saveInBackground();

                        }

                        postOb.increment("comment_count", -1);
                        postOb.saveInBackground();

                    } else {

                        e.printStackTrace();
                    }

                }

            });

        } else {

            if(commentFlag){

                ParseQuery contentQuery = ParseQuery.getQuery("Content");
                contentQuery.include("channel");
                contentQuery.include("category");
                contentQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject contentOb, ParseException e) {

                        if(e==null){

                            contentOb.increment("comment_count",-1);
                            contentOb.saveInBackground();

                            final ParseObject channelOb = contentOb.getParseObject("channel");
                            channelOb.increment("comment_count", -1);
                            channelOb.saveInBackground();

                            ParseObject categoryOb = contentOb.getParseObject("category");
                            categoryOb.increment("comment_count", -1);
                            categoryOb.saveInBackground();

                        } else {

                            e.printStackTrace();
                        }

                    }

                });


            } else {

                ParseQuery contentQuery = ParseQuery.getQuery("Comment");
                contentQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject parentCommentOb, ParseException e) {

                        if(e==null){

                            parentCommentOb.increment("comment_count", -1);
                            parentCommentOb.saveInBackground();

                        } else {

                            e.printStackTrace();
                        }

                    }

                });


            }

        }

        PointMinus(commentOb, saveCallback);

    }

    public void PointMinus( ParseObject commentOb, final SaveCallback saveCallback){

        final ParseObject pointManager = commentOb.getParseObject("pointManager");
        pointManager.put("status", false);
        pointManager.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){

                    currentUser.increment("current_point", -pointManager.getInt("amount"));
                    currentUser.increment("total_point", -pointManager.getInt("amount"));
                    currentUser.saveInBackground(saveCallback);

                    TastyToast.makeText(context, String.valueOf( pointManager.getInt("amount") )  + "P 지급이 취소 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);


                } else {


                }

            }
        });



    }

    public void deleteObject( final ParseObject commentOb, final String type, Boolean commentFlag, final String parentId, final SaveCallback saveCallback){

        commentOb.put("status", false);
        commentOb.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e==null){

                    CommentCounterMinus( commentOb,  type, true, parentId, saveCallback);

                } else {

                    e.printStackTrace();
                }


            }
        });

    }



    public void WritePointPlus( ParseObject writeOb, String write_body, String from, String type, SaveCallback saveCallback){

        final ParseObject pointManagerOb = new ParseObject("PointManager");
        pointManagerOb.put("from", from);
        pointManagerOb.put("type", type);
        pointManagerOb.put("amount", write_body.length());
        pointManagerOb.put("user", currentUser);
        pointManagerOb.put("status", true);

        if(from.equals("comment") || from.equals("reply")){

            pointManagerOb.put("comment", writeOb);

        } else if(from.equals("post")){

            pointManagerOb.put("post", writeOb);

        }


        pointManagerOb.saveInBackground(saveCallback);

        writeOb.put("pointManager", pointManagerOb);
        writeOb.saveInBackground();

    }

    public void ContentCommentCountPlus(String parentId,  final ParseObject commentOb){

        ParseQuery contentQuery = ParseQuery.getQuery("Content");
        contentQuery.include("channel");
        contentQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject contentOb, ParseException e) {

                contentOb.increment("comment_count",1);
                contentOb.saveInBackground();

                final ParseObject channelOb = contentOb.getParseObject("channel");
                channelOb.increment("comment_count", 1);
                channelOb.saveInBackground();

                ParseObject categoryOb = contentOb.getParseObject("category");
                categoryOb.increment("comment_count", 1);
                categoryOb.saveInBackground();

                ParseQuery notiQuery = ParseQuery.getQuery("Noti");
                notiQuery.whereEqualTo("sender", currentUser);
                notiQuery.whereEqualTo("msg", commentOb);
                notiQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if(objects.size() == 0){

                            ParseObject notiOb = new ParseObject("Noti");
                            notiOb.put("sender", currentUser);
                            notiOb.put("to", channelOb.getParseObject("owner") );
                            notiOb.put("msg", commentOb);
                            notiOb.put("from", "comment");
                            notiOb.put("status", true);
                            notiOb.saveInBackground();

                        }

                    }

                });

            }

        });

    }

    public void PostCommentCountPlus(String parentId, final ParseObject commentOb){

        ParseQuery postQuery = ParseQuery.getQuery("Post");
        postQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject postOb, ParseException e) {

                postOb.increment("comment_count",1);
                postOb.saveInBackground();

                if(postOb.get("parent") != null){

                    //타임라인

                    ParseObject channelOb = postOb.getParseObject("parent");
                    channelOb.increment("comment_count", 1);
                    channelOb.increment("post_comment_count", 1);
                    channelOb.saveInBackground();



                }

                ParseQuery notiQuery = ParseQuery.getQuery("Noti");
                notiQuery.whereEqualTo("sender", currentUser);
                notiQuery.whereEqualTo("msg", commentOb);
                notiQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if(objects.size() == 0){

                            ParseObject notiOb = new ParseObject("Noti");
                            notiOb.put("sender", currentUser);
                            notiOb.put("to", postOb.getParseObject("user") );
                            notiOb.put("msg", commentOb);
                            notiOb.put("from", "post");
                            notiOb.put("status", true);
                            notiOb.saveInBackground();

                        }

                    }

                });


            }

        });

    }

    public void ParentCommentCountPlus(String parent_commentId,  final ParseObject commentOb){

        ParseQuery commentQuery = ParseQuery.getQuery("Comment");
        commentQuery.getInBackground(parent_commentId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject parentCommentOb, ParseException e) {

                parentCommentOb.increment("comment_count", 1);
                parentCommentOb.saveInBackground();

                ParseQuery notiQuery = ParseQuery.getQuery("Noti");
                notiQuery.whereEqualTo("sender", currentUser);
                notiQuery.whereEqualTo("msg", commentOb);
                notiQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {

                        if(objects.size() == 0){

                            ParseObject notiOb = new ParseObject("Noti");
                            notiOb.put("sender", currentUser);
                            notiOb.put("to", parentCommentOb.getParseObject("user") );
                            notiOb.put("msg", commentOb);
                            notiOb.put("from", "reply");
                            notiOb.put("status", true);
                            notiOb.saveInBackground();

                        }

                    }

                });



            }

        });

    }

    public void CommentWrite(final Activity activity, final EditText write_body_input, final String parentId, final String parent_commentId, final String from, final String paymentType,  final SaveCallback saveCallback){

        final String write_body = write_body_input.getText().toString();

        if(write_body.length() == 0){

            Toast.makeText(context, "내용을 입력하세요", Toast.LENGTH_SHORT).show();

        } else {

            final ParseObject commentOb = new ParseObject("Comment");
            commentOb.put("body", write_body);
            commentOb.put("user", currentUser);
            commentOb.put("parent", parentId);
            commentOb.put("like_count", 0);
            commentOb.put("dislike_count", 0);
            commentOb.put("comment_count", 0);
            commentOb.put("type", from);
            commentOb.put("status", true);
            commentOb.put("parent_comment", parent_commentId);
            commentOb.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        write_body_input.setText("");
                        functionBase.hideKeyboard(activity);

                        if(from.equals("comment")){

                            ContentCommentCountPlus(parentId, commentOb);

                        } else if(from.equals("reply")){

                            ParentCommentCountPlus(parent_commentId,  commentOb);

                        } else if(from.equals("post")){

                            PostCommentCountPlus(parentId, commentOb);

                        }


                        WritePointPlus( commentOb, write_body, from, paymentType, new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                currentUser.increment("current_point", write_body.length());
                                currentUser.increment("total_point", write_body.length());
                                currentUser.saveInBackground(saveCallback);

                            }
                        });




                    } else {

                        e.printStackTrace();
                    }


                }
            });

        }

    }

    public void PostCommentWrite(final Activity activity, final EditText write_body_input, final ParseObject parentOb, final String type, final SaveCallback saveCallback){

        final String write_body = write_body_input.getText().toString();

        if(write_body.length() == 0){

            Toast.makeText(context, "내용을 입력하세요", Toast.LENGTH_SHORT).show();

        } else {

            final ParseObject commentOb = new ParseObject("Comment");
            commentOb.put("body", write_body);
            commentOb.put("user", currentUser);
            commentOb.put("parent_id", parentOb.getObjectId());
            commentOb.put("parentOb", parentOb);
            commentOb.put("like_count", 0);
            commentOb.put("comment_count", 0);
            commentOb.put("status", true);
            commentOb.put("type", type);
            commentOb.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        write_body_input.setText("");
                        functionBase.hideKeyboard(activity);

                        ParseRelation commentRelation = parentOb.getRelation("comment");
                        commentRelation.add(commentOb);
                        parentOb.increment("comment_count");
                        parentOb.saveInBackground(saveCallback);

                    } else {

                        e.printStackTrace();
                    }


                }
            });

        }

    }

    public void CommentRelationMaker(ParseUser currentUser, ParseObject commentOb, ParseObject parentOb, final SaveCallback saveCallback){

        HashMap<String, Object> params = new HashMap<>();

        params.put("key", currentUser.getSessionToken());
        params.put("target", parentOb.getObjectId());
        params.put("comment", commentOb.getObjectId());
        Date uniqueIdDate = new Date();
        String uniqueId = uniqueIdDate.toString();

        params.put("uid", uniqueId);

        ParseCloud.callFunctionInBackground("comment_relation_maker", params, new FunctionCallback<Map<String, Object>>() {

            public void done(Map<String, Object> mapObject, ParseException e) {

                if (e == null) {

                    Log.d("result", mapObject.toString());

                    if(mapObject.get("status").toString().equals("true")){

                        saveCallback.done(e);

                    } else {

                        TastyToast.makeText(context, "댓글 입력이 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                } else {

                    e.printStackTrace();

                }
            }
        });

    }


    public void CommentWithSocialRelationMaker(ParseUser currentUser, ParseObject commentOb, ParseObject parentOb, final SaveCallback saveCallback){

        HashMap<String, Object> params = new HashMap<>();

        params.put("key", currentUser.getSessionToken());
        params.put("target", parentOb.getObjectId());
        params.put("comment", commentOb.getObjectId());

        Date uniqueIdDate = new Date();
        String uniqueId = uniqueIdDate.toString();

        params.put("uid", uniqueId);

        ParseCloud.callFunctionInBackground("comment_social_maker", params, new FunctionCallback<Map<String, Object>>() {

            public void done(Map<String, Object> mapObject, ParseException e) {

                if (e == null) {

                    Log.d("result", mapObject.toString());

                    if(mapObject.get("status").toString().equals("true")){

                        saveCallback.done(e);

                    } else {

                        TastyToast.makeText(context, "댓글 입력이 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                } else {

                    e.printStackTrace();

                }
            }
        });

    }

    public void CommentCountAdjust(ParseUser currentUser, ParseObject targetOb, String action, final SaveCallback saveCallback){

        HashMap<String, Object> params = new HashMap<>();

        params.put("key", currentUser.getSessionToken());
        params.put("target", targetOb.getObjectId());
        params.put("action", action);

        Date uniqueIdDate = new Date();
        String uniqueId = uniqueIdDate.toString();

        params.put("uid", uniqueId);

        ParseCloud.callFunctionInBackground("comment_count_adjust", params, new FunctionCallback<Map<String, Object>>() {

            public void done(Map<String, Object> mapObject, ParseException e) {

                Log.d("result", mapObject.toString());

                if (e == null) {

                    if(mapObject.get("status").toString().equals("true")){

                        saveCallback.done(e);

                    } else {

                        TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                } else {

                    e.printStackTrace();

                }
            }
        });

    }





    public void PostReplyWrite(final Activity activity, final EditText write_body_input,final ParseObject commentOb, final String postId, final String type, final SaveCallback saveCallback){

        final String write_body = write_body_input.getText().toString();

        if(write_body.length() == 0){

            Toast.makeText(context, "내용을 입력하세요", Toast.LENGTH_SHORT).show();

        } else {

            final ParseObject replyOb = new ParseObject("Reply");
            replyOb.put("body", write_body);
            replyOb.put("user", currentUser);
            replyOb.put("parent_id", commentOb.getObjectId());
            replyOb.put("parentOb", commentOb);
            replyOb.put("post_id", postId);
            replyOb.put("like_count", 0);
            replyOb.put("comment_count", 0);
            replyOb.put("status", true);
            replyOb.put("type", type);
            replyOb.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        write_body_input.setText("");
                        functionBase.hideKeyboard(activity);

                        ParseRelation commentRelation = commentOb.getRelation("reply");
                        commentRelation.add(replyOb);
                        commentOb.increment("comment_count");
                        commentOb.saveInBackground(saveCallback);

                    } else {

                        e.printStackTrace();
                    }


                }
            });

        }

    }


}
