package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 8. 25..
 */

public class ArtworkCommercialHeaderViewHolder extends RecyclerView.ViewHolder {

    TagGroup tag_group;
    TextView recent_filter_button;
    TextView recommend_filter_button;
    TextView favor_filter_button;

    public ArtworkCommercialHeaderViewHolder(View itemView) {
        super(itemView);

        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);
        recent_filter_button = (TextView) itemView.findViewById(R.id.recent_filter_button);
        recommend_filter_button = (TextView) itemView.findViewById(R.id.recommend_filter_button);
        favor_filter_button = (TextView) itemView.findViewById(R.id.favor_filter_button);

    }

    public TagGroup getTagGroup(){return tag_group;}
    public TextView getRecentFilterButton(){return recent_filter_button;}
    public TextView getRecommendFilterButton(){return recommend_filter_button;}
    public TextView getFavorFilterButton(){return favor_filter_button;}

}
