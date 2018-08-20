package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PatronManagerAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.DealerTimelineFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryNewFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryPriceFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.PatronManagerHistoryFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.PatronManagerHomeFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class PatronManagerActivity extends AppCompatActivity {

    private static String type = "post";
    private static String postId;


    private ParseUser currentUser;
    private ParseObject postObject;


    private RequestManager requestManager;
    private FunctionBase functionBase;

    private TabLayout patron_tabs;
    private ViewPager patron_container;

    private FragmentPagerAdapter pageAdapter;

    private ImageView progress_icon1;
    private ImageView progress_icon2;
    private ImageView progress_icon3;
    private ImageView progress_icon4;

    private TextView progress_text1;
    private TextView progress_text2;
    private TextView progress_text3;
    private TextView progress_text4;

    private String progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patron_manager);

        postId = getIntent().getExtras().getString("postId");

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        currentUser = ParseUser.getCurrentUser();

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        patron_tabs = (TabLayout) findViewById(R.id.patron_tabs);
        patron_container = (ViewPager) findViewById(R.id.patron_container);

        progress_icon1 = (ImageView) findViewById(R.id.progress_icon1);
        progress_icon2 = (ImageView) findViewById(R.id.progress_icon2);
        progress_icon3 = (ImageView) findViewById(R.id.progress_icon3);
        progress_icon4 = (ImageView) findViewById(R.id.progress_icon4);

        progress_text1 = (TextView) findViewById(R.id.progress_text1);
        progress_text2 = (TextView) findViewById(R.id.progress_text2);
        progress_text3 = (TextView) findViewById(R.id.progress_text3);
        progress_text4 = (TextView) findViewById(R.id.progress_text4);

        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject postOb, ParseException e) {

                if(e==null){

                    progress = postOb.getString("progress");

                    if(progress.equals("start")){

                        progress_icon1.setColorFilter(functionBase.likedColor);
                        progress_text1.setTextColor(functionBase.likedColor);

                        progress_icon2.setColorFilter(functionBase.likeColor);
                        progress_text2.setTextColor(functionBase.likeColor);

                        progress_icon3.setColorFilter(functionBase.likeColor);
                        progress_text3.setTextColor(functionBase.likeColor);

                        progress_icon4.setColorFilter(functionBase.likeColor);
                        progress_text4.setTextColor(functionBase.likeColor);

                    } else if(progress.equals("production")){

                        progress_icon1.setColorFilter(functionBase.likeColor);
                        progress_text1.setTextColor(functionBase.likeColor);

                        progress_icon2.setColorFilter(functionBase.likedColor);
                        progress_text2.setTextColor(functionBase.likedColor);

                        progress_icon3.setColorFilter(functionBase.likeColor);
                        progress_text3.setTextColor(functionBase.likeColor);

                        progress_icon4.setColorFilter(functionBase.likeColor);
                        progress_text4.setTextColor(functionBase.likeColor);


                    } else if(progress.equals("delivery")){

                        progress_icon1.setColorFilter(functionBase.likeColor);
                        progress_text1.setTextColor(functionBase.likeColor);

                        progress_icon2.setColorFilter(functionBase.likeColor);
                        progress_text2.setTextColor(functionBase.likeColor);

                        progress_icon3.setColorFilter(functionBase.likedColor);
                        progress_text3.setTextColor(functionBase.likedColor);

                        progress_icon4.setColorFilter(functionBase.likeColor);
                        progress_text4.setTextColor(functionBase.likeColor);


                    } else if(progress.equals("finish")){

                        progress_icon1.setColorFilter(functionBase.likeColor);
                        progress_text1.setTextColor(functionBase.likeColor);

                        progress_icon2.setColorFilter(functionBase.likeColor);
                        progress_text2.setTextColor(functionBase.likeColor);

                        progress_icon3.setColorFilter(functionBase.likeColor);
                        progress_text3.setTextColor(functionBase.likeColor);

                        progress_icon4.setColorFilter(functionBase.likedColor);
                        progress_text4.setTextColor(functionBase.likedColor);


                    }

                } else {

                    e.printStackTrace();

                }

            }

        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("굿즈펀딩 관리");

        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(this, getApplicationContext());

        pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private final String[] menuFragmentNames = new String[]{

                    "관리",
                    "참여자",


            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        PatronManagerHomeFragment patronManagerHomeFragment = new PatronManagerHomeFragment();

                        Bundle homeBundle = new Bundle();
                        homeBundle.putString("postId", postId);
                        patronManagerHomeFragment.setArguments(homeBundle);

                        return patronManagerHomeFragment;

                    case 1:

                        PatronManagerHistoryFragment patronManagerHistoryFragment = new PatronManagerHistoryFragment();

                        Bundle historyBundle = new Bundle();
                        historyBundle.putString("postId", postId);
                        patronManagerHistoryFragment.setArguments(historyBundle);

                        return patronManagerHistoryFragment;

                    default:

                        return null;

                }

            }



            @Override
            public int getCount() {
                return menuFragmentNames.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return menuFragmentNames[position];
            }

        };

        patron_container.setAdapter(pageAdapter);
        patron_tabs.setupWithViewPager(patron_container);



    }




    @Override
    protected void onPostResume() {
        super.onPostResume();

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

            PatronManagerActivity.this.finish();

            return true;

        }

        return false;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        PatronManagerActivity.this.finish();

    }

}
