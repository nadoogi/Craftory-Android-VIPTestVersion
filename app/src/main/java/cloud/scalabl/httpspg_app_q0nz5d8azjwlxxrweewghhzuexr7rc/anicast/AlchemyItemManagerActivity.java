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

import java.util.ArrayList;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentPostAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.TagItemManagerAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class AlchemyItemManagerActivity extends AppCompatActivity {

    private RecyclerView my_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    private RequestManager requestManager;
    private TagItemManagerAdapter tagItemManagerAdapter;
    private GridLayoutManager layoutManager;

    private ArrayList<String> tagString;
    private LinearLayout tag_input_button;

    private FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alchemy_item_manager);

        my_content_list = (RecyclerView) findViewById(R.id.my_content_list);
        LinearLayout create_button = (LinearLayout) findViewById(R.id.create_button);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);


        final PullRefreshLayout swipeLayout = (PullRefreshLayout) findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //myContentPostAdapter.loadObjects(0);
                tagItemManagerAdapter = new TagItemManagerAdapter( getApplicationContext(), requestManager);
                my_content_list.setAdapter(tagItemManagerAdapter);

                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());

        layoutManager = new GridLayoutManager(getApplicationContext(),3);

        my_content_list.setLayoutManager(layoutManager);
        my_content_list.setHasFixedSize(true);
        my_content_list.setNestedScrollingEnabled(false);

        tagItemManagerAdapter = new TagItemManagerAdapter( getApplicationContext(), requestManager);


        my_content_list.setItemAnimator(new SlideInUpAnimator());
        my_content_list.setAdapter(tagItemManagerAdapter);

        my_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy > 0){
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        tagItemManagerAdapter.loadNextPage();
                    }

                }

            }
        });

        back_button_text.setText("연성태그관리");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        create_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TagItemMakerActivity.class);
                startActivity(intent);

            }
        });






    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(tagItemManagerAdapter != null){

            tagItemManagerAdapter.loadObjects(0);
        }
    }
}
