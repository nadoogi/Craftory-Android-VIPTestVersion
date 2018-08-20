package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CommercialEpisodeFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CommercialInfoFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CommercialRecommendFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;

public class CommercialActivity extends AppCompatActivity {


    private ParseObject serieseOb;

    private TextView back_button_text;

    private TabLayout commercial_tab;
    private ViewPager commercial_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commercial);

        final Intent intent = getIntent();

        final String serieseId = intent.getExtras().getString("serieseId");

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        commercial_tab = (TabLayout) findViewById(R.id.commercial_tabs);
        commercial_container = (ViewPager) findViewById(R.id.commercial_container);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);
        ImageView back_icon = (ImageView) findViewById(R.id.back_icon);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        int whiteColor = Color.parseColor("#ffffff");
        back_icon.setColorFilter(whiteColor);


        RequestManager requestManager = Glide.with(getApplicationContext());

        ParseQuery<ArtistPost> artistQuery = ParseQuery.getQuery(ArtistPost.class);
        artistQuery.include("user");
        artistQuery.getInBackground(serieseId, new GetCallback<ArtistPost>() {
            @Override
            public void done(final ArtistPost object, ParseException e) {

                if(e==null){

                    serieseOb = object;

                    back_button_text.setText(serieseOb.getString("title"));

                    FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


                        private final String[] menuFragmentNames = new String[]{

                                "에피소드",
                                "정보",
                                "추천작품"

                        };

                        @Override
                        public Fragment getItem(int position) {

                            switch (position){

                                case 0:

                                    CommercialEpisodeFragment commercialEpisodeFragment = new CommercialEpisodeFragment();

                                    Bundle episodeBundle = new Bundle();
                                    episodeBundle.putInt("page", position);
                                    episodeBundle.putString("serieseId", serieseId);
                                    commercialEpisodeFragment.setArguments(episodeBundle);


                                    return commercialEpisodeFragment;

                                case 1:

                                    CommercialInfoFragment commercialInfoFragment = new CommercialInfoFragment();

                                    Bundle infoBundle = new Bundle();
                                    infoBundle.putInt("page", position);
                                    infoBundle.putString("serieseId", serieseId);
                                    commercialInfoFragment.setArguments(infoBundle);


                                    return commercialInfoFragment;

                                /*

                                case 2:

                                    CommercialPurchaseFragment commercialPurchaseFragment = new CommercialPurchaseFragment();

                                    Bundle purchaseBundle = new Bundle();
                                    purchaseBundle.putInt("page", position);
                                    purchaseBundle.putString("serieseId", serieseId);
                                    commercialPurchaseFragment.setArguments(purchaseBundle);

                                    return commercialPurchaseFragment;

                                */

                                case 2:

                                    CommercialRecommendFragment commercialRecommendFragment = new CommercialRecommendFragment();

                                    Bundle recommendBundle = new Bundle();
                                    recommendBundle.putInt("page", position);
                                    recommendBundle.putString("serieseId", serieseId);
                                    commercialRecommendFragment.setArguments(recommendBundle);

                                    return commercialRecommendFragment;


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


                    commercial_container.setAdapter(pageAdapter);
                    commercial_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

                    commercial_tab.setupWithViewPager(commercial_container);


                } else {

                    e.printStackTrace();
                }

            }
        });


    }





    @Override
    protected void onPostResume() {



        super.onPostResume();

    }
}
