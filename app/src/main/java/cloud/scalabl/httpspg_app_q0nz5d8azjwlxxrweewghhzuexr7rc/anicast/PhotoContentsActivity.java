package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CardContentsEndFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CardContentsFragment;


public class PhotoContentsActivity extends AppCompatActivity implements View.OnClickListener {

    private CustomViewPager cardViewPager;


    private static String contentId;
    private static ParseObject currentContentOb;

    private TextView total_page;
    private TextView current_page;
    private int current_page_num;

    private static LinearLayout likeButton;

    private static Boolean likeFlag = false;

    private static TextView like_text;
    private static ImageView like_icon;
    private static TextView like_count;
    private static TextView comment_counter;


    private static ParseUser currentUser;

    private static int likeColor;
    private static int likedColor;

    private static LinearLayout origin_button;
    private static int currentPage = 0;


    private static LinearLayout back_button;
    private static TextView back_button_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_contents);


        Intent intent = getIntent();

        if (intent != null){

            contentId = intent.getExtras().getString("cardId");

        } else {

            Toast.makeText(PhotoContentsActivity.this, "no card id", Toast.LENGTH_SHORT).show();

        }

        //getSupportActionBar().hide();
        currentUser = ParseUser.getCurrentUser();

        likeColor = Color.parseColor("#ffffff");
        likedColor = Color.parseColor("#2db2ff");

        ArrayList<String> cardObKeyArray = new ArrayList<>();
        cardViewPager = (CustomViewPager) findViewById(R.id.container);
        TabLayout tapLayout = (TabLayout) findViewById(R.id.tabs);

        total_page = (TextView) findViewById(R.id.total_page);
        current_page = (TextView) findViewById(R.id.current_page);

        likeButton = (LinearLayout) findViewById(R.id.like_button);
        LinearLayout commentButton = (LinearLayout) findViewById(R.id.comment_button);


        like_text = (TextView) findViewById(R.id.like_text);
        like_icon = (ImageView) findViewById(R.id.like_icon);
        like_count = (TextView) findViewById(R.id.like_count);



        comment_counter = (TextView) findViewById(R.id.comment_counter);
        LinearLayout report_button = (LinearLayout) findViewById(R.id.report_button);

        report_button.setOnClickListener(this);
        likeButton.setOnClickListener(this);
        likeButton.setClickable(false);
        commentButton.setOnClickListener(this);


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


        ParseQuery cardQuery = ParseQuery.getQuery("Content");
        cardQuery.getInBackground(contentId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                currentContentOb = object;

                int likeCount = currentContentOb.getInt("like_count");
                int commentCount = currentContentOb.getInt("comment_count");

                like_count.setText(String.valueOf(likeCount));
                comment_counter.setText(String.valueOf(commentCount));

                final JSONArray photoList = currentContentOb.getJSONArray("images");
                final int dCount = photoList.length();
                Log.d("dCount", String.valueOf(dCount));

                total_page.setText(String.valueOf(dCount+1));
                current_page.setText("1");
                current_page_num = 1;

                for (int i=0;photoList.length() > i ; i++){


                    try {
                        JSONObject objectTarget = photoList.getJSONObject(i);
                        Log.d("photo object", objectTarget.toString());
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }



                }


                origin_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        try {
                            JSONObject targetImage = photoList.getJSONObject(0);
                            String imageUrl = targetImage.getString("image");

                            ParseQuery imageQuery = ParseQuery.getQuery("Image");
                            imageQuery.getInBackground(imageUrl, new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {

                                    if(e==null){

                                        if(object.get("origin") == null){

                                            origin_button.setVisibility(View.GONE);

                                        } else {

                                            origin_button.setVisibility(View.VISIBLE);

                                            Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
                                            webIntent.putExtra("type", "photo");
                                            webIntent.putExtra("origin", object.getString("origin"));
                                            webIntent.putExtra("contentId", contentId);
                                            startActivity(webIntent);

                                            finish();

                                        }



                                    } else {


                                        e.printStackTrace();

                                    }

                                }


                            });


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }



                    }
                });


                final FragmentPagerAdapter cardPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

                    private CardContentsFragment cardFragment(JSONObject content) throws JSONException {

                        CardContentsFragment cardContentsFragment = new CardContentsFragment();

                        Bundle args = new Bundle();
                        args.putString("cardId", contentId);
                        args.putString("contentId", content.getString("image"));
                        cardContentsFragment.setArguments(args);

                        return cardContentsFragment;
                    }

                    private CardContentsEndFragment cardEndFragment(){

                        CardContentsEndFragment cardContentsEndFragment = new CardContentsEndFragment();

                        Bundle args = new Bundle();
                        args.putString("cardId", contentId);
                        cardContentsEndFragment.setArguments(args);

                        return cardContentsEndFragment;
                    }


                    @Override
                    public Fragment getItem(int position) {

                        Log.d("position", String.valueOf(position));

                        if(position > dCount-1){

                            CardContentsEndFragment endFragment = null;
                            endFragment = cardEndFragment();

                            return endFragment;

                        } else {

                            CardContentsFragment fragment = null;
                            try {

                                fragment = cardFragment(photoList.getJSONObject(position));

                            } catch (JSONException e1) {

                                e1.printStackTrace();

                            }

                            return fragment;

                        }



                    }

                    @Override
                    public int getCount() {

                        return dCount+1;
                    }

                    @Override
                    public CharSequence getPageTitle(int position) {
                        return "";
                    }



                };
                // Set up the ViewPager with the sections adapter.

                cardViewPager.setAdapter(cardPagerAdapter);
                //mViewPager.setAdapter(testAdapter);
                TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                tabLayout.setupWithViewPager(cardViewPager);

                cardViewPager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(final int position) {

                        current_page.setText(String.valueOf(position+1));
                        current_page_num = position+1;

                        origin_button.setClickable(false);

                        try {

                            JSONObject targetImage = photoList.getJSONObject(position);
                            String imageUrl = targetImage.getString("image");

                            ParseQuery imageQuery = ParseQuery.getQuery("Image");
                            imageQuery.getInBackground(imageUrl, new GetCallback<ParseObject>() {
                                @Override
                                public void done(final ParseObject object, ParseException e) {

                                    if(e==null){

                                        origin_button.setClickable(true);

                                        if(object.get("origin") == null){

                                            origin_button.setVisibility(View.GONE);

                                        } else {

                                            origin_button.setVisibility(View.VISIBLE);

                                            origin_button.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {

                                                    Intent webIntent = new Intent(getApplicationContext(), WebActivity.class);
                                                    webIntent.putExtra("type", "photo");
                                                    webIntent.putExtra("origin", object.getString("origin"));
                                                    webIntent.putExtra("contentId", contentId);
                                                    startActivity(webIntent);

                                                    finish();

                                                }
                                            });

                                        }




                                    } else {

                                        e.printStackTrace();

                                    }

                                }


                            });


                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {


                    }
                });


            }

        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        current_page.setText(String.valueOf(current_page_num));

    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.like_button){

            if(currentUser == null){

                Toast.makeText(this, "로그인 해야 합니다", Toast.LENGTH_SHORT).show();

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



        }else if(view.getId() == R.id.comment_button){

            Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
            intent.putExtra("parentId", contentId);
            intent.putExtra("type", "photo");
            startActivity(intent);

            finish();


        } else if(view.getId() == R.id.report_button){

            if(currentUser != null){

                Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                intent.putExtra("parentId", contentId);
                intent.putExtra("type", "photo");
                startActivity(intent);

                finish();

            } else {

                TastyToast.makeText(getApplicationContext(), "로그인이 필요합니다", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }





        };

    }

}
