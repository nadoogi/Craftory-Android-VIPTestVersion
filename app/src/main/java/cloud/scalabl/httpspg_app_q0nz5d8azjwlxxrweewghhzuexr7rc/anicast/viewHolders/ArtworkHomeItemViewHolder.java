package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 8. 25..
 */

public class ArtworkHomeItemViewHolder extends RecyclerView.ViewHolder {

    private TextView home_menu_title;
    private RecyclerView home_horizon_list;

    public ArtworkHomeItemViewHolder(View itemView) {
        super(itemView);

        home_menu_title = (TextView) itemView.findViewById(R.id.home_menu_title);
        home_horizon_list = (RecyclerView) itemView.findViewById(R.id.home_horizon_list);

    }

    public RecyclerView getHomeHorizonList(){return home_horizon_list;}
    public TextView getHomeMenuTitle(){return home_menu_title;}


}
