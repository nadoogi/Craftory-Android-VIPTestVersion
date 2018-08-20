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
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.UserMyPostAdapter;

/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class UserDashBoardFragment extends Fragment {

    private static RecyclerView image_list;

    private static int pastVisibleItems, visibleItemCount, totalItemCount;

    private static UserMyPostAdapter userMyPostAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_user_dashboard, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final String userId = getArguments().getString("userId");

        image_list = (RecyclerView) view.findViewById(R.id.user_list);



        ParseQuery userQuery = ParseUser.getQuery();
        userQuery.include("my_info");
        userQuery.getInBackground(userId, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser userOb, ParseException e) {

                if(e==null){

                    final LinearLayoutManager layoutManager;

                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);


                    image_list.setLayoutManager(layoutManager);

                    image_list.setHasFixedSize(true);

                    Log.d("userId", userOb.getObjectId());

                    RequestManager requestManager = Glide.with(getActivity());

                    userMyPostAdapter = new UserMyPostAdapter(getActivity(), requestManager, userOb);

                    image_list.setAdapter(userMyPostAdapter);

                    image_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                        @Override
                        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                            super.onScrolled(recyclerView, dx, dy);
                            if(dy > 0) {
                                visibleItemCount = layoutManager.getChildCount();
                                totalItemCount = layoutManager.getItemCount();
                                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                                if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                    userMyPostAdapter.loadNextPage();
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

        if(userMyPostAdapter != null){

            userMyPostAdapter.loadObjects(0);

        }
    }

}
