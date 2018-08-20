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
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionPost;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineItemViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineWebtoonHeaderViewHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class ArtistWebtoonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    private String filterString;
    private FunctionBase functionBase;

    public ArtistWebtoonAdapter(Context context, RequestManager requestManager, final String filter) {

        ArtistWebtoonAdapter.requestManager = requestManager;
        ArtistWebtoonAdapter.context = context;
        currentUser = ParseUser.getCurrentUser();
        items = new ArrayList<>();
        objectPages = new ArrayList<>();
        this.currentPage = 0;
        this.paginationEnabled = true;
        this.hasNextPage = true;
        this.onQueryLoadListeners = new ArrayList<>();
        this.functionBase = new FunctionBase(context);

        this.filterString = filter;

        queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
            @Override
            public ParseQuery<ParseObject> create() {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                query.whereEqualTo("status", true);
                query.whereEqualTo("open_flag", true);
                query.whereEqualTo("post_type", "seriese");
                query.whereEqualTo("content_type", "webtoon");
                query.include("user");
                query.orderByDescending("last_update");
                query.addDescendingOrder("updatedAt");

                if(filterString.length() != 0){

                    ArrayList<String> queryString = new ArrayList<>();
                    queryString.add(filterString);

                    query.whereContainedIn("tag_array", queryString);

                }

                return query;
            }
        };

        loadObjects(currentPage);



    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == 0){

            View headerView;

            headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_webtoon_header, parent, false);

            return new TimelineWebtoonHeaderViewHolder(headerView);

        } else {

            View timelineView;

            timelineView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_item6, parent, false);

            return new TimelineItemViewHolder(timelineView);

        }

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position == 0){

            LinearLayout all_button = ((TimelineWebtoonHeaderViewHolder)holder).getAllButton();
            final ImageView all_icon = ((TimelineWebtoonHeaderViewHolder)holder).getAllIcon();
            final TextView all_text = ((TimelineWebtoonHeaderViewHolder)holder).getAllText();

            LinearLayout ilsang_button = ((TimelineWebtoonHeaderViewHolder)holder).getIlSangButton();
            final ImageView ilsang_icon = ((TimelineWebtoonHeaderViewHolder)holder).getIlSangIcon();
            final TextView ilsang_text = ((TimelineWebtoonHeaderViewHolder)holder).getIlSangText();

            LinearLayout soonjung_button = ((TimelineWebtoonHeaderViewHolder)holder).getSoonJungButton();
            final ImageView soonjung_icon = ((TimelineWebtoonHeaderViewHolder)holder).getSoonJungIcon();
            final TextView soonjung_text = ((TimelineWebtoonHeaderViewHolder)holder).getSoonJungText();

            LinearLayout gag_button = ((TimelineWebtoonHeaderViewHolder)holder).getGagButton();
            final ImageView gag_icon = ((TimelineWebtoonHeaderViewHolder)holder).getGagIcon();
            final TextView gag_text = ((TimelineWebtoonHeaderViewHolder)holder).getGagText();

            LinearLayout action_button = ((TimelineWebtoonHeaderViewHolder)holder).getActionButton();
            final ImageView action_icon = ((TimelineWebtoonHeaderViewHolder)holder).getActionIcon();
            final TextView action_text = ((TimelineWebtoonHeaderViewHolder)holder).getActionText();

            LinearLayout romance_button = ((TimelineWebtoonHeaderViewHolder)holder).getRomanceButton();
            final ImageView romance_icon = ((TimelineWebtoonHeaderViewHolder)holder).getRomanceIcon();
            final TextView romance_text = ((TimelineWebtoonHeaderViewHolder)holder).getRomanceText();

            LinearLayout thriller_button = ((TimelineWebtoonHeaderViewHolder)holder).getThrillerButton();
            final ImageView thriller_icon = ((TimelineWebtoonHeaderViewHolder)holder).getThrillerIcon();
            final TextView thriller_text = ((TimelineWebtoonHeaderViewHolder)holder).getThrillerText();

            LinearLayout fantasy_button = ((TimelineWebtoonHeaderViewHolder)holder).getFantasyButton();
            final ImageView fantasy_icon = ((TimelineWebtoonHeaderViewHolder)holder).getFantasyIcon();
            final TextView fantasy_text = ((TimelineWebtoonHeaderViewHolder)holder).getFantasyText();

            LinearLayout sports_button = ((TimelineWebtoonHeaderViewHolder)holder).getSportsButton();
            final ImageView sports_icon = ((TimelineWebtoonHeaderViewHolder)holder).getSportsIcon();
            final TextView sports_text = ((TimelineWebtoonHeaderViewHolder)holder).getSportsText();

            LinearLayout hackwon_button = ((TimelineWebtoonHeaderViewHolder)holder).getHackwonButton();
            final ImageView hackwon_icon = ((TimelineWebtoonHeaderViewHolder)holder).getHackwonIcon();
            final TextView hackwon_text = ((TimelineWebtoonHeaderViewHolder)holder).getHackwonText();

            LinearLayout top_layout = ((TimelineWebtoonHeaderViewHolder)holder).getTopLayout();
            top_layout.setVisibility(View.GONE);

            switch (filterString){

                case "":

                    all_icon.setColorFilter(functionBase.likedColor);
                    all_text.setTextColor(functionBase.likedColor);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);

                    break;

                case "일상":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.likedColor);
                    ilsang_text.setTextColor(functionBase.likedColor);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);

                    break;

                case "순정":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.likedColor);
                    soonjung_text.setTextColor(functionBase.likedColor);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);

                    break;

                case "개그":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.likedColor);
                    gag_text.setTextColor(functionBase.likedColor);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);

                    break;

                case "액션":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.likedColor);
                    action_text.setTextColor(functionBase.likedColor);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);

                    break;


                case "로맨스":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.likedColor);
                    romance_text.setTextColor(functionBase.likedColor);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);

                    break;

                case "스릴러":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.likedColor);
                    thriller_text.setTextColor(functionBase.likedColor);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);

                    break;

                case "판타지":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.likedColor);
                    fantasy_text.setTextColor(functionBase.likedColor);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);


                    break;

                case "스포츠":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.likedColor);
                    sports_text.setTextColor(functionBase.likedColor);

                    hackwon_icon.setColorFilter(functionBase.mainColor2);
                    hackwon_text.setTextColor(functionBase.mainColor2);

                    break;

                case "학원":

                    all_icon.setColorFilter(functionBase.mainColor2);
                    all_text.setTextColor(functionBase.mainColor2);

                    ilsang_icon.setColorFilter(functionBase.mainColor2);
                    ilsang_text.setTextColor(functionBase.mainColor2);

                    soonjung_icon.setColorFilter(functionBase.mainColor2);
                    soonjung_text.setTextColor(functionBase.mainColor2);

                    gag_icon.setColorFilter(functionBase.mainColor2);
                    gag_text.setTextColor(functionBase.mainColor2);

                    action_icon.setColorFilter(functionBase.mainColor2);
                    action_text.setTextColor(functionBase.mainColor2);

                    romance_icon.setColorFilter(functionBase.mainColor2);
                    romance_text.setTextColor(functionBase.mainColor2);

                    thriller_icon.setColorFilter(functionBase.mainColor2);
                    thriller_text.setTextColor(functionBase.mainColor2);

                    fantasy_icon.setColorFilter(functionBase.mainColor2);
                    fantasy_text.setTextColor(functionBase.mainColor2);

                    sports_icon.setColorFilter(functionBase.mainColor2);
                    sports_text.setTextColor(functionBase.mainColor2);

                    hackwon_icon.setColorFilter(functionBase.likedColor);
                    hackwon_text.setTextColor(functionBase.likedColor);

                    break;

            }


            all_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });


            ilsang_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "일상";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });

            soonjung_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "순정";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });


             gag_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "개그";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });


            action_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "액션";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });


            romance_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "로맨스";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });


            thriller_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "스릴러";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });


            fantasy_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "판타지";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });


            sports_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "스포츠";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });

            hackwon_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    filterString = "학원";

                    queryFactory = new ParseQueryAdapter.QueryFactory<ParseObject>() {
                        @Override
                        public ParseQuery<ParseObject> create() {

                            ParseQuery<ParseObject> query = ParseQuery.getQuery("ArtistPost");
                            query.whereEqualTo("status", true);
                            query.whereEqualTo("open_flag", true);
                            query.whereEqualTo("post_type", "seriese");
                            query.whereEqualTo("content_type", "webtoon");
                            query.include("user");
                            query.orderByDescending("updatedAt");

                            if(filterString.length() != 0){

                                ArrayList<String> queryString = new ArrayList<>();
                                queryString.add(filterString);

                                query.whereContainedIn("tag_array", queryString);

                            }

                            return query;
                        }
                    };

                    loadObjects(currentPage);

                }
            });

        } else {

            final ParseObject timelineOb = getItem(position);

            FunctionPost functionPost = new FunctionPost(context);
            functionPost.WebtoonSeriesePostBuilder( timelineOb, holder, requestManager);

        }

    }


    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);

       

    }


    @Override
    public int getItemViewType(int position) {

        return position;

    }




    @Override
    public int getItemCount() {

        Log.d("count", String.valueOf(items.size()));

        return items.size() + 1;
    }


    public ParseObject getItem(int position) {
        return items.get(position - 1);
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
