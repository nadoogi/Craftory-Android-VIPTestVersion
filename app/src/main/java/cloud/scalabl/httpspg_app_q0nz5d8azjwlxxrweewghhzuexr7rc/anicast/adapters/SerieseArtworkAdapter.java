package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseItemEditIllustActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseItemEditPostActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseItemEditWebtoonActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseManagerDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FileOpenClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.ImageInfoClickListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SerieseMGListViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class SerieseArtworkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private ArrayList<ParseObject> items = new ArrayList<>();
    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<List<ParseObject>> objectPages = new ArrayList<>();;
    private int objectsPerPage = 1000;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;
    private ParseObject parentObject;

    private int HeaderViewType = 0;
    private int ContentViewType = 1;
    private int ListViewType = 2;

    int pastVisibleItems, visibleItemCount, totalItemCount;

    private String commercial_status = "default";


    private FunctionBase functionBase;


    public void setFileOpenClickListener(FileOpenClickListener fileOpenClickListener){

        FileOpenClickListener fileOpenClickListener1 = fileOpenClickListener;

    }

    public void setImageInfoClickListener(ImageInfoClickListener imageInfoClickListener){

        ImageInfoClickListener imageInfoClickListener1 = imageInfoClickListener;

    }


    public SerieseArtworkAdapter(Context context, RequestManager requestManager, final ParseObject parentOb ) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.parentObject = parentOb;
        Boolean monFlag = false;
        Boolean tueFlag = false;
        Boolean wenFlag = false;
        Boolean thuFlag = false;
        Boolean friFlag = false;
        Boolean satFlag = false;
        Boolean sunFlag = false;
        this.functionBase = new FunctionBase(context);

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = parentOb.getRelation("seriese_item").getQuery();
                query.include("commercial");
                query.include("user");
                query.orderByDescending("order");

                return query;
            }
        };

        loadObjects(currentPage);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View timelineView;

        timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_commercial_edit_item, parent, false);

        return new SerieseMGListViewHolder(timelineView);


    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final ParseObject timelineOb = getItem(position);

        ImageView seriese_image = ((SerieseMGListViewHolder)holder).getSerieseImage();
        TextView post_body = ((SerieseMGListViewHolder)holder).getPostBody();
        TextView post_updated = ((SerieseMGListViewHolder)holder).getUpdated();
        TextView free_date = ((SerieseMGListViewHolder)holder).getFreeDate();
        TextView price = ((SerieseMGListViewHolder)holder).getPrice();
        TextView type = ((SerieseMGListViewHolder)holder).getType();
        TextView order = ((SerieseMGListViewHolder)holder).getOrder();
        TextView open_status = ((SerieseMGListViewHolder)holder).getOpenStatus();

        functionBase.generalImageLoading(seriese_image, timelineOb, requestManager);

        if(timelineOb.get("title") != null){

            post_body.setText( timelineOb.getString("title") );

        } else {

            post_body.setText( timelineOb.getString("body") );

        }

        if(timelineOb.get("order") != null){

            order.setText("회차: " + timelineOb.getInt("order") +"화");

        } else {

            order.setText("미입력");

        }

        if(timelineOb.get("open_flag") != null){

            if(timelineOb.getBoolean("open_flag")){

                open_status.setText("[공개]");

            } else {

                open_status.setText("[비공개]");

            }

        } else {

            open_status.setText("[공개]");

        }

        if(timelineOb.get("commercial") != null){

            ParseObject commercialOb = timelineOb.getParseObject("commercial");

            post_updated.setText( "오픈일: " + functionBase.dateToStringForDisplay( commercialOb.getDate("open_date") ) );


            if(commercialOb.get("type") != null){

                if(commercialOb.getString("type").equals("free")){

                    type.setText("무료");
                    type.setVisibility(View.VISIBLE);
                    price.setVisibility(View.GONE);
                    free_date.setVisibility(View.GONE);

                } else if(commercialOb.getString("type").equals("charge")) {

                    type.setText("유료");
                    type.setVisibility(View.VISIBLE);
                    price.setVisibility(View.VISIBLE);
                    price.setText( functionBase.makeComma(commercialOb.getInt("price")) + " BOX"  );
                    free_date.setVisibility(View.GONE);


                } else if(commercialOb.getString("type").equals("preview_charge")){

                    type.setText("부분유료(미리보기 구매)");
                    type.setVisibility(View.VISIBLE);
                    price.setVisibility(View.VISIBLE);
                    price.setText( functionBase.makeComma(commercialOb.getInt("price")) + " BOX"  );
                    free_date.setVisibility(View.VISIBLE);
                    free_date.setText( "무료 공개일: " + functionBase.dateToStringForDisplay( commercialOb.getDate("free_date") ) );

                }

            } else {

                type.setText("무료");
                type.setVisibility(View.VISIBLE);
                price.setVisibility(View.GONE);
                free_date.setVisibility(View.GONE);

            }


        } else {

            free_date.setVisibility(View.GONE);
            price.setVisibility(View.GONE);
            type.setVisibility(View.GONE);
            post_updated.setVisibility(View.VISIBLE);
            post_updated.setText("설정 안됨");

        }

        /*
        post_setting_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SerieseManagerDetailActivity.class);
                intent.putExtra("parentId", parentObject.getObjectId());
                intent.putExtra("parent_status", parentObject.getString("seriese_status"));
                intent.putExtra("artworkId", timelineOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        */

        seriese_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(parentObject.getString("content_type").equals("post")){

                    Intent intent = new Intent(context, SerieseItemEditPostActivity.class);
                    intent.putExtra("postId", timelineOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                } else if(parentObject.getString("content_type").equals("album")){

                    Intent intent = new Intent(context, SerieseItemEditIllustActivity.class);
                    intent.putExtra("postId", timelineOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                } else if(parentObject.getString("content_type").equals("webtoon")){

                    Intent intent = new Intent(context, SerieseItemEditWebtoonActivity.class);
                    intent.putExtra("postId", timelineOb.getObjectId());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }

            }
        });

    }

    @Override
    public int getItemViewType(int position) {

        return position;

    }


    @Override
    public int getItemCount() {
        return items.size() ;
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
