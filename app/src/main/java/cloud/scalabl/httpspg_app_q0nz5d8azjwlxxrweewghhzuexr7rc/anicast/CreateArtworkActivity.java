package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentPostAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class CreateArtworkActivity extends AppCompatActivity {

    private static RecyclerView my_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    //public static MyTimelineAdapter myTimelineAdapter;
    private static RequestManager requestManager;
    private MyContentPostAdapter myContentPostAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_artwork);


        my_content_list = (RecyclerView) findViewById(R.id.my_content_list);
        LinearLayout create_button = (LinearLayout) findViewById(R.id.create_button);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);


        final PullRefreshLayout swipeLayout = (PullRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //myContentPostAdapter.loadObjects(0);
                myContentPostAdapter = new MyContentPostAdapter(CreateArtworkActivity.this, getApplicationContext(), requestManager);
                my_content_list.setAdapter(myContentPostAdapter);

                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getApplicationContext());

        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        final GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);

        my_content_list.setLayoutManager(layoutManager);
        my_content_list.setHasFixedSize(true);
        my_content_list.setNestedScrollingEnabled(false);

        myContentPostAdapter = new MyContentPostAdapter(this,  getApplicationContext(), requestManager);


        my_content_list.setItemAnimator(new SlideInUpAnimator());
        my_content_list.setAdapter(myContentPostAdapter);

        my_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy > 0){
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        myContentPostAdapter.loadNextPage();
                    }

                }

            }
        });

        back_button_text.setText("작품관리");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ArtPostingActivity.class);
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(myContentPostAdapter != null){

            myContentPostAdapter.loadObjects(0);
        }



    }
}
