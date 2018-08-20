package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseUser;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentAdFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentDashBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentFollowFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentFollowerFragment;

public class ContentManagerActivity extends AppCompatActivity {

    private ParseUser currentUser;
    private LinearLayout setting_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_manager);

        TabLayout cm_tab = (TabLayout) findViewById(R.id.cm_tabs);
        ViewPager cm_container = (ViewPager) findViewById(R.id.cm_container);
        setting_button = (LinearLayout) findViewById(R.id.setting_button);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        currentUser = ParseUser.getCurrentUser();

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button_text.setText("내 공방 관리");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);

            }
        });

        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{

                    "프로필 관리",
                    "팔로윙 관리",
                    "팔로워 관리"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        MyContentDashBoardFragment myContentDashBoardFragment = new MyContentDashBoardFragment();

                        Bundle dashboardBundle = new Bundle();
                        dashboardBundle.putInt("page", position);
                        myContentDashBoardFragment.setArguments(dashboardBundle);


                        return myContentDashBoardFragment;


                    case 1:

                        MyContentFollowFragment myContentFollowFragment = new MyContentFollowFragment();

                        Bundle fanboardBundle = new Bundle();
                        fanboardBundle.putInt("page", position);
                        myContentFollowFragment.setArguments(fanboardBundle);

                        return myContentFollowFragment;


                    case 2:

                        MyContentFollowerFragment myContentFollowerFragment = new MyContentFollowerFragment();

                        Bundle followerBundle = new Bundle();
                        followerBundle.putInt("page", position);
                        myContentFollowerFragment.setArguments(followerBundle);

                        return myContentFollowerFragment;



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


        cm_container.setAdapter(pageAdapter);
        cm_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        cm_tab.setupWithViewPager(cm_container);


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(currentUser == null){

            finish();

        }

    }
}
