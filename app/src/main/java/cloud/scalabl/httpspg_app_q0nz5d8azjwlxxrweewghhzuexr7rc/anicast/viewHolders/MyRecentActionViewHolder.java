package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;


/**
 * Created by ssamkyu on 2017. 7. 10..
 */

public class MyRecentActionViewHolder extends RecyclerView.ViewHolder {

    private TextView more_info_button;
    private RecyclerView follow_list;
    private LinearLayout follow_detail_button;

    public MyRecentActionViewHolder(View itemView) {
        super(itemView);

        more_info_button = (TextView) itemView.findViewById(R.id.more_info_button);
        follow_list = (RecyclerView) itemView.findViewById(R.id.follow_list);
        follow_detail_button = (LinearLayout) itemView.findViewById(R.id.follow_detail_button);

    }

    public TextView getMoreInfoButton(){return more_info_button;}
    public RecyclerView getFollowList(){return follow_list;}
    public LinearLayout getFollowDetailButton(){return follow_detail_button;}


}
