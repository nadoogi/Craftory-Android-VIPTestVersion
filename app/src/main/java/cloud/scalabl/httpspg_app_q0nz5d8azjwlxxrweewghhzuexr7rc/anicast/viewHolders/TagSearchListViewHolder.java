package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class TagSearchListViewHolder extends RecyclerView.ViewHolder {

    TextView ranking_text;
    TextView ranker_name;
    TextView result_count;
    LinearLayout ranking_layout;
    ImageView icon_ranking;

    public TagSearchListViewHolder(View itemView) {
        super(itemView);

        ranking_text = (TextView) itemView.findViewById(R.id.ranking_text);
        ranker_name = (TextView) itemView.findViewById(R.id.ranker_name);
        result_count = (TextView) itemView.findViewById(R.id.result_count);
        ranking_layout = (LinearLayout) itemView.findViewById(R.id.ranking_layout);
        icon_ranking = (ImageView) itemView.findViewById(R.id.icon_ranking);

    }

    public TextView getRankingText(){return ranking_text;}
    public TextView getRankingName(){return ranker_name;}
    public TextView getResultCount(){return result_count;}
    public LinearLayout getRankingLayout(){return ranking_layout;}
    public ImageView getIconRanking(){return icon_ranking;}

}
