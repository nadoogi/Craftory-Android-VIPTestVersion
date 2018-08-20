package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.ParseObject;
import com.parse.ParseUser;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ImmoticonMainAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.ImmoticonOpenAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ImmoticonSelectListener;


/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class ImmoticonOpenFragment extends Fragment implements ImmoticonSelectListener{

    private ParseUser currentUser;
    private RecyclerView immoticon_list;
    private ImmoticonOpenAdapter immoticonOpenAdapter;
    private RequestManager requestManager;

    private ImmoticonSelectListener immoticonSelectListener;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        currentUser = ParseUser.getCurrentUser();
        requestManager = Glide.with(getActivity());

        return inflater.inflate(R.layout.fragment_immoticon, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        immoticon_list = (RecyclerView) view.findViewById(R.id.immoticon_list);

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);

        immoticon_list.setLayoutManager(layoutManager);
        immoticon_list.setHasFixedSize(true);
        immoticon_list.setNestedScrollingEnabled(false);

        immoticonOpenAdapter = new ImmoticonOpenAdapter(getActivity(), requestManager);
        immoticonOpenAdapter.setImmoticonSelectListener(this);

        immoticon_list.setAdapter(immoticonOpenAdapter);

        immoticon_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if(dy > 0){
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if ( (visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        immoticonOpenAdapter.loadNextPage();
                    }

                }

            }
        });



    }

    public void setImmoticonOpenFragment(ImmoticonSelectListener immoticonOpenFragment){

        this.immoticonSelectListener = immoticonOpenFragment;

    }


    @Override
    public void onResume() {
        super.onResume();

        if(immoticonOpenAdapter != null){

            immoticonOpenAdapter.loadObjects(0);
        }


    }


    @Override
    public void onItemSelect(ParseObject itemOb) {

        immoticonSelectListener.onItemSelect(itemOb);

    }
}
