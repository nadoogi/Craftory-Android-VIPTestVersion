package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyEventFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyRankingFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyRecentActionFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyRecommendCraftFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyTimelineFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.TagTimelineFavorFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.TagTimelineRankingFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.TagTimelineRecentFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class TagHomeActivity extends AppCompatActivity {

    private ViewPager tag_container;
    private TabLayout tag_tab;
    private FragmentPagerAdapter pageAdapter;

    private TextView tag;
    private ImageView image;
    private RequestManager requestManager;
    private FunctionBase functionBase;
    private ArrayList<String> tagArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_home);

        Intent intent = getIntent();
        String tagObId = intent.getExtras().getString("objectId");
        final String tagString = intent.getExtras().getString("tag");
        String imageString = intent.getExtras().getString("image");

        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());
        tagArray = new ArrayList<>();

        tag_container = (ViewPager) findViewById(R.id.tag_container);
        tag_tab = (TabLayout) findViewById(R.id.tag_tabs);

        tag = (TextView) findViewById(R.id.tag);
        image = (ImageView) findViewById(R.id.image);

        tag.setText("#" + tagString);
        functionBase.generalImageLoadingForHeader(image, imageString , requestManager);

        ParseQuery query = ParseQuery.getQuery("TagItem");
        query.getInBackground(tagObId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject tagItemOb, ParseException e) {

                if(e==null){

                    tagArray = functionBase.jsonArrayToArrayList(tagItemOb.getJSONArray("tag_array"));

                    pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


                        private final String[] menuFragmentNames = new String[]{

                                "인기",
                                "최신",
                                "랭킹"



                        };

                        @Override
                        public Fragment getItem(int position) {

                            switch (position){

                                case 0:

                                    TagTimelineFavorFragment tagTimelineFavorFragment = new TagTimelineFavorFragment();

                                    Bundle timelineBundle = new Bundle();
                                    timelineBundle.putInt("page", position);
                                    timelineBundle.putStringArrayList("tag", tagArray);
                                    tagTimelineFavorFragment.setArguments(timelineBundle);


                                    return tagTimelineFavorFragment;

                                case 1:

                                    TagTimelineRecentFragment tagTimelineRecentFragment = new TagTimelineRecentFragment();

                                    Bundle recordBundle = new Bundle();
                                    recordBundle.putInt("page", position);
                                    recordBundle.putStringArrayList("tag", tagArray);
                                    tagTimelineRecentFragment.setArguments(recordBundle);

                                    return tagTimelineRecentFragment;

                                case 2:

                                    TagTimelineRankingFragment tagTimelineRankingFragment = new TagTimelineRankingFragment();

                                    Bundle craftBundle = new Bundle();
                                    craftBundle.putInt("page", position);
                                    craftBundle.putStringArrayList("tag", tagArray);
                                    tagTimelineRankingFragment.setArguments(craftBundle);

                                    return tagTimelineRankingFragment;

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

                    tag_container.setAdapter(pageAdapter);
                    tag_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

                    tag_tab.setupWithViewPager(tag_container);

                } else {

                    e.printStackTrace();
                }

            }

        });




    }
}
