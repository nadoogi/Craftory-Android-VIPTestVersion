package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.RecommendItemSelectListener;

/**
 * Created by ssamkyu on 2017. 5. 26..
 */

public class FunctionLikeDislike {

    private Context context;
    private ParseUser currentUser;
    private ParseObject targetObject;
    private LinearLayout like_button;
    private ImageView like_icon;
    private TextView like_count;
    private FunctionBase functionBase;

    public FunctionLikeDislike(Context context, ParseObject targetObject, LinearLayout like_button, ImageView like_icon, TextView like_count) {

        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.targetObject = targetObject;
        this.like_button = like_button;
        this.like_icon = like_icon;
        this.like_count = like_count;
        this.functionBase = new FunctionBase(context);

    }

    private void likeStatusCheck(int defaultColor, int selectedColor){

        if(functionBase.parseArrayCheck(targetObject, "like_array", currentUser.getObjectId())){


            like_icon.setColorFilter(selectedColor);

            if(like_count != null){

                like_count.setTextColor(selectedColor);
            }


        } else {

            Log.d("like","no");

            like_icon.setColorFilter(defaultColor);
            if(like_count != null){

                like_count.setTextColor(defaultColor);
            }

        }


    }

    public void likeButtonStatusCheck(){

        if(currentUser != null){

            if(functionBase.parseArrayCheck(targetObject, "like_array", currentUser.getObjectId())){

                if(like_count != null){

                    like_count.setTextColor(functionBase.likedPostColor);
                }

                if(like_icon != null){

                    like_icon.setColorFilter(functionBase.likedPostColor);
                }


            } else {


                if(like_count != null){

                    like_count.setTextColor(functionBase.likePostColor);
                }

                if(like_icon != null){


                    like_icon.setColorFilter(functionBase.likePostColor);
                }

            }

        }

    }

    public void likeButtonStatusCheck(RecommendItemSelectListener recommendItemSelectListener){

        if(currentUser != null){

            if(functionBase.parseArrayCheck(targetObject, "like_array", currentUser.getObjectId())){

                if(like_count != null){

                    like_count.setTextColor(functionBase.likedPostColor);
                }

                if(like_icon != null){

                    like_icon.setImageResource(R.drawable.icon_like_selected);
                }


            } else {


                if(like_count != null){

                    like_count.setTextColor(functionBase.likePostColor);
                }

                if(like_icon != null){

                    like_icon.setImageResource(R.drawable.icon_like_not_selected);

                }

            }

        }



    }

