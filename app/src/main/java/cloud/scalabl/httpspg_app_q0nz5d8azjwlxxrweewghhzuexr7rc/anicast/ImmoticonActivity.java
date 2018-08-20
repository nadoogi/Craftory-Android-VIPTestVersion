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

import com.parse.ParseObject;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ImmoticonMainFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ImmoticonMyFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ImmoticonOpenFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentDashBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentFollowFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentFollowerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ImmoticonSelectListener;

public class ImmoticonActivity extends AppCompatActivity implements ImmoticonSelectListener{


    private TabLayout immoticon_tab;
    private ViewPager immoticon_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immoticon);

        immoticon_tab = (TabLayout) findViewById(R.id.immoticon_tabs);
        immoticon_container = (ViewPager) findViewById(R.id.immoticon_container);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button_text.setText("이모티콘 선택");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{

                    "추천 갤러리",
                    "오픈 갤러리",
                    "내 이모티콘"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        ImmoticonMainFragment immoticonMainFragment = new ImmoticonMainFragment();
                        immoticonMainFragment.setImmoticonMainListner(ImmoticonActivity.this);

                        Bundle mainBundle = new Bundle();
                        mainBundle.putInt("page", position);
                        immoticonMainFragment.setArguments(mainBundle);

                        return immoticonMainFragment;


                    case 1:

                        ImmoticonOpenFragment immoticonOpenFragment = new ImmoticonOpenFragment();
                        immoticonOpenFragment.setImmoticonOpenFragment(ImmoticonActivity.this);

                        Bundle openBundle = new Bundle();
                        openBundle.putInt("page", position);
                        immoticonOpenFragment.setArguments(openBundle);

                        return immoticonOpenFragment;


                    case 2:

                        ImmoticonMyFragment immoticonMyFragment = new ImmoticonMyFragment();
                        immoticonMyFragment.setImmoticonMyFragment(ImmoticonActivity.this);

                        Bundle myBundle = new Bundle();
                        myBundle.putInt("page", position);
                        immoticonMyFragment.setArguments(myBundle);

                        return immoticonMyFragment;

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


        immoticon_container.setAdapter(pageAdapter);
        immoticon_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        immoticon_tab.setupWithViewPager(immoticon_container);


    }

    private void sendIntent(ParseObject itemOb) {
        Intent intent = new Intent();
        intent.putExtra("itemId", itemOb.getObjectId());
        setResult(RESULT_OK, intent);
        finish();
    }


    @Override
    public void onItemSelect(ParseObject itemOb) {

        sendIntent(itemOb);

    }
}
