package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SerieseAddExistItemAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;

public class SerieseAddFromExistActivity extends AppCompatActivity {

    private static RecyclerView exist_artwork_list;
    private static SerieseAddExistItemAdapter serieseAddExistItemAdapter;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seriese_add_from_exist);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        back_button_text.setText("기존 작품 추가");

        Intent intent = getIntent();
        String serieseId = intent.getExtras().getString("serieseId");


        exist_artwork_list = (RecyclerView) findViewById(R.id.exist_artwork_list);

        ParseQuery<ArtistPost> query = ParseQuery.getQuery(ArtistPost.class);
        query.getInBackground(serieseId, new GetCallback<ArtistPost>() {
            @Override
            public void done(ArtistPost object, ParseException e) {

                if(e== null){

                    final GridLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);

                    exist_artwork_list.setLayoutManager(layoutManager);
                    exist_artwork_list.setHasFixedSize(true);


                    serieseAddExistItemAdapter = new SerieseAddExistItemAdapter( SerieseAddFromExistActivity.this, getApplicationContext(), Glide.with(getApplicationContext()), object);

                    exist_artwork_list.setAdapter(serieseAddExistItemAdapter);

                    exist_artwork_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                                    serieseAddExistItemAdapter.loadNextPage();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            finish();

        }

        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

}