    public void likeButtonFunction(){

        like_button.setClickable(false);

        if(currentUser != null){

            try {

                final ParseObject fetchedPostOb = targetObject.fetch();

                if(functionBase.parseArrayCheck(fetchedPostOb, "like_array", currentUser.getObjectId())){

                    TastyToast.makeText(context, "좋아요 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                    like_icon.setColorFilter(functionBase.likePostColor);

                    if(like_count != null){

                        like_count.setTextColor(functionBase.likePostColor);
                        like_count.setText(String.valueOf(fetchedPostOb.getInt("like_count") - 1));

                    }

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("target", fetchedPostOb.getObjectId());
                    params.put("action", "cancel");

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("like", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("result", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){

                                    like_icon.setColorFilter(functionBase.likePostColor);

                                    if(like_count != null){

                                        like_count.setTextColor(functionBase.likePostColor);
                                        like_count.setText(String.valueOf(mapObject.get("like_count")));

                                    }

                                    ParseRelation likeRelation = currentUser.getRelation("like_item");
                                    likeRelation.remove(fetchedPostOb);
                                    currentUser.saveInBackground();

                                    like_button.setClickable(true);

                                } else {

                                    TastyToast.makeText(context, "좋아요 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    like_button.setClickable(true);

                                }

                            } else {

                                e.printStackTrace();
                                like_button.setClickable(true);

                            }
                        }
                    });





                } else {

                    TastyToast.makeText(context, "좋아요", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                    like_icon.setColorFilter(functionBase.likedPostColor);

                    if(like_count != null){

                        like_count.setTextColor(functionBase.likedPostColor);
                        like_count.setText(String.valueOf(fetchedPostOb.getInt("like_count") + 1));

                    }

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("target", fetchedPostOb.getObjectId());
                    params.put("action", "like");
                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("like", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("result", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){

                                    like_icon.setColorFilter(functionBase.likedPostColor);

                                    if(like_count != null){

                                        like_count.setTextColor(functionBase.likedPostColor);
                                        like_count.setText(String.valueOf(mapObject.get("like_count")));

                                    }

                                    ParseRelation likeRelation = currentUser.getRelation("like_item");
                                    likeRelation.add(fetchedPostOb);
                                    currentUser.saveInBackground();



                                    if(!functionBase.parseArrayCheck(currentUser, "my_alert", targetObject.getObjectId())){


                                        if(!currentUser.getObjectId().equals(targetObject.getParseObject("user").getObjectId())){

                                            ParseObject myAlert = new ParseObject("MyAlert");
                                            myAlert.put("from", currentUser);
                                            myAlert.put("to", targetObject.getParseObject("user"));
                                            myAlert.put("type", "like_post");
                                            myAlert.put("artist_post", targetObject);
                                            myAlert.put("status", true);
                                            myAlert.saveInBackground();

                                            currentUser.addAllUnique("my_alert", Arrays.asList(targetObject.getObjectId()));
                                            currentUser.saveInBackground();

                                        }



                                    }

                                    like_button.setClickable(true);

                                } else {

                                    TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    like_button.setClickable(true);

                                }

                            } else {

                                e.printStackTrace();
                                like_button.setClickable(true);
                                TastyToast.makeText(context, "버튼 작동에 이상이 발생했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                            }
                        }
                    });

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }



        } else {

            TastyToast.makeText(context, "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

    }


    public void likeButtonFunction(final RecommendItemSelectListener recommendItemSelectListener){

        like_button.setClickable(false);

        if(currentUser != null){

            try {

                final ParseObject fetchedPostOb = targetObject.fetch();

                if(functionBase.parseArrayCheck(fetchedPostOb, "like_array", currentUser.getObjectId())){

                    TastyToast.makeText(context, "좋아요 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("target", fetchedPostOb.getObjectId());
                    params.put("action", "cancel");
                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("like", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("result", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){

                                    like_icon.setImageResource(R.drawable.icon_like_not_selected);

                                    if(like_count != null){

                                        like_count.setTextColor(functionBase.likePostColor);
                                        like_count.setText(String.valueOf(mapObject.get("like_count")));

                                    }

                                    recommendItemSelectListener.onItemSelected(false);

                                    ParseRelation likeRelation = currentUser.getRelation("like_item");
                                    likeRelation.remove(fetchedPostOb);
                                    currentUser.saveInBackground();
                                    like_button.setClickable(true);

                                } else {

                                    TastyToast.makeText(context, "좋아요 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    like_button.setClickable(true);

                                }

                            } else {

                                e.printStackTrace();
                                like_button.setClickable(true);

                            }
                        }
                    });





                } else {

                    TastyToast.makeText(context, "좋아요", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("target", fetchedPostOb.getObjectId());
                    params.put("action", "like");
                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("like", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("result", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){

                                    like_icon.setImageResource(R.drawable.icon_like_selected);

                                    if(like_count != null){

                                        like_count.setTextColor(functionBase.likedPostColor);
                                        like_count.setText(String.valueOf(mapObject.get("like_count")));

                                    }

                                    recommendItemSelectListener.onItemSelected(true);

                                    ParseRelation likeRelation = currentUser.getRelation("like_item");
                                    likeRelation.add(fetchedPostOb);
                                    currentUser.saveInBackground();



                                    if(!functionBase.parseArrayCheck(currentUser, "my_alert", targetObject.getObjectId())){


                                        if(!currentUser.getObjectId().equals(targetObject.getParseObject("user").getObjectId())){

                                            ParseObject myAlert = new ParseObject("MyAlert");
                                            myAlert.put("from", currentUser);
                                            myAlert.put("to", targetObject.getParseObject("user"));
                                            myAlert.put("type", "like_post");
                                            myAlert.put("artist_post", targetObject);
                                            myAlert.put("status", true);
                                            myAlert.saveInBackground();

                                            currentUser.addAllUnique("my_alert", Arrays.asList(targetObject.getObjectId()));
                                            currentUser.saveInBackground();

                                        }



                                    }

                                    like_button.setClickable(true);

                                } else {

                                    TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    like_button.setClickable(true);

                                }

                            } else {

                                e.printStackTrace();
                                like_button.setClickable(true);
                                TastyToast.makeText(context, "버튼 작동에 이상이 발생했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                            }
                        }
                    });

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }



        } else {

            TastyToast.makeText(context, "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

    }


    public void likeButtonFunctionViewerStatusCheck(){

        if(currentUser != null){

            if(functionBase.parseArrayCheck(targetObject, "like_array", currentUser.getObjectId())){

                if(like_count != null){
                    like_icon.setColorFilter(functionBase.likedPostColor);
                    like_count.setTextColor(functionBase.likedPostColor);
                }

            } else {

                if(like_count != null){
                    like_icon.setColorFilter(functionBase.viewerLikeColor);
                    like_count.setTextColor(functionBase.viewerLikeColor);
                }

            }

        }

    }


    public void likeButtonFunctionViewer(){

        like_button.setClickable(false);

        if(currentUser != null){

            try {

                final ParseObject fetchedPostOb = targetObject.fetch();

                if(functionBase.parseArrayCheck(fetchedPostOb, "like_array", currentUser.getObjectId())){

                    TastyToast.makeText(context, "좋아요 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("target", fetchedPostOb.getObjectId());
                    params.put("action", "cancel");

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("like", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            Log.d("result", mapObject.toString());

                            if (e == null) {

                                if(mapObject.get("status").toString().equals("true")){

                                    like_icon.setColorFilter(functionBase.viewerLikeColor);

                                    if(like_count != null){

                                        like_count.setTextColor(functionBase.viewerLikeColor);
                                        like_count.setText(String.valueOf(mapObject.get("like_count")));

                                    }

                                    ParseRelation likeRelation = currentUser.getRelation("like_item");
                                    likeRelation.remove(fetchedPostOb);
                                    currentUser.saveInBackground();

                                    like_button.setClickable(true);

                                } else {

                                    TastyToast.makeText(context, "좋아요 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    like_button.setClickable(true);

                                }

                            } else {

                                e.printStackTrace();
                                like_button.setClickable(true);

                            }
                        }
                    });





                } else {

                    TastyToast.makeText(context, "좋아요", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("target", fetchedPostOb.getObjectId());
                    params.put("action", "like");
                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("like", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            Log.d("result", mapObject.toString());

                            if (e == null) {

                                if(mapObject.get("status").toString().equals("true")){

                                    like_icon.setColorFilter(functionBase.likedPostColor);

                                    if(like_count != null){

                                        like_count.setTextColor(functionBase.likedPostColor);
                                        like_count.setText(String.valueOf(mapObject.get("like_count")));

                                    }

                                    ParseRelation likeRelation = currentUser.getRelation("like_item");
                                    likeRelation.add(fetchedPostOb);
                                    currentUser.saveInBackground();



                                    if(!functionBase.parseArrayCheck(currentUser, "my_alert", targetObject.getObjectId())){


                                        if(!currentUser.getObjectId().equals(targetObject.getParseObject("user").getObjectId())){

                                            ParseObject myAlert = new ParseObject("MyAlert");
                                            myAlert.put("from", currentUser);
                                            myAlert.put("to", targetObject.getParseObject("user"));
                                            myAlert.put("type", "like_post");
                                            myAlert.put("artist_post", targetObject);
                                            myAlert.put("status", true);
                                            myAlert.saveInBackground();

                                            currentUser.addAllUnique("my_alert", Arrays.asList(targetObject.getObjectId()));
                                            currentUser.saveInBackground();

                                        }



                                    }

                                    like_button.setClickable(true);

                                } else {

                                    TastyToast.makeText(context, "팔로우 취소가 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    like_button.setClickable(true);

                                }

                            } else {

                                e.printStackTrace();
                                like_button.setClickable(true);

                            }
                        }
                    });

                }

            } catch (ParseException e) {
                e.printStackTrace();
            }



        } else {

            TastyToast.makeText(context, "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

    }


    public void likeButtonFunctionCommentStatusCheck(){

        if(currentUser != null){

            if(functionBase.parseArrayCheck(targetObject, "like_array", currentUser.getObjectId())){

                if(like_count != null){
                    like_icon.setColorFilter(functionBase.likedPostColor);
                    like_count.setTextColor(functionBase.likedPostColor);
                }

            } else {

                if(like_count != null){
                    like_icon.setColorFilter(functionBase.viewerLikeColor);
                    like_count.setTextColor(functionBase.viewerLikeColor);
                }

            }

        }

    }


    public void likeButtonFunctionComment(final ParseObject parentObject){

        like_button.setClickable(false);

        if(currentUser != null){

            if(functionBase.parseArrayCheck(targetObject, "like_array", currentUser.getObjectId())){

                TastyToast.makeText(context, "좋아요 취소", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                targetObject.increment("like_count", -1);
                targetObject.removeAll("like_array", Arrays.asList(currentUser.getObjectId()));
                targetObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e==null){

                            like_icon.setColorFilter(functionBase.likeColor);

                            if(like_count != null){

                                like_count.setTextColor(functionBase.likeColor);
                                like_count.setText(String.valueOf(targetObject.getInt("like_count")));

                            }

                            like_button.setClickable(true);

                        } else {

                            e.printStackTrace();
                            like_button.setClickable(true);
                        }

                    }
                });

            } else {

                TastyToast.makeText(context, "좋아요", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                targetObject.increment("like_count");
                targetObject.addAllUnique("like_array", Arrays.asList(currentUser.getObjectId()));
                targetObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if(e==null){

                            like_icon.setColorFilter(functionBase.likedColor);

                            if(like_count != null){

                                like_count.setTextColor(functionBase.likedColor);
                                like_count.setText(String.valueOf(targetObject.getInt("like_count")));

                            }

                            if(!functionBase.parseArrayCheck(currentUser, "my_log", targetObject.getObjectId())){

                                ParseObject myLog = new ParseObject("MyLog");
                                myLog.put("type", "like_comment");
                                myLog.put("user", currentUser);
                                myLog.put("artist_post", parentObject);
                                myLog.put("comment", targetObject);
                                myLog.put("status", true);
                                myLog.saveInBackground();


                                currentUser.addAllUnique("my_log", Arrays.asList(targetObject.getObjectId()));
                                currentUser.saveInBackground();

                            }

                            if(!functionBase.parseArrayCheck(currentUser, "my_alert", targetObject.getObjectId())){


                                if(!currentUser.getObjectId().equals(targetObject.getParseObject("user").getObjectId())){

                                    ParseObject myAlert = new ParseObject("MyAlert");
                                    myAlert.put("from", currentUser);
                                    myAlert.put("to", targetObject.getParseObject("user"));
                                    myAlert.put("type", "like_comment");
                                    myAlert.put("artist_post", parentObject);
                                    myAlert.put("comment", targetObject);
                                    myAlert.put("status", true);
                                    myAlert.saveInBackground();

                                    currentUser.addAllUnique("my_alert", Arrays.asList(targetObject.getObjectId()));
                                    currentUser.saveInBackground();

                                }



                            }

                            like_button.setClickable(true);



                        } else {

                            e.printStackTrace();
                            like_button.setClickable(true);

                        }

                    }
                });

            }

        } else {

            TastyToast.makeText(context, "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }

    }


    public void LikeButtonFunction(final Context context, final String type, final ParseObject commentOb, final ParseUser currentUser,final Boolean backgroundBlack, final Boolean commentFlag, final LinearLayout comment_like, final TextView comment_likeCount,final TextView comment_like_text, final ImageView comment_like_icon, final SaveCallback saveCallback){


        comment_like.setClickable(false);

        ParseQuery likeQuery = commentOb.getRelation("like").getQuery();
        likeQuery.whereEqualTo("objectId", currentUser.getObjectId());
        likeQuery.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {

                if(e==null){

                    if(count == 0){

                        ParseRelation commentLikeRelation = commentOb.getRelation("like");
                        commentLikeRelation.add(currentUser);
                        commentOb.increment("like_count", 1);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    if(e==null){

                                        comment_likeCount.setText(String.valueOf( commentOb.getInt("like_count") ));

                                        if(backgroundBlack){

                                            comment_likeCount.setTextColor(functionBase.likedColor);
                                            comment_like_icon.setColorFilter(functionBase.likedColor);

                                            if(comment_like_text != null){

                                                comment_like_text.setTextColor(functionBase.likedColor);

                                            }

                                        } else {

                                            comment_likeCount.setTextColor(functionBase.likedPostColor);
                                            comment_like_icon.setColorFilter(functionBase.likedPostColor);

                                            if(comment_like_text != null){

                                                comment_like_text.setTextColor(functionBase.likedPostColor);

                                            }


                                        }



                                        ParseQuery notiQuery = ParseQuery.getQuery("Noti");
                                        notiQuery.whereEqualTo("sender", currentUser);

                                        if( type.equals("post_like") ){

                                            notiQuery.whereEqualTo("post", commentOb);

                                        } else {

                                            notiQuery.whereEqualTo("msg", commentOb);

                                        }

                                        comment_like.setClickable(true);

                                        notiQuery.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {

                                                if(objects.size() == 0){

                                                    if(type.equals("post_like")){

                                                        ParseObject notiOb = new ParseObject("Noti");
                                                        notiOb.put("sender", currentUser);
                                                        notiOb.put("to", commentOb.getParseObject("user") );
                                                        notiOb.put("post", commentOb );
                                                        notiOb.put("from", type);
                                                        notiOb.put("status", true );
                                                        notiOb.saveInBackground(saveCallback);

                                                    } else {

                                                        if( commentFlag ){

                                                            ParseObject notiOb = new ParseObject("Noti");
                                                            notiOb.put("sender", currentUser);
                                                            notiOb.put("to", commentOb.getParseObject("user"));
                                                            notiOb.put("msg",commentOb );
                                                            notiOb.put("from", "comment_like");
                                                            notiOb.put("status", true );
                                                            notiOb.saveInBackground(saveCallback);

                                                        } else {

                                                            ParseObject notiOb = new ParseObject("Noti");
                                                            notiOb.put("sender", currentUser);
                                                            notiOb.put("to", commentOb.getParseObject("user"));
                                                            notiOb.put("msg",commentOb );
                                                            notiOb.put("from", "comment_like");
                                                            notiOb.put("status", true );
                                                            notiOb.saveInBackground(saveCallback);

                                                        }

                                                    }

                                                }

                                            }

                                        });

                                    } else {

                                        Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                        comment_like.setClickable(true);
                                        e.printStackTrace();

                                    }


                                } else {

                                    Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                    comment_like.setClickable(true);
                                    e.printStackTrace();

                                }


                            }
                        });

                    } else {

                        ParseRelation commentLikeRelation = commentOb.getRelation("like");
                        commentLikeRelation.remove(currentUser);
                        commentOb.increment("like_count", -1);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    if(e==null){

                                        TastyToast.makeText(context, "좋아요 취소", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        comment_likeCount.setText(String.valueOf( commentOb.getInt("like_count") ));
                                        comment_like.setClickable(true);

                                        if(backgroundBlack){

                                            comment_likeCount.setTextColor(functionBase.likeColor);
                                            comment_like_icon.setColorFilter(functionBase.likeColor);

                                            if(comment_like_text != null){

                                                comment_like_text.setTextColor(functionBase.likeColor);

                                            }

                                        } else {

                                            comment_likeCount.setTextColor(functionBase.likePostColor);
                                            comment_like_icon.setColorFilter(functionBase.likePostColor);

                                            if(comment_like_text != null){

                                                comment_like_text.setTextColor(functionBase.likePostColor);

                                            }


                                        }


                                    } else {

                                        Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                        comment_like.setClickable(true);
                                        e.printStackTrace();

                                    }


                                } else {

                                    Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                    comment_like.setClickable(true);
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

    public void DislikeButtonFunction(final Context context, final ParseObject commentOb, final ParseUser currentUser, final Boolean backgroundBlack, final Boolean commentFlag, final LinearLayout comment_dislike, final TextView comment_dislikeCount,final TextView comment_dislike_text, final ImageView comment_dislike_icon){

        comment_dislike.setClickable(false);

        ParseQuery likeQuery = commentOb.getRelation("dislike").getQuery();
        likeQuery.whereEqualTo("objectId", currentUser.getObjectId());
        likeQuery.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {

                if(e==null){

                    if(count == 0){


                        ParseRelation commentDislikeRelation = commentOb.getRelation("dislike");
                        commentDislikeRelation.add(currentUser);
                        commentOb.increment("dislike_count", 1);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    if(e==null){

                                        TastyToast.makeText(context, "별루요", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        comment_dislikeCount.setText(String.valueOf( commentOb.getInt("dislike_count") ));
                                        comment_dislike.setClickable(true);

                                        if(backgroundBlack){

                                            comment_dislikeCount.setTextColor(functionBase.likedColor);
                                            comment_dislike_icon.setColorFilter(functionBase.likedColor);

                                            if(comment_dislike_text != null){

                                                comment_dislike_text.setTextColor(functionBase.likedColor);

                                            }

                                        } else {

                                            comment_dislikeCount.setTextColor(functionBase.likedPostColor);
                                            comment_dislike_icon.setColorFilter(functionBase.likedPostColor);

                                            if(comment_dislike_text != null){

                                                comment_dislike_text.setTextColor(functionBase.likedPostColor);

                                            }

                                        }



                                    } else {

                                        Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                        comment_dislike.setClickable(true);
                                        e.printStackTrace();

                                    }


                                } else {

                                    Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                    comment_dislike.setClickable(true);
                                    e.printStackTrace();

                                }


                            }
                        });

                    } else {

                        ParseRelation commentLikeRelation = commentOb.getRelation("dislike");
                        commentLikeRelation.remove(currentUser);
                        commentOb.increment("dislike_count", -1);
                        commentOb.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if(e==null){

                                    if(e==null){

                                        TastyToast.makeText(context, "별루요 취소", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                        comment_dislikeCount.setText(String.valueOf( commentOb.getInt("dislike_count") ));
                                        comment_dislike.setClickable(true);

                                        if(backgroundBlack){

                                            comment_dislikeCount.setTextColor(functionBase.likeColor);
                                            comment_dislike_icon.setColorFilter(functionBase.likeColor);

                                            if(comment_dislike_text != null){

                                                comment_dislike_text.setTextColor(functionBase.likeColor);

                                            }

                                        } else {

                                            comment_dislikeCount.setTextColor(functionBase.likePostColor);
                                            comment_dislike_icon.setColorFilter(functionBase.likePostColor);

                                            if(comment_dislike_text != null){

                                                comment_dislike_text.setTextColor(functionBase.likePostColor);

                                            }

                                        }


                                    } else {

                                        Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                        comment_dislike.setClickable(true);
                                        e.printStackTrace();

                                    }


                                } else {

                                    Toast.makeText(context, "문제 발생, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                                    comment_dislike.setClickable(true);
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


}
