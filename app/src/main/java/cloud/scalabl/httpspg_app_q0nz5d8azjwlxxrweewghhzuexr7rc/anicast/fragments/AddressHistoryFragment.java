package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONObject;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ChargeActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.ContentManagerActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LibraryActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.AddressHistoryListAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.AddressListAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressDataFromFragmentListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressDataFromHistoryFragmentListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.AddressHistoryClickListener;


/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class AddressHistoryFragment extends Fragment implements AddressHistoryClickListener {

    private LinearLayoutManager layoutManager;
    private AddressHistoryListAdapter addressHistoryListAdapter;
    private AddressDataFromHistoryFragmentListener addressDataFromFragmentListener;
    public void setAddressDataFromHistoryFragmentListener(AddressDataFromHistoryFragmentListener addressDataFromFragmentListener){

        this.addressDataFromFragmentListener = addressDataFromFragmentListener;
    }

    private RecyclerView result_list;
    private ParseUser currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_address_history, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentUser = ParseUser.getCurrentUser();

        result_list = (RecyclerView) view.findViewById(R.id.result_list);

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        result_list.setLayoutManager(layoutManager);
        result_list.setHasFixedSize(true);
        result_list.setNestedScrollingEnabled(false);


        if(currentUser != null){

            addressHistoryListAdapter = new AddressHistoryListAdapter(getActivity());
            addressHistoryListAdapter.setAddressHistoryClickListener(AddressHistoryFragment.this);
            result_list.setAdapter(addressHistoryListAdapter);

        }

    }


    @Override
    public void onResume() {
        super.onResume();



    }


    @Override
    public void onAddressClicked(ParseObject addressOb) {

        addressDataFromFragmentListener.onAddressClicked(addressOb);

    }


}
