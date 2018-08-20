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

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ChargeHistoryAdapter;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ChargeHistoryFragment extends Fragment {

    RecyclerView history_list;
    ChargeHistoryAdapter chargeHistoryAdapter;
    RequestManager requestManager;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        requestManager = Glide.with(getActivity());

        return inflater.inflate(R.layout.fragment_charge_history, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        history_list = (RecyclerView) view.findViewById(R.id.history_list);

        final LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

        history_list.setLayoutManager(layoutManager);
        history_list.setHasFixedSize(true);

        chargeHistoryAdapter = new ChargeHistoryAdapter(getActivity(), requestManager);

        history_list.setAdapter(chargeHistoryAdapter);
        history_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.d("dx", String.valueOf(dx));
                Log.d("dy", String.valueOf(dy));

                if(dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        chargeHistoryAdapter.loadNextPage();
                    }

                }

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
