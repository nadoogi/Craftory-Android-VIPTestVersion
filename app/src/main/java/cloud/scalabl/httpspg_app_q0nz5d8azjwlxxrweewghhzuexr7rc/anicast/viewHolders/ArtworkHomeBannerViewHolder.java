package cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import cloud.scalabl.httpspg_app_q0nz5d8azjwlxxrweewghhzuexr7rc.anicast.R;

/**
 * Created by ssamkyu on 2017. 8. 25..
 */

public class ArtworkHomeBannerViewHolder extends RecyclerView.ViewHolder {

    RecyclerView home_banner_list;

    public ArtworkHomeBannerViewHolder(View itemView) {
        super(itemView);

        home_banner_list = (RecyclerView) itemView.findViewById(R.id.home_banner_list);

    }

    public RecyclerView getHomeBannerList(){return home_banner_list;}


}
