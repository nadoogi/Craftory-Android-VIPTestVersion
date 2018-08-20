package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class YoutubePreviewActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener, YouTubePlayer.PlayerStateChangeListener{

    private String contentId;
    private String cardId;

    private YouTubePlayerView youTubePlayerView;


    private int currentPosition = 0;
    private Boolean mainActivity = true;

    private Boolean autoPlay = true;
    private YouTubePlayer player;

    private Boolean firstTime = true;

    private LinearLayout add_info;
    private LinearLayout top_youtube;

    //private ListView youtube_list;

    private LinearLayout tap_navigation;

    private ParseObject currentContentOb;

    private ParseQueryAdapter contentAdapter;

    private LinearLayout likeButton;
    private LinearLayout commentButton;
    private LinearLayout reportButton;
    private TextView like_text;
    private ImageView like_icon;

    private ParseUser currentUser;
    private Boolean likeFlag = true;

    private TextView commentCount;
    private TextView like_count;

    private int likeColor;
    private int likedColor;
    private int reportColor;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private FunctionBase functionBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        Intent intent = getIntent();

        if (intent.getExtras() != null){

            contentId = intent.getExtras().getString("cardId");

        } else {

            Toast.makeText(this, "컨텐츠 아이디가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
            finish();

        }

        likeColor = Color.parseColor("#ffffff");
        likedColor = Color.parseColor("#2db2ff");

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getApplicationContext());

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        likeButton = (LinearLayout) findViewById(R.id.like_button);
        commentButton = (LinearLayout) findViewById(R.id.comment_button);
        reportButton = (LinearLayout) findViewById(R.id.report_button);
        like_text = (TextView) findViewById(R.id.like_text);
        like_icon = (ImageView) findViewById(R.id.like_icon);

        commentCount = (TextView) findViewById(R.id.comment_counter);
        like_count = (TextView) findViewById(R.id.like_count);
        RecyclerView youtube_list = (RecyclerView) findViewById(R.id.youtube_list);

        likeButton.setClickable(false);

        RequestManager requestManager = Glide.with(this);


        if(currentUser == null){

            likeFlag = false;
            like_text.setTextColor(likeColor);
            like_icon.setColorFilter(likeColor);
            like_count.setTextColor(likeColor);

        } else {

            ParseQuery likeQuery = currentUser.getRelation("likes").getQuery();
            likeQuery.whereEqualTo("objectId", contentId);
            likeQuery.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> likeObs, ParseException e) {

                    if(e==null){

                        if(likeObs.size() == 0){
                            //좋아요 없음
                            likeFlag = false;
                            like_text.setTextColor(likeColor);
                            like_icon.setColorFilter(likeColor);
                            like_count.setTextColor(likeColor);


                        } else {
                            //좋아요 있음
                            likeFlag = true;
                            like_text.setTextColor(likedColor);
                            like_icon.setColorFilter(likedColor);
                            like_count.setTextColor(likedColor);

                        }

                        likeButton.setClickable(true);

                    } else {

                        e.printStackTrace();
                        likeButton.setClickable(true);
                    }

                }


            });

        }

        final ParseQuery<ParseObject> contentQuery = ParseQuery.getQuery("ArtistPost");
        //contentQuery.include("channel");
        //contentQuery.include("category");
        contentQuery.getInBackground(contentId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e == null){

                    currentContentOb = object;








                    int likeCount = currentContentOb.getInt("like_count");

                    like_count.setText(String.valueOf(likeCount));


                    if(currentContentOb.get("comment_count") != null){

                        commentCount.setText(String.valueOf(currentContentOb.getInt("comment_count")));

                    }


                    likeButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(currentUser == null){

                                Toast.makeText(getApplicationContext(), "로그인 해야 합니다", Toast.LENGTH_SHORT).show();

                            } else {

                                likeButton.setClickable(false);

                                if(likeFlag){


                                    TastyToast.makeText(getApplicationContext(), "좋아요 취소", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                                    ParseRelation likeRelation = currentUser.getRelation("likes");
                                    likeRelation.remove(currentContentOb);
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                currentContentOb.increment("like_count", -1);
                                                currentContentOb.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        likeFlag = false;

                                                        ParseObject currentChannel = currentContentOb.getParseObject("channel");
                                                        ParseObject currentCategory = currentContentOb.getParseObject("category");

                                                        currentChannel.increment("like_count", -1);
                                                        currentChannel.saveInBackground();

                                                        currentCategory.increment("like_count", -1);
                                                        currentCategory.saveInBackground();


                                                        like_text.setTextColor(likeColor);
                                                        like_icon.setColorFilter(likeColor);
                                                        like_count.setTextColor(likeColor);
                                                        likeButton.setClickable(true);

                                                        like_count.setText( String.valueOf(currentContentOb.getInt("like_count"))  );

                                                    }
                                                });




                                            } else {

                                                e.printStackTrace();
                                                likeButton.setClickable(true);

                                            }


                                        }
                                    });

                                } else {

                                    TastyToast.makeText(getApplicationContext(), "좋아요", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                                    ParseRelation likeRelation = currentUser.getRelation("likes");
                                    likeRelation.add(currentContentOb);
                                    currentUser.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {

                                            if(e==null){

                                                currentContentOb.increment("like_count", 1);
                                                currentContentOb.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        ParseObject currentChannel = currentContentOb.getParseObject("channel");
                                                        ParseObject currentCategory = currentContentOb.getParseObject("category");

                                                        currentChannel.increment("like_count", 1);
                                                        currentChannel.saveInBackground();

                                                        currentCategory.increment("like_count", 1);
                                                        currentCategory.saveInBackground();

                                                        likeFlag = true;
                                                        like_text.setTextColor(likedColor);
                                                        like_icon.setColorFilter(likedColor);
                                                        like_count.setTextColor(likedColor);
                                                        likeButton.setClickable(true);

                                                        like_count.setText( String.valueOf(currentContentOb.getInt("like_count"))  );

                                                    }
                                                });



                                            } else {

                                                e.printStackTrace();
                                                likeButton.setClickable(true);

                                            }


                                        }
                                    });

                                }


                            }

                        }
                    });



                    youTubePlayerView.initialize(functionBase.DEVELOPER_KEY, YoutubePreviewActivity.this);

                    reportButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if(currentUser!= null){

                                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                                intent.putExtra("parentId", contentId);
                                intent.putExtra("type", "youtube");
                                startActivity(intent);

                                finish();

                            } else {


                                TastyToast.makeText(getApplicationContext(), "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

                            }



                        }
                    });

                    commentButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                            intent.putExtra("parentId", contentId);
                            intent.putExtra("type", "youtube");
                            startActivity(intent);

                            finish();

                        }
                    });




                } else {

                    e.printStackTrace();

                }

            }

        });




    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, final YouTubePlayer youTubePlayer, boolean b) {

        player = youTubePlayer;
        player.setPlayerStateChangeListener(this);
        player.setOnFullscreenListener(this);

        playYoutubeVideo(player, currentContentOb);






    }


    public void playYoutubeVideo(YouTubePlayer player, ParseObject content){

        player.cueVideo(content.getString("youtubeId"));




    }



    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    @Override
    public void onFullscreen(boolean b) {

        if (!player.isPlaying()){

            player.play();

        }

    }


    @Override
    public void onLoading() {

    }

    @Override
    public void onLoaded(String s) {


    }

    @Override
    public void onAdStarted() {



    }

    @Override
    public void onVideoStarted() {


    }

    @Override
    public void onVideoEnded() {



    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

    }






    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            player.setFullscreen(true);

            tap_navigation.setVisibility(View.INVISIBLE);


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

            player.setFullscreen(false);

            tap_navigation.setVisibility(View.VISIBLE);

        }

    }


    @Override
    protected void onPause() {
        super.onPause();
        if(player != null){

            player.pause();

        }



    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d("check", String.valueOf(1));



    }
}
