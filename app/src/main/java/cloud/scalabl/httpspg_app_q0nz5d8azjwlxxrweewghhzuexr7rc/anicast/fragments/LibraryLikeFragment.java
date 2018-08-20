package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.LibraryLikeAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;



/**
 * Created by ssamkyu on 2017. 8. 7..
 */

public class LibraryLikeFragment extends Fragment implements View.OnClickListener{

    RecyclerView library_list;
    RequestManager requestManager;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    LibraryLikeAdapter libraryLikeAdapter;

    LinearLayout filter_all_button;
    ImageView filter_all_img;
    TextView filter_all_text;

    LinearLayout filter_webtoon_button;
    ImageView filter_webtoon_img;
    TextView filter_webtoon_text;

    /*

    LinearLayout filter_novel_button;
    ImageView filter_novel_img;
    TextView filter_novel_text;

    LinearLayout filter_youtube_button;
    ImageView filter_youtube_img;
    TextView filter_youtube_text;

    */

    LinearLayout filter_post_button;
    ImageView filter_post_img;
    TextView filter_post_text;

    String type;

    private FunctionBase functionBase;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_library_like, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        type = "all";

        library_list = (RecyclerView) view.findViewById(R.id.library_list);

        filter_all_button = (LinearLayout) view.findViewById(R.id.filter_all_button);
        filter_all_img = (ImageView) view.findViewById(R.id.filter_all_img);
        filter_all_text = (TextView) view.findViewById(R.id.filter_all_text);

        filter_webtoon_button = (LinearLayout) view.findViewById(R.id.filter_webtoon_button);
        filter_webtoon_img = (ImageView) view.findViewById(R.id.filter_webtoon_img);
        filter_webtoon_text = (TextView) view.findViewById(R.id.filter_webtoon_text);

        filter_post_button = (LinearLayout) view.findViewById(R.id.filter_post_button);
        filter_post_img = (ImageView) view.findViewById(R.id.filter_post_img);
        filter_post_text = (TextView) view.findViewById(R.id.filter_post_text);

        functionBase = new FunctionBase(getActivity());

        /*

        filter_novel_button = (LinearLayout) view.findViewById(R.id.filter_novel_button);
        filter_novel_img = (ImageView) view.findViewById(R.id.filter_novel_img);
        filter_novel_text = (TextView) view.findViewById(R.id.filter_novel_text);



        filter_youtube_button = (LinearLayout) view.findViewById(R.id.filter_youtube_button);
        filter_youtube_img = (ImageView) view.findViewById(R.id.filter_youtube_img);
        filter_youtube_text = (TextView) view.findViewById(R.id.filter_youtube_text);

        */

        filter_all_button.setOnClickListener(this);
        filter_webtoon_button.setOnClickListener(this);
        filter_post_button.setOnClickListener(this);

        //filter_novel_button.setOnClickListener(this);

        //filter_youtube_button.setOnClickListener(this);


        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                libraryLikeAdapter.loadObjects(0);
                swipeLayout.setRefreshing(false);

            }

        });

        requestManager = Glide.with(getActivity());

        //final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);

        library_list.setLayoutManager(layoutManager);
        library_list.setHasFixedSize(true);
        library_list.setNestedScrollingEnabled(false);

        libraryLikeAdapter = new LibraryLikeAdapter(getActivity(), requestManager, type);


        library_list.setItemAnimator(new SlideInUpAnimator());
        library_list.setAdapter(libraryLikeAdapter);

        library_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        libraryLikeAdapter.loadNextPage();
                    }

                }

            }
        });


        filterUISetting(type);

    }


    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.filter_all_button:

                type="all";

                filterUISetting(type);

                resetAdapter(type);

                break;

            case R.id.filter_webtoon_button:

                type = "webtoon";

                filterUISetting(type);

                resetAdapter(type);

                break;

            case R.id.filter_post_button:

                type = "post";

                filterUISetting(type);

                resetAdapter(type);

                break;

            /*

            case R.id.filter_novel_button:

                type = "novel";

                filterUISetting(type);

                resetAdapter(type);

                break;



            case R.id.filter_youtube_button:

                type = "youtube";

                filterUISetting(type);

                resetAdapter(type);

                break;

            */

        }

    }


    private void resetAdapter(String type){

        libraryLikeAdapter = new LibraryLikeAdapter(getActivity(), requestManager, type);
        library_list.setAdapter(libraryLikeAdapter);

    }



    private void filterUISetting(String type){

        functionBase.contentTypeFilterColorBuilder(type,filter_all_img, filter_all_text, filter_post_img, filter_post_text, filter_webtoon_img, filter_webtoon_text, null,null,null,null );

    }

}
