package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PokeItemAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.PokeItemSelectListener;
import in.myinnos.awesomeimagepicker.models.Image;

public class PokeActivity extends AppCompatActivity implements PokeItemSelectListener {

    private RecyclerView poke_list;
    private TextView action;
    private TextView target;
    private ImageView preview_image;
    private BootstrapButton poke_send_button;
    private BootstrapButton poke_image_make_button;
    private PokeItemAdapter pokeItemAdapter;
    private RequestManager requestManager;

    private ParseObject userObject;
    private ParseObject selectedPokeItemObject;
    private ParseUser currentUser;

    private FunctionBase functionBase;
    private ImageView delete_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke);

        //getSupportActionBar().setTitle("찌르기");

        Intent intent = getIntent();
        String userId = intent.getExtras().getString("userId");

        currentUser = ParseUser.getCurrentUser();

        functionBase = new FunctionBase(this, getApplicationContext());

        poke_list = (RecyclerView) findViewById(R.id.poke_list);
        action = (TextView) findViewById(R.id.action);
        target = (TextView) findViewById(R.id.target);
        preview_image = (ImageView) findViewById(R.id.preview_image);
        poke_send_button = (BootstrapButton) findViewById(R.id.poke_send_button);
        poke_image_make_button = (BootstrapButton) findViewById(R.id.poke_image_make_button);

        requestManager = Glide.with(getApplicationContext());

        LinearLayout back_button = (LinearLayout) findViewById(R.id.back_button);
        TextView back_button_text = (TextView) findViewById(R.id.back_button_text);
        delete_button = (ImageView) findViewById(R.id.delete_button);
        delete_button.setVisibility(View.GONE);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        back_button_text.setText("찌르기");


        poke_image_make_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(getApplicationContext(), PokeMakerActivity.class);
                startActivity(intent1);

            }
        });


        ParseQuery userQuery = ParseQuery.getQuery("_User");
        userQuery.getInBackground(userId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject userOb, ParseException e) {

                if(e==null){

                    userObject = userOb;

                    final LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);

                    poke_list.setLayoutManager(layoutManager);
                    poke_list.setHasFixedSize(true);

                    pokeItemAdapter = new PokeItemAdapter(getApplicationContext(), requestManager);
                    pokeItemAdapter.setPokeItemSelectListener(PokeActivity.this);
                    poke_list.setAdapter(pokeItemAdapter);

                    currentUser.fetchInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(final ParseObject fechedUser, ParseException e) {

                            poke_send_button.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(functionBase.pokeExist(fechedUser.getJSONArray("poke_list"), userOb.getObjectId())){

                                        TastyToast.makeText(getApplicationContext(), "이미 찔렀습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    } else if(selectedPokeItemObject == null){

                                        TastyToast.makeText(getApplicationContext(), "찌르기를 선택해 주세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                    }else {

                                        poke_send_button.setClickable(false);

                                        final ParseObject socialMsgOb = new ParseObject("SocialMessage");
                                        socialMsgOb.put("target", userOb);
                                        socialMsgOb.put("user", fechedUser);
                                        socialMsgOb.put("type", "poke");
                                        socialMsgOb.put("pokeItem", selectedPokeItemObject);
                                        socialMsgOb.put("status", true);
                                        socialMsgOb.saveInBackground(new SaveCallback() {
                                            @Override
                                            public void done(ParseException e) {

                                                if(e==null){

                                                    ArrayList<String> pokedUserArray = new ArrayList<>();
                                                    pokedUserArray.add(userOb.getObjectId());

                                                    fechedUser.addAllUnique("poke_list", pokedUserArray);
                                                    fechedUser.saveInBackground();

                                                    TastyToast.makeText(getApplicationContext(), "찌르기 성공", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                    HashMap<String, Object> params = new HashMap<>();

                                                    params.put("key", currentUser.getSessionToken());
                                                    params.put("target", userOb.getObjectId());
                                                    Date uniqueIdDate = new Date();
                                                    String uniqueId = uniqueIdDate.toString();

                                                    params.put("uid", uniqueId);

                                                    ParseCloud.callFunctionInBackground("poke", params, new FunctionCallback<Map<String, Object>>() {

                                                        public void done(Map<String, Object> mapObject, ParseException e) {

                                                            if (e == null) {

                                                                Log.d("result", mapObject.toString());

                                                                if(mapObject.get("status").toString().equals("true")){

                                                                    if(!functionBase.parseArrayCheck(fechedUser, "my_alert", socialMsgOb.getObjectId())){


                                                                        if(!fechedUser.getObjectId().equals(socialMsgOb.getParseObject("target").getObjectId())){

                                                                            ParseObject myAlert = new ParseObject("MyAlert");
                                                                            myAlert.put("from", fechedUser);
                                                                            myAlert.put("to", socialMsgOb.getParseObject("target"));
                                                                            myAlert.put("type", "social_poke");
                                                                            myAlert.put("social", socialMsgOb);
                                                                            myAlert.put("pokeItem", selectedPokeItemObject);
                                                                            myAlert.put("status", true);
                                                                            myAlert.saveInBackground();

                                                                            fechedUser.addAllUnique("my_alert", Arrays.asList(socialMsgOb.getObjectId()));
                                                                            fechedUser.saveInBackground();

                                                                        }



                                                                    }


                                                                    finish();

                                                                } else {

                                                                    poke_send_button.setClickable(true);

                                                                    TastyToast.makeText(getApplicationContext(), "찌르기 하던 중 에러가 발생했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                                }

                                                            } else {

                                                                poke_send_button.setClickable(true);
                                                                e.printStackTrace();

                                                            }
                                                        }
                                                    });


                                                } else {

                                                    poke_send_button.setClickable(true);
                                                    e.printStackTrace();
                                                }

                                            }
                                        });

                                    }


                                }
                            });


                        }
                    });



                } else {

                    e.printStackTrace();
                }

            }

        });




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {

            finish();

        }

        //return super.onOptionsItemSelected(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(pokeItemAdapter != null){

            pokeItemAdapter.loadObjects(0);
        }

    }

    @Override
    public void onImageSelected(ParseObject pokeItemObject) {

        selectedPokeItemObject = pokeItemObject;

        if(selectedPokeItemObject.getString("type").equals("custom")){

            delete_button.setVisibility(View.VISIBLE);
            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectedPokeItemObject.put("status", false);
                    selectedPokeItemObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {

                            if(e==null){

                                preview_image.setImageResource(R.drawable.image_background);
                                delete_button.setVisibility(View.GONE);

                                pokeItemAdapter.loadObjects(0);

                            } else {

                                e.printStackTrace();
                            }

                        }
                    });

                }
            });


        } else {

            delete_button.setVisibility(View.GONE);

        }

        if(selectedPokeItemObject.get("image") != null){

            requestManager
                    .load(selectedPokeItemObject.getParseFile("image").getUrl())
                    .into(preview_image);

        } else if(selectedPokeItemObject.get("image_cdn") != null){

            requestManager
                    .load( functionBase.imageUrlBase300 + selectedPokeItemObject.getString("image_cdn"))
                    .into(preview_image);

        } else {

            preview_image.setImageResource(R.drawable.image_background);

        }

        if(userObject.get("name") != null){

            target.setText(userObject.getString("name") + "님 ");

        } else {

            target.setText(userObject.getString("username") + "님");

        }

        if(selectedPokeItemObject.get("action") != null){

            action.setText( selectedPokeItemObject.getString("action") );

        }


    }
}
