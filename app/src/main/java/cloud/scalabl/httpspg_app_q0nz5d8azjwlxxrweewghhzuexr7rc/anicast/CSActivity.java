package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PostCSParseQueryAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CSRefundFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.CSUserVoiceFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentAdFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentDashBoardFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentFollowFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.MyContentFollowerFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;

public class CSActivity extends AppCompatActivity {

    private ParseUser currentUser;

    private FunctionBase functionBase;

    private TabLayout cs_tab;
    private ViewPager cs_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs);

        cs_tab = (TabLayout) findViewById(R.id.cs_tabs);
        cs_container = (ViewPager) findViewById(R.id.cs_container);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button_text.setText("고객센터");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(this, getApplicationContext());

        FragmentPagerAdapter pageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {


            private final String[] menuFragmentNames = new String[]{

                    "결제/환불",
                    "문의/제안"

            };

            @Override
            public Fragment getItem(int position) {

                switch (position){

                    case 0:

                        CSRefundFragment csRefundFragment = new CSRefundFragment();

                        Bundle csRefundBundle = new Bundle();
                        csRefundBundle.putInt("page", position);
                        csRefundFragment.setArguments(csRefundBundle);


                        return csRefundFragment;


                    case 1:

                        CSUserVoiceFragment csUserVoiceFragment = new CSUserVoiceFragment();

                        Bundle csUserVoiceBundle = new Bundle();
                        csUserVoiceBundle.putInt("page", position);
                        csUserVoiceFragment.setArguments(csUserVoiceBundle);

                        return csUserVoiceFragment;

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


        cs_container.setAdapter(pageAdapter);
        cs_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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

        cs_tab.setupWithViewPager(cs_container);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();



    }
}
