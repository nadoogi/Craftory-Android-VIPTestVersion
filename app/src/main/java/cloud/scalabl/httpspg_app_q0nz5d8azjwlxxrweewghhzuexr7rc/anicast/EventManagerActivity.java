package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyEventAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class EventManagerActivity extends AppCompatActivity {

    RecyclerView event_list;
    Button event_edit_button;
    private MyEventAdapter myEventAdapter;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manager);

        event_list = (RecyclerView) findViewById(R.id.event_list);
        event_edit_button = (Button) findViewById(R.id.event_edit_button);

        event_edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EventEditActivity.class);
                startActivity(intent);

            }
        });

        RequestManager requestManager = Glide.with(getApplicationContext());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        event_list.setLayoutManager(layoutManager);
        event_list.setHasFixedSize(true);
        event_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        myEventAdapter = new MyEventAdapter(getApplicationContext(), requestManager);

        event_list.setItemAnimator(new SlideInUpAnimator());
        event_list.setAdapter(myEventAdapter);

        event_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();


                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        myEventAdapter.loadNextPage();

                    }



                }

            }



        });


    }
}
