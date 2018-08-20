package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentPatronAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyFundingFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SocialMSGCheerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SocialMSGPokeFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SocialMSGRequestFragment;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class CreateFundingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_patron);

        TabLayout posting_tabs = (TabLayout) findViewById(R.id.funding_tabs);
        ViewPager posting_container = (ViewPager) findViewById(R.id.funding_container);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        ParseUser currentUser = ParseUser.getCurrentUser();

        back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        back_button_text.setText("굿즈펀딩 관리");

        if(currentUser != null){

            FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

                MyFundingFragment myFundingFragment = new MyFundingFragment();
                FundingMarketFragment fundingMarketFragment = new FundingMarketFragment();

                private final String[] menuFragmentNames = new String[]{

                        "굿즈마켓",
                        "나의펀딩"


                };

                @Override
                public Fragment getItem(int position) {

                    switch (position) {

                        case 0:
                            Bundle illustBundle = new Bundle();
                            illustBundle.putInt("page", position);
                            fundingMarketFragment.setArguments(illustBundle);

                            return fundingMarketFragment;

                        case 1:

                            Bundle postBundle = new Bundle();
                            postBundle.putInt("page", position);
                            myFundingFragment.setArguments(postBundle);

                            return myFundingFragment;

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

    @Override
    protected void onPostResume() {
        super.onPostResume();


    }
}
