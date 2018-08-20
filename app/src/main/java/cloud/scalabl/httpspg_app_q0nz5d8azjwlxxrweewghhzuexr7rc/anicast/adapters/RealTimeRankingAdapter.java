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
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.SearchResultActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.UserActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.functions.FunctionBase;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.RankingViewHolder;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class RealTimeRankingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public  interface OnQueryLoadListener<ParseObject> {
        public void onLoading();
        public void onLoaded(List<ParseObject> objects, Exception e);
    }

    private List<OnQueryLoadListener<ParseObject>> onQueryLoadListeners = new ArrayList<>();
    private int objectsPerPage = 25;
    private boolean paginationEnabled = true;
    private boolean hasNextPage = true;
    private int currentPage = 0;
    private RequestManager requestManager;
    protected Context context;
    protected ParseUser currentUser;

    private ArrayList<Object> tagList;

    public RealTimeRankingAdapter(Context context, RequestManager requestManager, ArrayList<Object> tagList ) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.tagList = tagList;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rankingView;

        rankingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_realtime_tag, parent, false);

        return new RankingViewHolder(rankingView);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //기능 추가
        final Object dataOb = getItem(position);

        Log.d("data", dataOb.toString());

        TextView ranking_text = ((RankingViewHolder)holder).getRankingText();
        TextView ranker_name = ((RankingViewHolder)holder).getRankingName();
        TextView gifted_point = ((RankingViewHolder)holder).getGiftedPoint();
        LinearLayout ranking_layout = ((RankingViewHolder)holder).getRankingLayout();
        ImageView icon_ranking = ((RankingViewHolder)holder).getIconRanking();

        ranking_text.setText(String.valueOf(position+1));

        if(position == 0){

            icon_ranking.setVisibility(View.VISIBLE);

        } else {

            icon_ranking.setVisibility(View.GONE);
        }

        FunctionBase functionBase = new FunctionBase(context);

        ranker_name.setText(((HashMap<String, Object>)dataOb).get("tag").toString());
        gifted_point.setText(((HashMap<String, Object>)dataOb).get("count").toString());


        ranking_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, SearchResultActivity.class);
                intent.putExtra("query", ((HashMap<String, Object>)dataOb).get("tag").toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });


        //기능 추가

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return tagList.size();
    }


    public Object getItem(int position) {

        return tagList.get(position);
    }





}
