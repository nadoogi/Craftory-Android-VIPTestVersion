package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PostLikeUserListAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class LikeUserListActivity extends AppCompatActivity {

    private RecyclerView like_list;
    private RequestManager requestManager;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private PostLikeUserListAdapter postLikeUserListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like_user_list);



        Intent intent = getIntent();

        String postId = intent.getExtras().getString("postId");
        requestManager = Glide.with(getApplicationContext());

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_Text = (TextView) findViewById(R.id.back_button_text);
        like_list = (RecyclerView) findViewById(R.id.like_list);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        back_button_Text.setText("좋아요 하신 분들");



        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        like_list.setLayoutManager(layoutManager);
        like_list.setHasFixedSize(true);
        like_list.setNestedScrollingEnabled(false);

        ParseQuery postQuery = ParseQuery.getQuery("ArtistPost");
        postQuery.include("user");
        postQuery.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject patronOb, ParseException e) {

                if(e==null){

                    Log.d("patronId2", patronOb.getObjectId());

                    postLikeUserListAdapter = new PostLikeUserListAdapter(getApplicationContext(), requestManager, patronOb);

                    like_list.setItemAnimator(new SlideInUpAnimator());
                    like_list.setAdapter(postLikeUserListAdapter);


                    like_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                                    postLikeUserListAdapter.loadNextPage();

                                }

                            }

                        }
                    });

                } else {

                    e.printStackTrace();
                }


            }

        });

    }
}
