package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.DealerTimelineFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryNewFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.FundingMarketCategoryPriceFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class SellerActivity extends AppCompatActivity {

    private LinearLayout icon_home_button;
    private LinearLayout icon_phone_button;
    private LinearLayout icon_mail_button;

    private ImageView icon_home;
    private ImageView icon_phone;
    private ImageView icon_mail;

    private LinearLayout follow_button;
    private TextView follow_button_text;

    private FunctionBase functionBase;
    private RequestManager requestManager;

    private String dealerId;
    private ParseObject dealerObject;

    private TextView seller_title;

    private TabLayout seller_tabs;
    private ViewPager seller_container;

    private FragmentPagerAdapter pageAdapter;

    private ImageView seller_image;
    private ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller);

        Intent intent = getIntent();

        dealerId = intent.getExtras().getString("dealerId");

        functionBase = new FunctionBase(getApplicationContext());
        requestManager = Glide.with(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        seller_title = (TextView) findViewById(R.id.seller_title);
        seller_tabs = (TabLayout) findViewById(R.id.seller_tabs);
        seller_container = (ViewPager) findViewById(R.id.seller_container);

        icon_home_button = (LinearLayout) findViewById(R.id.icon_home_button);
        icon_phone_button = (LinearLayout) findViewById(R.id.icon_phone_button);
        icon_mail_button = (LinearLayout) findViewById(R.id.icon_mail_button);

        follow_button = (LinearLayout) findViewById(R.id.follow_button);
        follow_button_text = (TextView) findViewById(R.id.follow_button_text);

        follow_button.setVisibility(View.GONE);


        icon_home = (ImageView) findViewById(R.id.icon_home);
        icon_phone = (ImageView) findViewById(R.id.icon_phone);
        icon_mail = (ImageView) findViewById(R.id.icon_mail);

        icon_home.setColorFilter(functionBase.likedColor);
        icon_phone.setColorFilter(functionBase.likedColor);
        icon_mail.setColorFilter(functionBase.likedColor);


        seller_image = (ImageView) findViewById(R.id.seller_image);

        ParseQuery query = ParseQuery.getQuery("FundingMarketDealer");
        query.getInBackground(dealerId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject dealerOb, ParseException e) {

                if(e==null){

                    dealerObject = dealerOb;
                    seller_title.setText(dealerObject.getString("name"));
                    functionBase.generalImageLoading(seller_image, dealerOb, requestManager);

                    if(dealerObject.get("homepage") != null ){

                        icon_home_button.setVisibility(View.VISIBLE);

                        icon_home_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent homepageIntent = new Intent(getApplicationContext(), WebActivity.class);
                                homepageIntent.putExtra("type", "seller");
                                homepageIntent.putExtra("origin", dealerObject.getString("homepage"));
                                startActivity(homepageIntent);

                            }
                        });

                    } else {

                        icon_home_button.setVisibility(View.GONE);

                    }

                    if(dealerObject.get("cs_phone") != null){

                        icon_phone_button.setVisibility(View.VISIBLE);

                        icon_phone_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String phoneString = functionBase.removeSpecialLetters( dealerObject.getString("cs_phone") );

                                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                                phoneIntent.setData(Uri.parse("tel:" + phoneString));
                                startActivity(phoneIntent);


                            }
                        });

                    } else {

                        icon_phone_button.setVisibility(View.GONE);
                    }


                    if(dealerObject.get("email") != null){

                        icon_mail_button.setVisibility(View.VISIBLE);

                        icon_mail_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                String emailString = dealerObject.getString("email");
                                String name = currentUser.getString("name");

                                Intent email = new Intent(Intent.ACTION_SEND);
                                email.setType("plain/text");
                                String[] address = {emailString};
                                email.putExtra(Intent.EXTRA_EMAIL, address);
                                email.putExtra(Intent.EXTRA_SUBJECT,"문의 드립니다." + name);
                                startActivity(email);


                            }
                        });

                    } else {

                        icon_mail_button.setVisibility(View.GONE);

                    }




                    pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

                        private final String[] menuFragmentNames = new String[]{

                                "베스트",
                                "가격순",
                                "최신순",
                                "공지사항",
                                //"이벤트"

                        };

                        @Override
                        public Fragment getItem(int position) {

                            switch (position){

                                case 0:

                                    FundingMarketCategoryFragment fundingCategoryBestFragment = new FundingMarketCategoryFragment();

                                    Bundle bestBundle = new Bundle();
                                    bestBundle.putInt("page", position);
                                    bestBundle.putString("filter", "best");
                                    bestBundle.putString("dealerId", dealerId);
                                    fundingCategoryBestFragment.setArguments(bestBundle);


                                    return fundingCategoryBestFragment;

                                case 1:

                                    FundingMarketCategoryPriceFragment fundingCategoryPriceFragment = new FundingMarketCategoryPriceFragment();

                                    Bundle priceBundle = new Bundle();
                                    priceBundle.putInt("page", position);
                                    priceBundle.putString("filter", "price");
                                    priceBundle.putString("dealerId", dealerId);
                                    fundingCategoryPriceFragment.setArguments(priceBundle);

                                    return fundingCategoryPriceFragment;

                                case 2:

                                    FundingMarketCategoryNewFragment fundingCategoryNewFragment = new FundingMarketCategoryNewFragment();

                                    Bundle newBundle = new Bundle();
                                    newBundle.putInt("page", position);
                                    newBundle.putString("filter", "new");
                                    newBundle.putString("dealerId", dealerId);
                                    fundingCategoryNewFragment.setArguments(newBundle);

                                    return fundingCategoryNewFragment;

                                case 3:

                                    DealerTimelineFragment dealerTimelineFragment = new DealerTimelineFragment();

                                    Bundle noticeBundle = new Bundle();
                                    noticeBundle.putInt("page", position);
                                    noticeBundle.putString("filter", "notice");
                                    noticeBundle.putString("dealerId", dealerId);
                                    dealerTimelineFragment.setArguments(noticeBundle);

                                    return dealerTimelineFragment;



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

                    seller_container.setAdapter(pageAdapter);
                    seller_tabs.setupWithViewPager(seller_container);

                } else {

                    e.printStackTrace();

                }

            }

        });


    }
}
