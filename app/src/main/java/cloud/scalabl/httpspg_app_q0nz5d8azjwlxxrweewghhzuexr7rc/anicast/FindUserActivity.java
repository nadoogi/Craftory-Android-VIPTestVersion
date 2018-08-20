package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.sdsmdg.tastytoast.TastyToast;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FindUserAdapter;

public class FindUserActivity extends AppCompatActivity {

    private EditText query_input;
    private LinearLayout find_button;
    private RecyclerView search_list;
    private LinearLayoutManager layoutManager;
    private FindUserAdapter findUserAdapter;

    private RequestManager requestManager;

    int pastVisibleItems, visibleItemCount, totalItemCount;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);

        Intent intent = getIntent();
        location = intent.getExtras().getString("location");


        query_input = (EditText) findViewById(R.id.query_input);
        find_button = (LinearLayout) findViewById(R.id.find_button);
        search_list = (RecyclerView) findViewById(R.id.search_list);

        requestManager = Glide.with(getApplicationContext());

        query_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String inputString = query_input.getText().toString();

                if(inputString != null){

                    if(count == before){

                        findUserAdapter = new FindUserAdapter(getApplicationContext(),requestManager, String.valueOf(s), location );
                        search_list.setAdapter(findUserAdapter);

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {



            }
        });


        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);
        search_list.setLayoutManager(layoutManager);
        search_list.setHasFixedSize(true);

        findUserAdapter = new FindUserAdapter(getApplicationContext(),requestManager, "follow", location );
        search_list.setAdapter(findUserAdapter);

        search_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        if(findUserAdapter.getItemCount() > 20){

                            findUserAdapter.loadNextPage();

                        }

                    }


                }

            }
        });

        find_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputString = query_input.getText().toString();
                if(inputString.length() == 0){

                    TastyToast.makeText(getApplicationContext(), "닉네임을 입력하세요", TastyToast.LENGTH_LONG, TastyToast.INFO);

                } else {

                    findUserAdapter = new FindUserAdapter(getApplicationContext(),requestManager, inputString, "social" );
                    search_list.setAdapter(findUserAdapter);

                }

            }
        });



    }
}
