package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.cloudinary.Transformation;
import com.cloudinary.android.MediaManager;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.PatronDetailActivity;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders.TimelineFundingItemHolder;


/**
 * Created by ssamkyu on 2017. 5. 8..
 */

public class FundingMarketTimelineImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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

    private ArrayList<String> images;
    private ParseObject patronObject;

    public FundingMarketTimelineImageAdapter(Context context, RequestManager requestManager, ArrayList<String> images , ParseObject patronOb) {

        this.requestManager = requestManager;
        this.context = context;
        this.currentUser = ParseUser.getCurrentUser();
        this.images = images;
        this.patronObject = patronOb;

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rankingView;

        rankingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_timeline_fundingitem, parent, false);

        return new TimelineFundingItemHolder(rankingView);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        String imageUrl = images.get(position);

        ImageView imageView = ((TimelineFundingItemHolder)holder).getImage();

        String[] imageUrlArrray = imageUrl.split("/");
        String imageFileName = imageUrlArrray[1];

        requestManager
                .load(MediaManager.get().url().transformation(new Transformation().width("300").gravity("faces").crop("fill").quality("auto").fetchFormat("auto")).generate(imageFileName))
                //.apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .thumbnail(0.3f)
                .transition(new DrawableTransitionOptions().crossFade())
                .into(imageView);

        //기능 추가

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, PatronDetailActivity.class);
                intent.putExtra("postId", patronObject.getObjectId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }


    @Override
    public int getItemCount() {
        return images.size();
    }


    public Object getItem(int position) {

        return images.get(position);
    }





}
