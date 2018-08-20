package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FavorCraftorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.RecommendCraftorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.RecommendCraftFollowListener;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RecommendCreatorActivity extends AppCompatActivity implements RecommendCraftFollowListener{

    int pastVisibleItems, visibleItemCount, totalItemCount;
    int pastVisibleItems2, visibleItemCount2, totalItemCount2;

    public static RecommendCraftorAdapter recommendCraftorAdapter;
    public static FavorCraftorAdapter favorCraftorAdapter;

    LinearLayout next_button;

    ParseUser currentUser;

    LinearLayout creator1_button;
    LinearLayout creator2_button;
    LinearLayout creator3_button;

    TextView creator_selected_1;
    TextView creator_selected_2;
    TextView creator_selected_3;

    TextView creator_count;
    TextView creator_message;

    ParseObject followOb1;
    ParseObject followOb2;
    ParseObject followOb3;

    int followCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creator_recommend);

        RequestManager requestManager = Glide.with(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        RecyclerView recommend_creator_list = (RecyclerView) findViewById(R.id.recommend_creator_list);
        next_button = (LinearLayout) findViewById(R.id.next_button);
        next_button.setVisibility(View.GONE);

        creator1_button = (LinearLayout) findViewById(R.id.creator1_button);
        creator2_button = (LinearLayout) findViewById(R.id.creator2_button);
        creator3_button = (LinearLayout) findViewById(R.id.creator3_button);

        creator_selected_1 = (TextView) findViewById(R.id.creator_selected_1);
        creator_selected_2 = (TextView) findViewById(R.id.creator_selected_2);
        creator_selected_3 = (TextView) findViewById(R.id.creator_selected_3);


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(followCount > 2){

                    currentUser.put("creator_check", true);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                                startActivity(intent);

                                finish();

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                } else {

                    TastyToast.makeText(getApplicationContext(), "작가를 팔로우 해주세요!", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

            }
        });

        if(currentUser != null){

            reloadFollow(currentUser);

        }

        final LinearLayoutManager recommend_layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);

        recommend_creator_list.setLayoutManager(recommend_layoutManager);
        recommend_creator_list.setHasFixedSize(false);

        recommendCraftorAdapter = new RecommendCraftorAdapter(getApplicationContext(), requestManager);
        recommendCraftorAdapter.setRecommendCraftFollowListener(this);

        recommend_creator_list.setItemAnimator(new SlideInUpAnimator());
        recommend_creator_list.setAdapter(recommendCraftorAdapter);

        recommend_creator_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) {
                    visibleItemCount2 = recommend_layoutManager.getChildCount();
                    totalItemCount2 = recommend_layoutManager.getItemCount();
                    pastVisibleItems2 = recommend_layoutManager.findFirstVisibleItemPosition();

                }

            }
        });


        RecyclerView creator_list = (RecyclerView) findViewById(R.id.creator_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);

        creator_list.setLayoutManager(layoutManager);
        creator_list.setHasFixedSize(false);


        favorCraftorAdapter = new FavorCraftorAdapter(getApplicationContext(), requestManager);
        favorCraftorAdapter.setRecommendCraftFollowListener(this);

        creator_list.setItemAnimator(new SlideInUpAnimator());
        creator_list.setAdapter(favorCraftorAdapter);

        creator_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                }

            }
        });

    }

    private void reloadFollow(final ParseObject currentUser){

        ParseQuery followQuery = currentUser.getRelation("follow").getQuery();
        followQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> followObs, ParseException e) {

                followCount = followObs.size();

                if(followObs.size() == 0){

                    next_button.setVisibility(View.GONE);
                    creator_selected_1.setText( "+" );
                    creator_selected_2.setText("+");
                    creator_selected_3.setText("+");

                } else if(followObs.size() == 1){

                    next_button.setVisibility(View.GONE);

                    followOb1 = followObs.get(0);
                    creator_selected_1.setText( followOb1.getString("name") );
                    creator_selected_2.setText("+");
                    creator_selected_3.setText("+");

                    creator1_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<String> followList = new ArrayList<>();
                            followList.add(followOb1.getObjectId());

                            ParseRelation followRelation = currentUser.getRelation("follow");
                            followRelation.remove(followOb1);
                            currentUser.removeAll("follow_array", followList);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        reloadFollow(currentUser);

                                    } else {

                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });

                } else if(followObs.size() == 2){

                    next_button.setVisibility(View.GONE);

                    followOb1 = followObs.get(0);
                    followOb2 = followObs.get(1);

                    creator_selected_1.setText( followOb1.getString("name") );
                    creator_selected_2.setText( followOb2.getString("name") );
                    creator_selected_3.setText("+");

                    creator1_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<String> followList = new ArrayList<>();
                            followList.add(followOb1.getObjectId());

                            ParseRelation followRelation = currentUser.getRelation("follow");
                            followRelation.remove(followOb1);
                            currentUser.removeAll("follow_array", followList);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        reloadFollow(currentUser);

                                    } else {

                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });

                    creator2_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<String> followList = new ArrayList<>();
                            followList.add(followOb2.getObjectId());

                            ParseRelation followRelation = currentUser.getRelation("follow");
                            followRelation.remove(followOb2);
                            currentUser.removeAll("follow_array", followList);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        reloadFollow(currentUser);

                                    } else {

                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });

                } else {

                    next_button.setVisibility(View.VISIBLE);

                    followOb1 = followObs.get(0);
                    followOb2 = followObs.get(1);
                    followOb3 = followObs.get(2);

                    creator_selected_1.setText( followOb1.getString("name") );
                    creator_selected_2.setText( followOb2.getString("name") );
                    creator_selected_3.setText( followOb3.getString("name") );

                    creator1_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<String> followList = new ArrayList<>();
                            followList.add(followOb1.getObjectId());

                            ParseRelation followRelation = currentUser.getRelation("follow");
                            followRelation.remove(followOb1);
                            currentUser.removeAll("follow_array", followList);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        reloadFollow(currentUser);

                                    } else {

                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });

                    creator2_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<String> followList = new ArrayList<>();
                            followList.add(followOb2.getObjectId());

                            ParseRelation followRelation = currentUser.getRelation("follow");
                            followRelation.remove(followOb2);
                            currentUser.removeAll("follow_array", followList);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        reloadFollow(currentUser);

                                    } else {

                                        e.printStackTrace();
                                    }

                                }
                            });


                        }
                    });

                    creator3_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ArrayList<String> followList = new ArrayList<>();
                            followList.add(followOb3.getObjectId());

                            ParseRelation followRelation = currentUser.getRelation("follow");
                            followRelation.remove(followOb3);
                            currentUser.removeAll("follow_array", followList);
                            currentUser.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        reloadFollow(currentUser);

                                    } else {

                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });

                }

            }

        });



    }

    @Override
    public void onFollowClicked() {

        reloadFollow(currentUser);
        recommendCraftorAdapter.loadObjects(0);
        favorCraftorAdapter.loadObjects(0);

    }
}
