package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ReportParseQueryAdapter;


public class ReportActivity extends AppCompatActivity {

    private static RecyclerView mRecyclerView;
    private static ReportParseQueryAdapter reportParseQueryAdapter;
    private static String parentId;
    private String type;

    int pastVisibleItems, visibleItemCount, totalItemCount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();

        if (intent != null){

            parentId = intent.getExtras().getString("parentId");
            type = intent.getExtras().getString("type");

        } else {

            Toast.makeText(ReportActivity.this, "no card id", Toast.LENGTH_SHORT).show();

        }

        //getSupportActionBar().setTitle("신고하기");

        mRecyclerView = (RecyclerView) findViewById(R.id.report_list);

        final LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        //Use this now
        //mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());

        //mAdapter = new TestRecyclerViewAdapter(mContentItems);

        ParseQuery contentQuery = ParseQuery.getQuery("Content");
        contentQuery.getInBackground(parentId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject contentOb, ParseException e) {

                if(e==null){

                    reportParseQueryAdapter = new ReportParseQueryAdapter(ReportActivity.this, getApplicationContext(), "order", true, contentOb);

                    reportParseQueryAdapter.setObjectsPerPage(20);
                    mRecyclerView.setAdapter(reportParseQueryAdapter);
                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                    reportParseQueryAdapter.loadNextPage();
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


            if(type.equals("youtube")){

                Log.d("cardId1", parentId);

                Intent intent = new Intent(getApplicationContext(), YoutubeActivity.class);
                intent.putExtra("cardId", parentId);
                startActivity(intent);

                ReportActivity.this.finish();

            } else {

                Intent intent = new Intent(getApplicationContext(), PhotoContentsActivity.class);
                intent.putExtra("cardId", parentId);
                startActivity(intent);

                ReportActivity.this.finish();

            }




        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(type.equals("youtube")){

            Log.d("cardId2", parentId);

            Intent intent = new Intent(getApplicationContext(), YoutubeActivity.class);
            intent.putExtra("cardId", parentId);
            startActivity(intent);


            ReportActivity.this.finish();

        } else {

            Intent intent = new Intent(getApplicationContext(), PhotoContentsActivity.class);
            intent.putExtra("cardId", parentId);
            startActivity(intent);

            ReportActivity.this.finish();



        }



    }


}
