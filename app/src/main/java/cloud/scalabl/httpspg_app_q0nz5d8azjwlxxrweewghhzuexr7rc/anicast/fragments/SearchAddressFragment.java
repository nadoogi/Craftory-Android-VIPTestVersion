package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.AddressListAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.MyTimelineAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SearchAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SearchPatronAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SearchSerieseAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressDataFromFragmentListener;
import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ssamkyu on 2017. 5. 15..
 */

public class SearchAddressFragment extends Fragment implements AddressClickListener{

    private EditText search_input;
    private ImageView search_input_reset;
    private LinearLayout search_button;
    private RecyclerView result_list;

    private FunctionBase functionBase;

    private final String key = "U01TX0FVVEgyMDE4MDMyNTEyNDYxMDEwNzc2NTI=";
    private final String url = "http://www.juso.go.kr/addrlink/addrLinkApi.do";

    private ArrayList<JSONObject> listArray;
    private LinearLayoutManager layoutManager;
    private AddressListAdapter addressListAdapter;

    private AddressDataFromFragmentListener addressDataFromFragmentListener;
    public void setAddressDataFromFragmentListener(AddressDataFromFragmentListener addressDataFromFragmentListener){

        this.addressDataFromFragmentListener = addressDataFromFragmentListener;
    }

    public SearchAddressFragment(){


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_address_search, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        functionBase = new FunctionBase(getActivity());


        search_input = (EditText) view.findViewById(R.id.search_input);
        search_input_reset = (ImageView) view.findViewById(R.id.search_input_reset);
        search_button = (LinearLayout) view.findViewById(R.id.search_button);

        result_list = (RecyclerView) view.findViewById(R.id.result_list);

        search_input_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_input.setText("");

            }
        });

        search_button.setFocusableInTouchMode(true);
        search_button.requestFocus();

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        result_list.setLayoutManager(layoutManager);
        result_list.setHasFixedSize(true);
        result_list.setNestedScrollingEnabled(false);

        //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
        addressListAdapter = new AddressListAdapter(getActivity(), listArray);

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_button.setClickable(false);

                listArray = new ArrayList<>();
                addressListAdapter = new AddressListAdapter(getActivity(), listArray);
                addressListAdapter.setAddressClickListener(SearchAddressFragment.this);

                String queryString = search_input.getText().toString();
                String requestUrl = url + "?confmKey=" + key + "&resultType=json&countPerPage=20&currentPage=1&keyword=" + queryString;

                Log.d("search", requestUrl);

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(requestUrl , new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // called before request is started
                        Log.d("step", "start");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        Log.d("step", "success");

                        try {

                            String result = new String(responseBody);
                            JSONObject json = new JSONObject(result);
                            JSONObject requestResult = json.getJSONObject("results");

                            JSONObject status = requestResult.getJSONObject("common");

                            String totalCount = status.getString("totalCount");
                            String currentPage = status.getString("currentPage");
                            String countPerPage = status.getString("countPerPage");
                            String errorCode = status.getString("errorCode");
                            String errorMessage = status.getString("errorMessage");

                            Log.d("errorCode", errorCode);

                            JSONArray juso = requestResult.getJSONArray("juso");



                            for(int i=0; juso.length()>i;i++){

                                JSONObject data = (JSONObject) juso.get(i);
                                listArray.add(data);

                            }

                            addressListAdapter = new AddressListAdapter(getActivity(), listArray);
                            addressListAdapter.setAddressClickListener(SearchAddressFragment.this);

                            result_list.setAdapter(addressListAdapter);

                            search_button.setClickable(true);

                        } catch (JSONException e) {

                            TastyToast.makeText(getActivity(), "검색 결과가 없습니다.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            search_button.setClickable(true);
                            e.printStackTrace();

                        }




                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        Log.d("step", "fail");
                        search_button.setClickable(true);

                    }


                    @Override
                    public void onRetry(int retryNo) {
                        // called when request is retried
                        Log.d("step", "retry");
                    }

                });


            }
        });



    }


    @Override
    public void onAddressClicked(JSONObject data) {

        addressDataFromFragmentListener.onAddressClicked(data);

    }
}
