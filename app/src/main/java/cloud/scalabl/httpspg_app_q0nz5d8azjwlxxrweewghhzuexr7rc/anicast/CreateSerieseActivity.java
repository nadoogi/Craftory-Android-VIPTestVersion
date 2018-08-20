package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentSerieseAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class CreateSerieseActivity extends AppCompatActivity {

    private static RecyclerView new_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private static RequestManager requestManager;

    public static MyContentSerieseAdapter myContentSerieseAdapter;

    private LinearLayout create_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_seriese);

        new_content_list = (RecyclerView) findViewById(R.id.new_content_list);
        LinearLayout seriese_add_button = (LinearLayout) findViewById(R.id.seriese_add_button);
        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myContentSerieseAdapter = new MyContentSerieseAdapter(CreateSerieseActivity.this, getApplicationContext(), requestManager);
                new_content_list.setAdapter(myContentSerieseAdapter);

                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getApplicationContext());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        new_content_list.setLayoutManager(layoutManager);
        new_content_list.setHasFixedSize(true);
        new_content_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        myContentSerieseAdapter = new MyContentSerieseAdapter(this,getApplicationContext(), requestManager);



        new_content_list.setItemAnimator(new SlideInUpAnimator());
        new_content_list.setAdapter(myContentSerieseAdapter);

        new_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                    {
                        myContentSerieseAdapter.loadNextPage();
                    }

                }

            }
        });


        seriese_add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SerieseActivity.class);
                startActivity(intent);


            }
        });

        back_button_text.setText("연재 관리");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(myContentSerieseAdapter != null){

            myContentSerieseAdapter.loadObjects(0);
        }
    }
}
