package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseUser;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryNewFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryPriceFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyEventFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyRankingFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyRecentActionFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyRecommendCraftFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyTimelineFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class FundingMarketCategoryActivity extends AppCompatActivity {

    private ViewPager category_container;
    public int currentPosition;

    private ParseUser currentUser;
    private FragmentPagerAdapter pageAdapter;
    private TabLayout category_tabs;

    private String category;
    private TextView category_text;
    private FunctionBase functionBase;

    private ImageView category_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funding_market_category);

        Intent intent = getIntent();

        category = intent.getExtras().getString("filter");

        category_text = (TextView) findViewById(R.id.category_text);
        category_image = (ImageView) findViewById(R.id.category_image);

        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getApplicationContext());

        category_text.setText(functionBase.makeCategory(category));

        functionBase.makeCategoryImageDisplay(category, category_image);

        category_container = (ViewPager) findViewById(R.id.category_container);
        category_tabs = (TabLayout) findViewById(R.id.category_tabs);

        pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            private final String[] menuFragmentNames = new String[]{

                    "베스트",
                    "가격순",
                    "최신순",

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        FundingMarketCategoryFragment fundingCategoryBestFragment = new FundingMarketCategoryFragment();

                        Bundle bestBundle = new Bundle();
                        bestBundle.putInt("page", position);
                        bestBundle.putString("category", category);
                        bestBundle.putString("filter", "best");
                        fundingCategoryBestFragment.setArguments(bestBundle);


                        return fundingCategoryBestFragment;

                    case 1:

                        FundingMarketCategoryPriceFragment fundingCategoryPriceFragment = new FundingMarketCategoryPriceFragment();

                        Bundle priceBundle = new Bundle();
                        priceBundle.putInt("page", position);
                        priceBundle.putString("category", category);
                        priceBundle.putString("filter", "price");
                        fundingCategoryPriceFragment.setArguments(priceBundle);

                        return fundingCategoryPriceFragment;

                    case 2:

                        FundingMarketCategoryNewFragment fundingCategoryNewFragment = new FundingMarketCategoryNewFragment();

                        Bundle newBundle = new Bundle();
                        newBundle.putInt("page", position);
                        newBundle.putString("category", category);
                        newBundle.putString("filter", "new");
                        fundingCategoryNewFragment.setArguments(newBundle);

                        return fundingCategoryNewFragment;


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

        category_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                Log.d("position", String.valueOf(position));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        category_container.setAdapter(pageAdapter);
        category_tabs.setupWithViewPager(category_container);

    }
}
