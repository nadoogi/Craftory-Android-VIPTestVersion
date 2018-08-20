package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.ArtworkHomeBannerViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.ArtworkHomeItemViewHolder;

import static com.igaworks.adbrix.db.ViralCPIDAO.getActivity;

/**
 * Created by ssamkyu on 2017. 4. 27..
 */

public class ArtworkHomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {



    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();

        public void onLoaded(List<ParseObject> objects, Exception e);
    }


    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners =
            new ArrayList<>();


    int viewType;
    String className;
    ArrayList<ParseObject> items = new ArrayList<>();
    Context context;
    Boolean cardViewBoolean;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;

    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private List<List<ParseObject>> objectPages = new ArrayList<>();

    private static RequestManager requestManager;




    public ArtworkHomeRecyclerAdapter(Context context, RequestManager requestManager){

        this.context = context;
        ArtworkHomeRecyclerAdapter.requestManager = requestManager;
        ParseUser currentUser = ParseUser.getCurrentUser();

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View listView;

        switch (viewType){

            case 0:

                listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artwork_home_banner , parent, false);

                return new ArtworkHomeBannerViewHolder(listView);


            case 1:

                listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artwork_home_horizon, parent, false);

                return new ArtworkHomeItemViewHolder(listView);



            /*
            case 2:

                //listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_list_horizon_item, parent, false);
                //MainListHorizonViewHolder type2Holder = new MainListHorizonViewHolder(listView);

                //return type2Holder;

                break;

            */
            default:

                return null;

        }



    }







    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {



        switch (position){

            case 0:


                RecyclerView banner_list = ((ArtworkHomeBannerViewHolder)holder).getHomeBannerList();

                //final LinearLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);

                banner_list.setLayoutManager(layoutManager);
                banner_list.setHasFixedSize(true);
                banner_list.setNestedScrollingEnabled(false);

                //final NotiAdapter notiAdapter = new NotiAdapter(getApplicationContext(), FunctionBase.createFilter, false, lastCheckTime);
                final ArtworkHomeBannerAdapter artworkHomeBannerAdapter = new ArtworkHomeBannerAdapter(getActivity(), requestManager);
                banner_list.setAdapter(artworkHomeBannerAdapter);

                break;




            case 1:

                RecyclerView item_list = ((ArtworkHomeItemViewHolder)holder).getHomeHorizonList();
                TextView item_title = ((ArtworkHomeItemViewHolder)holder).getHomeMenuTitle();


                break;


            /*
            case 2:


                break;

            default:


                break;

            */

        }


    }


    @Override
    public void onClick(View view) {



    }


    @Override
    public int getItemCount() {

        return 2;

    }

    public ParseObject getItem(int position){

        return null;

    }


    @Override
    public int getItemViewType(int position) {

        return position;
    }


    public void loadObjects(final int page) {

        final ParseQuery<ParseObject> query = this.queryFactory.create();

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


                if ((e != null) &&
                        ((e.getCode() == ParseException.CONNECTION_FAILED) ||
                                (e.getCode() != ParseException.CACHE_MISS))) {
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
        }
        else {
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
        query.setLimit(this.objectsPerPage + 1);
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
