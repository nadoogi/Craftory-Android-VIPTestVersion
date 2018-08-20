package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ChargeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ContentManagerActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LibraryActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;


/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class PatronManagerHomeFragment extends Fragment {

    private TextView total_box;
    private TextView achieve_ratio;

    private TextView patron_count;
    private TextView production_box;
    private TextView profit_box;
    private LinearLayout patron_cancel;
    private LinearLayout production_button;

    private ParseUser currentUser;

    private String postId;
    private FunctionBase functionBase;
    private ParseObject postOb;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();

        return inflater.inflate(R.layout.fragment_patron_manager, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postId = getArguments().getString("postId");
        functionBase = new FunctionBase(getActivity());

        total_box = (TextView) view.findViewById(R.id.total_box);
        achieve_ratio = (TextView) view.findViewById(R.id.achieve_ratio);
        patron_count = (TextView) view.findViewById(R.id.patron_count);
        production_box = (TextView) view.findViewById(R.id.production_box);
        profit_box = (TextView) view.findViewById(R.id.profit_box);

        patron_cancel = (LinearLayout) view.findViewById(R.id.patron_cancel);
        production_button = (LinearLayout) view.findViewById(R.id.production_button);

        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.include("user");
        query.include("item");
        query.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject postObject, ParseException e) {

                if(e==null){

                    postOb = postObject;

                    int price = postOb.getInt("price");

                    int patron_count_int = postOb.getInt("order_count");
                    int production_price = postOb.getParseObject("item").getInt("price");

                    //achieve box
                    int achieve_amount = postOb.getInt("achieve_amount");

                    //target count
                    int target_amount = postOb.getInt("target_amount");

                    int progressRatio = functionBase.progressMaker(patron_count_int, target_amount);

                    Long ratio = Long.parseLong(String.valueOf(patron_count_int)) / Long.parseLong(String.valueOf(target_amount)) * 100;
                    int production_cost = production_price * patron_count_int;
                    int profit = achieve_amount - production_cost;

                    total_box.setText(functionBase.makeComma( achieve_amount ) + " BOX");
                    achieve_ratio.setText(String.valueOf(progressRatio ) + "%");
                    profit_box.setText(functionBase.makeComma(profit) + " BOX");
                    production_box.setText(functionBase.makeComma(production_cost) + " BOX");
                    patron_count.setText(functionBase.makeComma(patron_count_int) + " 명");

                    if(patron_cancel != null){

                        patron_cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                                        .title("굿즈펀딩 취소")
                                        .content("펀딩을 취소 하겠습니까?. 지금까지 받은 펀딩은 모두 참여자분들에게 돌아갑니다.")
                                        .positiveText("네")
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                postOb.put("status", false);
                                                postOb.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {

                                                        if(e==null){

                                                            if(postOb.getInt("achieve_amount") == 0){

                                                                TastyToast.makeText(getActivity(), "후원이 취소 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                                getActivity().finish();

                                                            } else {

                                                                HashMap<String, Object> params = new HashMap<>();

                                                                params.put("patronId", postOb.getObjectId());
                                                                params.put("action", "cancel");
                                                                params.put("key", currentUser.getSessionToken());

                                                                Date uniqueIdDate = new Date();
                                                                String uniqueId = uniqueIdDate.toString();

                                                                params.put("uid", uniqueId);


                                                                ParseCloud.callFunctionInBackground("patron_cancel", params, new FunctionCallback<Map<String, Object>>() {

                                                                    public void done(Map<String, Object> mapObject, ParseException e) {

                                                                        if (e == null) {

                                                                            Log.d("parse result", mapObject.toString());
                                                                            if(mapObject.get("status").toString().equals("true")){

                                                                                TastyToast.makeText(getActivity(), "후원 취소 완료.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                                                getActivity().finish();

                                                                            } else {

                                                                                if(mapObject.get("message").toString().equals("badwritter")){

                                                                                    TastyToast.makeText(getActivity(), "후원에 대한 권한이 없습니다. 작성자가 아닙니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                                                } else {

                                                                                    TastyToast.makeText(getActivity(), "후원 취소에 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);

                                                                                }

                                                                            }

                                                                        } else {

                                                                            if(mapObject != null){
                                                                                Log.d("parse result", mapObject.toString());
                                                                            }


                                                                            TastyToast.makeText(getActivity(), "후원 취소에 실패 했습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                                                            Log.d("error", "error");
                                                                            e.printStackTrace();

                                                                        }
                                                                    }
                                                                });

                                                            }

                                                        } else {

                                                            e.printStackTrace();
                                                        }

                                                    }
                                                });

                                            }
                                        });

                                MaterialDialog dialog = builder.build();
                                dialog.show();


                            }
                        });

                    }

                    production_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                                    .title("제작 요청")
                                    .content("제작 요청 하시겠습니까?")
                                    .positiveText("네")
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                            postOb.put("progress", "finish");
                                            postOb.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    getActivity().recreate();

                                                }
                                            });

                                        }
                                    });

                            MaterialDialog dialog = builder.build();
                            dialog.show();



                        }
                    });


                } else {

                    e.printStackTrace();
                }

            }

        });



    }


    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
