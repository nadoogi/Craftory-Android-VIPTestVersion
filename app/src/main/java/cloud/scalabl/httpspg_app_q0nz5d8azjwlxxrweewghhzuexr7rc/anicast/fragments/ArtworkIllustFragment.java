package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ArtworkIllustFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_menu_board, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView menu_list = (RecyclerView) view.findViewById(R.id.menu_list);

        RequestManager requestManager = Glide.with(getActivity());

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);

        menu_list.setLayoutManager(layoutManager);
        menu_list.setHasFixedSize(true);
        menu_list.setNestedScrollingEnabled(false);

        //menuRecyclerAdapter = new MenuRecyclerAdapter(getActivity(), requestManager );

        //menuRecyclerAdapter.setObjectsPerPage(3);
        //menu_list.setAdapter(menuRecyclerAdapter);

    }


    @Override
    public void onResume() {
        super.onResume();

    }


}
