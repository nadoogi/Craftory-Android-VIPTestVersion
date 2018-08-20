package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.cloudinary.android.policy.TimeWindow;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentDefaultAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentHeaderParseQueryAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;

public class CommentDetailActivity extends AppCompatActivity {

    private String postId;

    int pastVisibleItems, visibleItemCount, totalItemCount;
    protected RecyclerView mRecyclerView;
    //private static CommentParseQueryAdapter commentAdapter;
    private CommentHeaderParseQueryAdapter commentAdapter;
    private CommentDefaultAdapter commentDefaultAdapter;

    private EditText comment_input;
    private LinearLayout send_button;
    private ParseUser currentUser;

    private TextView no_comment_msg;
    private ParseObject commentObject;

    private final String TAG ="CommentDetailActivity";
    private RequestManager requestManager;

    private FunctionBase functionBase;

    private LinearLayout immoticon_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        //getSupportActionBar().setTitle("답글");

        postId = getIntent().getExtras().getString("postId");
        String commentId = getIntent().getExtras().getString("commentId");

        Log.d("postId", postId);
        Log.d("commentId", commentId);

        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(this, getApplicationContext());

        comment_input = (EditText) findViewById(R.id.comment_input);
        send_button = (LinearLayout) findViewById(R.id.send_button);

        no_comment_msg = (TextView) findViewById(R.id.no_comment_msg);
        immoticon_button = (LinearLayout) findViewById(R.id.immoticon_button);

        immoticon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ImmoticonActivity.class);
                startActivityForResult(intent, functionBase.REQUEST_CODE);

            }
        });

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("답글");

        ParseQuery currentPostQuery = ParseQuery.getQuery("Comment");
        currentPostQuery.include("user");
        currentPostQuery.include("poke_item");
        currentPostQuery.getInBackground(commentId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject currentCommentOb, ParseException e) {

                if(e==null){

                    commentObject = currentCommentOb;

                    if(currentCommentOb.getInt("comment_count") == 0 ){

                        no_comment_msg.setVisibility(View.VISIBLE);

                    } else {

                        no_comment_msg.setVisibility(View.GONE);
                    }


                    comment_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {

                            if(currentUser == null){

                                TastyToast.makeText(getApplicationContext(), "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }

                        }
                    });



                    send_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(currentUser == null){

                                TastyToast.makeText(getApplicationContext(), "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            } else {

                                String write_body = comment_input.getText().toString();

                                Log.d("write_Body", write_body);

                                if(write_body.length() == 0){

                                    comment_input.setError("내용을 입력하세요!");

                                } else {

                                    HashMap<String, Object> params = new HashMap<>();

                                    params.put("key", currentUser.getSessionToken());

                                    Date uniqueIdDate = new Date();
                                    String uniqueId = uniqueIdDate.toString();

                                    params.put("uid", uniqueId);
                                    params.put("body", write_body);
                                    params.put("parent_id", currentCommentOb.getObjectId());
                                    params.put("post_id", postId);
                                    params.put("type", "reply");
                                    params.put("immoticonId", "");
                                    params.put("lastAction", "replySave");

                                    ParseCloud.callFunctionInBackground("reply_save", params, new FunctionCallback<Map<String, Object>>() {

                                        public void done(Map<String, Object> mapObject, ParseException e) {

                                            if (e == null) {

                                                Log.d("tag", mapObject.toString());

                                                if(mapObject.get("status").toString().equals("true")){

                                                    comment_input.setText("");
                                                    functionBase.hideKeyboard(CommentDetailActivity.this);

                                                    commentDefaultAdapter.loadObjects(0);
                                                    TastyToast.makeText(getApplicationContext(), "답글이 저장 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                } else {

                                                    send_button.setClickable(true);
                                                    TastyToast.makeText(getApplicationContext(), "저장 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                }

                                            } else {

                                                Log.d("step", "request fail");
                                                e.printStackTrace();
                                                send_button.setClickable(true);

                                            }
                                        }

                                    });

                                }

                            }


                        }
                    });


                    mRecyclerView = (RecyclerView) findViewById(R.id.comment_list);
                    final LinearLayoutManager layoutManager;

                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);


                    //commentAdapter = new CommentHeaderParseQueryAdapter(getApplicationContext(), FunctionBase.createFilter, false, commentOb , "reply", false, Glide.with(getApplicationContext()));
                    commentDefaultAdapter = new CommentDefaultAdapter(getApplicationContext(), requestManager, currentCommentOb );


                    commentDefaultAdapter.setObjectsPerPage(20);
                    mRecyclerView.setAdapter(commentDefaultAdapter);
                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                    commentDefaultAdapter.loadNextPage();
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
    protected void onPostResume() {
        super.onPostResume();

        if(commentDefaultAdapter!= null){

            commentDefaultAdapter.loadObjects(0);
            functionBase.noMessageStatus(commentObject, no_comment_msg);

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

            Intent intent1 = new Intent(getApplicationContext(), PostingDetailActivity.class);
            intent1.putExtra("postId", postId);
            startActivity(intent1);

            CommentDetailActivity.this.finish();

            return true;

        }

        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        CommentDetailActivity.this.finish();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == functionBase.REQUEST_CODE && resultCode == RESULT_OK && data != null){

            String itemId = data.getExtras().getString("itemId");

            HashMap<String, Object> params = new HashMap<>();

            params.put("key", currentUser.getSessionToken());

            Date uniqueIdDate = new Date();
            String uniqueId = uniqueIdDate.toString();

            params.put("uid", uniqueId);
            params.put("body", "");
            params.put("parent_id", commentObject.getObjectId());
            params.put("post_id", postId);
            params.put("immoticonId", itemId);
            params.put("type", "reply");
            params.put("lastAction", "replySave");

            ParseCloud.callFunctionInBackground("reply_save", params, new FunctionCallback<Map<String, Object>>() {

                public void done(Map<String, Object> mapObject, ParseException e) {

                    if (e == null) {

                        Log.d("tag", mapObject.toString());

                        if(mapObject.get("status").toString().equals("true")){

                            commentDefaultAdapter.loadObjects(0);
                            TastyToast.makeText(getApplicationContext(), "답글이 저장 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                        } else {

                            send_button.setClickable(true);
                            TastyToast.makeText(getApplicationContext(), "저장 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        }

                    } else {

                        Log.d("step", "request fail");
                        e.printStackTrace();
                        send_button.setClickable(true);

                    }
                }

            });


        } else {

            Log.d("message", "not selected");

        }



    }


}
