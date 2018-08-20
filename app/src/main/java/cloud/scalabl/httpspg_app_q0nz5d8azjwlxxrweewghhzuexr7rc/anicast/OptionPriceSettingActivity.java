package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.FundingMarketItemEditorAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.OptionListdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class OptionPriceSettingActivity extends AppCompatActivity {

    private String itemId;

    private TextView back_button_text;
    private LinearLayout back_button;

    private RecyclerView option_list;

    private LinearLayoutManager layoutManager;
    private RequestManager requestManager;

    private FunctionBase functionBase;
    private OptionListdapter optionListdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_price_setting);

        Intent intent = getIntent();

        itemId = intent.getExtras().getString("itemId");

        back_button = (LinearLayout) findViewById(R.id.back_button);
        back_button_text = (TextView) findViewById(R.id.back_button_text);
        option_list = (RecyclerView) findViewById(R.id.option_list);

        back_button_text.setText("옵션별 가격 설정");
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        functionBase = new FunctionBase(getApplicationContext());


        ParseQuery query = ParseQuery.getQuery("FundingMarketItem");
        query.getInBackground(itemId, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject itemOb, ParseException e) {

                if(e==null){

                    ParseQuery optionQuery = ParseQuery.getQuery("SalesItemOption");
                    optionQuery.whereEqualTo("market_item", itemOb);
                    optionQuery.whereEqualTo("status", true);
                    optionQuery.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> optionObs, ParseException e) {

                            if(e==null){

                                ParseRelation optionRelation = itemOb.getRelation("options");

                                for(int i=0;optionObs.size()>i;i++){

                                    optionRelation.add(optionObs.get(i));

                                }

                                itemOb.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            TastyToast.makeText(getApplicationContext(), "옵션 준비 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

                                        } else {

                                            e.printStackTrace();
                                        }

                                    }
                                });

                            } else {

                                e.printStackTrace();
                            }

                        }

                    });


                    layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL,false);

                    option_list.setLayoutManager(layoutManager);
                    option_list.setHasFixedSize(true);
                    option_list.setNestedScrollingEnabled(false);

                    optionListdapter = new OptionListdapter(getApplicationContext(), itemOb);

                    option_list.setItemAnimator(new SlideInUpAnimator());
                    option_list.setAdapter(optionListdapter);

                } else {


                }

            }

        });


    }
}
