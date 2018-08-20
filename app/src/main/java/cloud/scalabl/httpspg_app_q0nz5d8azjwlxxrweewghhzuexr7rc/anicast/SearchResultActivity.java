package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;


import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SearchPatronFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SearchSerieseFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments.SearchTotalContentFragment;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;



public class SearchResultActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText search_query;
    private TextView no_result;

    private int searchResultCount = 0;

    private String query = "";
    String type;

    ParseUser currentUser;
    FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        Intent intent = getIntent();

        query = intent.getExtras().getString("query");
        type = intent.getExtras().getString("type");

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        search_query = (EditText) findViewById(R.id.search_query);
        search_query.setText(query);
        LinearLayout search_button = (LinearLayout) findViewById(R.id.mymenu_button);
        TabLayout tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager searchPager = (ViewPager) findViewById(R.id.container);


        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        currentUser = ParseUser.getCurrentUser();
        search_button.setOnClickListener(this);

        FragmentManager manager = getSupportFragmentManager();
        functionBase = new FunctionBase(this, getApplicationContext());


        search_query.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    searchStart();
                    functionBase.hideKeyboard(SearchResultActivity.this);

                    return true;

                }

                return false;
            }
        });


        FragmentStatePagerAdapter searchPagerAdapter = new FragmentStatePagerAdapter(manager) {

            @Override
            public Fragment getItem(int position) {

                switch (position) {

                    case 0:

                        SearchTotalContentFragment searchTotalContentFragment = new SearchTotalContentFragment();
                        Bundle totalBundle = new Bundle();
                        totalBundle.putString("query", query);
                        totalBundle.putString("type", type);
                        totalBundle.putString("category", "artwork");
                        totalBundle.putInt("position", position);
                        searchTotalContentFragment.setArguments(totalBundle);

                        return searchTotalContentFragment;

                    case 1:

                        SearchPatronFragment searchPatronFragment = new SearchPatronFragment();
                        Bundle patronBundle = new Bundle();
                        patronBundle.putString("query", query);
                        patronBundle.putString("type", type);
                        patronBundle.putString("category", "patron");
                        patronBundle.putInt("position", position);
                        searchPatronFragment.setArguments(patronBundle);

                        return searchPatronFragment;

                    case 2:

                        SearchSerieseFragment searchSerieseFragment = new SearchSerieseFragment();
                        Bundle serieseBundle = new Bundle();
                        serieseBundle.putString("query", query);
                        serieseBundle.putString("type", type);
                        serieseBundle.putString("cateroty", "seriese");
                        serieseBundle.putInt("position", position);
                        searchSerieseFragment.setArguments(serieseBundle);

                        return searchSerieseFragment;

                    default:

                        return null;

                }


            }


            @Override
            public int getItemPosition(Object object) {

                return POSITION_NONE;

            }

            @Override
            public int getCount() {

                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {

                switch (position) {

                    case 0:

                        return "작품";


                    case 1:

                        return "후원";

                    case 2:

                        return "연재";

                    default:

                        return "";

                }


            }

        };


        searchPager.setAdapter(searchPagerAdapter);
        searchPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                switch (position){

                    case 0:


                        break;

                    case 1:


                        break;

                    case 2:

                        break;


                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tab_layout.setupWithViewPager(searchPager);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.mymenu_button:

                searchStart();

                break;

        }

    }

    private void searchStart(){

        query = search_query.getText().toString();

        if(query.length() == 0){

            TastyToast.makeText(this, "검색어가 입력되지 않았습니다. 다시 확인 해주세요",TastyToast.LENGTH_LONG, TastyToast.INFO);

        } else {

            ParseObject searchOb = new ParseObject("SearchQuery");
            searchOb.put("query", query);
            searchOb.put("type", type);

            if(currentUser != null){

                searchOb.put("user", currentUser);

            }

            searchOb.put("status", true);
            searchOb.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if(e==null){

                        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                        intent.putExtra("query", query);
                        intent.putExtra("type", type);
                        startActivity(intent);

                    } else {

                        e.printStackTrace();
                    }

                }
            });

        }




    }

}
