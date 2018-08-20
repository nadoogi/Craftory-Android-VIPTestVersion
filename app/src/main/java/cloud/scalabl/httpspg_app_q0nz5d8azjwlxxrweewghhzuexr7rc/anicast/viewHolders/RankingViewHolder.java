package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class RankingViewHolder extends RecyclerView.ViewHolder {

    TextView ranking_text;
    CircleImageView ranker_photo;
    TextView ranker_name;
    TextView gifted_point;
    LinearLayout ranking_layout;
    ImageView icon_ranking;

    CircleImageView profile_image;

    public RankingViewHolder(View itemView) {
        super(itemView);

        ranking_text = (TextView) itemView.findViewById(R.id.ranking_text);
        ranker_photo = (CircleImageView) itemView.findViewById(R.id.ranker_photo);
        ranker_name = (TextView) itemView.findViewById(R.id.ranker_name);
        gifted_point = (TextView) itemView.findViewById(R.id.gifted_point);
        ranking_layout = (LinearLayout) itemView.findViewById(R.id.ranking_layout);
        icon_ranking = (ImageView) itemView.findViewById(R.id.icon_ranking);
        profile_image = (CircleImageView) itemView.findViewById(R.id.profile_image);

    }

    public TextView getRankingText(){return ranking_text;}
    public CircleImageView getRankerPhoto(){return ranker_photo;}
    public TextView getRankingName(){return ranker_name;}
    public TextView getGiftedPoint(){return gifted_point;}
    public LinearLayout getRankingLayout(){return ranking_layout;}
    public ImageView getIconRanking(){return icon_ranking;}
    public CircleImageView getProfileImage(){return profile_image;}

}
