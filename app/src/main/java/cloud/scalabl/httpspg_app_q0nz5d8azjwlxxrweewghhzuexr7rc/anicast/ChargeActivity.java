package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ChargeHistoryFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ChargePointFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ChargeWithdrawFragment;


public class ChargeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

        TabLayout point_tab = (TabLayout) findViewById(R.id.point_tabs);
        ViewPager point_container = (ViewPager) findViewById(R.id.point_container);

        //getSupportActionBar().setTitle("관리");

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        back_button_text.setText("BOX 관리");

        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{

                    "충전",
                    "히스토리",
                    //"환급신청"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:


                        ChargePointFragment chargePointFragment = new ChargePointFragment();

                        Bundle pointBundle = new Bundle();
                        pointBundle.putInt("page", position);
                        chargePointFragment.setArguments(pointBundle);

                        return chargePointFragment;

                    case 1:

                        ChargeHistoryFragment chargeHistoryFragment = new ChargeHistoryFragment();

                        Bundle historyBundle = new Bundle();
                        historyBundle.putInt("page", position);
                        chargeHistoryFragment.setArguments(historyBundle);


                        return chargeHistoryFragment;

                    /*
                    case 2:

                        ChargeWithdrawFragment chargeWithdrawFragment = new ChargeWithdrawFragment();

                        Bundle refundBundle = new Bundle();
                        refundBundle.putInt("page", position);
                        chargeWithdrawFragment.setArguments(refundBundle);

                        return chargeWithdrawFragment;

                    */

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


        point_container.setAdapter(pageAdapter);
        point_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        point_tab.setupWithViewPager(point_container);




    }





}
