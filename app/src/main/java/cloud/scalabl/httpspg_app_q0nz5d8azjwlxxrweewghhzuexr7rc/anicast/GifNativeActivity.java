package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CardContentsEndFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.GifNativeFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.GifNativeInitFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.WebtoonContentsEndFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionLikeDislike;


public class GifNativeActivity extends AppCompatActivity  {

    private LinearLayout likeButton;
    private LinearLayout commentButton;

    private Boolean likeFlag = false;

    private TextView like_text;
    private ImageView like_icon;
    private TextView comment_count;
    private TextView like_count;

    private int likeColor;
    private int likedColor;

    private LinearLayout navigation_button;
    private LinearLayout infomation_layout;


    private LinearLayout option_button;


    private TextView back_button_text;

    private LinearLayout top_navigation;
    private LinearLayout tap_navigation;

    private CustomViewPager cardViewPager;


    private String contentId;
    private ParseObject currentContentOb;

    private ParseUser currentUser;

    private String writterImage = "";
    private FunctionBase functionBase;

    private TextView current_page;
    private TextView total_page;

    private LinearLayout share_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_contents);


        final Intent intent = getIntent();

        if (intent != null){

            contentId = intent.getExtras().getString("postId");

        } else {

            Toast.makeText(GifNativeActivity.this, "no card id", Toast.LENGTH_SHORT).show();

        }

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(this, getApplicationContext());

        ArrayList<String> cardObKeyArray = new ArrayList<>();
        cardViewPager = (CustomViewPager) findViewById(R.id.container);
        cardViewPager.setOffscreenPageLimit(5);

        TabLayout tapLayout = (TabLayout) findViewById(R.id.tabs);

        likeButton = (LinearLayout) findViewById(R.id.like_button);
        commentButton = (LinearLayout) findViewById(R.id.comment_button);

        like_icon = (ImageView) findViewById(R.id.like_icon);
        ImageView comment_icon = (ImageView) findViewById(R.id.comment_icon);

        comment_count = (TextView) findViewById(R.id.comment_count);
        like_count = (TextView) findViewById(R.id.like_count);

        LinearLayout patron_button = (LinearLayout) findViewById(R.id.patron_button);
        share_button = (LinearLayout) findViewById(R.id.share_button);
        option_button = (LinearLayout) findViewById(R.id.option_button);

        ImageView patron_icon = (ImageView) findViewById(R.id.patron_icon);
        ImageView share_icon = (ImageView) findViewById(R.id.share_icon);
        ImageView option_icon = (ImageView) findViewById(R.id.option_icon);


        LinearLayout pre_layout = (LinearLayout) findViewById(R.id.pre_layout);
        ImageView pre = (ImageView) findViewById(R.id.pre);

        LinearLayout next_layout = (LinearLayout) findViewById(R.id.next_layout);
        ImageView next = (ImageView) findViewById(R.id.next);
        TextView current_order = (TextView) findViewById(R.id.current_order);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);
        ImageView back_icon = (ImageView) findViewById(R.id.back_icon);

        top_navigation = (LinearLayout) findViewById(R.id.top_navigation);
        tap_navigation = (LinearLayout) findViewById(R.id.tap_navigation);

        current_page = (TextView) findViewById(R.id.current_page);
        total_page = (TextView) findViewById(R.id.total_page);

        top_navigation.setVisibility(View.GONE);
        tap_navigation.setVisibility(View.GONE);

        patron_button.setVisibility(View.GONE);



        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        int defaultColor = Color.parseColor("#ffffff");

        like_icon.setColorFilter(defaultColor);
        comment_icon.setColorFilter(defaultColor);
        patron_icon.setColorFilter(defaultColor);
        share_icon.setColorFilter(defaultColor);
        option_icon.setColorFilter(defaultColor);
        pre.setColorFilter(defaultColor);
        next.setColorFilter(defaultColor);
        back_icon.setColorFilter(defaultColor);


        navigation_button = (LinearLayout) findViewById(R.id.navigation_button);
        navigation_button.setVisibility(View.GONE);








        ParseQuery cardQuery = ParseQuery.getQuery("ArtistPost");
        cardQuery.include("user");
        cardQuery.getInBackground(contentId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                currentContentOb = object;

                share_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        MaterialDialog.Builder builder = new MaterialDialog.Builder(GifNativeActivity.this);

                        builder.title("확인");
                        builder.content("내 타임라인에 공유하시겠습니까?");
                        builder.positiveText("예");
                        builder.negativeText("아니요");
                        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            }
                        });

                        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                HashMap<String, Object> params = new HashMap<>();

                                params.put("key", currentUser.getSessionToken());
                                params.put("shareObId", currentContentOb.getObjectId() );

                                Date uniqueIdDate = new Date();
                                String uniqueId = uniqueIdDate.toString();

                                params.put("uid", uniqueId);

                                ParseCloud.callFunctionInBackground("share_item", params, new FunctionCallback<Map<String, Object>>() {

                                    public void done(Map<String, Object> mapObject, ParseException e) {

                                        if (e == null) {

                                            if(mapObject.get("status").toString().equals("true")){

                                                TastyToast.makeText(getApplicationContext(), "공유가 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                            } else {

                                                TastyToast.makeText(getApplicationContext(), "실패 했어요 ㅜㅜ", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                            }

                                        } else {

                                            e.printStackTrace();


                                        }
                                    }
                                });

                            }
                        });
                        builder.show();

                    }
                });

                back_button_text.setText(currentContentOb.getString("title"));

                if(currentContentOb.getBoolean("seriese_in")){

                    navigation_button.setVisibility(View.VISIBLE);

                } else {

                    navigation_button.setVisibility(View.GONE);

                }

                commentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent commentIntent = new Intent(getApplicationContext(), CommentActivity.class);
                        commentIntent.putExtra("postId", currentContentOb.getObjectId());
                        commentIntent.putExtra("type", "illust");
                        startActivity(commentIntent);

                    }
                });

                FunctionLikeDislike likeFunction = new FunctionLikeDislike(getApplicationContext(), currentContentOb, likeButton, like_icon, like_count);
                likeFunction.likeButtonFunctionViewerStatusCheck();

                like_count.setText(String.valueOf(currentContentOb.getInt("like_count")));
                comment_count.setText(String.valueOf(currentContentOb.getInt("comment_count")));

                likeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        FunctionLikeDislike likeClickFunction = new FunctionLikeDislike(getApplicationContext(), currentContentOb, likeButton, like_icon, like_count);
                        likeClickFunction.likeButtonFunctionViewer();

                    }
                });

                if(currentUser != null){

                    option_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            functionBase.OptionButtonFunction(GifNativeActivity.this, option_button, currentUser, currentContentOb );

                        }
                    });

                } else {

                    TastyToast.makeText(getApplicationContext(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();

                }

                Log.d("objectId", String.valueOf(currentContentOb.getObjectId()));


                if(currentContentOb.get("image_array") == null){

                    TastyToast.makeText(getApplicationContext(), "앨범에 이미지가 들어있지 않아 포스트 보기로 이동 합니다.", TastyToast.LENGTH_LONG, TastyToast.CONFUSING);

                    Intent intent1 = new Intent(getApplicationContext(), PostingDetailActivity.class);
                    intent1.putExtra("postId", contentId);
                    startActivity(intent1);

                    finish();

                } else {

                    final JSONArray photoList = currentContentOb.getJSONArray("image_array");
                    final int dCount = photoList.length();
                    Log.d("dCount", String.valueOf(dCount));
                    Log.d("photoList", String.valueOf(photoList));

                    total_page.setText(String.valueOf(dCount));


                    if(currentContentOb.getParseObject("user").get("image_cdn") != null){

                        writterImage = functionBase.imageUrlBase300 + currentContentOb.getParseObject("user").getString("image_cdn");

                    } else if(currentContentOb.getParseObject("user").get("pic_url") != null){

                        writterImage = currentContentOb.getParseObject("user").getString("pic_url");

                    } else if(currentContentOb.getParseObject("user").get("image") != null){

                        writterImage = currentContentOb.getParseObject("user").getParseFile("image").getUrl();

                    }


                    final FragmentPagerAdapter cardPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

                        private GifNativeInitFragment gifNativeInitFragment(){

                            GifNativeInitFragment gifNativeInitFragment = new GifNativeInitFragment();

                            Bundle args = new Bundle();

                            args.putString("image", currentContentOb.getString("image_cdn"));
                            args.putString("title", currentContentOb.getString("title"));
                            args.putString("body", currentContentOb.getString("body"));
                            args.putString("date", functionBase.dateToStringForDisplay( currentContentOb.getCreatedAt()));
                            args.putString("writter", currentContentOb.getParseObject("user").getString("name") );
                            args.putString("writter_image", writterImage );
                            gifNativeInitFragment.setArguments(args);

                            return gifNativeInitFragment;
                        }


                        private GifNativeFragment cardFragment(JSONObject content) throws JSONException {

                            GifNativeFragment gifNativeFragment = new GifNativeFragment();

                            Bundle args = new Bundle();

                            args.putString("image",content.getString("url") );
                            args.putString("main_title", currentContentOb.getString("title"));
                            args.putString("objectId", currentContentOb.getObjectId());



                            gifNativeFragment.setArguments(args);

                            return gifNativeFragment;
                        }

                        private CardContentsEndFragment cardEndFragment(){

                            CardContentsEndFragment cardContentsEndFragment = new CardContentsEndFragment();

                            Bundle args = new Bundle();
                            args.putString("cardId", contentId);
                            cardContentsEndFragment.setArguments(args);

                            return cardContentsEndFragment;
                        }

                        private WebtoonContentsEndFragment webtoonContentsEndFragment(){

                            WebtoonContentsEndFragment webtoonContentsEndFragment = new WebtoonContentsEndFragment();

                            Bundle args = new Bundle();
                            args.putString("cardId", contentId);
                            args.putString("type", "live");
                            webtoonContentsEndFragment.setArguments(args);

                            return webtoonContentsEndFragment;

                        }


                        @Override
                        public Fragment getItem(int position) {

                            Log.d("position", String.valueOf(position));

                            if(position == 0){


                                return gifNativeInitFragment();

                            } else if(position == dCount+1){


                                WebtoonContentsEndFragment endFragment = null;
                                endFragment = webtoonContentsEndFragment();


                                return endFragment;

                            } else {


                                GifNativeFragment fragment = null;
                                try {

                                    fragment = cardFragment(photoList.getJSONObject(position-1));

                                } catch (JSONException e1) {

                                    e1.printStackTrace();

                                }

                                return fragment;

                            }



                        }

                        @Override
                        public int getCount() {



                            return dCount+2;
                        }

                        @Override
                        public CharSequence getPageTitle(int position) {
                            return "";
                        }



                    };
                    // Set up the ViewPager with the sections adapter.

                    Log.d("test1", String.valueOf(cardPagerAdapter));
                    Log.d("test2", String.valueOf(cardViewPager));

                    cardViewPager.setAdapter(cardPagerAdapter);
                    //mViewPager.setAdapter(testAdapter);
                    TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                    tabLayout.setupWithViewPager(cardViewPager);

                    cardViewPager.addOnPageChangeListener(new CustomViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {

                            if(position == 0){



                                top_navigation.setVisibility(View.GONE);
                                tap_navigation.setVisibility(View.GONE);



                            } else if(position == dCount+1){



                                top_navigation.setVisibility(View.GONE);
                                tap_navigation.setVisibility(View.GONE);

                            } else {

                                current_page.setText(String.valueOf(position));

                                top_navigation.setVisibility(View.VISIBLE);
                                tap_navigation.setVisibility(View.VISIBLE);

                            }

                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {


                        }
                    });

                }




            }

        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();



    }





}
