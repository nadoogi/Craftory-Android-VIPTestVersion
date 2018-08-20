package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PostNoticeParseQueryAdapter;

public class NoticeActivity extends AppCompatActivity {

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private static PostNoticeParseQueryAdapter postNoticeParseQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        //getSupportActionBar().setTitle("공지사항");

        RecyclerView notice_list = (RecyclerView) findViewById(R.id.notice_list);

        final LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        notice_list.setLayoutManager(layoutManager);
        notice_list.setHasFixedSize(true);

        //final ChannelRankingAdapter channelAdapter = new ChannelRankingAdapter(getApplicationContext(), "total_count", false);

        RequestManager requestManager = Glide.with(this);

        postNoticeParseQueryAdapter = new PostNoticeParseQueryAdapter(getApplicationContext(), requestManager);

        postNoticeParseQueryAdapter.setObjectsPerPage(20);
        notice_list.setAdapter(postNoticeParseQueryAdapter);
        notice_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        postNoticeParseQueryAdapter.loadNextPage();
                    }

                }

            }
        });





    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(postNoticeParseQueryAdapter != null){

            postNoticeParseQueryAdapter.loadObjects(0);

        }
    }
}
