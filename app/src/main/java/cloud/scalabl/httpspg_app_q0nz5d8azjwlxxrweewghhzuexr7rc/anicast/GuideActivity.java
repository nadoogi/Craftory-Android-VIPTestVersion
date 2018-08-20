package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.GuidStep1Fragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.GuidStep2Fragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.GuidStep3Fragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.GuidStep4Fragment;

public class GuideActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewPager = (ViewPager) findViewById(R.id.container);

        //viewpager 관련 기능
        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{

                    "",
                    "",
                    "",
                    ""

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        GuidStep1Fragment guidStep1Fragment = new GuidStep1Fragment();

                        Bundle firstBundle = new Bundle();
                        firstBundle.putInt("page", position);
                        guidStep1Fragment.setArguments(firstBundle);

                        return guidStep1Fragment;

                    case 1:

                        GuidStep2Fragment guidStep2Fragment = new GuidStep2Fragment();

                        Bundle secondBundle = new Bundle();
                        secondBundle.putInt("page", position);
                        guidStep2Fragment.setArguments(secondBundle);

                        return guidStep2Fragment;

                    case 2:

                        GuidStep3Fragment guidStep3Fragment = new GuidStep3Fragment();

                        Bundle thirdBundle = new Bundle();
                        thirdBundle.putInt("page", position);
                        guidStep3Fragment.setArguments(thirdBundle);

                        return guidStep3Fragment;

                    case 3:

                        GuidStep4Fragment guidStep4Fragment = new GuidStep4Fragment();

                        Bundle fourthBundle = new Bundle();
                        fourthBundle.putInt("page", position);
                        guidStep4Fragment.setArguments(fourthBundle);

                        return guidStep4Fragment;


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


        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
