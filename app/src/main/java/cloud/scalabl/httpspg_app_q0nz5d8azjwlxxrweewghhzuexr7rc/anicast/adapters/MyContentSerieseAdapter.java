package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SerieseManagerActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SerieseListViewHolder;
import me.gujun.android.taggroup.TagGroup;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class MyContentSerieseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    private final int Header_View_Type = 1000;
    private ParseUser currentUser;

    private FunctionBase functionBase;
    private AppCompatActivity activity;

    public MyContentSerieseAdapter(AppCompatActivity activity, Context context, RequestManager requestManager) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.items = new ArrayList<>();
        this.objectPages = new ArrayList<>();
        this.onQueryLoadListeners = new ArrayList<>();
        this.currentPage = 0;
        this.functionBase = new FunctionBase(context);
        this.activity = activity;

        this.queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                query.whereEqualTo("user", currentUser);
                query.whereEqualTo("post_type", "seriese");
                query.whereEqualTo("status", true);
                query.whereEqualTo("open_flag", true);
                query.include("user");
                query.orderByDescending("createdAt");

                return query;
            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View timelineView;

        timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_seriese, parent, false);

        return new SerieseListViewHolder(timelineView);

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        final ParseObject timelineOb = getItem(position);

        ImageView seriese_image = ((SerieseListViewHolder)holder).getSerieseImage();
        TextView post_body = ((SerieseListViewHolder)holder).getPostBody();
        ImageView post_type_image = ((SerieseListViewHolder)holder).getPostTypeImage();

        LinearLayout post_tag_layout = ((SerieseListViewHolder)holder).getPostTagLayout();
        TagGroup tagGroup = ((SerieseListViewHolder)holder).getTagGroup();
        LinearLayout delete_button = ((SerieseListViewHolder)holder).getDeleteButton();

        if(timelineOb.get("image_cdn") != null){

            requestManager
                    .load(functionBase.imageUrlBase300 + timelineOb.getString("image_cdn") )
                    .into(seriese_image);

        }

        post_body.setText( timelineOb.getString("title") );

        if(timelineOb.getString("content_type").equals("post")){

            int white = Color.parseColor("#ffffff");
            post_type_image.setImageResource(R.drawable.icon_post);


        } else if( timelineOb.getString("content_type").equals("webtoon") ){

            int white = Color.parseColor("#ffffff");
            post_type_image.setImageResource(R.drawable.icon_webtoon);


        } else if( timelineOb.getString("content_type").equals("album") ){

            int white = Color.parseColor("#ffffff");
            post_type_image.setImageResource(R.drawable.icon_album);


        } else if( timelineOb.getString("content_type").equals("novel") ){

            int white = Color.parseColor("#ffffff");
            post_type_image.setImageResource(R.drawable.icon_novel);


        } else if( timelineOb.getString("content_type").equals("youtube") ){

            int white = Color.parseColor("#ffffff");
            post_type_image.setImageResource(R.drawable.icon_youtube);


        }

        seriese_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SerieseManagerActivity.class);
                intent.putExtra("serieseId", timelineOb.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        if(timelineOb.get("tag_array") != null){

            post_tag_layout.setVisibility(View.VISIBLE);
            JSONArray tagArray = timelineOb.getJSONArray("tag_array");

            tagGroup.setTags( functionBase.jsonArrayToArrayList(tagArray) );


        } else {

            post_tag_layout.setVisibility(View.GONE);
        }

        if(delete_button != null){

            delete_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);

                    builder.title("확인");
                    builder.content("삭제 하시겠습니까?");
                    builder.positiveText("예");
                    builder.negativeText("아니요");
                    builder.onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {



                        }
                    });

                    builder.onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                            timelineOb.put("status", false);
                            timelineOb.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {

                                    if(e==null){

                                        if(currentUser != null){

                                            currentUser.increment("serise_count",-1);
                                            currentUser.saveInBackground(new SaveCallback() {
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




                                    } else {

                                        e.printStackTrace();
                                    }

                                }
                            });

                        }
                    });
                    builder.show();



                }
            });

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
