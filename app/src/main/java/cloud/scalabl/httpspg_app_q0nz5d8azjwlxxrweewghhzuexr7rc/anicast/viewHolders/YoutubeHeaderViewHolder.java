package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 5. 31..
 */

public class YoutubeHeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView title;
    private TextView post_date;
    private TextView description;

    LinearLayout post_like_button;
    ImageView like_icon;
    TextView like_count;

    LinearLayout share_button;
    ImageView share_icon;
    TextView share_count;

    TextView pv_count;

    public YoutubeHeaderViewHolder(View itemView) {

        super(itemView);
        title = (TextView) itemView.findViewById(R.id.title);
        post_date = (TextView) itemView.findViewById(R.id.post_date);
        description = (TextView) itemView.findViewById(R.id.description);

        post_like_button = (LinearLayout) itemView.findViewById(R.id.post_like_button);
        like_icon = (ImageView) itemView.findViewById(R.id.like_icon);
        like_count = (TextView) itemView.findViewById(R.id.like_count);

        share_button = (LinearLayout) itemView.findViewById(R.id.share_button);
        share_icon = (ImageView) itemView.findViewById(R.id.share_icon);
        share_count = (TextView) itemView.findViewById(R.id.share_count);

        pv_count = (TextView) itemView.findViewById(R.id.pv_count);

    }

    public TextView getTitle(){return title;}
    public TextView getPostDate(){return post_date;}
    public TextView getDescription(){return description;}

    public LinearLayout getPostLikeButton(){return post_like_button; }
    public ImageView getLikeIcon(){return like_icon;}
    public TextView getLikeCount(){return like_count;}

    public LinearLayout getShareButton(){return share_button;}
    public ImageView getShareIcon(){return share_icon;}
    public TextView getShareCount(){return share_count;}

    public TextView getPvCount(){return pv_count;}

}
