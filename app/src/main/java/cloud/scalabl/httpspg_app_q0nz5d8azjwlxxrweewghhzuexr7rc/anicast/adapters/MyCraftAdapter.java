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

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionFollow;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.CreatorViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class MyCraftAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    private FunctionBase functionBase;

    public MyCraftAdapter(Context context, RequestManager requestManager, FunctionBase functionBase) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = functionBase;

        if(currentUser != null){

            final ArrayList<String> tagQuery = functionBase.jsonArrayToArrayList(currentUser.getJSONArray("tags"));
            final ArrayList<String> followQuery = functionBase.jsonArrayToArrayList(currentUser.getJSONArray("follow_array"));
            followQuery.add(currentUser.getObjectId());

            this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                @Override
                public ParseQuery<ParseObject> create() {

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                    query.whereNotContainedIn("objectId", followQuery);
                    query.whereContainedIn("tags", tagQuery);
                    query.whereEqualTo("user_role_type", "creator");
                    query.orderByDescending("follower_count");

                    return query;
                }
            };

            loadObjects(currentPage);

        } else {

            this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                @Override
                public ParseQuery<ParseObject> create() {

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                    query.whereEqualTo("user_role_type", "creator");

                    query.orderByDescending("follower_count");

                    return query;
                }
            };

            loadObjects(currentPage);

        }



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View creatorView;

        creatorView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_my_creator, parent, false);

        return new CreatorViewHolder(creatorView);



    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //기능 추가
        final ParseObject creatorOb = getItem(position);

        CircleImageView writter_photo = ((CreatorViewHolder)holder).getWritterPhoto();
        TextView writter_name = ((CreatorViewHolder)holder).getWritterName();
        final LinearLayout follow_button= ((CreatorViewHolder)holder).getFollowButton();
        final TextView follow_button_text = ((CreatorViewHolder)holder).getFollowButtonText();
        LinearLayout favor_list_layout = ((CreatorViewHolder)holder).getFavorListLayout();
        final TextView favor_msg = ((CreatorViewHolder)holder).getFavorMsg();
        final ImageView favor_image_1 = ((CreatorViewHolder)holder).getFavorImage1();
        final ImageView favor_image_2 = ((CreatorViewHolder)holder).getFavorImage2();
        final ImageView favor_image_3 = ((CreatorViewHolder)holder).getFavorImage3();
        final ImageView favor_image_4 = ((CreatorViewHolder)holder).getFavorImage4();
        final ImageView favor_image_5 = ((CreatorViewHolder)holder).getFavorImage5();
        final ImageView favor_image_6 = ((CreatorViewHolder)holder).getFavorImage6();

        functionBase.profileImageLoading(writter_photo, creatorOb, requestManager);
        functionBase.profileNameLoading(writter_name, creatorOb);

        writter_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("userId", creatorOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });

        writter_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, UserActivity.class);
                intent.putExtra("userId", creatorOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);

            }
        });




        if(creatorOb.get("post_count") != null){

            if(creatorOb.getInt("post_count") == 0){

                favor_msg.setVisibility(View.VISIBLE);

            } else {

                favor_msg.setVisibility(View.GONE);

            }

        } else {

            favor_msg.setVisibility(View.VISIBLE);

        }


        ParseQuery<ArtistPost> query1 = ParseQuery.getQuery(ArtistPost.class);
        query1.whereEqualTo("user", creatorOb);
        query1.whereEqualTo("post_type", "post");
        query1.whereEqualTo("status", true);
        query1.setLimit(6);
        query1.orderByDescending("like_count");
        query1.findInBackground(new FindCallback<ArtistPost>() {
            @Override
            public void done(List<ArtistPost> objects, ParseException e) {
                if(e==null){

                    if(objects.size() == 0){


                        favor_image_1.setImageResource(R.color.white);
                        favor_image_2.setImageResource(R.color.white);
                        favor_image_3.setImageResource(R.color.white);
                        favor_image_4.setImageResource(R.color.white);
                        favor_image_5.setImageResource(R.color.white);
                        favor_image_6.setImageResource(R.color.white);


                    } else if(objects.size() == 1){

                        ArtistPost postOb1 = objects.get(0);

                        String imageUrl1 = "";


                        functionBase.generalImageLoading(favor_image_1, postOb1, requestManager);
                        functionBase.chargeFollowPatronCheck( postOb1, favor_image_1);
                        favor_image_1.setVisibility(View.VISIBLE);



                        favor_image_2.setImageResource(R.drawable.image_background_500);
                        favor_image_3.setImageResource(R.drawable.image_background_500);
                        favor_image_4.setImageResource(R.drawable.image_background_500);
                        favor_image_5.setImageResource(R.drawable.image_background_500);
                        favor_image_6.setImageResource(R.drawable.image_background_500);

                    } else if(objects.size() == 2){

                        ArtistPost postOb1 = objects.get(0);
                        ArtistPost postOb2 = objects.get(1);

                        functionBase.generalImageLoading(favor_image_1, postOb1, requestManager);
                        functionBase.generalImageLoading(favor_image_2, postOb2, requestManager);

                        functionBase.chargeFollowPatronCheck( postOb1, favor_image_1);
                        functionBase.chargeFollowPatronCheck( postOb2, favor_image_2);

                        favor_image_1.setVisibility(View.VISIBLE);
                        favor_image_2.setVisibility(View.VISIBLE);


                        favor_image_3.setImageResource(R.drawable.image_background_500);
                        favor_image_4.setImageResource(R.drawable.image_background_500);
                        favor_image_5.setImageResource(R.drawable.image_background_500);
                        favor_image_6.setImageResource(R.drawable.image_background_500);

                    } else if(objects.size() == 3){

                        ArtistPost postOb1 = objects.get(0);
                        ArtistPost postOb2 = objects.get(1);
                        ArtistPost postOb3 = objects.get(2);

                        functionBase.generalImageLoading(favor_image_1, postOb1, requestManager);
                        functionBase.generalImageLoading(favor_image_2, postOb2, requestManager);
                        functionBase.generalImageLoading(favor_image_3, postOb3, requestManager);

                        functionBase.chargeFollowPatronCheck( postOb1, favor_image_1);
                        functionBase.chargeFollowPatronCheck( postOb2, favor_image_2);
                        functionBase.chargeFollowPatronCheck( postOb3, favor_image_3);

                        favor_image_1.setVisibility(View.VISIBLE);
                        favor_image_2.setVisibility(View.VISIBLE);
                        favor_image_3.setVisibility(View.VISIBLE);

                        favor_image_4.setImageResource(R.drawable.image_background_500);
                        favor_image_5.setImageResource(R.drawable.image_background_500);
                        favor_image_6.setImageResource(R.drawable.image_background_500);


                    } else if(objects.size() == 4){

                        ArtistPost postOb1 = objects.get(0);
                        ArtistPost postOb2 = objects.get(1);
                        ArtistPost postOb3 = objects.get(2);
                        ArtistPost postOb4 = objects.get(3);

                        functionBase.generalImageLoading(favor_image_1, postOb1, requestManager);
                        functionBase.generalImageLoading(favor_image_2, postOb2, requestManager);
                        functionBase.generalImageLoading(favor_image_3, postOb3, requestManager);
                        functionBase.generalImageLoading(favor_image_4, postOb4, requestManager);

                        functionBase.chargeFollowPatronCheck( postOb1, favor_image_1);
                        functionBase.chargeFollowPatronCheck( postOb2, favor_image_2);
                        functionBase.chargeFollowPatronCheck( postOb3, favor_image_3);
                        functionBase.chargeFollowPatronCheck( postOb4, favor_image_4);

                        favor_image_1.setVisibility(View.VISIBLE);
                        favor_image_2.setVisibility(View.VISIBLE);
                        favor_image_3.setVisibility(View.VISIBLE);
                        favor_image_4.setVisibility(View.VISIBLE);

                        favor_image_5.setImageResource(R.drawable.image_background_500);
                        favor_image_6.setImageResource(R.drawable.image_background_500);

                    } else if(objects.size() == 5){

                        ArtistPost postOb1 = objects.get(0);
                        ArtistPost postOb2 = objects.get(1);
                        ArtistPost postOb3 = objects.get(2);
                        ArtistPost postOb4 = objects.get(3);
                        ArtistPost postOb5 = objects.get(4);

                        functionBase.generalImageLoading(favor_image_1, postOb1, requestManager);
                        functionBase.generalImageLoading(favor_image_2, postOb2, requestManager);
                        functionBase.generalImageLoading(favor_image_3, postOb3, requestManager);
                        functionBase.generalImageLoading(favor_image_4, postOb4, requestManager);
                        functionBase.generalImageLoading(favor_image_5, postOb5, requestManager);

                        functionBase.chargeFollowPatronCheck( postOb1, favor_image_1);
                        functionBase.chargeFollowPatronCheck( postOb2, favor_image_2);
                        functionBase.chargeFollowPatronCheck( postOb3, favor_image_3);
                        functionBase.chargeFollowPatronCheck( postOb4, favor_image_4);
                        functionBase.chargeFollowPatronCheck( postOb5, favor_image_5);

                        favor_image_1.setVisibility(View.VISIBLE);
                        favor_image_2.setVisibility(View.VISIBLE);
                        favor_image_3.setVisibility(View.VISIBLE);
                        favor_image_4.setVisibility(View.VISIBLE);
                        favor_image_5.setVisibility(View.VISIBLE);

                        favor_image_6.setImageResource(R.color.white);

                    } else if(objects.size() == 6){

                        ArtistPost postOb1 = objects.get(0);
                        ArtistPost postOb2 = objects.get(1);
                        ArtistPost postOb3 = objects.get(2);
                        ArtistPost postOb4 = objects.get(3);
                        ArtistPost postOb5 = objects.get(4);
                        ArtistPost postOb6 = objects.get(5);

                        functionBase.generalImageLoading(favor_image_1, postOb1, requestManager);
                        functionBase.generalImageLoading(favor_image_2, postOb2, requestManager);
                        functionBase.generalImageLoading(favor_image_3, postOb3, requestManager);
                        functionBase.generalImageLoading(favor_image_4, postOb4, requestManager);
                        functionBase.generalImageLoading(favor_image_5, postOb5, requestManager);
                        functionBase.generalImageLoading(favor_image_6, postOb6, requestManager);

                        functionBase.chargeFollowPatronCheck( postOb1, favor_image_1);
                        functionBase.chargeFollowPatronCheck( postOb2, favor_image_2);
                        functionBase.chargeFollowPatronCheck( postOb3, favor_image_3);
                        functionBase.chargeFollowPatronCheck( postOb4, favor_image_4);
                        functionBase.chargeFollowPatronCheck( postOb5, favor_image_5);
                        functionBase.chargeFollowPatronCheck( postOb6, favor_image_6);

                        favor_image_1.setVisibility(View.VISIBLE);
                        favor_image_2.setVisibility(View.VISIBLE);
                        favor_image_3.setVisibility(View.VISIBLE);
                        favor_image_4.setVisibility(View.VISIBLE);
                        favor_image_5.setVisibility(View.VISIBLE);
                        favor_image_6.setVisibility(View.VISIBLE);



                    }


                } else {

                    e.printStackTrace();

                }


            }
        });



        follow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FunctionFollow functionFollow = new FunctionFollow(context);
                functionFollow.followFunction( follow_button, follow_button_text, creatorOb);

            }
        });

        if(currentUser != null){

            if(functionBase.parseArrayCheck(currentUser, "follow_array", creatorOb.getObjectId())){

                follow_button_text.setText("팔로우 취소");

            } else {

                follow_button_text.setText("팔로우");

            };

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
