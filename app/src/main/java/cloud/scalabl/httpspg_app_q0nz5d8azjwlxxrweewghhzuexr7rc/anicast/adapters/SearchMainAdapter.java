package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchResultActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SearchHeaderViewHolder;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.SearchMainViewHolder;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 5. 15..
 */

public class SearchMainAdapter extends RecyclerParseAdapter {

    int viewType;

    private interface OnQueryLoadListener<ParseObject> {

        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);

    }

    private ParseQueryAdapter.QueryFactory<ParseObject> queryFactory;
    private List<ParseObject> items;
    private List<String> tagCloud;

    private String queryString;
    private String typeString;

    private FunctionBase functionBase;

    public SearchMainAdapter(Context context, String type, RequestManager requestManager, List<ParseObject> items, List<String> tagCloudList) {

        super(context);

        List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
        int currentPage = 0;
        List<List<ParseObject>> objectPages = new ArrayList<>();
        this.items = new ArrayList<>();
        this.typeString = type;
        RequestManager requestManager1 = requestManager;
        this.items = items;
        this.tagCloud = tagCloudList;
        this.functionBase = new FunctionBase(context);


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View searchView;

        if(viewType == 0){

            searchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search_main_header, parent, false);

            return new SearchHeaderViewHolder(searchView);

        } else {

            searchView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_search_main, parent, false);

            return new SearchMainViewHolder(searchView);

        }



    }

    private String typeMaker(String type){

        switch (type){

            case "all":

                return "전체";

            case "post":

                return "일러스트";

            case "webtoon":

                return "웹툰";

            case "novel":

                return "웹소설";

            case "youtube":

                return "영상";

            default:

                return "선택 안됨";
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof SearchHeaderViewHolder){

            TextView typeView = ((SearchHeaderViewHolder) holder).getFilterType();
            final TagGroup tagGroup = ((SearchHeaderViewHolder)holder).getTagGroup();

            typeView.setText(typeMaker(typeString));

            tagGroup.setTags(tagCloud);
            tagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
                @Override
                public void onTagClick(String tag) {

                    Intent intent = new Intent(context, SearchResultActivity.class);
                    intent.putExtra("query", tag);
                    intent.putExtra("type", typeString);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);


                }
            });

        } else {

            final ParseObject contentOb = getItem(position);

            TextView search_query = ((SearchMainViewHolder)holder).getSearchQuery();
            TextView search_date = ((SearchMainViewHolder)holder).getSearchDate();
            LinearLayout search_item_layout = ((SearchMainViewHolder)holder).getSearchItemLayout();

            search_query.setText( contentOb.getString("query") );
            search_date.setText(functionBase.dateToStringForDisplay(contentOb.getCreatedAt()));

            search_item_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, SearchResultActivity.class);
                    intent.putExtra("query", contentOb.getString("query"));
                    intent.putExtra("type", typeString);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });

        }

    }

    @Override
    public int getItemViewType(int position) {

        return position;

    }

    @Override
    public int getItemCount() {

        Log.d("item size", String.valueOf( items.size() ));

        return items.size()+1;
    }

    @Override
    public ParseObject getItem(int position) {
        return items.get(position-1);
    }



}
