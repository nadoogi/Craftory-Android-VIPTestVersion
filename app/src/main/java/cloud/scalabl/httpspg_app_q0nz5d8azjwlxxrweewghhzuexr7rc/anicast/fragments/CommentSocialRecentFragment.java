package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommentSocialRecentAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionComment;


/**
 * Created by ssamkyu on 2016. 12. 17..
 */

public class CommentSocialRecentFragment extends Fragment {

    static final boolean GRID_LAYOUT = false;
    private final int ITEM_COUNT = 100;
    protected RecyclerView mRecyclerView;


    //private FirebaseRecyclerAdapter<Card, YoutubeViewHolder> mAdapter;
    protected RecyclerView.Adapter mAdapter;
    public static CommentSocialRecentAdapter commentSocialRecentAdapter;

    private int pager;
    private String channelId;
    private String categoryId;
    private String parentId;
    private String type;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private EditText comment_input;
    private LinearLayout send_button;
    private ParseUser currentUser;


    private TextView no_comment_msg;
    private RequestManager requestManager;
    private FunctionBase functionBase;

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


        ParseQuery query = ParseQuery.getQuery("SocialMessage");
        query.getInBackground(parentId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject postOb, ParseException e) {

                if(e==null){

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

                            if(currentUser == null){

                                //회원 가입 유도
                                TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);


                            } else {

                                final String write_body = comment_input.getText().toString();

                                if(write_body.length() == 0){

                                    Toast.makeText(getActivity(), "내용을 입력하세요", Toast.LENGTH_SHORT).show();

                                } else {

                                    final ParseObject commentOb = new ParseObject("Comment");
                                    commentOb.put("body", write_body);
                                    commentOb.put("user", currentUser);
                                    commentOb.put("parent_id", postOb.getObjectId());
                                    commentOb.put("socialOb", postOb);
                                    commentOb.put("like_count", 0);
                                    commentOb.put("comment_count", 0);
                                    commentOb.put("status", true);
                                    commentOb.put("type", "socialcomment");
                                    commentOb.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                comment_input.setText("");
                                                functionBase.hideKeyboard(getActivity());


                                                final FunctionComment functionComment = new FunctionComment(getActivity());
                                                functionComment.CommentWithSocialRelationMaker(currentUser, commentOb, postOb, new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if(e==null){

                                                            commentSocialRecentAdapter.loadObjects(0);

                                                        } else {


                                                            e.printStackTrace();
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

                        }
                    });


                    final LinearLayoutManager layoutManager;

                    layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);

                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setHasFixedSize(true);

                    commentSocialRecentAdapter = new CommentSocialRecentAdapter(getActivity(), requestManager, postOb );

                    mRecyclerView.setAdapter(commentSocialRecentAdapter);
                    mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                    commentSocialRecentAdapter.loadNextPage();
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

        if(commentSocialRecentAdapter != null){

            commentSocialRecentAdapter.loadObjects(0);

        }

    }



}
