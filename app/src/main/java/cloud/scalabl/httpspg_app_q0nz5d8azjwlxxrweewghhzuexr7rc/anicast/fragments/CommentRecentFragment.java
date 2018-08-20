package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ImmoticonActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.MainBoardActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentRecentAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;

import static android.app.Activity.RESULT_OK;


/**
 * Created by ssamkyu on 2016. 12. 17..
 */

public class CommentRecentFragment extends Fragment {

    static final boolean GRID_LAYOUT = false;
    private static final int ITEM_COUNT = 100;
    protected RecyclerView mRecyclerView;


    //private FirebaseRecyclerAdapter<Card, YoutubeViewHolder> mAdapter;
    protected RecyclerView.Adapter mAdapter;


    private int pager;
    private String channelId;
    private String categoryId;
    private String parentId;
    private String type;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private EditText comment_input;
    private LinearLayout send_button;
    private ParseUser currentUser;
    public CommentRecentAdapter commentRecentAdapter;

    private TextView no_comment_msg;
    private RequestManager requestManager;
    private FunctionBase functionBase;
    private LinearLayout immoticon_button;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        parentId = getArguments().getString("parentId");
        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getActivity());
        functionBase = new FunctionBase(getActivity());

        return inflater.inflate(R.layout.fragment_youtube_comment, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        no_comment_msg = (TextView) view.findViewById(R.id.no_comment_msg);
        comment_input = (EditText) view.findViewById(R.id.comment_input);
        send_button = (LinearLayout) view.findViewById(R.id.send_button);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.comment_list);
        immoticon_button = (LinearLayout) view.findViewById(R.id.immoticon_button);

        ParseQuery<ArtistPost> query = ParseQuery.getQuery(ArtistPost.class);
        query.getInBackground(parentId, new GetCallback<ArtistPost>() {
            @Override
            public void done(final ArtistPost postOb, ParseException e) {

                if(e==null){

                    immoticon_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(getActivity(), ImmoticonActivity.class);
                            startActivityForResult(intent, functionBase.REQUEST_CODE);

                        }
                    });

                    comment_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {

                            if(b){

                                if(currentUser == null){

                                    TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                                    startActivity(intent);
                                }

                            }

                        }
                    });

                    send_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            send_button.setClickable(false);

                            if(currentUser == null){

                                //회원 가입 유도
                                TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);

                                send_button.setClickable(true);

                            } else {

                                final String write_body = comment_input.getText().toString();

                                if(write_body.length() == 0){

                                    Toast.makeText(getActivity(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();
                                    send_button.setClickable(true);

                                } else {

                                    comment_input.setText("");
                                    functionBase.hideKeyboard(getActivity());

                                    HashMap<String, Object> params = new HashMap<>();

                                    params.put("key", currentUser.getSessionToken());

                                    Date uniqueIdDate = new Date();
                                    String uniqueId = uniqueIdDate.toString();

                                    params.put("uid", uniqueId);
                                    params.put("body", write_body);
                                    params.put("parent_id", postOb.getObjectId());
                                    params.put("type", "comment");
                                    params.put("commentId", "");
                                    params.put("immoticonId", "");
                                    params.put("lastAction", "commentSave");

                                    ParseCloud.callFunctionInBackground("comment_save", params, new FunctionCallback<Map<String, Object>>() {

                                        public void done(Map<String, Object> mapObject, ParseException e) {

                                            if (e == null) {

                                                Log.d("tag", mapObject.toString());

                                                if(mapObject.get("status").toString().equals("true")){

                                                    commentRecentAdapter.loadObjects(0);

                                                    TastyToast.makeText(getActivity(), "댓글이 저장 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                    send_button.setClickable(true);

                                                } else {

                                                    send_button.setClickable(true);
                                                    TastyToast.makeText(getActivity(), "저장 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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


                    final LinearLayoutManager layoutManager;

                    layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    commentRecentAdapter = new CommentRecentAdapter(getActivity(), requestManager, postOb );

                    mRecyclerView.setAdapter(commentRecentAdapter);
                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                    commentRecentAdapter.loadNextPage();
                                }

                            }

                        }
                    });

                    if(postOb.getInt("comment_count") == 0){

                        no_comment_msg.setVisibility(View.VISIBLE);

                    } else {

                        no_comment_msg.setVisibility(View.GONE);

                    }

                } else {

                    e.printStackTrace();
                }


            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

        if(commentRecentAdapter != null){

            commentRecentAdapter.loadObjects(0);

        }

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
            params.put("parent_id", parentId);
            params.put("type", "comment");
            params.put("commentId", "");
            params.put("immoticonId", itemId);
            params.put("lastAction", "commentSave");

            ParseCloud.callFunctionInBackground("comment_save", params, new FunctionCallback<Map<String, Object>>() {

                public void done(Map<String, Object> mapObject, ParseException e) {

                    if (e == null) {

                        Log.d("tag", mapObject.toString());

                        if(mapObject.get("status").toString().equals("true")){

                            commentRecentAdapter.loadObjects(0);
                            TastyToast.makeText(getActivity(), "댓글이 저장 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                        } else {

                            send_button.setClickable(true);
                            TastyToast.makeText(getActivity(), "저장 실패", TastyToast.LENGTH_LONG, TastyToast.ERROR);

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
