package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CommentSocialBestFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CommentSocialRecentFragment;


public class CommentSocialActivity extends AppCompatActivity {

    private String contentId;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        //getSupportActionBar().setTitle("댓글");

        Intent intent = getIntent();

        if (intent != null){

            contentId = intent.getExtras().getString("postId");
            Log.d("contentId", contentId);
            //type = intent.getExtras().getString("type");

        } else {

            Toast.makeText(this, "컨텐츠 아이디가 입력되지 않았습니다", Toast.LENGTH_SHORT).show();
            finish();

        }

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("댓글");


        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            //CommentRecentFragment commentRecentFragment = new CommentRecentFragment();
            //CommentBestFragment commentBestFragment = new CommentBestFragment();

            CommentSocialRecentFragment commentSocialRecentFragment = new CommentSocialRecentFragment();
            CommentSocialBestFragment commentSocialBestFragment = new CommentSocialBestFragment();


            private final String[] menuFragmentNames = new String[]{

                    "최근",
                    "인기"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position) {

                    case 0:

                        Bundle recentBundle = new Bundle();
                        recentBundle.putString("parentId", contentId);
                        commentSocialRecentFragment.setArguments(recentBundle);


                        return commentSocialRecentFragment;

                    case 1:

                        Bundle bestBundle = new Bundle();
                        bestBundle.putString("parentId", contentId);
                        commentSocialBestFragment.setArguments(bestBundle);

                        return commentSocialBestFragment;


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


        ViewPager mViewPager = (ViewPager) findViewById(R.id.comment_container);
        mViewPager.setAdapter(pageAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                /*
                if(position == 0){

                    commentSocialRecentAdapter.notifyDataSetChanged();

                } else {

                    commentSocialFavorAdapter.notifyDataSetChanged();
                }
                */


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.artwork_tabs);
        tabLayout.setupWithViewPager(mViewPager);





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {


            CommentSocialActivity.this.finish();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        CommentSocialActivity.this.finish();

    }

}
