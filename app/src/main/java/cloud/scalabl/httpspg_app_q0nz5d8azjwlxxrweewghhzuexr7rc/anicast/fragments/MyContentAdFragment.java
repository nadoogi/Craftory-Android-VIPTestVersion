package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentAdAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyContentDashboardAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.DateChageListener;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class MyContentAdFragment extends Fragment implements DateChageListener{

    private static RecyclerView my_content_list;
    int pastVisibleItems, visibleItemCount, totalItemCount;
    //public static MyTimelineAdapter myTimelineAdapter;
    private RequestManager requestManager;
    private MyContentAdAdapter myContentAdAdapter;

    private TextView current_date;
    private LinearLayout pre_move;
    private LinearLayout next_move;

    private TextView ad_view_user;
    private TextView ad_profit;

    private Date currentDate;
    private FunctionBase functionBase;
    private ParseUser currentUser;
    private int totalCount;
    private int adRevenue;

    private int myCount;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_my_content_ad, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        my_content_list = (RecyclerView) view.findViewById(R.id.my_content_list);

        current_date = (TextView) view.findViewById(R.id.current_date);
        pre_move = (LinearLayout) view.findViewById(R.id.pre_move);
        next_move = (LinearLayout) view.findViewById(R.id.next_move);

        ad_view_user = (TextView) view.findViewById(R.id.ad_view_user);
        ad_profit = (TextView) view.findViewById(R.id.ad_profit);

        functionBase = new FunctionBase(getActivity());

        currentDate = new Date();
        currentUser = ParseUser.getCurrentUser();

        current_date.setText( functionBase.dateToStringForDisplay(currentDate) );

        pre_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ad_view_user.setText("로딩중..");
                ad_profit.setText("로딩중..");

                Log.d("currentDate", currentDate.toString());

                Date preDate = functionBase.yesterday(currentDate);
                currentDate = preDate;

                current_date.setText( functionBase.dateToStringForDisplay(currentDate) );

                myContentAdAdapter = new MyContentAdAdapter(getActivity(), requestManager, currentDate);
                my_content_list.setAdapter(myContentAdAdapter);

                myDateRefresh(currentDate);


            }
        });

        next_move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("currentDate", currentDate.toString());

                ad_view_user.setText("로딩중..");
                ad_profit.setText("로딩중..");

                if(todayCheck(currentDate)){

                    Date nextDate = functionBase.tommorow(currentDate);
                    currentDate = nextDate;

                    current_date.setText( functionBase.dateToStringForDisplay(currentDate) );

                    myContentAdAdapter = new MyContentAdAdapter(getActivity(), requestManager, currentDate);
                    my_content_list.setAdapter(myContentAdAdapter);

                    myDateRefresh(currentDate);


                } else {

                    TastyToast.makeText(getActivity(), "내일 데이터는 저도 궁금합니다!", TastyToast.LENGTH_LONG, TastyToast.INFO);

                }



            }
        });


        myDateRefresh(currentDate);

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myContentAdAdapter = new MyContentAdAdapter(getActivity(), requestManager, currentDate);
                my_content_list.setAdapter(myContentAdAdapter);

                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getActivity());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

        my_content_list.setLayoutManager(layoutManager);
        my_content_list.setHasFixedSize(true);
        my_content_list.setNestedScrollingEnabled(false);

        myContentAdAdapter = new MyContentAdAdapter(getActivity(), requestManager, currentDate);

        my_content_list.setItemAnimator(new SlideInUpAnimator());
        my_content_list.setAdapter(myContentAdAdapter);


        my_content_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy > 0) //check for scroll down
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                    {
                        myContentAdAdapter.loadNextPage();
                    }

                }

            }
        });



    }

    private void myDateRefresh(final Date currentDate){

        final Calendar startDate = Calendar.getInstance();
        startDate.set(currentDate.getYear() + 1900, currentDate.getMonth(), currentDate.getDate(), 0,0,1);

        Log.d("startDate1", startDate.getTime().toString());

        final Calendar endDate = Calendar.getInstance();
        endDate.set(currentDate.getYear() + 1900, currentDate.getMonth(), currentDate.getDate(), 23, 59, 59);

        Log.d("endDate1", endDate.getTime().toString());


        ParseQuery myQuery = ParseQuery.getQuery("AdLog");

        myQuery.whereEqualTo("to", currentUser);
        myQuery.whereEqualTo("unique", true);
        myQuery.whereGreaterThan("createdAt", startDate.getTime());
        myQuery.whereLessThan("queryDate", endDate.getTime());
        myQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e==null){

                    int count = objects.size();

                    Log.d("myCount", String.valueOf(count));

                    myCount = count;

                    refreshData(currentDate, myCount);

                } else {

                    refreshData(currentDate, 0);

                    e.printStackTrace();
                }

            }

        });

    }

    private void refreshData(final Date currentDate, final int count){

        final Calendar startDate = Calendar.getInstance();
        startDate.set(currentDate.getYear() + 1900, currentDate.getMonth(), currentDate.getDate(), 0,0,1);

        Log.d("startDate2", startDate.getTime().toString());

        final Calendar endDate = Calendar.getInstance();
        endDate.set(currentDate.getYear() + 1900, currentDate.getMonth(), currentDate.getDate(), 23, 59, 59);

        Log.d("endDate2", endDate.getTime().toString());


        ParseQuery<ParseObject> query = ParseQuery.getQuery("AdDailyResult");

        query.whereGreaterThan("date", startDate.getTime());
        query.whereLessThan("date", endDate.getTime());
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {

                if(e==null){

                    if(object.get("ad_revenue") == null){

                        ad_view_user.setText("미입력");
                        ad_profit.setText("미입력");

                    } else {

                        adRevenue = object.getInt("ad_revenue");
                        totalCount = object.getInt("total_count");

                        Log.d("adRevenue",String.valueOf(adRevenue));
                        Log.d("totalCount", String.valueOf(totalCount));
                        Long ratio = Long.parseLong(String.valueOf(count)) / Long.parseLong(String.valueOf(totalCount));
                        Long longRevenue = Long.parseLong(String.valueOf(adRevenue)) * ratio;


                        if(todayCheck(currentDate)){

                            ad_view_user.setText(functionBase.makeComma(count) + " 회");
                            ad_profit.setText(functionBase.makeComma(Integer.parseInt(String.valueOf(longRevenue))) + " 원");

                        } else {

                            ad_view_user.setText("입력 예정");
                            ad_profit.setText("입력 예정");

                        }

                    }



                } else {

                    if(todayCheck(currentDate)){

                        ad_view_user.setText("0 회");
                        ad_profit.setText("0 원");

                    } else {

                        ad_view_user.setText("입력 예정");
                        ad_profit.setText("입력 예정");

                    }


                    e.printStackTrace();

                }

            }
        });

    }

    private Boolean todayCheck( Date compareDate){

        Date todayDate = new Date();

        Calendar todayCal = Calendar.getInstance();
        todayCal.set(todayDate.getYear() + 1900, todayDate.getMonth(), todayDate.getDate(), 0,0,1);
        Date today = todayCal.getTime();

        Log.d("today", today.toString());
        Log.d("comapreDate", compareDate.toString());

        if(today.after(compareDate)){

            return true;

        } else {

            return false;
        }

    }


    @Override
    public void onResume() {
        super.onResume();



    }

    @Override
    public void onDateChanged(Date selectedDate) {



    }
}
