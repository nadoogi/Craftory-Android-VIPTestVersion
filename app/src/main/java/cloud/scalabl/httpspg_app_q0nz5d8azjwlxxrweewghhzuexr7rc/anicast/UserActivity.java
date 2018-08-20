package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.UserDashBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.UserPostFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.UserSerieseFragment;


public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Intent intent = getIntent();

        final String userId = intent.getExtras().getString("userId");

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        TabLayout user_tab = (TabLayout) findViewById(R.id.user_tabs);
        ViewPager user_container = (ViewPager) findViewById(R.id.user_container);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("공방 보기");

        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{

                    "공방",
                    "작품",
                    "연재"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        UserDashBoardFragment userDashBoardFragment = new UserDashBoardFragment();

                        Bundle dashboardBundle = new Bundle();
                        dashboardBundle.putInt("page", position);
                        dashboardBundle.putString("userId", userId);
                        userDashBoardFragment.setArguments(dashboardBundle);


                        return userDashBoardFragment;

                    case 1:

                        UserPostFragment userPostFragment = new UserPostFragment();

                        Bundle postBundle = new Bundle();
                        postBundle.putInt("page", position);
                        postBundle.putString("userId", userId);
                        userPostFragment.setArguments(postBundle);


                        return userPostFragment;

                    case 2:

                        UserSerieseFragment userSerieseFragment = new UserSerieseFragment();

                        Bundle seriesBundle = new Bundle();
                        seriesBundle.putInt("page", position);
                        seriesBundle.putString("userId", userId);
                        userSerieseFragment.setArguments(seriesBundle);

                        return userSerieseFragment;


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


        user_container.setAdapter(pageAdapter);
        user_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        user_tab.setupWithViewPager(user_container);


    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
    }
}
