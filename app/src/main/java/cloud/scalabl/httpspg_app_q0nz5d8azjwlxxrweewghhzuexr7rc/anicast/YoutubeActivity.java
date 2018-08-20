package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.YoutubeDetailAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener, YouTubePlayer.PlayerStateChangeListener{

    private String contentId;
    private String cardId;

    private YouTubePlayerView youTubePlayerView;


    private int currentPosition = 0;
    private Boolean mainActivity = true;

    private Boolean autoPlay = true;
    private YouTubePlayer player;

    private Boolean firstTime = true;

    private ParseObject currentContentOb;


    private Boolean likeFlag = true;



    private RecyclerView youtube_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    private FunctionBase functionBase;

    private TextView back_button_text;

    private YoutubeDetailAdapter youtubeDetailAdapter;


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

        Log.d("contentId", contentId);

        ParseUser currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase( getApplicationContext());

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("");

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        youtube_list = (RecyclerView) findViewById(R.id.youtube_list);

        RequestManager requestManager = Glide.with(this);


        final ParseQuery<ParseObject> contentQuery = ParseQuery.getQuery("ArtistPost");
        contentQuery.getInBackground(contentId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e == null){

                    currentContentOb = object;

                    Log.d("contentId", currentContentOb.getObjectId());

                    back_button_text.setText(currentContentOb.getString("title"));

                    final GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
                    gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){

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

                    youtube_list.setLayoutManager(gridLayoutManager);
                    youtube_list.setHasFixedSize(true);


                    youtubeDetailAdapter = new YoutubeDetailAdapter( YoutubeActivity.this , getApplicationContext(), Glide.with(getApplicationContext()),  currentContentOb);
                    youtube_list.setAdapter(youtubeDetailAdapter);
                    youtube_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                            super.onScrolled(recyclerView, dx, dy);

                            if(dy > 0) {
                                visibleItemCount = gridLayoutManager.getChildCount();
                                totalItemCount = gridLayoutManager.getItemCount();
                                pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                    youtubeDetailAdapter.loadNextPage();
                                }

                            }

                        }
                    });

                    youTubePlayerView.initialize(functionBase.DEVELOPER_KEY, YoutubeActivity.this);

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

        Log.d("youtubeId", content.getString("youtubeId"));

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


        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){

            player.setFullscreen(false);

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
        if(youtubeDetailAdapter != null){

            youtubeDetailAdapter.loadObjects(0);

        }


    }
}
