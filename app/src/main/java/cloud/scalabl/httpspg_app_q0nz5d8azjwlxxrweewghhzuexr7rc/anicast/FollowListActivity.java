package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyRecentFollowListAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class FollowListActivity extends AppCompatActivity {

    private TextView follow_name;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_list);

        //getSupportActionBar().setTitle("팔로우 리스트");

        RecyclerView follow_list = (RecyclerView) findViewById(R.id.follow_list);

        RequestManager requestManager = Glide.with(getApplicationContext());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        follow_list.setLayoutManager(layoutManager);
        follow_list.setHasFixedSize(true);
        follow_list.setNestedScrollingEnabled(false);

        final MyRecentFollowListAdapter myRecentFollowAdapter = new MyRecentFollowListAdapter(getApplicationContext(), requestManager);

        follow_list.setItemAnimator(new SlideInUpAnimator());
        follow_list.setAdapter(myRecentFollowAdapter);


        follow_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        myRecentFollowAdapter.loadNextPage();

                    }

                }

            }
        });


    }
}
