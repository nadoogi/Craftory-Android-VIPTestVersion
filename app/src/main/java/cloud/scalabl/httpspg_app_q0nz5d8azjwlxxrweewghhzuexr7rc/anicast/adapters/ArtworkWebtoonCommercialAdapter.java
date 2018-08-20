package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.LoginActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.ArtworkCommercialHeaderViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class ArtworkWebtoonCommercialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    public static ArrayList<ParseObject> items;
    private static ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private static List<List<ParseObject>> objectPages;
    private int objectsPerPage = 100;
    private boolean paginationEnabled;
    private boolean hasNextPage;
    private int currentPage;
    private static RequestManager requestManager;
    protected static Context context;
    protected static ParseUser currentUser;

    private static final int patronType = 0;
    private static final int postType = 1;

    private FunctionBase functionBase;

    public ArtworkWebtoonCommercialAdapter(Context context, RequestManager requestManager) {

        ArtworkWebtoonCommercialAdapter.requestManager = requestManager;
        ArtworkWebtoonCommercialAdapter.context = context;
        currentUser = ParseUser.getCurrentUser();
        items = new ArrayList<>();
        objectPages = new ArrayList<>();
        this.currentPage = 0;
        this.paginationEnabled = true;
        this.hasNextPage = true;
        this.onQueryLoadListeners = new ArrayList<>();
        functionBase = new FunctionBase(context);

        queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                query.whereEqualTo("status", true);
                query.whereEqualTo("open_flag", true);
                query.whereEqualTo("post_type", "seriese");
                query.whereNotEqualTo("seriese_count", null);
                query.whereEqualTo("content_type", "webtoon");
                query.whereNotEqualTo("level", "commercial");
                query.include("user");
                query.orderByDescending("updatedAt");

                return query;
            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if(viewType == 0){

            View headerView;

            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artwork_header, parent, false);

            return new ArtworkCommercialHeaderViewHolder(headerView);

        } else {

            View timelineView;

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item6, parent, false);

            return new TimelineItemViewHolder(timelineView);

        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == 0){

            final TagGroup tag_group = ((ArtworkCommercialHeaderViewHolder)holder).getTagGroup();
            TextView recent_filter_button = ((ArtworkCommercialHeaderViewHolder)holder).getRecentFilterButton();
            TextView recommend_filter_button = ((ArtworkCommercialHeaderViewHolder)holder).getRecommendFilterButton();
            TextView favor_filter_button = ((ArtworkCommercialHeaderViewHolder)holder).getFavorFilterButton();

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

                                                            TastyToast.makeText(context, "태그가 추가 되었습니다.", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                                            loadObjects(0);

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

                            TastyToast.makeText(context, "로그인이 필요 합니다.", TastyToast.LENGTH_LONG, TastyToast.INFO);

                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            context.startActivity(intent);

                        }



                    } else {

                        e.printStackTrace();
                    }

                }

            });

        } else {

            final ParseObject timelineOb = getItem(position);

            FunctionPost functionPost = new FunctionPost(context);
            functionPost.WebtoonSeriesePostBuilder( timelineOb, holder, requestManager);

        }

    }




    @Override
    public int getItemViewType(int position) {

        return position;

    }




    @Override
    public int getItemCount() {

        return items.size()+1;
    }


    public ParseObject getItem(int position) {
        return items.get(position-1);
    }



    public void loadObjects(final int page) {

        final ParseQuery<ParseObject> query = queryFactory.create();

        if (this.objectsPerPage > 0 && this.paginationEnabled) {
            this.setPageOnQuery(page, query);
        }

        this.notifyOnLoadingListeners();

        if (page >= objectPages.size()) {
            objectPages.add(page, new ArrayList<ParseObject>());
        }


        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> foundObjects, ParseException e) {

                if ((e != null) && ((e.getCode() == ParseException.CONNECTION_FAILED) || (e.getCode() != ParseException.CACHE_MISS))) {

                    hasNextPage = true;

                } else if (foundObjects != null) {

                    // Only advance the page, this prevents second call back from CACHE_THEN_NETWORK to
                    // reset the page.
                    if (page >= currentPage) {
                        currentPage = page;

                        // since we set limit == objectsPerPage + 1
                        hasNextPage = (foundObjects.size() > objectsPerPage);
                    }

                    if (paginationEnabled && foundObjects.size() > objectsPerPage) {
                        // Remove the last object, fetched in order to tell us whether there was a "next page"
                        foundObjects.remove(objectsPerPage);
                    }

                    List<ParseObject> currentPage = objectPages.get(page);
                    currentPage.clear();
                    currentPage.addAll(foundObjects);

                    syncObjectsWithPages();

                    // executes on the UI thread
                    notifyDataSetChanged();
                }

                notifyOnLoadedListeners(foundObjects, e);

            }
        });
    }


    public void loadNextPage() {


        if (items.size() == 0) {

            loadObjects(0);

        } else {

            loadObjects(currentPage + 1);

        }
    }


    public void setObjectsPerPage(int objectsPerPage) {
        this.objectsPerPage = objectsPerPage;
    }

    private void syncObjectsWithPages() {
        items.clear();
        for (List<ParseObject> pageOfObjects : objectPages) {
            items.addAll(pageOfObjects);
        }
    }



    protected void setPageOnQuery(int page, ParseQuery<ParseObject> query) {
        query.setLimit(this.objectsPerPage);
        query.setSkip(page * this.objectsPerPage);
    }

    public void addOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {
        this.onQueryLoadListeners.add(listener);
    }

    public void removeOnQueryLoadListener(OnQueryLoadListener<ParseObject> listener) {
        this.onQueryLoadListeners.remove(listener);
    }

    private void notifyOnLoadingListeners() {
        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {
            listener.onLoading();
        }
    }

    private void notifyOnLoadedListeners(List<ParseObject> objects, Exception e) {
        for (OnQueryLoadListener<ParseObject> listener : this.onQueryLoadListeners) {
            listener.onLoaded(objects, e);
        }
    }





}
