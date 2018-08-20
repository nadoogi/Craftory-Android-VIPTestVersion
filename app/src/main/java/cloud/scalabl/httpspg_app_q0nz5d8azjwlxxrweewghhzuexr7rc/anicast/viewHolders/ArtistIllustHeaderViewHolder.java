package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class ArtistIllustHeaderViewHolder extends RecyclerView.ViewHolder {

    TagGroup tag_group;
    RecyclerView best_illust_list;
    RecyclerView weekly_best_list;
    LinearLayout recommend_button;
    LinearLayout recent_button;
    LinearLayout weekly_best_layout;

    TextView recommand_text;
    TextView recent_text;

    public ArtistIllustHeaderViewHolder(View itemView) {
        super(itemView);

        tag_group = (TagGroup) itemView.findViewById(R.id.tag_group);
        best_illust_list = (RecyclerView) itemView.findViewById(R.id.best_illust_list);
        weekly_best_list = (RecyclerView) itemView.findViewById(R.id.weekly_best_list);
        recommend_button = (LinearLayout) itemView.findViewById(R.id.recommand_button);
        recent_button = (LinearLayout) itemView.findViewById(R.id.recent_button);
        weekly_best_layout = (LinearLayout) itemView.findViewById(R.id.weekly_best_layout);

        recommand_text = (TextView) itemView.findViewById(R.id.recommand_text);
        recent_text = (TextView) itemView.findViewById(R.id.recent_text);

    }

    public TagGroup getTagGroup(){return tag_group;}
    public RecyclerView getBestIllustList(){return best_illust_list;}
    public RecyclerView getWeeklyBestList(){return weekly_best_list;}
    public LinearLayout getRecommendButton(){return recommend_button;}
    public LinearLayout getRecentButton(){return recent_button;}
    public LinearLayout getWeeklyBestLayout(){return weekly_best_layout;}

    public TextView getRecommandText(){return recommand_text;}
    public TextView getRecentText(){return recent_text;}


}
