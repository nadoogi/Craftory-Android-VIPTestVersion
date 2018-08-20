package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingIllustFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingNovelFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingWebtoonFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.ArtistPostingYoutubeFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyTimelineFragment;

public class ArtPostingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_posting);

        TabLayout posting_tabs = (TabLayout) findViewById(R.id.posting_tabs);
        ViewPager posting_container = (ViewPager) findViewById(R.id.posting_container);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        ParseUser currentUser = ParseUser.getCurrentUser();

        back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
                startActivity(intent);

                finish();
            }
        });

        back_button_text.setText("작품 올리기");

        if(currentUser != null){

            FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

                ArtistPostingFragment artistPostingFragment = new ArtistPostingFragment();
                ArtistPostingIllustFragment artistPostingIllustFragment = new ArtistPostingIllustFragment();
                ArtistPostingWebtoonFragment artistPostingWebtoonFragment = new ArtistPostingWebtoonFragment();
                ArtistPostingYoutubeFragment artistPostingYoutubeFragment = new ArtistPostingYoutubeFragment();
                ArtistPostingNovelFragment artistPostingNovelFragment = new ArtistPostingNovelFragment();

                MyTimelineFragment myTimelineFragment = new MyTimelineFragment();

                private final String[] menuFragmentNames = new String[]{

                        "포스팅",
                        "사진첩",
                        "웹툰",
                        "유튜브"

                };

                @Override
                public Fragment getItem(int position) {

                    switch (position) {

                        case 0:

                            Bundle postBundle = new Bundle();
                            postBundle.putInt("page", position);
                            artistPostingFragment.setArguments(postBundle);


                            return artistPostingFragment;

                        case 1:

                            Bundle illustBundle = new Bundle();
                            illustBundle.putInt("page", position);
                            artistPostingIllustFragment.setArguments(illustBundle);

                            return artistPostingIllustFragment;

                        case 2:

                            Bundle webtoonBundle = new Bundle();
                            webtoonBundle.putInt("page", position);
                            artistPostingWebtoonFragment.setArguments(webtoonBundle);

                            return artistPostingWebtoonFragment;


                        case 3:

                            Bundle youtubeBundle = new Bundle();
                            youtubeBundle.putInt("page", position);
                            artistPostingYoutubeFragment.setArguments(youtubeBundle);

                            return artistPostingYoutubeFragment;

                            /*
                        case 4:

                            Bundle novelBundle = new Bundle();
                            novelBundle.putInt("page", position);
                            artistPostingNovelFragment.setArguments(novelBundle);

                            return artistPostingNovelFragment;

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


            posting_container.setAdapter(pageAdapter);
            posting_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

            posting_tabs.setupWithViewPager(posting_container);

        } else {

            TastyToast.makeText(getApplicationContext(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);

        }





    }


    @Override
    public void onBackPressed() {

        MaterialDialog.Builder builder = new MaterialDialog.Builder(this);

        builder.title("확인");
        builder.content("이 페이지에서 나가시겠습니까?");
        builder.positiveText("예");
        builder.negativeText("아니요");
        builder.onNegative(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {



            }
        });

        builder.onPositive(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                finish();

            }
        });
        builder.show();

    }
}
