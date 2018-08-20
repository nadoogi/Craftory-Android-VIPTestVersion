package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SearchMainAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;



public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText search_query;
    private TextView no_result;

    RecyclerView search_main_list;

    private int searchResultCount = 0;

    private String query = null;
    private String type;

    SearchMainAdapter searchMainAdapter;

    LinearLayout filter_all_button;
    ImageView filter_all_img;
    TextView filter_all_text;

    LinearLayout filter_webtoon_button;
    ImageView filter_webtoon_img;
    TextView filter_webtoon_text;

    /*
    LinearLayout filter_novel_button;
    ImageView filter_novel_img;
    TextView filter_novel_text;

    LinearLayout filter_youtube_button;
    ImageView filter_youtube_img;
    TextView filter_youtube_text;
    */

    LinearLayout filter_post_button;
    ImageView filter_post_img;
    TextView filter_post_text;
    RequestManager requestManager;

    ParseUser currentUser;
    List<ParseObject> mySearchQuery;

    LinearLayout no_result_message;
    FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();

        if(intent.getExtras() == null){

            query = "";

        } else {

            query = intent.getExtras().getString("query");

        }

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        type = "all";
        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(this, getApplicationContext());
        requestManager = Glide.with(getApplicationContext());
        search_query = (EditText) findViewById(R.id.search_query);
        search_query.setText(query);
        LinearLayout search_button = (LinearLayout) findViewById(R.id.mymenu_button);


        search_main_list = (RecyclerView) findViewById(R.id.search_main_list);

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);

        filter_all_button = (LinearLayout) findViewById(R.id.filter_all_button);
        filter_all_img = (ImageView) findViewById(R.id.filter_all_img);
        filter_all_text = (TextView) findViewById(R.id.filter_all_text);

        filter_webtoon_button = (LinearLayout) findViewById(R.id.filter_webtoon_button);
        filter_webtoon_img = (ImageView) findViewById(R.id.filter_webtoon_img);
        filter_webtoon_text = (TextView) findViewById(R.id.filter_webtoon_text);

        filter_post_button = (LinearLayout) findViewById(R.id.filter_post_button);
        filter_post_img = (ImageView) findViewById(R.id.filter_post_img);
        filter_post_text = (TextView) findViewById(R.id.filter_post_text);

        /*
        filter_novel_button = (LinearLayout) findViewById(R.id.filter_novel_button);
        filter_novel_img = (ImageView) findViewById(R.id.filter_novel_img);
        filter_novel_text = (TextView) findViewById(R.id.filter_novel_text);

        filter_youtube_button = (LinearLayout) findViewById(R.id.filter_youtube_button);
        filter_youtube_img = (ImageView) findViewById(R.id.filter_youtube_img);
        filter_youtube_text = (TextView) findViewById(R.id.filter_youtube_text);
        */

        no_result_message = (LinearLayout) findViewById(R.id.no_result_message);
        no_result_message.setVisibility(View.GONE);

        filter_all_button.setOnClickListener(this);
        filter_webtoon_button.setOnClickListener(this);
        filter_post_button.setOnClickListener(this);
        //filter_youtube_button.setOnClickListener(this);
        //filter_novel_button.setOnClickListener(this);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        search_button.setOnClickListener(this);



        search_query.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    searchStart();
                    functionBase.hideKeyboard(SearchActivity.this);

                    return true;

                }

                return false;
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        search_main_list.setLayoutManager(layoutManager);
        search_main_list.setHasFixedSize(true);

        makeQueryList(type);

        if(query != null ){

            if(query.length() != 0){

                searchStart();

            }

        }

        filterUISetting(type);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.mymenu_button:

                searchStart();

                break;

            case R.id.filter_all_button:

                type="all";
                filterUISetting(type);
                makeQueryList(type);

                break;

            case R.id.filter_webtoon_button:

                type = "webtoon";
                filterUISetting(type);
                makeQueryList(type);

                break;

            case R.id.filter_post_button:
                type = "post";

                filterUISetting(type);
                makeQueryList(type);

                break;

            /*
            case R.id.filter_youtube_button:
                type = "youtube";

                filterUISetting(type);
                makeQueryList(type);

                break;

            case R.id.filter_novel_button:
                type = "novel";

                filterUISetting(type);
                makeQueryList(type);

                break;

            */

        }

    }

    private void makeQueryList(final String type){

        final ParseQuery searchQuery = ParseQuery.getQuery("SearchQuery");
        searchQuery.whereEqualTo("status", true);

        if(!type.equals("all")){

            searchQuery.whereEqualTo("type", type);

        }

        searchQuery.setLimit(1000);
        searchQuery.orderByDescending("createdAt");
        searchQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){

                    final List<String> queryList = new ArrayList<>();

                    for(int i=0; objects.size()>i;i++){

                        if(objects.get(i).getString("query") != null || objects.get(i).getString("query").length() != 0){

                            Log.d("string", objects.get(i).getString("query"));

                            if(!queryList.contains(objects.get(i).getString("query"))){

                                queryList.add(objects.get(i).getString("query"));

                            }

                        }
                    }

                    if(currentUser != null){

                        ParseQuery myQury = ParseQuery.getQuery("SearchQuery");
                        myQury.whereEqualTo("status", true);
                        myQury.whereEqualTo("user", currentUser);
                        myQury.setLimit(30);
                        myQury.orderByDescending("createdAt");
                        myQury.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> myListItem, ParseException e) {

                                if(e==null){

                                    final List<ParseObject> myQueryList = new ArrayList<>();
                                    List<String> queryCheck = new ArrayList<>();

                                    for(int i=0; myListItem.size()>i;i++){

                                        if(myListItem.get(i).getString("query") != null || myListItem.get(i).getString("query").length() != 0){


                                            if(!queryCheck.contains(myListItem.get(i).getString("query"))){

                                                queryCheck.add(myListItem.get(i).getString("query"));
                                                myQueryList.add(myListItem.get(i));

                                            }

                                        }
                                    }

                                    searchMainAdapter = new SearchMainAdapter(getApplicationContext(), type, requestManager, myQueryList, queryList);
                                    search_main_list.setAdapter(searchMainAdapter);

                                    no_result_message.setVisibility(View.GONE);


                                } else {

                                    e.printStackTrace();
                                    no_result_message.setVisibility(View.VISIBLE);

                                }
                            }

                        });

                    } else {

                        no_result_message.setVisibility(View.VISIBLE);

                    }



                } else {

                    e.printStackTrace();
                }

            }

        });

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

    private void filterUISetting(String type){

        //FunctionBase.contentTypeFilterColorBuilder(type,filter_all_img, filter_all_text, filter_post_img, filter_post_text, filter_webtoon_img, filter_webtoon_text, filter_novel_img,filter_novel_text,filter_youtube_img,filter_youtube_text );
        functionBase.contentTypeFilterColorBuilder(type,filter_all_img, filter_all_text, filter_post_img, filter_post_text, filter_webtoon_img, filter_webtoon_text, null,null,null,null );
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        makeQueryList(type);

    }
}
