package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.FundingItemDetailEditListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.NovelEditListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.NovelContentViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.NovelHeaderViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class FundingMarketItemEditorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    private ParseObject itemObject;
    private FunctionBase functionBase;
    private AppCompatActivity activity;
    private Boolean Editable;

    private FundingItemDetailEditListener fundingItemDetailEditListener;

    public void setEditListener(FundingItemDetailEditListener fundingItemDetailEditListener){

        this.fundingItemDetailEditListener = fundingItemDetailEditListener;

    }


    public FundingMarketItemEditorAdapter( AppCompatActivity act, Context context, RequestManager requestManager, ParseObject fundingItemOb) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.itemObject = fundingItemOb;
        this.functionBase = new FunctionBase(context);
        this.activity = act;
        this.Editable = true;
        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = itemObject.getRelation("info_content").getQuery();
                query.whereEqualTo("status", true);
                query.orderByAscending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);



    }

    public FundingMarketItemEditorAdapter(  Context context, RequestManager requestManager, ParseObject fundingItemOb) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.itemObject = fundingItemOb;
        this.functionBase = new FunctionBase(context);
        this.Editable = false;
        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = itemObject.getRelation("info_content").getQuery();
                query.whereEqualTo("status", true);
                query.orderByAscending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View contentView;

        contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_novel_content, parent, false);

        return new NovelContentViewHolder(contentView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //기능 추가
        final ParseObject novelContentOb = getItem(position);

        TextView novel_string = ((NovelContentViewHolder)holder).getNovelString();
        ImageView novel_image = ((NovelContentViewHolder)holder).getNovelImage();
        final LinearLayout novel_content_layout = ((NovelContentViewHolder)holder).getNovelContentLayout();


        if(novelContentOb.getString("type").equals("String")){

            novel_string.setVisibility(View.VISIBLE);
            novel_image.setVisibility(View.GONE);

            novel_string.setText( novelContentOb.getString("content"));

        } else {

            novel_string.setVisibility(View.GONE);
            novel_image.setVisibility(View.VISIBLE);

            requestManager
                    .load(functionBase.imageUrlBase300 + novelContentOb.getString("content"))
                    .into(novel_image);

        }

        if(Editable){

            final int defaultColor = Color.parseColor("#00ffffff");
            final int selectedColor = Color.parseColor("#FFFF99");

            novel_content_layout.setBackgroundColor(defaultColor);
            novel_content_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    novel_content_layout.setBackgroundColor(selectedColor);


                    PopupMenu popup = new PopupMenu(activity, novel_content_layout);
                    //Inflating the Popup using xml file

                    popup.getMenuInflater().inflate(R.menu.novel_option_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            if(item.getItemId() == R.id.edit){

                                fundingItemDetailEditListener.onEditorModeOpen(novelContentOb);

                            } else if(item.getItemId() == R.id.delete){

                                novelContentOb.put("status", false);
                                novelContentOb.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            TastyToast.makeText(context, "삭제 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
                                            loadObjects(0);

                                        } else {

                                            e.printStackTrace();
                                        }

                                    }
                                });

                            }

                            novel_content_layout.setBackgroundColor(defaultColor);

                            return true;
                        }
                    });

                    popup.show();//showing popup menu

                    popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                        @Override
                        public void onDismiss(PopupMenu menu) {

                            novel_content_layout.setBackgroundColor(defaultColor);

                        }
                    });


                }
            });

        }
        //footer

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
