package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FunctionCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.AdminBoxSendUserAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.UserSelectedListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class BoxManagerActivity extends AppCompatActivity implements UserSelectedListener{

    private String userId;
    private AdminBoxSendUserAdapter adminBoxSendUserAdapter;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private RecyclerView user_list;
    private RequestManager requestManager;
    private EditText user_query_input;
    private EditText send_text;
    private EditText send_box_amount;

    private Button save_button;

    private String selectedUserId;
    private ParseUser currentUser;

    private CircleImageView selected_profile_image;
    private TextView selected_profile_name;
    private FunctionBase functionBase;
    private ParseObject selectedOb;
    private TextView selected_user_point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_manager);

        user_list = (RecyclerView) findViewById(R.id.user_list);

        user_query_input = (EditText) findViewById(R.id.user_query_input);
        send_text = (EditText) findViewById(R.id.send_text);
        send_box_amount = (EditText) findViewById(R.id.send_box_amount);
        Button search_button = (Button) findViewById(R.id.search_button);
        selected_user_point = (TextView) findViewById(R.id.selected_user_point);

        save_button = (Button) findViewById(R.id.save_button);

        selected_profile_image = (CircleImageView) findViewById(R.id.selected_profile_image);
        selected_profile_name = (TextView) findViewById(R.id.selected_profile_name);

        requestManager = Glide.with(getApplicationContext());
        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getApplicationContext());

        final LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

        user_list.setLayoutManager(layoutManager);
        user_list.setHasFixedSize(true);

        //final ChannelRankingAdapter channelAdapter = new ChannelRankingAdapter(getApplicationContext(), "total_count", false);

        requestManager = Glide.with(this);

        adminBoxSendUserAdapter = new AdminBoxSendUserAdapter(getApplicationContext(), requestManager, "all");
        adminBoxSendUserAdapter.setUserSelecteListener(this);

        user_list.setAdapter(adminBoxSendUserAdapter);
        user_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        adminBoxSendUserAdapter.loadNextPage();
                    }

                }

            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String querySting = user_query_input.getText().toString();

                adminBoxSendUserAdapter = new AdminBoxSendUserAdapter(getApplicationContext(), requestManager, querySting);
                adminBoxSendUserAdapter.setUserSelecteListener(BoxManagerActivity.this);
                user_list.setAdapter(adminBoxSendUserAdapter);

                functionBase.hideKeyboard(BoxManagerActivity.this);

            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                save_button.setClickable(false);

                String sendTextString = send_text.getText().toString();
                String sendBoxAmountString = send_box_amount.getText().toString();


                if(sendTextString == null || sendTextString.length() == 0){

                    send_text.setError("필수입력 사항 입니다.");
                    save_button.setClickable(true);

                } else if(sendBoxAmountString == null || sendBoxAmountString.length() == 0){

                    send_box_amount.setError("필수 입력 사항 입니다.");
                    save_button.setClickable(true);

                } else if(selectedUserId == null){

                    TastyToast.makeText(getApplicationContext(), "보낼 사람을 선택하세요", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                    save_button.setClickable(true);

                } else {

                    HashMap<String, Object> params = new HashMap<>();

                    params.put("key", currentUser.getSessionToken());
                    params.put("point", sendBoxAmountString);
                    params.put("target", selectedUserId);
                    params.put("info", sendTextString);

                    Date uniqueIdDate = new Date();
                    String uniqueId = uniqueIdDate.toString();

                    params.put("uid", uniqueId);

                    ParseCloud.callFunctionInBackground("admin_send_box", params, new FunctionCallback<Map<String, Object>>() {

                        public void done(Map<String, Object> mapObject, ParseException e) {

                            if (e == null) {

                                Log.d("parse result", mapObject.toString());

                                if(mapObject.get("status").toString().equals("true")){

                                    userInfoUpdate(selectedOb);
                                    save_button.setClickable(true);
                                    TastyToast.makeText(getApplicationContext(), "성공", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);


                                } else {

                                    save_button.setClickable(true);
                                    TastyToast.makeText(getApplicationContext(), "BOX 저장 중 에러가 발생했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                }

                            } else {
                                Log.d("error", "error");
                                save_button.setClickable(true);
                                e.printStackTrace();
                            }
                        }
                    });

                }

            }
        });

    }

    @Override
    public void onUserSelected(ParseObject userOb) {

        this.selectedOb = userOb;
        this.selectedUserId = userOb.getObjectId();

        userInfoUpdate(userOb);

    }

    private void userInfoUpdate(ParseObject object){

        functionBase.profileImageLoading(selected_profile_image, object, requestManager);
        functionBase.profileNameLoading(selected_profile_name, object);

        ParseObject pointOb = object.getParseObject("point");

        if(pointOb != null){

            try {

                selected_user_point.setText(functionBase.makeComma( object.getParseObject("point").fetch().getInt("current_point") ));

            } catch (ParseException e) {

                e.printStackTrace();

            }

        } else {

            TastyToast.makeText(getApplicationContext(), "BOX가 설정되지 않았습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

        }

    }

}
