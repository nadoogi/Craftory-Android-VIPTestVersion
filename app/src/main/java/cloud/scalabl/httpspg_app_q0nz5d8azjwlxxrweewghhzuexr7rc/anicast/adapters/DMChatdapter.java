package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionFollow;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.CreatorViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.DMViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class DMChatdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private ArrayList<ParseObject> items = new ArrayList<>();
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages = new ArrayList<>();;
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;

    private FunctionBase functionBase;

    public DMChatdapter(Context context, RequestManager requestManager, FunctionBase functionBase, ParseObject targetUserOb) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.functionBase = functionBase;

        final ArrayList<String> queryString = new ArrayList<>();
        queryString.add(targetUserOb.getObjectId() + "-" + currentUser.getObjectId());
        queryString.add(currentUser.getObjectId() + "-" + targetUserOb.getObjectId());

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("DMChat");
                query.whereContainedIn("room", queryString);
                query.whereEqualTo("status", true);
                query.whereEqualTo("open_flag", true);
                query.include("from");
                query.include("to");
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View dmView;

        dmView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_dm, parent, false);

        return new DMViewHolder(dmView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //기능 추가
        ParseObject dmChatOb = getItem(position);

        LinearLayout send_layout = ((DMViewHolder)holder).getSendLayout();
        LinearLayout response_layout = ((DMViewHolder)holder).getResponseLayout();
        CircleImageView user_image = ((DMViewHolder)holder).getUserImage();
        TextView username = ((DMViewHolder)holder).getUserName();
        ImageView send_image = ((DMViewHolder)holder).getSendImage();
        TextView send_text = ((DMViewHolder)holder).getSendText();
        TextView send_date = ((DMViewHolder)holder).getSendDate();
        TextView response_date = ((DMViewHolder)holder).getResponseDate();

        RelativeLayout send_image_layout = ((DMViewHolder)holder).getSendImageLayout();
        RelativeLayout response_image_layout = ((DMViewHolder)holder).getResponseImageLayout();

        ImageView response_image = ((DMViewHolder)holder).getResponseImage();
        TextView response_text = ((DMViewHolder)holder).getResponseText();

        ParseObject sendUserOb = dmChatOb.getParseObject("from");
        ParseObject toUserOb = dmChatOb.getParseObject("to");


        if(sendUserOb.getObjectId().equals(currentUser.getObjectId())){
            //my message
            send_layout.setVisibility(View.GONE);
            response_layout.setVisibility(View.VISIBLE);
            response_date.setText(functionBase.dateToString(dmChatOb.getCreatedAt()));

            if(dmChatOb.getString("image_cdn") != null){


                response_image_layout.setVisibility(View.VISIBLE);
                functionBase.generalImageLoading(response_image, dmChatOb, requestManager);


            } else {


                response_image_layout.setVisibility(View.GONE);
            }

            if(dmChatOb.get("body") != null){


                response_text.setVisibility(View.VISIBLE);
                response_text.setText(dmChatOb.getString("body"));

            } else {


                response_text.setVisibility(View.GONE);
            }


        } else {
            //request message
            send_layout.setVisibility(View.VISIBLE);
            response_layout.setVisibility(View.GONE);

            functionBase.profileImageLoading(user_image, sendUserOb, requestManager);
            functionBase.profileNameLoading(username, sendUserOb);
            send_date.setText(functionBase.dateToString(dmChatOb.getCreatedAt()));;

            if(dmChatOb.getString("image_cdn") != null){


                send_image_layout.setVisibility(View.VISIBLE);
                functionBase.generalImageLoading(send_image, dmChatOb, requestManager);


            } else {


                send_image_layout.setVisibility(View.GONE);
            }

            if(dmChatOb.get("body") != null){


                send_text.setVisibility(View.VISIBLE);
                send_text.setText(dmChatOb.getString("body"));

            } else {


                send_text.setVisibility(View.GONE);
            }

        }

        //기능 추가

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
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
