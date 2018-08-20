package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.TagSearchResultAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.TagItemSelectListener;
import me.gujun.android.taggroup.TagGroup;

public class TagInputForSerieseActivity extends AppCompatActivity implements TagItemSelectListener {

    private LinearLayout save_button;
    private EditText tag_input;


    private RecyclerView search_tag_list;
    private TagSearchResultAdapter tagSearchResultAdapter;
    private LinearLayoutManager layoutManager;

    private RequestManager requestManager;
    private FunctionBase functionBase;

    private TagGroup category_tag_group;
    private TagGroup my_tag_group;
    private ParseUser currentUser;
    private ArrayList<String> finalResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_input_for_seriese);

        Intent intent = getIntent();
        finalResult = intent.getStringArrayListExtra("tags");

        search_tag_list = (RecyclerView) findViewById(R.id.search_tag_list);
        requestManager = Glide.with(getApplicationContext());
        functionBase = new FunctionBase(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        tag_input = (EditText) findViewById(R.id.tag_input);
        tag_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String inputString = String.valueOf(s);

                if(inputString.contains("#")){

                    if(before == count){

                        String[] splitText = inputString.split("#");
                        String targetString = splitText[splitText.length -1];

                        tagSearchResultAdapter = new TagSearchResultAdapter(getApplicationContext(), requestManager, targetString);
                        tagSearchResultAdapter.setTagItemSelectListener(TagInputForSerieseActivity.this);
                        search_tag_list.setAdapter(tagSearchResultAdapter);

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        if(finalResult.size() > 0){

            String existTag = functionBase.arrayListToTagString(finalResult);
            tag_input.setText(existTag);
            tag_input.setSelection(existTag.length()-1);

        }

        save_button = (LinearLayout) findViewById(R.id.save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendIntent();
            }
        });


        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        search_tag_list.setLayoutManager(layoutManager);
        search_tag_list.setHasFixedSize(true);

        tagSearchResultAdapter = new TagSearchResultAdapter(getApplicationContext(), requestManager, "");
        tagSearchResultAdapter.setTagItemSelectListener(this);

        search_tag_list.setAdapter(tagSearchResultAdapter);


        //

        category_tag_group = (TagGroup) findViewById(R.id.category_tag_group);
        my_tag_group = (TagGroup) findViewById(R.id.my_tag_group);

        ArrayList<String> tags = new ArrayList<>();
        tags.add("일상");
        tags.add("순정");
        tags.add("개그");
        tags.add("액션");
        tags.add("로맨스");
        tags.add("스릴러");
        tags.add("판타지");
        tags.add("스포츠");
        tags.add("학원물");


        category_tag_group.setTags(tags);
        category_tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {

                functionBase.tagReload(tag_input, tag);

            }
        });


        ParseQuery tagQuery = ParseQuery.getQuery("TagLog");
        tagQuery.whereEqualTo("user", currentUser);
        tagQuery.setLimit(20);
        tagQuery.orderByDescending("createdAt");
        tagQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> tagObs, ParseException e) {

                String[] recommendTag = functionBase.tagMaker(tagObs);

                my_tag_group.setTags(recommendTag);

                my_tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                    @Override
                    public void onTagClick(String tag) {

                        functionBase.tagReload(tag_input, tag);

                    }
                });

            }

        });

    }

    private ArrayList<String> getSelected() {

        String tagInputString = tag_input.getText().toString();

        ArrayList<String> selectedTag = new ArrayList<>();

        selectedTag = functionBase.stringToArrayList(tagInputString, "#");

        return selectedTag;

    }


    private void sendIntent() {

        Intent intent = new Intent();
        intent.putStringArrayListExtra("tags", getSelected());
        setResult(RESULT_OK, intent);
        finish();

    }

    @Override
    public void onSelectedItem(String tag, String queryString) {

        if(queryString.length() == 0){

            functionBase.tagReload(tag_input, tag);

        } else {

            if(tag.contains(queryString)){

                functionBase.tagReplace(tag_input,tag,queryString);


            } else {

                functionBase.tagReload(tag_input, tag);

            }

        }

    }



}
