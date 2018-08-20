package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingIllustFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingNovelFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingWebtoonFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingYoutubeFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyTimelineFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SocialMSGCheerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SocialMSGPokeFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SocialMSGRequestFragment;

public class SocialMSGActivity extends AppCompatActivity {

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_msg);

        Intent intent = getIntent();
        userId = intent.getExtras().getString("userId");


        TabLayout posting_tabs = (TabLayout) findViewById(R.id.social_tabs);
        ViewPager posting_container = (ViewPager) findViewById(R.id.social_container);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        ParseUser currentUser = ParseUser.getCurrentUser();

        back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                startActivity(intent);

                finish();
            }
        });

        back_button_text.setText("소통하기");

        if(currentUser != null){

            FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

                SocialMSGPokeFragment socialMSGPokeFragment = new SocialMSGPokeFragment();
                SocialMSGCheerFragment socialMSGCheerFragment = new SocialMSGCheerFragment();
                SocialMSGRequestFragment socialMSGRequestFragment = new SocialMSGRequestFragment();

                private final String[] menuFragmentNames = new String[]{

                        "찌르기",
                        "후원하기",
                        "제작요청",

                };

                @Override
                public Fragment getItem(int position) {

                    switch (position) {

                        case 0:

                            Bundle postBundle = new Bundle();
                            postBundle.putInt("page", position);
                            postBundle.putString("userId", userId);
                            socialMSGPokeFragment.setArguments(postBundle);


                            return socialMSGPokeFragment;

                        case 1:

                            Bundle illustBundle = new Bundle();
                            illustBundle.putInt("page", position);
                            illustBundle.putString("userId", userId);
                            socialMSGCheerFragment.setArguments(illustBundle);

                            return socialMSGCheerFragment;

                        case 2:

                            Bundle webtoonBundle = new Bundle();
                            webtoonBundle.putInt("page", position);
                            webtoonBundle.putString("userId", userId);
                            socialMSGRequestFragment.setArguments(webtoonBundle);

                            return socialMSGRequestFragment;

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


            posting_container.setAdapter(pageAdapter);
            posting_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

            posting_tabs.setupWithViewPager(posting_container);

        } else {

            TastyToast.makeText(getApplicationContext(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

            Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent1);

        }

    }
}
