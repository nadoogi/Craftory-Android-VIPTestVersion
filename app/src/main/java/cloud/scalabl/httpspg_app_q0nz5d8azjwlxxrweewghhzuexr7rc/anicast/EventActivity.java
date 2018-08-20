package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PostEventParseQueryAdapter;

public class EventActivity extends AppCompatActivity {

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private static PostEventParseQueryAdapter postEventParseQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //getSupportActionBar().setTitle("이벤트");

        RecyclerView event_list = (RecyclerView) findViewById(R.id.event_list);

        final LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        event_list.setLayoutManager(layoutManager);
        event_list.setHasFixedSize(true);

        //final ChannelRankingAdapter channelAdapter = new ChannelRankingAdapter(getApplicationContext(), "total_count", false);

        RequestManager requestManager = Glide.with(this);

        postEventParseQueryAdapter = new PostEventParseQueryAdapter(getApplicationContext(), requestManager);

        postEventParseQueryAdapter.setObjectsPerPage(20);
        event_list.setAdapter(postEventParseQueryAdapter);
        event_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        postEventParseQueryAdapter.loadNextPage();
                    }

                }

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(postEventParseQueryAdapter != null){

            postEventParseQueryAdapter.loadObjects(0);

        }

    }
}
