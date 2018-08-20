package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PostDetailAdapter;

public class PostingDetailActivity extends AppCompatActivity {

    private static String commentId;
    private static String parentId;
    private static String type = "post";


    int pastVisibleItems, visibleItemCount, totalItemCount;
    protected RecyclerView mRecyclerView;
    private PostDetailAdapter postDetailAdapter;

    private ParseObject postObject;

    private TextView back_button_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);


        String postId = getIntent().getExtras().getString("postId");

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        ParseUser currentUser = ParseUser.getCurrentUser();

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("글보기");


        ParseQuery commentQuery = ParseQuery.getQuery("ArtistPost");
        commentQuery.include("user");
        commentQuery.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject postOb, ParseException e) {

                if(e==null){

                    postObject = postOb;

                    if(postOb.get("title") != null){

                        back_button_text.setText(postOb.getString("title"));

                    } else if(postOb.get("body") != null){

                        back_button_text.setText(postOb.getString("body"));

                    } else {

                        back_button_text.setText("입력 안됨");

                    }



                    mRecyclerView = (RecyclerView) findViewById(R.id.comment_list);
                    final GridLayoutManager layoutManager;

                    //layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
                    layoutManager = new GridLayoutManager(getApplicationContext(),2);

                    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

                        @Override
                        public int getSpanSize(int position) {
                            if(position == 0){

                                return 2;

                            } else if(position == 1){

                                return 2;

                            }

                            return 1;
                        }
                    });

                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);


                    postDetailAdapter = new PostDetailAdapter( PostingDetailActivity.this , getApplicationContext(), Glide.with(getApplicationContext()), postOb);
                    mRecyclerView.setAdapter(postDetailAdapter);




                } else {

                    e.printStackTrace();

                }


            }

        });



    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(postDetailAdapter != null){

            postDetailAdapter.loadObjects(0);
        }

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            /*
            Log.d("parentId1", parentId);
            Log.d("type1", type);

            Intent intent1 = new Intent(getApplicationContext(), CommentActivity.class);
            intent1.putExtra("parentId", parentId);
            intent1.putExtra("type", type);
            startActivity(intent1);
            */

            PostingDetailActivity.this.finish();

            return true;

        }

        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        PostingDetailActivity.this.finish();

    }

}
