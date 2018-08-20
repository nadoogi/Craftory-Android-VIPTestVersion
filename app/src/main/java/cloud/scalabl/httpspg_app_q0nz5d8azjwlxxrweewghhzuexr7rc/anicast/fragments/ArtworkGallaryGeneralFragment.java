package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters.CommercialGallaryAdapter;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 8. 10..
 */

public class ArtworkGallaryGeneralFragment extends Fragment {

    RequestManager requestManager;

    RecyclerView gallary_post_list;
    TagGroup tag_group;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    CommercialGallaryAdapter commercialGallaryAdapter;

    ParseUser currentUser;

    private FunctionBase functionBase;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_artwork_gallary, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        requestManager = Glide.with(getActivity());
        currentUser = ParseUser.getCurrentUser();
        functionBase = new FunctionBase(getActivity());

        gallary_post_list = (RecyclerView) view.findViewById(R.id.gallary_post_list);
        tag_group = (TagGroup) view.findViewById(R.id.tag_group);

        ParseQuery query = ParseQuery.getQuery("TagLog");
        query.whereEqualTo("status", true);
        query.whereEqualTo("add", true);
        query.setLimit(100);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> tagObs, ParseException e) {

                if(e==null){

                    String[] tagArray = functionBase.tagMaker(tagObs);
                    tag_group.setTags( tagArray );

                    if(currentUser != null){

                        tag_group.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                            @Override
                            public void onTagClick(final String tag) {

                                ParseObject tagLog = new ParseObject("TagLog");
                                tagLog.put("tag", tag);
                                tagLog.put("user", currentUser);
                                tagLog.put("type", "tagAdd");
                                tagLog.put("place", "ArtistIllustFragment");
                                tagLog.put("status", true);
                                tagLog.put("add", true);
                                tagLog.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            currentUser.addUnique("tags", tag);
                                            currentUser.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {

                                                    if(e==null){

                                                        TastyToast.makeText(getActivity(), "태그가 추가 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                        commercialGallaryAdapter.loadObjects(0);

                                                    } else {



                                                    }

                                                }
                                            });

                                        } else {

                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }
                        });

                    } else {

                        TastyToast.makeText(getActivity(), "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);

                    }



                } else {

                    e.printStackTrace();
                }

            }

        });

        final PullRefreshLayout swipeLayout = (PullRefreshLayout) view.findViewById(R.id.swipeLayout);
        swipeLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                commercialGallaryAdapter = new CommercialGallaryAdapter(getActivity(), requestManager);

                swipeLayout.setRefreshing(false);

            }

        });

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);

        gallary_post_list.setLayoutManager(layoutManager);
        //webtoon_post_list.setHasFixedSize(true);
        gallary_post_list.setNestedScrollingEnabled(false);

        commercialGallaryAdapter = new CommercialGallaryAdapter(getActivity(), requestManager);

        gallary_post_list.setItemAnimator(new SlideInUpAnimator());
        gallary_post_list.setAdapter(commercialGallaryAdapter);

        gallary_post_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        commercialGallaryAdapter.loadNextPage();
                    }

                }

            }
        });



    }


    @Override
    public void onResume() {
        super.onResume();

        if(commercialGallaryAdapter != null){

            commercialGallaryAdapter.notifyDataSetChanged();

        }

    }


}
