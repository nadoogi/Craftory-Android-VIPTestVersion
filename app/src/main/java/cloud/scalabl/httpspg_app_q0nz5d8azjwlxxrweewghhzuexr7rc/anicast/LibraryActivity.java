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

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.LibraryLikeFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.LibraryMyFundingFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.LibraryPurchaseFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.LibrarySubscribeFragment;

public class LibraryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        TabLayout library_tab = (TabLayout) findViewById(R.id.library_tabs);
        ViewPager library_container = (ViewPager) findViewById(R.id.library_container);


        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);
        back_button_text.setText("내 서재");

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{

                    "좋아요 작품",
                    "구독 작품",
                    "구매 작품"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:


                        LibraryLikeFragment libraryLikeFragment = new LibraryLikeFragment();

                        Bundle likeBundle = new Bundle();
                        likeBundle.putInt("page", position);
                        libraryLikeFragment.setArguments(likeBundle);

                        return libraryLikeFragment;

                    case 1:

                        LibrarySubscribeFragment librarySubscribeFragment = new LibrarySubscribeFragment();

                        Bundle subscribeBundle = new Bundle();
                        subscribeBundle.putInt("page", position);
                        librarySubscribeFragment.setArguments(subscribeBundle);

                        return librarySubscribeFragment;

                    case 2:

                        LibraryPurchaseFragment libraryPurchaseFragment = new LibraryPurchaseFragment();

                        Bundle purchaseBundle = new Bundle();
                        purchaseBundle.putInt("page", position);
                        libraryPurchaseFragment.setArguments(purchaseBundle);

                        return libraryPurchaseFragment;


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


        library_container.setAdapter(pageAdapter);
        library_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        library_tab.setupWithViewPager(library_container);



    }
}
