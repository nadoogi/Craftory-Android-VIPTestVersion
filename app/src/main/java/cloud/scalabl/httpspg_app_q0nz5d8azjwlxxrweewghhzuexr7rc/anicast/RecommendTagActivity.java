package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import me.gujun.android.taggroup.TagGroup;

public class RecommendTagActivity extends AppCompatActivity {

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

    TagGroup tag_group;

    EditText tag_input;

    RelativeLayout tag_save;

    ParseUser currentUser;

    int inputTagCount = 5;

    Boolean tagSelected1 = false;
    Boolean tagSelected2 = false;
    Boolean tagSelected3 = false;
    Boolean tagSelected4 = false;
    Boolean tagSelected5 = false;

    LinearLayout next_button;

    String tag1;
    String tag2;
    String tag3;
    String tag4;
    String tag5;

    private  FunctionBase functionBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_tag);

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

        tag_group = (TagGroup) findViewById(R.id.tag_group);

        tag_input = (EditText) findViewById(R.id.tag_input);

        tag_save = (RelativeLayout) findViewById(R.id.tag_save);
        next_button = (LinearLayout) findViewById(R.id.next_button);
        next_button.setClickable(false);
        next_button.setVisibility(View.GONE);

        tag_count = (TextView) findViewById(R.id.tag_count);
        tag_message = (TextView) findViewById(R.id.tag_message);

        functionBase = new FunctionBase(this, getApplicationContext());
        currentUser = ParseUser.getCurrentUser();

        reloadInputTag();

        HashMap<String, Object> params = new HashMap<>();

        params.put("key", currentUser.getSessionToken());

        ParseCloud.callFunctionInBackground("tag_recommend", params, new FunctionCallback<Map<String, Object>>() {

            public void done(Map<String, Object> mapObject, ParseException e) {

                if (e == null) {

                    Log.d("parse result", mapObject.toString());
                    if(mapObject.get("status").toString().equals("true")){

                        ArrayList resultArray = (ArrayList) mapObject.get("result");
                        Log.d("resultArray", resultArray.toString());
                        Log.d("resultObject", resultArray.get(0).toString());
                        Log.d("resultString", String.valueOf( ((Map<String, String>) resultArray.get(0)).get("tag") ));

                        Collections.sort(resultArray, mapComparator);

                        Log.d("resultArraysorted", resultArray.toString());

                        ArrayList<String> tagList = new ArrayList<>();

                        for(int i=0;resultArray.size()>i;i++){

                            tagList.add( ((Map<String, String>) resultArray.get(i)).get("tag") );

                        }

                        tag_group.setTags( tagList );
                        tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                            @Override
                            public void onTagClick(final String tagString) {

                                if(inputTagCount == 0){

                                    TastyToast.makeText(getApplicationContext(), "태그 선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                } else {

                                    if(functionBase.parseArrayCheck( currentUser, "tags", tagString )){

                                        tag_save.setClickable(true);
                                        TastyToast.makeText(getApplicationContext(), "이미 태그가 존재 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    } else {

                                        if(tagString.length() == 0){

                                            tag_save.setClickable(true);
                                            TastyToast.makeText(getApplicationContext(), "태그를 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                        } else {

                                            functionBase.tagAddActionFromObject(currentUser, tagString, new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        if(inputTagCount > 0){

                                                            reloadInputTag();
                                                            refreshTagInputEditor();

                                                            tag_save.setClickable(true);

                                                        } else {

                                                            TastyToast.makeText(getApplicationContext(), "태그 선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                                            next_button.setClickable(true);
                                                            tag_save.setClickable(true);

                                                        }

                                                    } else {

                                                        e.printStackTrace();
                                                        tag_save.setClickable(true);
                                                    }

                                                }
                                            });



                                        }

                                    }

                                }

                            }
                        });

                    } else {

                        TastyToast.makeText(getApplicationContext(), "후원에 실패 했습니다. 다시 시도해주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    }

                } else {

                    Log.d("error", "error");
                    e.printStackTrace();

                }
            }
        });


        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputTagCount == 0){

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
                            }

                        }
                    });



                } else {

                    TastyToast.makeText(getApplicationContext(), "취향 선택을 완료해주세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                }

            }
        });

        tag_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tag_save.setClickable(false);

                if(inputTagCount ==0 ){

                    TastyToast.makeText(getApplicationContext(), "태그 선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                } else {

                    final String tagString = tag_input.getText().toString();

                    if(functionBase.parseArrayCheck( currentUser, "tags", tagString )){

                        tag_save.setClickable(true);
                        TastyToast.makeText(getApplicationContext(), "이미 태그가 존재 합니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                    } else {

                        if(tagString.length() == 0){

                            tag_save.setClickable(true);
                            TastyToast.makeText(getApplicationContext(), "태그를 입력하세요.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                        } else {

                            functionBase.tagAddActionFromObject(currentUser, tagString, new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        if(inputTagCount < 6){

                                            inputTagCount += 1;

                                            reloadInputTag();
                                            refreshTagInputEditor();

                                            tag_save.setClickable(true);

                                        } else {

                                            TastyToast.makeText(getApplicationContext(), "태그 선택이 완료 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                                            next_button.setClickable(true);
                                            tag_save.setClickable(true);

                                        }

                                    } else {

                                        e.printStackTrace();
                                        tag_save.setClickable(true);
                                    }

                                }
                            });

                        }

                    }

                }


            }
        });


        tag1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tag1 != null){

                    functionBase.tagRemoveActionFromString(currentUser, tag1, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                reloadInputTag();

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }

            }
        });

        tag2_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(tag2 != null){

                    functionBase.tagRemoveActionFromString(currentUser, tag2, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                reloadInputTag();

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }

            }
        });

        tag3_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tag3 != null){

                    functionBase.tagRemoveActionFromString(currentUser, tag3, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                reloadInputTag();

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }

            }
        });

        tag4_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tag4 != null){

                    functionBase.tagRemoveActionFromString(currentUser, tag4, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                reloadInputTag();

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }

            }
        });

        tag5_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tag5 != null){

                    functionBase.tagRemoveActionFromString(currentUser, tag5, new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                reloadInputTag();

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }

            }
        });

    }

    public Comparator<Map<String, Integer>> mapComparator = new Comparator<Map<String, Integer>>() {

        public int compare(Map<String, Integer> m1, Map<String, Integer> m2) {

            return m2.get("count").compareTo(m1.get("count"));

        }

    };




    private void refreshTagInputEditor(){

        tag_input.setText("");
        functionBase.hideKeyboard(this);

    }


    private void reloadInputTag(){

        selectedTagReset();

        if(currentUser!= null){

            currentUser.fetchInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject fetchedUserOb, ParseException e) {

                    if(fetchedUserOb.get("tags") != null){

                        JSONArray tagList = fetchedUserOb.getJSONArray("tags");

                        inputTagCount = 5 - tagList.length();

                        if(inputTagCount == 0){

                            tag_count.setVisibility(View.GONE);
                            tag_message.setText("'다음'을 선택하세요");
                            next_button.setVisibility(View.VISIBLE);

                        } else {

                            tag_count.setVisibility(View.VISIBLE);
                            tag_count.setText( String.valueOf(inputTagCount) + "개 " );
                            tag_message.setText( "를 선택하시면 다음으로 넘어갑니다." );
                            next_button.setVisibility(View.GONE);

                        }



                        for(int i=0; tagList.length()>i;i++){

                            String inputTag = null;

                            try {
                                inputTag = tagList.getString(i);

                                if(i==0){

                                    tag_selected_1.setText(inputTag);
                                    tagSelected1 = true;
                                    tag1 = inputTag;
                                    Log.d("tag1", tag1);

                                } else if(i==1){

                                    tag_selected_2.setText(inputTag);
                                    tagSelected2 = true;
                                    tag2 = inputTag;
                                    Log.d("tag2", tag2);

                                } else if(i==2){

                                    tag_selected_3.setText(inputTag);
                                    tagSelected3 = true;
                                    tag3 = inputTag;
                                    Log.d("tag3", tag3);

                                } else if(i==3){

                                    tag_selected_4.setText(inputTag);
                                    tagSelected4 = true;
                                    tag4 = inputTag;
                                    Log.d("tag4", tag4);

                                } else if(i==4){

                                    tag_selected_5.setText(inputTag);
                                    tagSelected5 = true;
                                    tag5 = inputTag;
                                    Log.d("tag5", tag5);

                                }

                            } catch (JSONException e1) {

                                e1.printStackTrace();

                            }

                        }

                    } else {

                        inputTagCount = 5;

                        if(inputTagCount == 0){

                            tag_count.setVisibility(View.GONE);
                            tag_message.setText("'다음'을 선택하세요");
                            next_button.setVisibility(View.VISIBLE);

                        } else {

                            tag_count.setVisibility(View.VISIBLE);
                            tag_count.setText( String.valueOf(inputTagCount) + "개 " );
                            tag_message.setText( "를 선택하시면 다음으로 넘어갑니다." );
                            next_button.setVisibility(View.GONE);

                        }

                    }

                }
            });

        } else {

            Intent intent = new Intent(getApplicationContext(), MainBoardActivity.class);
            startActivity(intent);

            finish();

        }





    }

    private void selectedTagReset(){

        tag_selected_1.setText("+");
        tag_selected_2.setText("+");
        tag_selected_3.setText("+");
        tag_selected_4.setText("+");
        tag_selected_5.setText("+");

        tagSelected1 = false;
        tagSelected2 = false;
        tagSelected3 = false;
        tagSelected4 = false;
        tagSelected5 = false;

    }





}
