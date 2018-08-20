package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.graphics.Color;
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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.listeners.NovelEditListener;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.models.ArtistPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.NovelContentViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.NovelHeaderViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class NovelContentParseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    private ParseObject postObject;
    private FunctionBase functionBase;

    private NovelEditListener novelEditListener;

    public void setNovelEditListener(NovelEditListener novelEditListener){

        this.novelEditListener = novelEditListener;

    }


    public NovelContentParseAdapter(Context context, RequestManager requestManager, final ArtistPost postOb) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.postObject = postOb;
        this.functionBase = new FunctionBase(context);

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = postOb.getRelation("novel_content").getQuery();
                query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
                query.setLimit(1000);
                query.whereEqualTo("status", true);
                query.orderByAscending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        if(viewType == 1000){

            Log.d("viewtype", "footer");
            Log.d("position2", String.valueOf(viewType));
            Log.d("total_count2", String.valueOf(getItemCount()));

            View contentView;

            contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_novel_header, parent, false);

            return new NovelHeaderViewHolder(contentView);

        } else {

            Log.d("viewtype", "content");
            Log.d("position3", String.valueOf(viewType));
            Log.d("total_count3", String.valueOf(getItemCount()));

            View contentView;

            contentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_novel_content, parent, false);

            return new NovelContentViewHolder(contentView);

        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Log.d("position", String.valueOf(position));
        Log.d("getItemCount-1", String.valueOf(getItemCount()-1));

        if(holder instanceof NovelHeaderViewHolder){
            //header
            ImageView reporesent_image = ((NovelHeaderViewHolder)holder).getRepresentImage();
            TextView title = ((NovelHeaderViewHolder)holder).getTitle();
            TextView body = ((NovelHeaderViewHolder)holder).getBody();
            LinearLayout comment_button = ((NovelHeaderViewHolder)holder).getCommentButton();
            LinearLayout playlist_button = ((NovelHeaderViewHolder)holder).getPlaylistButton();

            requestManager
                    .load( functionBase.imageUrlBase300 + postObject.getString("image_cdn"))
                    .into(reporesent_image);

            if(postObject.get("title") != null){

                title.setText(postObject.getString("title"));

            }

            if(postObject.get("body") != null){

                body.setText(postObject.getString("body"));

            }

            comment_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            playlist_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

        } else if(holder instanceof NovelContentViewHolder){


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


            final int defaultColor = Color.parseColor("#00ffffff");
            final int selectedColor = Color.parseColor("#FFFF99");

            novel_content_layout.setBackgroundColor(defaultColor);
            novel_content_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    novel_content_layout.setBackgroundColor(selectedColor);

                    Toast.makeText(context, "hello" + novelContentOb.getObjectId(), Toast.LENGTH_SHORT).show();

                    PopupMenu popup = new PopupMenu(context, novel_content_layout);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.novel_option_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {

                            if(item.getItemId() == R.id.edit){

                                novelEditListener.onEditorModeOpen(novelContentOb);

                            } else if(item.getItemId() == R.id.delete){

                                novelContentOb.put("status", false);
                                novelContentOb.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {

                                        if(e==null){

                                            TastyToast.makeText(context, "삭제 완료", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

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

            //footer


        } else {



        }

        //기능 추가

    }

    @Override
    public int getItemViewType(int position) {

        if(position == getItemCount() -1){

            return 1000;

        } else {

            return position;

        }


    }


    @Override
    public int getItemCount() {
        return items.size()+1;
    }


    public ParseObject getItem(int position) {

        if(position == getItemCount()-1){

            return null;

        } else {

            return items.get(position);

        }

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
