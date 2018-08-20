package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.NovelContentsFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.WebtoonContentsEndFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.BackKeyOnFragmentListener;

public class NovelActivity extends AppCompatActivity {

    private static String contentId;
    private BackKeyOnFragmentListener backKeyOnFragmentListener;

    private static LinearLayout back_button;
    private static TextView back_button_text;

    public void setBackKeyOnFragmentListener(BackKeyOnFragmentListener backKeyOnFragmentListener){

        this.backKeyOnFragmentListener = backKeyOnFragmentListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novel);

        Intent intent = getIntent();

        if (intent != null){

            contentId = intent.getExtras().getString("postId");

        } else {

            Toast.makeText(getApplicationContext(), "no card id", Toast.LENGTH_SHORT).show();

        }


        ViewPager cardViewPager = (ViewPager) findViewById(R.id.container);

        FragmentPagerAdapter cardPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private NovelContentsFragment novelContentsFragment() {

                NovelContentsFragment novelContentsFragment = new NovelContentsFragment();

                Bundle args = new Bundle();
                args.putString("cardId", contentId);
                novelContentsFragment.setArguments(args);

                return novelContentsFragment;
            }

            private WebtoonContentsEndFragment webtoonEndFragment(){

                WebtoonContentsEndFragment webtoonEndFragment = new WebtoonContentsEndFragment();

                Bundle args = new Bundle();
                args.putString("cardId", contentId);
                webtoonEndFragment.setArguments(args);

                return webtoonEndFragment;
            }


            @Override
            public Fragment getItem(int position) {

                Log.d("position", String.valueOf(position));

                if(position == 1){

                    WebtoonContentsEndFragment endFragment = null;
                    endFragment = webtoonEndFragment();

                    return endFragment;

                } else {

                    NovelContentsFragment novelContentsFragment = null;
                    novelContentsFragment = novelContentsFragment();

                    return novelContentsFragment;

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

    @Override
    public void onBackPressed() {

        if(backKeyOnFragmentListener != null){

            backKeyOnFragmentListener.onBack();

        } else {

            super.onBackPressed();

        }

    }
}
