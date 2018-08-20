package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SearchAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SearchPatronAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.SearchSerieseAdapter;

/**
 * Created by ssamkyu on 2017. 5. 15..
 */

public class SearchSerieseFragment extends Fragment {

    private String query;
    //private static RecyclerView search_result;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    private RequestManager requestManager;

    //public static SearchChannelAdapter searchChannelAdapter;
    public SearchAdapter searchAdapter;
    public SearchPatronAdapter searchPatronAdapter;
    public SearchSerieseAdapter searchSerieseAdapter;

    TextView query_text;
    TextView result_count;
    int position;

    TextView search_category;

    public SearchSerieseFragment(){


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        query = getArguments().getString("query");
        position = getArguments().getInt("position");
        requestManager = Glide.with(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView search_result = (RecyclerView) view.findViewById(R.id.search_result);
        query_text = (TextView) view.findViewById(R.id.query_text);
        result_count = (TextView) view.findViewById(R.id.result_count);
        search_category = (TextView) view.findViewById(R.id.search_category);

        query_text.setText(query);



        String type = getArguments().getString("type");

        if(position == 0){

            search_category.setText("작품");

        } else if(position == 1){

            search_category.setText("후원");

        } else {

            search_category.setText("연재");

        }

        final LinearLayoutManager layoutManager;

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

        search_result.setLayoutManager(layoutManager);
        search_result.setHasFixedSize(true);

        searchSerieseAdapter = new SearchSerieseAdapter( getActivity(), "createdAt", false , query, "channel" , requestManager);

        search_result.setAdapter(searchSerieseAdapter);
        search_result.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        searchSerieseAdapter.loadNextPage();
                    }

                }

            }
        });



    }

}
