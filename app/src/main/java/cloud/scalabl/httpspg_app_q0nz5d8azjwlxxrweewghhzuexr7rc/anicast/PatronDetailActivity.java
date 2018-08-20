package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PatronDetailAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionLikeDislike;
import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;

public class PatronDetailActivity extends AppCompatActivity {

    private static String commentId;
    private static String parentId;
    private static String type = "post";
    private static String postId;

    int pastVisibleItems, visibleItemCount, totalItemCount;//
    protected RecyclerView mRecyclerView;//
    private static PatronDetailAdapter patronDetailAdapter;//

    private ParseUser currentUser;
    private ParseObject postObject;

    private ParseObject currentPointOb;

    private RequestManager requestManager;
    private FunctionBase functionBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron_detail);

        postId = getIntent().getExtras().getString("postId");

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        currentUser = ParseUser.getCurrentUser();

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("굿즈펀딩 상세보기");


        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(this, getApplicationContext());



        if(currentUser != null){

            if(currentUser.get("point") != null){

                Log.d("point", "yes");

                currentUser.getParseObject("point").fetchInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {

                        if(e==null){

                            currentPointOb = object;

                        } else {

                            e.printStackTrace();
                        }

                    }
                });

            } else {

                Log.d("point", "no");
            }

        }



        ParseQuery patronQuery = ParseQuery.getQuery("ArtistPost");
        patronQuery.include("user");
        patronQuery.include("item");
        patronQuery.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject postOb, ParseException e) {

                if(e==null){

                    postObject = postOb;

                    mRecyclerView = (RecyclerView) findViewById(R.id.recommend_patron_list);


                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);
                    mRecyclerView.setNestedScrollingEnabled(false);


                    patronDetailAdapter = new PatronDetailAdapter( PatronDetailActivity.this,  getApplicationContext(), Glide.with(getApplicationContext()), postOb);

                    mRecyclerView.setAdapter(patronDetailAdapter);

                    patronDetailAdapter.addOnQueryLoadListener(new PatronDetailAdapter.OnQueryLoadListener<ParseObject>() {
                        @Override
                        public void onLoading() {



                        }

                        @Override
                        public void onLoaded(List<ParseObject> parseObjects, Exception e) {


                        }
                    });

                } else {

                    e.printStackTrace();

                }


            }

        });



    }




    @Override
    protected void onPostResume() {
        super.onPostResume();


        if(patronDetailAdapter != null){

            patronDetailAdapter.loadObjects(0);

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

            PatronDetailActivity.this.finish();

            return true;

        }

        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        PatronDetailActivity.this.finish();

    }

}
