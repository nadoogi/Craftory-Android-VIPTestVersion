package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.ArtworkDashboardBestCommentViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class ArtworkDashBoardBestCommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners;
    public ArrayList<ParseObject> items;
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;
    private FunctionBase functionBase;


    public ArtworkDashBoardBestCommentAdapter(Context context, RequestManager requestManager) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.functionBase = new FunctionBase(context);

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
                query.whereEqualTo("status", true);

                query.whereNotEqualTo("parentOb", null);
                query.include("user");
                query.include("parentOb");
                query.orderByDescending("like_count");

                return query;
            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView;

        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_artwork_best_comment, parent, false);

        return new ArtworkDashboardBestCommentViewHolder(itemView);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject postOb = getItem(position);

        ImageView seriese_image = ((ArtworkDashboardBestCommentViewHolder)holder).getSerieseImage();
        TextView title = ((ArtworkDashboardBestCommentViewHolder)holder).getTitle();
        TextView comment = ((ArtworkDashboardBestCommentViewHolder)holder).getComment();
        TextView nickname  = ((ArtworkDashboardBestCommentViewHolder)holder).getNickname();
        LinearLayout profile_tag  = ((ArtworkDashboardBestCommentViewHolder)holder).getProfileTag();
        CircleImageView profile_image = ((ArtworkDashboardBestCommentViewHolder)holder).getProfileImage();
        TextView writter_name = ((ArtworkDashboardBestCommentViewHolder)holder).getWritterName();
        ImageView like_icon = ((ArtworkDashboardBestCommentViewHolder)holder).getLikeIcon();
        TextView like_count = ((ArtworkDashboardBestCommentViewHolder)holder).getLikeCount();

        ParseObject userOb = postOb.getParseObject("user");
        final ParseObject parentOb = postOb.getParseObject("parentOb");

        if(seriese_image != null){

            if(parentOb.get("image_cdn") != null){

                requestManager
                        .load(functionBase.imageUrlBase300 + parentOb.getString("image_cdn") )
                        .into( seriese_image );

            } else if(parentOb.get("image_youtube") != null){

                requestManager
                        .load( parentOb.getString("image_youtube") )
                        .into( seriese_image );

            }

            functionBase.chargeFollowPatronCheck(parentOb, seriese_image);

        }

        if(parentOb.get("title") != null){

            title.setText(parentOb.getString("title"));

        } else {

            title.setText(parentOb.getString("body"));

        }

        comment.setText( "'" + postOb.getString("body") + "'");
        like_count.setText(String.valueOf( postOb.getInt("like_count") ));
        like_icon.setColorFilter(functionBase.likedColor);

        try {

            ParseObject writterOb = parentOb.getParseObject("user").fetch();
            writter_name.setText(writterOb.getString("name"));

            if(writterOb.get("image_cdn") != null){

                requestManager
                        .load(functionBase.imageUrlBase100 + writterOb.getString("image_cdn"))
                        .into(profile_image);

            } else if(writterOb.get("pic_url") != null){

                requestManager
                        .load(writterOb.getString("pic_url"))
                        .into(profile_image);

            } else if(writterOb.get("thumnail") != null){

                requestManager
                        .load(writterOb.getParseFile("thumnail").getUrl())
                        .into(profile_image);

            } else {

                profile_image.setImageResource(R.drawable.ic_action_circle_profile);

            }

        } catch (ParseException e) {

            e.printStackTrace();

        }


    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        Log.d("itemCount", String.valueOf(items.size()));
        return items.size();
    }


    public ParseObject getItem(int position) {
        return items.get(position);
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

        Log.d("loadNestPage", String.valueOf(items.size()));
        Log.d("currentPage", String.valueOf(currentPage));

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
