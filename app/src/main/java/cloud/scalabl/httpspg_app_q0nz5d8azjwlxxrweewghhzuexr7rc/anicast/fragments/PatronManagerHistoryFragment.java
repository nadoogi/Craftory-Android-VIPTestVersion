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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.PatronUserAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class PatronManagerHistoryFragment extends Fragment {

    private RecyclerView history_list;
    private ParseUser currentUser;

    private String postId;

    private PatronUserAdapter patronUserAdapter;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    private RequestManager requestManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getActivity());

        return inflater.inflate(R.layout.fragment_patron_manager_history, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        postId = getArguments().getString("postId");

        history_list = (RecyclerView) view.findViewById(R.id.history_list);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

        history_list.setLayoutManager(layoutManager);

        ParseQuery query = ParseQuery.getQuery("ArtistPost");
        query.include("user");
        query.include("item");
        query.getInBackground(postId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject patronOb, ParseException e) {

                if(e==null){



                    patronUserAdapter = new PatronUserAdapter(getActivity(), requestManager, patronOb);
                    history_list.setAdapter(patronUserAdapter);

                    history_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);

                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {

                                    patronUserAdapter.loadNextPage();

                                }

                            }

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

        if(patronUserAdapter != null){

            patronUserAdapter.loadObjects(0);

        }



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
