package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ContentEndFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ContentSerieseEndFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.WebtoonContentsEndFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.WebtoonContentsFragment;


public class WebtoonContentActivity extends AppCompatActivity {

    private static String contentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_content);

        Intent intent = getIntent();

        if (intent != null){

            contentId = intent.getExtras().getString("cardId");
            Boolean seriese_in = intent.getExtras().getBoolean("seriese_in");

            Log.d("seriese_in", String.valueOf(seriese_in));

        } else {

            Toast.makeText(WebtoonContentActivity.this, "no card id", Toast.LENGTH_SHORT).show();

        }

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        ParseUser currentUser = ParseUser.getCurrentUser();

        ViewPager cardViewPager = (ViewPager) findViewById(R.id.container);

        ArrayList<String> cardObKeyArray = new ArrayList<>();


        FragmentPagerAdapter cardPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private WebtoonContentsFragment webtoonContentsFragment() {

                WebtoonContentsFragment webtoonContentsFragment = new WebtoonContentsFragment();

                Bundle args = new Bundle();
                args.putString("cardId", contentId);
                args.putString("type", "live");
                webtoonContentsFragment.setArguments(args);

                return webtoonContentsFragment;
            }

            //private WebtoonContentsEndFragment webtoonEndFragment(){

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

                if(position == 1){

                    WebtoonContentsEndFragment webtoonContentsEndFragment = null;
                    webtoonContentsEndFragment = webtoonContentsEndFragment();

                    return webtoonContentsEndFragment;

                } else {

                    WebtoonContentsFragment webtoonFragment = null;
                    webtoonFragment = webtoonContentsFragment();

                    return webtoonFragment;

                }



            }

            @Override
            public int getCount() {

                return 2;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "";
            }


        };

        cardViewPager.setAdapter(cardPagerAdapter);
        //mViewPager.setAdapter(testAdapter);

        cardViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


    }






}
