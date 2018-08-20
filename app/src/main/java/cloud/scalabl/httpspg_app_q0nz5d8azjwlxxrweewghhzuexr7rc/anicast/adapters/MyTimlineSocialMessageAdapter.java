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
import android.widget.Toast;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.CommentSocialActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PokeResponseActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SocialViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class MyTimlineSocialMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    public MyTimlineSocialMessageAdapter(Context context, RequestManager requestManager, FunctionBase functionBase) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.userOb = userOb;
        this.functionBase = functionBase;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("SocialMessage");
                query.whereEqualTo("status", true);
                query.whereEqualTo("target", currentUser);
                query.include("user");
                query.include("pokeItem");
                query.include("target");
                query.include("cheer_point");
                query.include("artist_post");
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View socialMSGView;

        socialMSGView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_social, parent, false);

        return new SocialViewHolder(socialMSGView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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

        if(socialMSGOb.getString("type").equals("request")){

            request_layout.setVisibility(View.VISIBLE);
            final ParseObject userOb = socialMSGOb.getParseObject("user");
            final ParseObject postOb = socialMSGOb.getParseObject("artist_post");
            functionBase.profileImageLoading(user_image_request, userOb, requestManager);
            functionBase.profileNameLoading(username_request,userOb);
            functionBase.generalImageLoading(image_request, postOb, requestManager);

            title_request.setText( postOb.getString("title") );
            body_request.setText( postOb.getString("body") );

            request_detail_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, PatronDetailActivity.class);
                    intent.putExtra("postId", postOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

            if(socialMSGOb.get("progress") != null){

                if(socialMSGOb.getString("progress").equals("offer")){

                    request_accept_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(context, "준비 중 입니다.", Toast.LENGTH_LONG).show();

                            /*

                            HashMap<String, Object> params = new HashMap<String, Object>();

                            params.put("key", currentUser.getSessionToken());
                            params.put("socialOb", socialMSGOb.getObjectId());
                            params.put("requestOb", postOb.getObjectId());

                            ParseCloud.callFunctionInBackground("request_accept", params, new FunctionCallback<Map<String, Object>>() {

                                public void done(Map<String, Object> mapObject, ParseException e) {

                                    if (e == null) {

                                        Log.d("parse result", mapObject.toString());

                                        if(mapObject.get("status").toString().equals("true")){

                                            TastyToast.makeText( context, "수락 완료.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                            loadObjects(0);

                                        } else {

                                            accept_cheer_button.setClickable(true);
                                            TastyToast.makeText( context, "수락 중 에러가 발생했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                        }

                                    } else {
                                        Log.d("error", "error");
                                        e.printStackTrace();
                                    }
                                }
                            });

                            */


                        }
                    });

                } else {

                    request_accept_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Toast.makeText(context, "수락 되었습니다.", Toast.LENGTH_LONG).show();

                        }
                    });

                }


            }




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
                            Date uniqueIdDate = new Date();
                            String uniqueId = uniqueIdDate.toString();

                            params.put("uid", uniqueId);

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

        //기능 추가

    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);


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
