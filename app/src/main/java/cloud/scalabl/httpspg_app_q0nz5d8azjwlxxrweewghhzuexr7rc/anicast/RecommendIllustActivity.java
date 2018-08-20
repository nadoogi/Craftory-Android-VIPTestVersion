package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.RecommendIllustAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.RecommendItemSelectListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.TagSelectListener;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class RecommendIllustActivity extends AppCompatActivity implements TagSelectListener {

    TextView tag_selected_1;
    TextView tag_selected_2;
    TextView tag_selected_3;
    TextView tag_selected_4;
    TextView tag_selected_5;
    TextView tag_count;
    TextView tag_message;

    LinearLayout tag1_button;
    LinearLayout tag2_button;
    LinearLayout tag3_button;
    LinearLayout tag4_button;
    LinearLayout tag5_button;

    ParseUser currentUser;

    int inputTagCount = 5;

    Boolean tagSelected1 = false;
    Boolean tagSelected2 = false;
    Boolean tagSelected3 = false;
    Boolean tagSelected4 = false;
    Boolean tagSelected5 = false;

    RelativeLayout next_button;

    String tag1;
    String tag2;
    String tag3;
    String tag4;
    String tag5;

    RecyclerView artwork_list;
    RequestManager requestManager;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    RecommendIllustAdapter recommendIllustAdapter;

    private int selectedCount;
    private List<ParseObject> selectedTagItemList;

    private Boolean inputStatus;
    private FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_illust);

        tag_selected_1 = (TextView) findViewById(R.id.tag_selected_1);
        tag_selected_2 = (TextView) findViewById(R.id.tag_selected_2);
        tag_selected_3 = (TextView) findViewById(R.id.tag_selected_3);
        tag_selected_4 = (TextView) findViewById(R.id.tag_selected_4);
        tag_selected_5 = (TextView) findViewById(R.id.tag_selected_5);

        tag1_button = (LinearLayout) findViewById(R.id.tag1_button);
        tag2_button = (LinearLayout) findViewById(R.id.tag2_button);
        tag3_button = (LinearLayout) findViewById(R.id.tag3_button);
        tag4_button = (LinearLayout) findViewById(R.id.tag4_button);
        tag5_button = (LinearLayout) findViewById(R.id.tag5_button);

        next_button = (RelativeLayout) findViewById(R.id.next_button);
        next_button.setVisibility(View.GONE);


        artwork_list = (RecyclerView) findViewById(R.id.artwork_list);

        tag_count = (TextView) findViewById(R.id.tag_count);
        tag_message = (TextView) findViewById(R.id.tag_message);

        functionBase = new FunctionBase(this, getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        ArrayList<String> blankArray = new ArrayList<>();
        currentUser.put("tag_selected", blankArray);
        currentUser.saveInBackground();

        Log.d("currentUser tag status", String.valueOf(currentUser.getBoolean("tag_check")) );


        selectedTagItemList = new ArrayList<>();
        inputStatus = true;

        selectedCount = 0;

        requestManager = Glide.with(getApplicationContext());

        final LinearLayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);

        artwork_list.setLayoutManager(layoutManager);
        artwork_list.setHasFixedSize(true);
        artwork_list.setNestedScrollingEnabled(false);

        recommendIllustAdapter = new RecommendIllustAdapter(getApplicationContext(), requestManager );
        recommendIllustAdapter.setTagSelectListener(this);

        artwork_list.setItemAnimator(new SlideInUpAnimator());
        artwork_list.setAdapter(recommendIllustAdapter);

        artwork_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy > 0){
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();


                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                        recommendIllustAdapter.loadNextPage();

                    }

                }

            }



        });



        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_button.setClickable(false);

                if(selectedCount == 5){

                    ArrayList<String> tagList = new ArrayList<>();

                    for(int i=0;selectedTagItemList.size()>i;i++){

                        ArrayList<String> tag_list = functionBase.jsonArrayToArrayList(selectedTagItemList.get(i).getJSONArray("tag_array"));

                        for(int j=0;tag_list.size()>j;j++){

                            tagList.add(tag_list.get(j));

                            ParseObject tagLogOb = new ParseObject("TagLog");
                            tagLogOb.put("add", true);
                            tagLogOb.put("place", "recommend_tag");
                            tagLogOb.put("user", currentUser);
                            tagLogOb.put("status", true);
                            tagLogOb.put("tag", tag_list.get(j));
                            tagLogOb.put("type", "recommend");
                            tagLogOb.saveInBackground();

                        }
                    }


                    currentUser.put("tags", tagList);
                    currentUser.put("tag_check", true);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                Intent intent = new Intent(getApplicationContext(), RecommendCreatorActivity.class);
                                startActivity(intent);

                                finish();

                            } else {

                                e.printStackTrace();
                                next_button.setClickable(true);
                            }

                        }
                    });



                } else {

                    next_button.setClickable(true);

                    TastyToast.makeText(getApplicationContext(), "취향 선택을 완료해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

            }
        });

        progressCheck(selectedCount);


    }


    private void progressCheck(int counter){

        if(counter == 5 || counter > 5){

            inputStatus = false;

        } else {

            inputStatus = true;

        }

        switch (counter){

            case 0:

                tag1_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag2_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag3_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag4_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag5_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);

                next_button.setVisibility(View.GONE);

                break;

            case 1:

                tag1_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag2_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag3_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag4_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag5_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);

                next_button.setVisibility(View.GONE);

                break;

            case 2:

                tag1_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag2_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag3_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag4_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag5_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);

                next_button.setVisibility(View.GONE);

                break;

            case 3:

                tag1_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag2_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag3_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag4_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);
                tag5_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);

                next_button.setVisibility(View.GONE);

                break;

            case 4:

                tag1_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag2_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag3_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag4_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag5_button.setBackgroundResource(R.drawable.button_radius_5dp_point_color);

                next_button.setVisibility(View.GONE);

                break;

            case 5:

                tag1_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag2_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag3_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag4_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);
                tag5_button.setBackgroundResource(R.drawable.button_radius_5dp_point_fullcolor);

                next_button.setVisibility(View.VISIBLE);

                break;

        }

    }




    @Override
    public void onSelectedItem(ParseObject itemOb, Boolean selected) {

        Log.d("selected", itemOb.getObjectId());
        Log.d("selected", selected.toString());
        Log.d("selected", inputStatus.toString());

        Boolean itemSelected;

        if(selectedTagItemList.contains(itemOb)){

            itemSelected = true;

        } else {

            itemSelected = false;

        }

        if(inputStatus){

            if(selected && itemSelected){

                int indexNum = 0;

                for(int i=0; selectedTagItemList.size()>i;i++ ){

                    if(itemOb.getObjectId().equals( selectedTagItemList.get(i).getObjectId() )){

                        indexNum = i;

                    }

                }

                Log.d("index", String.valueOf(indexNum) );
                Log.d("before", selectedTagItemList.toString());

                selectedTagItemList.remove(indexNum);

                Log.d("after", selectedTagItemList.toString());
                selectedCount = selectedTagItemList.size();

                progressCheck(selectedCount);

                Log.d("seletedTagItemList1", selectedTagItemList.toString());

                recommendIllustAdapter.loadObjects(0);



            } else if(!selected && !itemSelected) {

                selectedTagItemList.add(itemOb);

                selectedCount = selectedTagItemList.size();

                progressCheck(selectedCount);

                Log.d("seletedTagItemList2", selectedTagItemList.toString());

                recommendIllustAdapter.loadObjects(0);

            } else if(selected && !itemSelected){

                int indexNum = 0;

                for(int i=0; selectedTagItemList.size()>i;i++ ){

                    if(itemOb.getObjectId().equals( selectedTagItemList.get(i).getObjectId() )){

                        indexNum = i;

                    }

                }

                Log.d("index", String.valueOf(indexNum) );
                Log.d("before", selectedTagItemList.toString());

                selectedTagItemList.remove(indexNum);

                Log.d("after", selectedTagItemList.toString());
                selectedCount = selectedTagItemList.size();

                progressCheck(selectedCount);



                Log.d("seletedTagItemList3", selectedTagItemList.toString());

                recommendIllustAdapter.loadObjects(0);

            } else if(!selected && itemSelected){

                Log.d("seletedTagItemList4", selectedTagItemList.toString());

                recommendIllustAdapter.loadObjects(0);

            }

        } else {

            TastyToast.makeText(getApplicationContext(), "선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

        }


    }
}
