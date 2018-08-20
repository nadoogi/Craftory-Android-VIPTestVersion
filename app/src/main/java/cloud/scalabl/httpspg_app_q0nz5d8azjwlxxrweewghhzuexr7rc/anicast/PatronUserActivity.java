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

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PatronUserAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class PatronUserActivity extends AppCompatActivity {

    private RecyclerView patron_user_list;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private RequestManager requestManager;
    private PatronUserAdapter patronUserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron_user);

        Intent intent = getIntent();

        String patronId = intent.getExtras().getString("patronId");

        Log.d("patronId", patronId);

        requestManager = Glide.with(getApplicationContext());

        patron_user_list = (RecyclerView) findViewById(R.id.patron_user_list);
        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_Text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });
        back_button_Text.setText("후원 해주신분들");

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        patron_user_list.setLayoutManager(layoutManager);
        patron_user_list.setHasFixedSize(true);
        patron_user_list.setNestedScrollingEnabled(false);

        ParseQuery postQuery = ParseQuery.getQuery("ArtistPost");
        postQuery.include("user");
        postQuery.getInBackground(patronId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject patronOb, ParseException e) {

                if(e==null){

                    Log.d("patronId2", patronOb.getObjectId());

                    patronUserAdapter = new PatronUserAdapter(getApplicationContext(), requestManager, patronOb);

                    patron_user_list.setItemAnimator(new SlideInUpAnimator());
                    patron_user_list.setAdapter(patronUserAdapter);


                    patron_user_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                                    patronUserAdapter.loadNextPage();

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
